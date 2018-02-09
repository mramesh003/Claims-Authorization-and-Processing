package com.acc.supervised;

import weka.core.AttributeStats;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.experiment.Stats;

public class AttrInstances{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		
		//get instances object
		Instances dataset = source.getDataSet();
		
		//set class index .. as the last attribute
		if(dataset.classIndex() == -1){
			dataset.setClassIndex(dataset.numAttributes() - 1);
		}
		
		//get number of attributes (notic class is not counted)
		int numAttr = dataset.numAttributes() - 1;
		for(int i=0; i<numAttr; i++){
			//check if current attribute is of type nominal
			if(dataset.attribute(i).isNominal()){
				System.out.println("The "+i+"th Attribute is Nominal");
				//get number of values
				int n = dataset.attribute(i).numValues();
				System.out.println("The "+i+"th Attribute has :"+n+" values");
			}
			
			//get an Attribute object
			AttributeStats as = dataset.attributeStats(i);
			int dc = as.distinctCount;
			System.out.println("The "+i+"th Attribute has :"+dc+" distinct values");
			
			//get a Stats object from AttributeStats
			if(dataset.attribute(i).isNumeric()){
				System.out.println("The "+i+"th Attribute is Numeric");
				Stats s = as.numericStats;
				System.out.println("The "+i+"th Attribute has min value :"+s.min+" and max value :"+s.max);
			}
		}
		
		//get number of instances
		int numInst = dataset.numInstances();
		
		//loop through all instances
		for(int j=0; j<numInst; j++){
			//get the j'th instance
			Instance instance = dataset.instance(j);
			//check if 1st attribute is missing from the j'th instance
			if(instance.isMissing(0)){
				System.out.println("The "+0+"th Attribute is missing");
			}
			
			//check if the class is missing from the j'th instance
			if(instance.classIsMissing()){
				System.out.println("The class is missing in the "+j+"th Instance");
			}
			
			//if you want to access the value of Class in your dataset
			//notice classes of type nominal and string are given ID's
			double cv = instance.classValue();
			System.out.println(instance.classAttribute().value((int)cv));
		}
	}
}