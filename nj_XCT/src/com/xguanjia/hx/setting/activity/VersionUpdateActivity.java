package com.xguanjia.hx.setting.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.setting.activity.adapter.VersionUpdateAdapter;
import com.xguanjia.hx.setting.activity.bean.VersionUpdateBean;

public class VersionUpdateActivity extends BaseActivity implements OnItemClickListener{

	private ListView updateListView;
	private VersionUpdateAdapter versionUpdateAdapter;
	private List<VersionUpdateBean> versionUpdateBeans = new ArrayList<VersionUpdateBean>();
	private ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View set_titleView = getLayoutInflater().inflate(
				R.layout.activity_versionupdate, null);
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(set_titleView, layoutParams);
		setTitleText("系统通知");
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		initView();
		getNoticeList();
	}

	private void initView() {
		updateListView = (ListView) this.findViewById(R.id.updateListview);
		updateListView.setOnItemClickListener(this);
		versionUpdateAdapter = new VersionUpdateAdapter(this);
		updateListView.setAdapter(versionUpdateAdapter);
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				VersionUpdateActivity.this.finish();
				break;
			default:
				break;
			}
		};

	};

	private void getNoticeList() {
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		pd = ProgressDialog.show(this, "", "获取中。。", true, true);
		doAsyncJsonAction("", Constants.UrlHead
				+ "client.action.SalaryAction$getRecentNews", requestMap);
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
							versionUpdateBeans = new Gson().fromJson(
									returnObject.getData().toString(),
									new TypeToken<List<VersionUpdateBean>>() {
									}.getType());
							versionUpdateAdapter.setDataChanged(versionUpdateBeans);
						}

						@Override
						public void onError(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							showToast(returnObject.getMessage());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(VersionUpdateActivity.this,WebviewActivity.class);
		intent.putExtra("id", versionUpdateBeans.get(arg2).getId());
		startActivity(intent);
	}

}
