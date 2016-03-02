package com.xguanjia.hx.notice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.chinamobile.salary.R;

public class NoticeListAdaptor extends BaseAdapter {

	private Context mContext;
	private LayoutInflater inflater;
	private List<Notice> mData;
	private List<View> views;

	public NoticeListAdaptor(Context context, List<Notice> data) {
		mContext = context;
		inflater = ((Activity) mContext).getLayoutInflater();
		mData = data;
		views = new ArrayList<View>();
	}

	public NoticeListAdaptor(Context context) {
		mContext = context;
		inflater = ((Activity) mContext).getLayoutInflater();
		views = new ArrayList<View>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (null != mData) {
			return mData.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		if (null != mData) {
			return mData.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View itemView = position >= views.size() ? null : views.get(position);
		Notice notice = mData.get(position);
		if (null == itemView) {
			itemView = inflater.inflate(R.layout.notice_list_item_1, null);
			views.add(position, itemView);
		}

		TextView titleView = (TextView) itemView.findViewById(R.id.title);
		TextView nameView = (TextView) itemView.findViewById(R.id.info_name);
		TextView startView = (TextView) itemView.findViewById(R.id.tv_starttime);
		if (null != notice.getTitle()) {

			// 0 未读
			if (notice.getBool().equals("0")) {

				String title = notice.getTitle().trim();
				titleView.setTextColor(Color.parseColor("#0085D0"));
				titleView.setText(title);
				titleView.setSingleLine();
				titleView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
				
				nameView.setTextColor(Color.parseColor("#0085D0"));
				nameView.setText(notice.getReleaseName());
				
				startView.setTextColor(Color.parseColor("#0085D0"));
				startView.setText(notice.getUpdateTime());
				// 1 已读
			} else if (notice.getBool().equals("1")) {

				String title = notice.getTitle().trim();
				System.out.println("notice.getBool():" + notice.getBool());

				titleView.setText(title);
				titleView.setTextColor(Color.GRAY);
				titleView.setSingleLine();
				titleView.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
				
				nameView.setTextColor(Color.GRAY);
				nameView.setText(notice.getReleaseName());
				
				startView.setTextColor(Color.GRAY);
				startView.setText(notice.getUpdateTime());
			}

		}
	

			nameView.setText(notice.getReleaseName());
			startView.setText(notice.getUpdateTime());
		

		return itemView;
	}

	public void setData(List<Notice> data) {
		mData = data;
	}

}
