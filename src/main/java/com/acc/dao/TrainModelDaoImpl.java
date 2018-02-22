package com.acc.dao;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.acc.dto.ArffFile;
@Repository
public class TrainModelDaoImpl extends AbstractDao implements TrainModelDao {
	
	public void saveArffFile(ArffFile arffFile){
		Session session = getSession();
		session.save(arffFile);		
	}
}
