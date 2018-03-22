package com.acc.dao;

import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.ModelFile;

public interface TrainModelDao {
	public void saveArffFile(ArffFile arffFile);
	
	public List<ArffFile> listAllArffs();

	public ArffFile getArffFileById(Integer fileId);
	
	public void deleteArff(ArffFile arffFile);
	
	public void saveModel(ModelFile modelFile);

	public ModelFile getModelById(Integer fileId);
    
    public List<ModelFile> listAllModelsOfJava();
    
    public ModelFile getModelFileByArffId(Integer id);

}
