package com.haoqee.chatsdk.Interface;

import com.haoqee.chatsdk.entity.TCError;

public interface FileDownloadListener {

	public void downloadProgress(int progress);
	
	public void onError(TCError error);
	
}
