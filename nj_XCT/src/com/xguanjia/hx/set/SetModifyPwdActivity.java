package com.xguanjia.hx.set;

import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import cmcc.ueprob.agent.UEProbAgent;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.HaoqeeProgressDialog;
import com.xguanjia.hx.common.MD5;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;

/**
 * 密码修改界面
 * 
 * @author dolphin
 * 
 */
public class SetModifyPwdActivity extends BaseActivity {
	private static final String TAG = "SetModifyPwdActivity";
	private EditText oldPwdEdit, newPwdEdit, newPwdAgainEdit, accountEdit;
	private View view;
	private MD5 md5;
	private SharedPreferences sf;
	private SharedPreferences sp;
	private Editor editor;
	private Editor editorSp;
	private String newPwd;
	private Button postBtn;
	private RelativeLayout oldpasswordRl, newpasswordRl, newagainpasswordRl;

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
		LayoutParams param = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		sp = getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		editorSp = sp.edit();
		sf = SetModifyPwdActivity.this.getSharedPreferences("basic_info",
				Context.MODE_PRIVATE);
		editor = sf.edit();
		setTitleText("密码管理");
		setTitleLeftButtonBack("", R.drawable.back_selector,
				buttonClickListener);
		// setTitleRightButton("提交", R.drawable.title_rightbutton_selector,
		// buttonClickListener);
		view = getLayoutInflater().inflate(R.layout.set_modify_password, null);
		mainView.addView(view, param);
		postBtn = (Button) findViewById(R.id.postBtn);
		accountEdit = (EditText) findViewById(R.id.set_account_edit);
		oldPwdEdit = (EditText) this.findViewById(R.id.set_oldPwd_edit);
		newPwdEdit = (EditText) this.findViewById(R.id.set_newPwd_edit);
		newPwdAgainEdit = (EditText) this
				.findViewById(R.id.set_newPwdAgain_edit);
		accountEdit.setText(sp.getString("loginName", ""));
		postBtn.setOnClickListener(buttonClickListener);
		newPwdEdit.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
				16) });
		newPwdAgainEdit
				.setFilters(new InputFilter[] { new InputFilter.LengthFilter(16) });
		md5 = new MD5();
		oldpasswordRl = (RelativeLayout) this.findViewById(R.id.oldpasswordRl);
		oldpasswordRl.setOnClickListener(buttonClickListener);
		newpasswordRl = (RelativeLayout) this.findViewById(R.id.newpasswordRl);
		newpasswordRl.setOnClickListener(buttonClickListener);
		newagainpasswordRl = (RelativeLayout) this
				.findViewById(R.id.newagainpasswordRl);
		newagainpasswordRl.setOnClickListener(buttonClickListener);
	}

	MOAOnClickListener buttonClickListener = new MOAOnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			// 返回
			case R.id.title_leftBtnBack:
				SetModifyPwdActivity.this.finish();
				break;
			// 显示旧密码
			case R.id.oldpasswordRl:
				if (!"".equals(oldPwdEdit.getText().toString())) {
					if (oldPwdEdit.getTransformationMethod() == HideReturnsTransformationMethod
							.getInstance()) {
						oldPwdEdit
								.setTransformationMethod(PasswordTransformationMethod
										.getInstance());
					} else if (oldPwdEdit.getTransformationMethod() == PasswordTransformationMethod
							.getInstance()) {
						oldPwdEdit
								.setTransformationMethod(HideReturnsTransformationMethod
										.getInstance());
					}
					oldPwdEdit.setSelection(oldPwdEdit.getText().toString()
							.length());
				}
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
				String oldPwd = oldPwdEdit.getText().toString();
				newPwd = newPwdEdit.getText().toString();
				String newPwdAgain = newPwdAgainEdit.getText().toString();
				if (oldPwd == null || oldPwd.equals("")) {
					// oldPwdEdit.setError("原密码不能为空");
					Toast.makeText(SetModifyPwdActivity.this, "原密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (newPwd == null || newPwd.equals("")) {
					// newPwdEdit.setError("新密码不能为空");
					Toast.makeText(SetModifyPwdActivity.this, "新密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (newPwdAgain == null || newPwdAgain.equals("")) {
					// newPwdAgainEdit.setError("重复密码不能为空");
					Toast.makeText(SetModifyPwdActivity.this, "重复密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				}
				if (!newPwdAgain.equals(newPwd)) {
					// newPwdAgainEdit.setError("重复密码与新密码不一致");
					Toast.makeText(SetModifyPwdActivity.this, "重复密码与新密码不一致",
							Toast.LENGTH_SHORT).show();
					return;
				}

				if (!matchPassWord(newPwd)) {
					Toast.makeText(
							SetModifyPwdActivity.this,
							SetModifyPwdActivity.this.getResources().getString(
									R.string.mmts_gs), Toast.LENGTH_SHORT)
							.show();
					return;
				}
				HashMap<String, Object> requestMap = new HashMap<String, Object>();
				requestMap.put("oldpwd", oldPwd);
				requestMap.put("newpwd", newPwd);
				requestMap.put("loginName", sp.getString("loginName", ""));
				HaoqeeProgressDialog.show(SetModifyPwdActivity.this, "",
						"密码修改，请稍后", false, null);
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
							editor.putString("password", ""); // 重新记录密码
							editor.commit();

							editorSp.putString("password", "");
							editorSp.putBoolean("isNeedVoluntaryLogin", false);
							editorSp.commit();
							Toast.makeText(SetModifyPwdActivity.this, "密码修改成功",
									Toast.LENGTH_SHORT).show();
							setResult(Constants.TWO);
							SetModifyPwdActivity.this.finish();
						}

						@Override
						public void onError(ActionResponse returnObject) {
							super.onError(returnObject);
							HaoqeeProgressDialog.dismiss();
							Toast.makeText(SetModifyPwdActivity.this,
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
