package com.xguanjia.hx.application.bean;

import java.io.Serializable;

public class YdGridBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String drawableTitle = "";

	private int drawableResourceId = -1;

	public YdGridBean() {
	}

	public YdGridBean(String drawableTitle, int drawableResourceId) {
		this.drawableTitle = drawableTitle;
		this.drawableResourceId = drawableResourceId;
	}

	public String getDrawableTitle() {
		return drawableTitle;
	}

	public void setDrawableTitle(String drawableTitle) {
		this.drawableTitle = drawableTitle;
	}

	public int getDrawableResourceId() {
		return drawableResourceId;
	}

	public void setDrawableResourceId(int drawableResourceId) {
		this.drawableResourceId = drawableResourceId;
	}

}
