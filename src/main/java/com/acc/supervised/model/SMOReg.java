package com.acc.supervised.model;

import weka.classifiers.functions.SMOreg;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class SMOReg{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		
		//get instances object
		Instances dataset = source.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//build model
		SMOreg smo = new SMOreg();
		smo.buildClassifier(dataset);
		
		//output the model
		System.out.println(smo);
	}
}