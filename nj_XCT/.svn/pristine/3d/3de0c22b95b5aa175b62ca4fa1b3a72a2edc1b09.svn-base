package com.haoqee.chat.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqee.chat.SettingActivity;
import com.haoqee.chat.UserInfoActivity;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.ImageLoader;
import com.chinamobile.salary.R;

/**
 * “我”页面相关功能
 *
 */
public class ProfileFragment extends BaseFragment implements OnClickListener{
	
	private User mUser;										//登录用户信息保存对象
	private ImageView mHeaderImageView;						//显示头像所用的控件
	private TextView mNickNameTextView;						//显示昵称所用的控件
	private TextView mSignTextView;							//显示签名所用的控件
	private RelativeLayout mProfileLayout;					//用户信息显示控件
	private RelativeLayout mSettingLayout;					//设置按钮控件
	private ImageLoader mImageLoader = new ImageLoader();	//图片下载类
	
	public final static String ACTION_REFRESH_USER = "com.xizue.thinkchat.intent.action.ACTION_REFRESH_USER";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.profile_fragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_REFRESH_USER);
		mContext.registerReceiver(mReceiver, filter);
	}
	
	/**
	 * 接收从服务器上获取数据后，更新界面的通知
	 */
	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			if(!TextUtils.isEmpty(action)){
				mUser = Common.getLoginResult(mContext);
				update();
			}
		}
	};

	/**
	 * 控件初始化
	 */
	@Override
	void setupViews(View contentView) {
		setTitleContent(0, 0, mContext.getString(R.string.profile_tab));
		
		mHeaderImageView = (ImageView) contentView.findViewById(R.id.header);
		mNickNameTextView = (TextView) contentView.findViewById(R.id.nickname);
		mSignTextView = (TextView) contentView.findViewById(R.id.sign);
		
		mProfileLayout = (RelativeLayout) contentView.findViewById(R.id.profilelayout);
		mProfileLayout.setOnClickListener(this);
		
		mSettingLayout = (RelativeLayout) contentView.findViewById(R.id.settinglayout);
		mSettingLayout.setOnClickListener(this);
		
		mUser = Common.getLoginResult(mContext);
		update();
	}
	
	/**
	 * 显示用户信息
	 */
	private void update(){
		if(mUser != null){
			if(!TextUtils.isEmpty(mUser.mSmallHead)){
				mImageLoader.getBitmap(mContext, mHeaderImageView, null, mUser.mSmallHead, 0, false, false);
			}
			
			mNickNameTextView.setText(mUser.nickName);
			
			if(!TextUtils.isEmpty(mUser.sign)){
				mSignTextView.setText(mUser.sign);
			}else {
				mSignTextView.setText(mContext.getString(R.string.user_sign) + "...");
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		//点击用户信息进图资料页面查看登录用户详细信息
		case R.id.profilelayout:
			Intent userIntent = new Intent(mContext, UserInfoActivity.class);
			startActivity(userIntent);
			break;
			
		//点击设置按钮进行的操作 
		case R.id.settinglayout:
			Intent settingIntent = new Intent(mContext, SettingActivity.class);
			startActivity(settingIntent);
			break;

		default:
			break;
		}
	}

}
