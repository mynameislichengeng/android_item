package com.haoqee.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.widget.CustomProgressDialog;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;

/**
 * 页面基类，用于初始化标题栏
 * 
 * @author Lily
 *
 */
public class IndexActivity extends Activity {
	final static String TAG = "IndexActivity";
	public final static int MSG_SHOW_CHILD_MENU = 0x00001;
	public final static int MSG_CHECK_STATE = 0x00002;
	public final static int MSG_SHOW_LISTVIEW_DATA = 0x00003;
	public final static int MSG_UPDATEA_TIP_TIME = 0x00004;

	protected Context mContext;

	public final static int BASE_SHOW_PROGRESS_DIALOG = 11112;
	public final static int BASE_HIDE_PROGRESS_DIALOG = 11113;
	protected CustomProgressDialog mProgressDialog;
	private boolean mIsRegisterLoginOutReceiver = false;
	public static final String FINISH_ACTION = "com.xizue.thinkchat.intent.action.ACTION_FINISH";
	protected int mWidth = 0;

	public Handler mBaseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case BASE_SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				boolean isCancelable = true;
				if (msg.arg1 == 1) {
					isCancelable = false;
				}
				baseShowProgressDialog(dialogMsg, isCancelable);
				break;
			case BASE_HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				String showMsg = (String) msg.obj;
				if (!TextUtils.isEmpty(showMsg)) {
					showToast(showMsg);
				}
				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				return;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String) msg.obj;
				showToast(error_Detail);
				break;
			}
		}
	};

	protected void registerFinishReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ACTION);
		registerReceiver(mFinishReceiver, filter);
		mIsRegisterLoginOutReceiver = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mIsRegisterLoginOutReceiver) {
			mIsRegisterLoginOutReceiver = false;
			unregisterReceiver(mFinishReceiver);
		}
	}

	BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(FINISH_ACTION)) {
				((Activity) mContext).finish();
			}
		}
	};

	/**
	 * 隐藏软键盘
	 */
	public void hideSoftKeyboard() {
		hideSoftKeyboard(getCurrentFocus());
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			hideSoftKeyboard();
		}
		return super.dispatchKeyEvent(event);
	}

	public void hideSoftKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void showProgressDialog(String msg, boolean isCancelable) {
		baseShowProgressDialog(msg, isCancelable);
	}

	public void showProgressDialog(String msg) {
		showProgressDialog(msg, true);
	}

	public void hideProgressDialog() {
		mBaseHandler.sendEmptyMessage(BASE_HIDE_PROGRESS_DIALOG);
	}

	public void baseShowProgressDialog(String msg, boolean isCancelable) {
		mProgressDialog = new CustomProgressDialog(mContext);
		mProgressDialog.setMessage(msg);
		mProgressDialog.setCancelable(isCancelable);
		mProgressDialog.show();
	}

	protected void baseHideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mContext = this;
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/** +++ for title bar +++ */
	protected ImageView mLeftBtn, mRightBtn, mNotifyIcon;
	protected TextView titileTextView, mLeftTextBtn, mRightTextBtn;
	protected RelativeLayout mTitleLayout;

	protected void setTitleContent(int left_src_id, int right_src_id,
			String title) {
		mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		titileTextView = (TextView) findViewById(R.id.title);

		if (left_src_id != 0) {
			mLeftBtn.setImageResource(left_src_id);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setLeftText(String left_src_id, int right_src_id,
			String title) {

		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		titileTextView = (TextView) findViewById(R.id.title);
		mLeftTextBtn = (TextView) findViewById(R.id.left_text_btn);
		if (left_src_id != "") {
			mLeftTextBtn.setText(left_src_id);
			mLeftTextBtn.setVisibility(View.VISIBLE);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setRightText(int left_src_id, String right_src_id,
			String title) {

		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		titileTextView = (TextView) findViewById(R.id.title);
		mRightTextBtn = (TextView) findViewById(R.id.right_text_btn);

		if (left_src_id != 0) {
			mLeftBtn.setImageResource(left_src_id);
		}

		if (right_src_id != "") {
			mRightTextBtn.setVisibility(View.VISIBLE);
			mRightTextBtn.setText(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setText(String left_src_id, String right_src_id, String title) {

		titileTextView = (TextView) findViewById(R.id.title);
		mLeftTextBtn = (TextView) findViewById(R.id.left_text_btn);
		mRightTextBtn = (TextView) findViewById(R.id.right_text_btn);

		if (left_src_id != "") {
			mLeftTextBtn.setText(left_src_id);
		}

		if (right_src_id != "") {
			mRightTextBtn.setText(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	/** --- for title bar --- */
}
