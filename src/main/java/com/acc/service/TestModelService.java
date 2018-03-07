package com.acc.service;

import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;

public interface TestModelService {
    public  List<String> testModel(ModelFile model, ArffFile arffFile) throws Exception;

}
