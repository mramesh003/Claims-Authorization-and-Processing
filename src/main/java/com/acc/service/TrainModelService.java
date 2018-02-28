package com.acc.service;

import java.util.List;

import com.acc.dto.ArffFile;

public interface TrainModelService {
	public void saveArffFile(ArffFile arffFile);
	
	public List<ArffFile> listAllArffs();

	public ArffFile getArffFileById(Integer fileId);
	
	public void deleteArff(ArffFile arffFile);
	
	public boolean convertToArffFilebyCsvId(Integer csvId);
}
