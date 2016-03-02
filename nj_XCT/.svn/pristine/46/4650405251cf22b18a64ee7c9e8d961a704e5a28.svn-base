package com.xguanjia.hx.version;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;

/**
 * 会员切换
 * 
 * @author ytg
 * 
 */
public class PartySwitch {
	/**
	 * 创建数据库
	 * 
	 * @author ytg
	 * @param dbName
	 */
	public static void createDataBase(String dbName) {
		SQLiteDatabase db = SQLiteManager.createDatabase(dbName);
		db.setVersion(1);
		createTable(db);
	}

	/**
	 * 创建表
	 * 
	 * @param db
	 */
	public static void createTable(SQLiteDatabase db) {
		SQLiteManager sqlmanager = new SQLiteManager(db);
		sqlmanager.setCreateTableSql(getCreateSQL());
		sqlmanager.commit();
		sqlmanager.close();
	}

	/**
	 * 获取建表语句
	 * 
	 * @return list
	 */
	public static List<String> getCreateSQL() {
		List<String> list = new ArrayList<String>();
		// pe_person_type 用作是否选中节点 0或者空表示未选中，1表示已选中
		// pe_skype 用作是否为关注
		String sql1 = "create table tb_person(primary_id varchar(35),pe_create_time varchar(20),pe_parent_id varchar(30),pe_group_id varchar(30),pe_user_id varchar(30),pe_text varchar(50),pe_name varchar(30),pe_nick_name varchar(30),pe_birthday varchar(30),pe_avatar varchar(30),pe_mobile varchar(30),pe_home_phone varchar(30),pe_work_phone varchar(30),pe_work_fax varchar(30),pe_skype varchar(30),pe_msn varchar(30),pe_qq varchar(30),pe_unit_name varchar(50),pe_email varchar(30),pe_work_email varchar(50),pe_mobile_email varchar(50),pe_unit_address varchar(50),pe_home_address varchar(50),pe_remark varchar(100),pe_contacts_name varchar(100),pe_contacts_id varchar(20),pe_post varchar(30),pe_person_type varchar(3),pe_sortkey varchar(50),pe_sortIndex varchar(2),pe_login_id varchar(20))";
		// 0为收件箱 1为发件箱 2为草稿箱
		// ismark 0代表未标旗，1代表标旗
		// isread 2代表已读 3代表未读
		String sql2 = "create table tb_information (primary_id varchar(35) primary key,id varchar(30),title varchar(50),received_time varchar(20),send_user varchar(20),attachment varchar(500),isread varchar(5),ismark varchar(2),group_name varchar(30),content varchar(200),received_user varchar(100),received_userid varchar(50),group_id varchar(20),flag varchar(2),userid varchar(10),localfilename varchar(100),in_attachment varchar(5),in_attachmentid varchar(5))";
		// 公告通知
		String sql3 = "create table tb_contact_notice (primary_id varchar(35) primary key, " + "userId varchar(50),id varchar(20), top varchar(20),topDate varchar(30),title varchar(50), "
				+ "startTime varchar(50), releaseName varchar(50)," + "details varchar(2000), briefIntroduction varchar(1000),bool varchar(20))";
		//创建公告详情
		String sql4 = "create table tb_contact_notice_detail (primary_id varchar(35) primary key,id varchar(35)," + "title varchar(500),startDate varchar(500), "
				+ "details varchar(5000), releaseName varchar(5000),attachment varchar(500))";
		//messagebox表
		String sql5 = "create table tb_message (primary_id varchar(35),msg_id varchar(50),msg_obj varchar(100),msg_obj_name varchar(100),msg_title varchar(200),msg_time varchar(100),msg_type varchar(100),msg_num varchar(100))";
		//附件表
		String sql6 = "create table tb_attach(primary_id varchar(50) primary key,fileId varchar(50),fileExt varchar(50),fileName varchar(150),fileUrl varchar(250),type varchar(50),fileIcon int,intentType varchar(50))";

		//文件柜 isShare 0表示未分享，1表示已经分享
		String sql7 = "create table tb_contact_folders (primary_id varchar(50) primary key,userId varchar(50),"
				+ "id varchar(50),folderName varchar(50),fileName varchar(50),subFiles varchar(100),"
				+ "fileSize varchar(100),fileUrl varchar(100),fileId varchar(5),releaseDate varchar(100),bool varchar(10),parentId varchar(10),isDirectory varchar(10),isPublic varchar(2),isShare varchar(2),shareTime varchar(20),shareUserName varchar(30),def varchar(30))";

		//工作日志 di_send_state 0发送成功，1发送失败
		String sql8	="create table tb_diary(primary_id varachar(35),id varhcar(20),title varchar(50),time varchar(30),content varchar(1000),type varchar(5),iscancel varchar(5),userid varchar(10),addr varchar(500),di_send_state varchar(2),attachment_ids varchar(50),user_name varchar(50))";

		//定位列表
		String sql9 = "create table tb_gps_location(primary_id varchar(50),loc_longitude varchar(50),loc_latitude varchar(50),loc_time varchar(30),loc_send_state varchar(2),work_image_path varchar(50),flag varchar(2),user_id varchar(2))";
		//文件柜分享
		String sql10 = "create table tb_share(primary_id varchar(50) primary key,fileId varchar(50),userId varchar(50))";
		
		//未发送数据关联表
		String sql11 ="create table tb_unsend_data(primary_id varchar(50) primary key,se_table_name varchar(50),se_msg_id varchar(50),se_send_time varchar(50),se_msg_title varchar(200),se_image_path varchar(100),se_user_id varchar(10))";
		//数据上报未发送数据关联表
		String sql12="create table tb_dataupload(primary_id varchar(50) primary key,da_title varchar(100),da_address varchar(100),da_content varchar(200),user_id varchar(50),send_time varchar(30),da_send_state varchar(2),da_attachmentid varchar(20), da_process_type varchar(2),da_id varchar(20),da_suggestion varchar(500))";
		
		//附件表
		String sql13="create table tb_attachment(primary_id varchar(50) primary key,at_localpath varchar(100),at_serverpath varchar(100),at_appath varchar(100),at_filetype varchar(30),at_filesize varchar(10),at_filename varchar(100),at_createtime varchar(20))";
		
		
		String sql16 = "create table tb_customer(primary_id varchar(50),id varchar(30),user_name varchar(100),type varchar(20),mobile varchar(50),telephone varchar(50),create_user_id varchar(40),create_time varchar(50),update_time varchar(50),operate_type varchar(40),belong_user_id varhcar(40))";

		String sql17 = "create table tb_contacts(primary_id varchar(35), id varchar(10) primary key,user_name varchar(50),sex varchar(1),age varchar(10),mobile varchar(11),telephone varchar(20),qq varchar(30),off_position varchar(50),email varchar(100),address varchar(255),content varchar(255),create_user_id varchar(30),create_time varchar(200),update_user_id varchar(50),update_time varchar(50), operate_type varchar(10), fax varchar(50),birthday varchar(12),custom_id varchar(10))";

		String sql18 = "create table tb_visit (primary_id varchar(35)," + "id varchar(10),content varchar(255),partakes varchar(255)," + "theme varchar(50),create_time datatime,create_user varchar(50),create_user_id varchar(10),"
				+ "update_user_id varchar(10),update_time datatime,custom_id varchar(10)," + "detailContent varchar(255),telephone varchar(50),operateType varchar(50) )";

		String sql19 = "create table tb_contact_task(primary_id varchar(50) primary key,userId varchar(50), id varchar(50), content varchar(100), assignUserName varchar(50), arrangementTime varchar(50), taskTitle varchar(50), longitude varchar(50), latitude varchar(50), estimateArriveTime varchar(50), createTime varchar(50),bool varchar(20),task_state varchar(2))";

		String sql20 = "create table tb_contact_taskdetail(primary_id varchar(50) primary key ," + "taskContent varchar(100), " + "id varchar(100) , " + "phoneNum varchar(50), " + "longitude varchar(50), " + "completeUserName varchar(50), " + "place varchar(50), "
				+ "refuseUserName varchar(50), " + "customerName varchar(50), " + "assignUserName varchar(50), " + "taskTitle varchar(50), " + "refuseReason varchar(50), " + "tasksState varchar(50), " + "latitude varchar(100), " + "arrangementTime varchar(100), "
				+ "estimateArriveTime varchar(50),attachment varchar(500))";

		//消息模板类型
		String sql21=("create table tb_msg_template_type (primary_id varchar(50) primary key,type_name varchar(50),type_id varchar(10))");
		//消息模板
		String sql22=("create table tb_msg_template (primary_id varchar(50) primary key,template_name varchar(50),msg_content varchar(500),template_id varchar(50),type_id varchar(50))");
		list.add(sql1);
		list.add(sql2);
		list.add(sql3);
		list.add(sql4);
		list.add(sql5);
		list.add(sql6);
		list.add(sql7);
		list.add(sql8);
		list.add(sql9);
		list.add(sql10);
		list.add(sql11);
		list.add(sql12);
		list.add(sql13);
		list.add(sql16);
		list.add(sql17);
		list.add(sql18);
		list.add(sql19);
		list.add(sql20);
		list.add(sql21);
		list.add(sql22);
		return list;
	}
}
