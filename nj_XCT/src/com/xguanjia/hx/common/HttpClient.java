package com.xguanjia.hx.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {

	public static String getContext(String urlStr, String data,String encode) throws Exception {
		InputStream in=null;
		java.io.BufferedReader breader=null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("Connection", "Keep-Alive");
			connection.setRequestProperty("Cache-Control", "no-cache");
			connection.setRequestProperty("Accept", "*/*");
			connection.setRequestProperty("Content-Type", "text/html");
			connection.setRequestProperty("User-Agent",	"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");
			connection.setRequestProperty("Accept-Language", "zh-cn");
			connection.setRequestProperty("Accept-Encoding", "gzip, deflate");
			connection.setRequestProperty("Content-Length", String.valueOf(data.length()));
			connection.setConnectTimeout(5000);
			connection.setDoOutput(true);
			connection.connect();
			connection.getOutputStream().write(data.getBytes());
			System.out.println(connection.getResponseCode());
			if (connection.getResponseCode() == 200) {
				in = connection.getInputStream();
				breader = new BufferedReader(new InputStreamReader(in, encode));
				String str = breader.readLine();
				StringBuffer sb = new StringBuffer();
				while (str != null) {
					sb.append(str);
					str = breader.readLine();
				}
				return sb.toString();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(null!=breader)
			{
				breader.close();
			}
			if(null!=in)
			{
				in.close();
			}
		}
		return "";
	}

}
