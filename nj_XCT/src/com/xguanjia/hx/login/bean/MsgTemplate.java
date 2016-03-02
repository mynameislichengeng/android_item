package com.xguanjia.hx.login.bean;

import java.io.Serializable;

public class MsgTemplate implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7099672643217560388L;

	private String id;
	
	/**
	 *  模板名
	 */
	private String templateName;
	
	/**
	 * 发送人
	 */
	private String  senderName;
	
	/**
	 * 消息内容模板
	 */
	private String msgContent;
	
	/**
	 * 消息模板类型
	 */
	private String typeId ;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
