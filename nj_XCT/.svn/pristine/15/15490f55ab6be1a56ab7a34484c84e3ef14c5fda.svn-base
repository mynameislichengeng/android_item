package com.xguanjia.hx.filecabinet.adaptor;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.contact.bean.PersonBean;

public class ShareFilePersonListAdaptor extends BaseAdapter {

	private List<PersonBean> mData;
	private LayoutInflater inflater;
	private List<View> fileViews;

	public ShareFilePersonListAdaptor(Context context, List<PersonBean> data) {

		this.mData = data;
		this.inflater = ((Activity) context).getLayoutInflater();
		fileViews = new ArrayList<View>();
	}

	public ShareFilePersonListAdaptor(Context context) {
		this.inflater = ((Activity) context).getLayoutInflater();
		fileViews = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		if (null != mData) {
			return mData.size();
		}

		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (null == mData) {
			return null;
		}
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View itemView = position >= fileViews.size() ? null : fileViews.get(position);
		PersonBean personBean = mData.get(position);
		if (itemView == null) {
			itemView = inflater.inflate(R.layout.choose_person_list_item, null);
			fileViews.add(position, itemView);
		}
		TextView personNameTv = (TextView) itemView.findViewById(R.id.personNameTv);
		personNameTv.setText(personBean.getName());
		CheckBox isCheckedCb = (CheckBox) itemView.findViewById(R.id.selectPersonCb);
		isCheckedCb.setChecked(personBean.isShareChecked());
		return itemView;
	}

	public void setData(List<PersonBean> data) {
		this.mData = data;
	}

}
