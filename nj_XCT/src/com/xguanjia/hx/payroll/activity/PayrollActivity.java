package com.xguanjia.hx.payroll.activity;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.tools.ant.taskdefs.Classloader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.common.selecttime.JudgeDate;
import com.xguanjia.hx.common.selecttime.ScreenInfo;
import com.xguanjia.hx.common.selecttime.WheelMain;
import com.xguanjia.hx.lc.userdefined.MyListView;
import com.xguanjia.hx.payroll.adapter.Item_ditaiAdapter;
import com.xguanjia.hx.payroll.bean.DataListBean;
import com.xguanjia.hx.payroll.bean.GroupListBean;
import com.xguanjia.hx.payroll.bean.ItemBean_grouplist;
import com.xguanjia.hx.payroll.bean.ItemBean_templist;
import com.xguanjia.hx.payroll.bean.PayRollSummary;
import com.xguanjia.hx.payroll.bean.Value_ItemBean;
import com.xguanjia.hx.payroll.piechart.PieChartActivity;

/***
 * 工资明细界面
 * 
 * @author Administrator
 * 
 */
public class PayrollActivity extends BaseActivity {

	private TextView dataTopTv, lastMonthTv, allwagesTv;
	private LinearLayout detailLinear;
	private ImageView noDetailImg;
	private ProgressDialog pd;// 提示框
	private PayRollSummary payRollBean = new PayRollSummary();
	private LinearLayout.LayoutParams params;
	private String flag = "1";// 判断groupListBeans里面是否有对应的值 1不存在 2存在
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	private String selectTime = "";
	private String style = "1";

	private GroupListBean groupListBean;

	private List<GroupListBean> listGroupListBean;

	private DataListBean listBean;
	private ArrayList<Value_ItemBean> valueList_Bean;
	private ArrayList<ItemBean_grouplist> grouplist_bean;
	private ArrayList<ItemBean_templist> templist_bean;

	// handle加载数据
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				String returnString = (String) msg.obj;
				Gson gson = new Gson();
				listBean = gson.fromJson(returnString, new TypeToken<DataListBean>() {
				}.getType());
				valueList_Bean = listBean.getList();
				grouplist_bean = listBean.getGrouplist();
				templist_bean = listBean.getTemplist();
				dataDispaly();
				break;
			case 2:
				String returnMessage = (String) msg.obj;
				Toast.makeText(PayrollActivity.this, returnMessage, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	public void dataDispaly() {
		if (type_flag.equals("0")) {// 当为全部时
			
			String decodeAllWages = null;
			try {
				DesUtil desUtil = new DesUtil();
				if (listBean.getAllwages().length() > 10) {
					decodeAllWages = desUtil.decrypt(listBean.getAllwages(), "haoqeeJSXCT!@#$%^&*");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			allwagesTv.setText(decodeAllWages);

			HashMap<String, String> dq_class_zx = new HashMap<String, String>();
			for (int i = 0; i < templist_bean.size(); i++) {// 得到当前工资单所拥有的种类
				String str = templist_bean.get(i).getSalaryGroupId();
				if (!str.equals("") && str != null) {
					dq_class_zx.put(str, str);
				}

			}
	

			for (int k = 0; k < grouplist_bean.size(); k++) {// 遍历每一个大类
				ItemBean_grouplist temp_group = grouplist_bean.get(k);
				if (dq_class_zx.containsKey(temp_group.getSalaryGroupId())) {// 将每一个工资单中对应的大类的每一个项，遍历出来
					MyListView lv = new MyListView(PayrollActivity.this);
					LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					lv.setLayoutParams(layoutParams);
					ArrayList<ItemBean_templist> itemList_temp = new ArrayList<ItemBean_templist>();
					for (int j = 0; j < templist_bean.size(); j++) {
						if (temp_group.getSalaryGroupId().equals(templist_bean.get(j).getSalaryGroupId())) {
							itemList_temp.add(templist_bean.get(j));
						}
					}
					Item_ditaiAdapter adapter_temp = new Item_ditaiAdapter(PayrollActivity.this, valueList_Bean, itemList_temp, temp_group);
					lv.setAdapter(adapter_temp);
					detailLinear.addView(lv, params);

				}

			}

		} else {// 当为部分时
			
			for (int k = 0; k < grouplist_bean.size(); k++) {// 遍历每一个大类
				ItemBean_grouplist temp_group = grouplist_bean.get(k);
				if (type_flag.equals(temp_group.getSalaryGroupId())) {// 将每一个工资单中对应的大类的每一个项，遍历出来
					MyListView lv = new MyListView(PayrollActivity.this);
					LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					lv.setLayoutParams(layoutParams);
					ArrayList<ItemBean_templist> itemList_temp = new ArrayList<ItemBean_templist>();
					for (int j = 0; j < templist_bean.size(); j++) {
						if (temp_group.getSalaryGroupId().equals(templist_bean.get(j).getSalaryGroupId())) {
							itemList_temp.add(templist_bean.get(j));

						}
					}
					Item_ditaiAdapter adapter_temp = new Item_ditaiAdapter(PayrollActivity.this, valueList_Bean, itemList_temp, temp_group);
					lv.setAdapter(adapter_temp);
					detailLinear.addView(lv, params);

					try {
						allwagesTv.setText(danyi_value);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}

			}

		}

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}
	TextView tv_xj;
	// 初始化界面
	private void initView() {
		View view = getLayoutInflater().inflate(R.layout.payroll_activity, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(view, layoutParams);
		
		this.setTitleLeftButtonBack("", R.drawable.back_selector, this);
		dataTopTv = (TextView) view.findViewById(R.id.payrolldateTv);
		dataTopTv.setOnClickListener(this);
		lastMonthTv = (TextView) view.findViewById(R.id.lastmonthTv);
		allwagesTv = (TextView) view.findViewById(R.id.allwagesTv);
		detailLinear = (LinearLayout) view.findViewById(R.id.llDetial);
		noDetailImg = (ImageView) view.findViewById(R.id.noDetailImg);
		tv_xj = (TextView) findViewById(R.id.text);
		initDate();
	}

	private String type_flag = "";
	private String danyi_value = "";
	private String title_temp ="";
	private void initDate() {
		
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		dataTopTv.setText(getIntent().getStringExtra("time") + " 收入明细");
		selectTime = getIntent().getStringExtra("time");
		type_flag = getIntent().getStringExtra("type_flag");
		danyi_value = getIntent().getStringExtra("danyi_value");
		title_temp = getIntent().getStringExtra("title");
		
		if(type_flag.equals("0")){
			this.setTitleText("收入");
			this.showTitleRightImage(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(PayrollActivity.this, PieChartActivity.class);
					intent.putExtra("salaryid", getIntent().getStringExtra("id"));
					startActivity(intent);
					
				}
			});
		}else {
			this.setTitleText(title_temp);
			tv_xj.setText("小计");
		}
		
		getAccessPost(getIntent().getStringExtra("time"));
	}

	private void getAccessPost(String time) {
		HashMap<String, String> getAccessPost = new HashMap<String, String>();
		getAccessPost.put("date", time);
		getAccessPost.put("userPhone", Constants.mobile);
		getAccessPost.put("id", getIntent().getStringExtra("id"));
		getAccessPost.put("partyId", Constants.partyId);

		getAccessPost.put("salaryUseTypeId", getIntent().getStringExtra("salaryUseTypeId"));

		pd = ProgressDialog.show(this, "", "数据获取中", true, true);
		try {
			ServerAdaptor.getInstance(this).doAction(1, Constants.UrlHead + "client.action.SalaryAction$accessPostV1_8", getAccessPost, new ServiceSyncListener() {

				public void onSuccess(ActionResponse returnObject) {
					// TODO Auto-generated method stub
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Message msg = new Message();
					msg = handler.obtainMessage();
					msg.what = 1;
					msg.obj = returnObject.getData().toString();
					handler.sendMessage(msg);
				}

				public void onError(ActionResponse returnObject) {
					// TODO Auto-generated method stub
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Message msg = new Message();
					msg = handler.obtainMessage();
					msg.what = 2;
					msg.obj = returnObject.getMessage().toString();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TextView newTextView(String title) {
		View v = (View) getLayoutInflater().inflate(R.layout.layout_text_title, null);
		TextView tv = (TextView) v.findViewById(R.id.tvDetial);
		tv.setText(title);
		return tv;
	}

	private RelativeLayout newRelativeLayout(String name, String vaule) {
		View v = getLayoutInflater().inflate(R.layout.layout_text_detial, null);
		TextView payrollNameTv = (TextView) v.findViewById(R.id.payrollNameTv);
		TextView payrollValueTv = (TextView) v.findViewById(R.id.payrollValueTv);
		payrollNameTv.setText(name);
		payrollValueTv.setText(vaule);
		return (RelativeLayout) v;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.title_rightBtn:
			if ("".equals(payRollBean.getNewwages()) || payRollBean.getNewwages() == null) {
				Toast.makeText(PayrollActivity.this, "请选择有工资单的月份进行对比", Toast.LENGTH_SHORT).show();
				return;
			}
			// optionMenuInit("选择对比日期", selectAttachItemClick);
			setTime();
			style = "1";
			break;
		case R.id.payrolldateTv:
			// setTime();
			// style="2";
			break;
		case R.id.title_leftBtnBack:
			finish();
			break;
		default:
			break;
		}
	}

	/*
	 * 设置时间
	 */
	private void setTime() {
		LayoutInflater inflater = LayoutInflater.from(PayrollActivity.this);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(PayrollActivity.this);
		final WheelMain wheelMain = new WheelMain(timepickerview, false, false, true);
		wheelMain.screenheight = screenInfo.getWidth();
		String time = "";
		Calendar calendar = Calendar.getInstance();
		if (JudgeDate.isDate(time, "yyyy-MM")) {
			try {
				calendar.setTime(dateFormat.parse(time));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		wheelMain.setEND_YEAR(year + 10);
		wheelMain.setSTART_YEAR(year - 10);
		wheelMain.initDateTimePicker(year, month, day, hour, min, second);
		new AlertDialog.Builder(PayrollActivity.this).setTitle("选择日期").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (style.equals("1")) {
					Intent intent = new Intent(PayrollActivity.this, PayrolldbActivity.class);
					intent.putExtra("nowDate", selectTime);
					intent.putExtra("date", wheelMain.getTime(0));
					intent.putExtra("payRollBean", payRollBean);
					startActivity(intent);
				} else if (style.equals("2")) {
					dataTopTv.setText(wheelMain.getTime(0) + " 收入明细");
					selectTime = wheelMain.getTime(0);
					getAccessPost(wheelMain.getTime(0));
				}
			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		}).show();
	}

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

}
