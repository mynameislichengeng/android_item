package com.haoqee.chat;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.haoqee.chat.entity.Server;
import com.haoqee.chat.global.Common;
import com.haoqee.chat.global.FeatureFunction;
import com.chinamobile.salary.R;

public class AddServerActivity extends BaseActivity implements OnClickListener{
	
	private EditText mServerNameEditText;				//服务器别名
	private EditText mServerAddressEditText;			//服务器域名或IP
	private EditText mLogisticServerEditText;			//业务服务器
	private EditText mSdkServerEditText;				//SDK服务器
	private EditText mIMServerEditText;					//IM服务器
	private Button mAddBtn;								//添加按钮
	
	private static final String SERVER_PRE = "http://";
	private static final String LOGISTIC_SERVER_END = "/tc_demo/index.php";
	private static final String SDK_SERVER_END = "/tc_sdk/index.php";
	private static final String IM_SERVER_END = ":5222";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_server_layout);
		
		initComponent();
	}

	private void initComponent(){
		setTitleContent(R.drawable.back_btn, 0, mContext.getString(R.string.add_server_title));
		findViewById(R.id.leftlayout).setOnClickListener(this);
		
		mServerNameEditText = (EditText) findViewById(R.id.servername);
		mServerAddressEditText = (EditText) findViewById(R.id.server_address);
		
		mServerAddressEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				String address = s.toString();
				
				if(TextUtils.isEmpty(address)){
					mLogisticServerEditText.setText("");
					mSdkServerEditText.setText("");
					mIMServerEditText.setText("");
				}else {
					mLogisticServerEditText.setText(SERVER_PRE + address + LOGISTIC_SERVER_END);
					mSdkServerEditText.setText(SERVER_PRE + address + SDK_SERVER_END);
					mIMServerEditText.setText(SERVER_PRE + address + IM_SERVER_END);
				}
			}
		});
		
		mLogisticServerEditText = (EditText) findViewById(R.id.logistic_server);
		mSdkServerEditText = (EditText) findViewById(R.id.sdk_server);
		mIMServerEditText = (EditText) findViewById(R.id.im_server);
		
		mAddBtn = (Button) findViewById(R.id.addbtn);
		mAddBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.leftlayout:
			hideSoftKeyboard();
			this.finish();
			break;
			
		case R.id.addbtn:
			
			String serverName = mServerNameEditText.getText().toString().trim();
			
			if(TextUtils.isEmpty(serverName)){
				String prompt = mContext.getString(R.string.please_input) + mContext.getString(R.string.server_name);
				showToast(prompt);
				return;
			}
			
			String logisticServer = mLogisticServerEditText.getText().toString().trim();
			String sdkServer = mSdkServerEditText.getText().toString().trim();
			String imServer = mIMServerEditText.getText().toString().trim();
			
			if(TextUtils.isEmpty(logisticServer)){
				String prompt = mContext.getString(R.string.please_input) + mContext.getString(R.string.logistic_server);
				showToast(prompt);
				return;
			}
			
			if(!FeatureFunction.isURL(logisticServer)){
				String prompt = mContext.getString(R.string.please_input_correct_logistic_server);
				showToast(prompt);
				
				return;
			}
			
			if(TextUtils.isEmpty(sdkServer)){
				String prompt = mContext.getString(R.string.please_input) + mContext.getString(R.string.sdk_server);
				showToast(prompt);
				return;
			}
			
			if(!FeatureFunction.isURL(sdkServer)){
				String prompt = mContext.getString(R.string.please_input_correct_sdk_server);
				showToast(prompt);
				
				return;
			}
			
			if(TextUtils.isEmpty(imServer)){
				String prompt = mContext.getString(R.string.please_input) + mContext.getString(R.string.im_server);
				showToast(prompt);
				return;
			}
			
			if(!FeatureFunction.isURL(imServer)){
				String prompt = mContext.getString(R.string.please_input_correct_im_server);
				showToast(prompt);
				
				return;
			}
			
			List<Server> serverList = Common.getServerList(mContext);
			
			if(serverList == null){
				serverList = new ArrayList<Server>();
			}
			
			Server server = new Server(serverName, sdkServer, logisticServer, imServer.substring("http://".length(), imServer.length()));
			serverList.add(server);
			
			Common.saveServerList(mContext, serverList);
			
			mContext.sendBroadcast(new Intent(ServerActivity.ACTION_REFRESH_SERVER_LIST));
			
			AddServerActivity.this.finish();
			
			break;

		default:
			break;
		}
	}
}
