package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.adapter.MenberListAdapter;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.Interface.TCMenberListListener;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCPageInfo;
import com.haoqee.chatsdk.entity.TCUser;

/**
 * 群成员列表页面
 *
 */
public class MenberListActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private List<TCUser> mUserList = new ArrayList<TCUser>();
	private String mGroupID = ""; // 群ID
	private int mGroupRole = 0; // 群角色 1--管理员
	private MenberListAdapter mAdapter; // 成员列表适配器

	private ListView mListView;
	private LinearLayout mFootView; // 列表加载更多控件
	private boolean mIsLoading = false; // 是否正在加载数据
	private boolean mNoMore; // 是否还有更多数据
	private TCPageInfo mPageInfo;
	public final static int KICK_MENBER_SUCCESS = 0x11111; // 踢人成功消息

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {
		View view = getLayoutInflater().inflate(R.layout.fragment_chat_layout,
				null);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mainView.addView(view, params);
		setTitleText("群成员");
		ImageView imageView1 = (ImageView) findViewById(R.id.title_leftImage);
		imageView1.setVisibility(View.VISIBLE);
		imageView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MenberListActivity.this.finish();

			}
		});

		mGroupID = getIntent().getStringExtra("groupid");
		mGroupRole = getIntent().getIntExtra("grouprole", 0);

		mListView = (ListView) findViewById(R.id.session_list);
		mListView.setCacheColorHint(0);

		mListView.setOnItemClickListener(this);

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_IDLE:
					if (view.getLastVisiblePosition() == (view.getCount() - 1)
							&& !mNoMore && !mIsLoading) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR;
						message.obj = mFootView;
						mHandler.sendMessage(message);
					}
					break;

				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});

		Message message = new Message();
		message.obj = mContext.getString(R.string.add_more_loading);
		message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);

		getMenberList(HaoXinProjectActivity.LIST_LOAD_FIRST);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			this.finish();
			break;

		default:
			break;
		}
	}

	private void updateListView() {

		mListView.setVisibility(View.VISIBLE);
		if (mPageInfo != null) {
			int currentPage = mPageInfo.currentPage + 1;
			if (currentPage > mPageInfo.totalPage) {
				mNoMore = true;
			} else {
				mNoMore = false;
			}
		}

		mAdapter = new MenberListAdapter(mContext, mUserList, mGroupRole);
		mAdapter.setItemBtnClickListener(new ItemButtonClickListener() {

			@Override
			public void onItemBtnClick(View v, int position) {
				switch (v.getId()) {
				case R.id.kickBtn:
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);

					kickMenber(position);
					break;

				default:
					break;
				}
			}
		});
		if (mListView.getFooterViewsCount() == 0) {
			mFootView = (LinearLayout) LayoutInflater.from(this).inflate(
					R.layout.hometab_listview_footer, null);
			ProgressBar pb = (ProgressBar) mFootView
					.findViewById(R.id.hometab_addmore_progressbar);
			pb.setVisibility(View.GONE);

			mListView.addFooterView(mFootView);

			if (mNoMore) {
				((TextView) mFootView.findViewById(R.id.hometab_footer_text))
						.setText(mContext.getString(R.string.no_more_data));
			} else {
				((TextView) mFootView.findViewById(R.id.hometab_footer_text))
						.setText(mContext.getString(R.string.add_more));
			}
		} else {
			if (mNoMore) {
				((TextView) mFootView.findViewById(R.id.hometab_footer_text))
						.setText(mContext.getString(R.string.no_more_data));
			} else {
				((TextView) mFootView.findViewById(R.id.hometab_footer_text))
						.setText(mContext.getString(R.string.add_more));
			}
		}

		mListView.setAdapter(mAdapter);

	}

	private void kickMenber(final int pos) {
		TCChatManager.getInstance().kickGroupMenber(mGroupID,
				mUserList.get(pos).getUserID(), new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {

						Intent intent = new Intent(
								GroupDetailActivity.ACTION_AGREE_ADD_GROUP);
						intent.putExtra("id", mGroupID);
						mContext.sendBroadcast(intent);

						Message message = new Message();
						message.what = KICK_MENBER_SUCCESS;
						message.arg1 = pos;
						mHandler.sendMessage(message);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 获取成员列表
	 * 
	 * @param loadType
	 *            加载类型
	 */
	private void getMenberList(final int loadType) {
		mIsLoading = true;

		int currentPage = 1;
		switch (loadType) {
		case HaoXinProjectActivity.LIST_LOAD_FIRST:
		case HaoXinProjectActivity.LIST_LOAD_REFERSH:
			currentPage = 1;
			break;
		case HaoXinProjectActivity.LIST_LOAD_MORE:
			if (mPageInfo != null) {
				currentPage = mPageInfo.currentPage + 1;
				if (currentPage >= mPageInfo.totalPage) {
					mNoMore = true;
				} else {
					mNoMore = false;
				}
			}
			break;
		default:
			break;
		}

		final int page = currentPage;

		TCChatManager.getInstance().getGroupMenberList(mGroupID, currentPage,
				new TCMenberListListener() {

					@Override
					public void onError(TCError error) {
						mIsLoading = false;

						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);

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
					}

					@Override
					public void doComplete() {

					}

					@Override
					public void doComplete(List userList, TCPageInfo pageInfo) {
						mIsLoading = false;
						mPageInfo = pageInfo;

						if (mPageInfo == null || page >= mPageInfo.totalPage) {
							mNoMore = true;
						} else {
							mNoMore = false;
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
					}

					@Override
					public void onProgress(int progress) {

					}
				});

	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();

				List<TCUser> userList = (List<TCUser>) msg.obj;

				if (userList != null) {
					if (mUserList != null) {
						mUserList.clear();
					}

					mUserList.addAll(userList);
				}
				updateListView();
				break;

			case HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR:
				LinearLayout footView = (LinearLayout) msg.obj;
				ProgressBar pb = (ProgressBar) footView
						.findViewById(R.id.hometab_addmore_progressbar);
				pb.setVisibility(View.VISIBLE);
				TextView more = (TextView) footView
						.findViewById(R.id.hometab_footer_text);
				more.setText(mContext.getString(R.string.add_more_loading));
				getMenberList(HaoXinProjectActivity.LIST_LOAD_MORE);

				break;

			case HaoXinProjectActivity.HIDE_LOADINGMORE_INDECATOR:
				if (mFootView != null) {
					ProgressBar pbar = (ProgressBar) mFootView
							.findViewById(R.id.hometab_addmore_progressbar);
					pbar.setVisibility(View.GONE);
					TextView moreView = (TextView) mFootView
							.findViewById(R.id.hometab_footer_text);
					moreView.setText(R.string.add_more);
				}

				if (mNoMore) {
					((TextView) mFootView
							.findViewById(R.id.hometab_footer_text))
							.setText(mContext.getString(R.string.no_more_data));
				} else {
					((TextView) mFootView
							.findViewById(R.id.hometab_footer_text))
							.setText(mContext.getString(R.string.add_more));
				}

				List<TCUser> moreUserList = (List<TCUser>) msg.obj;

				if (moreUserList != null) {
					mUserList.addAll(moreUserList);
				}

				if (mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				}

				break;

			case KICK_MENBER_SUCCESS:
				baseHideProgressDialog();
				Toast.makeText(mContext, R.string.remove_success,
						Toast.LENGTH_LONG).show();
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

			if (!mUserList.get(arg2).getUserID()
					.equals(Common.getUid(mContext))) {
				Intent intent = new Intent(mContext, UserInfoActivity.class);
				intent.putExtra("fuid", mUserList.get(arg2).getUserID());
				mContext.startActivity(intent);
			}

		} else if (arg2 == mUserList.size() && !mNoMore && !mIsLoading) {
			if (mFootView != null) {
				Message message = new Message();
				message.what = HaoXinProjectActivity.SHOW_LOADINGMORE_INDECATOR;
				message.obj = mFootView;
				mHandler.sendMessage(message);
			}
		}
	}

}
