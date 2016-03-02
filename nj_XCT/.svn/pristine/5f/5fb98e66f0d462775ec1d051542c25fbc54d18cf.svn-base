package com.haoqee.chat;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.global.ImageLoader;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.xguanjia.hx.haoxinchat.GroupChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.Interface.TCGroupDetailListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCSession;

/**
 * 群详细页面
 *
 */
public class GroupDetailActivity extends BaseActivity implements
		OnClickListener {

	private ScrollView mScrollView; // 纵向滑动控件
	private ImageView mGroupHeaderView; // 群头像显示控件
	private TextView mGroupNameTextView; // 群名称显示控件
	private TextView mGroupIDTextView; // 群ID显示控件
	private TextView mGroupMenberTextView; // 群成员数显示控件
	private TextView mGroupDescTextView; // 群简介显示控件
	private RelativeLayout mMenberLayout; // 群成员数栏点击控件
	private RelativeLayout mInviteLayout; // 邀请新成员栏点击控件
	private RelativeLayout mNotifyLayout; // 新消息提醒栏点击控件
	private RelativeLayout mClearLayout; // 清空聊天记录栏点击控件
	private ImageView mNotifyIcon; // 接收消息开关状态控件
	private Button mSendBtn; // 进入聊天室按钮
	private Button mApplyBtn; // 未加入群是为申请加入按钮，加入之后为退出群按钮
	private String mGroupID = ""; // 待获取群详细的群ID
	private TCGroup mGroup; // 群对象
	private ImageLoader mImageLoader = new ImageLoader(); // 图片下载类
	private LinearLayout mSettingLayout; // 下面设置栏（包括新消息设置和清空聊天记录）

	/** 刷新群详细通知 */
	public final static String ACTION_REFRESH_GROUP_DETAIL = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_GROUP_DETAIL";
	/** 同意加入群时收到该通知，更新界面 */
	public static final String ACTION_AGREE_ADD_GROUP = "com.gaopai.guiren.ACTION_AGREE_ADD_GROUP";
	/** 更新成员数通知 */
	public static final String ACTION_UPDATE_MENBER_COUNT = "com.gaopai.guiren.ACTION_UPDATE_MENBER_COUNT";

	private final static int APPLY_ADD_GROUP_SUCCESS = 0x00320;
	private final static int EXIT_GROUP_SUCCESS = 0x00321;
	private final static int DELETE_GROUP_SUCCESS = 0x00322;
	private final static int SET_NOTIFY_SUCCESS = 0x00323;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.group_detail_layout);
		registerFinishReceiver();

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_REFRESH_GROUP_DETAIL);
		filter.addAction(GroupChatActivity.ACTION_KICK_GROUP);
		filter.addAction(GroupChatActivity.ACTION_DELETE_GROUP);
		filter.addAction(ACTION_AGREE_ADD_GROUP);
		filter.addAction(ACTION_UPDATE_MENBER_COUNT);
		registerReceiver(mReceiver, filter);
		initComponent();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_REFRESH_GROUP_DETAIL)) {
					String groupid = intent.getStringExtra("groupid");
					if (!TextUtils.isEmpty(groupid)) {
						if (groupid.equals(mGroupID)) {
							getGroupDetail();
						}
					}
				} else if (action.equals(GroupChatActivity.ACTION_KICK_GROUP)) {
					String groupid = intent.getStringExtra("groupid");
					if (!TextUtils.isEmpty(groupid)) {
						if (groupid.equals(mGroupID)) {
							mGroup.setGroupIsJoin(0);
							mSendBtn.setVisibility(View.GONE);
							mApplyBtn.setText(mContext
									.getString(R.string.apply_add));
							mApplyBtn
									.setBackgroundResource(R.drawable.group_apply_btn);
							mSettingLayout.setVisibility(View.GONE);

							if (mGroup.getGroupGetMsg() == 0) {
								mGroup.setGroupGetMsg(1);
								mNotifyIcon
										.setImageResource(R.drawable.butn_open);
							}

							getGroupDetail();
						}
					}
				} else if (action.equals(GroupChatActivity.ACTION_DELETE_GROUP)) {
					String groupid = intent.getStringExtra("groupid");
					if (!TextUtils.isEmpty(groupid)) {
						if (groupid.equals(mGroupID)) {
							showDestoryDialog();
						}
					}
				} else if (action.equals(ACTION_AGREE_ADD_GROUP)) {
					String id = intent.getStringExtra("id");
					if (!TextUtils.isEmpty(id) && id.equals(mGroupID)) {
						mGroup.setGroupIsJoin(1);
						mApplyBtn.setText(mContext.getString(R.string.exit));
						mApplyBtn.setBackgroundResource(R.drawable.delete_btn);
						mSettingLayout.setVisibility(View.VISIBLE);
						getGroupDetail();
					}
				} else if (action.equals(ACTION_UPDATE_MENBER_COUNT)) {
					mGroup.setGroupMenberCount(mGroup.getGroupMenberCount() - 1);
					mGroupMenberTextView.setText(mGroup.getGroupMenberCount()
							+ mContext.getString(R.string.person));
					getGroupDetail();
				}
			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		// 初始化标题栏
		setTitleContent(R.drawable.back_btn, R.drawable.edit_btn, "");
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		mRightBtn.setVisibility(View.GONE);

		mGroupID = getIntent().getStringExtra("groupid");
		mScrollView = (ScrollView) findViewById(R.id.scrollview);
		mGroupHeaderView = (ImageView) findViewById(R.id.group_header);
		mGroupNameTextView = (TextView) findViewById(R.id.group_name);
		mGroupIDTextView = (TextView) findViewById(R.id.group_id);
		mGroupMenberTextView = (TextView) findViewById(R.id.menber_count);
		mGroupDescTextView = (TextView) findViewById(R.id.group_description);

		mMenberLayout = (RelativeLayout) findViewById(R.id.menberlayout);
		mMenberLayout.setOnClickListener(this);

		mInviteLayout = (RelativeLayout) findViewById(R.id.invitelayout);
		mInviteLayout.setOnClickListener(this);

		mNotifyLayout = (RelativeLayout) findViewById(R.id.notifylayout);
		mNotifyLayout.setOnClickListener(this);

		mClearLayout = (RelativeLayout) findViewById(R.id.clearlayout);
		mClearLayout.setOnClickListener(this);

		mNotifyIcon = (ImageView) findViewById(R.id.notify_btn);

		mSendBtn = (Button) findViewById(R.id.sendbtn);
		mSendBtn.setOnClickListener(this);

		mApplyBtn = (Button) findViewById(R.id.applybtn);
		mApplyBtn.setOnClickListener(this);

		mSettingLayout = (LinearLayout) findViewById(R.id.settinglayout);

		Message message = new Message();
		message.obj = mContext.getString(R.string.add_more_loading);
		message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);

		getGroupDetail();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 点击返回按钮
		case R.id.left_btn:
			this.finish();
			break;

		case R.id.right_btn:
			Intent editIntent = new Intent(mContext, CreateGroupActivity.class);
			editIntent.putExtra("group", mGroup);
			startActivity(editIntent);
			break;

		// 点击成员数栏
		case R.id.menberlayout:
			Intent menberIntent = new Intent(mContext, MenberListActivity.class);
			menberIntent.putExtra("groupid", mGroupID);
			menberIntent.putExtra("grouprole", mGroup.getGroupRole());
			startActivity(menberIntent);
			break;

		// 点击邀请新成员
		case R.id.invitelayout:
			Intent inviteIntent = new Intent(mContext,
					InviteUserListActivity.class);
			inviteIntent.putExtra("groupid", mGroupID);
			inviteIntent.putExtra("uids", mGroup.getJoinUids());
			startActivity(inviteIntent);
			break;

		// 点击新消息通知栏
		case R.id.notifylayout:
			Message notifyMessage = new Message();
			notifyMessage.obj = mContext.getString(R.string.send_loading);
			notifyMessage.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
			mBaseHandler.sendMessage(notifyMessage);

			if (mGroup.getGroupGetMsg() == 0) {
				setMsgType("1");
			} else if (mGroup.getGroupGetMsg() == 1) {
				setMsgType("0");
			}
			break;

		// 点击清空聊天记录
		case R.id.clearlayout:
			showDealDialog(2);
			break;

		// 点击进入按钮
		case R.id.sendbtn:
			Intent chatIntent = new Intent(mContext, ChatMainActivity.class);
			TCSession session = TCChatManager.getInstance().getSessionByID(
					mGroupID, ChatType.GROUP_CHAT);

			if (session == null) {
				session = new TCSession();
				// 设置会话类型
				session.setChatType(ChatType.GROUP_CHAT);
				// 设置会话对象ID
				session.setFromId(mGroupID);
				// 设置会话名称
				session.setSessionName(mGroup.getGroupName());
				// 设置会话头像
				session.setSessionHead(mGroup.getGroupLogoSmall());
				// 设置会话对象扩展属性
				// HashMap<String, String> extendMap = new HashMap<String,
				// String>();
				// 设置会话对象简介
				// extendMap.put("content", mGroup.getGroupDescription());
				// session.setExtendMap(extendMap);
			}

			chatIntent.putExtra("session", session);
			startActivity(chatIntent);
			break;

		// 点击申请加入或者是退出按钮
		case R.id.applybtn:

			if (mGroup.getGroupRole() == 1) {
				showDealDialog(1);
			} else {
				if (mGroup.getGroupIsJoin() == 0) {
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);
					applyAddGroup();
				} else {
					showDealDialog(0);
				}
			}

			break;

		default:
			break;
		}
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
					TCChatManager.getInstance().deleteSession(mGroupID,
							ChatType.GROUP_CHAT);
					mContext.sendBroadcast(new Intent(
							HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
					mContext.sendBroadcast(new Intent(
							ChatActivity.UPDATE_COUNT_ACTION));

					Intent clearChatIntent = new Intent(
							ChatMainActivity.ACTION_CLEAR_RECORD);
					clearChatIntent.putExtra("id", mGroupID);
					clearChatIntent.putExtra("chatType", ChatType.GROUP_CHAT);
					mContext.sendBroadcast(clearChatIntent);
				} else {
					Message message = new Message();
					message.obj = mContext.getString(R.string.send_loading);
					message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
					mBaseHandler.sendMessage(message);

					if (type == 0) {
						exitGroup();
					} else {
						deleteGroup();
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

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				update();
				break;

			case APPLY_ADD_GROUP_SUCCESS:
				baseHideProgressDialog();
				showToast(mContext.getString(R.string.apply_group_success));
				break;

			case EXIT_GROUP_SUCCESS:
				baseHideProgressDialog();
				mGroup.setGroupIsJoin(0);

				TCChatManager.getInstance().deleteGroupFromTable(mGroupID,
						ChatType.GROUP_CHAT);
				TCChatManager.getInstance().deleteSession(mGroupID,
						ChatType.GROUP_CHAT);

				Intent exitIntent = new Intent(
						GroupChatActivity.ACTION_DELETE_MY_GROUP);
				exitIntent.putExtra("groupid", mGroupID);
				exitIntent.putExtra("excuteType", 1);
				mContext.sendBroadcast(exitIntent);

				showToast(mContext.getString(R.string.exit_group_success));
				mSendBtn.setVisibility(View.GONE);
				mApplyBtn.setText(mContext.getString(R.string.apply_add));
				mApplyBtn.setBackgroundResource(R.drawable.group_apply_btn);
				mSettingLayout.setVisibility(View.GONE);
				break;

			case DELETE_GROUP_SUCCESS:
				baseHideProgressDialog();

				TCChatManager.getInstance().deleteGroupFromTable(mGroupID,
						ChatType.GROUP_CHAT);

				TCChatManager.getInstance().deleteSession(mGroupID,
						ChatType.GROUP_CHAT);

				// 发通知到群列表界面移除已经退出的群
				Intent deleteIntent = new Intent(
						GroupChatActivity.ACTION_DELETE_MY_GROUP);
				deleteIntent.putExtra("groupid", mGroupID);
				// 传入执行类型 1--退出 0--删除
				deleteIntent.putExtra("excuteType", 0);
				mContext.sendBroadcast(deleteIntent);
				showToast(mContext.getString(R.string.delete_group_success));
				GroupDetailActivity.this.finish();
				break;

			case SET_NOTIFY_SUCCESS:
				hideProgressDialog();
				Toast.makeText(mContext, R.string.operate_success,
						Toast.LENGTH_LONG).show();
				if (mGroup.getGroupGetMsg() == 1) {
					mNotifyIcon.setImageResource(R.drawable.butn_open);
				} else if (mGroup.getGroupGetMsg() == 0) {
					mNotifyIcon.setImageResource(R.drawable.butn_close);
				}
				break;
			}
		}

	};

	private void update() {
		if (mGroup != null) {
			// 设置页面标题
			titileTextView.setText(mGroup.getGroupName());

			// 如果大头像URL不为空，则下载头像
			if (!TextUtils.isEmpty(mGroup.getGroupLogoLarge())) {
				mImageLoader.getBitmap(mContext, mGroupHeaderView, null,
						mGroup.getGroupLogoLarge(), 0, false, false);
			} else {
				mGroupHeaderView
						.setImageResource(R.drawable.group_default_icon);
			}

			mGroupNameTextView.setText(mGroup.getGroupName());
			mGroupIDTextView.setText(mGroupID);
			mGroupMenberTextView.setText(mGroup.getGroupMenberCount()
					+ mContext.getString(R.string.person));
			mGroupDescTextView.setText(mGroup.getGroupDescription());

			// 如果设置消息接收状态为不接收，则将开关按钮的状态置为关闭状态，反之，则置为显示状态
			if (mGroup.getGroupGetMsg() == 0) {
				mNotifyIcon.setImageResource(R.drawable.butn_close);
			} else if (mGroup.getGroupGetMsg() == 1) {
				mNotifyIcon.setImageResource(R.drawable.butn_open);
			}

			// 如果是管理员，则显示邀请新成员按钮，并设置退出按钮的文本为删除并退出
			if (mGroup.getGroupRole() == 1) {
				mSendBtn.setVisibility(View.VISIBLE);
				mApplyBtn.setText(mContext.getString(R.string.delete_and_exit));
				mApplyBtn.setBackgroundResource(R.drawable.delete_btn);
				mInviteLayout.setVisibility(View.VISIBLE);
				mSettingLayout.setVisibility(View.VISIBLE);
				mRightBtn.setVisibility(View.VISIBLE);
			} else if (mGroup.getGroupRole() == 0) {
				// 如果是普通成员，则隐藏邀请新成员按钮
				mInviteLayout.setVisibility(View.GONE);
				mRightBtn.setVisibility(View.GONE);

				// 如果未加入该群，则显示申请按钮，并隐藏进入聊天室按钮
				if (mGroup.getGroupIsJoin() == 0) {
					mSendBtn.setVisibility(View.GONE);
					mApplyBtn.setText(mContext.getString(R.string.apply_add));
					mApplyBtn.setBackgroundResource(R.drawable.group_apply_btn);
					mSettingLayout.setVisibility(View.GONE);
					mInviteLayout.setVisibility(View.GONE);
				} else if (mGroup.getGroupIsJoin() == 1) { // 如果已经加入，则将申请按钮文本替换为退出，并显示进入聊天室按钮
					mSendBtn.setVisibility(View.VISIBLE);
					mApplyBtn.setText(mContext.getString(R.string.exit));
					mApplyBtn.setBackgroundResource(R.drawable.delete_btn);
					mSettingLayout.setVisibility(View.VISIBLE);
				}
			}
			mScrollView.setVisibility(View.VISIBLE);
		}
	}

	private void getGroupDetail() {
		TCChatManager.getInstance().getTCGroupDetail(mGroupID, mListener);
	}

	private TCGroupDetailListener mListener = new TCGroupDetailListener() {

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

			// 更新数据表中会话对象的名称和头像
			TCChatManager.getInstance().updateSessionByID(mGroupID,
					ChatType.GROUP_CHAT, mGroup.getGroupName(),
					mGroup.getGroupLogoSmall());

			// 发通知更新会话列表
			mContext.sendBroadcast(new Intent(ChatActivity.UPDATE_COUNT_ACTION));
			// 发通知更新会话总的未读数
			mContext.sendBroadcast(new Intent(
					HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));

			// 发通知更新聊天界面的该会话对象的名称
			Intent refreshIntent = new Intent(
					ChatMainActivity.ACTION_UPDATE_TEMP_CHAT_NAME);
			refreshIntent.putExtra("id", mGroup.getGroupID());
			refreshIntent.putExtra("chatType", ChatType.GROUP_CHAT);
			refreshIntent.putExtra("name", mGroup.getGroupName());
			refreshIntent.putExtra("head", mGroup.getGroupLogoSmall());
			mContext.sendBroadcast(refreshIntent);

			// 获取到最新数据后发通知到邀请用户页面，更新最新的成员ID串
			Intent intent = new Intent(
					InviteUserListActivity.ACTION_REFRESH_JOIN_UIDS);
			intent.putExtra("uids", group.getJoinUids());
			intent.putExtra("groupid", mGroupID);
			mContext.sendBroadcast(intent);

			mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
		}

		@Override
		public void onProgress(int progress) {

		}
	};

	/**
	 * 申请加入群
	 */
	private void applyAddGroup() {
		TCChatManager.getInstance().applyAddGroup(mGroupID,
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
						mHandler.sendEmptyMessage(APPLY_ADD_GROUP_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 退出群
	 */
	private void exitGroup() {

		TCChatManager.getInstance().exitGroup(mGroupID, new TCBaseListener() {

			@Override
			public void onError(TCError error) {
				Message message = new Message();
				message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
				message.obj = error.errorMessage;
				mBaseHandler.sendMessage(message);
			}

			@Override
			public void doComplete() {
				mHandler.sendEmptyMessage(EXIT_GROUP_SUCCESS);
			}

			@Override
			public void onProgress(int progress) {

			}
		});

	}

	/**
	 * 管理员删除群
	 */
	private void deleteGroup() {

		TCChatManager.getInstance().deleteGroup(mGroupID, new TCBaseListener() {

			@Override
			public void onError(TCError error) {
				Message message = new Message();
				message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
				message.obj = error.errorMessage;
				mBaseHandler.sendMessage(message);
			}

			@Override
			public void doComplete() {
				mHandler.sendEmptyMessage(DELETE_GROUP_SUCCESS);
			}

			@Override
			public void onProgress(int progress) {

			}
		});

	}

	/**
	 * 设置消息接收类型
	 * 
	 * @param getmsg
	 *            0--不接收 1--接收
	 */
	private void setMsgType(final String getmsg) {

		TCChatManager.getInstance().setGroupMsgType(mGroupID, getmsg,
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
						mGroup.setGroupGetMsg(Integer.parseInt(getmsg));
						TCChatManager.getInstance().updateGroupGetMsg(mGroup);
						mHandler.sendEmptyMessage(SET_NOTIFY_SUCCESS);
					}

					@Override
					public void onProgress(int progress) {

					}
				});
	}

	/**
	 * 群被删除显示的提示对话框
	 */
	private void showDestoryDialog() {

		final Dialog dlg = new Dialog(mContext, R.style.Destory_Dialog_Style);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.destory_dialog, null);
		layout.setMinimumWidth(mWidth);

		final TextView promptView = (TextView) layout.findViewById(R.id.prompt);
		final Button okBtn = (Button) layout.findViewById(R.id.okbtn);

		promptView.setText(mGroup.getGroupName()
				+ mContext.getString(R.string.group_has_been_delete));
		okBtn.setText(mContext.getString(R.string.ok));

		// 点击确定按钮，隐藏对话框
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				GroupDetailActivity.this.finish();
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
}
