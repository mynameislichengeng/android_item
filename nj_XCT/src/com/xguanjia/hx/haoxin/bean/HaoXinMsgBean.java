package com.xguanjia.hx.haoxin.bean;

import java.io.Serializable;

import com.xguanjia.hx.common.SlideView;


public class HaoXinMsgBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messageObjName = "";

	private String messageObj = "";

	private String messageCreateTime = "";

	private String messageId = "";

	private String messageReceiver = "";

	private String messageAppType = "";

	private String msgNum = "";

	private String messageTitle = "";

	// 头像
	private String avatar = "";

	public SlideView slideView;
	
	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageObjName() {
		return messageObjName;
	}

	public void setMessageObjName(String messageObjName) {
		this.messageObjName = messageObjName;
	}

	public String getMessageObj() {
		return messageObj;
	}

	public void setMessageObj(String messageObj) {
		this.messageObj = messageObj;
	}

	public String getMessageCreateTime() {
		return messageCreateTime;
	}

	public void setMessageCreateTime(String messageCreateTime) {
		this.messageCreateTime = messageCreateTime;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageReceiver() {
		return messageReceiver;
	}

	public void setMessageReceiver(String messageReceiver) {
		this.messageReceiver = messageReceiver;
	}

	public String getMessageAppType() {
		return messageAppType;
	}

	public void setMessageAppType(String messageAppType) {
		this.messageAppType = messageAppType;
	}

	public String getMsgNum() {
		return msgNum;
	}

	public void setMsgNum(String msgNum) {
		this.msgNum = msgNum;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
