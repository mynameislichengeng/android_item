package com.xguanjia.hx.set;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
import com.xguanjia.hx.contact.bean.PersonBean;
import com.xguanjia.hx.contact.service.ContactPersonService;

public class SetOtherInfoActivity extends BaseActivity {
	private static final String TAG = "SetOtherInfoActivity";
	private EditText editqiye,editName, editPhone, editEmail, loginNameEdit,deptNameEt,
			positionEdit;
	private OnClickListener backClickListener;
	private ContactPersonService personService;
	private ProgressDialog pd;
	private PersonBean personBean;
	private View view;
	private Editor editor;
	private LinearLayout personalLayout;
	private ImageView personalImage;
	private SharedPreferences sf, sf1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		initView();
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

	private void initView() {
		sf1 = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		sf = SetOtherInfoActivity.this.getSharedPreferences("basic_info",
				Context.MODE_PRIVATE);
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		view = getLayoutInflater().inflate(R.layout.set_other_info, null);
		mainView.addView(view, param);
		personService = new ContactPersonService(this);
		personBean = personService.queryPersonByUserId(Constants.userId);
		editqiye=(EditText)view.findViewById(R.id.qiyeName);
		editqiye.setText(Constants.loginBean.getDepname());
		editName = (EditText) view.findViewById(R.id.EditName);
		editName.setText(Constants.loginBean.getUserName());
		editPhone = (EditText) view.findViewById(R.id.EditPhone);
		editPhone.setText(Constants.loginBean.getMobile());
		editEmail = (EditText) view.findViewById(R.id.EditEmail);
		editEmail.setText(Constants.loginBean.getEmail());
		deptNameEt=(EditText)view.findViewById(R.id.departmentEdit);
		deptNameEt.setText(Constants.loginBean.getDeptName());
		loginNameEdit = (EditText) view.findViewById(R.id.loginNameEdit);
		loginNameEdit.setText(sp.getString("loginName", ""));
		positionEdit = (EditText) view.findViewById(R.id.positionEdit);
		positionEdit.setText(personBean.getPost());
		personalLayout = (LinearLayout) this
				.findViewById(R.id.set_group_personal_layout);
		personalLayout.setOnClickListener(this);
		personalImage = (ImageView) this.findViewById(R.id.setPersonal_image);
		backClickListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.set_group_personal_layout:
					Intent intent = new Intent();
					intent.setClass(SetOtherInfoActivity.this, SetPortraitActivity.class);
					startActivityForResult(intent, Constants.TWO);
					break;
				case R.id.title_leftBtnBack:
					SetOtherInfoActivity.this.finish();
					break;
				case R.id.title_rightBtn:
					// 提交修改信息
					HashMap<String, Object> requestMap = new HashMap<String, Object>();
					// if (!"".equals(editName.getText())) {
					// requestMap.put("userName", editName.getText());
					// }
					if (!"".equals(editPhone.getText())) {
						requestMap.put("workPhone", editPhone.getText());
					}
					if (!"".equals(editEmail.getText())) {
						requestMap.put("email", editEmail.getText());
					}
					requestMap.put("userId", Constants.userId);
					pd = ProgressDialog.show(SetOtherInfoActivity.this, "",
							"信息修改中");
					doActionRequest(requestMap);
					break;

				default:
					break;
				}
			}
		};
		setTitleText("基本信息");
		setTitleLeftButtonBack("", R.drawable.back_selector, backClickListener);
		// setTitleRightButton("提交", R.drawable.title_rightbutton_selector,
		// backClickListener);
		
		String path = Constants.loginBean.getHeadImg();
		sf.edit().putString("headImg", "http://" + sf1.getString("ip", Constants.IP)+ path).commit();
		options = new DisplayImageOptions.Builder()
		.showStubImage(R.drawable.default_avatar1)
		.showImageForEmptyUri(R.drawable.default_avatar1)
		.cacheInMemory().cacheOnDisc()
		.displayer(new RoundedBitmapDisplayer(100))
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage("http://" + sf1.getString("ip", Constants.IP)
				+ path, personalImage, options);
	}
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.set_group_personal_layout:
			Intent intent = new Intent();
			intent.setClass(SetOtherInfoActivity.this, SetPortraitActivity.class);
			startActivityForResult(intent, Constants.TWO);
			break;

		default:
			break;
		}
		super.onClick(v);
	}

	private void doActionRequest(HashMap<String, Object> requestMap) {
		try {
			ServerAdaptor
					.getInstance(this)
					.doAction(
							Constants.UrlHead
									+ "client.action.UserAction$updatePersonalInformation",
							requestMap, new ServiceSyncListener() {
								@Override
								public void onSuccess(
										ActionResponse returnObject) {
									super.onSuccess(returnObject);
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									Toast.makeText(SetOtherInfoActivity.this,
											"用户信息修改成功", Toast.LENGTH_SHORT)
											.show();
									// 更新本地数据库
									personBean.setEmail(editEmail.getText()
											.toString());
									personBean.setWorkPhone(editPhone.getText()
											.toString());
									personService.updatePersonInfo(personBean);
									SetOtherInfoActivity.this.finish();
								}

								@Override
								public void onError(ActionResponse returnObject) {
									super.onError(returnObject);
									if (pd != null && pd.isShowing()) {
										pd.dismiss();
									}
									Toast.makeText(SetOtherInfoActivity.this,
											(String) returnObject.getMessage(),
											Toast.LENGTH_SHORT).show();
								}
							});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 密码修改成功

		if (requestCode == Constants.TWO && resultCode == Constants.SIX) {
			String headPhotoPath = data.getStringExtra("headPhotoPath");
			// if (file.exists()) {
			imageLoader.displayImage(
					"http://" + sf1.getString("ip", Constants.IP)
							+ headPhotoPath, personalImage, options);
			sf.edit().putString("headImg", "http://" + sf1.getString("ip", Constants.IP)+ headPhotoPath).commit();
			// personalImage.setImageBitmap(AppUtils.getBitmapFromPath(path));
			// }
		}
	}
}
