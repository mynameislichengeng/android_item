package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class ItemBean_templist implements Serializable {

	// "salaryGroupId": "2",
	// "basisName": "value1",
	// "salaryFieldName": "基本工资"

	private String salaryGroupId = "";
	private String basisName = "";
	private String salaryFieldName = "";

	public String getSalaryGroupId() {
		return salaryGroupId;
	}

	public void setSalaryGroupId(String salaryGroupId) {
		this.salaryGroupId = salaryGroupId;
	}

	public String getBasisName() {
		return basisName;
	}

	public void setBasisName(String basisName) {
		this.basisName = basisName;
	}

	public String getSalaryFieldName() {
		return salaryFieldName;
	}

	public void setSalaryFieldName(String salaryFieldName) {
		this.salaryFieldName = salaryFieldName;
	}

}
