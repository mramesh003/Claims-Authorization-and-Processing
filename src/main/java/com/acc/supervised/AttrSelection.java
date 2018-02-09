package com.acc.supervised;

import java.io.File;

import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

public class AttrSelection{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		Instances dataset = source.getDataSet();
		
		//create AttributeSelection object
		AttributeSelection filter = new AttributeSelection();
		
		/*Evaluates the worth of a subset of attributes by considering the individual predictive 
		  ability of each feature along with the degree of redundancy between them.*/
		//create evaluator algorithm object
		CfsSubsetEval eval = new CfsSubsetEval();
		

        /* Performs a greedy forward or backward search through the space of attribute subsets. 
		May start with no/all attributes or from an arbitrary point in the space. 
		Stops when the addition/deletion of any remaining attributes results in a decrease in evaluation. 
		Can also produce a ranked list of attributes by traversing the space from one side to the other and 
		recording the order that attributes are selected.*/
		//create search algorithm object
		GreedyStepwise search = new GreedyStepwise();
		
		//set the algorithm to search backward
		search.setSearchBackwards(true);
		
		//set the filter to use the evaluator and search algorithm
		filter.setEvaluator(eval);
		filter.setSearch(search);
		
		//specify the dataset
		filter.setInputFormat(dataset);
		
		//apply
		Instances newData = Filter.useFilter(dataset, filter);
		
		//Save ARFF
		ArffSaver saver = new ArffSaver();
		
		//Set the dataset we want to convert
		saver.setInstances(newData);
		
		//Save as ARFF
		saver.setFile(new File(""));
		saver.writeBatch();
	}
	
}