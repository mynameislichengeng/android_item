package com.haoqee.chatsdk.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 会话类，用户消息界面显示
 *
 */

public class TCSession implements Serializable{
	private static final long serialVersionUID = 5389219102904727377L;
	private String fromId;															// 会话来源用户ID
	private int mChatType = 0;														//聊天类型
	private TCMessage mMessageInfo;													//最新一条消息
	private String mExtendStr = "";													//会话对象扩展信息
	private HashMap<String, String> mExtendMap = new HashMap<String, String>();		//会话对象扩展信息MAP
	private int mUnreadCount = 0;													//该会话未读数
	private long lastMessageTime = 0;												//最后一条消息的发送时间
	private String mContent = "";													//用于存放临时会话刚创建时消息列表为空时，存储内容所用
	private String mSessionName = "";												//会话名称
	private String mSessionHead = "";												//会话对象
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fromId == null) ? 0 : fromId.hashCode());
		result = prime * result;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TCSession other = (TCSession) obj;
		if (fromId == null) {
			if (other.fromId != null)
				return false;
		} else if (!fromId.equals(other.fromId))
			return false;
		return true;
	}
	
	
	/**
	 * @return the fuid
	 */
	public String getFromId() {
		return fromId;
	}
	
	/**
	 * @param fuid the fromId to set
	 */
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	/**
	 * 赋值聊天类型
	 * @param chatType					聊天类型
	 */
	public void setChatType(int chatType){
		this.mChatType = chatType;
	}
	
	/**
	 * 获取会话的聊天类型
	 * @return
	 */
	public int getChatType(){
		return mChatType;
	}
	
	/**
	 * 设置最新一条消息
	 * @param message
	 */
	public void setTCMessage(TCMessage message){
		this.mMessageInfo = message;
	}
	
	public TCMessage getTCMessage(){
		return mMessageInfo;
	}
	
	/**
	 * 设置会话对象扩展JSON字符串
	 * @param extend				扩展JSON字符串
	 */
	public void setSessionExtendStr(String extend){
		this.mExtendStr = extend;
	}
	
	/**
	 * 获取会话对象的扩展JSON字符串
	 * @return
	 */
	public String getSessionExtendStr(){
		return mExtendStr;
	}
	
	/**
	 * 设置扩展信息Map
	 * @param map			扩展信息Map					
	 */
	public void setExtendMap(HashMap<String, String> map){
		this.mExtendMap = map;
	}
	
	/**
	 * 获取扩展信息Map
	 * @return
	 */
	public HashMap<String, String> getExtendMap(){
		return mExtendMap;
	}
	
	/**
	 * 赋值会话的未读数
	 * @param count					未读数
	 */
	public void setUnreadCount(int count){
		this.mUnreadCount = count;
	}
	
	/**
	 * 获取会话的未读数
	 * @return
	 */
	public int getUnreadCount(){
		return mUnreadCount;
	}
	
	/**
	 * 赋值会话的最后一条消息的时间
	 * @param time					最后一条消息时间
	 */
	public void setLastMessageTime(long time){
		this.lastMessageTime = time;
	}
	
	/**
	 * 获取会话的最后一条消息的时间
	 * @return
	 */
	public long getLastMessageTime(){
		return lastMessageTime;
	}
	
	/**
	 * 设置会话对象初始化内容
	 * @param content				初始化内容
	 */
	public void setSessionContent(String content){
		this.mContent = content;
	}
	
	/**
	 * 获取会话对象的初始化内容
	 * @return
	 */
	public String getSessionContent(){
		return mContent;
	}
	
	/**
	 * 设置会话对象名称
	 * @param name				名称
	 */
	public void setSessionName(String name){
		this.mSessionName = name;
	}
	
	/**
	 * 获取会话对象的名称
	 * @return
	 */
	public String getSessionName(){
		return mSessionName;
	}
	
	/**
	 * 设置会话对象头像
	 * @param head				头像
	 */
	public void setSessionHead(String head){
		this.mSessionHead = head;
	}
	
	/**
	 * 获取会话对象的头像
	 * @return
	 */
	public String getSessionHead(){
		return mSessionHead;
	}
}
