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

public class TCNotifyRoomTable {

	public static final String TABLE_NAME = "TCNotifyRoomTable";//数据表的名称

	public static final String COLUMN_NOTIFY_ID = "notifyID";
	public static final String COLUMN_ID = "id";
	public static final String COLUMN_LOGIN_ID = "loginId";
	public static final String COLUMN_SAMLL_FACE = "samlllogo";
	public static final String COLUMN_ATTACH_NAME = "attachName";
	public static final String COLUMN_ATTACH_CONTENT = "attachContent";
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCNotifyRoomTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_NOTIFY_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_LOGIN_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ATTACH_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_SAMLL_FACE, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_ATTACH_CONTENT, COLUMN_TEXT_TYPE);
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_NOTIFY_ID + "," + COLUMN_ID + "," + COLUMN_LOGIN_ID + ")";

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
	
	public void update(String notifyID, TCGroup group) {
		if(TextUtils.isEmpty(group.getGroupID())){
			return;
		}
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_ATTACH_NAME, group.getGroupName());
		allPromotionInfoValues.put(COLUMN_SAMLL_FACE, group.getGroupLogoSmall());
		allPromotionInfoValues.put(COLUMN_ATTACH_CONTENT, group.getGroupDescription());
		
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_ID + " = '" + group.getGroupID() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}						
	}
	
	public void insert(String notifyID, TCGroup group) {
		if(TextUtils.isEmpty(group.getGroupID())){
			return;
		}
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_ID, group.getGroupID());
		allPromotionInfoValues.put(COLUMN_NOTIFY_ID, notifyID);
		allPromotionInfoValues.put(COLUMN_LOGIN_ID, TCChatManager.getInstance().getLoginUid());
		allPromotionInfoValues.put(COLUMN_ATTACH_NAME, group.getGroupName());
		allPromotionInfoValues.put(COLUMN_SAMLL_FACE, group.getGroupLogoSmall());
		allPromotionInfoValues.put(COLUMN_ATTACH_CONTENT, group.getGroupDescription());
		delete(notifyID, group);
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}					
	}
	
	public void delete(String notifyID, TCGroup group) {
		if(TextUtils.isEmpty(group.getGroupID())){
			return;
		}
		mDBStore.delete(TABLE_NAME, COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_ID + "='" + group.getGroupID() + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
	}
	
	public TCGroup query(String notifyID, String id){
		TCGroup group = new TCGroup();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NOTIFY_ID + "='" + notifyID + "' AND " + COLUMN_ID + "='" + id + "' AND " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexId = cursor.getColumnIndex(COLUMN_ID);
				int indexName = cursor.getColumnIndex(COLUMN_ATTACH_NAME);
				int indexSamllFace = cursor.getColumnIndex(COLUMN_SAMLL_FACE);
				int indexContent = cursor.getColumnIndex(COLUMN_ATTACH_CONTENT);
				
				group.setGroupID(cursor.getString(indexId));
				group.setGroupName(cursor.getString(indexName));
				group.setGroupLogoSmall(cursor.getString(indexSamllFace));
				group.setGroupDescription(cursor.getString(indexContent));
				
				return group;
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
	
	public List<TCGroup> queryList() {
		List<TCGroup> allInfo = new ArrayList<TCGroup>();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_LOGIN_ID + "='" + TCChatManager.getInstance().getLoginUid() + "'" , null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexId = cursor.getColumnIndex(COLUMN_ID);
				int indexName = cursor.getColumnIndex(COLUMN_ATTACH_NAME);
				int indexSamllFace = cursor.getColumnIndex(COLUMN_SAMLL_FACE);
				int indexContent = cursor.getColumnIndex(COLUMN_ATTACH_CONTENT);
				
				do {
					TCGroup group = new TCGroup();
					group.setGroupID(cursor.getString(indexId));
					group.setGroupName(cursor.getString(indexName));
					group.setGroupLogoSmall(cursor.getString(indexSamllFace));
					group.setGroupDescription(cursor.getString(indexContent));
					
					allInfo.add(group);
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
