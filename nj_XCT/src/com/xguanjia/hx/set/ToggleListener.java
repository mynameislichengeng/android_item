package com.xguanjia.hx.set;



import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Gravity;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ToggleButton;

import com.chinamobile.salary.R;

public class ToggleListener implements OnCheckedChangeListener {
	private Context context;
	private String settingName;
	private ToggleButton toggle;
	private ImageButton toggle_Button;
	private SharedPreferences sf ; 

	public ToggleListener(Context context, String settingName,
			ToggleButton toggle, ImageButton toggle_Button) {
		this.context = context;
		this.settingName = settingName;
		this.toggle = toggle;
		this.toggle_Button = toggle_Button;
		sf = context.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Editor et = sf.edit();
		et.putBoolean("isNice", isChecked);
		et.commit();
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) toggle_Button
				.getLayoutParams();
		if (isChecked) {
			// 调整位置
			params.addRule(RelativeLayout.ALIGN_RIGHT, -1);
				params.addRule(RelativeLayout.ALIGN_LEFT, R.id.toggle_AutoPlay);
			toggle_Button.setLayoutParams(params);
			toggle_Button.setImageResource(R.drawable.progress_thumb_selector);
			toggle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
			// 播放动画
			TranslateAnimation animation = new TranslateAnimation(
					dip2px(context, 40), 0, 0, 0);
			animation.setDuration(200);
			toggle_Button.startAnimation(animation);
		} else {
			// 调整位置
			params.addRule(RelativeLayout.ALIGN_RIGHT, R.id.toggle_AutoPlay);
			params.addRule(RelativeLayout.ALIGN_LEFT, -1);
			toggle_Button.setLayoutParams(params);
			toggle_Button
					.setImageResource(R.drawable.progress_thumb_off_selector);

			toggle.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			// 播放动画
			TranslateAnimation animation = new TranslateAnimation(
					dip2px(context, -40), 0, 0, 0);
			animation.setDuration(200);
			toggle_Button.startAnimation(animation);
		}
		
		
	}
	
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}

