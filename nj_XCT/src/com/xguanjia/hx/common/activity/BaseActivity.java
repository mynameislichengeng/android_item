package com.xguanjia.hx.common.activity;

import java.io.File;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ColorUtil;
import com.xguanjia.hx.common.Function;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.contact.view.CustomProgressDialog;

public class BaseActivity extends ActivityGroup implements OnClickListener {
	public static int NET_WORK_STATE = 0; // 网络状态
	public static int SD_CARD_STATE = 0; // SD卡状态
	public static final int ACTIVITY_RESULT_CODE = 1;
	public static final int SESSION_OVERDUE_CODE = 2;

	public static String imei = "";
	public static String imsi = "";

	// menu按钮Id
	public static final int ITEM_ID_LOGINACTIVITY_SETTING = 100;
	public static final int ITEM_ID_LOGINACTIVITY_ABOUT = 101;

	protected Resources resource = null;

	protected RelativeLayout titleView = null; // 头部布局
	protected RelativeLayout mainView = null; // 中间内容布局
	protected RelativeLayout bottomView = null; // 底部按钮布局
	protected RelativeLayout bottomView1 = null; // 底部按钮布局
	protected RelativeLayout bottomView2 = null; // 底部按钮布局
	protected LinearLayout bottomViewBtn = null; // 底部按钮布局
	protected RelativeLayout topRadioView = null; // 其他通讯录布局
	private RelativeLayout activity_layout;
	protected Button contactTitleLeftBtn, contactTitleRightBtn;
	protected SharedPreferences sp,sf;
	protected SharedPreferences contactsPreferences; // 维护联系人时间戳
	protected SharedPreferences visitPreferences; // 维护拜访记录时间戳
	protected View contactTopView;
	protected View contactBottomView;
	protected RelativeLayout relativeLayout;
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	public DisplayImageOptions options;

	public static WindowManager wm;

	public NotificationManager notificationManager;
	public Notification notification;
	public PendingIntent contentIntent;

	public Button titleLeftBtn;
	public Button titleRightBtn;
	public final static int MSG_SHOW_CHILD_MENU = 0x00001;
	public final static int MSG_CHECK_STATE = 0x00002;
	public final static int MSG_SHOW_LISTVIEW_DATA = 0x00003;
	public final static int MSG_UPDATEA_TIP_TIME = 0x00004;

	protected Context mContext;

	public final static int BASE_SHOW_PROGRESS_DIALOG = 11112;
	public final static int BASE_HIDE_PROGRESS_DIALOG = 11113;
	protected CustomProgressDialog mProgressDialog;
	private boolean mIsRegisterLoginOutReceiver = false;
	public static final String FINISH_ACTION = "com.xizue.thinkchat.intent.action.ACTION_FINISH";
	protected int mWidth = 0;
	
	public ImageView home_xhd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mWidth = metrics.widthPixels;
		mContext = this;
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.base_activity);

		resource = this.getResources();
		relativeLayout = new RelativeLayout(BaseActivity.this);

		// 布局视图初始化
		titleView = (RelativeLayout) findViewById(R.id.top);
		mainView = (RelativeLayout) findViewById(R.id.main_view);
		bottomView = (RelativeLayout) findViewById(R.id.bottom);
		// bottomViewBtn=(LinearLayout)findViewById(R.id.bottomBtn);
		topRadioView = (RelativeLayout) findViewById(R.id.topRadio);
		bottomView1 = (RelativeLayout) findViewById(R.id.bottom1);
		bottomView2=(RelativeLayout) findViewById(R.id.bottom2);
		activity_layout = (RelativeLayout) findViewById(R.id.activity_layout);
		
		
	
		
		
		sp = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		contactsPreferences = getSharedPreferences("contacts_stamp",
				Context.MODE_PRIVATE);
		visitPreferences = getSharedPreferences("visit_stamp",
				Context.MODE_PRIVATE);
		contactTopView = LayoutInflater.from(this).inflate(
				R.layout.radio_group_title, null);
		contactBottomView = LayoutInflater.from(this).inflate(
				R.layout.radio_group_bottom, null);
		options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.default_avatar)
				.showImageForEmptyUri(R.drawable.default_avatar)
				.cacheInMemory().cacheOnDisc()
				.displayer(new RoundedBitmapDisplayer(10))
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		notificationManager = (NotificationManager) this
				.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
	}

	public NotificationManager getNotificationManager() {
		return notificationManager;
	}

	public Notification getNotification() {
		return notification;
	}

	/**
	 * 设置头部标题
	 * 
	 * @param titleText
	 *            标题文字
	 */
	protected void setTitleText(String titleText) {
		if (null == titleView) {
			return;
		}
		if (null == titleText || "".equals(titleText)) {
			return;
		}
		TextView titleTextView = (TextView) findViewById(R.id.TitleText);
		titleTextView.setText(titleText);

		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		titleTextView.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@Override
	protected void onPause() {
		super.onPause();

	}

	/**
	 * 设置头部标题按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleText(String buttonText,
			View.OnClickListener titleClickListener) {
		if (null == titleView) {
			return;
		}

		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		TextView titleTextView = (TextView) findViewById(R.id.TitleText);

		if (null != titleClickListener) {
			titleTextView.setOnClickListener(titleClickListener);
		}

		titleTextView.setText(buttonText + " ▼");

		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		titleTextView.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeftButtonBack(String buttonText, int resId,
			View.OnClickListener backClickListener) {
		if (null == titleView) {
			return;
		}
		Button leftButton = (Button) findViewById(R.id.title_leftBtnBack);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}
		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeftButton(String buttonText, int resId,
			View.OnClickListener backClickListener) {
		if (null == titleView) {
			return;
		}
		Button leftButton = (Button) findViewById(R.id.title_leftBtn);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}
		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮 发消息
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeftButtonSend(String buttonText, int resId,
			View.OnClickListener backClickListener) {
		if (null == titleView) {
			return;
		}
		Button leftButton = (Button) findViewById(R.id.title_leftBtnSend);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}
		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeft1Button(String buttonText, int resId,
			View.OnClickListener backClickListener) {
		if (null == titleView) {
			return;
		}

		Button leftButton = (Button) findViewById(R.id.title_leftBtn1);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}

		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeftButton1(String buttonText, int resId,
			View.OnClickListener leftButtonClickListener) {
		if (null == titleView) {
			return;
		}
		Button leftButton = (Button) findViewById(R.id.title_leftBtn);

		if (null != leftButtonClickListener) {
			leftButton.setOnClickListener(leftButtonClickListener);
		}

		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置右边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setTitleRightButton(String buttonText, int resId,
			View.OnClickListener rightButtonClickListener) {
		if (null == titleView) {
			return;
		}

		Button rightButton = (Button) findViewById(R.id.title_rightBtn);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);
		rightButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		rightButton.setVisibility(View.VISIBLE);

	}
	/**
	 * 设置右边 通知的标志图标
	 */
	public void setButton_right_notice(String buttonText, int resId,
			View.OnClickListener rightButtonClickListener){
		
		if (null == titleView) {
			return;
		}

		Button rightButton = (Button) findViewById(R.id.btn_notice_imag);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);
		rightButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		rightButton.setVisibility(View.VISIBLE);
		
	}
	
	
	
	
	
	
	/**
	 * 设置右边饼图 发消息
	 * 
	 * @param Imageview
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void showTitleRightImage(
			View.OnClickListener rightButtonClickListener) {
		if (null == titleView) {
			return;
		}

		ImageView img = (ImageView) findViewById(R.id.title_rightImagepieChart);

		if (null != rightButtonClickListener) {
			img.setOnClickListener(rightButtonClickListener);
		}

		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		img.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置右边按钮 发消息
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setTitleRightButtonSend(String buttonText, int resId,
			View.OnClickListener rightButtonClickListener) {
		if (null == titleView) {
			return;
		}

		Button rightButton = (Button) findViewById(R.id.title_rightBtnSend1);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);
		rightButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		rightButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置右边按钮 发送
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setTitleRightButtonRight(String buttonText, int resId,
			View.OnClickListener rightButtonClickListener) {
		if (null == titleView) {
			return;
		}

		Button rightButton = (Button) findViewById(R.id.title_rightBtnSend);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);
		rightButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		rightButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置中间按钮事件
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setTitleRight1Button(String buttonText, int resId,
			View.OnClickListener rightButtonClickListener) {
		if (null == titleView) {
			return;
		}

		Button rightButton = (Button) findViewById(R.id.title_rightBtn1);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);
		rightButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		rightButton.setVisibility(View.VISIBLE);

	}

	public Button getRightButton() {
		return (Button) findViewById(R.id.title_rightBtn);
	}

	/**
	 * 设置底部按钮1
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setBottomButton1(String buttonText,
			MOAOnClickListener buttonClickListener) {
		if (null == bottomView) {
			return;
		}
		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button bottomButton = (Button) bottomView
				.findViewById(R.id.bottom_leftBtn1);

		if (null != buttonClickListener) {
			bottomButton.setOnClickListener(buttonClickListener);
		}

		bottomButton.setText(buttonText);

		if (bottomView.getVisibility() != View.VISIBLE) {
			bottomView.setVisibility(View.VISIBLE);
		}

		bottomButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置底部按钮2
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setBottomButton2(String buttonText,
			MOAOnClickListener buttonClickListener) {
		if (null == bottomView) {
			return;
		}
		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button bottomButton = (Button) bottomView
				.findViewById(R.id.bottom_leftBtn2);

		if (null != buttonClickListener) {
			bottomButton.setOnClickListener(buttonClickListener);
		}

		bottomButton.setText(buttonText);

		if (bottomView.getVisibility() != View.VISIBLE) {
			bottomView.setVisibility(View.VISIBLE);
		}

		bottomButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置底部按钮3
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setBottomButton3(String buttonText,
			MOAOnClickListener buttonClickListener) {
		if (null == bottomView) {
			return;
		}
		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button bottomButton = (Button) bottomView
				.findViewById(R.id.bottom_leftBtn3);

		if (null != buttonClickListener) {
			bottomButton.setOnClickListener(buttonClickListener);
		}

		bottomButton.setText(buttonText);

		if (bottomView.getVisibility() != View.VISIBLE) {
			bottomView.setVisibility(View.VISIBLE);
		}

		bottomButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置底部按钮2
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setBottomButton4(String buttonText,
			MOAOnClickListener buttonClickListener) {
		if (null == bottomView) {
			return;
		}
		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button bottomButton = (Button) bottomView
				.findViewById(R.id.bottom_leftBtn4);

		if (null != buttonClickListener) {
			bottomButton.setOnClickListener(buttonClickListener);
		}

		bottomButton.setText(buttonText);

		if (bottomView.getVisibility() != View.VISIBLE) {
			bottomView.setVisibility(View.VISIBLE);
		}

		bottomButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置头部左边按钮 发消息
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setTitleLeftButtonSendRight(String buttonText, int resId,
			View.OnClickListener backClickListener) {
		if (null == titleView) {
			return;
		}
		Button leftButton = (Button) findViewById(R.id.title_leftBtnSendRight);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}
		leftButton.setText(buttonText);
		leftButton.setBackgroundResource(resId);
		if (titleView.getVisibility() != View.VISIBLE) {
			titleView.setVisibility(View.VISIBLE);
		}

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置底部返回按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setBottomBackButton(String buttonText,
			MOAOnClickListener buttonClickListener) {
		if (null == bottomView) {
			return;
		}

		Button bottomButton = (Button) bottomView
				.findViewById(R.id.bottom_rightBtn);

		if (null != buttonClickListener) {
			bottomButton.setOnClickListener(buttonClickListener);
		} else {
			bottomButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}

		if (null != buttonText && !"".equals(buttonText)) {
			bottomButton.setText(buttonText);
		}

		if (bottomView.getVisibility() != View.VISIBLE) {
			bottomView.setVisibility(View.VISIBLE);
		}

		bottomButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 获取sd卡的状态
	 * 
	 * @return SD卡的状态 如果SD卡不可用，则返回null 如果可用则返回当前的SD卡的路径
	 */
	public File getSDcard() {
		File state = null;
		String SDCardState = Environment.getExternalStorageState();
		if (SDCardState.equals(Environment.MEDIA_MOUNTED)) {
			// SD卡可用的状态
			state = Environment.getExternalStorageDirectory();
		}
		return state;

	}

	/**
	 * 移动记录
	 */
	public void setMoveRecord(View.OnClickListener listener1,
			View.OnClickListener listener2) {
		LinearLayout nextAndPreviousBtn = new LinearLayout(this);
		nextAndPreviousBtn.setOrientation(LinearLayout.HORIZONTAL);
		ImageButton prevImgBtn = new ImageButton(this);
		prevImgBtn
				.setBackgroundResource(R.drawable.previous_record_btn_selector);
		LinearLayout.LayoutParams paramsBtn = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		paramsBtn.gravity = Gravity.CENTER;
		prevImgBtn.setId(R.id.previous_record_btn);
		nextAndPreviousBtn.addView(prevImgBtn, paramsBtn);
		View v = new View(this);
		LinearLayout.LayoutParams paramsV = new LinearLayout.LayoutParams(1,
				LayoutParams.WRAP_CONTENT);
		paramsV.topMargin = Function.convertDip2Px(this, 9);
		paramsV.bottomMargin = Function.convertDip2Px(this, 9);
		int color = ColorUtil.getColorValue("#366b84");
		v.setBackgroundColor(color);
		v.getBackground().setAlpha(200);
		nextAndPreviousBtn.addView(v, paramsV);
		ImageButton nextImgBtn = new ImageButton(this);
		nextImgBtn.setBackgroundResource(R.drawable.next_record_btn_selector);
		nextImgBtn.setId(R.id.next_record_btn);
		nextAndPreviousBtn.addView(nextImgBtn, paramsBtn);
		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		params.alignWithParent = true;
		params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
		params.rightMargin = 15;
		titleView.addView(nextAndPreviousBtn, params);
		if (listener1 != null) {
			prevImgBtn.setOnClickListener(listener1);
		}
		if (listener2 != null) {
			nextImgBtn.setOnClickListener(listener2);
		}

	}

	/**
	 * 获取下一条按钮
	 * 
	 * @return
	 */
	public View getNextImgBtn() {
		return findViewById(R.id.next_record_btn);
	}

	/**
	 * 获取上一条按钮
	 */
	public View getPreviousImgBtn() {
		return findViewById(R.id.previous_record_btn);
	}

	// @Override
	// public void onBackPressed() {
	// return;
	// }

	public void functionGuide(final String functionName, int guideImageResources) {
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		relativeLayout.setLayoutParams(layoutParams);
		ImageView imageIv = new ImageView(BaseActivity.this);
		imageIv.setBackgroundResource(guideImageResources);
		relativeLayout.addView(imageIv, layoutParams);
		RelativeLayout fullView = (RelativeLayout) this
				.findViewById(R.id.activity_layout);
		fullView.addView(relativeLayout, layoutParams);
		relativeLayout.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				relativeLayout.setVisibility(View.GONE);
				SharedPreferences.Editor editor = getSharedPreferences(
						"basic_info", Context.MODE_PRIVATE).edit();
				editor.putBoolean(functionName, false);
				editor.commit();
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/**
	 * 设置头部左边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param backListener
	 *            按钮点击事件
	 */
	protected void setContactTitleLeftButton(String buttonText,
			View.OnClickListener backClickListener) {
		if (null == contactTopView) {
			return;
		}

		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button leftButton = (Button) contactTopView
				.findViewById(R.id.contact_title_leftBtn);

		if (null != backClickListener) {
			leftButton.setOnClickListener(backClickListener);
		} else {
			leftButton.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					BaseActivity.this.finish();
				}
			});
		}

		leftButton.setText(buttonText);

		leftButton.setVisibility(View.VISIBLE);

	}

	/**
	 * 设置右边按钮
	 * 
	 * @param buttonText
	 *            按钮文字
	 * @param rightButtonClickListener
	 *            按钮事件
	 */
	protected void setContactTitleRightButton(String buttonText,
			View.OnClickListener rightButtonClickListener) {
		if (null == contactTopView) {
			return;
		}
		if (null == buttonText || "".equals(buttonText)) {
			return;
		}

		Button rightButton = (Button) contactTopView
				.findViewById(R.id.contact_title_rightBtn);

		rightButton
				.setBackgroundResource(R.drawable.title_rightbutton_selector);

		if (null != rightButtonClickListener) {
			rightButton.setOnClickListener(rightButtonClickListener);
		}

		rightButton.setText(buttonText);

		rightButton.setVisibility(View.VISIBLE);

	}

	public void showNotification(int drawable, String tickerText,
			String contentTile, String contentText) {
		Intent notificationIntent = new Intent(this, this.getClass());
		contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
				0);
		notification = new Notification(drawable, tickerText,
				System.currentTimeMillis());
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(this, contentTile, contentText,
				contentIntent);
		notificationManager.notify(10, notification);
		notificationManager.cancel(10);
	}

	public void showNotification(int type, int drawable, String tickerText,
			String contentTile, String contentText) {
		Intent notificationIntent = new Intent(this, this.getClass());
		contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
				0);
		notification = new Notification(drawable, tickerText,
				System.currentTimeMillis());
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		notification.setLatestEventInfo(this, contentTile, contentText,
				contentIntent);
		notificationManager.notify(type, notification);
		if (type == 10) {
			notificationManager.cancel(type);
		}
	}

	public RelativeLayout getActivity_layout() {
		return activity_layout;
	}

	public void setActivity_layout(RelativeLayout activity_layout) {
		this.activity_layout = activity_layout;
	}

	public RelativeLayout getRelativeLayout() {
		return relativeLayout;
	}

	public void setRelativeLayout(RelativeLayout relativeLayout) {
		this.relativeLayout = relativeLayout;
	}

	@Override
	public Resources getResources() {
		Resources res = super.getResources();
		Configuration config = new Configuration();
		config.setToDefaults();
		res.updateConfiguration(config, res.getDisplayMetrics());
		return res;
	}

	public void hideSoftKeyboard() {
		hideSoftKeyboard(getCurrentFocus());
	}

	public void hideSoftKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void showProgressDialog(String msg, boolean isCancelable) {
		baseShowProgressDialog(msg, isCancelable);
	}

	public void showProgressDialog(String msg) {
		showProgressDialog(msg, true);
	}

	public void hideProgressDialog() {
		mBaseHandler.sendEmptyMessage(IndexActivity.BASE_HIDE_PROGRESS_DIALOG);
	}

	public void baseShowProgressDialog(String msg, boolean isCancelable) {
		mProgressDialog = new CustomProgressDialog(mContext);
		mProgressDialog.setMessage(msg);
		mProgressDialog.setCancelable(isCancelable);
		mProgressDialog.show();
	}

	protected void baseHideProgressDialog() {
		if (mProgressDialog != null && mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	public Handler mBaseHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case BASE_SHOW_PROGRESS_DIALOG:
				String dialogMsg = (String) msg.obj;
				boolean isCancelable = true;
				if (msg.arg1 == 1) {
					isCancelable = false;
				}
				baseShowProgressDialog(dialogMsg, isCancelable);
				break;
			case BASE_HIDE_PROGRESS_DIALOG:
				baseHideProgressDialog();
				String showMsg = (String) msg.obj;
				if (!TextUtils.isEmpty(showMsg)) {
					showToast(showMsg);
				}
				break;

			case HaoXinProjectActivity.MSG_NETWORK_ERROR:
				Toast.makeText(mContext, R.string.network_error,
						Toast.LENGTH_LONG).show();
				return;

			case HaoXinProjectActivity.MSG_TICE_OUT_EXCEPTION:
				String message = (String) msg.obj;
				if (message == null || message.equals("")) {
					message = mContext.getString(R.string.timeout);
				}
				Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
				break;

			case HaoXinProjectActivity.MSG_LOAD_ERROR:
				baseHideProgressDialog();
				String error_Detail = (String) msg.obj;
				showToast(error_Detail);
				break;
			}
		}
	};

	protected void registerFinishReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(FINISH_ACTION);
		registerReceiver(mFinishReceiver, filter);
		mIsRegisterLoginOutReceiver = true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mIsRegisterLoginOutReceiver) {
			mIsRegisterLoginOutReceiver = false;
			unregisterReceiver(mFinishReceiver);
		}
	}

	BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(FINISH_ACTION)) {
				((Activity) mContext).finish();
			}
		}
	};

	public void showToast(String content) {
		Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
	}

	/** +++ for title bar +++ */
	protected ImageView mLeftBtn, mRightBtn, mNotifyIcon;
	protected TextView titileTextView, mLeftTextBtn, mRightTextBtn;
	protected RelativeLayout mTitleLayout;

	protected void setTitleContent(int left_src_id, int right_src_id,
			String title) {
		mTitleLayout = (RelativeLayout) findViewById(R.id.title_layout);
		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		titileTextView = (TextView) findViewById(R.id.title);

		if (left_src_id != 0) {
			mLeftBtn.setImageResource(left_src_id);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setLeftText(String left_src_id, int right_src_id,
			String title) {

		mRightBtn = (ImageView) findViewById(R.id.right_btn);
		titileTextView = (TextView) findViewById(R.id.title);
		mLeftTextBtn = (TextView) findViewById(R.id.left_text_btn);
		if (left_src_id != "") {
			mLeftTextBtn.setText(left_src_id);
			mLeftTextBtn.setVisibility(View.VISIBLE);
		}

		if (right_src_id != 0) {
			mRightBtn.setImageResource(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setRightText(int left_src_id, String right_src_id,
			String title) {

		mLeftBtn = (ImageView) findViewById(R.id.left_btn);
		titileTextView = (TextView) findViewById(R.id.title);
		mRightTextBtn = (TextView) findViewById(R.id.right_text_btn);

		if (left_src_id != 0) {
			mLeftBtn.setImageResource(left_src_id);
		}

		if (right_src_id != "") {
			mRightTextBtn.setVisibility(View.VISIBLE);
			mRightTextBtn.setText(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	protected void setText(String left_src_id, String right_src_id, String title) {

		titileTextView = (TextView) findViewById(R.id.title);
		mLeftTextBtn = (TextView) findViewById(R.id.left_text_btn);
		mRightTextBtn = (TextView) findViewById(R.id.right_text_btn);

		if (left_src_id != "") {
			mLeftTextBtn.setText(left_src_id);
		}

		if (right_src_id != "") {
			mRightTextBtn.setText(right_src_id);
		}

		if (title != "") {
			titileTextView.setText(title);
		}
	}

	/** --- for title bar --- */

}

