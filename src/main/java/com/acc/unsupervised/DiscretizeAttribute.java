package com.acc.unsupervised;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Discretize;

/* Sample File*/ 

public class DiscretizeAttribute{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		Instances dataset = source.getDataSet();
		
		//set options
		String[] options = new String[5];
		
		//choose the number of intervals, e.g. 2:
		options[0] = "-B"; options[1] = "4";
		
		//choose the range of attributes on which to apply the filter
		options[2] = "-R"; options[3] = "first-last";
		//options[3] = "first";
		//options[3] = "2";
		//options[3] = "1-3";
		//options[4] = "-V";
		
		//apply discretization
		Discretize discretize = new Discretize();
		discretize.setOptions(options);
		discretize.setInputFormat(dataset);
		Instances newData = Filter.useFilter(dataset, discretize);
		
		//Save ARFF
		ArffSaver saver = new ArffSaver();
		
		//Set the dataset we want to convert
		saver.setInstances(newData);
		
		//Save as ARFF
		saver.setFile(new File(""));
		saver.writeBatch();
	}
	
}