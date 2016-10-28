package com.bittorentlike.common;

import java.io.Serializable;

public class ChunkTest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String filePath;
	public ChunkTest(String fileName, String filePath) {
		this.setFileName(fileName);
		this.setFilePath(filePath);
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
