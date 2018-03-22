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
@Table(name = "modelfiles")
public class ModelFile {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "filename")
	private String fileName;
	
	@Column(name = "filecontent")
	private byte[] fileContent;
	
	@Column(name = "datacount")
	private Integer rowcount;
	
	@Column(name = "colcount")
	private Integer colCount;
	
	@Column(name = "arffId")
	private Integer arffId;
	
	@Column(name = "flag")
	private String flag;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(insertable=false,updatable=false,name="arffId")
	private ArffFile arffFile;

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

	public Integer getRowcount() {
		return rowcount;
	}

	public void setRowcount(Integer rowcount) {
		this.rowcount = rowcount;
	}

	public Integer getColCount() {
		return colCount;
	}

	public void setColCount(Integer colCount) {
		this.colCount = colCount;
	}

	public Integer getArffId() {
		return arffId;
	}

	public void setArffId(Integer arffId) {
		this.arffId = arffId;
	}

	public ArffFile getArffFile() {
		return arffFile;
	}

	public void setArffFile(ArffFile arffFile) {
		this.arffFile = arffFile;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
	

}
