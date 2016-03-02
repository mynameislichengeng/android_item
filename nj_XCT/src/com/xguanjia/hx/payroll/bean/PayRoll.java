package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * 工资单列表实体类
 * 
 * @author Administrator
 *
 */
public class PayRoll implements Serializable {

	private static final long serialVersionUID = 1L;
	private String payRollId = "";// 工资单id
	private String payRollTitle = "";// 工资单标题
	private String payRollMoney = "";// 工资单金额
	private String payRollTime = "";// 工资单时间
	private String isread = "";// 状态：0未读1已读
	private String updateTime = "";// 更新时间戳
	private String salaryUseTypeId = "";// 所属分类的id 小类
	private String salaryUseGroupingId = ""; // 全部分类 大类

	private String tu_type = "";
	

	public String getTu_type() {
		return tu_type;
	}

	public void setTu_type(String tu_type) {
		this.tu_type = tu_type;
	}

	private ArrayList<EveryPayValueBean> groupSalaryList = new ArrayList<EveryPayValueBean>();

	public ArrayList<EveryPayValueBean> getGroupSalaryList() {
		return groupSalaryList;
	}

	public void setGroupSalaryList(ArrayList<EveryPayValueBean> groupSalaryList) {
		this.groupSalaryList = groupSalaryList;
	}

	public String getSalaryUseGroupingId() {
		return salaryUseGroupingId;
	}

	public void setSalaryUseGroupingId(String salaryUseGroupingId) {
		this.salaryUseGroupingId = salaryUseGroupingId;
	}

	public String getSalaryUseTypeId() {
		return salaryUseTypeId;
	}

	public void setSalaryUseTypeId(String salaryUseTypeId) {
		this.salaryUseTypeId = salaryUseTypeId;
	}

	public String getIsread() {
		return isread;
	}

	public void setIsread(String isread) {
		this.isread = isread;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPayRollId() {
		return payRollId;
	}

	public void setPayRollId(String payRollId) {
		this.payRollId = payRollId;
	}

	public String getPayRollTitle() {
		return payRollTitle;
	}

	public void setPayRollTitle(String payRollTitle) {
		this.payRollTitle = payRollTitle;
	}

	public String getPayRollMoney() {
		return payRollMoney;
	}

	public void setPayRollMoney(String payRollMoney) {
		this.payRollMoney = payRollMoney;
	}

	public String getPayRollTime() {
		return payRollTime;
	}

	public void setPayRollTime(String payRollTime) {
		this.payRollTime = payRollTime;
	}

}
