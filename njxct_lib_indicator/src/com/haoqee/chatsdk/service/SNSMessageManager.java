package com.haoqee.chatsdk.service;

import java.util.UUID;

import xmpp.push.sns.Chat;
import xmpp.push.sns.ChatManagerListener;
import xmpp.push.sns.MessageListener;
import xmpp.push.sns.packet.Message;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.DB.TCDBHelper;
import com.haoqee.chatsdk.DB.TCNotifyTable;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCNotifyType;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.receiver.NotifyChatMessage;
import com.haoqee.chatsdk.receiver.NotifyMessage;
import com.haoqee.libs.R;
/**
 * 
 * 功能：聊天监听.监听服务端信息(聊天信息，系统消息等...) <br />
 * 日期：2013-5-5<br />
 * 地点：好奇科技<br />
 * 版本：ver 1.0<br />
 * 
 * @since
 */
public class SNSMessageManager implements ChatManagerListener{
	private static final String SYSTEM_USER = "beautyas";
	
	private XmppManager xmppManager;
	private MessageListener chatListener;
	
//	private LruMemoryCache<String, Chat> chatCache = new LruMemoryCache<String, Chat>(6);
	
	private NotifyChatMessage chatMessage;
	
	public SNSMessageManager(XmppManager xmppManager) {
		super();
		this.xmppManager = xmppManager;
		chatListener = new ChatListenerImpl();
		chatMessage = new NotifyChatMessage(xmppManager);
	}

	@Override
	public void chatCreated(Chat chat, boolean createdLocally) {
		if(!createdLocally){
			chat.addMessageListener(chatListener);
		}
//		chatCache.put(chat.getParticipant().split("@")[0], chat);
	}
	
	/**
	 * 创建一个会话.
	 * @param chatID
	 * @return 没有连接状态时,返回空.
	 * 作者:fighter <br />
	 * 创建时间:2013-5-5<br />
	 * 修改时间:<br />
	 */
	public Chat createChat(String chatID){
		Chat chat = null;
//			chatCache.get(chatID);
//		if(chat == null){
			try {
				chat = xmppManager
				.getConnection()
				.getChatManager()
				.createChat(
						chatID
								+ "@"
								+ xmppManager.getConnection()
										.getServiceName(), chatListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
//		}
		
//		if(chat != null){
//			chatCache.put(chatID, chat);
//		}
		
		return chat;
	}
	
	/**
	 * 接收到消息,通过广播发送发送.
	 * @param notifyMessage
	 * @param content
	 * 作者:fighter <br />
	 * 创建时间:2013-5-6<br />
	 * 修改时间:<br />
	 */
	public void notityMessage(NotifyMessage notifyMessage, String msg){
		notifyMessage.notifyMessage(msg);
	}
	
	public NotifyChatMessage getNotifyChatMessage() {
		return chatMessage;
	}

	/**
	 * 
	 * 功能：聊天对象的单对单对话监听<br />
	 * 日期：2013-5-5<br />
	 * 地点：好奇科技<br />
	 * 版本：ver 1.0<br />
	 * 
	 * @since
	 */
	class ChatListenerImpl implements MessageListener{

		@Override
		public void processMessage(Chat chat, Message message) {
			String chatId = chat.getParticipant().split("@")[0];  // 发来消息的用户
			String content = message.getBody();					// 发送来的内容.
			if(SYSTEM_USER.equals(chatId)){
				//Log.e("content", content);
				saveNotify(content);
			}else{
				if(!TextUtils.isEmpty(content) && content.startsWith("{")){
					//Log.e("ChatListenerImpl", content);
				}
				notityMessage(chatMessage, content);
			}
		}
		
	}
	
	/**
	 * 收到通知后，解析成通知对象，并保存到数据表
	 * @param notifyMsg		通知JSON数据
	 */
	private void saveNotify(String notifyMsg){
		TCNotifyVo notifyVo = new TCNotifyVo(notifyMsg);
		notifyVo.setNotifyID(UUID.randomUUID().toString());
		
		switch (notifyVo.getType()) {
		case TCNotifyType.APPLY_ADD_GROUP:
			TCNotifyVo notify = TCChatManager.getInstance().queryNotify(notifyVo);
			if(notify != null && notify.getUser().getUserID().equals(notifyVo.getUser().getUserID())){
				notifyVo.setNotifyID(notify.getNotifyID());
				
				TCChatManager.getInstance().deleteNotify(notifyVo);
			}
			break;
			
			
		case TCNotifyType.INVITE_ADD_GROUP:
			TCNotifyVo inviteNotify = TCChatManager.getInstance().queryNotify(notifyVo);
			if(inviteNotify != null && inviteNotify.getUser().getUserID().equals(notifyVo.getUser().getUserID())){
				notifyVo.setNotifyID(inviteNotify.getNotifyID());
				TCChatManager.getInstance().deleteNotify(notifyVo);
			}
			break;
			
		case TCNotifyType.GROUP_KICK_OUT:		//收到被踢出群的通知，清空数据表中和群相关的会话列表和消息列表， 并发通知更新会话列表页面和未读数
			TCChatManager.getInstance().deleteGroupFromTable(notifyVo.getRoomID(), ChatType.GROUP_CHAT);
			TCSession session = TCChatManager.getInstance().getSessionByID(notifyVo.getRoomID(), ChatType.GROUP_CHAT);
			if(session != null){
				TCChatManager.getInstance().deleteSession(notifyVo.getRoomID(),  ChatType.GROUP_CHAT);
			}
			
			break;
			
		case TCNotifyType.GROUP_KICK_OUT_OTHER:
			notifyVo.setContent(notifyVo.getUser().getName() + TCChatManager.getInstance().getContext().getString(R.string.been_remove_by_admin) + notifyVo.getRoom().getGroupName()) ;
			break;
			
		case TCNotifyType.DELETE_GROUP:		//收到群主删除群通知，清空数据表中和群相关的会话列表和消息列表， 并发通知更新会话列表页面和未读数
			TCChatManager.getInstance().deleteGroupFromTable(notifyVo.getRoomID(), ChatType.GROUP_CHAT);
			TCSession deletesession = TCChatManager.getInstance().getSessionByID(notifyVo.getRoomID(), ChatType.GROUP_CHAT);
			if(deletesession != null){
				TCChatManager.getInstance().deleteSession(notifyVo.getRoomID(),  ChatType.GROUP_CHAT);
			}
			
			break;
			
		case TCNotifyType.MODIFY_TEMP_CHAT_NAME:
			TCSession tempChatSession = TCChatManager.getInstance().getSessionByID(notifyVo.getRoomID(), ChatType.TEMP_CHAT);
			if(tempChatSession != null){
				tempChatSession.setSessionName(notifyVo.getRoom().getGroupName());
				TCChatManager.getInstance().addSession(tempChatSession);
			}
			
			break;
			
		case TCNotifyType.TEMP_CHAT_KICK_OUT:
			TCChatManager.getInstance().deleteGroupFromTable(notifyVo.getRoomID(), ChatType.TEMP_CHAT);
			TCSession kickTempSeesion = TCChatManager.getInstance().getSessionByID(notifyVo.getRoomID(), ChatType.TEMP_CHAT);
			if(kickTempSeesion != null){
				TCChatManager.getInstance().deleteSession(notifyVo.getRoomID(),  ChatType.TEMP_CHAT);
			}
			break;
			
		case TCNotifyType.KICK_OUT_TEMP_CHAT:
			//notifyVo.setContent(notifyVo.getUser().getName() + TCChatManager.getInstance().getContext().getString(R.string.been_remove_by_admin) + notifyVo.getRoom().getGroupName()) ;
			break;
			
		case TCNotifyType.DELETE_TEMP_CHAT:
			TCChatManager.getInstance().deleteGroupFromTable(notifyVo.getRoomID(), ChatType.TEMP_CHAT);
			TCSession deleteTempSeesion = TCChatManager.getInstance().getSessionByID(notifyVo.getRoomID(), ChatType.TEMP_CHAT);
			if(deleteTempSeesion != null){
				TCChatManager.getInstance().deleteSession(notifyVo.getRoomID(),  ChatType.TEMP_CHAT);
			}
			break;

		default:
			break;
		}
		
		insertNotify(notifyVo);
		TCChatManager.getInstance().getNotifyListenser().receive(notifyVo);
	}

	/**
	 * 插入一条通知
	 * @param notifyVo				通知对象
	 */
	private void insertNotify(TCNotifyVo notifyVo){
		SQLiteDatabase database = TCDBHelper.getInstance(TCChatManager.getInstance().getContext()).getWritableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		table.insert(notifyVo);
	}
}
