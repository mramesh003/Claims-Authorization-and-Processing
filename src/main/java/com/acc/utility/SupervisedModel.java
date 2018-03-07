package com.acc.utility;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

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

}
