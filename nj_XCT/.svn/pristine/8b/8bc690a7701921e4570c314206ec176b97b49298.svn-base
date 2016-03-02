package com.xguanjia.hx.filecabinet.activity;

import java.util.HashMap;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonService;
import com.xguanjia.hx.filecabinet.adaptor.ShareFilePersonListAdaptor;

/**
 * 选择人员分享
 * 
 * @author dolphin
 * 
 */
public class ShareFileChoosePersonActicity extends BaseActivity {
	private static String TAG = "ShareFileChoosePersonActicity";
	private Button titleLeftBtn, shareBtn, choosePersonBtn;
	private ShareFilePersonListAdaptor adapter;
	private ListView shareFilePersonLv;
	private PersonService personService;
	private List<PersonBean> personList;
	private ProgressDialog pd;
	private Handler handler;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.file_share_list);
		Log.d(TAG, "in onCreate method");
		initViews();
		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {
				switch (msg.what) {
				case Constants.ZERO:
					break;

				default:
					break;
				}
				return false;
			}
		});
	}

	/**
	 * 界面初始化
	 */
	private void initViews() {
		intent = this.getIntent();
		titleLeftBtn = (Button) this.findViewById(R.id.title_leftBtn);
		titleLeftBtn.setText("取消");
		titleLeftBtn.setOnClickListener(buttonClickListener);
		shareBtn = (Button) this.findViewById(R.id.shareFileBtn);
		shareBtn.setText("分享");
		shareBtn.setOnClickListener(buttonClickListener);
		choosePersonBtn = (Button) this.findViewById(R.id.choosePersonBtn);
		choosePersonBtn.setText("全选");
		choosePersonBtn.setOnClickListener(buttonClickListener);
		shareFilePersonLv = (ListView) this.findViewById(R.id.shareFilePersonLv);
		personService = new PersonService(this);
		personList = personService.queryInternalPerson();
		adapter = new ShareFilePersonListAdaptor(this, personList);
		shareFilePersonLv.setAdapter(adapter);
		shareFilePersonLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				boolean isShared = personList.get(position).isShareChecked();
				personList.get(position).setShareChecked(isShared == true ? false : true);
				adapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 按钮点击事件
	 */
	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			// 返回事件
			case R.id.title_leftBtn:
				ShareFileChoosePersonActicity.this.finish();
				break;
			// 分享事件，调用接口
			case R.id.shareFileBtn:
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < personList.size(); i++) {
					PersonBean bean = personList.get(i);
					if (bean.isShareChecked()) {
						sb.append(bean.getUserId() + ",");
					}
				}
				HashMap<String, Object> requestMap = new HashMap<String, Object>();
				requestMap.put("userId", Constants.userId);
				requestMap.put("fileId", intent.getStringExtra("fileId"));
				requestMap.put("shareUserIds", sb.toString());
				String method = Constants.UrlHead+"client.action.FileCabinetAction$shareFile";
				pd = ProgressDialog.show(ShareFileChoosePersonActicity.this, "", "文件分享中");
				doAsyncJsonAction(method, requestMap, "文件分享中");
				break;
			// 全选事件
			case R.id.choosePersonBtn:
				if ("全选".equals(choosePersonBtn.getText())) {
					for (int i = 0; i < personList.size(); i++) {
						PersonBean bean = personList.get(i);
						bean.setShareChecked(true);
					}
					choosePersonBtn.setText("全不选");
				} else {
					for (int i = 0; i < personList.size(); i++) {
						PersonBean bean = personList.get(i);
						bean.setShareChecked(false);
					}
					choosePersonBtn.setText("全选");
				}
				adapter.notifyDataSetChanged();
				break;
			}
		};
	};

	/***
	 * 后台交互
	 * 
	 * @param method
	 *            方法名称
	 * @param map
	 *            请求参数
	 */
	private void doAsyncJsonAction(String method, HashMap<String, Object> map, String showDialogMsg) {
		try {
			ServerAdaptor.getInstance(this).doAction(1, method, map, new ServiceSyncListener() {

				@Override
				public void onSuccess(ActionResponse returnObject) {
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
					Toast.makeText(ShareFileChoosePersonActicity.this, "分享成功", Toast.LENGTH_SHORT).show();
					ShareFileChoosePersonActicity.this.finish();
				}

				@Override
				public void onError(ActionResponse returnObject) {
					Log.i(TAG, "error" + returnObject.getMessage());
					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
