package com.xguanjia.hx.common;

/**
 * 报文包装类
 * @author Kirk Zhou
 * @date 2011-6-18
 */
public class ActionResponse {
	private int code = 0;
	private String message;
	private Object data;
	private Object returnData;//新增属性

	public ActionResponse(int code, String message, Object data, Object returnData) {
		this.code = code;
		this.message = message;
		this.data = data;
		this.returnData = returnData;
	}
	
	public ActionResponse(int code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public ActionResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getReturnData() {
		return returnData;
	}

	public void setReturnData(Object returnData) {
		this.returnData = returnData;
	}

}
