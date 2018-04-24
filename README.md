# Perceptrons_MachineLearning
Exercise in machine learning using Perceptron, an algorithm for supervised learning of binary classifiers (functions that can decide whether an input, represented by a vector of numbers, belongs to some specific class or not). It is a type of linear classifier, i.e. a classification algorithm that makes its predictions based on a linear predictor function combining a set of weights with the feature vector. 

This algorithm recognizes letters by a vector of numbers.

This program is an implementation of Perceptron learning. we will use it to train Perceptrons to do a letter recognition task.
The data for this task is the Letter Recognition data set from the UCI machine-learning repository: http://archive.ics.uci.edu/ml/datasets/Letter+Recognition <br>
There are 16 attributes used to describe each input figure, as described on the Webpage above.

<b>Task: </b>This perceptron model will distinguish ‘A’ from some other letter. For example, suppose that we want to learn a model that classifies an input figure into ‘A’ or ‘B’.

<b>Data:</b> The table on the Website includes examples for all letters. We will use the first half of the data for training and the
latter half for testing. For example, there are 789 As and 766 Bs in the data. So we will use the first 394 As and 383 Bs records for training, and the last 395 As and 383 Bs records for testing.

<b>Training: </b>We train a Perceptron with 17 inputs (16 for the attributes used in the letter-recognition
data plus one bias input, set to be always +1). Each input is associated a weight (so, overall there are 17
weights), and a single output unit.
We iterate over the training examples, one by one. For each example, compute the weighted sum of
inputs; if the total input is equal or greater than zero, output +1 (for ‘A’), otherwise output −1 (for ‘B’, or
any other alternative letter). Similarly, the true label is +1 for examples of ‘A’ and -1 if the label is a ‘B’.
<em>If an error occurs, the weights should be updated:
wi = wi + η(t − y) × xi </em>
where t is the true values (+/-1), y is the output label (+/-1), and xi is the value of attribute i.
If there is no error, we skip over to the next example with no update.

<b>Initial weights: </b>Our perceptrons will start off with small random weights, sampled from the range
(-1 < w < 1).

<b>Termination: </b>A complete pass over all of the training examples is called an epoch. After completing each
epoch, we run the model with the current weights on all of the training examples, and report the accuracy rate
(the rate of examples, for which the output classification is correct). We stop training once the accuracy on the
training data has stopped improving.
