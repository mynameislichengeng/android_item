package com.haoqee.chat;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.haoqee.chat.global.FeatureFunction;
import com.chinamobile.salary.R;

/**
 * 关于页面
 *
 */
public class AboutActivity extends BaseActivity implements OnClickListener{

	private TextView mVersionText;						//版本号显示控件
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_page);
		mContext = this;
		initComponent();
	}
	
	/**
	 * 初始化控件
	 */
	private void initComponent(){
		setTitleContent(R.drawable.back_btn, 0, mContext.getString(R.string.about_us));
		mLeftBtn.setOnClickListener(this);
		
		mVersionText = (TextView) findViewById(R.id.version);
		mVersionText.setText(AboutActivity.this.getString(R.string.app_name) + " V" + FeatureFunction.getAppVersionName(AboutActivity.this));
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			this.finish();
			break;

		default:
			break;
		}
	}
}
