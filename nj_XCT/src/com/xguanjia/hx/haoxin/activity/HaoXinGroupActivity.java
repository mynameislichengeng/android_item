package com.xguanjia.hx.haoxin.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.google.gson.reflect.TypeToken;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.HaoqeeProgressDialog;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.RefreshListView.IOnRefreshListener;
import com.xguanjia.hx.common.RefreshNewListView;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.filecabinet.activity.FileListActivity;
import com.xguanjia.hx.haoxin.adapter.HaoXinListAdapter;
import com.xguanjia.hx.haoxin.bean.HaoXinMsgBean;
import com.xguanjia.hx.haoxin.db.HaoXinGroupDb;
import com.xguanjia.hx.login.activity.TeLoginActivity;
import com.xguanjia.hx.notice.NoticeActivity;

public class HaoXinGroupActivity extends BaseActivity implements
		OnItemClickListener, IOnRefreshListener {
	private static final String TAG = "HaoxinGroupActivity";
	private TypeToken<List<HaoXinMsgBean>> targetType;
	private HashMap<String, Object> requestMap;
	private RefreshNewListView haoxinListView;
	private HaoXinListAdapter adapter;
	private HaoXinGroupDb db;
	private Handler handler;
	private Message msg;
	private View view;
	private String title = "";
	private SharedPreferences sp;

	private long exitTime = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		sp = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		Constants.userId = sp.getString("userID", "");
		if (null != savedInstanceState) {

			Constants.IMSI = savedInstanceState.getString("imsi");
			Constants.partyId = savedInstanceState.getString("partyId");
			Constants.SDK_VERSION = savedInstanceState.getString("SDK_VERSION");
			Constants.SYS_VERSION = savedInstanceState.getString("SYS_VERSION");
			Constants.IMEI = savedInstanceState.getString("imei");
			Constants.userId = savedInstanceState.getString("userId");
			Log.e(TAG, "onRestoreInstanceState+IntTest=" + Constants.userId);
		}
		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.ONE:
					List<HaoXinMsgBean> msgList = new ArrayList<HaoXinMsgBean>();
					String returnString = (String) msg.obj;
					if (!"[]".equals(returnString)) {
						msgList = getReqMsgDataInsert(returnString);
						if (msgList.size() > 0) {
							adapter.setDataChanged(msgList);
							if (msgList.size() != 0) {
								Editor editor = sp.edit();
								editor.putString(Constants.userId + "_time",
										msgList.get(0).getMessageCreateTime());
								editor.commit();
								requestMap.put(Constants.userId + "_time",
										msgList.get(0).getMessageCreateTime());
							} else {
								requestMap.put(Constants.userId + "_time", "");
							}
						}
					}

					break;

				default:
					break;
				}
				return false;
			}
		});
		initViews();
	}

	private void initViews() {
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		setTitleText("消息");
		view = getLayoutInflater().inflate(R.layout.haoxin_group, null);
		mainView.addView(view, param);
		db = new HaoXinGroupDb(this);
		haoxinListView = (RefreshNewListView) view.findViewById(R.id.haoxin_list);
		haoxinListView.setDivider(getResources().getDrawable(R.drawable.line));
		haoxinListView.setOnItemClickListener(this);
		sp = getSharedPreferences(Constants.userId + "_time",
				Context.MODE_PRIVATE);
	/*	haoxinListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						title = adapter.get_msgList().get(position - 1)
								.getMessageObjName();
						return false;
					}
				});*/
		adapter = new HaoXinListAdapter(queryLocalMsgData(), this);
		haoxinListView.setAdapter(adapter);
		registerForContextMenu(haoxinListView);
		haoxinListView.removeFootView();
		requestMap = new HashMap<String, Object>();
		requestMap.put("time", sp.getString(Constants.userId + "_time", ""));
		requestMap.put("userId", Constants.userId);
		// MAdaptorProgressDialog.show(HaoXinGroupActivity.this, "",
		// "数据请求中，请稍后", false, null);
		doActionRequest(requestMap);
		targetType = new TypeToken<List<HaoXinMsgBean>>() {
		};
		setTitleRightButton("更新", 0, buttonClickListener);
		setTitleLeftButtonSend("", R.drawable.send_selector,
				buttonClickListener);
	}

	/**
	 * 按钮点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_rightBtn:
				Constants.isClose = true;
				HaoqeeProgressDialog.show(HaoXinGroupActivity.this, "",
						"数据请求中，请稍后", false, null);
				doActionRequest(requestMap);
				break;
			default:
				break;
			}
		};
	};

	private void doActionRequest(HashMap<String, Object> requestMap) {
		try {
			ServerAdaptor
					.getInstance(this)
					.doAction(
							Constants.UrlHead
									+ "client.action.MessageBoxAction$getLastMessageBox",
							requestMap, new ServiceSyncListener() {
								@Override
								public void onSuccess(
										ActionResponse returnObject) {
									if (Constants.isClose) {
										HaoqeeProgressDialog.dismiss();
									}
									haoxinListView.onRefreshComplete();
									msg = handler.obtainMessage();
									msg.what = Constants.ONE;
									msg.obj = returnObject.getData().toString();
									handler.sendMessage(msg);
									Constants.isClose = false;// 对话框状态关闭
									super.onSuccess(returnObject);
								}

								@Override
								public void onError(ActionResponse returnObject) {
									super.onError(returnObject);
									HaoqeeProgressDialog.dismiss();
									Toast.makeText(HaoXinGroupActivity.this,
											(String) returnObject.getMessage(),
											Toast.LENGTH_SHORT).show();
								}
							});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void OnRefresh() {
		doActionRequest(requestMap);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		HaoXinMsgBean bean = adapter.get_msgList().get(position - 1);
		if ("folderGroupShare".equals(bean.getMessageAppType())
				|| "folderGroupUpdate".equals(bean.getMessageAppType())) {
			intent.setClass(HaoXinGroupActivity.this, FileListActivity.class);
			startActivity(intent);
		} else if ("announcement".equals(bean.getMessageAppType())) {
			intent.setClass(HaoXinGroupActivity.this, NoticeActivity.class);
			startActivity(intent);
		} else if ("messageBomb".equals(bean.getMessageAppType())) {
			// db.resetData();
			// adapter.setDataChanged(queryLocalMsgData());
		}
	}

/*	// 长按删除条目
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		menu.setHeaderTitle(title);
		inflater.inflate(R.menu.context_lv_menu, menu);
	}*/

	/*@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.delete:
			db.deleteMsg(adapter.get_msgList().get(info.position - 1));
			adapter.get_msgList().remove(info.position - 1);
			adapter.notifyDataSetChanged();
			return true;
		default:
			return super.onContextItemSelected(item);
		}
	}*/

	@Override
	protected void onResume() {
		adapter.setDataChanged(queryLocalMsgData());
		super.onResume();
	}

	public void setDataChange() {
		adapter.setDataChanged(queryLocalMsgData());
	}

	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	/**
	 * 查询本地消息
	 * 
	 * @return
	 */
	public List<HaoXinMsgBean> queryLocalMsgData() {
		HaoXinGroupDb db = new HaoXinGroupDb(HaoXinGroupActivity.this);
		List<HaoXinMsgBean> list = new ArrayList<HaoXinMsgBean>();
		list = db.queryMessgeOrderByTime();
		for (int i = 0; i < list.size(); i++) {
			if ("".equals(list.get(i).getMessageId())) {
				list.remove(i);
			}
		}
		return list;
	}

	/**
	 * 得到数据请求数据转成json 查询数据库数据
	 * 
	 * @param object
	 * @return
	 */
	public List<HaoXinMsgBean> getReqMsgDataInsert(Object object) {
		HaoXinGroupDb db = new HaoXinGroupDb(this);
		db.insertNewMsg(JsonUtil.fromJson((String) object, targetType));
		return queryLocalMsgData();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Log.i(TAG, "触发onKeyDown方法。。。。。。。。。。。。");
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
				return true;
			} else {
				Intent intent = new Intent(HaoXinGroupActivity.this,
						TeLoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Bundle bundle = new Bundle();
				bundle.putString("AppExit", "app_exit");
				intent.putExtras(bundle);
				startActivity(intent);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Log.i(TAG, "Activity被销毁");
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putString("userId", Constants.userId);
		outState.putString("imsi", Constants.IMSI);
		outState.putString("partyId", Constants.partyId);
		outState.putString("SDK_VERSION", Constants.SDK_VERSION);
		outState.putString("SYS_VERSION", Constants.SYS_VERSION);
		outState.putString("imei", Constants.IMEI);
		super.onSaveInstanceState(outState);
	}

}
