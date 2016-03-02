package com.xguanjia.hx.contact.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.PingYinUtil;
import com.xguanjia.hx.contact.bean.PersonBean;


/**
 * 联系人数据库操作
 * 
 * @author dolphin
 * 
 */
public class PersonSelfService {
	public static final String PE_PERSON_TYPE_ORG = "0";
	private SQLiteDatabase database;

	public SQLiteDatabase getDatabase() {
		if (database == null) {
			database = dbOpenHelper.getWritableDatabase();
		}
		return database;
	}

	private DatabaseHelper dbOpenHelper;

	public PersonSelfService(Context context) {
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
	}

	/**
	 * 根据登录ID查询登录人员姓名
	 */
	public String queryPerson(String loginId) {
		database = getDatabase();
		String sql = "select * from tb_selfperson where pe_user_id = ?";
		Cursor cursor = database.rawQuery(sql, new String[]{loginId});
		List<PersonBean> list = getPersonFromCursor(cursor);
		if (list.size() > 0) {
			cursor.close();
			return list.get(0).getName();
		}
		cursor.close();
		return null;
	}

	/**
	 * 保存联系人
	 * 
	 * @param list
	 * @param personType
	 */
	public void savePerson(List<PersonBean> list, int personType, boolean isDeleateAllPerson) {
		database = dbOpenHelper.getWritableDatabase();
		if (isDeleateAllPerson) {
			database.execSQL("delete from tb_selfperson", new Object[]{});
		}
		List<String> sqlList = new ArrayList<String>();
		int j = list.size();
		for (int i = 0; i < j; i++) {
			String sortKeyName = "";
			String sortIndex = "";
			String key = UUID.randomUUID().toString().replace("-", "");
			PersonBean bean = list.get(i);
			sortKeyName = PingYinUtil.changeToPingYin(bean.getName(), false) + bean.getName() + bean.getMobile();
			if (!"".equals(sortKeyName)) {
				sortIndex = sortKeyName.substring(0, 1);
			}
			String sql = "insert into tb_selfperson(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_isShared) values("
					+ "'"
					+ key
					+ "','"
					+ bean.getParentId()
					+ "','"
					+ bean.getGroupId()
					+ "','"
					+ bean.getUserId()
					+ "','"
					+ bean.getText()
					+ "','"
					+ bean.getName()
					+ "','"
					+ bean.getNickName()
					+ "','"
					+ bean.getBirthday()
					+ "','"
					+ bean.getAvatar()
					+ "','"
					+ bean.getMobile()
					+ "','"
					+ bean.getHomePhone()
					+ "','"
					+ bean.getWorkPhone()
					+ "','"
					+ bean.getWorkFax()
					+ "','"
					+ bean.getSkype()
					+ "','"
					+ bean.getMsn()
					+ "','"
					+ bean.getQq()
					+ "','"
					+ bean.getUnitName()
					+ "','"
					+ bean.getEmail()
					+ "','"
					+ bean.getWorkEmail()
					+ "','"
					+ bean.getMobile()
					+ "','"
					+ bean.getUnitAddress()
					+ "','"
					+ bean.getHomeAddress()
					+ "','"
					+ bean.getRemark()
					+ "','"
					+ bean.getContactsName()
					+ "','"
					+ bean.getContactsId()
					+ "','"
					+ personType
					+ "','" + sortKeyName + "','" + sortIndex + "','" + Constants.userId + "','" + bean.getPost()+ "','"+ bean.getIsShared() + "')";
			sqlList.add(sql);
		}
		database.beginTransaction();
		if (null != database) {
			try {
				for (int i = 0; i < sqlList.size(); i++) {
					database.execSQL(sqlList.get(i));
				}
				database.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				database.endTransaction();
				//database.close();
			}
		}
	}

	/**
	 * 根据机构ID查询子机构
	 * 
	 * @param groupId
	 * @return
	 */
	public List<PersonBean> queryChildOrg(String groupId) {
		// database = getDatabase();
		database = dbOpenHelper.getWritableDatabase();
		String sql = "select * from tb_selfperson where pe_parent_id = ?";
		Cursor cursor = database.rawQuery(sql, new String[]{groupId});
		List<PersonBean> list = getPersonFromCursor(cursor);
		cursor.close();
		return list;
	}

	/**
	 * 
	 * @param personType
	 *            人员类型
	 * @param groupId
	 *            人员组织id
	 * @param sortKey
	 *            关键字查询
	 * @param queryMode
	 *            1查询所有人，2按照类型查询，3按照关键字查询，4组织结构查询，5查询重名的联系人，6按照姓名查询
	 * @return
	 */
	public List<PersonBean> queryPersonByType(int personType, String groupId, String sortKey, int queryMode, boolean isFirstSerch) {

		List<PersonBean> list = new ArrayList<PersonBean>();
		// database = getDatabase();
		database = dbOpenHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder("select * from tb_selfperson where pe_login_id = ? and ");
		Cursor cursor = null;
		String sql2 = "select * from tb_selfperson where pe_login_id = ? and pe_group_id = 2";
		boolean flag = true;
		if (isFirstSerch) {
			// 第一次查询的时候,1/查询是否外部联系人：如果有外部联系人，查询出第一级的外部联系人公司与查询出的内部联系人合并，否则，直接查询出内部联系人
			cursor = database.rawQuery(sql2, new String[]{Constants.userId});
			if (cursor.moveToFirst()) {
				// 遍历游标，将结果放在list中
				List<PersonBean> list2 = getPersonFromCursor(cursor);
				cursor.close();
				if (list2.size() > 0) {
					String orgId = list2.get(0).getGroupId();
					List<PersonBean> childOrgList = queryChildOrg(orgId);
					if (childOrgList.size() > 0) {
						list2 = childOrgList;
						flag = false;
					}

				}

				// 根据group_id查询出内部联系人并遍历游标取出结果集
				sql2 = "select * from tb_selfperson where pe_login_id = ? and pe_group_id = 1";
				cursor = database.rawQuery(sql2, new String[]{Constants.userId});
				List<PersonBean> list3 = getPersonFromCursor(cursor);

				if ((list3.size() > 0) && (flag)) {
					String orgId = list3.get(0).getGroupId();
					List<PersonBean> childOrgList = queryChildOrg(orgId);
					if (childOrgList.size() > 0) {
						list3 = childOrgList;
					}
				}

				list3.addAll(list2);
				if (null != database) {
					if (null != cursor) {
						cursor.close();
					}
					//database.close();
				}
				Log.i("", "list3.size:" + list3.size() + ";" + list3.toString());
				return list3;
			} else {
				// 直接查询出内部联系人
				sql2 = "select * from tb_selfperson where pe_login_id = ? and pe_parent_id = 1";
				cursor = database.rawQuery(sql2, new String[]{Constants.userId});
				list = getPersonFromCursor(cursor);

				if (null != database) {
					if (null != cursor) {
						cursor.close();
					}
					//database.close();
				}
				return list;
			}
		}
		switch (queryMode) {
			case 1 :
				sql.append("pe_group_id = '' order by pe_sortIndex");
				cursor = database.rawQuery(sql.toString(), new String[]{Constants.userId});
				break;
			case 3 :
				// if (personType == 1) {
				// //
				// sql.append("pe_parent_id='' and pe_person_type != 0 and pe_sortkey like ? order by pe_sortIndex");
				// sql.append("pe_person_type = 1 and pe_sortkey like ? order by pe_sortIndex");
				// cursor = database.rawQuery(sql.toString(), new String[] {
				// Constants.userId, "%" + sortKey + "%" });
				// } else {
				// sql.append("pe_parent_id='' and pe_person_type = ? and pe_sortkey like ? order by pe_sortIndex");
				// cursor = database.rawQuery(sql.toString(), new String[] {
				// Constants.userId, personType + "", "%" + sortKey + "%" });
				// }
				sql.append("pe_group_id = '' and pe_sortkey like ? order by pe_sortIndex");
				cursor = database.rawQuery(sql.toString(), new String[]{Constants.userId, "%" + sortKey + "%"});
				break;
			case 4 :
				sql.append("pe_parent_id = ?");
				cursor = database.rawQuery(sql.toString(), new String[]{Constants.userId, groupId + ""});
				break;
			default :
				break;
		}

		list = getPersonFromCursor(cursor);
		if (null != database) {
			if (null != cursor) {
				cursor.close();
			}
			//database.close();
		}

		return list;
	}

	public List<PersonBean> getPersonFromCursor(Cursor cursor) {
		List<PersonBean> list = new ArrayList<PersonBean>();
		if (null == cursor) {
			return list;
		}
		while (cursor.moveToNext()) {
			PersonBean bean = new PersonBean();
			bean.setPrimaryId(cursor.getString(cursor.getColumnIndex("primary_id")));
			bean.setParentId(cursor.getString(cursor.getColumnIndex("pe_parent_id")));
			bean.setGroupId(cursor.getString(cursor.getColumnIndex("pe_group_id")));
			bean.setUserId(cursor.getString(cursor.getColumnIndex("pe_user_id")));
			bean.setText(cursor.getString(cursor.getColumnIndex("pe_text")));
			bean.setName(cursor.getString(cursor.getColumnIndex("pe_name")));
			bean.setNickName(cursor.getString(cursor.getColumnIndex("pe_nick_name")));
			bean.setBirthday(cursor.getString(cursor.getColumnIndex("pe_birthday")));
			bean.setAvatar(cursor.getString(cursor.getColumnIndex("pe_avatar")));
			bean.setMobile(cursor.getString(cursor.getColumnIndex("pe_mobile")));
			bean.setHomePhone(cursor.getString(cursor.getColumnIndex("pe_home_phone")));
			bean.setWorkPhone(cursor.getString(cursor.getColumnIndex("pe_work_phone")));
			bean.setWorkFax(cursor.getString(cursor.getColumnIndex("pe_work_fax")));
			bean.setSkype(cursor.getString(cursor.getColumnIndex("pe_skype")));
			bean.setMsn(cursor.getString(cursor.getColumnIndex("pe_msn")));
			bean.setQq(cursor.getString(cursor.getColumnIndex("pe_qq")));
			bean.setUnitName(cursor.getString(cursor.getColumnIndex("pe_unit_name")));
			bean.setEmail(cursor.getString(cursor.getColumnIndex("pe_email")));
			bean.setWorkEmail(cursor.getString(cursor.getColumnIndex("pe_work_email")));
			bean.setMobileEmail(cursor.getString(cursor.getColumnIndex("pe_mobile_email")));
			bean.setUnitAddress(cursor.getString(cursor.getColumnIndex("pe_unit_address")));
			bean.setHomeAddress(cursor.getString(cursor.getColumnIndex("pe_home_address")));
			bean.setRemark(cursor.getString(cursor.getColumnIndex("pe_remark")));
			bean.setContactsName(cursor.getString(cursor.getColumnIndex("pe_contacts_name")));
			bean.setContactsId(cursor.getString(cursor.getColumnIndex("pe_contacts_id")));
			bean.setSortIndex(cursor.getString(cursor.getColumnIndex("pe_sortIndex")));
			bean.setPersonType(cursor.getString(cursor.getColumnIndex("pe_person_type")));
			bean.setPost(cursor.getString(cursor.getColumnIndex("pe_post")));
			list.add(bean);
		}
		return list;
	}

	/*
	 * 删除组织机构
	 * 
	 * @param pe_person_type{0 组织机构}
	 */
	public void deletePerson() {
		database = dbOpenHelper.getWritableDatabase();
		database.beginTransaction();
		if (null != database) {
			try {
				database.execSQL("delete from tb_selfperson");
				database.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				database.endTransaction();
				//database.close();
			}
		}
	}

	/**
	 * 删除联系人
	 * 
	 * @param orderId
	 */
	public void deletePersonById(List<String> personIds) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		database.beginTransaction();
		if (null != database) {
			try {
				for (int i = 0; i < personIds.size(); i++) {
					database.execSQL("delete from tb_selfperson where primary_id = ?", new Object[]{personIds.get(i)});
				}
				database.setTransactionSuccessful();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				database.endTransaction();
				//database.close();
			}
		}
	}

	public List<PersonBean> getPhoneContacts(Context context) {
		List<PersonBean> data = new ArrayList<PersonBean>();
		Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
		try {
			while (cursor.moveToNext()) {
				PersonBean bean = new PersonBean();
				String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				bean.setName(name);
				String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
				String hasPhone = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (hasPhone.compareTo("1") == 0) {
					// 电话号码
					Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
					while (phoneCursor.moveToNext()) {
						String phoneNo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						String phoneTpye = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
						// 手机号码
						if ("2".equals(phoneTpye)) {
							bean.setMobile(phoneNo);
							// 住宅
						} else if ("1".equals(phoneTpye)) {
							bean.setHomePhone(phoneNo);
							// 工作号码
						} else if ("3".equals(phoneTpye)) {
							bean.setWorkPhone(phoneNo);
							// 其他号码
						} else {
						}
					}
				}
				// 电子邮件
				Cursor emailCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + contactId,
						null, null);
				while (emailCursor.moveToNext()) {
					String email = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
					String emailType = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
					bean.setEmail(email);
				}
				emailCursor.close();
				// 及时消息
				String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
				String[] imWhereParams = new String[]{contactId, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
				Cursor QQCursor = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, imWhere, imWhereParams, null);
				while (QQCursor.moveToNext()) {
					String qqNo = QQCursor.getString(QQCursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
					System.out.println("qqNo:" + qqNo);
					bean.setQq(qqNo);
				}
				QQCursor.close();
				// 组织
				/*
				 * String orgWhere = ContactsContract.Data.CONTACT_ID +
				 * " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
				 * String[] orgWhereParams = new String[] { contactId,
				 * ContactsContract
				 * .CommonDataKinds.Organization.CONTENT_ITEM_TYPE }; Cursor
				 * companyCursor =
				 * context.getContentResolver().query(ContactsContract
				 * .Data.CONTENT_URI, null, orgWhere, orgWhereParams, null);
				 * while (companyCursor.moveToNext()) { String company =
				 * companyCursor
				 * .getString(companyCursor.getColumnIndex(ContactsContract
				 * .CommonDataKinds.Organization.DATA));
				 * System.out.println("company:" + company);
				 * bean.setUnitName(company); }
				 */
				data.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 查询内部联系人，有userId的及为内部联系人
	 * 
	 * @return
	 */
	public List<PersonBean> queryInternalPerson() {
		String sql = "select * from tb_selfperson where pe_login_id = ? and pe_user_id!= ? and pe_user_id !=''";
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(sql, new String[]{Constants.userId, Constants.userId});
		List<PersonBean> list = getPersonFromCursor(cursor);
		return list;
	}

	/**
	 * 根据内部联系人手机号码查询人员的相关信息
	 * 
	 * @PersonBean
	 */
//	public String queryUserInfoByPhoneNum(String phoneNum) {
//		PersonBean bean = new PersonBean();
//		List<String> departmentList = new ArrayList<String>();
//		boolean flag = true;
//		String sql = "select pe_parent_id,pe_group_id,pe_text,pe_name,pe_post,pe_qq from tb_selfperson where pe_mobile = ?";
//		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
//		Cursor cursor = database.rawQuery(sql, new String[]{phoneNum});
//		if (cursor.moveToFirst()) {
//			bean.setParentId(cursor.getString(cursor.getColumnIndex("pe_parent_id")));
//			bean.setGroupId(cursor.getString(cursor.getColumnIndex("pe_group_id")));
//			bean.setText(cursor.getString(cursor.getColumnIndex("pe_text")));
//			bean.setName(cursor.getString(cursor.getColumnIndex("pe_name")));
//			bean.setPost(cursor.getString(cursor.getColumnIndex("pe_post")));
//			bean.setQq(cursor.getString(cursor.getColumnIndex("pe_qq")));
//			sql = "select pe_name,pe_parent_id,pe_group_id from tb_selfperson where pe_group_id = ?";
//			while (flag) {
//				cursor = database.rawQuery(sql, new String[]{bean.getParentId()});
//				if (cursor.moveToFirst()) {
//					departmentList.add(cursor.getString(cursor.getColumnIndex("pe_name")));
//					bean.setParentId(cursor.getString(cursor.getColumnIndex("pe_parent_id")));
//					bean.setGroupId(cursor.getString(cursor.getColumnIndex("pe_group_id")));
//				}
//				if ("1".equals(bean.getParentId()) || "2".equals(bean.getParentId())) {
//					flag = false;
//				}
//			}
//			// bean.setDepartmentList(departmentList);
//			departmentList.add(bean.getName() + "\t" + bean.getPost());
//			StringBuilder sb = new StringBuilder();
//			StringBuilder partLine = new StringBuilder();
//			for (int i = departmentList.size() - 1; i >= 0; i--) {
//				sb.append(partLine).append(departmentList.get(i) + "\n");
//				partLine.append("-");
//			}
//			return sb.toString();
//		}
//		return "";
//	}
	

	/**
	 * 查询第一级内部联系人组织机构
	 * 
	 * @return
	 */
	public List<PersonBean> queryInterDept() {
		List<PersonBean> list = new ArrayList<PersonBean>();
		Cursor cursor = null;
		database = dbOpenHelper.getReadableDatabase();
		String sql2 = "select * from tb_selfperson where pe_login_id = ? and pe_parent_id = 1";
		cursor = database.rawQuery(sql2, new String[]{Constants.userId});
		list = getPersonFromCursor(cursor);
		if (null != database) {
			if (null != cursor) {
				cursor.close();
			}
		}
		//database.close();
		return list;
	}

	/**
	 * 标注为关注
	 */
	public void attentionPerson(String isAttention, String primaryId) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		if (null != database) {
			try {
				StringBuilder sql = new StringBuilder("update tb_selfperson set pe_skype = ? where primary_id = ?");
				database.execSQL(sql.toString(), new String[]{isAttention, primaryId});
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//database.close();
			}
		}

	}

	/**
	 * 查询出关注的联系人
	 * 
	 * @return
	 */
	public List<PersonBean> queryAttactionPersonList() {
		List<PersonBean> data = new ArrayList<PersonBean>();
		database = dbOpenHelper.getWritableDatabase();
		StringBuilder sql = new StringBuilder("select * from tb_selfperson where pe_login_id = ? and pe_skype = 1");
		Cursor cursor = database.rawQuery(sql.toString(), new String[]{Constants.userId});
		data = getPersonFromCursor(cursor);
		if (null != database) {
			if (null != cursor) {
				cursor.close();
			}
			//database.close();
		}
		return data;
	}
	
	/**
	 * 根据内部联系人手机号码查询人员的相关信息
	 * 
	 * @PersonBean
	 */
	public List<String> queryUserInfoByPhoneNum(String phoneNum) {
		if(phoneNum == null){
			return null;
		}
//		if (dbOpenHelper == null) {
//			dbOpenHelper = DatabaseHelper.getInstance(context);
//		}
		Map<String, String> map1 = new HashMap<String, String>();
		PersonBean bean = new PersonBean();
		List<String> departmentList = new ArrayList<String>();
		boolean flag = true;
//		String sql = "select * from tb_selfperson where (pe_mobile = ? or pe_work_phone = ?) and pe_parent_id = (select min(pe_parent_id) from tb_selfperson where (pe_mobile = ? or pe_work_phone = ?))";
//		String sql = "select * from tb_selfperson where pe_mobile = ? or pe_work_phone = ?";
		String sql = "select * from tb_selfperson where pe_mobile like ?";
		Cursor cursor = null;
		try {
			SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
			cursor = database.rawQuery(sql, new String[] { "%"+phoneNum+"%"});
			if (cursor.moveToFirst()) {
				bean.setParentId(cursor.getString(cursor
						.getColumnIndex("pe_parent_id")));
				bean.setGroupId(cursor.getString(cursor
						.getColumnIndex("pe_group_id")));
				bean.setText(cursor.getString(cursor.getColumnIndex("pe_text")));
				bean.setName(cursor.getString(cursor.getColumnIndex("pe_name")));
				bean.setPost(cursor.getString(cursor.getColumnIndex("pe_post")));
				bean.setAvatar(cursor.getString(cursor.getColumnIndex("pe_avatar")));
				sql = "select * from tb_selfperson where pe_group_id = ?";
//				while (flag) {
					cursor = database.rawQuery(sql,
							new String[] { bean.getParentId() });
					if (cursor.moveToFirst()) {
						departmentList.add(cursor.getString(cursor
								.getColumnIndex("pe_name")));
						bean.setParentId(cursor.getString(cursor
								.getColumnIndex("pe_parent_id")));
						bean.setGroupId(cursor.getString(cursor
								.getColumnIndex("pe_group_id")));
					}
//					if ("0".equals(bean.getParentId())
//							|| "1".equals(bean.getParentId())) {
//						flag = false;
//					}
//				}
				// bean.setDepartmentList(departmentList);
				departmentList.add(bean.getName() + "\t" + bean.getPost());
				departmentList.add(bean.getAvatar());
				StringBuilder sb = new StringBuilder();
				StringBuilder partLine = new StringBuilder();
				for (int i = departmentList.size() - 1; i >= 0; i--) {
					sb.append(partLine).append(departmentList.get(i) + "\n");
					partLine.append("-");
				}
				return departmentList;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
//			database.close();
		}
		return null;
		
	}

}
