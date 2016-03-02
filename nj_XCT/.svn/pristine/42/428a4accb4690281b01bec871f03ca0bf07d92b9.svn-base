package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.entity.Server;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.haoqee.chat.net.ThinkchatInfo;
import com.chinamobile.salary.R;

/**
 * 登录页面功能
 *
 */
public class LoginActivity extends IndexActivity implements OnClickListener {

	private EditText mMobileEditText; // 登陆手机号输入框
	private EditText mPasswordEditText; // 登录密码输入框
	private Button mLoginBtn; // 登录按钮
	private TextView mNewUserRegisterBtn; // 新用户注册按钮
	private TextView mServerBtn; // 服务器按钮
	private final static int REGISTER_REQUEST = 1110;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_layout);
		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {
		setTitleContent(0, 0, mContext.getString(R.string.login));

		mMobileEditText = (EditText) findViewById(R.id.phone);
		mPasswordEditText = (EditText) findViewById(R.id.password);

		mLoginBtn = (Button) findViewById(R.id.loginbtn);
		mLoginBtn.setOnClickListener(this);

		mNewUserRegisterBtn = (TextView) findViewById(R.id.registerbtn);
		mNewUserRegisterBtn.setOnClickListener(this);

		mServerBtn = (TextView) findViewById(R.id.serverbtn);
		mServerBtn.setOnClickListener(this);

		List<Server> serverList = Common.getServerList(mContext);

		if (serverList == null) {
			serverList = new ArrayList<Server>();

			String name = mContext.getString(R.string.think_chat_server);
			Server server = new Server(name, MainActivity.LOGISTIC_SERVER,
					ThinkchatInfo.SERVER, MainActivity.HOST + ":"
							+ MainActivity.PORT);

			server.mIsDefault = true;
			server.mIsCurrent = true;

			serverList.add(server);

			Common.saveCurrentServer(mContext, server);

			Common.saveServerList(mContext, serverList);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		// 点击新用户注册执行事件
		case R.id.registerbtn:
			Intent registerIntent = new Intent(mContext, RegisterActivity.class);
			startActivityForResult(registerIntent, REGISTER_REQUEST);
			break;

		// 点击登录按钮执行事件
		case R.id.loginbtn:
			hideSoftKeyboard();
			checkLogin();
			break;

		case R.id.serverbtn:
			Intent serverIntent = new Intent(mContext, ServerActivity.class);
			startActivity(serverIntent);
			break;

		default:
			break;
		}
	}

	private void checkLogin() {
		String username = mMobileEditText.getText().toString().trim();
		String password = mPasswordEditText.getText().toString().trim();

		// 检测输入手机号是否为空
		if (username.equals("")) {
			showToast(mContext.getString(R.string.please_input)
					+ mContext.getString(R.string.mobile_phone));
			return;
		}

		// 检测输入密码号是否为空
		if (password.equals("")) {
			showToast(mContext.getString(R.string.please_input)
					+ mContext.getString(R.string.password));
			return;
		}

		// 判断网络是否连上
		if (!Common.verifyNetwork(mContext)) {
			showToast(mContext.getString(R.string.network_error));
			return;
		}

		Message message = new Message();
		message.what = MainActivity.SHOW_PROGRESS_DIALOG;
		message.obj = mContext.getString(R.string.login) + "...";
		mHandler.sendMessage(message);

		getLogin(username, password);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MainActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				showProgressDialog(dialogMsg);
				break;

			case MainActivity.HIDE_PROGRESS_DIALOG:
				hideProgressDialog();
				break;

			case MainActivity.MSG_LOAD_ERROR:
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

			case MainActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				break;

			case MainActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}
	};

	private void getLogin(final String mobile, final String password) {
		new Thread() {
			@Override
			public void run() {

				try {
					LoginResult result = Common.getThinkchatInfo().getLogin1(
							mobile, password);

					if (result != null && result.mState != null
							&& result.mState.code == 0) {

						baseHideProgressDialog();
						if (result.mUser != null) {
							result.mUser.password = password;

							Common.saveLoginResult(mContext, result.mUser);
							Common.setUid(result.mUser.uid);
							Common.setToken(result.mUser.mToken);

							setResult(RESULT_OK);
							LoginActivity.this.finish();

						}

					} else {
						Message message = new Message();
						message.what = MainActivity.MSG_TICE_OUT_EXCEPTION;
						if (result != null && result.mState != null
								&& !TextUtils.isEmpty(result.mState.errorMsg)) {
							message.obj = result.mState.errorMsg;
						} else {
							message.obj = mContext
									.getString(R.string.login_error);
						}
						mHandler.sendMessage(message);
					}

				} catch (ThinkchatException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = MainActivity.MSG_TICE_OUT_EXCEPTION;
					msg.obj = mContext.getString(R.string.timeout);
					mHandler.sendMessage(msg);
				}

			}
		}.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {

		case REGISTER_REQUEST:
			setResult(resultCode);
			LoginActivity.this.finish();

			break;

		default:
			break;
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			setResult(RESULT_CANCELED);
		}
		return super.dispatchKeyEvent(event);
	}
}
