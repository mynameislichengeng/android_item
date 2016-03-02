package com.xguanjia.hx.payroll.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chinamobile.salary.R;
import com.xguanjia.hx.lc.userdefined.MyDraw;
import com.xguanjia.hx.payroll.bean.QuanBu_AfterBean;

public class MyAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<QuanBu_AfterBean> myDrawList;

	public MyAdapter(Context context, ArrayList<QuanBu_AfterBean> myDrawList) {
		super();
		this.context = context;
		this.myDrawList = myDrawList;

	}

	@Override
	public int getCount() {
		return myDrawList.size();
	}

	@Override
	public Object getItem(int position) {
		return myDrawList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	ViewHolder holder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.items_my, null);
			holder.items = (MyDraw) convertView.findViewById(R.id.my);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		QuanBu_AfterBean bean = myDrawList.get(position);

		holder.items.setA(bean.getBackground());
		holder.items.setTime(bean.getTime());
		holder.items.setMoney(bean.getMoney());
		holder.items.setFlag(bean.getFlag());

		return convertView;
	}

	public void setChangerAdapter(ArrayList<QuanBu_AfterBean> myDrawList) {
		this.myDrawList = myDrawList;
		notifyDataSetChanged();

	}

	static class ViewHolder {
		MyDraw items;
	}

}
