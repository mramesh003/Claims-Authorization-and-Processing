package com.acc.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.acc.dao.PrepareTrainDataDao;
import com.acc.dao.TrainModelDao;
import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ModelFile;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

@Service
public class TrainModelServiceImpl implements TrainModelService {

	@Autowired
	TrainModelDao trainModelDao;

	@Autowired
	PrepareTrainDataDao prepareTrainDataDao;

	@Transactional
	public void saveArffFile(ArffFile arffFile) {
		trainModelDao.saveArffFile(arffFile);
	}

	@Transactional(readOnly = true)
	public List<ArffFile> listAllArffs() {
		return trainModelDao.listAllArffs();
	}

	@Transactional(readOnly = true)
	public ArffFile getArffFileById(Integer fileId) {
		return trainModelDao.getArffFileById(fileId);
	}

	@Transactional
	public void deleteArff(ArffFile arffFile) {
		trainModelDao.deleteArff(arffFile);
	}

	@Transactional
	public boolean convertToArffFilebyCsvId(Integer csvId) {
		boolean flag = false;
		CsvFile csvfile = prepareTrainDataDao.getCsvFileById(csvId);
		InputStream inputStream = new ByteArrayInputStream(csvfile.getFileContent());
		try {
			CSVLoader loader = new CSVLoader();
			loader.setSource(inputStream);
			Instances data = loader.getDataSet();
			byte[] arffFileContent = data.toString().getBytes();

			String csvFileName = csvfile.getFileName();
			int index = csvFileName.lastIndexOf('.');
			String arffilename = csvFileName.substring(0, index) + ".arff";

			ArffFile arffFile = new ArffFile();
			arffFile.setFileName(arffilename);
			arffFile.setFileContent(arffFileContent);
			arffFile.setColCount(data.numAttributes());
			arffFile.setRowCount(data.size());
			arffFile.setCsvId(csvId);

			arffFile.setExcelId(csvfile.getExcelId());
			trainModelDao.saveArffFile(arffFile);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}
	
	@Transactional
	public void saveModel(ModelFile modelFile) {
		trainModelDao.saveModel(modelFile);
		
	}
	
	@Transactional(readOnly = true)
    public ModelFile getModelById(Integer fileId) {
           return trainModelDao.getModelById(fileId);
    }

    @Transactional(readOnly = true)
    public List<ModelFile> listAllModelsOfJava() {
           return trainModelDao.listAllModelsOfJava();
    }

    @Transactional
	public ModelFile getModelFileByArffId(Integer id) {
		return trainModelDao.getModelFileByArffId(id);
	}


}
