package com.haoqee.chat;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haoqee.chat.adapter.ChatAdapter;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.haoqee.chatsdk.entity.TCMessage;

public class FileDetailActivity extends BaseActivity implements OnClickListener {

	private ImageView mFileIconView; // 文件类型图标
	private TextView mFileNameTextView; // 文件名显示控件
	private TextView mFileLenTextView; // 文件大小显示控件
	private Button mDownloadBtn; // 下载文件按钮
	private Button mOpenBtn; // 打开文件按钮

	private ProgressBar mProgressBar; // 文件下载进度控件
	public final static String RECEIVE_FILE = "RecFiles";

	/** 下载文件失败通知 */
	public static final String ACTION_DOWNLOAD_FILE_FAILED = "com.xizue.thinkchat.intent.action.ACTION_DOWNLOAD_FILE_FAILED";

	/** 更新文件下载进度通知 */
	public static final String ACTION_DOWNLOAD_FILE_PROGRESS = "com.xizue.thinkchat.intent.action.ACTION_DOWNLOAD_FILE_PROGRESS";

	private TCMessage mMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_file_layout);

		IntentFilter filter = new IntentFilter();
		filter.addAction(ACTION_DOWNLOAD_FILE_PROGRESS);
		filter.addAction(ACTION_DOWNLOAD_FILE_FAILED);
		registerReceiver(mReceiver, filter);
		initComponent();
	}

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (!TextUtils.isEmpty(action)) {
				if (action.equals(ACTION_DOWNLOAD_FILE_PROGRESS)) {

					TCMessage message = (TCMessage) intent
							.getSerializableExtra("message");

					if (message.getMessageTag()
							.equals(mMessage.getMessageTag())) {
						int progress = intent.getIntExtra("progress", 0);

						mProgressBar.setProgress(progress);

						if (progress == 100) {
							mProgressBar.setVisibility(View.GONE);
							mOpenBtn.setVisibility(View.VISIBLE);
						} else if (progress < 100) {
							mProgressBar.setVisibility(View.VISIBLE);
						}
					}
				} else if (action.equals(ACTION_DOWNLOAD_FILE_FAILED)) {
					TCMessage message = (TCMessage) intent
							.getSerializableExtra("message");

					if (message.getMessageTag()
							.equals(mMessage.getMessageTag())) {

						String filePath = Environment
								.getExternalStorageDirectory()
								+ FeatureFunction.PUB_TEMP_DIRECTORY
								+ RECEIVE_FILE
								+ "/"
								+ mMessage.getFileMessageBody().getFileName();

						File file = new File(filePath);

						if (file.exists()) {
							file.delete();
						}

						showToast(mContext
								.getString(R.string.download_file_failed));

						mProgressBar.setVisibility(View.GONE);
						mProgressBar.setProgress(0);
						mDownloadBtn.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	};

	private void initComponent() {

		setTitleContent(R.drawable.back_btn, 0,
				mContext.getString(R.string.file_detail));
		findViewById(R.id.leftlayout).setOnClickListener(this);

		mMessage = (TCMessage) getIntent().getSerializableExtra("message");

		mFileIconView = (ImageView) findViewById(R.id.fileIcon);

		mFileNameTextView = (TextView) findViewById(R.id.filename);
		mFileLenTextView = (TextView) findViewById(R.id.filelen);

		mDownloadBtn = (Button) findViewById(R.id.downloadbtn);
		mDownloadBtn.setOnClickListener(this);

		mOpenBtn = (Button) findViewById(R.id.openbtn);
		mOpenBtn.setOnClickListener(this);

		mProgressBar = (ProgressBar) findViewById(R.id.file_progressbar);

		if (mMessage != null) {
			String extension = mMessage
					.getFileMessageBody()
					.getFileName()
					.substring(
							mMessage.getFileMessageBody().getFileName()
									.lastIndexOf("."),
							mMessage.getFileMessageBody().getFileName()
									.length());

			if (FeatureFunction.isPic(extension)) {
				mFileIconView.setImageResource(R.drawable.file_picture);
			} else if (FeatureFunction.isWord(extension)) {
				mFileIconView.setImageResource(R.drawable.file_word);
			} else if (FeatureFunction.isExcel(extension)) {
				mFileIconView.setImageResource(R.drawable.file_excel);
			} else if (FeatureFunction.isPDF(extension)) {
				mFileIconView.setImageResource(R.drawable.file_pdf);
			} else if (FeatureFunction.isTXT(extension)) {
				mFileIconView.setImageResource(R.drawable.file_txt);
			} else {
				mFileIconView.setImageResource(R.drawable.file_unknown);
			}

			mFileNameTextView.setText(mMessage.getFileMessageBody()
					.getFileName());
			String fileLen = "";
			if (mMessage.getFileMessageBody().getFileLen() >= ChatAdapter.MB) {
				float len = ((float) mMessage.getFileMessageBody().getFileLen())
						/ ChatAdapter.MB;
				fileLen = (float) Math.round(len * 100) / 100 + "M";

			} else if (mMessage.getFileMessageBody().getFileLen() >= ChatAdapter.KB
					&& mMessage.getFileMessageBody().getFileLen() < ChatAdapter.MB) {
				float len = ((float) mMessage.getFileMessageBody().getFileLen())
						/ ChatAdapter.KB;
				fileLen = (float) Math.round(len * 100) / 100 + "K";
			} else if (mMessage.getFileMessageBody().getFileLen() < ChatAdapter.KB) {
				float len = ((float) mMessage.getFileMessageBody().getFileLen());
				fileLen = (float) Math.round(len * 100) / 100 + "B";
			}

			mFileLenTextView.setText(fileLen);

			if (mMessage.getFromId().equals(Common.getUid(mContext))) {
				mDownloadBtn.setVisibility(View.GONE);
				mOpenBtn.setVisibility(View.VISIBLE);
			} else {
				String filePath = Environment.getExternalStorageDirectory()
						+ FeatureFunction.PUB_TEMP_DIRECTORY + RECEIVE_FILE
						+ "/" + mMessage.getFileMessageBody().getFileName();

				File file = new File(filePath);

				if (!file.exists()) {
					mDownloadBtn.setVisibility(View.VISIBLE);
					mOpenBtn.setVisibility(View.GONE);
				} else {
					if (file.length() < mMessage.getFileMessageBody()
							.getFileLen()) {
						mOpenBtn.setVisibility(View.GONE);
						mDownloadBtn.setVisibility(View.GONE);

						int progress = (int) ((((float) file.length()) / mMessage
								.getFileMessageBody().getFileLen()) * 100);
						mProgressBar.setProgress(progress);

						mProgressBar.setVisibility(View.VISIBLE);
					} else {
						mOpenBtn.setVisibility(View.VISIBLE);
						mDownloadBtn.setVisibility(View.GONE);
					}
				}
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.leftlayout:
			this.finish();
			break;

		case R.id.downloadbtn:
			mDownloadBtn.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);

			String directory = Environment.getExternalStorageDirectory()
					+ FeatureFunction.PUB_TEMP_DIRECTORY
					+ FileDetailActivity.RECEIVE_FILE;

			File file = new File(directory);

			if (!file.exists()) {
				file.mkdirs();
			}

			Intent downloadIntent = new Intent(
					HaoXinProjectActivity.ACTION_DOWNLOAD_FILE_MESSAGE);
			downloadIntent.putExtra("message", mMessage);
			mContext.sendBroadcast(downloadIntent);

			break;

		case R.id.openbtn:

			String filePath = "";
			if (mMessage.getFromId().equals(Common.getUid(mContext))) {
				filePath = mMessage.getFileMessageBody().getFileUrl();
			} else {
				filePath = Environment.getExternalStorageDirectory()
						+ FeatureFunction.PUB_TEMP_DIRECTORY + RECEIVE_FILE
						+ "/" + mMessage.getFileMessageBody().getFileName();
			}

			if (!TextUtils.isEmpty(filePath)) {

				Intent intent = openFile(filePath);

				if (intent != null) {
					startActivity(intent);
				}
			} else {
				showToast(mContext.getString(R.string.file_not_exist));
			}

			break;

		default:
			break;
		}
	}

	private Intent openFile(String filePath) {

		File file = new File(filePath);

		if (!file.exists()) {
			showToast(mContext.getString(R.string.file_not_exist));
			return null;
		}

		/* 取得扩展名 */
		String end = file
				.getName()
				.substring(file.getName().lastIndexOf(".") + 1,
						file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt") || end.equals("pptx")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls") || end.equals("xlsx")) {
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc") || end.equals("docx")) {
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}
}
