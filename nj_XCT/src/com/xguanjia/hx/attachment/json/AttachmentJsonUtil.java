package com.xguanjia.hx.attachment.json;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.xguanjia.hx.attachment.bean.AttachmentBean;


public class AttachmentJsonUtil {
	private static final String TAG = "AttachBeanJsonUtil";
	
	
	public static AttachmentBean getAttachBean(JSONObject jsonObject){
		AttachmentBean bean=new AttachmentBean();
		try {
			bean.setFileName(jsonObject.isNull("fileName")?"": jsonObject.getString("fileName"));
//			bean.setFileId(jsonObject.isNull("fileId")?"": jsonObject.getString("fileId"));
//			bean.setFileName(jsonObject.isNull("fileOriginalName")?"": jsonObject.getString("fileOriginalName"));
			bean.setServerPath(jsonObject.isNull("fileUrl")?"": jsonObject.getString("fileUrl"));
			bean.setFileType(jsonObject.isNull("fileExt")?"": jsonObject.getString("fileExt").substring(jsonObject.getString("fileExt").lastIndexOf(".")+1));
//			bean.setFileExt(jsonObject.isNull("fileExt")?"": jsonObject.getString("fileExt"));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return bean;
	}
	
	
	public static ArrayList<AttachmentBean> getAttachList(JSONArray jsonArray){
		ArrayList<AttachmentBean> attachList=new ArrayList<AttachmentBean>();
		 try {			
				int size = jsonArray.length();
				for(int i = 0 ; i< size ;i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					AttachmentBean bean = getAttachBean(obj);
					if(bean != null){
						attachList.add(bean);					
					}
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}		
		 return attachList;
	}
	
/*	public static InfoAttachmentBean getInfoAttachBean(JSONObject jsonObject){
		InfoAttachmentBean bean=new InfoAttachmentBean();
		try {
			bean.setFileName(jsonObject.isNull("fileName")?"": jsonObject.getString("fileName"));
//			bean.setFileId(jsonObject.isNull("fileId")?"": jsonObject.getString("fileId"));
//			bean.setFileName(jsonObject.isNull("fileOriginalName")?"": jsonObject.getString("fileOriginalName"));
			bean.setFileUrl(jsonObject.isNull("fileUrl")?"": jsonObject.getString("fileUrl"));
			bean.setFileExt(jsonObject.isNull("fileExt")?"": jsonObject.getString("fileExt").substring(jsonObject.getString("fileExt").lastIndexOf(".")+1));
//			bean.setFileExt(jsonObject.isNull("fileExt")?"": jsonObject.getString("fileExt"));
		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return bean;
	}
	
	
	public static ArrayList<InfoAttachmentBean> getInfoAttachList(JSONArray jsonArray){
		ArrayList<InfoAttachmentBean> attachList=new ArrayList<InfoAttachmentBean>();
		 try {			
				int size = jsonArray.length();
				for(int i = 0 ; i< size ;i++){
					JSONObject obj = jsonArray.getJSONObject(i);
					InfoAttachmentBean bean = getInfoAttachBean(obj);
					if(bean != null){
						attachList.add(bean);					
					}
				}
			} catch (JSONException e) {
				Log.e(TAG, e.getMessage(), e);
			}		
		 return attachList;
	}*/
}
