package com.acc.utility;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.unsupervised.attribute.Remove;

public class SupervisedModel {

	public static List<String> testModel(ModelFile model, ArffFile arffFile) throws Exception {

		List<String> results = new ArrayList<String>();
		Classifier cls = (Classifier) weka.core.SerializationHelper
				.read(new ByteArrayInputStream(model.getFileContent()));
		DataSource testSource = new DataSource(new ByteArrayInputStream(arffFile.getFileContent()));
		Instances testDataset = testSource.getDataSet();
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		for (Integer i = 0; i < testDataset.numInstances(); i++) {
			double actualClass = testDataset.instance(i).classValue();
			String actual = testDataset.classAttribute().value((int) actualClass);
			Instance newInst = testDataset.instance(i);
			double predNB = cls.classifyInstance(newInst);
			String predString = testDataset.classAttribute().value((int) predNB);
			results.add(actual + "," + predString);
		}
		return results;
	}

	public static Map<String,Object> evaluateModel(ArffFile trainArffFile, ArffFile testArffFile) throws Exception {
		ArffLoader loader = new ArffLoader();
		loader.setSource(new ByteArrayInputStream(trainArffFile.getFileContent()));
		Instances structure = loader.getDataSet();
		structure.setClassIndex(structure.numAttributes() - 1);
		Remove rm = new Remove();
		rm.setAttributeIndices("1");
		Classifier j48 = new J48();
	    FilteredClassifier fc = new FilteredClassifier();
	    fc.setFilter(rm);
	    fc.setClassifier(j48);
	    fc.buildClassifier(structure);
		Evaluation eval = new Evaluation(structure);
		DataSource testSource = new DataSource(new ByteArrayInputStream(testArffFile.getFileContent()));
		Instances testDataset = testSource.getDataSet();
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		eval.evaluateModel(j48, testDataset);
		Map<String,Object> evaluationResult = new HashMap<String, Object>();
		evaluationResult.put("AUC", eval.areaUnderROC(1));
		evaluationResult.put("kappa",eval.kappa() );
		evaluationResult.put("MAE", eval.meanAbsoluteError());
		evaluationResult.put("RMSE",eval.meanAbsoluteError() );
		evaluationResult.put("RAE", eval.relativeAbsoluteError());
		evaluationResult.put("RRSE", eval.rootRelativeSquaredError());
		evaluationResult.put("Precision",eval.precision(1) );
		evaluationResult.put("Recall", eval.recall(1));
		evaluationResult.put("fMeasure", eval.fMeasure(1));
		evaluationResult.put("Error Rate", eval.errorRate());
		evaluationResult.put("Evaluation results", eval.toSummaryString("Evaluation results:\n", false));
		evaluationResult.put("Confusion Matrix", eval.toMatrixString("=== Overall Confusion Matrix ===\n"));
		return evaluationResult;
		
	}

}
