package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.haoqee.chat.adapter.ServerAdapter;
import com.haoqee.chat.entity.Server;
import com.haoqee.chat.global.Common;
import com.chinamobile.salary.R;

public class ServerActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private ListView mListView;
	private List<Server> mServerList; // 通知类表数据
	private boolean mIsRegisterReceiver = false;
	private ServerAdapter mAdapter; // 通知适配器
	/** 是否处于编辑模式 */
	private boolean mIsEdit = false;

	private LinearLayout mFootView;

	/** 刷新服务器地址列表通知 */
	public final static String ACTION_REFRESH_SERVER_LIST = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_SERVER_LIST";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify_page);
		// 注册Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_REFRESH_SERVER_LIST);
		registerReceiver(mReceiver, filter);
		mIsRegisterReceiver = true;
		initComponent();
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals(ACTION_REFRESH_SERVER_LIST)) {
					mServerList = Common.getServerList(mContext);

					if (mServerList == null) {
						mServerList = new ArrayList<Server>();
					}

					mAdapter.setData(mServerList);
					mAdapter.notifyDataSetChanged();
				}
			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		setTitleContent(R.drawable.back_btn, R.drawable.edit_btn,
				mContext.getString(R.string.server));
		findViewById(R.id.leftlayout).setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.notify_list);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);
		mListView.setFooterDividersEnabled(false);

		mServerList = Common.getServerList(mContext);

		if (mServerList == null) {
			mServerList = new ArrayList<Server>();
		}

		mFootView = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.server_list_footer, null);

		mAdapter = new ServerAdapter(mContext, mServerList);
		mListView.addFooterView(mFootView);
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void onDestroy() {
		if (mIsRegisterReceiver) {
			unregisterReceiver(mReceiver);
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 点击返回按钮
		case R.id.leftlayout:
			this.finish();
			break;

		case R.id.right_btn:
			if (mIsEdit) { // 如果是编辑状态，点击则显示编辑按钮，并隐藏每项删除按钮
				mIsEdit = false;
				mRightBtn.setImageResource(R.drawable.edit_btn);
				mFootView.setVisibility(View.VISIBLE);
				showCancelBtn(false);
			} else { // 如果不是编辑状态，则点击显示完成按钮，并显示每项删除按钮，
				mIsEdit = true;
				mRightBtn.setImageResource(R.drawable.title_complete_btn);
				mFootView.setVisibility(View.GONE);
				showCancelBtn(true);
			}
			break;

		default:
			break;
		}
	}

	public void showCancelBtn(boolean isShow) {
		mAdapter.mIsCancel = isShow;
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position < mServerList.size()) {

			if (!mServerList.get(position).mIsCurrent) {
				for (int i = 0; i < mServerList.size(); i++) {
					mServerList.get(i).mIsCurrent = false;
				}

				mServerList.get(position).mIsCurrent = true;

				Common.saveCurrentServer(mContext, mServerList.get(position));
				Common.saveServerList(mContext, mServerList);

				// Common.getThinkchatInfo().setServer();

				mAdapter.notifyDataSetChanged();
			}

		} else if (position == mServerList.size()) {
			Intent intent = new Intent(mContext, AddServerActivity.class);
			startActivity(intent);
		}
	}
}
