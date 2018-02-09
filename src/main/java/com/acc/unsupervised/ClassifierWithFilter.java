package com.acc.unsupervised;

import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

/* Sample File*/ 

public class ClassifierWithFilter{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		Instances dataset = source.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//the base classifier
		J48 tree = new J48();
		
		//use a simple filter to remove a certain attribute
		//set up options to remove 1st attribute
		String[] options = new String[]{ "-R", "1" };
		
		//create a Remove object (this is the filter class)
		Remove remove = new Remove();
		
		//set the filter option
		remove.setOptions(options);
		
		//pass the dataset to the filter
		//remove.setInputFormat(dataset);
		
		//Create the FilteredClassifier object
		FilteredClassifier fc = new FilteredClassifier();
		
		//specify the filter
		fc.setFilter(remove);
		
		//specify the base classifier
		fc.setClassifier(tree);
		
		//buid the meta-classifier
		fc.buildClassifier(dataset);
		
		System.out.println(tree.graph());
	}
	
}