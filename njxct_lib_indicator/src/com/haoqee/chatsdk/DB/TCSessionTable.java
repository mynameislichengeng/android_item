package com.haoqee.chatsdk.DB;

/**
 * 聊天会话数据表
 */

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
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCSession;

public class TCSessionTable {
	
	public static final String TABLE_NAME = "TCSessionTable";				//数据表的名称
	public static final String COLUMN_SESSION_ID = "sessionID";				//会话ID（如果是群就是群ID，如果是用户就是用户ID）
	public static final String COLUMN_LOGIN_ID = "loginId";
	public static final String COLUMN_EXTEND = "extend";					//扩展信息
	public static final String COLUMN_CHAT_TYPE = "type";					//聊天类型
	public static final String COLUMN_SESSION_CONTENT = "sessionContent";	//会话内容
	public static final String COLUMN_SESSION_TIME = "sessionTime";			//会话时间
	public static final String COLUMN_SESSION_NAME = "sessionName";			//会话名称
	public static final String COLUMN_SESSION_HEAD = "sessionHead";			//会话头像
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCSessionTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_SESSION_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_SESSION_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_SESSION_HEAD, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_EXTEND, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LOGIN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_SESSION_CONTENT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_SESSION_TIME, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_CHAT_TYPE, COLUMN_INTEGER_TYPE);
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_SESSION_ID + "," + COLUMN_CHAT_TYPE + "," + COLUMN_LOGIN_ID + ")";

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
	 * 插入会话列表
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录ID
	 * @param sessions			待插入的会话列表
	 */
	public void insert(List<TCSession> sessions) {
		List<TCSession> sessionList = new ArrayList<TCSession>();
		sessionList.addAll(sessions);
		for (TCSession session : sessionList) {
			ContentValues allPromotionInfoValues = new ContentValues();
			
			allPromotionInfoValues.put(COLUMN_SESSION_ID, session.getFromId());
			allPromotionInfoValues.put(COLUMN_EXTEND, session.getSessionExtendStr());
			allPromotionInfoValues.put(COLUMN_SESSION_CONTENT, session.getSessionContent());
			allPromotionInfoValues.put(COLUMN_SESSION_NAME, session.getSessionName());
			allPromotionInfoValues.put(COLUMN_SESSION_HEAD, session.getSessionHead());
			allPromotionInfoValues.put(COLUMN_SESSION_TIME, session.getLastMessageTime());
			allPromotionInfoValues.put(COLUMN_CHAT_TYPE, session.getChatType());
			allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
			
			try {
				mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			} catch (SQLiteConstraintException e) {
				e.printStackTrace();
			}
		}						
	}
	
	/**
	 * 插入单个会话
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录用户ID
	 * @param session			待插入的会话对象
	 * @return
	 */
	public boolean insert(TCSession session) {
		ContentValues allPromotionInfoValues = new ContentValues();
		
		allPromotionInfoValues.put(COLUMN_SESSION_ID, session.getFromId());
		allPromotionInfoValues.put(COLUMN_EXTEND, session.getSessionExtendStr());
		allPromotionInfoValues.put(COLUMN_SESSION_CONTENT, session.getSessionContent());
		allPromotionInfoValues.put(COLUMN_SESSION_NAME, session.getSessionName());
		allPromotionInfoValues.put(COLUMN_SESSION_HEAD, session.getSessionHead());
		allPromotionInfoValues.put(COLUMN_SESSION_TIME, session.getLastMessageTime());
		allPromotionInfoValues.put(COLUMN_CHAT_TYPE, session.getChatType());
		allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
		
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		return false;
	}
	
	/**
	 * 更新会话
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录用户ID
	 * @param session			待更新的session对象
	 * @param type				聊天类型（包括单聊，群聊以及讨论组）
	 * @return
	 */
	public boolean update(TCSession session, int type) {
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_EXTEND, session.getSessionExtendStr());
		allPromotionInfoValues.put(COLUMN_SESSION_NAME, session.getSessionName());
		allPromotionInfoValues.put(COLUMN_SESSION_HEAD, session.getSessionHead());
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_SESSION_ID + " = '" + session.getFromId() + "' AND " + COLUMN_CHAT_TYPE + "=" + type + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	/**
	 * 删除会话
	 * @param TCChatManager.getInstance().getLoginUid()					当前登录用户ID
	 * @param fromId				待删除的会话对象ID
	 * @param type					聊天类型（包括单聊，群聊以及讨论组）
	 * @return
	 */
	public boolean delete(String fromId, int type) {
		try {
			mDBStore.delete(TABLE_NAME, COLUMN_SESSION_ID + " = '" + fromId + "' AND " + COLUMN_CHAT_TYPE + "=" + type + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 根据会话对象ID查询会话记录
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录用户ID
	 * @param fromId			待查询的会话对象ID
	 * @param type				聊天类型（包括单聊，群聊以及讨论组）
	 * @return
	 */
	public TCSession query(String fromId, int type){
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_SESSION_ID + "='" + fromId + "' AND " + COLUMN_CHAT_TYPE + "=" + type + " AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexFromId = cursor.getColumnIndex(COLUMN_SESSION_ID);
				int indexExtend = cursor.getColumnIndex(COLUMN_EXTEND);
				int indexName = cursor.getColumnIndex(COLUMN_SESSION_NAME);
				int indexHead = cursor.getColumnIndex(COLUMN_SESSION_HEAD);
				int indexTypeId = cursor.getColumnIndex(COLUMN_CHAT_TYPE);
				int indexContent = cursor.getColumnIndex(COLUMN_SESSION_CONTENT);
				int indexTime = cursor.getColumnIndex(COLUMN_SESSION_TIME);
				
				TCSession session = new TCSession();
				session.setFromId(cursor.getString(indexFromId));
				session.setSessionExtendStr(cursor.getString(indexExtend));
				session.setSessionContent(cursor.getString(indexContent));
				session.setSessionName(cursor.getString(indexName));
				session.setSessionHead(cursor.getString(indexHead));
				session.setLastMessageTime(cursor.getLong(indexTime));
				
				HashMap<String, String> extendMap = new HashMap<String, String>();
				if(!TextUtils.isEmpty(session.getSessionExtendStr())){
					JSONObject json = new JSONObject(session.getSessionExtendStr());
					if(json != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = json.keys();
						while(keys.hasNext()){
							String key = keys.next();
							extendMap.put(key, json.getString(key));
						}
					}
				}
				
				session.setExtendMap(extendMap);
				
				session.setChatType(cursor.getInt(indexTypeId));
				
				TCMessageTable messageTable = new TCMessageTable(mDBStore);
				List<TCMessage> messageList = messageTable.query(session.getFromId(), -1, session.getChatType(), 20);
				if(messageList != null){
					session.setTCMessage(messageList.get(messageList.size() - 1));
					session.setLastMessageTime(messageList.get(messageList.size() - 1).getSendTime());
				}
				session.setUnreadCount(messageTable.queryUnreadCountByID(session.getFromId(), session.getChatType()));
				
				return session;
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
	 * 查询当前登录用户的会话列表
	 * @param TCChatManager.getInstance().getLoginUid()				当前登录用户ID
	 * @return
	 */
	public List<TCSession> query() {
		List<TCSession> allInfo = new ArrayList<TCSession>();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "' ", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexFromId = cursor.getColumnIndex(COLUMN_SESSION_ID);
				int indexExtend = cursor.getColumnIndex(COLUMN_EXTEND);
				int indexTypeId = cursor.getColumnIndex(COLUMN_CHAT_TYPE);
				int indexContent = cursor.getColumnIndex(COLUMN_SESSION_CONTENT);
				int indexTime = cursor.getColumnIndex(COLUMN_SESSION_TIME);
				int indexName = cursor.getColumnIndex(COLUMN_SESSION_NAME);
				int indexHead = cursor.getColumnIndex(COLUMN_SESSION_HEAD);
				
				do {
					TCSession session = new TCSession();
					session.setFromId(cursor.getString(indexFromId));
					session.setSessionExtendStr(cursor.getString(indexExtend));
					session.setSessionContent(cursor.getString(indexContent));
					session.setSessionName(cursor.getString(indexName));
					session.setSessionHead(cursor.getString(indexHead));
					session.setLastMessageTime(cursor.getLong(indexTime));
					
					HashMap<String, String> extendMap = new HashMap<String, String>();
					if(!TextUtils.isEmpty(session.getSessionExtendStr())){
						JSONObject json = new JSONObject(session.getSessionExtendStr());
						if(json != null){
							@SuppressWarnings("unchecked")
							Iterator<String> keys = json.keys();
							while(keys.hasNext()){
								String key = keys.next();
								extendMap.put(key, json.getString(key));
							}
						}
					}
					
					session.setExtendMap(extendMap);
					session.setChatType(cursor.getInt(indexTypeId));
					
					TCMessageTable messageTable = new TCMessageTable(mDBStore);
					List<TCMessage> messageList = messageTable.query(session.getFromId(), -1, session.getChatType(), 20);
					if(messageList != null){
						session.setTCMessage(messageList.get(messageList.size() - 1));
						session.setLastMessageTime(messageList.get(messageList.size() - 1).getSendTime());
					}
					session.setUnreadCount(messageTable.queryUnreadCountByID(session.getFromId(), session.getChatType()));
					
					allInfo.add(session);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return allInfo;
	}
	
	/**
	 * 获取当前登录用户的总的未读数
	 * @param TCChatManager.getInstance().getLoginUid()					当前登录用户ID
	 * @return
	 */
	public int querySessionCount() {
		int count = 0;
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "' ", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return 0;
				}
				
				int indexFromId = cursor.getColumnIndex(COLUMN_SESSION_ID);
				int indexTypeId = cursor.getColumnIndex(COLUMN_CHAT_TYPE);
				
				do {
					String fromid = cursor.getString(indexFromId);
					int type = cursor.getInt(indexTypeId);
					
					TCMessageTable messageTable = new TCMessageTable(mDBStore);
					int mUnreadCount = messageTable.queryUnreadCountByID(fromid, type);
					count += mUnreadCount;
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return count;
	}
}
