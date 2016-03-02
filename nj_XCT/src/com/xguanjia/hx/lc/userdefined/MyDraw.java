package com.xguanjia.hx.lc.userdefined;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.chinamobile.salary.R;

public class MyDraw extends TextView {

	public Drawable backgroundDrawable;

	public int xD;//
	public float a = 1.0f;//

	public String time, money;

	public int flag = 0;//

	public Context context;

	public MyDraw(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public MyDraw(Context context) {
		super(context);
	}

	public void setA(float m) {
		a = m;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		float temp = getWidth();

		xD = (int) (temp * a);

		canvas.drawColor(Color.WHITE);
		canvas.save();

		if (flag == 0) {
			backgroundDrawable = getContext().getResources().getDrawable(R.drawable.mybg);
			backgroundDrawable.setBounds(0, 0, xD, getHeight());
			backgroundDrawable.draw(canvas);
			canvas.save();
		}
		if (flag == 1) {
			backgroundDrawable = getContext().getResources().getDrawable(R.drawable.mybg1);
			backgroundDrawable.setBounds(0, 0, xD, getHeight());
			backgroundDrawable.draw(canvas);
			canvas.save();
		}

		Paint mPaintText = new Paint();
		mPaintText.setStyle(Paint.Style.FILL);
		mPaintText.setAntiAlias(true);//
		if (flag == 0) {
			mPaintText.setColor(Color.parseColor("#000000"));//
		} else {
			mPaintText.setColor(Color.parseColor("#ffffff"));//
		}

		mPaintText.setTextSize(getSize(24));
		canvas.drawText(time, getSize(20), getSize(40), mPaintText);
		canvas.restore();

		TextPaint tp = new TextPaint();

		tp.setStyle(Style.FILL);
		tp.setTextSize(getSize(24));
		tp.setAntiAlias(true);
		// if (a < 0.4f) {
		// tp.setColor(Color.parseColor("#E11D04"));
		// StaticLayout myStaticLayout = new StaticLayout(money, tp, getWidth(),
		// Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
		// canvas.translate((getWidth() / 2), getSize(40 - 20));
		// myStaticLayout.draw(canvas);
		//
		// } else {

		if (flag == 0) {
			tp.setColor(Color.parseColor("#000000"));
		} else {
			tp.setColor(Color.parseColor("#ffffff"));
		}

		StaticLayout myStaticLayout = new StaticLayout(money, tp, getWidth(), Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, false);
		canvas.translate(-(getWidth() - xD + 5), getSize(40 - 20));
		myStaticLayout.draw(canvas);

		// }

		canvas.restore();

		canvas.save();
	}

	public int getSize(int size) {
		float m = context.getResources().getDisplayMetrics().density;

		if (m == 2.0f) {
			return size;
		} else {
			float scale = m / 2.0f;
			return (int) ((float) size * scale);
		}

	}

}
