package com.xguanjia.hx.filecabinet.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout.LayoutParams;
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
import com.xguanjia.hx.filecabinet.adaptor.SelectedUserID;
import com.xguanjia.hx.filecabinet.views.InternalPersonListView;

/**
 * 选择人员分享 按机构层级进行选择
 * 
 * @author 杨通杆
 * @date 2012-08-31
 */
public class ShareFileChooseOrgPersonActicity extends BaseActivity {
	private static final String TAG = "ShareFileChooseOrgPersonActicity";
	// 机构堆栈
	private Stack<InternalPersonListView> internalPersonStack;
	private InternalPersonListView internalPersonLv;
	private List<PersonBean> personList;
	private int personType = Constants.ZERO;
	private PersonService personService;
	private ProgressDialog pd;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		personService = new PersonService(this);
		intent = this.getIntent();
		personList = new ArrayList<PersonBean>();
		internalPersonLv = new InternalPersonListView(this, personList);
		internalPersonStack = new Stack<InternalPersonListView>();
		initDataFromLocal(false, false);
		InternalPersonListView view = internalPersonStack.peek();
		refreshInternalView(view);
		// 设置返回按钮
		setTitleLeftButton("退出", R.drawable.title_leftbutton_selector, returnButtonClickListener);
		setTitleRightButton("分享", R.drawable.title_rightbutton_selector, returnButtonClickListener);

	}

	/**
	 * 返回按钮点击事件
	 */
	MOAOnClickListener returnButtonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_leftBtn:
				back();
				break;
			case R.id.title_rightBtn:
				// 分享事件，调用接口
			case R.id.shareFileBtn:
				StringBuilder sb = new StringBuilder();
				if (SelectedUserID.getUserIds().isEmpty()) {
					Toast.makeText(ShareFileChooseOrgPersonActicity.this, "请选择需要分享的人", Toast.LENGTH_SHORT).show();
				} else {
					Iterator<String> ids = SelectedUserID.getUserIds().iterator();
					while (ids.hasNext()) {
						sb.append(ids.next() + ",");
					}
					HashMap<String, Object> requestMap = new HashMap<String, Object>();
					requestMap.put("userId", Constants.userId);
					requestMap.put("fileId", intent.getStringExtra("fileId"));
					requestMap.put("shareUserIds", sb.toString());
					String method = Constants.UrlHead+"client.action.FileCabinetAction$shareFile";
					pd = ProgressDialog.show(ShareFileChooseOrgPersonActicity.this, "", "文件分享中");
					doAsyncJsonAction(method, requestMap, "文件分享中");
				}
				break;
			default:
				break;
			}
		}

	};

	private void initDataFromLocal(boolean isContactPersonStack, boolean isAllPerson) {
		if (isContactPersonStack) {
			Log.i(TAG, "isContactPersonStack is true");
		} else {
			personList = personService.queryPersonByType(personType, "0", "", 4, true);
			if (personList != null && !personList.isEmpty()) {
				internalPersonLv = new InternalPersonListView(ShareFileChooseOrgPersonActicity.this, personList);
				internalPersonLv.setOnItemClickListener(internalPersonListClickListener);
				refreshInternalView(internalPersonLv);
			} else {
				mainView.removeAllViews();
				setTitleText("组织机构");
				Toast.makeText(ShareFileChooseOrgPersonActicity.this, "暂无成员", Toast.LENGTH_SHORT).show();
			}
			internalPersonStack.push(internalPersonLv);
		}
	}

	/**
	 * 刷新当前页面
	 */
	private void refreshInternalView(View view) {
		mainView.removeAllViews();
		LayoutParams layoutParam = new LayoutParams(android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT);
		mainView.addView(view, layoutParam);
	}

	/**
	 * 返回事件处理，移除堆栈
	 */
	private void back() {
		if (!internalPersonStack.empty()) {
			InternalPersonListView view = internalPersonStack.pop();
			if (!internalPersonStack.empty()) {
				view = internalPersonStack.peek();
				personList = view.getPersonList();
				refreshInternalView(view);
			} else {
				this.finish();
				SelectedUserID.map = new HashMap<String, String>();
			}
		} else {
			this.finish();
			SelectedUserID.map = new HashMap<String, String>();
		}
	}

	// 组织机构listView点击事件
	OnItemClickListener internalPersonListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			PersonBean bean = null;
			// 0代表组织机构
			if (personType == 0) {
				bean = personList.get(position);
			} else {
				bean = personList.get(position - 1);
			}
			if ("".equals(bean.getGroupId())) {
				CheckBox cb = (CheckBox) view.findViewById(R.id.cb_selected_person);
				if (cb.isChecked()) {
					cb.setChecked(false);
					SelectedUserID.unSelectedUser(bean.getUserId());
				} else {
					cb.setChecked(true);
					SelectedUserID.selectedUser(bean.getUserId());
				}
			} else {
				List<PersonBean> personDataList = personService.queryPersonByType(personType, bean.getGroupId(), "", 4, false);
				if (personDataList != null && !personDataList.isEmpty()) {
					internalPersonLv = new InternalPersonListView(ShareFileChooseOrgPersonActicity.this, personDataList);
					personList = personDataList;
					refreshInternalView(internalPersonLv);
					if (!internalPersonStack.contains(internalPersonLv)) {
						internalPersonStack.push(internalPersonLv);
					}
					internalPersonLv.setOnItemClickListener(internalPersonListClickListener);
				} else {
					Toast.makeText(ShareFileChooseOrgPersonActicity.this, "暂无联系人", Toast.LENGTH_SHORT).show();
				}
			}
		}
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
					Toast.makeText(ShareFileChooseOrgPersonActicity.this, "分享成功", Toast.LENGTH_SHORT).show();
					setResult(6);
					SelectedUserID.map = new HashMap<String, String>();
					ShareFileChooseOrgPersonActicity.this.finish();
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
