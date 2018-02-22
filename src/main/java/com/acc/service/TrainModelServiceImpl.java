package com.acc.service;

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
}
