package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class AppState implements Serializable{

	private static final long serialVersionUID = 149681634654564865L;
	
	public int code;
	public String errorMsg = "";
	public String debugMsg = "";
	
	public AppState(){}
	
	public AppState(JSONObject json) {
		try {
			if(!json.isNull("code")){
				code = json.getInt("code");
			}
			
			if(!json.isNull("msg")){
				errorMsg = json.getString("msg");
			}
			
			if(!json.isNull("debugMsg")){
				debugMsg = json.getString("debugMsg");
			}
			
			/*if(code == DamiCommon.EXPIRED_CODE){
				DamiCommon.saveLoginResult(DamiApp.getInstance(), null);
				DamiCommon.setUid("");
				DamiCommon.setToken("");
				Intent toastIntent = new Intent(MainActivity.ACTION_SHOW_TOAST);
	        	toastIntent.putExtra("toast_msg", DamiApp.getInstance().getString(R.string.login_token_expired));
	        	DamiApp.getInstance().sendBroadcast(toastIntent);
				DamiApp.getInstance().sendBroadcast(new Intent(BaseActivity.FINISH_ACTION));
				FeatureFunction.stopService(DamiApp.getInstance());
				DamiApp.getInstance().sendBroadcast(new Intent(MainActivity.ACTION_LOGIN_OUT));
			}*/
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
