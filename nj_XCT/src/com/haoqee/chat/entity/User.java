package com.haoqee.chat.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.xguanjia.hx.common.Constants;

/**
 * 用户解析类
 *
 */

public class User implements Serializable {

	private static final long serialVersionUID = -1945455564L;

	/**
	 * "uid": "200001", "phone": "15888888888", "username": "", "realname": "",
	 * "nickname": "平常心", "headsmall": "", "email": "", "createtime":
	 * "1406799151" "gender":"0"
	 */

	public String uid; // 用户ID
	public String nickName; // 昵称
	public String password; // 登录openfire密码
	public String phone; // 电话号码
	public String mSmallHead; // 小头像
	public String mMiddleHead; // 中头像
	public String mToken = ""; // 验证接口Token
	public long mExpiredTime = 0; // Token过期时间
	public String sign = ""; // 个性签名
	public long mCreateTime = 0;
	public int mGender = 2; // 性别 2--未知 1--女 0--男
	public int mIsFriend = 0; // 当请求TA人资料时存在，0--非好友，1--好友
	public String mSort = ""; // 拼音首字母
	public boolean isShow = false; // 临时所用值，用来标示临时会话中添加联系人时联系人是否选中

	public User() {
	}

	public User(JSONObject json) {
		try {
			if (!json.isNull("uid")) {
				uid = json.getString("uid");
			}

			if (!json.isNull("password")) {
				password = json.getString("password");
			}

			if (!json.isNull("nickname")) {
				nickName = json.getString("nickname");
			}

			if (!json.isNull("phone")) {
				phone = json.getString("phone");
			}

			if (!json.isNull("headsmall")) {
				mSmallHead = Constants.IM_HEAD_ADDRESS
						+ json.getString("headsmall");
			}

			if (!json.isNull("headlarge")) {
				mMiddleHead = Constants.IM_HEAD_ADDRESS
						+ json.getString("headlarge");
			}

			if (!json.isNull("token")) {
				mToken = json.getString("token");
			}

			if (!json.isNull("tokenExpiredTime")) {
				mExpiredTime = json.getLong("tokenExpiredTime");
			}

			if (!json.isNull("sign")) {
				sign = json.getString("sign");
			}

			if (!json.isNull("addtime")) {
				mCreateTime = json.getLong("addtime");
			}

			if (!json.isNull("gender")) {
				mGender = json.getInt("gender");
			}

			if (!json.isNull("isfriend")) {
				mIsFriend = json.getInt("isfriend");
			}

			if (!json.isNull("sort")) {
				mSort = json.getString("sort");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
