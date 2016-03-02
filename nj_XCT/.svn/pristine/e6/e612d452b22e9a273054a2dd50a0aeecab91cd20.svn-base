package com.xguanjia.hx.set;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.ActionResponse;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.ServerAdaptor;
import com.xguanjia.hx.common.ServiceSyncListener;
import com.xguanjia.hx.common.activity.BaseActivity;
/**
 * 意见反馈    界面
 * @author Administrator
 *
 */
public class FeedBackActivity extends BaseActivity {
	private EditText mEditTextContent;
	private ProgressDialog pd;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View appView=LayoutInflater.from(this).inflate(R.layout.activity_feedback, null);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		mainView.addView(appView, param);
		this.setTitleText("意见反馈");
		this.setTitleRightButton("发送", 0, this);
		this.setTitleLeftButtonBack("", R.drawable.back_selector,this);
		mEditTextContent = (EditText) findViewById(R.id.etFeedback);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.title_leftBtnBack:
			FeedBackActivity.this.finish();
			break;
		case R.id.title_rightBtn:
			if("".equals(mEditTextContent.getText().toString())){
				showToast("意见不能为空");
				return;
			}
			send();
			break;
		default:
			break;
		}

	}

	/**
	 * 发送消息
	 */
	private void send() {
		String contString = mEditTextContent.getText().toString();
		doSendFeedbackAction(contString);
	}


	private void doSendFeedbackAction(String contString) {
		HashMap<String, String> getAccessPost = new HashMap<String, String>();
		getAccessPost.put("phone", Constants.mobile);
		getAccessPost.put("content", contString);
		getAccessPost.put("client", "android");
		getAccessPost.put("partyId", Constants.partyId);
		pd = ProgressDialog.show(this, "", "数据获取中", true, true);
		try {
			ServerAdaptor.getInstance(this).doAction(
					1,
					Constants.UrlHead
							+ "client.action.SalaryAction$SetOpinion",
					getAccessPost, new ServiceSyncListener() {

						public void onSuccess(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							Toast.makeText(FeedBackActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
							finish();
						}

						public void onError(ActionResponse returnObject) {
							// TODO Auto-generated method stub
							if (pd != null && pd.isShowing()) {
								pd.dismiss();
							}
							Toast.makeText(FeedBackActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
