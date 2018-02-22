


package com.acc.dao;


import java.util.List;

import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;

public interface PrepareTrainDataDao {

	public void saveExcelFile(ExcelFile excelFile);
	
	public void saveCsvFile(CsvFile csvFile);
	
	public List<ExcelFile> listAllExcels();
	
	public ExcelFile getExcelFileById(Integer fileId);
	
	public CsvFile getCsvFileById(Integer csvId);
	
	public List<CsvFile> listAllCsvs();
	
	
}
