package com.xguanjia.hx.payroll.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.notice.Notice;
import com.xguanjia.hx.payroll.bean.PayRoll;
import com.xguanjia.hx.payroll.bean.PayRollList;
import com.xguanjia.hx.payroll.db.PayRollDb;

public class VP_jiekou {
	private Context mContext;
	private PayRollDb rollDb;
	private int index = 0;

	public VP_jiekou(Context mContext) {
		super();
		this.mContext = mContext;
		init();
	}
	void init(){
		this.dbHelper = DatabaseHelper.getInstance(this.mContext);
		this.rollDb = new PayRollDb(this.mContext);
		
	}

	/**
	 * 请求收入的
	 */
	public void doAsyncJsonAction() {
		try {

			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("userPhone", Constants.mobile);
			requestMap.put("updateTime", rollDb.time(Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingId()));
			requestMap.put("salaryUseTypeId", "");
			requestMap.put("useGroupingId", Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingId());
			requestMap.put("salaryUseTypeName", Constants.payRollTypeGroups.getSalaryUseTypes().get(index).getUseGroupingName());
			requestMap.put("partyId", Constants.partyId);

			ServerAdaptor.getInstance(mContext).doAction(1, Constants.UrlHead + "client.action.SalaryAction$getSalaryList", requestMap, new ServiceSyncListener() {

				public void onSuccess(ActionResponse returnObject) {

					Gson gson = new Gson();
					try {
						PayRollList payRollBeanListList = gson.fromJson(returnObject.getData().toString(), new TypeToken<PayRollList>() {
						}.getType());
						List<PayRoll> payRollBeanLists = payRollBeanListList.getList();
						for (int i = 0; i < payRollBeanLists.size(); i++) {
							if (rollDb.getPayRollListForId(payRollBeanLists.get(i).getPayRollId()).size() == 0) {
								rollDb.savePayRoll(payRollBeanLists.get(i));
							}
						}
						if (index != Constants.payRollTypeGroups.getSalaryUseTypes().size() - 1) {
							index++;
							doAsyncJsonAction();
						}

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}

				public void onError(ActionResponse returnObject) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DatabaseHelper dbHelper;
	private String timeStamp = "";

	// 获取最大时间戳
	public String time() {
		final SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select max(updateTime) from tb_contact_notice where userId = ?", new String[] { Constants.userId });
		cursor.moveToFirst();
		int count = cursor.getColumnCount();
		if (count > -1) {
			timeStamp = cursor.getString(0);
			cursor.close();
			db.close();
		} else {
			timeStamp = "";
			cursor.close();
			db.close();
		}
		return timeStamp;
	}

	private List<Notice> noticeList = new ArrayList<Notice>();; // 新闻列表数据

	/**
	 * 获取公告列表
	 * 
	 * @param newTimeNews
	 */

	public void getNoticeList() {

		String lastAnnounceTime = time();
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", Constants.userId);
		requestMap.put("lastAnnounceTime", lastAnnounceTime);
		requestMap.put("partyId", Constants.partyId);

		final SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			ServerAdaptor.getInstance(mContext).doAction(1, Constants.UrlHead + "client.action.AnnounceAction$getAnnounceList", requestMap, new ServiceSyncListener() {

				@Override
				public void onError(ActionResponse returnObject) {
					super.onError(returnObject);

				}

				@Override
				public void onSuccess(ActionResponse returnObject) {

					Object jsonData = returnObject.getData();
					Log.i("", "jsonData:" + jsonData.toString());
					if (jsonData instanceof JSONArray) {
						noticeList = Notice.jsonToList((JSONArray) jsonData);

						for (int i = 0; i < noticeList.size(); i++) {
							final SQLiteDatabase db = dbHelper.getWritableDatabase();
							String key = UUID.randomUUID().toString().replace("-", "");
							db.execSQL("delete from tb_contact_notice where id = ?", new Object[] { noticeList.get(i).getId() });

							db.execSQL("insert into tb_contact_notice(primary_id,userId,id,top,topDate,title,startTime,releaseName,details,briefIntroduction,bool,author,sectionname,updateTime,isread) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { key, Constants.userId,
									noticeList.get(i).getId(), noticeList.get(i).getTop(), noticeList.get(i).getTopDate(), noticeList.get(i).getTitle(), noticeList.get(i).getStartTime(), noticeList.get(i).getReleaseName(), noticeList.get(i).getDetails(),
									noticeList.get(i).getBriefIntroduction(), "0", noticeList.get(i).getAuthor(), noticeList.get(i).getSectionName(), noticeList.get(i).getUpdateTime(), "0" });
						}

					}

				}
			});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}

	}

}
