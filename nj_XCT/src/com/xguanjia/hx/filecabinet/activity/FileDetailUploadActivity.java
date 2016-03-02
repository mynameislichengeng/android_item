package com.xguanjia.hx.filecabinet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chinamobile.salary.R;

public class FileDetailUploadActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "ContactDetailUploadActivity";
	private Button img_add, carmer, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_detail_upload);
		Log.d(TAG, "in onCreate method");
		initViews();
	}

	private void initViews() {
		img_add = (Button) findViewById(R.id.img_add);
		carmer = (Button) findViewById(R.id.carmer);
		cancel = (Button) findViewById(R.id.cancel);
		img_add.setOnClickListener(this);
		carmer.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_add:
			setResult(3);
			this.finish();
			break;
		case R.id.carmer:
			setResult(4);
			this.finish();
			break;
		case R.id.cancel:
			this.finish();
			break;
		default:
			break;
		}

	}

}
