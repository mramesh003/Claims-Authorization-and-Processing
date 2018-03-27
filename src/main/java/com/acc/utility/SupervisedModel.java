package com.acc.utility;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
			results.add(predString);
		}
		return results;
	}

	public static Map<String,Object> evaluateModel(ModelFile trainModel, ArffFile testArffFile) throws Exception {
		FileOutputStream outputstream = null;
		String filePath = System.getProperty("java.io.tmpdir") + File.separator + "ModelFile.model";		
		outputstream = new FileOutputStream(filePath);
		outputstream.write(trainModel.getFileContent());
		outputstream.close();
	    FilteredClassifier cls = (FilteredClassifier)weka.core.SerializationHelper.read(filePath);
		DataSource testSource = new DataSource(new ByteArrayInputStream(testArffFile.getFileContent()));
		Instances testDataset = testSource.getDataSet();
		testDataset.setClassIndex(testDataset.numAttributes() - 1);
		Evaluation eval = new Evaluation(testDataset);
		eval.evaluateModel(cls, testDataset);
		Map<String,Object> evaluationResult = new HashMap<String, Object>();
		evaluationResult.put("Evaluation results", eval.toSummaryString("Evaluation results:\n", false));
		evaluationResult.put("Confusion Matrix", eval.toMatrixString("=== Overall Confusion Matrix ===\n"));
		return evaluationResult;

	}

}
