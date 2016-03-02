package com.xguanjia.hx.filecabinet.views;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactBottomView extends LinearLayout {
	private TextView textView;
	private ImageView imageView;

	public ContactBottomView(Context context, String text, int imageResource) {
		super(context);
		textView = new TextView(context);
		textView.setText(text);
		textView.setGravity(Gravity.CENTER);
		imageView = new ImageView(context);
		imageView.setImageResource(imageResource);
		this.setOrientation(LinearLayout.VERTICAL);
		this.addView(imageView);
		this.addView(textView);
	}

	/**
	 * 设置底部图片和文字颜色
	 */
	public void setImageIcon(int imageResource, boolean isPressed) {
		imageView.setImageResource(imageResource);
		if (isPressed) {
			textView.setTextColor(Color.rgb(200, 200, 200));
		} else {
			textView.setTextColor(Color.rgb(123, 123, 123));
		}
	}

}
