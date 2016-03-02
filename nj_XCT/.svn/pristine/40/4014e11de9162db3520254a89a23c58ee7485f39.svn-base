package com.xguanjia.hx.common.selecttime;

import java.util.Arrays;
import java.util.List;

import android.view.View;

import com.chinamobile.salary.R;

public class WheelMain {

	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_second;
	private WheelView wv_mins;
	public int screenheight;
	private boolean hasSelectTime;
	private boolean haveDay;
	private boolean haveMonth;
	private static int START_YEAR = 1990, END_YEAR = 2100;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public static int getSTART_YEAR() {
		return START_YEAR;
	}

	public static void setSTART_YEAR(int sTART_YEAR) {
		START_YEAR = sTART_YEAR;
	}

	public static int getEND_YEAR() {
		return END_YEAR;
	}

	public static void setEND_YEAR(int eND_YEAR) {
		END_YEAR = eND_YEAR;
	}

	public WheelMain(View view) {
		super();
		this.view = view;
		hasSelectTime = true;
		setView(view);
	}

	public WheelMain(View view, boolean hasSelectTime,boolean isHaveDay,boolean isHaveMonth) {
		super();
		this.view = view;
		this.hasSelectTime = hasSelectTime;
		this.haveDay=isHaveDay;
		this.haveMonth=isHaveMonth;
		setView(view);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void initDateTimePicker(int year, int month, int day, int h, int m, int s) {
//		 int year = calendar.get(Calendar.YEAR);
		// int month = calendar.get(Calendar.MONTH);
		// int day = calendar.get(Calendar.DATE);
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		final List<String> list_big = Arrays.asList(months_big);
		final List<String> list_little = Arrays.asList(months_little);

		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
//		if(haveDay){
			wv_year.setAdapter(new NumericWheelAdapter(START_YEAR, END_YEAR));// 设置"年"的显示数据
//		}else {
//			wv_year.setAdapter(new NumericWheelAdapter(year, year));// 设置"年"的显示数据
//		}
		wv_year.setCyclic(true);// 可循环滚动
		if(haveMonth){
			wv_year.setLabel("年");// 添加文字
		}
		wv_year.setCurrentItem(year - START_YEAR);// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setAdapter(new NumericWheelAdapter(1, 12));
		wv_month.setCyclic(true);
		wv_month.setLabel("月");
		wv_month.setCurrentItem(month);

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setCyclic(true);
		// 判断大小月及是否闰年,用来确定"日"的数据
		if (list_big.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 31));
		} else if (list_little.contains(String.valueOf(month + 1))) {
			wv_day.setAdapter(new NumericWheelAdapter(1, 30));
		} else {
			// 闰年
			if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
				wv_day.setAdapter(new NumericWheelAdapter(1, 29));
			else
				wv_day.setAdapter(new NumericWheelAdapter(1, 28));
		}
		wv_day.setLabel("日");
		wv_day.setCurrentItem(day - 1);

		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_mins = (WheelView) view.findViewById(R.id.min);
		wv_second = (WheelView) view.findViewById(R.id.sec);
		if (hasSelectTime) {
			wv_hours.setVisibility(View.VISIBLE);
			wv_mins.setVisibility(View.VISIBLE);

			wv_hours.setAdapter(new NumericWheelAdapter(0, 23));
			wv_hours.setCyclic(true);// 可循环滚动
			wv_hours.setLabel("时");// 添加文字
			wv_hours.setCurrentItem(h);

			wv_mins.setAdapter(new NumericWheelAdapter(0, 59));
			wv_mins.setCyclic(true);// 可循环滚动
			wv_mins.setLabel("分");// 添加文字
			wv_mins.setCurrentItem(m);

			wv_second.setAdapter(new NumericWheelAdapter(0, 59));
			wv_second.setCyclic(true);// 可循环滚动
			wv_second.setLabel("秒");// 添加文字
			wv_second.setCurrentItem(s);
		} else {
			if(haveDay){
				wv_day.setVisibility(View.VISIBLE);
			}else {
				if(haveMonth){
					wv_month.setVisibility(View.VISIBLE);
				}else {
					wv_month.setVisibility(View.GONE);
				}
				wv_day.setVisibility(View.GONE);
			}
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			wv_second.setVisibility(View.GONE);
		}

		// 添加"年"监听
		OnWheelChangedListener wheelListener_year = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int year_num = newValue + START_YEAR;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(wv_month.getCurrentItem() + 1))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if ((year_num % 4 == 0 && year_num % 100 != 0) || year_num % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		// 添加"月"监听
		OnWheelChangedListener wheelListener_month = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				int month_num = newValue + 1;
				// 判断大小月及是否闰年,用来确定"日"的数据
				if (list_big.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 31));
				} else if (list_little.contains(String.valueOf(month_num))) {
					wv_day.setAdapter(new NumericWheelAdapter(1, 30));
				} else {
					if (((wv_year.getCurrentItem() + START_YEAR) % 4 == 0 && (wv_year.getCurrentItem() + START_YEAR) % 100 != 0) || (wv_year.getCurrentItem() + START_YEAR) % 400 == 0)
						wv_day.setAdapter(new NumericWheelAdapter(1, 29));
					else
						wv_day.setAdapter(new NumericWheelAdapter(1, 28));
				}
			}
		};
		wv_year.addChangingListener(wheelListener_year);
		wv_month.addChangingListener(wheelListener_month);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 0;
		if (hasSelectTime){
			textSize = (screenheight / 100) * 4;
		}else{
			if(haveDay){
				textSize = (screenheight / 100) * 6;
			}else {
				if(haveMonth){
					textSize = (screenheight / 100) * 8;
				}else {
					textSize = (screenheight / 100) * 10;
				}
			}
		}
			
		wv_day.TEXT_SIZE = textSize;
		wv_month.TEXT_SIZE = textSize;
		wv_year.TEXT_SIZE = textSize;
		wv_hours.TEXT_SIZE = textSize;
		wv_mins.TEXT_SIZE = textSize;
		wv_second.TEXT_SIZE = textSize;

	}

	public String getTime(int num) {
		StringBuffer sb = new StringBuffer();
		if (!hasSelectTime){
			if(!haveMonth){
				StringBuffer date = sb.append((wv_year.getCurrentItem() + START_YEAR));
			}else {
				StringBuffer date = sb.append((wv_year.getCurrentItem() + START_YEAR)+"-");
				if (wv_month.getCurrentItem() > 8) {
					date.append((wv_month.getCurrentItem() + 1));
				} else {
					date.append("0").append((wv_month.getCurrentItem() + 1));
				}
				if(haveDay){
					if (wv_day.getCurrentItem() > 8) {
						date.append("-"+(wv_day.getCurrentItem() + 1));
					} else {
						date.append("-0").append((wv_day.getCurrentItem() + 1));
					}
				}
			}
			
		}
		else if (num == 1) {
			sb.append((wv_year.getCurrentItem() + START_YEAR)).append("-").append((wv_month.getCurrentItem() + 1)).append("-").append((wv_day.getCurrentItem() + 1)).append(" ")
					.append(wv_hours.getCurrentItem()).append(":").append(wv_mins.getCurrentItem()).append(":").append(wv_second.getCurrentItem());
		} 
		else if(num==0)
		{

			StringBuffer date = sb.append((wv_year.getCurrentItem() + START_YEAR));
			if (wv_month.getCurrentItem() > 9) {
				date.append((wv_month.getCurrentItem() + 1));
			} else {
				date.append("0").append((wv_month.getCurrentItem() + 1));
			}
			if (wv_day.getCurrentItem() > 9) {
				date.append((wv_day.getCurrentItem() + 1));
			} else {
				date.append("0").append((wv_day.getCurrentItem() + 1));
			}
		}
		else {
			StringBuffer date = sb.append((wv_year.getCurrentItem() + START_YEAR));
			if (wv_month.getCurrentItem() > 9) {
				date.append((wv_month.getCurrentItem() + 1));
			} else {
				date.append("0").append((wv_month.getCurrentItem() + 1));
			}
			if (wv_day.getCurrentItem() > 9) {
				date.append((wv_day.getCurrentItem() + 1));
			} else {
				date.append("0").append((wv_day.getCurrentItem() + 1));
			}
			if(wv_mins.getCurrentItem()>9)
			{
				date.append(wv_mins.getCurrentItem());
			}
			else {
				date.append("0").append(wv_mins.getCurrentItem());
			}
			if(wv_second.getCurrentItem()>9)
			{
				date.append(wv_second.getCurrentItem());
			}
			else {
				date.append("0").append(wv_second.getCurrentItem());
			}
		}
		System.out.println("获取时间--->" + sb.toString());
		return sb.toString();
	}
}
