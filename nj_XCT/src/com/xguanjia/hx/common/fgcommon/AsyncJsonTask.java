package com.xguanjia.hx.common.fgcommon;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Base64;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DES;
import com.xguanjia.hx.common.DESEncrypter;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServiceSyncListener;
public class AsyncJsonTask extends AsyncTask<JSONObject, String, ActionResponse> {

	private static String DEFAULT_ENCODING = "UTF-8";
	public final static String POST_DOWNLOAD = "download";
	public final static String POST_UPLOAD = "upload";
	private ProgressDialog progressDialog;
	public HashMap<String, Object> postRequestMap;
	private SharedPreferences sf;
	protected String saveFilePath;
	public Context context;
	private int count;
	public ServiceSyncListener listener;
	private String httpType;
	private String serverUrl;
	private Resources res;
	public String funName;
	public String file_type;
	public String pathKey = "";
	// String seedName = "";
	String desName = "";
	public String downLoadUrl = "";
	public Object jsonObject;

	// TeController teController = new
	// TeController((AbstractJSActivity)context);

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

	public String getSaveFilePath() {
		return saveFilePath;
	}

	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}

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

	public HashMap<String, Object> getPostRequestMap() {
		return postRequestMap;
	}

	public void setPostRequestMap(HashMap<String, Object> postRequestMap) {
		this.postRequestMap = postRequestMap;
	}

	private static AsyncJsonTask instance = null;

	public static AsyncJsonTask instance() {
		instance = new AsyncJsonTask();
		return instance;
	}

	@Override
	protected void onPreExecute() {
		res = context.getResources();
		sf = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		serverUrl = sf.getString("ywIp", Constants.IP_FG) + "/ServiceServlet";
		if (POST_DOWNLOAD.equals(httpType)) {
			serverUrl = "";
			progressDialog = new ProgressDialog(context);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setMessage("文件下载中...");
			// 设置ProgressDialog 的进度条是否不明确
			progressDialog.setIndeterminate(false);
			// 设置ProgressDialog 是否可以按退回按键取消
			progressDialog.setCancelable(false);
			progressDialog.show();
		}
	};

	/**
	 * HTTP请求头ͷ
	 */
	@Override
	protected ActionResponse doInBackground(JSONObject... jsonObjects) {

		publishProgress(res.getString(R.string.disposing));
		// Log.d("serverUrl-----", serverUrl);
		// String url = "http://" + serverUrl + "/madaptor/ServiceServlet";
		String url = "http://" + serverUrl;
		HttpPost httpPost = new HttpPost(url);
		Log.i("URL", url);
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("te_method", funName);
		httpPost.setHeader("te_version", "v1.0");
		// httpPost.setHeader("compressMode", "false");
		// httpPost.setHeader("encryptMode", mode);
		try {
			if ("".equals(postType)) { // 普通提交HTTP请求
				return doPostAsJson(httpPost, jsonObjects);
			} else if (AsyncJsonTask.POST_DOWNLOAD.equals(postType)) {// 下载提交HTTP请求
//				return doPostAsDownLoad(httpPost, outputStream, jsonObjects);
				return doDownLoadAction(downLoadUrl);
			} else if (AsyncJsonTask.POST_UPLOAD.equals(postType)) { // 上传提交HTTP请求
				return doPostAsUpLoad(httpPost, inputStream);
			}
		} catch (HaoqeeException e) {
			e.printStackTrace();
//			return new ActionResponse(-1, e.getMessage(), null);
			return new ActionResponse(-1, "连接服务器超时,请检查网络!", null);
		}
		return null;
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
	 * 普通提交POST请求
	 * 
	 * @param httpPost
	 * @param jsonObjects
	 * @return
	 */
	private ActionResponse doPostAsJson(HttpPost httpPost, JSONObject... jsonObjects) throws HaoqeeException {
		ActionResponse returnObject = null;
		HttpResponse httpResponse;
		byte[] responseByte;
		String responseStr;
		try {
			ByteArrayEntity arrayEntity = null;
			byte[] jsonByte = null;
			byte[] encypt = new byte[3 * 1024];
			try {
				jsonByte = jsonObjects[0].toString().getBytes(DEFAULT_ENCODING);

				arrayEntity = new ByteArrayEntity(jsonByte);
				Log.d("22", "before jiami----" + jsonObjects[0].toString());

			} catch (UnsupportedEncodingException e) {
				throw new HaoqeeException("不支持的编码格式", e);
			}
			Log.d("MAdaptorV2", "---request JSON:\n" + new String(jsonByte, DEFAULT_ENCODING));
			httpPost.setEntity(arrayEntity);
			httpPost.setHeader("Accept", "application/json");
			
			DefaultHttpClient httpclient = new DefaultHttpClient();
			//设置请求超时
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constants.LOGIN_TIME_OUT);
			//设置等待数据时间
			httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Constants.LOGIN_TIME_OUT);
			// COOKIES
			HttpContext localContext = new BasicHttpContext();
			if (ServerAdaptor.getCookieStore() != null) {
				List<Cookie> list = ServerAdaptor.getCookieStore().getCookies();
				if (list != null && !list.isEmpty()) {
					System.out.println("cookies 不为空");
					System.out.println("value--->" + list.get(0).getValue());
				} else {
					System.out.println("cookie is null");
				}
				localContext.setAttribute(ClientContext.COOKIE_STORE, ServerAdaptor.getCookieStore());
			}
			httpResponse = httpclient.execute(httpPost, localContext);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new HaoqeeException(httpResponse.getStatusLine().getStatusCode() + "错误");
			}
			responseByte = EntityUtils.toByteArray(httpResponse.getEntity());
			if (ServerAdaptor.getCookieStore() == null) {
				ServerAdaptor.setCookieStore(httpclient.getCookieStore());
			}

			Log.d("MAdaptorV2", "response JSON:\n" + new String(responseByte, 0, responseByte.length, DEFAULT_ENCODING));
			// 当用户名和密码输入错误的时候取得的code是1，{data:{"code":1}}
			responseStr = new String(responseByte, 0, responseByte.length, DEFAULT_ENCODING);

			Log.i("AsyncJsonTask", "responseStr:" + responseStr);
		} catch (ClientProtocolException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("HTTP异常", e);
		} catch (IOException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("异常" + e.getMessage(), e);
		}

		int code = -1;
		Header[] headersCode = httpResponse.getHeaders("code");
		if (headersCode.length > 0) {
			code = Integer.parseInt(headersCode[0].getValue());
			Log.d("22", "responseCode--->" + code);
		}
		String message = "";
		Header[] headersMsg = httpResponse.getHeaders("message");
		if (headersMsg.length > 0) {
			message = new String(Base64.decode(headersMsg[0].getValue(), Base64.DEFAULT));
			Log.e("MAdaptorV2", " message " + message);
		}
		try {
			JSONObject responseData = null;
			if (null == responseStr || responseStr.length() == 0) {
				responseData = new JSONObject();

			} else {
				responseData = new JSONObject(responseStr);
			}

			if (!responseData.isNull("data")) {
				Object data = null;
				JSONObject mData = responseData.getJSONObject("data");
				JSONObject returnData = responseData.getJSONObject("data");
				if (mData.has("records")) {
					data = mData.get("records") == null ? "" : mData.get("records");
					returnObject = new ActionResponse(code, message, data, returnData);
				} else {
					returnObject = new ActionResponse(code, message, mData, returnData);
				}

			} else {
				returnObject = new ActionResponse(code, message, "");
			}

		} catch (JSONException e) {
			Log.e("M-Client", res.getString(R.string.response_error), e);
			throw new HaoqeeException("JSON转换异常", e);
		}
		return returnObject;
	}

	/**
	 * 下载POST请求
	 * 
	 * @param httpPost
	 * @param outputStream
	 * @param jsonObjects
	 * @return
	 */
	private ActionResponse doPostAsDownLoad(HttpPost httpPost, OutputStream outputStream, JSONObject... jsonObjects) throws HaoqeeException {
		ActionResponse returnObject;
		HttpResponse httpResponse;
		String message = "";
		int total_page = 0;
		int page = 0;
		try {
			ByteArrayEntity arrayEntity = null;
			byte[] jsonByte = null;
			try {
				jsonByte = jsonObjects[0].toString().getBytes(DEFAULT_ENCODING);

				arrayEntity = new ByteArrayEntity(jsonByte);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new HaoqeeException("不支持的编码格式", e);
			}
			Log.d("MAdaptorV2", "request JSON:\n" + new String(jsonByte, DEFAULT_ENCODING));
			httpPost.setEntity(arrayEntity);
			httpPost.setHeader("Accept", "application/octet-stream");

			DefaultHttpClient httpclient = new DefaultHttpClient();
			//设置请求超时
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constants.LOGIN_TIME_OUT);
			// 设置COOKIES
			HttpContext localContext = new BasicHttpContext();
			if (ServerAdaptor.getCookieStore() != null) {
				localContext.setAttribute(ClientContext.COOKIE_STORE, ServerAdaptor.getCookieStore());
			}
			httpResponse = httpclient.execute(httpPost, localContext);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				throw new HaoqeeException("Http请求响应失败");
			}

			// 获取总页数

			Header[] headersTotalPage = httpResponse.getHeaders("total_page");
			if (headersTotalPage.length > 0) {
				total_page = Integer.parseInt(headersTotalPage[0].getValue());
				Log.d("downloadSync-total_page", "--------------" + total_page);
			}

			Header[] headersPage = httpResponse.getHeaders("page");
			if (headersPage.length > 0) {
				page = Integer.parseInt(headersPage[0].getValue());
				Log.d("downloadSync-page", "--------------" + page);
			}

			// 对下在的文件进行读取和写入操作
			Header[] headersConfig = httpResponse.getHeaders("config_tag");
			if (headersConfig.length > 0) {
				String configTag = headersConfig[0].getValue();
				ServerAdaptor.configTag = configTag;
			}
			Header[] headersMsg = httpResponse.getHeaders("message");
			if (headersMsg.length > 0) {
				message = headersMsg[0].getValue();
				Log.i("downloadSync-message", "-----" + message);
			}

			InputStream in = httpResponse.getEntity().getContent();
			byte[] cache = new byte[1024];
			int read = -1;
			while ((read = in.read(cache)) != -1) {
				outputStream.write(cache, 0, read);
			}
			if (ServerAdaptor.getCookieStore() == null) {
				ServerAdaptor.setCookieStore(httpclient.getCookieStore());
			}
			in.close(); // 关闭输入流
			if (outputStream != null) {
				outputStream.flush();
				outputStream.close();// 关闭输出流
			}
		} catch (ClientProtocolException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("HTTP异常", e);
		} catch (IOException e) {
			Log.e("MAdaptorV2", res.getString(R.string.response_error), e);
			throw new HaoqeeException("IO异常" + e.getMessage(), e);
		}
		Log.i("down----", "DownLoad success");
		JSONObject resultobj = new JSONObject();
		try {
			resultobj.put("page", page);
			resultobj.put("total_page", total_page);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnObject = new ActionResponse(0, message, resultobj); // 382
		return returnObject;
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
				httpPost.setHeader("pathKey", pathKey);
			}
			if ("checkOnWorkAttendance".equals(funName)) {
//				httpPost.setHeader("longitude", String.valueOf(postRequestMap.get("longitude")));
//				httpPost.setHeader("latitude", String.valueOf(postRequestMap.get("latitude")));
//				httpPost.setHeader("userId", Constants.userId);
//				httpPost.setHeader("flag", String.valueOf(postRequestMap.get("flag")));
//				httpPost.setHeader("actionName", String.valueOf(postRequestMap.get("actionName")));
				Collection<Object> collection=postRequestMap.values();
				ArrayList<Object> arrayList=new ArrayList<Object>(collection);
				Set<String> set= postRequestMap.keySet();
				int i=0;
				for(String key:set){
					httpPost.setHeader(key,arrayList.get(i).toString() );
					i++;
				}
			}

			DefaultHttpClient httpclient = new DefaultHttpClient();
			//设置请求超时
			httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Constants.LOGIN_TIME_OUT);
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
			Log.d("responseStr--- ", responseStr);
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
			message = new String(Base64.decode(headersMsg[0].getValue(), Base64.DEFAULT));
		}
		
		try {
			JSONObject responseData = null;
			if (null != responseStr && !"".equals(responseStr) ) {
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
				returnObject = new ActionResponse(code, message);
			}
		} catch (JSONException e) {
			Log.e("M-Client", res.getString(R.string.response_error), e);
			throw new HaoqeeException("JSON转换异常", e);
		}
		return returnObject;
	}

	public void login(Map<String, String> val) {
		try {
			ServerAdaptor.getInstance(context).doAction(1, "LoginAction$login", val, new ServiceSyncListener() {

				@Override
				public void onError(ActionResponse returnObject) {
				}

				@Override
				public void onSuccess(ActionResponse returnObject) {
					HashMap<String, Object> requestMap = new HashMap<String, Object>();
					requestMap.put("userId", Constants.userId);
					requestMap.put("lastReceivedTime", "2012-07-28 15:22:05");
					String method = Constants.UrlHead+"client.action.InnerMsgAction$getReceivedMsg";
					HashMap<String, Object> body = new HashMap<String, Object>();
					body.put("viewId", 1);
					body.put("actionName", method);
					try {
						body.put("parameters", JsonUtil.object2Json(requestMap));
					} catch (HaoqeeException e) {
						try {
							throw new HaoqeeException("对象转换json异常", e);
						} catch (HaoqeeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					JSONObject object = new JSONObject(body);
					serverUrl = Constants.IP + "/ServiceServlet";
					String url = "http://" + serverUrl;
					HttpPost httpPost = new HttpPost(url);
					try {
						doPostAsJson(httpPost, object);
					} catch (HaoqeeException e1) {
						e1.printStackTrace();
					}
					JSONObject resData = (JSONObject) returnObject.getData();
					try {
						Constants.userId = resData.getString("userId");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}

	}

	private ActionResponse doDownLoadAction() throws HaoqeeException {
		ActionResponse returnObject = new ActionResponse(-1, "文件下载失败");
		File file = new File(saveFilePath);
		InputStream inputStream = null;
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			// 建立远程连接
			URL url = new URL(serverUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.connect();
			inputStream = urlConnection.getInputStream();
			progressDialog.setMax(urlConnection.getContentLength());
			int read = -1;
			// 文件缓冲区大小 1K
			byte[] cache = new byte[1024 * 10];
			out = new FileOutputStream(file);
			while ((read = inputStream.read(cache)) != -1) {
				count = count + read;
				progressDialog.setProgress(count);
				out.write(cache, 0, read);
			}
			returnObject.setCode(0);
			returnObject.setMessage("文件下载成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					inputStream = null;
				}
			}
		}
		return returnObject;
	}
	
	
	private ActionResponse doDownLoadAction(String downLoadUrl)
			throws HaoqeeException {
		ActionResponse returnObject = new ActionResponse(-1, "文件下载失败");
		String fileName = downLoadUrl.substring(
				downLoadUrl.lastIndexOf("/") + 1, downLoadUrl.length());
		File file = new File(Constants.SAVE_PATH + fileName);
		InputStream inputStream = null;
		FileOutputStream out = null;
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			// 建立远程连接
			URL url = new URL("http://" + sf.getString("ip", Constants.IP)
					+ downLoadUrl);
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
			urlConnection.connect();
			inputStream = urlConnection.getInputStream();
			// progressDialog.setMax(urlConnection.getContentLength());
			int read = -1;
			// 文件缓冲区大小 1K
			byte[] cache = new byte[1024 * 10];
			out = new FileOutputStream(file);
			while ((read = inputStream.read(cache)) != -1) {
				// count = count + read;
				// progressDialog.setProgress(count);
				out.write(cache, 0, read);
			}
			returnObject.setCode(0);
			returnObject.setData(fileName);
			returnObject.setMessage("文件下载成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != out) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
				}
			}
			if (null != inputStream) {
				try {
					inputStream.close();
				} catch (IOException e) {
					inputStream = null;
				}
			}
		}
		return returnObject;
	}

}
