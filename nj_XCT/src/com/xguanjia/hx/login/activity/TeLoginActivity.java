package com.xguanjia.hx.login.activity;





import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import cmcc.ueprob.agent.ReportPolicy;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AsyncAuthorizeTask;
import com.xguanjia.hx.common.AuthorizeManager;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.Function;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.MD5;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.login.bean.LoginBean;
import com.xguanjia.hx.login.service.MsgTemplateService;
import com.xguanjia.hx.login.update.UpdateManager;
import com.xguanjia.hx.openfire.listener.XMPPChatListenerService;
import com.xguanjia.hx.openfire.listener.XMPPFileListenerService;
import com.xguanjia.hx.openfire.listener.XmppTool;
import com.xguanjia.hx.setting.activity.AboutActivity;
import com.xguanjia.hx.setting.activity.TeSettingActivity;

public class TeLoginActivity extends BaseActivity {

	private EditText userEdit;
	private EditText pwdEdit;
	private EditText partyIdEt;
	private ImageView imgLoginLogo;
	private Button yanshiImg;
	private CheckBox checkBox;
	// private CheckBox savePwdPick;
	private Button loginBtn;
	private Button otherLoginImg;
	private static boolean isSavePwd = true;
	private Editor editor;
	private String resMessage = "";
	private ProgressDialog pd;
	private String clientPath = "";
	private HashMap<String, String> val;
	private AuthorizeManager authManager;

	private String m_strMac = "";

	private String m_strStandardTime = "";
	private String username = "";// 账号
	private String password = "";// 密码

//	private String partId = "";// 企业ID
	private String uid = "";
	private String username_yanshi = "";
	private Button btn_wjma;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			boolean auth = authManager.isAuth();
			switch (msg.what) {
			case 0:
				editor = sp.edit();
				if (isSavePwd) {
					editor.putString("loginName", userEdit.getText().toString());
					editor.putString("password", pwdEdit.getText().toString());
					editor.putString("partyId", Constants.partyId);
					if (checkBox.isChecked()) {
						editor.putBoolean("islogin", true);
					} else {
						editor.putBoolean("islogin", false);
					}
					Constants.loginName = username;
					if (username_yanshi.equals("13911111122")) {
						editor.putBoolean("isNeedVoluntaryLogin", false);
					} else {
						editor.putBoolean("isNeedVoluntaryLogin", true);
					}

				} else {
					editor.putString("partyid", "");
					editor.putString("loginName", "");
					editor.putString("password", "");
					editor.putBoolean("isNeedVoluntaryLogin", false);
				}
				// 会员ID
				// editor.putString("partyId", Constants.partyId);
				// 人员Id
				editor.putString("user_id", Constants.userId);
				// appId
				editor.putString("authCode", Constants.APP_ID);
				// 权限
				editor.putString("authority", Constants.AUTHORITY);
				// companyId
				editor.putString("company_id", Constants.COMPANY_ID);
				// serverVerson
				editor.putString("serverVerson", Constants.SERVER_VERSON);
				editor.putString("attendanceStandardTime", m_strStandardTime);
				editor.commit();
				// 授权
			
				setOpenfireUid();


				Intent intent = new Intent();
				intent.setClass(TeLoginActivity.this, HaoXinProjectActivity.class);
				startActivity(intent);
				break;
			case AsyncAuthorizeTask.AUTHORIZE_MESSAGE_ERR_CODE:
			case AsyncAuthorizeTask.AUTHORIZE_MESSAGE_CODE:
				break;
			case 1:
				// MAMessage.ShowMessage(TeLoginActivity.this,
				// R.string.prompt_message, "该用户不存在 ");
				break;
			case 2:
				MAMessage.ShowMessage(TeLoginActivity.this, R.string.prompt_message, "密码错误");
				break;
			case -1:
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				MAMessage.ShowMessage(TeLoginActivity.this, R.string.prompt_message, msg.obj.toString());
				break;
			case 6:
				MAMessage.ShowMessage(TeLoginActivity.this, R.string.prompt_message, "非法用户，请联系联通运营商");
			case 7:
				MAMessage.ShowMessage(TeLoginActivity.this, R.string.prompt_message, "服务端配置有误");
				break;
			default:
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		authManager = new AuthorizeManager(this);
		sp = this.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		sf = this.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.login_logo1).showImageForEmptyUri(R.drawable.login_logo1).cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(100)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		initView();
		 InitProb();
	}

	@Override
	protected void onResume() {
		super.onResume();
		UEProbAgent.onResume(this);
		System.out.println("执行了onresume方法");
		System.out.println("partyId---" + Constants.partyId);
	}

	private void AutoLogin() {
		// 自动登录
		if (sp.contains("isNeedVoluntaryLogin")) {
			if (sp.getBoolean("isNeedVoluntaryLogin", true)) {
				val = new HashMap<String, String>();
				val.put("loginName", sp.getString("loginName", ""));
				val.put("password", sp.getString("password", ""));
				val.put("mac", m_strMac);
				val.put("version", Constants.SYS_VERSION);// 系统版本号
				val.put("appVersion", getVersion());// 软件版本号
				val.put("mobiletype", "android");// partyId
				val.put("partyId", sp.getString("partyId", Constants.partyId));
				val.put("attendanceStandardTime", sp.getString("attendanceStandardTime", ""));
				val.put("product_id", Constants.PRODUCT_ID);
				pd = ProgressDialog.show(TeLoginActivity.this, "", "自动登录请稍后", true, false);
				login(val);
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	/*
	 * 界面初始化
	 */
	private void initView() {

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		if (bundle != null) {
			String strAppExit = bundle.getString("AppExit");
			if (strAppExit.equals("app_exit")) {
				// 退出应用程序
				finish();
				Intent m_intent = new Intent(TeLoginActivity.this, XMPPChatListenerService.class);
				stopService(m_intent);
//				Intent m_intent1 = new Intent(TeLoginActivity.this, XMPPFileListenerService.class);
//				stopService(m_intent1);
				System.exit(0);
				
			}
		}

		View login_View = getLayoutInflater().inflate(R.layout.moa_login, null);

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(login_View, params);
		// 手机号码
		userEdit = (EditText) login_View.findViewById(R.id.userEdit);
		// 密码
		pwdEdit = (EditText) login_View.findViewById(R.id.passwordEdit);

		partyIdEt = (EditText) login_View.findViewById(R.id.partyIdEt);
		loginBtn = (Button) login_View.findViewById(R.id.loginBtn);

		otherLoginImg = (Button) login_View.findViewById(R.id.otherLoginImg);
		otherLoginImg.setOnClickListener(this);
//		btn_wjma = (Button) login_View.findViewById(R.id.wjma);
//		btn_wjma.setOnClickListener(this);
		checkBox = (CheckBox) login_View.findViewById(R.id.checkbox);
		if (sp.getBoolean("islogin", false)) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}

		imgLoginLogo = (ImageView) login_View.findViewById(R.id.imgLoginLogo);
		imgLoginLogo.setOnClickListener(this);

		yanshiImg = (Button) login_View.findViewById(R.id.yanshiImg);
		yanshiImg.setOnClickListener(this);

		uid = sp.getString("uid", Constants.uid);
		username = sp.getString("loginName", sp.getString("loginName", ""));
		password = sp.getString("password", sp.getString("password", ""));

		partyIdEt.setText(sp.getString("partyId", Constants.partyId));
		partyIdEt.setSelection(partyIdEt.length());
		if ("".equals(username) || username == null) {
			// userEdit.setText("admin");
			// pwdEdit.setText("888888");
		} else {
			userEdit.setText(username);
			pwdEdit.setText(password);
			userEdit.setSelection(userEdit.length());
		}
		loginBtn.setOnClickListener(loginListener);

		// 自动更新
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String datanow = dateformat.format(new Date());
		Log.i("NoUpVersion", "now_day:" + datanow);
		editor = sp.edit();
		if (!sp.getString("now_day", "").equals(datanow)) {
			updata();
			editor.putString("now_day", datanow);
			editor.commit();
			Log.i("UpVersion", "commit:" + datanow);
		}

	}

	View.OnClickListener quitListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			TeLoginActivity.this.finish();

		}
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgLoginLogo:
			// Intent intent = new Intent();
			// intent.setClass(TeLoginActivity.this, TeSettingActivity.class);
			// startActivity(intent);
			break;

		case R.id.yanshiImg:
			username_yanshi = "13911111122";
			sf = mContext.getSharedPreferences("basic_info", Context.MODE_PRIVATE);

			Editor editor1 = sf.edit();
			editor1.putBoolean("isNeedVoluntaryLogin", false);
			editor1.putString("userName_yanshi", "13911111122");
			editor1.commit();

			val = new HashMap<String, String>();

			val.put("loginName", "13911111122");
			val.put("password", "888888");
			// val.put("imei", imei);
			// val.put("imsi", imsi);
			val.put("mac", m_strMac);
			val.put("version", Constants.SYS_VERSION);// 系统版本号
			val.put("appVersion", getVersion());// 软件版本号
			val.put("partyId", partyIdEt.getText().toString());
			val.put("mobiletype", "android");
			val.put("product_id", Constants.PRODUCT_ID);
			val.put("attendanceStandardTime", sp.getString("attendanceStandardTime", ""));
			val.put("msgTemplateTime", "");
			val.put("MsgTemplateTypeTime", "");
			try {
				pd = ProgressDialog.show(TeLoginActivity.this, "", "登录中,请稍后", true, false);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			login(val);
			break;
		case R.id.otherLoginImg:
			Intent intent = new Intent(TeLoginActivity.this, OtherLoginActivity.class);
			intent.putExtra("username_other", userEdit.getText().toString());
			startActivity(intent);
			break;
		case R.id.wjma:
			AlertDialog dialog = new AlertDialog.Builder(this).setTitle("请选择方式").setPositiveButton("使用验证码登录", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(TeLoginActivity.this, OtherLoginActivity.class);
					intent.putExtra("username_other", userEdit.getText().toString());
					startActivity(intent);
				}
			}).setNegativeButton("使用邮箱验证", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).create();
			dialog.show();
			break;
		default:
			break;
		}
	};

	// 开始登陆
	private void readLogin() {
		// String partyid = partyEdit.getText().toString();
		username = userEdit.getText().toString();
		password = pwdEdit.getText().toString();

		int ecolor = android.R.color.black; // whatever color you want

		if (partyIdEt.getText().toString() == null || partyIdEt.getText().toString().equals("")) {
			CharSequence html1 = Html.fromHtml("<font color='blue'>企业号不能为空</font>");
			partyIdEt.setError(html1);
			return;
		}
		if (username == null || username.equals("")) {
			CharSequence html1 = Html.fromHtml("<font color='blue'>用户名不能为空</font>");
			userEdit.setError(html1);
			return;
		}
		if (password == null || password.equals("")) {
			CharSequence html1 = Html.fromHtml("<font color='blue'>密码不能为空</font>");
			pwdEdit.setError(html1);
			return;
		}
		Log.i("TeLoginActivity", "init loginUser info...");
		// 保存用户信息
		val = new HashMap<String, String>();
		val.put("loginName", username);
		val.put("password", password);
		// val.put("imei", imei);
		// val.put("imsi", imsi);
		val.put("mac", m_strMac);
		val.put("version", Constants.SYS_VERSION);// 系统版本号
		val.put("appVersion", getVersion());// 软件版本号
		val.put("partyId", partyIdEt.getText().toString());
		val.put("mobiletype", "android");
		val.put("product_id", Constants.PRODUCT_ID);
		val.put("attendanceStandardTime", sp.getString("attendanceStandardTime", ""));
		val.put("msgTemplateTime", "");
		val.put("MsgTemplateTypeTime", "");
		val.put("deviceno", Constants.DEV_ID);
		try {
			pd = ProgressDialog.show(TeLoginActivity.this, "", "登录中,请稍后", true, false);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		login(val);

	}

	View.OnClickListener loginListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			sf = mContext.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
			Editor editor1 = sf.edit();
			editor1.remove("userName_yanshi");
			editor1.commit();

			readLogin();
		}

	};

	String updataType = "";

	// 檢測更新
	private void updata() {
		Constants.ISUPDATE = true;
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		// pd = ProgressDialog.show(TeLoginActivity.this, "", "检测更新...", true,
		// true);
		val = new HashMap<String, String>();
		val.put("productId", Constants.PRODUCT_ID);
		val.put("partyId", sp.getString("partyId", Constants.partyId));
		val.put("appVersion", getVersion());
		val.put("mobileType", "android");
		Constants.TIME_OUT = 5000; // 设置登录超时时间
		try {
			ServerAdaptor.getInstance(this).doAction(1, Constants.UrlHead + "maintain.client.action.UpdateAction$update", val, new ServiceSyncListener() {
				public void onError(ActionResponse returnObject) {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Constants.TIME_OUT = 3000;

				};

				public void onSuccess(ActionResponse returnObject) {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Log.i("onSuccess", "onSuccess");
					Constants.TIME_OUT = 3000;
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					if (null == returnObject) {
						resMessage = "返回数据错误";
						handler.sendEmptyMessage(-1);
						return;
					}

					if (returnObject.getCode() == 6) {
						handler.sendEmptyMessage(6);
						return;
					}
					JSONObject resData = (JSONObject) returnObject.getData();
					try {
						if (!resData.isNull("isUpdate")) {
							int boolIsUpdate = -1;
							boolIsUpdate = new Integer(resData.getString("isUpdate")).intValue();
							switch (boolIsUpdate) {
							case 0:
								// Toast.makeText(getApplicationContext(),
								// "当前为最新版本", Toast.LENGTH_SHORT).show();
								// readLogin();
								break;
							case 1:
								clientPath = resData.getString("clientPath");
								UpdateManager mUpdateManager = new UpdateManager(TeLoginActivity.this, Constants.SERVER_VERSON, clientPath, handler, true);
								mUpdateManager.checkUpdateInfo(7);
								break;
							case 2:
								clientPath = resData.getString("clientPath");
								UpdateManager mUpdateManager_update = new UpdateManager(TeLoginActivity.this, Constants.SERVER_VERSON, clientPath, true);
								mUpdateManager_update.checkUpdateInfo(8);
								break;
							default:
								break;
							}
						} else {
							// Intent intent = new Intent();
							// intent.setClass(TeLoginActivity.this,
							// HaoXinProjectActivity.class);
							// startActivity(intent);
						}

					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
		}
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
						Constants.password = pwdEdit.getText().toString();
						if (!resData.isNull("userId")) {
							editor = sp.edit();
							editor.putString("userID", resData.getString("userId"));
							editor.commit();
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
						if (!resData.isNull("partyId")) {
							Constants.partyId = resData.getString("partyId");
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
						if (!resData.isNull("serverVerson")) {
							// 服务端版本号
							Constants.SERVER_VERSON = resData.getString("serverVerson");
						}
						if (!resData.isNull("menuAuthCode")) {
							Constants.MENU_AUTH_CODE = resData.getString("menuAuthCode");
						}
						if (!resData.isNull("delay")) {
							// 延迟时间（分钟）
							// Constants.DELAY_TIME =
							// resData.getString("delay");
						}
						if (!resData.isNull("locateStatus")) {
							// 定位服务状态
							Constants.LOCATE_STATUS = resData.getString("locateStatus");
						}
						if (!resData.isNull("onWorkTime")) {
							// 上班时间
							Constants.ON_WORK_TIME = resData.getString("onWorkTime");
						}
						if (!resData.isNull("offWorkTime")) {
							// 下班时间
							Constants.OFF_WORK_TIME = resData.getString("offWorkTime");
						}
						// 采用新的推送
						/*
						 * if (!resData.isNull("pushServerAddress")) { //
						 * 推送服务器地址 Constants.PUSH_SERVER_IP = resData
						 * .getString("pushServerAddress"); }
						 */
						editor = sp.edit();
						if (!resData.isNull("onWorkTimeQuantum")) {
							// 保存签到的时间段
							editor.putString("onWorkTimeQuantum", resData.getString("onWorkTimeQuantum"));
						}
						if (!resData.isNull("offWorkTimeQuantum")) {
							// 保存签退的时间段
							editor.putString("offWorkTimeQuantum", resData.getString("offWorkTimeQuantum"));
						}
						if (!resData.isNull("repeats")) {
							// 重复周期(Mon:星期一；Tue:星期二；Web:星期三；Thu:星期四；Fri:星期五；Sat:星期六；Sun:星期日)
							editor.putString("repeats", resData.getString("repeats"));
						}

						// 登录成功，进入主界面，若数据库不存在则创建数据库
						// DatabaseHelper databaseHelper =
						// DatabaseHelper.getInstance(getApplicationContext());
						// workNormDb = new
						// WorkNormDb(getApplicationContext());
						// // 考勤规范
						// if (!resData.isNull("attendanceStandard")) {
						// Gson gson = new Gson();
						// WorkNormBean workNorm =
						// gson.fromJson(resData.getString("attendanceStandard"),
						// new TypeToken<WorkNormBean>() {
						// }.getType());
						// workNormDb.saveWorkNorm(workNorm);
						// }
						// workNormBean = workNormDb.queryWorkNorm();
						// m_strStandardTime =
						// workNormBean.getCreateTime();
						// editor.commit();
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
			// resMessage = e.getMessage();
			// handler.sendEmptyMessage(-1);
			e.printStackTrace();
		}

	}

	/**
	 * 从assets中复制文件
	 * 
	 * @param strOutFileName
	 * @throws IOException
	 */
	private void copyBigDataToSD(String strOutFileName) throws IOException {
		InputStream myInput;
		OutputStream myOutput = new FileOutputStream(strOutFileName);
		myInput = this.getAssets().open("jquerymobile.zip");
		byte[] buffer = new byte[1024];
		int length = myInput.read(buffer);
		while (length > 0) {
			myOutput.write(buffer, 0, length);
			length = myInput.read(buffer);
		}

		myOutput.flush();
		myInput.close();
		myOutput.close();
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
		
				new Thread(new Runnable() {
					public void run() {

						try {
							XmppTool.con = null;
							
							XmppTool.getConnection();
							Log.i("tag", "login"+XmppTool.getConnection().toString());
							// 状态
							Presence presence = new Presence(Presence.Type.available);
							XmppTool.getConnection().sendPacket(presence);

						} catch (Exception e) {
							XmppTool.closeConnection();
						}
					}
				}).start();
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case ITEM_ID_LOGINACTIVITY_SETTING:
			Intent intent = new Intent();
			intent.setClass(TeLoginActivity.this, TeSettingActivity.class);
			startActivity(intent);
			break;
		case ITEM_ID_LOGINACTIVITY_ABOUT:
			intent = new Intent();
			intent.setClass(TeLoginActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(0, ITEM_ID_LOGINACTIVITY_SETTING, 0, "系统设置");
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0 && resultCode == 2) {
			initView();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 授权操作
	public void doAuthorizeAction() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("deviceNo", imei);
		map.put("mobile_type", "android");
		map.put("version", getVersion());
		map.put("dpi", String.valueOf(Function.getWidthPixels(this)) + "*" + Function.getHeightPixels(this));
		map.put("app_id", Constants.APP_ID);
		map.put("company_id", Constants.COMPANY_ID);
		map.put("imsi", imsi);
		map.put("product_id", Constants.PRODUCT_ID);
		map.put("productActiveCode", Constants.MENU_AUTH_CODE);
		map.put("server_version", Constants.SERVER_VERSON);
		JSONObject requestJsonObject = new JSONObject(map);
		AsyncAuthorizeTask task = new AsyncAuthorizeTask(handler, requestJsonObject, TeLoginActivity.this);
		task.execute();
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

	/**
	 * 初始化 TRACE-SDK Interface to accetp a configuration within JSON JSON {
	 * service_api: http://10.2.48.188:9090/UEProb/prob session_timer : 3000L
	 * //milisecond proxy_addr: cmcc.proxy proxy_port: 8080 flag_get_location:
	 * true batch_policy: ReportPolicy.BATCH_AT_TERMINATE upload_policy:
	 * ReportPolicy.ONLY_WIFI }
	 * 
	 * @throws JSONException
	 */

	void InitProb() {
		try {
			JSONObject config = new JSONObject();
			config.put("service_api", "http://trace.hotpotpro.com:8080/TRACEProbeService/accept");
			config.put("proxy_addr", null);
			config.put("upload_policy", ReportPolicy.ANYTIME);
			config.put("batch_policy", ReportPolicy.BATCH_AT_LAUNCH);
			UEProbAgent.InitialConfig(config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// end init prob

}
