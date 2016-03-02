package com.haoqee.chatsdk.entity;

import java.io.Serializable;

public class LocationMessageBody extends BaseMessageBody implements Serializable{

	private static final long serialVersionUID = -1454254544545L;
	private double mLat = 0;				//纬度
	private double mLng = 0;				//经度
	private String mAddress = "";			//地址
	
	public LocationMessageBody(){
		
	}
	
	/**
	 * 变量初始化
	 * @param lat					纬度
	 * @param lng					经度
	 * @param address				地址
	 */
	public LocationMessageBody(double lat, double lng, String address){
		this.mLat = lat;
		this.mLng = lng;
		this.mAddress = address;
	}
	
	/**
	 * 赋值当前纬度
	 * @param lat				纬度
	 */
	public void setLocationLat(double lat){
		this.mLat = lat;
	}

	/**
	 * 获取纬度
	 * @return
	 */
	public double getLocaitonLat(){
		return mLat;
	}
	
	/**
	 * 赋值当前经度
	 * @param lng				经度
	 */
	public void setLocationLng(double lng){
		this.mLng = lng;
	}

	/**
	 * 获取纬度
	 * @return
	 */
	public double getLocaitonLng(){
		return mLng;
	}
	
	/**
	 * 设置地理坐标的地址
	 * @param address
	 */
	public void setLocationAddr(String address){
		this.mAddress = address;
	}
	
	/**
	 * 取得地理位置的地址
	 * @return
	 */
	public String getLocationAddr(){
		return mAddress;
	}
}
