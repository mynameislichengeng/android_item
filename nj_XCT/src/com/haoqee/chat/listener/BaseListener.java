package com.haoqee.chat.listener;

import com.haoqee.chatsdk.entity.TCError;

public interface BaseListener {

	public void doComplete();
	
	public void onError(TCError error);
	
}
