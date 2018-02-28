package com.acc.dao;

import java.util.List;

import com.acc.dto.ArffFile;

public interface TrainModelDao {
	public void saveArffFile(ArffFile arffFile);
	
	public List<ArffFile> listAllArffs();

	public ArffFile getArffFileById(Integer fileId);
	
	public void deleteArff(ArffFile arffFile);
}
