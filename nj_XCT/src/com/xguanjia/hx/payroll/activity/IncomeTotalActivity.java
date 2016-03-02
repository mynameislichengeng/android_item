package com.xguanjia.hx.payroll.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.DesUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.payroll.adapter.MyAdapter;
import com.xguanjia.hx.payroll.bean.QuanBuList_Bean;
import com.xguanjia.hx.payroll.bean.QuanBu_AfterBean;
import com.xguanjia.hx.payroll.bean.QuanBu_Bean;

public class IncomeTotalActivity extends BaseActivity {

	private TextView tv_total, tv_payroll, tv_award, tv_baoxiao, tv_welfare;

	private TextView disPaly_tv;

	private ListView lv_my;

	private ProgressDialog pd;// 提示框

	private QuanBuList_Bean dataList;

	private ArrayList<QuanBu_AfterBean> myDrawList = new ArrayList<QuanBu_AfterBean>();
	
	private float countsSum;

	private TreeMap<String, Float> timeAndSum = new TreeMap<String, Float>(
			new MyComp());
	private MyAdapter adapter;

	private Handler handler = new Handler(new Handler.Callback() {

		public boolean handleMessage(Message msg) {

			countsSum = 0.0f;
			switch (msg.what) {
			case 1:
				try {
					// 清楚数据，以免叠加
					myDrawList.clear();
					timeAndSum.clear();
					String data = (String) msg.obj;
					
					// 获取第一层数据
					dataList = new Gson().fromJson(data,
							new TypeToken<QuanBuList_Bean>() {
							}.getType());
					
					// 获取第二层数据
					List<QuanBu_Bean> list = dataList.getList();
					if(list==null){
						list = new ArrayList<QuanBu_Bean>();
					}
					for (int i = 0; i < list.size(); i++) {
						QuanBu_Bean bean = list.get(i);
						// 求出月份数
						// String[] month = bean.getPayRollTime().split("-");
						String monthString = bean.getPayRollTime();
						String decodeMoney = null;
						
						// 数据解密
						if (!"".equals(bean.getPayRollMoney())
								&& bean.getPayRollMoney() != null) {
							try {
								DesUtil desUtil = new DesUtil();
								if (bean.getPayRollMoney().length() > 10) {
									decodeMoney = desUtil.decrypt(
											bean.getPayRollMoney(),
											"haoqeeJSXCT!@#$%^&*");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						float sumTemp = Float.valueOf(decodeMoney);
						// 添加时间
						if (timeAndSum.containsKey(monthString)) {// 含有,这相加
							float tmp = timeAndSum.get(monthString);
							tmp = sumTemp + tmp;
							timeAndSum.put(monthString, tmp);
						} else {// 不含有
							timeAndSum.put(monthString, sumTemp);
						}
						
						
					}
					// 取出map中金钱
					// 抽取map中的时间
					String[] timeafter = {};
					float[] f = {};
					if (timeAndSum.size() != 0) {
						
						try {
							Set<Entry<String, Float>> ens = timeAndSum.entrySet();
							ArrayList<Entry<String, Float>> lists = new ArrayList<Entry<String, Float>>(
									ens);
							// 用于提取值后，
							ArrayList<Float> temp_Float_List = new ArrayList<Float>();
							for (Entry<String, Float> bea : lists) {
								temp_Float_List.add(bea.getValue());
							}
							f = new float[temp_Float_List.size()];
							for (int i = 0; i < temp_Float_List.size(); i++) {
								f[i] = temp_Float_List.get(i);
								countsSum = temp_Float_List.get(i) + countsSum;
							}
							disPaly_tv.setText(String.valueOf("￥" + countsSum));//这里countsSum做了2件事，总和和最大
							// 求最大值
							int max = 0;
							for (int i = 0; i < f.length; i++) {
								
								if (f[i] > f[max])
									max = i;
							}
							countsSum = f[max] * 1.1f;
							
							// 求最小，且不为0
							int min = 0;
							for (int j = 0; j < f.length; j++) {
								if (f[j] != 0) {
									min = j;
									break;
								}
							}
							for (int k = 0; k < f.length; k++) {
								if (f[k] < f[min] && f[k] != 0.0) {
									min = k;
								}
								
							}
							try {
								timeafter = new String[timeAndSum.keySet().size()];
								timeafter = (String[]) timeAndSum.keySet().toArray(timeafter);
								
							} catch (Exception e) {
								// TODO: handle exception
							}
							if (countsSum != 0.0f) {
								
								for (int t = f.length - 1; t >= 0; t--) {
									
									if (f[t] != 0.0f) {
										QuanBu_AfterBean af = new QuanBu_AfterBean();
										if ((f[min] / countsSum) < 0.35) {// 当小于0.35，就按最小来定义
											float k1 = f[max] - f[min];
											float a = 0.35f;
											float b = 0.95f;
											float ca = b - a;
											float scale = k1 / ca;
											float q = 0.0f;
											q = (f[t] - f[min]) / scale;
											q = q + a;
											af.setBackground(q);
										} else {// 当大于0.35时就按最大的来定义
											af.setBackground(f[t] / countsSum);
										}
										
										af.setMoney(String.valueOf(f[t]));
										af.setTime(timeafter[t]);
										myDrawList.add(af);
									}
									
								}
							}
						} catch (Exception e) {
						}
						
					}else {
						countsSum=0.0f;
						disPaly_tv.setText(String.valueOf("￥" + countsSum));
					}
					
					if (myDrawList.size() > 0) {
						myDrawList.get(0).setFlag(0);
					}
					adapter.setChangerAdapter(myDrawList);
				} catch (Exception e) {
					// TODO: handle exception
				}
			default:
				break;
			}
			return false;

		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
		initView();
	}

	private String str_title = "";// 用于显示标题
	private String str_classes = "";// 用于显示要的类型
	private String str_year = "";//用于显示的是那一年，若没有则显示2015，2016
	private void initData() {
		Intent intent = getIntent();
		try {
			str_title = intent.getStringExtra("title_str");
			str_classes = intent.getStringExtra("classes_str");
			str_year = intent.getStringExtra("year_str");
		} catch (Exception e) {
		}
		if (("").equals(str_title) || null == str_title) {
			str_title = "全部";
		}
		if (("").equals(str_classes) || null == str_classes) {
			str_classes = "0";
		}
		if(str_year==null){
			str_year = "";
		}
		
		
		

	}

	private LayoutParams lp;
	private View incomeTotalView;
	private LinearLayout linear_part01;
	private TextView tv_miaoshu;
	private void initView() {
		// TODO Auto-generated method stub
		lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		setTitleText(str_title, this);
		setTitleLeftButton("", R.drawable.back_selector, this);
		incomeTotalView = getLayoutInflater().inflate(
				R.layout.activity_incometotal, null);
		disPaly_tv = (TextView) incomeTotalView
				.findViewById(R.id.tv_total_display);
		lv_my = (ListView) incomeTotalView.findViewById(R.id.lv_degree);
		linear_part01 = (LinearLayout) incomeTotalView.findViewById(R.id.linear_part01);
		tv_miaoshu = (TextView) incomeTotalView.findViewById(R.id.tv_miaoshu);
		if(null==str_year||"".equals(str_year)){
			tv_miaoshu.setText("累积收入(元)");
		}else {
			tv_miaoshu.setText("年度累积收入(元)");
		}
		lv_my.setDividerHeight(0);
		lv_my.setOverScrollMode(View.OVER_SCROLL_NEVER);
		adapter = new MyAdapter(IncomeTotalActivity.this, myDrawList);
		lv_my.setAdapter(adapter);

		mainView.addView(incomeTotalView, lp);
		getAllData(str_classes);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		switch (v.getId()) {
		case R.id.title_leftBtn:
			finish();
			break;
		case R.id.TitleText:

			final String[] items = new String[Constants.payRollTypeGroups
					.getSalaryUseTypes().size()];
			for (int i = 0; i < Constants.payRollTypeGroups.getSalaryUseTypes()
					.size(); i++) {
				items[i] = Constants.payRollTypeGroups.getSalaryUseTypes()
						.get(i).getUseGroupingName();
			}

			new AlertDialog.Builder(this).setTitle("选择类型")
					.setItems(items, new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

							setTitleText(items[which], IncomeTotalActivity.this);
							getAllData(Constants.payRollTypeGroups
									.getSalaryUseTypes().get(which)
									.getUseGroupingId());

						}
					}).show();

			break;

		default:
			break;
		}
	}

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

	public void getAllData(String useGroupingId) {
		HashMap<String, String> getIndex = new HashMap<String, String>();
		getIndex.put("userPhone", Constants.mobile);
		getIndex.put("partyId", Constants.partyId);
		getIndex.put("useGroupingId", useGroupingId);
		getIndex.put("version", getVersion());
		getIndex.put("year", str_year);
		pd = ProgressDialog.show(this, "", "数据获取中", true, true);

		try {
			ServerAdaptor.getInstance(this).doAction(
					1,
					Constants.UrlHead
							+ "client.action.SalaryAction$getAllSalary",
					getIndex, new ServiceSyncListener() {

						public void onSuccess(ActionResponse returnObject) {
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

	private static class MyComp implements Comparator {

		@Override
		public int compare(Object lhs, Object rhs) {
			String str1 = (String) lhs;
			String str2 = (String) rhs;
			return str1.compareTo(str2);
		}

	}

}
