package com.xguanjia.hx.set;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.AppUtils;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.contact.service.ContactPersonService;

/**
 * 设置头像
 */
public class SetPortraitActivity extends Activity implements OnClickListener {
	private static final String TAG = "SetPortraitActivity";
	private TextView pictureBtn, picFromLocalBtn, picExitBtn;
	private ContactPersonService service;
	private String headPhotoPath, pickPhotoPath;
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/Medical/headTemp.jpg";
	private Bitmap headPhoto;
	private ProgressDialog pd;
	private Uri photoUri;
	private File tempFile = new File(Environment.getExternalStorageDirectory(), getPhotoFileName());
	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_portrait);
		Log.d(TAG, "in onCreate method");
		initViews();
	}

	private void initViews() {
		pictureBtn = (TextView) this.findViewById(R.id.set_picture_btn);
//		pictureBtn.getBackground().setAlpha(180);
		pictureBtn.setOnClickListener(this);
		picFromLocalBtn = (TextView) this.findViewById(R.id.set_picFromLocal_btn);
//		picFromLocalBtn.getBackground().setAlpha(180);
		picFromLocalBtn.setOnClickListener(this);
		picExitBtn = (TextView) findViewById(R.id.set_exit_btn);
//		picExitBtn.getBackground().setAlpha(180);
		picExitBtn.setOnClickListener(this);
		service = new ContactPersonService(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.finish();
		return true;
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UEProbAgent.onResume(this);
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UEProbAgent.onPause(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 直接拍照
		case R.id.set_picture_btn:
			//选择拍照
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// 指定调用相机拍照后照片的储存路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
			startActivityForResult(intent, 4);
			
			
			
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//			startActivityForResult(intent, Constants.TWO);
			// Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			// SimpleDateFormat timeStampFormat = new
			// SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
			// String filename = timeStampFormat.format(new Date());
			// ContentValues values = new ContentValues();
			// values.put(Media.TITLE, filename);
			// photoUri =
			// getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			// values);
			// intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			// startActivityForResult(intent, Constants.ONE);
			break;
		// 从本地文件中浏览
		case R.id.set_picFromLocal_btn:
			intent = new Intent();
			intent.setType("image/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(intent, Constants.TWO);
			break;
		case R.id.set_exit_btn:
			finish();
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			// 拍照返回，处理旋转图片
			case Constants.ONE:
				// setImage(photoUri);
				// picCut(Uri.fromFile(new File(pickPhotoPath)));
				//picCut(photoUri);
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				Bundle bundle1 = data.getExtras();
				headPhoto = bundle1.getParcelable("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				doImageUploadAction("jpg", Bitmap2Stream(headPhoto));
				
				break;
			// 从系统图片
			case Constants.TWO:
				// 从系统图片
//				startPhotoZoom(Uri.fromFile(tempFile), 150, 150, 3);
				picCut(data.getData());
				break;
			case Constants.THREE:
				Bundle bundle = data.getExtras();
				headPhoto = bundle.getParcelable("data");
				// 上传图片
				pd = ProgressDialog.show(SetPortraitActivity.this, "", "头像上传中");
				doImageUploadAction("jpg", Bitmap2Stream(headPhoto));
				
//				Bundle bundle11 = data.getExtras();
//				bitmap = bundle11.getParcelable("data");
//				if (null != bitmap) {
//					pd = ProgressDialog.show(SetPortraitActivity.this, "", "头像上传中");
//					doImageUploadAction("jpg", Bitmap2Stream(bitmap));
//				}
				
				
//				Bundle bundle = data.getExtras();
//				headPhoto = bundle.getParcelable("data");
//				// 上传图片
//				pd = ProgressDialog.show(SetPortraitActivity.this, "", "头像上传中");
//				doImageUploadAction("jpg", Bitmap2Stream(headPhoto));
				break;
			case 4:
				startPhotoZoom(Uri.fromFile(tempFile), 150, 150, 3);
				break;
			default:
				break;
			}
		}
	}

	/**
	 * 裁剪图片的方法
	 * 
	 * @param uri
	 */
	public void picCut(Uri uri) {
		Intent intentCarema = new Intent("com.android.camera.action.CROP");
		intentCarema.setDataAndType(uri, "image/*");
		intentCarema.putExtra("crop", true);
		// intentCarema.putExtra("scale", false);
		// intentCarema.putExtra("noFaceDetection", true);//不需要人脸识别功能
		// intentCarema.putExtra("circleCrop", "");//设定此方法选定区域会是圆形区域
		// aspectX aspectY是宽高比例
		intentCarema.putExtra("aspectX", 1);
		intentCarema.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片的宽高
		intentCarema.putExtra("outputX", 150);
		intentCarema.putExtra("outputY", 150);
		intentCarema.putExtra("return-data", true);
		startActivityForResult(intentCarema, 3);
	}

	private void doImageUploadAction(String fileType, InputStream inStream) {
		ServerAdaptor.getInstance(SetPortraitActivity.this).uploadFile(
				fileType, "headportrait_path", inStream,
				new ServiceSyncListener() {
					@Override
					public void onSuccess(ActionResponse returnObject) {
						JSONObject jsonObject=(JSONObject)returnObject.getData();
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("userId", Constants.userId);
//						String resData = (String) returnObject.getData();
//						headPhotoPath = resData.substring(
//								resData.lastIndexOf("/") + 1, resData.length());
						String fileId="";
						try {
							fileId=jsonObject.getString("fileId");
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						map.put("headPortrait", fileId);
						
						System.out.println("路径"+headPhotoPath);
						doAsyncJsonAction(
								Constants.UrlHead
										+ "client.action.UserAction$uploadHeadPortrait",
								map);
					}

					@Override
					public void onError(ActionResponse returnObject) {
						if (pd != null && pd.isShowing()) {
							pd.dismiss();
						}
					}
				});
	}

	public InputStream Bitmap2Stream(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}
	
	// 使用系统当前日期加以调整作为照片的名称
		private String getPhotoFileName() {
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
			return dateFormat.format(date) + ".jpg";
		}

	private void doAsyncJsonAction(String method, HashMap<String, Object> map) {
		try {
			ServerAdaptor.getInstance(this).doAction(1, method, map,
					new ServiceSyncListener() {

						@Override
						public void onError(ActionResponse returnObject) {
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							SetPortraitActivity.this.finish();
						}

						@Override
						public void onSuccess(ActionResponse returnObject) {
							Toast.makeText(SetPortraitActivity.this, "头像上传成功",
									Toast.LENGTH_SHORT).show();
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							// 上传成功后将图像保存在本地
							
//							AppUtils.storeImageToSD(bitmap, headPhotoPath);
//							service.updateUserPic(headPhotoPath);
							JSONObject jsonObject=(JSONObject)returnObject.getData();
							if(!jsonObject.isNull("photo")){
								try {
									headPhotoPath=jsonObject.getString("photo");
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									headPhotoPath="";
								}
							}
							System.out.println("headphotopath====>"+headPhotoPath);
							AppUtils.storeImageToSD(headPhoto, headPhotoPath);
//							service.updateUserPic(headPhotoPath);
							Constants.loginBean.setHeadImg(headPhotoPath);
							Intent intent=new Intent();
							intent.putExtra("headPhotoPath", headPhotoPath);
							setResult(Constants.SIX, intent);
//							setResult(Constants.SIX);
							SetPortraitActivity.this.finish();
						}
					});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}
	}
	
	//切割图片
	private void startPhotoZoom(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		startActivityForResult(intent, requestCode);

	}
	

	private void setImage(Uri mImageCaptureUri) {

		// 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
		// 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
		ContentResolver cr = this.getContentResolver();
		Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
		if (cursor != null) {
			cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
			pickPhotoPath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
			String orientation = cursor.getString(cursor
					.getColumnIndex("orientation"));// 获取旋转的角度
			cursor.close();
			if (pickPhotoPath != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(pickPhotoPath);// 根据Path读取资源图片
				int angle = 0;
				if (orientation != null && !"".equals(orientation)) {
					angle = Integer.parseInt(orientation);
				}
				if (angle != 0) {
					// 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
					Matrix m = new Matrix();
					int width = bitmap.getWidth();
					int height = bitmap.getHeight();
					m.setRotate(angle); // 旋转angle度
					bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
							m, true);// 从新生成图片

				}
			}
		}
	}

}
