package com.haoqee.chat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.haoqee.chat.exception.SPException;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.haoqee.chat.global.ImageLoader;
import com.haoqee.chat.global.MD5;
import com.haoqee.chat.widget.GestureDetector;
import com.haoqee.chat.widget.MyImageView;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.chinamobile.salary.R;

/**
 * 查看大图页面
 *
 */
public class ShowImageActivity extends BaseActivity implements OnClickListener, OnTouchListener{
	
	
	private MyImageView mImageView;									//图片显示控件
	private String mImageUrl;										//图片URL或图片本地路径
	private RelativeLayout mTitleLayout;							//标题栏
	private RelativeLayout mRelativeLayout;							//图片控件父控件
	final GetterHandler mAnimHandler = new GetterHandler();			
	private Bitmap mBitmap;											//URL或本地路径对应的Bitmap
	
	/** Bitmap生成成功消息类型 */
	public final static int SET_IMAGE_BITMAP = 11126;
	/** 保存成功消息类型 */
	private final static int SAVE_SUCCESS = 5126;
	protected GestureListener mGestureListener;						//手势监听对象
	protected GestureDetector mGestureDetector;						//手势控制对象
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		setContentView(R.layout.view_image);
		registerFinishReceiver();
		initComponent();
	}

	private void initComponent(){
		mImageUrl = getIntent().getStringExtra("imageurl");
		mImageView = (MyImageView) findViewById(R.id.imageview);
		mImageView.setOnTouchListener(this);
		findViewById(R.id.imageviewer_toolbar).setVisibility(View.GONE);
		
		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn, "");
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
		mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		mTitleLayout.setVisibility(View.INVISIBLE);
		mTitleLayout.setBackgroundDrawable(null);
		
		mGestureListener = new GestureListener();
		mGestureDetector = new GestureDetector(this, mGestureListener, null, true);
		mRelativeLayout = (RelativeLayout) findViewById(R.id.showZoomInOutLayout);
		mRelativeLayout.setOnTouchListener(new OnTouchListener(){

			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					showOnScreenControls();
					scheduleDismissOnScreenControls();
					break;

				case MotionEvent.ACTION_MOVE:
					break;

				case MotionEvent.ACTION_UP:
					break;

				default:
					break;
				}
				return false;
			}
        });


		if(mImageUrl != null && !mImageUrl.equals("")){
			Message message = new Message();
			message.what = HaoXinProjectActivity.SHOW_PROGRESS_DIALOG;
			message.obj = mContext.getString(R.string.add_more_loading);
			mAnimHandler.sendMessage(message);
		}else {
			this.finish();
		}
	}
	
	class GetterHandler extends Handler {
        private static final int IMAGE_GETTER_CALLBACK = 1;

        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case IMAGE_GETTER_CALLBACK:
                    ((Runnable) msg.obj).run();
                    break;
                    
               case SET_IMAGE_BITMAP:
    				if(mBitmap != null){
    					mImageView.setImageBitmap(mBitmap);
    				}else {
    					Toast.makeText(mContext, mContext.getString(R.string.load_error), Toast.LENGTH_LONG).show();
    					ShowImageActivity.this.finish();
    				}
    				break;
    				
               case HaoXinProjectActivity.SHOW_PROGRESS_DIALOG:
            	   String str = (String)msg.obj;
            	   showProgressDialog(str);
            	   if(mImageUrl.startsWith("http://")){
            		   loadImage(mImageUrl);
				   } else {
					   mBitmap = BitmapFactory.decodeFile(mImageUrl);
					   hideProgressDialog();
					   if (mBitmap != null) {
						   mImageView.setImageBitmap(mBitmap);
					   } else {
						   Toast.makeText(mContext,	mContext.getString(R.string.load_error), Toast.LENGTH_LONG).show();
						   ShowImageActivity.this.finish();
					   }
            	   }
					
            	   break;
   	        
               case HaoXinProjectActivity.HIDE_PROGRESS_DIALOG:
            	   hideProgressDialog();
            	   break;
            	   
               case SAVE_SUCCESS:
            	   Toast.makeText(mContext, mContext.getString(R.string.save_picture_to_ablun), Toast.LENGTH_SHORT).show();
            	   break;
            	   
               case HaoXinProjectActivity.MSG_NETWORK_ERROR:
            	   hideProgressDialog();
            	   Toast.makeText(mContext, mContext.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            	   ShowImageActivity.this.finish();
            	   break;
    					
            }
        }

        public void postGetterCallback(Runnable callback) {
           postDelayedGetterCallback(callback, 0);
        }

        public void postDelayedGetterCallback(Runnable callback, long delay) {
            if (callback == null) {
                throw new NullPointerException();
            }
            Message message = Message.obtain();
            message.what = IMAGE_GETTER_CALLBACK;
            message.obj = callback;
            sendMessageDelayed(message, delay);
        }

        public void removeAllGetterCallbacks() {
            removeMessages(IMAGE_GETTER_CALLBACK);
        }
    }
    
    @Override
	protected void onDestroy() {
    	mImageView.setImageBitmap(null);
    	if(mBitmap  != null && !mBitmap.isRecycled()){
    		mBitmap.recycle();
    		mBitmap = null;
    	}
		super.onDestroy();
	}


	private final Runnable mDismissOnScreenControlRunner = new Runnable() {
        public void run() {
            hideOnScreenControls();
        }
    };
    
    private void hideOnScreenControls() {

        if (mTitleLayout.getVisibility() == View.VISIBLE) {
            Animation a = new AlphaAnimation(1, 0);;
            a.setDuration(500);
            mTitleLayout.startAnimation(a);
            mTitleLayout.setVisibility(View.INVISIBLE);
        }

    }

    private void showOnScreenControls() {
        //if (mPaused) return;
        // If the view has not been attached to the window yet, the
        // zoomButtonControls will not able to show up. So delay it until the
        // view has attached to window.
        if (mTitleLayout.getVisibility() != View.VISIBLE) {
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(500);
            mTitleLayout.startAnimation(animation);
            mTitleLayout.setVisibility(View.VISIBLE);
        }else {
			hideOnScreenControls();
		}
    }
    
    private void scheduleDismissOnScreenControls() {
    	mAnimHandler.removeCallbacks(mDismissOnScreenControlRunner);
    	mAnimHandler.postDelayed(mDismissOnScreenControlRunner, 2000);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.left_btn:
			ShowImageActivity.this.finish();
			break;
			
		case R.id.right_btn:			//弹出图片保存对话框
			showPromptDialog();
			break;
			
		case R.id.imageview:
			showOnScreenControls();
			scheduleDismissOnScreenControls();
			break;
		default:
			break;
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		mGestureDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}
	
	class GestureListener extends GestureDetector.SimpleOnGestureListener {

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			showOnScreenControls();
			scheduleDismissOnScreenControls();
			return super.onSingleTapConfirmed(e);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return super.onSingleTapUp(e);
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			return super.onDoubleTap(e);
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			return super.onFling(e1, e2, velocityX, velocityY);
		}
		
	}
	
	/**
	 * 保存图片对话框
	 * @param context
	 */
	private void showPromptDialog(){
		
		final Dialog dlg = new Dialog(mContext, R.style.MMThem_DataSheet);
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.choose_picture_dialog, null);
		final int cFullFillWidth = 10000;
		layout.setMinimumWidth(cFullFillWidth);
		
		final Button promptView = (Button)layout.findViewById(R.id.camera);
		final Button okBtn = (Button)layout.findViewById(R.id.gallery);
		final Button cancelBtn = (Button)layout.findViewById(R.id.cancelbtn);
		
		promptView.setEnabled(false);
		promptView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		promptView.setText(mContext.getString(R.string.sure_to_save_picture_to_ablum));
		promptView.setTextColor(mContext.getResources().getColor(R.color.content_gray_color));
		
		okBtn.setText(mContext.getString(R.string.save));
		cancelBtn.setText(mContext.getString(R.string.cancel));
		
		okBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dlg.dismiss();
				savePicture();
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
	 * 下载图片
	 * @param mImageUrl				图片URL
	 */
	private void loadImage(final String mImageUrl ){
		new Thread(){
			@Override
			public void run() {
				super.run();
				if(mImageUrl != null){
					try {
						File file = null;
						byte[] imageData = null;
						String fileName = new MD5().getMD5ofStr(mImageUrl);// url.replaceAll("/",
						if (FeatureFunction.checkSDCard()) {

							if (FeatureFunction.newFolder(Environment.getExternalStorageDirectory()
									+ ImageLoader.SDCARD_PICTURE_CACHE_PATH)) {
								file = new File(
										Environment.getExternalStorageDirectory()
										+ ImageLoader.SDCARD_PICTURE_CACHE_PATH, fileName);
								if(file != null && file.exists()){
									try {
										FileInputStream fin = new FileInputStream(file.getPath());
										int length = fin.available();
										byte[] buffer = new byte[length];
										fin.read(buffer);
										fin.close();
										imageData = buffer;
										buffer = null;
										System.gc();
									} catch (FileNotFoundException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

								}
								else{
									if(!Common.verifyNetwork(mContext)){
										mAnimHandler.sendEmptyMessage(HaoXinProjectActivity.MSG_NETWORK_ERROR);
										return;
									}
									imageData = FeatureFunction.getImage(new URL(mImageUrl),file);
								}
							}
 						}
						
						if(imageData != null){
							mBitmap = BitmapFactory.decodeByteArray(imageData, 0,imageData.length);
						}
						imageData = null;
						System.gc();
						
					} catch (MalformedURLException e) {
						e.printStackTrace();
						
					} catch (SPException e) {
						e.printStackTrace();
					}
					
					mAnimHandler.sendEmptyMessage(HaoXinProjectActivity.HIDE_PROGRESS_DIALOG);
					mAnimHandler.sendEmptyMessage(SET_IMAGE_BITMAP);
				}
			}
		}.start();
	}

	/**
	 * 将图片保存到相册
	 */
	private void savePicture(){
		new Thread(){
			@Override
			public void run(){
				String fileName = FeatureFunction.getPhotoFileName();
				String uri = MediaStore.Images.Media.insertImage(getContentResolver(), mBitmap, fileName, "");
				String filePath = FeatureFunction.getFilePathByContentResolver(mContext, Uri.parse(uri));
				int index = filePath.lastIndexOf("/");
				String filePrefix = filePath.substring(0, index + 1) + fileName;
				try {
					File bitmapFile = new File(filePrefix);
					FileOutputStream bitmapWriter;
					bitmapWriter = new FileOutputStream(bitmapFile);
					if (mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapWriter)) {
						File oldfile = new File(filePath);
						if(oldfile.exists()){
							oldfile.delete();
						}
						bitmapWriter.flush();
						bitmapWriter.close();
						Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);     
						Uri refreshUri = Uri.fromFile(bitmapFile);     
						intent.setData(refreshUri);     
						mContext.sendBroadcast(intent);  
						mAnimHandler.sendEmptyMessage(SAVE_SUCCESS);
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
		
	}
}
