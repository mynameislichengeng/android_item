package com.haoqee.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haoqee.chat.global.Common;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;

/**
 * 设置页面
 *
 */
public class SettingActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout mNotifyLayout;			//设置接受新消息通知按钮
	private RelativeLayout mFeedbackLayout;			//意见反馈按钮
	private RelativeLayout mAboutLayout;			//关于我们按钮
	private RelativeLayout mModitypwdLayout;		//修改密码按钮
	
	private Button mLogoutBtn;						//退出登录按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		initComponent();
	}
	
	private void initComponent(){
		setTitleContent(R.drawable.back_btn, 0, mContext.getString(R.string.setting));
		mLeftBtn.setOnClickListener(this);
		
		mNotifyLayout = (RelativeLayout) findViewById(R.id.notifylayout);
		mNotifyLayout.setOnClickListener(this);
		
		mFeedbackLayout = (RelativeLayout) findViewById(R.id.feedbacklayout);
		mFeedbackLayout.setOnClickListener(this);
		
		mAboutLayout = (RelativeLayout) findViewById(R.id.aboutlayout);
		mAboutLayout.setOnClickListener(this);
		
		mModitypwdLayout = (RelativeLayout) findViewById(R.id.modifypwdlayout);
		mModitypwdLayout.setOnClickListener(this);
		
		mLogoutBtn = (Button) findViewById(R.id.logoutbtn);
		mLogoutBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//点击返回按钮
		case R.id.left_btn:
			this.finish();
			break;
			
		//点击设置接受新消息通知
		case R.id.notifylayout:
			Intent notifyIntent = new Intent(mContext, SettingNotifyActivity.class);
			startActivity(notifyIntent);
			break;
		
		//点击修改密码
		case R.id.modifypwdlayout:
			Intent modifyIntent = new Intent(mContext, ModifyPasswordActivity.class);
			startActivity(modifyIntent);
			break;
			
		//点击意见反馈按钮
		case R.id.feedbacklayout:
			Intent feedbackIntent = new Intent(mContext, FeedBackActivity.class);
			startActivity(feedbackIntent);
			break;
			
		//点击关于我们按钮
		case R.id.aboutlayout:
			Intent aboutIntent = new Intent(mContext, AboutActivity.class);
			startActivity(aboutIntent);
			break;
		
		//点击退出登录按钮
		case R.id.logoutbtn:
			showLogoutDialog();
			break;
			

		default:
			break;
		}
	}
	
	//退出登录对话框
	private void showLogoutDialog(){
		
		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		
		final Button cameraBtn = (Button)layout.findViewById(R.id.camera);
		final Button galleryBtn = (Button)layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button)layout.findViewById(R.id.cancelbtn);
		
		String promptText = mContext.getString(R.string.confirm_to_logout);
		cameraBtn.setText(promptText);
		cameraBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		cameraBtn.setTextColor(mContext.getResources().getColor(R.color.content_gray_color));
		cameraBtn.setEnabled(false);
		
		galleryBtn.setTextColor(Color.RED);
		galleryBtn.setText(mContext.getString(R.string.exit));
		
		cancelBtn.setText(mContext.getString(R.string.cancel));
		
		galleryBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				SharedPreferences preferences = mContext.getSharedPreferences(Common.LOGIN_SHARED, 0);
				Editor editor = preferences.edit();
				editor.remove(Common.LOGIN_RESULT);
				editor.commit();
				Common.setUid("");
				Common.setToken("");
				TCChatManager.getInstance().logoutXmpp();
				sendBroadcast(new Intent(HaoXinProjectActivity.ACTION_LOGIN_OUT));
				SettingActivity.this.finish();
			}
		});
		
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		
		// set a large value put it in bottom
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(true);

		dlg.setContentView(layout);
		dlg.show();
	}

}
