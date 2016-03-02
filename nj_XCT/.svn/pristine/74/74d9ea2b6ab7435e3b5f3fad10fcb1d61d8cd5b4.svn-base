package com.xguanjia.hx.filecabinet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chinamobile.salary.R;

public class FileDetailOperateActivity extends Activity implements
		OnClickListener {
	private static final String TAG = "ContactDetailOperateActivity";
	private Button file_add, img_add, cancel, file, camera_add;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_detail_operate);
		Log.d(TAG, "in onCreate method");
		initViews();
	}

	private void initViews() {
		file_add = (Button) findViewById(R.id.file_add);
		img_add = (Button) findViewById(R.id.img_add);
		cancel = (Button) findViewById(R.id.cancel);
		file = (Button) findViewById(R.id.file);
		camera_add = (Button) findViewById(R.id.camera_add);

		file_add.setOnClickListener(this);
		img_add.setOnClickListener(this);
		cancel.setOnClickListener(this);
		file.setOnClickListener(this);
		camera_add.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.file_add:
			setResult(1);
			this.finish();
			break;
		case R.id.img_add:
			setResult(3);
			this.finish();
			break;

		case R.id.camera_add:
			setResult(4);
			this.finish();
			break;
		case R.id.cancel:
			this.finish();
			break;
		case R.id.file:
			setResult(5);
			this.finish();
		default:
			break;
		}

	}

}
