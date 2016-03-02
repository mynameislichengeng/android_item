package com.haoqee.chatsdk.DB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCUser;

public class TCNotifyTable {

	public static final String TABLE_NAME = "TCNotifyTable";//数据表的名称
	/**
	 * CREATE TABLE IF NOT EXISTS tb_notify (type, content, userID, time, processed, currentUser, primary key(userID, time, type, currentUser))
	 */
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_CONTENT = "content";
	public static final String COLUMN_USERID = "userID";
	public static final String COLUMN_ROOMID = "roomID";
	public static final String COLUMN_MESSAGEID = "messageID";
	public static final String COLUMN_TIME = "time";
	public static final String COLUMN_READ_STATE = "read_state";
	public static final String COLUMN_PROCESSED = "processed";
	public static final String COLUMN_LOGINID = "loginID";
	public static final String COLUMN_CODE = "invite_code";
	public static final String COLUMN_PHONE = "phone";
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCNotifyTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TYPE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_CONTENT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_USERID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ROOMID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_MESSAGEID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_USERID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_TIME, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_CODE, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_PHONE, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_READ_STATE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_PROCESSED, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_LOGINID, COLUMN_TEXT_TYPE);
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_ID + ")";

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
	
	public void insert(List<TCNotifyVo> notifyVos) {
		List<TCNotifyVo> notifyList = new ArrayList<TCNotifyVo>();
		notifyList.addAll(notifyVos);
		for (TCNotifyVo notify : notifyList) {
			ContentValues allPromotionInfoValues = new ContentValues();
			
			allPromotionInfoValues.put(COLUMN_TYPE, notify.getType());
			allPromotionInfoValues.put(COLUMN_CONTENT, notify.getContent());
			allPromotionInfoValues.put(COLUMN_USERID, notify.getUserId());
			allPromotionInfoValues.put(COLUMN_ROOMID, notify.getRoomID());
			allPromotionInfoValues.put(COLUMN_TIME, notify.getTime());
			allPromotionInfoValues.put(COLUMN_PROCESSED, notify.getProcessed());
			allPromotionInfoValues.put(COLUMN_LOGINID, TCChatManager.getInstance().getLoginUid());
			
			try {
				mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			} catch (SQLiteConstraintException e) {
				e.printStackTrace();
			}
		}						
	}
	
	public boolean insert(TCNotifyVo notifyVo) {
		
		TCNotifyUserTable userTable = new TCNotifyUserTable(mDBStore);
		TCNotifyRoomTable roomTable = new TCNotifyRoomTable(mDBStore);
		
		if(!TextUtils.isEmpty(notifyVo.getUserId())){
			TCUser user = userTable.query(notifyVo.getNotifyID(), notifyVo.getUserId());
			if(user == null){
				user = notifyVo.getUser();
				userTable.insert(notifyVo.getNotifyID(), user);
			}else {
				user = notifyVo.getUser();
				userTable.update(notifyVo.getNotifyID(), user);
			}
		}
		
		if(!TextUtils.isEmpty(notifyVo.getRoomID())){
			TCGroup group = roomTable.query(notifyVo.getNotifyID(), notifyVo.getRoomID());
			if(group == null){
				group = notifyVo.getRoom();
				roomTable.insert(notifyVo.getNotifyID(), notifyVo.getRoom());
			}else {
				group = notifyVo.getRoom();
				roomTable.update(notifyVo.getNotifyID(), group);
			}
		}
		
		ContentValues allPromotionInfoValues = new ContentValues();
		
		allPromotionInfoValues.put(COLUMN_ID, notifyVo.getNotifyID());
		allPromotionInfoValues.put(COLUMN_TYPE, notifyVo.getType());
		allPromotionInfoValues.put(COLUMN_CONTENT, notifyVo.getContent());
		allPromotionInfoValues.put(COLUMN_USERID, notifyVo.getUserId());
		allPromotionInfoValues.put(COLUMN_ROOMID, notifyVo.getRoomID());
		allPromotionInfoValues.put(COLUMN_TIME, notifyVo.getTime());
		allPromotionInfoValues.put(COLUMN_PROCESSED, notifyVo.getProcessed());
		allPromotionInfoValues.put(COLUMN_READ_STATE, notifyVo.getNotifyReadState());
		allPromotionInfoValues.put(COLUMN_LOGINID, TCChatManager.getInstance().getLoginUid());
		
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}
		
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		return false;
	}
	
	public boolean update(TCNotifyVo notify) {
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_PROCESSED, notify.getProcessed());
		allPromotionInfoValues.put(COLUMN_READ_STATE, notify.getNotifyReadState());
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_ID + " = '" + notify.getNotifyID() + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}			
		
		return false;
	}
	
	public boolean delete(TCNotifyVo notify) {
		try {
			mDBStore.delete(TABLE_NAME, COLUMN_TYPE + " = '" + notify.getType() + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' AND " + COLUMN_USERID + "='" + notify.getUserId() + "' AND " + COLUMN_TIME + "='" + notify.getTime() + "'", null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteByID(TCNotifyVo notify) {
		try {
			
			TCNotifyUserTable userTable = new TCNotifyUserTable(mDBStore);
			if(notify.getUser() != null){
				userTable.delete(notify.getNotifyID(), notify.getUser());
			}
			
			TCNotifyRoomTable roomTable = new TCNotifyRoomTable(mDBStore);
			
			if(notify.getRoom() != null){
				roomTable.delete(notify.getNotifyID(), notify.getRoom());
			}
			
			mDBStore.delete(TABLE_NAME, COLUMN_ID + " = '" + notify.getNotifyID() + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteSame(TCNotifyVo notify) {
		try {
			mDBStore.delete(TABLE_NAME, COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' AND " + COLUMN_USERID + "='" + notify.getUserId() + "' AND " + COLUMN_TYPE + "=" + notify.getType() + " AND " + COLUMN_PROCESSED + "=0", null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public TCNotifyVo query(TCNotifyVo notify){
		Cursor cursor = null;
		List<TCNotifyVo> allInfo = new ArrayList<TCNotifyVo>();
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' AND " /*+ COLUMN_ROOMID + "='" + notify.mRoomID + "' AND "*/ + COLUMN_USERID + "='" + notify.getUserId() + "' AND " + COLUMN_TYPE + "=" + notify.getType() + " ORDER BY " + COLUMN_TIME + " DESC ", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexID = cursor.getColumnIndex(COLUMN_ID);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexUid = cursor.getColumnIndex(COLUMN_USERID);
				int indexTime = cursor.getColumnIndex(COLUMN_TIME);
				int indexProccessed = cursor.getColumnIndex(COLUMN_PROCESSED);
				int indexRoomID = cursor.getColumnIndex(COLUMN_ROOMID);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				
				do {
					TCNotifyVo notifiyVo = new TCNotifyVo();
					notifiyVo.setType(cursor.getInt(indexType));
					notifiyVo.setContent(cursor.getString(indexContent));
					notifiyVo.setUserId(cursor.getString(indexUid));
					notifiyVo.setProcessed(cursor.getInt(indexProccessed));
					notifiyVo.setTime(cursor.getLong(indexTime));
					notifiyVo.setNotifyID(cursor.getString(indexID));
					notifiyVo.setRoomID(cursor.getString(indexRoomID));
					notifiyVo.setNotifyReadState(cursor.getInt(indexReadState));
					if(!TextUtils.isEmpty(notifiyVo.getUserId())){
						TCNotifyUserTable userTable = new TCNotifyUserTable(mDBStore);
						notifiyVo.setUser(userTable.query(notifiyVo.getNotifyID(), notifiyVo.getUserId()));
					}
					
					if(!TextUtils.isEmpty(notifiyVo.getRoomID())){
						TCNotifyRoomTable table = new TCNotifyRoomTable(mDBStore);
						notifiyVo.setRoom(table.query(notifiyVo.getNotifyID(), notifiyVo.getRoomID()));
						notifiyVo.setRoomID(notifiyVo.getRoom().getGroupID());
					}
					
					allInfo.add(notifiyVo);
				} while (cursor.moveToNext());
				
				if(allInfo.size() != 0){
					return allInfo.get(0);
				}
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
	 * 获取通知列表
	 * @return
	 */
	public List<TCNotifyVo> query() {
		List<TCNotifyVo> allInfo = new ArrayList<TCNotifyVo>();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' ORDER BY " + COLUMN_TIME + " DESC ", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexID = cursor.getColumnIndex(COLUMN_ID);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexUid = cursor.getColumnIndex(COLUMN_USERID);
				int indexTime = cursor.getColumnIndex(COLUMN_TIME);
				int indexProccessed = cursor.getColumnIndex(COLUMN_PROCESSED);
				int indexRoomID = cursor.getColumnIndex(COLUMN_ROOMID);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				
				do {
					TCNotifyVo notifiyVo = new TCNotifyVo();
					notifiyVo.setType(cursor.getInt(indexType));
					notifiyVo.setContent(cursor.getString(indexContent));
					notifiyVo.setUserId(cursor.getString(indexUid));
					notifiyVo.setProcessed(cursor.getInt(indexProccessed));
					notifiyVo.setTime(cursor.getLong(indexTime));
					notifiyVo.setTime(cursor.getLong(indexTime));
					notifiyVo.setNotifyID(cursor.getString(indexID));
					notifiyVo.setRoomID(cursor.getString(indexRoomID));
					notifiyVo.setNotifyReadState(cursor.getInt(indexReadState));
					if(!TextUtils.isEmpty(notifiyVo.getUserId())){
						TCNotifyUserTable userTable = new TCNotifyUserTable(mDBStore);
						notifiyVo.setUser(userTable.query(notifiyVo.getNotifyID(), notifiyVo.getUserId()));
					}
					
					if(!TextUtils.isEmpty(notifiyVo.getRoomID())){
						TCNotifyRoomTable table = new TCNotifyRoomTable(mDBStore);
						notifiyVo.setRoom(table.query(notifiyVo.getNotifyID(), notifiyVo.getRoomID()));
						notifiyVo.setRoomID(notifiyVo.getRoom().getGroupID());
					}
					
					allInfo.add(notifiyVo);
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
	 * 获取最新一条通知
	 * @return
	 */
	public TCNotifyVo queryNewNotify(){
		TCNotifyVo notifyVo = null;
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' ORDER BY " + COLUMN_TIME + " DESC LIMIT 0,1", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexID = cursor.getColumnIndex(COLUMN_ID);
				int indexType = cursor.getColumnIndex(COLUMN_TYPE);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexUid = cursor.getColumnIndex(COLUMN_USERID);
				int indexTime = cursor.getColumnIndex(COLUMN_TIME);
				int indexProccessed = cursor.getColumnIndex(COLUMN_PROCESSED);
				int indexRoomID = cursor.getColumnIndex(COLUMN_ROOMID);
				int indexReadState = cursor.getColumnIndex(COLUMN_READ_STATE);
				
				TCNotifyVo notifiyVo = new TCNotifyVo();
				notifiyVo.setType(cursor.getInt(indexType));
				notifiyVo.setContent(cursor.getString(indexContent));
				notifiyVo.setUserId(cursor.getString(indexUid));
				notifiyVo.setProcessed(cursor.getInt(indexProccessed));
				notifiyVo.setTime(cursor.getLong(indexTime));
				notifiyVo.setTime(cursor.getLong(indexTime));
				notifiyVo.setNotifyID(cursor.getString(indexID));
				notifiyVo.setRoomID(cursor.getString(indexRoomID));
				notifiyVo.setNotifyReadState(cursor.getInt(indexReadState));
				if(!TextUtils.isEmpty(notifiyVo.getUserId())){
					TCNotifyUserTable userTable = new TCNotifyUserTable(mDBStore);
					notifiyVo.setUser(userTable.query(notifiyVo.getNotifyID(), notifiyVo.getUserId()));
				}
				
				if(!TextUtils.isEmpty(notifiyVo.getRoomID())){
					TCNotifyRoomTable table = new TCNotifyRoomTable(mDBStore);
					notifiyVo.setRoom(table.query(notifiyVo.getNotifyID(), notifiyVo.getRoomID()));
					notifiyVo.setRoomID(notifiyVo.getRoom().getGroupID());
				}
				
				notifyVo = notifiyVo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		
		return notifyVo;
	}
	
	public int queryUnread() {
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "' AND " + COLUMN_READ_STATE + "=0", null);
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
