package com.xguanjia.hx.notice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NoticeDetail {

	private String title;
	private String startDate;
	private String details;
	private String releaseName;
	private String [] aryDetails;
	private String attachment;
	
	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String[] getAryDetails() {
		return aryDetails;
	}

	public void setAryDetails(String[] aryDetails) {
		this.aryDetails = aryDetails;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getReleaseName() {
		return releaseName;
	}
	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}
	
	public NoticeDetail(){}
	public NoticeDetail(JSONObject data){
		
		if (data != null) {

			try {
				if (!data.isNull("title")) {
					this.title = data.getString("title");
				}
				
				if (!data.isNull("startTime")) {
					this.startDate = data.getString("startTime");
				}
				
//				if (!data.isNull("details")) {
//					this.details = data.getString("details");
//				}
				
				if (!data.isNull("details")) {
					
					this.details = data.getString("details");
					
					JSONArray ary = data.getJSONArray("details");
					int len = ary.length();
					aryDetails = new String[len];
					for(int i=0;i<len;i++){
						Object obj = ary.get(i);
						String str = String.valueOf(obj);
						aryDetails[i] = str;
					}
					
					
				}
				
				if (!data.isNull("releaseName")) {
					this.releaseName = data.getString("releaseName");
				}
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static List<NoticeDetail> jsonToList(JSONArray data){
		List<NoticeDetail> noticeDetail_list = null;
		
		if(null != data){
			noticeDetail_list = new ArrayList<NoticeDetail>();
			for(int i= 0; i<data.length(); i++){
				try {
					JSONObject oData = data.getJSONObject(i);
					noticeDetail_list.add(new NoticeDetail(oData));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return noticeDetail_list;
	}
	
	
}
