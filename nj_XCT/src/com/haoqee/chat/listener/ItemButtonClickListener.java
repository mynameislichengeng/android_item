package com.haoqee.chat.listener;

import android.view.View;

/**
 * 列表每项按钮点击监听回调类
 *
 */
public interface ItemButtonClickListener {
	
	/**
	 * 点击按钮回调函数
	 */
	public void onItemBtnClick(View v, int position);
}
