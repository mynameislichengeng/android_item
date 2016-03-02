package com.xguanjia.hx.haoxinchat;

import java.util.ArrayList;
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
import android.widget.TextView;

import com.haoqee.chat.ChatMainActivity;
import com.haoqee.chat.CreateGroupActivity;
import com.haoqee.chat.adapter.GroupAdapter;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.widget.MyPullToRefreshListView;
import com.haoqee.chat.widget.MyPullToRefreshListView.OnChangeStateListener;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCGroupListListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCPageInfo;
import com.haoqee.chatsdk.entity.TCSession;

public class GroupChatActivity extends BaseActivity implements OnClickListener,
		OnChangeStateListener, OnItemClickListener {

	private MyPullToRefreshListView mContainer; // 下拉刷新控件
	private ListView mListView;
	private boolean mIsRefreshing = false; // 是否正在刷新
	private TextView mRefreshViewLastUpdated; // 下拉刷新时间显示控件
	private List<TCGroup> mGroupList = new ArrayList<TCGroup>();
	private GroupAdapter mAdapter;

	/** 被踢出群通知 */
	public final static String ACTION_KICK_GROUP = "com.xizue.thinchat.fragment.ACTION_KICK_GROUP";

	/** 删除自己群通知 */
	public final static String ACTION_DELETE_MY_GROUP = "com.xizue.thinchat.fragment.ACTION_DELETE_MY_GROUP";

	/** 删除群通知 */
	public final static String ACTION_DELETE_GROUP = "com.xizue.thinchat.fragment.ACTION_DELETE_GROUP";

	/** 加入群通知 */
	public final static String REFRESH_CONTACT_GROUP_ACTION = "com.xizue.thinchat.fragment.REFRESH_CONTACT_GROUP_ACTION";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initViews();
		mContext = this;
	}

	private void initViews() {
		View contentView = getLayoutInflater().inflate(
				R.layout.fragment_group_layout, null);
		LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);
		mainView.addView(contentView, layoutParams);
		setTitleText("群组");
		ImageView imageView = (ImageView) findViewById(R.id.title_rightImage);
		imageView.setVisibility(View.VISIBLE);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent searchIntent = new Intent(GroupChatActivity.this,
						CreateGroupActivity.class);
				startActivity(searchIntent);
			}
		});
		ImageView imageView1 = (ImageView) findViewById(R.id.title_leftImage);
		imageView1.setVisibility(View.VISIBLE);
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GroupChatActivity.this.finish();

			}
		});
		mContainer = (MyPullToRefreshListView) contentView
				.findViewById(R.id.container);
		mContainer.setOnChangeStateListener(this);
		mRefreshViewLastUpdated = (TextView) contentView
				.findViewById(R.id.pull_to_refresh_time);
		mListView = mContainer.getList();
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);
		mListView.setSelector(mContext.getResources().getDrawable(
				R.drawable.transparent_selector));
		mAdapter = new GroupAdapter(mContext, mGroupList);
		mListView.setAdapter(mAdapter);
		mContainer.clickrefresh();

		// 注册Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_KICK_GROUP);
		filter.addAction(REFRESH_CONTACT_GROUP_ACTION);
		filter.addAction(ACTION_DELETE_GROUP);
		filter.addAction(ACTION_DELETE_MY_GROUP);
		mContext.registerReceiver(mReceiver, filter);

	}

	/**
	 * 监听踢出群以及加入群通知
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_KICK_GROUP)) { // 被踢出群组
					String id = intent.getStringExtra("id");
					if (!TextUtils.isEmpty(id)) {

						// 判断群列表中是否存在该id的群，存在则删除，并更新界面
						for (int i = 0; i < mGroupList.size(); i++) {
							if (id.equals(mGroupList.get(i).getGroupID())) {
								mGroupList.remove(i);
								mAdapter.notifyDataSetChanged();
								break;
							}
						}

					}
				} else if (action.equals(REFRESH_CONTACT_GROUP_ACTION)) { // 收到加入群的通知进入
					mContainer.clickrefresh();
				} else if (action.equals(ACTION_DELETE_GROUP)) { // 管理员删除群通知
					String id = intent.getStringExtra("id");
					if (!TextUtils.isEmpty(id)) {

						// 判断群列表中是否存在该id的群，存在则删除，并更新界面
						for (int i = 0; i < mGroupList.size(); i++) {
							if (id.equals(mGroupList.get(i).getGroupID())) {
								mGroupList.remove(i);
								mAdapter.notifyDataSetChanged();
								break;
							}
						}

					}
				} else if (action.equals(ACTION_DELETE_MY_GROUP)) { // 删除自己群通知
					String id = intent.getStringExtra("id");
					if (!TextUtils.isEmpty(id)) {

						// 判断群列表中是否存在该id的群，存在则删除，并更新界面
						for (int i = 0; i < mGroupList.size(); i++) {
							if (id.equals(mGroupList.get(i).getGroupID())) {
								mGroupList.remove(i);
								mAdapter.notifyDataSetChanged();
								break;
							}
						}

					}
				}
			}
		}

	};

	private TCGroupListListener mGroupListListener = new TCGroupListListener() {

		@Override
		public void onError(TCError error) {
			Message message = new Message();
			message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
			message.obj = error.errorMessage;
			mBaseHandler.sendMessage(message);

			mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_SCROLLREFRESH);
		}

		@Override
		public void doComplete() {

		}

		@Override
		public void onProgress(int progress) {

		}

		@Override
		public void doComplete(List groupList) {
			Message message = new Message();
			message.obj = groupList;
			message.arg1 = 1;
			message.what = HaoXinProjectActivity.HIDE_SCROLLREFRESH;
			mHandler.sendMessage(message);
		}

		@Override
		public void doComplete(List groupList, TCPageInfo pageInfo) {

		}
	};

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HaoXinProjectActivity.SHOW_SCROLLREFRESH:
				if (mIsRefreshing) {
					mContainer.onRefreshComplete();
					break;
				}
				mIsRefreshing = true;
				getGroupList();
				break;
			case HaoXinProjectActivity.HIDE_SCROLLREFRESH:
				mIsRefreshing = false;
				mContainer.onRefreshComplete();
				List<TCGroup> groupList = (List<TCGroup>) msg.obj;
				if (groupList != null && groupList.size() != 0) {

					if (mGroupList != null) {
						mGroupList.clear();
					}

					mGroupList.addAll(groupList);
				} else {
					if (mGroupList.size() == 0) {
						groupList = TCChatManager.getInstance().queryGroupList(
								ChatType.GROUP_CHAT);
					}

					if (groupList != null) {
						mGroupList.addAll(groupList);
					}

				}
				updateListView();
				break;
			}
		}
	};

	private void updateListView() {
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
			return;
		}

		mAdapter = new GroupAdapter(mContext, mGroupList);
		mListView.setAdapter(mAdapter);
	}

	private void getGroupList() {
		TCChatManager.getInstance().getGroupList(mGroupListListener);
	}

	@Override
	public void onChangeState(MyPullToRefreshListView container, int state) {
		mRefreshViewLastUpdated.setText(FeatureFunction.getRefreshTime());
		switch (state) {
		case MyPullToRefreshListView.STATE_LOADING:
			mHandler.sendEmptyMessage(HaoXinProjectActivity.SHOW_SCROLLREFRESH);
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent chatIntent = new Intent(mContext, ChatMainActivity.class);
		TCSession session = TCChatManager.getInstance().getSessionByID(
				mGroupList.get(position).getGroupID(), ChatType.GROUP_CHAT);

		if (session == null) {
			session = new TCSession();
			// 设置会话类型
			session.setChatType(ChatType.GROUP_CHAT);
			// 设置会话对象ID
			session.setFromId(mGroupList.get(position).getGroupID());
			// 设置会话名称
			session.setSessionName(mGroupList.get(position).getGroupName());
			// 设置会话头像
			session.setSessionHead(mGroupList.get(position).getGroupLogoSmall());
			// 设置会话对象扩展属性
			// HashMap<String, String> extendMap = new HashMap<String,
			// String>();
			// 设置会话对象简介
			// extendMap.put("content", mGroup.getGroupDescription());
			// session.setExtendMap(extendMap);
		}

		chatIntent.putExtra("session", session);
		startActivity(chatIntent);
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

}
