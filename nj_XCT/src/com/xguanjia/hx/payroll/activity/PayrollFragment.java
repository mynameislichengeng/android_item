package com.xguanjia.hx.payroll.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.badges.ABadgeUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.RefreshListView;
import com.xguanjia.hx.common.RefreshListView.IOnRefreshListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.payroll.activity.ViewpagerActivity.OnActivityResultCallback;
import com.xguanjia.hx.payroll.adapter.PayrollListAdapter;
import com.xguanjia.hx.payroll.bean.EveryPayValueBean;
import com.xguanjia.hx.payroll.bean.PayRoll;
import com.xguanjia.hx.payroll.bean.PayRollList;
import com.xguanjia.hx.payroll.bean.PayRollTypeGroup;
import com.xguanjia.hx.payroll.db.EveryItemValueDB;
import com.xguanjia.hx.payroll.db.PayRollDb;

/***
 * 列表界面
 * 
 * @author Administrator
 * 
 */
public class PayrollFragment extends Fragment implements IOnRefreshListener, OnItemClickListener, OnActivityResultCallback {
	private RefreshListView listView;
	private ImageView NoDateImg;
	private PayrollListAdapter payrollListAdapter;
	private List<PayRoll> payRollBeanLists;
	private ProgressDialog pd;
	private PayRollDb rollDb;
	private View view;
	private PayRollTypeGroup payRollTypeGroup = new PayRollTypeGroup();

	public void setPayRollTypeBean(PayRollTypeGroup payRollTypeGroup) { // 初始化对象
																		// 赋值
		this.payRollTypeGroup = payRollTypeGroup;
	}
	public PayRollTypeGroup getPayRollTypeGroup(){
		return payRollTypeGroup;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.payrolllist_activity, container, false);
		initView();
		return view;
	}

	private void initView() {
		listView = (RefreshListView) view.findViewById(R.id.payrollist_listview);
		NoDateImg = (ImageView) view.findViewById(R.id.NoDateImg);
		payrollListAdapter = new PayrollListAdapter(getActivity());
		listView.setAdapter(payrollListAdapter);
		listView.setOnRefreshListener(this);
		listView.setOnItemClickListener(this);
		payRollBeanLists = new ArrayList<PayRoll>();
		rollDb = new PayRollDb(getActivity());
		erveryDB = new EveryItemValueDB(getActivity());
	}

	/*
	 * 获取工资单列表
	 */
	private EveryItemValueDB erveryDB;

	public String getVersion() {
		String appVersion = "";
		PackageManager manager = getActivity().getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
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
			requestMap.put("updateTime", rollDb.time(payRollTypeGroup.getUseGroupingId()));
			requestMap.put("salaryUseTypeId", "");
			requestMap.put("useGroupingId", payRollTypeGroup.getUseGroupingId());
			requestMap.put("salaryUseTypeName", payRollTypeGroup.getUseGroupingName());
			requestMap.put("salaryids", rollDb.getParentIds());
			requestMap.put("version", getVersion());

			requestMap.put("partyId", Constants.partyId);
			ServerAdaptor.getInstance(getActivity()).doAction(1, Constants.UrlHead + "client.action.SalaryAction$getSalaryList", requestMap, new ServiceSyncListener() {

				public void onSuccess(ActionResponse returnObject) {

					Gson gson = new Gson();
					try {
						PayRollList payRollBeanListList = gson.fromJson(returnObject.getData().toString(), new TypeToken<PayRollList>() {
						}.getType());
						if (payRollBeanListList.getDeleteids() == null) {

						} else {
							List<Integer> str = payRollBeanListList.getDeleteids();
							rollDb.deleteParenSalarys(str);
							erveryDB.deleteItem(str);
						}
						payRollBeanLists = payRollBeanListList.getList();
						for (int i = 0; i < payRollBeanLists.size(); i++) {
							if (rollDb.getPayRollListForId(payRollBeanLists.get(i).getPayRollId()).size() == 0) {
								// 从网络获取数据存入本地数据库
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
						payRollBeanLists.clear();
						// 清空数据 并从本地读取数据
						setListView();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					// 下拉刷新完成
					listView.onRefreshComplete();
				}

				public void onError(ActionResponse returnObject) {

					setListView();
					listView.onRefreshComplete();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从本地读取数据并更新listview
	 */
	private void setListView() {
		
		payRollBeanLists = rollDb.getListData(payRollTypeGroup.getUseGroupingId());

		for (PayRoll rol : payRollBeanLists) {
			rol.setTu_type(payRollTypeGroup.getUseGroupingId());
		}

		if (!payRollTypeGroup.getUseGroupingId().equals("0")) {

			for (PayRoll rol : payRollBeanLists) {
				ArrayList<EveryPayValueBean> groupSalaryList = new ArrayList<EveryPayValueBean>();
				EveryPayValueBean b = erveryDB.getParentBean(rol.getPayRollId(), Integer.valueOf(payRollTypeGroup.getUseGroupingId()));
				if (b != null) {
					groupSalaryList.add(b);
				}
				rol.setGroupSalaryList(groupSalaryList);
			}
		}

		payrollListAdapter.setDataChanged(payRollBeanLists);
		if (payRollBeanLists.size() == 0) {
			listView.setVisibility(View.VISIBLE);
			NoDateImg.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.VISIBLE);
			NoDateImg.setVisibility(View.GONE);
		}

	}

	/**
	 * 下拉更新方法 重新访问网络获取数据
	 */
	public void OnRefresh() {
		doAsyncJsonAction();
	}

	public void onResume() {
		super.onResume();
		
		
		setListView();
	}

	// 点击跳转至明细
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		Intent intent = new Intent(getActivity(), PayrollActivity.class);
		rollDb.updateInfoById(payRollBeanLists.get(arg2 - 1).getPayRollId());
		payRollBeanLists.get(arg2 - 1).setIsread("1");
		// 更新未读数字
		if (HaoXinProjectActivity.user_yan_frag.equals("13911111122")) {
			ABadgeUtil.setBadge(getActivity(), 0);

		} else {
			Constants.unPayrollNum = rollDb.getUnreadNum();
			ABadgeUtil.setBadge(getActivity(), Constants.unPayrollNum + Constants.unnoticNum);
		}

		intent.putExtra("time", payRollBeanLists.get(arg2 - 1).getPayRollTime());
		intent.putExtra("id", payRollBeanLists.get(arg2 - 1).getPayRollId());

		intent.putExtra("type_flag", payRollTypeGroup.getUseGroupingId());// 用于判断打开的是全部还是单一
		intent.putExtra("salaryUseTypeId", payRollBeanLists.get(arg2 - 1).getSalaryUseTypeId());

		ArrayList<EveryPayValueBean> groupSalaryList = payRollBeanLists.get(arg2 - 1).getGroupSalaryList();

		if (!payRollTypeGroup.getUseGroupingId().equals("0")) {//不是全部
			if (groupSalaryList.size() != 0) {

				String str_1 = "";
				DesUtil desUtil = new DesUtil();
				try {
					str_1 = desUtil.decrypt(groupSalaryList.get(0).getWages(), "haoqeeJSXCT!@#$%^&*");
					intent.putExtra("danyi_value", str_1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else {//当是全部的时候
			intent.putExtra("danyi_value", "no");
		}
		intent.putExtra("title", payRollTypeGroup.getUseGroupingName());
		startActivity(intent);
	}

	/**
	 * 实现回调接口，并重写其方法，调用setListView()方法，
	 */
	@Override
	public void onActivityResultCallback(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		setListView();
	}
}
