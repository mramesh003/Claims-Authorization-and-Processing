package com.acc.supervised;

import java.io.File;

import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class ClassifyPrediction{
	public static void main(String args[]) throws Exception{
		File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single.arff");
		//File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\weather.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load training dataset
		DataSource trainSource = new DataSource(trainArff);
		//get instances object
		Instances trainDataset = trainSource.getDataSet();
		//set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		//get number of classes
		int numClasses = trainDataset.numClasses();
		System.out.println(numClasses);
		//print out class values in the training dataset
		for(int i=0; i<numClasses; i++){
			//get class string value using the class index
			String classValue = trainDataset.classAttribute().value(i);
			System.out.println("Class value "+i+" is "+ classValue);
		}
		
		//create and build the classifier
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(trainDataset);
		
		File testArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single_test.arff");
		String testArff = testArffFile.getAbsolutePath();
		
		//load new dataset
		DataSource testSource = new DataSource(testArff);
		//get instances object
		Instances testDataset = testSource.getDataSet();
		//set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		
		//loop through the new dataset and make predictions
		System.out.println("=================================");
		System.out.println("Actual Class , NB Predicted Class");
		//System.out.println("NB Predicted Class - Output");
		for(int i=0; i<testDataset.numInstances(); i++){
			//get class double value for current instance
			double actualClass = testDataset.instance(i).classValue();
			//get class string value using the class index using the class's int value
			String actual = testDataset.classAttribute().value((int)actualClass);
			//get instance object of current instance
			Instance newInst = testDataset.instance(i);
			//call classifyInstance, which returns a double value for the class
			double predNB = nb.classifyInstance(newInst);
			//use this value to get string value of the predicted class
			String predString = testDataset.classAttribute().value((int)predNB);
			System.out.println(actual+"("+(int)actualClass+") , " +  predString+"("+(int)predNB+")");
			//System.out.println(predString);
		}
	}
}