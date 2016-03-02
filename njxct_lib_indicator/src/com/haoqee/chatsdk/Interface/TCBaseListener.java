package com.haoqee.chatsdk.Interface;

import com.haoqee.chatsdk.entity.TCError;

public interface TCBaseListener {

	public void doComplete();
	
	public void onError(TCError error);
	
	void onProgress(int progress);
	
}
