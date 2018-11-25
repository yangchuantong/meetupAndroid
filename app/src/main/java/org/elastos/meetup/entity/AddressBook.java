package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 地址薄
 *
 */
public class AddressBook implements Serializable {
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static AddressBook addressBook;
	private static ArrayList<AddressBook> addressbookList;
	private String name,address,mobile,email,remark,linkType;
	public static AddressBook getInstance(){
		if(addressBook ==null){
			addressBook = new AddressBook();
		}
		return addressBook;
	}

	public static ArrayList<AddressBook> getAddressbookList() {
		return addressbookList;
	}

	public static void setAddressbookList(ArrayList<AddressBook> addressbookList) {
		AddressBook.addressbookList = addressbookList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
}
