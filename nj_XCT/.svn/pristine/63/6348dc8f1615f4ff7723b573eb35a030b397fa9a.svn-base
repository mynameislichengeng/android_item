package com.xguanjia.hx.attach;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class AttachmentBeanDao {
	private static final String TAG = "AttachmentBeanDao";
	
	private SQLiteDatabase database;
	
	public AttachmentBeanDao(SQLiteDatabase database) {
		super();
		this.database = database;
	}
	
	public SQLiteDatabase getDatabase() {
		return database;
	}
	public void setDatabase(SQLiteDatabase database) {
		this.database = database;
	}
	/**
	 * 保存AttachmentBean
	 * @param bean
	 */
	public void saveAttachmentBean(AttachmentBean bean){
		try{
			database.beginTransaction();
			String key = UUID.randomUUID().toString().replace("-", "");
			String sql = "insert into tb_attach(primary_id,fileId,fileExt,fileName,fileUrl,type,fileIcon,intentType) values(?,?,?,?,?,?,?,?)";
			database.execSQL(sql, new Object[]{key,bean.getFileId(),bean.getFileExt(),bean.getFileName(),bean.getFileUrl(),bean.getType(),bean.getFileIcon(),bean.getIntentType()});
			database.setTransactionSuccessful();
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}finally {
			database.endTransaction();		
		}		
	}
	
	public List<AttachmentBean> queryAttachmentBean(String[] ids){	
		if((ids == null) || (ids.length == 0)){
			return null;
		}
		StringBuilder sb = new StringBuilder("select * from tb_attach where fileId in (");
		for(String id : ids){
			sb.append("'"+id+"' ,");			
		}
		sb.replace(sb.length()-1, sb.length(), ")");
		String sql = sb.toString();
		Cursor cursor = database.rawQuery(sql, null);
		List<AttachmentBean> list = getAttachmentBeanList(cursor);
		cursor.close();
		return list;
	}
	private List<AttachmentBean> getAttachmentBeanList(Cursor cursor){
		List<AttachmentBean> list = new ArrayList<AttachmentBean>();
		while(cursor.moveToNext()){
			AttachmentBean bean = new AttachmentBean();			
			bean.setFileId(cursor.getString(cursor.getColumnIndex("fileId")));
			bean.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
			bean.setFileExt(cursor.getString(cursor.getColumnIndex("fileExt")));
			bean.setFileIcon(cursor.getInt(cursor.getColumnIndex("fileIcon")));
			bean.setFileUrl(cursor.getString(cursor.getColumnIndex("fileUrl")));
			bean.setIntentType(cursor.getString(cursor.getColumnIndex("intentType")));
			bean.setType(cursor.getString(cursor.getColumnIndex("type")));
			list.add(bean);
		}
		return list;
	}
	
	
	/**
	 * 保存List<AttachmentBean>
	 * @param list
	 */
	public void saveAttachments(List<AttachmentBean> list){
		for(AttachmentBean bean : list){
			saveAttachmentBean(bean);
		}
	}
	/**
	 * 获取id
	 * @param list
	 * @return
	 */
	public String[] getArrayIds(List<AttachmentBean> list){
		int size = list.size();
		if(size < 1){
			return null;
		}
		String[] strs = new String[size];
		for(int i = 0 ; i < size ; i++){
			String id = list.get(i).getFileId();
			strs[i] = id;
		}
		return strs;
	}
	
	/**
	 * 获取id
	 */
	public String getStringIds(List<AttachmentBean> list){
		int size = list.size();
		if(size < 1){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < size ; i++){
			String id = list.get(i).getFileId();
			sb.append(id);
			sb.append(",");
		}
		return sb.toString();
	}
	
	/**
	 * 关闭数据库连接
	 * @param db
	 */
	public void close(SQLiteDatabase db){
		if((db != null) && db.isOpen()){
			db.close();
		}
	}
}
