package com.acc.supervised;

import weka.associations.Apriori;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class AssocRules{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		//get instances object
		Instances dataset = source.getDataSet();
		//the Apriori algorithm
		Apriori model = new Apriori();
		//build model
		model.buildAssociations(dataset);
		//print out the extracted rules
		System.out.println(model);
	}
}