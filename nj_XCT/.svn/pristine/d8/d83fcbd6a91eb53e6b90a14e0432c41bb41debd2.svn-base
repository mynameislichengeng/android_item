package com.xguanjia.hx.version;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
/**
 * SQLiteManager
 * @author ytg
 * @date 2012-09-13
 */
public class SQLiteManager {
	private static String DB_PATH = "/data/data/com.xguanjia.hx/databases/";
	private static final String TAG = "SQLiteManager";
	private SQLiteDatabase db;	
	private List<String> createTableSql = new ArrayList<String>();
	
	private List<String> alterTableSql = new ArrayList<String>();
	
	public SQLiteManager(SQLiteDatabase db) {
		super();
		db.setLocale(Locale.CHINA);
		this.db = db;
	}	
	
	public void setCreateTableSql(List<String> createTableSql) {
		this.createTableSql = createTableSql;
	}
	/**
	 * 判断数据库是否存在
	 * @author ytg
	 * @param ctx
	 * @param dbName
	 * @return exists
	 */
	public static boolean dbExist(Context ctx,String dbName){
		if(! dbName.endsWith(".db3")){
			dbName += ".db3"; 
		}
		File dbFile = ctx.getDatabasePath(dbName);
		if(dbFile.exists() == true){
			return true;
		}else{
			return false;
        }		
	}
	/**
	 * 创建数据库
	 * @param dataBaseName
	 * 支持auto-commit模式
	 */
	public static SQLiteDatabase createDatabase(String dataBaseName) {
		if(! dataBaseName.endsWith(".db3")){
			dataBaseName += ".db3"; 
		}	
		File dir = new File(DB_PATH);
		if( !dir.exists()){
			dir.mkdir();
		}
		return SQLiteDatabase.openOrCreateDatabase(DB_PATH+dataBaseName,null);		
	}	
	
	/**
	 * 删除数据库
	 * @param context
	 * @param dataBaseName
	 */
	public static void dropDatabase(Context context,String dataBaseName){
		if(! dataBaseName.endsWith(".db3")){
			dataBaseName += ".db3"; 
		}
		context.deleteDatabase(dataBaseName);
	}
	/**
	 * 创建表结构
 	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。 
	 * @param sql DDL语句 
	 */
	public void createTable(String sql){
		createTableSql.add(sql);
	}	
	/**
	 * 删除表结构
	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。
	 * @param tableName
	 */
	public void dropTable(String tableName){
		String sql = "DROP TABLE IF EXISTS "+tableName;
		createTableSql.add(sql);
	}
	
	/**
	 * 创建表结构
	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。
	 * @param table
	 */
	public void createTable(Table table){
		String sql = table.toSqlString();
		createTableSql.add(sql);	
	}
	
	/**
	 * 修改表字段结构 
	 * @param sql DDL语句 
	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。 
	 */
	public void alterTable(String sql){
		alterTableSql.add(sql);
	}
	
	/**
	 * 重新命名表
	 * @param srcTableName 表名
	 * @param target 目标表名
	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。
	 */
	public void renameTable(String srcTableName,String target){		
		String sql = "alter table "+srcTableName+" rename to "+target;
		alterTableSql.add(sql);
	}
	/**
	 * 添加字段
	 * @param tableName
	 * @param field
	 * auto-commit模式屏蔽掉。在auto-commit模式屏蔽掉之后，如果不调用commit()方法，
 	 * SQL语句不会得到sqlite事务处理确认。在最近一次commit()方法调用之后的所有SQL会在方法commit()调用时得到确认。
	 */
	public void addField(String tableName,Field field){		
		StringBuilder sb = new StringBuilder();			
		sb.append("ALTER table "+tableName+" ADD COLUMN ");
		sb.append(field.toSqlString());
		alterTableSql.add(sb.toString());
	}	
	
	/**
	 * 提交需要执行的数据库操作
	 */
	public void commit(){
		Log.i(TAG, "db is open ? "+db.isOpen());
		if(db.isOpen()){
			for(String sql : createTableSql){
				db.execSQL(sql);
			}
			for(String sql : alterTableSql){
				db.execSQL(sql);
			}
		}else{
			Log.i(TAG, "db is closed");
			throw new SQLException("db is closed");			
		}
		
	}
	
	/**
	 * 增加、删除、修改表时，调用此方法 
	 * @param sql DDL语句  
	 * @throws SQLException
	 */
	public void execute(String sql) {
		try{
			db.execSQL(sql);
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}
		
	}	
	
	/**
	 * 关闭数据库
	 * 不推荐调用该方法
	 */
	
	public void close(){
		close(db);
	}
	/**
	 * 关闭数据库
	 * 当确定不需要操作数据库时，需要调用该方法关闭数据
	 */
	public void close(SQLiteDatabase db){
		if(db != null){
			db.close();
		}
	}
	/**
	 * 打开数据库
	 * @param file
	 * @return db
	 */
	private static SQLiteDatabase openDB(String file) {   
	    try {	       
	        int flag = SQLiteDatabase.OPEN_READWRITE;   
	        flag = flag | SQLiteDatabase.CREATE_IF_NECESSARY;   
	        flag = flag | SQLiteDatabase.NO_LOCALIZED_COLLATORS;   
	        SQLiteDatabase db = SQLiteDatabase.openDatabase(file, null, flag);   
	        return db;   
	    } catch (Throwable e) {   
	        Log.e(TAG,"openDatabase error:" + e.getMessage());   
	        return null;   
	    }   
	} 
	/**
	 * 打开数据库
	 * @param dataBaseName
	 * @return db
	 */
	public static SQLiteDatabase openDataBase(String dataBaseName){
		if(! dataBaseName.endsWith(".db3")){
			dataBaseName += ".db3"; 
		}
		return openDB(DB_PATH+dataBaseName);		
	}
}
