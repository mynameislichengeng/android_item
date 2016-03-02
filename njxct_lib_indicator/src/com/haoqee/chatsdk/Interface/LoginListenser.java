package com.haoqee.chatsdk.Interface;




/**
 * 登录回调类
 *
 */
public interface LoginListenser {
	
	public void onSuccess();			//登录成功回调函数
	public void onFailed(String error);	//登录失败回调函数
	public void onConflict();			//账号冲突回调函数
	
}
