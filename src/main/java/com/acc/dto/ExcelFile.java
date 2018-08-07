package com.acc.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "excelfiles")
public class ExcelFile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "filecontent")
	private byte[] fileContent;
	
	@Column(name = "rowcount")
	private Integer rowcount;
	
	
	@Column(name = "colcount")
	private Integer colCount;
	
	@Column(name = "activestatus")
	private Boolean activeStatus;
	
	@Column(name = "modeltype")
	private String modeltype;
	
	public String getModeltype() {
		return modeltype;
	}
	public void setModeltype(String modeltype) {
		this.modeltype = modeltype;
	}
	public Integer getRowcount() {
		return rowcount;
	}
	public void setRowcount(Integer rowcount) {
		this.rowcount = rowcount;
	}
	
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
	
	public Integer getColCount() {
		return colCount;
	}
	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}
	public Boolean getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
}
