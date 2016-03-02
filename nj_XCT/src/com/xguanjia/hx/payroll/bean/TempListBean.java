package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class TempListBean implements Serializable{

	/*
	 * 饼状图实体类
	 */
	private static final long serialVersionUID = 1L;
	private String salaryFieldName="";
	private String basisName="";
	private String salaryGroupId="";
	
	public String getSalaryFieldName() {
		return salaryFieldName;
	}
	public void setSalaryFieldName(String salaryFieldName) {
		this.salaryFieldName = salaryFieldName;
	}
	public String getBasisName() {
		return basisName;
	}
	public void setBasisName(String basisName) {
		this.basisName = basisName;
	}
	public String getSalaryGroupId() {
		return salaryGroupId;
	}
	public void setSalaryGroupId(String salaryGroupId) {
		this.salaryGroupId = salaryGroupId;
	}

}
