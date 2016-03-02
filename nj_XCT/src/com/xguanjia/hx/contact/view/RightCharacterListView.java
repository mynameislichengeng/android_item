package com.xguanjia.hx.contact.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 右侧字母表，快速定位
 * 
 * @author dolphin
 * 
 */
public class RightCharacterListView extends View {

	OnTouchingLetterChangedListener onTouchingLetterChangedListener;
	String[] b = {"#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	// String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "S", "W", "X",
	// "Z" };

	int choose = -1;
	Paint paint = new Paint();
	boolean showBkg = false;

	public RightCharacterListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RightCharacterListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RightCharacterListView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (showBkg) {
			canvas.drawColor(Color.parseColor("#00FFFFFF"));
		}

		int height = getHeight();
		int width = getWidth();
		int singleHeight = height / b.length;
		for (int i = 0; i < b.length; i++) {
			paint.setColor(Color.parseColor("#FE7613"));
			paint.setTypeface(Typeface.DEFAULT_BOLD);
			paint.setTextSize(20);
			paint.setAntiAlias(true);
			if (i == choose) {
				paint.setColor(Color.parseColor("#FFFFFF"));
				paint.setFakeBoldText(true);
			}
			float xPos = width / 2 - paint.measureText(b[i]) / 2;
			float yPos = singleHeight * i + singleHeight;
			canvas.drawText(b[i], xPos, yPos, paint);
			paint.reset();
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		final float y = event.getY();
		final int oldChoose = choose;
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		final int c = (int) (y / getHeight() * b.length);

		switch (action) {
			case MotionEvent.ACTION_DOWN :
				showBkg = true;
				if (oldChoose != c && listener != null) {
					if (c > 0 && c < b.length) { // 如果第一个字母是#，无效点击的话，条件变为c>0
						listener.onTouchingLetterChanged(b[c]);
						choose = c;
						invalidate();
					}
				}

				break;
			case MotionEvent.ACTION_MOVE :
				if (oldChoose != c && listener != null) {
					if (c > 0 && c < b.length) { // 如果第一个字母是#，无效点击的话，条件变为c>0
						listener.onTouchingLetterChanged(b[c]);
						choose = c;
						invalidate();
					}
				}
				break;
			case MotionEvent.ACTION_UP :
				showBkg = false;
				choose = -1;
				invalidate();
				break;
		}
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

}