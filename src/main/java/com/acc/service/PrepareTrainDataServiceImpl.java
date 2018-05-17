package com.acc.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.dao.PrepareTrainDataDao;
import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;
import com.acc.service.PrepareTrainDataService;

@Service
public class PrepareTrainDataServiceImpl implements PrepareTrainDataService {
	
	@Autowired
	PrepareTrainDataDao prepareTrainDataDao;
	
	@Transactional
	public void saveExcelFile(ExcelFile excelFile) throws Exception{
		prepareTrainDataDao.saveExcelFile(excelFile);
	}
	
	@Transactional
	public void saveCsvFile(CsvFile csvFile) throws Exception{
		prepareTrainDataDao.saveCsvFile(csvFile);
	}

	@Transactional(readOnly=true)
	public List<ExcelFile> listAllExcels() throws Exception{
		return prepareTrainDataDao.listAllExcels();
	}
	
	@Transactional(readOnly=true)
	public ExcelFile getExcelFileById(Integer fileId) throws Exception{
		return prepareTrainDataDao.getExcelFileById(fileId);
	}
	
	@Transactional(readOnly=true)
	public ExcelFile getExcelFileByName(String fileName)throws Exception{
		return prepareTrainDataDao.getExcelFileByName(fileName);
	}

	@Transactional(readOnly=true)
	public List<CsvFile> listAllCsvs() throws Exception{
		return prepareTrainDataDao.listAllCsvs();
	}
	
	@Transactional(readOnly=true)
	public CsvFile getCsvFileById(Integer csvId) throws Exception{		
		return prepareTrainDataDao.getCsvFileById(csvId);
	}
	
	@Transactional(readOnly=true)
	public CsvFile getCsvFileByExcelId(Integer excelId)throws Exception{
		return prepareTrainDataDao.getCsvFileByExcelId(excelId);
	}
	
	@Transactional
	public void deleteCsv(CsvFile csvFile) {		
		 prepareTrainDataDao.deleteCsv(csvFile);
	}
	
	@Transactional
	public void deleteExcel(ExcelFile excelFile) {
		 prepareTrainDataDao.deleteExcel(excelFile);
	}

	@Transactional
	public List<ArffFile> getArffByCsvId(Integer csvId)throws Exception {
		return prepareTrainDataDao.getArffFileByCsId(csvId);
	}

	@Transactional
	public List<CsvFile> listAllPythonCsv() throws Exception{
		return prepareTrainDataDao.listAllPythonCsv();
	}
	
	@Transactional
	public List<ModelFile> getModelFileByCsvId(Integer csvId)throws Exception {
		return prepareTrainDataDao.getModelFileByCsvId(csvId);
	}
	

}
