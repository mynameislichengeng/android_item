package com.xguanjia.hx.payroll.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import cmcc.ueprob.agent.UEProbAgent;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.MeasuredGridView;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.payroll.adapter.AttachmentAdapter;
import com.xguanjia.hx.payroll.bean.ExpenseAccountBean;
import com.chinamobile.salary.R;

public class ExpenseAccountDetailActivity extends BaseActivity implements OnItemClickListener{

	private TextView titleTv, moneyTv, detailTv;
	private TextView typeTv, postionTv, timeTv,commentTv;
	private LinearLayout linear,commentLinear;
	private ImageView img,commentImg;
	private ExpenseAccountBean expenseAccountBean;

	// 附件
	private MeasuredGridView gvImgs;
	private AttachmentAdapter attachmentAdapter;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		View view = getLayoutInflater().inflate(
				R.layout.expenseaccountdetail_activity, null);
		LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		mainView.addView(view, layoutParams);
		setTitleText("报销详情");
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		titleTv = (TextView) view.findViewById(R.id.titleTv);
		moneyTv = (TextView) view.findViewById(R.id.moneyTv);
		detailTv = (TextView) view.findViewById(R.id.detailTv);
		typeTv = (TextView) view.findViewById(R.id.typeTv);
		postionTv = (TextView) view.findViewById(R.id.postionTv);
		timeTv = (TextView) view.findViewById(R.id.timeTv);
		commentTv=(TextView)view.findViewById(R.id.commentTv);
		
		linear=(LinearLayout)view.findViewById(R.id.linear);
		img=(ImageView)view.findViewById(R.id.img);
		commentLinear=(LinearLayout)view.findViewById(R.id.commentLinear);
		commentImg=(ImageView)view.findViewById(R.id.commentImg);

		gvImgs = (MeasuredGridView) view.findViewById(R.id.gvImgs);
		gvImgs.setSelector(new ColorDrawable(Color.TRANSPARENT));
		gvImgs.setOnItemClickListener(this);
		attachmentAdapter = new AttachmentAdapter(this);
		gvImgs.setAdapter(attachmentAdapter);
		initDate();
	}

	private void initDate() {
		expenseAccountBean=(ExpenseAccountBean)getIntent().getSerializableExtra("expenseAccountBean");
		titleTv.setText(expenseAccountBean.getReimburseName());
		if("1".equals(expenseAccountBean.getReimburseType())){
			typeTv.setText("交通");
		}else if("2".equals(expenseAccountBean.getReimburseType())){
			typeTv.setText("住宿");
		}else if("3".equals(expenseAccountBean.getReimburseType())){
			typeTv.setText("餐饮");
		}else if("4".equals(expenseAccountBean.getReimburseType())){
			typeTv.setText("办公");
		}else if("5".equals(expenseAccountBean.getReimburseType())){
			typeTv.setText("其他");
		}
		postionTv.setText(expenseAccountBean.getLocation());
		timeTv.setText(expenseAccountBean.getReimburseDate());
		moneyTv.setText(expenseAccountBean.getExpenseAmount());
		detailTv.setText(expenseAccountBean.getExpenseContent());
		if("1".equals(expenseAccountBean.getTypeState())){
			commentLinear.setVisibility(View.VISIBLE);
			commentImg.setVisibility(View.VISIBLE);
		}else {
			commentLinear.setVisibility(View.GONE);
			commentImg.setVisibility(View.GONE);
		}
		commentTv.setText("".equals(expenseAccountBean.getAuditContent())?"暂无审批意见":expenseAccountBean.getAuditContent());
		if(expenseAccountBean.getFiles().size()!=0){
			attachmentAdapter.dataNotifyChange(expenseAccountBean.getFiles());
		}else {
			linear.setVisibility(View.GONE);
			img.setVisibility(View.GONE);
		}
	}


	/**
	 * 点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				finish();
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		SharedPreferences sf = getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Intent intent=new Intent(this,PayRollImageDetailActivity.class);
		intent.putExtra("path", "http://" + sf.getString("ip", Constants.IP)
				+ "/attachFiles/"+expenseAccountBean.getFiles().get(arg2).getFilePath());
		startActivity(intent);
	}
}
