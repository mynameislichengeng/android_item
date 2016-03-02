package com.haoqee.chatsdk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import xmpp.push.sns.ConnectionConfiguration;
import xmpp.push.sns.ConnectionConfiguration.SecurityMode;
import xmpp.push.sns.ConnectionListener;
import xmpp.push.sns.XMPPConnection;
import xmpp.push.sns.XMPPException;
import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.haoqee.chatsdk.Interface.LoginListenser;
import com.haoqee.chatsdk.receiver.PhoneStateChangeListener;
import com.haoqee.chatsdk.service.type.XmppType;
import com.haoqee.chatsdk.service.type.XmppTypeManager;

/**
 * 
 * 功能： 连接IM服务器.. <br />
 * 地点：好奇科技<br />
 * 
 * @since
 */
public class XmppManager {
	public static final String LOGTAG = "XmppManager";
	/** 连接状态 */
	public static final int XMPP_CONNECT_STATE = 0x1;
	/** 登录状态 */
	public static final int XMPP_LOGINING_STATE = 0x2;
	/** 登录成功状态 */
	public static final int XMPP_LOGINED_STATE = 0x3;
	/** 认证失败 */
	public static final int XMPP_AUTH_ERR = 0xc;
	
	//private static final String HOST = "101.231.194.49";
	//private static final String HOST = "guirenhui.cn";
	private String HOST = "223.166.157.177";
	private int PORT = 5222;
	//public static final String XMPP_RESOURCE_NAME = "win-9u09bkaji6c";
	//public static final String XMPP_RESOURCE_NAME = "127.0.0.1";
	public String XMPP_RESOURCE_NAME = "pc201407251922";
	//public static final String XMPP_RESOURCE_NAME = "6237fba915bc4db";
	
	private String username;
	private String password;
	
	private boolean running = false;
	private Future<?> futureTask;
	private XMPPConnection connection;
	private List<Runnable> taskList;
	
	private SNSMessageManager snsMessageLisener;
	
	private TaskSubmitter taskSubmitter;
	private TaskTracker taskTracker;
	private ConnectionListener connectionListener;
	
	private int connectState = 0x0;
	private LoginListenser mListenser;		//登录监听状态回调
	
	private TelephonyManager telephonyManager;   // 用于观察手机改变的状态
    //private BroadcastReceiver connectivityReceiver; // 网络状态监听
    private PhoneStateListener phoneStateListener;  
    /** XMPP 状态存放在应用共享区 */
    private XmppTypeManager xmppTypeManager;
    private ExecutorService executorService;   
    private Context mContext;
	
	private XmppManager(Context context){
		mContext = context;
		//taskSubmitter = snsService.getTaskSubmitter();
		//taskTracker = snsService.getTaskTracker();
		phoneStateListener = new PhoneStateChangeListener(this);
		executorService = Executors.newSingleThreadExecutor();
		 // 任务提交
        taskSubmitter = new TaskSubmitter();
        // 任务跟踪
        taskTracker = new TaskTracker();
		taskList = new ArrayList<Runnable>();
		System.setProperty("smack.debugEnabled", "true");
	}
	
	public XmppManager(Context context, String username, String password, LoginListenser listenser) {
		this(context);
		this.username = username;
		this.password = password;
		this.mListenser = listenser;
		
		connectionListener = new PersistentConnectionListener(this, mListenser);
		snsMessageLisener = new SNSMessageManager(XmppManager.this);
		
		xmppTypeManager = new XmppTypeManager(context);
		// XMPP 开启状态
		this.saveXmppType(XmppType.XMPP_STATE_START);
	}
	
	public void setHost(String host, String port, String resoursename){
		if(!TextUtils.isEmpty(host) && !TextUtils.isEmpty(port) && !TextUtils.isEmpty(resoursename)){
			this.HOST = host;
			this.PORT = Integer.parseInt(port);
			this.XMPP_RESOURCE_NAME = resoursename;
		}
	}
	
	/**
	 * 更新Openfire登录密码
	 * @param password
	 */
	public void setPassword(String password){
		this.password = password;
	}
	
	public void connect() {
		telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		registerConnectivityReceiver();
		Log.d(LOGTAG, "connect()...");
		// 提交登录任务.
		submitLoginTask();
		
	}
	
	public void disconnect() {
		unregisterConnectivityReceiver();
		Log.d(LOGTAG, "disconnect()...");
		// 取消登录
		saveXmppType(XmppType.XMPP_STATE_REAUTH);
		terminatePersistentConnection();
		executorService.shutdown();
	}

	public Future<?> getFutureTask() {
		return futureTask;
	}

	public List<Runnable> getTaskList() {
		return taskList;
	}

	public boolean isConnected() {
		return connection != null && connection.isConnected();
	}

	public SNSMessageManager getSnsMessageLisener() {
		return snsMessageLisener;
	}

	public boolean isAuthenticated() {
		return connection != null && connection.isConnected()
				&& connection.isAuthenticated();
	}

	public XMPPConnection getConnection() {
		return connection;
	}

	public void setConnection(XMPPConnection connection) {
		this.connection = connection;
	}
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	/**
	 * 开始重新连接
	 * 
	 * 作者:fighter <br />
	 * 创建时间:2013-4-16<br />
	 * 修改时间:<br />
	 */
	public void startReconnectionThread() {
		Log.i(LOGTAG, "重新连接");
		taskList.clear();
		taskTracker.count = taskList.size();
		addTask(new Runnable() {
			
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					// TODO: handle exception
				}
//				terminatePersistentConnection(); // 断开连接
				
				Log.d(LOGTAG, "开始连接");
				connect() ;
				runTask();
			}
		});
		
		runTask();
	}
	
	/**
	 * 终止连接
	 */
	public void terminatePersistentConnection() {
		Log.d(LOGTAG, "terminatePersistentConnection()...");
		Runnable runnable = new Runnable() {

			final XmppManager xmppManager = XmppManager.this;

			@Override
			public void run() {
				if (xmppManager.isConnected()) {
					Log.d(LOGTAG, "terminatePersistentConnection()... run()");
					xmppManager.getConnection().disconnect();
				}
				xmppManager.runTask();
			}

		};
		addTask(runnable);
	}
	
	public void runTask() {
		Log.d(LOGTAG, "runTask()...");
		synchronized (taskList) {
			running = false;
			futureTask = null;
			if (!taskList.isEmpty()) {
				Runnable runnable = taskList.get(0);
				taskList.remove(0);
				running = true;
				futureTask = taskSubmitter.submit(runnable);
				if (futureTask == null) {
					taskTracker.decrease();
				}
			}
		}
		taskTracker.decrease();
		Log.d(LOGTAG, "runTask()...done");
	}
	
	// 开始连接.
	private void submitConnectTask() {
		Log.d(LOGTAG, "submitConnectTask()...");
		addTask(new ConnectTask());
	}

	private void submitLoginTask() {
		Log.d(LOGTAG, "submitLoginTask()...");
		// 开启XMPP连接
		submitConnectTask();
		//登录XMPP
		addTask(new LoginTask());
	}
	
	private void addTask(Runnable runnable) {
		Log.d(LOGTAG, "addTask(runnable)...");
		taskTracker.increase();
		synchronized (taskList) {
			if (taskList.isEmpty() && !running) {
				running = true;
				futureTask = taskSubmitter.submit(runnable);
				if (futureTask == null) {
					taskTracker.decrease();
				}
			} else {
				taskList.add(runnable);
			}
		}
		Log.d(LOGTAG, "addTask(runnable)... done");
	}
	
	/**
	 * A runnable task to connect the server.
	 */
	private class ConnectTask implements Runnable {

		final XmppManager xmppManager;

		private ConnectTask() {
			this.xmppManager = XmppManager.this;
		}

		@Override
		public void run() {
			Log.i(LOGTAG, "ConnectTask.run()...");
			connectState = XMPP_CONNECT_STATE;
			if (!xmppManager.isAuthenticated()) {
				// Create the configuration for this new connection
				ConnectionConfiguration connConfig = new ConnectionConfiguration(
						HOST, PORT);
				// connConfig.setSecurityMode(SecurityMode.disabled);
				connConfig.setReconnectionAllowed(true);
				connConfig.setSecurityMode(SecurityMode.disabled); // 设置安全模式
				connConfig.setSASLAuthenticationEnabled(false); // 不启用sasl 认证启用
				connConfig.setCompressionEnabled(false); // 压缩不启用

				XMPPConnection connection = new XMPPConnection(connConfig);
				xmppManager.setConnection(connection);

				try {
					// Connect to the server
					connection.connect();
					// 连接成功,开始登录
					xmppManager.runTask();
					Log.i(LOGTAG, "XMPP connected successfully");
				} catch (XMPPException e) {
					Log.i(LOGTAG, "XMPP connection failed " + e.getMessage(), e);
//					terminatePersistentConnection();
					startReconnectionThread();
					if(mListenser != null){
						mListenser.onFailed("XMPP connection failed "
							+ e.getMessage());
					}
				}
				
			} else {
				Log.i(LOGTAG, "XMPP connected already");
				xmppManager.runTask();
			}
		}
	}
		
	/**
	 * A runnable task to log into the server.
	 */
	private class LoginTask implements Runnable {
		final XmppManager xmppManager;

		private LoginTask() {
			this.xmppManager = XmppManager.this;
		}

		@Override
		public void run() {
			Log.i(LOGTAG, "LoginTask.run()...");
			connectState = XMPP_LOGINING_STATE;
			// 1. 是否登录的
			if (!xmppManager.isAuthenticated()) {
				try {
					// 2.登录
					xmppManager.getConnection().login(
							xmppManager.getUsername(),
							xmppManager.getPassword(), XMPP_RESOURCE_NAME);
					//Log.d(LOGTAG, "Loggedn in successfully");
					Log.e("XMPP", "Login successfully");
					loginSuccess();
					xmppManager.runTask();
					if(mListenser != null){
						mListenser.onSuccess();
					}

				} catch (XMPPException e) {
					//Log.e(LOGTAG, "LoginTask.run()... xmpp error");
					Log.e(LOGTAG, "Failed to login to xmpp server. Caused by: "
							+ e.getMessage());
					String INVALID_CREDENTIALS_ERROR_CODE = "401";
					String errorMessage = e.getMessage();
					if (errorMessage != null
							&& errorMessage
									.contains(INVALID_CREDENTIALS_ERROR_CODE)) {
						// TODO 还没有注册或者用户名密码错误!
						connectState = XMPP_AUTH_ERR;
						// 认证错误.
						saveXmppType(XmppType.XMPP_STATE_AUTHERR);
					}
					xmppManager.startReconnectionThread();
					if(mListenser != null){
						mListenser.onFailed(errorMessage);
					}

				} catch (Exception e) {
					//Log.e(LOGTAG, "LoginTask.run()... other error");
					Log.e(LOGTAG, "Failed to login to xmpp server. Caused by: "
							+ e.getMessage());
					xmppManager.startReconnectionThread();
					if(mListenser != null){
						mListenser.onFailed("Failed to login to xmpp server. Caused by: "
							+ e.getMessage());
					}
				}

			} else {
				Log.i(LOGTAG, "Logged in already");
				xmppManager.runTask();
				connectState = XMPP_LOGINED_STATE;
				
				saveXmppType(XmppType.XMPP_STATE_AUTHENTICATION);
			}
		}
	}

	private void loginSuccess() {
		connectState = XMPP_LOGINED_STATE;
		// 认证成功.
		saveXmppType(XmppType.XMPP_STATE_AUTHENTICATION);
		// connection listener
		if (connectionListener != null) {
			getConnection().addConnectionListener(connectionListener);
		}
		
		getConnection().getChatManager().addChatListener(snsMessageLisener);
	}

	public int getConnectState() {
		return connectState;
	}

	public ConnectionListener getConnectionListener() {
		return connectionListener;
	}
	
	/**
     * Class for summiting a new runnable task.
     */
    public class TaskSubmitter {


        public TaskSubmitter() {
        }

        public Future<?> submit(Runnable task) {
            Future<?> result = null;
            if (!executorService.isTerminated()
                    && !executorService.isShutdown()
                    && task != null) {
                result = executorService.submit(task);
            }
            return result;
        }

    }
	
	/**
     * Class for monitoring the running task count.
     */
    public class TaskTracker {

        public int count;

        public TaskTracker() {
            this.count = 0;
        }

        public void increase() {
            synchronized (taskTracker) {
            	taskTracker.count++;
                Log.d(LOGTAG, "Incremented task count to " + count);
            }
        }

        public void decrease() {
            synchronized (taskTracker) {
            	taskTracker.count--;
                Log.d(LOGTAG, "Decremented task count to " + count);
            }
        }

    }
    /**
     * 保存xmpp状态
     * @param type  {@link XmppType}
     * 作者:fighter <br />
     * 创建时间:2013-6-1<br />
     * 修改时间:<br />
     */
    public void saveXmppType(String type){
    	xmppTypeManager.saveXmppType(type);
    }

	public XmppTypeManager getXmppTypeManager() {
		return xmppTypeManager;
	}
	
	private void registerConnectivityReceiver() {
        Log.d(LOGTAG, "registerConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_DATA_CONNECTION_STATE);
        /*IntentFilter filter = new IntentFilter();
        // filter.addAction(android.net.wifi.WifiManager.NETWORK_STATE_CHANGED_ACTION);
        
        // 注册网络发送改变后，接收通知
        filter.addAction(android.net.ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(connectivityReceiver, filter);*/
    }

    private void unregisterConnectivityReceiver() {
        Log.d(LOGTAG, "unregisterConnectivityReceiver()...");
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_NONE);
        //unregisterReceiver(connectivityReceiver);
    }

}
