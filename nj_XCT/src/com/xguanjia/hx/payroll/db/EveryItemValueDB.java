package com.xguanjia.hx.payroll.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.payroll.bean.EveryPayValueBean;

/**
 * 
 * @author Administrator 存储除全部以外，每一单项的值
 */

public class EveryItemValueDB {

	private DatabaseHelper dbOpenHelper;

	public EveryItemValueDB(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 
	 * @param bean_groupSalaryList
	 *            将当前的工资存储到单一工资的数据库中
	 */
	public void saveToDB(ArrayList<EveryPayValueBean> bean_groupSalaryList) {

		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();

			for (EveryPayValueBean bean : bean_groupSalaryList) {
				database.execSQL("insert into tb_dx(usegroupid,wages,usergroupname,payrollid) values(?,?,?,?)", new Object[] { bean.getUseGroupingId(), bean.getWages(), bean.getUseGroupingName(), bean.getPayRollId() });
			}

			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}

	}

	/**
	 * 根据传过来的删除工资单ID来删除有对应项的ID 传过来的是一个list
	 */

	public void deleteItem(List<Integer> list) {

		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		String str = "delete from tb_dx where payrollid =?";
		for (Integer i : list) {
			database.execSQL(str, new Object[] { i });
		}
		database.close();
	}

	/**
	 * 根据工资id以及tab类型返回
	 */
	public EveryPayValueBean getParentBean(String payrollid, int usergroupingid) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		String str = "select * from tb_dx where payrollid =? and usegroupid = ?";
		Cursor cr = database.rawQuery(str, new String[] { payrollid, String.valueOf(usergroupingid) });
		EveryPayValueBean bean;
		if (cr.moveToNext()) {
			bean = new EveryPayValueBean();
			bean.setPayRollId(payrollid);
			bean.setUseGroupingId(usergroupingid);
			bean.setUseGroupingName(cr.getString(cr.getColumnIndex("usergroupname")));
			bean.setWages(cr.getString(cr.getColumnIndex("wages")));
			cr.close();
			database.close();
			return bean;

		} else {
			return null;
		}

	}

	public void deleteData() {
		String strSQL = "delete from tb_dx";
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.execSQL(strSQL);
		database.close();
	}
}
