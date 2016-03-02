package com.xguanjia.hx.common;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**********
 * view嵌套
 * @Title: NestListView.java
 * @Packagecom.xguanjia.lapelgroup.common
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 吴金龙
 * @date 2013-12-9
 * @version V1.0
 */
public class NestListView  extends ListView{

	public NestListView(Context context) {
		super(context);
	}

	public NestListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public NestListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
