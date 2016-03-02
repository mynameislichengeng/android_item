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
import com.xguanjia.hx.common.RoundImageView;
import com.xguanjia.hx.setting.activity.bean.IntroTeamBean;

public class IntroTeamAdapter extends BaseAdapter {
	private Context context;
	private List<IntroTeamBean> introTeamBeans = new ArrayList<IntroTeamBean>();

	public IntroTeamAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return introTeamBeans.size();
	}

	@Override
	public Object getItem(int position) {
		return introTeamBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		final IntroTeamBean bean = introTeamBeans.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.introteam_activity_item, null);
			holder = new ViewHolder();
			holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			holder.titleTv = (TextView) convertView.findViewById(R.id.titleTv);
			holder.headImg=(RoundImageView)convertView.findViewById(R.id.headImg);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.nameTv.setText(bean.getName());
		holder.titleTv.setText(bean.getIntroduce());
		if(position==0){
//			holder.headImg.setImageResource(R.drawable.sunxiaoxing);
		}else if(position==1){
//			holder.headImg.setImageResource(R.drawable.baoyan);
		}else if(position==2){
//			holder.headImg.setImageResource(R.drawable.chenxiaofeng);
		}else if(position==3){
//			holder.headImg.setImageResource(R.drawable.yaojie);
		}else if(position==4){
//			holder.headImg.setImageResource(R.drawable.xuzhuting);
		}
		
		return convertView;
	}

	static class ViewHolder {
		TextView nameTv,titleTv;
		RoundImageView headImg;
	}

	public void setDataChanged(List<IntroTeamBean> introTeamBeans) {
		this.introTeamBeans =introTeamBeans;
		this.notifyDataSetChanged();
	}

}
