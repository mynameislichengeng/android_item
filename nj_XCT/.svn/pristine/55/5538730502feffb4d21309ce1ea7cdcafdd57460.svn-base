package com.xguanjia.hx.set.gesture.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.login.activity.TeLoginActivity;
import com.xguanjia.hx.set.gesture.view.YanzhengNinePointLineView;
import com.xguanjia.hx.set.zhdendong.VibratorUtil;
import com.chinamobile.salary.R;

/**
 * @author jc
 * */
public class YanzhengSafeSetActivity extends Activity implements OnClickListener, OnTouchListener {

	private LinearLayout nine_con;// 九宫格容器

	YanzhengNinePointLineView nv;// 九宫格View
	private ImageView headImg;

	private TextView showInfo, forgetPasswordTv;

	boolean isFirstWrite = false;
	private SharedPreferences shareDate;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置标题不显示

		setContentView(R.layout.safeset_activity_yanzhen);
		headImg = (ImageView) this.findViewById(R.id.logo);
		headImg.setVisibility(View.VISIBLE);

		nine_con = (LinearLayout) findViewById(R.id.nine_con_yz);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		YanzhengNinePointLineView.width = dm.widthPixels;
		YanzhengNinePointLineView.height = (Math.round(dm.heightPixels*63))/100;
		
		nv = new YanzhengNinePointLineView(this);
		nine_con.addView(nv);

		showInfo = (TextView) findViewById(R.id.show_set_info);
		forgetPasswordTv = (TextView) findViewById(R.id.forgetPasswordTv);
		forgetPasswordTv.setOnClickListener(this);
		forgetPasswordTv.setVisibility(View.VISIBLE);

		getSetPwd();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**
	 * 
	 * */
	public void getSetPwd() {

		shareDate = getSharedPreferences("basic_info", 0);

		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_avatar1).showImageForEmptyUri(R.drawable.default_avatar1).cacheInMemory().cacheOnDisc().displayer(new RoundedBitmapDisplayer(1000)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(shareDate.getString("headImg", ""), headImg, options);

		isFirstWrite = shareDate.getBoolean("isFirstWrite", true);

		if (isFirstWrite) {

			showInfo.setText("请输入手势密码");

		} else {
			VibratorUtil.Vibrate(YanzhengSafeSetActivity.this, 1000*2);
			showInfo.setText("密码错误，请重新输入");

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		shareDate.edit().putBoolean("isFirstWrite", true).commit();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.forgetPasswordTv:
			shareDate.edit().putString("password", "").commit();
			shareDate.edit().putBoolean("islogin", false).commit();
			shareDate.edit().putBoolean("isopensafeset", false).commit();
			shareDate.edit().putBoolean("isNeedVoluntaryLogin", false).commit();
			
			Intent intent = new Intent(YanzhengSafeSetActivity.this, TeLoginActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
