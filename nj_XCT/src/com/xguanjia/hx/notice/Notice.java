package com.xguanjia.hx.notice;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notice {
	private String id;                   //公告通知id
	private String top;                  //true表示置顶
	private String topDate;              //置顶时间		
	private String title;                //公告标题
	private String startTime;            //开始时间
	private String releaseName;			 //发布人姓名
	private String details;
	private String briefIntroduction;    //内容摘要
	private String[] arrDetails;
	private String bool;
	private String author="";//作者
	private String sectionName="";//栏目
	private String updateTime="";//修改时间
	private String operatorType="";//I 插入 U 更新 D 删除
	private String isread="";  //0未读1已读
	
	public String getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getBool() {
		return bool;
	}

	public void setBool(String bool) {
		this.bool = bool;
	}


	public Notice(){
		
	}

	private int image;                   //图片

	public Notice(JSONObject data) {
		if (data != null) {

			try {
				if (!data.isNull("id")) {
					this.id = data.getString("id");
				}
				
				if (!data.isNull("top")) {
					this.top = data.getString("top");
				}
				
				if (!data.isNull("topDate")) {
					this.topDate = data.getString("topDate");
				}
				
				if (!data.isNull("subject")) {
					this.title = data.getString("subject");
				}
				
				if (!data.isNull("createTime")) {
					this.startTime = data.getString("createTime");
				}
				
				if (!data.isNull("issuer")) {
					this.releaseName = data.getString("issuer");
				}
				if (!data.isNull("author")) {
					this.author = data.getString("author");
				}
				if (!data.isNull("sectionName")) {
					this.sectionName = data.getString("sectionName");
				}
				if (!data.isNull("updateTime")) {
					this.updateTime = data.getString("updateTime");
				}

				if (!data.isNull("details")) {
					this.details = data.getString("details");
					JSONArray ary = data.getJSONArray("details");
					arrDetails = new String[ary.length()];
					for(int i=0;i<arrDetails.length;i++){
						Object obj = ary.get(i);
						String str = String.valueOf(obj);
						arrDetails[i] = str;
					}
				}
				
				if (!data.isNull("briefIntroduction")) {
					this.briefIntroduction = data.getString("briefIntroduction");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReleaseName() {
		return releaseName;
	}

	public void setReleaseName(String releaseName) {
		this.releaseName = releaseName;
	}

	public int getImage() {
		return image;
	}

	public void setImage(int image) {
		this.image = image;
	}
	
	
	
	public String getBriefIntroduction() {
		return briefIntroduction;
	}


	public void setBriefIntroduction(String briefIntroduction) {
		this.briefIntroduction = briefIntroduction;
	}

	
	public String[] getArrDetails() {
		return arrDetails;
	}


	public void setArrDetails(String[] arrDetails) {
		this.arrDetails = arrDetails;
	}
	public String getTop() {
		return top;
	}


	public void setTop(String top) {
		this.top = top;
	}


	public String getTopDate() {
		return topDate;
	}


	public void setTopDate(String topDate) {
		this.topDate = topDate;
	}


	public static List<Notice> jsonToList(JSONArray data){
		List<Notice> noticeList = null;
		
		if(null != data){
			noticeList = new ArrayList<Notice>();
			for(int i= 0; i<data.length(); i++){
				try {
					JSONObject oData = data.getJSONObject(i);
					noticeList.add(new Notice(oData));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return noticeList;
	}


	public String getOperatorType() {
		return operatorType;
	}


	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}


	public String getIsread() {
		return isread;
	}


	public void setIsread(String isread) {
		this.isread = isread;
	}
	
}
