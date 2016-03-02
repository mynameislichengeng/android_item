package com.xguanjia.hx.application;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Application;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;
import cmcc.ueprob.agent.ReportPolicy;
import cmcc.ueprob.agent.UEProbAgent;


//import com.baidu.location.LocationClient;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.callback.OnInitCallback;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * 
 *
 */
public class MainApp extends Application {

	static MainApp mainApp;

	/** 全局信息 */
	public boolean isLogined = false;// 是否已经登录

	// 两种图片显示的设置
	public static DisplayImageOptions mNormalImageOptions;
	public static DisplayImageOptions mRoundImageOptions;
	private List<OnLowMemoryListener> mLowMemoryListeners = new ArrayList<OnLowMemoryListener>();

	public static MainApp getInstance() {
		return mainApp;
	}

	void InitProb() {
		try {
			JSONObject config = new JSONObject();
			config.put("service_api", "http://trace.hotpotpro.com:8080/TRACEProbeService/accept");
			config.put("proxy_addr", null);
			config.put("upload_policy", ReportPolicy.ANYTIME);
			config.put("batch_policy", ReportPolicy.BATCH_AT_LAUNCH);
			UEProbAgent.InitialConfig(config);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		for (OnLowMemoryListener listener : mLowMemoryListeners) {
			if (listener != null)
				listener.onLowMemoryReceived();
		}
	}

	public void registerOnLowMemoryListener(OnLowMemoryListener listener) {
		mLowMemoryListeners.add(listener);
	}

	public void removeOnLowMemoryListener(OnLowMemoryListener listener) {
		mLowMemoryListeners.remove(listener);
	}

	/**
	 * 低内存监听接口
	 */
	public interface OnLowMemoryListener {
		public void onLowMemoryReceived();
	}

	MediaPlayer mMediaPlayer;

	public synchronized MediaPlayer getMediaPlayer() {
		if (mMediaPlayer == null)
			mMediaPlayer = MediaPlayer.create(this, R.raw.notify);
		return mMediaPlayer;
	}

	// public LocationClient mLocationClient;

	@Override
	public void onCreate() {
		Log.v("MainApp", "onCreate");
		super.onCreate();
		// mLocationClient = new LocationClient(this.getApplicationContext());
		TCChatManager.init(this);
		InitProb();
		//李晨：560352874eae353b16000020
		//包烟：560267e54eae353c16000013
		try {
			MCClient.init(getApplicationContext(), "560267e54eae353c16000013", new OnInitCallback() {

				@Override
				public void onSuccess(String response) {

				}

				@Override
				public void onFailed(String response) {

				}
			});
			
		} catch (Exception e) {
		}
	}
}
