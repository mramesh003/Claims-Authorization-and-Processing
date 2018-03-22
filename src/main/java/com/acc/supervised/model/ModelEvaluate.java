package com.acc.supervised.model;

import java.io.File;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class ModelEvaluate{
	public static void main(String args[]) throws Exception{/*
		File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\Mocked_Data_Updated_single.arff");
		//File trainArffFile = new File("C:\\AISamplefiles\\SampleData\\weather.arff");
		String trainArff = trainArffFile.getAbsolutePath();
		System.out.println(trainArff);
		//load dataset
		DataSource source = new DataSource(trainArff);
		
		//get instances object
		Instances dataset = source.getDataSet();
		
		//set class index to the last attribute
		dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//create and build the classifier
		J48 tree = new J48();
		tree.buildClassifier(dataset);
		
		File inputArffFile = new File("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTrain.arff");
	    
		// load data
	    ArffLoader loader = new ArffLoader();
	    loader.setFile(new File(inputArffFile.getAbsolutePath()));
	    //Instances structure = loader.getStructure();
	    Instances structure = loader.getDataSet();
	    structure.setClassIndex(structure.numAttributes() - 1);

	    FilteredClassifier cls = (FilteredClassifier) weka.core.SerializationHelper.read("C:\\AISamplefiles\\03012018\\MockedDataTrain.model");
		
	       
	    Remove rm = new Remove();
	    rm.setAttributeIndices("1");  // remove 1st attribute
	    // classifier
	    //Classifier j48 = new J48();
	    Classifier j48 = new J48();
	    //j48.setUnpruned(true);        // using an unpruned J48
	    // meta-classifier
	    FilteredClassifier fc = new FilteredClassifier();
	    fc.setFilter(rm);
	    fc.setClassifier(j48);
	    // train and make predictions
	    fc.buildClassifier(structure);
		
		Evaluation eval = new Evaluation(structure);
		//Random rand = new Random(1);
		//int folds = 10;
		
		//Notice we build the classifier with the training set 
		//we initialize evaluation with the train dataset and then
		//evaluate using the test dataset
		File testArffFile = new File("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTrain.arff");
		String testArff = testArffFile.getAbsolutePath();
		//test dataset for evaluation
		DataSource testSource = new DataSource(testArff);
		Instances testDataset = testSource.getDataSet();
		//set class index to the last attribute
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		//now evaluate the model
		eval.evaluateModel(cls, testDataset);
		//eval.crossValidateModel(tree, testDataset, folds, rand);
		System.out.println(eval.toSummaryString("Evaluation results:\n", false));
		
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
		System.out.println(eval.toMatrixString("\n=== Overall Confusion Matrix ===\n"));
	*/
		FilteredClassifier cls = (FilteredClassifier) weka.core.SerializationHelper.read("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTrain.model");
        File testArffFile = new File("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTest.arff");
        String testArff = testArffFile.getAbsolutePath();
        DataSource testSource = new DataSource(testArff);
        Instances testDataset = testSource.getDataSet();
        testDataset.setClassIndex(testDataset.numAttributes() - 1);
        Evaluation eval = new Evaluation(testDataset);
        eval.evaluateModel(cls, testDataset);
        System.out.println(eval.toSummaryString("Evaluation results:\n", false));
        System.out.println("Error Rate % = "+eval.errorRate());
        System.out.println(eval.toMatrixString("=== Overall Confusion Matrix ===\n"));
	
	}
}