package com.haoqee.chatsdk.entity;


public class TCMorePicture {
	
	public String key;
	public String filePath;
	public int mType = 0;
	
	public TCMorePicture(String key, String filePath) {
		super();
		this.key = key;
		this.filePath = filePath;
	}
	
	public TCMorePicture(String key, String filePath, int type) {
		super();
		this.key = key;
		this.filePath = filePath;
		this.mType = type;
	}
	
	public TCMorePicture() {
		super();
	}
	
}
