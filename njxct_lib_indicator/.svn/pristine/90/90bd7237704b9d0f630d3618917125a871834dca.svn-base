package com.haoqee.chatsdk.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCUser;

public class TCNotifyUserTable {

	public static final String TABLE_NAME = "TCNotifyUserTable";			//数据表的名称

	public static final String COLUMN_NOTIFY_ID = "notifyID";			//通知唯一标识符
	public static final String COLUMN_UID = "uid";						//通知用户ID
	public static final String COLUMN_LOGIN_ID = "loginId";				//当前登录用户ID
	public static final String COLUMN_USER_NAME = "name";					//通知用户名称
	public static final String COLUMN_USER_HEAD = "head";					//通知用户头像
	public static final String COLUMN_EXTEND_STR = "extendStr";			//通知用户扩展属性
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCNotifyUserTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_NOTIFY_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_UID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_USER_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_USER_HEAD, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LOGIN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_EXTEND_STR, COLUMN_TEXT_TYPE);
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_NOTIFY_ID + "," + COLUMN_UID + "," + COLUMN_LOGIN_ID + ")";

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
	 * 跟新具体通知的用户对象
	 * @param notifyID					通知唯一标示符即通知ID
	 * @param user						待更新的用户对象
	 */
	public void update(String notifyID, TCUser user) {
		if(user.getUserID() == null || user.getUserID().equals("")){
			return;
		}
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_EXTEND_STR, user.getExtendStr());
		allPromotionInfoValues.put(COLUMN_USER_NAME, user.getName());
		allPromotionInfoValues.put(COLUMN_USER_HEAD, user.getHead());
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_UID + " = '" + user.getUserID() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}						
	}
	
	/**
	 * 插入某个通知对应的用户对象
	 * @param notifyID					通知唯一标示符即通知ID
	 * @param user						待插入的用户对象
	 */
	public void insert(String notifyID, TCUser user) {
		if(user.getUserID() == null || user.getUserID().equals("")){
			return;
		}
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_NOTIFY_ID, notifyID);
		allPromotionInfoValues.put(COLUMN_UID, user.getUserID());
		allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
		allPromotionInfoValues.put(COLUMN_EXTEND_STR, user.getUserID());
		allPromotionInfoValues.put(COLUMN_USER_NAME, user.getName());
		allPromotionInfoValues.put(COLUMN_USER_HEAD, user.getHead());
		delete(notifyID, user);
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}					
	}
	
	/**
	 * 删除某个通知对应的用户对象
	 * @param notifyID				通知唯一标示符即通知ID
	 * @param user					待删除的用户对象
	 */
	public void delete(String notifyID, TCUser user) {
		if(user.getUserID() == null){
			return;
		}
		mDBStore.delete(TABLE_NAME, COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_UID + "='" + user.getUserID() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
	}
	
	/**
	 * 查询某个通知对应的用户对象
	 * @param notifyID				通知唯一标示符即通知ID
	 * @param uid					待查询的用户ID
	 * @return
	 */
	public TCUser query(String notifyID, String uid){
		TCUser user = new TCUser();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_UID + "='" + uid + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexUId = cursor.getColumnIndex(COLUMN_UID);
				int indexName = cursor.getColumnIndex(COLUMN_USER_NAME);
				int indexHead = cursor.getColumnIndex(COLUMN_USER_HEAD);
				int indexEntendStr = cursor.getColumnIndex(COLUMN_EXTEND_STR);
				
				user.setUserID(cursor.getString(indexUId));
				user.setExtendStr(cursor.getString(indexEntendStr));
				user.setName(cursor.getString(indexName));
				user.setHead(cursor.getString(indexHead));
				
				return user;
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
	 * 获取当前登录用户的所有用户列表
	 * @return
	 */
	public List<TCUser> queryList() {
		List<TCUser> allInfo = new ArrayList<TCUser>();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" , null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexUId = cursor.getColumnIndex(COLUMN_UID);
				int indexName = cursor.getColumnIndex(COLUMN_USER_NAME);
				int indexHead = cursor.getColumnIndex(COLUMN_USER_HEAD);
				int indexEntendStr = cursor.getColumnIndex(COLUMN_EXTEND_STR);
				
				do {
					TCUser user = new TCUser();
					user.setUserID(cursor.getString(indexUId));
					user.setExtendStr(cursor.getString(indexEntendStr));
					user.setName(cursor.getString(indexName));
					user.setHead(cursor.getString(indexHead));
					
					allInfo.add(user);
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
	
}
