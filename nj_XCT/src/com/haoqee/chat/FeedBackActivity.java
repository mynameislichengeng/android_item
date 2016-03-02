package com.haoqee.chat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.haoqee.chat.entity.AppState;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;

/**
 * 意见反馈页面或举报页面
 * @author Lily
 *
 */
public class FeedBackActivity extends BaseActivity implements OnClickListener{
	
	/** +++++++++++++++ 页面类型  +++++++++++++++*/
	/** 意见反馈类型 */
	public static final int FEEDBACK = 0;
	/** 举报类型 */
	public static final int REPORT = 1;
	/** ----------------页面类型  ----------------*/

	private EditText mContentText;							//反馈内容输入框
	private int mPageType = FEEDBACK;
	private String mFuid = "";								//被举报用户ID，当页面是举报类型是，此变量不为空

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.feedback_page);
		registerFinishReceiver();
		initComponent();
	}

	//控件初始化
	private void initComponent(){
		
		mPageType = getIntent().getIntExtra("type", FEEDBACK);
		mFuid = getIntent().getStringExtra("fuid");
		String title = "";
		String hint = "";
		if(mPageType == FEEDBACK){
			title = mContext.getString(R.string.feedback);
			hint = mContext.getString(R.string.input_feedback_content);
		}else if(mPageType == REPORT){
			title = mContext.getString(R.string.report);
			hint = mContext.getString(R.string.please_input_report_content);
		}
		
		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn, title);
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		
		mContentText = (EditText) findViewById(R.id.content);
		mContentText.setHint(hint);
	}
	
	private Handler mHandler = new Handler() {
		
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				
			case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String)msg.obj;
				showProgressDialog(dialogMsg);
				break;
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				String successPrompt = mContext.getString(R.string.feedback_success);
				if(mPageType == REPORT){
					successPrompt = mContext.getString(R.string.report_success);
				}
				
				showToast(successPrompt);
				FeedBackActivity.this.finish();
				break;
				
			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String)msg.obj;
				if(error_Detail != null && !error_Detail.equals("")){
					showToast(error_Detail);
				}else{
					showToast(mContext.getString(R.string.feedback_fail));
				}
				break;
			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();
				
				showToast(mContext.getString(R.string.network_error));
				break;
			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message=(String)msg.obj;
				if (message==null || message.equals("")) {
					message=mContext.getString(R.string.timeout);
				}
				
				showToast(message);
				break;
				
			}
		}
	};
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//点击返回按钮执行操作
		case R.id.left_btn:
			hideSoftKeyboard();
			this.finish();
			break;
			
		//点击完成按钮执行操作
		case R.id.right_btn:
			
			hideSoftKeyboard();
			
			String content = mContentText.getText().toString().trim();
			//检测输入的内容是否为空
			if(content.equals("")){
				
				String prompt = mContext.getString(R.string.input_feedback_content);
				if(mPageType == REPORT){
					prompt = mContext.getString(R.string.please_input_report_content);
				}
				
				showToast(prompt);
				return;
			}
			
			Message message = new Message();
			message.obj = mContext.getString(R.string.send_loading);
			message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
			mHandler.sendMessage(message);
			if(mPageType == FEEDBACK){
				feedback(content);
			}else if(mPageType == REPORT){
				report(content);
			}
			break;

		default:
			break;
		}
	}
	
	/**
	 * 反馈意见接口入口函数
	 * @param content				反馈内容
	 */
	private void feedback(final String content){
		if(Common.verifyNetwork(mContext)){
			new Thread(){
				@Override
				public void run(){
					try {
						AppState state = Common.getThinkchatInfo().feedback(content);
						
						//返回状态state不为空并且state中code值为0时，表示举报成功
						if(state != null && state.code == 0){
							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}else {
							Message msg=new Message();
							msg.what=HaoXinProjectActivity.MSG_LOAD_ERROR;
							if(state != null && state.errorMsg != null && !state.errorMsg.equals("")){
								msg.obj = state.errorMsg;
							}else {
								msg.obj = mContext.getString(R.string.feedback_fail);
							}
							mHandler.sendMessage(msg);
						}
					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg=new Message();
						msg.what=HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj=mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				}
			}.start();
		}else {
			mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
		}
	}
	
	private void report(final String content){
		new Thread(){
			@Override
			public void run(){
				if(Common.verifyNetwork(mContext)){
					try {
						AppState state = Common.getThinkchatInfo().reportUser(mFuid, content);
						
						//返回状态state不为空并且state中code值为0时，表示举报成功
						if(state != null && state.code == 0){
							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
						}else {
							Message msg=new Message();
							msg.what=HaoXinProjectActivity.MSG_LOAD_ERROR;
							if(state != null && state.errorMsg != null && !state.errorMsg.equals("")){
								msg.obj = state.errorMsg;
							}else {
								msg.obj = mContext.getString(R.string.report_failed);
							}
							mHandler.sendMessage(msg);
						}
					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg=new Message();
						msg.what=HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj=mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				}else {
					mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
				}
			}
		}.start();
	}

}
