package com.xguanjia.hx.notice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.attachment.bean.AttachmentBean;
import com.xguanjia.hx.attachment.json.AttachmentJsonUtil;
import com.xguanjia.hx.attachment.service.AttachmentBeanService;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AsyncDownloadFile1;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;

public class NoticeIntroActivity extends BaseActivity {

	private ProgressDialog pd;
	private final String TAG = "NoticeIntroActivity";
	private NoticeDetail noticedetail;
	private Notice notice;
	private Handler handler;

	private TextView noticeIntroTitle;
	private TextView noticeIntroTime;
	private TextView noticeIntroreleasename;
	// 是否推送过来的信息
	private boolean isFromPush = false;
	private String noticeId;
	private String errorMsg = "";
	private File file = null;
	private ImageView image = null;
	private LinearLayout detailsView;
	private List<AttachmentBean> attachmentList;
	private AttrDetailExpandableListAdapter adapter;
	String title = "";
	String time = "";
	String name = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sp = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		Constants.userId = sp.getString("userID", "");
		this.setTitleText("公告详情");
		// this.setBottomBackButton("返回", null);
		this.setTitleLeftButtonBack("", R.drawable.back_selector,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						finish();
					}
				});
		View news_mainView = getLayoutInflater().inflate(R.layout.notice_intro,
				null);
		this.mainView.addView(news_mainView);
		noticedetail = new NoticeDetail();
		noticeIntroTitle = (TextView) findViewById(R.id.notice_intro_title);
		noticeIntroTime = (TextView) findViewById(R.id.notice_intro_time);
		noticeIntroreleasename = (TextView) findViewById(R.id.notice_intro_releasename);
		detailsView = (LinearLayout) findViewById(R.id.notice_text);
		title = getIntent().getStringExtra("title");
		time = getIntent().getStringExtra("time");
		name = getIntent().getStringExtra("name");
		noticeIntroTime.setText(time);
		noticeIntroreleasename.setText(name);

		noticeIntroTitle.setText(title);
		handler = new Handler() {

			public void handleMessage(Message msg) {
				if (msg.what == -1) {
					MAMessage.ShowMessage(NoticeIntroActivity.this,
							R.string.download_file_faile, errorMsg);
				}
				if (msg.what == 1) {
					updateUI();
				}
				// if(msg.what == 2){
				//
				// }

				// 更新公告中的图片
				if (msg.what > 99) {
					try {
						ImageView image = (ImageView) detailsView
								.findViewById(msg.what);
						if (null != image && null != file) {
							Drawable drawable = Drawable.createFromPath(file
									.getPath());
							image.setImageDrawable(drawable);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		};

		// 接受参数执行相应的工作
		Intent intent = getIntent();
		if (intent.hasExtra(Constants.NOTIFICATION_URI)) {
			isFromPush = true;
			noticeId = intent.getStringExtra(Constants.NOTIFICATION_URI);
		} else {
			noticeId = intent.getStringExtra("noticeId");
		}
		if (null != noticeId && !"".equals(noticeId)) {
			getNoticeIntro(noticeId);
		}
		this.setResult(5, null);
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

	private void updateUI() {
		if (null != noticedetail) {
			// if (noticedetail.getTitle() != null) {
			// noticeIntroTitle.setText(noticedetail.getTitle());
			// // TextPaint paint = noticeIntroTitle.getPaint();
			// // paint.setFakeBoldText(true);
			// }
			if (noticedetail.getStartDate() != null) {
				noticeIntroTime.setText(noticedetail.getStartDate());
			}
			if (noticedetail.getReleaseName() != null) {
				noticeIntroreleasename.setText(noticedetail.getReleaseName());
			}
			if (noticedetail.getDetails() != null) {

				JSONArray ary;
				String[] aryDetails = null;
				try {
					ary = new JSONArray(noticedetail.getDetails());
					int len = ary.length();
					aryDetails = new String[len];
					for (int i = 0; i < len; i++) {
						Object obj = ary.get(i);
						String str = String.valueOf(obj);
						aryDetails[i] = str;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for (int i = 0; i < aryDetails.length; i++) {
					String str = aryDetails[i];
					if (null == str) {
						continue;
					}
					if (str.contains(".jpg") || str.contains(".png")) {
						// 图片处理 1、创建imageView并设置id,下载图片并给imageView设置
						image = new ImageView(this);
						int id = i + 100;
						image.setId(id);
						image.setScaleType(ImageView.ScaleType.FIT_CENTER);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
								LinearLayout.LayoutParams.FILL_PARENT,
								LinearLayout.LayoutParams.WRAP_CONTENT,
								Gravity.CENTER);
						detailsView.addView(image, params);
						getImg(str, id);
					} else {
						// 文字处理,
						TextView textview = new TextView(this);
						textview.setId(i + 100);
						textview.setText(str);
						textview.setTextColor(0xFF303030);
						textview.setTextSize(18);
						textview.setPadding(20, 5, 20, 5);
						textview.setLineSpacing(1, (float) 1.2);
						detailsView.addView(textview);
					}
				}

				if (attachmentList != null && !attachmentList.isEmpty()) {
					ExpandableListView attachmentListView = (ExpandableListView) this
							.findViewById(R.id.achmentListView);
					adapter = new AttrDetailExpandableListAdapter(this,attachmentList);
					attachmentListView.setAdapter(adapter);
					attachmentListView.expandGroup(0);
					attachmentListView.setOnChildClickListener(onChildClickListener);
//					attachmentListView.setAdapter(new AttachmentListAdapter(
//							this, attachmentList));
				}
			}
		}

	}

	/**
	 * 获取公告详细列表
	 * 
	 * @param newTimeNews
	 */
	private void getNoticeIntro(final String id) {
		// 根据id先从明细表中查询是否有该记录，有则直接查询，若没有则去后台请求
		DatabaseHelper dbHelper = DatabaseHelper
				.getInstance(getApplicationContext());
		final SQLiteDatabase db = dbHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder(
				"select * from tb_contact_notice_detail where id = ?");
		Cursor cursor = db.rawQuery(sql.toString(), new String[] { id });
		if (cursor.moveToFirst()) {
			noticedetail.setTitle(cursor.getString(cursor
					.getColumnIndex("title")));
			noticedetail.setStartDate(cursor.getString(cursor
					.getColumnIndex("startDate")));
			noticedetail.setDetails(cursor.getString(cursor
					.getColumnIndex("details")));
			noticedetail.setReleaseName(cursor.getString(cursor
					.getColumnIndex("releaseName")));
			noticedetail.setAttachment(cursor.getString(cursor
					.getColumnIndex("attachment")));
			AttachmentBeanService dao = new AttachmentBeanService(this);
			String str = noticedetail.getAttachment();
			if ((str != null) && (str.trim().length() > 0)) {
				attachmentList = dao.queryAttachmentBean(str);
			}
			db.close();
			handler.sendEmptyMessage(1);
		} else {
			HashMap<String, Object> requestMap = new HashMap<String, Object>();
			requestMap.put("userId", Constants.userId);
			requestMap.put("announceId", id);
			pd = ProgressDialog.show(this, "", "数据获取中", true, true);

			try {
				ServerAdaptor
						.getInstance(this)
						.doAction(
								1,
								Constants.UrlHead
										+ "client.action.AnnounceAction$getAnnounceContent",
								requestMap, new ServiceSyncListener() {

									@Override
									public void onError(
											ActionResponse returnObject) {
										super.onError(returnObject);
										// handler.sendEmptyMessage(2);
										db.close();
										if (pd != null && pd.isShowing()) {
											pd.dismiss();
										}
									}

									@Override
									public void onSuccess(
											ActionResponse returnObject) {

										Object jsonData = returnObject
												.getData();
										Log.i(TAG,
												"jsonData:"
														+ jsonData.toString());
										String attachIds = "";
										if (null != jsonData
												&& jsonData instanceof JSONObject) {
											noticedetail = new NoticeDetail(
													(JSONObject) jsonData);

											JSONObject json = (JSONObject) jsonData;
											try {
												if (json.has("attachments")) {
													JSONArray ary = json
															.getJSONArray("attachments");
													List<AttachmentBean> list = AttachmentJsonUtil
															.getAttachList(ary);
													AttachmentBeanService attachmentBeanService = new AttachmentBeanService(
															NoticeIntroActivity.this);
													attachmentList = list;
													attachIds = attachmentBeanService
															.saveAttachmentList(list);
												}
											} catch (Exception e) {
												Log.e(TAG, e.getMessage(), e);
											}

											String key = UUID.randomUUID()
													.toString()
													.replace("-", "");
											try {
												DatabaseHelper dbHelper = DatabaseHelper
														.getInstance(getApplicationContext());
												SQLiteDatabase db = dbHelper.getWritableDatabase();
												db.execSQL(
														"insert into tb_contact_notice_detail (primary_id,id, title , startDate, details, releaseName, attachment) values(?,?,?,?,?,?,?)",
														new Object[] {
																key,
																id,
																noticedetail
																		.getTitle(),
																noticedetail
																		.getStartDate(),
																noticedetail
																		.getDetails(),
																noticedetail
																		.getReleaseName(),
																attachIds });
											} catch (Exception e) {
												// TODO: handle exception
												e.printStackTrace();
												Log.e(TAG, "公共详情存入数据库异常。。。。");
											}

											if (isFromPush) {
												key = UUID.randomUUID()
														.toString()
														.replace("-", "");
												db.execSQL(
														"insert into tb_contact_notice(primary_id,userId,id,top,topDate,title,startTime,releaseName,details,briefIntroduction,bool) values(?,?,?,?,?,?,?,?,?,?,?)",
														new Object[] {
																key,
																Constants.userId,
																id,
																"",
																"",
																noticedetail
																		.getTitle(),
																getNowDate(),
																noticedetail
																		.getReleaseName(),
																noticedetail
																		.getDetails(),
																noticedetail
																		.getDetails(),
																"0" });
											}

											db.close();
											handler.sendEmptyMessage(1);
										}
										if (pd != null && pd.isShowing()) {
											pd.dismiss();
										}
									}

								});
			} catch (HaoqeeException e) {
				e.printStackTrace();
			}
		}
	}

	public void getImg(String httpimg, final int id) {

		if (null == this.getSDcard()) {
			MAMessage.ShowMessage(this, R.string.prompt_message,
					R.string.please_check_sd);
			return;
		}

		if (null == httpimg || "".equals(httpimg)) {
			return;
		}
		String[] arrayimg = httpimg.split("/");
		String httpimgfile = arrayimg[arrayimg.length - 1];
		if (null == httpimgfile || "".equals(httpimgfile)) {
			return;
		}

		String filePath = this.getSDcard().toString() + "/moa/news/"
				+ httpimgfile;
		AsyncDownloadFile1 download = new AsyncDownloadFile1(this);
		download.setFileUrl(httpimg);
		download.setFilePath(filePath);
		download.setListener(new ServiceSyncListener() {

			@Override
			public void onError(ActionResponse returnObject) {
				Log.i(TAG, "error" + returnObject.getMessage());
				// errorMsg = returnObject.getMessage() == null ? "" :
				// returnObject.getMessage();
				// handler.sendEmptyMessage(-1);
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

			@Override
			public void onSuccess(ActionResponse returnObject) {
				Log.i(TAG, "success" + returnObject.getMessage());
				if (null == returnObject.getData()) {
					errorMsg = returnObject.getMessage() == null ? ""
							: returnObject.getMessage();
					handler.sendEmptyMessage(-1);
				}
				file = (File) returnObject.getData();
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
				handler.sendEmptyMessage(id);
			}
		});
		download.execute("");
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	private String getNowDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date());
	}
	
	
	OnChildClickListener onChildClickListener = new OnChildClickListener() {

		@Override
		public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
			AttachmentBean bean = adapter.getData().get(childPosition);
			File file = new File(new File(Constants.SAVE_IMAGE_PATH), bean.getFileName());
			if (!file.exists()) {
				// 如果文件不存在，则从服务端下载
				downloadFile(bean);
			} else {
				try {
					
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(file), bean.getIntentType());
						NoticeIntroActivity.this.startActivity(intent);
					
				} catch (Exception e) {
					Log.e(TAG, "查看信息详情异常：" + e.getMessage());
					MAMessage.ShowMessage(NoticeIntroActivity.this, "提示信息", "文件打开失败，缺少工具软件，请先安装指定办公软件");
				}

			}
			return false;
		}
	};
	
	
	/**
	 * 下载文件，下载成功后调用系统工具打开文件
	 * 
	 * @param fileBean
	 */
	public void downloadFile(final AttachmentBean fileBean) {
		if (null == ((BaseActivity) NoticeIntroActivity.this).getSDcard()) {
			MAMessage.ShowMessage(NoticeIntroActivity.this, R.string.prompt_message, R.string.please_check_sd);
			return;
		}

		pd = ProgressDialog.show(NoticeIntroActivity.this, "", "数据获取中", true, true);
		String filePath = Constants.SAVE_IMAGE_PATH + fileBean.getFileName();

		AsyncDownloadFile1 download = new AsyncDownloadFile1(NoticeIntroActivity.this);
		download.setFileUrl(fileBean.getServerPath());
		download.setFilePath(filePath);
		download.setListener(new ServiceSyncListener() {

			@Override
			public void onError(ActionResponse returnObject) {
				Log.i(TAG, "error" + returnObject.getMessage());
				MAMessage.ShowMessage(NoticeIntroActivity.this, R.string.download_file_faile, returnObject.getMessage());
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

			@Override
			public void onSuccess(ActionResponse returnObject) {
				Log.i(TAG, "success" + returnObject.getMessage());
				if (null == returnObject.getData()) {
					if (((Activity) NoticeIntroActivity.this).isTaskRoot()) {
						MAMessage.ShowMessage(NoticeIntroActivity.this, R.string.download_file_faile, returnObject.getMessage());
					}
				}
				File file = (File) returnObject.getData();
				try {
					String intentType = fileBean.getFileType();
					if (null == intentType || "".equals(intentType)) {
						if (((Activity) NoticeIntroActivity.this).isTaskRoot()) {
							MAMessage.ShowMessage(NoticeIntroActivity.this, R.string.download_file_faile, "不支持的格式");
						}
						return;
					}
						Intent intent = new Intent();
						intent.setAction(Intent.ACTION_VIEW);
						intent.setDataAndType(Uri.fromFile(file), fileBean.getIntentType());
						NoticeIntroActivity.this.startActivity(intent);
				
				} catch (Exception e) {
					MAMessage.ShowMessage(NoticeIntroActivity.this, "提示信息", "文件打开失败，缺少工具软件，请先安装指定办公软件");
				}
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

		});
		download.execute("");
	}

}
