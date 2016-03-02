//package com.xguanjia.hx.payroll.activity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.RadioGroup.OnCheckedChangeListener;
//import android.widget.RelativeLayout.LayoutParams;
//import cmcc.ueprob.agent.UEProbAgent;
//
//import com.chinamobile.salary.R;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.xguanjia.hx.common.ActionResponse;
//import com.xguanjia.hx.common.Constants;
//import com.xguanjia.hx.common.MOAOnClickListener;
//import com.xguanjia.hx.common.RefreshListView;
//import com.xguanjia.hx.common.RefreshListView.IOnRefreshListener;
//import com.xguanjia.hx.common.ServerAdaptor;
//import com.xguanjia.hx.common.ServiceSyncListener;
//import com.xguanjia.hx.common.activity.BaseActivity;
//import com.xguanjia.hx.payroll.adapter.ExpenseAccountAdapter;
//import com.xguanjia.hx.payroll.bean.ExpenseAccountReimburseBean;
//
///**
// * 报销上报  审批界面
// *
// */
//public class ExpenseAccountActivity extends BaseActivity implements IOnRefreshListener, OnItemClickListener ,OnCheckedChangeListener{
//	private RefreshListView listView;
//	private ImageView NoDateImg;
//	private ExpenseAccountAdapter expenseAccountAdapter;
//	private List<ExpenseAccountReimburseBean> expenseAccountReimburseBeans,wbxexpAccountReimburseBeans;
//	private ProgressDialog pd;
//	private RadioGroup topRadioGroup;
//	private RadioButton radioButton, radioButton2;
//	private String flag="1";//1全部报销2未报销
//
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		initView();
//	}
//	
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		UEProbAgent.onResume(this);
//	}
//	
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		UEProbAgent.onPause(this);
//	}
//
//	private void initView() {
//		View view = getLayoutInflater()
//				.inflate(R.layout.myagent_activity, null);
//		LayoutParams layoutParams = new LayoutParams(
//				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
//				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
//		mainView.addView(view, layoutParams);
////		setTitleText("报销");
//		setTitleLeftButtonBack("", R.drawable.back_selector,buttonClickListener);
//		setTitleRightButton("上报", 0, buttonClickListener);
//		NoDateImg=(ImageView)view.findViewById(R.id.NoDateImg);
//		topRadioGroup = (RadioGroup) titleView.findViewById(R.id.radiogroup);
//		topRadioGroup.setVisibility(View.VISIBLE);
//		topRadioGroup.setOnCheckedChangeListener(this);
//		radioButton = (RadioButton) titleView.findViewById(R.id.gzrz_radio);
//		radioButton2 = (RadioButton) titleView.findViewById(R.id.grrz_radio);
//		listView = (RefreshListView) view.findViewById(R.id.populrize_listview);
//		expenseAccountAdapter = new ExpenseAccountAdapter(this);
//		listView.setAdapter(expenseAccountAdapter);
//		listView.setOnRefreshListener(this);
//		listView.setOnItemClickListener(this);
//		expenseAccountReimburseBeans=new ArrayList<ExpenseAccountReimburseBean>();
//		wbxexpAccountReimburseBeans=new ArrayList<ExpenseAccountReimburseBean>();
//		doAsyncJsonAction();
//	}
//
//	/*
//	 * 报销单列表
//	 */
//
//	private void doAsyncJsonAction() {
//		try {
//			pd = ProgressDialog.show(ExpenseAccountActivity.this, "", "报销单获取中。。。",
//					true, true);
//			HashMap<String, Object> requestMap = new HashMap<String, Object>();
//			requestMap.put("userId", Constants.userId);
//			requestMap.put("partyId", Constants.partyId);
//			ServerAdaptor.getInstance(this).doAction(
//					1,
//					Constants.UrlHead
//							+ "client.action.ReimburseAction$getReimburseList",
//					requestMap, new ServiceSyncListener() {
//
//						public void onSuccess(ActionResponse returnObject) {
//							// TODO Auto-generated method stub
//							if (pd != null && pd.isShowing()) {
//								pd.dismiss();
//							}
//							Gson gson = new Gson();
//							expenseAccountReimburseBeans = gson.fromJson(returnObject.getData()
//									.toString(),
//									new TypeToken<List<ExpenseAccountReimburseBean>>() {
//									}.getType());
//							wbxexpAccountReimburseBeans.clear();
//							for(int i=0;i<expenseAccountReimburseBeans.size();i++){
//								if(expenseAccountReimburseBeans.get(i).getReimburse().getTypeState().equals("1")){
//									
//								}else if(expenseAccountReimburseBeans.get(i).getReimburse().getTypeState().equals("2")){
//									wbxexpAccountReimburseBeans.add(expenseAccountReimburseBeans.get(i));
//								}
//							}
//							if(flag.equals("1")){
//								expenseAccountAdapter.setDataChanged(expenseAccountReimburseBeans);
//								setIsDate(expenseAccountReimburseBeans);
//							}else {
//								expenseAccountAdapter.setDataChanged(wbxexpAccountReimburseBeans);
//								setIsDate(wbxexpAccountReimburseBeans);
//							}
//							listView.onRefreshComplete();
//						}
//
//						public void onError(ActionResponse returnObject) {
//							// TODO Auto-generated method stub
//							if (pd != null && pd.isShowing()) {
//								pd.dismiss();
//							}
//							listView.onRefreshComplete();
//						}
//					});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 点击事件
//	 */
//	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.title_leftBtnBack:
//				finish();
//				break;
//			case R.id.title_rightBtn:
//				Intent intent = new Intent(ExpenseAccountActivity.this,
//						ExpenseAccountApplicationActivity.class);
//				startActivityForResult(intent, 0);
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	//上报成功刷新列表
//	public void OnRefresh() {
//		// TODO Auto-generated method stub
//		doAsyncJsonAction();
//	}
//	
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		if(requestCode==0&&resultCode==1){
//			doAsyncJsonAction();
//		}
//	}
//	
//	@Override
//	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//		// TODO Auto-generated method stub
//		Intent intent = new Intent(ExpenseAccountActivity.this,
//				ExpenseAccountDetailActivity.class);
//		intent.putExtra("expenseAccountBean", expenseAccountReimburseBeans.get(arg2 - 1).getReimburse());
//		startActivity(intent);
//	}
//	
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		// TODO Auto-generated method stub
//		switch (checkedId) {
//		//已报销
//		case R.id.gzrz_radio:
//			radioButton.setChecked(true);
//			radioButton2.setChecked(false);
//			expenseAccountAdapter.setDataChanged(expenseAccountReimburseBeans);
//			setIsDate(expenseAccountReimburseBeans);
//			flag="1";
//			break;
//		//未报销
//		case R.id.grrz_radio:
//			radioButton.setChecked(false);
//			radioButton2.setChecked(true);
//			expenseAccountAdapter.setDataChanged(wbxexpAccountReimburseBeans);
//			setIsDate(wbxexpAccountReimburseBeans);
//			flag="2";
//			break;
//
//		default:
//			break;
//		}
//	}
//	
//	private void setIsDate(List<ExpenseAccountReimburseBean> expenseAccountReimburseBeans){
//		if(expenseAccountReimburseBeans.size()==0){
//			listView.setVisibility(View.GONE);
//			NoDateImg.setVisibility(View.VISIBLE);
//			
//		}else {
//			listView.setVisibility(View.VISIBLE);
//			NoDateImg.setVisibility(View.GONE);
//		}
//	}
//}
