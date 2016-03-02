package com.xguanjia.hx.filecabinet.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.chinamobile.salary.R;

public class FileBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 773207771037050338L;

	private static String TAG = "FileBean";

	private String def = "0";
	private String id = "-1"; // 文件夹ID
	private String folderName = ""; // 文件夹名称
	private String parentId = ""; // 上级文件夹ID
	private String userId = ""; // 父级节点，有此值
	private String fileName = ""; // 文件名
	private String filePath = ""; // 文件路径 本地存储路径
	private String fileUrl = ""; // 文件远程存储路径
	private String fileSize = ""; // 文件大小
	private String subFiles = ""; // 子文件个数
	private String releaseDate = ""; // 文件创建时间,父级节点，有此值
	private int fileIcon = -1; // 文件图标
	private String isDirectory = "false"; // 判断是文件目录还是文件夹 true :文件夹 false:文件
	private String fileType = ""; // 区分文件还是文件夹
	private String bool = ""; // 0 文件夹
	private String fileId = ""; // 文件id

	private String ispublic = ""; // 1 为公共 0 为个人

	private String isShare = "0";

	// 分享时间
	private String shareTime = "";

	// 分享人姓名
	private String shareUserName = "";

	public String getDef() {
		return def;
	}

	public void setDef(String def) {
		this.def = def;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getIsDirectory() {
		return isDirectory;
	}

	public void setIsDirectory(String isDirectory) {
		this.isDirectory = isDirectory;
	}

	public String getBool() {
		return bool;
	}

	public void setBool(String bool) {
		this.bool = bool;
	}

	private String intentType = "";

	public String getIntentType() {
		return intentType;
	}

	public void setIntentType(String intentType) {
		this.intentType = intentType;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getIsShare() {
		return isShare;
	}

	public void setIsShare(String isShare) {
		this.isShare = isShare;
	}

	public String getShareTime() {
		return shareTime;
	}

	public void setShareTime(String shareTime) {
		this.shareTime = shareTime;
	}

	public String getShareUserName() {
		return shareUserName;
	}

	public void setShareUserName(String shareUserName) {
		this.shareUserName = shareUserName;
	}

	public FileBean() {
	}

	public FileBean(JSONObject data) {
		if (null != data) {

			try {
				if (!data.isNull("id")) {
					this.id = data.getString("id");
				}

				if (!data.isNull("fileName")) {
					this.fileName = data.getString("fileName");
					setFileType(fileName);
				}

				if (!data.isNull("filePath")) {
					this.filePath = data.getString("filePath");
				}

				if (!data.isNull("fileUrl")) {
					this.fileUrl = data.getString("fileUrl");
				}

				if (!data.isNull("releaseDate")) {
					this.releaseDate = data.getString("releaseDate");
				}

				if (!data.isNull("fileSize")) {
					this.fileSize = data.getString("fileSize");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
		setFileType(fileName);
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getSubFiles() {
		return subFiles;
	}

	public void setSubFiles(String subFiles) {
		this.subFiles = subFiles;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public int getFileIcon() {
		return fileIcon;
	}

	public void setFileIcon(int fileIcon) {
		this.fileIcon = fileIcon;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileName) {
		if (null != fileName) {
			if (fileName.contains(".")) {
				this.fileType = fileName
						.substring(fileName.lastIndexOf(".") + 1);
			} else {
				fileType = fileName;
			}
			setFileIcon(fileType);
		}
	}

	public void setFileIcon(String fileType) {
		if (null == fileType) {
			return;
		} else if ("apk".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.android.package-archive");
			this.fileIcon = R.drawable.file_apk_icon;
		} else if ("ai".equalsIgnoreCase(fileType)) {
			this.fileIcon = R.drawable.file_ai_icon;
		} else if ("wav".equalsIgnoreCase(fileType)
				|| "ogg".equalsIgnoreCase(fileType)
				|| "mp3".equalsIgnoreCase(fileType)
				|| "m4a".equalsIgnoreCase(fileType)
				|| "ogg".equalsIgnoreCase(fileType)) {
			setIntentType("audio");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("3gp".equalsIgnoreCase(fileType)
				|| "mp4".equalsIgnoreCase(fileType)) {
			setIntentType("video");
			this.fileIcon = R.drawable.file_audio_icon;
		} else if ("rar".equalsIgnoreCase(fileType)) {
			setIntentType("application/x-rar-compressed");
			this.fileIcon = R.drawable.file_compressed_icon_rar;
		} else if ("zip".equalsIgnoreCase(fileType)) {
			setIntentType("application/zip");
			this.fileIcon = R.drawable.file_compressed_icon_7;
		} else if ("csv".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_csv_icon;
		} else if ("fla".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_fla_icon;
		} else if ("exe".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_exe_icon;
		} else if ("html".equalsIgnoreCase(fileType)
				|| "htm".equalsIgnoreCase(fileType)) {
			setIntentType("text/html");
			this.fileIcon = R.drawable.file_html_icon;
		} else if ("java".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_java_icon;
		} else if ("pdf".equalsIgnoreCase(fileType)) {
			setIntentType("application/pdf");
			this.fileIcon = R.drawable.file_pdf_icon;
		} else if ("jpg".equalsIgnoreCase(fileType)
				|| "jpeg".equalsIgnoreCase(fileType)
				|| "png".equalsIgnoreCase(fileType)
				|| "bmp".equalsIgnoreCase(fileType)
				|| "gif".equalsIgnoreCase(fileType)) {
			setIntentType("image/*");
			this.fileIcon = R.drawable.file_picture_icon;
		} else if ("ppt".equalsIgnoreCase(fileType)
				|| "pptx".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.ms-powerpoint");
			this.fileIcon = R.drawable.file_ppt_icon;
		} else if ("psd".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_psd_picture_icon;
		} else if ("xls".equalsIgnoreCase(fileType)
				|| "xlsx".equalsIgnoreCase(fileType)) {
			setIntentType("application/vnd.ms-excel");
			this.fileIcon = R.drawable.file_xls_icon;
		} else if ("doc".equalsIgnoreCase(fileType)
				|| "docx".equalsIgnoreCase(fileType)) {
			setIntentType("application/msword");
			this.fileIcon = R.drawable.file_word_icon;
		} else if ("text".equalsIgnoreCase(fileType)
				|| "txt".equalsIgnoreCase(fileType)) {
			setIntentType("text/plain");
			this.fileIcon = R.drawable.file_txt_icon;
		} else if ("swf".equalsIgnoreCase(fileType)) {
			// setIntentType();
			this.fileIcon = R.drawable.file_swf_icon;
		}
	}

	private static FileBean filesToFileBean(String parentId, JSONObject data) {
		FileBean fileBean = new FileBean();
		fileBean.setIsDirectory("false");
		fileBean.setParentId(parentId);
		try {

			if (!data.isNull("fileId")) {
				String fileId = data.getString("fileId");
				fileBean.setFileId(fileId);
			}

			if (!data.isNull("fileName")) {
				String fileName = data.getString("fileName");
				fileBean.setFileType(fileName);
				fileBean.setFileName(fileName);
			}

			if (!data.isNull("filePath")) {
				String filePath = data.getString("filePath");
				fileBean.setFilePath(filePath);
			}

			if (!data.isNull("fileUrl")) {
				String fileUrl = data.getString("fileUrl");
				fileBean.setFileUrl(fileUrl);
			}

			if (!data.isNull("releaseDate")) {
				String releaseDate = data.getString("releaseDate");
				fileBean.setReleaseDate(releaseDate);
			}

			if (!data.isNull("fileSize")) {
				String fileSize = data.getString("fileSize");
				fileBean.setFileSize(fileSize);

			}

		} catch (JSONException e) {
			Log.e(TAG, e.getMessage(), e);
		}

		return fileBean;
	}

	/**
	 * json报文转共享文件list
	 * 
	 * @param data
	 * @return
	 */
	public static List<FileBean> jsonToShareFileBean(JSONArray data) {
		List<FileBean> filesList = null;
		if (null == data || "{}".equals(data)) {
			return filesList;
		}
		try {
			filesList = new ArrayList<FileBean>();
			for (int i = 0; i < data.length(); i++) {
				String id = "";
				JSONObject mdir = data.getJSONObject(i);
				if (!mdir.isNull("file")) {
					String parentId = id;
					JSONArray ary = mdir.getJSONArray("file");
					for (int j = 0; j < ary.length(); j++) {
						JSONObject jsonObj = ary.getJSONObject(j);
						FileBean bean = filesToFileBean(parentId, jsonObj);
						if (!mdir.isNull("id")) {
							id = mdir.getString("id");
							bean.setId(mdir.getString("id"));
						}
						if (!mdir.isNull("shareTime")) {
							bean.setShareTime(mdir.getString("shareTime"));
						}
						if (!mdir.isNull("shareUserName")) {
							bean.setShareUserName(mdir
									.getString("shareUserName"));
						}
						bean.setSubFiles("0");
						filesList.add(bean);
					}
				}
			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return filesList;

	}

	/**
	 * json报文转List
	 * 
	 * @param data
	 * @return
	 */
	public static List<FileBean> jsonToFileBean(JSONArray data) {
		List<FileBean> filesList = null;
		if (null == data) {
			return filesList;
		}
		try {
			filesList = new ArrayList<FileBean>();
			for (int i = 0; i < data.length(); i++) {
				String id = "";
				JSONObject mdir = data.getJSONObject(i);
				FileBean fileBean = new FileBean();
				fileBean.setIsDirectory("true");
				if (!mdir.isNull("id")) {
					id = mdir.getString("id");
					fileBean.setId(mdir.getString("id"));
				}
				if (!mdir.isNull("parentId")) {
					fileBean.setParentId(mdir.getString("parentId"));

				}
				if (!mdir.isNull("createDate")) {
					fileBean.setReleaseDate(mdir.getString("createDate"));
				}
				if (!mdir.isNull("default")) {
					fileBean.setDef(mdir.getString("default"));
				}
				if (!mdir.isNull("userId")) {
					fileBean.setUserId(mdir.getString("userId"));
				}
				if (!mdir.isNull("name")) {
					fileBean.setFolderName(mdir.getString("name"));
					fileBean.setFileName(mdir.getString("name"));
				}
				if (!mdir.isNull("shareTime")) {
					fileBean.setShareTime(mdir.getString("shareTime"));
				}
				if (!mdir.isNull("shareUserName")) {
					fileBean.setShareTime(mdir.getString("shareUserName"));
				}
				fileBean.setSubFiles("0");
				filesList.add(fileBean);
				if (!mdir.isNull("files")) {
					String parentId = id;
					JSONArray ary = mdir.getJSONArray("files");
					for (int j = 0; j < ary.length(); j++) {
						JSONObject jsonObj = ary.getJSONObject(j);
						FileBean bean = filesToFileBean(parentId, jsonObj);
						;
						filesList.add(bean);
					}

				}

			}

		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		}
		return filesList;
	}

	/**
	 * 统计子文件个数
	 */
	public static void countSubFile(List<FileBean> list) {
		Map<String, FileBean> map = new HashMap<String, FileBean>();
		for (Iterator<FileBean> it = list.iterator(); it.hasNext();) {
			FileBean fb = it.next();
			map.put(fb.getId(), fb);
		}

		for (Iterator<FileBean> it = list.iterator(); it.hasNext();) {
			FileBean fb = it.next();
			String subFiles = fb.getSubFiles();
			if (subFiles == null) {
				fb.setSubFiles("0");
			}
			String pid = fb.getParentId();
			FileBean bean = map.get(pid);
			if (bean != null) {
				String childres = bean.getSubFiles();
				if (!"".equals(childres)) {
					int count = Integer.parseInt(childres);
					count++;
					bean.setSubFiles(String.valueOf(count));
				}
			}

		}
	}
}
