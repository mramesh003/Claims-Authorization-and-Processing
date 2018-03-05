package com.acc.supervised;

import java.io.File;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMOreg;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SaveModel{
	public static void main(String args[]) throws Exception{
		File trainArffFile = new File("C:\\Users\\ilakkia.shanmugam\\Desktop\\samparff.arff");
		//File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\weather.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load training dataset
		DataSource trainSource = new DataSource(trainArff);
		//get instances object
		Instances trainDataset = trainSource.getDataSet();
		//set class index to the last attribute
		trainDataset.setClassIndex(trainDataset.numAttributes() - 1);
		
		/*//create and build the classifier
		SMOreg smo = new SMOreg();
		smo.buildClassifier(trainDataset);
		//output the model
		System.out.println(smo);
		//save model
		weka.core.SerializationHelper.write("my_smo_model.model", smo);
		
		//load the above saved model
		SMOreg smoLoad = (SMOreg)weka.core.SerializationHelper.read("my_smo_model.model");
		*/
		
		//create and build the classifier
		NaiveBayes nb = new NaiveBayes();
		nb.buildClassifier(trainDataset);
		//print out capabilities
		System.out.println(nb.getCapabilities().toString());
		
		//save model
		weka.core.SerializationHelper.write("my_nb_model.model", nb);
		
	}
}