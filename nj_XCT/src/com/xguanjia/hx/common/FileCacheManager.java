package com.xguanjia.hx.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

public class FileCacheManager {

	final static String contantsIp = "171.0.0.1";

	final static String ATTRIBUTE_FILE_NAME = "att.property";

	/**
	 * 删除当前目录下所有文件
	 * 
	 * @param file
	 *            目录或文件
	 */
	public static void deleteAllFiles(File file) {
		if (file.exists() && file.isDirectory()) {
			if (!(file.delete())) {
				File subs[] = file.listFiles();
				for (int i = 0; i < subs.length; i++) {
					if (subs[i].isDirectory()) {
						deleteAllFiles(subs[i]);
					} else {
						subs[i].delete();
					}
				}
				file.delete();
			}
		}
		if (file.exists() && !file.getName().equals(ATTRIBUTE_FILE_NAME))
			file.delete();
	}

	/**
	 * 读取文件到MAP
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static HashMap<String, Object> readObject(Context context,
			String filename) throws HaoqeeException {
		HashMap<String, Object> object;
		InputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = context.openFileInput(filename);
			ois = new ObjectInputStream(is);
			object = (HashMap<String, Object>) ois.readObject();
		} catch (Exception e) {
			object = new HashMap<String, Object>();
		} finally {
			try {
				if (is != null)
					is.close();
				if (ois != null)
					ois.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new HaoqeeException("IO关闭异常", e);
			}
		}
		return object;
	}

	/**
	 * 判断jquerymobile是否存在SdCard
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean getJqueryMobileExistsSdCard(String fileName) {
		File file = new File(Constants.SAVE_PATH, fileName);
		return file.exists();
	}
	
	/**
	 * 判断缓存头像是否存在SdCard
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean getCacheAvatarExistsSdCard(String fileName) {
		File file = new File(Constants.SAVE_IMAGE_PATH
				+ "HeadAvatar/", fileName);
		return file.exists();
	}

	/**
	 * 对象写入文件
	 * 
	 * @param context
	 * @param fileName
	 * @param object
	 */
	public static void writeObject(Context context, String fileName,
			Object object) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 删除缓存文件
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean delCacheFile(Context context, String fileName) {
		File file = new File(context.getFilesDir(), fileName);
		if (file.exists()) {
			if (file.isDirectory()) {
				String[] files = file.list();
				for (int i = 0; i < files.length; i++) {
					delCacheFile(context, files[i]);
				}
			} else {
				return file.delete();
			}

		}
		return false;
	}

	/**
	 * 判断缓存文件是否存在
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean getCacheFileExists(Context context, String fileName) {
		File file = new File(context.getFilesDir(), fileName);
		System.out.println("160" + file.toString());
		return file.exists();
	}

	public static long getCacheFileModifiTime(Context context, String fileName) {

		long time = 0l;
		File file = new File(context.getFilesDir(), fileName);
		if (file.exists()) {
			time = file.lastModified();
		}
		return time;
	}

	/**
	 * 读取缓存文件到InputStream
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws HaoqeeException
	 */
	public static InputStream readCacheFile(Context context, String fileName)
			throws HaoqeeException {
		try {
			File file = new File(
					context.getFilesDir() + "/" + contantsIp + "/", fileName);
			if (file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return new FileInputStream(file);
			// return context.openFileInput(fileName);
		} catch (FileNotFoundException e) {
			throw new HaoqeeException(fileName + "，缓存文件读取失败", e);
		}
	}

	/**
	 * 写入文件到缓存
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 * @throws HaoqeeException
	 */
	public static OutputStream wirteCacheFile(Context context, String fileName)
			throws HaoqeeException {
		try {
			File file = new File(Constants.SAVE_IMAGE_PATH + fileName);
			if (!file.exists()) {
				file.createNewFile();
				Log.i("222", "fileName:" + fileName);
			}
			return new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HaoqeeException(fileName + "，缓存文件写入失败", e);
		}
	}

	/**
	 * 写入json到手机文件
	 * 
	 * @param context
	 * @param fileName
	 * @param object
	 */
	public static void writeJson(Context context, String fileName, Object object) {
		FileOutputStream fos = null;
		OutputStreamWriter oos = null;
		try {
			fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
			oos = new OutputStreamWriter(fos, "UTF-8");
			oos.write(object.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入json到手机文件(SDCard)
	 * 
	 * @param context
	 * @param fileName
	 * @param object
	 */
	public static void writeJsonForSDCard(Context context, String fileName,
			Object object) {
		FileOutputStream fos = null;
		OutputStreamWriter oos = null;
		try {
			File file = new File("sdcard/mclient_log/" + fileName);
			file.getParentFile().mkdirs();
			if (!file.exists()) {
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			oos = new OutputStreamWriter(fos, "UTF-8");
			oos.write(object.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断缓存文件是否存在SdCard
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static boolean getCacheFileExistsSdCard(String fileName) {
		File file = new File(Constants.SAVE_IMAGE_PATH, fileName);
		return file.exists();
	}

	/**
	 * 对象写入SD卡
	 * 
	 * @param context
	 * @param fileName
	 * @param object
	 */
	public static void writeObjectToSdCard(String fileName, Object object) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(new File(Constants.SAVE_IMAGE_PATH
					+ fileName));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 对象写入SD卡
	 * 
	 * @param context
	 * @param fileName
	 * @param object
	 */
	public static Object readObjectToSdCard(String fileName, Object object) {
		Object obj = null;
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(new File(Constants.SAVE_IMAGE_PATH
					+ fileName));
			ois = new ObjectInputStream(fis);
			obj = ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}
