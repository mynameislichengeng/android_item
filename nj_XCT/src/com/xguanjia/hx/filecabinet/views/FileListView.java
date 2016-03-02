package com.xguanjia.hx.filecabinet.views;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AsyncDownloadFile;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MAMessage;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.filecabinet.adaptor.FileListAdaptor;
import com.xguanjia.hx.filecabinet.pojo.FileBean;
import com.xguanjia.hx.login.activity.TeLoginActivity;

public class FileListView extends ListView {
	private final String TAG = "FileListView";
	private FileListAdaptor adaptor;
	public List<FileBean> data = new ArrayList<FileBean>();
	private Context context;
	private Handler mHandler;
	private ProgressDialog pd;
	private long viewId;
	private String fileType = "0"; // 0 个人文件 1 公共文件 2共享文件
	private boolean isPublic = false; // false 个人文件 true 公共文件
	private SQLiteDatabase db;
	private String parentId;

	Cursor cursor = null;

	public FileListView(SQLiteDatabase db, Context mContext, List<FileBean> mdata) {
		super(mContext);
		this.db = db;
		context = mContext;
		data = mdata;
		adaptor = new FileListAdaptor(context, data);
		this.setCacheColorHint(Color.alpha(00000000));
		this.setAdapter(adaptor);
		viewId = System.currentTimeMillis();
		// 设置listView分割线
		Drawable divider = getResources().getDrawable(R.drawable.line);
		this.setDivider(divider);
	}

	public FileListView(Context mContext) {
		super(mContext);
		context = mContext;
	}

	public FileListView(SQLiteDatabase db, Context mContext) {
		super(mContext);
		this.db = db;
		viewId = System.currentTimeMillis();
		context = mContext;
		this.setCacheColorHint(Color.alpha(00000000));
		adaptor = new FileListAdaptor(context);
		setAdapter(adaptor);
		// 更新界面
		mHandler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				if (null != pd) {
					pd.dismiss();
				}
				switch (msg.what) {
					// 创建文件列表视图
					case 1 :
						if (null != data) {
							FileListView.this.setFocusable(false);
							adaptor.setData(data);
							adaptor.setFileType(fileType);
							adaptor.notifyDataSetChanged();
							FileListView.this.setFocusable(true);
							// ((FileList_V1Activity)context).setBottomDividingLine(data);
						}
						break;
					// TODO 刷新文件列表视图
					case 2 :
						break;
				}
			}

		};
		// 设置listView分割线
		Drawable divider = getResources().getDrawable(R.drawable.line);
		this.setDivider(divider);

	}

	public void setData(List<FileBean> data) {
		if (null != adaptor) {
			adaptor.setData(data);
			this.setFocusable(false);
			adaptor.notifyDataSetChanged();
			this.setFocusable(true);
		}
	}

	/**
	 * 从本地数据库加载数据
	 */
	public void getFileListLocal(String parentId, String isPublic) {
		data = getFileBeanDataFromDB(parentId, isPublic);
		System.out.println("--->" + data.size());
		mHandler.sendEmptyMessage(1);
	}

	/**
	 * 从服务器下载数据
	 */
	public void getFileListRemote(final String lastFolderTime, final String isPublic) {
		pd = ProgressDialog.show(context, "", "数据刷新中...", true, true);
		HashMap<String, Object> requestMap = new HashMap<String, Object>();
		requestMap.put("userId", Constants.userId);
		String method = "";
		if ("2".equals(isPublic)) {
			method = Constants.UrlHead+"client.action.FileCabinetAction$synchronousShareFile";
		} else {
			requestMap.put("isPublic", isPublic);
			requestMap.put("lastFolderTime", "");
			method = Constants.UrlHead+"client.action.FileCabinetAction$getFolder";
		}
		db.beginTransaction();
		try {
			ServerAdaptor.getInstance(context).doAction(1, method, requestMap, new ServiceSyncListener() {
				public void onError(ActionResponse returnObject) {
					if (null != pd) {
						pd.dismiss();
					}
					super.onError(returnObject);
					Log.i(TAG, returnObject.getMessage());
					if (returnObject.getCode() == 3) {
						dialog();
					}
				}

				public void onSuccess(ActionResponse returnObject) {
					Object jsonData = returnObject.getData();
					Log.i(TAG, "jsonData:" + jsonData.toString());
					if ("2".equals(isPublic)) {
						if ("{}".equals(jsonData.toString())) {
							deleteFoldersData(isPublic);
						}
					}
					if (jsonData != null && jsonData instanceof JSONArray) {
						if ("2".equals(isPublic)) {
							data = FileBean.jsonToShareFileBean((JSONArray) jsonData);
							deleteFoldersData(isPublic);
						} else {
							data = FileBean.jsonToFileBean((JSONArray) jsonData);
							FileBean.countSubFile(data);
							if (data.size() > 0) {
								deleteFoldersData(isPublic);
							}
						}
						db.beginTransaction();
						for (int i = 0; i < data.size(); i++) {
							String key = UUID.randomUUID().toString().replace("-", "");
							db
									.execSQL(
											"insert into tb_contact_folders(primary_id,userId,id,folderName,fileName,subFiles,fileSize,fileUrl,fileId,releaseDate,bool,parentId,isDirectory,isPublic,isShare,shareTime,shareUserName) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
											new Object[]{key, Constants.userId, data.get(i).getId(), data.get(i).getFolderName(), data.get(i).getFileName(), data.get(i).getSubFiles(),
													data.get(i).getFileSize(), data.get(i).getFileUrl(), data.get(i).getFileId(), data.get(i).getReleaseDate(), data.get(i).getIspublic(),
													data.get(i).getParentId(), data.get(i).getIsDirectory(), isPublic, data.get(i).getIsShare(), data.get(i).getShareTime(),
													data.get(i).getShareUserName()});
						}
					}
					data.clear();
					data = getFileBeanDataFromDB(parentId, isPublic);

					if (pd != null && pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
			// 调用此方法会在执行到endTransaction() 时提交当前事务，
			db.setTransactionSuccessful();
			mHandler.sendEmptyMessage(1);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			// 由事务的标志决定是提交事务，还是回滚事务
			db.endTransaction();
		}

	}

	public void searchFile(String fileName, final String isPublic) {
		List<FileBean> mData = new ArrayList<FileBean>();
		if (fileName == null) {
			mHandler.sendEmptyMessage(1);
			return;
		} else {
			fileName = fileName.trim();
			fileName = '%' + fileName + '%';
		}
		try {
			StringBuilder sql = new StringBuilder("select * from tb_contact_folders where userId = ? and isPublic = ? and fileName LIKE ?");
			cursor = db.rawQuery(sql.toString(), new String[]{Constants.userId, isPublic, fileName});
			// 循环处理
			while (cursor.moveToNext()) {
				FileBean bean = new FileBean();
				bean.setId(cursor.getString(cursor.getColumnIndex("id")));
				bean.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
				bean.setSubFiles(cursor.getString(cursor.getColumnIndex("subFiles")));
				bean.setFileSize(cursor.getString(cursor.getColumnIndex("fileSize")));
				bean.setFileUrl(cursor.getString(cursor.getColumnIndex("fileUrl")));
				bean.setFileId(cursor.getString(cursor.getColumnIndex("fileId")));
				bean.setIsShare(cursor.getString(cursor.getColumnIndex("isShare")));
				bean.setShareTime(cursor.getString(cursor.getColumnIndex("shareTime")));
				bean.setShareUserName(cursor.getString(cursor.getColumnIndex("shareUserName")));
				bean.setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")));
				bean.setIsDirectory(cursor.getString(cursor.getColumnIndex("isDirectory")));
				System.out.println("cursor.getString(cursor.getColumnIndex(isDirectory))" + cursor.getString(cursor.getColumnIndex("isDirectory")));
				mData.add(bean);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}

		}
		data.clear();
		data = mData;
		mHandler.sendEmptyMessage(1);
	}

	/**
	 * 获取文件列表
	 * 
	 * @param isPublic
	 *            true ：公共文件 false ： 个人文件 重构改方法
	 */
	public void getFileListAndUpdateUI(final String parentId, final String isPublic) {
		if ("".equals(parentId)) {
			getFileListRemote(parentId, isPublic);
		} else {
			getFileListLocal(parentId, isPublic);
		}
	}

	public void deleteFoldersData(String isPublic) {
		try {
			db.delete("tb_contact_folders", "isPublic = ?", new String[]{isPublic});
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}

	}

	/**
	 * 从数据库查询数据，需要考虑 isPublic
	 * 
	 * @param parentId
	 * @param isPublic
	 * @return
	 */
	public List<FileBean> getFileBeanDataFromDB(String parentId, String isPublic) {
		List<FileBean> mData = new ArrayList<FileBean>();
		try {
			if ("2".equals(isPublic)) {
				StringBuilder sql = new StringBuilder("select * from tb_contact_folders where userId = ? and isPublic = 2");
				cursor = db.rawQuery(sql.toString(), new String[]{Constants.userId});
			} else if ("".equals(parentId)) {
				StringBuilder sql = new StringBuilder("select * from tb_contact_folders where userId = ? and isPublic = ? and parentId IS NULL");
				cursor = db.rawQuery(sql.toString(), new String[]{Constants.userId, isPublic});
				// 文件柜提级
				List<FileBean> list = getFileBean(cursor);

				if (list.size() > 0) {
					cursor.close();
					String pId = list.get(0).getId();
					return getFileBeanDataFromDB(pId, isPublic);
				}
			} else {
				StringBuilder sql = new StringBuilder("select * from tb_contact_folders where userId = ? and isPublic = ? and parentId = ?");
				cursor = db.rawQuery(sql.toString(), new String[]{Constants.userId, isPublic, parentId});
			}
			return getFileBean(cursor);
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}

		}
		return mData;

	}
	/**
	 * 获取 List<FileBean>
	 * 
	 * @param cursor
	 * @return mData
	 */
	private List<FileBean> getFileBean(Cursor cursor) {
		List<FileBean> mData = new ArrayList<FileBean>();
		// 循环处理
		while (cursor.moveToNext()) {
			FileBean bean = new FileBean();
			bean.setId(cursor.getString(cursor.getColumnIndex("id")));
			bean.setFileName(cursor.getString(cursor.getColumnIndex("fileName")));
			bean.setSubFiles(cursor.getString(cursor.getColumnIndex("subFiles")));
			bean.setFileSize(cursor.getString(cursor.getColumnIndex("fileSize")));
			bean.setFileUrl(cursor.getString(cursor.getColumnIndex("fileUrl")));
			bean.setFileId(cursor.getString(cursor.getColumnIndex("fileId")));
			bean.setIsShare(cursor.getString(cursor.getColumnIndex("isShare")));
			bean.setShareTime(cursor.getString(cursor.getColumnIndex("shareTime")));
			bean.setShareUserName(cursor.getString(cursor.getColumnIndex("shareUserName")));
			bean.setReleaseDate(cursor.getString(cursor.getColumnIndex("releaseDate")));
			bean.setIsDirectory(cursor.getString(cursor.getColumnIndex("isDirectory")));
			System.out.println("cursor.getString(cursor.getColumnIndex(isDirectory))" + cursor.getString(cursor.getColumnIndex("isDirectory")));
			mData.add(bean);
		}
		return mData;
	}

	/**
	 * 下载文件，下载成功后调用系统工具打开文件
	 * 
	 * @param fileBean
	 */
	public void downloadFile(final FileBean fileBean) {
		if (null == ((BaseActivity) context).getSDcard()) {
			MAMessage.ShowMessage(context, R.string.prompt_message, R.string.please_check_sd);
			return;
		}

		pd = ProgressDialog.show(context, "", "数据获取中", true, true);
		AsyncDownloadFile download = new AsyncDownloadFile(this.context);
		download.setFileUrl(fileBean.getFileUrl());
		download.setFilePath(fileBean.getFileName());
		download.setListener(new ServiceSyncListener() {

			@Override
			public void onError(ActionResponse returnObject) {
				Log.i(TAG, "error" + returnObject.getMessage());
				MAMessage.ShowMessage(context, R.string.download_file_faile, returnObject.getMessage());
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

			@Override
			public void onSuccess(ActionResponse returnObject) {
				Log.i(TAG, "success" + returnObject.getMessage());
				if (null == returnObject.getData()) {
					if (((Activity) context).isTaskRoot()) {
						MAMessage.ShowMessage(context, R.string.download_file_faile, returnObject.getMessage());
					}

				}
				File file = (File) returnObject.getData();
				try {
					String intentType = fileBean.getIntentType();
					if (null == intentType || "".equals(intentType)) {
						if (((Activity) context).isTaskRoot()) {
							MAMessage.ShowMessage(context, R.string.download_file_faile, "不支持的格式");
						}

						return;
					}
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.fromFile(file), intentType);
					context.startActivity(intent);
				} catch (ActivityNotFoundException e) {
					MAMessage.ShowMessage(context, R.string.download_file_faile, "打开出错了");
				}
				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}

		});
		download.execute("");
	}

	@Override
	public boolean equals(Object o) {
		if (null == o) {
			return false;
		}
		if (!(o instanceof FileListView)) {
			return false;
		}
		FileListView v = (FileListView) o;
		if (v.getViewId() == this.viewId) {
			return true;
		}
		return false;

	}

	public List<FileBean> getData() {
		return data;
	}

	public long getViewId() {
		return viewId;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * 会话过期返回登录界面
	 * 
	 * @param context
	 */
	public void dialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("会话已过期请重新登录");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 执行登录操作
				Intent intent = new Intent();
				intent.setClass(context, TeLoginActivity.class);
				intent.putExtra("sessionOverdue", true);
				context.startActivity(intent);
			}
		});
		builder.create().show();
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

}
