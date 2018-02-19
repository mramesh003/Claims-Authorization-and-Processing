package com.acc.service;


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
	
	@Transactional(readOnly=true)
	public void saveExcelFile(ExcelFile excelFile) {
		prepareTrainDataDao.saveExcelFile(excelFile);
	}
	
	@Transactional(readOnly=true)
	public void saveCsvFile(CsvFile csvFile) {
		prepareTrainDataDao.saveCsvFile(csvFile);
	}
}
