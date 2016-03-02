package com.xguanjia.hx.feedback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.DES;
import com.xguanjia.hx.common.DESEncrypter;
import com.xguanjia.hx.common.HttpClient;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;

/**
 * http异步任务
 * 
 * @author ytg
 * @date 2012-09-05
 */
public class AsyncHttpTask extends AsyncTask<JSONObject, String, ActionResponse> {
	private static final String TAG = "AsyncHttpTask";
	private static String DEFAULT_ENCODING = "UTF-8";
	public final static String POST_DOWNLOAD = "download";
	public final static String POST_UPLOAD = "upload";
	private SharedPreferences sf;
	Context context;
	ServiceSyncListener listener;
	private String serverUrl;
	private Resources res;
	String funName;
	String file_type;
	// String seedName = "";
	String desName = "";

	// 报文加解密类
	DESEncrypter _Des = new DESEncrypter();
	DES des = new DES("12345678");
	// 获取密匙，默认"12345678"
	@SuppressWarnings("static-access")
	String desKey = _Des.strDefaultKey;
	// 获取加密模式, "1"加密, "0"不加密
	// String mode = teController.getAttribute("m-adaptor-encrypt-mode");
	String mode = "1";

	// 下载使用输出流
	private OutputStream outputStream;
	// 上传使用输入流
	private InputStream inputStream;
	// 请求类型 默认普通请求 ， POST_DOWNLOAD下载请求 ， POST_UPLOAD 上传请求
	private String postType = "";

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	private static AsyncHttpTask instance = null;

	public static AsyncHttpTask instance() {
		instance = new AsyncHttpTask();
		return instance;
	}

	@Override
	protected void onPreExecute() {
		res = context.getResources();
		// serverUrl = Constants.MAINTAIN_IP + "/yw/mobile!problemBack.action";
		// serverUrl = "192.168.2.132:8080/yw/mobile!problemBack.action";
		// serverUrl = Constants.IP+"/mobile!problemBack.action";
		// http://42.121.110.141?mobile!problemBack.action
		serverUrl = "http://42.121.110.141/mobile!problemBack.action";
	};

	/**
	 * HTTP请求头ͷ
	 */
	@Override
	protected ActionResponse doInBackground(JSONObject... jsonObjects) {
		try {
			String respData = HttpClient.getContext(serverUrl, jsonObjects[0].toString(), "UTF-8");
			byte[] responseByte = respData.getBytes(DEFAULT_ENCODING);
			String responseStr = new String(responseByte, 0, responseByte.length, DEFAULT_ENCODING);
			JSONObject responseJson = null;
			if (null == responseStr || responseStr.length() == 0) {
				responseJson = new JSONObject();
			} else {
				responseJson = new JSONObject(responseStr);
			}

			int code = -1;
			String message = "";
			Object data = null;

			if (!responseJson.isNull("code")) {
				code = responseJson.getInt("code");
			}
			if (!responseJson.isNull("message")) {
				message = responseJson.getString("message");
			}
			if (!responseJson.isNull("data")) {
				data = responseJson.get("data");
			}
			ActionResponse returnObject = new ActionResponse(code, message, data);
			return returnObject;
		} catch (Exception e) {
			Log.i(TAG, e.getMessage(), e);
		}
		return new ActionResponse(404, "网络通讯失败", null);
	}

	@Override
	protected void onProgressUpdate(String... progress) {
		listener.setProgressMessage(progress[0]);
	}

	@Override
	protected void onPostExecute(ActionResponse returnObject) {

		if (returnObject.getCode() == 0) {
			listener.onSuccess(returnObject);
			return;
		}
		listener.onError(returnObject);
	}

	/**
	 * 上传POST请求
	 * 
	 * @param httpPost
	 * @param instream
	 * @return
	 */
	@SuppressWarnings("unused")
	private ActionResponse doPostAsUpLoad(HttpPost httpPost, InputStream instream) throws HaoqeeException {
		ActionResponse returnObject;
		HttpResponse httpResponse;
		byte[] responseByte;
		String responseStr;
		try {
			InputStreamEntity inputStreamEntity = new InputStreamEntity(instream, instream.available());
			httpPost.setEntity(inputStreamEntity);
			httpPost.setHeader("Accept", "application/octet-stream");
			if (null != file_type) {
				httpPost.setHeader("file_type", file_type);
			}

			DefaultHttpClient httpclient = new DefaultHttpClient();
			// 设置COOKIES
			HttpContext localContext = new BasicHttpContext();
			if (ServerAdaptor.getCookieStore() != null) {
				localContext.setAttribute(ClientContext.COOKIE_STORE, ServerAdaptor.getCookieStore());
			}
			httpResponse = httpclient.execute(httpPost, localContext);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new HaoqeeException("Http请求响应失败");
			}

			if (ServerAdaptor.getCookieStore() == null) {
				ServerAdaptor.setCookieStore(httpclient.getCookieStore());
			}
			responseByte = EntityUtils.toByteArray(httpResponse.getEntity());
			if (ServerAdaptor.getCookieStore() == null) {
				ServerAdaptor.setCookieStore(httpclient.getCookieStore());
			}
			if (instream != null) {
				instream.close(); // 关闭输入流
			}
			Log.d("MAdaptorV2", "response JSON:\n" + new String(responseByte, 0, responseByte.length, DEFAULT_ENCODING));
			responseStr = new String(responseByte, 0, responseByte.length, DEFAULT_ENCODING);

		} catch (ClientProtocolException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("IO异常" + e.getMessage(), e);
		} catch (IOException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("IO�쳣" + e.getMessage(), e);
		}
		int code = 1;
		Header[] headersCode = httpResponse.getHeaders("code");
		if (headersCode.length > 0) {
			code = Integer.parseInt(headersCode[0].getValue());
			Log.d(" field code ", "--------------" + code);
		}
		String message = "";
		Header[] headersMsg = httpResponse.getHeaders("message");
		if (headersMsg.length > 0) {
			message = headersMsg[0].getValue();
		}

		try {
			JSONObject responseData = null;
			if (null != responseStr || "" != responseStr) {
				Log.d("responseStr--- ", responseStr);
				responseData = new JSONObject(responseStr);
			} else {
				Log.d("responseStr--- ", "is null!!!");
				responseData = new JSONObject();

			}

			if (!responseData.isNull("file_id")) {
				// JSONObject mData = responseData.getJSONObject("file_id");
				Object data = responseData.get("file_id") == null ? "" : responseData.get("file_id");
				returnObject = new ActionResponse(code, message, data);
			} else {
				returnObject = new ActionResponse(0, message, "");
			}
		} catch (JSONException e) {
			Log.e("M-Client", res.getString(R.string.response_error), e);
			throw new HaoqeeException("JSON转换异常", e);
		}
		return returnObject;
	}

}
