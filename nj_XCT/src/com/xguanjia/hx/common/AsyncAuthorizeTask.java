package com.xguanjia.hx.common;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AsyncAuthorizeTask extends AsyncTask<String, String, String> {
	private static final String TAG = "AsyncAuthorizeTask";
	public static final int AUTHORIZE_MESSAGE_CODE = 150;
	public static final int AUTHORIZE_MESSAGE_ERR_CODE = 151;
	private JSONObject _jsonObject;
	private SharedPreferences _sp;
	private Handler _handler;
	private String message;
	private Message msg;
	private String isUpdate;
	private String newVersionNo;
	private String clientPath;
	private int code;
	private AuthorizeManager authManager;

	public AsyncAuthorizeTask() {
	}

	public AsyncAuthorizeTask(Handler handler, JSONObject jsonObject, Context context) {
		this._handler = handler;
		this._jsonObject = jsonObject;
		_sp = context.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		isUpdate = "";
		newVersionNo = "";
		clientPath = "";
		authManager = new AuthorizeManager(context);
	}

	@Override
	protected String doInBackground(String... string) {
		Log.d(TAG, "in doInBackground method");
		String url = "http://yw.xguanjia.com/mobile!communication.action";
		String responseData = null;
		try {
			Log.d(TAG, "--->"+_jsonObject.toString());
			responseData = HttpClient.getContext(url, _jsonObject.toString(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseData;
	}

	@Override
	protected void onPostExecute(String result) {
		Log.d(TAG, "--->" + result);
		JSONObject jsObject = null;
		try {
			msg = _handler.obtainMessage();
			jsObject = new JSONObject(result);
			code = jsObject.getInt("code");
			if (code == Activity.DEFAULT_KEYS_DISABLE) {
				msg.what = AUTHORIZE_MESSAGE_CODE;
				authManager.signAuth(AuthorizeManager.getCurrentDate(), true);
				JSONObject jsonObject = jsObject.getJSONObject("data");
				isUpdate = jsonObject.getString("isUpdate");
				newVersionNo = jsonObject.getString("newVersionNo");
				clientPath = jsonObject.getString("clientPath");
			} else {
				msg.what = AUTHORIZE_MESSAGE_ERR_CODE;
			}
			message = (String) jsObject.getString("message");
		} catch (Exception e) {
			msg.what = AUTHORIZE_MESSAGE_ERR_CODE;
			e.printStackTrace();
		}
		Bundle bundle = msg.getData();
		bundle.putString("message", message);
		bundle.putString("isUpdate", isUpdate);
		bundle.putString("newVersionNo", newVersionNo);
		bundle.putString("clientPath", clientPath);
		msg.setData(bundle);
		_handler.sendMessage(msg);
	}
}
