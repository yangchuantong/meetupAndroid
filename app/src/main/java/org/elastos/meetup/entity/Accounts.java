package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 新闻
 *
 */
public class Accounts implements Serializable{
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static Accounts accounts;
	private static ArrayList<Accounts> accountsList;
	private String address,walletname,mnemonic,pwdnote,balance,balancejson,masterWallet,linkType;
	public static Accounts getInstance(){
		if(accounts==null){
			accounts = new Accounts();
		}
		return accounts;
	}



	public static Accounts getAccounts() {
		return accounts;
	}

	public static void setAccounts(Accounts accounts) {
		Accounts.accounts = accounts;
	}

	public static ArrayList<Accounts> getAccountsList() {
		return accountsList;
	}

	public static void setAccountsList(ArrayList<Accounts> accountsList) {
		Accounts.accountsList = accountsList;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWalletname() {
		return walletname;
	}

	public void setWalletname(String walletname) {
		this.walletname = walletname;
	}

	public String getMnemonic() {
		return mnemonic;
	}

	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	public String getPwdnote() {
		return pwdnote;
	}

	public void setPwdnote(String pwdnote) {
		this.pwdnote = pwdnote;
	}

	public String getBalancejson() {
		return balancejson;
	}

	public void setBalancejson(String balancejson) {
		this.balancejson = balancejson;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getMasterWallet() {
		return masterWallet;
	}

	public void setMasterWallet(String masterWallet) {
		this.masterWallet = masterWallet;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}
}
