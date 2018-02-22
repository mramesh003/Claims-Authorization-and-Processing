package com.acc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.dao.TrainModelDao;
import com.acc.dto.ArffFile;

@Service
public class TrainModelServiceImpl implements TrainModelService{

	@Autowired
	TrainModelDao trainModelDao;

	@Transactional
	public void saveArffFile(ArffFile arffFile) {
		trainModelDao.saveArffFile(arffFile);
	}

	@Transactional(readOnly=true)
	public List<ArffFile> listAllArffs() {
		return trainModelDao.listAllArffs();
	}
	
	@Transactional(readOnly=true)
	public ArffFile getArffFileById(Integer fileId) {
		return trainModelDao.getArffFileById(fileId);
	}
	
	@Transactional
	public void deleteArff(ArffFile arffFile) {		
		 trainModelDao.deleteArff(arffFile);
	}
	
}
