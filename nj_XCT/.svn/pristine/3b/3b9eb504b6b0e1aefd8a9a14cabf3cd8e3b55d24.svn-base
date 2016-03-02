package com.haoqee.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.haoqee.chat.adapter.InviteUserListAdapter;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.entity.UserList;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.entity.TCError;

/**
 * 邀请成员页面
 *
 */
public class InviteUserListActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private List<User> mUserList = new ArrayList<User>(); // 待邀请的用户列表
	private String mGroupID = ""; // 群ID
	private InviteUserListAdapter mAdapter; // 列表适配器

	private ListView mListView;
	public final static int INVITE_USER_SUCCESS = 0x11111; // 邀请成功消息

	private List<String> mJoinUids; // 已加入的用户ID列表

	/** 刷新当前群已加入的用户ID串 */
	public final static String ACTION_REFRESH_JOIN_UIDS = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_JOIN_UIDS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_REFRESH_JOIN_UIDS);
		registerReceiver(mReceiver, filter);
		initComponent();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_REFRESH_JOIN_UIDS)) {
					String groupid = intent.getStringExtra("groupid");
					if (!TextUtils.isEmpty(groupid) && groupid.equals(mGroupID)) {
						String uids = intent.getStringExtra("uids");
						if (!TextUtils.isEmpty(uids)) {
							mJoinUids = Arrays.asList(uids.split(","));
						} else {
							mJoinUids.clear();
						}
					}
				}

			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void initComponent() {
		View view = getLayoutInflater().inflate(R.layout.fragment_chat_layout,
				null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mainView.addView(view, params);
		setTitleText("好友列表");
		ImageView imageView1 = (ImageView) findViewById(R.id.title_leftImage);
		imageView1.setVisibility(View.VISIBLE);
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				InviteUserListActivity.this.finish();

			}
		});

		mGroupID = getIntent().getStringExtra("groupid");
		String uids = getIntent().getStringExtra("uids");
		// 将已加入的用户用户串专程用户ID列表
		if (!TextUtils.isEmpty(uids)) {
			mJoinUids = Arrays.asList(uids.split(","));
		}

		if (mJoinUids == null) {
			mJoinUids = new ArrayList<String>();
		}

		mListView = (ListView) findViewById(R.id.session_list);
		mListView.setCacheColorHint(0);

		mListView.setOnItemClickListener(this);

		Message message = new Message();
		message.obj = mContext.getString(R.string.add_more_loading);
		message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);

		getFriendList(HaoXinProjectActivity.LIST_LOAD_FIRST);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	private void updateListView() {

		mListView.setVisibility(View.VISIBLE);

		mAdapter = new InviteUserListAdapter(mContext, mUserList);
		// 设置列表项中点击监听事件
		mAdapter.setItemBtnClickListener(new ItemButtonClickListener() {

			@Override
			public void onItemBtnClick(View v, int position) {
				switch (v.getId()) {
				case R.id.kickBtn: // 点击邀请按钮
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);

					inviteUser(position);
					break;

				default:
					break;
				}
			}
		});

		mListView.setAdapter(mAdapter);

	}

	/**
	 * 邀请用户
	 * 
	 * @param pos
	 *            带邀请的用户所在的列表位置
	 */
	private void inviteUser(final int pos) {
		TCChatManager.getInstance().inviteAddGroup(mGroupID,
				mUserList.get(pos).uid, new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {
						// 邀请成功
						Message message = new Message();
						message.what = INVITE_USER_SUCCESS;
						message.arg1 = pos;
						mHandler.sendMessage(message);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 获取好友列表
	 * 
	 * @param loadType
	 *            加载类型
	 */
	private void getFriendList(final int loadType) {
		new Thread() {
			@Override
			public void run() {
				if (Common.verifyNetwork(mContext)) {

					List<User> userList = null;

					try {
						UserList users = Common.getThinkchatInfo()
								.getFriendList();

						if (users != null && users.mState != null
								&& users.mState.code == 0) {
							if (users.mUserList != null) {
								userList = new ArrayList<User>();
								userList.addAll(users.mUserList);
							}
						} else {
							Message msg = new Message();
							msg.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							if (users != null && users.mState != null
									&& users.mState.errorMsg != null
									&& !users.mState.errorMsg.equals("")) {
								msg.obj = users.mState.errorMsg;
							} else {
								msg.obj = mContext
										.getString(R.string.load_error);
							}
							mHandler.sendMessage(msg);
						}
					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj = mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}

					Message message = new Message();
					message.obj = userList;

					switch (loadType) {
					case HaoXinProjectActivity.LIST_LOAD_FIRST:
						message.what = HaoXinProjectActivity.HIDE_PROGRESS_DIALOG;
						break;
					case HaoXinProjectActivity.LIST_LOAD_MORE:
						message.what = HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR;

					case HaoXinProjectActivity.LIST_LOAD_REFERSH:
						message.what = HaoXinProjectActivity.HIDE_SCROLLREFRESH;
						break;

					default:
						break;
					}

					mHandler.sendMessage(message);
				} else {
					switch (loadType) {
					case HaoXinProjectActivity.LIST_LOAD_FIRST:
						mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						break;
					case HaoXinProjectActivity.LIST_LOAD_MORE:
						mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR);

					case HaoXinProjectActivity.LIST_LOAD_REFERSH:
						mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_SCROLLREFRESH);
						break;

					default:
						break;
					}
					mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
				}
			}
		}.start();
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();

				List<User> userList = (List<User>) msg.obj;

				if (userList != null) {
					// 将自己过滤掉
					for (int i = 0; i < userList.size(); i++) {
						if (Common.getLoginResult(mContext).uid.equals(userList
								.get(i).uid)) {
							userList.remove(i);
							break;

						}

					}
					// 将好友列表的已存在的群成员移除
					for (int i = 0; i < mJoinUids.size(); i++) {
						for (int j = 0; j < userList.size(); j++) {
							if (mJoinUids.get(i).equals(userList.get(j).uid)) {
								userList.remove(j);
								j--;
								break;
							}
						}
					}

					if (mUserList != null) {
						mUserList.clear();
					}
					// 将剩余的好友列表加入到邀请列表中
					mUserList.addAll(userList);
				}
				// 更新界面
				updateListView();
				break;

			case INVITE_USER_SUCCESS:
				baseHideProgressDialog();
				Toast.makeText(mContext, R.string.invite_success,
						Toast.LENGTH_LONG).show();

				// 邀请成功之后，移除被邀请的项，并更新界面
				int pos = msg.arg1;
				mUserList.remove(pos);
				mAdapter.notifyDataSetChanged();
				break;
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 < mUserList.size()) {

			if (!mUserList.get(arg2).uid.equals(Common.getUid(mContext))) {
				Intent intent = new Intent(mContext, UserInfoActivity.class);
				intent.putExtra("fuid", mUserList.get(arg2).uid);
				mContext.startActivity(intent);
			}

		}
	}

}
