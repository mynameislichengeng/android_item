package com.xguanjia.hx.haoxinchat;

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
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;

import com.chinamobile.salary.R;
import com.haoqee.chat.ChatMainActivity;
import com.haoqee.chat.NotifyActivity;
import com.haoqee.chat.adapter.SessionAdapter;
import com.haoqee.chat.entity.NotifyType;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.filecabinet.activity.FileListActivity;
import com.xguanjia.hx.notice.NoticeActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.entity.TextMessageBody;

public class ChatActivity extends BaseActivity implements OnClickListener {

	public static final String EMOJIREX = "emoji_[\\d]{0,3}";

	public ListView mListView;

	/** 更新会话列表通知 */
	public static final String UPDATE_COUNT_ACTION = "com.xguanjia.sdydbg.haoxinchat.UPDATE_COUNT_ACTION";

	private static final int REFRESH_ADAPTER = 0x00001;

	public List<TCSession> mSessionList = new ArrayList<TCSession>();
	private SessionAdapter mAdapter;
	public final static int NOTIFY_TYPE = 10000; // 列表中表示通知类型
	protected int mWidth = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		DisplayMetrics metrics = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
		initView();
	}

	private void initView() {
		View view = getLayoutInflater().inflate(R.layout.fragment_chat_layout,
				null);
		LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		mainView.addView(view, layoutParams);
		setTitleText("消息");
		// setTitleRightButton("更新", 0, buttonClickListener);
		mListView = (ListView) view.findViewById(R.id.session_list);
		mAdapter = new SessionAdapter(mContext, mSessionList, mWidth);
		mAdapter.setItemBtnClickListener(mListener);
		mListView.setAdapter(mAdapter);
		IntentFilter filter = new IntentFilter();
		filter.addAction(UPDATE_COUNT_ACTION);
		mContext.registerReceiver(mReceiver, filter);
		initSession();

	}

	/**
	 * 点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_rightBtn:
				// Intent intent = new Intent(ChatActivity.this,
				// NewTempChatActivity.class);
				// startActivity(intent);
				initSession();
				break;
			default:
				break;
			}
		};
	};

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (!TextUtils.isEmpty(action)) {
				if (action.equals(UPDATE_COUNT_ACTION)) {
					initSession();
				}
			}
		}
	};

	private void initSession() {

		List<TCSession> sessionList = TCChatManager.getInstance()
				.getSessionList();

		if (sessionList == null) {
			sessionList = new ArrayList<TCSession>();
		}

		TCNotifyVo notifyVo = TCChatManager.getInstance().queryNewNotify();
		if (notifyVo != null) {
			if (notifyVo.getType() == NotifyType.AGREE_ADD_FRIEND) {
				notifyVo.setContent(notifyVo.getUser().getName()
						+ mContext
								.getString(R.string.agree_add_friend_with_you));
			} else if (notifyVo.getType() == NotifyType.APPLY_FRIEND) {
				notifyVo.setContent(notifyVo.getUser().getName()
						+ mContext
								.getString(R.string.apply_add_friend_with_you));
			} else if (notifyVo.getType() == NotifyType.REFUSE_ADD_FRIEND) {
				notifyVo.setContent(notifyVo.getUser().getName()
						+ mContext
								.getString(R.string.refuse_add_friend_with_you));
			} else if (notifyVo.getType() == NotifyType.DELETE_FRIEND) {
				notifyVo.setContent(notifyVo.getUser().getName()
						+ mContext.getString(R.string.unbind_friendship));
			}
			int unreadNotifyCount = TCChatManager.getInstance()
					.queryNotifyUnreadCount();
			TCMessage message = new TCMessage();
			TextMessageBody body = new TextMessageBody(notifyVo.getContent());
			message.setTextMessageBody(body);

			TCSession session = new TCSession();
			session.setSessionName(mContext.getString(R.string.system_info));
			session.setUnreadCount(unreadNotifyCount);
			session.setLastMessageTime(notifyVo.getTime());
			session.setChatType(NOTIFY_TYPE);
			session.setTCMessage(message);
			sessionList.add(session);
		}

		sort(sessionList);

	}

	private void sort(final List<TCSession> sessionList) {
		new Thread() {
			public void run() {

				if (sessionList != null && sessionList.size() != 0) {
					for (int i = 0; i < sessionList.size(); i++) {

						TCSession session = sessionList.get(i);
						if (session != null) {
							String head = session.getSessionHead();
							if (head != null && !"".equals(head)
									&& !head.startsWith("http")) {
								session.setSessionHead(Constants.IM_HEAD_ADDRESS
										+ head);
							}

						}

					}

					Collections.sort(sessionList, new Comparator<TCSession>() {

						@Override
						public int compare(TCSession lhs, TCSession rhs) {
							return ((Long) (rhs.getLastMessageTime()))
									.compareTo((Long) (lhs.getLastMessageTime()));
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

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_ADAPTER:
				List<TCSession> sessionList = (List<TCSession>) msg.obj;
				if (sessionList != null) {
					if (mSessionList != null) {
						mSessionList.clear();
					}

					mSessionList.addAll(sessionList);
				}
				updateListView();
				break;
			}
		}

	};

	private void updateListView() {
		if (mAdapter != null) {
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
				// if (mSessionList.get(position).getChatType() != NOTIFY_TYPE)
				// {
				TCChatManager.getInstance().deleteSession(
						mSessionList.get(position).getFromId(),
						mSessionList.get(position).getChatType());
				mSessionList.remove(position);
				updateListView();
				mContext.sendBroadcast(new Intent(
						HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
				// }
				break;

			case R.id.ll_content:

				if (mSessionList.get(position).getChatType() == NOTIFY_TYPE) {
					Intent intent = new Intent(mContext, NotifyActivity.class);
					startActivity(intent);
				} else if (mSessionList.get(position).getSessionName()
						.equals("通知公告提醒")) {
					Intent intent = new Intent();
					intent.setClass(mContext, NoticeActivity.class);
					intent.putExtra("session", mSessionList.get(position));
					startActivity(intent);

				} else if (mSessionList.get(position).getSessionName()
						.equals("文件柜提醒")) {
					Intent intent = new Intent();
					intent.setClass(mContext, FileListActivity.class);
					intent.putExtra("session", mSessionList.get(position));
					intent.putExtra("shareType", "2");
					startActivity(intent);

				} else {
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

}
