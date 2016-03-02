package com.xguanjia.hx.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

/**
 * 下载apk
 * 
 * @author dolphin
 * 
 */
public class AsyncDownloadApk extends AsyncTask<Object, Object, Object> {

	private Handler handler;

	public AsyncDownloadApk(Handler handler) {
		this.handler = handler;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Object... params) {
		// 后台请求数据
		downLoadApk("http://192.168.2.111:8080/MOA_V1.apk");
		publishProgress(10);
		return null;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		super.onProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	private void downLoadApk(String urlStr) {
		InputStream inputStream = null;
		FileOutputStream out = null;
		int len = 0;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			inputStream = urlConnection.getInputStream();
			byte[] bytes = new byte[2048];
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "moa/");
			if (!file.exists()) {
				file.mkdirs();
			}
			out = new FileOutputStream(file + "/moa.apk");
			while ((len = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, len);
				Message msg = Message.obtain();
				msg.arg1 = len;
				msg.what = 2;
				handler.sendMessage(msg);
			}
			handler.sendEmptyMessage(3);
		} catch (Exception e) {
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					inputStream = null;
				}
			}
		}

	}
}
