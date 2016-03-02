package com.haoqee.chatsdk.receiver;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.service.XmppManager;

/**
 * 
 * 功能： 接收到发送的消息.通过广播发送出去 
 */
public class NotifyChatMessage implements NotifyMessage {

	/**
	 * 聊天服务发来聊天信息, 广播包<br/>
	 * 附加参数: {@link NotifyChatMessage#EXTRAS_NOTIFY_CHAT_MESSAGE}
	 */
	public static final String ACTION_NOTIFY_CHAT_MESSAGE = "com.haoqee.chatsdk.sns.notify.ACTION_NOTIFY_CHAT_MESSAGE";
	/**
	 * 某消息列表有更新，注意查收
	 * 附加参数: {@link NotifyChatMessage#EXTRAS_NOTIFY_SESSION_MESSAGE}
	 */
	public static final String ACTION_NOTIFY_SESSION_MESSAGE = "com.haoqee.chatsdk.sns.notify.ACTION_NOTIFY_SESSION_MESSAGE";
	
	/**
	 * 更新语音转文字成功之后语音消息对应的文本信息通知
	 */
	public static final String ACTION_CHANGE_VOICE_CONTENT = "com.haoqee.chatsdk.intent.action.ACTION_CHANGE_VOICE_CONTENT";
	
	/**
	 * 附加信息<br/> {@link MessageInfo}
	 */
	public static final String EXTRAS_NOTIFY_CHAT_MESSAGE = "extras_message";
	/**
	 * 附加信息<br/> {@link SessionList}
	 */
	public static final String EXTRAS_NOTIFY_SESSION_MESSAGE = "extras_session";
	
	

	public XmppManager xmppManager;
	

	public NotifyChatMessage(XmppManager xmppManager) {
		super();
		this.xmppManager = xmppManager;
	}

	@Override
	public void notifyMessage(String msg) {
		try {
			
			if(msg.equals("This room is not anonymous.")){
				return;
			}
			
			TCMessage info = new TCMessage(msg);
			
			if(info.getChatType() != ChatType.SINGLE_CHAT && info.getFromId().equals(TCChatManager.getInstance().getLoginUid())){
				return;
			}
			info.setSendState(1);
			if (info != null) {
				saveMessageInfo(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存消息到数据表
	 * @param info
	 */
	private void saveMessageInfo(TCMessage info) {
		
		
		TCChatManager.getInstance().addSession(info);
		
		TCChatManager.getInstance().getMessageListenser().doComplete(info);
		
	}

	
}
