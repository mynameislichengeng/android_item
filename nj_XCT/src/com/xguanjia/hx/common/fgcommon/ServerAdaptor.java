package com.xguanjia.hx.common.fgcommon;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.CookieStore;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DES;
import com.xguanjia.hx.common.DESEncrypter;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServiceSyncListener;

/**
 * 
 * @author acer
 * 
 */
public class ServerAdaptor {
	private static ServerAdaptor serverAdaptor = null;
	private static Context context;
	public static String configTag = null;
	private static String DEFAULT_ENCODING = "UTF-8";
	private static CookieStore cookieStore = null;
	// 报文加解密类
	DESEncrypter _Des = new DESEncrypter();
	DES des = new DES("12345678");
	String desKey = _Des.strDefaultKey;

	public static CookieStore getCookieStore() {
		return cookieStore;
	}

	public static void setCookieStore(CookieStore cookieStore) {
		ServerAdaptor.cookieStore = cookieStore;
	}

	public static ServerAdaptor getInstance(Context context) {
		if (serverAdaptor == null)
			serverAdaptor = new ServerAdaptor();
		ServerAdaptor.context = context;
		return serverAdaptor;
	}

	/**
	 * 执行动作
	 */
	public void doAction(int viewInstance, String actionName, Object parameters, ServiceSyncListener listener) throws HaoqeeException {
		// 根据viewInstance创建（恢复）View上下文环境;
		// 根据actionName反射ActionController的doAction方法，并调用;
		// 返回结果;
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("viewId", viewInstance);
		body.put("actionName", actionName);
		body.put("mobiletype", "android");
		// body.put("partyId", Constants.partyId);
		try {
			HashMap<String, Object> parameter = (HashMap<String, Object>) parameters;
			if (!parameter.containsKey("partyId")) {
				parameter.put("partyId", Constants.partyId);
			}
			body.put("parameters", JsonUtil.object2Json(parameter));
		} catch (HaoqeeException e) {
			throw new HaoqeeException("对象转换json异常", e);
		}

		sendAsyncJsonMessage("doAction", body, listener);
	}

	/**
	 * 执行动作
	 */
	public void doAction(String actionName, Object parameters, ServiceSyncListener listener) throws HaoqeeException {
		// 根据viewInstance创建（恢复）View上下文环境;
		// 根据actionName反射ActionController的doAction方法，并调用;
		// 返回结果;
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("actionName", actionName);
		body.put("mobiletype", "android");
		try {
			HashMap<String, Object> parameter = (HashMap<String, Object>) parameters;
			if (!parameter.containsKey("partyId")) {
				parameter.put("partyId", Constants.partyId);
			}
			body.put("parameters", JsonUtil.object2Json(parameter));
		} catch (HaoqeeException e) {
			throw new HaoqeeException("对象转换json异常", e);
		}

		sendAsyncJsonMessage("doAction", body, listener);
	}

	/**
	 * 上传文件
	 */
	public void uploadFile(String file_type, InputStream inStream, ServiceSyncListener listener) {
		// 读取inStream，并输出到唯一的临时文件中;
		// 返回该临时文件名;
		sendAsyncUploadJsonMessage("uploadFile", file_type, new HashMap<String, Object>(), inStream, listener);
	}

	/**
	 * 上传文件
	 */
	public void uploadFile(String file_type, String pathKey, InputStream inStream, ServiceSyncListener listener) {
		// 读取inStream，并输出到唯一的临时文件中;
		// 返回该临时文件名;
		sendAsyncUploadJsonMessage("uploadFile", file_type, pathKey, new HashMap<String, Object>(), inStream, listener);
	}

	/**
	 * gps上传图片
	 */
	public void gpsUploadFile(String file_type, String pathKey, HashMap<String, Object> hashMap, InputStream inStream, ServiceSyncListener listener) {
		// 读取inStream，并输出到唯一的临时文件中;
		// 返回该临时文件名;
		sendAsyncUploadJsonMessage("checkOnWorkAttendance", file_type, pathKey, hashMap, inStream, listener);
	}

	/**
	 * MServer上传文件
	 */
	public void uploadMServerFile(InputStream inStream, ServiceSyncListener listener) {
		// 读取inStream，并输出到唯一的临时文件中;
		// 返回该临时文件名;
		sendAsyncUploadJsonMessage("uploadMServerFile", "", new HashMap<String, Object>(), inStream, listener);
	}

//	/**
//	 * 下载文件
//	 */
//	public void downloadFile(int viewInstance, OutputStream outStream, String fileDownloadUrl, ServiceSyncListener listener) {
//		// 根据viewInstance创建（恢复）View上下文环境;
//		// ActionController.getInstance().downloadFile(outStream,
//		// fileDownloadUrl);
//		HashMap<String, Object> body = new HashMap<String, Object>();
//		body.put("viewId", viewInstance);
//		body.put("url", fileDownloadUrl);
//
//		sendAsyncDownloadJsonMessage("downloadFile", body, outStream, listener);
//	}
	
	/**
	 * 下载文件
	 */
	public void downloadFile(String fileDownloadUrl,
			ServiceSyncListener listener) {
		// 根据viewInstance创建（恢复）View上下文环境;
		// ActionController.getInstance().downloadFile(outStream,
		// fileDownloadUrl);
		HashMap<String, Object> body = new HashMap<String, Object>();
		body.put("url", fileDownloadUrl);

		sendAsyncDownloadJsonMessage("downloadFile", body, fileDownloadUrl,
				listener);
	}

	/**
	 * 普通HTTP请求
	 * 
	 * @param methodName
	 *            提交方式
	 * @param parameters
	 *            参数
	 * @param listener
	 *            回调函数
	 */
	private void sendAsyncJsonMessage(String methodName, Map<String, Object> parameters, ServiceSyncListener listener) {
		// JSON格式转换
		JSONObject requestJsonObject = new JSONObject(parameters);

		AsyncJsonTask asyncJsonTask = AsyncJsonTask.instance();
		asyncJsonTask.funName = methodName;
		asyncJsonTask.listener = listener;
		asyncJsonTask.context = context;
		asyncJsonTask.execute(requestJsonObject);

	}

//	/**
//	 * 下载文件HTTP请求
//	 * 
//	 * @param methodName
//	 *            提交方式
//	 * @param parameters
//	 *            参数
//	 * @param outStream
//	 *            输出流
//	 * @param listener
//	 *            回调函数
//	 */
//	private void sendAsyncDownloadJsonMessage(String methodName, Map<String, Object> parameters, OutputStream outStream, ServiceSyncListener listener) {
//		JSONObject requestJsonObject = new JSONObject(parameters);
//
//		AsyncJsonTask asyncJsonTask = AsyncJsonTask.instance(); // 505
//		asyncJsonTask.funName = methodName;
//		asyncJsonTask.listener = listener;
//		asyncJsonTask.context = context;
//		asyncJsonTask.setOutputStream(outStream);
//		asyncJsonTask.setPostType(AsyncJsonTask.POST_DOWNLOAD);
//		asyncJsonTask.execute(requestJsonObject);
//
//	}
	
	/**
	 * 下载文件HTTP请求
	 * 
	 * @param methodName
	 *            提交方式
	 * @param parameters
	 *            参数
	 * @param outStream
	 *            输出流
	 * @param listener
	 *            回调函数
	 */
	private void sendAsyncDownloadJsonMessage(String methodName,
			Map<String, Object> parameters, String url,
			ServiceSyncListener listener) {
		JSONObject requestJsonObject = new JSONObject(parameters);

		AsyncJsonTask asyncJsonTask = AsyncJsonTask.instance(); // 505
		asyncJsonTask.funName = methodName;
		asyncJsonTask.listener = listener;
		asyncJsonTask.context = context;
		asyncJsonTask.downLoadUrl = url;
		asyncJsonTask.setPostType(AsyncJsonTask.POST_DOWNLOAD);
		asyncJsonTask.execute(requestJsonObject);

	}

	/**
	 * 上传文件HTTP请求
	 * 
	 * @param methodName
	 *            提交方式
	 * @param parameters
	 *            参数
	 * @param inStream
	 *            输入流
	 * @param listener
	 *            回调函数
	 */
	private void sendAsyncUploadJsonMessage(String methodName, String file_type, Map<String, Object> parameters, InputStream inStream, ServiceSyncListener listener) {
		JSONObject requestJsonObject = new JSONObject(parameters);

		AsyncJsonTask asyncJsonTask = AsyncJsonTask.instance();
		asyncJsonTask.funName = methodName;
		asyncJsonTask.listener = listener;
		asyncJsonTask.context = context;
		asyncJsonTask.file_type = file_type;
		asyncJsonTask.setInputStream(inStream);
		asyncJsonTask.setPostType(AsyncJsonTask.POST_UPLOAD);
		asyncJsonTask.execute(requestJsonObject);

	}

	/**
	 * 上传文件HTTP请求
	 * 
	 * @param methodName
	 *            提交方式
	 * @param parameters
	 *            参数
	 * @param inStream
	 *            输入流
	 * @param listener
	 *            回调函数
	 */
	private void sendAsyncUploadJsonMessage(String methodName, String file_type, String pathKey, HashMap<String, Object> parameters, InputStream inStream, ServiceSyncListener listener) {
		JSONObject requestJsonObject = new JSONObject(parameters);

		AsyncJsonTask asyncJsonTask = AsyncJsonTask.instance();
		asyncJsonTask.funName = methodName;
		asyncJsonTask.listener = listener;
		asyncJsonTask.context = context;
		asyncJsonTask.postRequestMap = parameters;
		asyncJsonTask.file_type = file_type;
		asyncJsonTask.pathKey = pathKey;
		asyncJsonTask.setInputStream(inStream);
		asyncJsonTask.setPostType(AsyncJsonTask.POST_UPLOAD);
		asyncJsonTask.execute(requestJsonObject);

	}

	/**
	 * 执行post请求
	 * 
	 * @param url
	 * @param listener
	 */
	public void doPostAction(String parameters, ServiceSyncListener listener) throws HaoqeeException {
		sendPostAsyncJsonMessage(parameters, listener);
	}

	private void sendPostAsyncJsonMessage(String jsonObject, ServiceSyncListener listener) throws HaoqeeException {
		JSONObject requestJsonObject = null;
		try {
			requestJsonObject = new JSONObject(jsonObject);
			AsyncJsonTask asyncRequestTask = AsyncJsonTask.instance();
			asyncRequestTask.funName = "doAction";
			asyncRequestTask.listener = listener;
			asyncRequestTask.context = context;
			asyncRequestTask.jsonObject = jsonObject;
			asyncRequestTask.execute(requestJsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
