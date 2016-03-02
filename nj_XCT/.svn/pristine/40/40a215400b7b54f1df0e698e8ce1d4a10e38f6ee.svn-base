package com.xguanjia.hx.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncDownloadFile1 extends AsyncTask<String, Integer, ActionResponse> {
	private final String TAG = "AsyncDownloadFile";
	private SharedPreferences sf;
	private Context mContext;
	// private int downloadSize = 0; //已下载的文件大小
	// private int fileSize = -1; //文件大小
	private String fileUrl; // 文件远程路径
	private String filePath; // 文件本地存储路径
	ServiceSyncListener listener; // 回调监听器
	private boolean isCancle = false; // 是否取消下载 false 下载 true 下载取消
	private int count = 0;
	
	public AsyncDownloadFile1(Context context) {
		mContext = context;
	}

	@Override
	protected ActionResponse doInBackground(String... params) {
		// TODO 下载处理操作 1,判断文件远程地址和本地存储路径是否为空，2判断是否取消，3下载处理
		String message = "下载文件开始";
		int code = -1;
		ActionResponse returnObject = new ActionResponse(code, message);
		Log.i(TAG, "fileUrl:" + fileUrl);
		Log.i(TAG, "filePath:" + filePath);
		if (null == fileUrl || "".equals(fileUrl)) {
			returnObject.setMessage("文件远程路径为空");
			return returnObject;
		}
		if (null == filePath || "".equals(filePath)) {
			returnObject.setMessage("文件存储路径为空");
			return returnObject;
		}

		HttpURLConnection conn = null;
		File file = new File(filePath);
		try {
			// 创建存储文件
			file.getParentFile().mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			if (file.isDirectory()) {
				returnObject.setMessage("文件存储路径不对");
				returnObject.setCode(-1);
				return returnObject;
			}
			file.getParentFile().mkdirs();
			file.createNewFile();
			OutputStream out = new FileOutputStream(file);
			// 建立远程连接
			URL url = new URL(fileUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Accept", "application/octet-stream");
			conn.connect();
			InputStream in = conn.getInputStream();
			int read = -1;
			byte[] cache = new byte[1024 * 10]; // 文件缓冲区大小 1K
			while (!isCancle && ((read = in.read(cache)) != -1)) {
				count = count + read;
				Log.i(TAG, "read:" + read);
				out.write(cache, 0, read);
			}
			in.close();
			if (null != out) {
				out.flush();
				out.close();
			}
			returnObject.setCode(0);
			returnObject.setMessage("文件下载成功");
			returnObject.setData(file);
		} catch (MalformedURLException e) {
			file.delete();
			returnObject.setCode(-1);
			returnObject.setMessage("请检查文件远程地址");
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			file.delete();
			returnObject.setCode(-1);
			returnObject.setMessage("服务器上没有该文件");
			e.printStackTrace();
		} catch (IOException e) {
			file.delete();
			returnObject.setCode(-1);
			returnObject.setMessage("请检查网络或服务器");
			e.printStackTrace();
		} finally {
			if (null != conn) {
				conn.disconnect();
			}
		}

		return returnObject;
	}

	@Override
	protected void onCancelled() {
		this.isCancle = true;
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(ActionResponse returnObject) {
		if (returnObject.getCode() == 0) {
			listener.onSuccess(returnObject);
			return;
		}
		listener.onError(returnObject);
	}

	/**
	 * 下载地址，要做地址的统一修改路径配置
	 */
	@Override
	protected void onPreExecute() {
		
		// 远程地址前加上服务器地址(远程地址为相对地址)
		if (null != fileUrl && !("".equals(fileUrl))) {
			if (fileUrl.contains("http")) {
				return;
			}
			sf = mContext.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			fileUrl = "http://" +sf.getString("ip", Constants.IP) +fileUrl;
		}
		super.onPreExecute();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	public String getFileUrl() {
		return fileUrl;
	}

	/**
	 * 设置下载文件的地址
	 * 
	 * @param fileUrl
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFilePath() {
		return filePath;
	}

	/**
	 * 设置文件本地存储路径
	 * 
	 * @param filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ServiceSyncListener getListener() {
		return listener;
	}

	/**
	 * 设置回调监听接口
	 * 
	 * @param listener
	 */
	public void setListener(ServiceSyncListener listener) {
		this.listener = listener;
	}

}
