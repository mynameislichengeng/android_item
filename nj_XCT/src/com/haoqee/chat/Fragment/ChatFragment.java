package com.haoqee.chat.Fragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.haoqee.chat.ChatMainActivity;
import com.haoqee.chat.MainActivity;
import com.haoqee.chat.NewTempChatActivity;
import com.haoqee.chat.NotifyActivity;
import com.haoqee.chat.adapter.SessionAdapter;
import com.haoqee.chat.entity.NotifyType;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.entity.TextMessageBody;

public class ChatFragment extends BaseFragment implements OnClickListener{
	
	public static final String EMOJIREX = "emoji_[\\d]{0,3}";
	
	public ListView mListView;
	
	/** 更新会话列表通知 */
	public static final String UPDATE_COUNT_ACTION = "com.xizue.thinkchat.fragment.UPDATE_COUNT_ACTION";
	
	private static final int REFRESH_ADAPTER = 0x00001;
	
	public List<TCSession> mSessionList = new ArrayList<TCSession>();
	private SessionAdapter mAdapter;
	public final static int NOTIFY_TYPE = 0;			//列表中表示通知类型
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_chat_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_COUNT_ACTION);
		mContext.registerReceiver(mReceiver, filter);
		initSession();
	}
	
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if(!TextUtils.isEmpty(action)){
				if(action.equals(UPDATE_COUNT_ACTION)){
					initSession();
				}
			}
		}
	};
	
	private void initSession(){
		
		List<TCSession> sessionList = TCChatManager.getInstance().getSessionList();
		
		if(sessionList == null){
			sessionList = new ArrayList<TCSession>();
		}
		
		TCNotifyVo notifyVo = TCChatManager.getInstance().queryNewNotify();
		if(notifyVo != null){
			if(notifyVo.getType() == NotifyType.AGREE_ADD_FRIEND){
				notifyVo.setContent(notifyVo.getUser().getName() + mContext.getString(R.string.agree_add_friend_with_you));
			}else if(notifyVo.getType() == NotifyType.APPLY_FRIEND){
				notifyVo.setContent(notifyVo.getUser().getName() + mContext.getString(R.string.apply_add_friend_with_you));
			}else if(notifyVo.getType() == NotifyType.REFUSE_ADD_FRIEND){
				notifyVo.setContent(notifyVo.getUser().getName() + mContext.getString(R.string.refuse_add_friend_with_you));
			}else if(notifyVo.getType() == NotifyType.DELETE_FRIEND){
				notifyVo.setContent(notifyVo.getUser().getName() + mContext.getString(R.string.unbind_friendship));
			}
			int unreadNotifyCount = TCChatManager.getInstance().queryNotifyUnreadCount();
			TCSession session = new TCSession();
			session.setSessionName(mContext.getString(R.string.system_info));
			session.setUnreadCount(unreadNotifyCount);
			session.setLastMessageTime(notifyVo.getTime());
			session.setChatType(NOTIFY_TYPE);
			TCMessage message = new TCMessage();
			TextMessageBody body = new TextMessageBody(notifyVo.getContent());
			message.setTextMessageBody(body);
			session.setTCMessage(message);
			sessionList.add(session);
		}
		
		sort(sessionList);
		
	}
	
	private void sort(final List<TCSession> sessionList){
		new Thread(){
			public void run(){
				
				if(sessionList != null && sessionList.size() != 0){
					Collections.sort(sessionList, new Comparator<TCSession>() {

						@Override
						public int compare(TCSession lhs, TCSession rhs) {
							return ((Long)(rhs.getLastMessageTime())).compareTo((Long)(lhs.getLastMessageTime()));
						}
					});
				}
				
				Message message = new Message();
				message.what = REFRESH_ADAPTER;
				message.obj = sessionList;
				mHandler.sendMessage(message);
			}
		}.start();
	}
	

	@Override
	void setupViews(View contentView) {
		setTitleContent(0, R.drawable.title_add_temp_chat_btn, mContext.getString(R.string.chat_tab));
		mRightBtn.setOnClickListener(this);
		
		mListView = (ListView) contentView.findViewById(R.id.session_list);
		initSession();
	}
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_ADAPTER:
				List<TCSession> sessionList = (List<TCSession>) msg.obj;
				if(sessionList != null){
					if(mSessionList != null){
						mSessionList.clear();
					}
					
					mSessionList.addAll(sessionList);
				}
				updateListView();
				break;
			}
		}
		
	};
	
	private void updateListView(){
		if(mAdapter != null){
			mAdapter.setData(mSessionList);
			mAdapter.notifyDataSetChanged();
			return;
		}
		
		mAdapter = new SessionAdapter(mContext, mSessionList, mWidth);
		mAdapter.setItemBtnClickListener(mListener);
		mListView.setAdapter(mAdapter);
	}
	
	private ItemButtonClickListener mListener = new ItemButtonClickListener() {
		
		@Override
		public void onItemBtnClick(View v, int position) {
			switch (v.getId()) {
			case R.id.deletebtn:
				if(mSessionList.get(position).getChatType() != NOTIFY_TYPE){
					TCChatManager.getInstance().deleteSession(mSessionList.get(position).getFromId(), mSessionList.get(position).getChatType());
					mSessionList.remove(position);
					updateListView();
					mContext.sendBroadcast(new Intent(MainActivity.UPDATE_SESSION_UNREAD_COUNT));
				}
				break;
				
			case R.id.ll_content:
				
				if(mSessionList.get(position).getChatType() == NOTIFY_TYPE){
					Intent intent = new Intent(mContext, NotifyActivity.class);
					startActivity(intent);
				}else {
					Intent intent = new Intent(mContext, ChatMainActivity.class);
					intent.putExtra("session", mSessionList.get(position));
					startActivity(intent);
				}
				break;

			default:
				break;
			}
		}
	};


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.right_btn:
			
			Intent addIntent = new Intent(mContext, NewTempChatActivity.class);
			startActivity(addIntent);
			break;
			
		default:
			break;
		}
	}
}
