package com.xguanjia.hx.login.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.login.bean.MsgTemplate;
import com.xguanjia.hx.login.bean.MsgTemplateType;

public class MsgTemplateService {
	private static final String TAG = "MsgTemplateService";
	private DatabaseHelper dbOpenHelper;
	public MsgTemplateService(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}
	
	/**
	 * 保存模板
	 * @param msgTemplateList
	 */
	public void saveMsgTemplate(List<MsgTemplate> msgTemplateList){
		//取得database
		SQLiteDatabase sqLiteDatabase=dbOpenHelper.getWritableDatabase();
		//开启事务
		sqLiteDatabase.beginTransaction();
		sqLiteDatabase.execSQL("delete from tb_msg_template");
		String primaryId = "";
		if(sqLiteDatabase!=null){
			try {
				for(MsgTemplate item:msgTemplateList){
					primaryId = UUID.randomUUID().toString().replace("-", "");
					sqLiteDatabase.execSQL("insert into tb_msg_template (primary_id ,template_name,msg_content,template_id,type_id) values(?,?,?,?,?)",
							new Object []{ primaryId,item.getTemplateName(),item.getMsgContent(),item.getId(),item.getTypeId()});
				}
				sqLiteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				sqLiteDatabase.endTransaction();
				sqLiteDatabase.close();
			}
		}
	}
	
	/**
	 * 保存模板类型
	 * @param msgTemplateTypeList
	 */
	public void saveMsgTemplateType(List<MsgTemplateType> msgTemplateTypeList){
		//取得database
		SQLiteDatabase sqLiteDatabase=dbOpenHelper.getWritableDatabase();
		//开启事务
		sqLiteDatabase.beginTransaction();
		sqLiteDatabase.execSQL("delete from tb_msg_template_type");
		String primaryId = "";
		if(sqLiteDatabase!=null){
			try {
				for(MsgTemplateType item:msgTemplateTypeList){
					primaryId = UUID.randomUUID().toString().replace("-", "");
					sqLiteDatabase.execSQL("insert into tb_msg_template_type (primary_id,type_name,type_id ) values(?,?,?)",
							new String []{ primaryId,item.getTemplateTypeName(),item.getId()});
				}
				sqLiteDatabase.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				sqLiteDatabase.endTransaction();
				sqLiteDatabase.close();
			}
		}
	}
	
	/**
	 * 根据类型查询模板
	 * @return ArrayList<MsgTemplate>
	 */
	public ArrayList<MsgTemplate> getMsgTemplateByTypeid(String typeId){
		ArrayList<MsgTemplate> msgTemplates=null;
		//取得database
		SQLiteDatabase sqLiteDatabase=dbOpenHelper.getReadableDatabase();
		if(sqLiteDatabase!=null){
			String sql="select template_name,template_id,msg_content from tb_msg_template where type_id=?";
			Cursor cursor=null;
			try {
				cursor=sqLiteDatabase.rawQuery(sql, new String []{typeId });
				msgTemplates=new ArrayList<MsgTemplate>(); 
				while(cursor.moveToNext()){
					MsgTemplate msgTemplate=new MsgTemplate();
					msgTemplate.setTemplateName(cursor.getString(0));
					msgTemplate.setId(cursor.getString(1));
					msgTemplate.setMsgContent(cursor.getString(2));
					msgTemplates.add(msgTemplate);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				cursor.close();
				sqLiteDatabase.close();
			}
		}
		return msgTemplates;
	}
	
	/**
	 * 获取所有模板类型
	 * @return ArrayList<MsgTemplateType>
	 */
	public ArrayList<MsgTemplateType> getAllType(){
		ArrayList<MsgTemplateType> types=null;
		//取得database
		SQLiteDatabase sqLiteDatabase=dbOpenHelper.getReadableDatabase();
		if(sqLiteDatabase!=null){
			
			Cursor cursor=null;
			try {
				String sql="select type_name,type_id from tb_msg_template_type";
				cursor=sqLiteDatabase.rawQuery(sql, null);
				types=new ArrayList<MsgTemplateType>();
				Log.d(TAG, cursor.getCount()+"") ;
				while(cursor.moveToNext()){
					MsgTemplateType type=new MsgTemplateType();
					type.setTemplateTypeName(cursor.getString(0));
					type.setId(cursor.getString(1));
					types.add(type);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally{
				cursor.close();
				sqLiteDatabase.close();
			}
		}
		return types;
	}
	
}
