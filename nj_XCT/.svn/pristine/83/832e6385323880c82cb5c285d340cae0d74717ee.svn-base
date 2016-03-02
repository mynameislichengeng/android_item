package com.haoqee.chat.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqee.chat.global.ImageLoader;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.entity.TCGroup;

public class GroupAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private List<TCGroup> mData;
	private ImageLoader mImageLoader;
	private Context mContext;

	public GroupAdapter(Context context, List<TCGroup> data) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
		mImageLoader = new ImageLoader();

	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public HashMap<String, Bitmap> getImageBuffer() {
		return mImageLoader.getImageBuffer();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.group_item, null);
			holder = new ViewHolder();
			holder.mGroupHeaderView = (ImageView) convertView
					.findViewById(R.id.group_header);
			holder.mGroupNameView = (TextView) convertView
					.findViewById(R.id.group_name);
			holder.mGroupMenberCountView = (TextView) convertView
					.findViewById(R.id.group_menber_count);
			holder.mGroupDescriptionView = (TextView) convertView
					.findViewById(R.id.group_description);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TCGroup group = mData.get(position);

		if (group != null) {
			holder.mGroupNameView.setText(group.getGroupName());

			holder.mGroupDescriptionView.setText(group.getGroupDescription());

			if (!TextUtils.isEmpty(group.getGroupLogoSmall())) {
				holder.mGroupHeaderView.setTag(group.getGroupLogoSmall());

				if (mImageLoader.getImageBuffer()
						.get(group.getGroupLogoSmall()) == null) {
					holder.mGroupHeaderView.setImageBitmap(null);
					holder.mGroupHeaderView
							.setImageResource(R.drawable.group_default_icon);
				}
				mImageLoader.getBitmap(mContext, holder.mGroupHeaderView, null,
						group.getGroupLogoSmall(), 0, false, false);
			} else {
				holder.mGroupHeaderView
						.setImageResource(R.drawable.group_default_icon);
			}

			holder.mGroupMenberCountView.setText("("
					+ group.getGroupMenberCount()
					+ mContext.getString(R.string.person) + ")");
		}

		return convertView;
	}

	final static class ViewHolder {
		private ImageView mGroupHeaderView;
		private TextView mGroupNameView;
		private TextView mGroupMenberCountView;
		private TextView mGroupDescriptionView;

		@Override
		public int hashCode() {
			return this.mGroupHeaderView.hashCode() + mGroupNameView.hashCode()
					+ mGroupMenberCountView.hashCode()
					+ mGroupDescriptionView.hashCode();
		}
	}

}
