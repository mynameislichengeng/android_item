package com.xguanjia.hx.login.activity;



import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Presence;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;
import cn.sharesdk.framework.authorize.e;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AsyncAuthorizeTask;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.login.bean.LoginBean;
import com.xguanjia.hx.openfire.listener.XmppTool;
import com.xguanjia.hx.set.gesture.activity.SafeSetActivity;
import com.xguanjia.hx.set.gesture.activity.YanzhengSafeSetActivity;
import com.chinamobile.salary.R;

public class StartupActivity extends BaseActivity implements AnimationListener {
	private Animation alphaAnimation = null;
	private ProgressDialog pd;
	private String resMessage = "";

	private String m_strMac = "";

	private HashMap<String, String> mval;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case 0:
				Constants.loginName = sp.getString("loginName", "");
				Constants.password = sp.getString("password", "");
				setOpenfireUid();
				Intent intent = new Intent();

				if (sp.getBoolean("isNeedVoluntaryLogin", false)&&(!sp.getString("loginName", "").equals(""))) {
					intent.setClass(StartupActivity.this, HaoXinProjectActivity.class);
				} else {
					intent.setClass(StartupActivity.this, TeLoginActivity.class);
				}

				if (sp.getBoolean("isopensafeset", false)) {
					intent.setClass(StartupActivity.this, YanzhengSafeSetActivity.class);
				} else {

				}
				startActivity(intent);
				finish();
				break;
			case AsyncAuthorizeTask.AUTHORIZE_MESSAGE_ERR_CODE:
			case AsyncAuthorizeTask.AUTHORIZE_MESSAGE_CODE:

				break;
			case 1:
				// MAMessage.ShowMessage(StartupActivity.this,
				// R.string.prompt_message, "该用户不存在 ");
				intent = new Intent();
				intent.setClass(StartupActivity.this, TeLoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case 2:
				MAMessage.ShowMessage(StartupActivity.this, R.string.prompt_message, "密码错误");
				intent = new Intent();
				intent.setClass(StartupActivity.this, TeLoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case -1:
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				MAMessage.ShowMessage(StartupActivity.this, R.string.prompt_message, msg.obj.toString());
				intent = new Intent();
				intent.setClass(StartupActivity.this, TeLoginActivity.class);
				startActivity(intent);
				finish();
				break;
			case 6:
				MAMessage.ShowMessage(StartupActivity.this, R.string.prompt_message, "非法用户，请联系联通运营商");
				intent = new Intent();
				intent.setClass(StartupActivity.this, TeLoginActivity.class);
				startActivity(intent);
				finish();
			case 7:
				MAMessage.ShowMessage(StartupActivity.this, R.string.prompt_message, "服务端配置有误");
				intent = new Intent();
				intent.setClass(StartupActivity.this, TeLoginActivity.class);
				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		Constants.DEV_ID = "" + tm.getDeviceId();
		imei = tm.getDeviceId();
		imsi = tm.getSubscriberId();
		Constants.IMEI = imei;
		Log.i("", "Constants.IMEI:" + Constants.IMEI);
		Constants.IMSI = imsi;
		Constants.partyId = sp.getString("partyId", Constants.partyId);
		Constants.SDK_VERSION = Build.VERSION.SDK;
		Constants.SYS_VERSION = Build.VERSION.RELEASE;
		AutoLogin();
		View image_View = getLayoutInflater().inflate(R.layout.activity_main, null);
		this.mainView.addView(image_View);
		alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
		alphaAnimation.setFillEnabled(true); // 启动Fill保持
		alphaAnimation.setFillAfter(true); // 设置动画的最后一帧是保持在View上面
		image_View.setAnimation(alphaAnimation);
		alphaAnimation.setAnimationListener(this); // 为动画设置监听
		TelListener myPhoneCallListener = new TelListener(StartupActivity.this);
		tm.listen(myPhoneCallListener, PhoneStateListener.LISTEN_CALL_STATE);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// 动画结束时结束欢迎界面并转到软件的主界面
		sp = this.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		Intent intent = null;
		if (sp.getBoolean("islogin", false) && !sp.getString("password", "").equals("")) {
			HashMap<String, String> val = new HashMap<String, String>();
			val.put("loginName", sp.getString("loginName", ""));
			val.put("password", sp.getString("password", ""));
			val.put("version", Constants.SYS_VERSION);// 系统版本号
			val.put("mobiletype", "android");// partyId
			val.put("partyId", sp.getString("partyId", Constants.partyId));
			val.put("attendanceStandardTime", sp.getString("attendanceStandardTime", ""));
			val.put("product_id", Constants.PRODUCT_ID);
			val.put("deviceno", Constants.DEV_ID);
			login(val);
		} else {
			intent = new Intent(this, TeLoginActivity.class);
			startActivity(intent);
			finish();
		}

	}

	private void AutoLogin() {

		// 自动登录
		if (sp.contains("isNeedVoluntaryLogin")) {
			if (sp.getBoolean("isNeedVoluntaryLogin", true)) {
				mval = new HashMap<String, String>();
				mval.put("loginName", sp.getString("loginName", ""));
				mval.put("password", sp.getString("password", ""));
				mval.put("mac", m_strMac);
				mval.put("version", Constants.SYS_VERSION);// 系统版本号
				mval.put("appVersion", getVersion());// 软件版本号
				mval.put("mobiletype", "android");// partyId
				mval.put("partyId", sp.getString("partyId", Constants.partyId));
				mval.put("attendanceStandardTime", sp.getString("attendanceStandardTime", ""));
				mval.put("product_id", Constants.PRODUCT_ID);
				mval.put("deviceno", Constants.DEV_ID);
				login(mval);
			}
		}

	}

	public String getVersion() {
		String appVersion = "";
		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			appVersion = info.versionName; // 版本名
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appVersion;
	}

	@Override
	public void onAnimationRepeat(Animation animation) {

	}

	@Override
	public void onAnimationStart(Animation animation) {

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 在欢迎界面屏蔽BACK键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}

	protected void onResume() {
		// TODO Auto-generated method stub
		UEProbAgent.onResume(this);
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		UEProbAgent.onPause(this);
		super.onPause();
	}

	public void login(Map<String, String> val) {
		Constants.TIME_OUT = 1000;
		try {
			ServerAdaptor.getInstance(this).doAction(1, "LoginAction$login", val, new ServiceSyncListener() {
				@Override
				public void onError(ActionResponse returnObject) {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Constants.TIME_OUT = 3000;
					resMessage = returnObject.getMessage();
					Message msg = handler.obtainMessage();
					msg.obj = resMessage;
					msg.what = -1;
					handler.sendMessage(msg);
				}

				@Override
				public void onSuccess(ActionResponse returnObject) {
					Constants.TIME_OUT = 3000;
					if (null == returnObject) {
						resMessage = "返回数据错误";
						handler.sendEmptyMessage(-1);
						return;
					}

					if (returnObject.getCode() == 6) {
						handler.sendEmptyMessage(6);
						return;
					}

					try {
						Constants.loginBean = new Gson().fromJson(returnObject.getData().toString(), new TypeToken<LoginBean>() {
						}.getType());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}

					JSONObject resData = (JSONObject) returnObject.getData();
					try {
						Constants.password = sp.getString("password", "");
						if (!resData.isNull("userId")) {
							Constants.userId = resData.getString("userId");
						}
						if (!resData.isNull("mobile")) {
							Constants.mobile = resData.getString("mobile");
						}
						if (!resData.isNull("is4G")) {
							Constants.is4G = resData.getString("is4G");
						}
						if (!resData.isNull("partyId")) {
							System.out.println("partyId为------" + resData.getString("partyId"));
							Constants.partyId = resData.getString("partyId");
							Editor editor = sp.edit();
							editor.putString("partyId", resData.getString("partyId"));
							editor.commit();
						}
						if (!resData.isNull("uid")) {
//							System.out.println("uid为------" + resData.getString("uid"));
//							Constants.uid = resData.getString("uid");
//							Editor editor = sp.edit();
//							editor.putString("uid", resData.getString("uid"));
//							editor.commit();
						}
						if (!resData.isNull("depname")) {
							Constants.depname = resData.getString("depname");
						}
						if (!resData.isNull("authCode")) {
							// 授权码
							Constants.APP_ID = resData.getString("authCode");
						}
						if (!resData.isNull("loginName")) {
							Constants.loginName = resData.getString("loginName");
						}
						if (!resData.isNull("userName")) {
							Constants.userName = resData.getString("userName");
						}
						if (!resData.isNull("companyId")) {
							// 企业Id
							Constants.COMPANY_ID = resData.getString("companyId");
						}

						handler.sendEmptyMessage(0);

					} catch (JSONException e) {
						resMessage = "返回数据格式错误:" + resData.toString();
						Message msg = handler.obtainMessage();
						msg.obj = resMessage;
						msg.what = -1;
						handler.sendMessage(msg);
						return;
					}

				}

			});
		} catch (HaoqeeException e) {
			resMessage = e.getMessage();
			handler.sendEmptyMessage(-1);
			e.printStackTrace();
		}

	}

	private void setOpenfireUid() {
		String partyID_uid = Constants.partyId + "_" + Constants.mobile;// 登录名就是openfire的uid
		Editor editor = sp.edit();
		editor.putString("uid", partyID_uid);
		editor.commit();
		Constants.uid = partyID_uid;
		getLogin("", sp.getString("password", sp.getString("password", "")).toString(), "");
	}
	
	
	
	private void getLogin(final String mobile, final String password, final String partyId) {
		new Thread() {
			@Override
			public void run() {

				try {
					XmppTool.con = null;
					// 连接
					XmppTool.getConnection();
					// 状态
					Presence presence = new Presence(Presence.Type.available);
					XmppTool.getConnection().sendPacket(presence);

				} catch (Exception e) {
					XmppTool.closeConnection();
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
				hideProgressDialog();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String) msg.obj;
				if (error_Detail != null && !error_Detail.equals("")) {
					// Toast.makeText(mContext, error_Detail,
					// Toast.LENGTH_LONG).show();
				} else {
					// Toast.makeText(mContext, R.string.load_error,
					// Toast.LENGTH_LONG).show();
				}
				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();
				// Toast.makeText(mContext, R.string.network_error,
				// Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				// Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}
	};

}
