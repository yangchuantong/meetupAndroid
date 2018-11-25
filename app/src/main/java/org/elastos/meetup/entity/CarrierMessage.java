package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 新闻
 *
 */
public class CarrierMessage implements Serializable{
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static CarrierMessage carrierMessage;
	private static ArrayList<CarrierMessage> list;
	private String c_from,c_to,c_message;
	private long send_time;
	public static CarrierMessage getInstance(){
		if(carrierMessage ==null){
			carrierMessage = new CarrierMessage();
		}
		return carrierMessage;
	}

	public static ArrayList<CarrierMessage> getList() {
		return list;
	}

	public static void setList(ArrayList<CarrierMessage> list) {
		CarrierMessage.list = list;
	}

	public String getC_from() {
		return c_from;
	}

	public void setC_from(String c_from) {
		this.c_from = c_from;
	}

	public String getC_to() {
		return c_to;
	}

	public void setC_to(String c_to) {
		this.c_to = c_to;
	}

	public String getC_message() {
		return c_message;
	}

	public void setC_message(String c_message) {
		this.c_message = c_message;
	}

	public long getSend_time() {
		return send_time;
	}

	public void setSend_time(long send_time) {
		this.send_time = send_time;
	}
}
