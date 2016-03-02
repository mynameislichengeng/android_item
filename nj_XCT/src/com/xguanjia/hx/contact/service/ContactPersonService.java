package com.xguanjia.hx.contact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.xguanjia.hx.common.Constants;
import com.xguanjia.hx.common.DatabaseHelper;
import com.xguanjia.hx.common.PingYinUtil;
import com.xguanjia.hx.contact.bean.PersonBean;

/**
 * 
 * @author huke
 * 
 *         说明：联系人表数据库操作类
 * 
 *         日期：2013-11-12
 * 
 */
public class ContactPersonService {

	private static final String TAG = "ContactPersonService";
	private DatabaseHelper dbOpenHelper;
	private Context context;

	public ContactPersonService(Context context) {
		this.context = context;
		this.dbOpenHelper = DatabaseHelper.getInstance(context);
		// this.m_sqliteDatabase = dbOpenHelper.getWritableDatabase();
	}

	/**
	 * 保存联系人
	 * 
	 * @param list
	 */
	public void savePerson(List<PersonBean> list) {
		SQLiteDatabase database = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}
			database.setLockingEnabled(false);
			database.beginTransaction();
			// database.execSQL("delete from tb_person", new Object[] {});
			int j = list.size();
			Log.i(TAG, "数据总数:" + j);
			for (int i = 0; i < j; i++) {
				String sortKeyName = "";
				String sortIndex = "";
				String key = UUID.randomUUID().toString().replace("-", "");
				PersonBean bean = list.get(i);
				sortKeyName = PingYinUtil
						.changeToPingYin(bean.getName(), false)
						+ bean.getName() + bean.getMobile();
				if (!"".equals(sortKeyName)) {
					sortIndex = sortKeyName.substring(0, 1);
				}
				database.execSQL(
						"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,pe_nick_name,pe_birthday,pe_avatar,"
								+ "pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,"
								+ "pe_mobile_email,pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,pe_person_type,pe_sortkey,"
								+ "pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
						new Object[] { key, bean.getParentId(),
								bean.getGroupId(), bean.getUserId(),
								bean.getText(), bean.getName(),
								bean.getNickName(), bean.getBirthday(),
								bean.getAvatar(), bean.getMobile(),
								bean.getHomePhone(), bean.getWorkPhone(),
								bean.getWorkFax(), bean.getSkype(),
								bean.getMsn(), bean.getQq(),
								bean.getUnitName(), bean.getEmail(),
								bean.getWorkEmail(), bean.getMobile(),
								bean.getUnitAddress(), bean.getHomeAddress(),
								bean.getRemark(), bean.getContactsName(),
								bean.getContactsId(), bean.getPersonType(),
								sortKeyName, sortIndex, Constants.userId,
								bean.getPost(), bean.getCreateTime(),
								bean.getEnable() });
			}
			database.setTransactionSuccessful();
			database.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "插入联系人记录异常：" + e.getMessage());
		} finally {
			if (null != database) {
				// database.close();
			}
			// database.close();
		}
	}

	/**
	 * 将指定联系人列表入库
	 * 
	 * @param personList
	 */
	public void insertPerson(List<PersonBean> personList) {
		int batchSize = 500;
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			List<PersonBean> tempPersonList = new ArrayList<PersonBean>();
			for (int i = 0; i < personList.size(); i++) {
				PersonBean bean = personList.get(i);
				tempPersonList.add(bean);
				if (i % batchSize == 0) {
					insertPersonList(tempPersonList);
					tempPersonList = new ArrayList<PersonBean>();
				}
			}
			insertPersonList(tempPersonList);
		} catch (Exception e) {
			Log.e(TAG, "人员列表入库异常。。。。。。。");
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 向表中插入一串数据
	 * 
	 * @param openHelper
	 * @param appInfo
	 * @return 如果成功则返回true，否则返回flase
	 */
	public void insert(List<PersonBean> personList) {
		SQLiteDatabase database = null;
		long time1 = System.currentTimeMillis();
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}
			database.beginTransaction();
			for (int i = 0; i < personList.size(); i++) {
				PersonBean bean = personList.get(i);
				// 执行插入操作
				String sortKeyName = "";
				String sortIndex = "";
				String key = UUID.randomUUID().toString().replace("-", "");
				if (bean.getName() != null && !"".equals(bean.getName().trim())) {
					// sortKeyName =
					// PingYinUtil.changeToPingYin(bean.getName(),
					// false) +
					// bean.getName() + bean.getMobile();
					sortIndex = PingYinUtil.changeToPingYin(bean.getName(),
							false).substring(0, 1);
					// sortKeyName = sortIndex + bean.getName();

					sortKeyName = "";
					String strName = bean.getName();
					String[] strNameNum = new String[strName.length()];
					for (int j = 0; j < strName.length(); j++) {
						strNameNum[j] = strName.substring(j, j + 1);
					}
					for (int j = 0; j < strNameNum.length; j++) {
						sortKeyName = sortKeyName
								+ PingYinUtil.changeToPingYin(strNameNum[j],
										false) + strNameNum[j];
					}
				}
				String sql = "insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,"
						+ "pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,"
						+ "pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,"
						+ "pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,"
						+ "pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable,"
						+ "pe_order_id,parent_person_id,pe_login_name,pe_uid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				SQLiteStatement stat = database.compileStatement(sql);

				// for(PersonBean person:personList){
				stat.bindString(1, key);
				stat.bindString(2, bean.getParentId());
				stat.bindString(3, bean.getGroupId());
				stat.bindString(4, bean.getUserId());
				stat.bindString(5, bean.getText());
				stat.bindString(6, bean.getName());
				stat.bindString(7, bean.getNickName());
				stat.bindString(8, bean.getBirthday());
				stat.bindString(9, bean.getAvatar());
				stat.bindString(10, bean.getMobile());
				stat.bindString(11, bean.getHomePhone());
				stat.bindString(12, bean.getWorkPhone());
				stat.bindString(13, bean.getWorkFax());
				stat.bindString(14, bean.getSkype());
				stat.bindString(15, bean.getMsn());
				stat.bindString(16, bean.getQq());
				stat.bindString(17, bean.getUnitName());
				stat.bindString(18, bean.getEmail());
				stat.bindString(19, bean.getWorkEmail());
				stat.bindString(20, bean.getMobile());
				stat.bindString(21, bean.getUnitAddress());
				stat.bindString(22, bean.getHomeAddress());
				stat.bindString(23, bean.getRemark());
				stat.bindString(24, bean.getContactsName());
				stat.bindString(25, bean.getContactsId());
				stat.bindString(26, bean.getPersonType());
				stat.bindString(27, sortKeyName);
				stat.bindString(28, sortIndex);
				stat.bindString(29, Constants.userId);
				stat.bindString(30, bean.getPost());
				stat.bindString(31, bean.getCreateTime());
				stat.bindString(32, bean.getEnable());
				stat.bindString(33, bean.getOrderId());
				stat.bindString(34, bean.getParentPersonId());
				stat.bindString(35, bean.getLoginName());
				stat.bindString(36, bean.getUid());
				long result = stat.executeInsert();
				if (result < 0) {
					Log.d("ContactPersonService",
							"数据接收出错数据接收出错数据接收出错数据接收出错数据接收出错");
				}

			}
			database.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != database) {
					database.endTransaction();
					database.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		Log.d("ContactPersonService", "-------------时间一共用了" + (endTime - time1)
				/ 1000 + "秒");
	}

	// 分批入库
	public void bactchInsertPerson(List<PersonBean> personList) {
		int batchSize = 500;
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			List<PersonBean> tempPersonList = new ArrayList<PersonBean>();
			for (int i = 0; i < personList.size(); i++) {
				PersonBean bean = personList.get(i);
				tempPersonList.add(bean);
				if (i % batchSize == 0) {
					bactchInsertPerson(tempPersonList);
					tempPersonList = new ArrayList<PersonBean>();
				}
			}
			bactchInsertPerson(tempPersonList);
		} catch (Exception e) {
			Log.e(TAG, "人员列表入库异常。。。。。。。");
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 根据PersonBean中不同的action对指定记录执行增，删，改操作
	 * 
	 * @param personList
	 */
	public void personDataOperate(List<PersonBean> personList) {
		SQLiteDatabase database = null;
		long lNum = 0;
		long pNum = 0;
		Cursor cursor = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}
			// database.setLockingEnabled(false);
			database.beginTransaction();
			if (null == personList || personList.size() <= 0) {
				Log.i(TAG, "指定操作的记录为空");
			} else {
				for (int i = 0; i < personList.size(); i++) {
					PersonBean bean = personList.get(i);
					if ("I".equals(bean.getOperateType())) {
						if (!"".equals(bean.getGroupId())) {
							List<PersonBean> groupList = selectDepartment(bean
									.getGroupId());
							if (groupList.size() == 1) {
								// 当前部门记录已存在，则跳过本次插入操作
								continue;
							}
						} else if (!"".equals(bean.getUserId())) {
							List<PersonBean> partPersonList = selectPerson(bean
									.getUserId());
							if (partPersonList.size() == 1) {
								// 当前人员记录已存在，则跳过本次插入操作
								continue;
							}
						}
						// 执行插入操作
						String sortKeyName = "";
						String sortIndex = "";
						String key = UUID.randomUUID().toString()
								.replace("-", "");
						if (bean.getName() != null
								&& !"".equals(bean.getName().trim())) {
							// sortKeyName =
							// PingYinUtil.changeToPingYin(bean.getName(),
							// false) +
							// bean.getName() + bean.getMobile();
							// Log.e(TAG,
							// bean.getName()+"---------------------name");
							sortIndex = PingYinUtil.changeToPingYin(
									bean.getName(), false).substring(0, 1);
							// sortKeyName = sortIndex + bean.getName();

							sortKeyName = "";
							String strName = bean.getName();
							String[] strNameNum = new String[strName.length()];
							for (int j = 0; j < strName.length(); j++) {
								strNameNum[j] = strName.substring(j, j + 1);
							}
							for (int j = 0; j < strNameNum.length; j++) {
								sortKeyName = sortKeyName
										+ PingYinUtil.changeToPingYin(
												strNameNum[j], false)
										+ strNameNum[j];
							}
						}
						database.execSQL(
								"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,"
										+ "pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,"
										+ "pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,"
										+ "pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,"
										+ "pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable,"
										+ "pe_order_id,parent_person_id,pe_login_name ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
								new Object[] { key, bean.getParentId(),
										bean.getGroupId(), bean.getUserId(),
										bean.getText(), bean.getName(),
										bean.getNickName(), bean.getBirthday(),
										bean.getAvatar(), bean.getMobile(),
										bean.getHomePhone(),
										bean.getWorkPhone(), bean.getWorkFax(),
										bean.getSkype(), bean.getMsn(),
										bean.getQq(), bean.getUnitName(),
										bean.getEmail(), bean.getWorkEmail(),
										bean.getMobile(),
										bean.getUnitAddress(),
										bean.getHomeAddress(),
										bean.getRemark(),
										bean.getContactsName(),
										bean.getContactsId(),
										bean.getPersonType(), sortKeyName,
										sortIndex, Constants.userId,
										bean.getPost(), bean.getCreateTime(),
										bean.getEnable(), bean.getOrderId(),
										bean.getParentPersonId(),
										bean.getLoginName() });
					} else if ("U".equals(bean.getOperateType())) {
						// 执行更新操作
						if (!"".equals(bean.getGroupId())) {
							// 更新部门记录信息
							// 判断是否有该部门，如果不存在，则是新增操作
							// 获取数据库中的总记录数
							String strCountSql = "select count(*) from tb_person where pe_group_id=?";
							cursor = database.rawQuery(strCountSql,
									new String[] { bean.getGroupId() });
							if (null != cursor) {
								cursor.moveToFirst();
								lNum = cursor.getLong(0);
							}
							// 插入操作
							if (lNum == 0) {
								String sortKeyName = "";
								String sortIndex = "";
								String key = UUID.randomUUID().toString()
										.replace("-", "");
								if (bean.getName() != null
										&& !"".equals(bean.getName())) {
									// sortKeyName =
									// PingYinUtil.changeToPingYin(bean.getName(),
									// false) +
									// bean.getName() + bean.getMobile();
									sortIndex = PingYinUtil.changeToPingYin(
											bean.getName(), false).substring(0,
											1);
									// sortKeyName = sortIndex + bean.getName();

									sortKeyName = "";
									String strName = bean.getName();
									String[] strNameNum = new String[strName
											.length()];
									for (int j = 0; j < strName.length(); j++) {
										strNameNum[j] = strName.substring(j,
												j + 1);
									}
									for (int j = 0; j < strNameNum.length; j++) {
										sortKeyName = sortKeyName
												+ PingYinUtil.changeToPingYin(
														strNameNum[j], false)
												+ strNameNum[j];
									}
								}
								database.execSQL(
										"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,"
												+ "pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,"
												+ "pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,"
												+ "pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,"
												+ "pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable,"
												+ "pe_order_id,parent_person_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { key, bean.getParentId(),
												bean.getGroupId(),
												bean.getUserId(),
												bean.getText(), bean.getName(),
												bean.getNickName(),
												bean.getBirthday(),
												bean.getAvatar(),
												bean.getMobile(),
												bean.getHomePhone(),
												bean.getWorkPhone(),
												bean.getWorkFax(),
												bean.getSkype(), bean.getMsn(),
												bean.getQq(),
												bean.getUnitName(),
												bean.getEmail(),
												bean.getWorkEmail(),
												bean.getMobile(),
												bean.getUnitAddress(),
												bean.getHomeAddress(),
												bean.getRemark(),
												bean.getContactsName(),
												bean.getContactsId(),
												bean.getPersonType(),
												sortKeyName, sortIndex,
												Constants.userId,
												bean.getPost(),
												bean.getCreateTime(),
												bean.getEnable(),
												bean.getOrderId(),
												bean.getParentPersonId() });
							} else {
								// 更新操作
								StringBuilder strSql = new StringBuilder(
										"update tb_person set pe_group_id=?,pe_name=?,"
												+ "pe_parent_id=?,pe_enable=?,pe_order_id=?,parent_person_id=? where pe_group_id = ?");
								database.execSQL(
										strSql.toString(),
										new String[] { bean.getGroupId(),
												bean.getName(),
												bean.getParentId(),
												bean.getEnable(),
												bean.getOrderId(),
												bean.getParentPersonId(),
												bean.getGroupId() });
							}

						} else if (!"".equals(bean.getUserId())) {
							// 更新人员记录信息
							// 判断是否有该人员信息，如果不存在，则是新增操作
							// 获取数据库中的总记录数
							String strCountSql = "select count(*) from tb_person where pe_user_id=?";
							cursor = database.rawQuery(strCountSql,
									new String[] { bean.getUserId() });
							if (null != cursor) {
								cursor.moveToFirst();
								pNum = cursor.getLong(0);
							}
							// 搜索名字
							String sortKeyName = "";
							String sortIndex = "";
							if (bean.getName() != null
									&& !"".equals(bean.getName().trim())) {
								// sortKeyName =
								// PingYinUtil.changeToPingYin(bean.getName(),
								// false) +
								// bean.getName() + bean.getMobile();
								sortIndex = PingYinUtil.changeToPingYin(
										bean.getName(), false).substring(0, 1);
								// sortKeyName = sortIndex + bean.getName();

								sortKeyName = "";
								String strName = bean.getName();
								String[] strNameNum = new String[strName
										.length()];
								for (int j = 0; j < strName.length(); j++) {
									strNameNum[j] = strName.substring(j, j + 1);
								}
								for (int j = 0; j < strNameNum.length; j++) {
									sortKeyName = sortKeyName
											+ PingYinUtil.changeToPingYin(
													strNameNum[j], false)
											+ strNameNum[j];
								}
							}
							// 插入操作

							if (pNum == 0) {

								String key = UUID.randomUUID().toString()
										.replace("-", "");

								database.execSQL(
										"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,"
												+ "pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,"
												+ "pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,"
												+ "pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,"
												+ "pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable,"
												+ "pe_order_id,parent_person_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { key, bean.getParentId(),
												bean.getGroupId(),
												bean.getUserId(),
												bean.getText(), bean.getName(),
												bean.getNickName(),
												bean.getBirthday(),
												bean.getAvatar(),
												bean.getMobile(),
												bean.getHomePhone(),
												bean.getWorkPhone(),
												bean.getWorkFax(),
												bean.getSkype(), bean.getMsn(),
												bean.getQq(),
												bean.getUnitName(),
												bean.getEmail(),
												bean.getWorkEmail(),
												bean.getMobile(),
												bean.getUnitAddress(),
												bean.getHomeAddress(),
												bean.getRemark(),
												bean.getContactsName(),
												bean.getContactsId(),
												bean.getPersonType(),
												sortKeyName, sortIndex,
												Constants.userId,
												bean.getPost(),
												bean.getCreateTime(),
												bean.getEnable(),
												bean.getOrderId(),
												bean.getParentPersonId() });
							} else {
								Log.i(TAG,
										bean.getQq() + ",bean.getWorkPhone(),"
												+ bean.getEmail());
								StringBuilder strSql = new StringBuilder(
										"update tb_person set pe_parent_id=?,pe_post=?,"
												+ "pe_name=?,pe_user_id=?,pe_mobile=?,pe_mobile_email=?,pe_enable=?,pe_order_id=?,pe_qq=?,"
												+ "parent_person_id =?,pe_work_phone=?,pe_email=?,pe_sortkey=?,pe_sortIndex=?,pe_order_id=? where pe_user_id = ?");
								database.execSQL(
										strSql.toString(),
										new String[] { bean.getParentId(),
												bean.getPost(), bean.getName(),
												bean.getUserId(),
												bean.getMobile(),
												bean.getMobileEmail(),
												bean.getEnable(),
												bean.getOrderId(),
												bean.getQq(),
												bean.getParentPersonId(),
												bean.getWorkPhone(),
												bean.getEmail(), sortKeyName,
												sortIndex, bean.getOrderId(),
												bean.getUserId() });
							}
						} else {
							Log.i(TAG, "更新指定记录异常：部门Id为：" + bean.getGroupId()
									+ "  人员Id为：" + bean.getUserId());
						}
					} else if ("D".equals(bean.getOperateType())) {
						// 执行删除操作
						if (!"".equals(bean.getGroupId())) {
							// 删除部门记录信息
							StringBuilder strSql = new StringBuilder(
									"delete from tb_person where pe_group_id=?");
							database.execSQL(strSql.toString(),
									new String[] { bean.getGroupId() });
						} else if (!"".equals(bean.getUserId())) {
							// 删除人员记录信息
							StringBuilder strSql = new StringBuilder(
									"delete from tb_person where pe_user_id=?");
							database.execSQL(strSql.toString(),
									new String[] { bean.getUserId() });
						} else {
							Log.i(TAG, "删除指定记录异常：部门Id为：" + bean.getGroupId()
									+ "  人员Id为：" + bean.getUserId());
						}
					} else {
						Log.i(TAG, "当前记录的操作类型不清楚");
					}
				}
			}
			System.out.println("2222");
			database.setTransactionSuccessful();
			database.endTransaction();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.e(TAG, "对通讯录中联系人记录操作异常：" + e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != database) {
				// database();
			}
		}

	}

	/**
	 * 将指定联系人列表入库
	 * 
	 * @param personList
	 */
	public void insertPersonList(List<PersonBean> personList) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}
			sqLiteDatabase.beginTransaction();
			for (int i = 0; i < personList.size(); i++) {
				PersonBean bean = personList.get(i);
				if (!"".equals(bean.getGroupId())) {
					// 当前是部门，查询该部门是否已存在于表中
					List<PersonBean> groupList = selectDepartment(bean
							.getGroupId());
					if (groupList.size() == 1) {
						// 当前部门已存在，对该部门记录执行更新操作
						StringBuilder strSql = new StringBuilder(
								"update tb_person set pe_group_id=?,pe_name=?,pe_parent_id=?,"
										+ "pe_enable=? where pe_group_id = ?");
						sqLiteDatabase.execSQL(
								strSql.toString(),
								new String[] { bean.getGroupId(),
										bean.getName(), bean.getParentId(),
										bean.getEnable(), bean.getGroupId() });
					} else if (groupList.size() == 0) {
						// 当前部门不存在，对该部门记录执行插入操作
						String sortKeyName = "";
						String sortIndex = "";
						String key = UUID.randomUUID().toString()
								.replace("-", "");
						if (bean.getName() != null
								&& !"".equals(bean.getName())) {
							sortKeyName = PingYinUtil.changeToPingYin(
									bean.getName(), false)
									+ bean.getName() + bean.getMobile();
							sortIndex = sortKeyName.substring(0, 1);
						}
						sqLiteDatabase
								.execSQL(
										"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,pe_nick_name,pe_birthday,pe_avatar,pe_mobile,pe_home_phone,"
												+ "pe_work_phone,pe_work_fax,pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,pe_mobile_email,pe_unit_address,pe_home_address,pe_remark,"
												+ "pe_contacts_name,pe_contacts_id,pe_person_type,pe_sortkey,pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { key, bean.getParentId(),
												bean.getGroupId(),
												bean.getUserId(),
												bean.getText(), bean.getName(),
												bean.getNickName(),
												bean.getBirthday(),
												bean.getAvatar(),
												bean.getMobile(),
												bean.getHomePhone(),
												bean.getWorkPhone(),
												bean.getWorkFax(),
												bean.getSkype(), bean.getMsn(),
												bean.getQq(),
												bean.getUnitName(),
												bean.getEmail(),
												bean.getWorkEmail(),
												bean.getMobile(),
												bean.getUnitAddress(),
												bean.getHomeAddress(),
												bean.getRemark(),
												bean.getContactsName(),
												bean.getContactsId(),
												bean.getPersonType(),
												sortKeyName, sortIndex,
												Constants.userId,
												bean.getPost(),
												bean.getCreateTime(),
												bean.getEnable() });
					} else {
						Log.e(TAG, "当前部门记录数异常：" + groupList.size());
					}

				} else {

					// 当前是部门下的人员，查询该人员是否已存在于表中
					List<PersonBean> partPersonList = selectPerson(bean
							.getUserId());
					if (partPersonList.size() == 1) {

						Log.i(TAG, bean.getMobile());
						// 当前人员记录已存在，指定更新操作
						StringBuilder strSql = new StringBuilder(
								"update tb_person set pe_parent_id=?,pe_post=?,pe_avatar=?,pe_name=?,pe_qq=?,pe_user_id=?,pe_mobile=?,"
										+ "pe_mobile_email=?,pe_enable=? where pe_user_id = ?");
						sqLiteDatabase.execSQL(
								strSql.toString(),
								new String[] { bean.getParentId(),
										bean.getPost(), bean.getAvatar(),
										bean.getName(), bean.getUserId(),
										bean.getMobile(),
										bean.getMobileEmail(),
										bean.getEnable(), bean.getQq(),
										bean.getUserId() });
					} else if (partPersonList.size() == 0) {
						// 当前人员记录不存在，执行插入操作
						String sortKeyName = "";
						String sortIndex = "";
						String key = UUID.randomUUID().toString()
								.replace("-", "");
						if (bean.getName() != null
								&& !"".equals(bean.getName())) {
							sortKeyName = PingYinUtil.changeToPingYin(
									bean.getName(), false)
									+ bean.getName() + bean.getMobile();
							sortIndex = sortKeyName.substring(0, 1);
						}
						sqLiteDatabase
								.execSQL(
										"insert into tb_person(primary_id,pe_parent_id,pe_group_id,pe_user_id,pe_text,pe_name,pe_nick_name,pe_birthday,pe_avatar,"
												+ "pe_mobile,pe_home_phone,pe_work_phone,pe_work_fax,pe_skype,pe_msn,pe_qq,pe_unit_name,pe_email,pe_work_email,"
												+ "pe_mobile_email,pe_unit_address,pe_home_address,pe_remark,pe_contacts_name,pe_contacts_id,pe_person_type,pe_sortkey,"
												+ "pe_sortIndex,pe_login_id,pe_post,pe_create_time,pe_enable,pe_order_id,parent_person_id ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
										new Object[] { key, bean.getParentId(),
												bean.getGroupId(),
												bean.getUserId(),
												bean.getText(), bean.getName(),
												bean.getNickName(),
												bean.getBirthday(),
												bean.getAvatar(),
												bean.getMobile(),
												bean.getHomePhone(),
												bean.getWorkPhone(),
												bean.getWorkFax(),
												bean.getSkype(), bean.getMsn(),
												bean.getQq(),
												bean.getUnitName(),
												bean.getEmail(),
												bean.getWorkEmail(),
												bean.getMobile(),
												bean.getUnitAddress(),
												bean.getHomeAddress(),
												bean.getRemark(),
												bean.getContactsName(),
												bean.getContactsId(),
												bean.getPersonType(),
												sortKeyName, sortIndex,
												Constants.userId,
												bean.getPost(),
												bean.getCreateTime(),
												bean.getEnable(),
												bean.getOrderId(),
												bean.getParentPersonId() });
					} else {
						Log.e(TAG, "查询指定人员Id：" + bean.getUserId() + "的记录数异常："
								+ partPersonList.size());
					}
				}
			}
			sqLiteDatabase.setTransactionSuccessful();
			sqLiteDatabase.endTransaction();
		} catch (Exception e) {
			Log.e(TAG, "人员列表入库异常。。。。。。。");
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 树级查询联系人
	 * 
	 * @param nodeId
	 * @param isChildOrParent
	 *            0表示往下级查询，1表示往上查询
	 */
	public List<PersonBean> queryContactTreeList(String nodeId,
			int isChildOrParent) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		List<PersonBean> userList = new ArrayList<PersonBean>();
		List<PersonBean> deptList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		try {
			String sql = "";
			if (0 == isChildOrParent) {

				// 过滤人查询部门进行排序
				sql = "select * from tb_person where   pe_parent_id = ? and pe_group_id!=''  ORDER BY (pe_order_id+0)";
				cursor = database.rawQuery(sql.toString(),
						new String[] { nodeId });
				deptList = getPersonFromCursor(cursor);
				// 然后把人和部门放到一起
				personList.addAll(deptList);
				// 过滤部门查询人进行排序
				sql = "select * from tb_person where   pe_parent_id = ? and pe_group_id=''  ORDER BY CASE WHEN pe_order_id='' Then 1 Else 0 End, pe_order_id";
				cursor = database.rawQuery(sql.toString(),
						new String[] { nodeId });
				userList = getPersonFromCursor(cursor);
				personList.addAll(userList);
			} else if (1 == isChildOrParent) {
				// 过滤人查询部门进行排序
				sql = "select * from tb_person where pe_parent_id = (select pe_parent_id from tb_person where  pe_group_id = ?) and pe_group_id!='' ORDER BY  (pe_order_id+0)";
				cursor = database.rawQuery(sql.toString(),
						new String[] { nodeId });
				deptList = getPersonFromCursor(cursor);
				// 然后把人和部门放到一起
				personList.addAll(deptList);
				// 过滤部门查询人进行排序
				sql = "select * from tb_person where pe_parent_id = (select pe_parent_id from tb_person where  pe_group_id = ?) and pe_group_id='' ORDER BY CASE WHEN pe_order_id='' Then 1 Else 0 End, pe_order_id";
				cursor = database.rawQuery(sql.toString(),
						new String[] { nodeId });
				userList = getPersonFromCursor(cursor);
				personList.addAll(userList);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			// database.close();
		}
		return personList;

	}

	/**
	 * 查询选中的发送人
	 * 
	 * @param isPercolate
	 * @return
	 */
	public ArrayList<PersonBean> querySendNode() {
		ArrayList<PersonBean> list = new ArrayList<PersonBean>();
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		String sql = "select * from tb_person where pe_person_type = 1";
		Cursor cursor = database.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			PersonBean bean = new PersonBean();
			bean.setPrimaryId(cursor.getString(cursor
					.getColumnIndex("primary_id")));
			bean.setParentId(cursor.getString(cursor
					.getColumnIndex("pe_parent_id")));
			bean.setGroupId(cursor.getString(cursor
					.getColumnIndex("pe_group_id")));
			bean.setUserId(cursor.getString(cursor.getColumnIndex("pe_user_id")));
			bean.setName(cursor.getString(cursor.getColumnIndex("pe_name")));
			list.add(bean);
		}
		return list;
	}

	public String getLastTime(int type) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = null;
		StringBuilder sql = null;
		if (type == 0) {
			sql = new StringBuilder("select pe_create_time from tb_person   "
					+ " where pe_group_id !='' order by pe_create_time desc");
		} else {
			sql = new StringBuilder("select pe_create_time from tb_person "
					+ "where pe_group_id ='' order by pe_create_time desc");
		}

		cursor = database.rawQuery(sql.toString(), new String[] {});
		if (cursor == null) {
			return "";
		} else {
			if (cursor.moveToNext()) {
				return cursor
						.getString(cursor.getColumnIndex("pe_create_time"));
			} else {
				return "";
			}
		}
	}

	/**
	 * 查询数据库中联系人的总记录数
	 * 
	 * @return
	 */
	public long queryPersonSize() {
		long lNum = 0;
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;

		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			// 获取数据库中的总记录数
			String strSql = "select count(*) from tb_person";
			cursor = sqLiteDatabase.rawQuery(strSql, null);
			if (null != cursor) {
				cursor.moveToFirst();
				lNum = cursor.getLong(0);
			}

		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "获取数据库中联系人总记录数异常：" + e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}

		return lNum;
	}

	/**
	 * 查询所有联系人记录
	 * 
	 * @param queryKeyWord
	 * @return
	 */
	public List<PersonBean> queryAllPerson(String queryKeyWord) {
		List<PersonBean> personList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;

		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"select * from tb_person where pe_login_id = ? and pe_group_id ='' ");
			Log.i(TAG, "查询条件为：userId：" + Constants.userId + "  pe_sortkey ："
					+ queryKeyWord);
			if (!"".equals(queryKeyWord)) {
				sql.append("and pe_sortkey like ? order by pe_sortkey");
				cursor = sqLiteDatabase.rawQuery(sql.toString(), new String[] {
						Constants.userId, "%" + queryKeyWord + "%" });
			} else {
				sql.append("order by pe_sortkey");
				cursor = sqLiteDatabase.rawQuery(sql.toString(),
						new String[] { Constants.userId });
			}

			if (null != cursor) {
				personList = getPersonFromCursor(cursor);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询所有联系人记录异常：" + e.getMessage());
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}

		return personList;
	}

	/**
	 * 查询所有联系人 当数据量较大时使用分页查询
	 * 
	 * @return
	 */
	public List<PersonBean> queryAllPersonList(String queryKeyWord) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		long lLength = 0;
		try {
			// 获取数据库中的总记录数
			String strSql = "select count(*) from tb_person";
			cursor = database.rawQuery(strSql, null);
			if (null != cursor) {
				cursor.moveToFirst();
				lLength = cursor.getLong(0);
				cursor.close();
				cursor = null;
			}
			Log.i(TAG, "数据库中的总记录数为：" + lLength);

			// 若数据记录总数超过1000，则做分页查询，防止内存溢出
			if (lLength > 1000) {
				// 每次查询1000条记录，防止查询过多记录造成内存溢出
				for (int i = 0; i < lLength; i = i + 1000) {
					try {
						StringBuilder sql = new StringBuilder(
								"select pe_name,pe_mobile,pe_mobile from tb_person where pe_login_id = ? and pe_group_id ='' ");
						Log.i(TAG, "查询条件为：userId：" + Constants.userId
								+ "  pe_sortkey ：" + queryKeyWord);
						if (!"".equals(queryKeyWord)) {
							sql.append("and pe_sortkey like ? order by pe_sortkey limit 2000 offset "
									+ i);
							cursor = database.rawQuery(sql.toString(),
									new String[] { Constants.userId,
											"%" + queryKeyWord + "%" });
						} else {
							sql.append("order by pe_sortkey limit 2000 offset "
									+ i);
							cursor = database.rawQuery(sql.toString(),
									new String[] { Constants.userId });
						}
						if (null != cursor) {
							List<PersonBean> dataList = new ArrayList<PersonBean>();
							while (cursor.moveToNext()) {
								PersonBean bean = new PersonBean();
								bean.setName(cursor.getString(cursor
										.getColumnIndex("pe_name")));
								bean.setMobile(cursor.getString(cursor
										.getColumnIndex("pe_mobile")));

								dataList.add(bean);
							}
							if (null != dataList && dataList.size() > 0) {
								personList.addAll(dataList);
								dataList.clear();
							}
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e(TAG, "查询数据库中联系人记录异常：" + e.getMessage());
					} finally {
						if (null != cursor) {
							cursor.close();
						}
					}
				}
			} else {
				StringBuilder sql = new StringBuilder(
						"select * from tb_person where pe_group_id ='' ");
				if (!"".equals(queryKeyWord)) {
					sql.append("and pe_sortkey like ? order by pe_sortkey");
					cursor = database.rawQuery(sql.toString(),
							new String[] { "%" + queryKeyWord + "%" });
				}
				if (null != cursor) {
					personList = getPersonFromCursor(cursor);
					cursor.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "查询所有联系人异常：" + e.getMessage());
		} finally {

		}
		Log.i(TAG, "查询到的联系人记录总数为：" + personList.size());
		return personList;

	}

	/**
	 * 查询所有联系人关键字（手机号或者名字）
	 * 
	 * @return
	 */
	public List<PersonBean> queryPersonInfo(String queryKeyWord) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select * from tb_person where pe_group_id ='' ");
			if (!"".equals(queryKeyWord)) {
				sql.append("and (pe_name like ? or pe_mobile like ?) order by pe_sortkey");
				Log.i(TAG, "sql:" + sql.toString() + ",关键词:" + queryKeyWord);
				cursor = database.rawQuery(sql.toString(), new String[] {
						"%" + queryKeyWord + "%", "%" + queryKeyWord + "%" });
			}
			if (null != cursor) {
				personList = getPersonFromCursor(cursor);
				cursor.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "查询所有联系人异常：" + e.getMessage());
		} finally {

		}
		Log.i(TAG, "查询到的联系人记录总数为：" + personList.size());
		return personList;

	}

	/**
	 * 查询有头像的用户
	 * 
	 * @return
	 */
	public List<PersonBean> queryAllPersonListByAvatar() {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		long lLength = 0;

		try {
			StringBuilder sql = new StringBuilder(
					"select pe_avatar from tb_person where pe_avatar is not null and  pe_avatar !='' ");

			cursor = database.rawQuery(sql.toString(), null);
			if (null != cursor) {
				List<PersonBean> dataList = new ArrayList<PersonBean>();
				while (cursor.moveToNext()) {
					PersonBean bean = new PersonBean();
					bean.setAvatar(cursor.getString(cursor
							.getColumnIndex("pe_avatar")));

					dataList.add(bean);
				}
				if (null != dataList && dataList.size() > 0) {
					personList.addAll(dataList);
					dataList.clear();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询数据库中联系人头像记录异常：" + e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
			}
		}
		return personList;

	}

	/**
	 * 查询指定数量的联系人记录
	 * 
	 * @param queryKeyWord
	 *            查询关键字
	 * @param iNumber
	 *            需要查询出来的记录数
	 * @param iPosition
	 *            查询的起始位置
	 * @return
	 */
	public List<PersonBean> queryPersonByNum(String queryKeyWord, int iNumber,
			int iPosition) {
		List<PersonBean> personList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;

		if (null == queryKeyWord) {
			return personList;
		}

		if (iNumber < 0 || iPosition < 0) {
			return personList;
		}

		try {
			if (null != dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder strSql = new StringBuilder(
					"select * from tb_person where pe_group_id =''");
			Log.i(TAG, "查询条件为：userId：" + Constants.userId + "  pe_sortkey ："
					+ queryKeyWord);
			if (!"".equals(queryKeyWord)) {
				strSql.append("and pe_sortkey like ? order by pe_sortkey limit "
						+ iNumber + " offset " + iPosition);
				cursor = sqLiteDatabase.rawQuery(strSql.toString(),
						new String[] {});
			} else {
				strSql.append("order by pe_sortkey limit " + iNumber
						+ " offset " + iPosition);
				cursor = sqLiteDatabase.rawQuery(strSql.toString(),
						new String[] {});
			}
			if (null != cursor) {
				personList = getPersonFromCursor(cursor);
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询指定数量的联系人记录异常：" + e.getMessage());
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}

		return personList;
	}

	/**
	 * 查询指定groupId的部门记录
	 * 
	 * @param strGroupId
	 * @return
	 */
	public List<PersonBean> selectDepartment(String strGroupId) {
		List<PersonBean> groupList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}
			StringBuilder strSql = new StringBuilder(
					"select * from tb_person where pe_group_id=?");
			cursor = sqLiteDatabase.rawQuery(strSql.toString(),
					new String[] { strGroupId });
			groupList = getPersonFromCursor(cursor);
			return groupList;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询指定GroupId：" + strGroupId + "的部门记录异常。。。。");
			return groupList;
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 根据指定的人员Id查询表中的人员
	 * 
	 * @param strPersonId
	 * @return
	 */
	public List<PersonBean> selectPerson(String strPersonId) {
		List<PersonBean> personList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder strSql = new StringBuilder(
					"select * from tb_person where pe_user_id=?");
			cursor = sqLiteDatabase.rawQuery(strSql.toString(),
					new String[] { strPersonId });
			personList = getPersonFromCursor(cursor);
			return personList;
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询指定personId的人员记录异常：" + strPersonId);
			return personList;
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 查询关注的联系人
	 * 
	 * @return
	 */
	public List<PersonBean> queryAttentionPersonList() {
		List<PersonBean> personList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;
		try {
			if (dbOpenHelper == null) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"select * from tb_person where pe_skype = '1' order by pe_sortIndex");
			cursor = sqLiteDatabase.rawQuery(sql.toString(), null);
			personList = getPersonFromCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
			// database.close();
		}
		return personList;

	}

	/**
	 * 根据主键id联系人
	 * 
	 * @return
	 */
	public PersonBean queryPersonById(String id) {
		SQLiteDatabase database = null;
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"select * from tb_person where primary_id =?");
			cursor = database.rawQuery(sql.toString(), new String[] { id });
			personList = getPersonFromCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (null != cursor) {
				cursor.close();
			}
			if (null != database) {
				// database.close();
			}
			// database.close();
		}
		return personList.get(0);

	}

	/**
	 * 根据人员登录名查询信息
	 * 
	 * @return
	 */
	public PersonBean queryPersonByUserLogin(String loginName) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select * from tb_person where pe_login_name = ?");
			cursor = database.rawQuery(sql.toString(),
					new String[] { loginName });
			personList = getPersonFromCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			// database.close();
		}
		if (personList != null && !personList.isEmpty()) {
			return personList.get(0);
		} else {
			return new PersonBean();
		}

	}

	/**
	 * 根据人员id查询信息
	 * 
	 * @return
	 */
	public PersonBean queryPersonByUserId(String userId) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		List<PersonBean> personList = new ArrayList<PersonBean>();
		Cursor cursor = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select * from tb_person where pe_user_id = ?");
			cursor = database.rawQuery(sql.toString(), new String[] { userId });
			personList = getPersonFromCursor(cursor);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			// database.close();
		}
		if (personList != null && !personList.isEmpty()) {
			return personList.get(0);
		} else {
			return new PersonBean();
		}
	}

	public String selectTextByParentId(String parentId) {
		SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
		Cursor cursor = null;
		String text = null;
		try {
			StringBuilder sql = new StringBuilder(
					"select*from tb_person where pe_group_id = ?");
			cursor = database.rawQuery(sql.toString(),
					new String[] { parentId });
			while (cursor.moveToNext()) {
				text = cursor.getString(cursor.getColumnIndex("pe_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cursor.close();
			// database.close();
		}
		return text;

	}

	public List<PersonBean> getPersonFromCursor(Cursor cursor) {
		List<PersonBean> list = new ArrayList<PersonBean>();
		if (null == cursor) {
			return list;
		}
		while (cursor.moveToNext()) {
			PersonBean bean = new PersonBean();
			bean.setPrimaryId(cursor.getString(cursor
					.getColumnIndex("primary_id")));
			bean.setParentId(cursor.getString(cursor
					.getColumnIndex("pe_parent_id")));
			bean.setGroupId(cursor.getString(cursor
					.getColumnIndex("pe_group_id")));
			bean.setUserId(cursor.getString(cursor.getColumnIndex("pe_user_id")));
			bean.setText(selectTextByParentId(cursor.getString(cursor
					.getColumnIndex("pe_parent_id"))));
			bean.setName(cursor.getString(cursor.getColumnIndex("pe_name")));
			bean.setNickName(cursor.getString(cursor
					.getColumnIndex("pe_nick_name")));
			bean.setBirthday(cursor.getString(cursor
					.getColumnIndex("pe_birthday")));
			bean.setAvatar(cursor.getString(cursor.getColumnIndex("pe_avatar")));
			bean.setMobile(cursor.getString(cursor.getColumnIndex("pe_mobile")));
			bean.setHomePhone(cursor.getString(cursor
					.getColumnIndex("pe_home_phone")));
			bean.setWorkPhone(cursor.getString(cursor
					.getColumnIndex("pe_work_phone")));
			bean.setWorkFax(cursor.getString(cursor
					.getColumnIndex("pe_work_fax")));
			bean.setSkype(cursor.getString(cursor.getColumnIndex("pe_skype")));
			bean.setMsn(cursor.getString(cursor.getColumnIndex("pe_msn")));
			bean.setQq(cursor.getString(cursor.getColumnIndex("pe_qq")));
			bean.setUnitName(cursor.getString(cursor
					.getColumnIndex("pe_unit_name")));
			bean.setEmail(cursor.getString(cursor.getColumnIndex("pe_email")));
			bean.setWorkEmail(cursor.getString(cursor
					.getColumnIndex("pe_work_email")));
			bean.setMobileEmail(cursor.getString(cursor
					.getColumnIndex("pe_mobile_email")));
			bean.setUnitAddress(cursor.getString(cursor
					.getColumnIndex("pe_unit_address")));
			bean.setHomeAddress(cursor.getString(cursor
					.getColumnIndex("pe_home_address")));
			bean.setRemark(cursor.getString(cursor.getColumnIndex("pe_remark")));
			bean.setContactsName(cursor.getString(cursor
					.getColumnIndex("pe_contacts_name")));
			bean.setContactsId(cursor.getString(cursor
					.getColumnIndex("pe_contacts_id")));
			bean.setSortIndex(cursor.getString(cursor
					.getColumnIndex("pe_sortIndex")));
			bean.setPersonType(cursor.getString(cursor
					.getColumnIndex("pe_person_type")));
			bean.setPost(cursor.getString(cursor.getColumnIndex("pe_post")));
			bean.setEnable(cursor.getString(cursor.getColumnIndex("pe_enable")));
			bean.setLoginName(cursor.getString(cursor
					.getColumnIndex("pe_login_name")));
			list.add(bean);
		}
		return list;
	}

	/**
	 * 标注为关注
	 */
	public void setAttentionPerson(String isAttention, String primaryId) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"update tb_person set pe_skype = ? where primary_id = ?");
			sqLiteDatabase.execSQL(sql.toString(), new String[] { isAttention,
					primaryId });
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "标注联系人为关注或取消关注时异常。。。。" + e.getMessage());
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}

	}

	/**
	 * 更新个人头像
	 */
	public void updateUserPic(String imagePath) {
		SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
		if (null != database) {
			try {
				StringBuilder sql = new StringBuilder(
						"update tb_person set pe_avatar = ? where pe_user_id = ?");
				database.execSQL(sql.toString(), new String[] { imagePath,
						Constants.userId });
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				// database.close();
			}
		}
	}

	/**
	 * 刷新节点状态
	 * 
	 * @param bean
	 * @param isCheck
	 *            pe_person_type 为0或是空时 表示未选中 1时表示选中
	 * @param updateNodes
	 *            需要更新的节点
	 */
	public void changeNodeState(ArrayList<String> updateNodes, String isCheck) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			sqLiteDatabase.beginTransaction();
			StringBuilder sql = new StringBuilder(
					"update tb_person set pe_person_type = ? where primary_id = ?");
			for (int i = 0; i < updateNodes.size(); i++) {
				sqLiteDatabase.execSQL(sql.toString(), new Object[] { isCheck,
						updateNodes.get(i) });
			}
			sqLiteDatabase.setTransactionSuccessful();
			sqLiteDatabase.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "刷新节点状态异常：" + e.getMessage());
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}

	}

	/**
	 * 更新指定Id记录的选中状态
	 * 
	 * @param strPrimaryId
	 * @param strSelect
	 *            0表示未选中，1表示选中
	 */
	public void updatePersonSelect(String strPrimaryId, String strSelect) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			String strSql = "update tb_person set pe_person_type = ? where primary_id = ?";
			sqLiteDatabase.execSQL(strSql, new String[] { strSelect,
					strPrimaryId });
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "更新指定记录：" + strPrimaryId + "的选中状态失败。。。。。");
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}
	}

	/**
	 * 更新个人信息
	 */
	public void updatePersonInfo(PersonBean personBean) {
		SQLiteDatabase sqLiteDatabase = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"update tb_person set pe_work_phone = ?,pe_mobile = ?,pe_email = ? ,pe_qq=? where primary_id = ?");
			sqLiteDatabase.execSQL(
					sql.toString(),
					new String[] { personBean.getWorkPhone(),
							personBean.getMobile(), personBean.getEmail(),
							personBean.getQq(), personBean.getPrimaryId() });
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "更新个人信息异常：" + e.getMessage());
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
		}

	}

	/**
	 * 查询分组联系人
	 * 
	 * @param queryKeyWord
	 *            分组id
	 * @return
	 */
	public List<PersonBean> queryPersonByGroup(String groupId) {
		Log.i(TAG, "群组ID" + groupId);
		List<PersonBean> personList = new ArrayList<PersonBean>();
		SQLiteDatabase sqLiteDatabase = null;
		Cursor cursor = null;

		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			} else {
				sqLiteDatabase = dbOpenHelper.getWritableDatabase();
			}

			StringBuilder sql = new StringBuilder(
					"select p.* from  tb_group_person as g left join tb_person as p on p.primary_id=g.person_id where g.group_id = ? order by p.pe_sortkey");
			Log.i(TAG, "sql" + sql.toString() + groupId);
			cursor = sqLiteDatabase.rawQuery(sql.toString(),
					new String[] { groupId });

			if (null != cursor) {
				personList = getPersonFromCursor(cursor);
				Log.i(TAG, "群组成员数" + personList.size());
			}
		} catch (Exception e) {
			// TODO: handle exception
			Log.e(TAG, "查询分组联系人记录异常：" + e.getMessage());
		} finally {
			if (null != sqLiteDatabase) {
				// sqLiteDatabase.close();
			}
			if (null != cursor) {
				cursor.close();
			}
		}

		return personList;
	}

	/**
	 * 保存群组人员
	 * 
	 * @param groupId
	 * 
	 * @return
	 */
	public boolean saveGroupByPerson(String groupId, String personId) {
		SQLiteDatabase database = null;
		Cursor cursor = null;
		long lNum = 0;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}
			// 检验是否已经存在,存在返回false
			StringBuilder sql = new StringBuilder(
					"select count(*) from tb_group_person where person_id=?");
			if (null != groupId && null != personId) {
				cursor = database.rawQuery(sql.toString(),
						new String[] { personId });
			}
			if (null != cursor) {
				cursor.moveToFirst();
				lNum = cursor.getLong(0);
			}
			// 已经存在记录
			if (lNum == 1) {
				return false;
			}
			database.setLockingEnabled(false);
			database.beginTransaction();
			// database.execSQL("delete from tb_person", new Object[] {});
			String key = UUID.randomUUID().toString().replace("-", "");
			database.execSQL(
					"insert into tb_group_person(primary_id,group_id,person_id ) values(?,?,?)",
					new Object[] { key, groupId, personId });
			database.setTransactionSuccessful();
			database.endTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "插入分组联系人记录异常：" + e.getMessage());
		} finally {
			if (null != database) {
				// database.close();
			}
		}
		return true;
	}

	/**
	 * 删除群组人员
	 * 
	 * @param groupId
	 * 
	 * @return
	 */
	public boolean deleteGroupByPerson(String groupId, String personId) {
		SQLiteDatabase database = null;
		try {
			if (null == dbOpenHelper) {
				dbOpenHelper = DatabaseHelper.getInstance(context);
				database = dbOpenHelper.getWritableDatabase();
			} else {
				database = dbOpenHelper.getWritableDatabase();
			}

			// 删除分组成员记录

			database.execSQL(
					"delete from tb_group_person where person_id=? and group_id=?",
					new String[] { personId, groupId });
			// database.setTransactionSuccessful();
			// database.endTransaction();

		} catch (Exception e) {
			e.printStackTrace();
			Log.e(TAG, "删除分组联系人记录异常：" + e.getMessage());
		} finally {
			if (null != database) {
				// database.close();
			}
		}
		return true;
	}

}
