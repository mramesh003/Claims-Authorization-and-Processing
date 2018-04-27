package com.acc.dao;

import java.util.List;
import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;

public interface PrepareTrainDataDao {

	public void saveExcelFile(ExcelFile excelFile);
	
	public void saveCsvFile(CsvFile csvFile);
	
	public List<ExcelFile> listAllExcels();
	
	public ExcelFile getExcelFileById(Integer fileId);
	
	public ExcelFile getExcelFileByName(String fileName);
	
	public CsvFile getCsvFileById(Integer csvId);
	
	public CsvFile getCsvFileByExcelId(Integer excelId);
	
	public List<CsvFile> listAllCsvs();
	
	public void deleteCsv(CsvFile csvFile);
	
	public void deleteExcel(ExcelFile excelFile);
	
	public List<ArffFile> getArffFileByCsId(Integer csvId);
	
	public List<CsvFile> listAllPythonCsv();
	
	public List<ModelFile> getModelFileByCsvId(Integer csvId);

	
	
}
