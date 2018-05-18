package com.acc.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;

@Repository
public class PrepareTrainDataDaoImpl extends AbstractDao implements PrepareTrainDataDao{

	public void saveExcelFile(ExcelFile excelFile) {
		Session session = getSession();
		session.save(excelFile);
	}
	
	public void updateExcelStatusToFalse(Integer id) {
		Session session = getSession();
		Query query = session.createQuery("Update ExcelFile e set e.activeStatus=:activeStatus where e.id=:id");
		query.setParameter("activeStatus", false);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	
	public void saveCsvFile(CsvFile csvFile) {
		Session session = getSession();
		session.save(csvFile);
	}
	
	public List<ExcelFile> listAllExcels(){
		Session session = getSession();
		Query query = session.createQuery("select e from ExcelFile e where e.activeStatus=:activeStatus");
		query.setParameter("activeStatus", true);
		return query.list();
	}
	
	public ExcelFile getExcelFileById(Integer excelId){
		Session session = getSession();
		ExcelFile excelFile = new ExcelFile();
		Query query = session.createQuery("select e from ExcelFile e where e.id=:excelId");
		query.setParameter("excelId", excelId);
		List<ExcelFile> excelList = query.list();
		for (ExcelFile file : excelList) {
			excelFile = file;
		}
		return excelFile;	
	}
	
	public ExcelFile getExcelFileByName(String fileName) {
		Session session = getSession();
		Query query = session.createQuery("select e from ExcelFile e where e.fileName=:fileName and e.activeStatus=:activeStatus");
		query.setParameter("activeStatus", true);
		query.setParameter("fileName", fileName);
		
		return (ExcelFile)query.uniqueResult();
	}
	
	public CsvFile getCsvFileById(Integer csvId){
		Session session = getSession();
		CsvFile csvfile = new CsvFile();
		Query query = session.createQuery("select e from CsvFile e where e.id=:csvId ");
		query.setParameter("csvId", csvId);
		return (CsvFile)query.uniqueResult();	
	} 
	
	public CsvFile getCsvFileByExcelId(Integer excelId){
		Session session = getSession();
		CsvFile csvfile = new CsvFile();
		Query query = session.createQuery("select e from CsvFile e where e.excelId=:excelId ");
		query.setParameter("excelId", excelId);
		List<CsvFile> csvList = query.list();
		for (CsvFile file : csvList) {
			csvfile = file;
		}
		return csvfile;	
	}

	public List<CsvFile> listAllCsvs() {
		Session session = getSession();
		Query query = session.createQuery("select e from CsvFile e where e.isJava=:isJava and e.excelId in (select m.id from ExcelFile m where m.activeStatus is not null)");
		query.setParameter("isJava",true);
		return query.list();
	}

	public void deleteCsv(CsvFile csvFile) {
		Session session = getSession();
		session.delete(csvFile);
	}
	
	public void deleteExcel(ExcelFile excelFile){
		Session session = getSession();
		session.delete(excelFile);
	}

	public List<ArffFile> getArffFileByCsId(Integer csvId) {
		Session session = getSession();
		ArffFile arffFile = new ArffFile();
		Query query = session.createQuery("select e from ArffFile e where e.csvId=:csvId");
		query.setParameter("csvId", csvId);
		List<ArffFile> arffList = query.list();
		return arffList;
	}

	public List<CsvFile> listAllPythonCsv() {
		Session session = getSession();
		Query query = session.createQuery("select e from CsvFile e where e.isJava=:isJava and e.excelId in (select m.id from ExcelFile m where m.activeStatus is not null)");
		query.setParameter("isJava",false);
		return query.list();
	}
	
	public List<ModelFile> getModelFileByCsvId(Integer csvId){
		Session session = getSession();
		ModelFile modelFile = new ModelFile();
		Query query = session.createQuery("select e from ModelFile e where e.csvId=:csvId");
		query.setParameter("csvId", csvId);
		List<ModelFile> modelFileList = query.list();
		return modelFileList;
	}

}
