package com.acc.service;


import java.util.List;

import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;

public interface PrepareTrainDataService {
	
	public void saveExcelFile(ExcelFile excelFile);
	
	public void saveCsvFile(CsvFile csvFile);
	
	public List<ExcelFile> listAllExcels();
	
	public List<CsvFile> listAllCsvs();
	
	public ExcelFile getExcelFileById(Integer fileId);
	
	public ExcelFile getExcelFileByName(String fileName);
	
	public CsvFile getCsvFileById(Integer csvId);
	
	public CsvFile getCsvFileByExcelId(Integer excelId);
	
	public void deleteCsv(CsvFile csvFile);
	
	public void deleteExcel(ExcelFile excelFile);
	
	public List<ArffFile> getArffByCsvId(Integer csvId);

	

}
