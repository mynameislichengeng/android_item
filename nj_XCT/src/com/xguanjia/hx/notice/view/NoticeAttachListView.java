package com.xguanjia.hx.notice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 
 * @author huke
 * 
 * 说明：附件列表ListView--自定义ListVIew，与ScrollView共享
 *
 */
public class NoticeAttachListView extends ListView {
	
	public NoticeAttachListView(Context context) {
		super(context);
	}
	
	public NoticeAttachListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoticeAttachListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int expandSpec = MeasureSpec.makeMeasureSpec(  
	               Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);  
	     
	    super.onMeasure(widthMeasureSpec, expandSpec); 
	}


	

}
