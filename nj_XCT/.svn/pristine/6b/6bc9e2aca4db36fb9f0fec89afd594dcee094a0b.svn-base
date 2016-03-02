package com.xguanjia.hx.setting.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.login.welcome.MoaWelcomeActivity;
/**
 *   我的   关于界面
 * @author Administrator
 *
 */
public class AboutActivity extends BaseActivity {
	private static final String TAG = "AboutActivity";
	private ImageView suggestionResponseIv;
	private TextView contactPhoneNumTv, qqTv, mailboxTv, contactAddressTv, appVersionTextView;
	private RelativeLayout welcomeRl,introTeamRl,versionUpdateRl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		initViewData();
	}

	private void initViewData() {
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		View mianView = getLayoutInflater().inflate(R.layout.activity_about, null);
		mainView.addView(mianView, layoutParams);
		welcomeRl=(RelativeLayout)this.findViewById(R.id.welcomeRl);
		welcomeRl.setOnClickListener(buttonClickListener);
		introTeamRl=(RelativeLayout)this.findViewById(R.id.introTeamRl);
		introTeamRl.setOnClickListener(buttonClickListener);
		versionUpdateRl=(RelativeLayout)this.findViewById(R.id.versionUpdateRl);
		versionUpdateRl.setOnClickListener(buttonClickListener);
		
		introTeamRl=(RelativeLayout)this.findViewById(R.id.introTeamRl);
		introTeamRl.setOnClickListener(buttonClickListener);
		contactPhoneNumTv = (TextView) this.findViewById(R.id.contactPhoneNumTv);
		contactPhoneNumTv.setText("http://www.js.chinamobile.com");
		qqTv = (TextView) this.findViewById(R.id.qqTv);
		qqTv.setText("025-68564292");
		mailboxTv = (TextView) this.findViewById(R.id.mailboxTv);
		mailboxTv.setText("4001129580");
		contactAddressTv = (TextView) this.findViewById(R.id.contactAddressTv);
		contactAddressTv.setText("常州市天宁区武青北路5号302");
		setTitleLeftButtonBack("", R.drawable.back_selector, buttonClickListener);
		suggestionResponseIv = (ImageView) this.findViewById(R.id.suggestionResponseIv);
		suggestionResponseIv.setOnClickListener(buttonClickListener);
		setTitleText("关于");
		appVersionTextView = (TextView) this.findViewById(R.id.appVersionTextView);
		appVersionTextView.setText("V" + getVersion());
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				AboutActivity.this.finish();
				break;
			case R.id.welcomeRl:
				Intent intent=new Intent(AboutActivity.this,MoaWelcomeActivity.class);
				intent.putExtra("type", "2");
				startActivity(intent);
				break;
			//团队介绍	
			case R.id.introTeamRl:
				intent=new Intent(AboutActivity.this,IntroTeamActivity.class);
				startActivity(intent);
				break;
			//版本更新
			case R.id.versionUpdateRl:
				intent=new Intent(AboutActivity.this,VersionUpdateActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		};

	};
	
	protected void onResume() {
		super.onResume();
		UEProbAgent.onResume(this);
	};
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
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
}
