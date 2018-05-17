package com.acc.service;

import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;
import com.acc.dto.CsvFile;

public interface TrainModelService {
	public void saveArffFile(ArffFile arffFile)throws Exception;
	
	public List<ArffFile> listAllArffs()throws Exception;

	public ArffFile getArffFileById(Integer fileId)throws Exception;
	
	public void deleteArff(ArffFile arffFile)throws Exception;
	
	public boolean convertToArffFilebyCsvId(Integer csvId)throws Exception;
	
	public void saveModel(ModelFile modelFile)throws Exception;
	
	public ModelFile getModelById(Integer fileId)throws Exception;
    
    public List<ModelFile> listAllModelsOfJava()throws Exception;
    
    public ModelFile getModelFileByArffId(Integer id)throws Exception;
   
    public ExcelFile getExcelFilebyModel(String language)throws Exception;
    
    public ExcelFile getLatestExcelFile()throws Exception;
    
    public CsvFile getLatestCSVFile()throws Exception;
    
    public ArffFile getLatestArffFile()throws Exception;

}
