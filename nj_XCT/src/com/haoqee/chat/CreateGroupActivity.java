package com.haoqee.chat;

import java.io.File;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;
import com.xguanjia.hx.haoxinchat.GroupChatActivity;
import com.haoqee.chatsdk.TCChatManager;
import com.haoqee.chatsdk.Interface.TCBaseListener;
import com.haoqee.chatsdk.entity.TCError;
import com.haoqee.chatsdk.entity.TCGroup;

/**
 * 创建群或编辑群页面
 *
 */
public class CreateGroupActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mGroupLogoView; // 头像显示控件
	private String mImageFilePath; // 上传图片路径
	private TextView mGroupNameView; // 群名称显示控件
	private TextView mGroupContentView; // 群简介显示控件
	private RelativeLayout mHeaderLayout; // 头像点击栏控件
	private RelativeLayout mGroupNameLayout; // 群名称点击栏控件
	private RelativeLayout mGroupDescLayout; // 群简介点击栏控件
	private final static int CONTENT_REQUEST = 12145; // 用于输入群名称或内容之后在onActivityResult接收值所用
	private static final String TEMP_FILE_NAME = "header.jpg"; // 上传图片时生成的图片的临时名称
	private static final int REQUEST_GET_IMAGE_BY_CAMERA = 1002;
	private static final int REQUEST_GET_URI = 101;
	public static final int REQUEST_GET_BITMAP = 124;
	private Bitmap mBitmap;

	private TCGroup mGroup = null; // 当群资料不为空时，为编辑资料
	private ImageLoader mImageLoader = new ImageLoader();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_group_layout);
		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		mGroup = (TCGroup) getIntent().getSerializableExtra("group");

		String title = mContext.getString(R.string.create_group);
		if (mGroup != null) {
			title = mContext.getString(R.string.edit_group_info);
		}

		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn,
				title);
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mGroupLogoView = (ImageView) findViewById(R.id.header);

		mGroupNameView = (TextView) findViewById(R.id.group_name);
		mGroupContentView = (TextView) findViewById(R.id.group_description);

		mHeaderLayout = (RelativeLayout) findViewById(R.id.headerlayout);
		mHeaderLayout.setOnClickListener(this);

		mGroupNameLayout = (RelativeLayout) findViewById(R.id.groupnamelayout);
		mGroupNameLayout.setOnClickListener(this);

		mGroupDescLayout = (RelativeLayout) findViewById(R.id.groupdesclayout);
		mGroupDescLayout.setOnClickListener(this);

		if (mGroup != null) { // 如果群消息不为空，则表示处于编辑模式

			// 更新界面
			if (!TextUtils.isEmpty(mGroup.getGroupLogoSmall())) {
				mImageLoader.getBitmap(mContext, mGroupLogoView, null,
						mGroup.getGroupLogoSmall(), 0, false, false);
			} else {
				mGroupLogoView.setImageResource(R.drawable.group_default_icon);
			}

			mGroupNameView.setText(mGroup.getGroupName());
			mGroupContentView.setText(mGroup.getGroupDescription());
		}
	}

	/**
	 * 显示图片选择对话框
	 */
	private void showPicDialog() {
		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button cameraBtn = (Button) layout.findViewById(R.id.camera);
		final Button galleryBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);
		cameraBtn.setText(mContext.getString(R.string.camera));
		galleryBtn.setText(mContext.getString(R.string.gallery));
		cancelBtn.setText(mContext.getString(R.string.cancel));

		cameraBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				getImageFromCamera();
			}
		});

		galleryBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				getImageFromGallery();
			}
		});

		cancelBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
			}
		});

		// set a large value put it in bottom
		Window w = dlg.getWindow();
		WindowManager.LayoutParams lp = w.getAttributes();
		lp.x = 0;
		final int cMakeBottom = -1000;
		lp.y = cMakeBottom;
		lp.gravity = Gravity.BOTTOM;
		dlg.onWindowAttributesChanged(lp);
		dlg.setCanceledOnTouchOutside(true);
		dlg.setCancelable(true);

		dlg.setContentView(layout);
		dlg.show();
	}

	/**
	 * 拍照
	 */
	private void getImageFromCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		if (FeatureFunction.newFolder(Environment.getExternalStorageDirectory()
				+ FeatureFunction.PUB_TEMP_DIRECTORY)) {
			File out = new File(Environment.getExternalStorageDirectory()
					+ FeatureFunction.PUB_TEMP_DIRECTORY, TEMP_FILE_NAME);
			Uri uri = Uri.fromFile(out);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

			startActivityForResult(intent, REQUEST_GET_IMAGE_BY_CAMERA);
		}
	}

	/**
	 * 从相册获取
	 */
	private void getImageFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");

		startActivityForResult(intent, REQUEST_GET_URI);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_GET_URI:
			if (resultCode == RESULT_OK) {
				doChoose(true, data);
			}

			break;

		case REQUEST_GET_IMAGE_BY_CAMERA:
			if (resultCode == RESULT_OK) {
				doChoose(false, data);
			}
			break;
		case REQUEST_GET_BITMAP:
			if (resultCode == RESULT_OK) {

				Bundle extras = data.getExtras();
				if (extras != null) {

					mGroupLogoView.setImageBitmap(null);
					if (mBitmap != null && !mBitmap.isRecycled()) {
						mBitmap.recycle();
						mBitmap = null;
					}

					mBitmap = extras.getParcelable("data");
					mGroupLogoView.setImageBitmap(mBitmap);
					File file = new File(
							Environment.getExternalStorageDirectory()
									+ FeatureFunction.PUB_TEMP_DIRECTORY
									+ TEMP_FILE_NAME);
					if (file != null && file.exists()) {
						file.delete();
						file = null;
					}

					mImageFilePath = FeatureFunction.saveTempBitmap(mBitmap,
							"header.jpg");
				}
			}
			break;

		case CONTENT_REQUEST:
			if (resultCode == RESULT_OK) {
				String content = data.getStringExtra("content");
				int type = data.getIntExtra("type",
						InputContentActivity.INPUT_GROUP_NAME);
				if (type == InputContentActivity.INPUT_GROUP_NAME) {
					mGroupNameView.setText(content);
				} else if (type == InputContentActivity.INPUT_GROUP_DESCRIPTION) {
					mGroupContentView.setText(content);
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doChoose(final boolean isGallery, final Intent data) {
		if (isGallery) {
			originalImage(data);
		} else {
			if (data != null) {
				originalImage(data);
			} else {
				// Here if we give the uri, we need to read it
				String path = Environment.getExternalStorageDirectory()
						+ FeatureFunction.PUB_TEMP_DIRECTORY + TEMP_FILE_NAME;
				String extension = path.substring(path.indexOf("."),
						path.length());
				if (FeatureFunction.isPic(extension)) {
					startPhotoZoom(Uri.fromFile(new File(path)));
				} else {
				}
			}
		}
	}

	private void originalImage(Intent data) {
		Uri uri = data.getData();
		if (!TextUtils.isEmpty(uri.getAuthority())) {
			Cursor cursor = getContentResolver().query(uri,
					new String[] { MediaStore.Images.Media.DATA }, null, null,
					null);
			if (null == cursor) {
				return;
			}
			cursor.moveToFirst();
			String path = cursor.getString(cursor
					.getColumnIndex(MediaStore.Images.Media.DATA));
			String extension = path.substring(path.lastIndexOf("."),
					path.length());
			if (FeatureFunction.isPic(extension)) {
				startPhotoZoom(data.getData());

			} else {
			}
		} else {
			String path = uri.getPath();
			String extension = path.substring(path.lastIndexOf("."),
					path.length());
			if (FeatureFunction.isPic(extension)) {
				startPhotoZoom(uri);
			} else {
			}
		}
	}

	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 180);
		intent.putExtra("outputY", 180);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, REQUEST_GET_BITMAP);
	}

	@Override
	protected void onDestroy() {
		mGroupLogoView.setImageBitmap(null);
		if (mBitmap != null && !mBitmap.isRecycled()) {
			mBitmap.recycle();
			mBitmap = null;
		}

		if (!TextUtils.isEmpty(mImageFilePath)) {
			File file = new File(mImageFilePath);
			if (file != null && file.exists()) {
				file.delete();
			}
		}

		FeatureFunction.freeBitmap(mImageLoader.getImageBuffer());

		System.gc();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			hideSoftKeyboard();
			this.finish();
			break;

		case R.id.right_btn:
			hideSoftKeyboard();
			if (mGroup != null) { // 群资料不为空，则处于编辑群资料模式，则点击确定调用编辑接口
				checkEdit();
			} else { // 群资料为空，则处于创建群模式，则点击确定按钮调用创建群接口
				checkCreate();
			}
			break;

		// 点击群头像栏，弹出选择相册和拍照菜单
		case R.id.headerlayout:
			showPicDialog();
			break;

		// 点击群名称进入输入内容界面输入群名称
		case R.id.groupnamelayout:
			Intent groupNameIntent = new Intent(mContext,
					InputContentActivity.class);
			groupNameIntent.putExtra("type",
					InputContentActivity.INPUT_GROUP_NAME);
			if (mGroup != null) {
				groupNameIntent.putExtra("content", mGroup.getGroupName());
			}
			startActivityForResult(groupNameIntent, CONTENT_REQUEST);
			break;

		// 点击群描述栏控件进入输入内容界面输入群描述
		case R.id.groupdesclayout:
			Intent groupDescIntent = new Intent(mContext,
					InputContentActivity.class);
			groupDescIntent.putExtra("type",
					InputContentActivity.INPUT_GROUP_DESCRIPTION);
			if (mGroup != null) {
				groupDescIntent.putExtra("content",
						mGroup.getGroupDescription());
			}
			startActivityForResult(groupDescIntent, CONTENT_REQUEST);
			break;

		default:
			break;
		}
	}

	/**
	 * 编辑群资料接口入口，包括检测参数的合法性及调用编辑群接口
	 */
	private void checkEdit() {
		String name = mGroupNameView.getText().toString().trim();
		String content = mGroupContentView.getText().toString().trim();

		boolean isEdit = false;

		// 检测头像是否有更改
		if (!TextUtils.isEmpty(mImageFilePath)) {
			isEdit = true;
		}

		// 检测名称是否被更改，如果未做任何更改，则赋值为空字符串
		if (name.equals(mGroup.getGroupName())) {
			name = "";
		}

		// 检测群简介是否被更改,如果未做任何更改，则赋值为空字符串
		if (content.equals(mGroup.getGroupDescription())) {
			content = "";
		}

		// 如果名称和简介不为空，则表示群资料已经被修改
		if (!name.equals("") || !content.equals("")) {
			isEdit = true;
		}

		// 如果资料未被编辑过，则提示当前资料未做任何更改
		if (!isEdit) {
			showToast(mContext.getString(R.string.group_info_is_new));
			return;
		}

		// 加入群扩展字段
		// HashMap<String, String> extendMap = new HashMap<String, String>();
		// extendMap.put("category", "Computer");

		Message message = new Message();
		message.obj = mContext.getString(R.string.send_loading);
		message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);

		TCChatManager.getInstance().editGroup(mGroup.getGroupID(), name,
				mImageFilePath, content, null, mListener);
	}

	/**
	 * 检测创建群输入内容的有效性， 如果参数有效，则调用接口
	 */
	private void checkCreate() {
		String name = mGroupNameView.getText().toString().trim();
		String content = mGroupContentView.getText().toString().trim();

		if (TextUtils.isEmpty(name)) {
			String prompt = mContext.getString(R.string.please_input)
					+ mContext.getString(R.string.group_name);
			showToast(prompt);
			return;
		}

		if (TextUtils.isEmpty(content)) {
			String prompt = mContext.getString(R.string.please_input)
					+ mContext.getString(R.string.group_description);
			showToast(prompt);
			return;
		}

		// 加入群扩展字段
		// HashMap<String, String> extendMap = new HashMap<String, String>();
		// extendMap.put("category", "Computer");

		Message message = new Message();
		message.obj = mContext.getString(R.string.send_loading);
		message.what = IndexActivity.BASE_SHOW_PROGRESS_DIALOG;
		mBaseHandler.sendMessage(message);

		TCChatManager.getInstance().createGroup(name, content, mImageFilePath,
				null, mListener);
	}

	private TCBaseListener mListener = new TCBaseListener() {

		@Override
		public void onError(TCError error) {
			Message message = new Message();
			message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
			message.obj = error.errorMessage;
			mBaseHandler.sendMessage(message);
		}

		@Override
		public void doComplete() {

			// 如果群资料不为空，则是编辑资料成功
			if (mGroup != null) {
				Message message = new Message();
				message.what = IndexActivity.BASE_HIDE_PROGRESS_DIALOG;
				message.obj = mContext.getString(R.string.edit_group_success);
				mBaseHandler.sendMessage(message);

				// 发通知刷新该群详细资料
				Intent intent = new Intent(
						GroupDetailActivity.ACTION_REFRESH_GROUP_DETAIL);
				intent.putExtra("groupid", mGroup.getGroupID());
				mContext.sendBroadcast(intent);

				// 发通知刷新群列表数据
				mContext.sendBroadcast(new Intent(
						GroupChatActivity.REFRESH_CONTACT_GROUP_ACTION));

				CreateGroupActivity.this.finish();
			} else {
				// 创建群成功，发通知刷新群列表数据
				mContext.sendBroadcast(new Intent(
						GroupChatActivity.REFRESH_CONTACT_GROUP_ACTION));
				Message message = new Message();
				message.what = IndexActivity.BASE_HIDE_PROGRESS_DIALOG;
				message.obj = mContext.getString(R.string.create_group_success);
				mBaseHandler.sendMessage(message);
				CreateGroupActivity.this.finish();
			}

		}

		@Override
		public void onProgress(int progress) {

		}
	};
}
