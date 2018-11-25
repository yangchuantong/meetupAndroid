package org.elastos.meetup.entity;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * @author zhouxianxian 新闻
 *
 */
public class ImMessage implements Serializable {
	

	/**
	 * 为了去掉警告 serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private static ImMessage imMessage;
	private static ArrayList<ImMessage> imMessageList;
	private int code;
	private String text,url;
	private ArrayList<ArticleList> list;
	private Function function;
	private boolean fromtuling=true;

	public static ImMessage getInstance(){
		if(imMessage ==null){
			imMessage = new ImMessage();
		}
		return imMessage;
	}

	public static ImMessage getImMessage() {
		return imMessage;
	}

	public static void setImMessage(ImMessage imMessage) {
		ImMessage.imMessage = imMessage;
	}

	public static ArrayList<ImMessage> getImMessageList() {
		return imMessageList;
	}

	public static void setImMessageList(ArrayList<ImMessage> imMessageList) {
		ImMessage.imMessageList = imMessageList;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<ArticleList> getList() {
		return list;
	}

	public void setList(ArrayList<ArticleList> list) {
		this.list = list;
	}

	public boolean isFromtuling() {
		return fromtuling;
	}

	public void setFromtuling(boolean fromtuling) {
		this.fromtuling = fromtuling;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public class ArticleList{
	private String article,source,icon,detailurl,name,info;

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDetailurl() {
		return detailurl;
	}

	public void setDetailurl(String detailurl) {
		this.detailurl = detailurl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
	private class Function{
		private String song,singer,author,name;

		public String getSong() {
			return song;
		}

		public void setSong(String song) {
			this.song = song;
		}

		public String getSinger() {
			return singer;
		}

		public void setSinger(String singer) {
			this.singer = singer;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
