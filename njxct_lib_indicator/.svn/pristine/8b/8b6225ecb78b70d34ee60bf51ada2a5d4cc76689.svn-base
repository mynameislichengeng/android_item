package com.haoqee.chatsdk.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.FileMessageBody;
import com.haoqee.chatsdk.entity.ImageMessageBody;
import com.haoqee.chatsdk.entity.LocationMessageBody;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TextMessageBody;
import com.haoqee.chatsdk.entity.VoiceMessageBody;

/**
 * 聊天消息表 
 *
 */
public class TCMessageTable {
	
	public static final String TABLE_NAME = "TCMessageTable";				//数据表的名称

	public static final String COLUMN_FROM_UID = "fromId";					//发送者ID
	public static final String COLUMN_FROM_NAME = "fromName";				//发送者名称
	public static final String COLUMN_FROM_HEAD = "fromHead";				//发送者头像
	public static final String COLUMN_FROM_EXTEND = "fromExtend";			//发送者扩展JSON字符串
	public static final String COLUMN_TO_ID = "toId";						//接收者ID
	public static final String COLUMN_TO_NAME = "toName";					//接收者名称
	public static final String COLUMN_TO_HEAD = "toHead";					//接收者头像
	public static final String COLUMN_TO_EXTEND = "toExtend";				//接收者扩展JSON字符串
	public static final String COLUMN_LOGIN_ID = "loginId";					//当前登录用户ID
	public static final String COLUMN_ID = "messageID";						//消息ID
	public static final String COLUMN_TAG = "messageTag";					//消息唯一标识符
	public static final String COLUMN_CONTENT = "content";					//消息文本内容
	public static final String COLUMN_BODY_EXTEND = "bodyExtend";			//body扩展JSON字符串
	public static final String COLUMN_IMAGE_URLS = "imageUrlS";				//小图URL
	public static final String COLUMN_IMAGE_URLL = "imageUrlL";				//大图URL
	public static final String COLUMN_VOICE_URL = "voiceUrl";				//语音URL
	public static final String COLUMN_TYPE = "chattype";					//聊天类型
	public static final String COLUMN_MESSAGE_TYPE = "msgtype";				//消息类型
	public static final String COLUMN_IMAGE_WIDTH = "imageWith";			//小图宽度
	public static final String COLUMN_IMAGE_HEIGHT = "imageHeight";			//大图宽度
	public static final String COLUMN_FILENAME = "fileName";				//文件名
	public static final String COLUMN_FILE_URL = "fileUrl";					//文件URL
	public static final String COLUMN_FILE_LEN = "fileLen";					//文件大小
	public static final String COLUMN_UPLOAD_PROGRESS = "uploadProgress";	//文件上传进度
	public static final String COLUMN_SEND_TIME = "sendTime";				//消息发送时间
	public static final String COLUMN_VOICE_TIME = "voiceTime";				//语音长度
	public static final String COLUMN_READ_STATE = "readState";				//消息阅读状态
	public static final String COLUMN_SEND_STATE = "sendState";				//消息发送状态
	public static final String COLUMN_IS_READ_VOICE = "readVoiceState";		//语音阅读状态
	public static final String COLUMN_LAT = "lat";							//纬度
	public static final String COLUMN_LNG = "lng";							//经度
	public static final String COLUMN_ADDRESS = "address";					//地址
	public static final String COLUMN_MESSAGE_EXTEND = "messageExtend";		//扩展消息JSON字符串
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCMessageTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_FROM_UID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FROM_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FROM_HEAD, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FROM_EXTEND, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TO_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TO_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TO_HEAD, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TO_EXTEND, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LOGIN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TAG, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_CONTENT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_IMAGE_URLS, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_IMAGE_URLL, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_VOICE_URL, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LAT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LNG, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ADDRESS, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TYPE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_MESSAGE_TYPE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_IMAGE_WIDTH, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_IMAGE_HEIGHT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FILENAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FILE_URL, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_FILE_LEN, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_UPLOAD_PROGRESS, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_SEND_TIME, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_VOICE_TIME, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_READ_STATE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_SEND_STATE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_IS_READ_VOICE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_BODY_EXTEND, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_MESSAGE_EXTEND, COLUMN_TEXT_TYPE);
			
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_FROM_UID + "," + COLUMN_TO_ID + "," + COLUMN_LOGIN_ID + "," + COLUMN_TAG + ")";

			mSQLCreateWeiboInfoTable = TCSqlHelper.formCreateTableSqlString(TABLE_NAME, columnNameAndType, primary_key);
		}
		return mSQLCreateWeiboInfoTable;

	}

	public static String getDeleteTableSQLString() {
		if (null == mSQLDeleteWeiboInfoTable) {
			mSQLDeleteWeiboInfoTable = TCSqlHelper.formDeleteTableSqlString(TABLE_NAME);
		}  
		return mSQLDeleteWeiboInfoTable;
	}
	
	/**
	 * 插入消息列表
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录用户ID
	 * @param messages			当前待插入的消息列表
	 */
	public void insert(List<TCMessage> messages) {
		List<TCMessage> messageList = new ArrayList<TCMessage>();
		messageList.addAll(messages);
		for (TCMessage message : messageList) {
			ContentValues allPromotionInfoValues = new ContentValues();
			
			allPromotionInfoValues.put(COLUMN_FROM_UID, message.getFromId());
			allPromotionInfoValues.put(COLUMN_FROM_NAME, message.getFromName());
			allPromotionInfoValues.put(COLUMN_FROM_HEAD, message.getFromHead());
			allPromotionInfoValues.put(COLUMN_FROM_EXTEND, message.getFromExtendStr());
			allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
			allPromotionInfoValues.put(COLUMN_TO_ID, message.getToId());
			allPromotionInfoValues.put(COLUMN_TO_NAME, message.getToName());
			allPromotionInfoValues.put(COLUMN_TO_HEAD, message.getToHead());
			allPromotionInfoValues.put(COLUMN_TO_EXTEND, message.getToExtendStr());
			allPromotionInfoValues.put(COLUMN_ID, message.getMessageID());
			allPromotionInfoValues.put(COLUMN_TAG, message.getMessageTag());
			
			if(message.getMessageType() == TCMessageType.TEXT){
				if(message.getTextMessageBody() != null){
					allPromotionInfoValues.put(COLUMN_CONTENT, message.getTextMessageBody().getContent());
					allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getTextMessageBody().getExtendStr());
				}
			}else if(message.getMessageType() == TCMessageType.PICTURE){
				if(message.getImageMessageBody() != null){
					allPromotionInfoValues.put(COLUMN_IMAGE_URLS, message.getImageMessageBody().getImageUrlS());
					allPromotionInfoValues.put(COLUMN_IMAGE_URLL, message.getImageMessageBody().getImageUrlL());
					allPromotionInfoValues.put(COLUMN_IMAGE_WIDTH, message.getImageMessageBody().getImageSWidth());
					allPromotionInfoValues.put(COLUMN_IMAGE_HEIGHT, message.getImageMessageBody().getImageSHeight());
					allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getImageMessageBody().getExtendStr());
				}
			}else if(message.getMessageType() == TCMessageType.VOICE){
				if(message.getVoiceMessageBody() != null){
					allPromotionInfoValues.put(COLUMN_VOICE_URL, message.getVoiceMessageBody().getVoiceUrl());
					allPromotionInfoValues.put(COLUMN_VOICE_TIME, message.getVoiceMessageBody().getVoiceTime());
					allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getVoiceMessageBody().getExtendStr());
				}
			}else if(message.getMessageType() == TCMessageType.MAP){
				if(message.getLocationMessageBody() != null){
					allPromotionInfoValues.put(COLUMN_LAT, String.valueOf(message.getLocationMessageBody().getLocaitonLat()));
					allPromotionInfoValues.put(COLUMN_LNG, String.valueOf(message.getLocationMessageBody().getLocaitonLng()));
					allPromotionInfoValues.put(COLUMN_ADDRESS, message.getLocationMessageBody().getLocationAddr());
					allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getLocationMessageBody().getExtendStr());
				}
				
			}else if(message.getMessageType() == TCMessageType.FILE){
				if(message.getFileMessageBody() != null){
					allPromotionInfoValues.put(COLUMN_FILENAME, message.getFileMessageBody().getFileName());
					allPromotionInfoValues.put(COLUMN_FILE_URL, message.getFileMessageBody().getFileUrl());
					allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getFileMessageBody().getExtendStr());
					allPromotionInfoValues.put(COLUMN_FILE_LEN, message.getFileMessageBody().getFileLen());
					allPromotionInfoValues.put(COLUMN_UPLOAD_PROGRESS, message.getFileMessageBody().getUploadProgress());
				}
			}
			
			allPromotionInfoValues.put(COLUMN_MESSAGE_EXTEND, message.getMessageExtendStr());
			
			allPromotionInfoValues.put(COLUMN_TYPE, message.getChatType());
			allPromotionInfoValues.put(COLUMN_MESSAGE_TYPE, message.getMessageType());
			
			allPromotionInfoValues.put(COLUMN_SEND_TIME, message.getSendTime());
			allPromotionInfoValues.put(COLUMN_READ_STATE, message.getReadState());
			allPromotionInfoValues.put(COLUMN_SEND_STATE, message.getSendState());
			allPromotionInfoValues.put(COLUMN_IS_READ_VOICE, message.getVoiceReadState());
			
			
			try {
				mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			} catch (SQLiteConstraintException e) {
				e.printStackTrace();
			}
		}						
	}
	
	/**
	 * 插入一条消息对象
	 * @param message				当前待插入的消息对象
	 */
	public void insert(TCMessage message) {
		
		Cursor cursor = null;
		
		ContentValues allPromotionInfoValues = new ContentValues();
		
		allPromotionInfoValues.put(COLUMN_FROM_UID, message.getFromId());
		allPromotionInfoValues.put(COLUMN_FROM_NAME, message.getFromName());
		allPromotionInfoValues.put(COLUMN_FROM_HEAD, message.getFromHead());
		allPromotionInfoValues.put(COLUMN_FROM_EXTEND, message.getFromExtendStr());
		allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
		allPromotionInfoValues.put(COLUMN_TO_ID, message.getToId());
		allPromotionInfoValues.put(COLUMN_TO_NAME, message.getToName());
		allPromotionInfoValues.put(COLUMN_TO_HEAD, message.getToHead());
		allPromotionInfoValues.put(COLUMN_TO_EXTEND, message.getToExtendStr());
		allPromotionInfoValues.put(COLUMN_ID, message.getMessageID());
		allPromotionInfoValues.put(COLUMN_TAG, message.getMessageTag());
		
		if(message.getMessageType() == TCMessageType.TEXT){
			if(message.getTextMessageBody() != null){
				allPromotionInfoValues.put(COLUMN_CONTENT, message.getTextMessageBody().getContent());
				allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getTextMessageBody().getExtendStr());
			}
		}else if(message.getMessageType() == TCMessageType.PICTURE){
			if(message.getImageMessageBody() != null){
				allPromotionInfoValues.put(COLUMN_IMAGE_URLS, message.getImageMessageBody().getImageUrlS());
				allPromotionInfoValues.put(COLUMN_IMAGE_URLL, message.getImageMessageBody().getImageUrlL());
				allPromotionInfoValues.put(COLUMN_IMAGE_WIDTH, message.getImageMessageBody().getImageSWidth());
				allPromotionInfoValues.put(COLUMN_IMAGE_HEIGHT, message.getImageMessageBody().getImageSHeight());
				allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getImageMessageBody().getExtendStr());
			}
		}else if(message.getMessageType() == TCMessageType.VOICE){
			if(message.getVoiceMessageBody() != null){
				allPromotionInfoValues.put(COLUMN_VOICE_URL, message.getVoiceMessageBody().getVoiceUrl());
				allPromotionInfoValues.put(COLUMN_VOICE_TIME, message.getVoiceMessageBody().getVoiceTime());
				allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getVoiceMessageBody().getExtendStr());
			}
		}else if(message.getMessageType() == TCMessageType.MAP){
			if(message.getLocationMessageBody() != null){
				allPromotionInfoValues.put(COLUMN_LAT, String.valueOf(message.getLocationMessageBody().getLocaitonLat()));
				allPromotionInfoValues.put(COLUMN_LNG, String.valueOf(message.getLocationMessageBody().getLocaitonLng()));
				allPromotionInfoValues.put(COLUMN_ADDRESS, message.getLocationMessageBody().getLocationAddr());
				allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getLocationMessageBody().getExtendStr());
			}
			
		}else if(message.getMessageType() == TCMessageType.FILE){
			if(message.getFileMessageBody() != null){
				allPromotionInfoValues.put(COLUMN_FILENAME, message.getFileMessageBody().getFileName());
				allPromotionInfoValues.put(COLUMN_FILE_URL, message.getFileMessageBody().getFileUrl());
				allPromotionInfoValues.put(COLUMN_BODY_EXTEND, message.getFileMessageBody().getExtendStr());
				allPromotionInfoValues.put(COLUMN_FILE_LEN, message.getFileMessageBody().getFileLen());
				allPromotionInfoValues.put(COLUMN_UPLOAD_PROGRESS, message.getFileMessageBody().getUploadProgress());
			}
		}
		
		allPromotionInfoValues.put(COLUMN_MESSAGE_EXTEND, message.getMessageExtendStr());
		
		allPromotionInfoValues.put(COLUMN_TYPE, message.getChatType());
		allPromotionInfoValues.put(COLUMN_MESSAGE_TYPE, message.getMessageType());
		
		allPromotionInfoValues.put(COLUMN_SEND_TIME, message.getSendTime());
		allPromotionInfoValues.put(COLUMN_READ_STATE, message.getReadState());
		allPromotionInfoValues.put(COLUMN_SEND_STATE, message.getSendState());
		allPromotionInfoValues.put(COLUMN_IS_READ_VOICE, message.getVoiceReadState());
		
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}	
		
	}
	
	/**
	 * 删除一条消息
	 * @param tag			消息唯一标识符
	 */
	public void delete(String tag) {
		mDBStore.delete(TABLE_NAME, COLUMN_TAG + "='" + tag + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
	}
	
	/**
	 * 删除某个对象的消息记录
	 * @param toId					待删除对象ID(用户ID，群ID或者是讨论组ID)
	 * @param type					聊天类型(单聊，群聊或者是讨论组)
	 * @return
	 */
	public boolean delete(String toId, int type) {
		try {
			
			if(type == ChatType.SINGLE_CHAT){
				mDBStore.delete(TABLE_NAME, (COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "'") + " AND "  + COLUMN_TYPE + "=" + type + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			}else {
				mDBStore.delete(TABLE_NAME, COLUMN_TO_ID + "='" + toId + "'" + " AND " + COLUMN_TYPE + "=" + type + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 更新具体消息对象的值
	 * @param message			当前待更新消息对象
	 * @return
	 */
	public boolean updateMessage(TCMessage message){
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_ID, message.getMessageID());
		if(message.getImageMessageBody() != null){
			allPromotionInfoValues.put(COLUMN_IMAGE_URLS, message.getImageMessageBody().getImageUrlS());
			allPromotionInfoValues.put(COLUMN_IMAGE_URLL, message.getImageMessageBody().getImageUrlL());
			allPromotionInfoValues.put(COLUMN_IMAGE_WIDTH, message.getImageMessageBody().getImageSWidth());
			allPromotionInfoValues.put(COLUMN_IMAGE_HEIGHT, message.getImageMessageBody().getImageSHeight());
		}
		
		if(message.getVoiceMessageBody() != null){
			allPromotionInfoValues.put(COLUMN_VOICE_URL, message.getVoiceMessageBody().getVoiceUrl());
			allPromotionInfoValues.put(COLUMN_VOICE_TIME, message.getVoiceMessageBody().getVoiceTime());
		}
		allPromotionInfoValues.put(COLUMN_SEND_TIME, message.getSendTime());
		allPromotionInfoValues.put(COLUMN_SEND_STATE, message.getSendState());
		allPromotionInfoValues.put(COLUMN_READ_STATE, message.getReadState());
		allPromotionInfoValues.put(COLUMN_IS_READ_VOICE, message.getVoiceReadState());
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_TAG + "='" + message.getMessageTag() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}	
		
		return false;
	}
	
	/**
	 * 更新某条消息的发送状态，阅读状态，语音阅读状态
	 * @param message			待更新的消息对象
	 * @return 
	 */
	public boolean update(TCMessage message){
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_SEND_STATE, message.getSendState());
		allPromotionInfoValues.put(COLUMN_READ_STATE, message.getReadState());
		allPromotionInfoValues.put(COLUMN_IS_READ_VOICE, message.getVoiceReadState());
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_TAG + "='" + message.getMessageTag() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 更新文件的发送进度
	 * @param tag					消息唯一标示符
	 * @param progress				文件发送进度
	 * @return
	 */
	public boolean updateUploadProgress(String tag, int progress){
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_UPLOAD_PROGRESS, progress);
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_TAG + "='" + tag + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}	
		
		return false;
	}
	
	public boolean updateVoiceContent(String tag, String content){
		
		try {
			String sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_CONTENT + "='" + content + "' WHERE " + COLUMN_TAG + "='" + tag + "' AND " + COLUMN_TYPE + "=100 AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			mDBStore.execSQL(sql);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 将于某个具体对象的消息阅读状态重置为已读
	 * @param fromID				对象ID
	 * @param chatType				聊天类型
	 */
	public boolean updateReadState(String fromID, int chatType){
		try {
			String sql = "";
			if(chatType == ChatType.SINGLE_CHAT){
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_READ_STATE + "=1 WHERE " + (COLUMN_FROM_UID + "='" + fromID + "' OR " + COLUMN_TO_ID + "='" + fromID) + "' AND "  + COLUMN_TYPE + "=" + chatType + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}else {
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_READ_STATE + "=1 WHERE " + COLUMN_TO_ID + "='" + fromID + "' AND " + COLUMN_TYPE + "=" + chatType + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}
			mDBStore.execSQL(sql);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 更新消息阅读状态
	 * @param message			当前待更新的消息
	 * @return
	 */
	public boolean updateMessageReadState(TCMessage message){
		
		try {
			String sql = "";
			if(message.getChatType() == ChatType.SINGLE_CHAT){
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_READ_STATE + "=" + message.getReadState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND "  + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}else {
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_READ_STATE + "=" + message.getReadState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND " + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}
			mDBStore.execSQL(sql);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 更新消息的发送状态
	 * @param message		待更新的消息
	 * @return
	 */
	public boolean updateMessageSendState(TCMessage message){
		
		try {
			String sql = "";
			if(message.getChatType() == ChatType.SINGLE_CHAT){
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_SEND_STATE + "=" + message.getSendState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND "  + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}else {
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_SEND_STATE + "=" + message.getSendState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND " + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}
			mDBStore.execSQL(sql);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	public boolean updateVoiceReadState(TCMessage message){
		
		try {
			String sql = "";
			if(message.getChatType() == ChatType.SINGLE_CHAT){
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_IS_READ_VOICE + "=" + message.getVoiceReadState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND "  + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}else {
				sql = "UPDATE " + TABLE_NAME + " SET " + COLUMN_IS_READ_VOICE + "=" + message.getVoiceReadState() + " WHERE " + COLUMN_TAG + "='" + message.getMessageTag() + "' AND " + COLUMN_TYPE + "=" + message.getChatType() + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			}
			mDBStore.execSQL(sql);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 根据唯一标识符tag获取某条消息
	 * @param tag		消息唯一标识符
	 * @return	消息对象
	 */
	public TCMessage query(String tag){
		TCMessage message = new TCMessage();
		Cursor cursor = null;
		try{
			String querySql = "";
			querySql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_TAG + "='" + tag + "'";
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				int indexUploadProgress = cursor.getColumnIndex(COLUMN_UPLOAD_PROGRESS);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				
				message.setFromId(cursor.getString(indexFromId));
				message.setFromExtendStr(cursor.getString(indexFromExtend));
				HashMap<String, String> fromExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getFromExtendStr())){
					JSONObject json = new JSONObject(message.getFromExtendStr());
					if(json != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = json.keys();
						while(keys.hasNext()){
							String key = keys.next();
							fromExtendMap.put(key, json.getString(key));
						}
					}
				}
				message.setFromExtendMap(fromExtendMap);
				
				message.setToId(cursor.getString(indexToID));
				message.setToExtendStr(cursor.getString(indexToExtend));
				
				HashMap<String, String> toExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getToExtendStr())){
					JSONObject json = new JSONObject(message.getToExtendStr());
					if(json != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = json.keys();
						while(keys.hasNext()){
							String key = keys.next();
							toExtendMap.put(key, json.getString(key));
						}
					}
				}
				message.setToExtendMap(toExtendMap);
				
				message.setMessageID(cursor.getString(indexMessageId));
				message.setMessageTag(cursor.getString(indexMessageTag));
				message.setChatType(cursor.getInt(indexType));
				message.setMessageType(cursor.getInt(indexMessageType));
				message.setSendTime(cursor.getLong(indexSendTime));
				message.setReadState(cursor.getInt(indexReadState));
				message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
				message.setSendState(cursor.getInt(indexSendState));
				message.setFromName(cursor.getString(indexFromName));
				message.setFromHead(cursor.getString(indexFromHead));
				message.setToName(cursor.getString(indexToName));
				message.setToHead(cursor.getString(indexToHead));


				String bodyExtend = cursor.getString(indexBobyExtend);
				HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(bodyExtend)){
					JSONObject bodyExtendJson = new JSONObject(bodyExtend);
					if(bodyExtendJson != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = bodyExtendJson.keys();
						while(keys.hasNext()){
							String key = keys.next();
							bodyExtendMap.put(key, bodyExtendJson.getString(key));
						}
					}
				}
				
				if(message.getMessageType() == TCMessageType.TEXT){
					TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
					textMessageBody.setExtendMap(bodyExtendMap);
					message.setTextMessageBody(textMessageBody);
				}else if(message.getMessageType() == TCMessageType.VOICE){
					VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
					voiceMessageBody.setExtendMap(bodyExtendMap);
					message.setVoiceMessageBody(voiceMessageBody);
				}else if(message.getMessageType() == TCMessageType.PICTURE){
					ImageMessageBody imageMessageBody = new ImageMessageBody();
					imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
					imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
					imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
					imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
					imageMessageBody.setExtendMap(bodyExtendMap);
					message.setImageMessageBody(imageMessageBody);
				}else if(message.getMessageType() == TCMessageType.MAP){
					LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
									, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
					locationMessageBody.setExtendMap(bodyExtendMap);
					message.setLocationMessageBody(locationMessageBody);
				}else if(message.getMessageType() == TCMessageType.FILE){
					FileMessageBody fileMessageBody = new FileMessageBody();
					fileMessageBody.setFileName(cursor.getString(indexFileName));
					fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
					fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
					fileMessageBody.setUploadProgress(cursor.getInt(indexUploadProgress));
					fileMessageBody.setExtendMap(bodyExtendMap);
					message.setFileMessageBody(fileMessageBody);
				}
				
				message.setMessageExtendStr(cursor.getString(indexMessageExtend));
				HashMap<String, String> messageExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getMessageExtendStr())){
					JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
					if(messageExtendJson != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = messageExtendJson.keys();
						while(keys.hasNext()){
							String key = keys.next();
							messageExtendMap.put(key, messageExtendJson.getString(key));
						}
					}
				}
				
				message.setMsgExtendMap(messageExtendMap);
				
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
		
	}
	
	/**
	 * 根据消息ID查询消息
	 * @param id				消息ID
	 * @return	消息对象
	 */
	public TCMessage queryByID(String id){
		TCMessage message = new TCMessage();
		Cursor cursor = null;
		try{
			String querySql = "";
			querySql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "='" + id + "'";
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				int indexUploadProgress = cursor.getColumnIndex(COLUMN_UPLOAD_PROGRESS);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				
				message.setFromId(cursor.getString(indexFromId));
				message.setFromExtendStr(cursor.getString(indexFromExtend));
				HashMap<String, String> fromExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getFromExtendStr())){
					JSONObject json = new JSONObject(message.getFromExtendStr());
					if(json != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = json.keys();
						while(keys.hasNext()){
							String key = keys.next();
							fromExtendMap.put(key, json.getString(key));
						}
					}
				}
				message.setFromExtendMap(fromExtendMap);
				
				message.setToId(cursor.getString(indexToID));
				message.setToExtendStr(cursor.getString(indexToExtend));
				
				HashMap<String, String> toExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getToExtendStr())){
					JSONObject json = new JSONObject(message.getToExtendStr());
					if(json != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = json.keys();
						while(keys.hasNext()){
							String key = keys.next();
							toExtendMap.put(key, json.getString(key));
						}
					}
				}
				message.setToExtendMap(toExtendMap);
				
				message.setMessageID(cursor.getString(indexMessageId));
				message.setMessageTag(cursor.getString(indexMessageTag));
				message.setChatType(cursor.getInt(indexType));
				message.setMessageType(cursor.getInt(indexMessageType));
				message.setSendTime(cursor.getLong(indexSendTime));
				message.setReadState(cursor.getInt(indexReadState));
				message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
				message.setSendState(cursor.getInt(indexSendState));
				message.setFromName(cursor.getString(indexFromName));
				message.setFromHead(cursor.getString(indexFromHead));
				message.setToName(cursor.getString(indexToName));
				message.setToHead(cursor.getString(indexToHead));


				String bodyExtend = cursor.getString(indexBobyExtend);
				HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(bodyExtend)){
					JSONObject bodyExtendJson = new JSONObject(bodyExtend);
					if(bodyExtendJson != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = bodyExtendJson.keys();
						while(keys.hasNext()){
							String key = keys.next();
							bodyExtendMap.put(key, bodyExtendJson.getString(key));
						}
					}
				}
				
				if(message.getMessageType() == TCMessageType.TEXT){
					TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
					textMessageBody.setExtendMap(bodyExtendMap);
					message.setTextMessageBody(textMessageBody);
				}else if(message.getMessageType() == TCMessageType.VOICE){
					VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
					voiceMessageBody.setExtendMap(bodyExtendMap);
					message.setVoiceMessageBody(voiceMessageBody);
				}else if(message.getMessageType() == TCMessageType.PICTURE){
					ImageMessageBody imageMessageBody = new ImageMessageBody();
					imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
					imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
					imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
					imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
					imageMessageBody.setExtendMap(bodyExtendMap);
					message.setImageMessageBody(imageMessageBody);
				}else if(message.getMessageType() == TCMessageType.MAP){
					LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
									, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
					locationMessageBody.setExtendMap(bodyExtendMap);
					message.setLocationMessageBody(locationMessageBody);
				}else if(message.getMessageType() == TCMessageType.FILE){
					FileMessageBody fileMessageBody = new FileMessageBody();
					fileMessageBody.setFileName(cursor.getString(indexFileName));
					fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
					fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
					fileMessageBody.setUploadProgress(cursor.getInt(indexUploadProgress));
					fileMessageBody.setExtendMap(bodyExtendMap);
					message.setFileMessageBody(fileMessageBody);
				}
				
				message.setMessageExtendStr(cursor.getString(indexMessageExtend));
				HashMap<String, String> messageExtendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(message.getMessageExtendStr())){
					JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
					if(messageExtendJson != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = messageExtendJson.keys();
						while(keys.hasNext()){
							String key = keys.next();
							messageExtendMap.put(key, messageExtendJson.getString(key));
						}
					}
				}
				
				message.setMsgExtendMap(messageExtendMap);
				
				return message;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
		
	}
	
	/**
	 * 获取当前对象某个具体对象的消息列表
	 * @param toId					对象ID（用户，讨论组或者群）
	 * @param autoID				获取消息的位置（传入-1则获取全部，传入其他则获取该ID之后的数据）
	 * @param type					聊天类型（单聊，群聊或者讨论组）
	 * @param loadSize				加载的条数
	 * @return	消息列表
	 */
	public List<TCMessage> query(String toId, int autoID, int type, int loadSize){
		List<TCMessage> allInfo = new ArrayList<TCMessage>();
		Cursor cursor = null;
		try {
			
			String querySql = "";
			
			if(type == ChatType.SINGLE_CHAT){
				if(autoID == -1){
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}else {
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + " rowid<" + autoID  + " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}
				
			}else {
				if(autoID == -1){
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}else {
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + " rowid<" + autoID  + " AND " + COLUMN_TYPE + "=" + type + " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}
				
			}
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexRowId = cursor.getColumnIndex("rowid");
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				int indexUploadProgress = cursor.getColumnIndex(COLUMN_UPLOAD_PROGRESS);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				do{
					TCMessage message = new TCMessage();
					message.setFromId(cursor.getString(indexFromId));
					message.setFromExtendStr(cursor.getString(indexFromExtend));
					HashMap<String, String> fromExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getFromExtendStr())){
						JSONObject json = new JSONObject(message.getFromExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								fromExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setFromExtendMap(fromExtendMap);
					
					message.setToId(cursor.getString(indexToID));
					message.setToExtendStr(cursor.getString(indexToExtend));
					
					HashMap<String, String> toExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getToExtendStr())){
						JSONObject json = new JSONObject(message.getToExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								toExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setToExtendMap(toExtendMap);
					
					message.setMessageID(cursor.getString(indexMessageId));
					message.setMessageTag(cursor.getString(indexMessageTag));
					message.setChatType(cursor.getInt(indexType));
					message.setMessageType(cursor.getInt(indexMessageType));
					message.setSendTime(cursor.getLong(indexSendTime));
					message.setReadState(cursor.getInt(indexReadState));
					message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
					message.setSendState(cursor.getInt(indexSendState));
					message.setFromName(cursor.getString(indexFromName));
					message.setFromHead(cursor.getString(indexFromHead));
					message.setToName(cursor.getString(indexToName));
					message.setToHead(cursor.getString(indexToHead));


					String bodyExtend = cursor.getString(indexBobyExtend);
					HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(bodyExtend)){
						JSONObject bodyExtendJson = new JSONObject(bodyExtend);
						if(bodyExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = bodyExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								bodyExtendMap.put(key, bodyExtendJson.getString(key));
							}
						}
					}
					
					if(message.getMessageType() == TCMessageType.TEXT){
						TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
						textMessageBody.setExtendMap(bodyExtendMap);
						message.setTextMessageBody(textMessageBody);
					}else if(message.getMessageType() == TCMessageType.VOICE){
						VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
						voiceMessageBody.setExtendMap(bodyExtendMap);
						message.setVoiceMessageBody(voiceMessageBody);
					}else if(message.getMessageType() == TCMessageType.PICTURE){
						ImageMessageBody imageMessageBody = new ImageMessageBody();
						imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
						imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
						imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
						imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
						imageMessageBody.setExtendMap(bodyExtendMap);
						message.setImageMessageBody(imageMessageBody);
					}else if(message.getMessageType() == TCMessageType.MAP){
						LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
										, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
						locationMessageBody.setExtendMap(bodyExtendMap);
						message.setLocationMessageBody(locationMessageBody);
					}else if(message.getMessageType() == TCMessageType.FILE){
						FileMessageBody fileMessageBody = new FileMessageBody();
						fileMessageBody.setFileName(cursor.getString(indexFileName));
						fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
						fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
						fileMessageBody.setUploadProgress(cursor.getInt(indexUploadProgress));
						fileMessageBody.setExtendMap(bodyExtendMap);
						message.setFileMessageBody(fileMessageBody);
					}
					
					message.setMessageExtendStr(cursor.getString(indexMessageExtend));
					HashMap<String, String> messageExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getMessageExtendStr())){
						JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
						if(messageExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = messageExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								messageExtendMap.put(key, messageExtendJson.getString(key));
							}
						}
					}
					
					message.setMsgExtendMap(messageExtendMap);
					
					message.setAutoID(cursor.getInt(indexRowId));
					allInfo.add(0, message);
				} while (cursor.moveToNext());
				
				return allInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * 加载上一页数据
	 * @param toId
	 * @param autoID
	 * @param type
	 * @param loadSize
	 * @return
	 */
	public List<TCMessage> queryPreviousPageMessageList(String toId, int autoID, int type, int loadSize){
		List<TCMessage> allInfo = new ArrayList<TCMessage>();
		Cursor cursor = null;
		try {
			
			String querySql = "";
			
			if(type == ChatType.SINGLE_CHAT){
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
						+ " AND " + " rowid>" + autoID  + " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " ASC LIMIT 0," + loadSize;
				
			}else {
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
						+ " AND " + " rowid>" + autoID  + " AND " + COLUMN_TYPE + "=" + type + " ORDER BY rowid" + " ASC LIMIT 0," + loadSize;
			}
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexRowId = cursor.getColumnIndex("rowid");
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				int indexUploadProgress = cursor.getColumnIndex(COLUMN_UPLOAD_PROGRESS);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				do{
					TCMessage message = new TCMessage();
					message.setFromId(cursor.getString(indexFromId));
					message.setFromExtendStr(cursor.getString(indexFromExtend));
					HashMap<String, String> fromExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getFromExtendStr())){
						JSONObject json = new JSONObject(message.getFromExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								fromExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setFromExtendMap(fromExtendMap);
					
					message.setToId(cursor.getString(indexToID));
					message.setToExtendStr(cursor.getString(indexToExtend));
					
					HashMap<String, String> toExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getToExtendStr())){
						JSONObject json = new JSONObject(message.getToExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								toExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setToExtendMap(toExtendMap);
					
					message.setMessageID(cursor.getString(indexMessageId));
					message.setMessageTag(cursor.getString(indexMessageTag));
					message.setChatType(cursor.getInt(indexType));
					message.setMessageType(cursor.getInt(indexMessageType));
					message.setSendTime(cursor.getLong(indexSendTime));
					message.setReadState(cursor.getInt(indexReadState));
					message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
					message.setSendState(cursor.getInt(indexSendState));
					message.setFromName(cursor.getString(indexFromName));
					message.setFromHead(cursor.getString(indexFromHead));
					message.setToName(cursor.getString(indexToName));
					message.setToHead(cursor.getString(indexToHead));


					String bodyExtend = cursor.getString(indexBobyExtend);
					HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(bodyExtend)){
						JSONObject bodyExtendJson = new JSONObject(bodyExtend);
						if(bodyExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = bodyExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								bodyExtendMap.put(key, bodyExtendJson.getString(key));
							}
						}
					}
					
					if(message.getMessageType() == TCMessageType.TEXT){
						TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
						textMessageBody.setExtendMap(bodyExtendMap);
						message.setTextMessageBody(textMessageBody);
					}else if(message.getMessageType() == TCMessageType.VOICE){
						VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
						voiceMessageBody.setExtendMap(bodyExtendMap);
						message.setVoiceMessageBody(voiceMessageBody);
					}else if(message.getMessageType() == TCMessageType.PICTURE){
						ImageMessageBody imageMessageBody = new ImageMessageBody();
						imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
						imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
						imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
						imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
						imageMessageBody.setExtendMap(bodyExtendMap);
						message.setImageMessageBody(imageMessageBody);
					}else if(message.getMessageType() == TCMessageType.MAP){
						LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
										, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
						locationMessageBody.setExtendMap(bodyExtendMap);
						message.setLocationMessageBody(locationMessageBody);
					}else if(message.getMessageType() == TCMessageType.FILE){
						FileMessageBody fileMessageBody = new FileMessageBody();
						fileMessageBody.setFileName(cursor.getString(indexFileName));
						fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
						fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
						fileMessageBody.setUploadProgress(cursor.getInt(indexUploadProgress));
						fileMessageBody.setExtendMap(bodyExtendMap);
						message.setFileMessageBody(fileMessageBody);
					}
					
					message.setMessageExtendStr(cursor.getString(indexMessageExtend));
					HashMap<String, String> messageExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getMessageExtendStr())){
						JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
						if(messageExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = messageExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								messageExtendMap.put(key, messageExtendJson.getString(key));
							}
						}
					}
					
					message.setMsgExtendMap(messageExtendMap);
					
					message.setAutoID(cursor.getInt(indexRowId));
					allInfo.add(message);
				} while (cursor.moveToNext());
				
				return allInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * 获取指定页码的消息类表
	 * @param toId					对象ID（用户，讨论组或者群）
	 * @param type					聊天类型（单聊，群聊或者讨论组）
	 * @param page					指定页码
	 * @param loadSize				加载条数
	 * @return
	 */
	public List<TCMessage> queryMessageListByPage(String toId, int type, int page, int loadSize){
		List<TCMessage> allInfo = new ArrayList<TCMessage>();
		Cursor cursor = null;
		try {
			
			String querySql = "";
			
			int initCount = 0;
			
			if(page > 0){
				initCount = (page - 1) * loadSize;
			}
			
			int endCount = initCount + loadSize;
			
			if(type == ChatType.SINGLE_CHAT){
				
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
						+ " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT " + initCount + "," + endCount;
			}else {
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
						+ " AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT " + initCount + "," + endCount;
			}
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexRowId = cursor.getColumnIndex("rowid");
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				do{
					TCMessage message = new TCMessage();
					message.setFromId(cursor.getString(indexFromId));
					message.setFromExtendStr(cursor.getString(indexFromExtend));
					HashMap<String, String> fromExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getFromExtendStr())){
						JSONObject json = new JSONObject(message.getFromExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								fromExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setFromExtendMap(fromExtendMap);
					
					message.setToId(cursor.getString(indexToID));
					message.setToExtendStr(cursor.getString(indexToExtend));
					
					HashMap<String, String> toExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getToExtendStr())){
						JSONObject json = new JSONObject(message.getToExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								toExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setToExtendMap(toExtendMap);
					
					message.setMessageID(cursor.getString(indexMessageId));
					message.setMessageTag(cursor.getString(indexMessageTag));
					message.setChatType(cursor.getInt(indexType));
					message.setMessageType(cursor.getInt(indexMessageType));
					message.setSendTime(cursor.getLong(indexSendTime));
					message.setReadState(cursor.getInt(indexReadState));
					message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
					message.setSendState(cursor.getInt(indexSendState));
					message.setFromName(cursor.getString(indexFromName));
					message.setFromHead(cursor.getString(indexFromHead));
					message.setToName(cursor.getString(indexToName));
					message.setToHead(cursor.getString(indexToHead));


					String bodyExtend = cursor.getString(indexBobyExtend);
					HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(bodyExtend)){
						JSONObject bodyExtendJson = new JSONObject(bodyExtend);
						if(bodyExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = bodyExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								bodyExtendMap.put(key, bodyExtendJson.getString(key));
							}
						}
					}
					
					if(message.getMessageType() == TCMessageType.TEXT){
						TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
						textMessageBody.setExtendMap(bodyExtendMap);
						message.setTextMessageBody(textMessageBody);
					}else if(message.getMessageType() == TCMessageType.VOICE){
						VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
						voiceMessageBody.setExtendMap(bodyExtendMap);
						message.setVoiceMessageBody(voiceMessageBody);
					}else if(message.getMessageType() == TCMessageType.PICTURE){
						ImageMessageBody imageMessageBody = new ImageMessageBody();
						imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
						imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
						imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
						imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
						imageMessageBody.setExtendMap(bodyExtendMap);
						message.setImageMessageBody(imageMessageBody);
					}else if(message.getMessageType() == TCMessageType.MAP){
						LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
										, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
						locationMessageBody.setExtendMap(bodyExtendMap);
						message.setLocationMessageBody(locationMessageBody);
					}else if(message.getMessageType() == TCMessageType.FILE){
						FileMessageBody fileMessageBody = new FileMessageBody();
						fileMessageBody.setFileName(cursor.getString(indexFileName));
						fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
						fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
						fileMessageBody.setExtendMap(bodyExtendMap);
						message.setFileMessageBody(fileMessageBody);
					}
					
					message.setMessageExtendStr(cursor.getString(indexMessageExtend));
					HashMap<String, String> messageExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getMessageExtendStr())){
						JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
						if(messageExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = messageExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								messageExtendMap.put(key, messageExtendJson.getString(key));
							}
						}
					}
					
					message.setMsgExtendMap(messageExtendMap);
					
					message.setAutoID(cursor.getInt(indexRowId));
					allInfo.add(0, message);
				} while (cursor.moveToNext());
				
				return allInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * 获取指定对象的指定条数的未读消息记录
	 * @param toId					对象ID（用户，讨论组或者群）
	 * @param autoID				获取消息的位置（传入-1则获取全部，传入其他则获取该ID之后的数据）
	 * @param type					聊天类型（单聊，群聊或者讨论组）
	 * @param loadSize				加载的条数
	 * @return
	 */
	public List<TCMessage> queryUnreadMessageList(String toId, int autoID, int type, int loadSize){
		List<TCMessage> allInfo = new ArrayList<TCMessage>();
		Cursor cursor = null;
		try {
			
			String querySql = "";
			
			if(type == ChatType.SINGLE_CHAT){
				if(autoID == -1){
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + COLUMN_READ_STATE + "=0 AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}else {
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE (" + COLUMN_FROM_UID + "='" + toId + "' or " + COLUMN_TO_ID + "='" + toId + "')" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + " rowid<" + autoID  + " AND " + COLUMN_READ_STATE + "=0 AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}
				
			}else {
				if(autoID == -1){
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + COLUMN_READ_STATE + "=0 AND " + COLUMN_TYPE + "=" + type +  " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}else {
					querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + toId + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
							+ " AND " + COLUMN_READ_STATE + "=0 AND " + " rowid<" + autoID  + " AND " + COLUMN_TYPE + "=" + type + " ORDER BY rowid" + " DESC LIMIT 0," + loadSize;
				}
				
			}
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexRowId = cursor.getColumnIndex("rowid");
				int indexFromId = cursor.getColumnIndex(COLUMN_FROM_UID);
				int indexFromName = cursor.getColumnIndex(COLUMN_FROM_NAME);
				int indexFromHead = cursor.getColumnIndex(COLUMN_FROM_HEAD);
				int indexFromExtend = cursor.getColumnIndex(COLUMN_FROM_EXTEND);
				int indexToID = cursor.getColumnIndex(COLUMN_TO_ID);
				int indexToName = cursor.getColumnIndex(COLUMN_TO_NAME);
				int indexToHead = cursor.getColumnIndex(COLUMN_TO_HEAD);
				int indexToExtend = cursor.getColumnIndex(COLUMN_TO_EXTEND);
				int indexMessageId = cursor.getColumnIndex(COLUMN_ID);
				int indexMessageTag = cursor.getColumnIndex(COLUMN_TAG);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexImgUrls = cursor.getColumnIndex(COLUMN_IMAGE_URLS);
				int indexImgUrlL = cursor.getColumnIndex(COLUMN_IMAGE_URLL);
				int indexVoiceUrl = cursor.getColumnIndex(COLUMN_VOICE_URL);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexMessageType = cursor.getColumnIndex(COLUMN_MESSAGE_TYPE);
				int indexImgWidth = cursor.getColumnIndex(COLUMN_IMAGE_WIDTH);
				int indexImgHeight = cursor.getColumnIndex(COLUMN_IMAGE_HEIGHT);
				int indexSendTime = cursor.getColumnIndex(COLUMN_SEND_TIME);
				
				int indexFileName = cursor.getColumnIndex(COLUMN_FILENAME);
				int indexFileUrl = cursor.getColumnIndex(COLUMN_FILE_URL);
				int indexFileLen = cursor.getColumnIndex(COLUMN_FILE_LEN);
				
				int indexVoiceTime = cursor.getColumnIndex(COLUMN_VOICE_TIME);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				int indexSendState = cursor.getColumnIndex(COLUMN_SEND_STATE);
				int indexVoiceReadState = cursor.getColumnIndex(COLUMN_IS_READ_VOICE);
				int indexLatID = cursor.getColumnIndex(COLUMN_LAT);
				int indexLngID = cursor.getColumnIndex(COLUMN_LNG);
				int indexAddressID = cursor.getColumnIndex(COLUMN_ADDRESS);
				int indexMessageExtend = cursor.getColumnIndex(COLUMN_MESSAGE_EXTEND);
				int indexBobyExtend = cursor.getColumnIndex(COLUMN_BODY_EXTEND);
				
				do{
					TCMessage message = new TCMessage();
					message.setFromId(cursor.getString(indexFromId));
					message.setFromExtendStr(cursor.getString(indexFromExtend));
					HashMap<String, String> fromExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getFromExtendStr())){
						JSONObject json = new JSONObject(message.getFromExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								fromExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setFromExtendMap(fromExtendMap);
					
					message.setToId(cursor.getString(indexToID));
					message.setToExtendStr(cursor.getString(indexToExtend));
					
					HashMap<String, String> toExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getToExtendStr())){
						JSONObject json = new JSONObject(message.getToExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								toExtendMap.put(key, json.getString(key));
							}
						}
					}
					message.setToExtendMap(toExtendMap);
					
					message.setMessageID(cursor.getString(indexMessageId));
					message.setMessageTag(cursor.getString(indexMessageTag));
					message.setChatType(cursor.getInt(indexType));
					message.setMessageType(cursor.getInt(indexMessageType));
					message.setSendTime(cursor.getLong(indexSendTime));
					message.setReadState(cursor.getInt(indexReadState));
					message.setVoiceReadState(cursor.getInt(indexVoiceReadState));
					message.setSendState(cursor.getInt(indexSendState));
					message.setFromName(cursor.getString(indexFromName));
					message.setFromHead(cursor.getString(indexFromHead));
					message.setToName(cursor.getString(indexToName));
					message.setToHead(cursor.getString(indexToHead));


					String bodyExtend = cursor.getString(indexBobyExtend);
					HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(bodyExtend)){
						JSONObject bodyExtendJson = new JSONObject(bodyExtend);
						if(bodyExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = bodyExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								bodyExtendMap.put(key, bodyExtendJson.getString(key));
							}
						}
					}
					
					if(message.getMessageType() == TCMessageType.TEXT){
						TextMessageBody textMessageBody = new TextMessageBody(cursor.getString(indexContent));
						textMessageBody.setExtendMap(bodyExtendMap);
						message.setTextMessageBody(textMessageBody);
					}else if(message.getMessageType() == TCMessageType.VOICE){
						VoiceMessageBody voiceMessageBody = new VoiceMessageBody(cursor.getString(indexVoiceUrl), cursor.getInt(indexVoiceTime));
						voiceMessageBody.setExtendMap(bodyExtendMap);
						message.setVoiceMessageBody(voiceMessageBody);
					}else if(message.getMessageType() == TCMessageType.PICTURE){
						ImageMessageBody imageMessageBody = new ImageMessageBody();
						imageMessageBody.setImageUrlS(cursor.getString(indexImgUrls));
						imageMessageBody.setImageUrlL(cursor.getString(indexImgUrlL));
						imageMessageBody.setImageSWidth(cursor.getString(indexImgWidth));
						imageMessageBody.setImageSHeight(cursor.getString(indexImgHeight));
						imageMessageBody.setExtendMap(bodyExtendMap);
						message.setImageMessageBody(imageMessageBody);
					}else if(message.getMessageType() == TCMessageType.MAP){
						LocationMessageBody locationMessageBody = new LocationMessageBody(Double.parseDouble(cursor.getString(indexLatID))
										, Double.parseDouble(cursor.getString(indexLngID)), cursor.getString(indexAddressID));
						locationMessageBody.setExtendMap(bodyExtendMap);
						message.setLocationMessageBody(locationMessageBody);
					}else if(message.getMessageType() == TCMessageType.FILE){
						FileMessageBody fileMessageBody = new FileMessageBody();
						fileMessageBody.setFileName(cursor.getString(indexFileName));
						fileMessageBody.setFileUrl(cursor.getString(indexFileUrl));
						fileMessageBody.setFileLen(cursor.getLong(indexFileLen));
						fileMessageBody.setExtendMap(bodyExtendMap);
						message.setFileMessageBody(fileMessageBody);
					}
					
					message.setMessageExtendStr(cursor.getString(indexMessageExtend));
					HashMap<String, String> messageExtendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(message.getMessageExtendStr())){
						JSONObject messageExtendJson = new JSONObject(message.getMessageExtendStr());
						if(messageExtendJson != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = messageExtendJson.keys();
							while(keys.hasNext()){
								String key = keys.next();
								messageExtendMap.put(key, messageExtendJson.getString(key));
							}
						}
					}
					
					message.setMsgExtendMap(messageExtendMap);
					
					message.setAutoID(cursor.getInt(indexRowId));
					allInfo.add(0, message);
				} while (cursor.moveToNext());
				
				return allInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}
	
	/**
	 * 获取当前登陆用户具体对象（用户，讨论组和群）的未读数
	 * @param id			对象ID
	 * @param type			聊天类型（包括单聊，群聊和讨论组）
	 * @return
	 */
	public int queryUnreadCountByID(String id, int type){
		Cursor cursor = null;
		try {
			
			String querySql = "";
			if(type == ChatType.SINGLE_CHAT){
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_FROM_UID + "='" + id 
				+ "' AND " + COLUMN_TYPE + "=" + ChatType.SINGLE_CHAT + " AND " + COLUMN_READ_STATE + "=0" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
						+ " ORDER BY " + COLUMN_SEND_TIME + " DESC ";
			}else {
				querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_TO_ID + "='" + id 
						+ "' AND " + COLUMN_TYPE + "=" + type + " AND " + COLUMN_READ_STATE + "=0" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" 
								+ " ORDER BY " + COLUMN_SEND_TIME + " DESC ";
			}
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return 0;
				}
				
				return cursor.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return 0;
	}
	
	/**
	 * 获取当前登录用户总的未读数
	 * @return 总的未读数
	 */
	public int queryUnreadCount(){
		Cursor cursor = null;
		try {
			
			String querySql = "";
			querySql = "SELECT rowid, * FROM " + TABLE_NAME + " WHERE " + COLUMN_READ_STATE + "=0" + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			
			cursor = mDBStore.rawQuery(querySql, null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return 0;
				}
				
				return cursor.getCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return 0;
	}
	
	
}
