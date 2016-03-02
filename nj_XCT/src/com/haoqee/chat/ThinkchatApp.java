package com.haoqee.chat;

import android.app.Application;

import com.haoqee.chatsdk.TCChatManager;

public class ThinkchatApp extends Application {

	public static ThinkchatApp mApp;

	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;
		TCChatManager.init(this);
		// SpeechUtility.createUtility(ThinkchatApp.this,
		// "appid="+getString(R.string.app_id));
//		 SDKInitializer.initialize(this);
	}

	public static ThinkchatApp getInstance() {
		return mApp;
	}

}
