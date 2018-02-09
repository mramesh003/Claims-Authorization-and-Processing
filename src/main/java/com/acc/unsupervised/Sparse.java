package com.acc.unsupervised;

import java.io.File;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.NonSparseToSparse;

/* Sample File*/ 

public class Sparse{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		Instances dataset = source.getDataSet();
		
		//create NonSparseToSparse object to save in sparse ARFF format
		NonSparseToSparse sp = new NonSparseToSparse();
		
		//specify the dataset
		sp.setInputFormat(dataset);
		
		//apply
		Instances newData = Filter.useFilter(dataset, sp);
		
		//Save ARFF
		ArffSaver saver = new ArffSaver();
		
		//Set the dataset we want to convert
		saver.setInstances(newData);
		
		//Save as ARFF
		saver.setFile(new File(""));
		saver.writeBatch();
	}
	
}