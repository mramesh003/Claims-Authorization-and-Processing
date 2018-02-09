package com.acc.supervised;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMOreg;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class LoadModel{
	
	private static final Logger logger = LoggerFactory.getLogger(LoadModel.class);
	
	public static void main(String args[]) throws Exception{
		
		//load the saved model
		NaiveBayes nbLoad = (NaiveBayes)weka.core.SerializationHelper.read("my_nb_model.model");
		
		File testArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single_test.arff");
		String testArff = testArffFile.getAbsolutePath();
		
		//load new test dataset
		DataSource testSource = new DataSource(testArff);
		//get instances object
		Instances testDataset = testSource.getDataSet();
		//set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		
		//get the first trained data and compare with the new dataset and make prediction
		System.out.println("=================================");
		System.out.println("Actual Class , NB Predicted Class");
		
		//get class double value for current instance
		double actualValue = testDataset.instance(0).classValue();
		//get instance object of first instance
		Instance newInst = testDataset.instance(0);
		//call classifyInstance, which returns a double value for the class
		double predSMO = nbLoad.classifyInstance(newInst);
		
		logger.info("sddddddddddddddddd");
		
		System.out.println(actualValue + " , " +  predSMO);
		
	}
}