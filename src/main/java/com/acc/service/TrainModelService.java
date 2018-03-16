package com.acc.service;

import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;

public interface TrainModelService {
	public void saveArffFile(ArffFile arffFile);
	
	public List<ArffFile> listAllArffs();

	public ArffFile getArffFileById(Integer fileId);
	
	public void deleteArff(ArffFile arffFile);
	
	public boolean convertToArffFilebyCsvId(Integer csvId);
	
	public void saveModel(ModelFile modelFile);
	
	public ModelFile getModelById(Integer fileId);
    
    public List<ModelFile> listAllModels();
    
    public ModelFile getModelFileByArffId(Integer id);

}
