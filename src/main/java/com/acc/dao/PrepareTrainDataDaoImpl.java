package com.acc.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.acc.dao.AbstractDao;
import com.acc.dao.PrepareTrainDataDao;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;

@Repository
public class PrepareTrainDataDaoImpl extends AbstractDao implements PrepareTrainDataDao{

	public void saveExcelFile(ExcelFile excelFile) {
		Session session = getSession();
		session.save(excelFile);
	}
	
	public void saveCsvFile(CsvFile csvFile) {
		Session session = getSession();
		session.save(csvFile);
	}
}
