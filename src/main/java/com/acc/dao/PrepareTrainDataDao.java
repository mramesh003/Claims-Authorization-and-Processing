package com.acc.dao;


import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;

public interface PrepareTrainDataDao {

	public void saveExcelFile(ExcelFile excelFile);
	
	public void saveCsvFile(CsvFile csvFile);
}
