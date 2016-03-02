package com.xguanjia.hx.login.bean;

import java.io.Serializable;

public class LoginBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String depname="";//企业名称
	private String userName="";//姓名
	private String mobile="";//手机
	private String deptName="";//部门
	private String email="";//email
	private String headImg="";//头像
	private String isModifyPwd="";//0 不需要修改密码 1  需要修改密码 （注：演示账户无需进行判断）
	public String getDepname() {
		return depname;
	}
	
	
	public String getIsModifyPwd() {
		return isModifyPwd;
	}


	public void setIsModifyPwd(String isModifyPwd) {
		this.isModifyPwd = isModifyPwd;
	}


	public void setDepname(String depname) {
		this.depname = depname;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	

}
