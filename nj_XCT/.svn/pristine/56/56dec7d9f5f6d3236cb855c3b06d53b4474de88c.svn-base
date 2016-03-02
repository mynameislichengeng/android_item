package com.haoqee.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.chinamobile.salary.R;

public class WelcomeActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		showMainpage();
	}
	
	public void showMainpage(){
		 
		Handler handler = new Handler();
		handler.postDelayed(new Runnable(){
			@Override
			public void run() {
			    Intent intentt = new Intent(WelcomeActivity.this,MainActivity.class);
			    WelcomeActivity.this.startActivity(intentt);
			    WelcomeActivity.this.finish();
			}
		}, 2000);
	 }
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_UP
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			this.finish();
			System.exit(0);
		}
		return super.dispatchKeyEvent(event);
	}
	
}
