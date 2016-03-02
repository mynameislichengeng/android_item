package com.xguanjia.hx.common;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;

import com.chinamobile.salary.R;


public class MOAOnClickListener implements View.OnClickListener{

	@Override
	public void onClick(View v) {
		ViewParent parentView = v.getParent();
		if(null == parentView){
			return;
		}
		if(parentView instanceof ViewGroup){
			int views = ((ViewGroup)parentView).getChildCount();
			for(int i=0; i<views; i++){
				View chiledView = ((ViewGroup)parentView).getChildAt(i);
				if(chiledView instanceof Button){
					if(chiledView.getId() == v.getId()){
						chiledView.setBackgroundResource(R.drawable.def_bottom_btn_left_pressed);
					}else{
						chiledView.setBackgroundResource(R.drawable.def_bottom_left_btn_selector);
					}
				}
			}
		}
		
		
	}

}
