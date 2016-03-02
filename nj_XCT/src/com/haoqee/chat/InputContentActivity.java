package com.haoqee.chat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.chinamobile.salary.R;

/**
 * 输入内容页面
 *
 */
public class InputContentActivity extends BaseActivity implements
		OnClickListener {

	private EditText mContentEdit;
	private int mType = 0;
	private final static int GROUP_NAME_MIN_LEN = 2;
	private final static int GROUP_NAME_MAX_LEN = 10;

	/** 输入群名称 */
	public final static int INPUT_GROUP_NAME = 0;
	/** 输入群简介 */
	public final static int INPUT_GROUP_DESCRIPTION = 1;
	/** 输入个性签名 */
	public final static int INPUT_USER_SIGN = 2;
	/** 输入昵称 */
	public final static int INPUT_USER_NICKNAME = 3;
	/** 输入临时会话名称 */
	public final static int INPUT_TEMP_CHAT_NAME = 4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_input_page);
		mContext = this;
		registerFinishReceiver();
		initComponent();
	}

	private void initComponent() {

		mType = getIntent().getIntExtra("type", 0);
		String title = "";

		setTitleContent(R.drawable.back_btn, R.drawable.title_complete_btn, "");
		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);

		mContentEdit = (EditText) findViewById(R.id.content);

		mContentEdit.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				// TODO Auto-generated method stub
				return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
			}
		});

		String content = getIntent().getStringExtra("content");

		if (mType == INPUT_GROUP_NAME) {
			title = mContext.getString(R.string.group_name);
			if (!TextUtils.isEmpty(content)) {
				mContentEdit.setText(content);
				mContentEdit.setSelection(content.length());
			} else {
				mContentEdit.setHint(mContext.getString(R.string.group_name));
			}
		} else if (mType == INPUT_GROUP_DESCRIPTION) {
			title = mContext.getString(R.string.group_description);
			if (!TextUtils.isEmpty(content)) {
				mContentEdit.setText(content);
				mContentEdit.setSelection(content.length());
			} else {
				mContentEdit.setHint(mContext
						.getString(R.string.group_description));
			}
		} else if (mType == INPUT_USER_SIGN) {
			title = mContext.getString(R.string.user_sign);
			if (!TextUtils.isEmpty(content)) {
				mContentEdit.setText(content);
				mContentEdit.setSelection(content.length());
			} else {
				mContentEdit.setHint(mContext.getString(R.string.user_sign));
			}
		} else if (mType == INPUT_USER_NICKNAME) {
			title = mContext.getString(R.string.nickname);
			if (!TextUtils.isEmpty(content)) {
				mContentEdit.setText(content);
				mContentEdit.setSelection(content.length());
			} else {
				mContentEdit.setHint(mContext.getString(R.string.nickname));
			}
		} else if (mType == INPUT_TEMP_CHAT_NAME) {
			title = mContext.getString(R.string.temp_chat_name);
			if (!TextUtils.isEmpty(content)) {
				mContentEdit.setText(content);
				mContentEdit.setSelection(content.length());
			} else {
				mContentEdit.setHint(mContext
						.getString(R.string.temp_chat_name));
			}
		}

		titileTextView.setText(title);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.left_btn:
			hideSoftKeyboard(getCurrentFocus());
			this.finish();
			break;

		case R.id.right_btn:
			hideSoftKeyboard(getCurrentFocus());
			String content = mContentEdit.getText().toString().trim();
			String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(content);

			if (mType == INPUT_GROUP_NAME || mType == INPUT_TEMP_CHAT_NAME) {

				if (content.equals("")) {
					String prompt = "";
					if (mType == INPUT_GROUP_NAME) {
						prompt = mContext.getString(R.string.please_input)
								+ mContext.getString(R.string.group_name);
					} else {
						prompt = mContext.getString(R.string.please_input)
								+ mContext.getString(R.string.temp_chat_name);
					}
					showToast(prompt);
					return;
				}

				if (content.length() > GROUP_NAME_MAX_LEN
						|| content.length() < GROUP_NAME_MIN_LEN) {
					if (mType == INPUT_GROUP_NAME) {
						showToast(mContext
								.getString(R.string.group_length_prompt));
					} else {
						showToast(mContext
								.getString(R.string.temp_chat_name_length_prompt));
					}

					return;
				}

				if (m.find()) {
					Toast.makeText(InputContentActivity.this, "群名称不允许输入特殊符号！",
							Toast.LENGTH_LONG).show();
					return;
				}
			} else if (mType == INPUT_GROUP_DESCRIPTION) {
				if (content.equals("")) {
					String prompt = mContext.getString(R.string.please_input)
							+ mContext.getString(R.string.group_description);
					showToast(prompt);
					return;
				}
				if (m.find()) {
					Toast.makeText(InputContentActivity.this, "群简介不允许输入特殊符号！",
							Toast.LENGTH_LONG).show();
					return;
				}
			}

			Intent intent = new Intent();
			intent.putExtra("content", content);
			intent.putExtra("type", mType);
			setResult(RESULT_OK, intent);
			InputContentActivity.this.finish();
			break;

		default:
			break;
		}
	}

	public void hideSoftKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (view != null) {
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

}
