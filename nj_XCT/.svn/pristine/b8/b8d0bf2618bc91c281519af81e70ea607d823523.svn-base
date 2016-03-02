package com.xguanjia.hx.login.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.HaoXinProjectActivity;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.HaoqeeProgressDialog;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;

/**
 * 重新设置密码
 * 
 * @author dolphin
 * 
 */
public class setPasswordActivity extends BaseActivity {
	private static final String TAG = "SetModifyPwdActivity";
	private EditText newPwdEdit, newPwdAgainEdit;
	private TextView myAccountTv;
	private View view;
	private String newPwd;
	private Button postBtn;
	private RelativeLayout newpasswordRl, newagainpasswordRl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "in onCreate method");
		initViews();
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

	private void initViews() {
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		setTitleText("密码设置");
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		setTitleRightButton("跳过", 0, buttonClickListener);
		view = getLayoutInflater().inflate(R.layout.set_password, null);
		mainView.addView(view, param);
		postBtn = (Button) findViewById(R.id.postBtn);
		postBtn.setOnClickListener(buttonClickListener);
		myAccountTv = (TextView) findViewById(R.id.myAccountTv);
		myAccountTv.setText("手机号码    " + Constants.loginBean.getMobile());
		newPwdEdit = (EditText) this.findViewById(R.id.set_newPwd_edit);
		newPwdAgainEdit = (EditText) this
				.findViewById(R.id.set_newPwdAgain_edit);
		newPwdEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				16) });
		newPwdAgainEdit
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
		newpasswordRl = (RelativeLayout) this.findViewById(R.id.newpasswordRl);
		newpasswordRl.setOnClickListener(buttonClickListener);
		newagainpasswordRl = (RelativeLayout) this
				.findViewById(R.id.newagainpasswordRl);
		newagainpasswordRl.setOnClickListener(buttonClickListener);
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.title_rightBtn:
				if ("0".equals(Constants.loginBean.getIsModifyPwd())) {
					Intent intent = new Intent();
					intent.setClass(setPasswordActivity.this,
							HaoXinProjectActivity.class);
					startActivity(intent);
				} else {
					showToast("首次登录，必须设置密码");
				}
				break;
			// 返回
			case R.id.title_leftBtnBack:
				setPasswordActivity.this.finish();
				break;
			// 显示新密码
			case R.id.newpasswordRl:
				if (!"".equals(newPwdEdit.getText().toString())) {
					if (newPwdEdit.getTransformationMethod() == HideReturnsTransformationMethod
							.getInstance()) {
						newPwdEdit
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					} else if (newPwdEdit.getTransformationMethod() == PasswordTransformationMethod
							.getInstance()) {
						newPwdEdit
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					}
					newPwdEdit.setSelection(newPwdEdit.getText().toString()
							.length());
				}
				break;
			// 显示再次新密码
			case R.id.newagainpasswordRl:
				if (!"".equals(newPwdAgainEdit.getText().toString())) {
					if (newPwdAgainEdit.getTransformationMethod() == HideReturnsTransformationMethod
							.getInstance()) {
						newPwdAgainEdit
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					} else if (newPwdAgainEdit.getTransformationMethod() == PasswordTransformationMethod
							.getInstance()) {
						newPwdAgainEdit
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					}
					newPwdAgainEdit.setSelection(newPwdAgainEdit.getText()
							.toString().length());
				}
				break;
			// 调用发送接口
			case R.id.postBtn:
				newPwd = newPwdEdit.getText().toString();
				String newPwdAgain = newPwdAgainEdit.getText().toString();
				if (newPwd == null || newPwd.equals("")) {
					// newPwdEdit.setError("新密码不能为空");
					Toast.makeText(setPasswordActivity.this, "新密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (newPwdAgain == null || newPwdAgain.equals("")) {
					// newPwdAgainEdit.setError("重复密码不能为空");
					Toast.makeText(setPasswordActivity.this, "重复密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!newPwdAgain.equals(newPwd)) {
					// newPwdAgainEdit.setError("重复密码与新密码不一致");
					Toast.makeText(setPasswordActivity.this, "重复密码与新密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!matchPassWord(newPwd)) {
					// Toast.makeText(setPasswordActivity.this,
					// "不符合密码规则,由6-20位字母、数字、下划线组成", Toast.LENGTH_SHORT)
					// .show();
					Toast.makeText(
							setPasswordActivity.this,
							setPasswordActivity.this.getResources().getString(
									R.string.mmts_gs), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				HashMap<String, Object> requestMap = new HashMap<String, Object>();
				requestMap.put("newpwd", newPwd);
				requestMap.put("isInitpwd", "0");
				requestMap.put("loginName", sp.getString("loginName", ""));
				HaoqeeProgressDialog.show(setPasswordActivity.this, "",
						"重置密码中。。。", false, null);
				doActionRequest(requestMap);
				break;
			default:
				break;
			}
		};
	};

	private void doActionRequest(HashMap<String, Object> requestMap) {
		try {
			ServerAdaptor.getInstance(this).doAction(
					Constants.UrlHead + "client.action.UserAction$modifyPwd",
					requestMap, new ServiceSyncListener() {
						@Override
						public void onSuccess(ActionResponse returnObject) {
							super.onSuccess(returnObject);
							HaoqeeProgressDialog.dismiss();
							Intent intent = new Intent();
							intent.setClass(setPasswordActivity.this,
									HaoXinProjectActivity.class);
							startActivity(intent);
							finish();
						}

						@Override
						public void onError(ActionResponse returnObject) {
							super.onError(returnObject);
							HaoqeeProgressDialog.dismiss();
							Toast.makeText(setPasswordActivity.this,
									(String) returnObject.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					});
		} catch (HaoqeeException e) {
			e.printStackTrace();
		}
	}

	private boolean matchPassWord(String passWord) {
		String match = "^[a-zA-Z0-9_]{6,20}$";
		if (passWord.matches(match)) {
			return true;
		} else {
			return false;
		}
	}

}
