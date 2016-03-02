package com.xguanjia.hx.payroll.bean;

import java.io.Serializable;

public class QuanBu_AfterBean implements Serializable {

	private String money;
	private String time;
	private float background;
	private int flag = 1;
	
	
	public QuanBu_AfterBean(String money, String time, float background, int flag) {
		super();
		this.money = money;
		this.time = time;
		this.background = background;
		this.flag = flag;
	}
	public QuanBu_AfterBean() {
		super();
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getBackground() {
		return background;
	}
	public void setBackground(float background) {
		this.background = background;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	

}
