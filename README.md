# PerceptronLanguage

!!!TASK!!!

Input data (create them yourself)
1. Create a training data folder.
2. For each language (at least three) create a folder with the language name (e.g. Czech, Hungarian, Spanish etc.)
3. In each folder create at least ten text files with text from that language. Consider only those based on the Latin alphabet. Minimal length – at least two paragraphs.
The program must not assume how many languages there are when it starts.
4. Create a similar testing folder.
The algorithm:
A single-layer perceptron network will be used.
For each input text file, remove all non-English characters. For each English letter (A-Z), count their relative frequency of occurrence in the file.
We train perceptrons to detect the languages. Each single perceptron will detect one language.
There are two variants of training/testing:
Like in the previous project, we use binary activation functions, and during testing the class with output 1 will be chosen.
Training answers are coded -1/1 instead of 0/1. We normalized input and weight vectors. As an activation function we use y=net. Normalize the weight vectors after each iteration. The training phase is finished once the error |d-y| is below a chosen threshold (e.g. 0,01). During testing the class with highest output is selected (maximum selector).
Evaluate your classifier’s performance (print out the confusion matrix, accuracy, precision, recall, F-measure) for each language on the test data (or one matrix for 3 of them).
Provide an edit box for the user to enter/paste a text of his/her choice for your program to classify.
You must not use any third party Maching Learning library or tools , all the elements of the algorithm must be implemented from scratch. java.lang.Math and java.util. are permitted.



!!!Proj Solution Descript!!!

-Input is done in input.txt File // Put data in the file and write "start" in console // To stop the programm write "stop"
-Input data could not be too little, the accuracy will fall down
