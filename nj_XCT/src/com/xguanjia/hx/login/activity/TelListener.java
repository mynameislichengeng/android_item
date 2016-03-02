package com.xguanjia.hx.login.activity;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.contact.service.PersonService;

/**
 * 
 * @author huke
 * 
 * 说明：电话拦截器
 * 
 * 日期：2013-08-06
 *
 */
public class TelListener extends PhoneStateListener {
	
	private static final String TAG = "TelListener";

	private Context context;
	private WindowManager wm;
	private TextView userInfoTv,persontext,deptertext;
	private float touchX;
	private float touchY;
	private PersonService personService;
	private WindowManager.LayoutParams params;
	private int statusBarHeight;// 状态栏高度
	private View view;// 透明窗体
	private WindowManager.LayoutParams layoutParams;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private DisplayImageOptions options;
	private ImageView personimage;
	private SharedPreferences sp ;

	public TelListener(Context context) {
		this.context = context;
		sp = context.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		personService = new PersonService(context);
		options = new DisplayImageOptions.Builder().showStubImage(R.drawable.default_avatar).showImageForEmptyUri(R.drawable.default_avatar).cacheInMemory().cacheOnDisc().displayer(
				new RoundedBitmapDisplayer(10)).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
	}

	@Override
	public void onCallStateChanged(int state, String incomingNumber) {
		super.onCallStateChanged(state, incomingNumber);
		switch (state) {
		case TelephonyManager.CALL_STATE_RINGING:
			Log.e("--------", "-----------"+incomingNumber);
			showView(incomingNumber);
			break;
		case TelephonyManager.CALL_STATE_IDLE:
			System.out.println("CALL_STATE_IDLECALL_STATE_IDLECALL_STATE_IDLECALL_STATE_IDLE");
			if (wm != null) {
				Log.d(TAG, "执行了tele方法");
				wm.removeView(view);
				wm = null;
			}
//			if(isCheck){
//				DatabaseHelper dbOpenHelper = DatabaseHelper.getInstance(context);
//				dbOpenHelper.closeDB();
//				Constants.isFirst = "0";
//				Intent intent = new Intent();
//				intent = new Intent();
//				intent.setClass(context, TeLoginActivity.class);
////				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
//				intent.addCategory(Intent.CATEGORY_HOME);  
//				Editor ed = sp.edit();
//				ed.putBoolean("isNeedVoluntaryLogin", true);
//				ed.commit();
//				context.startActivity(intent);
//			}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			System.out.println("执行了callstateofffff");
			if (wm != null) {
				Log.d(TAG, "执行了tele方法");
				wm.removeView(view);
				wm = null;
			}
			System.out.println("TelephonyManager.CALL_STATE_OFFHOOK");
			break;
		default:
			break;
		}
	}
//
//	public void updateViewPosition() {
//		params.x = (int) (touchX - userInfoTv.getWidth() / 2);
//		params.y = (int) (touchY - userInfoTv.getHeight() / 2);
//		wm.updateViewLayout(userInfoTv, params);
//	}
	
	
	
	
	
	/*
	 * 悬浮
	 */
	public void refreshView(int x, int y) {
        //状态栏高度不能立即取，不然得到的值是0
        if(statusBarHeight == 0){
            View rootView  = view.getRootView();
            Rect r = new Rect();
            rootView.getWindowVisibleDisplayFrame(r);
            statusBarHeight = r.top;
        }
        
        layoutParams.x = x;
        // y轴减去状态栏的高度，因为状态栏不是用户可以绘制的区域，不然拖动的时候会有跳动
        layoutParams.y = y - statusBarHeight;//STATUS_HEIGHT;
        refresh();
    }

    /**
     * 添加悬浮窗或者更新悬浮窗 如果悬浮窗还没添加则添加 如果已经添加则更新其位置
     */
    private void refresh() {
    	wm.updateViewLayout(view, layoutParams);
    }
    
    
    private void showView(String incomingNumber){
		LayoutInflater inflater = LayoutInflater.from(context);
		view = (LinearLayout) inflater.inflate(R.layout.personsetdia, null);
		persontext = (TextView) view.findViewById(R.id.persontext);
		deptertext = (TextView) view.findViewById(R.id.deptertext);
		personimage = (ImageView) view.findViewById(R.id.personimage);
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        /*
         * LayoutParams.TYPE_SYSTEM_ERROR：保证该悬浮窗所有View的最上层
         * LayoutParams.FLAG_NOT_FOCUSABLE:该浮动窗不会获得焦点，但可以获得拖动
         * PixelFormat.TRANSPARENT：悬浮窗透明
         */
        layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
        		LayoutParams.WRAP_CONTENT, LayoutParams.TYPE_SYSTEM_ERROR,
                LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSPARENT);
        layoutParams.gravity = Gravity.TOP;
		view.setOnTouchListener(new OnTouchListener() {
	            float[] temp = new float[] { 0f, 0f };
	            public boolean onTouch(View v, MotionEvent event) {
	                int eventaction = event.getAction();
	                switch (eventaction) {
	                case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
	                    temp[0] = event.getX();
	                    temp[1] = event.getY();
	                    break;

	                case MotionEvent.ACTION_MOVE:
	                    refreshView((int) (event.getRawX() - temp[0]), (int) (event
	                            .getRawY() - temp[1]));
	                    break;

	                }
	                return true;
	            }

	        });
		
		if(incomingNumber.length()>10){
			List<String> personInfo = personService.queryUserInfoByPhoneNum(incomingNumber);
			Log.e(TAG, "-------------------2");
			String avatarStr = "";
			if(personInfo!=null){
				Log.e(TAG, "-------------------3");
				if(personInfo.size()>0){
					Log.e(TAG, "-------------------4"+personInfo.get(1)+"----"+personInfo.get(0));
					persontext.setText(personInfo.get(1));
					deptertext.setText(personInfo.get(0));
					if(!personInfo.get(2).trim().equals("")){
						Log.e(TAG, "-------------------5");
						avatarStr = personInfo.get(2);
						String[] splitPath = avatarStr.split("/");
						avatarStr = Constants.SAVE_IMAGE_PATH +"HeadAvatar/" + splitPath[splitPath.length-1];
						Log.i(TAG, avatarStr);
						File file = new File(avatarStr);
						if (file.exists()) {
							imageLoader.displayImage("file://" + avatarStr, personimage, options);
						}
					}
				}
				wm.addView(view, layoutParams);
			}else{
				wm=null;//重新置空
				Log.i(TAG, "获取到陌生电话，号码为" + incomingNumber+"222");
			}
		}else{
			wm=null;//重新置空
			Log.i(TAG, "获取到陌生电话，号码为" + incomingNumber+"222");
		}
	}
    
    
//    
//    public static int px2dip(Context context, float pxValue) {
//		final float scale = context.getResources().getDisplayMetrics().density;
//		return (int) (pxValue / scale + 0.5f);
//	}
	
	

}
