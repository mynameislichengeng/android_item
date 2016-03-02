package com.xguanjia.hx.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollviewEdit extends ScrollView{

	public ScrollviewEdit(Context context) {
		this(context , null);
		// TODO Auto-generated constructor stub
	}
	
	public ScrollviewEdit(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //System.out.println("MyScrollView-->onInterceptTouchEvent");
        return false;
    }


}
