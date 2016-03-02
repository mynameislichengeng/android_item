package com.haoqee.chatsdk;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import com.haoqee.chatsdk.DB.TCDBHelper;
import com.haoqee.chatsdk.DB.TCGroupTable;
import com.haoqee.chatsdk.DB.TCMessageTable;
import com.haoqee.chatsdk.DB.TCNotifyTable;
import com.haoqee.chatsdk.DB.TCSessionTable;
import com.haoqee.chatsdk.Interface.FileDownloadListener;
import com.haoqee.chatsdk.Interface.LoginListenser;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.Interface.TCGroupDetailListener;
import com.haoqee.chatsdk.Interface.TCGroupListListener;
import com.haoqee.chatsdk.Interface.TCMenberListListener;
import com.haoqee.chatsdk.Interface.TCMessageListener;
import com.haoqee.chatsdk.Interface.TCNotifyListener;
import com.haoqee.chatsdk.Interface.TCNotifyMessageListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageResult;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.net.SocketHttpRequester;
import com.haoqee.chatsdk.net.HaoqeeChatException;
import com.haoqee.chatsdk.net.HaoqeeChatSdkInfo;
import com.haoqee.chatsdk.service.XmppManager;
import com.haoqee.libs.R;

public class TCChatManager {
	
	private static TCChatManager mTCChatManager;
	
    private static Context mContext;
    private HaoqeeChatSdkInfo mHaoqeesdkInfo = new HaoqeeChatSdkInfo();
    private String mLoginUID = "";					//当前登录用户ID			
    
    private LoginListenser mLoginListenser;			//Openfire登录监听回调对象
    private TCNotifyMessageListener mNotifyMessageListener;	//消息监听回调对象
    private TCNotifyListener mNotifyListener;				//通知监听回调对象
    public XmppManager mXmppManager;
    
    public static void init(Context context){
    	mContext = context;
    	mTCChatManager = new TCChatManager();
    }
    
	private TCChatManager(){
		
	}
	
	public Context getContext(){
		return mContext;
	}

	public static TCChatManager getInstance(){
		return mTCChatManager;
	}
	
	public String getLoginUid(){
		return mLoginUID;
	}
	
	/**
	 * 获取登录回调事件
	 * @return
	 */
	public LoginListenser getLoginListenser(){
		return mLoginListenser;
	}
	
	/**
	 * 获取消息通知监听事件
	 * @return
	 */
	public TCNotifyMessageListener getMessageListenser(){
		return mNotifyMessageListener;
	}
	
	/**
	 * 设置消息通知监听事件
	 * @param listenser
	 */
	public void setNotifyMessageListener(TCNotifyMessageListener listenser){
		this.mNotifyMessageListener = listenser;
	}
	
	/**
	 * 获取通知监听事件
	 * @return
	 */
	public TCNotifyListener getNotifyListenser(){
		return mNotifyListener;
	}
	
	/**
	 * 设置通知监听事件
	 * @param listenser
	 */
	public void setNotifyListener(TCNotifyListener listenser){
		this.mNotifyListener = listenser;
	}
	
	/**
	 * 登录XMPP
	 * @param host					服务器地址			必传
	 * @param port					端口号			必传
	 * @param resourceName			域				必传
	 * @param uid					登录用户ID			必传
	 * @param password				登录密码			必传
	 * @param listenser				登录监听对象		必传
	 * @return	String				参数错误返回值		
	 */
	public String loginXmpp(final String host, final String port, final String resourceName, final String uid, final String password, LoginListenser listenser) {
		
		String endDate = "2014-11-15 00:00:00";
		
		long endTime = getTimeStamp(endDate);
		long currentTime = System.currentTimeMillis();
		
		/*if(currentTime > endTime){
			((Activity)mContext).finish();
			System.exit(0);
			return "unexpected error";
		}*/
		
		if(TextUtils.isEmpty(uid)){
			return mContext.getString(R.string.please_input_uid);
		}
		
		mLoginUID = uid;
		
		if(TextUtils.isEmpty(host)){
			return mContext.getString(R.string.please_input_host);
		}
		
		if(TextUtils.isEmpty(port)){
			return mContext.getString(R.string.please_input_port);
		}
		
		if(TextUtils.isEmpty(resourceName)){
			return mContext.getString(R.string.please_input_resourcename);
		}
		
		if(TextUtils.isEmpty(password)){
			return mContext.getString(R.string.please_input_password);
		}
		
		if(listenser == null){
			return mContext.getString(R.string.please_input_listener);
		}
		
		mXmppManager = new XmppManager(mContext, uid, password, listenser);
		mXmppManager.setHost(host, port, resourceName);
		mXmppManager.connect();
		
		
		return "";
	}
	
	private long getTimeStamp(String brithDate) {
    	long time = 0;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = formatter.parse(brithDate); 
			time = date.getTime();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
	
	/**
	 * 检测网络状态
	 * @param context
	 * @return
	 */
	public static boolean verifyNetwork(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null) {
            if (activeNetInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
	
	/**
	 * 设置业务服务器地址 
	 * @param server
	 */
	public void setLogitiscServer(String server){
		mHaoqeesdkInfo.setServer(server);
	}
	
	private String generator(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

	private String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    
	private boolean reNameFile(File file, String newName){
		return file.renameTo(new File(file.getParentFile(), newName));
	}
    
    
    /** ++++++++++++++++++++++++   会话和消息操作       +++++++++++++++++++++++++*/
	
	/**
	 * 新增或者更新会话
	 * @param message				待插入的消息
	 */
	public void addSession(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.insert(message);
		TCSession session =null;
		if(message.getChatType() == ChatType.SINGLE_CHAT){
			if(message.getFromId().equals(mLoginUID)){
				session = table.query(message.getToId(), message.getChatType());
			}else {
				session = table.query(message.getFromId(), message.getChatType());
			}
		}else {
			session = table.query(message.getToId(), message.getChatType());
		}
		
		if(session == null){
			session = new TCSession();
			session.setChatType(message.getChatType());
			
			if(message.getChatType() == ChatType.SINGLE_CHAT){
				if(message.getFromId().equals(mLoginUID)){
					message.setReadState(1);
					session.setFromId(message.getToId());
					session.setSessionName(message.getToName());
					session.setSessionHead(message.getToHead());
					session.setSessionExtendStr(message.getToExtendStr());
				}else {
					session.setFromId(message.getFromId());
					session.setSessionName(message.getFromName());
					session.setSessionHead(message.getFromHead());
					session.setSessionExtendStr(message.getFromExtendStr());
				}
				
			}else {
				session.setFromId(message.getToId());
				session.setSessionName(message.getToName());
				session.setSessionHead(message.getToHead());
				session.setSessionExtendStr(message.getToExtendStr());
			}
			
			table.insert(session);
		}else {
			
			if(message.getChatType() == ChatType.SINGLE_CHAT){
				if(message.getFromId().equals(mLoginUID)){
					message.setReadState(1);
					session.setSessionName(message.getToName());
					session.setSessionHead(message.getToHead());
					session.setSessionExtendStr(message.getToExtendStr());
				}else {
					session.setSessionName(message.getFromName());
					session.setSessionHead(message.getFromHead());
					session.setSessionExtendStr(message.getFromExtendStr());
				}
				
			}else {
				session.setSessionName(message.getToName());
				session.setSessionHead(message.getToHead());
				session.setSessionExtendStr(message.getToExtendStr());
			}
			
			table.update(session, session.getChatType());
		}
	}
	
	/**
	 * 新增会话
	 * @param session							会话对象
	 */
	public void addSession(TCSession session){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		TCSession existSession = table.query(session.getFromId(), session.getChatType());
		if(existSession != null){
			table.update(session, session.getChatType());
		}else {
			table.insert(session);
		}
	}
	
	/**
	 * 更新会话
	 * @param session
	 */
	public void updateSession(TCSession session){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		if(session != null){
			table.update(session, session.getChatType());
		}
	}
	
	/**
	 * 根据会话对象ID更新名称和头像
	 * @param id					会话对象ID
	 * @param chatType				聊天类型
	 * @param name					名称
	 * @param head					头像
	 */
	public void updateSessionByID(String id, int chatType, String name, String head){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		TCSession session = table.query(id, chatType);
		if(session != null){
			
			session.setSessionName(name);
			session.setSessionHead(head);
			table.update(session, session.getChatType());
		}
	}
	
	/**
	 * 根据对象ID获取会话
	 
	 * @param id				对象的ID（用户ID,群ID或讨论组ID）
	 * @param chatType			聊天类型
	 * @return	TCSession 会话对象
	 */
	public TCSession getSessionByID(String id, int chatType){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		return table.query(id, chatType);
	}
	
	/**
	 * 获取当前登录用户的会话列表
	 * @return		会话列表 
	 */
	public List<TCSession> getSessionList(){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		return table.query();
	}
	
	/**
	 * 获取当前登录用户的消息未读数
	 * @param loginUid				当前登录用户ID
	 * @return	
	 */
	public int getUnreadCount(){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		return table.querySessionCount();
	}
	
	/**
	 * 删除与指定对象的会话
	 	
	 * @param id				对象的ID（用户ID,群ID或讨论组ID）	
	 * @param chatType			聊天类型
	 */
	public void deleteSession(String id, int chatType){
		
		deleteMessage(id, chatType);
		
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCSessionTable table = new TCSessionTable(database);
		table.delete(id, chatType);
	}
	
	/**
	 * 重置与某个对象的消息未读数为0
	 * @param fromID				对象ID
	 * @param loginUid				当前登录用户ID
	 * @param chatType				聊天类型
	 */
	public boolean resetUnreadCount(String fromID, int chatType){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.updateReadState(fromID, chatType);
	}
	
	/**
	 * 获取与某个对象的消息列表
	 * @param autoID				自增ID（加载更多用）	获取消息的位置（传入-1则获取全部，传入其他则获取该ID之后的数据）		
	 * @param fromID				对象ID（用户，讨论组或者群）
	 * @param chatType				聊天类型（单聊，群聊或者讨论组）
	 * @param loadSize				加载的条数
	 * @return	消息列表 
	 */
	public List<TCMessage> getMessageList(int autoID, String fromID, int chatType, int loadSize){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.query(fromID, autoID, chatType, loadSize);
	}
	
	/**
	 * 获取指定对象的指定条数的消息体记录
	 * @param fromID				对象ID（用户，讨论组或者群）
	 * @param chatType				聊天类型（单聊，群聊或者讨论组）
	 * @param page					加载的页码
	 * @param loadSize				加载的条数
	 * @return
	 */
	public List<TCMessage> getMessageListByPage(String fromID, int chatType, int page, int loadSize){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.queryMessageListByPage(fromID, chatType, page, loadSize);
	}
	
	/**
	 * 获取上一页指定条数的消息记录
	 * @param fromID				对象ID（用户，讨论组或者群）
	 * @param chatType				聊天类型（单聊，群聊或者讨论组）
	 * @param autoID				消息自增ID
	 * @param loadSize				加载条数 
	 * @return
	 */
	public List<TCMessage> getPreviousPageMessageList(String fromID, int chatType, int autoID, int loadSize){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.queryPreviousPageMessageList(fromID, autoID, chatType, loadSize);
	}
	
	/**
	 * 获取指定对象的指定条数的未读消息记录
	 * @param fromID				对象ID（用户，讨论组或者群）
	 * @param chatType				聊天类型（单聊，群聊或者讨论组）
	 * @param autoID				消息自增ID
	 * @param loadSize				加载条数 
	 * @return
	 */
	public List<TCMessage> getUnreadMessageList(String fromID, int chatType, int autoID, int loadSize){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.queryUnreadMessageList(fromID, autoID, chatType, loadSize);
	}
	
	/**
	 * 更新消息的阅读状态
	 * @param message			待更新的消息体
	 */
	public boolean updateMessageReadState(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return  messageTable.updateMessageReadState(message);
	}
	
	/**
	 * 更新消息的发送状态
	 * @param message			待更新的消息体
	 */
	public void updateMessageSendState(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.updateMessageSendState(message);
	}
	
	/**
	 * 更新语音阅读状态
	 * @param loginUid				当前登录用户ID
	 * @param message			待更新的消息体
	 */
	public void updateMessageVoiceReadState(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.updateVoiceReadState(message);
	}
	
	/**
	 * 更新消息的状态（包括消息的阅读状态，发送状态以及语音阅读状态）
	 * @param message			待更新的消息体
	 */
	public void updateMessageState(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.update(message);
	}
	
	/**
	 * 更新消息体
	 * @param message				待更新的消息体
	 */	
	private void updateMessage(TCMessage message){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.updateMessage(message);
	}
	
	/**
	 * 删除与某个聊天对象的消息记录，即清空消息记录
	 * @param id					对象的ID（用户ID,群ID或讨论组ID）
	 * @param chatType				聊天类型
	 */
	private boolean deleteMessage(String id, int chatType){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		return messageTable.delete(id, chatType);
	}
	
	/**
	 * 根据消息唯一标示符删除指定消息
	 * @param tag				消息的唯一标示符
	 */
	public void deleteMessage(String tag){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCMessageTable messageTable = new TCMessageTable(database);
		messageTable.delete(tag);
	}
	
	/** -------------------------   会话和消息操作       -------------------------*/
	
	
	/** +++++++++++++++++++++++++    通知操作     	+++++++++++++++++++++++++*/
	
	
	/**
	 * 查询通知是否存在
	 	
	 * @param notifyVo					待查询的通知
	 * @return
	 */
	public TCNotifyVo queryNotify(TCNotifyVo notifyVo){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		return table.query(notifyVo);
	}
	
	/**
	 * 查询最新一条通知
	 
	 * @return
	 */
	public TCNotifyVo queryNewNotify(){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		return table.queryNewNotify();
	}
	
	/**
	 * 查询通知未读数
	 
	 * @return
	 */
	public int queryNotifyUnreadCount(){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		return table.queryUnread();
	}
	
	/**
	 * 查询通知列表
	 
	 * @return
	 */
	public List<TCNotifyVo> queryNotifyList(){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		return table.query();
	}
	
	/**
	 * 更新通知的阅读状态或处理状态
	 
	 * @param notifyVo
	 */
	public void updateNotify(TCNotifyVo notifyVo){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		table.update(notifyVo);
	}
	
	/**
	 * 删除一条通知
	 * @param notifyVo
	 */
	public void deleteNotify(TCNotifyVo notifyVo){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCNotifyTable table = new TCNotifyTable(database);
		table.deleteByID(notifyVo);
	}
	
	/** -------------------------    通知操作      	-------------------------*/
	
	/**
	 * 发送消息接口
	 * @param message				待发送的消息对象
	 * @param listener				接口回调对象
	 */
	public void sendMessage(final TCMessage message, final TCMessageListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					message.setSendState(2);				//设置消息正在发送中
					try {
						TCMessageResult result = mHaoqeesdkInfo.sendMessage(message, listener);
						if(result != null && result.mState != null && result.mState.code == 0){
							
							result.mMessageInfo.setSendState(1);
							result.mMessageInfo.setReadState(1);
							result.mMessageInfo.setVoiceReadState(1);
							if(result.mMessageInfo.getMessageType() == TCMessageType.VOICE){
								reNameFile(new File(message.getVoiceMessageBody().getVoiceUrl()), generator(result.mMessageInfo.getVoiceMessageBody().getVoiceUrl()));
							}else if(result.mMessageInfo.getMessageType() == TCMessageType.FILE){
								result.mMessageInfo.getFileMessageBody().setFileUrl(message.getFileMessageBody().getFileUrl());
							}
							updateMessage(result.mMessageInfo);
							if(listener != null){
								listener.doComplete(result.mMessageInfo);
							}
							
							return;
						}
						
						if(result != null && result.mState != null && !TextUtils.isEmpty(result.mState.errorMsg)){
							error.errorCode = result.mState.code;
							error.errorMessage = result.mState.errorMsg;
						}else {
							error.errorMessage = mContext.getString(R.string.send_message_failed);
						}
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
				}
				
				message.setSendState(0);
				TCChatManager.getInstance().updateMessageSendState(message);
				if(listener != null){
					listener.onError(message, error);
				}
			}
		}.start();
	}
	
	/** +++++++++++++++++++++++++    群操作     	+++++++++++++++++++++++++*/
	
	/**
	 * 创建群接口
	 * @param groupName				群名称			必传
	 * @param groupDesc				群简介			必传
	 * @param groupLogo				群头像			非必传
	 * @param extendMap				扩展字段Map		非必传
	 * @param listener				结果回调监听对象
	 */
	public void createGroup(final String groupName, final String groupDesc, final String groupLogo, final HashMap<String, String> extendMap, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {

						mHaoqeesdkInfo.createGroup(groupName, groupLogo, groupDesc, extendMap, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 获取我的群列表接口
	 * @param listener				结果回调监听对象			必传
	 */
	public void getGroupList(final TCGroupListListener listener) {
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.getGroupList(listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 获取群详细接口
	 * @param groupid				群ID				必传
	 * @param listener				结果回调监听对象		必传
	 */
	public void getTCGroupDetail(final String groupid, final TCGroupDetailListener listener) {
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.getGroupDetail(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 编辑群资料接口
	 * @param groupId				群ID			必传
	 * @param groupName				群名称		非必传
	 * @param groupLogo				群头像		非必传
	 * @param groupDes				群简介		非必传
	 * @param extend				群扩展属性		非必传
	 * @param listener				结果监听回调对象	必传
	 */
	public void editGroup(final String groupId, final String groupName, final String groupLogo, final String groupDes, final HashMap<String, String> extendMap, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.editGroup(groupId, groupName, groupLogo, groupDes, extendMap, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 搜索群接口
	 * @param keyword				搜索关键字			必传
	 * @param page					搜索页数			
	 * @param listener				结果监听回调对象		必传
	 */
	public void searchGroup(final String keyword, final int page, final TCGroupListListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.searchGroup(keyword, page, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 申请加入群接口
	 * @param groupid			群ID			必传
	 * @param listener			结果监听回调对象	必传
	 */
	public void applyAddGroup(final String groupid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.applyAddGroup(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 同意用户加入群接口
	 * @param groupid					群ID			必传
	 * @param fuid						用户ID		必传
	 * @param listener					结果监听回调对象	必传
	 */
	public void agreeAddGroup(final String groupid, final String fuid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.agreeAddGroup(groupid, fuid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 拒绝用户加入群接口
	 * @param groupid					群ID			必传
	 * @param fuid						用户ID		必传
	 * @param listener					结果监听回调对象	必传
	 */
	public void refuseAddGroup(final String groupid, final String fuid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.refuseAddGroup(groupid, fuid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 邀请用户加入群接口
	 * @param groupid				群ID				必传
	 * @param fuid					被邀请的用户ID		必传
	 * @param listener				结果监听回调对象		必传
	 */
	public void inviteAddGroup(final String groupid, final String fuid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.inviteAddGroup(groupid, fuid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 同意群邀请接口
	 * @param groupid
	 * @param listener
	 */
	public void agreeGroupInvite(final String groupid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.agreeInvite(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						TCError error = new TCError();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 拒绝群邀请接口
	 * @param groupid
	 * @param listener
	 */
	public void refuseGroupInvite(final String groupid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.refuseInvite(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 用户退出群接口
	 * @param groupid				群ID				必传
	 * @param listener				结果监听回调对象		必传
	 */
	public void exitGroup(final String groupid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.exitGroup(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 用户删除群接口
	 * @param groupid				群ID				必传
	 * @param listener				结果监听回调对象		必传
	 */
	public void deleteGroup(final String groupid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.deleteGroup(groupid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 获取群成员列表接口
	 * @param groupid						群ID				必传
	 * @param page					请求的页数
	 * @param listener						结果监听回调对象		必传
	 */
	public void getGroupMenberList(final String groupid, final int page, final TCMenberListListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.getMenberList(groupid, page, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 踢出成员接口
	 * @param groupid					群ID					必传	
	 * @param fuid						被踢用户ID				必传
	 * @param listener					结果监听回调对象			必传
	 */
	public void kickGroupMenber(final String groupid, final String fuid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.kickGroupMenber(groupid, fuid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 设置接收群消息接口
	 * @param groupid				群ID							必传
	 * @param getmsg				是否接口（0--不接收	1--接收）		必传
	 * @param listener				接口监听回调对象					必传
	 */
	public void setGroupMsgType(final String groupid, final String getmsg, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.setMsgType(groupid, getmsg, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 插入群列表
	 * @param groupList					群列表
	 * @param type						群类型
	 */
	public void insertGroupList(List<TCGroup> groupList, int type){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		table.insert(groupList, type);
	}
	
	/**
	 * 插入群
	 * @param group						群
	 * @param type						群类型
	 */
	public void insertGroup(TCGroup group, int type){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		table.insert(group, type);
	}
	
	/**
	 * 查询群列表数据
	 * @param type				类型（讨论组或群）					必传
	 * @return
	 */
	public List<TCGroup> queryGroupList(int type){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getReadableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		return table.queryList(type);
	}
	
	/**
	 * 从群表中删除某个群
	 * @param groupid				群ID							必传
	 * @param type					类型（讨论组或群）				必传
	 */
	public void deleteGroupFromTable(String groupid, int type){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		table.deleteGroup(groupid, type);
	}
	
	/**
	 * 更新数据表保存的群通知设置
	 * @param group				群信息
	 */
	public void updateGroupGetMsg(TCGroup group){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		TCGroup existGroup = table.query(group.getGroupID(), ChatType.GROUP_CHAT);
		
		if(existGroup == null){
			table.insert(group, ChatType.GROUP_CHAT);
		}else {
			table.updateGroupGetMsg(group, ChatType.GROUP_CHAT);
		}
	}
	
	/**
	 * 根据群（临时会话）ID获取群（临时会话）
	 * @param id					群（临时会话）ID
	 * @param chatType				聊天类型
	 * @return
	 */
	public TCGroup queryGroupByID(String id, int chatType){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		return table.query(id, chatType);
	}
	
	/** -------------------------    群操作      	-------------------------*/
	
	/** +++++++++++++++++++++++++    临时会话操作     	+++++++++++++++++++++++++*/
	
	/**
	 * 创建临时会话接口
	 * @param name					临时会话名称					必传
	 * @param uids					待加入用户ID串					必传
	 * @param listener				结果监听回调对象					必传
	 */
	public void createTempChat(final String name, final String uids, final TCGroupDetailListener listener) {
		new Thread(){
			@Override
			public void run(){
				new Thread(){
					@Override
					public void run(){
						TCError error = new TCError();
						if(verifyNetwork(mContext)){
							try {
								mHaoqeesdkInfo.createTempChat(name, uids, listener);
							} catch (HaoqeeChatException e) {
								e.printStackTrace();
								error.errorMessage = mContext.getString(R.string.timeout);
								if(listener != null){
									listener.onError(error);
								}
							}
						}else {
							error.errorMessage = mContext.getString(R.string.network_error);
							if(listener != null){
								listener.onError(error);
							}
						}
					}
				}.start();
			}
		}.start();
	}
	
	/**
	 * 添加用户到临时会话接口
	 * @param id						临时会话ID						必传
	 * @param uids						待加入的用户ID串					必传
	 * @param listener					结果回调监听对象					必传
	 */
	public void addPersonToTempChat(final String id, final String uids, final TCBaseListener listener) {
		new Thread(){
			@Override
			public void run(){
				new Thread(){
					@Override
					public void run(){
						TCError error = new TCError();
						if(verifyNetwork(mContext)){
							try {
								mHaoqeesdkInfo.addPersonToTempChat(id, uids, listener);
							} catch (HaoqeeChatException e) {
								e.printStackTrace();
								error.errorMessage = mContext.getString(R.string.timeout);
								if(listener != null){
									listener.onError(error);
								}
							}
						}else {
							error.errorMessage = mContext.getString(R.string.network_error);
							if(listener != null){
								listener.onError(error);
							}
						}
					}
				}.start();
			}
		}.start();
	}
	
	/**
	 * 获取临时会话详情接口
	 * @param id							临时会话ID					必传
	 * @param listener						结果回调监听对象				必传
	 */
	public void getTemChatDetail(final String id, final TCGroupDetailListener listener) {
		new Thread(){
			@Override
			public void run(){
				new Thread(){
					@Override
					public void run(){
						TCError error = new TCError();
						if(verifyNetwork(mContext)){
							try {
								mHaoqeesdkInfo.getTempChatDetail(id, listener);;
							} catch (HaoqeeChatException e) {
								e.printStackTrace();
								error.errorMessage = mContext.getString(R.string.timeout);
								if(listener != null){
									listener.onError(error);
								}
							}
						}else {
							error.errorMessage = mContext.getString(R.string.network_error);
							if(listener != null){
								listener.onError(error);
							}
						}
					}
				}.start();
			}
		}.start();
	}
	
	/**
	 * 踢出临时会话用户接口
	 * @param id				临时会话ID				必传
	 * @param fuid				被踢用户ID				必传
	 * @param listener			结果回调监听对象			必传
	 */
	public void kickTempChatMenber(final String id, final String fuid, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.kickPersonFromTempChat(id, fuid, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 退出临时会话接口
	 * @param id					临时会话ID					必传
	 * @param listener				结果回调监听对象				必传
	 */
	public void exitTempChat(final String id, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.exitTempChat(id, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 编辑临时会话名称接口				
	 * @param id					临时会话ID					必传
	 * @param name					临时会话名称				必传
	 * @param listener				结果监听回调对象				必传
	 */
	public void editTempChat(final String id, final String name, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.editTempChat(id, name, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 设置临时会话是否接收消息接口
	 * @param id					临时会话ID					必传
	 * @param getmsg				是否接收（0--不接收	1--接收）	必传
	 * @param listener
	 */
	public void setTempChatMsgType(final String id, final String getmsg, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.setTempChatMsgType(id, getmsg, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 删除临时会话接口
	 * @param id						临时会话ID				必传
	 * @param listener					结果监听回调类			必传
	 */
	public void deleteTempChat(final String id, final TCBaseListener listener){
		new Thread(){
			@Override
			public void run(){
				TCError error = new TCError();
				if(verifyNetwork(mContext)){
					try {
						mHaoqeesdkInfo.deleteTempChat(id, listener);
					} catch (HaoqeeChatException e) {
						e.printStackTrace();
						error.errorMessage = mContext.getString(R.string.timeout);
						if(listener != null){
							listener.onError(error);
						}
					}
				}else {
					error.errorMessage = mContext.getString(R.string.network_error);
					if(listener != null){
						listener.onError(error);
					}
				}
			}
		}.start();
	}
	
	/**
	 * 更新临时会话新消息通知设置
	 * @param tempChat				临时会话对象
	 */
	public void updateTempChatGetMsg(TCGroup tempChat){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		TCGroup existGroup = table.query(tempChat.getGroupID(), ChatType.TEMP_CHAT);
		
		if(existGroup == null){
			table.insert(tempChat, ChatType.TEMP_CHAT);
		}else {
			table.updateGroupGetMsg(tempChat, ChatType.TEMP_CHAT);
		}
	}
	
	/**
	 * 更新会话列表中临时会话名称
	 * @param TCGroup					临时会话对象
	 */
	public void updateTempChatName(TCGroup group){
		SQLiteDatabase database = TCDBHelper.getInstance(mContext).getWritableDatabase();
		TCGroupTable table = new TCGroupTable(database);
		TCSessionTable sessionTable = new TCSessionTable(database);
		TCGroup existGroup = table.query(group.getGroupID(), ChatType.TEMP_CHAT);
		if(existGroup != null){
			table.updateGroupName(group, ChatType.TEMP_CHAT);
		}else {
			table.insert(group, ChatType.TEMP_CHAT);
		}
		
		TCSession session = sessionTable.query(group.getGroupID(), ChatType.TEMP_CHAT);
		if(session != null){
			session.setSessionName(group.getGroupName());
			sessionTable.update(session, ChatType.TEMP_CHAT);
		}
		
	}
	
	/** -------------------------    临时会话操作      	-------------------------*/
	
	/**
	 * 更新Openfire登录密码
	 * @param password				新密码
	 */
	public void setXmppPwd(String password){
		if(mXmppManager != null){
			mXmppManager.setPassword(password);
		}
	}
	
	/**
	 * 登出XMPP
	 */
	public void logoutXmpp(){
		if(mXmppManager != null){
			mXmppManager.disconnect();
		}	
		
	}
	
	/**
	 * 下载文件
	 * @param url					文件URL
	 * @param path					文件保存路径
	 * @param listener				文件下载进度监听对象
	 */
	public void downloadFile(final String url, final String path, final FileDownloadListener listener){
		new Thread(){
			@Override
			public void run(){
				
				try {
					SocketHttpRequester.downloadFile(mContext, url, path, listener);
				} catch (Exception e) {
					TCError error = new TCError();
					error.errorMessage = mContext.getString(R.string.file_download_failed);
					listener.onError(error);
				}
			}
		}.start();
	}
}
