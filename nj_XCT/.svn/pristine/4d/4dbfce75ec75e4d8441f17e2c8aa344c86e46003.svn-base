package com.xguanjia.hx.attachment.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.bean.AttachmentOperateTypeBean;

public class AttachmentOperateAdapter extends BaseAdapter {

	private Context _context;

	private List<AttachmentOperateTypeBean> _operateTypeList;

	public AttachmentOperateAdapter(Context context) {
		this._context = context;
		_operateTypeList = new ArrayList<AttachmentOperateTypeBean>();
	}

	@Override
	public int getCount() {
		return _operateTypeList.size();
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
		AttachmentOperateTypeBean bean = _operateTypeList.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.info_operate_item, null);
			holder = new ViewHolder();
			holder.operateType = (TextView) convertView.findViewById(R.id.operateNameView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.operateType.setText(bean.getOperateType());
		return convertView;
	}

	public Context get_context() {
		return _context;
	}

	public void set_context(Context context) {
		_context = context;
	}

	public List<AttachmentOperateTypeBean> get_operateTypeList() {
		return _operateTypeList;
	}

	public void set_operateTypeList(List<AttachmentOperateTypeBean> operateTypeList) {
		_operateTypeList = operateTypeList;
	}

	public void dataNotifyChange(List<AttachmentOperateTypeBean> list) {
		this._operateTypeList = list;
		this.notifyDataSetChanged();
	}

	private class ViewHolder {
		public TextView operateType;
	}

}
