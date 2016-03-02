package com.haoqee.chatsdk.Interface;

import java.util.List;

import com.haoqee.chatsdk.entity.TCPageInfo;
import com.haoqee.chatsdk.entity.TCUser;

public interface TCMenberListListener extends TCBaseListener{

	public void doComplete(List<TCUser> userList, TCPageInfo pageInfo);
	
}
