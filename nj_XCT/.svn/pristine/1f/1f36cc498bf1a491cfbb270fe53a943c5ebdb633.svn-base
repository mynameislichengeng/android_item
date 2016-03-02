package com.haoqee.chat.adapter;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqee.chat.FileDetailActivity;
import com.haoqee.chat.ShowImageActivity;
import com.haoqee.chat.UserInfoActivity;
import com.haoqee.chat.action.AudioPlayListener;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageType;

public class ChatAdapter extends BaseAdapter {
	private final LayoutInflater mInflater;
	public List<TCMessage> mData; // 适配器对应的数据源，即消息列表

	private ImageLoader mImageLoader = new ImageLoader(); // 图片下载类
	private Context mContext;

	private final static int MAX_SECOND = 10; // 语音最大宽度对应的时长
	private final static int MIN_SECOND = 2; // 语音最小宽度对应的时长

	private AudioPlayListener mPlayListener; // 语音播放对象

	private static final int TYPE_COUNT = 2; // 适配器对所对应的布局的个数

	private static final int TYPE_RIGHT = 0; // 右边聊天布局
	private static final int TYPE_LEFT = 1; // 左边聊天布局

	private ItemButtonClickListener mListener; // 适配器内按钮点击监听回调对象

	private HashMap<String, SoftReference<Bitmap>> mBitmapCache; // 用于存放本地图片路径生成的Bitmap
	public static final long MB = 1024 * 1024;
	public static final long KB = 1024;

	/**
	 * 返回本地图片路径生成的图片Map
	 * 
	 * @return
	 */
	public HashMap<String, SoftReference<Bitmap>> getBitmapCache() {
		return mBitmapCache;
	}

	/**
	 * 返回图片下载对象
	 * 
	 * @return
	 */
	public ImageLoader getImageLoader() {
		return mImageLoader;
	}

	/**
	 * 初始化适配器
	 * 
	 * @param data
	 *            适配器对应的数据源
	 * @param context
	 *            适配器所对应的Activity的Context
	 * @param playListener
	 *            音频的播放对象
	 */
	public ChatAdapter(List<TCMessage> data, Context context,
			AudioPlayListener playListener) {
		mContext = context;

		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mData = data;

		mBitmapCache = new HashMap<String, SoftReference<Bitmap>>();
		mPlayListener = playListener;
	}

	/**
	 * 设置适配器数据源
	 * 
	 * @param data
	 *            消息数据
	 */
	public void setData(List<TCMessage> data) {
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public TCMessage getItem(int arg0) {
		return mData.get(arg0);
	}

	/**
	 * 设置适配器内控件点击所需的监听回调对象
	 * 
	 * @param listener
	 */
	public void setItemBtnListener(ItemButtonClickListener listener) {
		mListener = listener;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 获取每项对应的View的类型
	 */
	@Override
	public int getItemViewType(int position) {
		TCMessage messageInfo = mData.get(position);
		if (messageInfo.getFromId().equals(Common.getUid(mContext))) { // 如果是自己发送的消息，则每项view的类型则是右边聊天控件类型
			return TYPE_RIGHT;
		} else { // 接收到的消息是左边聊天控件类型
			return TYPE_LEFT;
		}
	}

	/**
	 * 获取适配器内对应的View的类型的个数
	 */
	@Override
	public int getViewTypeCount() {
		return TYPE_COUNT;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		int type = getItemViewType(position);
		final TCMessage messageInfo = mData.get(position);

		ViewHolder viewHolder = null;

		if (convertView == null) { // 如果convertView为空，则初始化item对应的布局
			if (TYPE_LEFT == type) {
				// 收到的消息，初始化左边聊天布局
				convertView = mInflater.inflate(R.layout.chat_talk_left, null);
				ViewHolderLeft viewHolderleft = ViewHolderLeft
						.getInstance(convertView);
				convertView.setTag(viewHolderleft);
				viewHolder = viewHolderleft;
			} else {
				// 自己发的消息，初始化右边聊天布局
				convertView = mInflater.inflate(R.layout.chat_talk_right, null);
				ViewHolderRight viewHolderRight = ViewHolderRight
						.getInstance(convertView);
				convertView.setTag(viewHolderRight);
				viewHolder = viewHolderRight;
			}
		} else { // convertView不为空，则通过tag获取对应的viewHolder
			try {
				if (TYPE_LEFT == type) { // 如果是收到的消息，则将viewHolder转换成左边对应的ViewHolderLeft
					viewHolder = (ViewHolderLeft) convertView.getTag();
				} else {
					viewHolder = (ViewHolderRight) convertView.getTag();
				}
			} catch (Exception e) {
				e.printStackTrace();

				// 如果转换错误，则从新初始化控件
				if (TYPE_LEFT == type) {
					convertView = mInflater.inflate(R.layout.chat_talk_left,
							null);
					ViewHolderLeft viewHolderleft = ViewHolderLeft
							.getInstance(convertView);
					convertView.setTag(viewHolderleft);
					viewHolder = viewHolderleft;
				} else {
					convertView = mInflater.inflate(R.layout.chat_talk_right,
							null);
					ViewHolderRight viewHolderRight = ViewHolderRight
							.getInstance(convertView);
					convertView.setTag(viewHolderRight);
					viewHolder = viewHolderRight;
				}
			}
		}

		viewHolder.imgMsgPhoto.setImageBitmap(null);
		viewHolder.imgMsgPhoto.setImageResource(R.drawable.normal);

		if (type == TYPE_RIGHT) { // 自己发的消息，则给发送状态按钮添加点击事件
			viewHolder.imgSendState.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (mListener != null) {
						mListener.onItemBtnClick(v, position);
					}
				}
			});
		}

		bindView(viewHolder, messageInfo);
		return convertView;
	}

	private void bindView(ViewHolder viewHolder, final TCMessage messageInfo) {
		final int type = messageInfo.getFromId()
				.equals(Common.getUid(mContext)) ? 0 : 1;
		if (0 == type) { // 自己发的消息
			// 如果发送失败，则显示发送状态按钮
			if (0 == messageInfo.getSendState()) {
				viewHolder.imgSendState.setVisibility(View.VISIBLE);
			} else { // 发送成功则隐藏发送状态按钮
				viewHolder.imgSendState.setVisibility(View.GONE);
			}

		} else {
			viewHolder.imgVoiceReadState.setVisibility(View.GONE);

			// 如果是收到的消息，则点击头像查看该用户详情
			viewHolder.imgHead.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext, UserInfoActivity.class);
					intent.putExtra("fuid", messageInfo.getFromId());
					mContext.startActivity(intent);
				}
			});
		}

		if (!TextUtils.isEmpty(messageInfo.getFromHead())) {
			viewHolder.imgHead.setTag(messageInfo.getFromHead());
			mImageLoader.getBitmap(mContext, viewHolder.imgHead, null,
					messageInfo.getFromHead(), 0, false, false);
		}

		// viewHolder.txtTime.setVisibility(View.GONE);
		viewHolder.txtTime.setText(FeatureFunction.getSecondTime(messageInfo
				.getSendTime()));
		viewHolder.textName.setText(messageInfo.getFromName());

		if (messageInfo.getMessageType() == TCMessageType.VOICE) { // 如果是语音消息，则根据语音的时长控件泡泡的长度
			// 语音时长
			int length = messageInfo.getVoiceMessageBody().getVoiceTime();
			// 最大语音泡泡显示的宽度
			float max = mContext.getResources().getDimension(
					R.dimen.voice_max_length);
			// 最小语音泡泡显示宽度
			float min = mContext.getResources().getDimension(
					R.dimen.voice_min_length);
			int width = (int) min;

			// 根据语音时长计算宽度
			if (length >= MIN_SECOND && length <= MAX_SECOND) {
				width += (length - MIN_SECOND)
						* (int) ((max - min) / (MAX_SECOND - MIN_SECOND));
			} else if (length > MAX_SECOND) {
				width = (int) max;
			}

			// 设置泡泡显示的宽、高以及位置
			RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
					width, FeatureFunction.dip2px(mContext, 48));
			if (type == 0) {
				param.addRule(RelativeLayout.LEFT_OF,
						viewHolder.imgHead.getId());
				param.setMargins(0, FeatureFunction.dip2px(mContext, 5),
						FeatureFunction.dip2px(mContext, 5),
						FeatureFunction.dip2px(mContext, 5));
			} else {
				param.addRule(RelativeLayout.RIGHT_OF,
						viewHolder.imgHead.getId());
				param.setMargins(FeatureFunction.dip2px(mContext, 5),
						FeatureFunction.dip2px(mContext, 5), 0,
						FeatureFunction.dip2px(mContext, 5));
			}
			param.addRule(RelativeLayout.BELOW, viewHolder.textName.getId());
			viewHolder.mRootLayout.setLayoutParams(param);

		} else { // 非语音消息则显示原本的宽度
			int padding = FeatureFunction.dip2px(mContext, 5);
			if (type == 0) {
				viewHolder.mParams.setMargins(0, padding,
						FeatureFunction.dip2px(mContext, 5),
						FeatureFunction.dip2px(mContext, 5));
			} else {
				viewHolder.mParams.setMargins(
						FeatureFunction.dip2px(mContext, 5), padding, 0,
						FeatureFunction.dip2px(mContext, 5));
			}
			viewHolder.mRootLayout.setLayoutParams(viewHolder.mParams);
		}

		viewHolder.mRootLayout.setOnClickListener(null);

		switch (messageInfo.getMessageType()) {
		case TCMessageType.TEXT: // 文本消息
			viewHolder.txtMsgMap.setVisibility(View.GONE);
			viewHolder.txtVoiceNum.setVisibility(View.GONE);
			if (viewHolder.wiatProgressBar != null) {
				viewHolder.wiatProgressBar.setVisibility(View.GONE);
			}
			viewHolder.imgMsgVoice.setVisibility(View.GONE);
			viewHolder.imgMsgPhoto.setVisibility(View.GONE);
			viewHolder.mFileLayout.setVisibility(View.GONE);
			viewHolder.txtMsg.setVisibility(View.VISIBLE);
			viewHolder.txtMsg.setText(EmojiUtil.getExpressionString(mContext,
					messageInfo.getTextMessageBody().getContent(),
					ChatActivity.EMOJIREX));

			break;
		case TCMessageType.PICTURE: // 图片消息
			viewHolder.txtMsgMap.setVisibility(View.GONE);
			// 获取图片小图路径（如果图片未发送成功，则是本地途径，发送成功之后，则是URL）
			final String path = messageInfo.getImageMessageBody()
					.getImageUrlS();
			viewHolder.txtVoiceNum.setVisibility(View.GONE);
			viewHolder.mFileLayout.setVisibility(View.GONE);

			viewHolder.imgMsgPhoto.setTag(path);
			if (path.startsWith("http://") && 1 == messageInfo.getSendState()) {
				if (viewHolder.wiatProgressBar != null) {
					viewHolder.wiatProgressBar.setVisibility(View.VISIBLE);
				}
				viewHolder.imgMsgVoice.setVisibility(View.GONE);
				viewHolder.txtMsg.setVisibility(View.GONE);

				viewHolder.imgMsgPhoto.setTag(path);

				// 如果该页面缓存中不存在改图片，则下载
				if (!mImageLoader.getImageBuffer().containsKey(path)) {
					viewHolder.imgMsgPhoto.setImageBitmap(null);
					viewHolder.imgMsgPhoto.setImageResource(R.drawable.normal);
					mImageLoader.getBitmap(mContext, viewHolder.imgMsgPhoto,
							viewHolder.wiatProgressBar, path, 0, false, false);
				} else {
					viewHolder.wiatProgressBar.setVisibility(View.GONE);
					viewHolder.imgMsgPhoto.setImageBitmap(mImageLoader
							.getImageBuffer().get(path));
				}

				viewHolder.imgMsgPhoto.setVisibility(View.VISIBLE);

				// 点击图片查看大图
				viewHolder.imgMsgPhoto
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Intent intent = new Intent(mContext,
										ShowImageActivity.class);
								intent.putExtra("imageurl", messageInfo
										.getImageMessageBody().getImageUrlL());
								mContext.startActivity(intent);
							}
						});

			} else {
				Bitmap bitmap = null;
				if (!mBitmapCache.containsKey(path)) {
					bitmap = BitmapFactory.decodeFile(path);
					mBitmapCache.put(path, new SoftReference<Bitmap>(bitmap));
				} else {
					bitmap = mBitmapCache.get(path).get();
				}
				viewHolder.imgMsgPhoto.setImageBitmap(bitmap);
				viewHolder.imgMsgPhoto.setVisibility(View.VISIBLE);
				if (viewHolder.wiatProgressBar != null) {
					viewHolder.imgMsgVoice.setVisibility(View.GONE);
					// viewHolder.imgMsgPhoto.setVisibility(View.GONE);
					viewHolder.txtMsg.setVisibility(View.GONE);

					if (2 == messageInfo.getSendState()) {
						viewHolder.wiatProgressBar.setVisibility(View.VISIBLE);
					} else {
						viewHolder.wiatProgressBar.setVisibility(View.GONE);
					}
				}

				if (messageInfo.getSendState() == 0) {
					viewHolder.imgMsgPhoto
							.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent intent = new Intent(mContext,
											ShowImageActivity.class);
									intent.putExtra("imageurl", path);
									mContext.startActivity(intent);
								}
							});
				}
			}
			break;
		case TCMessageType.VOICE: // 语音消息

			if (2 == messageInfo.getSendState()) {
				if (viewHolder.wiatProgressBar != null) {
					viewHolder.imgMsgVoice.setVisibility(View.GONE);
					viewHolder.imgMsgPhoto.setVisibility(View.GONE);
					viewHolder.txtMsg.setVisibility(View.GONE);
					viewHolder.mFileLayout.setVisibility(View.GONE);
					viewHolder.wiatProgressBar.setVisibility(View.VISIBLE);
				}
			} else {
				if (viewHolder.wiatProgressBar != null) {
					viewHolder.wiatProgressBar.setVisibility(View.GONE);
				}
				viewHolder.imgMsgPhoto.setVisibility(View.GONE);
				viewHolder.imgMsgVoice.setVisibility(View.VISIBLE);
				viewHolder.txtMsg.setVisibility(View.GONE);
				viewHolder.mFileLayout.setVisibility(View.GONE);
				viewHolder.mRootLayout.setTag(messageInfo);
				viewHolder.mRootLayout.setOnClickListener(mPlayListener);
				viewHolder.txtVoiceNum.setVisibility(View.VISIBLE);
				viewHolder.txtVoiceNum.setText(messageInfo
						.getVoiceMessageBody().getVoiceTime() + "''");
				try {
					AnimationDrawable drawable = (AnimationDrawable) viewHolder.imgMsgVoice
							.getDrawable();
					if (mPlayListener.getMessageTag().equals(
							messageInfo.getMessageTag())) {
						drawable.start();
					} else {
						drawable.stop();
						drawable.selectDrawable(0);
					}

				} catch (Exception e) {

				}
			}
			break;

		case TCMessageType.MAP:
			viewHolder.txtVoiceNum.setVisibility(View.GONE);
			if (viewHolder.wiatProgressBar != null) {
				viewHolder.wiatProgressBar.setVisibility(View.GONE);
			}
			viewHolder.imgMsgVoice.setVisibility(View.GONE);
			viewHolder.imgMsgPhoto.setVisibility(View.GONE);
			viewHolder.txtMsg.setVisibility(View.GONE);
			viewHolder.mFileLayout.setVisibility(View.GONE);
			viewHolder.txtMsgMap.setVisibility(View.VISIBLE);
			// try {
			// if(!TextUtils.isEmpty(messageInfo.getLocationMessageBody().getLocationAddr())){
			// viewHolder.txtMsgMap.setText(messageInfo.getLocationMessageBody().getLocationAddr());
			// }
			// viewHolder.txtMsgMap.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Intent intent = new Intent(mContext, LocationActivity.class);
			// intent.putExtra("show", true);
			// intent.putExtra("lat",
			// messageInfo.getLocationMessageBody().getLocaitonLat());
			// intent.putExtra("lng",
			// messageInfo.getLocationMessageBody().getLocaitonLng());
			// mContext.startActivity(intent);
			// }
			// });
			// } catch (Exception e) {
			// }

			viewHolder.txtMsgMap.setTag(messageInfo);
			break;

		case TCMessageType.FILE:
			viewHolder.txtVoiceNum.setVisibility(View.GONE);
			if (viewHolder.wiatProgressBar != null) {
				viewHolder.wiatProgressBar.setVisibility(View.GONE);
			}
			viewHolder.imgMsgVoice.setVisibility(View.GONE);
			viewHolder.imgMsgPhoto.setVisibility(View.GONE);
			viewHolder.txtMsg.setVisibility(View.GONE);
			viewHolder.txtMsgMap.setVisibility(View.GONE);
			viewHolder.mFileLayout.setVisibility(View.VISIBLE);

			viewHolder.mFileNameTextView.setText(messageInfo
					.getFileMessageBody().getFileName());
			String fileLen = "";
			if (messageInfo.getFileMessageBody().getFileLen() >= MB) {
				float len = ((float) messageInfo.getFileMessageBody()
						.getFileLen()) / MB;
				fileLen = (float) Math.round(len * 100) / 100 + "M";

			} else if (messageInfo.getFileMessageBody().getFileLen() >= KB
					&& messageInfo.getFileMessageBody().getFileLen() < MB) {
				float len = ((float) messageInfo.getFileMessageBody()
						.getFileLen()) / KB;
				fileLen = (float) Math.round(len * 100) / 100 + "K";
			} else if (messageInfo.getFileMessageBody().getFileLen() < KB) {
				float len = ((float) messageInfo.getFileMessageBody()
						.getFileLen());
				fileLen = (float) Math.round(len * 100) / 100 + "B";
			}
			viewHolder.mFileLenTextView.setText(fileLen);

			if (type == 0) {
				if (messageInfo.getSendState() == 1) {
					viewHolder.mFileStatusTextView.setVisibility(View.VISIBLE);
					viewHolder.mFileStatusTextView.setText(mContext
							.getString(R.string.has_sent));
					viewHolder.mFileProgressBar.setVisibility(View.GONE);
				} else {
					viewHolder.mFileStatusTextView.setVisibility(View.GONE);
					viewHolder.mFileProgressBar.setVisibility(View.VISIBLE);
					viewHolder.mFileProgressBar.setProgress(messageInfo
							.getFileMessageBody().getUploadProgress());
				}
			} else {

				viewHolder.mFileStatusTextView.setVisibility(View.VISIBLE);

				File file = new File(Environment.getExternalStorageDirectory()
						+ FeatureFunction.PUB_TEMP_DIRECTORY
						+ FileDetailActivity.RECEIVE_FILE + "/"
						+ messageInfo.getFileMessageBody().getFileName());
				if (file.exists()) {

					if (file.length() == messageInfo.getFileMessageBody()
							.getFileLen()) {
						viewHolder.mFileStatusTextView.setText(mContext
								.getString(R.string.has_downloaded));
					} else {
						viewHolder.mFileStatusTextView.setText(mContext
								.getString(R.string.load_download));
					}
				} else {
					viewHolder.mFileStatusTextView.setText(mContext
							.getString(R.string.un_download));
				}
			}

			String extension = messageInfo
					.getFileMessageBody()
					.getFileName()
					.substring(
							messageInfo.getFileMessageBody().getFileName()
									.lastIndexOf("."),
							messageInfo.getFileMessageBody().getFileName()
									.length());

			if (FeatureFunction.isPic(extension)) {
				viewHolder.mFileIconView
						.setImageResource(R.drawable.file_picture);
			} else if (FeatureFunction.isWord(extension)) {
				viewHolder.mFileIconView.setImageResource(R.drawable.file_word);
			} else if (FeatureFunction.isExcel(extension)) {
				viewHolder.mFileIconView
						.setImageResource(R.drawable.file_excel);
			} else if (FeatureFunction.isPDF(extension)) {
				viewHolder.mFileIconView.setImageResource(R.drawable.file_pdf);
			} else if (FeatureFunction.isTXT(extension)) {
				viewHolder.mFileIconView.setImageResource(R.drawable.file_txt);
			} else {
				viewHolder.mFileIconView
						.setImageResource(R.drawable.file_unknown);
			}

			viewHolder.mRootLayout.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(mContext,
							FileDetailActivity.class);
					intent.putExtra("message", messageInfo);
					mContext.startActivity(intent);
				}
			});
			break;
		default:
			break;
		}
	}

	static class ViewHolderLeft extends ViewHolder {
		public static ViewHolderLeft getInstance(View view) {

			ViewHolderLeft viewHolderLeft = new ViewHolderLeft();
			return (ViewHolderLeft) getInstance(view, viewHolderLeft);
		}
	}

	static class ViewHolderRight extends ViewHolder {
		public static ViewHolderRight getInstance(View view) {
			ViewHolderRight viewHolderRight = new ViewHolderRight();
			return (ViewHolderRight) getInstance(view, viewHolderRight);
		}
	}

	static class ViewHolder {
		int flag = 0; // 1 好友 0 自己
		TextView txtTime, txtMsg, txtVoiceNum, txtMsgMap, textName;
		ImageView imgHead, imgMsgPhoto, imgMsgVoice, imgSendState,
				imgVoiceReadState;
		ProgressBar wiatProgressBar;
		RelativeLayout mRootLayout;
		private RelativeLayout.LayoutParams mParams;
		private LinearLayout mFileLayout;
		private TextView mFileLenTextView, mFileStatusTextView,
				mFileNameTextView;
		private ImageView mFileIconView;
		private ProgressBar mFileProgressBar;

		public static Object getInstance(View view, Object object) {
			ViewHolder holder = (ViewHolder) object;
			holder.mRootLayout = (RelativeLayout) view
					.findViewById(R.id.chat_talk_msg_info);
			holder.mParams = (RelativeLayout.LayoutParams) holder.mRootLayout
					.getLayoutParams();

			holder.textName = (TextView) view
					.findViewById(R.id.chat_talk_nick_name);
			holder.txtTime = (TextView) view
					.findViewById(R.id.chat_talk_txt_time);
			holder.txtMsg = (TextView) view
					.findViewById(R.id.chat_talk_msg_info_text);
			holder.txtMsgMap = (TextView) view
					.findViewById(R.id.chat_talk_msg_map);

			holder.imgHead = (ImageView) view
					.findViewById(R.id.chat_talk_img_head);
			holder.imgMsgPhoto = (ImageView) view
					.findViewById(R.id.chat_talk_msg_info_msg_photo);
			holder.imgMsgVoice = (ImageView) view
					.findViewById(R.id.chat_talk_msg_info_msg_voice);

			holder.imgSendState = (ImageView) view
					.findViewById(R.id.chat_talk_msg_sendsate);
			holder.wiatProgressBar = (ProgressBar) view
					.findViewById(R.id.chat_talk_msg_progressBar);
			holder.txtVoiceNum = (TextView) view
					.findViewById(R.id.chat_talk_voice_num);
			holder.imgVoiceReadState = (ImageView) view
					.findViewById(R.id.unread_voice_icon);

			holder.mFileLayout = (LinearLayout) view
					.findViewById(R.id.filelayout);

			holder.mFileLenTextView = (TextView) view
					.findViewById(R.id.fileLen);
			holder.mFileStatusTextView = (TextView) view
					.findViewById(R.id.fileStatus);

			holder.mFileIconView = (ImageView) view.findViewById(R.id.fileIcon);

			holder.mFileProgressBar = (ProgressBar) view
					.findViewById(R.id.file_progressbar);
			holder.mFileNameTextView = (TextView) view
					.findViewById(R.id.fileName);
			return holder;
		}
	}
}