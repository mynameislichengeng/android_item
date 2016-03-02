package com.xguanjia.hx.filecabinet.db;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.filecabinet.pojo.FileBean;

public class FilecabinetDb {
	private DatabaseHelper dbOpenHelper;

	public FilecabinetDb(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 
	 * @param nodeId
	 * @param isChildOrParent
	 *            0表示往下级查询，1表示往上查询
	 * @param fileType
	 *            0表示我的文件柜，1表示公共文件柜，2表示分享文件柜
	 * @return
	 */
	public List<FileBean> getFileList(String nodeId, int isChildOrParent,
			String fileType) {
		List<FileBean> fileList = new ArrayList<FileBean>();
		Cursor cursor = null;
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder(
				"select * from tb_contact_folders where userId = ? and isPublic = ? and ");
		if ("".equals(nodeId) && !"2".equals(fileType)) {
			if (0 == isChildOrParent) {
				sql.append("parentId ='0'");
			} else if (1 == isChildOrParent) {
				sql.append("id = (select id from tb_contact_folders where userId = ? and isPublic = ? and parentId = '')");
			}
		} else {
			if (0 == isChildOrParent) {
				sql.append("parentId = ?");
			} else if (1 == isChildOrParent) {
				sql.append("id = ?");
			}
		}
		try {
			if ("".equals(nodeId) && !"2".equals(fileType)) {
				cursor = database.rawQuery(sql.toString(), new String[] {
						Constants.userId, fileType });
			} else {
				cursor = database.rawQuery(sql.toString(), new String[] {
						Constants.userId, fileType, nodeId });
			}
			while (cursor.moveToNext()) {
				FileBean bean = new FileBean();
				bean.setId(cursor.getString(cursor.getColumnIndex("id")));
				// xx
				bean.setDef(cursor.getString(cursor.getColumnIndex("def")));
				bean.setParentId(cursor.getString(cursor
						.getColumnIndex("parentId")));
				bean.setFileName(cursor.getString(cursor
						.getColumnIndex("fileName")));
				bean.setSubFiles(cursor.getString(cursor
						.getColumnIndex("subFiles")));
				bean.setFileSize(cursor.getString(cursor
						.getColumnIndex("fileSize")));
				bean.setFileUrl(cursor.getString(cursor
						.getColumnIndex("fileUrl")));
				bean.setFileId(cursor.getString(cursor.getColumnIndex("fileId")));
				bean.setIsShare(cursor.getString(cursor
						.getColumnIndex("isShare")));
				bean.setShareTime(cursor.getString(cursor
						.getColumnIndex("shareTime")));
				bean.setShareUserName(cursor.getString(cursor
						.getColumnIndex("shareUserName")));
				bean.setReleaseDate(cursor.getString(cursor
						.getColumnIndex("releaseDate")));
				bean.setIsDirectory(cursor.getString(cursor
						.getColumnIndex("isDirectory")));
				fileList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
		return fileList;
	}

	/**
	 * 保存文件
	 * 
	 * @param fileList
	 */
	public void saveFileList(List<FileBean> fileList, String isPublic) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			database.execSQL(
					"delete from tb_contact_folders where isPublic = ? and userId = ?",
					new String[] { isPublic, Constants.userId });
			int j = fileList.size();
			for (int i = 0; i < j; i++) {
				FileBean bean = fileList.get(i);
				String key = UUID.randomUUID().toString().replace("-", "");
				// xx
				database.execSQL(
						"insert into tb_contact_folders(primary_id,userId,id,folderName,fileName,subFiles,fileSize,fileUrl,fileId,releaseDate,bool,parentId,isDirectory,isPublic,isShare,shareTime,shareUserName,def) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { key, Constants.userId, bean.getId(),
								bean.getFolderName(), bean.getFileName(),
								bean.getSubFiles(), bean.getFileSize(),
								bean.getFileUrl(), bean.getFileId(),
								bean.getReleaseDate(), bean.getIspublic(),
								bean.getParentId(), bean.getIsDirectory(),
								isPublic, bean.getIsShare(),
								bean.getShareTime(), bean.getShareUserName(),
								bean.getDef() });
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 保存文件
	 * 
	 * @param fileList
	 */

	public void saveFileAddList(List<FileBean> fileList, String isPublic) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			int j = fileList.size();
			for (int i = 0; i < j; i++) {
				FileBean bean = fileList.get(i);
				String key = UUID.randomUUID().toString().replace("-", "");
				// xx
				database.execSQL(
						"insert into tb_contact_folders(primary_id,userId,id,folderName,fileName,subFiles,fileSize,fileUrl,fileId,releaseDate,bool,parentId,isDirectory,isPublic,isShare,shareTime,shareUserName,def) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { key, Constants.userId, bean.getId(),
								bean.getFolderName(), bean.getFileName(),
								bean.getSubFiles(), bean.getFileSize(),
								bean.getFileUrl(), bean.getFileId(),
								bean.getReleaseDate(), bean.getIspublic(),
								bean.getParentId(), bean.getIsDirectory(),
								isPublic, bean.getIsShare(),
								bean.getShareTime(), bean.getShareUserName(),
								bean.getDef() });
			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 修改父节点下子文件的个数
	 * 
	 * @param fileList
	 */
	public void updateSubFiles(String parentId, String subFiles) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			database.execSQL(
					" update tb_contact_folders set subFiles = ? where id = ?",
					new Object[] { subFiles, parentId });
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 删除文件文件夹
	 * 
	 * @param fileList
	 */
	public void deleteFiles(String parentId, List<FileBean> fileDeleteList) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			for (int i = 0; i < fileDeleteList.size(); i++) {
				if (fileDeleteList.get(i).getIsDirectory().equals("true")) {
					database.execSQL(
							" delete from tb_contact_folders where id = ? and parentId = ?",
							new Object[] { fileDeleteList.get(i).getId(),
									parentId });
				} else {
					database.execSQL(
							" delete from tb_contact_folders where fileId = ? and parentId = ?",
							new Object[] { fileDeleteList.get(i).getFileId(),
									parentId });
				}
			}

			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 查询之后请求的文件时间
	 * 
	 * @return
	 */
	public String lastRequestTime(String isPublic) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select max(shareTime) from tb_contact_folders  where userId = ? and isPublic = ?");
			cursor = database.rawQuery(sql.toString(), new String[] {
					Constants.userId, isPublic });
			cursor.moveToFirst();
			return cursor.getString(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
		return null;
	}

	/**
	 * 根据输入的信息查询
	 * 
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	public List<FileBean> queryByKeyWord(String fileType, String fileName) {
		List<FileBean> fileList = new ArrayList<FileBean>();
		Cursor cursor = null;
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder(
				"select * from tb_contact_folders where userId = ? and isPublic  = ? and fileName like ? and isDirectory = 'false'");
		try {
			cursor = database.rawQuery(sql.toString(), new String[] {
					Constants.userId, fileType, '%' + fileName + '%' });
			while (cursor.moveToNext()) {
				FileBean bean = new FileBean();
				bean.setId(cursor.getString(cursor.getColumnIndex("id")));
				// xx
				bean.setDef(cursor.getString(cursor.getColumnIndex("def")));
				bean.setParentId(cursor.getString(cursor
						.getColumnIndex("parentId")));
				bean.setFileName(cursor.getString(cursor
						.getColumnIndex("fileName")));
				bean.setSubFiles(cursor.getString(cursor
						.getColumnIndex("subFiles")));
				bean.setFileSize(cursor.getString(cursor
						.getColumnIndex("fileSize")));
				bean.setFileUrl(cursor.getString(cursor
						.getColumnIndex("fileUrl")));
				bean.setFileId(cursor.getString(cursor.getColumnIndex("fileId")));
				bean.setIsShare(cursor.getString(cursor
						.getColumnIndex("isShare")));
				bean.setShareTime(cursor.getString(cursor
						.getColumnIndex("shareTime")));
				bean.setShareUserName(cursor.getString(cursor
						.getColumnIndex("shareUserName")));
				bean.setReleaseDate(cursor.getString(cursor
						.getColumnIndex("releaseDate")));
				bean.setIsDirectory(cursor.getString(cursor
						.getColumnIndex("isDirectory")));
				fileList.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			database.close();
		}
		return fileList;
	}

	/**
	 * 修改分享文件处理事件
	 * 
	 * @param fileList
	 */
	public void shareEditFiles(String num, FileBean bean) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			database.execSQL(
					"update tb_contact_folders set isShare = ? where fileId = ? and parentId = ?",
					new Object[] { num, bean.getFileId(), bean.getParentId() });
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 本地存储分享信息，解决同步后分享数据消失问题
	 */
	public void setShare(FileBean bean) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			database.execSQL("insert into tb_share(userId,fileId) values(?,?)",
					new Object[] { Constants.userId, bean.getFileId() });
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 本地存储分享信息，解决同步后分享数据消失问题
	 */
	public void deleteShare(FileBean bean) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		try {
			database.beginTransaction();
			database.execSQL(
					"delete from tb_share where userId = ? and fileId = ?",
					new Object[] { Constants.userId, bean.getFileId() });
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			database.endTransaction();
			database.close();
		}
	}

	/**
	 * 本地存储分享信息，解决同步后分享数据消失问题
	 */
	public boolean queryShare(FileBean bean) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = null;
		try {
			database.beginTransaction();
			cursor = database.rawQuery(
					"select * from tb_share where userId = ? and fileId = ? ",
					new String[] { Constants.userId, bean.getFileId() });
			database.setTransactionSuccessful();
			if (cursor.moveToFirst()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			database.endTransaction();
			database.close();
		}
		return false;
	}

}
