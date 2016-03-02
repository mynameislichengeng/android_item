package com.xguanjia.hx.plugin.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xguanjia.hx.PluginPhoneGapActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.plugin.bean.PlugBean;

/********
 * 动态显示列表
 * 
 * @Title: PlugFormsAdapter.java
 * @Packagecom.xguanjia.hx.plugin.adapter
 * @Description: TODO(用一句话描述该文件做什么)
 * @author 吴金龙
 * @date 2013-12-5
 * @version V1.0
 */
public class PlugFormsAdapter extends BaseAdapter {
	private Context _context;
	private List<PlugBean> plugList;

	public PlugFormsAdapter(Context _context) {
		this._context = _context;
		plugList = new ArrayList<PlugBean>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return plugList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		PlugBean bean = plugList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(
					R.layout.item_plug, null);
			holder = new ViewHolder();
			holder.relativeAppFileCabinet = (RelativeLayout) convertView
					.findViewById(R.id.relativeAppFileCabinet);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.imgAppFileCabinet = (ImageView) convertView
					.findViewById(R.id.imgAppFileCabinet);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.tv_name.setText(bean.getClientMenuName());
	
		holder.relativeAppFileCabinet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				PlugBean bean1 = plugList.get(position);
				intent.putExtra("bean", (Serializable) bean1);
				intent.setClass(_context, PluginPhoneGapActivity.class);
				_context.startActivity(intent);
			}
		});
		return convertView;
	}
	/*******
	 * 如果当前指定的位置不是一个separator--分隔符（分隔符是一个不能选中，不能点击的item），那么返回true。
	 */
	public boolean areAllItemsEnabled() {
		// all items are separator
		return true;
	}

	public boolean isEnabled(int position) {
		// all items are separator
		return true;
	}

	static class ViewHolder {
		private TextView tv_name;
		private RelativeLayout relativeAppFileCabinet;
		private ImageView imgAppFileCabinet;
	}

	public void setDataChanged(List<PlugBean> list) {
		plugList = list;
		this.notifyDataSetChanged();
	}
}
