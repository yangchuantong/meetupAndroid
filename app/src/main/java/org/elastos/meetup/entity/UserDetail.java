package org.elastos.meetup.entity;

import java.io.Serializable;


/**
 * @author zhouxianxian 新闻
 *
 */
public class UserDetail implements Serializable{
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static UserDetail accounts;
	private String name,company,job,email,mobile,remark;
	public static UserDetail getInstance(){
		if(accounts==null){
			accounts = new UserDetail();
		}
		return accounts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
