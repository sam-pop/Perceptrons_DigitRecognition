import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

class MLDR {

	static int TOTAL_RECORDS = 20000; //total number of records in file
	static int ATTRIBUTES = 17; //16 attributes + 1 bias
	static double LEARNING_RATE = 0.2;           
	static int theta = 0; 
	static double BIAS = 1.0;

	static Double[][] features = new Double [TOTAL_RECORDS][ATTRIBUTES];
	static String[] classes = new String[TOTAL_RECORDS];
	static int[] outputs = new int [TOTAL_RECORDS];
	static Double[] weights = new Double[ATTRIBUTES];


	public static void main(String args[]){
		int firstClassTotal = 789;
		int[] secondClassSizes = {766,736,805,768,775,773,739,755,747,739,761,792,783,753,803,783,758,748,796,813,764,752,787,786,734};
		String[] letters = {"B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	
		for (int i=0 ; i<secondClassSizes.length ; i++){
			int secondClassTotal = secondClassSizes[i];

			init();
			System.out.println("\nA VS "+letters[i]+":");
			trainig((firstClassTotal/2),(secondClassTotal/2),"A",letters[i]);
			test((firstClassTotal/2),(secondClassTotal/2),"A",letters[i]);
			//System.out.println("\n\n");
		}

	}



	//reads classes and features from file and convert & place them in the appropriate arrays
	//generate random initial values for the weights 
	public static void init(){
		String file = "letter-recognition.data"; //FILE(name & location) to read data from
		BufferedReader br = null;
		String line = "";
		String splitBy = ",";
		int lineCount=0;
		try {

			br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {

				//use comma as separator
				String[] currentLine = line.split(splitBy);
				//enter the line class into the corresponding record#
				classes[lineCount] = currentLine[0];
				//divide the values by 15 and places them in the features array
				for (int k=1 ; k < currentLine.length ; k++){
					Double tmp = Double.parseDouble(currentLine[k]);
					tmp /= 15;
					features[lineCount][k-1] = tmp;
				}
				//enters the bias value
				features[lineCount][ATTRIBUTES-1] = BIAS;
				lineCount++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		//inserts random values to weights
		for (int i=0 ; i < weights.length ; i++){
			weights[i] = randomNum(-1, 1);
		}

	}
	//training the weights with the first half of the data
	public static void trainig(int amountFirstClass, int amountSecondClass, String firstClassLetter, String secondClassLetter){

		int iter=0;
		double error;
		double accuracy = 0, bestAccuracy = 0;
		int output;
		int correctCounter;
		int flag = 0;

		//System.out.println("TRAINING:\n==========");

		do {
			iter++;
			int firstCounter=0;
			int secondCounter=0;
			correctCounter=0;
			//keeps the best accuracy achieved
			if (accuracy > bestAccuracy)
				bestAccuracy = accuracy;

			//epoch start
			for (int i=0 ; i<TOTAL_RECORDS ; i++){

				//going only over the first half of data for this class
				if (firstCounter < amountFirstClass){
					if (classes[i].equals(firstClassLetter)){
						outputs[i] = 1; //insert output manually to outputs array for comparison purposes
						firstCounter++;
						//calc the output
						output = calcOutput(features[i], weights); 
						error = outputs[i] - output; 
						//counts correct outputs
						if (error == 0.0)
							correctCounter++;
						//updating the weights
						for (int j=0 ; j<weights.length ; j++){
							weights[j] += (LEARNING_RATE*error*features[i][j]);
						}
					}
				}

				//going only over the first half of data for this class
				if (secondCounter < amountSecondClass){
					if (classes[i].equals(secondClassLetter)){
						outputs[i] = -1; //insert output manually to outputs array for comparison purposes
						secondCounter++;
						//calc the output
						output = calcOutput(features[i], weights);
						error = outputs[i] - output;
						//counts correct outputs
						if (error == 0.0)
							correctCounter++;
						//updating the weights
						for (int j=0 ; j<weights.length ; j++){
							weights[j] += (LEARNING_RATE*error*features[i][j]);
						}
					}
				}
			} //end of epoch

			int sumRecords = (amountFirstClass+amountSecondClass);
			accuracy = (((double)correctCounter)/((double)sumRecords));
			//System.out.print("Correct: "+correctCounter+"/"+sumRecords);
			//System.out.print(" --> Accuracy: "+accuracy+"\n");
			if (bestAccuracy == accuracy)
				flag++;

		} while (flag != 2); //stops after the accuracy has stopped improving twice
		System.out.println("* Number of training epochs: "+iter);
		/*
		//test print weights
		System.out.println("Weights:");
		for (int i=0 ; i<weights.length ; i++)
			System.out.print(weights[i]+" ");
		 */

	}

	//testing the outputs using the reached weights
	public static void test(int startFromFirst, int startFromSecond, String firstClassLetter, String secondClassLetter){
		int output;
		int firstCounter = 0;
		int secondCounter = 0;
		int correctCounter = 0;
		int correctCounterFirst = 0, correctCounterSecond = 0;
		int totalFirst = 0, totalSecond = 0;
		double accuracy;

		//System.out.println("\nTEST:\n======");

		for (int i=0 ; i<TOTAL_RECORDS ; i++){
			if (classes[i].equals(firstClassLetter)){
				firstCounter++;
				if (firstCounter > startFromFirst){
					totalFirst++;
					output = calcOutput(features[i], weights);
					if (output == 1)
						correctCounterFirst++;
				}
			}
			if (classes[i].equals(secondClassLetter)){
				secondCounter++;
				if (secondCounter > startFromSecond){
					totalSecond++;
					output = calcOutput(features[i], weights);
					if (output == -1)
						correctCounterSecond++;
				}
			}
		}

		int sumRecords = (totalFirst+totalSecond);
		correctCounter = (correctCounterFirst+correctCounterSecond);
		accuracy = (((double)correctCounter)/((double)sumRecords));
		System.out.print("Total Accuracy Rate: "+accuracy);
		DecimalFormat df = new DecimalFormat("#.##");
		double percentage = accuracy*100;
		System.out.print("\t ~"+(df.format(percentage))+"%\n");
		System.out.println("'A' Correct: "+correctCounterFirst+"/"+totalFirst+" | Incorrect: "+(totalFirst-correctCounterFirst));
		System.out.println("'"+secondClassLetter+"' Correct: "+correctCounterSecond+"/"+totalSecond+" | Incorrect: "+(totalSecond-correctCounterSecond));
	}

	//returns random number between the given range (min,max)
	public static double randomNum(int min , int max) {
		DecimalFormat df = new DecimalFormat("#.####");
		double d = min + Math.random() * (max - min);
		String s = df.format(d);
		double x = Double.parseDouble(s);
		return x;
	}

	//returns either 1 or -1 using a threshold function
	public static int calcOutput(Double[] features, Double[] weights){
		double sum = 0;
		for (int i=0 ; i<ATTRIBUTES ; i++){
			sum += features[i]*weights[i];
		}
		return (sum >= theta) ? 1 : -1;
	}

} //END OF CLASS