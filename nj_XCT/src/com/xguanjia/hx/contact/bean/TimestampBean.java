package com.xguanjia.hx.contact.bean;

import java.io.Serializable;
/*****
 * 
 * @Title: TimestampBean.java
 * @Packagecom.xguanjia.hx.contact.bean
 * @Description:  说明：时间戳表对象
 * @author 吴金龙
 * @date 2013-11-25
 * @version V1.0
 */
public class TimestampBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String primaryId = "";  //时间戳表主键
	
	private String departmentTimestamp = "0";   //通讯录部门时间戳
	
	private String personTimestamp = "0";   //通讯录人员时间戳
	private String dutyTimestamp = "0";   //考勤历史时间戳

	public String getDutyTimestamp() {
		return dutyTimestamp;
	}

	public void setDutyTimestamp(String dutyTimestamp) {
		this.dutyTimestamp = dutyTimestamp;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getDepartmentTimestamp() {
		return departmentTimestamp;
	}

	public void setDepartmentTimestamp(String departmentTimestamp) {
		this.departmentTimestamp = departmentTimestamp;
	}

	public String getPersonTimestamp() {
		return personTimestamp;
	}

	public void setPersonTimestamp(String personTimestamp) {
		this.personTimestamp = personTimestamp;
	}

}