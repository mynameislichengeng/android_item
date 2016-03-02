package com.xguanjia.hx.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.contact.bean.TimestampBean;


/**
 * 
 * @author huke
 * 
 * 说明：时间戳表数据库操作类
 * 
 * 日期：2013-09-22
 *
 */
public class TimestampDb {
	
	private static final String TAG = "TimestampDb";
	
	private Context context;
	
	private DatabaseHelper m_dbHelper = null;
	
	public TimestampDb(Context context) {
		this.context = context;
		this.m_dbHelper = DatabaseHelper.getInstance(context);
	}
	
	/**
	 * 更新当前记录时间戳
	 * @param bean
	 */
	public void updateTimestamp(TimestampBean bean) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if(null == m_dbHelper) {
				m_dbHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = m_dbHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = m_dbHelper.getWritableDatabase();
			}
			
			List<TimestampBean> timestampList = new ArrayList<TimestampBean>();
			
			//从时间戳表中查询数据
			Cursor cursor = sqLiteDatabase.rawQuery("select * from tb_timestamp", new String[] {});			
			while (cursor.moveToNext()) {
				TimestampBean timestampBean = new TimestampBean();
				timestampBean.setPrimaryId(cursor.getString(cursor.getColumnIndex("primary_id")));
				timestampBean.setPersonTimestamp(cursor.getString(cursor.getColumnIndex("person_timestamp")));
				timestampBean.setDepartmentTimestamp(cursor.getString(cursor.getColumnIndex("department_timestamp")));
				timestampBean.setDutyTimestamp(cursor.getString(cursor.getColumnIndex("dutyTimestamp")));
				timestampList.add(timestampBean);
			}
			cursor.close();
			if(timestampList.size() == 0) {
				//当前还没有时间戳记录，执行插入操作
				String key = UUID.randomUUID().toString().replace("-", "");
				StringBuilder strSql = new StringBuilder("insert into tb_timestamp(primary_id, person_timestamp, " +"department_timestamp," +"dutyTimestamp) values (?,?,?,?)");
				sqLiteDatabase.execSQL(strSql.toString(), new String[] { key, bean.getPrimaryId(), 
					bean.getDepartmentTimestamp(),bean.getDutyTimestamp()});
			} else if(timestampList.size() == 1) {
				//当前已存在时间戳记录，执行更新操作
				StringBuilder strSql = new StringBuilder("update tb_timestamp set person_timestamp=?," +"department_timestamp=?," +"dutyTimestamp = ? where primary_id=?");
				sqLiteDatabase.execSQL(strSql.toString(), new String[] { bean.getPersonTimestamp(), 
					bean.getDepartmentTimestamp(),bean.getDutyTimestamp(), bean.getPrimaryId()});
			} else {
				Log.i(TAG, "当前时间戳记录数异常，记录数为：" + timestampList.size());
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "更新当前时间戳记录异常：" + e.getMessage());
		} finally {
			if(null != sqLiteDatabase) {
				sqLiteDatabase.close();
			}
		}
	}
	
	/**
	 * 从时间戳表中查询时间戳
	 * @return
	 */
	public TimestampBean selectTimestamp() {
		TimestampBean bean = new TimestampBean();
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if(null == m_dbHelper) {
				m_dbHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = m_dbHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = m_dbHelper.getWritableDatabase();
			}
			
			List<TimestampBean> timestampList = new ArrayList<TimestampBean>();
			
			//从时间戳表中查询数据
			Cursor cursor = sqLiteDatabase.rawQuery("select * from tb_timestamp", new String[] {});			
			while (cursor.moveToNext()) {
				TimestampBean timestampBean = new TimestampBean();
				timestampBean.setPrimaryId(cursor.getString(cursor.getColumnIndex("primary_id")));
				timestampBean.setPersonTimestamp(cursor.getString(cursor.getColumnIndex("person_timestamp")));
				timestampBean.setDepartmentTimestamp(cursor.getString(cursor.getColumnIndex("department_timestamp")));
				timestampBean.setDutyTimestamp(cursor.getString(cursor.getColumnIndex("dutyTimestamp")));
				timestampList.add(timestampBean);
			}
			cursor.close();
			if(timestampList.size() == 0) {
				Log.i(TAG, "当前还没有时间戳记录");
				return bean;
			} else if(timestampList.size() == 1) {
				bean = timestampList.get(0);
				return bean;
			} else {
				Log.e(TAG, "当前获取到的时间戳记录异常：" + timestampList.size());
				return bean;
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询时间戳表异常：" + e.getMessage());			
		} finally {
			if(null != sqLiteDatabase) {
				sqLiteDatabase.close();
			}
		}
		return bean;
	}

}
