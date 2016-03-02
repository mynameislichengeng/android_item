package com.xguanjia.hx.payroll.activity;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import cmcc.ueprob.agent.UEProbAgent;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.chinamobile.salary.R;

/***
 * 照片详情
 * 
 * @author Lee
 * 
 */
public class PayRollImageDetailActivity extends Activity{
	public ImageLoader imageLoader = null;
	private ImageView ivImage;
	private String path;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_detail);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.ic_launcher).resetViewBeforeLoading()
				.showImageForEmptyUri(R.drawable.ic_launcher).cacheInMemory()
				.cacheOnDisc().imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
		imageLoader = ImageLoader.getInstance();
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		ivImage = (ImageView) findViewById(R.id.ivImage);
		if (getIntent().getStringExtra("path") != null) {
			path = getIntent().getStringExtra("path");
			imageLoader.displayImage(path, ivImage,options);
		} else {
			return;
		}

	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UEProbAgent.onResume(this);
	}
	
	
	protected void onPause() {
		super.onPause();
		UEProbAgent.onPause(this);	
		if (isFinishing()) {
			overridePendingTransition(0, 0);
		}
	}
}
