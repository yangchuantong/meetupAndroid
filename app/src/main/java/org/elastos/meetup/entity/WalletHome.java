package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 新闻
 *
 */
public class WalletHome implements Serializable{
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static WalletHome walletHome;
	private static ArrayList<WalletHome> walletHomeList;
	private String logo,name,balance,tokenAddress;
	private double worth;
	public static WalletHome getInstance(){
		if(walletHome ==null){
			walletHome = new WalletHome();
		}
		return walletHome;
	}

	public static ArrayList<WalletHome> getWalletHomeList() {
		return walletHomeList;
	}

	public static void setWalletHomeList(ArrayList<WalletHome> walletHomeList) {
		WalletHome.walletHomeList = walletHomeList;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getTokenAddress() {
		return tokenAddress;
	}

	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}

	public double getWorth() {
		return worth;
	}

	public void setWorth(double worth) {
		this.worth = worth;
	}
}
