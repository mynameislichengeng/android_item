package com.haoqee.chatsdk.entity;

import org.json.JSONException;
import org.json.JSONObject;

import com.haoqee.chatsdk.entity.TCSNSMessage;

/**
 * 
 * 功能： 系统通知信息 <br />
 */
public class TCNotifyVo extends TCSNSMessage{
	
	/** 拒绝 */
	public static final int STATE_REFUSED = 2;								
	/** 同意 */	
	public static final int STATE_ADDED = 1;
	/** 消息未完成的状态 */
	public static final int STATE_NO_FINISH = 0;
	/** 消息完成状态 */
	public static final int STATE_FINISH = 5;
	private static final long serialVersionUID = -5731925495114017054L;
	
	/** 通知类型 */
	private int type;	
	/** 通知文本内容 */
	private String content;
	/** 通知时间 */
	private long time;
	/** 通知处理状态 */
	private int processed = STATE_NO_FINISH;
	/** 通知相关用户 */
	private TCUser mUser;
	/** 通知用户ID */
	private String mUserID = "";
	private TCGroup mRoom;
	private String mRoomID = "";
	/** 通知唯一标示符*/
	private String mID = "";
	/** 通知阅读状态 */
	private int mReadState = 0;
	
	public TCNotifyVo(){}
	
	public TCNotifyVo(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			if(!json.isNull("type")){
				type = json.getInt("type");
			}
			
			if(!json.isNull("content")){
				content = json.getString("content");
			}
			
			if(!json.isNull("time")){
				time = json.getLong("time");
			}
			
			if(!json.isNull("user")){
				mUser = new TCUser(json.getJSONObject("user"));
				mUserID = mUser.getUserID();
			}
			
			if(!json.isNull("room")){
				mRoom = new TCGroup(json.getJSONObject("room"));
				mRoomID = mRoom.getGroupID();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取通知处理状态
	 * @return	0--未处理		1--已处理
	 */
	public int getProcessed() {
		return processed;
	}

	/**
	 * 更新通知处理状态
	 * @param processed			0--未处理		1--已处理
	 */
	public void setProcessed(int processed) {
		this.processed = processed;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}

	/**
	 * @return the id
	 */
	public TCUser getUser() {
		return mUser;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setUser(TCUser user) {
		this.mUser = user;
	}
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setUserId(String userid){
		this.mUserID = userid;
	}
	
	public String getUserId(){
		return mUserID;
	}
	
	/**
	 * 设置通知唯一标示符
	 * @param id
	 */
	public void setNotifyID(String id){
		mID = id;
	}
	
	/**
	 * 获取通知唯一标示符
	 * @return
	 */
	public String getNotifyID(){
		return mID;
	}
	
	/**
	 * 设置通知的阅读状态
	 * @param readState				0--未读		1--已读
	 */
	public void setNotifyReadState(int readState){
		this.mReadState = readState;
	}
	
	/**
	 * 获取通知阅读状态
	 * @return		0/1			0--未读		1--已读	
	 */
	public int getNotifyReadState(){
		return mReadState;
	}
	
	/**
	 * 设置通知对应的群或讨论组的ID
	 * @param roomID
	 */
	public void setRoomID(String roomID){
		mRoomID = roomID;
	}
	
	/**
	 * 获取通知对应的群或讨论组ID
	 * @return
	 */
	public String getRoomID(){
		return mRoomID;
	}
	
	/**
	 * 设置通知对应的群或讨论组
	 * @param group
	 */
	public void setRoom(TCGroup group){
		mRoom = group;
	}
	
	/**
	 * 获取通知所对应的群或临时会话信息
	 * @return
	 */
	public TCGroup getRoom(){
		return mRoom;
	}
}
