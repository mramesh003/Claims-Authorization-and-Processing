package com.acc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;
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
	
	public List<ExcelFile> listAllExcels(){
		Session session = getSession();
		Query query = session.createQuery("from ExcelFile");
		return query.list();
	}
	
	public ExcelFile getExcelFileById(Integer excelId){
		Session session = getSession();
		ExcelFile excelFile = new ExcelFile();
		Query query = session.createQuery("select e from ExcelFile e where e.id=:excelId ");
		query.setParameter("excelId", excelId);
		List<ExcelFile> excelList = query.list();
		for (ExcelFile file : excelList) {
			excelFile.setId(file.getId());
			excelFile.setFileName(file.getFileName());
			excelFile.setFileContent(file.getFileContent());
		}
		return excelFile;	
	}
	
	public CsvFile getCsvFileById(Integer csvId){
		Session session = getSession();
		CsvFile csvfile = new CsvFile();
		Query query = session.createQuery("select e from CsvFile e where e.id=:csvId ");
		query.setParameter("csvId", csvId);
		List<CsvFile> csvList = query.list();
		for (CsvFile file : csvList) {
			csvfile = file;
		}
		return csvfile;	
	} 
	

	public List<CsvFile> listAllCsvs() {
		Session session = getSession();
		Query query = session.createQuery("from CsvFile");
		return query.list();
	}
}
