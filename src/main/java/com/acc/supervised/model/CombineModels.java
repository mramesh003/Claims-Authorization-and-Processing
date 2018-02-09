package com.acc.supervised.model;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class CombineModels{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource trainingSource = new DataSource("");
		//get instances object
		Instances trainingDataset = trainingSource.getDataSet();
		//set class index .. as the last attribute
		if(trainingDataset.classIndex() == -1){
			trainingDataset.setClassIndex(trainingDataset.numAttributes() - 1);
		}
		
		/*Boosting a week classifier using the Adaboost M1 method
		for boosting a nominal class classifier
		Tackels only nominal class problems
		Improves performance
		Sometimes overfits*/
		//AdaBoost ..
		AdaBoostM1 m1 = new AdaBoostM1();
		//needs one base-classifier
		//m1.setClassifier(new NaiveBayes());
		m1.setClassifier(new DecisionStump());
		m1.setNumIterations(20);
		m1.buildClassifier(trainingDataset);
		
		/*Bagging a classifier to reduce variance.
		Can do classification and regression (depending on the base model)
		*/
		//Bagging ..
		Bagging bagger = new Bagging();
		//needs one base-classifier
		bagger.setClassifier(new RandomTree());
		bagger.setNumIterations(20);
		bagger.buildClassifier(trainingDataset);
		
		/*The Stacking method combines several models
		Can do classification or regression 
		*/
		//Stacking ..
		Stacking stacker = new Stacking();
		//needs one base-classifier
		stacker.setMetaClassifier(new J48());
		Classifier[] classifiers = {
				new J48(),
				new NaiveBayes(),
				new RandomForest()
		};
		stacker.setClassifiers(classifiers);
		stacker.buildClassifier(trainingDataset);
		
		/*Class for combining classifiers.
		Different combinations of probability estimates for classification are available
		*/
		//Vote ..
		Vote voter = new Vote();
		voter.setClassifiers(classifiers);
		voter.buildClassifier(trainingDataset);
		
		
		
		
	}
}