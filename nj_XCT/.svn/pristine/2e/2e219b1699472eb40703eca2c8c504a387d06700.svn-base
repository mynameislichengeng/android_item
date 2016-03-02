package com.haoqee.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.haoqee.chat.entity.ModifyPwd;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;

/**
 * 修改密码页面
 *
 */
public class ModifyPasswordActivity extends BaseActivity implements
		OnClickListener {

	private EditText mOldPasswordEditText; // 原密码输入框
	private EditText mNewPasswordEditText; // 新密码输入框
	private EditText mConfirmPasswordEditText; // 确认密码输入框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modify_password_layout);
		initComponent();
	}

	private void initComponent() {
		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn,
				mContext.getString(R.string.modify_pwd));
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mOldPasswordEditText = (EditText) findViewById(R.id.oldpassword);
		mNewPasswordEditText = (EditText) findViewById(R.id.newpassword);
		mConfirmPasswordEditText = (EditText) findViewById(R.id.confirm_password);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			this.finish();
			break;

		case R.id.right_btn:
			checkModify();
			break;

		default:
			break;
		}
	}

	private void checkModify() {
		String oldPwd = mOldPasswordEditText.getText().toString().trim();
		String newPwd = mNewPasswordEditText.getText().toString().trim();
		String confirmPwd = mConfirmPasswordEditText.getText().toString()
				.trim();

		// 检测输入的旧密码是否为空
		if (oldPwd.equals("")) {
			showToast(mContext.getString(R.string.please_input_old_pwd));
			return;
		}

		// 检测输入的新密码是否为空
		if (newPwd.equals("")) {
			showToast(mContext.getString(R.string.please_input_new_password));
			return;
		}

		// 检测输入的确认密码是否和新密码一致
		if (!confirmPwd.equals(newPwd)) {
			showToast(mContext.getString(R.string.confirm_password_error));
			return;
		}

		// 检测网络是否连上
		if (!Common.verifyNetwork(mContext)) {
			showToast(mContext.getString(R.string.network_error));
			return;
		}

		Message message = new Message();
		message.obj = mContext.getString(R.string.send_loading);
		message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
		mHandler.sendMessage(message);

		modifyPwd(oldPwd, newPwd);
	}

	// 修改密码接口访问入口
	private void modifyPwd(final String oldPwd, final String pwd) {

		new Thread() {
			@Override
			public void run() {
				try {
					ModifyPwd result = Common.getThinkchatInfo()
							.modifyPassword(oldPwd, pwd);
					if (result != null && result.mState != null
							&& result.mState.code == 0) {
						mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);

						TCChatManager.getInstance().setXmppPwd(pwd);
						User login = Common.getLoginResult(mContext);
						login.password = pwd;
						Common.saveLoginResult(mContext, login);

						ModifyPasswordActivity.this.finish();

					} else {
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
						if (result != null && result.mState != null
								&& !TextUtils.isEmpty(result.mState.errorMsg)) {
							msg.obj = result.mState.errorMsg;
						} else {
							msg.obj = mContext
									.getString(R.string.modify_pwd_failed);
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
			}
		}.start();

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
				baseHideProgressDialog();
				showToast(mContext.getString(R.string.modify_pwd_successfully));
				ModifyPasswordActivity.this.finish();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String) msg.obj;
				if (error_Detail != null && !error_Detail.equals("")) {
					showToast(error_Detail);
				} else {
					showToast(mContext.getString(R.string.feedback_fail));
				}
				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();

				showToast(mContext.getString(R.string.network_error));
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}

				showToast(message);
				break;

			default:
				break;
			}
		}
	};
}
