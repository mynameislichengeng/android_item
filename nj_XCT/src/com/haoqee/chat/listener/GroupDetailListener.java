package com.haoqee.chat.listener;

import java.security.acl.Group;

public interface GroupDetailListener extends BaseListener{
	
	/**
	 * 群消息回调函数
	 * @param group			群对象
	 */
	public void doComplete(Group group);
}
