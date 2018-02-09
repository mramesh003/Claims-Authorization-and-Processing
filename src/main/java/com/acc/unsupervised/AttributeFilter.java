package com.acc.unsupervised;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

/* Sample File*/ 

public class AttributeFilter{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		Instances dataset = source.getDataSet();
		
		//use a simple filter to remove a certain attribute
		//set up options to remove 1st attribute
		String[] options = new String[]{ "-R", "1" };
		
		//create a Remove object (this is the filter class)
		Remove remove = new Remove();
		
		//set the filter option
		remove.setOptions(options);
		
		//pass the dataset to the filter
		remove.setInputFormat(dataset);
		
		//apply the filter
		Instances newData = Filter.useFilter(dataset, remove);
		
		//now save the dataset to a new file as we learned before
		ArffSaver saver = new ArffSaver();
		saver.setInstances(newData);
		saver.setFile(new File(""));
		saver.writeBatch();
	}
	
}