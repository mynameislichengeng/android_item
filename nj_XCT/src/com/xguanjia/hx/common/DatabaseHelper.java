package com.xguanjia.hx.common;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = "DatabaseHelper";

	private static final int DATABASEVERSION = 8;

	private static DatabaseHelper databaseHelper = null;
	private static SharedPreferences sp;
	private static SharedPreferences sf;
	private static String serverUrl = "";

	public static DatabaseHelper getInstance(Context context) {
		sp = context.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
		Constants.userId = sp.getString("userID", "");

		if ("".equals(Constants.userId)) {
			Log.e(TAG, "当前还未获得用户Id，无法建立数据库");
			return null;
		}

		if (databaseHelper == null) {
			String strPath = null;
			String str;
			File m_sdcardFile = android.os.Environment.getExternalStorageDirectory();
			sf = context.getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
			sp = context.getSharedPreferences("basic_info", Context.MODE_PRIVATE);
			serverUrl = sf.getString("ip", Constants.IP) + "/ServiceServlet";

			// // 在SD卡中创建数据库
			// strPath = m_sdcardFile.getAbsolutePath() + File.separator +
			// "Xinchoutong";

			// //判断手机是否存在SD卡
			// if(android.os.Environment.getExternalStorageState().equals(
			// android.os.Environment.MEDIA_MOUNTED)){
			// strPath = m_sdcardFile.getAbsolutePath() + File.separator
			// + "Xinchoutong";
			// }else{
			// strPath = context.getFilesDir() + File.separator + "Xinchoutong";
			// }

			// 在手机内存中创建数据库
			strPath = context.getFilesDir() + File.separator + "Xinchoutong";

			File pathFile = new File(strPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			str = pathFile.getAbsolutePath() + File.separator + sp.getString("partyId", Constants.partyId) + "_" + Constants.userId + ".db3";
			databaseHelper = new DatabaseHelper(context, str, DATABASEVERSION);
			Log.i(TAG, "建立数据库：" + str);
		}
		return databaseHelper;
	}

	public DatabaseHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DatabaseHelper(Context context, String name, CursorFactory factory) {
		this(context, name, null, DATABASEVERSION);
	}

	public DatabaseHelper(Context context, String name) {

		this(context, name, null);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// pe_person_type 用作是否选中节点 0或者空表示未选中，1表示已选中
		// pe_skype 用作是否为关注
		db.execSQL("create table tb_person(primary_id varchar(35),pe_parent_id varchar(30)," + "pe_group_id varchar(30),pe_user_id varchar(30),pe_uid varchar(50),pe_text varchar(50),pe_name varchar(30),"
				+ "pe_nick_name varchar(30),pe_birthday varchar(30),pe_avatar varchar(30),pe_mobile varchar(30)," + "pe_home_phone varchar(30),pe_work_phone varchar(30),pe_work_fax varchar(30),"
				+ "pe_skype varchar(30),pe_msn varchar(30),pe_qq varchar(30),pe_unit_name varchar(50)," + "pe_email varchar(30),pe_work_email varchar(50),pe_mobile_email varchar(50)," + "pe_unit_address varchar(50),pe_home_address varchar(50),pe_remark varchar(100),"
				+ "pe_contacts_name varchar(100),pe_contacts_id varchar(20),pe_post varchar(30)," + "pe_person_type varchar(3),pe_sortkey varchar(50),pe_sortIndex varchar(2),"
				+ "pe_login_id varchar(20),pe_login_name varchar(50),pe_create_time varchar(20),pe_enable varchar,pe_order_id varchar,parent_person_id varchar)");
		// 0为收件箱 1为发件箱 2为草稿箱
		// ismark 0代表未标旗，1代表标旗
		// isread 2代表已读 3代表未读

		/**
		 * 新增tb_selfperson
		 * */
		db.execSQL("create table tb_selfperson(primary_id varchar(35),pe_parent_id varchar(30)," + "pe_group_id varchar(30),pe_user_id varchar(30),pe_text varchar(50),pe_name varchar(30),"
				+ "pe_nick_name varchar(30),pe_birthday varchar(30),pe_avatar varchar(30),pe_mobile varchar(30)," + "pe_home_phone varchar(30),pe_work_phone varchar(30),pe_work_fax varchar(30),"
				+ "pe_skype varchar(30),pe_msn varchar(30),pe_qq varchar(30),pe_unit_name varchar(50)," + "pe_email varchar(30),pe_work_email varchar(50),pe_mobile_email varchar(50)," + "pe_unit_address varchar(50),pe_home_address varchar(50),pe_remark varchar(100),"
				+ "pe_contacts_name varchar(100),pe_contacts_id varchar(20),pe_post varchar(30)," + "pe_person_type varchar(3),pe_sortkey varchar(50),pe_sortIndex varchar(2),"
				+ "pe_login_id varchar(20),pe_create_time varchar(20),pe_enable varchar,pe_order_id varchar,parent_person_id varchar,pe_isShared varchar)");
		// 0为收件箱 1为发件箱 2为草稿箱
		// ismark 0代表未标旗，1代表标旗
		// isread 2代表已读 3代表未读
		db.execSQL("create table tb_allperson(primary_id varchar(35),pe_parent_id varchar(30)," + "pe_group_id varchar(30),pe_user_id varchar(30),pe_text varchar(50),pe_name varchar(30),"
				+ "pe_nick_name varchar(30),pe_birthday varchar(30),pe_avatar varchar(30),pe_mobile varchar(30)," + "pe_home_phone varchar(30),pe_work_phone varchar(30),pe_work_fax varchar(30),"
				+ "pe_skype varchar(30),pe_msn varchar(30),pe_qq varchar(30),pe_unit_name varchar(50)," + "pe_email varchar(30),pe_work_email varchar(50),pe_mobile_email varchar(50)," + "pe_unit_address varchar(50),pe_home_address varchar(50),pe_remark varchar(100),"
				+ "pe_contacts_name varchar(100),pe_contacts_id varchar(20),pe_post varchar(30)," + "pe_person_type varchar(3),pe_sortkey varchar(50),pe_sortIndex varchar(2),"
				+ "pe_login_id varchar(20),pe_create_time varchar(20),pe_enable varchar,pe_order_id varchar,parent_person_id varchar)");

		db.execSQL("create table tb_shperson(primary_id varchar(35),pe_parent_id varchar(30)," + "pe_group_id varchar(30),pe_user_id varchar(30),pe_text varchar(50),pe_name varchar(30),"
				+ "pe_nick_name varchar(30),pe_birthday varchar(30),pe_avatar varchar(30),pe_mobile varchar(30)," + "pe_home_phone varchar(30),pe_work_phone varchar(30),pe_work_fax varchar(30),"
				+ "pe_skype varchar(30),pe_msn varchar(30),pe_qq varchar(30),pe_unit_name varchar(50)," + "pe_email varchar(30),pe_work_email varchar(50),pe_mobile_email varchar(50)," + "pe_unit_address varchar(50),pe_home_address varchar(50),pe_remark varchar(100),"
				+ "pe_contacts_name varchar(100),pe_contacts_id varchar(20),pe_post varchar(30)," + "pe_person_type varchar(3),pe_sortkey varchar(50),pe_sortIndex varchar(2),"
				+ "pe_login_id varchar(20),pe_create_time varchar(20),pe_enable varchar,pe_order_id varchar,parent_person_id varchar)");

		db.execSQL("create table tb_information (primary_id varchar(35) primary key,id varchar(30),title varchar(50),received_time varchar(20),send_user varchar(20),attachment varchar(500),isread varchar(5),ismark varchar(2),group_name varchar(30),content varchar(200),received_user varchar(100),received_userid varchar(50),group_id varchar(20),flag varchar(2),userid varchar(10),localfilename varchar(100),in_attachment varchar(5),in_attachmentid varchar(10))");
		// 外部邮件
		db.execSQL("create table tb_email_set (primary_id varchar(35) primary key,id varchar(30),set_id varchar(50),account_name varchar(50),mail_address varchar(50),mail_pass varchar(50),smtp_host varchar(50),smtp_port varchar(50),pop_host varchar(50),pop_port varchar(50))");
		db.execSQL("create table tb_email_list (primary_id varchar(35) primary key,set_id varchar(50),folder_id varchar(50),folder_name varchar(50),parent_id varchar(50),dep_level varchar(50),_path varchar(50),folder_type varchar(50), user_id varchar(50))");
		db.execSQL("create table tb_email_list_list (primary_id varchar(35) primary key,mail_id varchar(50),_title varchar(50),sender_name varchar(50),mail_date varchar(50),read_flag varchar(50),reply_flag varchar(50), user_id varchar(50),folder_id varchar(50))");
		db.execSQL("create table tb_email_list_list_info (primary_id varchar(35) primary key,_title varchar(50),_content varchar(50),sender_addresses varchar(50),sender_name varchar(50),receiver_addresses varchar(50),receiver_names varchar(50), cc_addresses varchar(50),cc_names varchar(50),bcc_addresses varchar(50),bcc_anames varchar(50),mail_date varchar(50),mail_id varchar(50),set_id varchar(50),folder_id varchar(50),in_attachmentid varchar(50))");

		db.execSQL("create table tb_outinformation (primary_id varchar(35) primary key,id varchar(30),title varchar(50),received_time varchar(20),send_user varchar(20),attachment varchar(500),isread varchar(5),ismark varchar(2),group_name varchar(30),content varchar(200),received_user varchar(100),received_userid varchar(50),group_id varchar(20),flag varchar(2),userid varchar(10),localfilename varchar(100),in_attachment varchar(5),in_attachmentid varchar(10))");
		// 公告通知
		db.execSQL("create table tb_contact_notice (primary_id varchar(35) primary key, " + "userId varchar(50),id varchar(20), top varchar(20),topDate varchar(30),title varchar(50), " + "startTime varchar(50), releaseName varchar(50),"
				+ "details varchar(2000), briefIntroduction varchar(1000),bool varchar(20),author varchar(50),sectionname varchar(100),updateTime varchar(50),isread varchar(20))");
		// 创建公告详情
		db.execSQL("create table tb_contact_notice_detail (primary_id varchar(35) primary key,id varchar(35)," + "title varchar(500),startDate varchar(500), " + "details varchar(5000), releaseName varchar(5000),attachment varchar(500))");
		// messagebox表
		db.execSQL("create table tb_message (primary_id varchar(35),msg_id varchar(50),msg_obj varchar(100),msg_obj_name varchar(100),msg_title varchar(200),msg_time varchar(100),msg_type varchar(100),msg_num varchar(100))");

		// 文件柜 isShare 0表示未分享，1表示已经分享
		db.execSQL("create table tb_contact_folders (primary_id varchar(50) primary key,userId varchar(50)," + "id varchar(50),folderName varchar(50),fileName varchar(50),subFiles varchar(100),"
				+ "fileSize varchar(100),fileUrl varchar(100),fileId varchar(5),releaseDate varchar(100),bool varchar(10),parentId varchar(10),isDirectory varchar(10),isPublic varchar(2),isShare varchar(2),shareTime varchar(20),shareUserName varchar(30),def varchar(30))");

		// 工作日志 di_send_state 0发送成功，1发送失败 delete 0存在 1被删除
		db.execSQL("create table tb_diary(primary_id varachar(35),id varhcar(20),title varchar(50),time varchar(30),content varchar(1000),type varchar(5),iscancel varchar(5),userid varchar(10),addr varchar(500),di_send_state varchar(2),attachment_ids varchar(50),user_name varchar(50),dayTime varchar(100),updateTime varchar(100),operateType varchar(20),"
				+ "rzordpType varchar(20))");

		// 工资单
		db.execSQL("create table tb_payroll(primary_id varachar(35),id varhcar(20),title varchar(50),time varchar(30),money varchar(30),salaryUseTypeId varchar(50),updateTime varchar(100),operateType varchar(20),isread varchar(20),userId varchar(20),salaryUseGroupingId varchar(20))");

		// 定位列表
		db.execSQL("create table tb_gps_location(primary_id varchar(50),loc_longitude varchar(50),loc_latitude varchar(50),loc_time varchar(30),loc_send_state varchar(2),work_image_path varchar(50),flag varchar(2),user_id varchar(2))");

		db.execSQL("create table tb_share(primary_id varchar(50) primary key,fileId varchar(50),userId varchar(50))");
		// 历史记录
		db.execSQL("create table tb_duty(primary_id varchar(50) primary key,registerId varchar(50),registerDate varchar(50),sectionName varchar(50),regFlag varchar(50),fullname varchar(50),dayOfWeek varchar(50),regMins varchar(50),inOffFlag varchar(50),user_id varchar(2))");

		// 未发送数据关联表
		db.execSQL("create table tb_unsend_data(primary_id varchar(50) primary key,se_table_name varchar(50),se_msg_id varchar(50),se_send_time varchar(50),se_msg_title varchar(200),se_image_path varchar(100),se_user_id varchar(10))");

		// 数据上报未发送数据关联表
		db.execSQL("create table tb_dataupload(primary_id varchar(50) primary key,da_title varchar(100),da_address varchar(100),da_content varchar(200),user_id varchar(50),send_time varchar(30),da_send_state varchar(2),da_attachmentid varchar(20), da_process_type varchar(2),da_id varchar(20),da_suggestion varchar(500))");

		// 附件表
		db.execSQL("create table tb_attachment(primary_id varchar(50) primary key,at_localpath varchar(100),at_serverpath varchar(100),at_appath varchar(100),at_filetype varchar(30),at_filesize varchar(10),at_filename varchar(100),at_createtime varchar(20))");

		// 客户表
		db.execSQL("create table tb_customer(primary_id varchar(50),id varchar(30),user_name varchar(100),type varchar(20),mobile varchar(50),telephone varchar(50),create_user_id varchar(40),create_time varchar(50),update_time varchar(50),operate_type varchar(40),belong_user_id varhcar(40),longitude varchar,latitude varchar,attachment varchar,attachmentId varchar,address varchar)");

		// 联系人
		db.execSQL("create table tb_contacts(primary_id varchar(35), id varchar(10) primary key,user_name varchar(50),sex varchar(1),age varchar(10),mobile varchar(11),telephone varchar(20),qq varchar(30),off_position varchar(50),email varchar(100),address varchar(255),content varchar(255),create_user_id varchar(30),create_time varchar(200),update_user_id varchar(50),update_time varchar(50), operate_type varchar(10), fax varchar(50),birthday varchar(12),custom_id varchar(10))");

		// 拜访记录，visit_state 0表示未执行，1表示已经执行
		db.execSQL("create table tb_visit (primary_id varchar(35)," + "id varchar(10),content varchar(255),partakes varchar(255)," + "theme varchar(50),create_time datatime,create_user varchar(50),create_user_id varchar(10),"
				+ "update_user_id varchar(10),update_time datatime,custom_id varchar(10)," + "detailContent varchar(255),telephone varchar(50),operateType varchar(50),visit_state varchar(3),"
				+ "visit_record varchar(500),visit_status varchar,visit_content varchar,start_date varchar,end_date varchar," + "visit_target_id varchar,sign_in_time varchar,sign_out_time varchar,sign_in_address varchar,"
				+ "sign_out_address varchar,visit_custom varchar,visit_target varchar )");

		// 任务列表 task_state--> 0表示未完成 1表示完成 2表示拒绝
		db.execSQL("create table tb_contact_task(primary_id varchar(50) primary key,userId varchar(50)," + " id varchar(50), content varchar(100), assignUserName varchar(50), " + "arrangementTime varchar(50), taskTitle varchar(50), longitude varchar(50),"
				+ " latitude varchar(50), estimateArriveTime varchar(50), createTime varchar(50)," + "bool varchar(20),task_state varchar(2))");

		// 详细任务
		db.execSQL("create table tb_contact_taskdetail(primary_id varchar(50) primary key ," + "taskContent varchar(100), " + "id varchar(100) , " + "phoneNum varchar(50), " + "longitude varchar(50), " + "completeUserName varchar(50), " + "place varchar(50), "
				+ "refuseUserName varchar(50), " + "customerName varchar(50), " + "assignUserName varchar(50), " + "taskTitle varchar(50), " + "refuseReason varchar(50), " + "tasksState varchar(50), " + "latitude varchar(100), " + "arrangementTime varchar(100), "
				+ "estimateArriveTime varchar(50)," + "attachment varchar(500))");

		// 日程管理 status: 1 删除 operateType I,U,D
		db.execSQL("create table tb_schedule(primary_id varchar(50) primary key,id varchar(50),tb_content varchar(500)," + "tb_trans_type varchar(50),tb_begin_time varchar(50),tb_end_time varchar(50),"
				+ "tb_remind_type varchar(50),tb_remind_time varchar(50),tb_color varchar(50)," + "tb_cycles varchar(50),tb_index varchar(50),tb_update_time varchar(50)," + "status varchar(50),tb_operate_type varchar(50),tb_assignerName varchar(50),"
				+ "tb_assignerId varchar(50),tb_task_feedback varchar(50),tb_fullname varchar(50))");

		// 附件表
		db.execSQL("create table tb_attach(primary_id varchar(50) primary key,fileId varchar(50),fileExt varchar(50),fileName varchar(150),fileUrl varchar(250),type varchar(50),fileIcon int,intentType varchar(50))");

		// 消息模板类型
		db.execSQL("create table tb_msg_template_type (primary_id varchar(50) primary key,type_name varchar(50),type_id varchar(50))");
		// 消息模板
		db.execSQL("create table tb_msg_template (primary_id varchar(50) primary key,template_name varchar(50),msg_content varchar(500),template_id varchar(50),type_id varchar(50))");
		
		db.execSQL("CREATE TABLE IF NOT EXISTS scheduletagdate(tagID integer primary key autoincrement,year integer,month integer,day integer,scheduleID integer)");

		// 考勤规范表
		db.execSQL("create table tb_work_norm(primary_id varchar(50) primary key,attendanceStandardId varchar(50),attendanceAddress varchar(50),onWorkTime varchar(50),offWorkTime varchar(50),repeats varchar(50),delay varchar(50),createTime varchar(50),locationPeriod integer)");

		// 群组表
		db.execSQL("CREATE TABLE [tb_group] (primary_id varchar PRIMARY KEY,group_name varchar)");

		// 群组关联人员表
		db.execSQL("CREATE TABLE [tb_group_person] (primary_id varchar,group_id varchar,person_id varchar)");
		// 时间戳表
		db.execSQL("CREATE TABLE [tb_timestamp] (primary_id varchar PRIMARY KEY,person_timestamp varchar,department_timestamp varchar,dutyTimestamp varchar)");
		// 模板分类
		db.execSQL("create table templateType(primaryKey varchar(50) primary key,templateTypeId varchar(50),templateTypeName varchar(50),updateDate varchar(50))");
		// 模板
		db.execSQL("create table template(primaryKey varchar(50) primary key,templateId varchar(50),templateTypeId varchar(50),templateName varchar(50),templateExplain varchar(50),templateFieldLayouType varchar(50),updateDate varchar(50))");
		// 模板字段
		db.execSQL("create table templateField(primaryKey varchar(50) primary key,templateFieldId varchar(50),templateId varchar(50),templateFieldName varchar(50),templateFieldNameIsShow varchar(50),templateFieldIsDetailed varchar(50),fieldType varchar(50),templateFieldSortNum varchar(50),fieldSelectData varchar(300),updateDate varchar(50))");
		// 插件表
		db.execSQL("create table [data_plugin] (id varchar(50) primary key,template_name varchar(50),client_menu_name varchar(60),template_zip_file varchar(60),create_date varchar,sort_num vachar(5),plugin_type varchar(20),operate_type varchar(5))");
		// 模板分类
		db.execSQL("create table reportTemplateType(primaryKey varchar(50) primary key,templateTypeId varchar(50),templateTypeName varchar(50),updateDate varchar(50))");
		// 模板
		db.execSQL("create table reportTemplate(primaryKey varchar(50) primary key,templateId varchar(50),templateTypeId varchar(50),templateName varchar(50),templateExplain varchar(50),templateFieldLayouType varchar(50),updateDate varchar(50))");
		// 模板字段
		db.execSQL("create table reportTemplateField(primaryKey varchar(50) primary key,templateFieldId varchar(50),templateId varchar(50),templateFieldName varchar(50),templateFieldNameIsShow varchar(50),templateFieldIsDetailed varchar(50),templateFieldSortNum varchar(50),updateDate varchar(50))");

		// 聊天
		db.execSQL("create table talk_msg(tb_name varchar(50),tb_date varchar(50),tb_message varchar(500),tb_iscome varchar(5),tb_sendUser varchar(50),tb_isFile varchar(50),tb_url varchar(300))");

		// 文件助手
		db.execSQL("create table file_ass(tb_name varchar(50),tb_date varchar(50),tb_filesize varchar(666),tb_filepath varchar(100),tb_type varchar(5),tb_sendUser varchar(50),tb_fileIcon varchar(444),tb_intentType varchar(555))");
		// 段子
		db.execSQL("create table jokes(id varchar(50) primary key,content varchar(500),praiseNumber varchar(50),collectNumber varchar(50),nickName varchar(100),selfPhoto varchar(100),userid varchar(50),releaseTime varchar(100), place varchar(100), replyNumber vachar(50))");

		// 除全部以外的的单项工资
		db.execSQL("create table tb_dx(usegroupid int(30),wages varchar(50),usergroupname varchar(50),payrollid varchar(50) )");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + "tb_person");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_selfperson");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_allperson");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_shperson");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_information");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_email_set");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_email_list");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_email_list_list");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_email_list_list_info");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_outinformation");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contact_notice");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contact_notice_detail");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_message");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contact_folders");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_diary");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_payroll"); // 删除重新建表
		db.execSQL("DROP TABLE IF EXISTS " + "tb_gps_location");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_share");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_duty");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_unsend_data");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_dataupload");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_attachment");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_customer");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contacts");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_visit");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contact_task");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_contact_taskdetail");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_attach");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_msg_template_type");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_msg_template");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_worknorm");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_work_norm");
		db.execSQL("DROP TABLE IF EXISTS " + "[tb_group]");
		db.execSQL("DROP TABLE IF EXISTS " + "[tb_group_person]");
		db.execSQL("DROP TABLE IF EXISTS " + "[tb_timestamp]");
		db.execSQL("DROP TABLE IF EXISTS " + "[data_plugin]");
		db.execSQL("DROP TABLE IF EXISTS " + "templateType");
		db.execSQL("DROP TABLE IF EXISTS " + "template");
		db.execSQL("DROP TABLE IF EXISTS " + "templateField");
		db.execSQL("DROP TABLE IF EXISTS " + "reportTemplateType");
		db.execSQL("DROP TABLE IF EXISTS " + "reportTemplate");
		db.execSQL("DROP TABLE IF EXISTS " + "reportTemplateField");
		db.execSQL("DROP TABLE IF EXISTS " + "talk_msg");
		db.execSQL("DROP TABLE IF EXISTS " + "schedule");
		db.execSQL("DROP TABLE IF EXISTS " + "scheduletagdate");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_schedule");
		db.execSQL("DROP TABLE IF EXISTS " + "file_ass");
		db.execSQL("DROP TABLE IF EXISTS " + "jokes");
		db.execSQL("DROP TABLE IF EXISTS " + "tb_dx");

		onCreate(db);
	}

	/**
	 * 关闭数据库
	 */
	public void closeDB() {
		if (databaseHelper != null) {
			databaseHelper.getWritableDatabase().close();
			databaseHelper = null;
		}
	}

	public static void closeDBHelper() {
		if (databaseHelper != null) {
			databaseHelper.getWritableDatabase().close();
			databaseHelper = null;
			Log.i(TAG, "数据库执行了操作");
		}
		databaseHelper = null;
	}

	public boolean deleteDatabase(Context context) {
		return context.deleteDatabase(Constants.userId + ".db3");
	}

	/**
	 * public static void setCurrentDataBase(String databaseName) { DATABASENAME
	 * = databaseName; }
	 **/

}
