package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.haoqee.chat.Fragment.FriendsFragment;
import com.haoqee.chat.adapter.NotifyAdapter;
import com.haoqee.chat.entity.AppState;
import com.haoqee.chat.entity.NotifyType;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.xguanjia.hx.haoxinchat.GroupChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCNotifyType;
import com.haoqee.chatsdk.entity.TCNotifyVo;

/**
 * 通知列表页面
 *
 */
public class NotifyActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {

	private ListView mListView;
	private List<TCNotifyVo> mNotifyList; // 通知类表数据
	private boolean mIsRegisterReceiver = false;
	private NotifyAdapter mAdapter; // 通知适配器

	/** 销毁页面通知 */
	public static final String NOTIFY_DESTORY_ACTION = "com.xizue.thinkchat.intent.action.NOTIFY_DESTORY_ACTION";

	/** 是否处于编辑模式 */
	private boolean mIsEdit = false;

	/** 刷新通知类表通知 */
	public final static String ACTION_NOTIFY_SYSTEM_MESSAGE = "com.xizue.thinkchat.intent.action.ACTION_NOTIFY_SYSTEM_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify_page);
		mContext = this;
		registerFinishReceiver();

		// 注册Receiver
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_NOTIFY_SYSTEM_MESSAGE);
		filter.addAction(NOTIFY_DESTORY_ACTION);
		registerReceiver(mReceiver, filter);
		mIsRegisterReceiver = true;

		initComponent();
	}

	@Override
	protected void onResume() {
		super.onResume();
		clearNotification();
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null) {
				String action = intent.getAction();
				if (action.equals(ACTION_NOTIFY_SYSTEM_MESSAGE)) {
					mNotifyList = TCChatManager.getInstance().queryNotifyList();

					if (mNotifyList == null) {
						mNotifyList = new ArrayList<TCNotifyVo>();
					}

					mAdapter.setData(mNotifyList);
					mAdapter.notifyDataSetChanged();
				} else if (action.equals(NOTIFY_DESTORY_ACTION)) { // 应用处于后台时并停留在该页面，点击通知栏进入该页面先销毁之前的页面
					NotifyActivity.this.finish();
				}
			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		setTitleContent(R.drawable.back_btn, R.drawable.edit_btn,
				mContext.getString(R.string.notification));
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mListView = (ListView) findViewById(R.id.notify_list);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);

		clearNotification();

		mNotifyList = TCChatManager.getInstance().queryNotifyList();

		if (mNotifyList == null) {
			mNotifyList = new ArrayList<TCNotifyVo>();
		}

		mAdapter = new NotifyAdapter(mContext, mNotifyList);
		mListView.setAdapter(mAdapter);
	}

	/**
	 * 如果通知栏存在该通知，则清空
	 */
	private void clearNotification() {
		NotificationManager notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (notificationManager != null) {
			notificationManager.cancel(HaoXinProjectActivity.NOTION_ID);
		}
	}

	public void showCancelBtn(boolean isShow) {
		mAdapter.mIsCancel = isShow;
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy() {
		if (mIsRegisterReceiver) {
			unregisterReceiver(mReceiver);
		}
		super.onDestroy();
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
				showToast(mContext.getString(R.string.operate_success));
				hideProgressDialog();
				mAdapter.notifyDataSetChanged();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				hideProgressDialog();
				String prompt = (String) msg.obj;
				if (prompt != null && !prompt.equals("")) {
					Toast.makeText(mContext, prompt, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext,
							mContext.getString(R.string.operate_failed),
							Toast.LENGTH_LONG).show();
				}

				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				hideProgressDialog();
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				hideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 点击返回按钮
		case R.id.left_btn:
			this.finish();
			break;

		case R.id.right_btn:
			if (mIsEdit) { // 如果是编辑状态，点击则显示编辑按钮，并隐藏每项删除按钮
				mIsEdit = false;
				mRightBtn.setImageResource(R.drawable.edit_btn);
				showCancelBtn(false);
			} else { // 如果不是编辑状态，则点击显示完成按钮，并显示每项删除按钮，
				mIsEdit = true;
				mRightBtn.setImageResource(R.drawable.title_complete_btn);
				showCancelBtn(true);
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (mNotifyList.get(arg2).getNotifyReadState() == 0) {
			mNotifyList.get(arg2).setNotifyReadState(1);
			TCChatManager.getInstance().updateNotify(mNotifyList.get(arg2));

			mAdapter.notifyDataSetChanged();

			mContext.sendBroadcast(new Intent(ChatActivity.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
		}

		switch (mNotifyList.get(arg2).getType()) {

		case NotifyType.APPLY_FRIEND: // 申请加好友
		case TCNotifyType.APPLY_ADD_GROUP: // 申请加入群
		case TCNotifyType.INVITE_ADD_GROUP: // 邀请加入群
			if (mNotifyList.get(arg2).getProcessed() == 0) {
				showPromptDialog(arg2, mNotifyList.get(arg2).getType());
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 通知处理方式选择对话框
	 * 
	 * @param pos
	 *            通知具体所在列表位置
	 * @param type
	 *            通知类型
	 */
	private void showPromptDialog(final int pos, final int type) {

		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.chat_add_menu_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button agreeBtn = (Button) layout.findViewById(R.id.sendType);
		final Button refuseBtn = (Button) layout.findViewById(R.id.camera);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);
		final Button detailBtn = (Button) layout.findViewById(R.id.gallery);
		agreeBtn.setText(mContext.getString(R.string.agree));
		refuseBtn.setText(mContext.getString(R.string.refuse));
		cancelBtn.setText(mContext.getString(R.string.cancel));

		if (type == TCNotifyType.INVITE_ADD_GROUP) { // 如果是邀请加入群组，则显示查看用户详细按钮
			detailBtn.setText(mContext.getString(R.string.view_group_info));
		} else { // 如果是申请加好友和申请加入群，则显示查看群详细按钮
			detailBtn.setText(mContext.getString(R.string.view_user_info));
		}

		// 点击同意按钮进行的操作
		agreeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				Message message = new Message();
				message.obj = mContext.getString(R.string.send_loading);
				message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
				mHandler.sendMessage(message);

				agree(pos, type);
			}
		});

		// 点击拒绝按钮进行擦做
		refuseBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				Message message = new Message();
				message.obj = mContext.getString(R.string.add_more_loading);
				message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
				mHandler.sendMessage(message);

				refuse(pos, type);
			}
		});

		// 点击查看详细资料按钮
		detailBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				if (type == TCNotifyType.INVITE_ADD_GROUP) { // 如果是邀请加入群，则点击群详细进入群详细页面查看
					Intent intent = new Intent(mContext,
							GroupDetailActivity.class);
					intent.putExtra("groupid", mNotifyList.get(pos).getRoomID());
					startActivity(intent);
				} else { // 如果是申请加入群和申请加好友，则进入用户详细页面查看
					Intent intent = new Intent(mContext, UserInfoActivity.class);
					intent.putExtra("fuid", mNotifyList.get(pos).getUserId());
					startActivity(intent);
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
	 * 同意操作入口
	 * 
	 * @param pos
	 *            通知所在位置
	 * @param type
	 *            通知的类型
	 */
	private void agree(final int pos, final int type) {

		if (type == TCNotifyType.APPLY_ADD_GROUP) {
			TCChatManager.getInstance().agreeAddGroup(
					mNotifyList.get(pos).getRoomID(),
					mNotifyList.get(pos).getUserId(), new TCBaseListener() {

						@Override
						public void onError(TCError error) {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							message.obj = error.errorMessage;
							mBaseHandler.sendMessage(message);
						}

						@Override
						public void doComplete() {

							mNotifyList.get(pos).setProcessed(1);
							TCChatManager.getInstance().updateNotify(
									mNotifyList.get(pos));

							Intent detailIntent = new Intent(
									GroupDetailActivity.ACTION_REFRESH_GROUP_DETAIL);
							detailIntent.putExtra("groupid",
									mNotifyList.get(pos).getRoomID());
							mContext.sendBroadcast(detailIntent);

							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}

						@Override
						public void onProgress(int progress) {

						}
					});
		} else if (type == TCNotifyType.INVITE_ADD_GROUP) {
			TCChatManager.getInstance().agreeGroupInvite(
					mNotifyList.get(pos).getRoomID(), new TCBaseListener() {

						@Override
						public void onError(TCError error) {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							message.obj = error.errorMessage;
							mBaseHandler.sendMessage(message);
						}

						@Override
						public void doComplete() {
							mNotifyList.get(pos).setProcessed(1);
							TCChatManager.getInstance().updateNotify(
									mNotifyList.get(pos));
							mContext.sendBroadcast(new Intent(
									GroupChatActivity.REFRESH_CONTACT_GROUP_ACTION));

							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}

						@Override
						public void onProgress(int progress) {

						}
					});
		} else {
			new Thread() {
				@Override
				public void run() {
					if (Common.verifyNetwork(mContext)) {
						try {
							AppState state = Common.getThinkchatInfo()
									.agreeFriend(
											mNotifyList.get(pos).getUserId());
							;

							if (state != null && state.code == 0) {

								// 调用接口成功后，更新数据表该条通知的处理状态为已处理
								mNotifyList.get(pos).setProcessed(1);
								TCChatManager.getInstance().updateNotify(
										mNotifyList.get(pos));

								mContext.sendBroadcast(new Intent(
										FriendsFragment.REFRESH_FRIEND_ACTION));
								mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
							} else {
								Message message = new Message();
								message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
								if (state != null && state.errorMsg != null
										&& !state.errorMsg.equals("")) {
									message.obj = state.errorMsg;
								} else {
									message.obj = mContext
											.getString(R.string.operate_failed);
								}
								mHandler.sendMessage(message);
							}
						} catch (ThinkchatException e) {
							e.printStackTrace();
							mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION);
						}
					} else {
						mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
					}
				}
			}.start();
		}
	}

	/**
	 * 拒绝操作入口
	 * 
	 * @param pos
	 *            通知所在位置
	 * @param type
	 *            通知类型
	 */
	private void refuse(final int pos, final int type) {

		if (type == TCNotifyType.INVITE_ADD_GROUP) { // 邀请加入群类型，则调用拒绝加入群接口
			TCChatManager.getInstance().refuseGroupInvite(
					mNotifyList.get(pos).getRoomID(), new TCBaseListener() {

						@Override
						public void onError(TCError error) {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							message.obj = error.errorMessage;
							mBaseHandler.sendMessage(message);
						}

						@Override
						public void doComplete() {
							mNotifyList.get(pos).setProcessed(1);
							TCChatManager.getInstance().updateNotify(
									mNotifyList.get(pos));
							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}

						@Override
						public void onProgress(int progress) {

						}
					});
		} else if (type == TCNotifyType.APPLY_ADD_GROUP) { // 申请加入群类型，则调用拒绝加入群接口
			TCChatManager.getInstance().refuseAddGroup(
					mNotifyList.get(pos).getRoomID(),
					mNotifyList.get(pos).getUserId(), new TCBaseListener() {

						@Override
						public void onError(TCError error) {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							message.obj = error.errorMessage;
							mBaseHandler.sendMessage(message);
						}

						@Override
						public void doComplete() {
							mNotifyList.get(pos).setProcessed(1);
							TCChatManager.getInstance().updateNotify(
									mNotifyList.get(pos));
							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}

						@Override
						public void onProgress(int progress) {

						}
					});
		} else if (type == NotifyType.APPLY_FRIEND) { // 申请加好友类型，则调用拒绝加好友接口
			new Thread() {
				@Override
				public void run() {
					if (Common.verifyNetwork(mContext)) {
						try {
							AppState state = Common.getThinkchatInfo()
									.refuseFriend(
											mNotifyList.get(pos).getUserId());
							if (state != null && state.code == 0) {

								// 调用接口成功后，更新数据表该条通知的处理状态为已处理
								mNotifyList.get(pos).setProcessed(1);
								TCChatManager.getInstance().updateNotify(
										mNotifyList.get(pos));

								Message message = new Message();
								message.what = HaoXinProjectActivity.HIDE_PROGRESS_DIALOG;
								message.arg1 = 1;
								mHandler.sendMessage(message);
							} else {
								Message message = new Message();
								message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
								if (state != null && state.errorMsg != null
										&& !state.errorMsg.equals("")) {
									message.obj = state.errorMsg;
								} else {
									message.obj = mContext
											.getString(R.string.operate_failed);
								}
								mHandler.sendMessage(message);
							}
						} catch (ThinkchatException e) {
							e.printStackTrace();
							mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION);
						}
					} else {
						mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
					}
				}
			}.start();
		}
	}
}
