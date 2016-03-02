package com.haoqee.chatsdk.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.Interface.TCGroupDetailListener;
import com.haoqee.chatsdk.Interface.TCGroupListListener;
import com.haoqee.chatsdk.Interface.TCMenberListListener;
import com.haoqee.chatsdk.Interface.TCMessageListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageResult;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TCMorePicture;
import com.haoqee.chatsdk.entity.TCPageInfo;
import com.haoqee.chatsdk.entity.TCState;
import com.haoqee.chatsdk.entity.TCUser;
import com.haoqee.libs.R;

public class HaoqeeChatSdkInfo  implements Serializable{
	private static final long serialVersionUID = 1651654562644564L;
	
	private String SERVER = "http://121.199.59.82:8083/index.php";
	public static final int LOAD_SIZE = 20;
	
	
	public void setServer(String server){
		this.SERVER = server;
	}
	
	/**
	 * 网络访问入口函数
	 * @param url							请求的url
	 * @param params						请求的参数数组
	 * @param httpMethod					请求的方式
	 * @param loginType						是否登录				0--不登陆			1--需要登录			2--两种都可以
	 * @param listener						错误回调监听
	 * @return
	 * @throws BridgeException
	 */
	public String request(String url, BaseParameters params, String httpMethod, int loginType, TCBaseListener listener) throws HaoqeeChatException{
        String rlt = null;
        rlt = Utility.openUrl(url, httpMethod, params, loginType, listener);
        
        return rlt;
        
    }
	
	/**
	 * 发送消息接口
	 * @param message					消息体对象
	 * @param listener					发送消息回调
	 * @return
	 * @throws HaoqeeChatException
	 */
	public TCMessageResult sendMessage(TCMessage message, TCMessageListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.send_message_failed);
		bundle.add("typechat", String.valueOf(message.getChatType()));
		bundle.add("tag", message.getMessageTag());
		
		if(!TextUtils.isEmpty(message.getFromName())){
			bundle.add("fromname", message.getFromName());
		}
		
		if(!TextUtils.isEmpty(message.getFromHead())){
			bundle.add("fromhead", message.getFromHead());
		}
		
		if(!TextUtils.isEmpty(message.getFromExtendStr())){
			bundle.add("fromextend", message.getFromExtendStr());
		}
		
		bundle.add("toid", message.getToId());
		
		if(!TextUtils.isEmpty(message.getToName())){
			bundle.add("toname", message.getToName());
		}
		
		if(!TextUtils.isEmpty(message.getToHead())){
			bundle.add("tohead", message.getToHead());
		}
		
		if(!TextUtils.isEmpty(message.getToExtendStr())){
			bundle.add("toextend", message.getToExtendStr());
		}
		
		bundle.add("typefile", String.valueOf(message.getMessageType()));
		
		String bodyExtendStr = "";
		if(message.getMessageType() == TCMessageType.TEXT){
			bundle.add("content", message.getTextMessageBody().getContent());
			bodyExtendStr = message.getTextMessageBody().getExtendStr();
		}else if(message.getMessageType() == TCMessageType.VOICE){
			bodyExtendStr = message.getVoiceMessageBody().getExtendStr();
			if(message.getVoiceMessageBody() != null && !TextUtils.isEmpty(message.getVoiceMessageBody().getVoiceUrl())){
				List<TCMorePicture> fileList = new ArrayList<TCMorePicture>();
				fileList.add(new TCMorePicture("image", message.getVoiceMessageBody().getVoiceUrl()));
				bundle.addPicture("fileList", fileList);
				bundle.add("voicetime", String.valueOf(message.getVoiceMessageBody().getVoiceTime()));
			}else {
				error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.please_input_voice_path);
				if(listener != null){
					listener.onError(error);
				}
				return null;
			}

		}else if(message.getMessageType() == TCMessageType.PICTURE){
			bodyExtendStr = message.getImageMessageBody().getExtendStr();
			if(message.getImageMessageBody() != null && !TextUtils.isEmpty(message.getImageMessageBody().getImageUrlS())){
				List<TCMorePicture> fileList = new ArrayList<TCMorePicture>();
				fileList.add(new TCMorePicture("image", message.getImageMessageBody().getImageUrlS()));
				bundle.addPicture("fileList", fileList);
			}else {
				error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.please_input_picture_path);
				if(listener != null){
					listener.onError(error);
				}
				return null;
			}
			
		}else if(message.getMessageType() == TCMessageType.MAP ){
			bodyExtendStr = message.getLocationMessageBody().getExtendStr();
			if(message.getLocationMessageBody() != null){
				bundle.add("lat", String.valueOf(message.getLocationMessageBody().getLocaitonLat()));
				bundle.add("lng", String.valueOf(message.getLocationMessageBody().getLocaitonLng()));
				bundle.add("address", message.getLocationMessageBody().getLocationAddr());
			}else {
				error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.please_input_location_info);
				if(listener != null){
					listener.onError(error);
				}
				return null; 
			}
			
		}else if(message.getMessageType() == TCMessageType.FILE){
			bodyExtendStr = message.getFileMessageBody().getExtendStr();
			if(message.getFileMessageBody() != null && !TextUtils.isEmpty(message.getFileMessageBody().getFileUrl())){
				List<TCMorePicture> fileList = new ArrayList<TCMorePicture>();
				fileList.add(new TCMorePicture("image", message.getFileMessageBody().getFileUrl()));
				bundle.addPicture("fileList", fileList);
			}else {
				error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.please_input_file_path);
				if(listener != null){
					listener.onError(error);
				}
				return null;
			}
		}
		
		if(!TextUtils.isEmpty(bodyExtendStr)){
			bundle.add("bodyextend", bodyExtendStr);
		}
		
		if(!TextUtils.isEmpty(message.getMessageExtendStr())){
			bundle.add("extend", message.getMessageExtendStr());
		}
		
		String url = SERVER + "/message/api/sendMessage";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, listener);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			Log.e("SEND_MESSAGE_RESULT", reString);
			return new TCMessageResult(reString);
		}
		
		return null;
	}
	
	/**
	 * 创建群接口
	 * @param groupName						群名称		必传
	 * @param logo							群头像		非必传
	 * @param content						群简介		必传
	 * @param listener						返回结果监听对象	必传
	 * @throws HaoqeeChatException			
	 */
	public void createGroup(String groupName, String logo, String content, HashMap<String, String> extendMap, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		TCError error = new TCError();
		bundle.add("name", groupName);
		if(!TextUtils.isEmpty(logo)){
			List<TCMorePicture> fileList = new ArrayList<TCMorePicture>();
			fileList.add(new TCMorePicture("pic", logo));
			bundle.addPicture("fileList", fileList);
		}
		
		String extend = "";
		if(extendMap != null && !extendMap.isEmpty()){
			extend = new JSONObject(extendMap).toString();
		}
		if(!TextUtils.isEmpty(extend)){
			bundle.add("extend", extend);
		}
		
		if(!TextUtils.isEmpty(content)){
			bundle.add("description", content);
		}
		
		String url = SERVER + "/group/api/add";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, listener);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
						
						if(listener != null){
							listener.onError(error);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 获取我的群列表	
	 * @param listener				接口回调对象
	 * @throws HaoqeeChatException
	 */
	public void getGroupList(TCGroupListListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
		String url = SERVER + "/group/api/groupList";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, listener);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state  = new TCState(json.getJSONObject("state"));
					
					if(state != null && state.code == 0){
						List<TCGroup> groupList = null;
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONArray){
								JSONArray array = json.getJSONArray("data");
								if(array != null && array.length() != 0){
									groupList = new ArrayList<TCGroup>();
									for (int i = 0; i < array.length(); i++) {
										groupList.add(new TCGroup(array.getJSONObject(i)));
									}
									
									if(groupList != null && groupList.size() != 0){
										TCChatManager.getInstance().insertGroupList(groupList, ChatType.GROUP_CHAT);
									}
								}
							}
						}
						
						if(listener != null){
							listener.doComplete(groupList);
						}
						return;
					}
					
					if(state != null){
						error.errorCode = state.code;
						if(!TextUtils.isEmpty(state.errorMsg)){
							error.errorMessage = state.errorMsg;
						}
					}
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 获取群详细接口
	 * @param groupid					群ID					必传
	 * @param listener					结果回调监听对象			必传
	 * @throws HaoqeeChatException
	 */
	public void getGroupDetail(String groupid, TCGroupDetailListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
		
		bundle.add("groupid", groupid);
		
		String url = SERVER + "/group/api/detail";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, listener);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state  = new TCState(json.getJSONObject("state"));
					
					if(state != null && state.code == 0){
						TCGroup group = null;
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONObject){
								JSONObject object = (JSONObject)data;
								group = new TCGroup(object);
								
							}
						}

						if(listener != null){
							listener.doComplete(group);
						}
						return;
					}
					
					if(state != null){
						error.errorCode = state.code;
						if(!TextUtils.isEmpty(state.errorMsg)){
							error.errorMessage = state.errorMsg;
						}
					}
					
				}else {
					error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 搜索群接口
	 * @param keyword						群ID/群名称		必传
	 * @param listener						结果监听回调对象		必传
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void searchGroup(String keyword, int page, TCGroupListListener listener) throws HaoqeeChatException {
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
		
		bundle.add("search", keyword);
		
		bundle.add("page", page + "");
		bundle.add("pageSize", String.valueOf(LOAD_SIZE));
		
		String url = SERVER + "/group/api/search";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state  = new TCState(json.getJSONObject("state"));
					
					if(state != null && state.code == 0){
						List<TCGroup> groupList = null;
						TCPageInfo pageInfo = null; 
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONArray){
								JSONArray array = json.getJSONArray("data");
								groupList = new ArrayList<TCGroup>();
								if(array != null && array.length() != 0){
									for (int i = 0; i < array.length(); i++) {
										groupList.add(new TCGroup(array.getJSONObject(i)));
									}
									
								}
								
								if(!json.isNull("pageInfo")){
									pageInfo = new TCPageInfo(json.getJSONObject("pageInfo"));
								}
								
							}
						}
						
						if(listener != null){
							listener.doComplete(groupList, pageInfo);
						}
						return;
					}
					
					if(state != null){
						error.errorCode = state.code;
						if(!TextUtils.isEmpty(state.errorMsg)){
							error.errorMessage = state.errorMsg;
						}
					}
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 设置群消息是否接收
	 * @param groupid				群ID				必传
	 * @param getmsg				是否接收消息		必传		0--不接收		1--接收
	 * @param listener				结果监听回调		必传
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void setMsgType(String groupid, String getmsg, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("getmsg", getmsg);
		
		String url = SERVER + "/group/api/getmsg";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
						
						if(listener != null){
							listener.onError(error);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 编辑群资料
	 * @param groupid				群ID				必传
	 * @param groupName				群名称			非必传
	 * @param logo					群头像			非必传
	 * @param content				群简介			非必传
	 * @param listener				结果回调对象		必传
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void editGroup(String groupid, String groupName, String logo, String content, final HashMap<String, String> extendMap, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("name", groupName);
		if(!TextUtils.isEmpty(logo)){
			List<TCMorePicture> fileList = new ArrayList<TCMorePicture>();
			fileList.add(new TCMorePicture("pic", logo));
			bundle.addPicture("fileList", fileList);
		}
		
		String extend = "";
		if(extendMap != null && !extendMap.isEmpty()){
			extend = new JSONObject(extendMap).toString();
		}
		if(!TextUtils.isEmpty(extend)){
			bundle.add("extend", extend);
		}
		
		bundle.add("description", content);
		
		String url = SERVER + "/group/api/edit";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
		
	}
	
	/**
	 * 获取群的成员列表
	 * @param groupid				群ID			必传
	 * @param listener				成员列表结果监听回调对象
	 * @return		
	 * @throws HaoqeeChatException
	 */
	public void getMenberList(String groupid, int page, TCMenberListListener listener) throws HaoqeeChatException {
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
		
		bundle.add("groupid", groupid);
		bundle.add("page", page + "");
		bundle.add("pageSize", String.valueOf(LOAD_SIZE));
		
		String url = SERVER + "/group/api/groupUserList";
		
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state  = new TCState(json.getJSONObject("state"));
					
					if(state != null && state.code == 0){
						List<TCUser> userList = null;
						TCPageInfo pageInfo = null; 
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONArray){
								JSONArray array = json.getJSONArray("data");
								userList = new ArrayList<TCUser>();
								if(array != null && array.length() != 0){
									for (int i = 0; i < array.length(); i++) {
										userList.add(new TCUser(array.getJSONObject(i)));
									}
									
								}
								
								if(!json.isNull("pageInfo")){
									pageInfo = new TCPageInfo(json.getJSONObject("pageInfo"));
								}
								
							}
						}
						
						if(listener != null){
							listener.doComplete(userList, pageInfo);
						}
						return;
					}
					
					if(state != null){
						error.errorCode = state.code;
						if(!TextUtils.isEmpty(state.errorMsg)){
							error.errorMessage = state.errorMsg;
						}
					}
					
				}
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 申请加入群
	 * @param groupid					待加入的群ID
	 * @param listener					结果回调监听对象
	 * @return		接口访问状态
	 * @throws HaoqeeChatException
	 */
	public void applyAddGroup(String groupid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		
		String url = SERVER + "/group/api/apply";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 同意申请加入群
	 * @param groupid					所加入的群ID
	 * @param fuid						被加入群的用户ID
	 * @param listener					结果回调监听对象
	 * @return	结果状态信息
	 * @throws HaoqeeChatException
	 */
	public void agreeAddGroup(String groupid, String fuid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();

		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("fuid", fuid);
		
		String url = SERVER + "/group/api/agreeApply";
		
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 拒绝用户加入群的申请
	 * @param groupid					群ID					
	 * @param fuid						拒绝的用户ID
	 * @param listener					结果回调监听对象
	 * @return 接口状态信息
	 * @throws HaoqeeChatException
	 */
	public void	refuseAddGroup(String groupid, String fuid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("fuid", fuid);
		
		String url = SERVER + "/group/api/disagreeApply";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 邀请用户加入群
	 * @param groupid				群ID
	 * @param fuid					被邀请的用户ID
	 * @param listener					结果回调监听对象
	 * @return	接口状态信息
	 * @throws HaoqeeChatException
	 */
	public void inviteAddGroup(String groupid, String fuid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("fuid", fuid);
		
		String url = SERVER + "/group/api/invite";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 同意邀请
	 * @param id
	 * @param listener					结果回调监听对象
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void agreeInvite(String id, TCBaseListener listener) throws HaoqeeChatException {
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", id);
		
		String url = SERVER + "/group/api/agreeInvite";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 拒绝邀请
	 * @param id
	 * @param listener					结果回调监听对象
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void refuseInvite(String id, TCBaseListener listener) throws HaoqeeChatException {
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", id);
		
		String url = SERVER + "/group/api/disagreeInvite";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
						
						if(listener != null){
							listener.onError(error);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 用户退出群
	 * @param groupid				群ID				必传
	 * @param listener					结果回调监听对象
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void exitGroup(String groupid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		
		String url = SERVER + "/group/api/quit";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 管理员删除群
	 * @param groupid				群ID 			必传
	 * @param listener					结果回调监听对象
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void deleteGroup(String groupid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		
		String url = SERVER + "/group/api/delete";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
				
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 管理员删除群
	 * @param groupid				群ID 			必传
	 * @param fuid					被踢用户ID			必传
	 * @param listener				结果回调监听对象
	 * @return
	 * @throws HaoqeeChatException
	 */
	public void kickGroupMenber(String groupid, String fuid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("groupid", groupid);
		bundle.add("fuid", fuid);
		
		String url = SERVER + "/group/api/remove";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
						
						if(listener != null){
							listener.onError(error);
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 创建临时会话接口
	 * @param name									临时会话名称					必传
	 * @param uids									临时会话中添加的用户ID串			必传
	 * @param listener								结果监听回调对象					必传
	 * @throws HaoqeeChatException
	 */
	public void createTempChat(String name, String uids, TCGroupDetailListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("name", name);
		bundle.add("uids", uids);
		
		String url = SERVER + "/session/api/add";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONObject){
								JSONObject object = (JSONObject)data;
								TCGroup group = new TCGroup(object);
								
								TCChatManager.getInstance().insertGroup(group, ChatType.TEMP_CHAT);
								
								if(listener != null){
									listener.doComplete(group);
								}
								return;
							}
						}
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 添加用户到临时会话
	 * @param id								临时会话ID						必传
	 * @param uids								待加入的用户ID串					必传
	 * @param listener							结果监听回调对象					必传
	 * @throws HaoqeeChatException
	 */
	public void addPersonToTempChat(String id, String uids, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		bundle.add("uids", uids);
		
		String url = SERVER + "/session/api/addUserToSession";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 获取临时会话详情接口
	 * @param id						临时会话ID					必传
	 * @param listener					结果监听回调对象				必传
	 * @throws HaoqeeChatException
	 */
	public void getTempChatDetail(String id, TCGroupDetailListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.load_error);
		
		bundle.add("sessionid", id);
		
		String url = SERVER + "/session/api/detail";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state  = new TCState(json.getJSONObject("state"));
					
					if(state != null && state.code == 0){
						TCGroup group = null;
						
						if(!json.isNull("data")){
							Object data = json.get("data");
							if(data instanceof JSONObject){
								JSONObject object = (JSONObject)data;
								group = new TCGroup(object);
								
							}
						}
						
						if(listener != null){
							listener.doComplete(group);
						}
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 临时会话踢人接口
	 * @param id						临时会话ID				必传
	 * @param fuid						被踢用户ID				必传
	 * @param listener					结果监听回调对象			必传
	 * @throws HaoqeeChatException
	 */
	public void kickPersonFromTempChat(String id, String fuid, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		bundle.add("fuid", fuid);
		
		String url = SERVER + "/session/api/remove";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 退出临时会话
	 * @param id						临时会话ID				必传
	 * @param listener					结果监听回调对象			必传
	 * @throws HaoqeeChatException
	 */
	public void exitTempChat(String id, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		
		String url = SERVER + "/session/api/quit";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 编辑临时会话名称
	 * @param id						临时会话ID				必传
	 * @param name						临时会话名称			必传
	 * @param listener					结果监听回调对象			必传
	 * @throws HaoqeeChatException
	 */
	public void editTempChat(String id, String name, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		bundle.add("name", name);
		
		String url = SERVER + "/session/api/edit";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 设置临时会话是否接收消息接口
	 * @param id							临时会话ID						必传
	 * @param getmsg						是否接收消息（0--不接收	1--接收）	必传
	 * @param listener						结果监听回调对象					必传
	 * @throws HaoqeeChatException
	 */
	public void setTempChatMsgType(String id, String getmsg, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		bundle.add("getmsg", getmsg);
		
		String url = SERVER + "/session/api/getmsg";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
	
	/**
	 * 删除临时会话接口
	 * @param id						临时会话ID					必传
	 * @param listener					结果回调监听对象				必传
	 * @throws HaoqeeChatException
	 */
	public void deleteTempChat(String id, TCBaseListener listener) throws HaoqeeChatException{
		BaseParameters bundle = new BaseParameters();
		
		TCError error = new TCError();
		error.errorMessage = TCChatManager.getInstance().getContext().getString(R.string.tc_group_operate_failed);
		
		bundle.add("sessionid", id);
		
		String url = SERVER + "/session/api/delete";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if(reString != null && !reString.equals("") && !reString.equals("null")  && reString.startsWith("{")){
			try {
				JSONObject json = new JSONObject(reString);
				if(!json.isNull("state")){
					TCState state = new TCState(json.getJSONObject("state"));
					if(state != null && state.code == 0){
						if(listener != null){
							listener.doComplete();
						}
						
						return;
					}else {
						
						if(state != null){
							error.errorCode = state.code;
							if(!TextUtils.isEmpty(state.errorMsg)){
								error.errorMessage = state.errorMsg;
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if(listener != null){
			listener.onError(error);
		}
	}
}
	
