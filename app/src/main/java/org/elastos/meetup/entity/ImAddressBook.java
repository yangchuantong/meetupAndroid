package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 新闻
 *
 */
public class ImAddressBook implements Serializable {


	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static ImAddressBook imAddressBook;
	private static ArrayList<ImAddressBook> addressbookList;
	private String c_id, remark;
	private int accept;

	public static ImAddressBook getInstance() {
		if (imAddressBook == null) {
			imAddressBook = new ImAddressBook();
		}
		return imAddressBook;
	}

	public static ArrayList<ImAddressBook> getAddressbookList() {
		return addressbookList;
	}

	public static void setAddressbookList(ArrayList<ImAddressBook> addressbookList) {
		ImAddressBook.addressbookList = addressbookList;
	}

	public static ImAddressBook getImAddressBook() {
		return imAddressBook;
	}

	public static void setImAddressBook(ImAddressBook imAddressBook) {
		ImAddressBook.imAddressBook = imAddressBook;
	}

	public String getC_id() {
		return c_id;
	}

	public void setC_id(String c_id) {
		this.c_id = c_id;
	}

	public int getAccept() {
		return accept;
	}

	public void setAccept(int accept) {
		this.accept = accept;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}