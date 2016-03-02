package com.xguanjia.hx.payroll.activity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.selecttime.JudgeDate;
import com.xguanjia.hx.common.selecttime.ScreenInfo;
import com.xguanjia.hx.common.selecttime.WheelMain;
import com.xguanjia.hx.common.util.MyDimensAdapter;

public class AlertDialogActivity extends Activity implements OnClickListener {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy");// 日期控件
	private String year_str, selectyearTv;
	private Button btn_sure, btn_cancle;
	private WheelMain wheelMain;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = LayoutInflater.from(AlertDialogActivity.this);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		//
		btn_sure = (Button) timepickerview.findViewById(R.id.btn_home_sel_sure);
		btn_cancle = (Button) timepickerview
				.findViewById(R.id.btn_home_sel_cancle);
		btn_sure.setOnClickListener(this);
		btn_cancle.setOnClickListener(this);
		//
		ScreenInfo screenInfo = new ScreenInfo(AlertDialogActivity.this);
		wheelMain = new WheelMain(timepickerview, false, false, false);
		wheelMain.screenheight = MyDimensAdapter.setCurrentSize(650);
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

		setContentView(timepickerview);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_home_sel_sure:
			Intent intent = new Intent();
			intent.putExtra("re_date", wheelMain.getTime(0));
			setResult(101, intent);
			finish();
			break;
		case R.id.btn_home_sel_cancle:
			finish();
			break;
		default:
			break;
		}

	}

}
