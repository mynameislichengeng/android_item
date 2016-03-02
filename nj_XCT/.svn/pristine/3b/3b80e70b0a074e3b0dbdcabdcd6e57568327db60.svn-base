package com.xguanjia.hx.setting.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.setting.activity.bean.VersionUpdateBean;

public class VersionUpdateAdapter extends BaseAdapter {
	private Context context;
	private List<VersionUpdateBean> versionUpdateBeans = new ArrayList<VersionUpdateBean>();

	public VersionUpdateAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return versionUpdateBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return versionUpdateBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final VersionUpdateBean bean = versionUpdateBeans.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.versionupdate_activity_item, null);
			holder = new ViewHolder();
			holder.titileTv = (TextView) convertView.findViewById(R.id.titleTv);
			holder.timeTv = (TextView) convertView.findViewById(R.id.timeTv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.titileTv.setText(bean.getTitle());
		holder.timeTv.setText(bean.getCreateTime());
		
		return convertView;
	}

	static class ViewHolder {
		TextView titileTv,timeTv;
	}

	public void setDataChanged(List<VersionUpdateBean> versionUpdateBeans) {
		this.versionUpdateBeans =versionUpdateBeans;
		this.notifyDataSetChanged();
	}

}
