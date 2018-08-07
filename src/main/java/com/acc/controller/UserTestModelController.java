package com.acc.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.udf.UDFFinder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.codec.Base64;
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
import com.acc.utility.excelBuilder.ModelEvalReport;

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
		try {
		modelandview.setViewName("modelEvalReport");
		HttpSession session = request.getSession();
		HSSFWorkbook workbook = (HSSFWorkbook) session.getAttribute("workbook");
		System.out.println(workbook);
		}
		catch(Exception e) {
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
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
		try
		{
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
			csvFile.setModeltype(excelFile.getModeltype());
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

				try {
					claimData = testModelService.testModel(model, arffFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				Map<String, Object> evaluationResult = new HashMap<String, Object>();
				try {
					evaluationResult = SupervisedModel.evaluateModel(model, arffFile);

				} catch (Exception e) {
					e.printStackTrace();
				}
				numberOfTestClaims = claimData.size();
				for(String res : claimData)
				{
					if(res.equalsIgnoreCase("accept"))
						acceptCount++;
					if(res.equalsIgnoreCase("pend"))
						pendCount++;
					if(res.equalsIgnoreCase("reject"))
						rejectCount++;

				}
				session.setAttribute("excelFile",excelFile);
				session.setAttribute("results",claimData);
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
				//int index1 = result.indexOf("acc");
				String claimsStr = result.substring(0,index);
				//String accScore = result.substring(index + 6);
				evaluationResult.put("Evaluation results", " ");
				//evaluationResult.put("Confusion Matrix", confusionMatrix);
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
				session.setAttribute("numberOfTestClaims",numberOfTestClaims);
				String baseUrl1 = "http://localhost:5000/getmatrix";
				URL url1 = new URL(baseUrl1);
				URLConnection urlcon1 = url1.openConnection();
				InputStream stream1 = urlcon1.getInputStream();	
				byte[] imageFile = IOUtils.toByteArray(stream1);
				byte[] encodeBase64 = Base64.encode(imageFile);
				String base64Encoded = new String(encodeBase64,"UTF-8");
				modelandview.addObject("imagefile", base64Encoded);
				modelandview.addObject("flag","yes");
				modelandview.addObject("numberOfTestClaims",numberOfTestClaims );
				modelandview.addObject("acceptCount", acceptCount);
				modelandview.addObject("pendCount",pendCount );
				modelandview.addObject("rejectCount", rejectCount);
				//modelandview.addObject("accScore", accScore);
				modelandview.setViewName("evalResultDisplay");
			}
		}
		}
		/*ExcelFile destinationExcelFile = new ExcelFile();
		destinationExcelFile = trainModelService.getExcelFilebyModel(language);
		byte [] destFile = destinationExcelFile.getFileContent();
		byte [] srcFile = (byte[]) session.getAttribute("sourcefile");
		XSSFWorkbook workBook = new XSSFWorkbook(); 
		byte[] resultContent = ExcelUtility.finalColAppend(excelFile, claimData, workBook);
		byte [] resultExcelContent =  ExcelUtility.xlsxDataAppend(new ByteArrayInputStream(resultContent) , new ByteArrayInputStream(destFile) );
		ExcelFile resultExcel = new ExcelFile();
		resultExcel.setFileName(destinationExcelFile.getFileName());
		resultExcel.setFileContent(resultExcelContent);
		resultExcel.setRowcount(RowCount.xlsxRowCount(new ByteArrayInputStream(resultExcelContent)));
		resultExcel.setColCount(destinationExcelFile.getColCount());
		prepareTrainDataService.saveExcelFile(resultExcel);
		ExcelFile appendFile = new ExcelFile();
		appendFile= trainModelService.getLatestExcelFile();
		position = appendFile.getFileName().lastIndexOf(".");
		fileType = appendFile.getFileName().substring(position);
		csvData = null;
		if (".xlsx".equals(fileType)) {
			csvData = ClaimsUtility.XLSX2CSV(new ByteArrayInputStream(appendFile.getFileContent()),language);
		}
		else if (".xls".equals(fileType)) {
			csvData = ClaimsUtility.XLS2CSV(new ByteArrayInputStream(appendFile.getFileContent()),language);
		}
		csvName = appendFile.getFileName().substring(0, position) + ".csv";
		csvFile = new CsvFile();
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
		}
		}
		}*/
		catch(NoClassDefFoundError e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		catch(Exception e)
		{
			modelandview.addObject("error", e.getMessage());
			modelandview.setViewName("errorPage");
		}
		
		
		return modelandview;
	}
	
    @RequestMapping("reasoncodepredict.htm")
	public void getreasonCode(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	
    	
    	

		HttpSession session = request.getSession();
		Workbook workBook = new XSSFWorkbook();
		ModelEvalReport evalreport = new ModelEvalReport();
		ExcelFile excelFile = (ExcelFile) session.getAttribute("excelFile");
		List<String> predictionResult = (List<String>) session.getAttribute("results");
		Map<String, Object> evaluationResult = (Map<String, Object>) session.getAttribute("evaluationResult");
		evalreport.xlsxReport(session, excelFile, predictionResult, evaluationResult, workBook);
		
			List<byte[]> list = new ArrayList<byte[]>();
		XSSFWorkbook workbook = (XSSFWorkbook) session.getAttribute("workbook");
		list = ExcelUtility.splitExcel(workbook, request);
		byte[] pendarray = list.get(0);
		byte[] rejectarray = list.get(1);
		ExcelFile excelfile  =new ExcelFile();
		excelfile.setActiveStatus(false);
		excelfile.setFileName("pend.xlsx");
		excelfile.setFileContent(pendarray);
		excelfile.setColCount(ColumnCount.xlsxColumnCount(new ByteArrayInputStream(pendarray)));
		excelfile.setRowcount(RowCount.xlsxRowCount(new ByteArrayInputStream(pendarray)));
		excelfile.setModeltype("pend");
		
		ExcelFile excelfile1  =new ExcelFile();
		excelfile1.setActiveStatus(false);
		excelfile1.setFileName("reject.xlsx");
		excelfile1.setFileContent(rejectarray);
		excelfile1.setColCount(ColumnCount.xlsxColumnCount(new ByteArrayInputStream(rejectarray)));
		excelfile1.setRowcount(RowCount.xlsxRowCount(new ByteArrayInputStream(rejectarray)));
		excelfile1.setModeltype("reject");
		
		
		prepareTrainDataService.saveExcelFile(excelfile1);
		System.out.println("****************************"+workbook);
		prepareTrainDataService.saveExcelFile(excelfile);
		
		InputStream pendstream = new ByteArrayInputStream(pendarray);
		byte[] pendcsvarray = ClaimsUtility.XLSX2CSV(pendstream, "python");
		CsvFile csvFile = new CsvFile();
		csvFile.setFileName("pend.csv");
		csvFile.setFileContent(pendcsvarray);
		csvFile.setIsJava(false);
		csvFile.setColumnCount(excelFile.getColCount());
		csvFile.setRowCount(excelFile.getRowcount());
		csvFile.setExcelId(excelFile.getId());
		csvFile.setModeltype("pend");
		prepareTrainDataService.saveCsvFile(csvFile);
		
		InputStream rejectstream = new ByteArrayInputStream(rejectarray);
		byte[] rejectcsvarray = ClaimsUtility.XLSX2CSV(rejectstream, "python");
		CsvFile csvFile1 = new CsvFile();
		csvFile1.setFileName("reject.csv");
		csvFile1.setFileContent(rejectcsvarray);
		csvFile1.setIsJava(false);
		csvFile1.setColumnCount(excelfile1.getColCount());
		csvFile1.setRowCount(excelfile1.getRowcount());
		csvFile1.setExcelId(excelfile1.getId());
		csvFile1.setModeltype("reject");
		prepareTrainDataService.saveCsvFile(csvFile1);
		
		System.out.println("===========**********============"+csvFile1.getId());
		String resultPend = null;
		String resultReject = null;
		String[] claims = null;
		String[] claims1 = null;
		
		if(excelFile.getRowcount() >0)
			resultPend = getReasonCodeFromAI(csvFile.getId().toString());
		if(resultPend!=null) {
			
		
		List<String> penddata = new ArrayList<String>();
 		int index = resultPend.indexOf("result");
		String claimsStr1 = resultPend.substring(0,index);
		 claims = claimsStr1.split(",");
	
		
		for (int i = 0; i < claims.length; i++) {
			penddata.add(claims[i]);
			
		}
	
		}
		
		if(excelfile1.getRowcount() >0)
			resultReject = getReasonCodeFromAI(csvFile1.getId().toString());
		
		if(resultReject!=null) {
			
		
		List<String> rejectdata = new ArrayList<String>();
 		int index1 = resultReject.indexOf("result");
		String claimsStr2 = resultReject.substring(0,index1);
		 claims1 = claimsStr2.split(",");
	
		
		for (int i = 0; i < claims1.length; i++) {
			rejectdata.add(claims1[i]);
			
		}	

		}
		
	
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=GeneratedReport.xlsx");
		
		XSSFWorkbook reportFinal=(XSSFWorkbook) session.getAttribute("workbook");
		int claimscount=(Integer) session.getAttribute("numberOfTestClaims");
		XSSFWorkbook finalexcel = ExcelUtility.finalColAppend(reportFinal,claims,claims1,claimscount);
		
		  
		
			finalexcel.write(response.getOutputStream());
		


		
	
		
		
	}

    public String getReasonCodeFromAI(String fileId) throws IOException
    {
    	String baseUrl = "http://localhost:5000/evaluate/";
		String completeUrl = baseUrl + fileId;
		URL url = new URL(completeUrl);
		URLConnection urlcon = url.openConnection();
		InputStream stream = urlcon.getInputStream();				
		String line = null;
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		String result = sb.toString();
		//String prediction[] = result.split(",");
		System.out.println("==================================");
		System.out.println(result);
		return result;
    	
    }

}



