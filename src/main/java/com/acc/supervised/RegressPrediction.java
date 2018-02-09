package com.acc.supervised;

import java.io.File;

import weka.classifiers.functions.SMOreg;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class RegressPrediction{
	public static void main(String args[]) throws Exception{
		File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load training dataset
		DataSource trainSource = new DataSource(trainArff);
		//get instances object
		Instances trainDataset = trainSource.getDataSet();
		//set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		
		//create and build the classifier
		SMOreg smo = new SMOreg();
		smo.buildClassifier(trainDataset);
		//output the model
		System.out.println(smo);
		
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
		System.out.println("Actual Class , SMO Predicted Class");
		for(int i=0; i<testDataset.numInstances(); i++){
			//get class double value for current instance
			double actualValue = testDataset.instance(i).classValue();
			
			//get instance object of current instance
			Instance newInst = testDataset.instance(i);
			
			//call classifyInstance, which returns a double value for the class
			double predSMO = smo.classifyInstance(newInst);
			
			System.out.println(actualValue + " , " +  predSMO);
		}
	}
}