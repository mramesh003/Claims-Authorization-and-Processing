package com.acc.supervised;

import java.io.File;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Classification{
	public static void main(String args[]) throws Exception{
		//File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single.arff");
		File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\weather.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load training dataset
		DataSource trainSource = new DataSource(trainArff);
		
		//get instances objectMocked_Data_Updated_single
		Instances dataset = trainSource.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//create and build the classifier
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(dataset);
		//print out capabilities
		System.out.println(nb.getCapabilities().toString());
		
		SMO svm = new SMO();
		svm.buildClassifier(dataset);
		//print out capabilities
		System.out.println(svm.getCapabilities().toString());
		
		//String[] options = new String[4];
		//options[0] = "-U";
		//options[0] = "-C"; options[1] = "0.11";
		//options[2] = "-M"; options[3] = "3";
		J48 tree = new J48();
		tree.buildClassifier(dataset);
		//print out capabilities
		System.out.println(tree.getCapabilities().toString());
		System.out.println(tree.graph());
	}
}