package com.xguanjia.hx.filecabinet.activity;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonService;
import com.xguanjia.hx.filecabinet.adaptor.FileListAdaptor;
import com.xguanjia.hx.filecabinet.db.FilecabinetDb;
import com.xguanjia.hx.filecabinet.pojo.FileBean;

public class FileListActivity extends BaseActivity implements
		OnCheckedChangeListener {
	private final String TAG = "FileListActivity";
	private ListView containerListView;
	private List<FileBean> fileList;
	private FileListAdaptor adaptor;
	private EditText searchEditView;
	private FilecabinetDb fileDb;
	private ProgressDialog pd;
	private PersonService service;
	private Handler handler;
	private FileBean currentFile;
	// 设置分享选中的索引
	private int shareSelectIndex = 0;
	// 当前页面数据的parentId，切换时查询出上次的界面
	private String bottom1ParentId = "", bottom2ParentId = "";
	// 搜索框视图ID
	public static final int CONTACT_QUERY_FIELD_VIEW_ID = 123;
	// 文件柜ListView视图ID
	public static final int CONTACT_LIST_VIEW_ID = 456;

	private String selectIndex = "0"; // 0个人文件夹，1公共文件夹,2分享文件柜

	private RadioGroup bottomRadioGroup;
	private LayoutParams params;
	private Intent intent;
	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");

		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {
				// 导入数据
				case Constants.ZERO:
					Bundle bundle = msg.getData();
					String jsonData = bundle.getString("respData");
					if (jsonData != null && "{}".equals(jsonData)) {
						Toast.makeText(FileListActivity.this, "暂无新联系人",
								Toast.LENGTH_SHORT).show();
					} else {
						TypeToken<List<PersonBean>> targetType = new TypeToken<List<PersonBean>>() {
						};
						List<PersonBean> personList = (List<PersonBean>) JsonUtil
								.fromJson(jsonData, targetType);
						service.savePerson(personList, 1, true);
						Intent intent = new Intent();
						intent.setClass(FileListActivity.this,
								ShareFileChooseOrgPersonActicity.class);
						intent.putExtra("fileId", currentFile.getFileId());
						startActivity(intent);
					}
					break;
				case 1:
					// 先把更新的数据插入数据库，然后再从数据库中查询出当前页面的数据
					fileDb.saveFileList(fileList, selectIndex);
					String nodeId = "";
					if ("0".equals(selectIndex)) {
						nodeId = bottom1ParentId;
					} else if ("1".equals(selectIndex)) {
						nodeId = bottom2ParentId;
					} else if ("2".equals(selectIndex)) {
						nodeId = "";
					}
					fileList = fileDb.getFileList(nodeId, 0, selectIndex);
					adaptor.setData(fileList);
					adaptor.notifyDataSetChanged();
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

		intent = new Intent();
		bottomView.removeAllViews();
		View fileBottomView = getLayoutInflater().inflate(
				R.layout.file_radiogroup_bottom, null);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		bottomView.addView(fileBottomView, params);
		bottomView.setVisibility(View.VISIBLE);
		bottomRadioGroup = (RadioGroup) bottomView
				.findViewById(R.id.file_bottom_radio_group);
		bottomRadioGroup.setOnCheckedChangeListener(this);
		params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		onCheckedChanged(bottomRadioGroup, R.id.mycabinet);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		// 我的文件柜
		case R.id.mycabinet:
			intent = new Intent(FileListActivity.this, MyCabinetActivity.class);
			view = getLocalActivityManager().startActivity("contact_organize",
					intent).getDecorView();
			break;
		// 公共文件柜
		case R.id.pubcabinet:
			intent = new Intent(FileListActivity.this,
					MyPubcabinetActivity.class);
			view = getLocalActivityManager().startActivity("contact_attention",
					intent).getDecorView();
			break;
		// 分享文件柜
		case R.id.sharecabinet:
			intent = new Intent(FileListActivity.this,
					MySharecabinetActivity.class);
			view = getLocalActivityManager().startActivity("contact_list",
					intent).getDecorView();
			break;
		default:
			break;
		}
		mainView.removeAllViews();
		mainView.addView(view, params);

	}

	/**
	 * 返回按钮事件
	 */
	private void onKeyBack() {
		// 树节点往上查询
		if (fileList != null && !fileList.isEmpty() && !"2".equals(selectIndex)) {
			fileList = fileDb.getFileList(fileList.get(0).getParentId(), 1,
					selectIndex);
			// 已经是最顶层父亲节点，直接关闭页面
			if ("".equals(fileList.get(0).getParentId())) {
				FileListActivity.this.finish();
			} else {
				fileList = fileDb.getFileList(fileList.get(0).getParentId(), 0,
						selectIndex);
				adaptor.setData(fileList);
				adaptor.notifyDataSetChanged();
			}
		} else {
			FileListActivity.this.finish();
		}
	}

	@Override
	public void onBackPressed() {
		onKeyBack();
	}

}
