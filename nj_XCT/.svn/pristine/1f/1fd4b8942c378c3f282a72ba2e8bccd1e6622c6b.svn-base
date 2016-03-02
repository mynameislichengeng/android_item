package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class PageInfo implements Serializable{

	private static final long serialVersionUID = 146435615132L;
	
	/**
	 *  "total": "2",
    "count": 20,
    "pageCount": 1,
    "page": 1
	 */
	
	public int currentPage;
	public int totalPage;
	public int totalCount;
	public int pageSize;
	public boolean mHasMore;

	public PageInfo(){}
	
	public PageInfo(JSONObject json){
		try {
			currentPage = json.getInt("page");
			totalPage = json.getInt("pageCount");
			totalCount = json.getInt("total");
			pageSize = json.getInt("count");
			int hasMore = json.getInt("hasMore");
			mHasMore = hasMore == 1 ? true : false;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
