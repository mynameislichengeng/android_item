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
import android.os.Handler;
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
import android.widget.Toast;

import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.entity.User;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.net.ThinkchatException;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;

/**
 * 编辑资料页面
 *
 */
public class EditProfileActivity extends BaseActivity implements
		OnClickListener {

	private ImageView mHeaderView; // 头像显示控件
	private TextView mNickNameView; // 昵称显示控件
	private TextView mGenderView; // 性别显示控件
	private TextView mSignView; // 个性签名显示控件

	private RelativeLayout mHeaderLayout; // 头像点击栏控件，点击之后弹出选择图片对话框
	private RelativeLayout mNickNameLayout; // 昵称点击栏控件，点击之后进入内容输入页面编辑昵称
	private RelativeLayout mGenderLayout; // 性别点击栏控件，点击之后弹出性别选择对话框
	private RelativeLayout mSignLayout; // 个性签名点击栏，点击之后进入内容输入页面编辑个性签名

	private static final String TEMP_FILE_NAME = "header.jpg"; // 图片临时名称
	private static final int REQUEST_GET_IMAGE_BY_CAMERA = 1002; // onActivityResult拍照请求
	private static final int REQUEST_GET_URI = 101; // onActivityResult相册选择请求
	public static final int REQUEST_GET_BITMAP = 124; // onActivityResult裁剪之后处理请求

	private User mUser;
	private ImageLoader mImageLoader = new ImageLoader();
	private Bitmap mBitmap;
	private String mImageFilePath; // 选择的图片路径
	private final static int CONTENT_REQUEST = 12145; // 用于输入昵称或个性签名之后在onActivityResult接收值所用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_profile);
		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {

		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn,
				mContext.getString(R.string.edit_profile));
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mUser = Common.getLoginResult(mContext);

		mHeaderView = (ImageView) findViewById(R.id.header);
		mNickNameView = (TextView) findViewById(R.id.nickname);
		mGenderView = (TextView) findViewById(R.id.sex);
		mSignView = (TextView) findViewById(R.id.sign);

		mHeaderLayout = (RelativeLayout) findViewById(R.id.headerlayout);
		mHeaderLayout.setOnClickListener(this);

		mNickNameLayout = (RelativeLayout) findViewById(R.id.nicknamelayout);
		mNickNameLayout.setOnClickListener(this);

		mGenderLayout = (RelativeLayout) findViewById(R.id.sexlayout);
		mGenderLayout.setOnClickListener(this);

		mSignLayout = (RelativeLayout) findViewById(R.id.signlayout);
		mSignLayout.setOnClickListener(this);

		update();
	}

	/**
	 * 更新界面
	 */
	private void update() {
		if (mUser != null) {
			if (!TextUtils.isEmpty(mUser.mMiddleHead)) {
				mImageLoader.getBitmap(mContext, mHeaderView, null,
						mUser.mSmallHead, 0, false, false);
			}

			mNickNameView.setText(mUser.nickName);

			if (mUser.mGender == 2) {
				mGenderView.setText(mContext.getString(R.string.secret));
			} else if (mUser.mGender == 0) {
				mGenderView.setText(mContext.getString(R.string.male));
			} else if (mUser.mGender == 1) {
				mGenderView.setText(mContext.getString(R.string.female));
			}

			mSignView.setText(mUser.sign);
		}
	}

	@Override
	protected void onDestroy() {
		mHeaderView.setImageBitmap(null);
		FeatureFunction.freeBitmap(mImageLoader.getImageBuffer());
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

		System.gc();
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.left_btn:
			this.finish();
			break;
		// 点击完成按钮执行曹组
		case R.id.right_btn:
			checkEdit();
			break;

		// 点击头像栏，弹出图片选择对话框
		case R.id.headerlayout:
			showPicDialog();
			break;

		case R.id.nicknamelayout:
			Intent nicknameIntent = new Intent(mContext,
					InputContentActivity.class);
			nicknameIntent.putExtra("content", mUser.nickName);
			nicknameIntent.putExtra("type",
					InputContentActivity.INPUT_USER_NICKNAME);
			startActivityForResult(nicknameIntent, CONTENT_REQUEST);
			break;

		case R.id.sexlayout:
			showGenderDialog();
			break;

		case R.id.signlayout:
			Intent signIntent = new Intent(mContext, InputContentActivity.class);
			signIntent.putExtra("content", mUser.sign);
			signIntent.putExtra("type", InputContentActivity.INPUT_USER_SIGN);
			startActivityForResult(signIntent, CONTENT_REQUEST);
			break;

		default:
			break;
		}
	}

	/**
	 * 点击图片栏弹出选择图片对话框
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
	 * 调用系统相机拍照
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
	 * 从相册选择图片
	 */
	private void getImageFromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");

		startActivityForResult(intent, REQUEST_GET_URI);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {

		// 选择图片成功后进入
		case REQUEST_GET_URI:
			if (resultCode == RESULT_OK) {
				doChoose(true, data);
			}

			break;

		// 拍照成功后进入
		case REQUEST_GET_IMAGE_BY_CAMERA:
			if (resultCode == RESULT_OK) {
				doChoose(false, data);
			}
			break;

		// 图片裁剪成功后进入
		case REQUEST_GET_BITMAP:
			if (resultCode == RESULT_OK) {

				Bundle extras = data.getExtras();
				if (extras != null) {

					mHeaderView.setImageBitmap(null);
					if (mBitmap != null && !mBitmap.isRecycled()) {
						mBitmap.recycle();
						mBitmap = null;
					}

					mBitmap = extras.getParcelable("data");
					mHeaderView.setImageBitmap(mBitmap);
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

		// 输入昵称或个人签名完成后进入
		case CONTENT_REQUEST:
			if (resultCode == RESULT_OK) {
				String content = data.getStringExtra("content");
				int type = data.getIntExtra("type",
						InputContentActivity.INPUT_USER_NICKNAME);
				if (type == InputContentActivity.INPUT_USER_NICKNAME) {
					mNickNameView.setText(content);
				} else if (type == InputContentActivity.INPUT_USER_SIGN) {
					mSignView.setText(content);
				}
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 图片处理入口函数
	 * 
	 * @param isGallery
	 *            图片是否来自相册
	 * @param data
	 *            拍照或图片选择成功后回传的Intent
	 */
	private void doChoose(final boolean isGallery, final Intent data) {

		// 如果从相册选择或者是data不为空，则直接处理，反之则直接从SD卡中读出文件处理

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

	/**
	 * 根据图片路径进入裁剪页面
	 * 
	 * @param data
	 */
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

	/**
	 * 调用系统裁剪功能
	 * 
	 * @param uri
	 *            文件URI
	 */
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

	/**
	 * 检测当前资料是否更改，如有更改，则调用编辑接口更改资料
	 */
	private void checkEdit() {
		String nickName = mNickNameView.getText().toString().trim();
		String sign = mSignView.getText().toString().trim();
		String gender = mGenderView.getText().toString().trim();

		int sex = mUser.mGender;
		if (gender.equals(mContext.getString(R.string.male))) {
			sex = 0;
		} else if (gender.equals(mContext.getString(R.string.female))) {
			sex = 1;
		} else if (gender.equals(mContext.getString(R.string.secret))) {
			sex = 2;
		}

		if (nickName.equals(mUser.nickName)) {
			nickName = "";
		}

		if (sign.equals(mUser.sign)) {
			sign = "";
		}

		if (sex == mUser.mGender) {
			gender = "";
		}

		boolean isEdit = false;
		if (!TextUtils.isEmpty(mImageFilePath) || !TextUtils.isEmpty(nickName)
				|| !TextUtils.isEmpty(sign) || !TextUtils.isEmpty(gender)) {
			isEdit = true;
		}

		if (!isEdit) {
			Toast.makeText(mContext,
					mContext.getString(R.string.profile_is_new),
					Toast.LENGTH_SHORT).show();
			return;
		}

		Message message = new Message();
		message.obj = mContext.getString(R.string.send_loading);
		message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
		mHandler.sendMessage(message);

		editProfile(mImageFilePath, nickName, sign, sex + "");
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				showProgressDialog(dialogMsg);
				break;
			case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();

				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String) msg.obj;
				if (error_Detail != null && !error_Detail.equals("")) {
					showToast(error_Detail);
				} else {
					showToast(mContext.getString(R.string.load_error));
				}
				break;
			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				baseHideProgressDialog();
				showToast(mContext.getString(R.string.network_error));
				break;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				baseHideProgressDialog();
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}

				showToast(message);
				break;

			}
		}
	};

	private void editProfile(final String userFace, final String nickName,
			final String userSign, final String gender) {
		if (Common.verifyNetwork(mContext)) {
			new Thread() {
				@Override
				public void run() {
					try {
						LoginResult loginResult = Common.getThinkchatInfo()
								.editProfile(userFace, nickName, userSign,
										gender);
						if (loginResult != null && loginResult.mState != null
								&& loginResult.mState.code == 0) {
							User login = loginResult.mUser;
							login.password = mUser.password;
							Common.saveLoginResult(mContext, login);
							Common.setUid(mUser.uid);

							if (!TextUtils.isEmpty(userFace)) {
								mContext.sendBroadcast(new Intent(
										UserInfoActivity.ACTION_REFRESH_USER_INFO));
							}

							mHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
							EditProfileActivity.this.finish();
						} else {
							Message message = new Message();
							message.what = HaoXinProjectActivity.MSG_LOAD_ERROR;
							if (loginResult != null
									&& loginResult.mState != null
									&& loginResult.mState.errorMsg != null
									&& !loginResult.mState.errorMsg.equals("")) {
								message.obj = loginResult.mState.errorMsg;
							} else {
								message.obj = mContext
										.getString(R.string.edit_profile_failed);
							}
							mHandler.sendMessage(message);
						}
					} catch (ThinkchatException e) {
						e.printStackTrace();
						Message msg = new Message();
						msg.what = HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION;
						msg.obj = mContext.getString(R.string.timeout);
						mHandler.sendMessage(msg);
					}
				}
			}.start();
		} else {
			mHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
		}
	}

	/**
	 * 性别显示对话框
	 */
	private void showGenderDialog() {
		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(
				R.layout.chat_add_menu_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);

		final Button maleBtn = (Button) layout.findViewById(R.id.sendType);
		final Button femaleBtn = (Button) layout.findViewById(R.id.camera);
		final Button secretBtn = (Button) layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button) layout.findViewById(R.id.cancelbtn);

		maleBtn.setText(mContext.getString(R.string.male));
		femaleBtn.setText(mContext.getString(R.string.female));
		secretBtn.setText(mContext.getString(R.string.secret));
		cancelBtn.setText(mContext.getString(R.string.cancel));

		maleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				mGenderView.setText(mContext.getString(R.string.male));
			}
		});

		femaleBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				mGenderView.setText(mContext.getString(R.string.female));
			}
		});

		secretBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				mGenderView.setText(mContext.getString(R.string.secret));
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

}
