package com.haoqee.chatsdk.DB;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TCDBHelper extends SQLiteOpenHelper{
	private SQLiteDatabase mDB = null;
	private static TCDBHelper mInstance = null;
	public static final String DataBaseName = "HaoqeechatSdk.db";
	public static final int DataBaseVersion = 2;

	public TCDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		if (null == mDB) {
			mDB = db;
		}
		db.execSQL(TCSessionTable.getCreateTableSQLString());
		db.execSQL(TCMessageTable.getCreateTableSQLString());
		db.execSQL(TCNotifyTable.getCreateTableSQLString());
		db.execSQL(TCNotifyRoomTable.getCreateTableSQLString());
		db.execSQL(TCNotifyUserTable.getCreateTableSQLString());
		db.execSQL(TCGroupTable.getCreateTableSQLString());
		/**
		 * 添加一个首页的欢迎信息
		 *  **/
		
	}
	
	public synchronized static TCDBHelper getInstance(Context context){
		if (mInstance == null) {
			mInstance = new TCDBHelper(context, DataBaseName, null, DataBaseVersion);
		}
		
		return mInstance;
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TCSessionTable.getDeleteTableSQLString());
		db.execSQL(TCMessageTable.getDeleteTableSQLString());
		db.execSQL(TCNotifyTable.getDeleteTableSQLString());
		db.execSQL(TCNotifyRoomTable.getDeleteTableSQLString());
		db.execSQL(TCNotifyUserTable.getDeleteTableSQLString());
		db.execSQL(TCGroupTable.getDeleteTableSQLString());
		onCreate(db);
	}
	
	@Override
	public synchronized void close() {
		if (mDB != null){
			mDB.close();
		}
		super.close();
	}
}
