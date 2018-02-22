package com.acc.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.dao.PrepareTrainDataDao;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.service.PrepareTrainDataService;

@Service
public class PrepareTrainDataServiceImpl implements PrepareTrainDataService {
	
	@Autowired
	PrepareTrainDataDao prepareTrainDataDao;
	
	@Transactional
	public void saveExcelFile(ExcelFile excelFile) {
		prepareTrainDataDao.saveExcelFile(excelFile);
	}
	
	@Transactional
	public void saveCsvFile(CsvFile csvFile) {
		prepareTrainDataDao.saveCsvFile(csvFile);
	}

	@Transactional(readOnly=true)
	public List<ExcelFile> listAllExcels() {
		return prepareTrainDataDao.listAllExcels();
	}
	
	@Transactional(readOnly=true)
	public ExcelFile getExcelFileById(Integer fileId) {
		return prepareTrainDataDao.getExcelFileById(fileId);
	}

	@Transactional(readOnly=true)
	public List<CsvFile> listAllCsvs() {
		return prepareTrainDataDao.listAllCsvs();
	}

	@Transactional(readOnly=true)
	public CsvFile getCsvFileById(Integer csvId) {		
		return prepareTrainDataDao.getCsvFileById(csvId);
	}
}
