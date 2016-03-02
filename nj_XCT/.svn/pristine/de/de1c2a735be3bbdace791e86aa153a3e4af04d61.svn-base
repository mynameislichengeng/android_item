package com.haoqee.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.chinamobile.salary.R;

/**
 * 注册功能页面
 *
 */
public class RegisterActivity extends IndexActivity implements OnClickListener{
	
	private EditText mMobileEditText;				//手机号输入框
	private EditText mNicknameEditText;				//昵称输入框
	private EditText mPasswordEditText;				//密码输入框
	private EditText mConfirmPwdEditText;			//确认密码输入框
	private Button mRegisterBtn;					//注册按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initComponent();
	}

	/**
	 * 初始化控件
	 */
	private void initComponent(){
		setTitleContent(0, 0, mContext.getString(R.string.register));
		
		mMobileEditText = (EditText) findViewById(R.id.phone);
		mNicknameEditText = (EditText) findViewById(R.id.nickname);
		mPasswordEditText = (EditText) findViewById(R.id.password);
		mConfirmPwdEditText = (EditText) findViewById(R.id.confirm_password);
		
		mRegisterBtn = (Button) findViewById(R.id.registerbtn);
		mRegisterBtn.setOnClickListener(this);
	}
	
	private Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			
			case MainActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String)msg.obj;
				showProgressDialog(dialogMsg);
				break;
				
			case MainActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				break;
				
			case MainActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String)msg.obj;
				if(error_Detail != null && !error_Detail.equals("")){
					Toast.makeText(mContext,error_Detail,Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(mContext,R.string.load_error,Toast.LENGTH_LONG).show();
				}
				break;
				
			case MainActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();
				Toast.makeText(mContext,R.string.network_error,Toast.LENGTH_LONG).show();
				break;
				
			case MainActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message=(String)msg.obj;
				if (message==null || message.equals("")) {
					message=mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext,message, Toast.LENGTH_LONG).show();
				break;
				
			default:
				break;
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//点击注册按钮执行事件
		case R.id.registerbtn:
			hideSoftKeyboard();
			checkRegister();
			break;

		default:
			break;
		}
	}
	
	private void checkRegister(){
		String mobile = mMobileEditText.getText().toString().trim();
		String nickName = mNicknameEditText.getText().toString().trim();
		String password = mPasswordEditText.getText().toString().trim();
		String confirmPwd = mConfirmPwdEditText.getText().toString().trim();
		
		//检测手机号是否为空
		if(TextUtils.isEmpty(mobile)){
			showToast(mContext.getString(R.string.please_input) + mContext.getString(R.string.mobile_phone));
			return;
		}
		
		//检测手机号是否为11位
		if(mobile.length() != 11){
			showToast(mContext.getString(R.string.please_input_correct_mobile_phone));
			return;
		}
		
		//检测昵称是否输入
		if(TextUtils.isEmpty(nickName)){
			showToast(mContext.getString(R.string.please_input) + mContext.getString(R.string.nickname));
			return;
		}
		
		//检测密码是否输入
		if(TextUtils.isEmpty(password)){
			showToast(mContext.getString(R.string.please_input) + mContext.getString(R.string.password));
			return;
		}
		
		//检测确认密码是输入否和密码一致
		if(!confirmPwd.equals(password)){
			showToast(mContext.getString(R.string.confirm_password_error));
			return;
		}
		
		//判断网络是否连上
		if(!Common.verifyNetwork(mContext)){
			showToast(mContext.getString(R.string.network_error));
			return;
		}
		
		Message message = new Message();
		message.what = MainActivity.SHOW_PROGRESS_DIALOG;
		message.obj = mContext.getString(R.string.send_loading);
		mHandler.sendMessage(message);
		
		register(mobile, nickName, password);
	}
	
	/**
	 * 调用注册接口入口
	 * @param mobile			手机号
	 * @param nickName			昵称
	 * @param password			密码
	 */
	private void register(final String mobile, final String nickName, final String password){
		new Thread(){
			@Override
			public void run(){
				try {
					LoginResult result = Common.getThinkchatInfo().register(mobile, nickName, password);
					if(result != null && result.mState != null && result.mState.code == 0){
						
						mHandler.sendEmptyMessage(MainActivity.HIDE_PROGRESS_DIALOG);
						if (result.mUser != null) {
							result.mUser.password = password;
							
							Common.saveLoginResult(mContext, result.mUser);
							Common.setUid(result.mUser.uid);
							Common.setToken(result.mUser.mToken);
						}
						setResult(RESULT_OK);
						RegisterActivity.this.finish();
					}else {
						Message message = new Message();
						message.what = MainActivity.MSG_LOAD_ERROR;
						if(result != null && result.mState != null && !TextUtils.isEmpty(result.mState.errorMsg)){
							message.obj = result.mState.errorMsg;
						}else {
							message.obj = mContext.getString(R.string.register_error);
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
}
