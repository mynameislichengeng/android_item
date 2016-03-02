package com.xguanjia.hx;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import cmcc.ueprob.agent.ReportPolicy;
import cmcc.ueprob.agent.UEProbAgent;

import com.xguanjia.hx.login.activity.StartupActivity;
import com.xguanjia.hx.login.welcome.MoaWelcomeActivity;

public class MainMenuActivity extends Activity {

	private boolean isFirst = false;
	private SharedPreferences sf;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){ 
		super.onCreate(savedInstanceState);
		InitProb();
		intent = new Intent();
		sf = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		isFirst = sf.getBoolean("isFirstLoginApp", true);
		isFirstLoginApp();
	}
	
	public boolean isFirstLoginApp(){
		if(isFirst){
			SharedPreferences.Editor editor = sf.edit();
    		intent.setClass(MainMenuActivity.this, MoaWelcomeActivity.class);
    		intent.putExtra("type", "1");
    		startActivity(intent);
    		editor.putBoolean("isFirstLoginApp", false);
    		editor.commit();
    		finish();
    	}else{
    		intent.setClass(MainMenuActivity.this, StartupActivity.class);
    		startActivity(intent);
    		finish();
    	}
		return false;
	}
	
	
	void InitProb(){
		try {
			JSONObject config=new JSONObject();
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
	
	
	
	protected void onResume() {
		// TODO Auto-generated method stub
		UEProbAgent.onResume(this);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		UEProbAgent.onResume(this);
		super.onPause();
	}



}