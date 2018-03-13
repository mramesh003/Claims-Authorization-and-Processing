package com.acc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "arfffiles")
public class ArffFile {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "filecontent")
	private byte[] fileContent;
	
	@Column(name = "excelId")
	private Integer excelId;
	
	@Column(name = "csvId")
	private Integer csvId;
	
	@Column(name = "rowcount")
	private Integer rowCount;	
	
	@Column(name="colcount")
	private Integer colCount;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(insertable=false,updatable=false,name="excelId")
	private ExcelFile excelFile;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(insertable=false,updatable=false,name="csvId")
	private CsvFile csvFile;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public Integer getExcelId() {
		return excelId;
	}

	public void setExcelId(Integer excelId) {
		this.excelId = excelId;
	}

	public ExcelFile getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(ExcelFile excelFile) {
		this.excelFile = excelFile;
	}
	public Integer getCsvId() {
		return csvId;
	}

	public void setCsvId(Integer csvId) {
		this.csvId = csvId;
	}

	public CsvFile getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(CsvFile csvFile) {
		this.csvFile = csvFile;
	}
	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public Integer getColCount() {
		return colCount;
	}

	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}
	
}
