package com.haoqee.chat.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.haoqee.chat.UserInfoActivity;
import com.haoqee.chat.adapter.UserAdapter;
import com.haoqee.chat.entity.User;
import com.chinamobile.salary.R;

/**
 * 好友搜索对话框
 *
 */
public class SearchDialog extends Dialog implements OnItemClickListener, OnClickListener{
	
	private Context mContext;
	private EditText mContentEdit;							//关键字输入框
	private TextView mContentView;							//不需要输入时显示的文本框
	private ListView mListView;								//搜索列表显示控件
	private List<User> mUserList;							//搜索的列表对象
	private List<User> mSearchList;							//搜索的结果列表
	private UserAdapter mAdapter;							//搜索结果列表适配器
	private Button mCancelBtn;								//取消按钮

	public SearchDialog(Context context, List<User> userList) {
		super(context, R.style.ContentOverlay);
		mContext = context;
		mUserList = userList;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_search_dialog);
		initComponent();
	}

	/**
	 * 控件初始化
	 */
	private void initComponent() {
		mSearchList = new ArrayList<User>();
		
		mContentView = (TextView) findViewById(R.id.contentView);
		mContentView.setVisibility(View.GONE);
		
		mCancelBtn = (Button) findViewById(R.id.cancelbtn);
		mCancelBtn.setVisibility(View.VISIBLE);
		//注册取消按钮的点击事件
		mCancelBtn.setOnClickListener(this);
		
		mContentEdit = (EditText) findViewById(R.id.keyword);
		mContentEdit.setVisibility(View.VISIBLE);
		
		//注册EditText的文本变化监听事件，用户文本改变后，重新搜索结果
		mContentEdit.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				List<User> tempList = new ArrayList<User>();
				if (s.toString() != null && !s.toString().equals("")) {
					
					for (int i = 0; i < mUserList.size(); i++) {
						if (mUserList.get(i).nickName.contains(s.toString())) {
							tempList.add(mUserList.get(i));
						}
					}

				}
				
				if (mSearchList != null) {
					mSearchList.clear();
				}
				
				mSearchList.addAll(tempList);
				updateListView();
			}
		});

		//设置对话框对应的软键盘可见 
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		mListView = (ListView) findViewById(R.id.contact_list);
		mListView.setVisibility(View.GONE);
		mListView.setCacheColorHint(0);
		mListView.setOnItemClickListener(this);
		mListView.setSelector(mContext.getResources().getDrawable(R.drawable.transparent_selector));
	}
	
	@Override
	public void setOnShowListener(OnShowListener listener) {
		super.setOnShowListener(listener);
		showSoftKeyboard();
	}

	public void hideKeyboard() {
		InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (inputManager != null){
			inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	public void showSoftKeyboard(){
		InputMethodManager imm = (InputMethodManager) mContentEdit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
	}
	
	private void updateListView() {
		
		if(mSearchList != null && mSearchList.size() != 0){
			mListView.setVisibility(View.VISIBLE);
		}
		
		if(mAdapter != null){
			mAdapter.notifyDataSetChanged();
			return;
		}

		if (mSearchList != null) {
			mAdapter = new UserAdapter(mContext, mSearchList);
			mAdapter.setIsShowCheckBox(false);
			mListView.setAdapter(mAdapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		hideKeyboard();
		Intent intent = new Intent(mContext, UserInfoActivity.class);
		intent.putExtra("fuid", mSearchList.get(arg2).uid);
		mContext.startActivity(intent);
		SearchDialog.this.dismiss();
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			//当软键盘弹出时，点击键盘外部区域隐藏软键盘
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				InputMethodManager manager = (InputMethodManager) mContext
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}

		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cancelbtn:
			hideKeyboard();
			this.dismiss();
			break;

		default:
			break;
		}
	}
	
}
