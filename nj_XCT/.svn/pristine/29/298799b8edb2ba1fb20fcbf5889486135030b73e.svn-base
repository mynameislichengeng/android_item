package com.xguanjia.hx.setting.activity;


import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;

public class WebviewActivity extends BaseActivity {

	private WebView webView;
	private ProgressDialog pd;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View set_titleView = getLayoutInflater().inflate(
				R.layout.activity_webview, null);
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(set_titleView,layoutParams);
		setTitleText("系统通知");
		setTitleLeftButtonBack("", R.drawable.back_selector, buttonClickListener);
		webView  = (WebView)findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDefaultTextEncodingName("UTF-8");
		getWebView();
	}
	
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				WebviewActivity.this.finish();
				break;
			default:
				break;
			}
		};

	};
	
	
	private void getWebView() {
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("id", getIntent().getStringExtra("id"));
		pd = ProgressDialog.show(this, "", "获取中。。", true, true);
		doAsyncJsonAction("", Constants.UrlHead
				+ "client.action.SalaryAction$getRecentNewsContent", requestMap);
	}

	private void doAsyncJsonAction(String msgWhat, String method,
			HashMap<String, Object> map) {
		try {
			ServerAdaptor.getInstance(this).doAction(1, method, map,
					new ServiceSyncListener() {

						@Override
						public void onSuccess(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							String html="";
							try {
								JSONObject jsonObject=new JSONObject(returnObject.getData().toString());
								if(!jsonObject.isNull("content")){
									html=jsonObject.getString("content");
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							webView.loadData(html, "text/html; charset=UTF-8", null);
//							webView.loadData(html, "text/html", "UTF-8");

						}

						@Override
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

}
