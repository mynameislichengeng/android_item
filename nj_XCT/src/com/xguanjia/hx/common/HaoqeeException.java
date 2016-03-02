package com.xguanjia.hx.common;

public class HaoqeeException extends Exception {

	private static final long serialVersionUID = 1L;

	public HaoqeeException(String msg, Exception e) {
		super(msg, e);
	}

	public HaoqeeException(String msg) {
		super(msg);
	}

	public HaoqeeException(String msg, Throwable e) {
		super(msg, e);
	}
}
