package com.acc.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;
import com.acc.utility.SupervisedModel;

@Service
public class TestModelServiceImpl implements TestModelService {

	public List<String> testModel(ModelFile model, ArffFile arffFile) throws Exception {
		
		return SupervisedModel.testModel(model, arffFile);
	}

}
