package com.xguanjia.hx.common.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.chinamobile.salary.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.filecabinet.activity.FileListActivity;

public class IndexActivity extends BaseActivity{
	private ImageView Img;
	private Intent intent;
	private RelativeLayout zxksRelativeLayout,ctjRelativeLayout,xxzlRelativeLayout;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.default_avatar1)
				.showImageForEmptyUri(R.drawable.default_avatar1)
				.cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(100))
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		initView();
	}

	private void initView() {
		View view = getLayoutInflater().inflate(R.layout.index_activity,
				null);
		LayoutParams layoutParams = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
		mainView.addView(view,layoutParams);
		Img=(ImageView)view.findViewById(R.id.img);
		ImageLoader.getInstance().displayImage("",Img, options);
		zxksRelativeLayout=(RelativeLayout)view.findViewById(R.id.linearlayout1);
		ctjRelativeLayout=(RelativeLayout)view.findViewById(R.id.linearlayout2);
		xxzlRelativeLayout=(RelativeLayout)view.findViewById(R.id.linearlayout3);
		zxksRelativeLayout.setOnClickListener(this);
		ctjRelativeLayout.setOnClickListener(this);
		xxzlRelativeLayout.setOnClickListener(this);
		
	}
	
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.linearlayout3:
			intent=new Intent(IndexActivity.this,FileListActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
