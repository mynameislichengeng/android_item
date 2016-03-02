package com.xguanjia.hx.attachment.bean;

import java.io.Serializable;

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
    //文件名
	private String fileName = "";
    //文件类别
	private String fileType = "";
    //文件icon
	private int fileIcon = -1;
	//
	private String intentType = "";
	//本地附件路径
	private String localPath="";
	//服务器端附件路径
	private String serverPath="";
	// 应用附件路径
	private String appPath="";
	//创建时间
	private String createTime="";
	//文件大小
	private String fileSize="";
	
	private String fileUrl ="";
	
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getIntentType() {
		return intentType;
	}
	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}
	public AttachmentBean(){}
	
	/**
	 * @param primaryId
	 * @param fileName
	 * @param type
	 * @param localPath
	 * @param serverPath
	 * @param appPath
	 * @param createTime
	 */
	public AttachmentBean(String primaryId, String fileName, String fileType,
			String localPath, String serverPath, String appPath,
			String createTime) {
		super();
		this.primaryId = primaryId;
		this.fileName = fileName;
		this.fileType = fileType;
		this.localPath = localPath;
		this.serverPath = serverPath;
		this.appPath = appPath;
		this.createTime = createTime;
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
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
		
		if ("apk".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.android.package-archive");
			this.fileIcon = R.drawable.file_apk_icon;
		} else if ("ai".equalsIgnoreCase(fileType)) {
			this.fileIcon = R.drawable.file_ai_icon;
		} else if ("wav".equalsIgnoreCase(fileType) || "ogg".equalsIgnoreCase(fileType) || "mp3".equalsIgnoreCase(fileType) || "m4a".equalsIgnoreCase(fileType) || "ogg".equalsIgnoreCase(fileType)) {
			setIntentType("audio");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("3gp".equalsIgnoreCase(fileType) || "mp4".equalsIgnoreCase(fileType)) {
			setIntentType("video");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("rar".equalsIgnoreCase(fileType)) {
			setIntentType("application/x-rar-compressed");
			this.fileIcon = R.drawable.file_compressed_icon_rar;
		} else if ("zip".equalsIgnoreCase(fileType)) {
			setIntentType("application/zip");
			this.fileIcon = R.drawable.file_compressed_icon_7;
		} else if ("csv".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_csv_icon;
		} else if ("fla".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_fla_icon;
		} else if ("exe".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_exe_icon;
		} else if ("html".equalsIgnoreCase(fileType) || "htm".equalsIgnoreCase(fileType)) {
			setIntentType("text/html");
			this.fileIcon = R.drawable.file_html_icon;
		} else if ("java".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_java_icon;
		} else if ("pdf".equalsIgnoreCase(fileType)) {
			setIntentType("application/pdf");
			this.fileIcon = R.drawable.file_pdf_icon;
		} else if ("jpg".equalsIgnoreCase(fileType) || "jpeg".equalsIgnoreCase(fileType) || "png".equalsIgnoreCase(fileType) || "bmp".equalsIgnoreCase(fileType) || "gif".equalsIgnoreCase(fileType)) {
			setIntentType("image/*");
			this.fileIcon = R.drawable.file_picture_icon;
		} else if ("ppt".equalsIgnoreCase(fileType) || "pptx".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.ms-powerpoint");
			this.fileIcon = R.drawable.file_ppt_icon;
		} else if (".psd".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_psd_picture_icon;
		} else if ("xls".equalsIgnoreCase(fileType) || "xlsx".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.ms-excel");
			this.fileIcon = R.drawable.file_xls_icon;
		} else if ("doc".equalsIgnoreCase(fileType) || "docx".equalsIgnoreCase(fileType)) {
			setIntentType("application/msword");
			this.fileIcon = R.drawable.file_word_icon;
		} else if ("text".equalsIgnoreCase(fileType) || "txt".equalsIgnoreCase(fileType)) {
			setIntentType("text/plain");
			this.fileIcon = R.drawable.file_txt_icon;
		} else if ("swf".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_swf_icon;
		}else 
		if ("arm".equalsIgnoreCase(fileType)) {
			this.fileIcon = R.drawable.file_audio_icon;
			setIntentType("audio/amr");
//			holder.fileImg.setBackgroundResource(R.drawable.file_audio_icon);
		} 
		
	}
	public int getFileIcon() {
		return fileIcon;
	}
	public void setFileIcon(int fileIcon) {
		this.fileIcon = fileIcon;
	}
	
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public String getServerPath() {
		return serverPath;
	}
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setFileExt(String fileExt) {
		if (null == fileExt) {
			return;
		} else if ("apk".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.android.package-archive");
			this.fileIcon = R.drawable.file_apk_icon;
		} else if ("ai".equalsIgnoreCase(fileExt)) {
			this.fileIcon = R.drawable.file_ai_icon;
		} else if ("wav".equalsIgnoreCase(fileExt) || "ogg".equalsIgnoreCase(fileExt) || "mp3".equalsIgnoreCase(fileExt) || "m4a".equalsIgnoreCase(fileExt) || "ogg".equalsIgnoreCase(fileExt)) {
			setIntentType("audio");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("3gp".equalsIgnoreCase(fileExt) || "mp4".equalsIgnoreCase(fileExt)) {
			setIntentType("video");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("rar".equalsIgnoreCase(fileExt)) {
			setIntentType("application/x-rar-compressed");
			this.fileIcon = R.drawable.file_compressed_icon_rar;
		} else if ("zip".equalsIgnoreCase(fileExt)) {
			setIntentType("application/zip");
			this.fileIcon = R.drawable.file_compressed_icon_7;
		} else if ("csv".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_csv_icon;
		} else if ("fla".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_fla_icon;
		} else if ("exe".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_exe_icon;
		} else if ("html".equalsIgnoreCase(fileExt) || "htm".equalsIgnoreCase(fileExt)) {
			setIntentType("text/html");
			this.fileIcon = R.drawable.file_html_icon;
		} else if ("java".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_java_icon;
		} else if ("pdf".equalsIgnoreCase(fileExt)) {
			setIntentType("application/pdf");
			this.fileIcon = R.drawable.file_pdf_icon;
		} else if ("jpg".equalsIgnoreCase(fileExt) || "jpeg".equalsIgnoreCase(fileExt) || "png".equalsIgnoreCase(fileExt) || "bmp".equalsIgnoreCase(fileExt) || "gif".equalsIgnoreCase(fileExt)) {
			setIntentType("image/*");
			this.fileIcon = R.drawable.file_picture_icon;
		} else if ("ppt".equalsIgnoreCase(fileExt) || "pptx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.ms-powerpoint");
			this.fileIcon = R.drawable.file_ppt_icon;
		} else if (".psd".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_psd_picture_icon;
		} else if ("xls".equalsIgnoreCase(fileExt) || "xlsx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/vnd.ms-excel");
			this.fileIcon = R.drawable.file_xls_icon;
		} else if ("doc".equalsIgnoreCase(fileExt) || "docx".equalsIgnoreCase(fileExt)) {
			setIntentType("application/msword");
			this.fileIcon = R.drawable.file_word_icon;
		} else if ("text".equalsIgnoreCase(fileExt) || "txt".equalsIgnoreCase(fileExt)) {
			setIntentType("text/plain");
			this.fileIcon = R.drawable.file_txt_icon;
		} else if ("swf".equalsIgnoreCase(fileExt)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_swf_icon;
		}
	}
	
}
