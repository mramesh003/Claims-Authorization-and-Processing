package com.acc.supervised.model;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.filters.unsupervised.attribute.Remove;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;

import java.io.File;

/**
 * This example trains NaiveBayes incrementally on data obtained
 * from the ArffLoader.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */

public class Arff2Model {
	 /**
	   * Expects an ARFF file as first argument (class attribute is assumed
	   * to be the last attribute).
	   *
	   * @param args        the commandline arguments
	   * @throws Exception  if something goes wrong
	   */
	  public static void main(String[] args) throws Exception {
		  
		File inputArffFile = new File("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTrain.arff");
		// load data
	    ArffLoader loader = new ArffLoader();
	    loader.setFile(new File(inputArffFile.getAbsolutePath()));
	    //Instances structure = loader.getStructure();
	    Instances structure = loader.getDataSet();
	    structure.setClassIndex(structure.numAttributes() - 1);

	       
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
	    weka.core.SerializationHelper.write("C:\\Users\\ilakkia.shanmugam\\Desktop\\MockedDataTrain.model", fc);
	    
	    
	    
	    /*for (int i = 0; i < test.numInstances(); i++) {
	      double pred = fc.classifyInstance(test.instance(i));
	      System.out.print("ID: " + test.instance(i).value(0));
	      System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
	      System.out.println(", predicted: " + test.classAttribute().value((int) pred));
	    }*/
	    
	    
	    
	    /*Instance current;
	    while ((current = loader.getNextInstance(structure)) != null)
	      nb.updateClassifier(current);*/

	    // output generated model
	    System.out.println(j48);
	    
	    
	  }
}
