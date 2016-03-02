package com.haoqee.chat.listener;

import java.security.acl.Group;
import java.util.List;

public interface GroupListListener extends BaseListener{

	/** 回传群列表 */
	public void doComplete(List<Group> groupList);
	
}
