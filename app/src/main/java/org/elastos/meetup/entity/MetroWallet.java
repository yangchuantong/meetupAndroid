package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 资产余额 老版本
 *
 */
public class MetroWallet implements Serializable {
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static MetroWallet metroWallet;
	private static ArrayList<MetroWallet> metroWalletList;
	private String logo,name,balance,tokenAddress;
	private double worth;
	private double ethbalance;
	public static MetroWallet getInstance(){
		if(metroWallet==null){
			metroWallet = new MetroWallet();
		}
		return metroWallet;
	}

	public static ArrayList<MetroWallet> getMetroWalletList() {
		return metroWalletList;
	}

	public static void setMetroWalletList(ArrayList<MetroWallet> metroWalletList) {
		MetroWallet.metroWalletList = metroWalletList;
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

	public double getEthbalance() {
		return ethbalance;
	}

	public void setEthbalance(double ethbalance) {
		this.ethbalance = ethbalance;
	}
}
