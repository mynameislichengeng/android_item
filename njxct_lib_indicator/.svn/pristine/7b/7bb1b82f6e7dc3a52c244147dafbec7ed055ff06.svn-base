package com.haoqee.chatsdk.entity;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class TCMessage extends TCSNSMessage{
	private static final long serialVersionUID = -4274108350647182194L;

	private String id = "";																	//消息ID
	private String tag = "";																//消息标识符
	private int mChatType;																	//单聊100/群聊200/讨论组300
	private String toId = "";   															//本消息发送给谁
	private String fromId = ""; 															// 本消息来自 谁
	private int msgType;																	// 该消息的类型为什么.
	private int sendState = 0; 																// 消息发送成功与否的状态  1 成功, 2 正在发送， 4， 正在下载。0 失败
	private int readState = 0; 																// 读取消息的状态.		1--已读		0--未读
	private long time;   																	// 对方发送的时间
	private int isReadVoice = 0;															//语音消息阅读状态
	private int auto_id;
	private String mFromName = "";															//发送者昵称
	private String mFromHead = "";															//发送者头像
	private String mToName = "";															//接收方昵称
	private String mToHead = "";															//接收方头像
	
	private VoiceMessageBody mVoiceMessageBody;												//语音消息对象
	private TextMessageBody mTextMessageBody;												//文本消息对象
	private ImageMessageBody mImageMessageBody;												//图片消息对象
	private LocationMessageBody mLocationMessageBody;										//地址消息对象
	private FileMessageBody mFileMessageBody;												//语音消息对象
	private String mFromExtend = "";														//发送者扩展信息
	private String mToExtend = "";															//接收者扩展信息
	private String mMsgExtend = "";															//消息扩展信息
	private HashMap<String, String> mFromExtendMap = new HashMap<String, String>();			//发送者扩展信息Map
	private HashMap<String, String> mToExtendMap = new HashMap<String, String>();			//接收者扩展信息Map
	private HashMap<String, String> mMsgExtendMap = new HashMap<String, String>();			//消息扩展信息Map
	
	public TCMessage(){
		time = System.currentTimeMillis();
	}

	public TCMessage(String reString){
		try {
			JSONObject json = new JSONObject(reString);
			
			if(!json.isNull("id")){
				id = json.getString("id");
			}
			
			if(!json.isNull("tag")){
				tag = json.getString("tag");
			}
			
			if(!json.isNull("typechat")){
				mChatType = json.getInt("typechat");
			}
			
			fromId = json.getString("fromid");
			toId = json.getString("toid");
			if(!json.isNull("fromname")){
				mFromName = json.getString("fromname");
			}
			
			if(!json.isNull("fromhead")){
				mFromHead = json.getString("fromhead");
			}
			
			if(!json.isNull("toname")){
				mToName = json.getString("toname");
			}
			
			if(!json.isNull("tohead")){
				mToHead = json.getString("tohead");
			}
			
			if(!json.isNull("fromextend")){
				Object object = json.get("fromextend");
				if(object instanceof JSONObject){
					JSONObject fromExtend = (JSONObject) object;
					mFromExtend = fromExtend.toString();
					
					if(fromExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = fromExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mFromExtendMap.put(key, fromExtend.getString(key));
						}
					}
					
					if(mFromExtendMap.isEmpty()){
						mFromExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("toextend")){
				Object object = json.get("toextend");
				if(object instanceof JSONObject){
					JSONObject toExtend = (JSONObject) object;
					mToExtend = toExtend.toString();
					
					if(toExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = toExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mToExtendMap.put(key, toExtend.getString(key));
						}
					}
					
					if(mToExtendMap.isEmpty()){
						mToExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("extend")){
				Object object = json.get("extend");
				if(object instanceof JSONObject){
					JSONObject extend = (JSONObject) object;
					mMsgExtend = extend.toString();
					
					if(extend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = extend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mMsgExtendMap.put(key, extend.getString(key));
						}
					}
					
					if(mMsgExtendMap.isEmpty()){
						mMsgExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("time")){
				time = json.getLong("time");
			}
			
			if(!json.isNull("typefile")){
				msgType = json.getInt("typefile");
			}
			
			String bodyExtendStr = "";
			HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
			if(!json.isNull("bodyextend")){
				bodyExtendStr = json.getString("bodyextend");
				Object object = json.get("bodyextend") ;
				if(object instanceof JSONObject){
					JSONObject bodyExtend = (JSONObject) object;
					if(bodyExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = bodyExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							bodyExtendMap.put(key, bodyExtend.getString(key));
						}
					}
				}
			}
			
			if(bodyExtendMap.isEmpty()){
				bodyExtendStr = "";
			}
			
			if(msgType == TCMessageType.TEXT){
				String content = json.getString("content");
				mTextMessageBody = new TextMessageBody(content);
				mTextMessageBody.setExtendStr(bodyExtendStr);
				mTextMessageBody.setExtendMap(bodyExtendMap);
			}else if(msgType == TCMessageType.VOICE){
				if(!json.isNull("voice")){
					Object object = json.get("voice");
					if(object instanceof JSONObject){
						JSONObject voice = (JSONObject) object;
						
						int voiceTime = 0;
						String voiceUrl = "";
						if(!voice.isNull("time")){
							voiceTime = voice.getInt("time");
						}
						
						if(!voice.isNull("url")){
							voiceUrl = voice.getString("url");
						}
						
						mVoiceMessageBody = new VoiceMessageBody(voiceUrl, voiceTime);
						mVoiceMessageBody.setExtendStr(bodyExtendStr);
						mVoiceMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}else if(msgType == TCMessageType.PICTURE){
				if(!json.isNull("image")){
					Object object = json.get("image");
					if(object instanceof JSONObject){
						JSONObject image = (JSONObject) object;
						
						String imgUrlS = "";
						String imgUrlL = "";
						String imgWidth = "0";
						String imgHeight = "0";
						if(!image.isNull("urlsmall")){
							imgUrlS = image.getString("urlsmall");
						}
						
						if(!image.isNull("urllarge")){
							imgUrlL = image.getString("urllarge");
						}
						
						if(!image.isNull("width")){
							imgWidth = image.getString("width");
						}
						
						if(!image.isNull("height")){
							imgHeight = image.getString("height");
						}
						
						mImageMessageBody = new ImageMessageBody();
						mImageMessageBody.setImageUrlS(imgUrlS);
						mImageMessageBody.setImageUrlL(imgUrlL);
						mImageMessageBody.setImageSWidth(imgWidth);
						mImageMessageBody.setImageSHeight(imgHeight);
						
						mImageMessageBody.setExtendStr(bodyExtendStr);
						mImageMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}else if(msgType == TCMessageType.MAP){
				if(!json.isNull("location")){
					

					Object object = json.get("location");
					if(object instanceof JSONObject){
						JSONObject location = (JSONObject) object;
						double lat = location.getDouble("lat");
						double lng = location.getDouble("lng");
						String address = location.getString("address");
						mLocationMessageBody = new LocationMessageBody(lat, lng, address);
						
						mLocationMessageBody.setExtendStr(bodyExtendStr);
						mLocationMessageBody.setExtendMap(bodyExtendMap);
					}
				
				}
			}else if(msgType == TCMessageType.FILE){
				if(!json.isNull("file")){
					Object object = json.get("file");
					
					if(object instanceof JSONObject){
						JSONObject file = (JSONObject) object;
						String fileName = file.getString("name");
						long fileLen = file.getLong("size");
						String url = file.getString("url");
						mFileMessageBody = new FileMessageBody();
						
						mFileMessageBody.setFileName(fileName);
						mFileMessageBody.setFileLen(fileLen);
						mFileMessageBody.setFileUrl(url);
						mFileMessageBody.setExtendStr(bodyExtendStr);
						mFileMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public TCMessage (JSONObject json){
		try {
			
			if(!json.isNull("id")){
				id = json.getString("id");
			}
			
			if(!json.isNull("tag")){
				tag = json.getString("tag");
			}
			
			if(!json.isNull("typechat")){
				mChatType = json.getInt("typechat");
			}
			
			fromId = json.getString("fromid");
			toId = json.getString("toid");
			mFromName = json.getString("fromname");
			mFromHead = json.getString("fromhead");
			mToName = json.getString("toname");
			mToHead = json.getString("tohead");
			
			if(!json.isNull("fromextend")){
				Object object = json.get("fromextend");
				if(object instanceof JSONObject){
					JSONObject fromExtend = (JSONObject) object;
					mFromExtend = fromExtend.toString();
					
					if(fromExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = fromExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mFromExtendMap.put(key, fromExtend.getString(key));
						}
					}
					
					if(mFromExtendMap.isEmpty()){
						mFromExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("toextend")){
				Object object = json.get("toextend");
				if(object instanceof JSONObject){
					JSONObject toExtend = (JSONObject) object;
					mToExtend = toExtend.toString();
					
					if(toExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = toExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mToExtendMap.put(key, toExtend.getString(key));
						}
					}
					
					if(mToExtendMap.isEmpty()){
						mToExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("extend")){
				Object object = json.get("extend");
				if(object instanceof JSONObject){
					JSONObject extend = (JSONObject) object;
					mMsgExtend = extend.toString();
					
					if(extend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = extend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							mMsgExtendMap.put(key, extend.getString(key));
						}
					}
					
					if(mMsgExtendMap.isEmpty()){
						mMsgExtend = "";
					}
				}
				
			}
			
			if(!json.isNull("time")){
				time = json.getLong("time");
			}
			
			if(!json.isNull("typefile")){
				msgType = json.getInt("typefile");
			}
			
			String bodyExtendStr = "";
			HashMap<String, String> bodyExtendMap = new HashMap<String, String>();
			if(!json.isNull("bodyextend")){
				bodyExtendStr = json.getString("bodyextend");
				Object object = json.get("bodyextend") ;
				if(object instanceof JSONObject){
					JSONObject bodyExtend = (JSONObject) object;
					if(bodyExtend != null){
						@SuppressWarnings("unchecked")
						Iterator<String> keys = bodyExtend.keys();
						while(keys.hasNext()){
							String key = keys.next();
							bodyExtendMap.put(key, bodyExtend.getString(key));
						}
					}
				}
			}
			
			if(bodyExtendMap.isEmpty()){
				bodyExtendStr = "";
			}
			
			if(msgType == TCMessageType.TEXT){
				String content = json.getString("content");
				mTextMessageBody = new TextMessageBody(content);
				mTextMessageBody.setExtendStr(bodyExtendStr);
				mTextMessageBody.setExtendMap(bodyExtendMap);
			}else if(msgType == TCMessageType.VOICE){
				if(!json.isNull("voice")){
					Object object = json.get("voice");
					if(object instanceof JSONObject){
						JSONObject voice = (JSONObject) object;
						
						int voiceTime = 0;
						String voiceUrl = "";
						if(!voice.isNull("time")){
							voiceTime = voice.getInt("time");
						}
						
						if(!voice.isNull("url")){
							voiceUrl = voice.getString("url");
						}
						
						mVoiceMessageBody = new VoiceMessageBody(voiceUrl, voiceTime);
						mVoiceMessageBody.setExtendStr(bodyExtendStr);
						mVoiceMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}else if(msgType == TCMessageType.PICTURE){
				if(!json.isNull("image")){
					Object object = json.get("image");
					if(object instanceof JSONObject){
						JSONObject image = (JSONObject) object;
						
						String imgUrlS = "";
						String imgUrlL = "";
						String imgWidth = "0";
						String imgHeight = "0";
						if(!image.isNull("urlsmall")){
							imgUrlS = image.getString("urlsmall");
						}
						
						if(!image.isNull("urllarge")){
							imgUrlL = image.getString("urllarge");
						}
						
						if(!image.isNull("width")){
							imgWidth = image.getString("width");
						}
						
						if(!image.isNull("height")){
							imgHeight = image.getString("height");
						}
						
						mImageMessageBody = new ImageMessageBody();
						mImageMessageBody.setImageUrlS(imgUrlS);
						mImageMessageBody.setImageUrlL(imgUrlL);
						mImageMessageBody.setImageSWidth(imgWidth);
						mImageMessageBody.setImageSHeight(imgHeight);
						
						mImageMessageBody.setExtendStr(bodyExtendStr);
						mImageMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}else if(msgType == TCMessageType.MAP){
				if(!json.isNull("location")){

					Object object = json.get("location");
					if(object instanceof JSONObject){
						JSONObject location = (JSONObject) object;
						double lat = location.getDouble("lat");
						double lng = location.getDouble("lng");
						String address = location.getString("address");
						mLocationMessageBody = new LocationMessageBody(lat, lng, address);
						
						mLocationMessageBody.setExtendStr(bodyExtendStr);
						mLocationMessageBody.setExtendMap(bodyExtendMap);
					}
				
				}
			}else if(msgType == TCMessageType.FILE){
				if(!json.isNull("file")){
					Object object = json.get("file");
					
					if(object instanceof JSONObject){
						JSONObject file = (JSONObject) object;
						String fileName = file.getString("name");
						long fileLen = file.getLong("size");
						String url = file.getString("url");
						mFileMessageBody = new FileMessageBody();
						
						mFileMessageBody.setFileName(fileName);
						mFileMessageBody.setFileLen(fileLen);
						mFileMessageBody.setFileUrl(url);
						mFileMessageBody.setExtendStr(bodyExtendStr);
						mFileMessageBody.setExtendMap(bodyExtendMap);
					}
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取接收方ID
	 * @return
	 */
	public String getToId() {
		return toId;
	}

	/**
	 * 获取发送方ID
	 * @return
	 */
	public String getFromId() {
		return fromId;
	}
	
	/**
	 * 获取发送方名称
	 * @return
	 */
	public String getFromName() {
		return mFromName;
	}
	
	/**
	 * 获取发送方头像
	 * @return
	 */
	public String getFromHead() {
		return mFromHead;
	}
	
	/**
	 * 获取接收方名称
	 * @return
	 */
	public String getToName() {
		return mToName;
	}
	
	/**
	 * 获取接收方头像
	 * @return
	 */
	public String getToHead() {
		return mToHead;
	}
	
	/**
	 *获取消息ID
	 * @return
	 */
	public String getMessageID(){
		return id;
	}
	
	/**
	 * 获取消息唯一标示符TAG
	 * @return
	 */
	public String getMessageTag(){
		return tag;
	}
	
	/**
	 * 获取聊天类型
	 * @return
	 */
	public int getChatType(){
		return mChatType;
	}
	
	/**
	 * 获取自增ID
	 * @return
	 */
	public int getAutoId(){
		return auto_id;
	}
	
	/**
	 * 获取消息类型
	 * @return
	 */
	public int getMessageType(){
		return msgType;
	}

	/**
	 * 获取发送状态
	 * @return
	 */
	public int getSendState() {
		return sendState;
	}

	/**
	 * 获取阅读状态
	 * @return
	 */
	public int getReadState() {
		return readState;
	}
	
	/**
	 * 获取音频阅读状态
	 * @return
	 */
	public int getVoiceReadState() {
		return isReadVoice;
	}
	
	/**
	 * 获取消息发送时间
	 * @return
	 */
	public long getSendTime(){
		return time;
	}
	
	/**
	 * 获取文本消息体
	 * @return
	 */
	public TextMessageBody getTextMessageBody(){
		return mTextMessageBody;
	}
	
	/**
	 * 获取语音消息体
	 * @return
	 */
	public VoiceMessageBody getVoiceMessageBody(){
		return mVoiceMessageBody;
	}
	
	/**
	 * 获取位置消息体
	 * @return
	 */
	public LocationMessageBody getLocationMessageBody(){
		return mLocationMessageBody;
	}
	
	/**
	 * 获取图片消息体
	 * @return
	 */
	public ImageMessageBody getImageMessageBody(){
		return mImageMessageBody;
	}
	
	/**
	 * 获取文件消息体
	 * @return
	 */
	public FileMessageBody getFileMessageBody(){
		return mFileMessageBody;
	}
	
	/**
	 * 设置接收方ID
	 * @param toId
	 */
	public void setToId(String toId) {
		this.toId = toId;
	}

	/**
	 * 设置发送方ID
	 * @param fromId
	 */
	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	/**
	 * 设置发送方名称
	 * @param fromName
	 */
	public void setFromName(String fromName) {
		this.mFromName = fromName;
	}
	
	/**
	 * 设置发送方头像
	 * @param fromHead
	 */
	public void setFromHead(String fromHead) {
		this.mFromHead = fromHead;
	}
	
	/**
	 * 设置接收方名称
	 * @param toName
	 */
	public void setToName(String toName) {
		this.mToName = toName;
	}
	
	/**
	 * 设置接收方头像
	 * @param toHead
	 */
	public void setToHead(String toHead) {
		this.mToHead = toHead;
	}
	
	/**
	 * 设置消息ID
	 * @param id
	 */
	public void setMessageID(String id){
		this.id = id;
	}
	
	/**
	 * 设置消息唯一标示符
	 * @param tag
	 */
	public void setMessageTag(String tag){
		this.tag = tag;
	}
	
	/**
	 * 设置自增ID
	 * @param auto_id
	 */
	public void setAutoID(int auto_id){
		this.auto_id = auto_id;
	}

	/**
	 * 设置发送状态
	 * @param sendState			0--发送失败	1--发送成功	2--正在发送	
	 */
	public void setSendState(int sendState) {
		this.sendState = sendState;
	}

	/**
	 * 设置阅读状态
	 * @param readState				1--已读		0--未读
	 */
	public void setReadState(int readState) {
		this.readState = readState;
	}

	/**
	 * 设置聊天类型
	 * @param chatType			聊天类型
	 */
	public void setChatType(int chatType) {
		this.mChatType = chatType;
	}
	
	/**
	 * 设置消息类型
	 * @param msgType		消息类型
	 */
	public void setMessageType(int msgType) {
		this.msgType = msgType;
	}
	
	/**
	 * 设置音频阅读状态
	 * @param voiceReadState
	 */
	public void setVoiceReadState(int voiceReadState){
		this.isReadVoice = voiceReadState;
	}
	
	/**
	 * 设置消息发送时间
	 * @param time
	 */
	public void setSendTime(long time){
		this.time = time;
	}
	
	/**
	 * 设置文本消息对象
	 * @param body
	 */
	public void setTextMessageBody(TextMessageBody body){
		this.mTextMessageBody = body;
	}
	
	/**
	 * 设置语音消息对象
	 * @param body
	 */
	public void setVoiceMessageBody(VoiceMessageBody body){
		this.mVoiceMessageBody = body;
	}
	
	/**
	 * 设置地理位置消息对象
	 * @param body
	 */
	public void setLocationMessageBody(LocationMessageBody body){
		this.mLocationMessageBody = body;
	}
	
	/**
	 * 设置图片消息对象
	 * @param body
	 */
	public void setImageMessageBody(ImageMessageBody body){
		this.mImageMessageBody = body;
	}
	
	/**
	 * 设置文件消息对象
	 * @param body
	 */
	public void setFileMessageBody(FileMessageBody body){
		this.mFileMessageBody = body;
	}
	
	/**
	 * 添加消息体
	 * @param body
	 */
	public void addBody(Object body){
		if(body instanceof TextMessageBody){
			mTextMessageBody = (TextMessageBody) body;
		}else if(body instanceof VoiceMessageBody){
			mVoiceMessageBody = (VoiceMessageBody) body;
		}else if(body instanceof LocationMessageBody){
			mLocationMessageBody = (LocationMessageBody) body;
		}else if(body instanceof ImageMessageBody){
			mImageMessageBody = (ImageMessageBody) body;
		}else if(body instanceof FileMessageBody){
			mFileMessageBody = (FileMessageBody) body;
		}
	}
	
	/**
	 * 设置发送者扩展JSON数据
	 * @param extend				发送者扩展JSON字符串
	 */
	public void setFromExtendStr(String extend){
		mFromExtend = extend;
	}
	
	/**
	 * 设置接收者扩展JSON数据
	 * @param extend				接收者扩展JSON字符串
	 */
	public void setToExtendStr(String extend){
		mToExtend = extend;
	}
	
	/**
	 * 设置发送者扩展信息
	 * @param map				发送者扩展信息Map
	 */
	public void setFromExtendMap(HashMap<String, String> map){
		this.mFromExtendMap = map;
		
		if(mFromExtendMap != null && !mFromExtendMap.isEmpty()){
			mFromExtend = new JSONObject(mFromExtendMap).toString();
		}
	}

	/**
	 * 设置接收者扩展信息
	 * @param map				接收者扩展信息Map
	 */
	public void setToExtendMap(HashMap<String, String> map){
		this.mToExtendMap = map;
		if(mToExtendMap != null && !mToExtendMap.isEmpty()){
			mToExtend = new JSONObject(mToExtendMap).toString();
		}
	}
	
	/**
	 * 设置扩展消息JSON字符串
	 * @param extendStr			扩展消息JSON字符串
	 */
	public void setMessageExtendStr(String extendStr){
		this.mMsgExtend = extendStr;
	}
	
	/**
	 * 设置消息扩展信息
	 * @param map				消息扩展信息Map
	 */
	public void setMsgExtendMap(HashMap<String, String> map){
		this.mMsgExtendMap = map;
		if(mMsgExtendMap != null && !mMsgExtendMap.isEmpty()){
			mMsgExtend = new JSONObject(mMsgExtendMap).toString();
		}
	}
	
	/**
	 * 获取发送者扩展信息Map
	 * @return
	 */
	public HashMap<String, String> getFromExtendMap(){
		return mFromExtendMap;
	}
	
	/**
	 * 获取接收者扩展信息Map
	 * @return
	 */
	public HashMap<String, String> getToExtendMap(){
		return mToExtendMap;
	}
	
	/**
	 * 获取消息扩展信息Map
	 * @return
	 */
	public HashMap<String, String> getMsgExtendMap(){
		return mMsgExtendMap;
	}
	
	/**
	 * 获取发送者扩展信息JSON数据
	 * @return
	 */
	public String getFromExtendStr(){
		return mFromExtend;
	}
	
	/**
	 * 获取接收者扩展信息JSON数据
	 * @return
	 */
	public String getToExtendStr(){
		return mToExtend;
	}
	
	/**
	 * 获取消息扩展JSON数据
	 * @return
	 */
	public String getMessageExtendStr(){
		return mMsgExtend;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + tag.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TCMessage other = (TCMessage) obj;
		if (tag != other.tag)
			return false;
		return true;
	}

}
