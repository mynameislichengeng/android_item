package com.xguanjia.hx.attach;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.chinamobile.salary.R;

public class AttachmentBean implements Serializable{
	private static final String TAG = "AttachmentBean";
	/**
	 * 
	 */
	private static final long serialVersionUID = 5901907981208846489L;
	private String primaryId = "";    
	//文件ID
	private String fileId = "";
	//文件后缀名
	private String fileExt = "";
    //文件名
	private String fileName = "";
    //文件url
	private String fileUrl = "";
    //文件类别
	private String type = "";
    //文件icon
	private int fileIcon = -1;
	//
	private String intentType = "";
	
	public String getIntentType() {
		return intentType;
	}
	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}
	public AttachmentBean(){}
	public AttachmentBean(String fileId, String fileExt, String fileName,
			String fileUrl, String type, int fileIcon) {
		super();
		this.fileId = fileId;
		this.fileExt = fileExt;
		this.fileName = fileName;
		this.fileUrl = fileUrl;
		this.type = type;
		this.fileIcon = fileIcon;
		setFileExt(fileExt);
	}
	
	public String getPrimaryId() {
		return primaryId;
	}
	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileExt() {
		return fileExt;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getFileIcon() {
		return fileIcon;
	}
	public void setFileIcon(int fileIcon) {
		this.fileIcon = fileIcon;
	}
	
	public void setFileExt(String fileExt) {
		if (null == fileExt) {
			return;
		} else if (".apk".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.android.package-archive");
			this.fileIcon = R.drawable.file_apk_icon;
		} else if (".ai".equalsIgnoreCase(fileExt)) {
			this.fileIcon = R.drawable.file_ai_icon;
		} else if (".wav".equalsIgnoreCase(fileExt) || ".ogg".equalsIgnoreCase(fileExt) || ".mp3".equalsIgnoreCase(fileExt) || "m4a".equalsIgnoreCase(fileExt) || "ogg".equalsIgnoreCase(fileExt)) {
			setIntentType("audio");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if (".3gp".equalsIgnoreCase(fileExt) || ".mp4".equalsIgnoreCase(fileExt)) {
			setIntentType("video");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("rar".equalsIgnoreCase(fileExt)) {
			setIntentType("application/x-rar-compressed");
			this.fileIcon = R.drawable.file_compressed_icon_rar;
		} else if (".zip".equalsIgnoreCase(fileExt)) {
			setIntentType("application/zip");
			this.fileIcon = R.drawable.file_compressed_icon_7;
		} else if (".csv".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_csv_icon;
		} else if (".fla".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_fla_icon;
		} else if (".exe".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_exe_icon;
		} else if (".html".equalsIgnoreCase(fileExt) || ".htm".equalsIgnoreCase(fileExt)) {
			setIntentType("text/html");
			this.fileIcon = R.drawable.file_html_icon;
		} else if (".java".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_java_icon;
		} else if (".pdf".equalsIgnoreCase(fileExt)) {
			setIntentType("application/pdf");
			this.fileIcon = R.drawable.file_pdf_icon;
		} else if (".jpg".equalsIgnoreCase(fileExt) || ".jpeg".equalsIgnoreCase(fileExt) || ".png".equalsIgnoreCase(fileExt) || "bmp".equalsIgnoreCase(fileExt) || "gif".equalsIgnoreCase(fileExt)) {
			setIntentType("image/*");
			this.fileIcon = R.drawable.file_picture_icon;
		} else if (".ppt".equalsIgnoreCase(fileExt) || ".pptx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.ms-powerpoint");
			this.fileIcon = R.drawable.file_ppt_icon;
		} else if (".psd".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_psd_picture_icon;
		} else if (".xls".equalsIgnoreCase(fileExt) || ".xlsx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.ms-excel");
			this.fileIcon = R.drawable.file_xls_icon;
		} else if (".doc".equalsIgnoreCase(fileExt) || ".docx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/msword");
			this.fileIcon = R.drawable.file_word_icon;
		} else if (".text".equalsIgnoreCase(fileExt) || ".txt".equalsIgnoreCase(fileExt)) {
			setIntentType("text/plain");
			this.fileIcon = R.drawable.file_txt_icon;
		} else if (".swf".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_swf_icon;
		}
	}
	/**
	 * 构造方法
	 * @param json
	 */
	public AttachmentBean(JSONObject json) {
		this();
		try{
			if(json.has("fileId")){
				this.fileId = json.getString("fileId");
			}
			if(json.has("fileExt")){
				this.fileExt = json.getString("fileExt");
			}
			if(json.has("fileName")){
				this.fileName = json.getString("fileName");
			}
			if(json.has("fileUrl")){
				this.fileUrl = json.getString("fileUrl");
			}			
			setFileExt(fileExt);
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}		
		
	}
	/**
	 * 从JSONArray获取list
	 * @param ary
	 * @return list
	 */
	public static List<AttachmentBean> getAttachmentBean(JSONArray ary){
		List<AttachmentBean> list = new ArrayList<AttachmentBean>();
		int size = ary.length();
		try{
			for(int i=0;i<size;i++){
				JSONObject json = ary.getJSONObject(i);
				AttachmentBean attach = new AttachmentBean(json);
				list.add(attach);
			}
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}		
		return list;
	}
	
}
