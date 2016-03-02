package com.xguanjia.hx.set.gesture.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.set.gesture.view.NinePointLineView;
import com.xguanjia.hx.set.gesture.view.YanzhengNinePointLineView;
import com.chinamobile.salary.R;

/**
 * 作用：测试九宫格手势密码 作者：unfind 时间：2013年10月29日 09:37:54
 * */
public class SafeSetActivity extends Activity {

	private LinearLayout nine_con;// 九宫格容器

	NinePointLineView nv;// 九宫格View

	TextView showInfo;

	boolean isSetFirst = false;
	private SharedPreferences shareDate;

	private ImageView headImg;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	public DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置标题不显示

		setContentView(R.layout.safeset_activity);

		headImg = (ImageView) this.findViewById(R.id.logo);
		headImg.setVisibility(View.VISIBLE);

		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		int width = metric.widthPixels; // 宽度（PX）
		int height = metric.heightPixels; // 高度（PX）

		NinePointLineView.width = width;
		NinePointLineView.height = (Math.round(height*63))/100;
		nv = new NinePointLineView(SafeSetActivity.this);

		nine_con = (LinearLayout) findViewById(R.id.nine_con);

		nine_con.addView(nv);

		showInfo = (TextView) findViewById(R.id.show_set_info);

		shareDate = getSharedPreferences("basic_info", 0);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.default_avatar1)
				.showImageForEmptyUri(R.drawable.default_avatar1)
				.cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(1000))
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage(shareDate.getString("headImg", ""), headImg,
				options);

		getSetPwd();

	}

	/**
	 * 作用：获取现在密码的设置步骤 作者：unfind 时间：2013年10月29日 14:20:36
	 * */
	public void getSetPwd() {

		shareDate = getSharedPreferences("basic_info", 0);

		isSetFirst = shareDate.getBoolean("IS_SET_FIRST", true);

		if (isSetFirst) {

			showInfo.setText("请设置手势密码");

		} else {

			showInfo.setText("请再次确认手势密码");

		}

		boolean is_second_error = shareDate.getBoolean("SECOND_ERROR", false);

		if (is_second_error) {

			showInfo.setText("和第一次输入手势密码不一致，重新输入");

		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			shareDate.edit().putBoolean("IS_SET_FIRST", true).commit();
			shareDate.edit().putBoolean("SECOND_ERROR", false).commit();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
