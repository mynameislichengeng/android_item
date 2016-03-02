package com.xguanjia.hx.payroll.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.payroll.bean.PayRoll;

public class PayRollDb {
	private DatabaseHelper dbOpenHelper;

	public PayRollDb(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	// 工资等单项排序排序
	public List<PayRoll> getListData(String userGroupId) {

		String first_wen = userGroupId;// 如果：没有“，”
		String two_wen = userGroupId + ",%";// 如果：有“，”，且在第一个位置
		String three_wen = "%," + userGroupId;// 如果：有“，”，在最后一个位置
		String four_wen = "%," + userGroupId + ",%";// 如果：有“，”，不在首，也不在尾

		List<PayRoll> payRollBeanLists = new ArrayList<PayRoll>();
		Cursor cursor = null;
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		if ("0".equals(userGroupId)) {
			StringBuilder sql = new StringBuilder("select * from tb_payroll where userId = ? order by time desc,salaryUseGroupingId desc");
			cursor = database.rawQuery(sql.toString(), new String[] { Constants.userId });

		} else {
			// StringBuilder sql = new
			// StringBuilder("select * from tb_payroll where userId = ? and salaryUseGroupingId=? order by time desc");
			StringBuilder sql = new StringBuilder("select * from tb_payroll  where (( salaryUseGroupingId = ?) " + "or (salaryUseGroupingId like ?) or ( salaryUseGroupingId like ?) " + "or (salaryUseGroupingId like ?))  and userId = ? "
					+ "order by time desc,salaryUseGroupingId desc");

			cursor = database.rawQuery(sql.toString(), new String[] { first_wen, two_wen, three_wen, four_wen, Constants.userId });
		}
		while (cursor.moveToNext()) {
			PayRoll bean = new PayRoll();
			bean.setPayRollId(cursor.getString(cursor.getColumnIndex("id")));
			bean.setPayRollTitle(cursor.getString(cursor.getColumnIndex("title")));
			bean.setSalaryUseTypeId(cursor.getString(cursor.getColumnIndex("salaryUseTypeId")));
			bean.setPayRollMoney(cursor.getString(cursor.getColumnIndex("money")));
			bean.setPayRollTime(cursor.getString(cursor.getColumnIndex("time")));
			bean.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
			bean.setIsread(cursor.getString(cursor.getColumnIndex("isread")));
			bean.setSalaryUseGroupingId(cursor.getString(cursor.getColumnIndex("salaryUseGroupingId")));
			payRollBeanLists.add(bean);
		}
		cursor.close();
		database.close();

		return payRollBeanLists;

	}

	// 根据大类查询明细 lc
	public List<PayRoll> getPayRollList(String salaryUseGroupingId) {
		List<PayRoll> payRollBeanLists = new ArrayList<PayRoll>();
		Cursor cursor = null;
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

		try {

			if ("0".equals(salaryUseGroupingId)) {
				StringBuilder sql = new StringBuilder("select * from tb_payroll where userId = ? order by updateTime desc");
				cursor = database.rawQuery(sql.toString(), new String[] { Constants.userId });
			} else {
				StringBuilder sql = new StringBuilder("select * from tb_payroll where userId = ? and salaryUseGroupingId=? order by updateTime desc");
				cursor = database.rawQuery(sql.toString(), new String[] { Constants.userId, salaryUseGroupingId });
			}

			while (cursor.moveToNext()) {
				PayRoll bean = new PayRoll();
				bean.setPayRollId(cursor.getString(cursor.getColumnIndex("id")));
				bean.setPayRollTitle(cursor.getString(cursor.getColumnIndex("title")));
				bean.setSalaryUseTypeId(cursor.getString(cursor.getColumnIndex("salaryUseTypeId")));
				bean.setPayRollMoney(cursor.getString(cursor.getColumnIndex("money")));
				bean.setPayRollTime(cursor.getString(cursor.getColumnIndex("time")));
				bean.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
				bean.setIsread(cursor.getString(cursor.getColumnIndex("isread")));
				bean.setSalaryUseGroupingId(cursor.getString(cursor.getColumnIndex("salaryUseGroupingId")));
				payRollBeanLists.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
		return payRollBeanLists;
	}

	// 根据ID查询明细
	public List<PayRoll> getPayRollListForId(String id) {
		List<PayRoll> payRollBeanLists = new ArrayList<PayRoll>();
		Cursor cursor = null;
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder("select * from tb_payroll where userId = ? and id=? order by time desc");
		try {
			cursor = database.rawQuery(sql.toString(), new String[] { Constants.userId, id });
			while (cursor.moveToNext()) {
				PayRoll bean = new PayRoll();
				bean.setPayRollId(cursor.getString(cursor.getColumnIndex("id")));
				bean.setPayRollTitle(cursor.getString(cursor.getColumnIndex("title")));
				bean.setSalaryUseTypeId(cursor.getString(cursor.getColumnIndex("salaryUseTypeId")));
				bean.setPayRollMoney(cursor.getString(cursor.getColumnIndex("money")));
				bean.setPayRollTime(cursor.getString(cursor.getColumnIndex("time")));
				bean.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
				bean.setIsread(cursor.getString(cursor.getColumnIndex("isread")));
				bean.setSalaryUseGroupingId(cursor.getString(cursor.getColumnIndex("salaryUseGroupingId")));
				payRollBeanLists.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
		return payRollBeanLists;
	}

	/**
	 * 保存 添加
	 * 
	 */
	public void savePayRoll(PayRoll bean) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			String key = UUID.randomUUID().toString().replace("-", "");
			database.execSQL("insert into tb_payroll(primary_id,id,title,time,money,updateTime,isread,userId,salaryUseTypeId,salaryUseGroupingId) values(?,?,?,?,?,?,?,?,?,?)",
					new Object[] { key, bean.getPayRollId(), bean.getPayRollTitle(), bean.getPayRollTime(), bean.getPayRollMoney(), bean.getUpdateTime(), "0", Constants.userId, bean.getSalaryUseTypeId(), bean.getSalaryUseGroupingId() });
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	// 根据id更新读取的状态
	public void updateInfoById(String id) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		if (null != database) {
			try {
				StringBuilder sql = new StringBuilder("update tb_payroll set isread = ? where id = ?");
				database.execSQL(sql.toString(), new String[] { "1", id });
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				database.close();
			}
		}
	}

	// 获取时间戳
	public String time(String salaryUseTypeId) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		String timeStamp = "";
		Cursor cursor = database.rawQuery("select max(updateTime) from tb_payroll where userId = ?", new String[] { Constants.userId });
		cursor.moveToFirst();
		int count = cursor.getColumnCount();
		if (count > -1) {
			timeStamp = cursor.getString(0);
			cursor.close();
			database.close();
		} else {
			timeStamp = "";
			cursor.close();
			database.close();
		}
		return timeStamp;
	}

	// 查询未读数目
	public int getUnreadNum() {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = null;
		StringBuilder sql = new StringBuilder("select * from tb_payroll where isread = ?");
		cursor = database.rawQuery(sql.toString(), new String[] { "0" });
		int unreadnum = cursor.getCount();
		return unreadnum;
	}

	// 返回当前已有工资单的ID
	public String getParentIds() {
		String str = "";
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = null;
		StringBuilder sql = new StringBuilder("select id from tb_payroll");
		cursor = database.rawQuery(sql.toString(), null);
		if (cursor.getCount() != 0) {
			while (cursor.moveToNext()) {
				str = str + cursor.getString(cursor.getColumnIndex("id")) + ",";
			}
			str = str.substring(0, str.length() - 1);

		} else {
			return str;
		}

		return str;
	}

	// 根据后台返回的值，，来删除本地数据库中的ID
	public void deleteParenSalarys(List<Integer> list) {

		String str = "delete from tb_payroll where id =?";
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();

		for (int i = 0; i < list.size(); i++) {
			database.execSQL(str, new Object[] { list.get(i) });
		}
		database.close();

	}

	/**
	 * 删除数据库中的所有数据
	 */
	public void deleteData() {
		try {
			String strSQL = "delete from tb_payroll";
			SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
			database.execSQL(strSQL);
			database.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
