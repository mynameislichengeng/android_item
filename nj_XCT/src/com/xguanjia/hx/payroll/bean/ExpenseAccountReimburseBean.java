package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class ExpenseAccountReimburseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExpenseAccountBean reimburse=new ExpenseAccountBean();
	public ExpenseAccountBean getReimburse() {
		return reimburse;
	}
	public void setReimburse(ExpenseAccountBean reimburse) {
		this.reimburse = reimburse;
	}
	

}
