package com.haoqee.chatsdk.entity;

import java.io.File;
import java.io.Serializable;

/**
 * 聊天语音消息发送类
 *
 */
public class FileMessageBody extends BaseMessageBody implements Serializable{

	private static final long serialVersionUID = -1245454654652L;
	
	private long mFileLen = 0; 				//文件大小
	private String mFileUrl = "";			//文件路径或URL
	private String mFileName = "";			//文件名
	private int mUploadProgress = 0;		//文件上传进度
	
	public FileMessageBody(){
		
	}
	
	/**
	 * 变量初始化
	 * @param filePath			音频文件路径
	 * @param len				音频长度
	 */
	public FileMessageBody(String filePath){
		this.mFileUrl = filePath;
		
		File file = new File(filePath);
		this.mFileName = file.getName();
		this.mFileLen = file.length();
	}

	/**
	 * 设置文件的URL
	 * @param voiceUrl
	 */
	public void setFileUrl(String fileUrl){
		this.mFileUrl = fileUrl;
	}
	
	/**
	 * 获取文件URL
	 * @return
	 */
	public String getFileUrl(){
		return mFileUrl;
	}
	
	/**
	 * 设置文件名
	 * @param voiceUrl
	 */
	public void setFileName(String fileName){
		this.mFileName = fileName;
	}
	
	/**
	 * 获取文件名
	 * @return
	 */
	public String getFileName(){
		return mFileName;
	}
	
	/**
	 * 设置文件长度
	 * @param len
	 */
	public void setFileLen(long len){
		this.mFileLen = len;
	}
	
	/**
	 * 获取文件长度
	 * @return
	 */
	public long getFileLen(){
		return mFileLen;
	}
	
	/**
	 * 设置文件上传进度
	 * @param len
	 */
	public void setUploadProgress(int progress){
		this.mUploadProgress = progress;
	}
	
	/**
	 * 获取文件上传进度
	 * @return
	 */
	public int getUploadProgress(){
		return mUploadProgress;
	}
}
