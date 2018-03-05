package com.acc.supervised.model;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ConverterUtils.DataSource;

public class TestModel{
	
	public static void main(String args[]) throws Exception{
		
		
		//FilteredClassifier clf = (FilteredClassifier) SerializationHelper.read("C:\\AISamplefiles\\02232018\\MockedDataTrain.model");
		
		Classifier cls = (Classifier) weka.core.SerializationHelper.read("C:\\AISamplefiles\\03012018\\MockedDataTrain.model");
		
		
		File testArffFile = new File("C:\\AISamplefiles\\03012018\\MockedDataTest.arff");
		String testArff = testArffFile.getAbsolutePath();
		
		//load new test dataset
		DataSource testSource = new DataSource(testArff);
		//get instances object
		Instances testDataset = testSource.getDataSet();
		//set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		//testDataset.setClassIndex(1);
		
		//get the first trained data and compare with the new dataset and make prediction
		System.out.println("=================================");
		System.out.println("Actual Class , Predicted Class");
		
		
		for(int i=0; i<testDataset.numInstances(); i++){
			//get class double value for current instance
			double actualClass = testDataset.instance(i).classValue();
			//get class string value using the class index using the class's int value
			String actual = testDataset.classAttribute().value((int)actualClass);
			//get instance object of current instance
			Instance newInst = testDataset.instance(i);
			//call classifyInstance, which returns a double value for the class
			double predNB = cls.classifyInstance(newInst);
			//use this value to get string value of the predicted class
			String predString = testDataset.classAttribute().value((int)predNB);
			//System.out.println(actual+"("+(int)actualClass+") , " +  predString+"("+(int)predNB+")");
			System.out.println(actual+ " , " +  predString);
			//System.out.println(predString);
		}
		
		/*//get class double value for current instance
		double actualValue = testDataset.instance(0).classValue();
		//get instance object of first instance
		Instance newInst = testDataset.instance(0);
		//call classifyInstance, which returns a double value for the class
		//double predSMO = nbLoad.classifyInstance(newInst);
		double predSMO = clf.classifyInstance(newInst);
		
		
		
		System.out.println(actualValue + " , " +  predSMO);*/
		
	}
}