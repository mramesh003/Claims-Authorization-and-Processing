package com.acc.supervised.model;

import java.io.File;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ModelEvaluate{
	public static void main(String args[]) throws Exception{
		File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single.arff");
		//File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\weather.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load dataset
		DataSource source = new DataSource(trainArff);
		
		//get instances object
		Instances dataset = source.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//create and build the classifier
		J48 tree = new J48();
		tree.buildClassifier(dataset);
		
		Evaluation eval = new Evaluation(dataset);
		//Random rand = new Random(1);
		//int folds = 10;
		
		//Notice we build the classifier with the training set 
		//we initialize evaluation with the train dataset and then
		//evaluate using the test dataset
		File testArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single_test.arff");
		String testArff = testArffFile.getAbsolutePath();
		//test dataset for evaluation
		DataSource testSource = new DataSource(testArff);
		Instances testDataset = testSource.getDataSet();
		//set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		//now evaluate the model
		eval.evaluateModel(tree, testDataset);
		//eval.crossValidateModel(tree, testDataset, folds, rand);
		System.out.println(eval.toSummaryString("Evaluation results:\n", false));
		
		System.out.println("Correct % = "+eval.pctCorrect());
		System.out.println("Incorrect % = "+eval.pctIncorrect());
		System.out.println("AUC % = "+eval.areaUnderROC(1));
		System.out.println("kappa % = "+eval.kappa());
		System.out.println("MAE % = "+eval.meanAbsoluteError());
		System.out.println("RMSE % = "+eval.rootMeanSquaredError());
		System.out.println("RAE % = "+eval.relativeAbsoluteError());
		System.out.println("RRSE % = "+eval.rootRelativeSquaredError());
		System.out.println("Precision % = "+eval.precision(1));
		System.out.println("Recall % = "+eval.recall(1));
		System.out.println("fMeasure % = "+eval.fMeasure(1));
		System.out.println("Error Rate % = "+eval.errorRate());
		
		//the confusion matrix
		System.out.println(eval.toMatrixString("=== Overall Confusion Matrix ===\n"));
	}
}