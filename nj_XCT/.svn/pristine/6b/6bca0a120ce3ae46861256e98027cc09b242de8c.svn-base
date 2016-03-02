//package com.xguanjia.hx.common.activity;
//
//import android.os.Bundle;
//
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
//import com.baidu.location.LocationClientOption.LocationMode;
//import com.xguanjia.hx.application.MainApp;
//import com.xguanjia.hx.payroll.activity.ExpenseAccountApplicationActivity.MyLocationListener;
//
///**
// * 地图定位类
// *
// */
//public class BaseLocationActivity extends BaseActivity {
//	public LocationClient mLocationClient;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		mLocationClient = ((MainApp)getApplication()).mLocationClient;
//		InitLocation();
//		super.onCreate(savedInstanceState);
//	}
//	
//	@Override
//	protected void onStop() {
//		// TODO Auto-generated method stub
//		mLocationClient.stop();
//		super.onStop();
//	}
//	
//	protected void onDestroy() {
//		super.onDestroy();
//		try {
//			mLocationClient.stop();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	//初始化    定位
//	public void InitLocation(){
//		LocationClientOption option = new LocationClientOption();
//		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
//		option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02
//		//设置返回值的坐标类型。
//		
//		option.setScanSpan(1000);//设置发起定位请求的间隔时间为1000ms
//		option.setIsNeedAddress(true);
//		option.setAddrType("all");//设置是否要返回地址信息，默认为无地址信息。String 值为 all时，表示返回地址信息。
//                                    //其他值都表示不返回地址信息。 
//		mLocationClient.setLocOption(option);
//	}
//
//}
//
