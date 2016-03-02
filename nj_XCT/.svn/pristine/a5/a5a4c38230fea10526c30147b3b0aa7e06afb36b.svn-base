package com.xguanjia.hx.feedback;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.HaoqeeException;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;

public class SuggestionFeedbackActivity extends BaseActivity {
	private static final String TAG = "SuggestionFeedbackActivity";
	private EditText suggestionEdit, emailEdit, otherContactsEdit,suggestion_titles;
	private ProgressDialog pd;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View mainView = getLayoutInflater().inflate(R.layout.feedback, null);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		this.mainView.addView(mainView, params);
		suggestionEdit = (EditText) this.findViewById(R.id.suggestion_edit);
//		emailEdit = (EditText) this.findViewById(R.id.email_edit);
//		otherContactsEdit = (EditText) this.findViewById(R.id.other_contacts_edit);
//		suggestion_titles =(EditText)this.findViewById(R.id.suggestion_titles);
		setTitleText("意见反馈");
		this.setTitleLeftButtonBack("",R.drawable.back_selector, new BackClickListener(this));
		this.setTitleRightButton("提交",R.drawable.title_rightbutton_selector, new RightButtonClickListener());
		
	}

	/**
	 * 返回按钮监听器
	 * 
	 * @author ytg
	 */
	class BackClickListener implements View.OnClickListener {
		private Activity context;

		public BackClickListener(Activity context) {
			super();
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			context.finish();
		}
	}

	/**
	 * 提交按钮
	 * 
	 * @author ytg
	 */
	class RightButtonClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			String suggestionEditStr = suggestionEdit.getText().toString().trim();
//			String emailEditStr = emailEdit.getText().toString().trim();
//			String otherContactsEditStr = otherContactsEdit.getText().toString().trim();
//			String suggestiontitle = suggestion_titles.getText().toString().trim();
//			if("".equals(suggestiontitle))
//			{
//			    suggestion_titles.setError("请输入标题");
//			}
//			if ("".equals(suggestionEditStr)) {
//			    suggestionEdit.setError("请填写反馈意见");
////				Toast.makeText(SuggestionFeedbackActivity.this, "请填写反馈意见", Toast.LENGTH_SHORT).show();
//			} else if ("".equals(emailEditStr)) {
//			    emailEdit.setError("请填写email");
////				Toast.makeText(SuggestionFeedbackActivity.this, "请填写email", Toast.LENGTH_SHORT).show();
//			} else if ("".equals(otherContactsEditStr)) {
//			    otherContactsEdit.setError("请填写联系电话");
////				Toast.makeText(SuggestionFeedbackActivity.this, "请填写联系电话", Toast.LENGTH_SHORT).show();
//			}
//			else {
				String actionName = "problemBack";
				HashMap<String, String> parameters = new HashMap<String, String>();
//				title	String	问题反馈标题
//				content	String	问题反馈内容
//				companyId	String	企业的id
//				mobileType	String	手机型号
//				systemType	String	手机系统版本
//				clientVersion	String	客户端版本
//				email	String	邮箱
//				mobile	String	电话
//				productId	String	产品编号
				parameters.put("title", "");
				parameters.put("content", suggestionEditStr);
				parameters.put("email", "");
				parameters.put("mobile", "");
				parameters.put("companyId", "companyId");
				parameters.put("mobileType", Build.MODEL);
				parameters.put("systemType", Build.VERSION.SDK);
				parameters.put("clientVersion", getVersion());
				pd = ProgressDialog.show(SuggestionFeedbackActivity.this, "", "数据上传中", false);
				try {
					HttpAdapter.getInstance(SuggestionFeedbackActivity.this).doAction(1, actionName, parameters, new SyncHttpListener());
				} catch (HaoqeeException e) {
					Log.e(TAG, e.getMessage(), e);
//				}
			}
		}
	}

	class SyncHttpListener extends ServiceSyncListener {
		// 获取数据成功
		public void onSuccess(ActionResponse returnObject) {
			if (pd != null && pd.isShowing()) {
				pd.dismiss();
			}
			Log.i(TAG, "onSuccess");
			Toast.makeText(SuggestionFeedbackActivity.this, "谢谢你的宝贵意见!", Toast.LENGTH_LONG).show();
			SuggestionFeedbackActivity.this.finish();
		}

		// 获取数据失败
		public void onError(ActionResponse returnObject) {
			if (pd != null && pd.isShowing()) {
				pd.dismiss();
			}
			Log.i(TAG, "onError");
			Toast.makeText(SuggestionFeedbackActivity.this, "数据提交失败!", Toast.LENGTH_LONG).show();
		}
	}

	public String getVersion() {
		String appVersion = "";
		PackageManager manager = this.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			appVersion = info.versionName; // 版本名
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return appVersion;
	}
}
