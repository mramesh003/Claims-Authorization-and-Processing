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
public class TrainModelDaoImpl extends AbstractDao implements TrainModelDao {

	public void saveArffFile(ArffFile arffFile){
		Session session = getSession();
		session.save(arffFile);		
	}

	public List<ArffFile> listAllArffs(){
		Session session = getSession();
		Query query = session.createQuery("select a from ArffFile a where a.excelId in (select m.id from ExcelFile m where m.activeStatus is not null)");
		return query.list();
	}

	public ArffFile getArffFileById(Integer arffId){
		Session session = getSession();
		ArffFile arffFile = new ArffFile();
		Query query = session.createQuery("select a from ArffFile a where a.id=:arffId ");
		query.setParameter("arffId", arffId);
		List<ArffFile> arffList = query.list();
		return (ArffFile)query.uniqueResult();	
	}

	public void deleteArff(ArffFile arffFile) {
		Session session = getSession();
		session.delete(arffFile);


	}

	public void saveModel(ModelFile modelFile) {
		Session session = getSession();
		session.save(modelFile);		
	}

	public ModelFile getModelById(Integer fileId) {
		Session session = getSession();
		Query query = session.createQuery("select a from ModelFile a where a.id=:fileId ");
		query.setParameter("fileId", fileId);
		return (ModelFile)query.uniqueResult();
	}

	public List<ModelFile> listAllModelsOfJava() {
		Session session = getSession();
		Query query = session.createQuery("select a from ModelFile a where a.flag=:flag");
		query.setParameter("flag","java");
		return query.list();
	}

	public ModelFile getModelFileByArffId(Integer id) {
		Session session = getSession();
		Query query = session.createQuery("select a from ModelFile a where a.arffId=:fileId ");
		query.setParameter("fileId", id);
		return (ModelFile)query.uniqueResult();
	}

	public ExcelFile getExcelFilebyModel(String language) {
		Session session = getSession();
		ExcelFile excelfile = new ExcelFile();
		if(language.equalsIgnoreCase("java"))
		{
			Query query = session.createQuery("select a.arffId from ModelFile a where a.flag=:flag order by a.id desc");
			query.setParameter("flag", "java");
			query.setMaxResults(1);
			int arffid = (Integer) query.uniqueResult();

			Query query1 = session.createQuery("select a.excelId from ArffFile a where a.id=:arffid ");
			query1.setParameter("arffid", arffid);
			int excelid = (Integer) query1.uniqueResult();

			Query query2 = session.createQuery("select a from ExcelFile a where a.id=:excelid ");
			query2.setParameter("excelid", excelid);
			excelfile=(ExcelFile) query2.uniqueResult();


		}
		else if(language.equalsIgnoreCase("python"))
		{	
			Query query = session.createQuery("select a.csvId from ModelFile a where a.flag=:flag order by a.id desc");
			query.setParameter("flag", "python");
			query.setMaxResults(1);
			int csvid = (Integer) query.uniqueResult();

			Query query1 = session.createQuery("select a.excelId from CsvFile a where a.id=:csvid ");
			query1.setParameter("csvid", csvid);
			int excelid = (Integer) query1.uniqueResult();                                                   

			Query query2 = session.createQuery("select a from ExcelFile a where a.id=:excelid ");
			query2.setParameter("excelid", excelid);
			excelfile=(ExcelFile) query2.uniqueResult();

		}
		
		return excelfile;

	}
	public ExcelFile getLatestExcelFile() {
		ExcelFile excelfile = new ExcelFile();
		Session session = getSession();
		Query query = session.createQuery("select a from ExcelFile a order by a.id desc");
		query.setMaxResults(1);
		excelfile=(ExcelFile) query.uniqueResult();
		return excelfile;

	}

	public CsvFile getLatestCSVFile() {
		CsvFile csvfile = new CsvFile();
		Session session = getSession();
		Query query = session.createQuery("select a from CsvFile a order by a.id desc");
		query.setMaxResults(1);
		csvfile=(CsvFile) query.uniqueResult();
		return csvfile;

	}

	public ArffFile getLatestArffFile() {
		ArffFile arfffile = new ArffFile();
		Session session = getSession();
		Query query = session.createQuery("select a from ArffFile a order by a.id desc");
		query.setMaxResults(1);
		arfffile=(ArffFile) query.uniqueResult();
		return arfffile;

	}
}



