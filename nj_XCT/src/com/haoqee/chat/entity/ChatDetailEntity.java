package com.haoqee.chat.entity;

import java.io.Serializable;

import com.haoqee.chatsdk.entity.TCUser;

public class ChatDetailEntity implements Serializable{

	private static final long serialVersionUID = -148538571545L;

	public TCUser mUser;
	public int mType = 0;
	
	public ChatDetailEntity(){}
	
	public ChatDetailEntity(TCUser user, int type){
		this.mUser = user;
		this.mType = type;
	}
}
