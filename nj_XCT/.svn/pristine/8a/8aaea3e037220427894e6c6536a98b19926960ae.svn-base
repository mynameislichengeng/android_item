package com.xguanjia.hx.attachment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xguanjia.hx.attachment.bean.AttachmentBean;
import com.xguanjia.hx.common.DatabaseHelper;

public class AttachmentBeanService {
	private static final String TAG = "AttachmentBeanService";
	private DatabaseHelper dbOpenHelper;
	/**
	 * @param dbOpenHelper
	 */
	public AttachmentBeanService(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	/**
	 * 保存AttachmentBean
	 * @param bean
	 */
	public String  saveAttachmentBean(AttachmentBean bean){
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		String key="";
		try{
			key = UUID.randomUUID().toString().replace("-", "");
			String sql = "insert into tb_attachment (primary_id,at_localpath,at_serverpath,at_appath,at_filetype,at_filesize,at_filename,at_createtime) values(?,?,?,?,?,?,?,?)";
			ContentValues cv=new ContentValues();
			cv.put("primary_id", key);
			cv.put("at_localpath", bean.getLocalPath());
			cv.put("at_serverpath", bean.getServerPath());
			cv.put("at_appath", bean.getAppPath());
			cv.put("at_filetype", bean.getFileType());
			cv.put("at_filesize", bean.getFileSize());
			cv.put("at_filename", bean.getFileName());
			cv.put("at_createtime", bean.getCreateTime());
			
			database.insert("tb_attachment", null, cv);
			//database.execSQL(sql, new Object[]{key,bean.getLocalPath(),bean.getServerPath(),bean.getAppPath(),bean.getFileType(),bean.getFileSize() ,bean.getFileName(),bean.getCreateTime()});
			database.setTransactionSuccessful();
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}finally {
			database.endTransaction();	
			database.close();
		}	
		return key;
	}
	
	public String  saveAttachmentList(List<AttachmentBean> attachBeans) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		String key="";
		StringBuffer ids=new StringBuffer("");
		try{
			for(AttachmentBean bean:attachBeans){
				if(!"".equals(bean.getFileId())&&bean.getFileId()!=null){
					key=bean.getFileId();
				}else{
					key = UUID.randomUUID().toString().replace("-", "");
				}
				ids.append(key+",");
				String sql = "insert into tb_attachment (primary_id,at_localpath,at_serverpath,at_appath,at_filetype,at_filesize,at_filename,at_createtime) values(?,?,?,?,?,?,?,?)";
				database.execSQL(sql, new Object[]{key,bean.getLocalPath(),bean.getServerPath(),bean.getAppPath(),bean.getFileType(),bean.getFileSize() ,bean.getFileName(),bean.getCreateTime()});
			}
			database.setTransactionSuccessful();
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}finally {
			database.endTransaction();	
			database.close();
		}	
		return ids.toString();
	}
	
	
	/**
	 * 根据附件id 获取附件
	 * @param ids
	 * @return
	 */
	public ArrayList<AttachmentBean> queryAttachmentBean(String idStr){	
		//String [] ids=idStr.split(",");
		String [] ids=idStr.split(",");
		idStr="";
		for(int i=0;i<ids.length;i++){
			idStr=idStr+"'"+ids[i]+"',";
		}
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		if((idStr == null) || (idStr.length() == 0)){
			return new ArrayList<AttachmentBean>();
		}
		
		StringBuilder sb = new StringBuilder("select * from tb_attachment where primary_id in (");
		sb.append(idStr);
		sb.replace(sb.length()-1, sb.length(), ")");
		String sql = sb.toString();
		Cursor cursor = database.rawQuery(sql, null);
		ArrayList<AttachmentBean> list = getAttachmentBeanList(cursor);
		cursor.close();
		database.close();
		return list;
	}
	
	private ArrayList<AttachmentBean> getAttachmentBeanList(Cursor cursor){
		ArrayList<AttachmentBean> list = new ArrayList<AttachmentBean>();
		while(cursor.moveToNext())
		{
			AttachmentBean bean = new AttachmentBean();		
			bean.setLocalPath(cursor.getString(cursor.getColumnIndex("at_localpath")));
			bean.setServerPath(cursor.getString(cursor.getColumnIndex("at_serverpath")));
			bean.setAppPath(cursor.getString(cursor.getColumnIndex("at_appath")));
			bean.setFileType(cursor.getString(cursor.getColumnIndex("at_filetype")));
			bean.setFileSize(cursor.getString(cursor.getColumnIndex("at_filesize")));
			bean.setFileName(cursor.getString(cursor.getColumnIndex("at_filename")));
			bean.setCreateTime(cursor.getString(cursor.getColumnIndex("at_createtime")));
			bean.setFileExt("."+bean.getFileType());
			list.add(bean);
		}
		return list;
	}
}
