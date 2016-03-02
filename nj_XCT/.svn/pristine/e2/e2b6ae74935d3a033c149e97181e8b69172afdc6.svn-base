package com.xguanjia.hx.openfire.listener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.openfire.bean.OpenfireMessageBean;
import com.xguanjia.hx.openfire.fianlconstant.CommonConstant;
import com.xguanjia.hx.openfire.until.DataUntil;

public class XMPPChatListenerService extends Service {

	
	

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void unbindService(ServiceConnection conn) {
		// TODO Auto-generated method stub
		super.unbindService(conn);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		IntentFilter inf = new IntentFilter();
		inf.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		registerReceiver(mReceiver, inf);
		

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		HaoXinProjectActivity.cm = XmppTool.getConnection().getChatManager();
		HaoXinProjectActivity.cm.addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat arg0, boolean arg1) {
				arg0.addMessageListener(new ChatMessage_listener());
			}
		});

		return super.onStartCommand(intent, flags, startId);
	}

	private class ChatMessage_listener implements MessageListener {
		@Override
		public void processMessage(Chat arg0, Message arg1) {
			if(null ==arg1.getBody()||"".equals(arg1.getBody())){
				return;
			}
			String strContent = arg1.getBody();
			OpenfireMessageBean msgBean = DataUntil.getBeanFromJson(OpenfireMessageBean.class, strContent);
			Intent intent = new Intent();
			intent.setAction(CommonConstant.OPENFIREMSG_BRODACAST);
			intent.putExtra("data", msgBean);
			XMPPChatListenerService.this.sendBroadcast(intent);
			
		}// 接口方法结束

	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		public void onReceive(Context context, Intent intent) {

			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
			if (activeNetInfo != null) {// 连接
				XmppTool.con = null;
				Intent intent1 = new Intent(XMPPChatListenerService.this, XMPPChatListenerService.class);
				startService(intent1);
//				Intent intent2 = new Intent(XMPPChatListenerService.this, XMPPFileListenerService.class);
//				startService(intent2);
				Log.i("tag", "kimin1");
			} else {// 断开
				XmppTool.con = null;
				Log.i("tag", "kimin2");
			}

		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("tag", "onDestroy" + "xmppchatservice");
		if (mReceiver != null) {
			unregisterReceiver(mReceiver);
		}
	}

}
