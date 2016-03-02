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

/**
 * 群数据表
 *
 */

public class TCGroupTable {

	public static final String TABLE_NAME = "TCGroupTable";//数据表的名称
	
	public static final String COLUMN_GROUP_ID = "groupid";							//群ID
	public static final String COLUMN_GROUP_TYPE = "grouptype";						//群类型						
	public static final String COLUMN_CONTENT = "content";							//群简介
	public static final String COLUMN_GROUP_NAME = "groupName";						//群名称
	public static final String COLUMN_CREATE_UID = "createUID";						//群创建者ID
	public static final String COLUMN_CREATE_NAME = "createName";					//群创建者名称
	public static final String COLUMN_CREATE_TIME = "createTime";					//群创建时间
	public static final String COLUMN_GROUP_LOGO_SMALL = "logosmall";				//群小头像URL
	public static final String COLUMN_GROUP_LOGO_LARGE = "logolarge";				//群大头像URL
	public static final String COLUMN_GROUP_ROLE = "groupRole";						//群角色			0--普通成员			1--管理员
	public static final String COLUMN_GROUP_MENBER_COUNT = "menberCount";			//群成员数
	public static final String COLUMN_GROUP_GETMSG = "getMsg";						//群接收消息设置		0--不接收				1--接收
	public static final String COLUMN_GROUP_IS_JOIN = "isJoin";						//是否已加入该群		0--未加入				1--已加入
	public static final String COLUMN_LOGINID = "loginID";							//当前登录ID
	
	public static final String COLUMN_INTEGER_TYPE = "integer";
	public static final String COLUMN_TEXT_TYPE = "text";
	public static final String PRIMARY_KEY_TYPE = "primary key(";
	
	private SQLiteDatabase mDBStore;

	private static String mSQLCreateWeiboInfoTable = null;
	private static String mSQLDeleteWeiboInfoTable = null;
	
	public TCGroupTable(SQLiteDatabase sqlLiteDatabase) {
		mDBStore = sqlLiteDatabase;
	}
	
	public static String getCreateTableSQLString() {
		if (null == mSQLCreateWeiboInfoTable) {

			HashMap<String, String> columnNameAndType = new HashMap<String, String>();
			columnNameAndType.put(COLUMN_GROUP_ID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_GROUP_TYPE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_CONTENT, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_GROUP_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_CREATE_UID, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_CREATE_NAME, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_CREATE_TIME, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_GROUP_LOGO_SMALL, COLUMN_TEXT_TYPE);
			columnNameAndType.put(COLUMN_GROUP_LOGO_LARGE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_GROUP_ROLE, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_GROUP_MENBER_COUNT, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_GROUP_GETMSG, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_GROUP_IS_JOIN, COLUMN_INTEGER_TYPE);
			columnNameAndType.put(COLUMN_LOGINID, COLUMN_TEXT_TYPE);
			String primary_key = PRIMARY_KEY_TYPE + COLUMN_GROUP_ID + ")";

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
	 * 插入群组列表
	 * @param groups			群列表或讨论组列表
	 * @param type				类型（群或讨论组）
	 */
	public void insert(List<TCGroup> groups, int type) {
		List<TCGroup> groupList = new ArrayList<TCGroup>();
		groupList.addAll(groups);
		for (TCGroup group : groupList) {
			ContentValues allPromotionInfoValues = new ContentValues();
			
			allPromotionInfoValues.put(COLUMN_LOGINID, TCChatManager.getInstance().getLoginUid());
			allPromotionInfoValues.put(COLUMN_GROUP_ID, group.getGroupID());
			allPromotionInfoValues.put(COLUMN_CONTENT, group.getGroupDescription());
			allPromotionInfoValues.put(COLUMN_GROUP_NAME, group.getGroupName());
			allPromotionInfoValues.put(COLUMN_GROUP_TYPE, type);
			allPromotionInfoValues.put(COLUMN_CREATE_UID, group.getCreatorID());
			allPromotionInfoValues.put(COLUMN_CREATE_NAME, group.getCreatorName());
			allPromotionInfoValues.put(COLUMN_CREATE_TIME, group.getGroupCreateTime());
			allPromotionInfoValues.put(COLUMN_GROUP_LOGO_SMALL, group.getGroupLogoSmall());
			allPromotionInfoValues.put(COLUMN_GROUP_LOGO_LARGE, group.getGroupLogoLarge());
			allPromotionInfoValues.put(COLUMN_GROUP_ROLE, group.getGroupRole());
			allPromotionInfoValues.put(COLUMN_GROUP_MENBER_COUNT, group.getGroupMenberCount());
			allPromotionInfoValues.put(COLUMN_GROUP_GETMSG, group.getGroupGetMsg());
			allPromotionInfoValues.put(COLUMN_GROUP_IS_JOIN, group.getGroupIsJoin());
			
			deleteGroup(group.getGroupID(), type);
			
			try {
				mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
			} catch (SQLiteConstraintException e) {
				e.printStackTrace();
			}
		}						
	}
	
	/**
	 * 插入单个群
	 * @param group				群对象
	 * @param type				类型
	 */
	public void insert(TCGroup group, int type) {
		if(TextUtils.isEmpty(group.getGroupID())){
			return;
		}
		
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_LOGINID, TCChatManager.getInstance().getLoginUid());
		allPromotionInfoValues.put(COLUMN_GROUP_ID, group.getGroupID());
		allPromotionInfoValues.put(COLUMN_CONTENT, group.getGroupDescription());
		allPromotionInfoValues.put(COLUMN_GROUP_NAME, group.getGroupName());
		allPromotionInfoValues.put(COLUMN_GROUP_TYPE, type);
		allPromotionInfoValues.put(COLUMN_CREATE_UID, group.getCreatorID());
		allPromotionInfoValues.put(COLUMN_CREATE_NAME, group.getCreatorName());
		allPromotionInfoValues.put(COLUMN_CREATE_TIME, group.getGroupCreateTime());
		allPromotionInfoValues.put(COLUMN_GROUP_LOGO_SMALL, group.getGroupLogoSmall());
		allPromotionInfoValues.put(COLUMN_GROUP_LOGO_LARGE, group.getGroupLogoLarge());
		allPromotionInfoValues.put(COLUMN_GROUP_ROLE, group.getGroupRole());
		allPromotionInfoValues.put(COLUMN_GROUP_MENBER_COUNT, group.getGroupMenberCount());
		allPromotionInfoValues.put(COLUMN_GROUP_GETMSG, group.getGroupGetMsg());
		allPromotionInfoValues.put(COLUMN_GROUP_IS_JOIN, group.getGroupIsJoin());
		
		deleteGroup(group.getGroupID(), type);
		try {
			mDBStore.insertOrThrow(TABLE_NAME, null, allPromotionInfoValues);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}					
	}
	
	/**
	 * 更新群消息设置
	 * @param group					群对象
	 * @param type					类型
	 */
	public void updateGroupGetMsg(TCGroup group, int type){
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_GROUP_GETMSG, group.getGroupGetMsg());
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_GROUP_TYPE + "=" + type + " AND " + COLUMN_GROUP_ID + "='" + group.getGroupID() + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 更新群名称
	 * @param group					群对象
	 * @param type					类型
	 */
	public void updateGroupName(TCGroup group, int type){
		ContentValues allPromotionInfoValues = new ContentValues();
		allPromotionInfoValues.put(COLUMN_GROUP_NAME, group.getGroupName());
		try {
			mDBStore.update(TABLE_NAME, allPromotionInfoValues, COLUMN_GROUP_TYPE + "=" + type + " AND " + COLUMN_GROUP_ID + "='" + group.getGroupID() + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
		} catch (SQLiteConstraintException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 删除指定ID的群
	 * @param type
	 */
	public void deleteGroup(String id, int type) {
		try{
			String execSql = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_GROUP_TYPE + "=" + type + " AND " + COLUMN_GROUP_ID + "='" + id + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'";
			mDBStore.execSQL(execSql);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TCGroup query(String groupid, int type){
		TCGroup group = new TCGroup();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_GROUP_TYPE + "=" + type + " AND " + COLUMN_GROUP_ID + "='" + groupid + "' AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'", null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexGroupId = cursor.getColumnIndex(COLUMN_GROUP_ID);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexGroupName = cursor.getColumnIndex(COLUMN_GROUP_NAME);
				int indexCreateUid = cursor.getColumnIndex(COLUMN_CREATE_UID);
				int indexCreateName = cursor.getColumnIndex(COLUMN_CREATE_NAME);
				int indexCreateTime = cursor.getColumnIndex(COLUMN_CREATE_TIME);
				int indexGroupLogoSmall = cursor.getColumnIndex(COLUMN_GROUP_LOGO_SMALL);
				int indexGroupLogoLarge = cursor.getColumnIndex(COLUMN_GROUP_LOGO_LARGE);
				int indexGroupRole = cursor.getColumnIndex(COLUMN_GROUP_ROLE);
				int indexGroupMenberCount = cursor.getColumnIndex(COLUMN_GROUP_MENBER_COUNT);
				int indexGroupGetMsg = cursor.getColumnIndex(COLUMN_GROUP_GETMSG);
				int indexGroupIsJoin = cursor.getColumnIndex(COLUMN_GROUP_IS_JOIN);
				
				
				group.setGroupID(cursor.getString(indexGroupId));
				group.setGroupName(cursor.getString(indexGroupName));
				group.setGroupDescription(cursor.getString(indexContent));
				group.setGroupCreatorID(cursor.getString(indexCreateUid));
				group.setGroupCreatorName(cursor.getString(indexCreateName));
				group.setGroupCreateTime(cursor.getLong(indexCreateTime));
				group.setGroupLogoSmall(cursor.getString(indexGroupLogoSmall));
				group.setGroupLogoLarge(cursor.getString(indexGroupLogoLarge));
				group.setGroupRole(cursor.getInt(indexGroupRole));
				group.setGroupMenberCount(cursor.getInt(indexGroupMenberCount));
				group.setGroupGetMsg(cursor.getInt(indexGroupGetMsg));
				group.setGroupIsJoin(cursor.getInt(indexGroupIsJoin));
				
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
	
	/**
	 * 获取指定类型的群类表
	 * @param type				群和讨论组
	 * @return
	 */
	public List<TCGroup> queryList(int type) {
		List<TCGroup> allInfo = new ArrayList<TCGroup>();
		Cursor cursor = null;
		try {
			cursor = mDBStore.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_GROUP_TYPE + "=" + type + " AND " + COLUMN_LOGINID + "='" + TCChatManager.getInstance().getLoginUid() + "'" , null);
			if (cursor != null) {
				
				if (!cursor.moveToFirst()) {
					return null;
				}
				
				int indexGroupId = cursor.getColumnIndex(COLUMN_GROUP_ID);
				int indexContent = cursor.getColumnIndex(COLUMN_CONTENT);
				int indexGroupName = cursor.getColumnIndex(COLUMN_GROUP_NAME);
				int indexCreateUid = cursor.getColumnIndex(COLUMN_CREATE_UID);
				int indexCreateName = cursor.getColumnIndex(COLUMN_CREATE_NAME);
				int indexCreateTime = cursor.getColumnIndex(COLUMN_CREATE_TIME);
				int indexGroupLogoSmall = cursor.getColumnIndex(COLUMN_GROUP_LOGO_SMALL);
				int indexGroupLogoLarge = cursor.getColumnIndex(COLUMN_GROUP_LOGO_LARGE);
				int indexGroupRole = cursor.getColumnIndex(COLUMN_GROUP_ROLE);
				int indexGroupMenberCount = cursor.getColumnIndex(COLUMN_GROUP_MENBER_COUNT);
				int indexGroupGetMsg = cursor.getColumnIndex(COLUMN_GROUP_GETMSG);
				int indexGroupIsJoin = cursor.getColumnIndex(COLUMN_GROUP_IS_JOIN);
				
				do {
					TCGroup group = new TCGroup();
					group.setGroupID(cursor.getString(indexGroupId));
					group.setGroupName(cursor.getString(indexGroupName));
					group.setGroupDescription(cursor.getString(indexContent));
					group.setGroupCreatorID(cursor.getString(indexCreateUid));
					group.setGroupCreatorName(cursor.getString(indexCreateName));
					group.setGroupCreateTime(cursor.getLong(indexCreateTime));
					group.setGroupLogoSmall(cursor.getString(indexGroupLogoSmall));
					group.setGroupLogoLarge(cursor.getString(indexGroupLogoLarge));
					group.setGroupRole(cursor.getInt(indexGroupRole));
					group.setGroupMenberCount(cursor.getInt(indexGroupMenberCount));
					group.setGroupGetMsg(cursor.getInt(indexGroupGetMsg));
					group.setGroupIsJoin(cursor.getInt(indexGroupIsJoin));
					
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
