package com.acc.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	private String filename;
	private List<MultipartFile> file;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
}
