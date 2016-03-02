package com.xguanjia.hx.payroll.bean;

import java.util.ArrayList;

public class DataListBean {
	private ArrayList<Value_ItemBean> list;
	private ArrayList<ItemBean_grouplist> grouplist;
	private ArrayList<ItemBean_templist> templist;
	private String allwages = "";
	private String wages = "";

	public ArrayList<Value_ItemBean> getList() {
		return list;
	}

	public void setList(ArrayList<Value_ItemBean> list) {
		this.list = list;
	}

	public ArrayList<ItemBean_grouplist> getGrouplist() {
		return grouplist;
	}

	public void setGrouplist(ArrayList<ItemBean_grouplist> grouplist) {
		this.grouplist = grouplist;
	}

	public ArrayList<ItemBean_templist> getTemplist() {
		return templist;
	}

	public void setTemplist(ArrayList<ItemBean_templist> templist) {
		this.templist = templist;
	}

	public String getAllwages() {
		return allwages;
	}

	public void setAllwages(String allwages) {
		this.allwages = allwages;
	}

	public String getWages() {
		return wages;
	}

	public void setWages(String wages) {
		this.wages = wages;
	}

}
