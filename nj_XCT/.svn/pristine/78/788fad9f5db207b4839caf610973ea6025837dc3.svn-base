package com.xguanjia.hx.login.activity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jivesoftware.smack.packet.Presence;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.login.bean.LoginBean;
import com.xguanjia.hx.openfire.listener.XmppTool;

public class OtherLoginActivity extends BaseActivity {

	private TextView getCodeTv;
	private EditText mobileEt, codeEt;
	private Button nextBtn;
	private ProgressDialog pd;
	private String mobile, code;
	private Timer timer, timer1;
	private Button btn_yy;// 语音验证码
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int msgId = msg.what;
			switch (msgId) {
			case 1:
				if (Constants.i < 1) {
					getCodeTv.setText("获取验证码");
					getCodeTv.setClickable(true);
					if (timer != null) {
						timer.cancel();
					}
					if (timer1 != null) {
						timer1.cancel();
					}
				} else {
					Constants.i--;
					getCodeTv.setText("(" + Constants.i + ")秒后重新获取");
					getCodeTv.setClickable(false);
				}
				break;
			default:
				break;

			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UEProbAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	private void initView() {
		sp = this.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		View view = getLayoutInflater().inflate(R.layout.otherlogin_activity,
				null);
		LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		mainView.addView(view, layoutParams);
		setTitleText("手机登录");
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		mobileEt = (EditText) view.findViewById(R.id.mobileEt);
		String mobileNum = "";
		try {
			mobileNum = getIntent().getStringExtra("username_other");
		} catch (Exception e) {
			// TODO: handle exception
		}
		if ("".equals(mobileNum) || mobileNum == null) {
			showToast("请输入手机号码");
		}
		mobileEt.setText(mobileNum);
		codeEt = (EditText) view.findViewById(R.id.codeEt);
		getCodeTv = (TextView) view.findViewById(R.id.getCodeTv);
		getCodeTv.setOnClickListener(buttonClickListener);
		btn_yy = (Button) view.findViewById(R.id.btn_yy);
		btn_yy.setOnClickListener(buttonClickListener);
		nextBtn = (Button) view.findViewById(R.id.nextBtn);
		nextBtn.setOnClickListener(buttonClickListener);
		if (Constants.i != 61 && Constants.i != 0) {
			getCodeTv.setText("(" + Constants.i + ")秒后重新获取");
			getCodeTv.setClickable(false);
			timer = new Timer();
			if (timer != null) {
				timer.cancel();
			}
			timer1 = new Timer();
			timer1.schedule(new TimerTask() {
				public void run() {
					Message message = new Message();
					message.what = 1;
					handler.sendMessage(message);
					System.out.println(Constants.i + "");
				}
			}, 0, 1000);
		}
	}

	/**
	 * 点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				Editor editor = sp.edit();
				editor.putString("mobileNum", mobileEt.getText().toString());
				editor.commit();
				finish();
				break;
			case R.id.nextBtn:
				nextAction();
				break;
			case R.id.getCodeTv:
				getCodeAction();
				break;
			case R.id.btn_yy:// 语音验证码
				doYYYZM();
				break;
			default:
				break;
			}
		};
	};

	private void getCodeAction() {
		mobile = mobileEt.getText().toString();
		if ("".equals(mobile)) {
			showToast("请输入手机号码");
			return;
		}
		if (!checkPhoneNum(mobile)) {
			showToast("请输入正确的手机号码");
			return;
		}
		try {
			pd = ProgressDialog.show(OtherLoginActivity.this, "", "验证码获取中。。。",
					true, true);
			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("loginName", mobile);
			requestMap.put("partyId", Constants.partyId);
			ServerAdaptor.getInstance(this)
					.doAction(
							1,
							Constants.UrlHead
									+ "client.action.CommonAction$getCaptcha",
							requestMap, new ServiceSyncListener() {

								public void onSuccess(
										ActionResponse returnObject) {
									// TODO Auto-generated method stub
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									Constants.i = 61;
									if (timer1 != null) {
										timer1.cancel();
									}
									timer = new Timer();
									timer.schedule(new TimerTask() {
										public void run() {
											Message message = new Message();
											message.what = 1;
											handler.sendMessage(message);
											System.out
													.println(Constants.i + "");
										}
									}, 0, 1000);
									showToast(returnObject.getMessage()
											.toString());
								}

								public void onError(ActionResponse returnObject) {
									// TODO Auto-generated method stub
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									showToast(returnObject.getMessage()
											.toString());
								}
							});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (timer != null) {
			timer.cancel();
		}
		if (timer1 != null) {
			timer1.cancel();
		}
		super.onDestroy();
	}

	/**
	 * 判断指定号码是否为合法的手机号码
	 * 
	 * @param strPhoneNum
	 * @return true表示为合法的手机号码，false表示为非法的手机号码
	 */
	public boolean checkPhoneNum(String strPhoneNum) {
		// 判断输入的号码是否有效
		String expression = "((^(13|15|18|14|12)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|"
				+ "(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|"
				+ "(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
		CharSequence inputStr = strPhoneNum;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		Log.i("DeviceManagerActivity", "号码判断：" + matcher.matches());
		if (!matcher.matches()) {
			return false;
		} else {
			return true;
		}
	}

	private void nextAction() {
		mobile = mobileEt.getText().toString();
		code = codeEt.getText().toString();
		if ("".equals(mobile)) {
			showToast("请输入手机号码");
			return;
		}
		if (!checkPhoneNum(mobile)) {
			showToast("请输入正确的手机号码");
			return;
		}
		if ("".equals(code)) {
			showToast("请输入验证码");
			return;
		}
		try {
			pd = ProgressDialog.show(OtherLoginActivity.this, "", "执行中。。。",
					true, true);
			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("loginName", mobile);
			requestMap.put("putCaptcha", code);
			requestMap.put("partyId", Constants.partyId);
			ServerAdaptor.getInstance(this).doAction(1, "LoginAction$login",
					requestMap, new ServiceSyncListener() {

						public void onSuccess(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							try {
								Constants.loginBean = new Gson().fromJson(
										returnObject.getData().toString(),
										new TypeToken<LoginBean>() {
										}.getType());
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
							}

							Editor editor = sp.edit();
							editor.putString("loginName", mobile);
							JSONObject resData = (JSONObject) returnObject
									.getData();
							try {
								if (!resData.isNull("userId")) {
									editor.putString("userID",
											resData.getString("userId"));
									Constants.userId = resData
											.getString("userId");
								}
								if (!resData.isNull("mobile")) {
									Constants.mobile = resData
											.getString("mobile");
								}
								if (!resData.isNull("partyId")) {
									editor.putString("partyId",
											resData.getString("partyId"));
									Constants.partyId = resData
											.getString("partyId");
								}
								if (!resData.isNull("uid")) {
//									editor.putString("uid",
//											resData.getString("uid"));
//									Constants.uid = resData.getString("uid");
								}
								if (!resData.isNull("depname")) {
									Constants.depname = resData
											.getString("depname");
								}
								if (!resData.isNull("partyId")) {
									Constants.partyId = resData
											.getString("partyId");
								}
								if (!resData.isNull("authCode")) {
									// 授权码
									Constants.APP_ID = resData
											.getString("authCode");
								}
								if (!resData.isNull("loginName")) {
									Constants.loginName = resData
											.getString("loginName");
								}
								if (!resData.isNull("userName")) {
									Constants.userName = resData
											.getString("userName");
								}
								editor.commit();
							} catch (JSONException e) {
								e.printStackTrace();
							}
							setOpenfireUid();
							Intent intent = new Intent();
							intent.setClass(OtherLoginActivity.this,
									setPasswordActivity.class);
							startActivity(intent);
						}

						public void onError(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							showToast(returnObject.getMessage().toString());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void setOpenfireUid() {
		String partyID_uid = Constants.partyId + "_" + Constants.mobile;// 登录名就是openfire的uid
		Editor editor = sp.edit();
		editor.putString("uid", partyID_uid);
		editor.commit();
		Constants.uid = partyID_uid;
		getLogin("", sp.getString("password", sp.getString("password", ""))
				.toString(), "");
	}

	private void getLogin(final String mobile, final String password,
			final String partyId) {

		new Thread(new Runnable() {
			public void run() {

				try {
					XmppTool.con = null;

					XmppTool.getConnection();
					Log.i("tag", "login" + XmppTool.getConnection().toString());
					// 状态
					Presence presence = new Presence(Presence.Type.available);
					XmppTool.getConnection().sendPacket(presence);

				} catch (Exception e) {
					XmppTool.closeConnection();
				}
			}
		}).start();
	}

	/**
	 * 语音验证码
	 * **/
	private void doYYYZM() {
		mobile = mobileEt.getText().toString();
		if ("".equals(mobile)) {
			showToast("请输入手机号码");
			return;
		}
		if (!checkPhoneNum(mobile)) {
			showToast("请输入正确的手机号码");
			return;
		}
		try {
			pd = ProgressDialog.show(OtherLoginActivity.this, "", "执行中。。。",
					true, true);
			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("mobile", mobile);

			ServerAdaptor.getInstance(this).doAction(1,
					"com.xguanjia.client.action.CommonAction$sendVoiceCode",
					requestMap, new ServiceSyncListener() {

						public void onSuccess(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							if (returnObject != null) {
								if (returnObject.getCode() == 0) {
									Toast.makeText(OtherLoginActivity.this,
											"语音电话已发送，请注意接听", Toast.LENGTH_SHORT)
											.show();
								}
							}

						}

						public void onError(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							showToast(returnObject.getMessage().toString());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

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

			default:
				break;
			}
		}
	};
}
