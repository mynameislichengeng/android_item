package com.haoqee.chatsdk.entity;

import java.io.Serializable;

/**
 * 聊天语音消息发送类
 *
 */
public class VoiceMessageBody extends BaseMessageBody implements Serializable{

	private static final long serialVersionUID = -124545465465L;
	
	private int voiceTime = 0; 			// 录音的时间
	private String voiceUrl = "";		//音频URL
	
	public VoiceMessageBody(){
		
	}
	
	/**
	 * 变量初始化
	 * @param filePath			音频文件路径
	 * @param len				音频长度
	 */
	public VoiceMessageBody(String filePath, int len){
		this.voiceUrl = filePath;
		this.voiceTime = len;
	}

	/**
	 * 设置音频的URL
	 * @param voiceUrl
	 */
	public void setVoiceUrl(String voiceUrl){
		this.voiceUrl = voiceUrl;
	}
	
	/**
	 * 获取音频URL
	 * @return
	 */
	public String getVoiceUrl(){
		return voiceUrl;
	}
	
	/**
	 * 设置音频长度
	 * @param len
	 */
	public void setVoiceTime(int len){
		this.voiceTime = len;
	}
	
	/**
	 * 获取音频长度
	 * @return
	 */
	public int getVoiceTime(){
		return voiceTime;
	}
}
