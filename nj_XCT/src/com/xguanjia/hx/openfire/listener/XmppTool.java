package com.xguanjia.hx.openfire.listener;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;

import android.util.Log;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.openfire.until.Openfir_MD5;


public class XmppTool {

	public static XMPPConnection con = null;
//	private static String ip = "112.4.17.113";//服务器测试
	private static String ip = "112.4.17.103";//服务器正式
	private static void openConnection() {
		try {
			ConnectionConfiguration connConfig = new ConnectionConfiguration(ip, 5222);
			con = new XMPPConnection(connConfig);
			con.connect();
			
			if (!Constants.uid.equals("") && !Constants.password.equals("")) {
				Openfir_MD5 md5 = new Openfir_MD5();
			
				con.login(Constants.uid, md5.getMD5ofStr("888888"));
			}

		} catch (Exception xe) {
			xe.printStackTrace();
		}
	}

	public static XMPPConnection getConnection() {
		if (con == null) {
	
			openConnection();
		}
		
		return con;
	}

	public static void closeConnection() {
		con.disconnect();
		con = null;
	}
}
