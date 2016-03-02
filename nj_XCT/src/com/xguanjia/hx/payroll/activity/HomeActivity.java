package com.xguanjia.hx.payroll.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AppUtils;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.ElasticScrollView;
import com.xguanjia.hx.common.ElasticScrollView.OnRefreshListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.common.selecttime.JudgeDate;
import com.xguanjia.hx.common.selecttime.ScreenInfo;
import com.xguanjia.hx.common.selecttime.WheelMain;
import com.xguanjia.hx.login.update.UpdateManager;
import com.xguanjia.hx.notice.NoticeActivity;
import com.xguanjia.hx.notice.db.NoticeDb;
import com.xguanjia.hx.payroll.bean.PayRollSummary;
import com.xguanjia.hx.payroll.view.ChartView;
import com.xguanjia.hx.set.SetGroupActivity;

/***
 * 首页
 * 
 * @author Administrator
 * 
 */
public class HomeActivity extends BaseActivity implements OnRefreshListener {

	private RelativeLayout relativeBatteryForm;
	private ElasticScrollView scrollView;
	private TextView nowmonthTv;
	private TextView titleTv;
	private TextView selectyearTv, moneySum, yearTotalTv, monthTotalTv;
	private ChartView chartView; // 图标类(曲线图)
	private ProgressDialog pd;// 提示框
	private PayRollSummary payRollBean = new PayRollSummary(); // 收入概要
	private LayoutParams layoutParamsChartView;
	private DateFormat dateFormat = new SimpleDateFormat("yyyy");// 日期控件
	private HomeBoardcast homeBoardcast; // 广播接收者
	private boolean isRefresh = false;
	private String[] monthdate;

	private float singleMonthMoney = 0.0f;

	private float sum1 = 0.0f, sum2 = 0.0f, sum3 = 0.0f, sum4 = 0.0f,
			sum5 = 0.0f, sum6 = 0.0f, sum7 = 0.0f, sum8 = 0.0f, sum9 = 0.0f,
			sum10 = 0.0f, sum11 = 0.0f, sum12 = 0.0f;
	private float[] arrSum;

	public float getMaxSum(float[] arrSum) {
		float maxSum = arrSum[0];
		for (int i = 0; i < arrSum.length; i++) {
			if (maxSum < arrSum[i]) {
				maxSum = arrSum[i];
			}
		}
		return maxSum;
	}

	// 获取数组最小值
	public float getMinSum(float[] arrSum) {
		float minSum = arrSum[0];
		for (int i = 0; i < arrSum.length; i++) {
			if (minSum > arrSum[i]) {
				minSum = arrSum[i];
			}
		}
		return minSum;
	}

	// 处理数据
	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				scrollView.onRefreshComplete();
				String returnString = (String) msg.obj;
				payRollBean = new Gson().fromJson(returnString,
						new TypeToken<PayRollSummary>() {
						}.getType());

				// 判断显示工资
				if (!"".equals(payRollBean.getNewwages())
						&& payRollBean.getNewwages() != null) {
					// 数据解密
					String decodeNewWages = null;
					try {
						DesUtil desUtil = new DesUtil();
						if (payRollBean.getNewwages().length() > 10) {
							decodeNewWages = desUtil.decrypt(
									payRollBean.getNewwages(),
									"haoqeeJSXCT!@#$%^&*");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					// nowmonthTv.setText(payRollBean.getNewwages());
					nowmonthTv.setText(decodeNewWages);
				} else {
					// nowmonthTv.setText("0");
				}
				titleTv.setText(payRollBean.getNewdate() + "最新收入(元)");
				if ("".equals(payRollBean.getWagess())
						|| payRollBean.getWagess() == null) {
					yearTotalTv.setText("0");
				} else {
					// yearTotalTv.setText(String.valueOf(Float.parseFloat(payRollBean.getWagess())));
					yearTotalTv.setText(payRollBean.getWagess());
				}
				if (payRollBean.getList().size() != 0) {
					// monthTotalTv.setText(Integer.parseInt(yearTotalTv.getText().toString())/payRollBean.getList().size()+"");
					SimpleDateFormat format = new SimpleDateFormat();
					String st = yearTotalTv.getText().toString();
					Float f = Float.valueOf(st)
							/ (payRollBean.getList().size());
					monthTotalTv.setText(String.format("%.2f", f));
				} else {
					monthTotalTv.setText("0.0");
				}

				monthdate = new String[] { "0", "0", "0", "0", "0", "0", "0",
						"0", "0", "0", "0", "0" };

				int maxValue = 0,
				minValue = 0;
				// 判断月份中是否存在值,默认取一个值
				if (payRollBean.getList().size() > 0) {
					if ("".equals(payRollBean.getList().get(0).getWages())
							|| payRollBean.getList().get(0).getWages() == null) {
						payRollBean.getList().get(0).setWages("0");
					}

				}

				for (int i = 0; i < payRollBean.getList().size(); i++) {
					String month = "";
					// 去掉月份工资的最大值和最小值
					if ("".equals(payRollBean.getList().get(i).getWages())
							|| payRollBean.getList().get(i).getWages() == null) {
						payRollBean.getList().get(i).setWages("0");
					}

					// 按照月份进行排序
					if (payRollBean.getList().get(i).getDate().contains("-")) {
						month = payRollBean.getList().get(i).getDate()
								.split("-")[1];
					}

					if ("01".equals(month)) {
						// 同月份的收入求和 显示在曲线图
						float singleMonthMoney1 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum1 += singleMonthMoney1;
						monthdate[0] = String.valueOf(sum1).split("\\.")[0];

					} else if ("02".equals(month)) {
						// monthdate[1] =
						// payRollBean.getList().get(i).getWages()
						// .split("\\.")[0];
						float singleMonthMoney2 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum2 += singleMonthMoney2;
						monthdate[1] = String.valueOf(sum2).split("\\.")[0];

					} else if ("03".equals(month)) {
						float singleMonthMoney3 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum3 += singleMonthMoney3;
						monthdate[2] = String.valueOf(sum3).split("\\.")[0];

					} else if ("04".equals(month)) {
						float singleMonthMoney4 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum4 += singleMonthMoney4;
						monthdate[3] = String.valueOf(sum4).split("\\.")[0];

					} else if ("05".equals(month)) {
						float singleMonthMoney5 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum5 += singleMonthMoney5;
						monthdate[4] = String.valueOf(sum5).split("\\.")[0];

					} else if ("06".equals(month)) {
						float singleMonthMoney6 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum6 += singleMonthMoney6;
						monthdate[5] = String.valueOf(sum6).split("\\.")[0];

					} else if ("07".equals(month)) {
						float singleMonthMoney7 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum7 += singleMonthMoney7;
						monthdate[6] = String.valueOf(sum7).split("\\.")[0];

					} else if ("08".equals(month)) {
						float singleMonthMoney8 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum8 += singleMonthMoney8;
						monthdate[7] = String.valueOf(sum8).split("\\.")[0];

					} else if ("09".equals(month)) {
						float singleMonthMoney9 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum9 += singleMonthMoney9;
						monthdate[8] = String.valueOf(sum9).split("\\.")[0];

					} else if ("10".equals(month)) {
						float singleMonthMoney10 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum10 += singleMonthMoney10;
						monthdate[9] = String.valueOf(sum10).split("\\.")[0];

					} else if ("11".equals(month)) {
						float singleMonthMoney11 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum11 += singleMonthMoney11;
						monthdate[10] = String.valueOf(sum11).split("\\.")[0];

					} else if ("12".equals(month)) {
						float singleMonthMoney12 = Float.parseFloat(payRollBean
								.getList().get(i).getWages().toString());
						sum12 += singleMonthMoney12;
						monthdate[11] = String.valueOf(sum12).split("\\.")[0];
					}
				}

				// 获取数组最大值
				arrSum = new float[] { sum1, sum2, sum3, sum4, sum5, sum6,
						sum7, sum8, sum9, sum10, sum11, sum12 };

				maxValue = (int) getMaxSum(arrSum);
				minValue = (int) getMinSum(arrSum);

				int centerVaule = (maxValue + minValue) / 2;
				if (centerVaule == 0 || maxValue <= 10000) {// 当所有的数据为0，或者小于10000时
					chartView.SetInfo(
							new String[] { "1", "2", "3", "4", "5", "6", "7",
									"8", "9", "10", "11", "12" }, // X轴刻度
							new String[] { "", "2000", "4000", "6000", "8000",
									"10000", }, // Y轴刻度
							monthdate, // 数据
							"", AppUtils.getWidthPixels(HomeActivity.this),
							AppUtils.getHeighPixels(HomeActivity.this));
				} else {// 当最大值大于10000时，Y轴最大值乘以15%,然后取第一位的数，加1，然后补0；
					int y_max = maxValue + maxValue * 15 / 100;
					String str = String.valueOf(y_max);
					// 判断有几个字符
					int wei = str.length();
					// 判断第一位
					String one = String.valueOf(str.charAt(0));
					int one_shou = Integer.valueOf(one);
					int one_shou_jia = one_shou + 1;

					// 计算要乘以的倍数
					StringBuffer br = new StringBuffer();
					br.append("1");
					for (int i = 1; i <= wei - 1; i++) {
						br.append("0");
					}

					int bei = Integer.valueOf(br.toString());

					one_shou_jia = one_shou_jia * bei;
					int m = one_shou_jia / 5;
					String str1 = String.valueOf(m);
					String str2 = String.valueOf(m * 2);
					String str3 = String.valueOf(m * 3);
					String str4 = String.valueOf(m * 4);
					String str5 = String.valueOf(m * 5);
					chartView.SetInfo(
							new String[] { "1", "2", "3", "4", "5", "6", "7",
									"8", "9", "10", "11", "12" }, // X轴刻度
							new String[] { "", str1, str2, str3, str4, str5 }, // Y轴刻度
							monthdate, // 数据
							"", AppUtils.getWidthPixels(HomeActivity.this),
							AppUtils.getHeighPixels(HomeActivity.this));

				}
				relativeBatteryForm.removeAllViews();
				relativeBatteryForm.addView(chartView, layoutParamsChartView);
				break;
			case 2:
				scrollView.onRefreshComplete();
				String returnMessage = (String) msg.obj;
				Toast.makeText(HomeActivity.this, returnMessage,
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
			return false;
		}
	});

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		HaoXinProjectActivity.dangqian_view = "1";
		initView();
		if ("13911111122".equals(Constants.mobile)) {

		} else {
			UEProbAgent.onUserID(HomeActivity.this, Constants.mobile);
		}

		updata();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		HaoXinProjectActivity.dangqian_view = "1";
		UEProbAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	private String year_str = "";

	// 初始化界面
	private void initView() {
		// 设置首页通知公告小红点
		home_xhd = (ImageView) titleView.findViewById(R.id.home_ld_xhd);
		View view1 = getLayoutInflater().inflate(R.layout.main_activity, null);
		View view = getLayoutInflater().inflate(R.layout.main_activity1, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.mainView.addView(view1, layoutParams);
		this.setTitleText("首页");
		this.setButton_right_notice("", R.drawable.notice_home,
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						home_xhd.setVisibility(View.GONE);
						sendBroadcast(new Intent("com.tzgg.xhd_un"));

						sendBroadcast(new Intent("com.myandgz.xhd"));
						if (!HaoXinProjectActivity.first_enter_setActivity) {// 用来判断是否进入过我的页面了没有的
							HaoXinProjectActivity.kaishi = true;
						}
						Intent intent = new Intent(HomeActivity.this,
								NoticeActivity.class);
						startActivity(intent);

					}
				});

		scrollView = (ElasticScrollView) view1.findViewById(R.id.scrollview);
		scrollView.setonRefreshListener(this);
		scrollView.addChild(view, 1);
		nowmonthTv = (TextView) view.findViewById(R.id.nowmonthTv);
		titleTv = (TextView) view.findViewById(R.id.titleTv);
		selectyearTv = (TextView) view.findViewById(R.id.selectyearTv);
		selectyearTv.setOnClickListener(this);

		moneySum = (TextView) view.findViewById(R.id.moneySum);
		moneySum.setOnClickListener(this);

		yearTotalTv = (TextView) view.findViewById(R.id.yearTotalTv);
		monthTotalTv = (TextView) view.findViewById(R.id.monthTotalTv);
		relativeBatteryForm = (RelativeLayout) view
				.findViewById(R.id.relativeBatteryForm);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		chartView = new ChartView(this);
		chartView.fdensity = dm.density;
		int iWidth = AppUtils.getWidthPixels(this);
		int iHeight = AppUtils.getHeighPixels(this);
		layoutParamsChartView = new LayoutParams((int) (iWidth),
				(int) (iHeight * 0.35));
		// relativeBatteryForm.addView(chartView, layoutParamsChartView);
		relativeBatteryForm.setOnClickListener(this);
		vp = new VP_jiekou(this);
		xhd_broadcast = new Home_xhd_Broadcast();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.home.tzgg.xhd");
		intentFilter.addAction("com.home.tzgg.xhd_un");
		registerReceiver(xhd_broadcast, intentFilter);
		initDate();
	}

	private Home_xhd_Broadcast xhd_broadcast;

	private VP_jiekou vp;
	private NoticeDb noticeDb;

	private void initDate() {
		year_str = AppUtils.getYear();
		selectyearTv.setText(AppUtils.getYear() + "年");
		getIndex(AppUtils.getToday());
		vp.getNoticeList();
		noticeDb = new NoticeDb(this);
		if (noticeDb.getUnnoticeNum() != 0) {
			home_xhd.setVisibility(View.VISIBLE);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_rightBtn:
			getIndex(AppUtils.getToday());
			break;
		case R.id.selectyearTv:
			// setTime();
			// selectyearTv.setText(AppUtils.getYear() + "年工资单曲线图");
			setT();
			break;

		case R.id.relativeBatteryForm:
			Intent intent = new Intent(HomeActivity.this,
					IncomeTotalActivity.class);
			intent.putExtra("year_str", year_str);
			startActivity(intent);
			break;

		case R.id.moneySum:

			final String[] items = { "全部", "工资", "奖金", "报销", "福利" };
			new AlertDialog.Builder(this).setTitle("选择类型")
					.setItems(items, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							moneySum.setText(items[which]);

						}
					}).show();

		default:
			break;
		}
		super.onClick(v);
	}

	/*
	 * 设置时间
	 */
	private void setT() {
		// new DatePicker(HomeActivity.this).
		Intent intent = new Intent(HomeActivity.this, AlertDialogActivity.class);

		startActivityForResult(intent, 100);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 100:
			if (resultCode == 101) {
				String m = data.getStringExtra("re_date");
				if ("".equals(m) || m == null) {

				} else {

					year_str = data.getStringExtra("re_date");
					selectyearTv.setText(year_str + "年");
					getIndex(year_str + "-01");
				}
			}
			break;

		default:
			break;
		}

	}

	private void setTime() {
		LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(HomeActivity.this);
		final WheelMain wheelMain = new WheelMain(timepickerview, false, false,
				false);
		wheelMain.screenheight = screenInfo.getWidth();
		String time = "";
		Calendar calendar = Calendar.getInstance();
		if (JudgeDate.isDate(time, "yyyy")) {
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
		new AlertDialog.Builder(HomeActivity.this, R.style.Theme_Transparent)
				.setTitle("选择年度工资单").setView(timepickerview)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						year_str = wheelMain.getTime(0);
						selectyearTv.setText(wheelMain.getTime(0) + "年");
						getIndex(wheelMain.getTime(0) + "-01");
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

					}
				}).show();
	}

	private void getIndex(String year) {
		HashMap<String, String> getIndex = new HashMap<String, String>();
		getIndex.put("date", year);
		getIndex.put("userPhone", Constants.mobile);
		getIndex.put("partyId", Constants.partyId);
		if (isRefresh) {
			isRefresh = false;
		} else {
			// pd = ProgressDialog.show(this, "", "数据获取中", true, true);
		}
		try {
			ServerAdaptor.getInstance(this).doAction(1,
					Constants.UrlHead + "client.action.SalaryAction$getIndex",
					getIndex, new ServiceSyncListener() {

						public void onSuccess(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							// if (pd != null && pd.isShowing()) {
							// pd.dismiss();
							// }
							Message msg = new Message();
							msg = handler.obtainMessage();
							msg.what = 1;
							msg.obj = returnObject.getData().toString();
							handler.sendMessage(msg);
						}

						public void onError(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							// if (pd != null && pd.isShowing()) {
							// pd.dismiss();
							// }
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

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		sum1 = 0.0f;
		sum2 = 0.0f;
		sum3 = 0.0f;
		sum4 = 0.0f;
		sum5 = 0.0f;
		sum6 = 0.0f;
		sum7 = 0.0f;
		sum8 = 0.0f;
		sum9 = 0.0f;
		sum10 = 0.0f;
		sum11 = 0.0f;
		sum12 = 0.0f;
		getIndex(AppUtils.getToday());
		year_str = AppUtils.getYear();
		selectyearTv.setText(AppUtils.getYear() + "年");
	}

	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(xhd_broadcast);

	}

	private class HomeBoardcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("homepayroll")) {
				getIndex(AppUtils.getToday());
				isRefresh = true;
			}
		}
	}

	private class Home_xhd_Broadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if (intent.getAction().equals("com.home.tzgg.xhd")) {
				home_xhd.setVisibility(View.VISIBLE);

			} else if (intent.getAction().equals("com.home.tzgg.xhd_un")) {
				home_xhd.setVisibility(View.GONE);

			}

		}

	}

	private Editor editor;

	private void getNewVersion() {
		// 自动更新
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		String datanow = dateformat.format(new Date());
		Log.i("NoUpVersion", "now_day:" + datanow);
		editor = sp.edit();
		if (!sp.getString("now_day", "").equals(datanow)) {
			updata();
			editor.putString("now_day", datanow);
			editor.commit();
			Log.i("UpVersion", "commit:" + datanow);
		}
	}

	private HashMap<String, String> val_new;
	private String clientPath = "";

	// 檢測更新
	private void updata() {
		Constants.ISUPDATE = true;
		if (pd != null && pd.isShowing()) {
			pd.dismiss();
		}
		// pd = ProgressDialog.show(TeLoginActivity.this, "", "检测更新...", true,
		// true);
		val_new = new HashMap<String, String>();
		val_new.put("productId", Constants.PRODUCT_ID);
		val_new.put("partyId", sp.getString("partyId", Constants.partyId));
		val_new.put("appVersion", getVersion());
		val_new.put("mobileType", "android");
		Constants.TIME_OUT = 5000; // 设置登录超时时间
		try {
			ServerAdaptor.getInstance(this).doAction(
					1,
					Constants.UrlHead
							+ "maintain.client.action.UpdateAction$update",
					val_new, new ServiceSyncListener() {
						public void onError(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							Constants.TIME_OUT = 3000;

						};

						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}

							JSONObject resData = (JSONObject) returnObject
									.getData();
							try {
								if (!resData.isNull("isUpdate")) {
									int boolIsUpdate = -1;
									boolIsUpdate = new Integer(resData
											.getString("isUpdate")).intValue();
									switch (boolIsUpdate) {
									case 0:
										// Toast.makeText(getApplicationContext(),
										// "当前为最新版本",
										// Toast.LENGTH_SHORT).show();

										break;
									case 1:
										clientPath = resData
												.getString("clientPath");
										UpdateManager mUpdateManager = new UpdateManager(
												HomeActivity.this,
												Constants.SERVER_VERSON,
												clientPath, mmhandler, true);
										mUpdateManager.checkUpdateInfo(7);
										break;
									case 2:
										clientPath = resData
												.getString("clientPath");
										UpdateManager mUpdateManager_update = new UpdateManager(
												HomeActivity.this,
												Constants.SERVER_VERSON,
												clientPath, true);
										mUpdateManager_update
												.checkUpdateInfo(8);
										break;
									default:
										break;
									}
								} else {

								}

							} catch (Exception e) {
							}
						}
					});
		} catch (Exception e) {
		}
	}

	private Handler mmhandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};

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

}
