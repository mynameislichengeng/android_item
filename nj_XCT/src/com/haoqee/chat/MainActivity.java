package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.haoqee.chat.Fragment.ChatFragment;
import com.haoqee.chat.Fragment.FriendsFragment;
import com.haoqee.chat.Fragment.GroupFragment;
import com.haoqee.chat.Fragment.ProfileFragment;
import com.haoqee.chat.entity.NotifyType;
import com.haoqee.chat.entity.Server;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.FileDownloadListener;
import com.haoqee.chatsdk.Interface.LoginListenser;
import com.haoqee.chatsdk.Interface.TCMessageListener;
import com.haoqee.chatsdk.Interface.TCNotifyListener;
import com.haoqee.chatsdk.Interface.TCNotifyMessageListener;
import com.haoqee.chatsdk.entity.ChatType;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;
import com.haoqee.chatsdk.entity.TCMessage;
import com.haoqee.chatsdk.entity.TCMessageType;
import com.haoqee.chatsdk.entity.TCNotifyType;
import com.haoqee.chatsdk.entity.TCNotifyVo;
import com.haoqee.chatsdk.entity.TCSession;

/**
 * 程序主页面
 *
 */
public class MainActivity extends FragmentActivity {

	private LinearLayout mTabView;

	private Fragment mContentFragment;
	private Context mContext;

	/** 监听网络状态通知 */
	public final static String ACTION_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
	/** 被挤下线或者是未登录时显示TOAST通知 */
	public static final String ACTION_SHOW_TOAST = "com.xizue.thinkchat.intent.action.ACTION_SHOW_TOAST";
	/** 注销 */
	public static final String ACTION_LOGIN_OUT = "com.xizue.thinkchat.intent.action.ACTION_LOGIN_OUT";
	/** 登录成功执行操作通知 */
	public final static String LOGIN_SUCCESS_ACTION = "com.xizue.thinkchat.action.LOGIN_SUCCESS_ACTION";
	/** 退出通知 */
	public final static String SYSTEM_EXIT = "com.xizue.thinkchat.action.SYSTEM_EXIT";
	/** 被踢出临时会话或群主删除临时会话通知 */
	public final static String ACTION_KICK_OR_DELETE_TEMP_CHAT = "com.xizue.thinkchat.action.ACTION_KICK_OR_DELETE_TEMP_CHAT";

	/** 更新未读数通知 */
	public final static String UPDATE_SESSION_UNREAD_COUNT = "com.xizue.thinkchat.action.UPDATE_SESSION_UNREAD_COUNT";

	/** 发送文件通知 */
	public final static String ACTION_SEND_FILE_MESSAGE = "com.xizue.thinkchat.action.ACTION_SEND_FILE_MESSAGE";
	/** 下载文件通知 */
	public final static String ACTION_DOWNLOAD_FILE_MESSAGE = "com.xizue.thinkchat.action.ACTION_DOWNLOAD_FILE_MESSAGE";
	private boolean mIsRegisterReceiver = false;
	public final static int SHOW_PROGRESS_DIALOG = 11112;
	public final static int HIDE_PROGRESS_DIALOG = 11113;
	public final static int UPDATE_LIST_DATA = 11114;
	public final static int MSG_NETWORK_ERROR = 11306;
	public final static int MSG_TICE_OUT_EXCEPTION = 11307;
	public final static int MSG_LOAD_ERROR = 11818;
	public final static int LIST_LOAD_FIRST = 501;
	public final static int LIST_LOAD_REFERSH = 502;
	public final static int LIST_LOAD_MORE = 503;
	public final static int SHOW_LOADINGMORE_INDECATOR = 11105;
	public final static int HIDE_LOADINGMORE_INDECATOR = 11106;
	public final static int LOGIN_REQUEST = 29312;
	public final static int SHOW_SCROLLREFRESH = 11117;
	public final static int HIDE_SCROLLREFRESH = 11118;
	private int mSelectedIndex = 0; // 当前所选中的菜单
	private List<TabIndicator> mTabIndicatorList = new ArrayList<TabIndicator>();
	/** 通知栏中通知的ID */
	public static final int NOTION_ID = 1000000023;

	/** OPENFIRE服务器IP */
//	public static final String HOST = "42.121.110.141";
	public static final String HOST = "123.27.12.78";
	/** OPENFIRE服务器端口号 */
	public static final String PORT = "5222";
	/** OPENFIRE服务器域 */
	private static final String RESOURSE_NAME = "iz25uqhn5q4z";
	// private static final String RESOURSE_NAME = "localhost.localdomain";
	/** 业务服务器地址 */
	// public static final String LOGISTIC_SERVER =
	// "http://42.121.110.141:8125/tc_sdk/index.php";
	public static final String LOGISTIC_SERVER = "http://123.27.12.78/tc_sdk/index.php";

	// /** OPENFIRE服务器IP */
	// private static final String HOST = "192.168.1.126";
	// /** OPENFIRE服务器端口号 */
	// private static final String PORT = "5222";
	// /** OPENFIRE服务器域 */
	// private static final String RESOURSE_NAME = "nye8o4n0kbpk4g";
	// /** 业务服务器地址 */
	// private static final String LOGISTIC_SERVER =
	// "http://192.168.1.126/server_sdk/index.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabactivity);
		mContext = this;
		registerNetWorkMonitor();
		initComponent();

		if (Common.isLogin(mContext)) {
			init();
		} else {
			Intent intent = new Intent(mContext, LoginActivity.class);
			startActivityForResult(intent, LOGIN_REQUEST);
		}
	}

	private void init() {
		// 设置业务服务器地址

		String logisticServer = LOGISTIC_SERVER;

		Server server = Common.getCurrentServer(mContext);
		if (server != null && !TextUtils.isEmpty(server.mSdkServer)) {
			logisticServer = server.mSdkServer;
		}
		TCChatManager.getInstance().setLogitiscServer(logisticServer);
		// 初始化菜单栏
		initTab();
		// 登录成功，发通知更新XMPP
		mContext.sendBroadcast(new Intent(LOGIN_SUCCESS_ACTION));
	}

	private LoginListenser mLoginListenser = new LoginListenser() {

		@Override
		public void onSuccess() {
			// 登录XMPP成功之后进入该回调
			// 注册消息监听事件
			TCChatManager.getInstance().setNotifyMessageListener(
					mTCNotifyMessageListener);
			TCChatManager.getInstance().setNotifyListener(mNotifyListener);
		}

		@Override
		public void onFailed(String error) {
			// 登录XMPP失败之后进入该回调
		}

		@Override
		public void onConflict() {
			// 账号被挤下线之后进入该回调
			Intent toastIntent = new Intent(MainActivity.ACTION_SHOW_TOAST);
			toastIntent.putExtra("toast_msg",
					mContext.getString(R.string.openfire_login_prompt));
			mContext.sendBroadcast(toastIntent);
			Common.saveLoginResult(mContext, null);
			Common.setUid("");
			Common.setToken("");
			TCChatManager.getInstance().logoutXmpp();
			mContext.sendBroadcast(new Intent(BaseActivity.FINISH_ACTION));
			mContext.sendBroadcast(new Intent(MainActivity.ACTION_LOGIN_OUT));
		}
	};

	private TCNotifyMessageListener mTCNotifyMessageListener = new TCNotifyMessageListener() {

		@Override
		public void doComplete(TCMessage message) {

			Intent infoIntent = new Intent(
					ChatMainActivity.ACTION_NOTIFY_CHAT_MESSAGE);
			infoIntent.putExtra(ChatMainActivity.EXTRAS_MESSAGE, message);
			mContext.sendBroadcast(infoIntent);

			mContext.sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));

			// 收到消息之后进入该回调

			String msg = null;

			switch (message.getMessageType()) {
			case TCMessageType.PICTURE:
				msg = " <" + mContext.getString(R.string.get_picture) + " > ";
				break;
			case TCMessageType.TEXT:
				msg = message.getTextMessageBody().getContent();
				break;
			case TCMessageType.VOICE:
				msg = " <" + mContext.getString(R.string.get_voice) + " > ";
				break;
			case TCMessageType.MAP:
				msg = " <" + mContext.getString(R.string.get_location) + " > ";
				break;

			default:
				break;
			}

			// Notification
			Notification notification = new Notification();
			notification.icon = R.drawable.ic_launcher; // 设置通知的图标
			long currentTime = System.currentTimeMillis();
			if (currentTime - Common.getNotificationTime(mContext) > Common.NOTIFICATION_INTERVAL) {
				if (Common.getOpenSound(mContext)) {
					notification.defaults |= Notification.DEFAULT_SOUND;
				}
				Common.saveNotificationTime(mContext, currentTime);
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}

			notification.defaults |= Notification.DEFAULT_LIGHTS;
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			// 音频将被重复直到通知取消或通知窗口打开。
			// notification.flags |= Notification.FLAG_INSISTENT;
			notification.flags |= Notification.FLAG_SHOW_LIGHTS;
			notification.when = currentTime;

			TCSession session = null;
			if (message.getChatType() == ChatType.SINGLE_CHAT) {
				session = TCChatManager.getInstance().getSessionByID(
						message.getFromId(), ChatType.SINGLE_CHAT);
			} else {
				session = TCChatManager.getInstance().getSessionByID(
						message.getToId(), message.getChatType());
			}

			try {
				ActivityManager am = (ActivityManager) mContext
						.getSystemService(Context.ACTIVITY_SERVICE);
				ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
				if (cn.getClassName().equals(
						cn.getPackageName() + ".ChatMainActivity")
						&& Common.getChatType(mContext) == message
								.getChatType()) {
					if (FeatureFunction.isAppOnForeground(mContext)) {
						return;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			mContext.sendBroadcast(new Intent(
					MainActivity.UPDATE_SESSION_UNREAD_COUNT));

			if (!Common.getAcceptMsgAuth(mContext)) {
				return;
			}

			if (message.getChatType() != ChatType.SINGLE_CHAT) {
				TCGroup group = TCChatManager.getInstance().queryGroupByID(
						message.getToId(), message.getChatType());
				if (group != null && group.getGroupGetMsg() == 0) {
					return;
				}
			}

			String notifyMsg = "";
			String id = "";
			if (message.getChatType() == ChatType.SINGLE_CHAT) {
				notifyMsg = message.getFromName();
				id = message.getFromId();
			} else {
				notifyMsg = message.getToName();
				id = message.getToId();
			}

			Intent intent = new Intent(mContext, ChatMainActivity.class);
			intent.putExtra("session", session);

			PendingIntent contentIntent = PendingIntent.getActivity(mContext,
					id.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(mContext, notifyMsg, msg,
					contentIntent);
			NotificationManager manager = (NotificationManager) mContext
					.getSystemService(Context.NOTIFICATION_SERVICE);

			manager.notify(id.hashCode(), notification);
		}
	};

	private TCNotifyListener mNotifyListener = new TCNotifyListener() {

		@Override
		public void receive(TCNotifyVo notify) {
			// 收到通知后进入该回调
			sendNotify(notify);
		}
	};

	/**
	 * 发送通知到系统通知栏
	 * 
	 * @param notifyVo
	 *            通知对象
	 */
	private void sendNotify(TCNotifyVo notifyVo) {

		sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));

		String msg = "";
		msg = notifyVo.getContent();
		switch (notifyVo.getType()) {
		case NotifyType.SYSTEM_MSG:
			msg = mContext.getString(R.string.system_info);
			break;

		case NotifyType.APPLY_FRIEND:
			msg = notifyVo.getUser().getName()
					+ mContext.getString(R.string.apply_add_friend_with_you);
			break;

		case NotifyType.AGREE_ADD_FRIEND:
			msg = notifyVo.getUser().getName()
					+ mContext.getString(R.string.agree_add_friend_with_you);
			Intent addFriendIntent = new Intent(
					UserInfoActivity.ACTION_AGREE_FRIEND);
			addFriendIntent.putExtra("uid", notifyVo.getUser().getUserID());
			sendBroadcast(addFriendIntent);
			mContext.sendBroadcast(new Intent(
					FriendsFragment.REFRESH_FRIEND_ACTION));
			break;

		case NotifyType.REFUSE_ADD_FRIEND:
			msg = notifyVo.getUser().getName()
					+ mContext.getString(R.string.refuse_add_friend_with_you);
			break;

		case NotifyType.DELETE_FRIEND:
			msg = notifyVo.getUser().getName()
					+ mContext.getString(R.string.unbind_friendship);
			mContext.sendBroadcast(new Intent(
					FriendsFragment.REFRESH_FRIEND_ACTION));
			break;

		case TCNotifyType.APPLY_ADD_GROUP:
			msg = mContext.getString(R.string.apply_add_group);
			break;

		case TCNotifyType.AGREE_ADD_GROUP:
			msg = mContext.getString(R.string.agree_apply_add_group);
			Intent agreeApplyIntent = new Intent(
					GroupDetailActivity.ACTION_AGREE_ADD_GROUP);
			agreeApplyIntent.putExtra("id", notifyVo.getRoomID());
			mContext.sendBroadcast(agreeApplyIntent);
			mContext.sendBroadcast(new Intent(
					GroupFragment.REFRESH_CONTACT_GROUP_ACTION));
			break;

		case TCNotifyType.DISAGREE_ADD_GROUP:
			msg = mContext.getString(R.string.disagree_apply_add_group);
			break;

		case TCNotifyType.INVITE_ADD_GROUP:
			msg = mContext.getString(R.string.invite_add_group);
			break;

		case TCNotifyType.AGREE_INVITE_ADD_GROUP:
			msg = mContext.getString(R.string.agree_invite_add_group);
			Intent agreeInviteIntent = new Intent(
					GroupDetailActivity.ACTION_AGREE_ADD_GROUP);
			agreeInviteIntent.putExtra("id", notifyVo.getRoomID());
			mContext.sendBroadcast(agreeInviteIntent);
			mContext.sendBroadcast(new Intent(
					GroupFragment.REFRESH_CONTACT_GROUP_ACTION));
			break;

		case TCNotifyType.DISAGREE_INVITE_ADD_GROUP:
			msg = mContext.getString(R.string.disagree_invite_add_group);
			break;

		case TCNotifyType.GROUP_KICK_OUT: // 收到被踢出群的通知，清空数据表中和群相关的会话列表和消息列表，
											// 并发通知更新会话列表页面和未读数

			mContext.sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					MainActivity.UPDATE_SESSION_UNREAD_COUNT));
			msg = mContext.getString(R.string.you_have_been_kick_out_group);

			// 刷新群列表数据
			Intent kickIntent = new Intent(GroupFragment.ACTION_KICK_GROUP);
			kickIntent.putExtra("id", notifyVo.getRoomID());
			mContext.sendBroadcast(kickIntent);
			break;

		case TCNotifyType.GROUP_KICK_OUT_OTHER: // 收到群里其他成员被踢出之后的通知
			Intent kickOtherIntent = new Intent(
					GroupDetailActivity.ACTION_REFRESH_GROUP_DETAIL);
			kickOtherIntent.putExtra("groupid", notifyVo.getRoomID());
			mContext.sendBroadcast(kickOtherIntent);
			break;

		case TCNotifyType.EXIT_GROUP:
			Intent exitIntent = new Intent(
					GroupDetailActivity.ACTION_REFRESH_GROUP_DETAIL);
			exitIntent.putExtra("groupid", notifyVo.getRoomID());
			mContext.sendBroadcast(exitIntent);

			break;

		case TCNotifyType.DELETE_GROUP: // 收到群主删除群通知，清空数据表中和群相关的会话列表和消息列表，
										// 并发通知更新会话列表页面和未读数

			mContext.sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					MainActivity.UPDATE_SESSION_UNREAD_COUNT));

			msg = mContext.getString(R.string.group_has_been_deleted_by_admin);

			// 刷新群列表数据
			Intent deleteIntent = new Intent(GroupFragment.ACTION_DELETE_GROUP);
			deleteIntent.putExtra("id", notifyVo.getRoomID());
			mContext.sendBroadcast(deleteIntent);
			break;

		case TCNotifyType.EXIT_TEMP_CHAT:
			Intent exitChatIntent = new Intent(
					ChatDetailActivity.ACTION_REFRESH_CHAT_DETAIL_PAGE);
			exitChatIntent.putExtra("groupid", notifyVo.getRoomID());
			mContext.sendBroadcast(exitChatIntent);
			break;

		case TCNotifyType.TEMP_CHAT_KICK_OUT:

			mContext.sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					MainActivity.UPDATE_SESSION_UNREAD_COUNT));

			Intent kickTempIntent = new Intent(ACTION_KICK_OR_DELETE_TEMP_CHAT);
			kickTempIntent.putExtra("id", notifyVo.getRoomID());
			kickTempIntent.putExtra("excuteType", 1);
			mContext.sendBroadcast(kickTempIntent);
			break;

		case TCNotifyType.KICK_OUT_TEMP_CHAT:
			Intent kickChatOtherIntent = new Intent(
					ChatDetailActivity.ACTION_REFRESH_CHAT_DETAIL_PAGE);
			kickChatOtherIntent.putExtra("groupid", notifyVo.getRoomID());
			mContext.sendBroadcast(kickChatOtherIntent);
			break;

		case TCNotifyType.DELETE_TEMP_CHAT:

			mContext.sendBroadcast(new Intent(ChatFragment.UPDATE_COUNT_ACTION));
			mContext.sendBroadcast(new Intent(
					MainActivity.UPDATE_SESSION_UNREAD_COUNT));

			Intent deleteTempIntent = new Intent(
					ACTION_KICK_OR_DELETE_TEMP_CHAT);
			deleteTempIntent.putExtra("id", notifyVo.getRoomID());
			deleteTempIntent.putExtra("excuteType", 0);
			mContext.sendBroadcast(deleteTempIntent);
			break;

		case TCNotifyType.MODIFY_TEMP_CHAT_NAME:
			Intent intent = new Intent(
					ChatMainActivity.ACTION_UPDATE_TEMP_CHAT_NAME);
			intent.putExtra("id", notifyVo.getRoomID());
			intent.putExtra("chatType", ChatType.TEMP_CHAT);
			intent.putExtra("name", notifyVo.getRoom().getGroupName());
			mContext.sendBroadcast(intent);
			break;

		default:
			break;
		}

		mContext.sendBroadcast(new Intent(UPDATE_SESSION_UNREAD_COUNT));

		mContext.sendBroadcast(new Intent(
				NotifyActivity.ACTION_NOTIFY_SYSTEM_MESSAGE));
		try {
			ActivityManager am = (ActivityManager) mContext
					.getSystemService(Context.ACTIVITY_SERVICE);
			ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
			if (cn.getClassName().equals(
					cn.getPackageName() + ".NotifyActivity")) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Notification
		Notification notification = new Notification();
		notification.icon = R.drawable.ic_launcher; // 设置通知的图标
		long currentTime = System.currentTimeMillis();
		if (currentTime - Common.getNotificationTime(mContext) > Common.NOTIFICATION_INTERVAL) {
			if (Common.getOpenSound(mContext)) {
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			Common.saveNotificationTime(mContext, currentTime);
			notification.defaults |= Notification.DEFAULT_VIBRATE;
		}

		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		// 音频将被重复直到通知取消或通知窗口打开。
		notification.flags |= Notification.FLAG_SHOW_LIGHTS;
		notification.when = currentTime;

		Intent intent = new Intent(mContext, NotifyActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(mContext,
				NOTION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		notification.setLatestEventInfo(mContext,
				mContext.getString(R.string.has_new_notification), msg,
				contentIntent);
		NotificationManager manager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(NOTION_ID, notification);
	}

	private void initComponent() {
		mTabView = (LinearLayout) this.findViewById(R.id.tabs);

		mTabIndicatorList.add(new TabIndicator(mContext
				.getString(R.string.chat_tab), R.drawable.selector_tab_one,
				ChatFragment.class.getName()));
		mTabIndicatorList.add(new TabIndicator(mContext
				.getString(R.string.friend_tab), R.drawable.selector_tab_two,
				FriendsFragment.class.getName()));
		mTabIndicatorList.add(new TabIndicator(mContext
				.getString(R.string.group_tab), R.drawable.selector_tab_three,
				GroupFragment.class.getName()));
		mTabIndicatorList.add(new TabIndicator(mContext
				.getString(R.string.profile_tab), R.drawable.selector_tab_four,
				ProfileFragment.class.getName()));

	}

	/**
	 * 初始化导航栏
	 */
	private void initTab() {

		// 如果TabView中子View的个数不为0，则清空
		if (mTabView.getChildCount() != 0) {
			mTabView.removeAllViews();
		}

		mSelectedIndex = 0;

		/**
		 * 释放页面中存在的Fragment
		 */
//		List<Fragment> fragments = getSupportFragmentManager().getFragments();
//		if (fragments != null && fragments.size() != 0) {
//			for (int i = 0; i < fragments.size(); i++) {
//				getSupportFragmentManager().beginTransaction()
//						.remove(fragments.get(i)).commitAllowingStateLoss();
//			}
//		}

		/**
		 * 添加导航栏上的图标
		 */
		for (int i = 0; i < 4; i++) {
			View view = null;
			try {
				view = LayoutInflater.from(mContext).inflate(
						R.layout.tab_indicator, null);
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (view != null) {
				TextView titleView = (TextView) view.findViewById(R.id.title);
				ImageView iconView = (ImageView) view.findViewById(R.id.icon);

				titleView.setText(mTabIndicatorList.get(i).getTitle());
				iconView.setImageResource(mTabIndicatorList.get(i).getIcon());

				final int index = i;
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (mSelectedIndex == index) {
							return;
						}

						setCurrentTab(index);
					}
				});

				final LinearLayout.LayoutParams lp = new LayoutParams(0,
						LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
				lp.setMargins(0, 0, 0, 0);
				view.setLayoutParams(lp);

				if (i == 0) {
					view.setSelected(true);
				}

				mTabView.addView(view);
			}
		}

	}

	private void setCount(int i, int count) {

		if (mTabView.getChildCount() >= i) {
			TextView tv = (TextView) mTabView.getChildAt(i).findViewById(
					R.id.count);

			String countText = String.valueOf(count);
			if (count > 99) {
				countText = "99+";
			}
			tv.setText(countText);
			if (count > 0) {
				tv.setVisibility(View.VISIBLE);
			} else {
				tv.setVisibility(View.GONE);
			}

			mTabView.invalidate();
		}
	}

	/**
	 * 切换页面
	 * 
	 * @param i
	 *            当前需要切换页面所对应的父控件的位置
	 */
	private void setCurrentTab(int i) {

		View view = mTabView.getChildAt(mSelectedIndex);
		if (view != null) {
			view.setSelected(false);
		}

		mSelectedIndex = i;

		view = mTabView.getChildAt(i);
		if (view != null) {
			view.setSelected(true);
		}

		onSwitchContentFrom(mTabIndicatorList.get(i).getFragmentName(), null);
	}

	/**
	 * 切换不同的页面展示。
	 * <P>
	 * 在页面被切换后，碎片的生命周期将只执行{@link Fragment#onPause()}，
	 * {@link Fragment#onAttach(android.app.Activity)}，
	 * {@link Fragment#onResume()}三个方法
	 * 
	 * @param newFragmentName
	 *            将要被切换到的新页面
	 * @param bundle
	 *            创建新页面时，指定初始化的相关数据
	 */
	private void onSwitchContentFrom(String newFragmentName, Bundle bundle) {
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		Fragment mFragment = fragmentManager.findFragmentByTag(newFragmentName);
		if (null == mFragment) {
			mFragment = Fragment.instantiate(getApplicationContext(),
					newFragmentName, bundle);
		}

		// 若当前显示的视图与将要被显示的视图冲突或不符，则以新的要显示的视图为准
		if (mContentFragment != mFragment) {
			FragmentTransaction mTransaction = fragmentManager
					.beginTransaction();
			if (null == mContentFragment) {
				mTransaction.add(R.id.tabcontent, mFragment, newFragmentName);
			} else {
				if (!mFragment.isAdded()) {
					mTransaction.hide(mContentFragment).add(R.id.tabcontent,
							mFragment, newFragmentName);
					mContentFragment.onPause();
				} else {
					mTransaction.hide(mContentFragment).show(mFragment);
					mContentFragment.onPause();
					mFragment.onAttach(this);
					mFragment.onResume();
				}
			}
			mTransaction.commitAllowingStateLoss();
			mContentFragment = mFragment;
		}
	}

	/**
	 * 注册通知
	 */
	private void registerNetWorkMonitor() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_NETWORK_CHANGE);
		filter.addAction(ACTION_LOGIN_OUT);
		filter.addAction(LOGIN_SUCCESS_ACTION);
		filter.addAction(SYSTEM_EXIT);
		filter.addAction(ACTION_SHOW_TOAST);
		filter.addAction(UPDATE_SESSION_UNREAD_COUNT);
		filter.addAction(ACTION_SEND_FILE_MESSAGE);
		filter.addAction(ACTION_DOWNLOAD_FILE_MESSAGE);
		registerReceiver(mReceiver, filter);
		mIsRegisterReceiver = true;
	}

	/**
	 * 处理通知类
	 */
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ACTION_NETWORK_CHANGE)) {
				boolean isNetConnect = false;
				ConnectivityManager connectivityManager = (ConnectivityManager) context
						.getSystemService(Context.CONNECTIVITY_SERVICE);

				NetworkInfo activeNetInfo = connectivityManager
						.getActiveNetworkInfo();
				if (activeNetInfo != null) {
					if (activeNetInfo.isConnected()) {
						isNetConnect = true;
						Toast.makeText(
								context,
								mContext.getResources().getString(
										R.string.message_net_connect)
										+ activeNetInfo.getTypeName(),
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(
								context,
								mContext.getResources().getString(
										R.string.network_error)
										+ " " + activeNetInfo.getTypeName(),
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(
							context,
							mContext.getResources().getString(
									R.string.network_error), Toast.LENGTH_SHORT)
							.show();
				}
				Common.setNetWorkState(isNetConnect);
			} else if (ACTION_LOGIN_OUT.equals(action)) {

				Intent loginIntent = new Intent(mContext, LoginActivity.class);
				startActivityForResult(loginIntent, LOGIN_REQUEST);

			} else if (LOGIN_SUCCESS_ACTION.equals(action)) {
				// 登录XMPP

				String host = HOST;
				String port = PORT;
				Server server = Common.getCurrentServer(mContext);
				if (server != null && !TextUtils.isEmpty(server.mOPHost)) {
					String[] strs = server.mOPHost.split(":");

					host = strs[0];
					port = strs[1];
				}

				String loginStr = TCChatManager.getInstance().loginXmpp(host,
						port, RESOURSE_NAME,
						Common.getLoginResult(mContext).uid,
						Common.getLoginResult(mContext).password,
						mLoginListenser);
				// 根据返回值不为空判断是否存在传入的参数有误
				if (!TextUtils.isEmpty(loginStr)) {
					Toast.makeText(mContext, loginStr, Toast.LENGTH_SHORT)
							.show();
					;
				}
				// 初始化会话页面
				onSwitchContentFrom(ChatFragment.class.getName(), null);
				// 显示总的未读数
				updateSessionCount();
			} else if (SYSTEM_EXIT.equals(action)) {
				MainActivity.this.finish();
				System.exit(0);
			} else if (ACTION_SHOW_TOAST.equals(action)) {
				String str = intent.getStringExtra("toast_msg");
				Toast.makeText(mContext, str, Toast.LENGTH_LONG).show();
			} else if (UPDATE_SESSION_UNREAD_COUNT.equals(action)) {
				updateSessionCount();
			} else if (ACTION_SEND_FILE_MESSAGE.equals(action)) {
				TCMessage message = (TCMessage) intent
						.getSerializableExtra("message");

				sendMessage(message);
			} else if (ACTION_DOWNLOAD_FILE_MESSAGE.equals(action)) {
				TCMessage message = (TCMessage) intent
						.getSerializableExtra("message");

				downloadFile(message);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mIsRegisterReceiver) {
			unregisterReceiver(mReceiver);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		/*
		 * if(intent == null){ return ; }
		 * 
		 * boolean isChatNotify = intent.getBooleanExtra("chatnotify", false);
		 * boolean isNotify = intent.getBooleanExtra("notify", false);
		 * if(isChatNotify){ Intent chatIntent = new Intent(mContext,
		 * ChatMainActivity.class); chatIntent.putExtra("data",
		 * intent.getSerializableExtra("data")); chatIntent.putExtra("type",
		 * intent.getIntExtra("type", 100)); startActivity(chatIntent); }else
		 * if(isNotify){ Intent chatIntent = new Intent(mContext,
		 * NotifyActivity.class); startActivity(chatIntent); }
		 */

		super.onNewIntent(intent);
	}

	private void updateSessionCount() {
		int count = TCChatManager.getInstance().getUnreadCount();
		int notifyCount = TCChatManager.getInstance().queryNotifyUnreadCount();
		count += notifyCount;
		setCount(0, count);
	}

	/**
	 * 导航栏单个菜单显示对象内容
	 *
	 */
	public class TabIndicator {
		private String mTitle; // 导航栏显示标题
		private int mIcon; // 导航栏显示图标
		private String mFragmentName; // 导航栏对应的Fragment名称

		public TabIndicator() {

		}

		public TabIndicator(String title, int icon, String name) {
			this.mTitle = title;
			this.mIcon = icon;
			this.mFragmentName = name;
		}

		public String getTitle() {
			return mTitle;
		}

		public int getIcon() {
			return mIcon;
		}

		public String getFragmentName() {
			return mFragmentName;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		switch (requestCode) {
		case LOGIN_REQUEST:
			if (resultCode == RESULT_OK) {
				init();
			} else {
				sendBroadcast(new Intent(SYSTEM_EXIT));
			}
			break;

		default:
			break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private void sendMessage(final TCMessage msg) {
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
				Intent intent = new Intent(
						ChatMainActivity.ACTION_SEND_FILE_FAILED);
				intent.putExtra("message", tcMessage);
				intent.putExtra("error", error);
				mContext.sendBroadcast(intent);
			}

			@Override
			public void doComplete(TCMessage tcMessage) {
				Intent intent = new Intent(
						ChatMainActivity.ACTION_SEND_FILE_COMPLETE);
				intent.putExtra("message", tcMessage);
				mContext.sendBroadcast(intent);
			}

			@Override
			public void onProgress(int progress) {
				msg.getFileMessageBody().setUploadProgress(progress);
				Intent intent = new Intent(
						ChatMainActivity.ACTION_UPDATE_FILE_PROGRESS);
				intent.putExtra("message", msg);
				mContext.sendBroadcast(intent);
			}
		});
	}

	private void downloadFile(final TCMessage message) {

		String path = Environment.getExternalStorageDirectory()
				+ FeatureFunction.PUB_TEMP_DIRECTORY
				+ FileDetailActivity.RECEIVE_FILE + "/"
				+ message.getFileMessageBody().getFileName();
		TCChatManager.getInstance().downloadFile(
				message.getFileMessageBody().getFileUrl(), path,
				new FileDownloadListener() {

					@Override
					public void onError(TCError error) {
						Intent intent = new Intent(
								FileDetailActivity.ACTION_DOWNLOAD_FILE_FAILED);
						intent.putExtra("message", message);
						mContext.sendBroadcast(intent);
					}

					@Override
					public void downloadProgress(int progress) {
						Intent intent = new Intent(
								FileDetailActivity.ACTION_DOWNLOAD_FILE_PROGRESS);
						intent.putExtra("message", message);
						intent.putExtra("progress", progress);
						mContext.sendBroadcast(intent);
					}
				});
	}
}
