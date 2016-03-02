package com.xguanjia.hx.login.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;

/*
 * 检测版本更新
 */
public class UpdateManager {

	private Context mContext;
	private String apkUrl = "";
	private boolean isJumpMainActivity;
	private String newVersionNo = "";
	private Dialog noticeDialog;
	private Dialog downloadDialog;
	private static final String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar;
	private static final String saveFileName = savePath + "xct.apk";
	private ProgressBar mProgress;
	private static final int DOWN_UPDATE = 1;
	private static final int DOWN_OVER = 2;
	private int progress;
	private Thread downLoadThread;
	private boolean interceptFlag = false;

	private Handler _handler;
	private Message msg;

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				if (downloadDialog != null && downloadDialog.isShowing()) {
					downloadDialog.dismiss();
				}
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public UpdateManager(Context context) {
		this.mContext = context;
	}

	public UpdateManager(Context context, String newVersionNo, String clientPath, boolean isJump) {
		this.mContext = context;
		this.newVersionNo = newVersionNo;
		this.apkUrl = clientPath;
		this.isJumpMainActivity = isJump;
	}

	public UpdateManager(Context context, String newVersionNo, String clientPath, Handler handler, boolean isJump) {
		this.mContext = context;
		this.newVersionNo = newVersionNo;
		this.apkUrl = clientPath;
		this._handler = handler;
		this.isJumpMainActivity = isJump;
	}

	public void checkUpdateInfo(int num) {
		if (7 == num) {
			showNoticeDialog();
		} else {
			showNoticeDialogUpdata();
		}
	}

	private void showNoticeDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.updatetitle);
		builder.setMessage(R.string.updateMsg);
		builder.setPositiveButton(R.string.down, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					// sd card 可用
					dialog.dismiss();
					showDownloadDialog();
				} else {
					// 当前不可用
					Toast.makeText(mContext, "请检查sd卡是否存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
		builder.setNegativeButton(R.string.later, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
//				if (isJumpMainActivity) {
//					Intent intent = new Intent();
//					intent.setClass(mContext, HaoXinProjectActivity.class);
//					mContext.startActivity(intent);
//				}
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showNoticeDialogUpdata() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.updatetitle);
		builder.setMessage(R.string.updateMsg);
		builder.setPositiveButton(R.string.down, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					// sd card 可用
					dialog.dismiss();
					showDownloadDialog();
				} else {
					// 当前不可用
					Toast.makeText(mContext, "请检查sd卡是否存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog() {
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle(R.string.updatetitle);

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		builder.setView(v);
		builder.setNegativeButton(R.string.back, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				SharedPreferences sf = mContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
				URL url = new URL("http://" + sf.getString("mhIp", Constants.UPDATE_IP) + apkUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);
				int count = 0;
				byte buf[] = new byte[2048];
				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	private void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);
	}
}
