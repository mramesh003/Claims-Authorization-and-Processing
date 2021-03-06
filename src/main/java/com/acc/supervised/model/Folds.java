package com.acc.supervised.model;

import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Folds{
	public static void main(String args[]) throws Exception{
		//load dataset
		DataSource source = new DataSource("");
		
		//get instances object
		Instances dataset = source.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//create the classifier
		NaiveBayes nb = new NaiveBayes();
				
		int seed = 1;
		int folds = 15;
		
		//randomize data
		Random rand = new Random(seed);
		
		//create random dataset
		Instances randData = new Instances(dataset);
		randData.randomize(rand);
		
		//stratify
		if(randData.classAttribute().isNominal())
			randData.stratify(folds);
		
		//perform cross-validation
		for(int n=0; n<folds; n++){
			Evaluation eval = new Evaluation(randData);
		
			//get the folds
			Instances train = randData.trainCV(folds, n);
			Instances test = randData.trainCV(folds, n);
			
			//build and evaluate classifier
			nb.buildClassifier(train);
			eval.evaluateModel(nb, test);
		
			//output for evaluation
			System.out.println();
			System.out.println(eval.toMatrixString("=== Confusion Matrix for fold " + (n+1) + "\n" +  folds + " === "));
			
			System.out.println("Correct % = "+eval.pctCorrect());
			System.out.println("Incorrect % = "+eval.pctIncorrect());
			System.out.println("AUC % = "+eval.areaUnderROC(1));
			System.out.println("kappa % = "+eval.kappa());
			System.out.println("MAE % = "+eval.meanAbsoluteError());
			System.out.println("RMSE % = "+eval.rootMeanSquaredError());
			System.out.println("RAE % = "+eval.relativeAbsoluteError());
			System.out.println("RRSE % = "+eval.rootRelativeSquaredError());
			System.out.println("Precision % = "+eval.precision(1));
			System.out.println("Recall % = "+eval.recall(1));
			System.out.println("fMeasure % = "+eval.fMeasure(1));
			System.out.println("Error Rate % = "+eval.errorRate());
			
			//the confusion matrix
			//System.out.println(eval.toMatrixString("=== Overall Confusion Matrix ===\n"));
		}
	}
}