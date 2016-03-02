package com.haoqee.chat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.haoqee.chat.global.Common;
import com.chinamobile.salary.R;

/**
 * 新消息通知提醒设置页
 *
 */
public class SettingNotifyActivity extends BaseActivity implements OnClickListener{
	
	private RelativeLayout mNotifyLayout;					//新消息提醒点击按钮
	private ImageView mNotifyBtn;							//新消息提醒开关按钮
	private RelativeLayout mSoundLayout;					//声音点击按钮
	private ImageView mSoundBtn;							//声音开关状态按钮
	private boolean mIsReceive = true;						//是否接收新消息
	private boolean mIsOpenSound = true;					//是否开启声音提醒

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.message_notify_setting);
		registerFinishReceiver();
		initComponent();
	}
	
	//初始化控件
	private void initComponent(){
		
		setTitleContent(R.drawable.back_btn, 0, mContext.getString(R.string.receive_notify_setting));
		mLeftBtn.setOnClickListener(this);
		
		mNotifyLayout = (RelativeLayout) findViewById(R.id.notifylayout);
		mNotifyLayout.setOnClickListener(this);
		
		mSoundLayout = (RelativeLayout) findViewById(R.id.soundlayout);
		mSoundLayout.setOnClickListener(this);
		
		
		mNotifyBtn = (ImageView) findViewById(R.id.notify_btn);
		mSoundBtn = (ImageView) findViewById(R.id.sound_btn);
		
		mIsReceive = Common.getAcceptMsgAuth(mContext);
		
		//根据保存的提醒设置，更新新消息提醒开关的状态
		if (mIsReceive) {
			mNotifyBtn.setImageResource(R.drawable.butn_open);
        } else {
        	mNotifyBtn.setImageResource(R.drawable.butn_close);
        }
		
		mIsOpenSound = Common.getOpenSound(mContext);
		
		//根据保存的铃声设置，更新界面上开关的显示状态
		if (mIsOpenSound) {
			mSoundBtn.setImageResource(R.drawable.butn_open);
        } else {
        	mSoundBtn.setImageResource(R.drawable.butn_close);
        }
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//点击返回按钮执行操作 
        case R.id.left_btn:
            this.finish();
            break;
            
        //点击新消息提醒设置操作    
        case R.id.notifylayout:
        	if(mIsReceive){			//如果是接收，则关闭，并更改界面显示
        		mIsReceive = false;
        		mNotifyBtn.setImageResource(R.drawable.butn_close);
        		
        	}else {					//如果是关闭，则接收，并更改界面显示
        		mIsReceive = true;
        		mNotifyBtn.setImageResource(R.drawable.butn_open);
			}
        	
        	//将新设置的提醒状态存入文件中
        	Common.setAcceptMsgAuth(mContext, mIsReceive);
            break;
         
        //点击声音设置
        case R.id.soundlayout:
        	
        	if(mIsOpenSound){			//如果声音提醒是打开的，则关闭声音提醒，并更改界面显示
        		mIsOpenSound = false;
        		mSoundBtn.setImageResource(R.drawable.butn_close);
        		
        	}else {						//如果声音设置是关闭的，则打开，并更改界面显示
        		mIsOpenSound = true;
        		mSoundBtn.setImageResource(R.drawable.butn_open);
			}
        	
        	//将新设置的状态存入文件中
        	Common.setOpenSound(mContext, mIsOpenSound);
        	
        	break;
            
            
        default:
            break;
        }
	}

}
