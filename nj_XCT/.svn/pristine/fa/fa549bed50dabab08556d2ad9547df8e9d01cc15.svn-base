package com.haoqee.chat.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.xguanjia.hx.common.Constants;
import com.haoqee.chatsdk.entity.TCUser;

/**
 * 群成员列表适配器
 *
 */
public class MenberListAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private List<TCUser> mData; // 用户列表
	private ImageLoader mImageLoader;
	private Context mContext;
	private int mGroupRole = 0; // 群角色 0--普通成员 1--管理员
	private ItemButtonClickListener mListener;

	public MenberListAdapter(Context context, List<TCUser> data, int groupRole) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
		mImageLoader = new ImageLoader();
		mGroupRole = groupRole;

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

	public void setItemBtnClickListener(ItemButtonClickListener listener) {
		this.mListener = listener;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.user_item, null);
			holder = new ViewHolder();
			holder.mHeaderView = (ImageView) convertView
					.findViewById(R.id.headerimage);
			holder.mScreenName = (TextView) convertView
					.findViewById(R.id.username);
			holder.mSignView = (TextView) convertView.findViewById(R.id.sign);
			holder.mKickOutBtn = (Button) convertView
					.findViewById(R.id.kickBtn);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		TCUser user = mData.get(position);

		if (mGroupRole == 0) {
			holder.mKickOutBtn.setVisibility(View.GONE);
		} else {
			if (user.getUserID().equals(Common.getUid(mContext))) {
				holder.mKickOutBtn.setVisibility(View.GONE);
			} else {
				holder.mKickOutBtn.setVisibility(View.VISIBLE);
			}
		}

		if (user != null) {
			holder.mScreenName.setText(user.getName());

			holder.mSignView.setText(user.getName());

			if (!TextUtils.isEmpty(user.getHead())) {
				holder.mHeaderView.setTag(Constants.IM_HEAD_ADDRESS
						+ user.getHead());

				if (mImageLoader.getImageBuffer().get(
						Constants.IM_HEAD_ADDRESS + user.getHead()) == null) {
					holder.mHeaderView.setImageBitmap(null);
					holder.mHeaderView
							.setImageResource(R.drawable.default_header);
				}
				mImageLoader.getBitmap(mContext, holder.mHeaderView, null,
						Constants.IM_HEAD_ADDRESS + user.getHead(),
						0, false, false);
			} else {
				holder.mHeaderView.setImageResource(R.drawable.default_header);
			}
		}

		holder.mKickOutBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemBtnClick(v, position);
				}
			}
		});

		return convertView;
	}

	final static class ViewHolder {
		private ImageView mHeaderView;
		private TextView mScreenName;
		private TextView mSignView;
		private Button mKickOutBtn;

		@Override
		public int hashCode() {
			return this.mHeaderView.hashCode() + mScreenName.hashCode()
					+ mSignView.hashCode() + mKickOutBtn.hashCode();
		}
	}

}
