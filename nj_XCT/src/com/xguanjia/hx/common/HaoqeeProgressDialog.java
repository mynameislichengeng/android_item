package com.xguanjia.hx.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/**
 * Dialog类
 *
 */
public class HaoqeeProgressDialog {
	private static ProgressDialog progressDialog;
	private static HaoqeeProgressDialog pd;

	public static HaoqeeProgressDialog getInstance() {
		if (pd == null) {
			pd = new HaoqeeProgressDialog();
		}
		return pd;
	}

	public static void show(Context context, CharSequence title, String message, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
		if (progressDialog != null && progressDialog.isShowing()) {
			return;
		}
		progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(false);
		// progressDialog.setIcon(R.drawable.icon);
		// progressDialog.setContentView(R.layout.loding);
//		progressDialog.setTitle("提示信息");
		progressDialog.setMessage(message);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		/*progressDialog.setButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				progressDialog.cancel();
			}

		});*/
		progressDialog.show();
//		View v = progressDialog.getWindow().getDecorView();
//		setDialogText(v);
	}

	public static void dismiss() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	private static void setDialogText(View v) {

		if (v instanceof ViewGroup) {
			ViewGroup parent = (ViewGroup) v;
			int count = parent.getChildCount();
			for (int i = 0; i < count; i++) {
				View child = parent.getChildAt(i);
				setDialogText(child);
			}
		} else if (v instanceof TextView) {
			((TextView) v).setTextSize(20); // 是textview，设置字号
		}
	}
}
