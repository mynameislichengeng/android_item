package com.xguanjia.hx.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.application.bean.UnSendBean;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;

public class UnSendDb {
	private DatabaseHelper dbOpenHelper;

	public UnSendDb(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	/**
	 * 删除未上传的数据
	 * @param id
	 */
	public void deleteUnSendData(String id){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		try {
			db.execSQL("delete from tb_unsend_data where primary_id=? ",new String []{id});
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	/**
	 * 保存未上传的数据
	 * 
	 * @param bean
	 */
	public void saveUnSendData(UnSendBean bean) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		try {
			db.execSQL("insert into tb_unsend_data (primary_id,se_table_name,se_msg_id,se_send_time,se_msg_title,se_image_path,se_user_id) values (?,?,?,?,?,?,?)", new Object[] {
					UUID.randomUUID().toString().replace("-", ""),bean.getTableName(),bean.getMsgId(),bean.getMsgSendTime(),bean.getMsgTitle(),bean.getImagePath(),Constants.userId});
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}

	}

	public List<UnSendBean> queryMessgeOrderByTime() {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<UnSendBean> list = new ArrayList<UnSendBean>();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder("select * from tb_unsend_data where se_user_id = ? order by se_send_time");
			cursor = database.rawQuery(sql.toString(), new String[] { Constants.userId });
			list = getListCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			database.close();
		}
		return list;

	}

	private List<UnSendBean> getListCursor(Cursor cursor) {
		List<UnSendBean> list = new ArrayList<UnSendBean>();
		if (null == cursor) {
			return list;
		}
		while (cursor.moveToNext()) {
			UnSendBean bean = new UnSendBean();
			bean.setImagePath(cursor.getString(cursor.getColumnIndex("se_image_path")));
			bean.setMsgId(cursor.getString(cursor.getColumnIndex("se_msg_id")));
			bean.setMsgSendTime(cursor.getString(cursor.getColumnIndex("se_send_time")));
			bean.setMsgTitle(cursor.getString(cursor.getColumnIndex("se_msg_title")));
			bean.setPrimaryId(cursor.getString(cursor.getColumnIndex("primary_id")));
			bean.setTableName(cursor.getString(cursor.getColumnIndex("se_table_name")));
			bean.setUserId(cursor.getString(cursor.getColumnIndex("se_user_id")));
			list.add(bean);
		}
		return list;
	}
}
