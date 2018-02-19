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
@Table(name = "csvfiles")
public class CsvFile {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "filecontent")
	private byte[] fileContent;
	
	@Column(name = "excelId")
	private Integer excelId;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(insertable=false,updatable=false,name="excelId")
	private ExcelFile excelFile;
	
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
	
	
}
