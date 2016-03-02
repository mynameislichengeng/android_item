package com.xguanjia.hx.setting.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.setting.activity.adapter.IntroTeamAdapter;
import com.xguanjia.hx.setting.activity.bean.IntroTeamBean;

public class IntroTeamActivity extends BaseActivity {

	private ListView measuredListView;
	private IntroTeamAdapter introTeamAdapter;
	private List<IntroTeamBean> introTeamBeans=new ArrayList<IntroTeamBean>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View set_titleView = getLayoutInflater().inflate(
				R.layout.activity_introteam, null);
		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.mainView.addView(set_titleView,layoutParams);
		setTitleText("产品团队");
		setTitleLeftButtonBack("", R.drawable.back_selector, buttonClickListener);
		measuredListView=(ListView)set_titleView.findViewById(R.id.listview);
		introTeamAdapter=new IntroTeamAdapter(this);
		measuredListView.setAdapter(introTeamAdapter);
		setDataChange();
		introTeamAdapter.setDataChanged(introTeamBeans);
	}
	
	private void setDataChange(){
		IntroTeamBean introTeamBean=new IntroTeamBean();
		introTeamBean.setName("孙晓星");
		introTeamBean.setIntroduce("产品策划：针对市场需求，策划产品方向、目标用户、以及产品开发的整体思路");
		introTeamBeans.add(introTeamBean);
		IntroTeamBean introTeamBean1=new IntroTeamBean();
		introTeamBean1.setName("包岩");
		introTeamBean1.setIntroduce("产品设计：用户需求调研、分析、设计，结合用户需求持续对产品进行迭代优化和升级");
		introTeamBeans.add(introTeamBean1);
		IntroTeamBean introTeamBean2=new IntroTeamBean();
		introTeamBean2.setName("陈筱丰");
		introTeamBean2.setIntroduce("产品管理：跟踪分析产品各项数据和用户反馈，跟进产品实施效果以及业务发展状况");
		introTeamBeans.add(introTeamBean2);
		IntroTeamBean introTeamBean3=new IntroTeamBean();
		introTeamBean3.setName("姚洁");
		introTeamBean3.setIntroduce("产品测试：制定、编写软件测试方案与计划、问题管理跟踪，软件bug的跟踪处理");
		introTeamBeans.add(introTeamBean3);
		IntroTeamBean introTeamBean4=new IntroTeamBean();
		introTeamBean4.setName("徐姝婷");
		introTeamBean4.setIntroduce("用户体验研究：制定移动产品设计规范，产品交互体验，参与用户研究");
		introTeamBeans.add(introTeamBean4);
	}
	
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtnBack:
				IntroTeamActivity.this.finish();
				break;
			default:
				break;
			}
		};

	};





}
