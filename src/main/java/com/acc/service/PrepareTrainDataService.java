package com.acc.service;


import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;

public interface PrepareTrainDataService {
	
	public void saveExcelFile(ExcelFile excelFile);
	
	public void saveCsvFile(CsvFile csvFile);

}
