package com.xguanjia.hx.payroll.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.payroll.bean.ExpenseAccountBean;
import com.xguanjia.hx.payroll.bean.ExpenseAccountReimburseBean;

public class ExpenseAccountAdapter extends BaseAdapter {
	private Context context;
	private List<ExpenseAccountReimburseBean> expenseAccountReimburseBeans = new ArrayList<ExpenseAccountReimburseBean>();

	public ExpenseAccountAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return expenseAccountReimburseBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return expenseAccountReimburseBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final ExpenseAccountBean bean = expenseAccountReimburseBeans.get(position).getReimburse();
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.expenseaccout_activity_item, null);
			holder = new ViewHolder();
			holder.titile = (TextView) convertView.findViewById(R.id.dbtitle);
			holder.time = (TextView) convertView.findViewById(R.id.dbtime);
			holder.states = (TextView) convertView.findViewById(R.id.statues_tv);
			holder.imageView=(ImageView)convertView.findViewById(R.id.left_icon);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(position%2==0){
			holder.imageView.setBackgroundColor(Color.parseColor("#FFBF90"));
		}else {
			holder.imageView.setBackgroundColor(Color.parseColor("#FE7613"));
		}
		if("1".equals(bean.getReimburseType())){
			holder.titile.setText(bean.getReimburseName()+"(交通)");
		}else if("2".equals(bean.getReimburseType())){
			holder.titile.setText(bean.getReimburseName()+"(住宿)");
		}else if("3".equals(bean.getReimburseType())){
			holder.titile.setText(bean.getReimburseName()+"(餐饮)");
		}else if("4".equals(bean.getReimburseType())){
			holder.titile.setText(bean.getReimburseName()+"(办公)");
		}else if("5".equals(bean.getReimburseType())){
			holder.titile.setText(bean.getReimburseName()+"(其他)");
		}
		holder.time.setText(bean.getCreateDate());
		if("1".equals(bean.getTypeState())){
			holder.states.setBackgroundResource(R.drawable.yspbxd);
		}else if("2".equals(bean.getTypeState())){
			holder.states.setBackgroundResource(R.drawable.mspbxd);
		}
		return convertView;
	}

	static class ViewHolder {
		TextView titile, time, states;
		ImageView imageView;
	}

	public void setDataChanged(List<ExpenseAccountReimburseBean> expenseAccountReimburseBeans) {
		this.expenseAccountReimburseBeans =expenseAccountReimburseBeans;
		this.notifyDataSetChanged();
	}

}
