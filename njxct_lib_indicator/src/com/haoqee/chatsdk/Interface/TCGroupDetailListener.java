package com.haoqee.chatsdk.Interface;

import com.haoqee.chatsdk.entity.TCGroup;

public interface TCGroupDetailListener extends TCBaseListener{
	
	/**
	 * 群消息回调函数
	 * @param group			群对象
	 */
	public void doComplete(TCGroup group);
}
