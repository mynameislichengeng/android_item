package com.haoqee.chat.net;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;
import android.util.Log;

import com.haoqee.chat.entity.AppState;
import com.haoqee.chat.entity.LoginResult;
import com.haoqee.chat.entity.ModifyPwd;
import com.haoqee.chat.entity.MorePicture;
import com.haoqee.chat.entity.UserList;
import com.haoqee.chat.listener.BaseListener;
import com.xguanjia.hx.common.Constants;

public class ThinkchatInfo implements Serializable {
	private static final long serialVersionUID = 1651654562644564L;

	public static String SERVER = Constants.OF_LOGISTIC_SERVER;
	public static final int LOAD_SIZE = 20;

	public ThinkchatInfo() {
		// Server server = Common.getCurrentServer(ThinkchatApp.getInstance());
		// if (server != null && !TextUtils.isEmpty(server.mLogisticServer)) {
		// SERVER = server.mLogisticServer;
		// }
		// SERVER = SERVER;
	}

	// public void setServer() {
	// Server server = Common.getCurrentServer(ThinkchatApp.getInstance());
	// if (server != null && !TextUtils.isEmpty(server.mLogisticServer)) {
	// SERVER = server.mLogisticServer;
	// }
	// }

	/**
	 * 网络访问入口函数
	 * 
	 * @param url
	 *            请求的url
	 * @param params
	 *            请求的参数数组
	 * @param httpMethod
	 *            请求的方式
	 * @param loginType
	 *            是否登录 0--不登陆 1--需要登录 2--两种都可以
	 * @param listener
	 *            错误回调监听
	 * @return
	 * @throws BridgeException
	 */
	public String request(String url, Parameters params, String httpMethod,
			int loginType, BaseListener listener) throws ThinkchatException {
		String rlt = null;
		rlt = Utility.openUrl(url, httpMethod, params, loginType, listener);

		return rlt;

	}

	/**
	 * 登录接口
	 * 
	 * @param username
	 *            必传 手机号
	 * @param password
	 *            必传 密码
	 * @return LoginResult
	 * @throws ThinkchatException
	 *             处理网络超时异常
	 */
	public LoginResult getLogin(String mobile, String password, String partId)
			throws ThinkchatException {
		Parameters bundle = new Parameters();

		bundle.add("username", mobile);
		bundle.add("password", password);
		bundle.add("partId", partId);

		String url = SERVER + "/user/api/login";

		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 0, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new LoginResult(reString);
		}

		return null;
	}

	/**
	 * 登录接口
	 * 
	 * @param username
	 *            必传 手机号
	 * @param password
	 *            必传 密码
	 * @return LoginResult
	 * @throws ThinkchatException
	 *             处理网络超时异常
	 */
	public LoginResult getLogin1(String mobile, String password)
			throws ThinkchatException {
		Parameters bundle = new Parameters();

		bundle.add("username", mobile);
		bundle.add("password", password);

		String url = SERVER + "/user/api/login";

		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 0, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new LoginResult(reString);
		}

		return null;
	}

	/**
	 * 注册接口
	 * 
	 * @param mobile
	 *            必传 注册手机号
	 * @param nickName
	 *            必传 注册昵称
	 * @param password
	 *            必传 注册密码
	 * @return LoginResult
	 * @throws ThinkchatException
	 *             处理网络超时异常
	 */
	public LoginResult register(String mobile, String nickName, String password)
			throws ThinkchatException {
		Parameters bundle = new Parameters();

		bundle.add("phone", mobile);
		bundle.add("nickname", nickName);
		bundle.add("password", password);

		String url = SERVER + "/user/api/regist";

		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 0, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new LoginResult(reString);
		}

		return null;
	}

	/**
	 * 获取用户详细资料接口
	 * 
	 * @param fuid
	 *            不必传 不传入时获取当前登录用户资料，传入则获取指定用户的资料
	 * @return LoginResult
	 * @throws ThinkchatException
	 *             处理网络超时异常
	 */
	public LoginResult getUserDetail(String fuid) throws ThinkchatException {
		Parameters bundle = new Parameters();

		if (!TextUtils.isEmpty(fuid)) {
			bundle.add("fuid", fuid);
		}

		String url = SERVER + "/user/api/detail";

		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new LoginResult(reString);
		}

		return null;
	}

	/**
	 * 申请加好友接口
	 * 
	 * @param fuid
	 *            被加好友的ID
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState applyFriend(String fuid) throws ThinkchatException {
		Parameters bundle = new Parameters();

		bundle.add("fuid", fuid);

		String url = SERVER + "/user/api/applyAddFriend";

		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 同意加好友
	 * 
	 * @param fuid
	 *            被加好友用户ID
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState agreeFriend(String fuid) throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("fuid", fuid);

		String url = SERVER + "/user/api/agreeAddFriend";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 拒绝好友申请
	 * 
	 * @param fuid
	 *            被拒绝用户ID
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState refuseFriend(String fuid) throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("fuid", fuid);

		String url = SERVER + "/user/api/refuseAddFriend";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			Log.e("reString", reString);
			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 删除好有接口
	 * 
	 * @param fuid
	 *            待删除的用户ID 必传
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState deleteFriend(String fuid) throws ThinkchatException {
		Parameters bundle = new Parameters();

		bundle.add("fuid", fuid);
		String url = SERVER + "/user/api/deleteFriend";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null") && reString.startsWith("{")) {
			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 修改密码接口
	 * 
	 * @param oldpassword
	 *            旧密码
	 * @param password
	 *            新密码
	 * @return
	 * @throws ThinkchatException
	 */
	public ModifyPwd modifyPassword(String oldpassword, String password)
			throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("oldpassword", oldpassword);
		bundle.add("newpassword", password);
		String url = SERVER + "/user/api/editPassword";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new ModifyPwd(reString);
		}

		return null;
	}

	/**
	 * 意见反馈接口
	 * 
	 * @param content
	 *            反馈内容 必填
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState feedback(String content) throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("content", content);
		String url = SERVER + "/user/api/feedback";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null") && reString.startsWith("{")) {

			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 搜索用户接口
	 * 
	 * @param userName
	 *            手机号或昵称 必传
	 * @param page
	 *            页数 默认第一页
	 * @return
	 * @throws ThinkchatException
	 */
	public UserList searchUser(String userName, int page)
			throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("search", userName);

		bundle.add("page", page + "");
		bundle.add("pageSize", String.valueOf(LOAD_SIZE));

		String url = SERVER + "/user/api/search";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			return new UserList(reString);
		}

		return null;
	}

	/**
	 * 编辑个人资料接口
	 * 
	 * @param userFace
	 *            头像文件路径 非必传
	 * @param nickname
	 *            昵称 非必传
	 * @param userSign
	 *            个性签名 非必传
	 * @param gender
	 *            性别 非必传
	 * @return
	 * @throws ThinkchatException
	 */
	public LoginResult editProfile(String userFace, String nickname,
			String userSign, String gender) throws ThinkchatException {
		Parameters bundle = new Parameters();
		if (!TextUtils.isEmpty(userFace)) {
			List<MorePicture> fileList = new ArrayList<MorePicture>();
			fileList.add(new MorePicture("picture", userFace));
			bundle.addPicture("fileList", fileList);
		}

		if (!TextUtils.isEmpty(nickname)) {
			bundle.add("nickname", nickname);
		}

		if (!TextUtils.isEmpty(userSign)) {
			bundle.add("sign", userSign);
		}

		if (!TextUtils.isEmpty(gender)) {
			bundle.add("gender", gender);
		}

		String url = SERVER + "/user/api/edit";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null") && reString.startsWith("{")) {

			return new LoginResult(reString);
		}

		return null;
	}

	/**
	 * 获取好友列表接口
	 * 
	 * @return
	 * @throws ThinkchatException
	 */
	public UserList getFriendList() throws ThinkchatException {
		Parameters bundle = new Parameters();
		String url = SERVER + "/user/api/friendList";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null") && reString.startsWith("{")) {
			return new UserList(reString);
		}

		return null;
	}

	/**
	 * 举报用户接口
	 * 
	 * @param fuid
	 *            被举报用户ID
	 * @param content
	 *            举报内容
	 * @return
	 * @throws ThinkchatException
	 */
	public AppState reportUser(String fuid, String content)
			throws ThinkchatException {
		Parameters bundle = new Parameters();
		bundle.add("fuid", fuid);
		bundle.add("content", content);

		String url = SERVER + "/user/api/jubao";
		String reString = request(url, bundle, Utility.HTTPMETHOD_POST, 1, null);
		if (reString != null && !reString.equals("")
				&& !reString.equals("null")) {
			try {
				JSONObject json = new JSONObject(reString);
				if (!json.isNull("state")) {
					return new AppState(json.getJSONObject("state"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
