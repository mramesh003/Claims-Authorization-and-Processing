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
import com.acc.dto.ExcelFile;
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
	public void saveArffFile(ArffFile arffFile) throws Exception{
		trainModelDao.saveArffFile(arffFile);
	}

	@Transactional(readOnly = true)
	public List<ArffFile> listAllArffs() throws Exception{
		return trainModelDao.listAllArffs();
	}

	@Transactional(readOnly = true)
	public ArffFile getArffFileById(Integer fileId)throws Exception {
		return trainModelDao.getArffFileById(fileId);
	}

	@Transactional
	public void deleteArff(ArffFile arffFile)throws Exception {
		trainModelDao.deleteArff(arffFile);
	}

	@Transactional
	public boolean convertToArffFilebyCsvId(Integer csvId) throws Exception{
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
	public void saveModel(ModelFile modelFile)throws Exception {
		trainModelDao.saveModel(modelFile);
		
	}
	
	@Transactional(readOnly = true)
    public ModelFile getModelById(Integer fileId) throws Exception{
           return trainModelDao.getModelById(fileId);
    }

    @Transactional(readOnly = true)
    public List<ModelFile> listAllModelsOfJava() throws Exception{
           return trainModelDao.listAllModelsOfJava();
    }

    @Transactional
	public ModelFile getModelFileByArffId(Integer id) throws Exception{
		return trainModelDao.getModelFileByArffId(id);
	}
    
    @Transactional
	public ExcelFile getExcelFilebyModel(String language)throws Exception {
		// TODO Auto-generated method stub
		return trainModelDao.getExcelFilebyModel(language);
	}
    @Transactional
    public ExcelFile getLatestExcelFile()throws Exception
    {
    	return trainModelDao.getLatestExcelFile();
    }
    @Transactional
    public CsvFile getLatestCSVFile()throws Exception
    {
    	return trainModelDao.getLatestCSVFile();
    }
    @Transactional
    public ArffFile getLatestArffFile()throws Exception
    {
    	return trainModelDao.getLatestArffFile();
    }

}
