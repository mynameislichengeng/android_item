package com.xguanjia.hx.notice.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.common.DatabaseHelper;

public class NoticeDb {
	
	private DatabaseHelper dbOpenHelper;

	public NoticeDb(Context context) {
		dbOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	//获取通知公告未读信息
		public int getUnnoticeNum(){
			SQLiteDatabase sd = dbOpenHelper.getReadableDatabase();
			Cursor cursor = null;
			StringBuilder sql = new StringBuilder("select * from tb_contact_notice where isread = ?");
			cursor = sd.rawQuery(sql.toString(), new String[]{"0"});
			int unnoticNum = cursor.getCount();
			return unnoticNum;
		}
		//根据id更新读取的状态
		public void updataNoticeState(String noticeId){
			SQLiteDatabase sd = dbOpenHelper.getReadableDatabase();
			try{
				StringBuilder sb = new StringBuilder("update tb_contact_notice set isread=? where id=?");
				sd.execSQL(sb.toString(), new Object[]{"1",noticeId});
			}catch(Exception e){
				e.getMessage();
			}finally{
				sd.close();
			}
		}
}
