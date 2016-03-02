package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PayRollList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<PayRoll> list=new ArrayList<PayRoll>();
	
	private List<Integer> deleteids= new ArrayList<Integer>();
	
	
	public List<Integer> getDeleteids() {
		return deleteids;
	}
	public void setDeleteids(List<Integer> deleteids) {
		this.deleteids = deleteids;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<PayRoll> getList() {
		return list;
	}
	public void setList(List<PayRoll> list) {
		this.list = list;
	}
	
	

}
