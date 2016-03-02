package com.xguanjia.hx.set;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.service.ContactPersonService;
import com.xguanjia.hx.haoxin.db.HaoXinGroupDb;

/**
 * 个人设置
 * 
 * @author dolphin
 * 
 */
public class SetPersonalActivity extends BaseActivity {
	private static final String TAG = "SetPersonalActivity";
	private ContactPersonService service;
	private RelativeLayout personalLayout, otherInfoLayout;
	private ImageView personalImage;
	private HaoXinGroupDb haoXinDb;
	private Intent intent;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		initViews();
	}

	/**
	 * 界面初始化
	 */
	private void initViews() {
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		view = getLayoutInflater().inflate(R.layout.set_personal, null);
		mainView.addView(view, param);
		personalLayout = (RelativeLayout) this.findViewById(R.id.set_group_personal_layout);
		personalLayout.setOnClickListener(buttonClickListener);
		otherInfoLayout = (RelativeLayout) view.findViewById(R.id.otherInfoLayout);
		otherInfoLayout.setOnClickListener(buttonClickListener);
		setTitleLeftButton("返回", R.drawable.title_leftbutton_selector, buttonClickListener);
		setTitleText("个人信息");
		personalImage = (ImageView) this.findViewById(R.id.setPersonal_image);
		personalImage.setOnClickListener(buttonClickListener);
		service = new ContactPersonService(this);
		haoXinDb = new HaoXinGroupDb(this);
		String path = haoXinDb.selectAvatar(Constants.userId);
		String imagePath = Constants.SAVE_IMAGE_PATH + "HeadAvatar/" + path.substring(path.lastIndexOf("/") + 1, path.length());
		File file = new File(imagePath);
		if (file.exists()) {
			imageLoader.displayImage("file://" + imagePath, personalImage, options);
		}
	}
	
	protected void onResume() {
		super.onResume();
		UEProbAgent.onResume(this);
	};

	protected void onPause() {
		super.onPause();
		UEProbAgent.onPause(this);
	};
	
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			// 返回
			case R.id.title_leftBtn:
				SetPersonalActivity.this.finish();
				break;
			case R.id.set_group_personal_layout:
				intent = new Intent();
				intent.setClass(SetPersonalActivity.this, SetPortraitActivity.class);
				startActivityForResult(intent, Constants.TWO);
				break;
			case R.id.otherInfoLayout:
				intent = new Intent();
				intent.setClass(SetPersonalActivity.this, SetOtherInfoActivity.class);
				startActivityForResult(intent, Constants.TWO);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constants.TWO && resultCode == Constants.SIX) {
			String path = Constants.SAVE_IMAGE_PATH + "HeadAvatar/" + haoXinDb.selectAvatar(Constants.userId);
			File file = new File(path);
			if (file.exists()) {
				imageLoader.displayImage("file://" + path, personalImage, options);
				// personalImage.setImageBitmap(AppUtils.getBitmapFromPath(path));
			}
		}
	}

}
