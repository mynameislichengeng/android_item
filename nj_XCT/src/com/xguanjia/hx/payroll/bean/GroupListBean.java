package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class GroupListBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String salaryGroupId="";
	private String groupingName="";
	private String sortNum="";
	public String getSalaryGroupId() {
		return salaryGroupId;
	}
	public void setSalaryGroupId(String salaryGroupId) {
		this.salaryGroupId = salaryGroupId;
	}
	public String getGroupingName() {
		return groupingName;
	}
	public void setGroupingName(String groupingName) {
		this.groupingName = groupingName;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	

}
