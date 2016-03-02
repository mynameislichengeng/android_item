package com.xguanjia.hx.payroll.piechart;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.payroll.bean.PayRollDetail;
import com.xguanjia.hx.payroll.bean.PayRollSummary;
import com.xguanjia.hx.payroll.bean.PieChartBean;
import com.xguanjia.hx.payroll.bean.TempListBean;

/**
 * 饼状图界面
 * 
 * @author Administrator
 * 
 */
public class PieChartActivity extends BaseActivity implements OnDateChangedLinstener {
	private StatisticsView mView;
	private float total = 0;
	private float[] items = {};
	private float[] items_vlaue = {};
	private String[] type = {};
	private List<PieChartBean> pieChartBeans = new ArrayList<PieChartBean>();
	private ProgressDialog pd;
	private int showYear, showMonth;
	private String isfirst = "1";// 1第一次进入

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				String returnString = (String) msg.obj;
				PayRollSummary payRollBean = new Gson().fromJson(returnString, new TypeToken<PayRollSummary>() {
				}.getType());

				// 新的饼状图元素类；
				List<TempListBean> tempListBeans = payRollBean.getNewtemplist();

				PayRollDetail listBean = payRollBean.getSalaryBasis();
				pieChartBeans.clear();
				if (!"".equals(payRollBean.getWages()) && payRollBean.getWages() != null) {
					// total = Integer.parseInt(payRollBean.getWages());

					total = (int) Double.parseDouble(payRollBean.getWages());
				}
				total = 0;
				for (int j = 0; j < tempListBeans.size(); j++) {
					if (tempListBeans.get(j).getBasisName().equals("basisId")) {

					} else if (tempListBeans.get(j).getBasisName().equals("userName")) {
					} else if (tempListBeans.get(j).getBasisName().equals("userPhone")) {
					} else if (tempListBeans.get(j).getBasisName().equals("date")) {
					} else if (tempListBeans.get(j).getBasisName().equals("dept")) {
					} else if (tempListBeans.get(j).getBasisName().equals("wages")) {
					} else if (tempListBeans.get(j).getBasisName().equals("value1")) {
						if (!"".equals(listBean.getValue1())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue1());
							total = total + Float.parseFloat(listBean.getValue1());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value2")) {
						if (!"".equals(listBean.getValue2())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue2());
							total = total + Float.parseFloat(listBean.getValue2());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value3")) {
						if (!"".equals(listBean.getValue3())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue3());
							total = total + Float.parseFloat(listBean.getValue3());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value4")) {
						if (!"".equals(listBean.getValue4())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue4());
							total = total + Float.parseFloat(listBean.getValue4());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value5")) {
						if (!"".equals(listBean.getValue5())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue5());
							total = total + Float.parseFloat(listBean.getValue5());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value6")) {
						if (!"".equals(listBean.getValue6())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue6());
							total = total + Float.parseFloat(listBean.getValue6());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value7")) {
						if (!"".equals(listBean.getValue7())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue7());
							total = total + Float.parseFloat(listBean.getValue7());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value8")) {
						if (!"".equals(listBean.getValue8())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue8());
							total = total + Float.parseFloat(listBean.getValue8());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value9")) {
						if (!"".equals(listBean.getValue9())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue9());
							total = total + Float.parseFloat(listBean.getValue9());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value10")) {
						if (!"".equals(listBean.getValue10())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue10());
							total = total + Float.parseFloat(listBean.getValue10());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value11")) {
						if (!"".equals(listBean.getValue11())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue11());
							total = total + Float.parseFloat(listBean.getValue11());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value12")) {
						if (!"".equals(listBean.getValue12())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue12());
							total = total + Float.parseFloat(listBean.getValue12());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value13")) {
						if (!"".equals(listBean.getValue13())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue13());
							total = total + Float.parseFloat(listBean.getValue13());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value14")) {
						if (!"".equals(listBean.getValue14())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue14());
							total = total + Float.parseFloat(listBean.getValue14());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value15")) {
						if (!"".equals(listBean.getValue15())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue15());
							total = total + Float.parseFloat(listBean.getValue15());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value16")) {
						if (!"".equals(listBean.getValue16())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue16());
							total = total + Float.parseFloat(listBean.getValue16());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value17")) {
						if (!"".equals(listBean.getValue17())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue17());
							total = total + Float.parseFloat(listBean.getValue17());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value18")) {
						if (!"".equals(listBean.getValue18())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue18());
							total = total + Float.parseFloat(listBean.getValue18());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value19")) {
						if (!"".equals(listBean.getValue19())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue19());
							total = total + Float.parseFloat(listBean.getValue19());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value20")) {
						if (!"".equals(listBean.getValue20())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue20());
							total = total + Float.parseFloat(listBean.getValue20());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value21")) {
						if (!"".equals(listBean.getValue21())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue21());
							total = total + Float.parseFloat(listBean.getValue21());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value22")) {
						if (!"".equals(listBean.getValue22())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue22());
							total = total + Float.parseFloat(listBean.getValue22());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value23")) {
						if (!"".equals(listBean.getValue23())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue23());
							total = total + Float.parseFloat(listBean.getValue23());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value24")) {
						if (!"".equals(listBean.getValue24())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue24());
							total = total + Float.parseFloat(listBean.getValue24());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value25")) {
						if (!"".equals(listBean.getValue25())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue25());
							total = total + Float.parseFloat(listBean.getValue25());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value26")) {
						if (!"".equals(listBean.getValue26())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue26());
							total = total + Float.parseFloat(listBean.getValue26());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value27")) {
						if (!"".equals(listBean.getValue27())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue27());
							total = total + Float.parseFloat(listBean.getValue27());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value28")) {
						if (!"".equals(listBean.getValue28())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue28());
							total = total + Float.parseFloat(listBean.getValue28());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value29")) {
						if (!"".equals(listBean.getValue29())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue29());
							total = total + Float.parseFloat(listBean.getValue29());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value30")) {
						if (!"".equals(listBean.getValue30())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue30());
							total = total + Float.parseFloat(listBean.getValue30());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value31")) {
						if (!"".equals(listBean.getValue31())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue31());
							total = total + Float.parseFloat(listBean.getValue31());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value32")) {
						if (!"".equals(listBean.getValue32())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue32());
							total = total + Float.parseFloat(listBean.getValue32());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value33")) {
						if (!"".equals(listBean.getValue33())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue33());
							total = total + Float.parseFloat(listBean.getValue33());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value34")) {
						if (!"".equals(listBean.getValue34())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue34());
							total = total + Float.parseFloat(listBean.getValue34());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value35")) {
						if (!"".equals(listBean.getValue35())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue35());
							total = total + Float.parseFloat(listBean.getValue35());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value36")) {
						if (!"".equals(listBean.getValue36())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue36());
							total = total + Float.parseFloat(listBean.getValue36());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value37")) {
						if (!"".equals(listBean.getValue37())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue37());
							total = total + Float.parseFloat(listBean.getValue37());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value38")) {
						if (!"".equals(listBean.getValue38())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue38());
							total = total + Float.parseFloat(listBean.getValue38());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value39")) {
						if (!"".equals(listBean.getValue39())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue39());
							total = total + Float.parseFloat(listBean.getValue39());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value40")) {
						if (!"".equals(listBean.getValue40())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue40());
							total = total + Float.parseFloat(listBean.getValue40());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value41")) {
						if (!"".equals(listBean.getValue41())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue41());
							total = total + Float.parseFloat(listBean.getValue41());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value42")) {
						if (!"".equals(listBean.getValue42())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue42());
							total = total + Float.parseFloat(listBean.getValue42());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value43")) {
						if (!"".equals(listBean.getValue43())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue43());
							total = total + Float.parseFloat(listBean.getValue43());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value44")) {
						if (!"".equals(listBean.getValue44())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue44());
							total = total + Float.parseFloat(listBean.getValue44());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value45")) {
						if (!"".equals(listBean.getValue45())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue45());
							total = total + Float.parseFloat(listBean.getValue45());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value46")) {
						if (!"".equals(listBean.getValue46())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue46());
							total = total + Float.parseFloat(listBean.getValue46());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value47")) {
						if (!"".equals(listBean.getValue47())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue47());
							total = total + Float.parseFloat(listBean.getValue47());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value48")) {
						if (!"".equals(listBean.getValue48())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue48());
							total = total + Float.parseFloat(listBean.getValue48());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value49")) {
						if (!"".equals(listBean.getValue49())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue49());
							total = total + Float.parseFloat(listBean.getValue49());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value50")) {
//						if (!"".equals(listBean.getValue50())) {
//							PieChartBean pieChartBean = new PieChartBean();
//							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
//							pieChartBean.setVaule(listBean.getValue50());
//							total = total + Float.parseFloat(listBean.getValue50());
//							pieChartBeans.add(pieChartBean);
//						}
					} else if (tempListBeans.get(j).getBasisName().equals("value51")) {
						if (!"".equals(listBean.getValue51())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue51());
							total = total + Float.parseFloat(listBean.getValue51());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value52")) {
						if (!"".equals(listBean.getValue52())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue52());
							total = total + Float.parseFloat(listBean.getValue52());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value53")) {
						if (!"".equals(listBean.getValue53())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue53());
							total = total + Float.parseFloat(listBean.getValue53());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value54")) {
						if (!"".equals(listBean.getValue54())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue54());
							total = total + Float.parseFloat(listBean.getValue54());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value55")) {
						if (!"".equals(listBean.getValue55())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue55());
							total = total + Float.parseFloat(listBean.getValue55());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value56")) {
						if (!"".equals(listBean.getValue56())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue56());
							total = total + Float.parseFloat(listBean.getValue56());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value57")) {
						if (!"".equals(listBean.getValue57())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue57());
							total = total + Float.parseFloat(listBean.getValue57());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value58")) {
						if (!"".equals(listBean.getValue58())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue58());
							total = total + Float.parseFloat(listBean.getValue58());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value59")) {
						if (!"".equals(listBean.getValue59())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue59());
							total = total + Float.parseFloat(listBean.getValue59());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value60")) {
						if (!"".equals(listBean.getValue60())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue60());
							total = total + Float.parseFloat(listBean.getValue60());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value61")) {
						if (!"".equals(listBean.getValue61())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue61());
							total = total + Float.parseFloat(listBean.getValue61());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value62")) {
						if (!"".equals(listBean.getValue62())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue62());
							total = total + Float.parseFloat(listBean.getValue62());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value63")) {
						if (!"".equals(listBean.getValue63())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue63());
							total = total + Float.parseFloat(listBean.getValue63());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value64")) {
						if (!"".equals(listBean.getValue64())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue64());
							total = total + Float.parseFloat(listBean.getValue64());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value65")) {
						if (!"".equals(listBean.getValue65())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue65());
							total = total + Float.parseFloat(listBean.getValue65());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value66")) {
						if (!"".equals(listBean.getValue66())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue66());
							total = total + Float.parseFloat(listBean.getValue66());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value67")) {
						if (!"".equals(listBean.getValue67())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue67());
							total = total + Float.parseFloat(listBean.getValue67());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value68")) {
						if (!"".equals(listBean.getValue68())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue68());
							total = total + Float.parseFloat(listBean.getValue68());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value69")) {
						if (!"".equals(listBean.getValue69())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue69());
							total = total + Float.parseFloat(listBean.getValue69());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value70")) {
						if (!"".equals(listBean.getValue70())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue70());
							total = total + Float.parseFloat(listBean.getValue70());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value71")) {
						if (!"".equals(listBean.getValue71())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue71());
							total = total + Float.parseFloat(listBean.getValue71());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value72")) {
						if (!"".equals(listBean.getValue72())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue72());
							total = total + Float.parseFloat(listBean.getValue72());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value73")) {
						if (!"".equals(listBean.getValue73())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue73());
							total = total + Float.parseFloat(listBean.getValue73());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value74")) {
						if (!"".equals(listBean.getValue74())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue74());
							total = total + Float.parseFloat(listBean.getValue74());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value75")) {
						if (!"".equals(listBean.getValue75())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue75());
							total = total + Float.parseFloat(listBean.getValue75());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value76")) {
						if (!"".equals(listBean.getValue76())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue76());
							total = total + Float.parseFloat(listBean.getValue76());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value77")) {
						if (!"".equals(listBean.getValue77())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue77());
							total = total + Float.parseFloat(listBean.getValue77());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value78")) {
						if (!"".equals(listBean.getValue78())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue78());
							total = total + Float.parseFloat(listBean.getValue78());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value79")) {
						if (!"".equals(listBean.getValue79())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue79());
							total = total + Float.parseFloat(listBean.getValue79());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value80")) {
						if (!"".equals(listBean.getValue80())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue80());
							total = total + Float.parseFloat(listBean.getValue80());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value81")) {
						if (!"".equals(listBean.getValue81())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue81());
							total = total + Float.parseFloat(listBean.getValue81());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value82")) {
						if (!"".equals(listBean.getValue82())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue82());
							total = total + Float.parseFloat(listBean.getValue82());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value83")) {
						if (!"".equals(listBean.getValue83())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue83());
							total = total + Float.parseFloat(listBean.getValue83());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value84")) {
						if (!"".equals(listBean.getValue84())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue84());
							total = total + Float.parseFloat(listBean.getValue84());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value85")) {
						if (!"".equals(listBean.getValue85())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue85());
							total = total + Float.parseFloat(listBean.getValue85());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value86")) {
						if (!"".equals(listBean.getValue86())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue86());
							total = total + Float.parseFloat(listBean.getValue86());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value87")) {
						if (!"".equals(listBean.getValue87())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue87());
							total = total + Float.parseFloat(listBean.getValue87());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value88")) {
						if (!"".equals(listBean.getValue88())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue88());
							total = total + Float.parseFloat(listBean.getValue88());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value89")) {
						if (!"".equals(listBean.getValue89())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue89());
							total = total + Float.parseFloat(listBean.getValue89());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value90")) {
						if (!"".equals(listBean.getValue90())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue90());
							total = total + Float.parseFloat(listBean.getValue90());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value91")) {
						if (!"".equals(listBean.getValue91())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue91());
							total = total + Float.parseFloat(listBean.getValue91());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value92")) {
						if (!"".equals(listBean.getValue92())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue92());
							total = total + Float.parseFloat(listBean.getValue92());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value93")) {
						if (!"".equals(listBean.getValue93())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue93());
							total = total + Float.parseFloat(listBean.getValue93());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value94")) {
						if (!"".equals(listBean.getValue94())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue94());
							total = total + Float.parseFloat(listBean.getValue94());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value95")) {
						if (!"".equals(listBean.getValue95())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue95());
							total = total + Float.parseFloat(listBean.getValue95());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value96")) {
						if (!"".equals(listBean.getValue96())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue96());
							total = total + Float.parseFloat(listBean.getValue96());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value97")) {
						if (!"".equals(listBean.getValue97())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue97());
							total = total + Float.parseFloat(listBean.getValue97());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value98")) {
						if (!"".equals(listBean.getValue98())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue98());
							total = total + Float.parseFloat(listBean.getValue98());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value99")) {
						if (!"".equals(listBean.getValue99())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue99());
							total = total + Float.parseFloat(listBean.getValue99());
							pieChartBeans.add(pieChartBean);
						}
					} else if (tempListBeans.get(j).getBasisName().equals("value100")) {
						if (!"".equals(listBean.getValue100())) {
							PieChartBean pieChartBean = new PieChartBean();
							pieChartBean.setName(tempListBeans.get(j).getSalaryFieldName());
							pieChartBean.setVaule(listBean.getValue100());
							total = total + Float.parseFloat(listBean.getValue100());
							pieChartBeans.add(pieChartBean);
						}
					}

				}
				items = new float[pieChartBeans.size()];
				type = new String[pieChartBeans.size()];
				items_vlaue = new float[pieChartBeans.size()];

				float numTotal = 0;
				for (int i = 0; i < pieChartBeans.size(); i++) {
					numTotal = numTotal + (float) Double.parseDouble(pieChartBeans.get(i).getVaule());
				}
				for (int i = 0; i < pieChartBeans.size(); i++) {
					items[i] = ((float) Double.parseDouble(pieChartBeans.get(i).getVaule()) / numTotal) * 100;// 每一项占的比值
					items_vlaue[i] = Float.parseFloat(pieChartBeans.get(i).getVaule());// 每一项的值
					type[i] = pieChartBeans.get(i).getName();
				}
				if (items.length == 0) {
					if (!"".equals(payRollBean.getWages())) {
						total = (int) Double.parseDouble(payRollBean.getWages());
						items = new float[1];
						items[0] = 100;
						items_vlaue = new float[1];
						items_vlaue[0] = 0;
						type = new String[1];
						type[0] = "应发";
					} else {
						total = 0;
						items = new float[1];
						items[0] = 100;
						items_vlaue = new float[1];
						items_vlaue[0] = 0;
						type = new String[1];
						type[0] = "无";
					}
				}

				mView = new StatisticsView(PieChartActivity.this, items, total, type, items_vlaue);
				if (!"".equals(payRollBean.getSalaryBasisDate()) && payRollBean.getSalaryBasisDate() != null && "1".equals(isfirst)) {
					showYear = (int) Double.parseDouble(payRollBean.getSalaryBasisDate().split("-")[0]);
					showMonth = (int) Double.parseDouble(payRollBean.getSalaryBasisDate().split("-")[1]) + 1;
				}
				String[] str_data = payRollBean.getDate().split("-");
				showYear = Integer.valueOf(str_data[0]);
				showMonth = Integer.valueOf(str_data[1])+1;
				mView.setCurrDate(showYear, showMonth);
				mView.setDateChangedListener(PieChartActivity.this);

				LayoutParams layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				mainView.addView(mView, layoutParams);
				isfirst = "2";
				break;
			case 2:
				String returnMessage = (String) msg.obj;
				Toast.makeText(PieChartActivity.this, returnMessage, Toast.LENGTH_SHORT).show();
				total = 0;
				items = new float[1];
				items[0] = 100;
				items_vlaue = new float[1];
				items_vlaue[0] = 0;
				type = new String[1];
				type[0] = "无";
				mView = new StatisticsView(PieChartActivity.this, items, total, type, items_vlaue);
				mView.setCurrDate(showYear, showMonth);
				mView.setDateChangedListener(PieChartActivity.this);

				// setContentView(mView);
				LayoutParams layoutParams1 = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
				mainView.addView(mView, layoutParams1);
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		Calendar c = Calendar.getInstance();
//		int year = c.get(Calendar.YEAR);
//		int month = c.get(Calendar.MONTH) + 1;
//		showYear = year;
//		showMonth = month;
//		if (month == 1) {
//			month = 12;
//			year = year - 1;
//		} else {
//			month = month - 1;
//		}
//
//		// 字符串月份
//		String monthNew = "";
//		if (month < 10) {
//			monthNew = "0" + month;
//		} else {
//			monthNew = month + "";
//		}
//
//		mView = new StatisticsView(this, items, total, type, items_vlaue);
//		mView.setCurrDate(year, month);
//		// mView.setDateChangedListener(this);
		initData();
//		LayoutParams layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
//		mainView.addView(mView, layoutParams);
		setTitleText("分析");
		setTitleLeftButtonBack("", R.drawable.back_selector, buttonClickListener);
		UpdateDateBroadcaset updateDateBroadcaset = new UpdateDateBroadcaset();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("updateDate");
		registerReceiver(updateDateBroadcaset, intentFilter);
		// getAccessPost(year + "-" + monthNew);
		getAccessPost("");

	}

	public void onDateChanged(String startDate, String endDate) {

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

	private String salaryid_str = "";

	private void initData() {
		salaryid_str = getIntent().getStringExtra("salaryid");
	}

	private void getAccessPost(String time) {
		HashMap<String, String> getAccessPost = new HashMap<String, String>();
		// getAccessPost.put("date", time);
		getAccessPost.put("salaryid", salaryid_str);
		getAccessPost.put("userPhone", Constants.mobile);
		getAccessPost.put("partyId", Constants.partyId);
		pd = ProgressDialog.show(this, "", "数据获取中", true, true);
		try {
			ServerAdaptor.getInstance(this).doAction(1, Constants.UrlHead + "client.action.SalaryAction$getPieChartById", getAccessPost, new ServiceSyncListener() {

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
					handler.sendMessage(msg);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class UpdateDateBroadcaset extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String data = intent.getStringExtra("data");
			String[] dataInt = data.split("-");
			showYear = (int) Double.parseDouble(dataInt[0]);
			showMonth = (int) Double.parseDouble(dataInt[1]) + 1;
			getAccessPost(data);
		}

	}

}
