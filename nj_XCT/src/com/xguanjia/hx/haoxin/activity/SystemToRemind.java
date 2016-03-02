package com.xguanjia.hx.haoxin.activity;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chinamobile.salary.R;


public class SystemToRemind extends Activity{
	
	private Intent intent;
	private TextView remindName, remindText, remindTime, remindBackBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_to_remind);
		initView();
	}

	private void initView() {
		intent = this.getIntent();
		
		remindName = (TextView) this.findViewById(R.id.remind_name);
		remindName.setText(intent.getStringExtra("remindName"));
		remindText = (TextView) this.findViewById(R.id.remind_text);
		remindText.setText(Html.fromHtml(intent.getStringExtra("remindText")));
		remindBackBtn = (TextView) this.findViewById(R.id.remind_back_btn);
		remindBackBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SystemToRemind.this.finish();
			}
		});
	}

	
	
}
