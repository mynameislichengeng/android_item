package com.xguanjia.hx;

import org.apache.cordova.DroidGap;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.FileCacheManager;
import com.xguanjia.hx.plugin.bean.PlugBean;
import com.chinamobile.salary.R;

/**
 * 
 * @author Kirk Zhou
 * 
 */
public class PluginPhoneGapActivity extends DroidGap {
	private PlugBean plugBean;
	private Intent intent;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.init();
		intent = this.getIntent();
		plugBean = (PlugBean) intent.getSerializableExtra("bean");
		// // Set by <content src="index.html" /> in config.xml
		// // super.loadUrl(Config.getStartUrl());
		// // appView.getSettings().setJavaScriptEnabled(true);
		//
		//
		// // appView.addJavascriptInterface(new PluginMethod(this, appView),
		// // "SM"); // 注意这里一句
		//
		super.setIntegerProperty("splashscreen", R.drawable.welcome_bg);
		//
		// //super.loadUrl("file:///android_asset/www/port/index.html", 5000);

		//
		// 判断是否已经存在本地jquerymobile资源包
		boolean flag = FileCacheManager
				.getJqueryMobileExistsSdCard("jquerymobile");
		if (!flag) {
			PluginPhoneGapActivity.this.finish();
			//弹出窗体提示然后返回
			Toast.makeText(PluginPhoneGapActivity.this,
					"初始化插件还未成功，请稍后再试", Toast.LENGTH_SHORT).show();
		} else {
			String filePath = plugBean.getTemplateName();
			// Config.isUrlWhiteListed("*");
			// Config.init(this);
			Log.i("loadUrl", "file://" + Constants.SAVE_PATH + filePath
					+ "/index.html?userId=" + Constants.userId);
			super.loadUrl("file://" + Constants.SAVE_PATH + filePath
					+ "/index.html?userId=" + Constants.userId, 5000);
		}
		// super.loadUrl("file:///storage/sdcard0/lapelgroup/consultCustoner/index.html?userId=143");
		// super.loadUrl("file://android_asset/www/index.html");
	}
}
