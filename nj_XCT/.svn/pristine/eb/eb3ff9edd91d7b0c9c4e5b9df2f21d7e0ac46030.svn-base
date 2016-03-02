package com.xguanjia.hx.payroll.activity;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.payroll.bean.GroupListBean;
import com.xguanjia.hx.payroll.bean.PayRollDetail;
import com.xguanjia.hx.payroll.bean.PayRollSummary;
import com.xguanjia.hx.payroll.bean.TempListBean;

public class PayrolldbActivity extends BaseActivity {

	private TextView nowMonthTv, lastMonthTv;
	private LinearLayout detailLinear;
	private ProgressDialog pd;// 提示框
	private PayRollSummary payRollBean = new PayRollSummary();
	private LinearLayout.LayoutParams params;
	private String flag = "1";// 判断groupListBeans里面是否有对应的值 1不存在 2存在
	private String selectTime = "";
	private PayRollSummary payRollBeanLast = new PayRollSummary();
	// handle加载数据
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case 1:
				String returnString = (String) msg.obj;
				payRollBean = new Gson().fromJson(returnString, new TypeToken<PayRollSummary>() {
				}.getType());
				List<GroupListBean> groupListBeans = payRollBean.getGrouplist();
				List<TempListBean> tempListBeans = payRollBean.getTemplist();
				List<PayRollDetail> listBeans = payRollBean.getList();
				List<PayRollDetail> listBeansGet = payRollBeanLast.getList();
				if (listBeans.size() == 0) {
					Toast.makeText(PayrolldbActivity.this, "对比的月份无数据，请重新选择", Toast.LENGTH_SHORT).show();
					finish();
					return false;
				}

				for (int i = 0; i < groupListBeans.size(); i++) {
					TextView tv = newTextView(groupListBeans.get(i).getGroupingName());
					detailLinear.addView(tv, params);
					for (int j = 0; j < tempListBeans.size(); j++) {
						if (groupListBeans.get(i).getSalaryGroupId().equals(tempListBeans.get(j).getSalaryGroupId())) {
							for (int k = 0; k < 1; k++) {
								RelativeLayout relativeLayout = null;
								if (tempListBeans.get(j).getBasisName().equals("basisId")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), listBeans.get(k).getBasisId());
								} else if (tempListBeans.get(j).getBasisName().equals("userName")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), listBeans.get(k).getUserName());
								} else if (tempListBeans.get(j).getBasisName().equals("userPhone")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), listBeans.get(k).getUserPhone());
								} else if (tempListBeans.get(j).getBasisName().equals("date")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), listBeansGet.get(k).getDate() + "/" + listBeans.get(k).getDate());
								} else if (tempListBeans.get(j).getBasisName().equals("dept")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), listBeans.get(k).getDept());
								} else if (tempListBeans.get(j).getBasisName().equals("wages")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getWages(), "" + listBeansGet.get(k).getWages());
								} else if (tempListBeans.get(j).getBasisName().equals("value1")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue1(), "" + listBeansGet.get(k).getValue1());
								} else if (tempListBeans.get(j).getBasisName().equals("value2")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue2(), "" + listBeansGet.get(k).getValue2());
								} else if (tempListBeans.get(j).getBasisName().equals("value3")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue3(), "" + listBeansGet.get(k).getValue3());
								} else if (tempListBeans.get(j).getBasisName().equals("value4")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue4(), "" + listBeansGet.get(k).getValue4());
								} else if (tempListBeans.get(j).getBasisName().equals("value5")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue5(), "" + listBeansGet.get(k).getValue5());
								} else if (tempListBeans.get(j).getBasisName().equals("value6")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue6(), "" + listBeansGet.get(k).getValue6());
								} else if (tempListBeans.get(j).getBasisName().equals("value7")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue7(), "" + listBeansGet.get(k).getValue7());
								} else if (tempListBeans.get(j).getBasisName().equals("value8")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue8(), "" + listBeansGet.get(k).getValue8());
								} else if (tempListBeans.get(j).getBasisName().equals("value9")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue9(), "" + listBeansGet.get(k).getValue9());
								} else if (tempListBeans.get(j).getBasisName().equals("value10")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue10(), "" + listBeansGet.get(k).getValue10());
								} else if (tempListBeans.get(j).getBasisName().equals("value11")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue11(), "" + listBeansGet.get(k).getValue11());
								} else if (tempListBeans.get(j).getBasisName().equals("value12")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue12(), "" + listBeansGet.get(k).getValue12());
								} else if (tempListBeans.get(j).getBasisName().equals("value13")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue13(), "" + listBeansGet.get(k).getValue13());
								} else if (tempListBeans.get(j).getBasisName().equals("value14")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue14(), "" + listBeansGet.get(k).getValue14());
								} else if (tempListBeans.get(j).getBasisName().equals("value15")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue15(), "" + listBeansGet.get(k).getValue15());
								} else if (tempListBeans.get(j).getBasisName().equals("value16")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue16(), "" + listBeansGet.get(k).getValue16());
								} else if (tempListBeans.get(j).getBasisName().equals("value17")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue17(), "" + listBeansGet.get(k).getValue17());
								} else if (tempListBeans.get(j).getBasisName().equals("value18")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue18(), "" + listBeansGet.get(k).getValue18());
								} else if (tempListBeans.get(j).getBasisName().equals("value19")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue19(), "" + listBeansGet.get(k).getValue19());
								} else if (tempListBeans.get(j).getBasisName().equals("value20")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue20(), "" + listBeansGet.get(k).getValue20());
								} else if (tempListBeans.get(j).getBasisName().equals("value21")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue21(), "" + listBeansGet.get(k).getValue21());
								} else if (tempListBeans.get(j).getBasisName().equals("value22")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue22(), "" + listBeansGet.get(k).getValue22());
								} else if (tempListBeans.get(j).getBasisName().equals("value23")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue23(), "" + listBeansGet.get(k).getValue23());
								} else if (tempListBeans.get(j).getBasisName().equals("value24")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue24(), "" + listBeansGet.get(k).getValue24());
								} else if (tempListBeans.get(j).getBasisName().equals("value25")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue25(), "" + listBeansGet.get(k).getValue25());
								} else if (tempListBeans.get(j).getBasisName().equals("value26")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue26(), "" + listBeansGet.get(k).getValue26());
								} else if (tempListBeans.get(j).getBasisName().equals("value27")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue27(), "" + listBeansGet.get(k).getValue27());
								} else if (tempListBeans.get(j).getBasisName().equals("value28")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue28(), "" + listBeansGet.get(k).getValue28());
								} else if (tempListBeans.get(j).getBasisName().equals("value29")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue29(), "" + listBeansGet.get(k).getValue29());
								} else if (tempListBeans.get(j).getBasisName().equals("value30")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue30(), "" + listBeansGet.get(k).getValue30());
								} else if (tempListBeans.get(j).getBasisName().equals("value31")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue31(), "" + listBeansGet.get(k).getValue31());
								} else if (tempListBeans.get(j).getBasisName().equals("value32")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue32(), "" + listBeansGet.get(k).getValue32());
								} else if (tempListBeans.get(j).getBasisName().equals("value33")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue33(), "" + listBeansGet.get(k).getValue33());
								} else if (tempListBeans.get(j).getBasisName().equals("value34")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue34(), "" + listBeansGet.get(k).getValue34());
								} else if (tempListBeans.get(j).getBasisName().equals("value35")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue35(), "" + listBeansGet.get(k).getValue35());
								} else if (tempListBeans.get(j).getBasisName().equals("value36")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue36(), "" + listBeansGet.get(k).getValue36());
								} else if (tempListBeans.get(j).getBasisName().equals("value37")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue37(), "" + listBeansGet.get(k).getValue37());
								} else if (tempListBeans.get(j).getBasisName().equals("value38")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue38(), "" + listBeansGet.get(k).getValue38());
								} else if (tempListBeans.get(j).getBasisName().equals("value39")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue39(), "" + listBeansGet.get(k).getValue39());
								} else if (tempListBeans.get(j).getBasisName().equals("value40")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue40(), "" + listBeansGet.get(k).getValue40());
								} else if (tempListBeans.get(j).getBasisName().equals("value41")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue41(), "" + listBeansGet.get(k).getValue41());
								} else if (tempListBeans.get(j).getBasisName().equals("value42")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue42(), "" + listBeansGet.get(k).getValue42());
								} else if (tempListBeans.get(j).getBasisName().equals("value43")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue43(), "" + listBeansGet.get(k).getValue43());
								} else if (tempListBeans.get(j).getBasisName().equals("value44")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue44(), "" + listBeansGet.get(k).getValue44());
								} else if (tempListBeans.get(j).getBasisName().equals("value45")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue45(), "" + listBeansGet.get(k).getValue45());
								} else if (tempListBeans.get(j).getBasisName().equals("value46")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue46(), "" + listBeansGet.get(k).getValue46());
								} else if (tempListBeans.get(j).getBasisName().equals("value47")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue47(), "" + listBeansGet.get(k).getValue47());
								} else if (tempListBeans.get(j).getBasisName().equals("value48")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue48(), "" + listBeansGet.get(k).getValue48());
								} else if (tempListBeans.get(j).getBasisName().equals("value49")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue49(), "" + listBeansGet.get(k).getValue49());
								} else if (tempListBeans.get(j).getBasisName().equals("value50")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue50(), "" + listBeansGet.get(k).getValue50());
								} else if (tempListBeans.get(j).getBasisName().equals("value51")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue51(), "" + listBeansGet.get(k).getValue51());
								} else if (tempListBeans.get(j).getBasisName().equals("value52")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue52(), "" + listBeansGet.get(k).getValue52());
								} else if (tempListBeans.get(j).getBasisName().equals("value53")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue53(), "" + listBeansGet.get(k).getValue53());
								} else if (tempListBeans.get(j).getBasisName().equals("value54")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue54(), "" + listBeansGet.get(k).getValue54());
								} else if (tempListBeans.get(j).getBasisName().equals("value55")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue55(), "" + listBeansGet.get(k).getValue55());
								} else if (tempListBeans.get(j).getBasisName().equals("value56")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue56(), "" + listBeansGet.get(k).getValue56());
								} else if (tempListBeans.get(j).getBasisName().equals("value57")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue57(), "" + listBeansGet.get(k).getValue57());
								} else if (tempListBeans.get(j).getBasisName().equals("value58")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue58(), "" + listBeansGet.get(k).getValue58());
								} else if (tempListBeans.get(j).getBasisName().equals("value59")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue59(), "" + listBeansGet.get(k).getValue59());
								} else if (tempListBeans.get(j).getBasisName().equals("value60")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue60(), "" + listBeansGet.get(k).getValue60());
								} else if (tempListBeans.get(j).getBasisName().equals("value61")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue61(), "" + listBeansGet.get(k).getValue61());
								} else if (tempListBeans.get(j).getBasisName().equals("value62")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue62(), "" + listBeansGet.get(k).getValue62());
								} else if (tempListBeans.get(j).getBasisName().equals("value63")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue63(), "" + listBeansGet.get(k).getValue63());
								} else if (tempListBeans.get(j).getBasisName().equals("value64")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue64(), "" + listBeansGet.get(k).getValue64());
								} else if (tempListBeans.get(j).getBasisName().equals("value65")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue65(), "" + listBeansGet.get(k).getValue65());
								} else if (tempListBeans.get(j).getBasisName().equals("value66")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue66(), "" + listBeansGet.get(k).getValue66());
								} else if (tempListBeans.get(j).getBasisName().equals("value67")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue67(), "" + listBeansGet.get(k).getValue67());
								} else if (tempListBeans.get(j).getBasisName().equals("value68")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue68(), "" + listBeansGet.get(k).getValue68());
								} else if (tempListBeans.get(j).getBasisName().equals("value69")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue69(), "" + listBeansGet.get(k).getValue69());
								} else if (tempListBeans.get(j).getBasisName().equals("value70")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue70(), "" + listBeansGet.get(k).getValue70());
								} else if (tempListBeans.get(j).getBasisName().equals("value71")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue71(), "" + listBeansGet.get(k).getValue71());
								} else if (tempListBeans.get(j).getBasisName().equals("value72")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue72(), "" + listBeansGet.get(k).getValue72());
								} else if (tempListBeans.get(j).getBasisName().equals("value73")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue73(), "" + listBeansGet.get(k).getValue73());
								} else if (tempListBeans.get(j).getBasisName().equals("value74")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue74(), "" + listBeansGet.get(k).getValue74());
								} else if (tempListBeans.get(j).getBasisName().equals("value75")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue75(), "" + listBeansGet.get(k).getValue75());
								} else if (tempListBeans.get(j).getBasisName().equals("value76")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue76(), "" + listBeansGet.get(k).getValue76());
								} else if (tempListBeans.get(j).getBasisName().equals("value77")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue77(), "" + listBeansGet.get(k).getValue77());
								} else if (tempListBeans.get(j).getBasisName().equals("value78")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue78(), "" + listBeansGet.get(k).getValue78());
								} else if (tempListBeans.get(j).getBasisName().equals("value79")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue79(), "" + listBeansGet.get(k).getValue79());
								} else if (tempListBeans.get(j).getBasisName().equals("value80")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue80(), "" + listBeansGet.get(k).getValue80());
								} else if (tempListBeans.get(j).getBasisName().equals("value81")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue81(), "" + listBeansGet.get(k).getValue81());
								} else if (tempListBeans.get(j).getBasisName().equals("value82")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue82(), "" + listBeansGet.get(k).getValue82());
								} else if (tempListBeans.get(j).getBasisName().equals("value83")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue83(), "" + listBeansGet.get(k).getValue83());
								} else if (tempListBeans.get(j).getBasisName().equals("value84")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue84(), "" + listBeansGet.get(k).getValue84());
								} else if (tempListBeans.get(j).getBasisName().equals("value85")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue85(), "" + listBeansGet.get(k).getValue85());
								} else if (tempListBeans.get(j).getBasisName().equals("value86")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue86(), "" + listBeansGet.get(k).getValue86());
								} else if (tempListBeans.get(j).getBasisName().equals("value87")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue87(), "" + listBeansGet.get(k).getValue87());
								} else if (tempListBeans.get(j).getBasisName().equals("value88")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue88(), "" + listBeansGet.get(k).getValue88());
								} else if (tempListBeans.get(j).getBasisName().equals("value89")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue89(), "" + listBeansGet.get(k).getValue89());
								} else if (tempListBeans.get(j).getBasisName().equals("value90")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue90(), "" + listBeansGet.get(k).getValue90());
								} else if (tempListBeans.get(j).getBasisName().equals("value91")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue91(), "" + listBeansGet.get(k).getValue91());
								} else if (tempListBeans.get(j).getBasisName().equals("value92")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue92(), "" + listBeansGet.get(k).getValue92());
								} else if (tempListBeans.get(j).getBasisName().equals("value93")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue93(), "" + listBeansGet.get(k).getValue93());
								} else if (tempListBeans.get(j).getBasisName().equals("value94")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue94(), "" + listBeansGet.get(k).getValue94());
								} else if (tempListBeans.get(j).getBasisName().equals("value95")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue95(), "" + listBeansGet.get(k).getValue95());
								} else if (tempListBeans.get(j).getBasisName().equals("value96")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue96(), "" + listBeansGet.get(k).getValue96());
								} else if (tempListBeans.get(j).getBasisName().equals("value97")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue97(), "" + listBeansGet.get(k).getValue97());
								} else if (tempListBeans.get(j).getBasisName().equals("value98")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue98(), "" + listBeansGet.get(k).getValue98());
								} else if (tempListBeans.get(j).getBasisName().equals("value99")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue99(), "" + listBeansGet.get(k).getValue99());
								} else if (tempListBeans.get(j).getBasisName().equals("value100")) {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "" + listBeans.get(k).getValue100(), "" + listBeansGet.get(k).getValue100());
								} else {
									relativeLayout = newRelativeLayout(tempListBeans.get(j).getSalaryFieldName(), "0", "0");
								}
								detailLinear.addView(relativeLayout, params);
								flag = "2";
							}
						}
					}

					if (flag.equals("1")) {
						detailLinear.removeViewAt(detailLinear.getChildCount() - 1);
					}
					flag = "1";
				}
				break;
			case 2:
				String returnMessage = (String) msg.obj;
				Toast.makeText(PayrolldbActivity.this, returnMessage, Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
	}

	// 初始化界面
	private void initView() {
		View view = getLayoutInflater().inflate(R.layout.payrolldb_activity, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(view, layoutParams);
		this.setTitleText("工资对比");
		setTitleLeftButtonBack("", R.drawable.back_selector, this);
		nowMonthTv = (TextView) view.findViewById(R.id.nowmonthTv);
		lastMonthTv = (TextView) view.findViewById(R.id.lastmonthTv);
		detailLinear = (LinearLayout) view.findViewById(R.id.llDetial);
		initDate();
	}

	private void initDate() {
		payRollBeanLast = (PayRollSummary) getIntent().getSerializableExtra("payRollBean");
		selectTime = getIntent().getStringExtra("date");
		String selectMonth = selectTime.split("-")[1];
		String nowMonth = getIntent().getStringExtra("nowDate").split("-")[1];
		nowMonthTv.setText(getMonth(nowMonth));
		lastMonthTv.setText(getMonth(selectMonth));
		params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		getAccessPost(selectTime);
	}

	private String getMonth(String month) {
		String months = "";
		if (month.equals("01")) {
			months = "一月";
		} else if (month.equals("02")) {
			months = "二月";
		} else if (month.equals("03")) {
			months = "三月";
		} else if (month.equals("04")) {
			months = "四月";
		} else if (month.equals("05")) {
			months = "五月";
		} else if (month.equals("06")) {
			months = "六月";
		} else if (month.equals("07")) {
			months = "七月";
		} else if (month.equals("08")) {
			months = "八月";
		} else if (month.equals("09")) {
			months = "九月";
		} else if (month.equals("10")) {
			months = "十月";
		} else if (month.equals("11")) {
			months = "十一月";
		} else if (month.equals("12")) {
			months = "十二月";
		}
		return months;
	}

	private void getAccessPost(String selectTime) {
		HashMap<String, String> getAccessPost = new HashMap<String, String>();
		getAccessPost.put("date", selectTime);
		getAccessPost.put("userPhone", Constants.mobile);
		getAccessPost.put("partyId", Constants.partyId);
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

	private RelativeLayout newRelativeLayout(String name, String vaule, String vauledb) {
		View v = getLayoutInflater().inflate(R.layout.layout_text_detialdb, null);
		TextView payrollNameTv = (TextView) v.findViewById(R.id.payrollNameTv);
		TextView payrollValueTv = (TextView) v.findViewById(R.id.payrollValueTv);
		TextView payrollValuedbTv = (TextView) v.findViewById(R.id.payrollValuedbTv);
		TextView resultTv = (TextView) v.findViewById(R.id.resultTv);

		payrollNameTv.setText(name);
		payrollValueTv.setText(vaule);
		payrollValuedbTv.setText(vauledb);
		if (vauledb.length() != 1 && vaule.length() != 1) {
			int vauleI = Integer.parseInt(vaule.substring(0));
			int vauledbI = Integer.parseInt(vauledb.substring(0));
			int vauleDistance = vauledbI - vauleI;
			double distance = 0.00;
			if (vauleDistance > 0) {
				distance = (double) vauleDistance / (double) vauledbI;
				Drawable left = getResources().getDrawable(R.drawable.home_up);
				left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
				resultTv.setCompoundDrawables(left, null, null, null);
				resultTv.setTextColor(Color.parseColor("#FE7613"));
			} else if (vauleDistance < 0) {
				distance = (double) Math.abs(vauleDistance) / (double) vauledbI;

				Drawable left = getResources().getDrawable(R.drawable.home_down);
				left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());
				resultTv.setCompoundDrawables(left, null, null, null);
				resultTv.setTextColor(Color.parseColor("#FF3000"));
			} else {
				distance = 0.0;
			}
			String distanceString = String.valueOf(distance);
			if (distanceString.length() > 4) {
				resultTv.setText(distanceString.substring(0, 4) + "%");
			} else {
				if (distanceString.length() == 3) {
					resultTv.setText(distanceString + "0%");
				} else if (distanceString.length() == 1) {
					resultTv.setText(distanceString + ".00%");
				} else {
					resultTv.setText(distanceString + "%");
				}
			}
		} else {
			resultTv.setText("0.00%");
		}
		return (RelativeLayout) v;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UEProbAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
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
		case R.id.title_leftBtnBack:
			finish();
			break;

		default:
			break;
		}
	}
}
