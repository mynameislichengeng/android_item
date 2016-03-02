package com.xguanjia.hx.notice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout.LayoutParams;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.badges.ABadgeUtil;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.RefreshListView;
import com.xguanjia.hx.common.RefreshListView.IOnRefreshListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.login.activity.TeLoginActivity;
import com.xguanjia.hx.notice.db.NoticeDb;

/**
 * 通知通告界面
 * 
 */
public class NoticeActivity extends BaseActivity implements IOnRefreshListener, OnItemClickListener {
	private final String TAG = "NoticeActivity";
	private ProgressDialog pd;
	private View bottomView; // ListView底部按钮
	private ListView lv;
	private RefreshListView refreshListView; // 下拉刷新
	private Button morerecord;
	private ProgressBar ProgressBar;
	// SimpleAdapter adapter;
	String title;
	String titme;
	String name;
	private NoticeListAdaptor adapter;
	private List<Notice> noticeList; // 新闻列表数据
	int j = 1;
	DatabaseHelper dbHelper = null;
	Cursor cursor = null;
	private String timeStamp = "";
	private NoticeDb noticeDb;
	private ImageView iv_no;
	View v;

	/** Called when the activity is first created. */

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (msg.what == 1) {
				adapter = new NoticeListAdaptor(NoticeActivity.this, noticeList);
				refreshListView.setAdapter(adapter);

			}
			if (msg.what == 2) {
				adapter = new NoticeListAdaptor(NoticeActivity.this, noticeList);
				refreshListView.setAdapter(adapter);
			}
			if (noticeList.size() > 0) {
				iv_no.setVisibility(View.GONE);
				refreshListView.addFooterView(v);

			} else {

				iv_no.setVisibility(View.VISIBLE);

			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		noticeDb = new NoticeDb(NoticeActivity.this);
		this.setTitleText("通知公告");
		this.setTitleLeftButtonBack("", R.drawable.back_selector, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		View news_mainView = getLayoutInflater().inflate(R.layout.notice, null);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		this.mainView.addView(news_mainView, layoutParams);
		dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		noticeList = new ArrayList<Notice>();

		refreshListView = (RefreshListView) news_mainView.findViewById(R.id.notice_listview);
		refreshListView.setOnRefreshListener(this);
		refreshListView.setOnItemClickListener(this);

		v = getLayoutInflater().inflate(R.layout.a_jizhunxian, null);

		iv_no = (ImageView) findViewById(R.id.iv_no);

		bottomView = getLayoutInflater().inflate(R.layout.notice_bottomview, null);
		morerecord = (Button) bottomView.findViewById(R.id.morerecord);
		ProgressBar = (ProgressBar) bottomView.findViewById(R.id.ProgressBar);

		getNoticeList(time());

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UEProbAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

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

//	AdapterView.OnItemClickListener onItemClick = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//			Notice notice = noticeList.get(position);
//			if (null != notice) {
//
//				final SQLiteDatabase db = dbHelper.getReadableDatabase();
//				ContentValues values = new ContentValues();
//				values.put("bool", "1");
//				// 第一个参数是要更新的表名
//				// 第二个参数是一个ContentValues对象
//				// 第三个参数是where子句
//				db.update("tb_contact_notice", values, "id=? and userId=?", new String[] { notice.getId(), Constants.userId });
//
//				Intent intent = new Intent();
//				// 传递参数
//				intent.putExtra("title", notice.getTitle().trim());
//				intent.putExtra("time", notice.getUpdateTime());
//				intent.putExtra("name", notice.getReleaseName());
//
//				intent.putExtra("noticeId", notice.getId());
//				intent.setClass(NoticeActivity.this, NoticeIntroActivity.class);
//				startActivityForResult(intent, 5);
//			}
//		}
//	};

	/**
	 * 获取公告列表
	 * 
	 * @param newTimeNews
	 */

	private void getNoticeList(String lastAnnounceTime) {
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", Constants.userId);
		requestMap.put("lastAnnounceTime", lastAnnounceTime);
		requestMap.put("partyId", Constants.partyId);
		pd = ProgressDialog.show(this, "", "数据获取中", true, true);

		DatabaseHelper dbHelper = DatabaseHelper.getInstance(getApplicationContext());
		final SQLiteDatabase db = dbHelper.getWritableDatabase();

		try {
			ServerAdaptor.getInstance(this).doAction(1, Constants.UrlHead + "client.action.AnnounceAction$getAnnounceList", requestMap, new ServiceSyncListener() {

				@Override
				public void onError(ActionResponse returnObject) {
					super.onError(returnObject);
					// db.close();
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					if (returnObject.getCode() == 3) {
						dialog();

					} else {
						noticeList = (getNoticeDataFromDB());
						handler.sendEmptyMessage(1);
					}

				}

				@Override
				public void onSuccess(ActionResponse returnObject) {

					Object jsonData = returnObject.getData();
					Log.i("", "jsonData:" + jsonData.toString());
					if (jsonData instanceof JSONArray) {
						noticeList = Notice.jsonToList((JSONArray) jsonData);

						for (int i = 0; i < noticeList.size(); i++) {
							String key = UUID.randomUUID().toString().replace("-", "");
							db.execSQL("delete from tb_contact_notice where id = ?", new Object[] { noticeList.get(i).getId() });

						
							db.execSQL("insert into tb_contact_notice(primary_id,userId,id,top,topDate,title,startTime,releaseName,details,briefIntroduction,bool,author,sectionname,updateTime,isread) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[] { key, Constants.userId,
									noticeList.get(i).getId(), noticeList.get(i).getTop(), noticeList.get(i).getTopDate(), noticeList.get(i).getTitle(), noticeList.get(i).getStartTime(), noticeList.get(i).getReleaseName(), noticeList.get(i).getDetails(), noticeList.get(i).getBriefIntroduction(), "0",
									noticeList.get(i).getAuthor(), noticeList.get(i).getSectionName(), noticeList.get(i).getUpdateTime(), "0" });
						}
						noticeList.clear();
						noticeList = getNoticeDataFromDB();
					}

					handler.sendEmptyMessage(1);
					db.close();
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					refreshListView.onRefreshComplete();
				}
			});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取本地数据库中所有的任务
	 * 
	 * @return
	 */
	private List<Notice> getNoticeDataFromDB() {
		List<Notice> data = new ArrayList<Notice>();
		// 查询数据并添加到data中
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		StringBuilder sql = new StringBuilder("select * from tb_contact_notice where userId = ? order by top desc,topDate desc, updateTime desc");
		cursor = db.rawQuery(sql.toString(), new String[] { Constants.userId });
		while (cursor.moveToNext()) {
			Notice bean = new Notice();
			bean.setId(cursor.getString(cursor.getColumnIndex("id")));
			bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
			bean.setStartTime(cursor.getString(cursor.getColumnIndex("startTime")));
			bean.setReleaseName(cursor.getString(cursor.getColumnIndex("releaseName")));
			bean.setDetails(cursor.getString(cursor.getColumnIndex("details")));
			bean.setBriefIntroduction(cursor.getString(cursor.getColumnIndex("briefIntroduction")));
			bean.setBool(cursor.getString(cursor.getColumnIndex("bool")));
			bean.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
			bean.setSectionName(cursor.getString(cursor.getColumnIndex("sectionname")));
			bean.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
			bean.setIsread(cursor.getString(cursor.getColumnIndex("isread")));
			data.add(bean);
		}
		cursor.close();
		db.close();
		return data;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 5: {
			noticeList = (getNoticeDataFromDB());
			adapter.notifyDataSetChanged();
			handler.sendEmptyMessage(1);
		}
			break;
		default:
			break;
		}
	}

	/**
	 * 会话过期返回登录界面
	 * 
	 * @param context
	 */
	public void dialog() {
		AlertDialog.Builder builder = new Builder(NoticeActivity.this);
		builder.setMessage("会话已过期请重新登录");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行登录操作
				Intent intent = new Intent();
				intent.setClass(NoticeActivity.this, TeLoginActivity.class);
				intent.putExtra("sessionOverdue", true);
				startActivity(intent);
			}
		});
		builder.create().show();
	}

	@Override
	public void OnRefresh() {
		noticeList.clear();
		getNoticeList(time());
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		Notice notice = noticeList.get(arg2 - 1);
		if (null != notice) {
			final SQLiteDatabase db = dbHelper.getReadableDatabase();
			ContentValues values = new ContentValues();
			values.put("bool", "1");
			// 第一个参数是要更新的表名
			// 第二个参数是一个ContentValues对象
			// 第三个参数是where子句
			db.update("tb_contact_notice", values, "id=? and userId=?", new String[] { notice.getId(), Constants.userId });
			// 更新未读状态
			noticeDb.updataNoticeState(notice.getId());
			notice.setIsread("1");
			// 获取未读数目
			if (sp.getString("userName_yanshi", "").equals("13911111122")) {
				ABadgeUtil.setBadge(NoticeActivity.this, 0);

			} else {
				Constants.unnoticNum = noticeDb.getUnnoticeNum();
				ABadgeUtil.setBadge(this, Constants.unPayrollNum + Constants.unnoticNum);
			}
			
			

			Intent intent = new Intent();
			// 传递参数
			intent.putExtra("title", notice.getTitle().trim());
			intent.putExtra("time", notice.getUpdateTime());
			intent.putExtra("name", notice.getReleaseName());

			intent.putExtra("noticeId", notice.getId());
			intent.setClass(NoticeActivity.this, NoticeIntroActivity.class);
			startActivityForResult(intent, 5);
		}
		// 更新本地未读状态
		noticeDb.updataNoticeState(notice.getId());
		notice.setIsread("1");
		Constants.unnoticNum = noticeDb.getUnnoticeNum();
		// ABadgeUtil.setBadge(getActivity(), uncount);
	}

}
