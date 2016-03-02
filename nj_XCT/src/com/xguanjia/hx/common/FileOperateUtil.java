package com.xguanjia.hx.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

public class FileOperateUtil {
	
	/**
	 * 解压缩zip文件
	 * 
	 * @param fileName
	 *            要解压的文件名 包含路径 如："c:\\test.zip"
	 * @param filePath
	 *            解压后存放文件的路径 如："c:\\temp"
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void unZip(String fileName, String filePath) throws Exception {
		ZipFile zipFile = new ZipFile(fileName, "GBK"); // 以“GBK”编码创建zip文件，用来处理winRAR压缩的文件。
		Enumeration emu = zipFile.getEntries();
		String phoneGapPath = "";
		int iFlag = 0;
		while (emu.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) emu.nextElement();
			if (entry.isDirectory()) {
				new File(filePath + entry.getName()).mkdirs();
				continue;
			}
			BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
			
			phoneGapPath = filePath + entry.getName();
			
//			if(iFlag == 0) {				
//				m_strPluginPath = filePath + entry.getName();
//			}
//			iFlag ++;
			
			File file = new File(phoneGapPath);
			File parent = file.getParentFile();
			if (parent != null && (!parent.exists())) {
				parent.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(file);
			BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = bis.read(buf, 0, 1024)) != -1) {
				fos.write(buf, 0, len);
			}
			bos.flush();
			bos.close();
			bis.close();
		}
		zipFile.close();
//		return m_strPluginPath;
	}

}
