package com.haoqee.chat;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.haoqee.chat.action.AudioPlayListener;
import com.haoqee.chat.action.AudioRecorderAction;
import com.haoqee.chat.action.ReaderImpl;
import com.haoqee.chat.adapter.ChatAdapter;
import com.haoqee.chat.adapter.EmojiAdapter;
import com.haoqee.chat.adapter.ThinkchatViewPagerAdapter;
import com.haoqee.chat.entity.MapInfo;
import com.haoqee.chat.global.AjaxCallBack;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.VoiceTask;
import com.haoqee.chat.listener.ItemButtonClickListener;
import com.haoqee.chat.net.Utility;
import com.haoqee.chat.widget.ResizeLayout;
import com.haoqee.chat.widget.ResizeLayout.OnResizeListener;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.ChatActivity;
import com.xguanjia.hx.haoxinchat.GroupChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCMessageListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.FileMessageBody;
import com.haoqee.chatsdk.entity.ImageMessageBody;
import com.haoqee.chatsdk.entity.LocationMessageBody;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TCSession;
import com.haoqee.chatsdk.entity.TCUser;
import com.haoqee.chatsdk.entity.TextMessageBody;
import com.haoqee.chatsdk.entity.VoiceMessageBody;

/**
 * 
 * 功能： 聊天页面
 * 
 */
public class ChatMainActivity extends BaseActivity implements
		OnItemLongClickListener, OnClickListener, OnTouchListener,
		OnPageChangeListener {
	private static final String TAG = "chat_main";
	private final static int MSG_RESIZE = 1234; // 键盘弹出重新计算显示区域消息值

	private ListView mListView; // 消息列表显示控件
	private Button mMsgSendBtn; // 消息发送按钮
	private Button mCameraBtn; // 相机按钮
	private Button mGalleryBtn; // 相册按钮
	private Button mEmotionBtn; // 表情按钮
	// private Button mFileBtn; // 文件按钮
	private Button mVoiceSendBtn; // 录音按钮
	private ToggleButton mToggleBtn; // 文本语音切换按钮
	private Button mAddBtn; // 相机，相册，地图，表情展开按钮
	private EditText mContentEdit; // 文本消息输入框
	private View mChatExpraLayout; // 相机，相册，地图，表情父控件
	private ChatAdapter mAdapter; // 消息适配器
	private List<TCMessage> messageInfos; // 消息列表
	private ReaderImpl mReaderImpl; // 录音接口
	private AudioRecorderAction audioRecorder; // 录音对象
	private AudioPlayListener mPlayListener; // 语音播放对象

	private List<String> downVoiceList = new ArrayList<String>(); // 语音下载列表

	private ResizeLayout mRootLayout; // 页面父控件
	private ResizeLayout mListLayout; // ListView父控件
	private static final int BIGGER = 1;
	private static final int SMALLER = 2;
	private String TEMP_FILE_NAME = "header.jpg"; // 临时图片名称
	private static final int REQUEST_GET_IMAGE_BY_CAMERA = 1002; // 拍照请求
	private static final int REQUEST_GET_URI = 101; // 从相册选取照片请求
	public static final int REQUEST_GET_BITMAP = 124; // 图片处理请求
	public final static int REQUEST_FILE_SELECT = 11111; // 文件选择请求
	private boolean mIsFirst = true; // 是否是初始化页面
	private int mLastY = 0;
	private final int UP_MOVE_CHECK_NUM = 10;

	/** 单聊消息聊天页面销毁通知 */
	public static final String DESTORY_ACTION = "com.xizue.thinkchat.intent.action.DESTORY_ACTION";
	/** 语音消息阅读状态通知 */
	public static final String ACTION_READ_VOICE_STATE = "com.xizue.thinkchat.intent.action.ACTION_READ_VOICE_STATE";
	/** 录音失败通知 */
	public static final String ACTION_RECORD_AUTH = "com.xizue.thinkchat.intent.action.ACTION_RECORD_AUTH";
	/** 群聊消息聊天页面销毁通知 */
	public static final String ACTION_DESTORY_GROUP_CHAT = "com.xizue.thinkchat.intent.action.ACTION_DESTORY_GROUP_CHAT";
	/** 临时会话消息聊天页面销毁通知 */
	public static final String ACTION_DESTORY_TEMP_CHAT = "com.xizue.thinkchat.intent.action.ACTION_DESTORY_TEMP_CHAT";
	/** 临时会话消息聊天页面销毁通知 */
	public static final String ACTION_CLEAR_RECORD = "com.xizue.thinkchat.intent.action.ACTION_CLEAR_RECORD";
	/** 更新临时会话名称 */
	public static final String ACTION_UPDATE_TEMP_CHAT_NAME = "com.xizue.thinkchat.intent.action.ACTION_UPDATE_TEMP_CHAT_NAME";

	/** 发送文件成功通知 */
	public static final String ACTION_SEND_FILE_COMPLETE = "com.xizue.thinkchat.intent.action.ACTION_SEND_FILE_COMPLETE";

	/** 发送文件失败通知 */
	public static final String ACTION_SEND_FILE_FAILED = "com.xizue.thinkchat.intent.action.ACTION_SEND_FILE_FAILED";

	/** 更新文件上传进度通知 */
	public static final String ACTION_UPDATE_FILE_PROGRESS = "com.xizue.thinkchat.intent.action.ACTION_UPDATE_FILE_PROGRESS";

	private String mFilePath = ""; // 待发送的图片路径
	private final static int SEND_SUCCESS = 13454; // 消息发送成功消息值
	private final static int SEND_FAILED = 13455; // 消息发送失败消息值
	private final static int SHOW_KICK_OUT_DIALOG = 15454; // 弹出被踢出群或临时会话对话框框消息值
	public final static int INIT_COMPONENT = 15455;
	public final static int UPDATE_PROGRESS = 15456; // 初始化页面消息值
	private boolean mIsRegisterReceiver = false; // 是否注册Receiver
	private boolean mHasLocalData = true; // 本地数据表中是否还有更多消息
	private static final int RESQUEST_CODE = 100; // 选择地图位置请求值
	private TCSession mSession; // 当前聊天所对应的会话对象

	private List<List<String>> mTotalEmotionList = new ArrayList<List<String>>(); // 表情页表
	private ViewPager mViewPager; // 显示表情所用的滑动控件
	private ThinkchatViewPagerAdapter mEmotionAdapter; // ViewPager适配器
	private LinkedList<View> mViewList = new LinkedList<View>(); // ViewPager所对应的数据源
	private LinearLayout mLayoutCircle; // 页面标识控件所在的父控件
	public int mPageIndxe = 0; // 表情当前所处的页
	private RelativeLayout mEmotionLayout; // 表情滑动控件ViewPager所在的父控件
	public final static String EXTRAS_MESSAGE = "tcmessage"; // Bundle中传输消息对象所用的Key值
	/** 新消息监听通知 */
	public final static String ACTION_NOTIFY_CHAT_MESSAGE = "com.xizue.thinkchat.intent.action.ACTION_NOTIFY_CHAT_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.chat_main);
		registerFinishReceiver();
		baseShowProgressDialog(mContext.getString(R.string.add_more_loading),
				true);
		mHandler.sendEmptyMessage(INIT_COMPONENT);
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		destoryNewMsgTag();
		setTitleContent(R.drawable.back_btn, R.drawable.chat_person_btn, "");
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		registerReceiver();
		mListView = (ListView) findViewById(R.id.chat_main_list_msg);
		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				case OnScrollListener.SCROLL_STATE_IDLE:

					// 判断第一条可见位置是否为0，如果为0，并且本地数据表中还有更多消息数据，则加载更多
					if (view.getFirstVisiblePosition() == 0) {
						if (mHasLocalData) {
							List<TCMessage> tempList = TCChatManager
									.getInstance().getMessageList(
											messageInfos.get(0).getAutoId(),
											mSession.getFromId(),
											mSession.getChatType(), 20);
							if (tempList == null || tempList.size() < 20) {
								mHasLocalData = false;
							}

							if (tempList != null && tempList.size() != 0) {
								messageInfos.addAll(0, tempList);
								mAdapter.notifyDataSetChanged();
								mListView.setSelection(tempList.size());
							}
						}

					}
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int beforeItem = firstVisibleItem - 4;
				if (beforeItem > 0) {
					recycleBitmapCaches(0, beforeItem);
				}

				int endItem = firstVisibleItem + visibleItemCount + 4;
				if (endItem < totalItemCount) {
					recycleBitmapCaches(endItem, totalItemCount);
				}
			}
		});

		mListView.setOnItemLongClickListener(this);
		mMsgSendBtn = (Button) findViewById(R.id.chat_box_btn_send);
		mMsgSendBtn.setText(mContext.getString(R.string.send));
		mMsgSendBtn.setOnClickListener(this);

		mContentEdit = (EditText) findViewById(R.id.chat_box_edit_keyword);
		mContentEdit.setOnClickListener(this);
		// mContentEdit.setOnEditorActionListener(mEditActionLister);
		// mContentEdit.setVisibility(View.GONE);

		mToggleBtn = (ToggleButton) findViewById(R.id.chat_box_btn_info);
		mToggleBtn.setOnClickListener(this);

		mVoiceSendBtn = (Button) findViewById(R.id.chat_box_btn_voice);
		mVoiceSendBtn.setText(mContext.getString(R.string.pressed_to_record));
		mVoiceSendBtn.setOnTouchListener(new OnVoice());
		// mVoiceSendBtn.setVisibility(View.VISIBLE);

		mAddBtn = (Button) findViewById(R.id.chat_box_btn_add);
		mAddBtn.setOnClickListener(this);

		mChatExpraLayout = (View) findViewById(R.id.chat_box_layout_expra);

		mCameraBtn = (Button) findViewById(R.id.chat_box_expra_btn_camera);
		mCameraBtn.setOnClickListener(this);

		mGalleryBtn = (Button) findViewById(R.id.chat_box_expra_btn_picture);
		mGalleryBtn.setOnClickListener(this);

		mEmotionBtn = (Button) findViewById(R.id.chat_box_expra_btn_experssion);
		mEmotionBtn.setOnClickListener(this);

		// mFileBtn = (Button) findViewById(R.id.chat_box_expra_btn_file);
		// mFileBtn.setOnClickListener(this);

		mViewPager = (ViewPager) findViewById(R.id.imagepager);
		mViewPager.setOnPageChangeListener(this);
		mLayoutCircle = (LinearLayout) findViewById(R.id.circlelayout);
		mEmotionLayout = (RelativeLayout) findViewById(R.id.emotionlayout);
		mEmotionLayout.setVisibility(View.GONE);

		// 获取表情列表
		mTotalEmotionList = getEmojiList();
		// 初始化表情空间
		for (int i = 0; i < mTotalEmotionList.size(); i++) {
			addView(i);
		}

		// 设置表情滑动所用的ViewPager的Adapter
		mEmotionAdapter = new ThinkchatViewPagerAdapter(mViewList);
		mViewPager.setAdapter(mEmotionAdapter);
		mViewPager.setCurrentItem(0);
		showCircle(mViewList.size());

		mRootLayout = (ResizeLayout) findViewById(R.id.rootlayout);
		mRootLayout.setOnResizeListener(new OnResizeListener() {

			@Override
			public void OnResize(int w, int h, int oldw, int oldh) {
				int change = BIGGER;
				if (mIsFirst) {
					change = SMALLER;
					mIsFirst = false;
				}
				if (h < oldh) {
					change = SMALLER;
				}

				if (mListView.getLastVisiblePosition() == messageInfos.size() - 1) {
					Message msg = new Message();
					msg.what = MSG_RESIZE;
					msg.arg1 = change;
					mHandler.sendMessage(msg);
				}
			}
		});

		mListLayout = (ResizeLayout) findViewById(R.id.listlayout);
		mListLayout.setOnResizeListener(new OnResizeListener() {

			@Override
			public void OnResize(int w, int h, int oldw, int oldh) {

				int change = BIGGER;
				if (mIsFirst) {
					change = SMALLER;
					mIsFirst = false;
				}
				if (h < oldh) {
					change = SMALLER;
				}

				if (mListView.getLastVisiblePosition() == messageInfos.size() - 1) {
					Message msg = new Message();
					msg.what = MSG_RESIZE;
					msg.arg1 = change;
					mHandler.sendMessage(msg);
				}
			}
		});
		mSession = (TCSession) getIntent().getSerializableExtra("session");
		Common.setChatType(this, mSession.getChatType());
		titileTextView.setText(mSession.getSessionName());

		clearNotification();

		audioRecorder = new AudioRecorderAction(getBaseContext());
		mReaderImpl = new ReaderImpl(mContext, mHandler, audioRecorder) {

			@Override
			public void stop(String path) {

				if (TextUtils.isEmpty(path)) {
					showToast(mContext
							.getString(R.string.record_time_too_short));
					return;
				}

				if (audioRecorder.getRecordTime() > AudioRecorderAction.MAX_TIME) {
					showToast(mContext.getString(R.string.record_time_too_long));
					return;
				} else if (audioRecorder.getRecordTime() < AudioRecorderAction.MIN_TIME) {
					showToast(mContext
							.getString(R.string.record_time_too_short));
					return;
				}
				File file = new File(path);
				if (file.exists()) {
					sendFile(TCMessageType.VOICE, path);
				} else {
					Toast.makeText(mContext,
							mContext.getString(R.string.voice_cancle),
							Toast.LENGTH_SHORT).show();
				}
			}

		};

		mPlayListener = new AudioPlayListener(this) {

			@Override
			public void down(TCMessage msg) {
				super.down(msg);
				downVoice(msg, 1);
			}

		};

		initTCMessages();
		mListView.setOnTouchListener(this);
		mListView.setOnItemLongClickListener(this);
		mVoiceSendBtn.setOnTouchListener(new OnVoice());

		mContentEdit.setOnFocusChangeListener(sendTextFocusChangeListener);
		mContentEdit.setOnClickListener(sendTextClickListener);
		mContentEdit.setHint(mContext.getString(R.string.input_message_hint));

		mAdapter = new ChatAdapter(messageInfos, mContext, mPlayListener);
		mAdapter.setItemBtnListener(new ItemButtonClickListener() {

			@Override
			public void onItemBtnClick(View v, int position) {
				switch (v.getId()) {
				case R.id.chat_talk_msg_sendsate:
					showResendDialog(messageInfos.get(position));
					break;

				default:
					break;
				}
			}
		});
		mListView.setAdapter(mAdapter);

		mListView.setSelection(messageInfos.size() - 1);

		if (messageInfos == null || messageInfos.size() < 20) {
			mHasLocalData = false;
		}

	}

	/**
	 * 销毁已存在的对应聊天类型的聊天页面
	 */
	private void destoryNewMsgTag() {

		if (mSession != null) {
			if (mSession.getChatType() == ChatType.SINGLE_CHAT) {
				sendBroadcast(new Intent(DESTORY_ACTION));
			} else if (mSession.getChatType() == ChatType.GROUP_CHAT) {
				sendBroadcast(new Intent(ACTION_DESTORY_GROUP_CHAT));
			} else if (mSession.getChatType() == ChatType.TEMP_CHAT) {
				sendBroadcast(new Intent(ACTION_DESTORY_TEMP_CHAT));
			}
		}

	}

	/**
	 * 滑动时候回收图片
	 * 
	 * @param start
	 *            回收列表数据的起始位置
	 * @param end
	 *            回收列表数据的终止位置
	 */
	private void recycleBitmapCaches(int start, int end) {
		if (mAdapter != null) {
			HashMap<String, Bitmap> buffer = mAdapter.getImageLoader()
					.getImageBuffer();
			for (int i = start; i < end; i++) {
				if (messageInfos.get(i).getMessageType() == TCMessageType.PICTURE) {
					String url = messageInfos.get(i).getImageMessageBody()
							.getImageUrlS();
					ImageView imageView = (ImageView) mListView
							.findViewWithTag(url);
					if (imageView != null) {
						imageView.setImageBitmap(null);
						imageView.setImageResource(R.drawable.normal);
					}

					if (url.startsWith("http://")
							&& 1 == messageInfos.get(i).getSendState()) {
						Bitmap bitmap = buffer.get(url);
						if (bitmap != null && !bitmap.isRecycled()) {
							bitmap.recycle();
							bitmap = null;
							buffer.remove(url);
						}
					} else {
						if (mAdapter.getBitmapCache() != null
								&& mAdapter.getBitmapCache().get(url) != null) {
							Bitmap bitmap = mAdapter.getBitmapCache().get(url)
									.get();
							if (bitmap != null && !bitmap.isRecycled()) {
								bitmap.recycle();
								bitmap = null;
								mAdapter.getBitmapCache().remove(url);
							}
						}
					}
				}
			}

		}
	}

	/**
	 * 群被删除显示的提示对话框
	 */
	private void showDestoryDialog(int dealType, boolean isExit) {

		final Dialog dlg = new Dialog(mContext, R.style.Destory_Dialog_Style);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.destory_dialog, null);
		layout.setMinimumWidth(mWidth);

		final TextView promptView = (TextView) layout.findViewById(R.id.prompt);
		final Button okBtn = (Button) layout.findViewById(R.id.okbtn);

		if (dealType == 0) {
			if (!isExit) {
				promptView.setText(mSession.getSessionName()
						+ mContext.getString(R.string.group_has_been_delete));
			} else {
				promptView.setText(mContext
						.getString(R.string.you_have_deleted)
						+ mSession.getSessionName());
			}
		} else {
			if (!isExit) {
				promptView.setText(mContext
						.getString(R.string.you_have_been_kick_out_group)
						+ mSession.getSessionName());
			} else {
				promptView.setText(mContext.getString(R.string.you_have_exited)
						+ mSession.getSessionName());
			}
		}
		okBtn.setText(mContext.getString(R.string.ok));

		// 点击确定按钮，隐藏对话框
		okBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				ChatMainActivity.this.finish();
			}
		});

		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.gravity = Gravity.CENTER;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(false);
		dlg.setCancelable(false);

		dlg.setContentView(layout);
		dlg.show();
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			// 如果表情控件和展开菜单控件处于显示状态，则隐藏表情控件和展开菜单控件
			if (mChatExpraLayout.getVisibility() == View.VISIBLE
					|| mEmotionLayout.getVisibility() == View.VISIBLE) {
				hideEmojiGridView();
			}

			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_RESIZE:
				if (msg.arg1 != BIGGER) {
					if (messageInfos != null && messageInfos.size() != 0) {
						mListView.setSelection(messageInfos.size() - 1);
					}
				}

				break;

			case SEND_SUCCESS:
				TCMessage messageInfo = (TCMessage) msg.obj;
				int isResend = msg.arg1;
				modifyMessageState(messageInfo);
				break;

			case SEND_FAILED:
				TCMessage message = (TCMessage) msg.obj;
				modifyMessageState(message);
				break;

			case SHOW_KICK_OUT_DIALOG:
				showDestoryDialog(1, false);
				break;

			case INIT_COMPONENT:
				initComponent();
				baseHideProgressDialog();
				break;

			case UPDATE_PROGRESS:
				mAdapter.notifyDataSetChanged();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * 调用系统相机拍照
	 */
	private void getImageFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		TEMP_FILE_NAME = FeatureFunction.getPhotoFileName();

		if (FeatureFunction.newFolder(Environment.getExternalStorageDirectory()
				+ FeatureFunction.PUB_TEMP_DIRECTORY)) {
			File out = new File(Environment.getExternalStorageDirectory()
					+ FeatureFunction.PUB_TEMP_DIRECTORY,
					FeatureFunction.getPhotoFileName());
			Uri uri = Uri.fromFile(out);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

			startActivityForResult(intent, REQUEST_GET_IMAGE_BY_CAMERA);
		}
	}

	/**
	 * 从系统相册中选择图片
	 */
	private void getImageFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");

		startActivityForResult(intent, REQUEST_GET_URI);
	}

	/**
	 * 图片处理入口
	 * 
	 * @param isGallery
	 *            是否从相册中选择
	 * @param data
	 *            选择成功后返回的data
	 */
	private void doChoose(final boolean isGallery, final Intent data) {
		if (isGallery) {
			originalImage(data);
		} else {
			if (data != null) {
				originalImage(data);
			} else {
				// Here if we give the uri, we need to read it
				String path = Environment.getExternalStorageDirectory()
						+ FeatureFunction.PUB_TEMP_DIRECTORY + TEMP_FILE_NAME;
				String extension = path.substring(path.indexOf("."),
						path.length());
				if (FeatureFunction.isPic(extension)) {
					// startPhotoZoom(Uri.fromFile(new File(path)));
					Intent intent = new Intent(mContext,
							RotateImageActivity.class);
					intent.putExtra("path", path);
					startActivityForResult(intent, REQUEST_GET_BITMAP);
				}
			}
		}
	}

	/**
	 * 处理图片
	 * 
	 * @param data
	 *            图片对应的Data
	 */
	private void originalImage(Intent data) {
		/*
		 * switch (requestCode) {
		 */
		// case FLAG_CHOOSE:
		Uri uri = data.getData();
		// Log.d("may", "uri=" + uri + ", authority=" + uri.getAuthority());
		if (!TextUtils.isEmpty(uri.getAuthority())) {
			Cursor cursor = getContentResolver().query(uri,
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			if (null == cursor) {
				// Toast.makeText(mContext, R.string.no_found,
				// Toast.LENGTH_SHORT).show();
				return;
			}
			cursor.moveToFirst();
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			Log.d("may", "path=" + path);
			String extension = path.substring(path.lastIndexOf("."),
					path.length());
			if (FeatureFunction.isPic(extension)) {
				Intent intent = new Intent(mContext, RotateImageActivity.class);
				intent.putExtra("path", path);
				startActivityForResult(intent, REQUEST_GET_BITMAP);

				// startPhotoZoom(data.getData());

			} else {
				// Toast.makeText(mContext, R.string.please_choose_pic,
				// Toast.LENGTH_SHORT).show();
			}
			// ShowBitmap(false);

		} else {
			Log.d("may", "path=" + uri.getPath());
			String path = uri.getPath();
			String extension = path.substring(path.lastIndexOf("."),
					path.length());
			if (FeatureFunction.isPic(extension)) {
				Intent intent = new Intent(mContext, RotateImageActivity.class);
				intent.putExtra("path", path);
				startActivityForResult(intent, REQUEST_GET_BITMAP);
			} else {
				// Toast.makeText(mContext, R.string.please_choose_pic,
				// Toast.LENGTH_SHORT).show();
			}
			// mImageFilePath = uri.getPath();
			// ShowBitmap(false);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		// 选择地图之后执行操作
		case RESQUEST_CODE:
			if (data != null && RESULT_OK == resultCode) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {

					MapInfo mapInfo = (MapInfo) data
							.getSerializableExtra("mapInfo");
					if (mapInfo == null) {
						Toast.makeText(
								mContext,
								mContext.getString(R.string.get_location_failed),
								Toast.LENGTH_SHORT).show();
						return;
					}

					sendMap(mapInfo);
				}
			}
			break;

		// 从相册选择图片之后执行操作
		case REQUEST_GET_URI:
			if (resultCode == RESULT_OK) {
				doChoose(true, data);
			}

			break;

		// 调用系统相机拍照后执行操作
		case REQUEST_GET_IMAGE_BY_CAMERA:
			if (resultCode == RESULT_OK) {
				doChoose(false, data);
			}
			break;

		// 图片处理完成之后执行操作
		case REQUEST_GET_BITMAP:
			if (resultCode == RESULT_OK) {
				mFilePath = data.getStringExtra("path");
				if (mFilePath != null && !mFilePath.equals("")) {
					sendFile(TCMessageType.PICTURE, mFilePath);
				}
			}

			break;

		case REQUEST_FILE_SELECT:
			if (resultCode == RESULT_OK) {
				getPath(data);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 根据文件URI获取文件路径
	 * 
	 * @param uri
	 * @return
	 */
	private void getPath(Intent data) {

		Uri uri = data.getData();

		String path = "";

		if ("content".equalsIgnoreCase(uri.getScheme())) {
			Cursor cursor = getContentResolver().query(uri,
					new String[] { MediaStore.MediaColumns.DATA }, null, null,
					null);
			if (null == cursor) {
				// Toast.makeText(mContext, R.string. no_found,
				// Toast.LENGTH_SHORT).show();
				return;
			}
			cursor.moveToFirst();
			path = cursor.getString(cursor
					.getColumnIndex(MediaStore.MediaColumns.DATA));
		} else if ("file".equalsIgnoreCase(uri.getScheme())) {
			path = uri.getPath();
		}

		if (!TextUtils.isEmpty(path)) {
			/*
			 * String extension = path.substring(path.lastIndexOf("."),
			 * path.length());
			 * 
			 * int fileType = FileType.OTHER;
			 * if(FeatureFunction.isPic(extension)){
			 * 
			 * }else if(FeatureFunction.isWord(extension)){
			 * 
			 * }else if(FeatureFunction.isExcel(extension)){
			 * 
			 * }else if(FeatureFunction.isPDF(extension)){
			 * 
			 * }else if(FeatureFunction.isTXT(extension)){
			 * 
			 * }else {
			 * 
			 * }
			 */

			File file = new File(path);
			if (file.exists()) {
				sendFile(TCMessageType.FILE, path);
			}
		}

	}

	/**
	 * 页面初始化后，首次从数据表中读取最新的消息，并更新和该对象的所有消息的阅读状态为已读状态
	 */
	private void initTCMessages() {
		// 更新该对象的所有消息的阅读状态为已读
		boolean status = TCChatManager.getInstance().resetUnreadCount(
				mSession.getFromId(), mSession.getChatType());

		// 更新成功后发通知更新会话列表中该会话的未读数和会话列表总的未读数
		if (status) {
			mContext.sendBroadcast(new Intent(ChatActivity.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
		}

		// 从数据表中获取消息
		messageInfos = TCChatManager.getInstance().getMessageList(-1,
				mSession.getFromId(), mSession.getChatType(), 20);

		if (messageInfos == null) {
			messageInfos = new ArrayList<TCMessage>();
		}

	}

	/**
	 * 发送文本消息
	 */
	private void sendText() {
		Log.d(TAG, "sendText()");
		String str = mContentEdit.getText().toString();
		if (str != null
				&& (str.trim().replaceAll("\r", "").replaceAll("\t", "")
						.replaceAll("\n", "").replaceAll("\f", "")) != "") {
			if (str.length() > Common.MESSAGE_CONTENT_LEN) {
				// showToast(mContext.getString(R.string.message_limit_count));
				return;
			}
			if (str.contains("@")) {
				showToast(mContext.getString(R.string.notsupport));
				return;
			}
			if (str.contains("\"")) {

				str = str.trim().replaceAll("\"", "“");
			}

			mContentEdit.setText("");

			TCMessage message = new TCMessage();
			// 设置消息唯一标示符
			message.setMessageTag(UUID.randomUUID().toString());
			// 设置接收者ID
			message.setToId(mSession.getFromId());
			// 设置接收者名称
			message.setToName(mSession.getSessionName());
			// 设置接收者头像
			message.setToHead(mSession.getSessionHead());
			// 设置接收者扩展信息
			// HashMap<String, String> toExendMap = new HashMap<String,
			// String>();
			// message.setToExtendMap(mSession.getExtendMap());

			// 设置发送者ID
			message.setFromId(Common.getLoginResult(mContext).uid);
			// 设置发送者名称
			message.setFromName(Common.getLoginResult(mContext).nickName);
			// 设置发送者头像
			message.setFromHead(Common.getLoginResult(mContext).mSmallHead);

			// 设置发送者扩展信息
			// HashMap<String, String> fromExendMap = new HashMap<String,
			// String>();
			// 设置发送者签名
			// fromExendMap.put("sign", Common.getLoginResult(mContext).sign);
			// message.setFromExtendMap(fromExendMap);

			// HashMap<String, String> exendMap = new HashMap<String, String>();
			// 设置发送者签名
			// exendMap.put("sign", Common.getLoginResult(mContext).sign);
			// message.setMsgExtendMap(exendMap);

			// 设置聊天类型
			message.setChatType(mSession.getChatType());
			// 设置消息类型
			message.setMessageType(TCMessageType.TEXT);
			message.setSendTime(System.currentTimeMillis());

			// 创建文本消息
			TextMessageBody body = new TextMessageBody(str);
			message.addBody(body);

			sendBroad2Save(message);
		}
	}

	/**
	 * 发送地图信息
	 * 
	 * @param mapInfo
	 *            地图位置对象
	 */
	private void sendMap(MapInfo mapInfo) {
		TCMessage message = new TCMessage();
		// 设置消息唯一标示符
		message.setMessageTag(UUID.randomUUID().toString());
		// 设置接收者ID
		message.setToId(mSession.getFromId());
		// 设置接收者名称
		message.setToName(mSession.getSessionName());
		// 设置接收者头像
		message.setToHead(mSession.getSessionHead());
		// 设置接收者扩展信息
		// HashMap<String, String> toExendMap = new HashMap<String,
		// String>();
		// message.setToExtendMap(mSession.getExtendMap());

		// 设置发送者ID
		message.setFromId(Common.getLoginResult(mContext).uid);
		// 设置发送者名称
		message.setFromName(Common.getLoginResult(mContext).nickName);
		// 设置发送者头像
		message.setFromHead(Common.getLoginResult(mContext).mSmallHead);
		// 设置发送者扩展信息
		// HashMap<String, String> fromExendMap = new HashMap<String, String>();
		// 设置发送者签名
		// fromExendMap.put("sign", Common.getLoginResult(mContext).sign);
		// message.setFromExtendMap(fromExendMap);

		// 设置聊天类型
		message.setChatType(mSession.getChatType());
		// 设置消息类型
		message.setMessageType(TCMessageType.MAP);
		message.setSendTime(System.currentTimeMillis());

		// 创建地图消息
		LocationMessageBody body = new LocationMessageBody(
				Double.parseDouble(mapInfo.getLat()),
				Double.parseDouble(mapInfo.getLon()), mapInfo.getAddr());
		message.addBody(body);

		sendBroad2Save(message);
	}

	/**
	 * 发送语音和图片消息
	 * 
	 * @param type
	 *            类型（图片和语音类型）
	 * @param filePath
	 *            文件路径
	 */
	private void sendFile(int type, String filePath) {

		TCMessage message = new TCMessage();
		// 设置消息唯一标示符
		message.setMessageTag(UUID.randomUUID().toString());
		// 设置接收者ID
		message.setToId(mSession.getFromId());
		// 设置接收者名称
		message.setToName(mSession.getSessionName());
		// 设置接收者头像
		message.setToHead(mSession.getSessionHead());
		// 设置接收者扩展信息
		// HashMap<String, String> toExendMap = new HashMap<String,
		// String>();
		// message.setToExtendMap(mSession.getExtendMap());

		// 设置发送者ID
		message.setFromId(Common.getLoginResult(mContext).uid);
		// 设置发送者名称
		message.setFromName(Common.getLoginResult(mContext).nickName);
		// 设置发送者头像
		message.setFromHead(Common.getLoginResult(mContext).mSmallHead);
		// 设置发送者扩展信息
		// HashMap<String, String> fromExendMap = new HashMap<String, String>();
		// 设置发送者签名
		// fromExendMap.put("sign", Common.getLoginResult(mContext).sign);
		// message.setFromExtendMap(fromExendMap);

		// 设置聊天类型
		message.setChatType(mSession.getChatType());
		// 设置消息类型
		message.setMessageType(type);
		message.setSendTime(System.currentTimeMillis());
		if (type == TCMessageType.VOICE) { // 如果为语音类型，则创建语音消息
			VoiceMessageBody body = new VoiceMessageBody(filePath,
					(int) mReaderImpl.getReaderTime());
			message.addBody(body);

		} else if (type == TCMessageType.PICTURE) { // 如果为图片类型，则创建图片消息
			ImageMessageBody body = new ImageMessageBody(filePath);
			message.addBody(body);
		} else if (type == TCMessageType.FILE) {
			FileMessageBody body = new FileMessageBody(filePath);
			message.addBody(body);
		}
		addTCMessage(message);

		if (type == TCMessageType.FILE) {
			Intent intent = new Intent(
					HaoXinProjectActivity.ACTION_SEND_FILE_MESSAGE);
			intent.putExtra("message", message);
			mContext.sendBroadcast(intent);
		} else {
			sendFilePath(message, 0);
		}

	}

	/**
	 * 发送文件
	 * 
	 * @param messageInfo
	 *            待发送的消息体
	 * @param isResend
	 *            是否为重发
	 */
	private void sendFilePath(TCMessage messageInfo, int isResend) {
		sendMessage(messageInfo, isResend);
	}

	/**
	 * 重发语音和图片消息
	 * 
	 * @param messageInfo
	 *            待重发的消息体
	 */
	private void resendFile(TCMessage messageInfo) {
		try {
			sendFilePath(messageInfo, 1);
		} catch (Exception e) {
			Log.d(TAG, "resendVoice:", e);
			showToast(mContext.getString(R.string.resend_failed));
		}
	}

	/**
	 * 下载音频
	 * 
	 * @param msg
	 *            音频消息
	 * @param type
	 */
	private synchronized void downVoice(final TCMessage msg, final int type) {
		if (!FeatureFunction.checkSDCard()) {
			return;

		}

		if (downVoiceList.contains(msg.getVoiceMessageBody().getVoiceUrl())) {
			// showToast(mContext.getString(R.string.download_voice));
			return;
		}
		downVoiceList.add(msg.getVoiceMessageBody().getVoiceUrl());
		File voicePath = ReaderImpl.getAudioPath(getBaseContext());
		String tag = FeatureFunction.generator(msg.getVoiceMessageBody()
				.getVoiceUrl());
		String tagName = new File(voicePath, tag).getAbsolutePath();
		HttpGet get = new HttpGet(msg.getVoiceMessageBody().getVoiceUrl());
		DefaultHttpClient client = (DefaultHttpClient) Utility
				.getNewHttpClient(Utility.DEFAULT_TIMEOUT);

		VoiceTask<File> voiceTask = new VoiceTask<File>(client,
				new SyncBasicHttpContext(new BasicHttpContext()),
				new AjaxCallBack<File>() {
					@Override
					public void onSuccess(File t) {
						super.onSuccess(t);
						downVoiceSuccess(msg, type);
						downVoiceList.remove(msg.getVoiceMessageBody()
								.getVoiceUrl());
					}

					@Override
					public void onFailure(Throwable t, String strMsg) {
						super.onFailure(t, strMsg);
						showToast(mContext
								.getString(R.string.download_voice_error)
								+ strMsg);
						downVoiceList.remove(msg.getVoiceMessageBody()
								.getVoiceUrl());
					}
				});

		Executor executor = Executors.newFixedThreadPool(5,
				new ThreadFactory() {
					private final AtomicInteger mCount = new AtomicInteger(1);

					@Override
					public Thread newThread(Runnable r) {
						Thread tread = new Thread(r, "FinalHttp #"
								+ mCount.getAndIncrement());
						tread.setPriority(Thread.NORM_PRIORITY - 1);
						return tread;
					}
				});
		voiceTask.executeOnExecutor(executor, get, tagName);
	}

	/**
	 * 语音下载成功之后播放语音
	 * 
	 * @param msg
	 *            语音消息体
	 * @param type
	 *            0--不播放 1--播放
	 */
	private void downVoiceSuccess(final TCMessage msg, final int type) {

		// 如果当前正在播放的语音和下载成功的语音消息的唯一标示符相同，则播放此语音
		if (type == 1
				&& mPlayListener.getMessageTag().equals(msg.getMessageTag())) {
			mPlayListener.play(msg);
		}
	}

	/**
	 * 删除临时图片
	 * 
	 * @param path
	 */
	private void deleteImgFile(String path) {
		File file = new File(path);
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	/********* 聊天选择器 ***********/

	private void togInfoSelect() {
		hideExpra();

		// 如果选中语音消息，则隐藏文本消息框， 显示录音按钮
		if (mToggleBtn.isChecked()) {
			// 语音开启显示
			mContentEdit.setVisibility(View.GONE);
			mMsgSendBtn.setVisibility(View.GONE);
			mVoiceSendBtn.setVisibility(View.VISIBLE);
			hideSoftKeyboard(mToggleBtn);
		} else { // 如果选中文本消息，则隐藏录音按钮， 显示文本消息框
			mContentEdit.setVisibility(View.VISIBLE);
			mMsgSendBtn.setVisibility(View.VISIBLE);
			mVoiceSendBtn.setVisibility(View.GONE);
			hideSoftKeyboard(mToggleBtn);
		}
	}

	/**
	 * 重发信息
	 * 
	 * @param messageInfo
	 *            带重发的消息体
	 */
	private void btnResendAction(TCMessage messageInfo) {
		if (messageInfo != null) {
			if (messageInfo.getFileMessageBody() != null) {

				messageInfo.getFileMessageBody().setUploadProgress(0);
			} else {
				messageInfo.getTextMessageBody();
			}
			mAdapter.notifyDataSetChanged();
			switch (messageInfo.getMessageType()) {
			case TCMessageType.PICTURE:
			case TCMessageType.VOICE:
			case TCMessageType.FILE:
				resendFile(messageInfo);
				break;
			case TCMessageType.TEXT:
				// sendBroad2Update(messageInfo, 1);
				sendMessage(messageInfo, 1);
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 显示表情控件
	 */
	private void btnEmojiAction() {
		showEmojiGridView();
	}

	/**
	 * 点击相机按钮
	 */
	private void btnCameraAction() {
		getImageFromCamera();
		hideExpra();
	}

	/**
	 * 点击相册按钮
	 */
	private void btnPhotoAction() {
		getImageFromGallery();
		hideExpra();
	}

	/**
	 * 点击地图按钮
	 */
	// private void btnLocationAction(){
	// hideExpra();
	// Intent intent = new Intent(this, LocationActivity.class);
	// startActivityForResult(intent, RESQUEST_CODE);
	// }

	/**
	 * 点击展开按钮
	 */
	private void btnAddAction() {
		if (mChatExpraLayout.getVisibility() == View.VISIBLE) {
			hideExpra();
		} else {

			if (mEmotionLayout.getVisibility() == View.VISIBLE) {
				hideEmojiGridView();
			}

			showExpra();
		}
	}

	private void showExpra() {
		hideSoftKeyboard();
		mChatExpraLayout.setVisibility(View.VISIBLE);
	}

	private void hideExpra() {
		mChatExpraLayout.setVisibility(View.GONE);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			hideSoftKeyboard();
			if (mChatExpraLayout.getVisibility() == View.VISIBLE
					|| mEmotionLayout.getVisibility() == View.VISIBLE) {
				hideEmojiGridView();
				// hideExpra();
				return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	/**
	 * 将消息加入到消息列表中，并发送消息
	 * 
	 * @param msg
	 *            消息体
	 */
	private void sendBroad2Save(TCMessage msg) {
		addTCMessage(msg);
		sendMessage(msg, 0);
	}

	/**
	 * 发送消息
	 * 
	 * @param msg
	 *            待发送的消息体
	 * @param isResend
	 */
	private void sendMessage(final TCMessage msg, final int isResend) {

		TCChatManager.getInstance().sendMessage(msg, new TCMessageListener() {

			@Override
			public void onError(TCError error) {

			}

			@Override
			public void doComplete() {

			}

			@Override
			public void onError(TCMessage tcMessage, TCError error) {
				// 消息发送出错进入该回调
				if (error.errorCode == 3) { // 如果code为3，表示已经不是该群的成员
					TCChatManager.getInstance().deleteGroupFromTable(
							mSession.getFromId(), mSession.getChatType());
					TCChatManager.getInstance().deleteSession(
							mSession.getFromId(), mSession.getChatType());
					mContext.sendBroadcast(new Intent(
							ChatActivity.UPDATE_COUNT_ACTION));
					mContext.sendBroadcast(new Intent(
							HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
					mHandler.sendEmptyMessage(SHOW_KICK_OUT_DIALOG);
				} else {
					Message message = new Message();
					message.what = SEND_FAILED;
					message.arg1 = isResend;
					message.obj = tcMessage;
					mHandler.sendMessage(message);
				}
			}

			@Override
			public void doComplete(TCMessage tcMessage) {
				// 消息发送成后之后进入该回调

				mContext.sendBroadcast(new Intent(
						ChatActivity.UPDATE_COUNT_ACTION));
				mContext.sendBroadcast(new Intent(
						HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));

				Message message = new Message();
				message.what = SEND_SUCCESS;
				message.arg1 = isResend;
				message.obj = tcMessage;
				mHandler.sendMessage(message);
			}

			@Override
			public void onProgress(int progress) {
				if (msg.getMessageType() == TCMessageType.FILE) {
					msg.getFileMessageBody().setUploadProgress(progress);
					mHandler.sendEmptyMessage(UPDATE_PROGRESS);
				}
			}
		});

	}

	/**
	 * 添加消息体到消息列表中
	 * 
	 * @param info
	 *            待添加的消息体
	 */
	private void addTCMessage(TCMessage info) {

		info.setSendState(2);
		if (messageInfos.size() == 0) {
			messageInfos.add(info);
		} else {
			boolean isEixst = false;
			for (int i = 0; i < messageInfos.size(); i++) {
				if (messageInfos.get(i).getMessageTag()
						.equals(info.getMessageTag())) { // 判断消息列表中是否存在此条消息
					isEixst = true;
					break;
				}
			}
			if (!isEixst) { // 如果不存在则加入到消息列表的最后
				messageInfos.add(info);
			}
		}
		mAdapter.notifyDataSetInvalidated(); // 更新界面
		if (messageInfos != null && messageInfos.size() != 0) {
			mListView.setSelection(messageInfos.size() - 1);
		}

		// 更新会话
		TCChatManager.getInstance().addSession(info);

		// 更新会话列表此会话对应的内容和时间
		mContext.sendBroadcast(new Intent(ChatActivity.UPDATE_COUNT_ACTION));
	}

	@Override
	protected void onPause() {
		super.onPause();
		mVoiceSendBtn.setBackgroundResource(R.drawable.send_voice_btn_n);
		if (!mReaderImpl.mIsStop) {
			mReaderImpl.cancelDg();
		} else {
			mReaderImpl.mIsStop = false;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (mIsRegisterReceiver) {
			unregisterReceiver();
		}

		if (mReaderImpl != null) {
			mReaderImpl.unregisterRecordReceiver();
		}

		// 更新会话列表此会话
		mContext.sendBroadcast(new Intent(ChatActivity.UPDATE_COUNT_ACTION));
		// 更新会话列表总的未读数
		mContext.sendBroadcast(new Intent(
				HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
		// 停止语音播放
		mPlayListener.stop();

		// 释放该页面存在内存中的图片
		if (messageInfos != null) {
			for (int i = 0; i < messageInfos.size(); i++) {

				if (!TextUtils.isEmpty(messageInfos.get(i).getFromHead())) {
					ImageView headerView = (ImageView) mListView
							.findViewWithTag(messageInfos.get(i).getFromHead());
					if (headerView != null) {
						headerView.setImageBitmap(null);
						headerView.setImageResource(R.drawable.default_header);
					}
				}

				if (messageInfos.get(i).getMessageType() == TCMessageType.PICTURE) {
					ImageView imageView = (ImageView) mListView
							.findViewWithTag(messageInfos.get(i)
									.getImageMessageBody().getImageUrlS());
					if (imageView != null) {
						imageView.setImageBitmap(null);
						imageView.setVisibility(View.GONE);
					}

					if (!messageInfos.get(i).getImageMessageBody()
							.getImageUrlS().startsWith("http://")
							&& messageInfos.get(i).getSendState() != 1) {
						if (mAdapter != null) {
							mAdapter.getBitmapCache().remove(
									messageInfos.get(i).getImageMessageBody()
											.getImageUrlS());
						}
					}
				}
			}
		}

		/**
		 * 删除发送成功的图片文件
		 */
		Set<String> keys = mAdapter.getBitmapCache().keySet();
		if (keys != null && !keys.isEmpty()) {
			for (String key : keys) {
				deleteImgFile(key);
			}
		}

		if (mAdapter != null) {
			FeatureFunction.freeBitmap(mAdapter.getImageLoader()
					.getImageBuffer());
			freeBitmap(mAdapter.getBitmapCache());
		}
		System.gc();

	}

	private void freeBitmap(HashMap<String, SoftReference<Bitmap>> cache) {
		if (cache == null || cache.isEmpty()) {
			return;
		}
		for (SoftReference<Bitmap> bitmap : cache.values()) {
			if (bitmap.get() != null && !bitmap.get().isRecycled()) {
				bitmap.get().recycle();
				bitmap = null;

			}
		}
		cache.clear();
	}

	/**
	 * 注册Receiver
	 */
	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_NOTIFY_CHAT_MESSAGE);
		filter.addAction(DESTORY_ACTION);
		filter.addAction(ACTION_DESTORY_GROUP_CHAT);
		filter.addAction(ACTION_DESTORY_TEMP_CHAT);
		filter.addAction(ACTION_READ_VOICE_STATE);
		filter.addAction(ACTION_RECORD_AUTH);
		filter.addAction(ACTION_CLEAR_RECORD);
		filter.addAction(ACTION_UPDATE_TEMP_CHAT_NAME);
		filter.addAction(GroupChatActivity.ACTION_KICK_GROUP);
		filter.addAction(HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT);
		filter.addAction(GroupChatActivity.ACTION_DELETE_MY_GROUP);
		filter.addAction(ACTION_SEND_FILE_COMPLETE);
		filter.addAction(ACTION_SEND_FILE_FAILED);
		filter.addAction(ACTION_UPDATE_FILE_PROGRESS);
		filter.addAction(FileDetailActivity.ACTION_DOWNLOAD_FILE_PROGRESS);
		registerReceiver(chatReceiver, filter);
		mIsRegisterReceiver = true;
	}

	private void unregisterReceiver() {
		unregisterReceiver(chatReceiver);
	}

	/**
	 * 清除通知栏通知
	 */
	void clearNotification() {
		NotificationManager notificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (notificationManager != null && mSession != null) {
			notificationManager.cancel(mSession.getFromId().hashCode());
		}

	}

	/**
	 * 判断是否在上滑
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean upMove(int y) {
		if ((mLastY - y) > UP_MOVE_CHECK_NUM) {
			return true;
		}
		return false;
	}

	/**
	 * 语音按钮触发
	 */
	class OnVoice implements OnTouchListener {

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mLastY = (int) event.getY();
				// 说话键按下处理事件
				mVoiceSendBtn
						.setBackgroundResource(R.drawable.send_voice_btn_d);
				if (!FeatureFunction.checkSDCard()) {
					break;
				}

				// 显示录音对话框，并录音
				mReaderImpl.showDg();
				break;
			case MotionEvent.ACTION_UP:
				mVoiceSendBtn
						.setBackgroundResource(R.drawable.send_voice_btn_n);
				// 说话键手指谈起处理事件，如果当前录音未停止，则停止录音，并隐藏录音对话框
				if (!mReaderImpl.mIsStop) {
					mReaderImpl.cancelDg();
				} else {
					mReaderImpl.mIsStop = false;
				}
				break;

			case MotionEvent.ACTION_MOVE:
				if (upMove((int) event.getY())) {
					audioRecorder.delRecord();
				}
				break;
			}
			return true;
		}

	}

	/************** 表情功能 *************/

	/**
	 * 显示表情列表
	 */
	private void showEmojiGridView() {
		hideExpra();
		mToggleBtn.setChecked(false);
		togInfoSelect();
		mEmotionLayout.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏表情列表
	 */
	private void hideEmojiGridView() {
		hideExpra();

		mEmotionLayout.setVisibility(View.GONE);
	}

	private View.OnFocusChangeListener sendTextFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			// TODO Auto-generated method stub
			if (hasFocus) {
				// 文本框得到焦点，隐藏附加信息和表情列表
				hideEmojiGridView();
			}

		}
	};

	private View.OnClickListener sendTextClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// 获取到文本框的点击事件隐藏表情
			hideEmojiGridView();
		}
	};

	/**
	 * 聊天页面Receiver
	 */
	private BroadcastReceiver chatReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (ACTION_NOTIFY_CHAT_MESSAGE.equals(action)) { // 收到消息通知
				final TCMessage msg = (TCMessage) intent
						.getSerializableExtra(EXTRAS_MESSAGE);
				if ((msg.getChatType() == ChatType.SINGLE_CHAT && msg
						.getFromId().equals(mSession.getFromId()))
						|| (msg.getToId().equals(mSession.getFromId()) && msg
								.getChatType() == mSession.getChatType())) {
					// 如果消息的对应的聊天对象为当前页面的聊天对象，则将消息加入到消息列表中
					// 设置消息的阅读状态为已读
					msg.setReadState(1);
					// 重置消息表中的该条消息为已读
					TCChatManager.getInstance().updateMessageReadState(msg);

					// 更新消息总的未读数和会话列表数据
					mContext.sendBroadcast(new Intent(
							ChatActivity.UPDATE_COUNT_ACTION));
					mContext.sendBroadcast(new Intent(
							HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
					notifyMessage(msg);
				}
			} else if (action.equals(DESTORY_ACTION)) { // 单聊销毁页面通知
				ChatMainActivity.this.finish();
			} else if (action.equals(ACTION_DESTORY_GROUP_CHAT)) {
				ChatMainActivity.this.finish();
			} else if (action.equals(ACTION_DESTORY_TEMP_CHAT)) {
				ChatMainActivity.this.finish();
			} else if (action.equals(GroupChatActivity.ACTION_KICK_GROUP)) {
				String id = intent.getStringExtra("id");
				boolean isExit = intent.getBooleanExtra("isexit", false);
				if (!TextUtils.isEmpty(id)) {
					if (id.equals(mSession.getFromId())) {
						showDestoryDialog(1, isExit);
					}
				}

			} else if (action
					.equals(HaoXinProjectActivity.ACTION_KICK_OR_DELETE_TEMP_CHAT)) {
				String id = intent.getStringExtra("id");
				int excuteType = intent.getIntExtra("excuteType", 0);
				boolean isExit = intent.getBooleanExtra("isexit", false);
				if (!TextUtils.isEmpty(id)) {
					if (id.equals(mSession.getFromId())) {
						showDestoryDialog(excuteType, isExit);
					}
				}
			} else if (ACTION_READ_VOICE_STATE.equals(action)) {
				final TCMessage messageInfo = (TCMessage) intent
						.getSerializableExtra(EXTRAS_MESSAGE);
				TCChatManager.getInstance().updateMessageVoiceReadState(
						messageInfo);
				changeVoiceState(messageInfo);

			} else if (action.equals(ACTION_RECORD_AUTH)) {
				showToast(mContext.getString(R.string.record_auth_control));
			} else if (action.equals(GroupChatActivity.ACTION_DELETE_GROUP)) {
				String id = intent.getStringExtra("id");
				if (!TextUtils.isEmpty(id)) {
					if (id.equals(mSession.getFromId())) {
						showDestoryDialog(0, false);
					}
				}
			} else if (action.equals(GroupChatActivity.ACTION_DELETE_MY_GROUP)) {
				String id = intent.getStringExtra("id");
				if (!TextUtils.isEmpty(id)) {
					if (id.equals(mSession.getFromId())) {
						showDestoryDialog(0, true);
					}
				}
			} else if (action.equals(ACTION_CLEAR_RECORD)) {
				String id = intent.getStringExtra("id");
				int chatType = intent.getIntExtra("chatType", 0);
				if (!TextUtils.isEmpty(id) && id.equals(mSession.getFromId())
						&& chatType == mSession.getChatType()) {
					// 从数据表中获取消息
					messageInfos = TCChatManager.getInstance().getMessageList(
							-1, mSession.getFromId(), mSession.getChatType(),
							20);

					if (messageInfos == null) {
						messageInfos = new ArrayList<TCMessage>();
					}

					if (mAdapter != null) {
						mAdapter.setData(messageInfos);
						mAdapter.notifyDataSetChanged();
					}
				}
			} else if (action.equals(ACTION_UPDATE_TEMP_CHAT_NAME)) {
				String id = intent.getStringExtra("id");
				int chatType = intent.getIntExtra("chatType", 0);
				String name = intent.getStringExtra("name");
				String head = intent.getStringExtra("head");
				if (!TextUtils.isEmpty(id) && id.equals(mSession.getFromId())
						&& chatType == mSession.getChatType()) {
					mSession.setSessionName(name);
					titileTextView.setText(name);

					if (!TextUtils.isEmpty(head)) {
						mSession.setSessionHead(head);
					}
				}
			} else if (action.equals(ACTION_SEND_FILE_COMPLETE)) {
				TCMessage tcMessage = (TCMessage) intent
						.getSerializableExtra("message");

				mContext.sendBroadcast(new Intent(
						ChatActivity.UPDATE_COUNT_ACTION));
				mContext.sendBroadcast(new Intent(
						HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));

				Message message = new Message();
				message.what = SEND_SUCCESS;
				message.obj = tcMessage;
				mHandler.sendMessage(message);
			} else if (action.equals(ACTION_SEND_FILE_FAILED)) {
				TCMessage tcMessage = (TCMessage) intent
						.getSerializableExtra("message");
				TCError error = (TCError) intent.getSerializableExtra("error");

				if (error.errorCode == 3) { // 如果code为3，表示已经不是该群的成员
					TCChatManager.getInstance().deleteGroupFromTable(
							mSession.getFromId(), mSession.getChatType());
					TCChatManager.getInstance().deleteSession(
							mSession.getFromId(), mSession.getChatType());
					mContext.sendBroadcast(new Intent(
							ChatActivity.UPDATE_COUNT_ACTION));
					mContext.sendBroadcast(new Intent(
							HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
					mHandler.sendEmptyMessage(SHOW_KICK_OUT_DIALOG);
				} else {
					Message message = new Message();
					message.what = SEND_FAILED;
					message.obj = tcMessage;
					mHandler.sendMessage(message);
				}
			} else if (action.equals(ACTION_UPDATE_FILE_PROGRESS)) {
				TCMessage tcMessage = (TCMessage) intent
						.getSerializableExtra("message");

				for (int i = 0; i < messageInfos.size(); i++) {
					if (tcMessage.getMessageTag().equals(
							messageInfos.get(i).getMessageTag())) {
						messageInfos
								.get(i)
								.getFileMessageBody()
								.setUploadProgress(
										tcMessage.getFileMessageBody()
												.getUploadProgress());

						mAdapter.notifyDataSetChanged();
						break;
					}
				}
			} else if (action
					.equals(FileDetailActivity.ACTION_DOWNLOAD_FILE_PROGRESS)) {
				int progress = intent.getIntExtra("progress", 0);

				if (progress > 0) {
					mAdapter.notifyDataSetChanged();
				}
			}
		}
	};

	/**
	 * 收到新消息之后加入到消息列表最后
	 * 
	 * @param msg
	 *            收到的消息
	 */
	private void notifyMessage(final TCMessage msg) {
		if (msg == null) {
			return;
		}

		mHandler.post(new Runnable() {

			@Override
			public void run() {
				try {
					// 当该信息不来自好友就过滤掉!
					if (msg.getFromId().equals(Common.getUid(mContext))) {
						return;
					}
					messageInfos.add(msg);
					mAdapter.notifyDataSetInvalidated();
					// 如果消息列表在追加最新消息前的位置是最后一条消息，则消息加入之后，将ListView滑动到最后
					if (messageInfos.size() == 1
							|| (mListView.getLastVisiblePosition() == messageInfos
									.size() - 2)) {
						mListView.setSelection(messageInfos.size() - 1);
					}
				} catch (Exception e) {

				}

			}
		});
	}

	private void changeVoiceState(final TCMessage messageInfo) {
		if (messageInfo != null) {
			mHandler.post(new Runnable() {

				@Override
				public void run() {
					modifyMessageVoiceState(messageInfo);
				}
			});
		}
	}

	/**
	 * 更新列表中消息的发送状态，如果是图片或语音消息，则更新列表中图片和语音的路径为URL
	 * 
	 * @param messageInfo
	 */
	private void modifyMessageState(TCMessage messageInfo) {

		for (int i = 0; i < messageInfos.size(); i++) {
			if (messageInfo.getMessageTag().equals(
					messageInfos.get(i).getMessageTag())) {
				TCMessage tempInfo = messageInfos.get(i);
				tempInfo.setSendState(messageInfo.getSendState());

				if (messageInfo.getMessageType() == TCMessageType.PICTURE) {
					tempInfo.setImageMessageBody(messageInfo
							.getImageMessageBody());
				} else if (messageInfo.getMessageType() == TCMessageType.VOICE) {
					tempInfo.setVoiceMessageBody(messageInfo
							.getVoiceMessageBody());
				}
				tempInfo.setReadState(messageInfo.getReadState());
				tempInfo.setSendTime(messageInfo.getSendTime());
				mAdapter.notifyDataSetInvalidated();
				break;
			}
		}

	}

	/**
	 * 修改语音消息的阅读状态
	 * 
	 * @param messageInfo
	 *            消息
	 */
	private void modifyMessageVoiceState(TCMessage messageInfo) {
		for (int i = 0; i < messageInfos.size(); i++) {
			if (messageInfo.getMessageTag().equals(
					messageInfos.get(i).getMessageTag())) {
				TCMessage tempInfo = messageInfos.get(i);
				tempInfo.setVoiceReadState(messageInfo.getVoiceReadState());
				break;
			}
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		return true;
	}

	private void showDeleteDialog(final int pos) {

		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button promptView = (Button) layout.findViewById(R.id.camera);
		final Button deleteBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);

		promptView.setEnabled(false);
		promptView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		promptView.setTextColor(mContext.getResources().getColor(
				R.color.content_gray_color));

		deleteBtn.setText(mContext.getString(R.string.delete));

		deleteBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
				TCChatManager.getInstance().deleteMessage(
						messageInfos.get(pos).getMessageTag());
				messageInfos.remove(pos);
				mAdapter.notifyDataSetChanged();

				mContext.sendBroadcast(new Intent(
						ChatActivity.UPDATE_COUNT_ACTION));
				mContext.sendBroadcast(new Intent(
						HaoXinProjectActivity.UPDATE_SESSION_UNREAD_COUNT));
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		// set a large value put it in bottom
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(true);

		dlg.setContentView(layout);
		dlg.show();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {

		// 点击发送按钮
		case R.id.chat_box_btn_send:
			hideSoftKeyboard();
			hideEmojiGridView();
			sendText();
			break;

		// 点击文本消息和语音消息切换按钮
		case R.id.chat_box_btn_info:
			togInfoSelect();
			break;

		// 点击添加按钮
		case R.id.chat_box_btn_add:
			btnAddAction();
			break;

		// 点击相机按钮
		case R.id.chat_box_expra_btn_camera:
			btnCameraAction();
			break;

		// 点击相册按钮
		case R.id.chat_box_expra_btn_picture:
			btnPhotoAction();
			break;

		// 点击地图按钮
		// case R.id.chat_box_expra_btn_location:
		// btnLocationAction();
		// break;

		// 点击表情按钮
		case R.id.chat_box_expra_btn_experssion:
			btnEmojiAction();
			break;

		// 点击返回按钮
		case R.id.left_btn:
			hideSoftKeyboard();
			this.finish();
			break;

		case R.id.right_btn:
			if (mSession.getChatType() == ChatType.GROUP_CHAT) { // 群聊，进入群详细
				Intent groupIntent = new Intent(mContext,
						GroupDetailActivity.class);
				groupIntent.putExtra("groupid", mSession.getFromId());
				startActivity(groupIntent);
			} else {

				// 单聊和临时会话，进入聊天详细页面
				Intent chatIntent = new Intent(mContext,
						ChatDetailActivity.class);
				// 临时会话，则需传入临时会话ID
				if (mSession.getChatType() == ChatType.TEMP_CHAT) {
					chatIntent.putExtra("groupid", mSession.getFromId());
				} else { // 单聊则需传入当前聊天的用户
					TCUser user = new TCUser();
					user.setUserID(mSession.getFromId());
					user.setName(mSession.getSessionName());
					user.setHead(mSession.getSessionHead());
					chatIntent.putExtra("user", user);
				}
				startActivity(chatIntent);
			}
			break;

		// case R.id.chat_box_expra_btn_file:
		// hideExpra();
		// showFileChooser();
		// break;

		default:
			break;
		}
	}

	private void showFileChooser() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("*/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);

		try {
			startActivityForResult(
					Intent.createChooser(intent,
							mContext.getString(R.string.select_file)),
					REQUEST_FILE_SELECT);
		} catch (ActivityNotFoundException ex) {
			showToast(mContext.getString(R.string.install_file_manager));
		}
	}

	public void showSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) mContentEdit.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 添加表情滑动控件
	 * 
	 * @param i
	 *            添加的位置
	 */
	private void addView(final int i) {
		View view = LayoutInflater.from(mContext).inflate(
				R.layout.emotion_gridview, null);
		GridView gridView = (GridView) view.findViewById(R.id.emoji_grid);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position < mTotalEmotionList.get(i).size() - 1) {
					ImageView imageView = (ImageView) view
							.findViewById(R.id.emotion);
					if (imageView != null) {
						Drawable drawable = imageView.getDrawable();
						if (drawable instanceof BitmapDrawable) {
							Bitmap bitmap = ((BitmapDrawable) drawable)
									.getBitmap();
							String name = mTotalEmotionList.get(i)
									.get(position);

							Drawable mDrawable = new BitmapDrawable(
									getResources(), bitmap);
							int width = getResources().getDimensionPixelSize(
									R.dimen.pl_emoji);
							int height = width;
							mDrawable.setBounds(0, 0, width > 0 ? width : 0,
									height > 0 ? height : 0);
							ImageSpan span = new ImageSpan(mDrawable);

							SpannableString spannableString = new SpannableString(
									"[" + name + "]");
							// 类似于集合中的(start, end)，不包括起始值也不包括结束值。
							// 同理，Spannable.SPAN_INCLUSIVE_EXCLUSIVE类似于
							// [start，end)
							spannableString.setSpan(span, 0,
									spannableString.length(),
									Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
							Editable dEditable = mContentEdit.getEditableText();
							int index = mContentEdit.getSelectionStart();
							dEditable.insert(index, spannableString);
						}
					}
				} else {
					int index = mContentEdit.getSelectionStart();

					String text = mContentEdit.getText().toString();
					if (index > 0) {
						String text2 = text.substring(index - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = index;
							mContentEdit.getText().delete(start, end);
							return;
						}
						mContentEdit.getText().delete(index - 1, index);
					}
				}
			}

		});
		gridView.setAdapter(new EmojiAdapter(mContext,
				mTotalEmotionList.get(i), mWidth));
		mViewList.add(view);
	}

	/**
	 * 显示表情处于第几页标志
	 * 
	 * @param size
	 */
	private void showCircle(int size) {
		mLayoutCircle.removeAllViews();

		for (int i = 0; i < size; i++) {
			ImageView img = new ImageView(mContext);
			img.setLayoutParams(new LinearLayout.LayoutParams(FeatureFunction
					.dip2px(mContext, 5), FeatureFunction.dip2px(mContext, 5)));
			LinearLayout layout = new LinearLayout(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			int margin = FeatureFunction.dip2px(mContext, 5);
			params.setMargins(margin, 0, margin, 0);
			layout.setLayoutParams(params);
			layout.addView(img);
			// img.setLayoutParams()
			if (mPageIndxe == i) {
				img.setImageResource(R.drawable.circle_d);
			} else {
				img.setImageResource(R.drawable.circle_n);
			}
			mLayoutCircle.addView(layout);
		}
	}

	/**
	 * 获取表情列表
	 * 
	 * @return
	 */
	private List<List<String>> getEmojiList() {
		List<String> emojiList = new ArrayList<String>();
		String baseName = "emoji_";
		for (int i = 85; i <= 88; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 340; i <= 363; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 94; i <= 101; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 115; i <= 117; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 364; i <= 373; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 12; i <= 17; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 0; i <= 11; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 18; i <= 84; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 89; i <= 93; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 101; i <= 114; i++) {
			emojiList.add(baseName + i);
		}

		for (int i = 114; i <= 339; i++) {
			emojiList.add(baseName + i);
		}

		List<List<String>> totalList = new ArrayList<List<String>>();
		int page = emojiList.size() % 20 == 0 ? emojiList.size() / 20
				: emojiList.size() / 20 + 1;
		for (int i = 0; i < page; i++) {
			int startIndex = i * 20;
			List<String> singleList = new ArrayList<String>();
			if (singleList != null) {
				singleList.clear();
			}
			int endIndex = 0;
			if (i < page - 1) {
				endIndex = startIndex + 20;
			} else if (i == page - 1) {
				endIndex = emojiList.size() - 1;
			}

			// 当前页对应的列表最后加入删除按钮
			singleList.addAll(emojiList.subList(startIndex, endIndex));
			singleList.add("delete_emotion_btn");
			totalList.add(singleList);

		}

		return totalList;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		// 滑动时更新页面标识View的图片
		mPageIndxe = position;
		showCircle(mViewList.size());
	}

	/**
	 * 重发对话框
	 * 
	 * @param messageInfo
	 *            待重发的消息体
	 */
	private void showResendDialog(final TCMessage messageInfo) {
		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button promptBtn = (Button) layout.findViewById(R.id.camera);
		promptBtn.setText(mContext.getString(R.string.sure_to_resend_message));
		promptBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		promptBtn.setTextColor(mContext.getResources().getColor(
				R.color.content_gray_color));
		promptBtn.setEnabled(false);

		final Button deleteBtn = (Button) layout.findViewById(R.id.gallery);
		deleteBtn.setText(mContext.getString(R.string.send));
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);
		cancelBtn.setText(mContext.getString(R.string.cancel));

		deleteBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				btnResendAction(messageInfo);
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		// set a large value put it in bottom
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(true);

		dlg.setContentView(layout);
		dlg.show();
	}
}
