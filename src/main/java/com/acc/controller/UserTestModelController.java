package com.acc.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.acc.utility.ExcelUtility;
import com.acc.utility.RowCount;
import com.acc.utility.SupervisedModel;

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
	@RequestMapping("generateReport.xls")
	public ModelAndView redirect(HttpServletRequest request) {
		ModelAndView modelandview = new ModelAndView();
		modelandview.setViewName("modelEvalReport");
		return modelandview;
	}

	@RequestMapping("evaluateResults.xls")
	public ModelAndView evaluateExcelWithModel(HttpServletRequest request, FileUpload uploadItem,
			@RequestParam("language") String language) throws Exception {
		ModelAndView modelandview = new ModelAndView();

		List<MultipartFile> files = uploadItem.getFile();
		List<String> claimData = new ArrayList<String>();
		InputStream inputStream = null;
		HttpSession session = request.getSession(); 
		int numberOfTestClaims = 0;
		int acceptCount = 0;
		int pendCount = 0;
		int rejectCount = 0;
		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			inputStream = file.getInputStream();
			int position = fileName.lastIndexOf(".");
			String fileType = fileName.substring(position);
			byte[] excelData = null;
			excelData = IOUtils.toByteArray(inputStream);
			session.setAttribute("sourcefile", excelData);
			ExcelFile excelFile = new ExcelFile();
			excelFile.setFileName(fileName);
			excelFile.setFileContent(excelData);
			inputStream = file.getInputStream();
			byte[] csvData = null;
			if (".xlsx".equals(fileType)) {
				csvData = ClaimsUtility.XLSX2CSV(inputStream, language);
				inputStream = file.getInputStream();
				excelFile.setRowcount(RowCount.xlsxRowCount(inputStream));
				numberOfTestClaims = RowCount.xlsxRowCount(file.getInputStream());
				excelFile.setColCount(ColumnCount.xlsxColumnCount(file.getInputStream()));
			} else if (".xls".equals(fileType)) {
				csvData = ClaimsUtility.XLS2CSV(inputStream, language);
				inputStream = file.getInputStream();
				excelFile.setRowcount(RowCount.xlsRowCount(inputStream));
				numberOfTestClaims = RowCount.xlsRowCount(file.getInputStream());
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
				List<ModelFile> modelfiles = trainModelService.listAllModelsOfJava();
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
				try {
					evaluationResult = SupervisedModel.evaluateModel(model, arffFile);

				} catch (Exception e) {
					e.printStackTrace();
				}
				numberOfTestClaims = results.size();
				for(String res : results)
				{
					if(res.equalsIgnoreCase("accept"))
						acceptCount++;
					if(res.equalsIgnoreCase("pend"))
						pendCount++;
					if(res.equalsIgnoreCase("reject"))
						rejectCount++;

				}
				session.setAttribute("excelFile",excelFile);
				session.setAttribute("results",results);
				session.setAttribute("evaluationResult", evaluationResult);
				modelandview.addObject("numberOfTestClaims",numberOfTestClaims );
				modelandview.addObject("acceptCount", acceptCount);
				modelandview.addObject("pendCount",pendCount );
				modelandview.addObject("rejectCount", rejectCount);
				modelandview.setViewName("evalResultDisplay");
				//modelandview.setViewName("modelEvalReport");
			}
			if (language.equals("python"))
			{
				String baseUrl = "http://localhost:5000/evaluate/";
				String completeUrl = baseUrl + csvFile.getId();
				URL url = new URL(completeUrl);
				URLConnection urlcon = url.openConnection();
				Map<String, Object> evaluationResult = new HashMap<String, Object>();
				InputStream stream = urlcon.getInputStream();				
				String line = null;
				StringBuilder sb = new StringBuilder();
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				String result = sb.toString();
				int index = result.indexOf("result");
				String claimsStr = result.substring(0,index);
				String confusionMatrixStr = result.substring(index + 6);
				String confusionMatrix = confusionMatrixStr.replaceAll(",","\n");
				evaluationResult.put("Evaluation results", " ");
				evaluationResult.put("Confusion Matrix", confusionMatrix);
				String claims[] = claimsStr.split(",");
				numberOfTestClaims = claims.length;
				acceptCount = 0;
				pendCount = 0;
				rejectCount = 0;
				for (int i = 0; i < claims.length; i++) {
					claimData.add(claims[i]);
					if(claims[i].equalsIgnoreCase("accept"))
						acceptCount++;
					if(claims[i].equalsIgnoreCase("pend"))
						pendCount++;
					if(claims[i].equalsIgnoreCase("reject"))
						rejectCount++;
				}			
				session.setAttribute("results",claimData);
				session.setAttribute("evaluationResult", evaluationResult);
				session.setAttribute("excelFile",excelFile);
				/*	String baseUrl1 = "http://localhost:5000/getmatrix";
				URL url1 = new URL(baseUrl1);
				URLConnection urlcon1 = url1.openConnection();
				InputStream stream1 = urlcon1.getInputStream();	
				byte[] imageFile = IOUtils.toByteArray(stream1);
				byte[] encodeBase64 = Base64.encode(imageFile);
				String base64Encoded = new String(encodeBase64,"UTF-8");
				modelandview.addObject("imagefile", base64Encoded);
				modelandview.addObject("flag","yes");*/
				modelandview.addObject("numberOfTestClaims",numberOfTestClaims );
				modelandview.addObject("acceptCount", acceptCount);
				modelandview.addObject("pendCount",pendCount );
				modelandview.addObject("rejectCount", rejectCount);
				modelandview.setViewName("evalResultDisplay");
			}
		}
		
		ExcelFile destinationExcelFile = new ExcelFile();
		destinationExcelFile = trainModelService.getExcelFilebyModel(language);
		byte [] destFile = destinationExcelFile.getFileContent();
		byte [] srcFile = (byte[]) session.getAttribute("sourcefile");
		byte [] resultExcelContent =  ExcelUtility.xlsxDataAppend(new ByteArrayInputStream(srcFile) , new ByteArrayInputStream(destFile) );
		ExcelFile resultExcel = new ExcelFile();
		resultExcel.setFileName(destinationExcelFile.getFileName());
		resultExcel.setFileContent(resultExcelContent);
		resultExcel.setRowcount(RowCount.xlsxRowCount(new ByteArrayInputStream(resultExcelContent)));
		resultExcel.setColCount(destinationExcelFile.getColCount());
		prepareTrainDataService.saveExcelFile(resultExcel);
		ExcelFile appendFile = new ExcelFile();
		appendFile= trainModelService.getLatestExcelFile();
		int position = appendFile.getFileName().lastIndexOf(".");
		String fileType = appendFile.getFileName().substring(position);
		byte[] csvData = null;
		if (".xlsx".equals(fileType)) {
			csvData = ClaimsUtility.XLSX2CSV(new ByteArrayInputStream(appendFile.getFileContent()),language);
		}
		else if (".xls".equals(fileType)) {
			csvData = ClaimsUtility.XLS2CSV(new ByteArrayInputStream(appendFile.getFileContent()),language);
		}
		String csvName = appendFile.getFileName().substring(0, position) + ".csv";
		CsvFile csvFile = new CsvFile();
		csvFile.setFileName(csvName);
		csvFile.setFileContent(csvData);
		csvFile.setExcelId(appendFile.getId());
		csvFile.setRowCount(appendFile.getRowcount());
		csvFile.setColumnCount(appendFile.getColCount());
		if(language.equals("java"))
			csvFile.setIsJava(true);
		else
			csvFile.setIsJava(false);
		prepareTrainDataService.saveCsvFile(csvFile);
	
		if(language.equalsIgnoreCase("java"))
		{
			CsvFile csvfile = new CsvFile();
			csvfile= trainModelService.getLatestCSVFile();
			int id = csvfile.getId();
			boolean flag = trainModelService.convertToArffFilebyCsvId(Integer.valueOf(id));
			ArffFile arfffile = new ArffFile();
			arfffile= trainModelService.getLatestArffFile();
			int position4 = arfffile.getFileName().lastIndexOf(".");
			String modelName = arfffile.getFileName().substring(0, position4) + ".model";
			InputStream inputStream4 = new ByteArrayInputStream(arfffile.getFileContent());
			byte[] modelContent = ClaimsUtility.ARFF2Model(inputStream4);
			ModelFile modelFile = new ModelFile();
			modelFile.setFileContent(modelContent);
			modelFile.setFileName(modelName);
			modelFile.setColCount(arfffile.getColCount());
			modelFile.setRowcount(arfffile.getRowCount());
			modelFile.setArffId(arfffile.getId());
			modelFile.setFlag("java");
			trainModelService.saveModel(modelFile);

		}
		else if(language.equalsIgnoreCase("python"))
		{
			String baseUrll= "http://localhost:5000/saveModell";
			URL urll = new URL(baseUrll);
			URLConnection urlconn = urll.openConnection();
			InputStream streaminput = urlconn.getInputStream();
			System.out.println(streaminput);
		}
		return modelandview;
	}
}


