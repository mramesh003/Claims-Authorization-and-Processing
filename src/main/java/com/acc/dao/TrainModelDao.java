package com.acc.dao;

import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;
import com.acc.dto.CsvFile;

public interface TrainModelDao {
	public void saveArffFile(ArffFile arffFile);
	
	public List<ArffFile> listAllArffs();

	public ArffFile getArffFileById(Integer fileId);
	
	public void deleteArff(ArffFile arffFile);
	
	public void saveModel(ModelFile modelFile);

	public ModelFile getModelById(Integer fileId);
    
    public List<ModelFile> listAllModelsOfJava();
    
    public ModelFile getModelFileByArffId(Integer id);
    
    public ExcelFile getExcelFilebyModel(String language);
    
    public ExcelFile getLatestExcelFile();
    
    public CsvFile getLatestCSVFile();
    
    public ArffFile getLatestArffFile();

}
