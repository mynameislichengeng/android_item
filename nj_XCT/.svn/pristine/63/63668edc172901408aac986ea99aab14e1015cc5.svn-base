package com.haoqee.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.haoqee.chat.adapter.ChatPersonAdapter;
import com.haoqee.chat.entity.ChatDetailEntity;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.widget.MyGridView;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.Interface.TCGroupDetailListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCUser;

/**
 * 聊天信息页面
 *
 */
public class ChatDetailActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnItemLongClickListener {

	private MyGridView mGridView; // 临时会话成员显示控件
	private RelativeLayout mNameLayout; // 临时会话名称点击控件
	private RelativeLayout mNotifyLayout; // 新消息提醒栏点击控件
	private RelativeLayout mClearLayout; // 清空聊天记录栏点击控件
	private ImageView mNotifyIcon; // 接收消息开关状态控件
	private Button mDeleteBtn; // 删除并退出按钮
	private TextView mTempChatNameView; // 会话名称显示控件
	private ChatPersonAdapter mAdapter; // GridView数据列表适配器
	private List<ChatDetailEntity> mList = new ArrayList<ChatDetailEntity>(); // 包含成员列表以及加减图片的数据列表
	private String mGroupID = ""; // 临时会话ID
	private TCGroup mGroup; // 临时会话详细
	private ScrollView mScrollView; // 页面滑动控件
	private ImageView mNameArrowView; // 会话名称右边箭头

	/** 踢人成功消息类型 */
	private final static int KICK_SUCCESS = 0x03301;
	/** 添加人成功消息类型 */
	private final static int ADD_SUCCESS = 0x03302;
	/** 添加人请求 */
	private final static int ADD_REQUEST = 0x03303;
	/** 删除临时会话成功消息类型 */
	private final static int DELETE_TEMP_CHAT_SUCCESS = 0x03306;
	/** 临时会话消息接收设置成功消息类型 */
	private final static int SET_NOTIFY_SUCCESS = 0x03307;
	/** 编辑临时会话名称成功消息类型 */
	private final static int EDIT_TEMP_CHAT_NAME_SUCCESS = 0x03308;
	/** 退出临时会话成功消息类型 */
	private final static int EXIT_TEMP_CHAT_SUCCESS = 0x03309;
	private TCUser mChatUser; // 单聊进入聊天详细时，单聊页面的聊天对象

	/** 销毁聊天详细页面通知 */
	public final static String ACTION_DESTROY_CHAT_DETAIL_PAGE = "com.xizue.thinkchat.intent.action.ACTION_DESTROY_CHAT_DETAIL_PAGE";
	/** 刷新聊天详细数据 */
	public final static String ACTION_REFRESH_CHAT_DETAIL_PAGE = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_CHAT_DETAIL_PAGE";
	/** 编辑临时会话名称请求 */
	public final static int INPUT_TEMP_CHAT_NAME_REQUEST = 0x11111;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.chat_detail_page);

		// 注册当前页所用的通知
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_DESTROY_CHAT_DETAIL_PAGE);
		filter.addAction(ACTION_REFRESH_CHAT_DETAIL_PAGE);
		filter.addAction(HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT);
		registerReceiver(mReceiver, filter);
		initComponent();
	}

	/**
	 * 聊天信息页面接收通知的Receiver
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_DESTROY_CHAT_DETAIL_PAGE)) {
					ChatDetailActivity.this.finish();
				} else if (action.equals(ACTION_REFRESH_CHAT_DETAIL_PAGE)) {
					String groupid = intent.getStringExtra("groupid");

					if (!TextUtils.isEmpty(mGroupID)
							&& !TextUtils.isEmpty(groupid)
							&& mGroupID.equals(groupid)) {
						getGroupDetail();
					}
				} else if (action
						.equals(HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT)) {
					// 如果被踢或者管理员删除了该临时会话，则显示提示对话框
					String groupid = intent.getStringExtra("groupid");
					int excuteType = intent.getIntExtra("excuteType", 0);
					if (!TextUtils.isEmpty(mGroupID)
							&& !TextUtils.isEmpty(groupid)
							&& mGroupID.equals(groupid)) {
						showDestoryDialog(excuteType);
					}
				}
			}
		}
	};

	/**
	 * 初始化控件
	 */
	private void initComponent() {

		setTitleContent(R.drawable.back_btn, 0,
				mContext.getString(R.string.chat_detail));
		mLeftBtn.setOnClickListener(this);

		mGroupID = getIntent().getStringExtra("groupid");
		mChatUser = (TCUser) getIntent().getSerializableExtra("user");

		mScrollView = (ScrollView) findViewById(R.id.scrollView);
		mScrollView.setVisibility(View.GONE);

		mNameLayout = (RelativeLayout) findViewById(R.id.namelayout);
		mNameLayout.setOnClickListener(this);

		mClearLayout = (RelativeLayout) findViewById(R.id.clearlayout);
		mClearLayout.setOnClickListener(this);

		mNotifyLayout = (RelativeLayout) findViewById(R.id.notifylayout);
		mNotifyLayout.setOnClickListener(this);

		mTempChatNameView = (TextView) findViewById(R.id.name);

		mNotifyIcon = (ImageView) findViewById(R.id.notify_btn);
		mNameArrowView = (ImageView) findViewById(R.id.name_arrow);

		mDeleteBtn = (Button) findViewById(R.id.exitbtn);
		mDeleteBtn.setOnClickListener(this);

		mGridView = (MyGridView) findViewById(R.id.gridview);
		mGridView.setOnItemClickListener(this);
		mGridView.setOnItemLongClickListener(this);

		// 如果mGroupID不为空，则获取临时会话详细信息
		if (!TextUtils.isEmpty(mGroupID)) {
			Message notifyMessage = new Message();
			notifyMessage.obj = mContext.getString(R.string.add_more_loading);
			notifyMessage.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
			mBaseHandler.sendMessage(notifyMessage);
			getGroupDetail();

		} else {

			// 从单聊进入，直接显示单聊对应的用户，更新界面

			mNotifyLayout.setVisibility(View.GONE);
			mNameLayout.setVisibility(View.GONE);
			mDeleteBtn.setVisibility(View.GONE);

			mList.add(new ChatDetailEntity(mChatUser, 0));
			mList.add(new ChatDetailEntity(null, 1));

			mAdapter = new ChatPersonAdapter(mContext, mList);
			mGridView.setAdapter(mAdapter);

			mScrollView.setVisibility(View.VISIBLE);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn: // 点击返回按钮
			this.finish();
			break;

		case R.id.clearlayout: // 点击清空聊天记录
			showDealDialog(2);
			break;

		case R.id.notifylayout: // 点击设置新消息接收类型
			Message notifyMessage = new Message();
			notifyMessage.obj = mContext.getString(R.string.send_loading);
			notifyMessage.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
			mBaseHandler.sendMessage(notifyMessage);

			if (mGroup.getGroupGetMsg() == 0) { // 如果关闭了新消息接收，则设置成打开
				setMsgType("1");
			} else if (mGroup.getGroupGetMsg() == 1) { // 如果是打开了新消息接收，则设置成关闭
				setMsgType("0");
			}
			break;

		case R.id.exitbtn: // 点击退出按钮，弹出处理提示对话框
			if (!mGroup.getCreatorID().equals(Common.getUid(mContext))) {
				showDealDialog(0);
			} else {
				showDealDialog(1);
			}
			break;

		case R.id.namelayout: // 如果是管理员，则点击进入输入内容页面输入临时会话名称
			Intent editNameIntent = new Intent(mContext,
					InputContentActivity.class);
			editNameIntent.putExtra("content", mGroup.getGroupName());
			editNameIntent.putExtra("type",
					InputContentActivity.INPUT_TEMP_CHAT_NAME);
			startActivityForResult(editNameIntent, INPUT_TEMP_CHAT_NAME_REQUEST);
			break;

		default:
			break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				update();
				break;

			case DELETE_TEMP_CHAT_SUCCESS: // 删除临时会话成功
				baseHideProgressDialog();

				// 删除成功之后，删除本地数据表中存储的群，会话和消息记录
				TCChatManager.getInstance().deleteGroupFromTable(mGroupID,
						ChatType.TEMP_CHAT);

				TCChatManager.getInstance().deleteSession(mGroupID,
						ChatType.TEMP_CHAT);

				showToast(mContext.getString(R.string.delete_temp_chat_success));

				// 发通知到聊天界面处理删除群之后的操作
				Intent deleteIntent = new Intent(
						HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT);
				deleteIntent.putExtra("id", mGroupID);
				deleteIntent.putExtra("excuteType", 0);
				deleteIntent.putExtra("isexit", true);
				mContext.sendBroadcast(deleteIntent);

				ChatDetailActivity.this.finish();
				break;

			case SET_NOTIFY_SUCCESS: // 消息接收设置成功
				baseHideProgressDialog();
				showToast(mContext.getString(R.string.operate_success));

				// 如果设置的状态更新界面的显示状态
				if (mGroup.getGroupGetMsg() == 1) {
					mNotifyIcon.setImageResource(R.drawable.butn_open);
				} else if (mGroup.getGroupGetMsg() == 0) {
					mNotifyIcon.setImageResource(R.drawable.butn_close);
				}
				break;

			case KICK_SUCCESS: // 踢人成功
				baseHideProgressDialog();
				int pos = msg.arg1;
				hideProgressDialog();

				// 从列表中移除改用户
				mGroup.getUserList().remove(pos);
				mList.remove(pos);

				// 如果临时会话中用户的个数为0，则移除踢人按钮
				if (mGroup.getUserList().size() == 0) {
					mList.remove(mList.size() - 1);
				}

				// 更新界面
				if (mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				}
				break;

			case ADD_SUCCESS: // 添加人成功
				baseHideProgressDialog();

				// 添加用户成功后执行操作
				List<TCUser> userList = (List<TCUser>) msg.obj;
				int addPos = 0;

				// 计算追加到列表的位置
				if (mGroup.getUserList() == null
						|| mGroup.getUserList().size() == 0) {
					// 如果临时会话中用户为空，则追加位置为0
					addPos = 0;

					// 给列表增加编辑按钮
					mList.add(new ChatDetailEntity(null, 2));
				} else {

					// 如果列表不为空，则追加的位置，即为之前临时会话中用户的个数
					addPos = mGroup.getUserList().size();
				}

				if (mGroup.getUserList() == null) {
					mGroup.setUserList(new ArrayList<TCUser>());
				}

				// 添加到用户列表中
				mGroup.getUserList().addAll(userList);

				// 装入到界面所用的列表中
				for (int i = 0; i < userList.size(); i++) {
					mList.add(i + addPos, new ChatDetailEntity(userList.get(i),
							0));
				}

				// 更新界面
				if (mAdapter != null) {
					mAdapter.notifyDataSetChanged();
				} else {
					mAdapter = new ChatPersonAdapter(mContext, mList);
					mGridView.setAdapter(mAdapter);
				}
				break;

			case EDIT_TEMP_CHAT_NAME_SUCCESS: // 编辑临时会话名称成功
				baseHideProgressDialog();
				// 更新界面上显示的名称
				mTempChatNameView.setText(mGroup.getGroupName());
				break;

			case EXIT_TEMP_CHAT_SUCCESS: // 退出临时会话成功
				baseHideProgressDialog();

				// 退出成功之后删除本地存储的对象，以及会话和消息记录
				TCChatManager.getInstance().deleteGroupFromTable(mGroupID,
						ChatType.TEMP_CHAT);
				TCChatManager.getInstance().deleteSession(mGroupID,
						ChatType.TEMP_CHAT);

				showToast(mContext.getString(R.string.exit_temp_chat_success));

				// 发通知到聊天界面，处于退出群之后的操作
				Intent exitIntent = new Intent(
						HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT);
				exitIntent.putExtra("id", mGroupID);
				exitIntent.putExtra("excuteType", 1);
				exitIntent.putExtra("isexit", true);
				mContext.sendBroadcast(exitIntent);

				ChatDetailActivity.this.finish();
				break;
			}
		}

	};

	/**
	 * 列表项点击事件
	 */
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg2 < mList.size()) {
			if (mList.get(arg2).mType == 0) { // 如果列表项对应的数据类型为0，表示是点击的用户项
				if (mAdapter.getIsDelete()) { // 编辑模式

					// 如果该项用户非管理员,则踢出该用户
					if (!((TCUser) mGroup.getUserList().get(arg2)).getUserID()
							.equals(Common.getUid(mContext))) {
						Message message = new Message();
						message.obj = mContext.getString(R.string.send_loading);
						message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
						mBaseHandler.sendMessage(message);

						kickPerson(arg2);
					} else { // 如果是管理员，则给出提示
						showToast(mContext
								.getString(R.string.admin_can_not_remove));
					}

				} else { // 非编辑模式

					// 如果点击的是非自己，则进入用户详细查看用户资料

					if (mChatUser == null) {
						if (!((TCUser) mGroup.getUserList().get(arg2))
								.getUserID().equals(Common.getUid(mContext))) {
							Intent intent = new Intent(mContext,
									UserInfoActivity.class);
							intent.putExtra("fuid", ((TCUser) mGroup
									.getUserList().get(arg2)).getUserID());
							startActivity(intent);
						}
					} else {
						Intent intent = new Intent(mContext,
								UserInfoActivity.class);
						intent.putExtra("fuid", mChatUser.getUserID());
						startActivity(intent);
					}

				}

			} else if (mList.get(arg2).mType == 1) { // 点击添加按钮

				// 如果处于编辑模式，则设置成非编辑模式
				if (mAdapter.getIsDelete()) {
					mAdapter.setIsDelete(false);
					mAdapter.notifyDataSetChanged();
				} else {

					// 处于非编辑模式时，进入添加参与人界面，添加用户
					Intent intent = new Intent(mContext,
							NewTempChatActivity.class);
					if (!TextUtils.isEmpty(mGroupID)) {
						intent.putExtra("users",
								(Serializable) mGroup.getUserList());
					} else {
						intent.putExtra("user", mChatUser);
					}
					startActivityForResult(intent, ADD_REQUEST);
				}

			} else { // 点击踢人按钮

				// 如果当前已经处于编辑模式，则设置成非编辑模式
				if (mAdapter.getIsDelete()) {
					mAdapter.setIsDelete(false);
					mAdapter.notifyDataSetChanged();
				} else { // 如果处于非编辑模式，则设置成编辑模式
					mAdapter.setIsDelete(true);
					mAdapter.notifyDataSetChanged();
				}

			}
		} else {

			// 如果当前已经处于编辑模式，则设置成非编辑模式
			if (mAdapter.getIsDelete()) {
				mAdapter.setIsDelete(false);
				mAdapter.notifyDataSetChanged();
			}
		}

	}

	/**
	 * 设置消息接收类型
	 * 
	 * @param getmsg
	 *            0--不接收 1--接收
	 */
	private void setMsgType(final String getmsg) {

		TCChatManager.getInstance().setTempChatMsgType(mGroupID, getmsg,
				new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {

						// 设置成功后，更新本地数据表中保存的对应的消息接收类型
						mGroup.setGroupGetMsg(Integer.parseInt(getmsg));
						TCChatManager.getInstance()
								.updateTempChatGetMsg(mGroup);
						mHandler.sendEmptyMessage(SET_NOTIFY_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 被管理员提出或临时会话被删除显示的提示对话框
	 * 
	 * @param excuteType
	 *            0--临时会话被删除 1--被管理员踢出
	 */
	private void showDestoryDialog(int excuteType) {

		final Dialog dlg = new Dialog(mContext, R.style.Destory_Dialog_Style);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.destory_dialog, null);
		layout.setMinimumWidth(mWidth);

		final TextView promptView = (TextView) layout.findViewById(R.id.prompt);
		final Button okBtn = (Button) layout.findViewById(R.id.okbtn);

		if (excuteType == 0) {
			promptView.setText(mGroup.getGroupName()
					+ mContext.getString(R.string.group_has_been_delete));
		} else {
			promptView.setText(mContext
					.getString(R.string.you_have_been_kick_out_group)
					+ mGroup.getGroupName());
		}
		okBtn.setText(mContext.getString(R.string.ok));

		// 点击确定按钮，隐藏对话框
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				ChatDetailActivity.this.finish();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setCancelable(false);

		dlg.setContentView(layout);
		dlg.show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		if (arg2 < mList.size()) {
			if (mList.get(arg2).mType == 0) { // 长按设置成编辑模式
				if (!mAdapter.getIsDelete()) {
					mAdapter.setIsDelete(true);
					mAdapter.notifyDataSetChanged();
				}
				return true;
			}
		}

		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		// 选择用户成功后进入
		case ADD_REQUEST:
			if (resultCode == RESULT_OK) {
				List<TCUser> userList = (List<TCUser>) data
						.getSerializableExtra("userlist");
				// 提交添加用户操作
				if (userList != null && userList.size() != 0) {
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);

					inviteUser(userList);
				}

			}
			break;

		// 临时会话名称输入完成之后进入
		case INPUT_TEMP_CHAT_NAME_REQUEST:
			if (resultCode == RESULT_OK) {

				String name = data.getStringExtra("content");

				// 提交临时会话名称
				Message message = new Message();
				message.obj = mContext.getString(R.string.send_loading);
				message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
				mBaseHandler.sendMessage(message);
				editTempChatName(name);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 更新界面显示数据
	 */
	private void update() {
		if (mGroup != null) {

			mTempChatNameView.setText(mGroup.getGroupName());

			// 根据消息设置类型更新界面控件显示状态
			if (mGroup.getGroupGetMsg() == 0) {
				mNotifyIcon.setImageResource(R.drawable.butn_close);
			} else if (mGroup.getGroupGetMsg() == 1) {
				mNotifyIcon.setImageResource(R.drawable.butn_open);
			}

			// 将临时会话成员加入到界面显示对应列表中
			if (mGroup.getUserList() != null) {
				for (int i = 0; i < mGroup.getUserList().size(); i++) {
					mList.add(new ChatDetailEntity((TCUser) mGroup
							.getUserList().get(i), 0));
				}
			}

			if (mGroup.getCreatorID().equals(Common.getUid(mContext))) { // 管理员

				// 显示删除按钮
				mDeleteBtn
						.setText(mContext.getString(R.string.delete_and_exit));
				// 设置名称栏可用
				mNameLayout.setEnabled(true);
				// 显示名称右边箭头
				mNameArrowView.setVisibility(View.VISIBLE);
				// 界面数据列表中加入添加按钮
				mList.add(new ChatDetailEntity(null, 1));

				// 如果成员列表不为空，界面数据列表中加入删除按钮,点击触发编辑模式
				if (mGroup.getUserList() != null
						&& mGroup.getUserList().size() != 0) {
					mList.add(new ChatDetailEntity(null, 2));
				}
			} else {
				// 普通成员，显示退出按钮
				mDeleteBtn.setText(mContext.getString(R.string.exit));
				// 设置会话名称栏不可用
				mNameLayout.setEnabled(false);
				// 隐藏名称右边箭头
				mNameArrowView.setVisibility(View.GONE);
			}

			// 更新界面
			mAdapter = new ChatPersonAdapter(mContext, mList);
			mGridView.setAdapter(mAdapter);

			mScrollView.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 获取会话详细
	 */
	private void getGroupDetail() {
		TCChatManager.getInstance().getTemChatDetail(mGroupID,
				new TCGroupDetailListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {

					}

					@Override
					public void doComplete(TCGroup group) {
						mGroup = group;

						// 更新数据表中对应的临时会话的名称
						TCChatManager.getInstance().updateTempChatName(mGroup);

						// 发通知更新会话列表
						mContext.sendBroadcast(new Intent(
								ChatActivity.UPDATE_COUNT_ACTION));
						// 发通知更新会话总的未读数
						mContext.sendBroadcast(new Intent(
								HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));

						// 发通知更新聊天界面的该会话对象的名称
						Intent refreshIntent = new Intent(
								ChatMainActivity.ACTION_UPDATE_TEMP_CHAT_NAME);
						refreshIntent.putExtra("id", mGroupID);
						refreshIntent.putExtra("chatType", ChatType.TEMP_CHAT);
						refreshIntent.putExtra("name", mGroup.getGroupName());

						mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 踢出用户
	 * 
	 * @param pos
	 *            被踢用户所在列表中的位置
	 */
	private void kickPerson(final int pos) {
		TCChatManager.getInstance().kickTempChatMenber(mGroupID,
				((TCUser) mGroup.getUserList().get(pos)).getUserID(),
				new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {
						// 踢出成功
						Message message = new Message();
						message.what = KICK_SUCCESS;
						message.arg1 = pos;
						mHandler.sendMessage(message);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 向临时会话中添加用户
	 * 
	 * @param userList
	 *            待添加的用户列表
	 */
	private void inviteUser(final List<TCUser> userList) {

		// 拼接待添加的用户ID串,以逗号分隔
		StringBuffer uids = new StringBuffer();
		for (int i = 0; i < userList.size(); i++) {
			if (i < userList.size() - 1) {
				uids.append(userList.get(i).getUserID() + ",");
				continue;
			}

			uids.append(userList.get(i).getUserID());
		}

		TCChatManager.getInstance().addPersonToTempChat(mGroupID,
				uids.toString(), new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {

						// 添加用户成功
						Message message = new Message();
						message.what = ADD_SUCCESS;
						message.obj = userList;
						mHandler.sendMessage(message);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 点击退出、删除或清空聊天记录时显示的提示对话框
	 * 
	 * @param type
	 *            处理类型 0--退出 1--删除 2--清空聊天记录
	 */
	private void showDealDialog(final int type) {

		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button promptView = (Button) layout.findViewById(R.id.camera);
		final Button okBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);

		promptView.setEnabled(false);
		promptView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		promptView.setTextColor(mContext.getResources().getColor(
				R.color.content_gray_color));

		if (type == 0) {
			promptView.setText(mContext.getString(R.string.sure_to_exit_group)
					+ mGroup.getGroupName() + "?");
			okBtn.setText(mContext.getString(R.string.exit));
		} else if (type == 1) {
			promptView.setText(mContext
					.getString(R.string.sure_to_delete_group)
					+ mGroup.getGroupName() + "?");
			okBtn.setText(mContext.getString(R.string.delete));
		} else {
			promptView.setText(mContext.getString(R.string.ok)
					+ mContext.getString(R.string.clear_chat_record) + "?");
			okBtn.setText(mContext.getString(R.string.clear));
		}

		cancelBtn.setText(mContext.getString(R.string.cancel));

		// 点击同意按钮进行的操作
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();

				if (type == 2) {

					Intent clearChatIntent = new Intent(
							ChatMainActivity.ACTION_CLEAR_RECORD);

					if (mChatUser == null) {
						clearChatIntent.putExtra("id", mGroupID);
						clearChatIntent
								.putExtra("chatType", ChatType.TEMP_CHAT);

						// 清空临时会话聊天记录
						TCChatManager.getInstance().deleteSession(mGroupID,
								ChatType.TEMP_CHAT);
					} else {
						clearChatIntent.putExtra("id", mChatUser.getUserID());
						clearChatIntent.putExtra("chatType",
								ChatType.SINGLE_CHAT);

						// 清空单聊用户聊天记录
						TCChatManager.getInstance().deleteSession(
								mChatUser.getUserID(), ChatType.SINGLE_CHAT);
					}

					// 发通知更新会话的总未读数和会话列表数据
					mContext.sendBroadcast(new Intent(
							HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
					mContext.sendBroadcast(new Intent(
							ChatActivity.UPDATE_COUNT_ACTION));

					// 发通知到聊天界面更新数据
					mContext.sendBroadcast(clearChatIntent);

				} else {
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);

					if (type == 0) {
						exitTempChat();
					} else {
						deleteTempChat();
					}
				}

			}
		});

		// 点击取消按钮，隐藏对话框
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		// set a large value put it in bottom
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(true);

		dlg.setContentView(layout);
		dlg.show();
	}

	/**
	 * 编辑临时会话名称
	 * 
	 * @param name
	 *            临时会话名称
	 */
	private void editTempChatName(final String name) {
		TCChatManager.getInstance().editTempChat(mGroupID, name,
				new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {
						mGroup.setGroupName(name);
						// 编辑会话名称成功之后，更新会话数据表中存储的名称
						TCChatManager.getInstance().updateTempChatName(mGroup);

						// 发通知更新聊天页面会话的名称
						Intent intent = new Intent(
								ChatMainActivity.ACTION_UPDATE_TEMP_CHAT_NAME);
						intent.putExtra("id", mGroupID);
						intent.putExtra("chatType", ChatType.TEMP_CHAT);
						intent.putExtra("name", name);
						mContext.sendBroadcast(intent);
						mHandler.sendEmptyMessage(EDIT_TEMP_CHAT_NAME_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 删除临时会话
	 */
	private void deleteTempChat() {
		TCChatManager.getInstance().deleteTempChat(mGroupID,
				new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {
						// 删除成功
						mHandler.sendEmptyMessage(DELETE_TEMP_CHAT_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 退出临时会话
	 */
	private void exitTempChat() {
		TCChatManager.getInstance().exitTempChat(mGroupID,
				new TCBaseListener() {

					@Override
					public void onError(TCError error) {
						Message message = new Message();
						message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						message.obj = error.errorMessage;
						mBaseHandler.sendMessage(message);
					}

					@Override
					public void doComplete() {
						// 退出成功
						mHandler.sendEmptyMessage(EXIT_TEMP_CHAT_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}
}
