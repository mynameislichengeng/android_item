package com.haoqee.chat.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinamobile.salary.R;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TCSession;

public class SessionAdapter extends BaseAdapter {
	private static final String TAG = "SessionAdapter";
	private final LayoutInflater mInflater;
	private List<TCSession> mData;
	private Context mContext;
	private ImageLoader mImageLoader;
	private int mScreentWidth;
	private ItemButtonClickListener mListener = null;

	public SessionAdapter(Context context, List<TCSession> data, int screenWidth) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mData = data;
		this.mScreentWidth = screenWidth;
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

	/**
	 * 获取图片缓存Map
	 * 
	 * @return
	 */
	public HashMap<String, Bitmap> getImageBuffer() {
		return mImageLoader.getImageBuffer();
	}

	/**
	 * 设置适配器内部按钮点击的回调对象
	 * 
	 * @param listener
	 *            回调对象
	 */
	public void setItemBtnClickListener(ItemButtonClickListener listener) {
		this.mListener = listener;
	}

	/**
	 * 更新适配器对应的数据源
	 * 
	 * @param data
	 */
	public void setData(List<TCSession> data) {
		mData = data;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.session_item, null);
			holder = new ViewHolder();

			holder.mUserNameTextView = (TextView) convertView
					.findViewById(R.id.username);
			holder.mTimeTextView = (TextView) convertView
					.findViewById(R.id.releasetime);
			holder.mContentTextView = (TextView) convertView
					.findViewById(R.id.content);
			holder.mMessageCount = (TextView) convertView
					.findViewById(R.id.message_count);
			holder.mHeaderView = (ImageView) convertView
					.findViewById(R.id.header);
			holder.mGroupHeaderLayout = (LinearLayout) convertView
					.findViewById(R.id.group_header);
			holder.mScrollView = (HorizontalScrollView) convertView
					.findViewById(R.id.hsv);
			holder.mScrollView.setPadding(0, 0, 0, 0);
			holder.mContentLayout = (RelativeLayout) convertView
					.findViewById(R.id.ll_content);
			holder.mDeleteBtn = (TextView) convertView
					.findViewById(R.id.deletebtn);

			LayoutParams lp = (LayoutParams) holder.mContentLayout
					.getLayoutParams();
			lp.width = mScreentWidth;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final TCSession session = mData.get(position);
		Log.i("SessionAdapter", position + "");

		if (holder.mGroupHeaderLayout.getChildCount() != 0) {
			holder.mGroupHeaderLayout.removeAllViews();
		}

		if (session.getChatType() == ChatActivity.NOTIFY_TYPE) { // 如果是通知项，则直接显示系统消息图标
			holder.mGroupHeaderLayout.setVisibility(View.GONE);
			holder.mHeaderView.setVisibility(View.VISIBLE);
			holder.mHeaderView.setImageResource(R.drawable.system_info_icon);
		} else {

			if (session.getChatType() == ChatType.TEMP_CHAT) {

				// 如果是临时会话，则将会话对象的头像URL串已逗号为分隔拆分成多个头像URL
				String[] heads = session.getSessionHead().split(",");
				for (int i = 0; i < heads.length; i++) {
					if (!heads[i].startsWith("http")) {
						heads[i] = Constants.IM_HEAD_ADDRESS
								+ heads[i];
					}

				}
				// 头像的个数
				int count = heads.length;
				holder.mGroupHeaderLayout.setVisibility(View.VISIBLE);
				holder.mHeaderView.setVisibility(View.GONE);

				// 计算头像的个数是否是奇数
				boolean single = count % 2 == 0 ? false : true;
				// 计算头像需要显示的行数
				int row = !single ? count / 2 : count / 2 + 1;
				for (int i = 0; i < row; i++) {

					// 初始化每行装入头像控件的父控件
					LinearLayout outLayout = new LinearLayout(mContext);
					outLayout.setOrientation(LinearLayout.HORIZONTAL);
					int width = FeatureFunction.dip2px(mContext, 23);
					// 设置父控件的宽和高
					outLayout.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT, width));
					int padding = FeatureFunction.dip2px(mContext, 1);

					if (single && i == 0) { // 如果头像个数是奇数并且是第一个头像，则在第一行居中显示

						// 初始化单个头像的父控件
						LinearLayout layout = new LinearLayout(mContext);
						// 设置单个父控件的宽和高
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								width, width);
						// 设置该控件的预留边距
						layout.setPadding(padding, padding, padding, padding);
						layout.setLayoutParams(params);

						// 初始化头像显示控件
						ImageView imageView = new ImageView(mContext);
						imageView.setImageResource(R.drawable.default_header);

						if (!TextUtils.isEmpty(heads[0])) {
							imageView.setTag(heads[0]);

							if (mImageLoader.getImageBuffer().get(heads[0]) == null) {
								imageView.setImageBitmap(null);
								imageView
										.setImageResource(R.drawable.default_header);
							}

							mImageLoader.getBitmap(mContext, imageView, null,
									heads[0], 0, false, false);
						}

						imageView
								.setLayoutParams(new LinearLayout.LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.MATCH_PARENT));
						layout.addView(imageView);
						// 设置第一行头像居中显示
						outLayout.setGravity(Gravity.CENTER_HORIZONTAL);
						outLayout.addView(layout);
					} else {
						for (int j = 0; j < 2; j++) {
							// 初始化单个头像的父控件
							LinearLayout layout = new LinearLayout(mContext);
							// 设置单个父控件的宽和高
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									width, width);
							// 设置该控件的预留边距
							layout.setPadding(padding, padding, padding,
									padding);
							layout.setLayoutParams(params);

							// 初始化头像显示控件
							ImageView imageView = new ImageView(mContext);
							imageView
									.setImageResource(R.drawable.default_header);

							if (single) { // 下载奇数位置的头像
								if (!TextUtils.isEmpty(heads[2 * i + j - 1])) {
									imageView.setTag(heads[2 * i + j - 1]);

									if (mImageLoader.getImageBuffer().get(
											heads[2 * i + j - 1]) == null) {
										imageView.setImageBitmap(null);
										imageView
												.setImageResource(R.drawable.default_header);
									}

									mImageLoader.getBitmap(mContext, imageView,
											null, heads[2 * i + j - 1], 0,
											false, false);
								} else {
									imageView
											.setImageResource(R.drawable.default_header);
								}
							} else { // 下载偶数位置的头像
								if (!TextUtils.isEmpty(heads[2 * i + j])) {
									imageView.setTag(heads[2 * i + j]);

									if (mImageLoader.getImageBuffer().get(
											heads[2 * i + j]) == null) {
										imageView.setImageBitmap(null);
										imageView
												.setImageResource(R.drawable.default_header);
									}

									mImageLoader.getBitmap(mContext, imageView,
											null, heads[2 * i + j], 0, false,
											false);
								} else {
									imageView
											.setImageResource(R.drawable.default_header);
								}
							}
							imageView
									.setLayoutParams(new LinearLayout.LayoutParams(
											LayoutParams.MATCH_PARENT,
											LayoutParams.MATCH_PARENT));
							layout.addView(imageView);
							outLayout.addView(layout);
						}
					}

					holder.mGroupHeaderLayout.addView(outLayout);
				}
			} else {

				holder.mGroupHeaderLayout.setVisibility(View.GONE);
				holder.mHeaderView.setVisibility(View.VISIBLE);

				if (!TextUtils.isEmpty(session.getSessionHead())) {
					holder.mHeaderView.setTag(session.getSessionHead());
					if (mImageLoader.getImageBuffer().get(
							session.getSessionHead()) == null) {
						holder.mHeaderView.setImageBitmap(null);
						holder.mHeaderView
								.setImageResource(R.drawable.default_header);
					}

					mImageLoader.getBitmap(mContext, holder.mHeaderView, null,
							session.getSessionHead(), 0, false, false);
				} else {
					if (session.getChatType() == ChatType.SINGLE_CHAT) {
						holder.mHeaderView
								.setImageResource(R.drawable.default_header);
					} else {
						holder.mHeaderView
								.setImageResource(R.drawable.group_default_icon);
					}
				}
			}
		}

		holder.mUserNameTextView.setText(session.getSessionName());

		holder.mMessageCount.setText(String.valueOf(session.getUnreadCount()));
		if (session.getUnreadCount() == 0) {
			holder.mMessageCount.setVisibility(View.GONE);
		} else if (session.getUnreadCount() > 0) {
			holder.mMessageCount.setVisibility(View.VISIBLE);
		}
		if (session.getLastMessageTime() != 0) {
			holder.mTimeTextView.setText(FeatureFunction.getTime(session
					.getLastMessageTime()));
		}

		if (session.getTCMessage() != null) {

			if (session.getChatType() == ChatActivity.NOTIFY_TYPE) { // 如果是通知，则直接显示文本

				holder.mContentTextView.setText(session.getTCMessage()
						.getTextMessageBody().getContent());
			} else {
				switch (session.getTCMessage().getMessageType()) {
				case TCMessageType.TEXT: // 文本消息
					holder.mContentTextView.setText(EmojiUtil
							.getExpressionString(mContext, session
									.getTCMessage().getTextMessageBody()
									.getContent(), ChatActivity.EMOJIREX));
					break;
				case TCMessageType.MAP: // 地图消息
					holder.mContentTextView.setText("["
							+ mContext.getString(R.string.location) + "]");
					break;

				case TCMessageType.PICTURE: // 图片消息
					holder.mContentTextView.setText("["
							+ mContext.getString(R.string.picture) + "]");
					break;

				case TCMessageType.VOICE: // 语音消息
					holder.mContentTextView.setText("["
							+ mContext.getString(R.string.voice) + "]");
					break;

				case TCMessageType.FILE:
					holder.mContentTextView.setText("["
							+ mContext.getString(R.string.file)
							+ "]"
							+ session.getTCMessage().getFileMessageBody()
									.getFileName());
					break;

				default:
					break;
				}
			}

		} else { // 如果消息为空，则表示是新建的临时会话，没有任何产生任何消息
			holder.mContentTextView.setText(session.getSessionContent());
		}

		// 设置监听事件
		convertView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					// 获得ViewHolder
					// 获得HorizontalScrollView滑动的水平方向值.
					int scrollX = holder.mScrollView.getScrollX();
					// 获得操作区域的长度
					int actionW = holder.mDeleteBtn.getWidth();
					// 注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
					// 如果水平方向的移动值<操作区域的长度的一半,就复原
					if (scrollX < actionW / 2) {
						holder.mScrollView.smoothScrollTo(0, 0);
					} else { // 否则的话显示操作区域
						holder.mScrollView.smoothScrollTo(actionW, 0);
						notifyDataSetChanged();
					}
					return true;
				}
				return false;
			}
		});

		// 这里防止删除一条item后,ListView处于操作状态,直接还原
		if (holder.mScrollView.getScrollX() != 0) {
			holder.mScrollView.scrollTo(0, 0);
		}

		// 点击删除按钮
		holder.mDeleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onItemBtnClick(v, position);
				}
			}
		});
		holder.mContentLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (holder.mScrollView.getScrollX() != 0) {
					holder.mScrollView.scrollTo(0, 0);
					return;
				}

				if (mListener != null) {
					mListener.onItemBtnClick(v, position);
				}
			}
		});

		return convertView;
	}

	final static class ViewHolder {
		TextView mUserNameTextView; // 会话对象名称显示控件
		TextView mContentTextView; // 会话对象最新消息内容显示控件
		TextView mTimeTextView; // 会话对象最新消息时间显示控件
		TextView mMessageCount; // 会话对象未读消息数显示控件
		ImageView mHeaderView; // 单个头像显示控件
		LinearLayout mGroupHeaderLayout; // 临时会话群头像组显示控件
		private HorizontalScrollView mScrollView; // 横向滑动控件，用户横向滑动时，显示删除按钮
		private RelativeLayout mContentLayout; // 所有显示内容父控件
		private TextView mDeleteBtn; // 删除按钮

		@Override
		public int hashCode() {
			return this.mUserNameTextView.hashCode()
					+ mContentTextView.hashCode() + mTimeTextView.hashCode()
					+ mMessageCount.hashCode() + mHeaderView.hashCode()
					+ mGroupHeaderLayout.hashCode() + mScrollView.hashCode()
					+ mContentLayout.hashCode() + mDeleteBtn.hashCode();
		}
	}

}
