package com.xguanjia.hx.filecabinet.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.FileCacheManager;
import com.xguanjia.hx.common.JsonUtil;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.NetworkManager;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.PersonService;
import com.xguanjia.hx.filecabinet.adaptor.FileListAdaptor;
import com.xguanjia.hx.filecabinet.db.FilecabinetDb;
import com.xguanjia.hx.filecabinet.pojo.FileBean;
import com.xguanjia.hx.filecabinet.views.FileListView;

public class MyCabinetActivity extends BaseActivity {

	private final String TAG = "MyCabinetActivity";

	private ListView containerListView;
	private List<FileBean> fileList;
	private List<FileBean> allFileList = new ArrayList<FileBean>();
	private List<FileBean> fileAddList;
	private List<FileBean> fileDeleteList;
	private FileListAdaptor adaptor;
	private EditText searchEditView;
	private FilecabinetDb fileDb;
	private ProgressDialog pd;
	private PersonService service;
	private Handler handler;
	private FileBean currentFile;
//	private RadioGroup bottomRadioGroup;
	private LinearLayout image_button_managent, image_button_add,
			image_button_synchro, image_button_delete;
	// 设置分享选中的索引
	private int shareSelectIndex = 0;
	// 当前页面数据的parentId，切换时查询出上次的界面
	private String bottom1ParentId = "", bottom2ParentId = "";
	// 搜索框视图ID
	public static final int CONTACT_QUERY_FIELD_VIEW_ID = 123;
	// 文件柜ListView视图ID
	public static final int CONTACT_LIST_VIEW_ID = 456;

	private String selectIndex = "0"; // 0个人文件夹，1公共文件夹,2分享文件柜

	// xx
	private boolean flag = false;// true管理
	private String parentId = "";
	private String subfiles = "";

	private String name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		sp = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		Constants.userId = sp.getString("userID", "");
		if (sp.getBoolean("function_guide_filecabinet", true)) {
			functionGuide("function_guide_filecabinet",
					R.drawable.a1_bx);
		}

		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg) {

				switch (msg.what) {
				// 导入数据
				case Constants.ZERO:
					Bundle bundle = msg.getData();
					String jsonData = bundle.getString("respData");
					if (jsonData != null && "{}".equals(jsonData)) {
						Toast.makeText(MyCabinetActivity.this, "暂无新联系人",
								Toast.LENGTH_SHORT).show();
					} else {
						TypeToken<List<PersonBean>> targetType = new TypeToken<List<PersonBean>>() {
						};
						List<PersonBean> personList = (List<PersonBean>) JsonUtil
								.fromJson(jsonData, targetType);
						service.savePerson(personList, 1, true);
						Intent intent = new Intent();
						intent.setClass(MyCabinetActivity.this,
								ShareFileChooseOrgPersonActicity.class);
						intent.putExtra("fileId", currentFile.getFileId());
						startActivityForResult(intent, 6);
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
					allFileList = fileList;
					adaptor.setData(fileList);
					adaptor.notifyDataSetChanged();
					break;

				case 2:
					fileDb.saveFileAddList(fileAddList, selectIndex);
					fileAddList.clear();
					if (!subfiles.equals("")) {

						subfiles = (Integer.valueOf(subfiles) + 1) + "";
					}
					fileDb.updateSubFiles(parentId, subfiles);

					// fileList = fileDb.getFileList(nodeId1, 0, selectIndex);
					fileList = fileDb.getFileList(
							parentId.equals("") ? fileList.get(0).getParentId()
									: parentId, 0, selectIndex);
					adaptor.mChecked.clear();
					for (int i = 0; i < fileList.size(); i++) {
						adaptor.mChecked.add(false);
					}
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
		this.relativeLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				relativeLayout.setVisibility(View.GONE);
				SharedPreferences.Editor editor = getSharedPreferences(
						"basic_info", Context.MODE_PRIVATE).edit();
				editor.putBoolean("function_guide_filecabinet", false);
				editor.commit();

				getFileListRemote("", "0");
				return false;
			}
		});
		bottomView.removeAllViews();
		View fileBottomView = getLayoutInflater().inflate(
				R.layout.file_radiogroup_bottom, null);
		LayoutParams parmasManage = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
//		bottomView.addView(fileBottomView, parmasManage);
//		bottomRadioGroup = (RadioGroup) bottomView
//				.findViewById(R.id.file_bottom_radio_group);
		fileAddList = new ArrayList<FileBean>();
		fileDeleteList = new ArrayList<FileBean>();
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		setTitleRightButton("管理", 0, buttonClickListener);
		setTitleText("个人文件柜");

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

		// 处理事件
		View fileOperationView = getLayoutInflater().inflate(
				R.layout.file_operation_top, null);
		LayoutParams parmasManage1 = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		parmasManage1.addRule(RelativeLayout.BELOW, queryFieldView.getId());
		mainView.addView(fileOperationView, parmasManage1);
		image_button_managent = (LinearLayout) fileOperationView
				.findViewById(R.id.image_button_managent);
		image_button_add = (LinearLayout) image_button_managent
				.findViewById(R.id.image_button_add);
		image_button_synchro = (LinearLayout) image_button_managent
				.findViewById(R.id.image_button_synchro);
		image_button_delete = (LinearLayout) image_button_managent
				.findViewById(R.id.image_button_delete);
		image_button_add.setOnClickListener(buttonClickListener);
		image_button_synchro.setOnClickListener(buttonClickListener);
		image_button_delete.setOnClickListener(buttonClickListener);
		image_button_managent.setVisibility(View.GONE);
		// 搜索事件
		fileDb = new FilecabinetDb(this);
		containerListView = new ListView(this);
		containerListView.setId(CONTACT_LIST_VIEW_ID);
		containerListView.setDivider(getResources()
				.getDrawable(R.drawable.line));
		containerListView.setCacheColorHint(Color.alpha(00000000));
		containerListView.setOnItemClickListener(clickListener);

		containerListView
				.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						currentFile = fileList.get(position);
						if (currentFile.getIsDirectory().equals("false")) {
						}
						return false;
					}
				});

		service = new PersonService(MyCabinetActivity.this);
		fileList = fileDb.getFileList("", 0, selectIndex);
		allFileList = fileList;
		adaptor = new FileListAdaptor(this, fileList);
		containerListView.setAdapter(adaptor);
		layoutParams = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		layoutParams.addRule(RelativeLayout.BELOW, fileOperationView.getId());
		mainView.addView(containerListView, layoutParams);
		// 在底部加入分割线
	}

	/**
	 * listView点击事件
	 */
	OnItemClickListener clickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// if(!flag){
			final FileBean bean = fileList.get(position);
			// 如果是文件则下载打开
			String intentType = bean.getIntentType();
			if ("false".equals(fileList.get(position).getIsDirectory())) {
				// 存在该文件直接打开
				if (FileCacheManager.getCacheFileExists(MyCabinetActivity.this,
						bean.getFileName())) {
					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);

					File file = new File(getFilesDir(), bean.getFileName());
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), intentType);
					MyCabinetActivity.this.startActivity(intent);
				} else {
					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);

					if (!NetworkManager
							.isNetworkConnected(MyCabinetActivity.this)) {
						AlertDialog.Builder dialog = new AlertDialog.Builder(
								MyCabinetActivity.this);
						dialog.setIcon(R.drawable.ic_launcher);
						dialog.setTitle("提示信息");
						dialog.setMessage("当前网络不可用，是否设置网络？");
						dialog.setPositiveButton("是",
								new NeedSettingsNetworkListener());
						dialog.setNegativeButton("否", null);
						dialog.create();
						dialog.show();
						return;
					}
					if (!bean.getFileSize().equals("")
							&& Integer.valueOf(bean.getFileSize()) > 512) {

						if (!NetworkManager
								.currentNetworkIsWifi(MyCabinetActivity.this)) {
							AlertDialog.Builder dialog = new AlertDialog.Builder(
									MyCabinetActivity.this);
							dialog.setIcon(R.drawable.ic_launcher);
							dialog.setTitle("提示信息");
							dialog.setMessage("当前网络不是WIFI，是否设置网络？");
							dialog.setPositiveButton("是",
									new NeedSettingsWifiworkListener());
							dialog.setNegativeButton("否",
									new OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											FileListView fileView = new FileListView(
													MyCabinetActivity.this);
											fileView.downloadFile(bean);
										}
									});
							dialog.create();
							dialog.show();
							return;
						}

					}

					FileListView fileView = new FileListView(
							MyCabinetActivity.this);
					fileView.downloadFile(bean);
				}
			} else {
				// 如果是文件夹 则查询下级子节点
				List<FileBean> list = fileDb.getFileList(fileList.get(position)
						.getId(), 0, selectIndex);
				// if (list != null && !list.isEmpty()) {
				setTitleLeftButtonBack("", R.drawable.back_selector,
						buttonClickListener);
				setTitleRightButton("管理", 0, buttonClickListener);
				adaptor.setData(list);
				adaptor.notifyDataSetChanged();
				// xx
				parentId = fileList.get(position).getId();
				subfiles = fileList.get(position).getSubFiles();
				fileList = list;
				adaptor.mChecked.clear();
				for (int i = 0; i < fileList.size(); i++) {
					adaptor.mChecked.add(false);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Intent intent;
		switch (resultCode) {
		case 1:// 新增文件夹
			LayoutInflater factory = LayoutInflater.from(this);
			final View okView = factory.inflate(R.layout.file_add, null);
			AlertDialog dlg = new AlertDialog.Builder(this)
					.setTitle("新建文件夹")
					.setView(okView)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									EditText currname = (EditText) okView
											.findViewById(R.id.fileadd_et);
									if (currname.getText() != null
											&& !currname.getText().toString()
													.trim().equals("")) {
										name = currname.getText().toString()
												.trim();
									} else {
										Toast.makeText(MyCabinetActivity.this,
												"文件夹名不能为空", 0).show();
										return;
									}
									try {
										HashMap<String, Object> val = new HashMap<String, Object>();
										val.put("parentId",
												parentId.equals("") ? fileList
														.get(0).getParentId()
														: parentId);
										val.put("name", name);
										val.put("userId", Constants.userId);
										val.put("descp", "");
										doAsyncFolderAdd(val, "新增上传中...", name);
									} catch (Exception e) {
										// TODO: handle exception
										Toast.makeText(MyCabinetActivity.this,
												"请先同步文件文件柜", 0).show();
										e.printStackTrace();
									}
								}
							}).setNegativeButton("取消", null).create();
			dlg.show();

			break;

		// case 2://新增文件夹
		// String name = data.getStringExtra("data");
		// HashMap<String, Object> val = new HashMap<String, Object>();
		// // val.put("parentId", fileList.get(0).getParentId());
		// val.put("parentId", parentId);
		// val.put("name", name);
		// val.put("userId", Constants.userId);
		// doAsyncFolderAdd(val,"新增上传中...",name);
		// break;
		case 3:// 新增图片
			if (!isSdCardAvailable()) {
				Toast.makeText(MyCabinetActivity.this, "当前SD卡不可用",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 3);
			}
			break;
		case 4:// 拍照上传
			if (!isSdCardAvailable()) {
				Toast.makeText(MyCabinetActivity.this, "当前SD卡不可用",
						Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				picName = new DateFormat().format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA))
						+ ".jpg";
				String fileName = Constants.SAVE_IMAGE_PATH + picName;
				file = new File(Constants.SAVE_IMAGE_PATH);
				file.mkdirs();
				file = new File(Constants.SAVE_IMAGE_PATH + picName);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
				startActivityForResult(intent, 4);
			}
			break;
		case 5:// 上传文件
			if (!isSdCardAvailable()) {
				Toast.makeText(MyCabinetActivity.this, "当前SD卡不可用",
						Toast.LENGTH_SHORT).show();
			} else {
				try {
					intent = new Intent();
					intent.setType("application/msword" + "application/pdf"
							+ "application/vnd.ms-powerpoint"
							+ "application/vnd.ms-excel");
					intent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intent, 5);
				} catch (Exception e) {
					Toast.makeText(MyCabinetActivity.this, "请安装文件浏览工具", 0)
							.show();
				}

			}
			break;
		case 6:// 分享成功
			currentFile.setIsShare("1");
			fileDb.shareEditFiles("1", currentFile);
			fileDb.setShare(currentFile);
			break;
		case RESULT_OK:
			if (requestCode == 3) {
				String imgName = new DateFormat().format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA)) + ".jpg";
				Uri uri = data.getData();

				String[] pojo = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				String path = "";
				if (cursor != null) {
					ContentResolver cr = this.getContentResolver();
					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					path = cursor.getString(colunm_index);
				}

				ContentResolver cr = this.getContentResolver();
				try {
					doUploadAction("jpg", cr.openInputStream(uri), imgName,
							path);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

			if (requestCode == 4) {

				// String picName = new DateFormat().format("yyyyMMdd_hhmmss",
				// Calendar.getInstance(Locale.CHINA)) + ".jpg";
				// Bundle bundle = data.getExtras();
				// if (bundle == null) {
				// Toast.makeText(MyCabinetActivity.this,
				// "您手机的系统为定制系统，该版本不支持该功能", 0).show();
				// return;
				// }
				// Bitmap bitmap = (Bitmap) bundle.get("data");//
				// 获取相机返回的数据，并转换为Bitmap图片格式
				// // String fileName = Constants.CACHE_PATH + picName;
				// String fileName = Constants.SAVE_IMAGE_PATH + picName;
				//
				// // File file = new File(Constants.CACHE_PATH);
				// File file = new File(Constants.SAVE_IMAGE_PATH);
				// file.mkdirs();// 创建文件夹
				// FileOutputStream b = null;
				// try {
				// b = new FileOutputStream(fileName);
				// bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//
				// 把数据写入文件
				// } catch (FileNotFoundException e) {
				// e.printStackTrace();
				// } finally {
				// try {
				// b.flush();
				// b.close();
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }

				// 保存图片后上传至服务器
				FileInputStream fileInputStream = null;
				try {
					if (!(null == file)) {
						fileInputStream = new FileInputStream(file);

						doUploadAction("jpg", fileInputStream, picName,
								file.toString());
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				// 相机上传图片
			}

			if (requestCode == 5) {
				if (data == null) {
					return;
				}
				Uri uri = data.getData();

				String path = getRealPath(uri);

				String imgName = path.substring(path.lastIndexOf("/") + 1);
				ContentResolver cr = this.getContentResolver();
				try {
					doUploadAction(path.substring(path.lastIndexOf(".") + 1),
							cr.openInputStream(uri), imgName, path);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private String getRealPath(Uri fileUrl) {
		String fileName = null;
		Uri filePathUri = fileUrl;
		if (fileUrl != null) {
			if (fileUrl.getScheme().toString().compareTo("content") == 0) // content://开头的uri
			{
				Cursor cursor = MyCabinetActivity.this.getContentResolver()
						.query(filePathUri, null, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					int column_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					fileName = cursor.getString(column_index); // 取出文件路径
					/**
					 * if (!fileName.startsWith("/mnt")) { // 检查是否有”/mnt“前缀
					 * 
					 * fileName = "/mnt" + fileName; }
					 **/
					cursor.close();
				}
			} else if (filePathUri.getScheme().compareTo("file") == 0) // file:///开头的uri
			{
				fileName = filePathUri.decode(filePathUri.toString());
				fileName = filePathUri.toString().replace("file://", "");

				// 替换file://
				/**
				 * if (!fileName.startsWith("/mnt")) { // 加上"/mnt"头 fileName +=
				 * "/mnt"; }
				 **/
			}
		}
		return fileName;
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.image_button_synchro:
				getFileListRemote("", "0");
				break;

			case R.id.image_button_delete:// 删除文件夹与文件
				for (int i = 0; i < fileList.size(); i++) {
					if (adaptor.mChecked.get(i)) {
						if (!fileList.get(i).getDef().equals("1"))
							fileDeleteList.add(fileList.get(i));
					}
				}

				if (fileDeleteList != null && fileDeleteList.size() > 0) {
					StringBuffer folderId = new StringBuffer();
					StringBuffer fileId = new StringBuffer();
					HashMap<String, Object> requestMap = new HashMap<String, Object>();
					for (int i = 0; i < fileDeleteList.size(); i++) {

						if (fileDeleteList.get(i).getIsDirectory()
								.equals("true")) {
							folderId.append(",").append(
									fileDeleteList.get(i).getId());
						} else {
							fileId.append(",").append(
									fileDeleteList.get(i).getFileId());
						}
					}

					requestMap.put("folderId",
							folderId.toString().equals("") ? "" : folderId
									.toString().substring(1));
					requestMap.put("userId", Constants.userId);
					requestMap.put("fileId", fileId.toString().equals("") ? ""
							: fileId.toString().substring(1));
					requestMap.put("parentId", parentId.equals("") ? fileList
							.get(0).getParentId() : parentId);

					doAsyncFileDelete(requestMap, "删除中，请稍后...");
				} else {
					Toast.makeText(MyCabinetActivity.this, "请选择删除项", 0).show();
				}

				break;
			case R.id.image_button_add:
				// xx
				Intent intent = new Intent(MyCabinetActivity.this,
						FileDetailOperateActivity.class);
				startActivityForResult(intent, 0);
				break;
			// case R.id.image_upload:
			// Intent intent1 = new Intent(MyCabinetActivity.this,
			// FileDetailUploadActivity.class);
			// startActivityForResult(intent1, 0);
			// break;
			// 刷新当前界面
			case R.id.title_rightBtn:
				// xx

				if (sp.getBoolean("function_guide_one", true)) {
					SharedPreferences.Editor editor = getSharedPreferences(
							"basic_info", Context.MODE_PRIVATE).edit();
					editor.putBoolean("function_guide_one", false);
					editor.commit();
					return;
				}

				if (!flag) {
					adaptor.setFlag(flag);
					flag = true;

					setTitleRightButton("完成", 0, buttonClickListener);
//					bottomRadioGroup.setVisibility(View.GONE);
					image_button_managent.setVisibility(View.VISIBLE);
					adaptor.notifyDataSetChanged();
				} else {
					setTitleRightButton("管理", 0, buttonClickListener);
					adaptor.setFlag(flag);
					for (int i = 0; i < adaptor.mChecked.size(); i++) {
						adaptor.mChecked.set(i, false);
					}
					flag = false;

					image_button_managent.setVisibility(View.GONE);
					adaptor.notifyDataSetChanged();
				}
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
		pd = ProgressDialog.show(MyCabinetActivity.this, "", "数据刷新中...", true,
				true);
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
			ServerAdaptor.getInstance(MyCabinetActivity.this).doAction(1,
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
									// xx
									adaptor.mChecked.clear();
									for (int i = 0; i < fileList.size(); i++) {
										adaptor.mChecked.add(false);
									}

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
				list = allFileList;
			} else {
				list = fileDb.queryByKeyWord(selectIndex, text);
			}
			fileList = list;
			adaptor.setData(list);
			adaptor.notifyDataSetChanged();
		}
	};

	private File file = null;

	private String picName;

	/**
	 * 返回按钮事件
	 */
	private void onKeyBack() {

		searchEditView.setText("");

		// 树节点往上查询
		// xx
		// if (fileList != null && !fileList.isEmpty() &&
		// !"2".equals(selectIndex)) {
		if (!parentId.equals("")) {
			fileList = fileDb.getFileList(parentId, 1, selectIndex);
			// 已经是最顶层父亲节点，直接关闭页面
			if ("".equals(fileList.get(0).getParentId())) {
				MyCabinetActivity.this.finish();
			} else {
				fileList = fileDb.getFileList(fileList.get(0).getParentId(), 0,
						selectIndex);
				if ("".equals(fileDb
						.getFileList(fileList.get(0).getParentId(), 1,
								selectIndex).get(0).getParentId())) {
					setTitleLeftButtonBack("", R.drawable.back_selector,
							buttonClickListener);
					setTitleRightButton("管理", 0, buttonClickListener);
					image_button_managent.setVisibility(View.GONE);
					subfiles = "0";
				} else {
					subfiles = fileDb
							.getFileList(fileList.get(0).getParentId(), 1,
									selectIndex).get(0).getSubFiles();
				}
				adaptor.mChecked.clear();
				for (int i = 0; i < fileList.size(); i++) {
					adaptor.mChecked.add(false);
				}
				parentId = fileList.get(0).getParentId();
				adaptor.setData(fileList);
				flag = true;
				adaptor.setFlag(flag);
				adaptor.notifyDataSetChanged();
				flag = false;
				image_button_managent.setVisibility(View.GONE);
			}
		} else {
			MyCabinetActivity.this.finish();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case Constants.ONE:
			return new AlertDialog.Builder(MyCabinetActivity.this)
					.setTitle("设置分享")
					.setSingleChoiceItems(R.array.shareType1, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// shareSelectIndex = whichButton;
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 跳转至选人界面
									List<PersonBean> list = service
											.queryInternalPerson();
									if (list != null && !list.isEmpty()) {
										Intent intent = new Intent();
										intent.setClass(
												MyCabinetActivity.this,
												ShareFileChooseOrgPersonActicity.class);
										intent.putExtra("fileId",
												currentFile.getFileId());
										startActivityForResult(intent, 6);
									} else {
										// 导入组织机构
										HashMap<String, Object> hashMap = new HashMap<String, Object>();
										StringBuilder requestMethod = new StringBuilder(
												Constants.UrlHead
														+ "client.action.ContactsAction");
										requestMethod
												.append("$getOrganization");
										hashMap.put("lastContactsTime", "");
										hashMap.put("userId", Constants.userId);
										doAsyncContactJsonAction(
												requestMethod.toString(),
												hashMap, "联系人同步中");
									}

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();
		case Constants.TWO:
			return new AlertDialog.Builder(MyCabinetActivity.this)
					.setTitle("设置分享")
					.setSingleChoiceItems(R.array.shareType, 0,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									shareSelectIndex = whichButton;
								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// 跳转至选人界面
									if (shareSelectIndex == 0) {
										List<PersonBean> list = service
												.queryInternalPerson();
										if (list != null && !list.isEmpty()) {
											Intent intent = new Intent();
											intent.setClass(
													MyCabinetActivity.this,
													ShareFileChooseOrgPersonActicity.class);
											intent.putExtra("fileId",
													currentFile.getFileId());
											startActivity(intent);
										} else {
											// 导入组织机构
											HashMap<String, Object> hashMap = new HashMap<String, Object>();
											StringBuilder requestMethod = new StringBuilder(
													Constants.UrlHead
															+ "client.action.ContactsAction");
											requestMethod
													.append("$getOrganization");
											hashMap.put("lastContactsTime", "");
											hashMap.put("userId",
													Constants.userId);
											doAsyncContactJsonAction(
													requestMethod.toString(),
													hashMap, "联系人同步中");
										}

									} else if (shareSelectIndex == 1) {
										HashMap<String, Object> requestMap = new HashMap<String, Object>();
										requestMap.put("fileId",
												currentFile.getFileId());
										String method = Constants.UrlHead
												+ "client.action.FileCabinetAction$undoShareFile";
										pd = ProgressDialog.show(
												MyCabinetActivity.this, "",
												"分享撤销中");
										doAsyncJsonAction(method, requestMap,
												"");

									}

								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {

								}
							}).create();

		}
		return null;
	}

	/***
	 * 后台交互
	 * 
	 * @param method
	 *            方法名称
	 * @param map
	 *            请求参数 分享文件
	 */
	private void doAsyncJsonAction(String method, HashMap<String, Object> map,
			String showDialogMsg) {
		try {
			ServerAdaptor.getInstance(this).doAction(1, method, map,
					new ServiceSyncListener() {

						@Override
						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							// shareSelectIndex = 0;
							fileDb.shareEditFiles("0", currentFile);
							fileDb.deleteShare(currentFile);
							currentFile.setIsShare("0");
							Toast.makeText(MyCabinetActivity.this, "撤销分享成功",
									Toast.LENGTH_SHORT).show();
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

	/***
	 * 后台交互
	 * 
	 * @param method
	 *            方法名称
	 * @param map
	 *            请求参数
	 */
	private void doAsyncContactJsonAction(String method,
			HashMap<String, Object> map, String showDialogMsg) {
		pd = ProgressDialog.show(this, "", showDialogMsg, true, false);
		try {
			ServerAdaptor.getInstance(this).doAction(1, method, map,
					new ServiceSyncListener() {

						@Override
						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							String jsonData = returnObject.getData().toString();
							Message msg = handler.obtainMessage();
							Bundle bundle = new Bundle();
							bundle.putString("respData", jsonData);
							bundle.putBoolean("isNetWorkData", true);
							msg.setData(bundle);
							msg.what = 0;
							handler.sendMessage(msg);
						}

						@Override
						public void onError(ActionResponse returnObject) {
							Log.i(TAG, "error" + returnObject.getMessage());
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							MAMessage.ShowMessage(MyCabinetActivity.this,
									"数据获取失败", returnObject.getMessage());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 新增文件夹
	private void doAsyncFolderAdd(HashMap<String, Object> map,
			String showDialogMsg, final String filename) {
		pd = ProgressDialog.show(this, "", showDialogMsg, true, false);
		try {
			ServerAdaptor.getInstance(this).doAction(
					1,
					Constants.UrlHead
							+ "client.action.FileCabinetAction$newFolder", map,
					new ServiceSyncListener() {

						@Override
						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}

							JSONObject obj = (JSONObject) returnObject
									.getData();
							try {
								String fileid = "";
								if (!obj.isNull("folderId")) {
									fileid = obj.getString("folderId");
								}
								FileBean bean = new FileBean();
								bean.setId(fileid);
								bean.setFileName(filename);
								if (parentId.equals("")) {
									bean.setParentId(fileList.get(0)
											.getParentId());
								} else {
									bean.setParentId(parentId);
								}

								bean.setSubFiles("0");
								bean.setIsDirectory("true");
								fileAddList.add(bean);
								// fileList.add(bean);
								// adaptor.mChecked.add(false);
								// adaptor.notifyDataSetChanged();
								handler.sendEmptyMessage(2);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onError(ActionResponse returnObject) {
							Log.i(TAG, "error" + returnObject.getMessage());
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							MAMessage.ShowMessage(MyCabinetActivity.this,
									"数据获取失败", returnObject.getMessage());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * 上传照片
	 * 
	 * @param method
	 *            方法名称
	 * @param map
	 *            请求参数
	 */
	private void doUploadAction(final String fileType, InputStream inStream,
			final String name, final String filePath) {
		Constants.fileName = name;
		ServerAdaptor.getInstance(MyCabinetActivity.this).uploadFile(fileType,
				"person_filecabinet_path", inStream, new ServiceSyncListener() {
					@Override
					public void onSuccess(ActionResponse returnObject) {
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}
						Toast.makeText(MyCabinetActivity.this, "照片上传成功",
								Toast.LENGTH_SHORT).show();
						// String imagePath = (String) returnObject.getData();
						JSONObject jsonObject = (JSONObject) returnObject
								.getData();
						String fileId = "";
						try {
							fileId = jsonObject.getString("fileId");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						HashMap<String, Object> requestMap = new HashMap<String, Object>();
						requestMap.put("parentId",
								"".equals(parentId) ? fileList.get(0)
										.getParentId() : parentId);
						// requestMap.put("parentId", parentId);
						requestMap.put("fileIds", fileId);
						requestMap.put("orignalName", name);
						requestMap.put("userId", Constants.userId);

						doAsyncFileAdd(requestMap, "", name, filePath);
						super.onSuccess(returnObject);
					}

					@Override
					public void onError(ActionResponse returnObject) {
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}

						super.onError(returnObject);
					}
				});
	}

	// 新增文件
	private void doAsyncFileAdd(HashMap<String, Object> map,
			String showDialogMsg, final String filename, final String filePath) {
		pd = ProgressDialog.show(this, "", showDialogMsg, true, false);
		try {
			ServerAdaptor.getInstance(this).doAction(
					1,
					Constants.UrlHead
							+ "client.action.FileCabinetAction$newFile", map,
					new ServiceSyncListener() {

						@Override
						public void onSuccess(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							Toast.makeText(MyCabinetActivity.this, "上传成功",
									Toast.LENGTH_SHORT).show();
							JSONObject obj = (JSONObject) returnObject
									.getData();
							try {

								FileBean bean = new FileBean();
								String ids = obj.getString("ids");
								bean.setFileId(ids);

								String urls = obj.getString("urls");
								bean.setFileUrl(urls);

								String fileNames = obj.getString("fileNames");
								bean.setFileName(fileNames);

								String createTimes = obj
										.getString("createTimes");
								bean.setReleaseDate(createTimes);

								String sizes = obj.getString("sizes");
								bean.setFileSize(sizes);
								bean.setFilePath(filePath);
								// bean.setParentId(parentId);
								bean.setIsDirectory("false");
								bean.setParentId("".equals(parentId) ? fileList
										.get(0).getParentId() : parentId);

								fileAddList.clear();
								fileAddList.add(bean);

								fileList.add(bean);
								adaptor.mChecked.add(false);
								adaptor.notifyDataSetChanged();

								handler.sendEmptyMessage(2);
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}

						@Override
						public void onError(ActionResponse returnObject) {
							Log.i(TAG, "error" + returnObject.getMessage());
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							MAMessage.ShowMessage(MyCabinetActivity.this,
									"照片上传失败", returnObject.getMessage());
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 文件删除
	private void doAsyncFileDelete(HashMap<String, Object> map,
			String showDialogMsg) {
		pd = ProgressDialog.show(this, "", showDialogMsg, true, false);
		try {
			ServerAdaptor
					.getInstance(this)
					.doAction(
							1,
							Constants.UrlHead
									+ "client.action.FileCabinetAction$deleteFolderAndFile",
							map, new ServiceSyncListener() {

								@Override
								public void onSuccess(
										ActionResponse returnObject) {
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									fileDb.deleteFiles(
											parentId.equals("") ? fileList.get(
													0).getParentId() : parentId,
											fileDeleteList);
									if (!subfiles.equals("")) {
										subfiles = (Integer.valueOf(subfiles) - fileDeleteList
												.size()) + "";
									}
									fileDeleteList.clear();
									fileDb.updateSubFiles(parentId, subfiles);

									fileList = fileDb.getFileList(parentId, 0,
											selectIndex);
									adaptor.mChecked.clear();
									for (int i = 0; i < fileList.size(); i++) {
										adaptor.mChecked.add(false);
									}
									adaptor.setData(fileList);
									adaptor.notifyDataSetChanged();
									Toast.makeText(MyCabinetActivity.this,
											"删除成功", 0).show();
								}

								@Override
								public void onError(ActionResponse returnObject) {
									Log.i(TAG,
											"error" + returnObject.getMessage());
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									MAMessage.ShowMessage(
											MyCabinetActivity.this, "删除失败",
											returnObject.getMessage());
								}
							});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重写功能返回键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// Toast.makeText(this, "keycode_back", 1).show();
			onKeyBack();
		}
		return true;

	}

	/**
	 * 需要设置网络
	 * 
	 */
	class NeedSettingsNetworkListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			NetworkManager.setNetSetting(MyCabinetActivity.this);
		}

	}

	/**
	 * 需要设置无线网络
	 * 
	 */
	class NeedSettingsWifiworkListener implements
			DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			NetworkManager.setNetwork(MyCabinetActivity.this);
		}
	}

	private boolean isSdCardAvailable() {
		boolean sdCardAvailable = false;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			sdCardAvailable = true;
		}
		return sdCardAvailable;
	}
}
