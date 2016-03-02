package com.haoqee.chat.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.ImageLoader;
import com.chinamobile.salary.R;

public class FriendAdapter extends BaseAdapter {

	private List<User> mData; // 用户列表数据
	private final LayoutInflater mInflater;
	private ImageLoader mImageLoader = new ImageLoader();
	private Context mContext;

	private HashMap<String, Integer> mKeyMap = new HashMap<String, Integer>(); // 列表数据所包含的字母索引以及对应的在列表数据中的位置
	private String temp = "-1"; // 临时存储查找的字母
	private int mStart; // KeyMap在数据列表中查找的初始位置
	private boolean mIsShowCheckBox = false;// 是否显示CheckBox

	public FriendAdapter(Context context, List<User> data) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = data;
		initKeyMap();
	}

	public void setData(List<User> data) {
		mData = data;
		initKeyMap();
	}

	/**
	 * 初始化字母索引以及所在位置的Map
	 */
	public void initKeyMap() {
		if (!mKeyMap.isEmpty()) {
			mKeyMap.clear();
		}

		mStart = 0;
		temp = "-1";

		for (int i = mStart; i < mData.size(); i++) {
			if (TextUtils.isEmpty(mData.get(i).mSort)) {
				mKeyMap.put("#", mStart);
			} else {
				String alpha = String.valueOf(mData.get(i).mSort.charAt(0))
						.toUpperCase();

				if (isLetter(alpha)) {
					if (!alpha.equals(temp)) {
						mKeyMap.put(alpha, i);
						temp = alpha;
					}
				} else {
					mKeyMap.put("#", mStart);
				}
			}
		}
	}

	public HashMap<String, Integer> getSortKey() {
		return mKeyMap;
	}

	public HashMap<String, Bitmap> getImageBuffer() {
		return mImageLoader.getImageBuffer();
	}

	/**
	 * 匹配是否为字母
	 * 
	 * @param str
	 * @return
	 */
	private boolean isLetter(String str) {
		Pattern pattern = Pattern.compile("[a-zA-Z]+");
		Matcher m = pattern.matcher(str);
		return m.matches();
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	/**
	 * 设置是否显示CheckBox
	 * 
	 * @param isShow
	 *            false为不显示 true为显示
	 */
	public void setIsShowCheckBox(boolean isShow) {
		mIsShowCheckBox = isShow;
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.friend_item, null);
			holder = new ViewHolder();
			holder.mUserNameTextView = (TextView) convertView
					.findViewById(R.id.username);
			holder.mHeaderView = (ImageView) convertView
					.findViewById(R.id.headerimage);
			holder.mSignView = (TextView) convertView.findViewById(R.id.sign);
			holder.mSortKeyView = (TextView) convertView
					.findViewById(R.id.sortKey);
			holder.mCheckBox = (CheckBox) convertView
					.findViewById(R.id.checkperson);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		User user = mData.get(position);
		if (user != null) {

			if (mIsShowCheckBox) {
				holder.mCheckBox.setVisibility(View.VISIBLE);
			} else {
				holder.mCheckBox.setVisibility(View.GONE);
			}

			String alpha = "";
			if (TextUtils.isEmpty(user.mSort)) {
				alpha = "#";
			} else {
				alpha = String.valueOf(user.mSort.charAt(0)).toUpperCase();
				if (!isLetter(alpha))
					alpha = "#";
			}

			if (mKeyMap != null) {
				if (mKeyMap.get(alpha) == position) {
					holder.mSortKeyView.setVisibility(View.VISIBLE);
					holder.mSortKeyView.setText(alpha);
				} else {
					holder.mSortKeyView.setVisibility(View.GONE);
				}
			}

			holder.mUserNameTextView.setText(user.nickName);

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

			holder.mCheckBox.setChecked(user.isShow);
		}

		return convertView;
	}

	final static class ViewHolder {
		TextView mUserNameTextView;
		TextView mSignView;
		ImageView mHeaderView;
		TextView mSortKeyView;
		private CheckBox mCheckBox;

		@Override
		public int hashCode() {
			return this.mUserNameTextView.hashCode() + mSignView.hashCode()
					+ mHeaderView.hashCode() + mSortKeyView.hashCode();
		}
	}
}
