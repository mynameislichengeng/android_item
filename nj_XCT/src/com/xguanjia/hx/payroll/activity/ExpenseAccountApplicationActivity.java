//package com.xguanjia.hx.payroll.activity;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URLDecoder;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.ContentResolver;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Environment;
//import android.provider.MediaStore;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout.LayoutParams;
//import android.widget.TextView;
//import android.widget.Toast;
//import cmcc.ueprob.agent.UEProbAgent;
//
//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.chinamobile.salary.R;
//import com.xguanjia.hx.application.MainApp;
//import com.xguanjia.hx.attachment.adapter.AttachmentOperateAdapter;
//import com.xguanjia.hx.attachment.bean.AttachmentBean;
//import com.xguanjia.hx.attachment.bean.AttachmentOperateTypeBean;
//import com.xguanjia.hx.attachment.view.AttachmentOperateDialog;
//import com.xguanjia.hx.common.ActionResponse;
//import com.xguanjia.hx.common.Constants;
//import com.xguanjia.hx.common.ImageCompressUtil;
//import com.xguanjia.hx.common.MOAOnClickListener;
//import com.xguanjia.hx.common.MeasuredGridView;
//import com.xguanjia.hx.common.ServerAdaptor;
//import com.xguanjia.hx.common.ServiceSyncListener;
//import com.xguanjia.hx.common.activity.BaseLocationActivity;
//import com.xguanjia.hx.common.selecttime.JudgeDate;
//import com.xguanjia.hx.common.selecttime.ScreenInfo;
//import com.xguanjia.hx.common.selecttime.WheelMain;
//import com.xguanjia.hx.payroll.adapter.AttachmentChooseAdapter;
//
///**
// * 报销上报 详情界面  
// * 
// */
//public class ExpenseAccountApplicationActivity extends BaseLocationActivity
//		implements OnItemClickListener {
//	private static final String TAG = "ExpenseAccountApplicationActivity";
//	private EditText nameEt, titleEt, moneyEt, detailEt;
//	private TextView typeTv, postionTv, timeTv;
//	private ImageView postionImg;
//	private Button submitBtn;
//	private ProgressDialog pd;
//	private HashMap<String, Object> requestMap;
//
//	// 时间
//	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//	private BDLocation bdlocation;// 定位返回的地理位置信息
//	private int requestCount = 0;
//
//	// 选择类型
//	private AttachmentOperateDialog attachmentOperateDialogReason;// 附件操作弹出框
//	private AttachmentOperateAdapter operateAdapter;
//	private String type = "";
//
//	// 附件
//	private MeasuredGridView gvImgs;
//	private ArrayList<AttachmentBean> attachmentList = new ArrayList<AttachmentBean>();
//	private AttachmentOperateDialog attachmentOperateDialog;
//	private AttachmentChooseAdapter attachmentChooseAdapter;
//	private String pickPicName;
//	private Intent intent;
//	private int attachmentIndex;
//    //百度地图定位   监听类
//	public BDLocationListener myListener = new MyLocationListener();
//	public double m_dLontitude, m_dLatitude;
//
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		initView();
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//		UEProbAgent.onResume(this);
//	}
//
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//		UEProbAgent.onPause(this);
//	}
//
//	private void initView() {
//		View view = getLayoutInflater().inflate(
//				R.layout.expenseaccountapplication_activity, null);
//		LayoutParams layoutParams = new LayoutParams(
//				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
//				android.view.ViewGroup.LayoutParams.MATCH_PARENT);
//		mainView.addView(view, layoutParams);
//		setTitleText("报销上报");
//		setTitleLeftButtonBack("", R.drawable.back_selector,
//				buttonClickListener);
//		nameEt = (EditText) view.findViewById(R.id.nameEt);
//		titleEt = (EditText) view.findViewById(R.id.titleEt);
//		moneyEt = (EditText) view.findViewById(R.id.moneyEt);
//		detailEt = (EditText) view.findViewById(R.id.detailEt);
//		typeTv = (TextView) view.findViewById(R.id.typeTv);
//		typeTv.setOnClickListener(buttonClickListener);
//		postionTv = (TextView) view.findViewById(R.id.postionTv);
//		postionTv.setText("正在获取中");
//		postionTv.setOnClickListener(buttonClickListener);
//		timeTv = (TextView) view.findViewById(R.id.timeTv);
//		timeTv.setOnClickListener(buttonClickListener);
//		postionImg = (ImageView) view.findViewById(R.id.postionImg);
//		postionImg.setOnClickListener(buttonClickListener);
//		submitBtn = (Button) view.findViewById(R.id.submitBtn);
//		submitBtn.setOnClickListener(buttonClickListener);
//
//		gvImgs = (MeasuredGridView) view.findViewById(R.id.gvImgs);
//		gvImgs.setSelector(new ColorDrawable(Color.TRANSPARENT));
//		gvImgs.setOnItemClickListener(this);
//		attachmentChooseAdapter = new AttachmentChooseAdapter(this);
//		operateAdapter = new AttachmentOperateAdapter(this);
//		gvImgs.setAdapter(attachmentChooseAdapter);
//		initDate();
//		initMap();
//	}
//
//	private void initDate() {
//		timeTv.setText(sdf1.format(new Date()));
//	}
//	// 定位参数初始化
//	private void initMap() {
//		//注册监听函数
//		mLocationClient.registerLocationListener(myListener);
//		mLocationClient.start();
//	}
//
//	/*
//	 * 设置时间
//	 */
//	public void setTime() {
//		LayoutInflater inflater = LayoutInflater
//				.from(ExpenseAccountApplicationActivity.this);
//		final View timepickerview = inflater.inflate(R.layout.timepicker, null);
//		ScreenInfo screenInfo = new ScreenInfo(
//				ExpenseAccountApplicationActivity.this);
//		final WheelMain wheelMain = new WheelMain(timepickerview, false, true,
//				true);
//		wheelMain.screenheight = screenInfo.getWidth();
//		String time = "";
//		Calendar calendar = Calendar.getInstance();
//		if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
//			try {
//				calendar.setTime(dateFormat.parse(time));
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		int year = calendar.get(Calendar.YEAR);
//		int month = calendar.get(Calendar.MONTH);
//		int day = calendar.get(Calendar.DAY_OF_MONTH);
//		int hour = calendar.get(Calendar.HOUR_OF_DAY);
//		int min = calendar.get(Calendar.MINUTE);
//		int second = calendar.get(Calendar.SECOND);
//		wheelMain.setEND_YEAR(year + 10);
//		wheelMain.setSTART_YEAR(year - 10);
//		wheelMain.initDateTimePicker(year, month, day, hour, min, second);
//		new AlertDialog.Builder(ExpenseAccountApplicationActivity.this)
//				.setTitle("选择报销时间").setView(timepickerview)
//				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						timeTv.setText(wheelMain.getTime(0));
//					}
//				})
//				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//					public void onClick(DialogInterface dialog, int which) {
//						timeTv.setText(sdf1.format(new Date()));
//					}
//				}).show();
//	}
//
//	/**
//	 * 点击事件
//	 */
//	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
//		public void onClick(View v) {
//			switch (v.getId()) {
//			case R.id.title_leftBtnBack:
//				finish();
//				break;
//			case R.id.timeTv:
//				setTime();
//				break;
//			case R.id.postionTv:
//				postionTv.setText("正在获取中");
//				mLocationClient.start();
//
//				break;
//			case R.id.postionImg:
//				postionTv.setText("正在获取中");
//				mLocationClient.start();
//
//				break;
//			case R.id.typeTv:
//				optionMenuInit("选择报销类型", selectAttachItemClick);
//				break;
//			case R.id.submitBtn:
//				submit();
//				break;
//			default:
//				break;
//			}
//		};
//	};
//
//	// 提交
//	private void submit() {
//		String name = nameEt.getText().toString();
//		String title = titleEt.getText().toString();
//		String typeString = typeTv.getText().toString();
//		String postion = postionTv.getText().toString();
//		String time = timeTv.getText().toString();
//		String money = moneyEt.getText().toString();
//		String detail = detailEt.getText().toString();
//		if (TextUtils.isEmpty(title) || TextUtils.isEmpty(typeString)
//				|| TextUtils.isEmpty(time) || TextUtils.isEmpty(money)
//				|| TextUtils.isEmpty(detail)) {
//			Toast.makeText(this, "请将信息填写完整", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		requestMap = new HashMap<String, Object>();
//		requestMap.put("userid", Constants.userId);
//		requestMap.put("reimburseName", title);
//		requestMap.put("reimburseType", type);
//		requestMap.put("location", postion);
//		requestMap.put("reimburseDate", time);
//		requestMap.put("expenseAmount", money);
//		requestMap.put("expenseContent", detail);
//		requestMap.put("partyId", Constants.partyId);
//		pd = ProgressDialog.show(ExpenseAccountApplicationActivity.this, "",
//				"上报中。。。", true, true);
//		try {
//			if (attachmentList != null && !attachmentList.isEmpty()) {
//				// 过滤文件类型的点，".jpg"->"jpg"
//				for (int i = 0; i < attachmentList.size(); i++) {
//					attachmentList.get(i).setFileType(
//							attachmentList.get(i).getFileType()
//									.replace(".", ""));
//				}
//				AttachmentBean bean = attachmentList.get(requestCount);
//				File file = new File(bean.getLocalPath());
//				InputStream inStream = new FileInputStream(file);
//				doUploadAction(bean.getFileType(), inStream, bean.getFileName());
//			}
//			// 没附件直接上传信息
//			else {
//				doAsyncJsonAction(Constants.UrlHead
//						+ "client.action.ReimburseAction$postReimburse",
//						requestMap);
//			}
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	/***
//	 * 上传附件
//	 * 
//	 * @param method
//	 *            方法名称
//	 * @param map
//	 *            请求参数
//	 */
//	private void doUploadAction(String fileType, InputStream inStream,
//			final String name) {
//		Constants.fileName = name;
//		ServerAdaptor.getInstance(ExpenseAccountApplicationActivity.this)
//				.uploadFile(fileType, "datareport_path", inStream,
//						new ServiceSyncListener() {
//							@Override
//							public void onSuccess(ActionResponse returnObject) {
//								JSONObject jsonObject = (JSONObject) returnObject
//										.getData();
//								String fileId = "";
//								try {
//									fileId = jsonObject.getString("fileId");
//								} catch (JSONException e1) {
//									e1.printStackTrace();
//								}
//								attachmentList.get(requestCount).setFileId(
//										fileId);
//								if (requestCount == attachmentList.size() - 1) {
//									// 拼接附件的名称和路径
//									StringBuilder imgNames = new StringBuilder();
//									StringBuilder imgFileId = new StringBuilder();
//									if (attachmentList.size() == 1) {
//										for (int i = 0; i < attachmentList
//												.size(); i++) {
//											AttachmentBean bean = attachmentList
//													.get(i);
//											imgNames.append(bean.getFileName());
//											imgFileId.append(bean.getFileId());
//										}
//									} else {
//										for (int i = 0; i < attachmentList
//												.size(); i++) {
//											AttachmentBean bean = attachmentList
//													.get(i);
//											imgNames.append(bean.getFileName()
//													+ ",");
//											imgFileId.append(bean.getFileId()
//													+ ",");
//										}
//									}
//									requestMap.put("attachmentid",
//											imgFileId.toString());
//									doAsyncJsonAction(
//											Constants.UrlHead
//													+ "client.action.ReimburseAction$postReimburse",
//											requestMap);
//								} else {
//									// 继续上传图片
//									++requestCount;
//									try {
//										AttachmentBean bean = attachmentList
//												.get(requestCount);
//										File file = new File(bean.getAppPath());
//										InputStream inStream = new FileInputStream(
//												file);
//										doUploadAction(bean.getFileType(),
//												inStream, bean.getFileName());
//									} catch (FileNotFoundException e) {
//										e.printStackTrace();
//									}
//								}
//								super.onSuccess(returnObject);
//							}
//
//							@Override
//							public void onError(ActionResponse returnObject) {
//								super.onError(returnObject);
//								if (pd != null && pd.isShowing()) {
//									pd.dismiss();
//								}
//								Toast.makeText(
//										ExpenseAccountApplicationActivity.this,
//										"网络异常，请重新上报", Toast.LENGTH_SHORT)
//										.show();
//							}
//						});
//
//	}
//
//	/***
//	 * 后台交互
//	 * 
//	 * @param method
//	 *            方法名称
//	 * @param map
//	 *            请求参数
//	 */
//	private void doAsyncJsonAction(String method, HashMap<String, Object> map) {
//		try {
//			ServerAdaptor.getInstance(this).doAction(1, method, map,
//					new ServiceSyncListener() {
//
//						@Override
//						public void onSuccess(ActionResponse returnObject) {
//							super.onSuccess(returnObject);
//							if (pd != null && pd.isShowing()) {
//								pd.dismiss();
//							}
//							Toast.makeText(
//									ExpenseAccountApplicationActivity.this,
//									"上报审批成功", Toast.LENGTH_SHORT).show();
//							setResult(1);
//							finish();
//						}
//
//						@Override
//						public void onError(ActionResponse returnObject) {
//							super.onError(returnObject);
//							if (pd != null && pd.isShowing()) {
//								pd.dismiss();
//							}
//							Toast.makeText(
//									ExpenseAccountApplicationActivity.this,
//									"网络异常，请重新上报", Toast.LENGTH_SHORT).show();
//						}
//					});
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	private void optionMenuInit(String title,
//			OnItemClickListener itemClickListener) {
//		attachmentOperateDialogReason = new AttachmentOperateDialog.Builder(
//				this).setTitle(title)
//				.setItems(operateAdapter, itemClickListener).create();
//		List<AttachmentOperateTypeBean> operateList = setOperateDate();
//		operateAdapter.dataNotifyChange(operateList);
//		attachmentOperateDialogReason.show();
//	}
//
//	OnItemClickListener selectAttachItemClick = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//				long arg3) {
//			// TODO Auto-generated method stub
//			attachmentOperateDialogReason.dismiss();
//			if (position == 0) {
//				typeTv.setText("交通");
//				type = "1";
//			} else if (position == 1) {
//				typeTv.setText("住宿");
//				type = "2";
//			} else if (position == 2) {
//				typeTv.setText("餐饮");
//				type = "3";
//			} else if (position == 3) {
//				typeTv.setText("办公");
//				type = "4";
//			} else if (position == 4) {
//				typeTv.setText("其他");
//				type = "5";
//			}
//		}
//	};
//
//	private List<AttachmentOperateTypeBean> setOperateDate() {
//		List<AttachmentOperateTypeBean> operateList = new ArrayList<AttachmentOperateTypeBean>();
//		AttachmentOperateTypeBean attachmentOperateTypeBean = new AttachmentOperateTypeBean();
//		attachmentOperateTypeBean.setOperateType("交通");
//		operateList.add(attachmentOperateTypeBean);
//		attachmentOperateTypeBean = new AttachmentOperateTypeBean();
//		attachmentOperateTypeBean.setOperateType("住宿");
//		operateList.add(attachmentOperateTypeBean);
//		attachmentOperateTypeBean = new AttachmentOperateTypeBean();
//		attachmentOperateTypeBean.setOperateType("餐饮");
//		operateList.add(attachmentOperateTypeBean);
//		attachmentOperateTypeBean = new AttachmentOperateTypeBean();
//		attachmentOperateTypeBean.setOperateType("办公");
//		operateList.add(attachmentOperateTypeBean);
//		attachmentOperateTypeBean = new AttachmentOperateTypeBean();
//		attachmentOperateTypeBean.setOperateType("其他");
//		operateList.add(attachmentOperateTypeBean);
//		return operateList;
//	}
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		// TODO Auto-generated method stub
//		if (position != attachmentList.size()) {
//			attachmentIndex = position;
//			optionMenuInit(Constants.G_TWO, attachmentList.get(position)
//					.getFileName(), attachOptionClick);
//		} else {
//			optionMenuInit(Constants.G_ONE,
//					getString(R.string.stringAddAttachment), addAttachItemClick);
//		}
//	}
//
//	/**
//	 * 操作菜单初始化
//	 * 
//	 * @param type
//	 * @param title
//	 * @param itemClickListener
//	 */
//	private void optionMenuInit(int type, String title,
//			OnItemClickListener itemClickListener) {
//		attachmentOperateDialog = new AttachmentOperateDialog.Builder(this)
//				.setTitle(title).setItems(operateAdapter, itemClickListener)
//				.create();
//		List<AttachmentOperateTypeBean> operateList = attachmentOperateDialog
//				.operateData(type);
//		operateAdapter.dataNotifyChange(operateList);
//		attachmentOperateDialog.show();
//	}
//
//	/**
//	 * 根据uri得到路径
//	 * 
//	 * @param uri
//	 * @return
//	 */
//	private String getRealPath(Uri uri) {
//		String fileName = null;
//		Uri filePathUri = uri;
//		if (uri != null) {
//			if (uri.getScheme().toString().compareTo("content") == 0) // content://开头的uri
//			{
//				Cursor cursor = getContentResolver().query(filePathUri, null,
//						null, null, null);
//				if (cursor != null && cursor.moveToFirst()) {
//					int column_index = cursor
//							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//					fileName = cursor.getString(column_index); // 取出文件路径
//					/**
//					 * if (!fileName.startsWith("/mnt")) { fileName = "/mnt" +
//					 * fileName; }
//					 **/
//					cursor.close();
//				}
//			} else if (filePathUri.getScheme().compareTo("file") == 0) {
//				fileName = filePathUri.toString().replace("file://", "");
//				// 替换file://
//				/**
//				 * if (!fileName.startsWith("/mnt")) { // 加上"/mnt"头 fileName +=
//				 * "/mnt"; }
//				 **/
//			}
//		}
//		return fileName;
//	}
//
//	/**
//	 * 点击附件操作：预览，删除
//	 */
//	OnItemClickListener attachOptionClick = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//				long arg3) {
//			// TODO Auto-generated method stub
//			// 删除
//			if (position == Constants.G_ONE) {
//				attachmentList.remove(attachmentIndex);
//				attachmentChooseAdapter.dataNotifyChange(attachmentList);
//			}
//			// 预览
//			else if (position == Constants.G_ZERO) {
//				final Intent tempintent = new Intent();
//				tempintent.setAction(Intent.ACTION_VIEW);
//				final AttachmentBean bean = attachmentList.get(attachmentIndex);
//				final File file = new File(bean.getLocalPath());
//				// 判断图片是否存在，不存在就先下载到本地
//				if (file.exists()) {
//					Log.d(TAG, "file  exist----" + bean.getIntentType());
//					tempintent.setDataAndType(Uri.fromFile(file),
//							bean.getIntentType());
//					startActivity(tempintent);
//				} else {
//					Log.d(TAG, "file  not exist");
//				}
//
//			}
//			attachmentOperateDialog.dismiss();
//		}
//	};
//
//	/***
//	 * 添加附件操作菜单Itemclick
//	 */
//	OnItemClickListener addAttachItemClick = new OnItemClickListener() {
//
//		@Override
//		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//				long arg3) {
//			// TODO Auto-generated method stub
//			attachmentOperateDialog.dismiss();
//			// 拍照
//			if (position == Constants.G_ZERO) {
//				if (Environment.getExternalStorageState().equals(
//						Environment.MEDIA_MOUNTED)) {
//					// SD卡存在则进行拍照，设置照片的存储路径
//					pickPicName = android.text.format.DateFormat.format(
//							"yyyyMMdd_hhmmss",
//							Calendar.getInstance(Locale.CHINA))
//							+ ".jpg";
//					File photoFile = new File(Constants.SAVE_IMAGE_PATH);
//					if (!photoFile.exists()) {
//						photoFile.mkdirs();
//					}
//
//					// 生成拍照后照片保存的文件
//					File file = new File(photoFile, pickPicName);
//					Uri uri = Uri.fromFile(file);
//
//					// 调用拍照Activity
//					Intent photoIntent = new Intent(
//							MediaStore.ACTION_IMAGE_CAPTURE);
//					photoIntent
//							.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
//					photoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//					startActivityForResult(photoIntent, Constants.G_ZERO);
//				} else {
//					Toast.makeText(ExpenseAccountApplicationActivity.this,
//							"请先插入SD卡", Toast.LENGTH_LONG).show();
//				}
//			}
//			// 选择照片
//			else if (position == Constants.G_ONE) {
//				Intent tempIntent = new Intent();
//				tempIntent.setType("image/*");
//				tempIntent.setAction(Intent.ACTION_GET_CONTENT);
//				startActivityForResult(tempIntent, Constants.G_ONE);
//			}
//			// 其他文件
//			else if (position == Constants.G_TWO) {
//				try {
//					Intent tempIntent = new Intent();
//					tempIntent.setType("application/msword" + "application/pdf"
//							+ "application/vnd.ms-powerpoint"
//							+ "application/vnd.ms-excel");
//					tempIntent.setAction(Intent.ACTION_GET_CONTENT);
//					startActivityForResult(tempIntent, Constants.G_TWO);
//				} catch (Exception e) {
//					Toast.makeText(ExpenseAccountApplicationActivity.this,
//							getString(R.string.stringInstallTools),
//							Toast.LENGTH_SHORT).show();
//				}
//
//			}
//		}
//	};
//
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		Log.e("onActivityResult", "LawyerOrderFragmet");
//		if (resultCode == Activity.RESULT_OK) {
//			switch (requestCode) {
//			case Constants.G_ZERO: {
//				// 拍照文件,原始文件路径
//				try {
//					// 处理图片bitmap size exceeds VM budget
//					BitmapFactory.Options options = new BitmapFactory.Options();
//					options.inJustDecodeBounds = true;
//					// 获取这个图片的宽和高,此时返回bm为空
//					Bitmap compImage = BitmapFactory.decodeFile(
//							Constants.SAVE_IMAGE_PATH + pickPicName, options);
//					options.inJustDecodeBounds = false;
//					// 计算缩放比
//					int be = (int) (options.outHeight / (float) 200);
//					if (be <= 0)
//						be = 1;
//					options.inSampleSize = be;
//
//					// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
//					compImage = BitmapFactory.decodeFile(
//							Constants.SAVE_IMAGE_PATH + pickPicName, options);
//
//					File file = new File(Constants.SAVE_IMAGE_PATH
//							+ pickPicName);
//					FileOutputStream fOut = new FileOutputStream(file);
//					compImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//					fOut.flush();
//					fOut.close();
//					intent = new Intent();
//					AttachmentBean bean = new AttachmentBean();
//					bean.setAppPath(Constants.SAVE_IMAGE_PATH + pickPicName);
//					bean.setLocalPath(Constants.SAVE_IMAGE_PATH + pickPicName);
//					bean.setFileSize(file.length() + "");
//					bean.setFileName(pickPicName);
//					bean.setFileType("jpg");
//					attachmentList.add(bean);
//
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//				break;
//			case Constants.G_ONE: {
//				// 图片文件,原始文件路径
//				try {
//					String realPath = getRealPath(data.getData());
//					Bitmap realImage = ImageCompressUtil.getimage(realPath);
//					String appPath = Constants.SAVE_IMAGE_PATH
//							+ realPath.substring(realPath.lastIndexOf("/") + 1);
//					File file = new File(appPath);
//					FileOutputStream fOut = new FileOutputStream(file);
//					realImage.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//					fOut.flush();
//					fOut.close();
//					intent = new Intent();
//					AttachmentBean bean = new AttachmentBean();
//					bean.setAppPath(appPath);
//					bean.setLocalPath(realPath);
//					bean.setFileSize(file.length() + "");
//					bean.setFileName(realPath.substring(realPath
//							.lastIndexOf("/") + 1));
//					bean.setFileType(realPath.substring(realPath
//							.lastIndexOf(".") + 1));
//					attachmentList.add(bean);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//				break;
//			case Constants.G_TWO: {
//				// 其他文件，如果是图片则拷贝压缩，如果是文件则不做任何处理
//				try {
//					if (data == null) {
//						break;
//					}
//					AttachmentBean bean = new AttachmentBean();
//					Uri uri = data.getData();
//					String realPath = URLDecoder.decode(getRealPath(uri),
//							"UTF-8");
//					File file = new File(realPath);
//					bean.setFileSize(file.length() + "");
//					bean.setFileName(realPath.substring(realPath
//							.lastIndexOf("/") + 1));
//					bean.setFileType(realPath.substring(realPath
//							.lastIndexOf(".") + 1));
//
//					String strFileType = bean.getFileType();
//					if (strFileType.equals("jpg") || strFileType.equals("png")
//							|| strFileType.equals("bmp")
//							|| strFileType.equals("pdf")
//							|| strFileType.equals("xls")
//							|| strFileType.equals("xlsx")
//							|| strFileType.equals("doc")
//							|| strFileType.equals("docx")
//							|| strFileType.equals("txt")
//							|| strFileType.equals("ppt")
//							|| strFileType.equals("pptx")) {
//						if (file.length() == 0) {
//							Toast.makeText(
//									ExpenseAccountApplicationActivity.this,
//									"当前文件已损坏，无法作为附件", Toast.LENGTH_SHORT)
//									.show();
//						} else if (file.length() > 10 * 1024 * 1024) {
//							Toast.makeText(
//									ExpenseAccountApplicationActivity.this,
//									"当前文件超过10M，无法作为附件", Toast.LENGTH_SHORT)
//									.show();
//						} else {
//							if (realPath.endsWith(".jpg")
//									|| realPath.endsWith(".png")
//									|| realPath.endsWith(".jpeg")
//									|| realPath.endsWith(".bmp")
//									|| realPath.endsWith(".gif")) {
//								String appPath = Constants.SAVE_IMAGE_PATH
//										+ ".jpg";
//								Bitmap realImage = ImageCompressUtil
//										.getimage(realPath);
//								file = new File(appPath);
//								FileOutputStream fOut = new FileOutputStream(
//										file);
//								realImage.compress(Bitmap.CompressFormat.JPEG,
//										100, fOut);
//								fOut.flush();
//								fOut.close();
//								bean.setAppPath(appPath);
//								bean.setFileSize(file.length() + "");
//							} else {
//								SimpleDateFormat sdf = new SimpleDateFormat(
//										"yyyyMMddHHmmssSSS");
//								String appPath = Constants.SAVE_IMAGE_PATH
//										+ sdf.format(new Date()) + "."
//										+ strFileType;
//								file = new File(appPath);
//								FileOutputStream fOut = new FileOutputStream(
//										file);
//								ContentResolver cr = getContentResolver();
//								InputStream fIn = cr.openInputStream(uri);
//								byte[] readBytes = new byte[1024];
//								int readLength = fIn.read(readBytes);
//								while (readLength != -1)// 读取数据到文件输出流
//								{
//									fOut.write(readBytes, 0, readLength);
//									fOut.flush();
//									readLength = fIn.read(readBytes);
//								}
//								// 关闭相关对象
//								fIn.close();
//								fOut.flush();
//								fOut.close();
//								bean.setAppPath(appPath);
//							}
//							bean.setLocalPath(realPath);
//							attachmentList.add(bean);
//						}
//					} else {
//						Toast.makeText(ExpenseAccountApplicationActivity.this,
//								"暂不支持该格式的文件作为附件", Toast.LENGTH_SHORT).show();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//				break;
//			default:
//				break;
//			}
//		}
//		attachmentChooseAdapter.changeDataNotify(attachmentList);
//	}
//
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		super.onDestroy();
//	}
//
//	/**
//	 * 实现实位回调监听
//	 */
//	public class MyLocationListener implements BDLocationListener {
//
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			// Receive Location
//
//			m_dLontitude = location.getLongitude();
//			m_dLatitude = location.getLatitude();
//
//			String m_strAddress = location.getAddrStr();
//			if (null != m_strAddress) {
//				postionTv.setText(m_strAddress);
//			}
//
//			// if(m_strAddress!=null&&!m_strAddress.equals("")&&m_strAddress.contains("省")){
//			// String[] adress=m_strAddress.split("省");
//			// if(adress.length>1){
//			// postionTv.setText("位置:"+adress[1]);
//			// }
//			// }else{
//			// postionTv.setText("位置:"+m_strAddress);
//			// }
//		}
//	}
//
//}
