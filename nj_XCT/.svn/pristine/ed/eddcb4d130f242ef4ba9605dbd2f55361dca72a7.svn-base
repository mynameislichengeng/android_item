package com.haoqee.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.haoqee.chat.adapter.FriendAdapter;
import com.haoqee.chat.adapter.UserAdapter;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.entity.UserList;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCGroupDetailListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.entity.TCUser;

/**
 * 发起群聊页面
 *
 */
public class NewTempChatActivity extends BaseActivity implements
		OnItemClickListener, OnClickListener, OnTouchListener {

	private List<User> mUserList = new ArrayList<User>(); // 好友列表
	private List<TCUser> mOriginalList = new ArrayList<TCUser>(); // 原始临时会话成员列表
	private ListView mListView; // 显示好友列表控件
	private FriendAdapter mAdapter; // 好友列表适配器
	private RelativeLayout mBottomLayout; // 底部已选择好友显示栏
	private HorizontalScrollView mScrollView; // 放置已选择好友列表的滑动控件
	private LinearLayout mUserLayout; // 放置已选择好友列表控件
	private ImageLoader mImageLoader = new ImageLoader(); // 图片下载对象
	private List<TCUser> mSelectedUser = new ArrayList<TCUser>(); // 已选择好友列表
	private List<User> mSearchList = new ArrayList<User>(); // 搜索好友列表
	private EditText mKeywordEditText; // 搜索关键字输入框控件
	private UserAdapter mSearchAdapter; // 搜索好友列表适配器
	private ListView mSearchListView; // 搜索列表显示控件
	private TCUser mChatUser; // 单聊对象

	private final class RemoveWindow implements Runnable {
		public void run() {
			removeWindow();
		}
	}

	private static String[] LETTERS = new String[] { "#", "A", "B", "C", "D",
			"E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
			"R", "S", "T", "U", "V", "W", "X", "Y", "Z" }; // 字母索引数组
	private RemoveWindow mRemoveWindow = new RemoveWindow(); // 隐藏点击字母弹出的对话框
	private WindowManager mWindowManager;
	private TextView mDialogText;
	private LinearLayout mLinearLayout; // 字母索引列表放置空间
	private int mLettersLength = LETTERS.length;
	private HashMap<String, Integer> mSortKeyMap = new HashMap<String, Integer>(); // 当前列表字母索引Map

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.new_tempchat_layout);
		initComponent();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
		removeWindow();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			hideSoftKeyboard();
			if (mSearchListView.getVisibility() == View.VISIBLE) {
				mSearchList.clear();
				mKeywordEditText.setText("");
				mSearchListView.setVisibility(View.GONE);
				mListView.setVisibility(View.VISIBLE);
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private void initComponent() {

		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn,
				mContext.getString(R.string.create_temp_chat));
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		mRightBtn.setEnabled(false);

		mOriginalList = (List<TCUser>) getIntent()
				.getSerializableExtra("users");
		mChatUser = (TCUser) getIntent().getSerializableExtra("user");
		if (mOriginalList != null && mOriginalList.size() != 0) {
			titileTextView
					.setText(mContext.getString(R.string.add_participant));
		}

		mBottomLayout = (RelativeLayout) findViewById(R.id.bottomlayout);
		mBottomLayout.setVisibility(View.VISIBLE);

		mScrollView = (HorizontalScrollView) findViewById(R.id.scrollview);
		mUserLayout = (LinearLayout) findViewById(R.id.userlayout);

		mListView = (ListView) findViewById(R.id.friend_list);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);
		mListView.setSelector(mContext.getResources().getDrawable(
				R.drawable.transparent_selector));

		mSearchListView = (ListView) findViewById(R.id.search_list);
		mSearchListView.setCacheColorHint(0);
		mSearchListView.setOnItemClickListener(this);
		mSearchListView.setSelector(mContext.getResources().getDrawable(
				R.drawable.transparent_selector));

		mKeywordEditText = (EditText) findViewById(R.id.keyword);
		mKeywordEditText.setVisibility(View.VISIBLE);
		findViewById(R.id.contentView).setVisibility(View.GONE);

		mKeywordEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null && !s.toString().equals("")) {

					if (mSearchList != null) {
						mSearchList.clear();
					}

					for (int i = 0; i < mUserList.size(); i++) {
						if (mUserList.get(i).nickName.contains(s.toString())) {
							mSearchList.add(mUserList.get(i));
						}
					}

					mListView.setVisibility(View.GONE);

					updateSearchListView();
				} else {

					if (mSearchList != null) {
						mSearchList.clear();
					}
					for (int i = 0; i < mUserList.size(); i++) {
						mSearchList.add(mUserList.get(i));
					}

					mListView.setVisibility(View.GONE);

					updateSearchListView();

				}
			}
		});

		mWindowManager = (WindowManager) mContext
				.getSystemService(Context.WINDOW_SERVICE);
		mLinearLayout = (LinearLayout) findViewById(R.id.ll_indicator);
		LayoutInflater inflate = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		for (int i = 0; i < LETTERS.length; i++) {
			View view = inflate.inflate(R.layout.textview_key, null);
			TextView textView = (TextView) view.findViewById(R.id.key);
			// TextView textView = new TextView(mContext);
			textView.setText(LETTERS[i]);
			textView.setTextSize(10);
			textView.setTextColor(mContext.getResources().getColor(
					R.color.blue_other));
			view.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, 0, 1.0f));
			;
			int padding = FeatureFunction.dip2px(mContext, 3);
			view.setPadding(padding, 0, padding, 0);
			mLinearLayout.addView(view);
		}
		mLinearLayout.setOnTouchListener(this);
		mDialogText = (TextView) inflate.inflate(R.layout.list_position, null);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler.post(new Runnable() {
			public void run() {
				WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
						LayoutParams.WRAP_CONTENT,
						LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_APPLICATION,
						WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
								| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
						PixelFormat.TRANSLUCENT);
				mWindowManager.addView(mDialogText, lp);
			}
		});

		mListView.setHeaderDividersEnabled(false);

		Message message = new Message();
		message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
		message.obj = mContext.getString(R.string.add_more_loading);
		mHandler.sendMessage(message);

		getFriendList(HaoXinProjectActivity.LIST_LOAD_FIRST);
	}

	private void updateSearchListView() {

		mSearchListView.setVisibility(View.VISIBLE);

		if (mSearchAdapter != null) {
			mSearchAdapter.notifyDataSetChanged();
			return;
		}

		if (mSearchList != null) {
			mSearchAdapter = new UserAdapter(mContext, mSearchList);
			mSearchAdapter.setIsShowCheckBox(true);
			mSearchListView.setAdapter(mSearchAdapter);
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				showProgressDialog(dialogMsg);
				break;
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				hideProgressDialog();

				List<User> firstList = (List<User>) msg.obj;

				if (firstList != null) {
					// 将自己过滤掉
					for (int i = 0; i < firstList.size(); i++) {
						if (Common.getLoginResult(mContext).uid
								.equals(firstList.get(i).uid)) {
							firstList.remove(i);
							break;

						}

					}

					// 如果临时会话中成员列表不为空，则移除已存在的成员
					if (mOriginalList != null && mOriginalList.size() != 0) {
						for (int i = 0; i < mOriginalList.size(); i++) {
							for (int j = 0; j < firstList.size(); j++) {
								if (mOriginalList.get(i).getUserID()
										.equals(firstList.get(j).uid)) {
									firstList.remove(j);
									j--;
									break;
								}
							}
						}
					} else if (mChatUser != null) { // 如果单聊用户不为空，则移除单聊用户
						for (int i = 0; i < firstList.size(); i++) {
							if (firstList.get(i).uid.equals(mChatUser
									.getUserID())) {
								firstList.remove(i);
								break;
							}
						}
					}

					if (mUserList != null) {
						mUserList.clear();
					}

					mUserList.addAll(firstList);
				}

				updateListView();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				String error_Detail = (String) msg.obj;
				if (error_Detail != null && !error_Detail.equals("")) {
					Toast.makeText(mContext, error_Detail, Toast.LENGTH_LONG)
							.show();
				} else {
					Toast.makeText(mContext, R.string.load_error,
							Toast.LENGTH_LONG).show();
				}
				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				String message = (String) msg.obj;
				if (!TextUtils.isEmpty(message)) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			}
		}
	};

	private void addView(final User user) {

		ImageView imageView = new ImageView(mContext);
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < mSelectedUser.size(); i++) {
					if (mSelectedUser.get(i).getUserID().equals(user.uid)) {
						mSelectedUser.remove(i);
						mUserLayout.removeViewAt(i);
						if (mSearchList != null && mSearchList.size() != 0) {
							for (int j = 0; j < mSearchList.size(); j++) {
								if (mSearchList.get(j).uid.equals(user.uid)) {
									mSearchList.get(j).isShow = false;
									for (int k = 0; k < mUserList.size(); j++) {
										if (mUserList.get(k).uid
												.equals(user.uid)) {
											mUserList.get(k).isShow = false;
											updateListView();
											break;
										}
									}
									break;
								}
							}
							updateSearchListView();
						} else {
							for (int j = 0; j < mUserList.size(); j++) {
								if (mUserList.get(j).uid.equals(user.uid)) {
									mUserList.get(j).isShow = false;
									break;
								}
							}

							updateListView();
						}

						if (mSelectedUser.size() == 0) {
							mRightBtn.setEnabled(false);
						} else {
							mRightBtn.setEnabled(true);
						}
						break;
					}
				}
			}
		});
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				FeatureFunction.dip2px(mContext, 40), FeatureFunction.dip2px(
						mContext, 40));
		params.rightMargin = FeatureFunction.dip2px(mContext, 5);
		imageView.setLayoutParams(params);
		imageView.setImageResource(R.drawable.default_header);
		LinearLayout layout = new LinearLayout(mContext);
		layout.addView(imageView);
		mImageLoader.getBitmap(mContext, imageView, null, user.mSmallHead, 0,
				false, false);
		mUserLayout.addView(layout);
		mUserLayout.invalidate();
		mScrollView.smoothScrollTo(mUserLayout.getMeasuredWidth(), 0);
	}

	@Override
	public boolean onTouch(final View v, MotionEvent event) {
		int height = v.getHeight();
		final int alphaHeight = height / mLettersLength;
		final int fingerY = (int) event.getY();
		int selectIndex = fingerY / alphaHeight;
		if (selectIndex < 0 || selectIndex > mLettersLength - 1) {
			mLinearLayout.setBackgroundColor(Color.parseColor("#00ffffff"));
			mHandler.removeCallbacks(mRemoveWindow);
			mHandler.post(mRemoveWindow);
			return true;
		}
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			String letter = LETTERS[selectIndex];
			findLocation(letter);
			break;
		case MotionEvent.ACTION_MOVE:
			letter = LETTERS[selectIndex];
			findLocation(letter);
			break;
		case MotionEvent.ACTION_UP:
			mLinearLayout.setBackgroundColor(Color.parseColor("#00ffffff"));
			mHandler.removeCallbacks(mRemoveWindow);
			mHandler.post(mRemoveWindow);
			break;
		default:
			break;
		}
		return true;
	}

	private void findLocation(String letter) {

		mLinearLayout.setBackgroundResource(android.R.color.darker_gray);
		mDialogText.setVisibility(View.VISIBLE);
		mDialogText.setText(letter);

		if (mSortKeyMap.containsKey(letter)) {
			int position = mSortKeyMap.get(letter);
			mListView.setSelection(position);
		}
	}

	private void removeWindow() {
		mDialogText.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		switch (parent.getId()) {
		case R.id.friend_list:
			if (position < mUserList.size()) {
				if (mUserList.get(position).isShow) {
					mUserList.get(position).isShow = false;

					for (int i = 0; i < mSelectedUser.size(); i++) {
						if (mSelectedUser.get(i).getUserID()
								.equals(mUserList.get(position).uid)) {
							mSelectedUser.remove(i);
							mUserLayout.removeViewAt(i);
							break;
						}
					}
				} else {
					TCUser user = new TCUser();
					user.setUserID(mUserList.get(position).uid);
					user.setName(mUserList.get(position).nickName);
					user.setHead(mUserList.get(position).mSmallHead);
					mSelectedUser.add(user);
					addView(mUserList.get(position));
					mUserList.get(position).isShow = true;
				}

				updateListView();

				if (mSelectedUser.size() == 0) {
					mRightBtn.setEnabled(false);
				} else {
					mRightBtn.setEnabled(true);
				}
			}
			break;

		case R.id.search_list:
			if (position < mSearchList.size()) {
				if (mSearchList.get(position).isShow) {
					mSearchList.get(position).isShow = false;

					for (int i = 0; i < mSelectedUser.size(); i++) {
						if (mSelectedUser.get(i).getUserID()
								.equals(mSearchList.get(position).uid)) {

							for (int j = 0; j < mUserList.size(); i++) {
								if (mSearchList.get(position).uid
										.equals(mUserList.get(j).uid)) {
									mUserList.get(j).isShow = false;
									updateListView();
									break;
								}
							}

							mSelectedUser.remove(i);
							mUserLayout.removeViewAt(i);
							break;
						}
					}

				} else {
					TCUser user = new TCUser();
					user.setUserID(mSearchList.get(position).uid);
					user.setName(mSearchList.get(position).nickName);
					user.setHead(mSearchList.get(position).mSmallHead);
					mSelectedUser.add(user);

					addView(mSearchList.get(position));
					mSearchList.get(position).isShow = true;

					for (int i = 0; i < mUserList.size(); i++) {
						if (mSearchList.get(position).uid.equals(mUserList
								.get(i).uid)) {
							mUserList.get(i).isShow = true;
							updateListView();
							break;
						}
					}
				}

				updateSearchListView();

				if (mSelectedUser.size() == 0) {
					mRightBtn.setEnabled(false);
				} else {
					mRightBtn.setEnabled(true);
				}
			}
			break;

		default:
			break;
		}
	}

	private void updateListView() {

		if (mAdapter != null) {
			mAdapter.setData(mUserList);
			mSortKeyMap = mAdapter.getSortKey();
			mAdapter.notifyDataSetChanged();
			return;
		}

		if (mUserList != null) {

			mAdapter = new FriendAdapter(mContext, mUserList);
			mAdapter.setIsShowCheckBox(true);
			mSortKeyMap = mAdapter.getSortKey();
			mListView.setVisibility(View.VISIBLE);
			mListView.setAdapter(mAdapter);
		}

	}

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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			hideSoftKeyboard();
			this.finish();
			break;

		case R.id.okbtn:
			Intent intent = new Intent();
			intent.putExtra("userlist", (Serializable) mSelectedUser);
			setResult(RESULT_OK, intent);
			NewTempChatActivity.this.finish();
			break;

		case R.id.right_btn:
			hideSoftKeyboard();
			int count = mSelectedUser.size();

			if (mOriginalList != null && mOriginalList.size() != 0) {
				Intent addIntent = new Intent();
				addIntent.putExtra("userlist", (Serializable) mSelectedUser);
				setResult(RESULT_OK, addIntent);
				NewTempChatActivity.this.finish();
			} else {

				if (mChatUser == null && count == 1) {
					Intent chatIntent = new Intent(mContext,
							ChatMainActivity.class);
					TCSession session = TCChatManager.getInstance()
							.getSessionByID(mSelectedUser.get(0).getUserID(),
									ChatType.SINGLE_CHAT);
					if (session == null) {
						session = new TCSession();
						session.setFromId(mSelectedUser.get(0).getUserID());
						session.setChatType(ChatType.SINGLE_CHAT);
						HashMap<String, String> extendMap = new HashMap<String, String>();
						extendMap.put("name", mSelectedUser.get(0).getName());
						extendMap.put("head",
								Constants.IM_HEAD_ADDRESS
										+ mSelectedUser.get(0).getHead());

					}

					chatIntent.putExtra("session", session);
					startActivity(chatIntent);

					NewTempChatActivity.this.finish();
				} else {

					Message message = new Message();
					message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
					message.obj = mContext.getString(R.string.send_loading);
					mHandler.sendMessage(message);

					createTempChat();
				}
			}

			break;

		default:
			break;
		}
	}

	private void createTempChat() {

		int count = mSelectedUser.size();

		if (count >= 4) {
			count = 4;
		}

		final StringBuffer name = new StringBuffer();
		StringBuffer uids = new StringBuffer();
		final StringBuffer heads = new StringBuffer();
		final StringBuffer sessionName = new StringBuffer();

		int initCount = 4;
		if (mChatUser != null) {
			initCount = 3;
			name.append(mChatUser.getName() + ",");
			heads.append(mChatUser.getHead() + ",");
			uids.append(mChatUser.getUserID() + ",");
			sessionName.append(mChatUser.getName() + ",");
		}

		if (count < initCount) {
			name.append(Common.getLoginResult(mContext).nickName);
			name.append(",");
			heads.append(Common.getLoginResult(mContext).mSmallHead);
			heads.append(",");
		}

		for (int i = 0; i < mSelectedUser.size(); i++) {
			if (i < count) {
				if (i == count - 1) {
					name.append(mSelectedUser.get(i).getName());
					heads.append(mSelectedUser.get(i).getHead());
				} else {
					name.append(mSelectedUser.get(i).getName());
					name.append(",");
					heads.append(mSelectedUser.get(i).getHead());
					heads.append(",");
				}
			}

			if (i == mSelectedUser.size() - 1) {
				uids.append(mSelectedUser.get(i).getUserID());
				sessionName.append(mSelectedUser.get(i).getName());
			} else {
				uids.append(mSelectedUser.get(i).getUserID());
				uids.append(",");
				sessionName.append(mSelectedUser.get(i).getName());
				sessionName.append(",");
			}
		}

		TCChatManager.getInstance().createTempChat(name.toString(),
				uids.toString(), new TCGroupDetailListener() {

					@Override
					public void onError(TCError error) {

					}

					@Override
					public void doComplete() {

					}

					@Override
					public void doComplete(TCGroup group) {

						/**
						 * 将新增临时会话插入会话表中
						 */

						TCSession session = new TCSession();

						// 设置会话类型
						session.setChatType(ChatType.TEMP_CHAT);
						// 设置会话对象ID
						session.setFromId(group.getGroupID());
						// 设置会话名称
						session.setSessionName(name.toString());
						// 设置会话头像
						session.setSessionHead(heads.toString());
						// 设置会话对象扩展属性
						// HashMap<String, String> extendMap = new
						// HashMap<String, String>();
						// 设置会话对象简介
						// extendMap.put("content",
						// mGroup.getGroupDescription());
						// session.setExtendMap(extendMap);

						// 设置临时会话创建时间
						session.setLastMessageTime(group.getGroupCreateTime() * 1000);

						// 设置临时会话后会话列表显示的内容
						session.setSessionContent(mContext
								.getString(R.string.you_invited)
								+ sessionName.toString()
								+ mContext.getString(R.string.add_temp_chat));
						TCChatManager.getInstance().addSession(session);

						mContext.sendBroadcast(new Intent(
								ChatActivity.UPDATE_COUNT_ACTION));

						if (mChatUser != null) {
							mContext.sendBroadcast(new Intent(
									ChatDetailActivity.ACTION_DESTROY_CHAT_DETAIL_PAGE));
							mContext.sendBroadcast(new Intent(
									ChatMainActivity.DESTORY_ACTION));
						}

						Intent intent = new Intent(mContext,
								ChatMainActivity.class);
						intent.putExtra("session", session);
						startActivity(intent);

						NewTempChatActivity.this.finish();
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

}
