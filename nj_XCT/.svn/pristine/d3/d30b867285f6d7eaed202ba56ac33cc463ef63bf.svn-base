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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.Fragment.ProfileFragment;
import com.haoqee.chat.entity.AppState;
import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCSession;

/**
 * 用户资料页面
 *
 */
public class UserInfoActivity extends BaseActivity implements OnClickListener {

	private ImageView mHeaderImageView; // 头像显示控件
	private TextView mNickNameTextView; // 昵称显示控件
	private TextView mSignTextView; // 个性签名显示控件
	private TextView mSexTextView; // 性别显示控件

	private Button mSendBtn; // 发送消息按钮
	private Button mApplyBtn; // 申请加好友按钮

	private String mFuid = ""; // 待获取用户资料的ID，如果不传入的话则为空
	private User mUser; // 用户详细资料料对象

	private ImageLoader mImageLoader = new ImageLoader();
	private final static int APPLY_FRIEND_SUCCESS = 0x00220;
	private final static int DELETE_FRIEND_SUCCESS = 0x00221;

	/** 同意添加好友通知 */
	public final static String ACTION_AGREE_FRIEND = "com.xizue.thinkchat.intent.action.ACTION_AGREE_FRIEND";

	/** 刷新个人资料 */
	public final static String ACTION_REFRESH_USER_INFO = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_USER_INFO";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		registerFinishReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_AGREE_FRIEND);
		filter.addAction(ACTION_REFRESH_USER_INFO);
		registerReceiver(mReceiver, filter);
		initComponent();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_AGREE_FRIEND)) {
					String uid = intent.getStringExtra("uid");
					if (!TextUtils.isEmpty(uid) && !TextUtils.isEmpty(mFuid)
							&& uid.equals(mFuid)) {
						mUser.mIsFriend = 1;
						mApplyBtn.setVisibility(View.GONE);
					}
				} else if (action.equals(ACTION_REFRESH_USER_INFO)) {
					if (TextUtils.isEmpty(mFuid)) {
						getUserDetail();
					}
				}
			}
		}
	};

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		mFuid = getIntent().getStringExtra("fuid");
		setTitleContent(R.drawable.back_btn, R.drawable.title_more_btn, "");
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		mRightBtn.setVisibility(View.GONE);

		mHeaderImageView = (ImageView) findViewById(R.id.header);
		mNickNameTextView = (TextView) findViewById(R.id.nickname);
		mSignTextView = (TextView) findViewById(R.id.sign);
		mSexTextView = (TextView) findViewById(R.id.sex);

		mSendBtn = (Button) findViewById(R.id.sendbtn);
		mSendBtn.setOnClickListener(this);

		mApplyBtn = (Button) findViewById(R.id.applybtn);
		mApplyBtn.setOnClickListener(this);

		// 判断当前传入的用户ID是否为空，如果不为空标题栏就显示更多操作按钮
		// 如果为空则表示是当前登录用户的资料，标题栏上显示的是编辑按钮
		if (!TextUtils.isEmpty(mFuid)) {
			mRightBtn.setImageResource(R.drawable.title_more_btn);
		} else {
			mRightBtn.setImageResource(R.drawable.edit_btn);
		}

		Message message = new Message();
		message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
		message.obj = mContext.getString(R.string.add_more_loading);
		mHandler.sendMessage(message);

		getUserDetail();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mHeaderImageView.setImageBitmap(null);
		FeatureFunction.freeBitmap(mImageLoader.getImageBuffer());
		unregisterReceiver(mReceiver);

	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				showProgressDialog(dialogMsg);
				break;

			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				hideProgressDialog();
				update();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
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
				baseHideProgressDialog();
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			case APPLY_FRIEND_SUCCESS:
				baseHideProgressDialog();
				showToast(mContext.getString(R.string.apply_friend_success));
				break;

			case DELETE_FRIEND_SUCCESS:
				baseHideProgressDialog();
				mApplyBtn.setVisibility(View.VISIBLE);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 显示用户信息
	 */
	private void update() {
		if (mUser != null) {

			titileTextView.setText(mUser.nickName);

			if (!TextUtils.isEmpty(mUser.mMiddleHead)) {
				mImageLoader.getBitmap(mContext, mHeaderImageView, null,
						mUser.mMiddleHead, 0, false, false);
			}

			mNickNameTextView.setText(mUser.nickName);

			if (!TextUtils.isEmpty(mUser.sign)) {
				mSignTextView.setText(mUser.sign);
			} else {
				if (!TextUtils.isEmpty(mFuid)) {
					mSignTextView.setText(mContext
							.getString(R.string.default_sign) + "...");
				} else {
					mSignTextView.setText(mContext
							.getString(R.string.user_sign) + "...");
				}
			}

			if (mUser.mGender == 2) {
				mSexTextView.setText(mContext.getString(R.string.secret));
			} else if (mUser.mGender == 0) {
				mSexTextView.setText(mContext.getString(R.string.male));
			} else if (mUser.mGender == 1) {
				mSexTextView.setText(mContext.getString(R.string.female));
			}

			mRightBtn.setVisibility(View.VISIBLE);

			// 判断当前传入的用户ID是否为空，如果不为空显示发送消息按钮
			// 如果为空则表示是当前登录用户的资料，隐藏发送消息按钮和申请加好友按钮
			if (!TextUtils.isEmpty(mFuid)) {
				// 如果已经是好友，就隐藏申请加好友按钮
				if (mUser.mIsFriend == 0) {
					mApplyBtn.setVisibility(View.VISIBLE);
				} else {
					mApplyBtn.setVisibility(View.GONE);
				}
				mSendBtn.setVisibility(View.VISIBLE);
			} else {
				mApplyBtn.setVisibility(View.GONE);
				mSendBtn.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 点击发送消息按钮
		case R.id.sendbtn:
			Intent chatIntent = new Intent(mContext, ChatMainActivity.class);
			TCSession session = TCChatManager.getInstance().getSessionByID(
					mFuid, ChatType.SINGLE_CHAT);

			if (session == null) {
				session = new TCSession();
				// 设置会话类型
				session.setChatType(ChatType.SINGLE_CHAT);
				// 设置会话名称
				session.setSessionName(mUser.nickName);
				// 设置会话头像
				session.setSessionHead(mUser.mSmallHead);
				// 设置会话ID
				session.setFromId(mUser.uid);
				// 设置会话对象的扩展属性
				// HashMap<String, String> extendMap = new HashMap<String,
				// String>();
				// 比如设置会话对象的签名
				// extendMap.put("sign", mUser.sign);
				// 比如设置会话对象的性别
				// extendMap.put("gender", mUser.mGender + "");
				// session.setExtendMap(extendMap);
			}

			chatIntent.putExtra("session", session);
			startActivity(chatIntent);
			break;

		// 点击申请加好友按钮
		case R.id.applybtn:
			Message message = new Message();
			message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
			message.obj = mContext.getString(R.string.send_loading);
			mHandler.sendMessage(message);
			applyFriend();
			break;

		case R.id.left_btn:
			this.finish();
			break;

		case R.id.right_btn:
			if (!TextUtils.isEmpty(mFuid)) {
				showMoreDialog();
			} else {
				Intent editIntent = new Intent(mContext,
						EditProfileActivity.class);
				startActivity(editIntent);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 获取用户详细入口
	 */
	private void getUserDetail() {
		new Thread() {
			@Override
			public void run() {

				if (Common.verifyNetwork(mContext)) {
					try {
						LoginResult result = Common.getThinkchatInfo()
								.getUserDetail(mFuid);

						if (result != null && result.mState != null
								&& result.mState.code == 0) {

							mUser = result.mUser;

							// 如果获取的当前登录用户的资料，则将其保存到文件中，并发送通知更新“我”页面数据
							if (TextUtils.isEmpty(mFuid)
									&& result.mUser != null) {

								User user = Common.getLoginResult(mContext);
								result.mUser.password = user.password;

								Common.saveLoginResult(mContext, result.mUser);
								Common.setUid(result.mUser.uid);
								Common.setToken(result.mUser.mToken);

								mContext.sendBroadcast(new Intent(
										ProfileFragment.ACTION_REFRESH_USER));
							} else if (result.mUser != null) {
								TCChatManager.getInstance().updateSessionByID(
										mFuid, ChatType.SINGLE_CHAT,
										result.mUser.nickName,
										result.mUser.mSmallHead);
								// 发通知更新会话列表
								mContext.sendBroadcast(new Intent(
										ChatActivity.UPDATE_COUNT_ACTION));
								// 发通知更新会话总的未读数
								mContext.sendBroadcast(new Intent(
										HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
							}

							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);

						} else {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
							if (result != null
									&& result.mState != null
									&& !TextUtils
											.isEmpty(result.mState.errorMsg)) {
								message.obj = result.mState.errorMsg;
							} else {
								message.obj = mContext
										.getString(R.string.load_error);
							}
							mHandler.sendMessage(message);
						}

					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj = mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				} else {
					mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
				}

			}
		}.start();
	}

	/**
	 * 点击更多弹出的对话框
	 * 
	 * @param context
	 */
	private void showMoreDialog() {

		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button cameraBtn = (Button) layout.findViewById(R.id.camera);
		final Button galleryBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);
		LinearLayout line = (LinearLayout) layout.findViewById(R.id.line);
		cameraBtn.setText(mContext.getString(R.string.report));
		galleryBtn.setText(mContext.getString(R.string.delete_friend));
		cancelBtn.setText(mContext.getString(R.string.cancel));

		if (mUser.mIsFriend == 0) {
			line.setVisibility(View.GONE);
			cameraBtn
					.setBackgroundResource(R.drawable.round_half_transparent_btn);
			galleryBtn.setVisibility(View.GONE);
		} else {
			line.setVisibility(View.VISIBLE);
			cameraBtn
					.setBackgroundResource(R.drawable.top_half_transparent_btn);
			galleryBtn.setVisibility(View.VISIBLE);
		}

		cameraBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				Intent reportIntent = new Intent(mContext,
						FeedBackActivity.class);
				reportIntent.putExtra("type", FeedBackActivity.REPORT);
				reportIntent.putExtra("fuid", mFuid);
				startActivity(reportIntent);
			}
		});

		galleryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				Message message = new Message();
				message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
				message.obj = mContext.getString(R.string.send_loading);
				mHandler.sendMessage(message);
				deleteFriend();
			}
		});

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
	 * 申请加好友接口入口函数
	 */
	private void applyFriend() {
		new Thread() {
			@Override
			public void run() {
				if (Common.verifyNetwork(mContext)) {
					try {
						AppState state = Common.getThinkchatInfo().applyFriend(
								mFuid);
						if (state != null && state.code == 0) {
							mHandler.sendEmptyMessage(APPLY_FRIEND_SUCCESS);
						} else {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
							if (state != null
									&& !TextUtils.isEmpty(state.errorMsg)) {
								message.obj = state.errorMsg;
							} else {
								message.obj = mContext
										.getString(R.string.operate_failed);
							}
							mHandler.sendMessage(message);
						}
					} catch (ThinkchatException e) {
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj = mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				} else {
					mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
				}
			}
		}.start();
	}

	/**
	 * 取消好友
	 */
	private void deleteFriend() {
		new Thread() {
			@Override
			public void run() {
				if (Common.verifyNetwork(mContext)) {
					try {
						AppState state = Common.getThinkchatInfo()
								.deleteFriend(mFuid);
						if (state != null && state.code == 0) {
							mHandler.sendEmptyMessage(DELETE_FRIEND_SUCCESS);
						} else {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
							if (state != null
									&& !TextUtils.isEmpty(state.errorMsg)) {
								message.obj = state.errorMsg;
							} else {
								message.obj = mContext
										.getString(R.string.operate_failed);
							}
							mHandler.sendMessage(message);
						}
					} catch (ThinkchatException e) {
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj = mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				} else {
					mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
				}
			}
		}.start();
	}
}
