package com.xguanjia.hx.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.jivesoftware.smackx.filetransfer.FileTransfer.Status;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;

import android.os.Environment;
import android.view.View;

import com.xguanjia.hx.login.bean.LoginBean;
import com.xguanjia.hx.payroll.bean.PayRollTypeGroupList;

public class Constants {

//
//	public static String IP = "112.4.17.113:8090/salary";// 测试服务器地址
//	public static String IP_FG = "112.4.17.113:8090/maintain";// 测试运维地址
//	public static String UPDATE_IP = "112.4.17.113:8090/appcms";// 测试升级地址

//	 public static String IP = "192.168.1.100:8080/salary";// 本机测试地址
//	 public static String IP_FG = "192.168.1.4:8080/maintain";
//	 public static String UPDATE_IP = "192.168.1.4:8080/appcms";

	 public static String IP = "112.4.17.115:8090/salary";// 生产服务器地址
	 public static String IP_FG = "112.4.17.115:8090/maintain";// 生产运维地址
	 public static String UPDATE_IP = "112.4.17.115:8090/appcms";// 生产升级地址

	// 测试
	/** OPENFIRE服务器IP */
//	public static final String OF_HOST = "112.4.17.113"; // 测试
	 public static final String OF_HOST = "112.4.17.103";// 正式
	/** OPENFIRE服务器端口号 */
	public static final String OF_PORT = "5222";
	/** OPENFIRE服务器域 */
	public static final String OF_RESOURSE_NAME = "localhost.localdomain";
	/** OPENFIRE服务器业务服务器地址 */
//	public static final String OF_LOGISTIC_SERVER = "http://112.4.17.113:1120/ims/index.php"; // 测试
	 public static final String OF_LOGISTIC_SERVER =
	 "http://112.4.17.103:1120/ims/index.php";// 正式

	// smallHead前缀
//	public static String IM_HEAD_ADDRESS = "http://112.4.17.113:1120/imssdk";
	// 测试
	 public static String IM_HEAD_ADDRESS =
	 "http://112.4.17.103:1120/imssdk";// 正式

	public static String IP_NOW = "";
	public static boolean ISUPDATE = false;
	public static String DEVICE_IMEI = "";
	public static String DEVICE_IMSI = "";
	public static String DEV_ID="";//每台手机的设备号

	public static String partyId = "en";
	// public static String partyId = "";

	public static String partyIdRel = "";
	public static int i = 61;
	public static String userId = "";
	public static String mobile = "";
	public static String depname = "";// 企业
	public static String is4G = "";
	public static boolean payRollRefresh = false;
	public static int width = 0;
	public static int height = 0;

	public static boolean isClose = false;
	public static String loginName = "admin";
	public static String userName = "";
	public static String loginOpenName = "";
	public static String openFireProjectName = "iZ23g9n6fsuZ";
	public static String fileName = "";
	public static String examId = "";
	public static String uid = "";// openfire的id
	public static LoginBean loginBean = new LoginBean();

	// 会议室用
	public static String serviceName = "yun.haoqee.com";
	// public static String serviceName = "localhost";
	public static String LOCATE_STATUS = ""; // 定位服务状态

	public static String ON_WORK_TIME = ""; // 上班时间

	public static String OFF_WORK_TIME = ""; // 下班时间

	public static long TOTAL_CONTACTS = 0;// 通讯录人员总数
	// public static String userId = "8";

	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int FOUR = 4;
	public static final int FIVE = 5;
	public static final int SIX = 6;

	public static final int G_ZERO = 0x000000;
	public static final int G_ONE = 0x000001;
	public static final int G_TWO = 0x000002;
	public static final int G_THREE = 0x000003;
	public static final int G_FOUR = 0x000004;
	public static final int G_FiVE = 0x000005;
	public static final int G_SIX = 0x000006;
	public static final int G_SEVEN = 0x000007;

	public static int LOCATION_REPORT_INTERVAL = 0; // 后台位置数据上报间隔
	public static String OFF_WORK = "";
	public static String ON_WORK = "";
	public static String locationPeriod = "";
	// 强制拍照
	public static String PHOTOGRAPH = "1";

	public static int RECODE_STATE = 0; // 录音的状态
	public static int RECORD_NO = 0; // 不在录音
	public static int RECORD_ING = 1; // 正在录音
	public static int RECODE_ED = 2; // 完成录音

	public static String openName = "";
	public static String openRelName = "";

	// openfire服务器名
	public static String openServiceUrl = "yun.haoqee.com";
	// public static String openServiceUrl = "localhost";

	// openfire服务器地址
	public static String openfireIP = "yun.haoqee.com";
	// public static String openfireIP = "42.120.20.41";

	public static final String BMAPKEY = "71219AA100277F5838738F10C759B2F3295551CE";

	public static String MENU_AUTH_CODE = "";

	public static String APP_ID = "";

	public static String AUTHORITY = "";

	public static String COMPANY_ID = "";

	public static String SERVER_VERSON = "";

	public static String PRODUCT_ID = "bd13cf8b3b89183c013bb1da026800cf";

	public static String IMEI = ""; // 设备号

	public static String IMSI = ""; // sim卡序列号

	public static String SDK_VERSION = ""; // SDK版本号

	public static String SYS_VERSION = ""; // 系统版本号

	// 服务器访问前缀
	public static String UrlHead = "com.xguanjia.";
	// 时间戳
	public static String SharelastReceivedTime = "";

	public static String userLastTime = "";
	// 未读数目
	public static int unPayrollNum = 0;// 未读工资单
	public static int unnoticNum = 0; // 未读通知公告

	// 消息推送用到
	// public static String PUSH_SERVER_IP = "192.168.2.135";
	// public static String PUSH_SERVER_IP = "42.121.110.144";

	public static final String SHARED_PREFERENCE_NAME = "client_preferences";

	// PREFERENCE KEYS

	public static final String CALLBACK_ACTIVITY_PACKAGE_NAME = "CALLBACK_ACTIVITY_PACKAGE_NAME";

	public static final String CALLBACK_ACTIVITY_CLASS_NAME = "CALLBACK_ACTIVITY_CLASS_NAME";

	public static final String API_KEY = "API_KEY";

	public static final String VERSION = "VERSION";

	public static final String XMPP_HOST = "xmpp_host";

	public static final String XMPP_PORT = "XMPP_PORT";

	public static final String XMPP_USERNAME = "XMPP_USERNAME";

	public static final String XMPP_PASSWORD = "XMPP_PASSWORD";

	// public static final String USER_KEY = "USER_KEY";

	public static final String DEVICE_ID = "DEVICE_ID";

	public static final String EMULATOR_DEVICE_ID = "EMULATOR_DEVICE_ID";

	public static final String NOTIFICATION_ICON = "NOTIFICATION_ICON";

	public static final String SETTINGS_NOTIFICATION_ENABLED = "SETTINGS_NOTIFICATION_ENABLED";

	public static final String SETTINGS_SOUND_ENABLED = "SETTINGS_SOUND_ENABLED";

	public static final String SETTINGS_VIBRATE_ENABLED = "SETTINGS_VIBRATE_ENABLED";

	public static final String SETTINGS_TOAST_ENABLED = "SETTINGS_TOAST_ENABLED";

	// NOTIFICATION FIELDS

	public static final String NOTIFICATION_ID = "NOTIFICATION_ID";

	public static final String NOTIFICATION_API_KEY = "NOTIFICATION_API_KEY";

	public static final String NOTIFICATION_TITLE = "NOTIFICATION_TITLE";

	public static final String NOTIFICATION_MESSAGE = "NOTIFICATION_MESSAGE";

	public static final String NOTIFICATION_URI = "NOTIFICATION_URI";

	public static final String PACKET_ID = "PACKET_ID";

	public static final String NOTIFICATION_FROM = "NOTIFICATION_FROM";

	// INTENT ACTIONS

	public static final String ACTION_SHOW_NOTIFICATION = "org.androidpn.client.SHOW_NOTIFICATION";

	public static final String ACTION_NOTIFICATION_CLICKED = "org.androidpn.client.NOTIFICATION_CLICKED";

	public static final String ACTION_NOTIFICATION_CLEARED = "org.androidpn.client.NOTIFICATION_CLEARED";

	public static final String SPLIT_TAG = ",";

	// 功能引导
	// 通讯录
	public static final String FUNCTION_GUIDE_CONTACT = "function_guide_contact";
	// 文件柜
	public static final String FUNCTION_GUIDE_FILECABINET = "function_guide_filecabinet";

	// 图片保存路径
	public static String SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "HaoXin/image/";

	public static String SAVE_OPEN_IMG = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "HaoXinTXL/";
	// 插件
	public static String SAVE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separatorChar + "HaoXin/";

	public static Stack<View> contactOrgazineStack = new Stack<View>();

	public static Stack<View> contactListStack = new Stack<View>();

	public static Stack<View> contactOrgazineStack1 = new Stack<View>();

	public static Stack<View> contactListStack1 = new Stack<View>();

	public static Stack<View> contactOrgazineStack2 = new Stack<View>();

	public static Stack<View> contactListStack2 = new Stack<View>();

	public static Stack<View> contactAttentionStack = new Stack<View>();

	public static Stack<View> groupPersonStack = new Stack<View>();

	public static Stack<View> groupManaStack = new Stack<View>();

	public static Stack<View> contactOrgazineTitleStack = new Stack<View>();

	public static int TIME_OUT = 3000; // 系统默认连接超时

	public static int LOGIN_TIME_OUT = 30000; // 系统登录请求超时

	public static int DOWNLOAD_TIME_OUT = 60000; // 系统下载请求超时

	public static List<FileTransferRequest> requests = new ArrayList<FileTransferRequest>();

	// 收入的类型 作为全局变量
	public static PayRollTypeGroupList payRollTypeGroups = new PayRollTypeGroupList();

	public static String password = "1";

	public static Status status;
	public static String path = "";
	public static String week = "";

	public static String updateTime = "";

}
