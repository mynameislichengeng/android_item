package com.haoqee.chatsdk.service;

import xmpp.push.sns.ConnectionListener;
import android.util.Log;

import com.haoqee.chatsdk.Interface.LoginListenser;

/**
 * 监听XMPP连接类
 *
 */
public class PersistentConnectionListener implements ConnectionListener {

    private static final String LOGTAG = "PersissstentConnectionListener";

    private final XmppManager xmppManager;
    private LoginListenser mListenser;			//登录回调函数

    public PersistentConnectionListener(XmppManager xmppManager, LoginListenser listenser) {
        this.xmppManager = xmppManager;
        this.mListenser = listenser;
    }

    @Override
    public void connectionClosed() {
        Log.i(LOGTAG, "connectionClosed()...");
        try {
        	xmppManager.startReconnectionThread();
		} catch (Exception e) {
			mListenser.onFailed(e.getMessage());
		}
    }

    @Override
    public void connectionClosedOnError(Exception e) {
        Log.i(LOGTAG, "connectionClosedOnError()..." + " " + e.getMessage());
        if(e.getMessage().contains("stream:error (conflict)")){
        	mListenser.onConflict();
        }else {
        	try {
            	xmppManager.getConnection().disconnect();
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    		try {
    			xmppManager.startReconnectionThread();
    		} catch (Exception e2) {
    		}
		}
        
    }

    @Override
    public void reconnectingIn(int seconds) {
        Log.i(LOGTAG, "reconnectingIn()...");
    }

    @Override
    public void reconnectionFailed(Exception e) {
        Log.i(LOGTAG, "reconnectionFailed()...");
    }

    @Override
    public void reconnectionSuccessful() {
        Log.i(LOGTAG, "reconnectionSuccessful()...");
    }

}
