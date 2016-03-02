package com.xguanjia.hx.feedback;

import java.util.Map;

import org.apache.http.client.CookieStore;
import org.json.JSONObject;

import android.content.Context;

import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServiceSyncListener;

/**
 * HttpAdapter,负责域服务器进行通讯的
 * 
 * @author ytg
 * @date 2012-09-05
 */
public class HttpAdapter {
    private static HttpAdapter httAdaptor = null;
    private static Context context;
    public static String configTag = null;
    private static String DEFAULT_ENCODING = "UTF-8";
    private static CookieStore cookieStore = null;

    public static CookieStore getCookieStore() {
	return cookieStore;
    }

    public static void setCookieStore(CookieStore cookieStore) {
	HttpAdapter.cookieStore = cookieStore;
    }

    /**
     * 获取HttpAdapter实例
     * 
     * @param context
     * @return httAdaptor
     */
    public static HttpAdapter getInstance(Context context) {
	if (httAdaptor == null)
	    httAdaptor = new HttpAdapter();
	HttpAdapter.context = context;
	return httAdaptor;
    }

    /**
     * 执行动作
     */
    public void doAction(int viewInstance, String actionName, Object parameters, ServiceSyncListener listener) throws HaoqeeException {
	// 根据viewInstance创建（恢复）View上下文环境;
	// 根据actionName反射ActionController的doAction方法，并调用;
	/*
	 * 返回结果; HashMap<String, Object> body = new HashMap<String, Object>();
	 * Object data = null; //body.put("viewId", viewInstance);
	 * //body.put("actionName", actionName); try { body.put("parameters",
	 * JsonUtil.object2Json(parameters)); } catch (MAdaptorException e) {
	 * throw new MAdaptorException("对象转换json异常", e); }
	 */
	sendAsyncJsonMessage("doAction", (Map) parameters, listener);
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
	AsyncHttpTask asyncJsonTask = AsyncHttpTask.instance();
	asyncJsonTask.listener = listener;
	asyncJsonTask.context = context;
	asyncJsonTask.execute(requestJsonObject);

    }
}
