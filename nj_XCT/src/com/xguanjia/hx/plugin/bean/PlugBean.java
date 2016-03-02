package com.xguanjia.hx.plugin.bean;

import java.io.Serializable;

public class PlugBean implements Serializable {
	public String getTemplateZipFile() {
		return templateZipFile;
	}
	public void setTemplateZipFile(String templateZipFile) {
		this.templateZipFile = templateZipFile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getClientMenuName() {
		return clientMenuName;
	}
	public void setClientMenuName(String clientMenuName) {
		this.clientMenuName = clientMenuName;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getPluginType() {
		return pluginType;
	}
	public void setPluginType(String pluginType) {
		this.pluginType = pluginType;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	private String templateZipFile;
	private String id;
	private String operateType;
	private String clientMenuName;
	private String createDate;
	private String pluginType;
	private String sortNum;
	private String templateName;
}
