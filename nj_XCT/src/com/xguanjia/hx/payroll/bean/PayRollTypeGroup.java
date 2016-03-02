package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

/***
 * 顶部大类
 * @author Administrator
 *
 */
public class PayRollTypeGroup implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String useGroupingId="";  //大类ID
	private String useGroupingName="";  //大类的名称
	private String sortNum="";
	public String getUseGroupingId() {
		return useGroupingId;
	}
	public void setUseGroupingId(String useGroupingId) {
		this.useGroupingId = useGroupingId;
	}
	public String getUseGroupingName() {
		return useGroupingName;
	}
	public void setUseGroupingName(String useGroupingName) {
		this.useGroupingName = useGroupingName;
	}
	public String getSortNum() {
		return sortNum;
	}
	public void setSortNum(String sortNum) {
		this.sortNum = sortNum;
	}
	
}
