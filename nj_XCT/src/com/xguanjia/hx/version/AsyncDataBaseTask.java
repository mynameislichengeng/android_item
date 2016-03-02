package com.xguanjia.hx.version;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * 后台建库任务
 * 
 * @author ytg
 * @date 2012-09-17
 */
public class AsyncDataBaseTask extends AsyncTask<String, String, String> {
	private static final String TAG = "AsyncDataBaseTask";
	private ProgressBarUpdater prgBar;	                            
	private static final String STATUS_CODE_SUCCESS = "0";
	private static final String STATUS_CODE_ERROR = "2";

	public AsyncDataBaseTask(Context context) {
		prgBar = new ProgressBarUpdater(context);
		prgBar.setEnabled(false);
	}

	@Override
	protected void onPreExecute() {
		prgBar.setEnabled(true);
		prgBar.setPressed(true);
	}

	@Override
	protected void onPostExecute(String result) {
		Log.i(TAG, "数据库创建完毕");
		// 停止prgBar
		prgBar.stopProgressBar();
	}

	@Override
	protected String doInBackground(String... params) {
		Log.i(TAG, "开始创建数据库");
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		Log.i(TAG, "建库完成");
		return STATUS_CODE_SUCCESS;
	}
	
	

	// 通过类 ProgressBarUpdater 来控制线程的运行状态
	public class ProgressBarUpdater extends ProgressBar implements Runnable {
		private static final String TAG = "ProgressBarUpdater";

		public ProgressBarUpdater(Context context) {
			super(context);
		}

		private volatile boolean finished = false;

		public void run() {
			while (!finished) {
				// wait
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}

			}
		}

		public void stopProgressBar() {
			finished = true;
			this.notifyAll();
		}
	}

}
