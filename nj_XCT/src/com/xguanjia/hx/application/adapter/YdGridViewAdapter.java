package com.xguanjia.hx.application.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.application.bean.YdGridBean;


public class YdGridViewAdapter extends BaseAdapter {

	private Context _context;

	private List<YdGridBean> menuList;

	public YdGridViewAdapter(Context context) {
		this._context = context;
		menuList = new ArrayList<YdGridBean>();
	}

	@Override
	public int getCount() {
		return menuList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		YdGridBean bean = menuList.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.yd_gridview_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.ydMenuImage);
			holder.text = (TextView) convertView.findViewById(R.id.ydTv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.image.setBackgroundResource(bean.getDrawableResourceId());
		holder.text.setText(bean.getDrawableTitle());
		return convertView;
	}

	private class ViewHolder {
		public ImageView image;
		public TextView text;
	}
	
	public void setDataChanged(List<YdGridBean> list) {
		menuList = list;
		this.notifyDataSetChanged();
	}

}
