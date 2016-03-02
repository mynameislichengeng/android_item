package com.xguanjia.hx.haoxin.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.haoxin.bean.HaoXinMsgBean;

public class HaoXinGroupDb {
	private DatabaseHelper dbOpenHelper;
	private Context th_context;

	public HaoXinGroupDb(Context context) {
		th_context = context;
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 保存所有最新的message，按照倒叙排列
	 */
	public void insertNewMsg(List<HaoXinMsgBean> list) {
		if(dbOpenHelper == null){
			this.dbOpenHelper = DatabaseHelper.getInstance(th_context);
		}
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			int j = list.size();
			for (int i = 0; i < j; i++) {
				String key = UUID.randomUUID().toString().replace("-", "");
				HaoXinMsgBean bean = list.get(i);
				database.execSQL("insert into tb_message(primary_id,msg_id,msg_obj,msg_obj_name,msg_title,msg_time,msg_type,msg_num) values(?,?,?,?,?,?,?,?)", new Object[] { key, bean.getMessageId(),
						bean.getMessageObj(), bean.getMessageObjName(), bean.getMessageTitle(), bean.getMessageCreateTime(), bean.getMessageAppType(), bean.getMsgNum() });
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}
	
	/*
	 * 删除message
	 */
	
	public void delAnnounce(String announceId){
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			StringBuilder sql = new StringBuilder("delete from tb_contact_notice where id = ?");
			StringBuilder sql1 = new StringBuilder("delete from tb_contact_notice_detail where id = ?");
			StringBuilder sql2 = new StringBuilder("delete from tb_message where msg_id = ?");
			database.execSQL(sql2.toString(), new String[] { announceId });
			database.execSQL(sql.toString(), new String[] { announceId });
			database.execSQL(sql1.toString(), new String[] { announceId });
			
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.close();
		}
	}
	
	/**
	 * 即时聊天保存 文件传输
	 */
	public void insertChatMsg(List<HaoXinMsgBean> list) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		
		try {
			database.beginTransaction();
			int j = list.size();
			for (int i = 0; i < j; i++) {
				String key = UUID.randomUUID().toString().replace("-", "");
				HaoXinMsgBean bean = list.get(i);
				StringBuilder sql = new StringBuilder("delete from tb_message where msg_obj_name = ?");
				database.execSQL(sql.toString(), new String[] { bean.getMessageObjName() });
				database.execSQL("insert into tb_message(primary_id,msg_id,msg_obj,msg_obj_name,msg_title,msg_time,msg_type,msg_num) values(?,?,?,?,?,?,?,?)", new Object[] { key, bean.getMessageId(),
						bean.getMessageObj(), bean.getMessageObjName(), bean.getMessageTitle(), bean.getMessageCreateTime(), bean.getMessageAppType(), bean.getMsgNum() });
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}
	
	//更新即时消息
	public void updateNewMsg(HaoXinMsgBean bean){
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			StringBuilder strSql = new StringBuilder(
					"update tb_message set msg_title=?"
							+ "where msg_obj_name = ?");
			database.execSQL(
					strSql.toString(),
					new String[] { bean.getMessageTitle(),bean.getMessageObjName()});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.close();
		}
		
	}

	/**
	 * 查询所有最新的message，按照倒叙排列
	 */
	public List<HaoXinMsgBean> queryMessgeOrderByTime() {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<HaoXinMsgBean> list = new ArrayList<HaoXinMsgBean>();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder("select * from (select * from (select * from tb_message where msg_type in('openfire','innerMessage')) group by (msg_obj_name) union all select * from (select * from tb_message where msg_type not in('innerMessage','openfire')) group by(msg_type)) order by msg_time desc");
			cursor = database.rawQuery(sql.toString(), new String[] {});
			list = getListCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			database.close();
		}
		return list;

	}

	private List<HaoXinMsgBean> getListCursor(Cursor cursor) {
		List<HaoXinMsgBean> list = new ArrayList<HaoXinMsgBean>();
		if (null == cursor) {
			return list;
		}
		while (cursor.moveToNext()) {
			HaoXinMsgBean bean = new HaoXinMsgBean();
			bean.setMessageAppType(cursor.getString(cursor.getColumnIndex("msg_type")));
			bean.setMessageCreateTime(cursor.getString(cursor.getColumnIndex("msg_time")));
			bean.setMessageId(cursor.getString(cursor.getColumnIndex("msg_id")));
			bean.setMessageObj(cursor.getString(cursor.getColumnIndex("msg_obj")));
			bean.setMessageObjName(cursor.getString(cursor.getColumnIndex("msg_obj_name")));
			bean.setMessageTitle(cursor.getString(cursor.getColumnIndex("msg_title")));
			bean.setMsgNum(cursor.getString(cursor.getColumnIndex("msg_num")));
			bean.setAvatar(selectAvatar(bean.getMessageObj()));
			list.add(bean);
		}
		return list;
	}

	public void deleteMsg(HaoXinMsgBean bean) {

		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			StringBuilder sql = new StringBuilder("delete from tb_message where msg_obj = ?");
			database.execSQL(sql.toString(), new String[] { bean.getMessageObj() });
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.close();
		}
	}

	public String selectAvatar(String userId) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder("select * from tb_person where pe_user_id =?");
			cursor = database.rawQuery(sql.toString(), new String[] { userId });
			if (cursor.moveToFirst()) {
				return cursor.getString(cursor.getColumnIndex("pe_avatar"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			database.close();
		}
		return "";
	}

	/**
	 * 清除数据
	 */
	public void resetData() {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("DROP TABLE IF EXISTS tb_person");
		db.execSQL("DROP TABLE IF EXISTS tb_information");
		db.execSQL("DROP TABLE IF EXISTS tb_contact_notice");
		db.execSQL("DROP TABLE IF EXISTS tb_contact_notice_detail");
		db.execSQL("DROP TABLE IF EXISTS tb_message");
		db.execSQL("DROP TABLE IF EXISTS tb_attach");
		db.execSQL("DROP TABLE IF EXISTS tb_contact_folders");
		db.execSQL("DROP TABLE IF EXISTS tb_diary");
		db.execSQL("DROP TABLE IF EXISTS tb_gps_location");
		db.execSQL("DROP TABLE IF EXISTS tb_share");
	}

}
