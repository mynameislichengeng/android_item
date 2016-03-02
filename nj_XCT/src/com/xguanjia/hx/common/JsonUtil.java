package com.xguanjia.hx.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonUtil {
	private static final String TAG = "JsonUtilFactory";

	public static Object json2Object(Object jsonObject) throws HaoqeeException {
		try {
			if (jsonObject instanceof JSONObject) {
				return JsonUtil.json2Map((JSONObject) jsonObject);
			}
			if (jsonObject instanceof JSONArray) {
				return JsonUtil.json2List((JSONArray) jsonObject);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new HaoqeeException("json转对象异常", e);
		}
		return null;
	}

	public static Object object2Json(Object object) throws HaoqeeException {
		try {
			if (object instanceof List) {
				JSONArray jsonArray = new JSONArray();
				for (int i = 0; i < ((List) object).size(); i++) {
					jsonArray.put(JsonUtil.object2Json(((List) object).get(i)));
				}
				return jsonArray;
			}
			if (object instanceof Map) {
				JSONObject jsonObject = new JSONObject();
				for (Object key : ((Map) object).keySet()) {
					jsonObject.put((String) key, JsonUtil.object2Json(((Map) object).get(key)));
				}
				return jsonObject;
			}
			if (object == null)
				return "";
			else {
				return object;
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new HaoqeeException("对象转json异常", e);
		}
	}

	public static Map<String, Object> json2Map(JSONObject jsonObject) throws JSONException {
		Iterator<String> iterator = jsonObject.keys();
		String key;
		Object object;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		while (iterator.hasNext()) {
			key = iterator.next();
			object = jsonObject.get(key);
			if (object == null || object == JSONObject.NULL)
				continue;
			if (object instanceof JSONObject) {
				Map<String, Object> map = JsonUtil.json2Map((JSONObject) object);
				valueMap.put(key, map);
			} else if (object instanceof JSONArray) {
				List<Object> list = JsonUtil.json2List((JSONArray) object);
				valueMap.put(key, list);
			} else {
				valueMap.put(key, object);
			}
		}
		return valueMap;
	}

	private static List<Object> json2List(JSONArray jsonArray) throws JSONException {
		Object object;
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < jsonArray.length(); i++) {
			object = jsonArray.get(i);
			if (object == JSONObject.NULL) {
				list.add(null);
			} else if (object instanceof JSONObject) {
				Map<String, Object> map = JsonUtil.json2Map((JSONObject) object);
				list.add(map);
			} else if (object instanceof JSONArray) {
				List<Object> subList = JsonUtil.json2List((JSONArray) object);
				list.add(subList);
			} else {
				list.add(object);
			}
		}
		return list;
	}

	public static <T> T fromJson(String json, TypeToken<T> token) {
		if ("".equals(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			Log.e(TAG, json + " 无法转换为 " + token.getRawType().getName() + " 对象!", ex);
			return null;
		}
	}

}
