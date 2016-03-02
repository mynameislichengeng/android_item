package com.xguanjia.hx.openfire.listener;

import java.io.File;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smackx.filetransfer.FileTransfer;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.haoqee.chat.global.FeatureFunction;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.haoxin.db.HaoXinGroupDb;

public class XMPPFileListenerService extends Service {
	private HaoXinGroupDb mHaoXinGroupDb;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mHaoXinGroupDb = new HaoXinGroupDb(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		XMPPConnection connection = XmppTool.getConnection();
		FileTransferManager manager = new FileTransferManager(connection);
		manager.addFileTransferListener(new RecFileTransferListener());
		return super.onStartCommand(intent, flags, startId);

	}

	private IncomingFileTransfer transfer_in;
	private File saveTo;

	public class RecFileTransferListener implements FileTransferListener {
		@Override
		public void fileTransferRequest(FileTransferRequest request) {
			Log.i("tag", "file_receiver_listener");
			/**
			 * 判断下载路径目录是否存在，不存在，这新建文件夹getPareant()；
			 * 存在，则判断该目录下的文件名是否存在，存在删除，不存在最好
			 * **/
			File mFile = getSetPath();// 下载路径
			String str_name_accpte = request.getFileName();
			saveTo = new File(mFile, str_name_accpte);
			if (saveTo.exists()) {
				saveTo.delete();
			}
			transfer_in = request.accept();
			final String str1_from[] = request.getRequestor().split("@");// 消息的发送者
			new Thread(new Runnable() {

				@Override
				public void run() {

					try {
						transfer_in.recieveFile(saveTo);
						while (!transfer_in.getStatus().equals(
								FileTransfer.Status.complete)) {
							if (transfer_in.getStatus().equals(
									FileTransfer.Status.error)) {
								transfer_in.cancel();
								transfer_in.recieveFile(saveTo);
							}
						}// 接受完成完成

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}).start();

		}// 接口方法结束

	}

	private File getSetPath() {
		File mulu;
		File mFile;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {// 有sd卡
			mulu = Environment.getExternalStorageDirectory();
			String ss = mulu.getAbsolutePath()
					+ FeatureFunction.PUB_TEMP_DIRECTORY + "receiver_image";
			mFile = new File(ss);
		} else {// 没有sdk
			mulu = XMPPFileListenerService.this.getFilesDir();
			mFile = new File(mulu, "receiver_chat_image");
		}
		if (!mFile.exists()) {
			mFile.mkdirs();
		}
		return mFile;
	}

}
