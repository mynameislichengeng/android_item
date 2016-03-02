package com.xguanjia.hx.filecabinet.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.FileCacheManager;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonService;
import com.xguanjia.hx.filecabinet.adaptor.FileListAdaptor;
import com.xguanjia.hx.filecabinet.db.FilecabinetDb;
import com.xguanjia.hx.filecabinet.pojo.FileBean;
import com.xguanjia.hx.filecabinet.views.FileListView;

public class MyPubcabinetActivity extends BaseActivity {

	private final String TAG = "MyPubcabinetActivity";

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

	private String selectIndex = "1"; // 0个人文件夹，1公共文件夹,2分享文件柜

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
						Toast.makeText(MyPubcabinetActivity.this, "暂无新联系人",
								Toast.LENGTH_SHORT).show();
					} else {
						TypeToken<List<PersonBean>> targetType = new TypeToken<List<PersonBean>>() {
						};
						List<PersonBean> personList = (List<PersonBean>) JsonUtil
								.fromJson(jsonData, targetType);
						service.savePerson(personList, 1, true);
						Intent intent = new Intent();
						intent.setClass(MyPubcabinetActivity.this,
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
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		setTitleRightButton("更新", 0, buttonClickListener);
		setTitleText("公共文件柜");

		LayoutParams layoutParams = new LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT,
				android.view.ViewGroup.LayoutParams.FILL_PARENT);

		// 加入搜索框
		View queryFieldView = getLayoutInflater().inflate(
				R.layout.contact_query_fields, null);
		searchEditView = (EditText) queryFieldView
				.findViewById(R.id.contact_edit);
		layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		searchEditView.setPadding(50, 8, searchEditView.getWidth() - 20,
				searchEditView.getHeight());

		searchEditView.setHint("搜索");
		searchEditView.setLayoutParams(layoutParams);
		searchEditView.addTextChangedListener(queryContactWatcher);
		mainView.addView(queryFieldView);
		// 搜索事件
		// searchEditView.addTextChangedListener(queryContactWatcher);
		fileDb = new FilecabinetDb(this);
		containerListView = new ListView(this);
		containerListView.setId(CONTACT_LIST_VIEW_ID);
		containerListView.setDivider(getResources()
				.getDrawable(R.drawable.line));
		containerListView.setCacheColorHint(Color.alpha(00000000));
		containerListView.setOnItemClickListener(clickListener);

		service = new PersonService(MyPubcabinetActivity.this);
		fileList = fileDb.getFileList("", 0, selectIndex);
		adaptor = new FileListAdaptor(this, fileList);
		containerListView.setAdapter(adaptor);
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		layoutParams.addRule(RelativeLayout.BELOW, queryFieldView.getId());
		mainView.addView(containerListView, layoutParams);
		// 在底部加入分割线
		// LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
		// LayoutParams.FILL_PARENT);
		// params.topMargin = 10;
		// params.bottomMargin = 5;
		// params.addRule(RelativeLayout.BELOW, containerListView.getId());
		// ImageView imgView = getImgViewDivider();
		// imgView.setVisibility(View.GONE);
		// mainView.addView(imgView, params);
	}

	/**
	 * listView点击事件
	 */
	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			FileBean bean = fileList.get(position);
			// 如果是文件则下载打开
			String intentType = bean.getIntentType();
			if ("false".equals(fileList.get(position).getIsDirectory())) {
				// 存在该文件直接打开
				if (FileCacheManager.getCacheFileExists(
						MyPubcabinetActivity.this, bean.getFileName())) {
					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);

					File file = new File(getFilesDir(), bean.getFileName());
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), intentType);
					MyPubcabinetActivity.this.startActivity(intent);
				} else {

					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);

					FileListView fileView = new FileListView(
							MyPubcabinetActivity.this);
					fileView.downloadFile(bean);
				}
			} else {
				// 如果是文件夹 则查询下级子节点
				List<FileBean> list = fileDb.getFileList(fileList.get(position)
						.getId(), 0, selectIndex);
				if (list != null && !list.isEmpty()) {

					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);

					adaptor.setData(list);
					adaptor.notifyDataSetChanged();
					fileList = list;
				} else {
					Toast.makeText(MyPubcabinetActivity.this, "文件夹下没有内容", 0)
							.show();
				}
			}
		}

	};

	/**
	 * 获取底部分割线
	 * 
	 * @return
	 */
	private ImageView getImgViewDivider() {
		ImageView img = new ImageView(this);
		img.setId(R.id.bottom_dividing_line);
		img.setBackgroundResource(R.drawable.line);
		return img;
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			// 刷新当前界面
			case R.id.title_rightBtn:
				getFileListRemote("", "1");
				break;
			// 返回按钮
			case R.id.title_leftBtnBack:
				onKeyBack();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * 从服务器下载数据
	 */
	public void getFileListRemote(final String lastFolderTime,
			final String isPublic) {
		pd = ProgressDialog.show(MyPubcabinetActivity.this, "", "数据刷新中...",
				true, true);
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", Constants.userId);
		String method = "";
		if ("2".equals(isPublic)) {
			method = Constants.UrlHead
					+ "client.action.FileCabinetAction$synchronousShareFile";
			requestMap.put("lastFileTime", "");
		} else {
			requestMap.put("isPublic", isPublic);
			requestMap.put("lastFolderTime", "");
			method = Constants.UrlHead
					+ "client.action.FileCabinetAction$getFolder";
		}
		try {
			ServerAdaptor.getInstance(MyPubcabinetActivity.this).doAction(1,
					method, requestMap, new ServiceSyncListener() {
						public void onError(ActionResponse returnObject) {
							if (null != pd && pd.isShowing()) {
								pd.dismiss();
							}
							super.onError(returnObject);
							Log.i(TAG, returnObject.getMessage());
						}

						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							Object jsonData = returnObject.getData();
							Log.i(TAG, "jsonData:" + jsonData.toString());
							if (jsonData != null
									&& jsonData instanceof JSONArray) {
								if ("2".equals(isPublic)) {
									fileList = FileBean
											.jsonToShareFileBean((JSONArray) jsonData);
								} else {
									fileList = FileBean
											.jsonToFileBean((JSONArray) jsonData);
									FileBean.countSubFile(fileList);
								}
								handler.sendEmptyMessage(1);
							}
						}
					});
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
	}

	// 文件搜索
	TextWatcher queryContactWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			String text = searchEditView.getText().toString();
			List<FileBean> list = new ArrayList<FileBean>();
			if ("".equals(text)) {
				list = fileList;
			} else {
				list = fileDb.queryByKeyWord(selectIndex, text);
			}
			adaptor.setData(list);
			adaptor.notifyDataSetChanged();
		}
	};

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
				MyPubcabinetActivity.this.finish();
			} else {
				fileList = fileDb.getFileList(fileList.get(0).getParentId(), 0,
						selectIndex);

				if ("".equals(fileDb
						.getFileList(fileList.get(0).getParentId(), 1,
								selectIndex).get(0).getParentId())) {
					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);
				}

				adaptor.setData(fileList);
				adaptor.notifyDataSetChanged();
			}
		} else {
			MyPubcabinetActivity.this.finish();
		}
	}
}
