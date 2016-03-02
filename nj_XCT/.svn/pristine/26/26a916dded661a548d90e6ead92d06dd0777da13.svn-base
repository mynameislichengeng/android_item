package com.xguanjia.hx.setting.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.chinamobile.salary.R;
import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.MOAOnClickListener;
import com.xguanjia.hx.common.activity.BaseActivity;

public class TeSettingActivity extends BaseActivity {
	EditText ipEt, ywIp, mhIp;
	private Editor editor;
	private SharedPreferences sf;
	private EditText memberEdit;

	// 0代表外网ip,1代表内网ip

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sf = this.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
		View set_titleView = getLayoutInflater().inflate(
				R.layout.activity_setting_moa, null);
		this.mainView.addView(set_titleView);
		this.setTitleText(resource.getString(R.string.tosettingset));
		updateButton();
		ipEt = (EditText) set_titleView.findViewById(R.id.tosetip);
		ywIp = (EditText) set_titleView.findViewById(R.id.tosetywip);
		mhIp = (EditText) set_titleView.findViewById(R.id.tosetmhip);
		ipEt.setText(sf.getString("ip", Constants.IP));
		ywIp.setText(sf.getString("ywIp", Constants.IP_FG));
		mhIp.setText(sf.getString("mhIp", Constants.UPDATE_IP));
		ipEt.setSelection(ipEt.length());
		memberEdit = (EditText) this.findViewById(R.id.member_edit);
		memberEdit.setText(sp.getString("partyId", Constants.partyId));

		// setTitleRightButton("默认", R.drawable.title_rightbutton_selector,
		// new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// ipEt.setText(Constants.defaultIP);
		// }
		// });

	}

	public void updateButton() {
		// 返回按钮
		this.setTitleLeftButtonBack("", R.drawable.back_selector,
				new MOAOnClickListener() {

					@Override
					public void onClick(View v) {
						String ipet = ipEt.getText().toString().trim();
						if (isChinese(ipet)) {
							Toast.makeText(TeSettingActivity.this,
									"ip地址不能包含中文字符", 1).show();
							return;
						}
						Constants.IP = ipEt.getText().toString().trim();
						if ("".equals(Constants.IP)
								|| "".equals(ywIp.getText().toString())
										|| "".equals(mhIp.getText().toString())) {
							Toast.makeText(TeSettingActivity.this, "ip地址不能为空",
									Toast.LENGTH_SHORT).show();;
							return;
						}
						editor = sf.edit();
						editor.putString("ip", Constants.IP);
						editor.putString("ywIp", ywIp.getText().toString());
						editor.putString("mhIp", mhIp.getText().toString());
						editor.commit();
						Constants.partyId = memberEdit.getText().toString();
						Editor editor = sp.edit();
						editor.putString("partyId", memberEdit.getText()
								.toString());
						editor.commit();
						TeSettingActivity.this.finish();
					}

				});

	}

	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	// 根据Unicode编码完美的判断中文汉字和符号

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

}
