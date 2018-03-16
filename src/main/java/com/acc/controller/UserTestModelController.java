package com.acc.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.acc.dto.ArffFile;
import com.acc.dto.CsvFile;
import com.acc.dto.ExcelFile;
import com.acc.dto.ModelFile;
import com.acc.entity.FileUpload;
import com.acc.service.PrepareTrainDataService;
import com.acc.service.TestModelService;
import com.acc.service.TrainModelService;
import com.acc.utility.ClaimsUtility;
import com.acc.utility.ColumnCount;
import com.acc.utility.RowCount;
import com.acc.utility.SupervisedModel;

import weka.classifiers.Evaluation;

@Controller
public class UserTestModelController {

	@Autowired
	PrepareTrainDataService prepareTrainDataService;

	@Autowired
	TrainModelService trainModelService;

	@Autowired
	TestModelService testModelService;

	@RequestMapping("userTestModel.htm")
	public ModelAndView trainSaveModel(HttpServletRequest request) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("userTestModel");
		return modelandview;
	}

	// "redirect:/yourDestinationControllerPath"

	/*
	 * @RequestMapping("evaluateModel.htm") public String
	 * evaluateModel(HttpServletRequest request,@RequestParam("language") String
	 * language) { String redirect = null; if(language.equals("java")) redirect
	 * = "redirect:/evaluateExcelWithModel.htm"; if(language.equals("python"))
	 * redirect = "redirect:/evaluateExcelWithModel.htm"; return redirect; }
	 */
	@RequestMapping("evaluateExcelWithModel.htm")
	public ModelAndView evaluateExcelWithModel(HttpServletRequest request, FileUpload uploadItem,
			@RequestParam("language") String language) throws IOException {
		ModelAndView modelandview = new ModelAndView();
		List<MultipartFile> files = uploadItem.getFile();

		InputStream inputStream = null;

		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			inputStream = file.getInputStream();
			int position = fileName.lastIndexOf(".");
			String fileType = fileName.substring(position);
			byte[] excelData = null;
			excelData = IOUtils.toByteArray(inputStream);
			ExcelFile excelFile = new ExcelFile();
			excelFile.setFileName(fileName);
			excelFile.setFileContent(excelData);
			inputStream = file.getInputStream();
			byte[] csvData = null;
			if (".xlsx".equals(fileType)) {
				csvData = ClaimsUtility.XLSX2CSV(inputStream, language);
				inputStream = file.getInputStream();
				excelFile.setRowcount(RowCount.xlsxRowCount(inputStream));
				excelFile.setColCount(ColumnCount.xlsxColumnCount(file.getInputStream()));
			} else if (".xls".equals(fileType)) {
				csvData = ClaimsUtility.XLS2CSV(inputStream, language);
				inputStream = file.getInputStream();
				excelFile.setRowcount(RowCount.xlsRowCount(inputStream));
				excelFile.setColCount(ColumnCount.xlsColumnCount(file.getInputStream()));
			}

			prepareTrainDataService.saveExcelFile(excelFile);

			CsvFile csvFile = new CsvFile();
			String csvName = excelFile.getFileName().substring(0, position) + ".csv";
			csvFile.setFileName(csvName);
			csvFile.setFileContent(csvData);
			csvFile.setExcelId(excelFile.getId());
			csvFile.setRowCount(excelFile.getRowcount());
			csvFile.setColumnCount(excelFile.getColCount());
			if (language.equals("java"))
				csvFile.setIsJava(true);
			if (language.equals("python"))
				csvFile.setIsJava(false);
			prepareTrainDataService.saveCsvFile(csvFile);

			if (language.equals("java")) {

				byte[] arffData = null;
				ArffFile arffFile = new ArffFile();
				String arffName = excelFile.getFileName().substring(0, position) + ".arff";
				arffData = ClaimsUtility.CSV2ARFF(new ByteArrayInputStream(csvFile.getFileContent()));
				arffFile.setFileName(arffName);
				arffFile.setFileContent(arffData);
				arffFile.setRowCount(csvFile.getRowCount());
				arffFile.setColCount(csvFile.getColumnCount());
				arffFile.setCsvId(csvFile.getId());
				arffFile.setExcelId(csvFile.getExcelId());
				trainModelService.saveArffFile(arffFile);
				List<ModelFile> modelfiles = trainModelService.listAllModels();
				ModelFile model = new ModelFile();
				for (ModelFile file1 : modelfiles)
					model = file1;

				List<String> results = new ArrayList<String>();
				try {
					results = testModelService.testModel(model, arffFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String, Object> evaluationResult = new HashMap<String, Object>();
				ArffFile trainArffFile = trainModelService.getArffFileById(model.getArffId());
				try {
					evaluationResult = SupervisedModel.evaluateModel(trainArffFile, arffFile);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				modelandview.addObject("results", results);
				modelandview.addObject("evaluationResult", evaluationResult);
				modelandview.addObject("isjava","yes");
			}
			if (language.equals("python"))
			{
				String baseUrl = "http://localhost:5000/evaluate/";
				String completeUrl = baseUrl + csvFile.getId();
				URL url = new URL(completeUrl);
				URLConnection urlcon = url.openConnection();
				InputStream stream = urlcon.getInputStream();
				String line = null;
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				List<List<String>> ListOfClaims = new ArrayList<List<String>>();
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();
				String claims[] = result.split(";");
				for (int i = 0; i < claims.length; i++) {
					List<String> claimData = new ArrayList<String>();
					String x = claims[i];
					String a = x.substring(1, x.length() - 1);
					String claim[] = a.split(",");
					for (int j = 0; j < claim.length; j++)
						claimData.add(claim[j]);
					ListOfClaims.add(claimData);
				}
				modelandview.addObject("ListOfClaims", ListOfClaims);
				modelandview.addObject("isjava","no");
			}
			
		}
		modelandview.addObject("resultpage", "yes");
		modelandview.setViewName("userTestModel");
		return modelandview;
	}
}
