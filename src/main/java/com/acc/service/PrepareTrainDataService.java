package com.acc.service;


import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;

public interface PrepareTrainDataService {
	
	public void saveExcelFile(ExcelFile excelFile)throws Exception;
	
	public void saveCsvFile(CsvFile csvFile)throws Exception;
	
	public List<ExcelFile> listAllExcels()throws Exception;
	
	public List<CsvFile> listAllCsvs()throws Exception;
	
	public ExcelFile getExcelFileById(Integer fileId)throws Exception;
	
	public ExcelFile getExcelFileByName(String fileName)throws Exception;
	
	public CsvFile getCsvFileById(Integer csvId)throws Exception;
	
	public CsvFile getCsvFileByExcelId(Integer excelId)throws Exception;
	
	public void deleteCsv(CsvFile csvFile);
	
	public void deleteExcel(ExcelFile excelFile);
	
	public List<ArffFile> getArffByCsvId(Integer csvId)throws Exception;

	public List<CsvFile> listAllPythonCsv()throws Exception;
	
	public List<ModelFile> getModelFileByCsvId(Integer csvId)throws Exception;

}
