package com.xguanjia.hx.payroll.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.viewpagerindicator.TabPageIndicator;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.badges.ABadgeUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AppUtils;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseFragmentActivity;
import com.xguanjia.hx.login.activity.TeLoginActivity;
import com.xguanjia.hx.payroll.adapter.ViewPagerAdapter;
import com.xguanjia.hx.payroll.bean.EveryPayValueBean;
import com.xguanjia.hx.payroll.bean.PayRoll;
import com.xguanjia.hx.payroll.bean.PayRollList;
import com.xguanjia.hx.payroll.db.EveryItemValueDB;
import com.xguanjia.hx.payroll.db.PayRollDb;
import com.xguanjia.hx.payroll.piechart.PieChartActivity;
import com.xguanjia.hx.payroll.piechart.PieChartActivity_year;

/**
 * 收入列表
 * 
 */
public class ViewpagerActivity extends BaseFragmentActivity implements OnClickListener {

	protected ViewPager contentViewPager; // viewpage
	protected TabPageIndicator tabPageIndicator; // ViewPage指示器
	private ImageView rightImg;
	protected View view;
	protected List<String> nameTop = new ArrayList<String>(); // ViewPage标题栏名称
	protected List<Fragment> fragments = new ArrayList<Fragment>();// ViewPage标题栏对应的Fragment
	protected ViewPagerAdapter adapter;
	private ProgressDialog pd;
	private PayRollDb rollDb;
	private int index = 0;
	private String title_str = "";
	private String classes_str = "";

	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		HaoXinProjectActivity.dangqian_view = "2";
		initData();
		initView();
	}

	protected void initView() {
		View view = getLayoutInflater().inflate(R.layout.activity_viewpager, null);
		LayoutParams layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		mainView.addView(view, layoutParams);
		setTitleText("收入");
		if ("2".equals(getIntent().getStringExtra("type"))) {
			setTitleLeftButtonBack("", R.drawable.back_selector, buttonClickListener);
		}
		this.showTitleRightImage(buttonClickListener,R.drawable.zhuzhuangtu);

		rightImg = (ImageView) this.findViewById(R.id.rightImg);
		contentViewPager = (ViewPager) this.findViewById(R.id.pager);
		contentViewPager.setOffscreenPageLimit(0);

		tabPageIndicator = (TabPageIndicator) this.findViewById(R.id.indicator);

		adapter = new ViewPagerAdapter(getSupportFragmentManager());
		adapter.setData(nameTop);
		adapter.setFragments(fragments);

		contentViewPager.setAdapter(adapter);
		tabPageIndicator.setViewPager(contentViewPager);
		if (tabPageIndicator.getMeasuredWidth() > AppUtils.getWidthPixels(this)) {
			rightImg.setVisibility(View.VISIBLE);
		} else {
			rightImg.setVisibility(View.GONE);
		}

		tabPageIndicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				PayrollFragment fragment = (PayrollFragment) adapter.getItem(arg0);
				classes_str = fragment.getPayRollTypeGroup().getUseGroupingId();
				title_str = fragment.getPayRollTypeGroup().getUseGroupingName();

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	protected void initData() {
		rollDb = new PayRollDb(this);
		erveryDB = new EveryItemValueDB(this);
		// 遍历标题栏名称
		for (int i = 0; i < Constants.payRollTypeGroups.getSalaryUseTypes().size(); i++) {
			nameTop.add(Constants.payRollTypeGroups.getSalaryUseTypes().get(i).getUseGroupingName());
		}
		for (int i = 0; i < nameTop.size(); i++) {
			PayrollFragment fragment = new PayrollFragment();
			// 调用回调接口 并绑定对应的Fragment
			addOnActivityResultCallback((OnActivityResultCallback) fragment);
			// 赋值 并初始化PayRollTypeGroup
			fragment.setPayRollTypeBean(Constants.payRollTypeGroups.getSalaryUseTypes().get(i));
			fragments.add(fragment);
		}
		if (Constants.payRollTypeGroups.getSalaryUseTypes().size() != 0) {
			doAsyncJsonAction();
		}
	}

	/**
	 * 回调接口 调用PayrollFragment中setListView()进行数据加载
	 * 
	 */
	public interface OnActivityResultCallback {
		public void onActivityResultCallback(int requestCode, int resultCode, Intent data);
	}

	private List<OnActivityResultCallback> activityResultCallbacks = new ArrayList<OnActivityResultCallback>();

	public void addOnActivityResultCallback(OnActivityResultCallback onActivityResultCallback) {
		if (onActivityResultCallback != null)
			this.activityResultCallbacks.add(onActivityResultCallback);
	}

	public static boolean clear_catch_after = false;

	protected void onResume() {
		super.onResume();
		title_str = "";
		classes_str = "";
		HaoXinProjectActivity.dangqian_view = "2";
		if (sp.getString("userName_yanshi", "").equals("13911111122")) {
			ABadgeUtil.setBadge(ViewpagerActivity.this, 0);
		} else {
			ABadgeUtil.setBadge(this, Constants.unPayrollNum + Constants.unnoticNum);
		}
		if (Constants.payRollRefresh) {
			doAsyncJsonAction();
			Constants.payRollRefresh = false;
		}
		if (clear_catch_after) {
			doAsyncJsonAction();
			clear_catch_after = false;
		}
		UEProbAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	/*
	 * 获取工资单（收入）列表
	 */
	private EveryItemValueDB erveryDB;

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

	private void doAsyncJsonAction() {
		try {

			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("userPhone", Constants.mobile);
			requestMap.put("updateTime", rollDb.time(Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingId()));
			requestMap.put("salaryUseTypeId", "");
			requestMap.put("useGroupingId", Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingId());
			requestMap.put("salaryUseTypeName", Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingName());
			requestMap.put("partyId", Constants.partyId);
			requestMap.put("salaryids", rollDb.getParentIds());
			requestMap.put("version", getVersion());

			ServerAdaptor.getInstance(this).doAction(1, Constants.UrlHead + "client.action.SalaryAction$getSalaryList", requestMap, new ServiceSyncListener() {

				public void onSuccess(ActionResponse returnObject) {

					Gson gson = new Gson();
					try {
						PayRollList payRollBeanListList = gson.fromJson(returnObject.getData().toString(), new TypeToken<PayRollList>() {
						}.getType());
						if (payRollBeanListList.getDeleteids() == null) {
						} else {
							List<Integer> str = payRollBeanListList.getDeleteids();
							rollDb.deleteParenSalarys(str);
						}
						List<PayRoll> payRollBeanLists = payRollBeanListList.getList();

						for (int i = 0; i < payRollBeanLists.size(); i++) {
							if (rollDb.getPayRollListForId(payRollBeanLists.get(i).getPayRollId()).size() == 0) {
								rollDb.savePayRoll(payRollBeanLists.get(i));
								ArrayList<EveryPayValueBean> bean_groupSalaryList = payRollBeanLists.get(i).getGroupSalaryList();
								for (EveryPayValueBean bean : bean_groupSalaryList) {// 为该工资工资ID
									bean.setPayRollId(payRollBeanLists.get(i).getPayRollId());
								}
								if (bean_groupSalaryList.size() > 0) {
									erveryDB.saveToDB(bean_groupSalaryList);
								}
							}
						}

						if (index != Constants.payRollTypeGroups.getSalaryUseTypes().size() - 1) {
							index++;
							doAsyncJsonAction();
						}
						// // 调用其对应fragment
						if (index == Constants.payRollTypeGroups.getSalaryUseTypes().size() - 1) {

							for (int i = 0; i < Constants.payRollTypeGroups.getSalaryUseTypes().size(); i++) {
								activityResultCallbacks.get(i).onActivityResultCallback(0, 0, new Intent());
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				public void onError(ActionResponse returnObject) {

					Toast.makeText(mContext, "网络有问题", Toast.LENGTH_SHORT).show();

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			
			case R.id.title_rightImagepieChart:
				Intent intent = new Intent(ViewpagerActivity.this, IncomeTotalActivity.class);
				intent.putExtra("title_str", title_str);
				intent.putExtra("classes_str", classes_str);
				startActivity(intent);
				break;
			default:
				break;
			}
		};
	};
	private boolean isExit = false;

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (!isExit) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				isExit = true;
				return true;
			} else {

				if (sp.getString("userName_yanshi", "").equals("13911111122")) {
					ABadgeUtil.setBadge(ViewpagerActivity.this, 0);

				} else {
					ABadgeUtil.setBadge(ViewpagerActivity.this, Constants.unPayrollNum + Constants.unnoticNum);
				}

				Editor ed1 = sp.edit();
				ed1.remove("userName_yanshi");
				ed1.commit();
				Intent intent = new Intent(ViewpagerActivity.this, TeLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Bundle bundle = new Bundle();
				bundle.putString("AppExit", "app_exit");
				intent.putExtras(bundle);
				startActivity(intent);
			}

		}
		return false;
	}

}
