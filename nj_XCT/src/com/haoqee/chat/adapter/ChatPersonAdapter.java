package com.haoqee.chat.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoqee.chat.entity.ChatDetailEntity;
import com.haoqee.chat.global.ImageLoader;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.Constants;
import com.chinamobile.salary.R;

/**
 * 聊天信息页面用户列表适配器
 *
 */
public class ChatPersonAdapter extends BaseAdapter {

	private final LayoutInflater mInflater;
	private List<ChatDetailEntity> mData;
	private Context mContext;
	private ImageLoader mImageLoader;
	private boolean mIsDelete = false;

	public ChatPersonAdapter(Context context, List<ChatDetailEntity> data) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
		mImageLoader = new ImageLoader();
	}

	@Override
	public int getCount() {
		int columns = mData.size() / 4;
		if (mData.size() % 4 != 0) {
			return (columns + 1) * 4;
		} else {
			mData.size();
		}
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		if (position < mData.size()) {
			return mData.get(position);
		} else {
			return null;
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 设置是否为编辑模式
	 * 
	 * @param isDelete
	 */
	public void setIsDelete(boolean isDelete) {
		mIsDelete = isDelete;
	}

	/**
	 * 获取是否处于编辑模式
	 * 
	 * @return
	 */
	public boolean getIsDelete() {
		return mIsDelete;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.chat_detail_person_item,
					null);
			holder = new ViewHolder();

			holder.mUserNameTextView = (TextView) convertView
					.findViewById(R.id.username);
			holder.mHeaderView = (ImageView) convertView
					.findViewById(R.id.header);
			holder.mDeleteBtn = (ImageView) convertView
					.findViewById(R.id.deletebtn);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.mDeleteBtn.setVisibility(View.GONE);

		if (position < mData.size()) {
			if (mData.get(position).mType == 0) { // 如果是用户类型，则显示用户的头像和名称
				holder.mHeaderView.setVisibility(View.VISIBLE);
				holder.mUserNameTextView.setVisibility(View.VISIBLE);
				if (mIsDelete) { // 如果处于编辑模式，则显示删除按钮
					holder.mDeleteBtn.setVisibility(View.VISIBLE);
				}

				if (mData.get(position).mUser != null) {

					String head = mData.get(position).mUser.getHead();
					if (!TextUtils.isEmpty(head)) {
						if (!head.startsWith("http")) {

							mImageLoader
									.getBitmap(
											mContext,
											holder.mHeaderView,
											null,
											(Constants.IM_HEAD_ADDRESS + head),
											0, false, false);

						} else {
							mImageLoader.getBitmap(mContext,
									holder.mHeaderView, null, head, 0, false,
									false);
						}

					} else {
						holder.mHeaderView
								.setImageResource(R.drawable.default_header);
					}
					holder.mUserNameTextView.setText(mData.get(position).mUser
							.getName());
				}
			} else { // 如果类型不为0，则隐藏用户名控件
				holder.mUserNameTextView.setVisibility(View.INVISIBLE);
				if (mIsDelete) { // 处于编辑模式时隐藏操作控件
					holder.mHeaderView.setVisibility(View.INVISIBLE);
				} else {
					holder.mHeaderView.setVisibility(View.VISIBLE);
				}
				if (mData.get(position).mType == 1) { // 如果类型为1，则设置为加人按钮
					holder.mHeaderView
							.setImageResource(R.drawable.smiley_add_btn);
				} else if (mData.get(position).mType == 2) { // 如果类型为2，则设置为踢人按钮
					holder.mHeaderView
							.setImageResource(R.drawable.smiley_minus_btn);
				}
			}
		} else {
			holder.mHeaderView.setVisibility(View.INVISIBLE);
			holder.mUserNameTextView.setVisibility(View.INVISIBLE);
		}

		return convertView;
	}

	final static class ViewHolder {
		TextView mUserNameTextView;
		ImageView mHeaderView;
		private ImageView mDeleteBtn;

		@Override
		public int hashCode() {
			return this.mUserNameTextView.hashCode() + mHeaderView.hashCode()
					+ mDeleteBtn.hashCode();
		}
	}

}
