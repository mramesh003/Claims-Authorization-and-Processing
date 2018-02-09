package com.acc.unsupervised;

import weka.clusterers.ClusterEvaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Clustering{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource trainingSource = new DataSource("");
		//get instances object
		Instances trainingDataset = trainingSource.getDataSet();
		//new instance of clusterer
		SimpleKMeans model = new SimpleKMeans();
		//number of clusters
		model.setNumClusters(4);
		//set distance function
		//model.setDistanceFunction(new weka.core.ManhattanDistance());
		//build the clusterer
		model.buildClusterer(trainingDataset);
		System.out.println(model);
		
		//to cluster an instance .. return cluster number as int
		//model.clusterInstance(instance);
		
		//return an array containing the estimated membership probabilities of the test instance in each cluster
		//model.distributionForInstance(instance);
		
		/*We can evaluate a clusterer with the ClusterEvaluation class
		For instance, separate train and test datasets can be used
		we can print out the number of clusters found
		*/
		ClusterEvaluation clsEval = new ClusterEvaluation();
		DataSource testSource = new DataSource("");
		//get instances object
		Instances testDataset = testSource.getDataSet();
		
		clsEval.setClusterer(model);
		clsEval.evaluateClusterer(testDataset);//this should be a test dataset
		
		System.out.println("# of clusters: " + clsEval.getNumClusters());
		
		
		
		
		
		
	}
}