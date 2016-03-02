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

import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.chinamobile.salary.R;

/**
 * 群成员列表适配器
 *
 */
public class InviteUserListAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private List<User> mData; // 用户列表
	private ImageLoader mImageLoader;
	private Context mContext;
	private ItemButtonClickListener mListener;

	public InviteUserListAdapter(Context context, List<User> data) {
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

		User user = mData.get(position);

		holder.mKickOutBtn.setVisibility(View.VISIBLE);
		holder.mKickOutBtn.setBackgroundResource(R.drawable.message_btn);
		holder.mKickOutBtn.setText(mContext.getString(R.string.invite));

		if (user != null) {
			holder.mScreenName.setText(user.nickName);

			holder.mSignView.setText(user.phone);

			if (!TextUtils.isEmpty(user.mSmallHead)) {
				holder.mHeaderView.setTag(user.mSmallHead);

				if (mImageLoader.getImageBuffer().get(user.mSmallHead) == null) {
					holder.mHeaderView.setImageBitmap(null);
					holder.mHeaderView
							.setImageResource(R.drawable.default_header);
				}
				mImageLoader.getBitmap(mContext, holder.mHeaderView, null,
						user.mSmallHead, 0, false, false);
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
