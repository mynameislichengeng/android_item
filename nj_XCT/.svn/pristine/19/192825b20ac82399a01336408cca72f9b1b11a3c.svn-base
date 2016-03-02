package com.xguanjia.hx.plugin.db;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.plugin.bean.PlugBean;

public class PlugFormsDb {
	private static final String TAG = "PlugFormsDb";
	private Context context;

	private DatabaseHelper dbOpenHelper;

	public PlugFormsDb(Context context) {
		super();
		this.context = context;
		this.dbOpenHelper = DatabaseHelper.getInstance(context);

	}

	/**
	 * 保存报表模板
	 */
	public void savePlugForms(List<PlugBean> list) {
		Cursor cursor = null;
		if (dbOpenHelper == null) {
			dbOpenHelper = DatabaseHelper.getInstance(context);
		}
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {

			long lNum = 0;
			database.beginTransaction();
			for (int i = 0; i < list.size(); i++) {
				PlugBean bean = list.get(i);
				if ("U".equals(bean.getOperateType())) {
					// 查询是否存在，如果不存在就是插入
					// 检验是否已经存在,存在返回false
					StringBuilder sql = new StringBuilder(
							"select count(*) from data_plugin where id=?");
					if (null != bean.getId() && null != bean.getId()) {
						cursor = database.rawQuery(sql.toString(),
								new String[] { bean.getId() });
					}
					if (null != cursor) {
						cursor.moveToFirst();
						lNum = cursor.getLong(0);
					}
					// 已经存在记录就是更新操作
					if (lNum == 1) {
						database.execSQL(
								"update data_plugin set template_name=? ,client_menu_name=?,template_zip_file=?,create_date=?,plugin_type=?,sort_num=?,operate_type=? where id=?",
								new String[] { bean.getTemplateName(),
										bean.getClientMenuName(),
										bean.getTemplateZipFile(),
										bean.getCreateDate(),
										bean.getPluginType(),
										bean.getSortNum(),
										bean.getOperateType(), bean.getId() });
					} else {
						database.execSQL(
								"insert into data_plugin(id,template_name,client_menu_name,template_zip_file,create_date,plugin_type,sort_num,operate_type) "
										+ "values(?,?,?,?,?,?,?,?)",
								new Object[] { bean.getId(),
										bean.getTemplateName(),
										bean.getClientMenuName(),
										bean.getTemplateZipFile(),
										bean.getCreateDate(),
										bean.getPluginType(),
										bean.getSortNum(),
										bean.getOperateType() });
					}

				}
				if ("D".equals(bean.getOperateType())) {
					database.execSQL("delete from data_plugin where id=?",
							new Object[] { bean.getId() });
					Log.i("测试----------------------", bean.getId());
				}
				if ("I".equals(bean.getOperateType())) {
					String id = bean.getId();
					database.execSQL(
							"insert into data_plugin(id,template_name,client_menu_name,template_zip_file,create_date,plugin_type,sort_num,operate_type) "
									+ "values(?,?,?,?,?,?,?,?)",
							new Object[] { id, bean.getTemplateName(),
									bean.getClientMenuName(),
									bean.getTemplateZipFile(),
									bean.getCreateDate(), bean.getPluginType(),
									bean.getSortNum(), bean.getOperateType() });
				}
			}
			database.setTransactionSuccessful();
			database.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "插入模板数据异常：" + e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
	}

	/**
	 * 查询报表模板
	 */
	public List<PlugBean> queryPlugForms() {
		Cursor cursor = null;
		SQLiteDatabase database = null;
		List<PlugBean> plugsList = new ArrayList<PlugBean>();
		try {
			database = dbOpenHelper.getWritableDatabase();
			cursor = database.rawQuery(
					"select * from data_plugin  order by sort_num",
					new String[] {});
			while (cursor.moveToNext()) {
				PlugBean bean = new PlugBean();
				bean.setId(cursor.getString(cursor.getColumnIndex("id")));
				bean.setClientMenuName(cursor.getString(cursor
						.getColumnIndex("client_menu_name")));
				bean.setTemplateName(cursor.getString(cursor
						.getColumnIndex("template_name")));
				bean.setTemplateZipFile(cursor.getString(cursor
						.getColumnIndex("template_zip_file")));
				plugsList.add(bean);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "查询模板数据异常：" + e.getMessage());
		}
		return plugsList;
	}

	public List<PlugBean> selectMyActualRecords() {
		Cursor cursor = null;
		SQLiteDatabase database = null;
		List<PlugBean> plugList = new ArrayList<PlugBean>();
		try {
			database = dbOpenHelper.getWritableDatabase();
			cursor = database.rawQuery(
					"select * from data_plugin  order by sort_num",
					new String[] {});
			while (cursor.moveToNext()) {
				PlugBean bean = new PlugBean();
				bean.setId(cursor.getString(cursor.getColumnIndex("id")));

				bean.setClientMenuName(cursor.getString(cursor
						.getColumnIndex("client_menu_name")));
				bean.setTemplateName(cursor.getString(cursor
						.getColumnIndex("template_name")));
				bean.setTemplateZipFile(cursor.getString(cursor
						.getColumnIndex("template_zip_file")));
				bean.setCreateDate(cursor.getString(cursor
						.getColumnIndex("create_date")));
				bean.setPluginType(cursor.getString(cursor
						.getColumnIndex("plugin_type")));
				bean.setOperateType(cursor.getString(cursor
						.getColumnIndex("operate_type")));
				plugList.add(bean);
			}
			cursor.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "查询模板数据异常：" + e.getMessage());
		}
		return plugList;
	}

	/**
	 * 查询最新的时间
	 * 
	 * @return
	 */
	public String seletMaxTimePlug() {
		String strMaxTime = "";
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
			}
			SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
			String strSql = "select max(create_date) from data_plugin";
			Cursor cursor = database.rawQuery(strSql, null);
			while (cursor.moveToNext()) {
				strMaxTime = cursor.getString(0);
				return strMaxTime;
			}
			return "";
		} catch (Exception e) {
			Log.e(TAG, "查询模板最新时间异常：" + e.getMessage());
			return "";
		}
	}

}
