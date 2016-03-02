package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class FilesBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileId="";
	private String fileName;
	private String filePath="";
	private String createtime="";
	private String ext="";
	private String fileType;
	private String note="";
	private String creator;
	private String creatorId;
	private String totalBytes;
	private String delFlag;
	private String firstKeyColumnName;
	private String id;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
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
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public String getTotalBytes() {
		return totalBytes;
	}
	public void setTotalBytes(String totalBytes) {
		this.totalBytes = totalBytes;
	}
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getFirstKeyColumnName() {
		return firstKeyColumnName;
	}
	public void setFirstKeyColumnName(String firstKeyColumnName) {
		this.firstKeyColumnName = firstKeyColumnName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
