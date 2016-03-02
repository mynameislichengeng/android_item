package com.xguanjia.hx.payroll.view;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xguanjia.hx.common.AppUtils;
import com.xguanjia.hx.common.Constants;
import com.chinamobile.salary.R;

/**
 * 
 * @author huke
 * 
 * 说明：自定义控件--绘制柱状图
 * 
 * 日期：2013-04-23
 *
 */
public class BarChartView extends View {
	
	private static final String TAG = "BarChartView";
	
	private String m_strFormTitle = "";
	private String m_strMaxItemKey;
	
	//private String[] m_seriesNames;   //数据记录集Key
	private String[] m_xValueNames = {"1","2","3","4","5","6","7","8","9","10","11","12"};  //X轴刻度值
	private List<String> m_dateTimeList = new ArrayList<String>();
	
	private int m_iFormWidth;   //图表的宽度
	private int m_iFormHeight;    //图表的高度
	private int m_iOffsetX;
	private int m_iOffsetY;
	private int m_iXScaleInterval;   //X轴刻度间间隔
	private int m_iXpadding = 18;
	private int m_iYInterval;   //Y轴横向虚线间的间隔
	private int m_iCount;   //数据记录数
	private int m_iUnitValue;   //单条记录数值
	private int m_iBarWidth;   //柱状图中矩形的宽度
	private int m_iBarHeight;   //柱状图中矩形的高度
	private int m_iStartPosition;   //矩形的起始位置
	private int m_iBarInterval;  //矩形间的间隔
	
	private List<HashMap<String, Object>> m_hourRecordList;
	
	private float m_fMin;
	private float m_fMax;
	private float m_fYmarkers;
	
	private Paint m_formPaint;  //绘制图表的画笔
	
	private int m_iXYLineColor = Color.BLACK;   //XY坐标轴颜色
	private int m_iFrameColor = Color.argb(0, 220, 228, 234);   //图表边框颜色
	private int m_iBgColor = Color.argb(0, 220, 228, 234);   //图表背景颜色
	
	private Bitmap m_redBitmap;   //红色矩形柱
	private Bitmap m_grayBitmap;  // 灰色矩形柱
	private Bitmap m_yellowBitmap;   //黄色矩形柱 
	private Bitmap m_greenBitmap;   //绿色矩形柱
	
	private float m_fMaxValue = 0;   //传递过来24小时用电值中最大值
	private float[] m_fValues;
	private float[] m_fYValues = new float[4];
	
	private Context context;
	
	public final static int[] platterTable = new int[]{Color.RED, Color.BLUE, Color.GREEN, 
		Color.YELLOW, Color.CYAN};
	
	private DataSeries series;
	
	public BarChartView(Context context, List<HashMap<String, Object>> recordsList) {
		super(context);
		this.context = context;
		m_hourRecordList = recordsList;
		m_fValues = new float[recordsList.size()];
		for (int i = 0; i < recordsList.size(); i++) {
			m_fValues[i] = (Float) recordsList.get(i).get("value");
			Log.e("BarChartView", "获取到的小时记录值：" + m_fValues[i]);
			
			String strDateTime = (String) recordsList.get(i).get("DateTime");
			m_dateTimeList.add(strDateTime);
			Log.e("BarChartView", "获取到的每小时时间为：" + strDateTime);
		}
		//求最大值
		/**
		for (int i = 0; i < m_fValues.length; i++) {
			if(m_fValues[i] > m_fMaxValue) {
				m_fMaxValue = m_fValues[i];
			}
		}
		Log.e("BarChartView", "求最大值：" + m_fMaxValue);
		if(m_fMaxValue == 0) {
			m_fMaxValue = 0.2f;
		}
		m_fMaxValue = m_fMaxValue;
		float fEverValue = m_fMaxValue / 4;
		fEverValue = (float)(Math.round(fEverValue*100))/100;
		for (int i = 0; i < m_fYValues.length; i++) {
			m_fYValues[i] = fEverValue * (i + 1);
		}
		**/
		//y轴坐标值
		m_fYValues[0] = 2000;
		m_fYValues[1] = 4000;
		m_fYValues[2] = 6000;
		m_fYValues[3] = 8000;
		
		if(m_dateTimeList.size() == 24) {
			changeXValue(m_dateTimeList);
		} else {
			Log.e("BarChartView", "没有获取到正确的实时记录，使用默认时间");
		}
	}
	
	public BarChartView(Context context, AttributeSet atts) {
		super(context, atts);
	}

	public BarChartView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		
		InputStream inputStream = getResources().openRawResource(R.drawable.bar_yellow);
		BitmapDrawable bmpDrawable = new BitmapDrawable(inputStream);		
		m_yellowBitmap = bmpDrawable.getBitmap();
		
		inputStream = getResources().openRawResource(R.drawable.bar_gray);
		bmpDrawable = new BitmapDrawable(inputStream);
		m_grayBitmap = bmpDrawable.getBitmap();
		
		inputStream = getResources().openRawResource(R.drawable.bar_red);
		bmpDrawable = new BitmapDrawable(inputStream);
		m_redBitmap = bmpDrawable.getBitmap();
		
		inputStream = getResources().openRawResource(R.drawable.bar_green);
		bmpDrawable = new BitmapDrawable(inputStream);
		m_greenBitmap = bmpDrawable.getBitmap();
		
		//根据设备屏幕宽度和高度设置报表的宽度和高度
		int iWindowWidth = Constants.width;
		int iWindowHeight = Constants.height;
		if(iWindowHeight < 800) {
			m_iFormWidth = (int) (iWindowWidth * 0.84);
			m_iFormHeight = (int) (iWindowHeight * 0.18);
		} else {
			m_iFormWidth = (int) (iWindowWidth * 0.84);
			m_iFormHeight = (int) (iWindowHeight * 0.26);
		}
		
		m_formPaint = new Paint();
		//绘制柱状图边框颜色
		m_formPaint.setColor(m_iFrameColor);
		m_formPaint.setStrokeWidth(2);
		//绘制柱状图背景颜色
		canvas.drawRect(0, 0, m_iFormWidth, m_iFormHeight, m_formPaint);
		m_formPaint.setColor(m_iBgColor);
		m_formPaint.setStrokeWidth(0);
		canvas.drawRect(2, 2, m_iFormWidth - 2, m_iFormHeight - 2, m_formPaint);
		
		m_iOffsetX = (int) (m_iFormWidth * 0.1);
		m_iOffsetY = (int) (m_iFormHeight * 0.1);
		
		//设置Y轴虚线间间隔
		m_iYInterval = (m_iFormHeight - m_iOffsetY) / 5;
		//设置X轴刻度间间隔
		m_iXScaleInterval = (m_iFormWidth - m_iOffsetX) / 8;
		
		//绘制X轴,Y轴
		m_formPaint.setColor(m_iXYLineColor);
		m_formPaint.setStrokeWidth(2);		
		canvas.drawLine(2 + m_iOffsetX, m_iFormHeight - 2 - m_iOffsetY, m_iFormWidth - 2, 
				m_iFormHeight - 2 - m_iOffsetY, m_formPaint);    //X轴
		canvas.drawLine(2 + m_iOffsetX, m_iFormHeight - 2 - m_iOffsetY, 2 + m_iOffsetX, 2, m_formPaint);   //左侧Y轴
		canvas.drawLine(m_iFormWidth - 2, m_iFormHeight - 2 - m_iOffsetY, m_iFormWidth - 2, 2, m_formPaint);  //右侧Y轴
		
		//设置X轴和Y轴的名称
		Paint textPaint = new Paint();
		if(iWindowHeight < 800) {
			textPaint.setTextSize(10);
			canvas.drawText("", m_iOffsetX - 20, 15, textPaint);
			canvas.drawText("", m_iOffsetX - 20, m_iFormHeight - 2 - m_iOffsetY, textPaint);
		} else {
			textPaint.setTextSize(17);
			canvas.drawText("", m_iOffsetX - 60, 15, textPaint);
			canvas.drawText("", m_iOffsetX - 60, m_iFormHeight - 2 - m_iOffsetY, textPaint);
		}	
		
		m_formPaint.setColor(Color.BLACK);
		m_formPaint.setAntiAlias(true);   //设置抗锯齿
		m_formPaint.setStyle(Style.FILL);   //设置填充属性
		canvas.drawText(m_strFormTitle, (m_iFormWidth - 2) / 4, 30, m_formPaint);  //设置图表名称
		
		if(m_hourRecordList != null) {
			//获取记录数
			//m_iCount = series.getSeriesCount();
			m_iCount = m_hourRecordList.size();
			//int iXunit = (m_iFormWidth - 2 - m_iOffsetX) / m_iCount;
			//m_seriesNames = series.getSeriesKeys();
			
			/**
			 * 绘制X轴的刻度值
			 */
			Paint xTextPaint = new Paint();
			if(iWindowHeight < 800) {
				xTextPaint.setTextSize(10);
			} else {
				xTextPaint.setTextSize(15);
			}
			
			for (int i = 0; i < m_xValueNames.length; i++) {
				canvas.drawText(m_xValueNames[i], m_iOffsetX - 15  + i * m_iXScaleInterval,
						m_iFormHeight - m_iOffsetY + m_iXpadding, xTextPaint);
			}
			
			/**
			 * 绘制Y轴的刻度值
			 */
			Paint yTextPaint = new Paint();
			if(iWindowHeight < 800) {
				yTextPaint.setTextSize(10);
				for(int i = 0; i < 4; i++) {
					if(i != 0) {
						canvas.drawText(String.valueOf(m_fYValues[3 - i]),  m_iOffsetX - 20,  
								m_iYInterval + i * m_iYInterval, yTextPaint);
					}
				}
			} else {
				yTextPaint.setTextSize(15);
				for(int i = 0; i < 4; i++) {
					canvas.drawText(String.valueOf(m_fYValues[3 - i]),  m_iOffsetX - 30,  
							m_iYInterval + i * m_iYInterval, yTextPaint);
				}
			}
			

			//设置获取到的数据的最大值与最小值 
			setMinMax();
			/**
			 * 绘制纵向的背景线
			 */
			m_formPaint.setStyle(Style.FILL);
			m_formPaint.setStrokeWidth(1);
			m_formPaint.setColor(Color.GRAY);
			for(int i = 1; i <= 7; i++) {
				canvas.drawLine(m_iOffsetX + 2 + i * m_iXScaleInterval, m_iFormHeight - 2 - m_iOffsetY, 
						m_iOffsetX + 2 + i * m_iXScaleInterval, 2, m_formPaint);
			}
			
			/**
			* 绘制横向的虚线
			**/
			int iYunit = 22;
			m_iUnitValue = (m_iFormHeight - 2 - m_iOffsetY) / iYunit;
			m_formPaint.setStyle(Style.STROKE);
			m_formPaint.setStrokeWidth(1);
			m_formPaint.setColor(Color.GRAY);
			m_formPaint.setPathEffect(new DashPathEffect(new float[] {1, 3}, 0));
			float fYmarkers = (m_fMax - m_fMin) / iYunit;
			NumberFormat numberFormat = NumberFormat.getInstance();
			numberFormat.setMinimumFractionDigits(2);
			numberFormat.setMaximumFractionDigits(2);
			for (int i = 0; i < 5; i++) {
				//canvas.drawLine(2 + m_iOffsetX, m_iFormHeight - 2 - m_iOffsetY - (m_iUnitValue * (i + 1)), 
				//		m_iFormWidth - 2, m_iFormHeight - 2 - m_iOffsetY - (m_iUnitValue * (i + 1)), m_formPaint);
				canvas.drawLine(2 + m_iOffsetX,  m_iYInterval + i * m_iYInterval, m_iFormWidth - 2, 
						m_iYInterval + i * m_iYInterval, m_formPaint);
			}		
			
			/**
			 * 绘制柱状图中的矩形
			 */
			drawRectBitmap(canvas);

			/**
			 * 绘制底部元素名称
			 */
			/**
			List<DataElement> maxItemList = series.getItems(m_strMaxItemKey);
			int iItemIndex = 0;
			int iBasePos = 0;
			for(DataElement item : maxItemList) {
				m_formPaint.setColor(Color.RED);
				canvas.drawRect(iBasePos + iItemIndex * 10, m_iFormHeight - m_iOffsetY + 15, 
						iBasePos + iItemIndex * 10 + 10, m_iFormHeight - m_iOffsetY + 30, m_formPaint);
				m_formPaint.setColor(Color.BLACK);
				canvas.drawText(item.getItemName(), iBasePos + (iItemIndex + 1) * 10, 
						m_iFormHeight - m_iOffsetY + 25, m_formPaint);
				iItemIndex++;
				iBasePos = iBasePos + iXunit * iItemIndex;
			}
			**/
		}
		
		
	}	
	
	/**
	 * 设置矩形图中数值的最大值与最小值
	 */
	public void setMinMax() {
		m_fMin = 0;
		m_fMax = 0;
		for (int i = 0; i < m_hourRecordList.size(); i++) {
			HashMap<String, Object> hashMap = m_hourRecordList.get(i);
			float fValue = (Float) hashMap.get("value");
			if(fValue > m_fMax) {
				m_fMax =fValue;
			}
			if(fValue < m_fMin) {
				m_fMin = fValue;
			}
		}
	}
	

	/**
	 * 根据获取到的列表数据绘制柱状矩形
	 * @param canvas
	 */
	public void drawRectBitmap(Canvas canvas) {
		
		m_formPaint.setStyle(Style.FILL);
		m_formPaint.setStrokeWidth(0);
		
		//设置矩形的宽度	
		//m_iBarWidth = (m_iFormWidth - m_iOffsetX) / 24;
		m_iBarWidth = (m_iXScaleInterval - 6) / 3;
		
		//设置矩形起始位置
		m_iStartPosition = m_iOffsetX + 2;
		
		m_fYmarkers = (m_fMax - m_fMin) / 22;
		
		for (int i = 0; i < m_hourRecordList.size(); i++) {
			
			HashMap<String, Object> hashMap = m_hourRecordList.get(i);
			float fValue = (Float) hashMap.get("value");
			String strColor = (String) hashMap.get("color");
			
			m_iBarHeight = (int) ((fValue / m_fYmarkers) * m_iUnitValue);
			
			Rect rect;
				rect = new Rect(m_iStartPosition + i * m_iBarWidth + i*2, 
						m_iFormHeight - 2 - m_iOffsetY - m_iBarHeight, 
						m_iStartPosition + m_iBarWidth + i * m_iBarWidth + i*2,
						m_iFormHeight - 2 - m_iOffsetY);
			
			if(strColor.equals("gray")) {
				canvas.drawBitmap(m_grayBitmap, null, rect, null);
			} else if(strColor.equals("green")) {
				canvas.drawBitmap(m_greenBitmap, null, rect, null);
			} else if(strColor.equals("yellow")) {
				canvas.drawBitmap(m_yellowBitmap, null, rect, null);
			} else if(strColor.equals("red")) {
				canvas.drawBitmap(m_redBitmap, null, rect, null);
			}
		}
	}
	
	public DataSeries getMockUpSeries() {  
        series = new DataSeries();  
        List<DataElement> itemListOne = new ArrayList<DataElement>();  
        itemListOne.add(new DataElement("shoes",120.0f, platterTable[0]));  
        //itemListOne.add(new DataElement("jacket",100.0f, platterTable[1]));  
        series.addSeries("First Quarter", itemListOne);  
          
        List<DataElement> itemListTwo = new ArrayList<DataElement>();  
        itemListTwo.add(new DataElement("shoes",110.0f, platterTable[0]));  
        //itemListTwo.add(new DataElement("jacket",50.0f, platterTable[1]));  
        series.addSeries("Second Quarter", itemListTwo);  
          
        List<DataElement> itemListThree = new ArrayList<DataElement>();  
        itemListThree.add(new DataElement("shoes",100.0f, platterTable[0]));  
        //itemListThree.add(new DataElement("jacket",280.0f, platterTable[1]));  
        series.addSeries("Third Quarter", itemListThree);  
          
        List<DataElement> itemListFour = new ArrayList<DataElement>();  
        itemListFour.add(new DataElement("shoes",120.0f, platterTable[0]));  
        //itemListFour.add(new DataElement("jacket",100.0f, platterTable[1]));  
        series.addSeries("Fourth Quarter", itemListFour);  
        return series;  
    }  
	
	/**
	 * 打印日志信息
	 * @param strLogInfo
	 */
	private void printLogInfo(String strLogInfo) {
		Log.e(TAG, strLogInfo);
	}
	
	/**
	 * 将列表中的数值处理成X轴值
	 * @param dateTimeList
	 */
	public void changeXValue(List<String> dateTimeList) {
		
		int j = 0;
		for (int i = 0; i < dateTimeList.size(); i++) {
			String strDateTime = dateTimeList.get(i);
			String[] strDate = strDateTime.split(" ");
			String[] strTimes = strDate[1].split(":");
			String strTime = "";
			if(i== (dateTimeList.size() - 1)) {
				int iHour = Integer.valueOf(strTimes[0]);
				iHour += 1;
				if(iHour == 24) {
					iHour = 0;
					strTime = "0" + iHour + ":" + strTimes[1];
				} else {
					if(iHour < 10) {
						strTime = "0" + iHour + ":" + strTimes[1];
					} else {
						strTime = iHour + ":" + strTimes[1];
					}
				}
			} else {
				strTime = strTimes[0] + ":" + strTimes[1];
			}
			Log.e("BarChartView", "截取到的时间为：" + strTime);
			
			if(i % 3 == 0) {
				m_xValueNames[j] = strTime;
				j++;
			}
			if(i== (dateTimeList.size() - 1)) {
				m_xValueNames[8] = strTime;
			}
		}
	}

}
