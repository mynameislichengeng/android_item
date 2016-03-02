package com.xguanjia.hx.set;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import cmcc.ueprob.agent.UEProbAgent;
import cn.sharesdk.framework.ShareSDK;

import com.chinamobile.salary.R;
import com.haoqee.chat.global.Common;
import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.MCOnlineConfig;
import com.mechat.mechatlibrary.MCUserConfig;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.badges.ABadgeUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DataClearManager;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.feedback.SuggestionFeedbackActivity;
import com.xguanjia.hx.haoxin.db.HaoXinGroupDb;
import com.xguanjia.hx.login.activity.TeLoginActivity;
import com.xguanjia.hx.login.update.UpdateManager;
import com.xguanjia.hx.notice.NoticeActivity;
import com.xguanjia.hx.notice.db.NoticeDb;
//import com.xguanjia.hx.payroll.activity.ExpenseAccountActivity;
import com.xguanjia.hx.payroll.activity.VP_jiekou;
import com.xguanjia.hx.payroll.activity.ViewpagerActivity;
import com.xguanjia.hx.payroll.db.EveryItemValueDB;
import com.xguanjia.hx.payroll.db.PayRollDb;
import com.xguanjia.hx.set.gesture.activity.SafeSetActivity;
import com.xguanjia.hx.setting.activity.AboutActivity;
import com.xguanjia.hx.sharesdk.OnekeyShare;

/**
 * 我的 设置界面
 * 
 * @author Administrator
 * 
 */
public class SetGroupActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = "SetttingGroupActivity";
	private RelativeLayout groupPasswordLayout, personalLayout, set_message_layout, set_about_layout, unSendDataLayout, otherInfoLayout, newVersionLayout, set_quit_layout1, set_yyfx, feedbackLayout, set_group_baoxiao_layout, set_group_notice_layout;
	// 清楚缓存
	private RelativeLayout set_clearCache;

	private TextView versionTv;
	private ImageView versionImg, lineImg;
	private Button set_quit_layout;
	private ImageView personalImage;
	private ImageView anquanImg;
	private HaoXinGroupDb haoXinDb;
	private ProgressDialog pd;
	private Intent intent;
	private View view;
	private SharedPreferences sf, sf1;
	private ToggleButton toggle_AutoPlay;
	private ImageButton toggleButton_AutoPlay;
	private boolean isAutoPlay = false;

	private long exitTime = 0;

	private ImageView tzgg_xhd;
	private NoticeDb noticeDb;

	private BroadcastReceiver tzgg_xhd_Broad = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 显示通知公告小红点
			if (intent.getAction().equals("com.tzgg.xhd")) {
				tzgg_xhd.setVisibility(View.VISIBLE);

			} else if (intent.getAction().equals("com.tzgg.xhd_un")) {
				tzgg_xhd.setVisibility(View.GONE);
			}

		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HaoXinProjectActivity.dangqian_view = "3";
		UEProbAgent.onResume(this);
		String path = haoXinDb.selectAvatar(Constants.userId);
		if (sf.getBoolean("isopensafeset", false)) {
			anquanImg.setImageResource(R.drawable.butn_open);
		} else {
			anquanImg.setImageResource(R.drawable.butn_close);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	private VP_jiekou vp;

	// http://192.168.0.115/hx//uploadFiles/default/headportrait/2013112820160023412.jpg
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		vp = new VP_jiekou(this);
		vp.getNoticeList();
		HaoXinProjectActivity.dangqian_view = "3";
		Log.d(TAG, "in onCreate method");
		sf1 = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		sf = SetGroupActivity.this.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		initViews();
		setListeners();
		// 注册广播，用于接受在通知公告显示小红点
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.tzgg.xhd");
		filter.addAction("com.tzgg.xhd_un");
		registerReceiver(tzgg_xhd_Broad, filter);

	}

	// 初始化设置界面
	private void initViews() {
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		setTitleText("我的");
		view = getLayoutInflater().inflate(R.layout.set_group, null);
		mainView.addView(view, param);
		// 密码管理
		groupPasswordLayout = (RelativeLayout) this.findViewById(R.id.set_group_password_layout);
		groupPasswordLayout.setOnClickListener(this);
		personalLayout = (RelativeLayout) this.findViewById(R.id.set_group_personal_layout);
		personalLayout.setOnClickListener(this);
		set_message_layout = (RelativeLayout) this.findViewById(R.id.set_message_layout);
		set_message_layout.setOnClickListener(this);
		// 关于
		set_about_layout = (RelativeLayout) this.findViewById(R.id.set_about_layout);
		set_about_layout.setOnClickListener(this);
		set_quit_layout = (Button) this.findViewById(R.id.set_quit_layout);
		set_quit_layout.setOnClickListener(this);
		// 基本信息
		otherInfoLayout = (RelativeLayout) this.findViewById(R.id.otherInfoLayout);
		otherInfoLayout.setOnClickListener(this);
		set_quit_layout1 = (RelativeLayout) this.findViewById(R.id.set_quit_layout1);
		set_quit_layout1.setOnClickListener(this);
		// 意见反馈
		feedbackLayout = (RelativeLayout) this.findViewById(R.id.set_yjfk);
		feedbackLayout.setOnClickListener(this);
		// 应用分享
		set_yyfx = (RelativeLayout) this.findViewById(R.id.set_yyfx);
		set_yyfx.setOnClickListener(this);

		set_group_baoxiao_layout = (RelativeLayout) this.findViewById(R.id.set_group_baoxiao_layout);
		set_group_baoxiao_layout.setOnClickListener(this);
		set_group_baoxiao_layout.setVisibility(View.GONE);

		// 清除缓存
		set_clearCache = (RelativeLayout) this.findViewById(R.id.set_clearCache);
		set_clearCache.setOnClickListener(this);

		// 通知公告

		set_group_notice_layout = (RelativeLayout) this.findViewById(R.id.set_group_notice_layout);
		set_group_notice_layout.setOnClickListener(this);
		tzgg_xhd = (ImageView) findViewById(R.id.tzgg_xhd);
		noticeDb = new NoticeDb(this);
		if (noticeDb.getUnnoticeNum() != 0) {
			tzgg_xhd.setVisibility(View.VISIBLE);
		}
		HaoXinProjectActivity.first_enter_setActivity = true;
		if (HaoXinProjectActivity.kaishi) {
			tzgg_xhd.setVisibility(View.GONE);
		}

		// 手势设置
		anquanImg = (ImageView) this.findViewById(R.id.anquanImg);
		anquanImg.setOnClickListener(this);

		// 九宫格
		toggle_AutoPlay = (ToggleButton) findViewById(R.id.toggle_AutoPlay);
		toggleButton_AutoPlay = (ImageButton) findViewById(R.id.toggleButton_AutoPlay);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toggleButton_AutoPlay.getLayoutParams();
		isAutoPlay = sf.getBoolean("isNice", false);
		toggle_AutoPlay.setChecked(isAutoPlay);
		if (isAutoPlay) {
			// 调整位置
			params.addRule(RelativeLayout.ALIGN_RIGHT, -1);
			params.addRule(RelativeLayout.ALIGN_LEFT, R.id.toggle_AutoPlay);
			toggleButton_AutoPlay.setLayoutParams(params);
			toggleButton_AutoPlay.setImageResource(R.drawable.progress_thumb_selector);
			toggle_AutoPlay.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			// isAutoPlay = false;
		} else {
			// 调整位置
			params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.toggle_AutoPlay);
			params.addRule(RelativeLayout.ALIGN_LEFT, -1);
			toggleButton_AutoPlay.setLayoutParams(params);
			toggleButton_AutoPlay.setImageResource(R.drawable.progress_thumb_off_selector);
			toggle_AutoPlay.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			// isAutoPlay = true;
		}
		// 检查更新
		newVersionLayout = (RelativeLayout) this.findViewById(R.id.set_newVersion_layout);
		newVersionLayout.setOnClickListener(this);

		unSendDataLayout = (RelativeLayout) this.findViewById(R.id.application_group_unSendData_layout);
		unSendDataLayout.setOnClickListener(this);
		personalImage = (ImageView) this.findViewById(R.id.setPersonal_image);
		versionTv = (TextView) this.findViewById(R.id.versionTv);
		versionTv.setText("V" + getVersion());
		versionImg = (ImageView) this.findViewById(R.id.newVersionImg);
		lineImg = (ImageView) this.findViewById(R.id.lineImg);
		personalImage.setOnClickListener(this);

		haoXinDb = new HaoXinGroupDb(this);
		String path = Constants.loginBean.getHeadImg();
		sf.edit().putString("headImg", "http://" + sf1.getString("ip", Constants.IP) + path).commit();
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_avatar1).showImageForEmptyUri(R.drawable.default_avatar1).cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(100)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage("http://" + sf1.getString("ip", Constants.IP) + path, personalImage, options);
		if (Constants.loginName.equals("13911111122")) {
			groupPasswordLayout.setVisibility(View.GONE);
			lineImg.setVisibility(View.GONE);
		}
		updata1();

	}

	private void setListeners() {
		toggle_AutoPlay.setOnCheckedChangeListener(new ToggleListener(this, null, toggle_AutoPlay, toggleButton_AutoPlay));

		// UI事件，按钮点击事件
		OnClickListener clickToToggleListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				toggle_AutoPlay.toggle();
			}
		};

		toggleButton_AutoPlay.setOnClickListener(clickToToggleListener);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 返回
		case R.id.title_leftBtn:
			SetGroupActivity.this.finish();
			break;
		// 密码管理
		case R.id.set_group_password_layout:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, SetModifyPwdActivity.class);
			startActivityForResult(intent, Constants.ONE);
			break;
		case R.id.set_group_personal_layout:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, SetPortraitActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;
		case R.id.setPersonal_image:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, SetPortraitActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;
		case R.id.set_message_layout:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, SuggestionFeedbackActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;
		// 关于
		case R.id.set_about_layout:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, AboutActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;
		case R.id.set_quit_layout1:

			showLogoutDialog();

			break;
		// 基本信息
		case R.id.otherInfoLayout:
			intent = new Intent();
			intent.setClass(SetGroupActivity.this, SetOtherInfoActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;
		// 检测版本
		case R.id.set_newVersion_layout:
			updata();
			break;
		// 手势设置
		case R.id.anquanImg:
			Editor editor = sf.edit();
			if (sf.getBoolean("isopensafeset", false)) {
				editor.putBoolean("isopensafeset", false);
				anquanImg.setImageResource(R.drawable.butn_close);
			} else {
				Intent intent = new Intent(SetGroupActivity.this, SafeSetActivity.class);
				startActivity(intent);
			}
			editor.commit();
			break;
		// 意见反馈
		case R.id.set_yjfk:
			MCOnlineConfig onlineConfig = new MCOnlineConfig();
			//onlineConfig.setChannel("channel"); // 璁剧疆娓犻亾
			// onlineConfig.setSpecifyAgent("4840", false); // 璁剧疆鎸囧畾瀹㈡湇
			// onlineConfig.setSpecifyGroup("1"); // 璁剧疆鎸囧畾鍒嗙粍

			// 鏇存柊鐢ㄦ埛淇℃伅锛屽彲閫�. 
			// 璇︾粏淇℃伅鍙互鍒版枃妗ｄ腑鏌ョ湅锛歨ttps://meiqia.com/docs/sdk/android.html
			MCUserConfig mcUserConfig = new MCUserConfig();
			Map<String,String> userInfo = new HashMap<String,String>();
			userInfo.put(MCUserConfig.PersonalInfo.REAL_NAME,Constants.userName);
			userInfo.put(MCUserConfig.Contact.TEL,Constants.mobile);
			Map<String,String> userInfoExtra = new HashMap<String,String>();
			
			mcUserConfig.setUserInfo(SetGroupActivity.this,userInfo,userInfoExtra,null);
			
			// 鍚姩瀹㈡湇瀵硅瘽鐣岄潰
			MCClient.getInstance().startMCConversationActivity(onlineConfig);

//			intent = new Intent();
//			intent.setClass(SetGroupActivity.this, FeedBackActivity.class);
//			startActivity(intent);
			break;
		// 应用分享
		case R.id.set_yyfx:
			showShare();
			break;
		// 报销
		case R.id.set_group_baoxiao_layout:
//			intent = new Intent(SetGroupActivity.this, ExpenseAccountActivity.class);
//			startActivity(intent);
			break;
		// 通知公告
		case R.id.set_group_notice_layout:
			tzgg_xhd.setVisibility(View.GONE);
			sendBroadcast(new Intent("com.home.tzgg.xhd_un"));
			intent = new Intent(SetGroupActivity.this, NoticeActivity.class);
			startActivity(intent);
			break;
		// 清楚缓存
		case R.id.set_clearCache:
			new AlertDialog.Builder(SetGroupActivity.this).setTitle("提示").setMessage("确定清除缓存吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DataClearManager.cleanApplicationData(SetGroupActivity.this, "");
					// clearCache();
					ViewpagerActivity.clear_catch_after = true;

				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).show();

			break;

		default:
			break;
		}
	}


	private PayRollDb mPayRollDb;
	private EveryItemValueDB mEv;

	private void clearCache() {
		mPayRollDb = new PayRollDb(this);
		mPayRollDb.deleteData();
		mEv = new EveryItemValueDB(this);
		mEv.deleteData();
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("HI，我正在使用薪酬通客户端，工资详情、五险一金、工资曲线，第一时间尽在掌握");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://112.4.17.115:8090/appcms/download");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("HI，我正在使用薪酬通客户端，工资详情、五险一金、工资曲线，第一时间尽在掌握。下载链接： http://112.4.17.115:8090/appcms/download");
		oks.setImageUrl("http://" + Constants.UPDATE_IP + "/fileUpload/Mclient.png");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://112.4.17.115:8090/appcms/download");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("评论");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://112.4.17.115:8090/appcms/download");

		// 启动分享GUI
		oks.show(this);
	}

	// 登出
	void loginOut() {
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", Constants.userId);
		requestMap.put("type", "loginOut");
		pd = ProgressDialog.show(this, "", "正在注销，请稍后...", true, true);
		try {
			ServerAdaptor.getInstance(this).doAction(1, "LoginAction$loginOut", requestMap, new ServiceSyncListener() {

				@Override
				public void onError(ActionResponse returnObject) {
					super.onError(returnObject);
					Log.e(TAG, "登出失败");
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					// Toast.makeText(SetGroupActivity.this, "注销失败", 0)
					// .show();
				}

				@Override
				public void onSuccess(ActionResponse returnObject) {
					Log.e(TAG, "登出成功");
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}
	}

	void loginOutSuccess() {
		sf = SetGroupActivity.this.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		DatabaseHelper dbOpenHelper = DatabaseHelper.getInstance(SetGroupActivity.this);
		dbOpenHelper.closeDB();
		Editor editor1 = sf.edit();
		editor1.putBoolean("islogin", false);
		editor1.putBoolean("isNeedVoluntaryLogin", false);
		editor1.putBoolean("isopensafeset", false);
		editor1.putString("username", null);
		editor1.putString("password", null);

		editor1.putString("userID", "");
		editor1.remove("userName_yanshi");
		editor1.commit();

		SharedPreferences preferences = mContext.getSharedPreferences(Common.LOGIN_SHARED, 0);
		Editor editor = preferences.edit();
		editor.remove(Common.LOGIN_RESULT);
		editor.commit();
		Common.setUid("");
		Common.setToken("");
//		TCChatManager.getInstance().logoutXmpp();
		sendBroadcast(new Intent(HaoXinProjectActivity.ACTION_LOGIN_OUT));
		// intent = new Intent();
		// intent.setClass(SetGroupActivity.this, TeLoginActivity.class);
		// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// startActivity(intent);
		// finish();
	}

	// @Overrides
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 密码修改成功
		if (requestCode == Constants.ONE && resultCode == Constants.TWO) {
			// setResult(Constants.SIX);
			// SetGroupActivity.this.finish();
			// Intent intent = new Intent(SetGroupActivity.this,
			// TeLoginActivity.class);
			// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// startActivity(intent);
			loginOutSuccess();
		}

		if (requestCode == Constants.TWO && resultCode == Constants.SIX) {
			String path = Constants.SAVE_IMAGE_PATH + "HeadAvatar/" + haoXinDb.selectAvatar(Constants.userId);
			String headPhotoPath = data.getStringExtra("headPhotoPath");
			File file = new File(path);
			// if (file.exists()) {

			imageLoader.displayImage("http://" + sf1.getString("ip", Constants.IP) + headPhotoPath, personalImage, options);
			sf.edit().putString("headImg", "http://" + sf1.getString("ip", Constants.IP) + headPhotoPath).commit();
			// personalImage.setImageBitmap(AppUtils.getBitmapFromPath(path));
			// }
		}
	}

	// 檢測更新
	private void updata() {
		Constants.ISUPDATE = true;
		pd = ProgressDialog.show(SetGroupActivity.this, "", "检测更新...", true, true);
		HashMap<String, String> val = new HashMap<String, String>();
		val.put("productId", Constants.PRODUCT_ID);
		val.put("partyId", Constants.partyId);
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
					Constants.TIME_OUT = 3000;
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					JSONObject resData = (JSONObject) returnObject.getData();
					try {
						if (!resData.isNull("isUpdate")) {
							int boolIsUpdate = -1;
							boolIsUpdate = new Integer(resData.getString("isUpdate")).intValue();
							switch (boolIsUpdate) {
							case 0:
								Toast.makeText(getApplicationContext(), "当前为最新版本", Toast.LENGTH_SHORT).show();
								break;
							case 1:
								String clientPath = resData.getString("clientPath");
								UpdateManager mUpdateManager = new UpdateManager(SetGroupActivity.this, Constants.SERVER_VERSON, clientPath, false);
								mUpdateManager.checkUpdateInfo(7);
								break;
							case 2:
								clientPath = resData.getString("clientPath");
								UpdateManager mUpdateManager_update = new UpdateManager(SetGroupActivity.this, Constants.SERVER_VERSON, clientPath, false);
								mUpdateManager_update.checkUpdateInfo(8);
								break;
							default:
								break;
							}
						} else {
							Toast.makeText(getApplicationContext(), "当前为最新版本", Toast.LENGTH_SHORT).show();
						}

					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
		}
	}

	// 檢測更新
	private void updata1() {
		Constants.ISUPDATE = true;
		HashMap<String, String> val = new HashMap<String, String>();
		val.put("productId", Constants.PRODUCT_ID);
		val.put("partyId", Constants.partyId);
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
					Constants.TIME_OUT = 3000;
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					JSONObject resData = (JSONObject) returnObject.getData();
					try {
						if (!resData.isNull("isUpdate")) {
							int boolIsUpdate = -1;
							boolIsUpdate = new Integer(resData.getString("isUpdate")).intValue();
							switch (boolIsUpdate) {
							case 0:
								// Toast.makeText(getApplicationContext(),
								// "当前为最新版本", Toast.LENGTH_SHORT)
								// .show();
								versionImg.setVisibility(View.GONE);
								break;
							case 1:
								versionImg.setVisibility(View.VISIBLE);
								break;
							case 2:
								versionImg.setVisibility(View.VISIBLE);
								break;
							default:
								break;
							}
						} else {
							// Toast.makeText(getApplicationContext(),
							// "当前为最新版本", Toast.LENGTH_SHORT)
							// .show();
						}

					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(TAG, "触发onKeyDown方法。。。。。。。。。。。。");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
				return true;
			} else {
				if (sp.getString("userName_yanshi", "").equals("13911111122")) {
					ABadgeUtil.setBadge(SetGroupActivity.this, 0);

				} else {
					ABadgeUtil.setBadge(SetGroupActivity.this, Constants.unPayrollNum + Constants.unnoticNum);
				}

				Intent intent = new Intent(SetGroupActivity.this, TeLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Bundle bundle = new Bundle();
				bundle.putString("AppExit", "app_exit");
				intent.putExtras(bundle);
				startActivity(intent);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	// 退出登录对话框
	private void showLogoutDialog() {

		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button cameraBtn = (Button) layout.findViewById(R.id.camera);
		final Button galleryBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);

		String promptText = mContext.getString(R.string.confirm_to_logout);
		cameraBtn.setText(promptText);
		cameraBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		cameraBtn.setTextColor(mContext.getResources().getColor(R.color.content_gray_color));
		cameraBtn.setEnabled(false);

		galleryBtn.setTextColor(Color.RED);
		galleryBtn.setText(mContext.getString(R.string.exit));

		cancelBtn.setText(mContext.getString(R.string.cancel));

		galleryBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				loginOutSuccess();
				loginOut();
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

}
