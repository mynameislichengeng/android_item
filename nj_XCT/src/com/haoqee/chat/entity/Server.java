package com.haoqee.chat.entity;

import java.io.Serializable;

public class Server implements Serializable{

	private static final long serialVersionUID = 232052614771414635L;
	
	
	public String mSdkServer = "";
	public String mLogisticServer = "";
	public String mOPHost = "";
	public String mServerName = "";
	
	public boolean mIsDefault = false;
	public boolean mIsCurrent = false;
	
	public Server(){
		
	}
	
	public Server(String serverName, String sdkServer, String logisticServer, String host) {
		this.mServerName = serverName;
		this.mSdkServer = sdkServer;
		this.mLogisticServer = logisticServer;
		this.mOPHost = host;
	}

}
