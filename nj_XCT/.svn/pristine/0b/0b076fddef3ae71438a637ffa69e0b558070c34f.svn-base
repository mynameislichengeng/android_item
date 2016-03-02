package com.haoqee.chat.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.IndexActivity;
import com.haoqee.chat.MainActivity;
import com.haoqee.chat.widget.CustomProgressDialog;
import com.chinamobile.salary.R;

/**
 * Fragment基础页面
 */
public abstract class BaseFragment extends Fragment {
	protected Context mContext;
	protected int mWidth = 0;
	protected CustomProgressDialog mProgressDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this.getActivity();
		DisplayMetrics metrics = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
	}
	
	public Handler mBaseHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case IndexActivity.BASE_SHOW_PROGRESS_DIALOG:
                String dialogMsg = (String)msg.obj;
                boolean isCancelable = false;
                if (msg.arg1 == 1) {
                	isCancelable = true;
                }
                baseShowProgressDialog(dialogMsg, isCancelable);
                break;
            case IndexActivity.BASE_HIDE_PROGRESS_DIALOG:
            	baseHideProgressDialog();
                break;
                
            case MainActivity.MSG_NETWORK_ERROR:
				Toast.makeText(mContext,R.string.network_error,Toast.LENGTH_LONG).show();
				return;
				
			case MainActivity.MSG_TICE_OUT_EXCEPTION:
				String message=(String)msg.obj;
				if (message==null || message.equals("")) {
					message=mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext,message, Toast.LENGTH_LONG).show();
				break;
            }
        }
    };
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setupViews(getView());
	}
	
	public void showProgressDialog(String msg, boolean isCancelable){
		baseShowProgressDialog(msg, isCancelable);
	}
	
	public void showProgressDialog(String msg){
		showProgressDialog(msg, true);
	}
	
	public void hideProgressDialog(){
		mBaseHandler.sendEmptyMessage(IndexActivity.BASE_HIDE_PROGRESS_DIALOG);
	}
	
	public void baseShowProgressDialog(String msg, boolean isCancelable){
		mProgressDialog = new CustomProgressDialog(mContext);
		mProgressDialog.setMessage(msg);
		mProgressDialog.setCancelable(isCancelable);
		mProgressDialog.show();
	}
	
	protected void baseHideProgressDialog(){
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}
	
	public void showToast(String content){
	    Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
	}
	
	 /**+++ for title bar +++*/
    protected ImageView mLeftBtn, mRightBtn, mNotifyIcon;
    protected TextView titileTextView, mLeftTextBtn, mRightTextBtn;
    protected RelativeLayout mTitleLayout;
    
    /**
     * 设置左右都是图标的标题栏
     * @param left_src_id					左边图片资源ID
     * @param right_src_id					右边图片资源ID
     * @param title							标题名称
     */
    protected void setTitleContent(int left_src_id, int right_src_id, String title){
    	mTitleLayout = (RelativeLayout) this.getView().findViewById(R.id.title_layout);
    	mLeftBtn = (ImageView) this.getView().findViewById(R.id.left_btn);
        mRightBtn = (ImageView) this.getView().findViewById(R.id.right_btn);
        titileTextView = (TextView) this.getView().findViewById(R.id.title);
        
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
    
    /**
     * 设置左边是文本按钮的标题栏
     * @param left_src_id					左边显示文本字符串
     * @param right_src_id					右边显示图片资源ID
     * @param title							标题名称
     */
    protected void setLeftText(String left_src_id, int right_src_id, String title){
    	
        mRightBtn = (ImageView) this.getView().findViewById(R.id.right_btn);
        titileTextView = (TextView) this.getView().findViewById(R.id.title);
        mLeftTextBtn = (TextView) this.getView().findViewById(R.id.left_text_btn);
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
    
    /**
     * 设置右边是文本按钮的标题栏
     * @param left_src_id						左边显示图片的资源ID
     * @param right_src_id						右边显示文本字符串
     * @param title								标题名称
     */
    protected void setRightText(int left_src_id, String right_src_id, String title){
    	
    	mLeftBtn = (ImageView) this.getView().findViewById(R.id.left_btn);
        titileTextView = (TextView) this.getView().findViewById(R.id.title);
        mRightTextBtn = (TextView) this.getView().findViewById(R.id.right_text_btn);
    	
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
    
    /**
     * 设置左右都是文本按钮的标题栏
     * @param left_src_id						左边显示文本字符串
     * @param right_src_id						右边显示文本字符串
     * @param title								标题名称
     */
    protected void setText(String left_src_id, String right_src_id, String title){
    	
        titileTextView = (TextView) this.getView().findViewById(R.id.title);
        mLeftTextBtn = (TextView) this.getView().findViewById(R.id.left_text_btn);
        mRightTextBtn = (TextView) this.getView().findViewById(R.id.right_text_btn);
    	
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
    
    /**--- for title bar ---*/
	
	abstract void setupViews(View contentView);
}
