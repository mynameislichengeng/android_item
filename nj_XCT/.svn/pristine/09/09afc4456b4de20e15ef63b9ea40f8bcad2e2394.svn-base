package com.xguanjia.hx.filecabinet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;

public class MyFileAddActivity extends BaseActivity {
	EditText fileadd_et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = View.inflate(MyFileAddActivity.this, R.layout.file_add, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		fileadd_et = (EditText) view.findViewById(R.id.fileadd_et);
		mainView.addView(view, params);
		setTitleRightButton("完成", R.drawable.title_rightbutton_selector, buttonClickListener);
		setTitleLeftButton("返回", R.drawable.title_leftbutton_selector, buttonClickListener);
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.image_add:
				break;
			// 刷新当前界面
			case R.id.title_rightBtn:
				String data = fileadd_et.getText().toString();
				if (!data.equals("") && data != null) {
					Intent intent = new Intent();
					intent.putExtra("data", data.trim());
					setResult(2, intent);
					MyFileAddActivity.this.finish();
				} else {
					Toast.makeText(MyFileAddActivity.this, "请输入文件名", 0).show();
				}
				break;
			// 返回按钮
			case R.id.title_leftBtn:
				MyFileAddActivity.this.finish();
				break;
			default:
				break;
			}
		}
	};

}
