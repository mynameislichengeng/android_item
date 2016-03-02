package com.xguanjia.hx.application.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.application.bean.UnSendBean;

public class UnSendDataAdapter extends BaseAdapter {

	private List<UnSendBean> _list;

	private Context _context;
	
	private OnClickListener _uploadBtn;

	public UnSendDataAdapter(Context context,OnClickListener uploadBtn) {
		this._context = context;
		this._uploadBtn=uploadBtn;
		_list = new ArrayList<UnSendBean>();
	}

	@Override
	public int getCount() {
		return _list.size();
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
		UnSendBean bean = _list.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(_context).inflate(R.layout.unsend_data_item, null);
			holder = new ViewHolder();
			holder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
			holder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
			holder.uploadBtn = (Button) convertView.findViewById(R.id.uploadBtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.timeTextView.setText(bean.getMsgSendTime());
		holder.titleTextView.setText(bean.getMsgTitle());
		holder.uploadBtn.setOnClickListener(this._uploadBtn); 
		return convertView;
	}

	static class ViewHolder {
		TextView timeTextView, titleTextView;
		Button uploadBtn;
	}

	public void setDataChanged(List<UnSendBean> list) {
		this._list = list;
		this.notifyDataSetChanged();
	}

	public OnClickListener get_uploadBtn() {
		return _uploadBtn;
	}

	public void set_uploadBtn(OnClickListener _uploadBtn) {
		this._uploadBtn = _uploadBtn;
	}

}
