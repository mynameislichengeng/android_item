package com.xguanjia.hx.payroll.piechart;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.selecttime.JudgeDate;
import com.xguanjia.hx.common.selecttime.ScreenInfo;
import com.xguanjia.hx.common.selecttime.WheelMain;
import com.xguanjia.hx.payroll.activity.PayrollActivity;
import com.xguanjia.hx.payroll.activity.PayrolldbActivity;

/**
 * 统计页面
 * 
 * @author tz
 * 
 */
public class StatisticsView extends ViewGroup implements OnClickListener {

	private Context context;

	/** 子View */
	private View view;

	private TextView mLast, mCurrent, mNext;

	/** 保存当前显示的上个月、本月和下个月的月份 几当前年份 */
	private int mLastDate, mCurrDate, mNextDate, mYear, mDay;

	private int mMaxMonth, mMaxYear, mMinMonth, mMinYear;

	private String startDate, endDate;

	private OnDateChangedLinstener mDateChangedListener;

	private PieChartView pieChart;
	private String[] colors = { "#fe5513", "#fe7613", "#fda635", "#fdbd35","#f0d72d",
								"#9dc62f", "#4164fb", "#1570fa", "#03a9d2","#00ce7e",
								"#9dc317", "#ddcb00", "#eea904", "#ff8400","#E56405",
								"#F37233", "#ED5823", "#E94221", "#BD1B13","#E23343" };
	private float[] items;
	private TextView textInfo;
	private float animSpeed = 7f;
	private float total;
	private float[] items_value;
	// 每块扇形代表的类型
	private String[] type;
	
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

	public StatisticsView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StatisticsView(Context context, float[] items, float total,
			String[] type) {
		super(context);
		this.context = context;
		this.items = items;
		this.total = total;
		this.type = type;
		
		initView();
	}
	public StatisticsView(Context context, float[] items, float total,
			String[] type,float[] items_value) {
		super(context);
		this.context = context;
		this.items = items;
		this.total = total;
		this.type = type;
		this.items_value = items_value;
		
		initView();
	}
	
	public void setDrawView(Context context, float[] items, float total,
			String[] type){
		this.context = context;
		this.items = items;
		this.total = total;
		this.type = type;
		intitPieChart();
	}

	private void initView() {

		view = LayoutInflater.from(context).inflate(
				R.layout.statistics_layout, null);

		mLast = (TextView) view.findViewById(R.id.last);
		mCurrent = (TextView) view.findViewById(R.id.curr);

		mNext = (TextView) view.findViewById(R.id.next);
//		mLast.setOnClickListener(this);
//		mCurrent.setOnClickListener(this);
//		mNext.setOnClickListener(this);
		intitPieChart();
		this.addView(view);
		initDate();
	}

	private void intitPieChart() {

		textInfo = (TextView) view.findViewById(R.id.text_item_info);
//		pieChart=new PieChartView(context, null, null, 0, 0, 0, "", 0, 0, 0);
		pieChart = (PieChartView) view.findViewById(R.id.parbar_view);

		pieChart.setAnimEnabled(true);// 是否开启动画
		pieChart.setItemsColors(colors);// 设置各个块的颜色
		pieChart.setActualTotal(total);
		pieChart.setItemsSizes(items);// 设置各个块的值
		pieChart.setRotateSpeed(animSpeed);// 设置旋转速度
		pieChart.setTotal(100);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		pieChart.setRaduis((int) (dm.widthPixels / 2.3));// 设置饼状图半径
		pieChart.setOnItemSelectedListener(new OnPieChartItemSelectedLinstener() {
			public void onPieChartItemSelected(PieChartView view, int position,
					String colorRgb, float size, float rate,
					boolean isFreePart, float rotateTime) {

				try {
					textInfo.setTextColor(Color.parseColor(pieChart
							.getShowItemColor()));
					if (isFreePart) {
					
					} else {
						float percent = (float) (Math.round(size * 100)) / 100;
//						textInfo.setText(Html.fromHtml(type[position]+" 所占比例 " + percent
//								+ "%<br>" + "<font color='black'>"
//								+ (int) Math.round((total * size / 100)) + "元</font>"));
						textInfo.setText(Html.fromHtml(type[position]+" 所占比例 " + percent
								+ "%<br>" + "<font color='black'>"
								+ String.format("%.2f", items_value[position]) + "元</font>"));
						
					}
					if (total > 0)
						textInfo.setVisibility(View.VISIBLE);
					Animation myAnimation_Alpha = new AlphaAnimation(0.1f, 1.0f);
					myAnimation_Alpha.setDuration((int) (3 * rotateTime));
					textInfo.startAnimation(myAnimation_Alpha);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			public void onTriggerClicked() {
//				Toast.makeText(context, "点击了切换按钮!",
//						Toast.LENGTH_SHORT).show();
			}

		});
		pieChart.setShowItem(0, true, true);// 设置显示的块

	}

	/**
	 * 初始化日期
	 */
	private void initDate() {
		Calendar c = Calendar.getInstance();
		mMaxYear = mYear = c.get(Calendar.YEAR);
		mMinMonth = mMaxMonth = mCurrDate = c.get(Calendar.MONTH) + 1;
		mLastDate = mCurrDate - 1;
		mNextDate = mCurrDate + 1;
		mDay = c.get(Calendar.DAY_OF_MONTH);
		mMinYear = mMaxYear - 1;
		freshDate();
	}

	/**
	 * 设置当前日期
	 * 
	 * @param year
	 * @param month
	 */
	public void setCurrDate(int year, int month) {
		mMaxYear = mYear = year;
		mMinYear = mMaxYear - 1;
		mMinMonth = mMaxMonth = mCurrDate = month;
		mNextDate = mCurrDate + 1;
		mLastDate = mCurrDate - 1;
		freshDate();
	}

	/**
	 * 设置日期范围
	 * 
	 * @param mMaxMonth
	 * @param mMaxYear
	 * @param mMinMonth
	 * @param mMinYear
	 */
	public void setDateRange(int mMaxMonth, int mMaxYear, int mMinMonth,
			int mMinYear) {
		this.mMaxMonth = mMaxMonth;
		this.mMaxYear = mMaxYear;
		this.mMinMonth = mMinMonth;
		this.mMinYear = mMinYear;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		View child = getChildAt(0);
		child.layout(l, t, r, b);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
		int measureHeigth = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(measureWidth, measureHeigth);
		for (int i = 0; i < getChildCount(); i++) {
			View v = getChildAt(i);
			int widthSpec = 0;
			int heightSpec = 0;
			LayoutParams params = v.getLayoutParams();
			if (params.width > 0) {
				widthSpec = MeasureSpec.makeMeasureSpec(params.width,
						MeasureSpec.EXACTLY);
			} else if (params.width == -1) {
				widthSpec = MeasureSpec.makeMeasureSpec(measureWidth,
						MeasureSpec.EXACTLY);
			} else if (params.width == -2) {
				widthSpec = MeasureSpec.makeMeasureSpec(measureWidth,
						MeasureSpec.AT_MOST);
			}

			if (params.height > 0) {
				heightSpec = MeasureSpec.makeMeasureSpec(params.height,
						MeasureSpec.EXACTLY);
			} else if (params.height == -1) {
				heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth,
						MeasureSpec.EXACTLY);
			} else if (params.height == -2) {
				heightSpec = MeasureSpec.makeMeasureSpec(measureHeigth,
						MeasureSpec.AT_MOST);
			}
			v.measure(widthSpec, heightSpec);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.last:
			if (mDateChangedListener != null) {
				if (mMinYear >= mYear && mLastDate < mMinMonth) {
//					Toast.makeText(context, "只能查询一年内的数据哦!",
//							Toast.LENGTH_SHORT).show();
					return;
				}
				if (mLastDate == 1) {
					mLastDate = 12;
					mCurrDate--;
					mNextDate--;
				} else if (mLastDate == 12) {
					mLastDate--;
					mCurrDate = 12;
					mNextDate--;
					mYear--;
				} else if (mLastDate == 11) {
					mLastDate--;
					mCurrDate--;
					mNextDate = 12;
				} else {
					mLastDate--;
					mCurrDate--;
					mNextDate--;
				}

				freshDate();

				String startDate = mYear + "-" + mCurrDate + "-" + "1 00:00:00";
				String endDate = mYear + "-" + (mCurrDate + 1) + "-"
						+ "1 00:00:00";

				mDateChangedListener.onDateChanged(startDate, endDate);
				String date="";
				if (mCurrDate < 10) {
					date = mYear + "-" +"0" + mCurrDate;
				} else {
					date = mYear + "-" + mCurrDate;
				}
				Intent intent=new Intent();
				intent.setAction("updateDate");
				intent.putExtra("date", date);
				intent.putExtra("year", mYear);
				intent.putExtra("month", mCurrDate);
				context.sendBroadcast(intent);

			}
			break;

		case R.id.next:
			if (mDateChangedListener != null) {

				if (mMaxYear == mYear && mNextDate > mMaxMonth) {
//					Toast.makeText(context, "还没有这个月的数据哦!",
//							Toast.LENGTH_SHORT).show();
					return;
				}

				if (mNextDate == 12) {
					mLastDate++;
					mCurrDate++;
					mNextDate = 1;
				} else if (mNextDate == 1) {
					mLastDate++;
					mCurrDate = 1;
					mNextDate++;
					mYear++;
				} else if (mNextDate == 2) {
					mLastDate = 1;
					mCurrDate++;
					mNextDate++;
				} else {
					mLastDate++;
					mCurrDate++;
					mNextDate++;
				}
				freshDate();

				String startDate = mYear + "-" + mCurrDate + "-1 00:00:00";
				String endDate = mYear + "-" + (mCurrDate + 1) + "-1 00:00:00";
				mDateChangedListener.onDateChanged(startDate, endDate);
				String date="";
				if (mCurrDate < 10) {
					date = mYear + "-" +"0" + mCurrDate;
				} else {
					date = mYear + "-" + mCurrDate;
				}
				Intent intent=new Intent();
				intent.setAction("updateDate");
				intent.putExtra("date", date);
				intent.putExtra("year", mYear);
				intent.putExtra("month", mCurrDate);
				context.sendBroadcast(intent);
			}
			break;
		case R.id.curr:
			setTime();
			break;
		default:
			break;
		}
	}
	
	
	/*
	 * 设置时间
	 */
	private void setTime() {
		LayoutInflater inflater = LayoutInflater.from(context);
		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
		ScreenInfo screenInfo = new ScreenInfo(context);
		final WheelMain wheelMain = new WheelMain(timepickerview, false, false,true);
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
		if (month == 1) {
			month = 12;
			year = year - 1;
		} else {
			month = month - 1;
		}
		wheelMain.setEND_YEAR(year+10);
		wheelMain.setSTART_YEAR(year-10);
		wheelMain.initDateTimePicker(year, month, day, hour, min, second);
		new AlertDialog.Builder(context).setTitle("选择日期")
				.setView(timepickerview)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent();
						intent.setAction("updateDate");
						intent.putExtra("data", wheelMain.getTime(0));
						context.sendBroadcast(intent);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
	}

	public void freshDate() {
		mLast.setText(mLastDate + "月");
		if (mCurrDate == 1) {
			mCurrDate = 12;
			mYear = mYear - 1;
		} else {
			mCurrDate = mCurrDate - 1;
		}
		mCurrent.setText(mYear + "年" + mCurrDate + "月");
		mNext.setText(mNextDate + "月");
	}

	public float[] getItems() {
		return items;
	}

	public void setItems(float[] items) {
		this.items = items;
		pieChart.setItemsSizes(items);
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		if (total <= 0) {
			pieChart.setVisibility(View.GONE);
			textInfo.setVisibility(View.GONE);
			pieChart.setTotal(0);
		} else {
			pieChart.setVisibility(View.VISIBLE);
			textInfo.setVisibility(View.VISIBLE);
			pieChart.setTotal(100);
			pieChart.setActualTotal(total);
		}
	}

	public void freshView() {
		pieChart.setShowItem(0, true, true);// 设置显示的块
		pieChart.invalidate();
		this.invalidate();
	}

	public void relaseTotal() {
		pieChart.relaseTotal(0);
	}

	public OnDateChangedLinstener getDateChangedListener() {
		return mDateChangedListener;
	}

	public void setDateChangedListener(
			OnDateChangedLinstener mDateChangedListener) {
		this.mDateChangedListener = mDateChangedListener;
	}

	public String[] getType() {
		return type;
	}

	public void setType(String[] type) {
		this.type = type;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
