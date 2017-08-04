package com.delevin.boluolcs.bean;

public class NoticeList {
	private String posttime;
	private String title;
	private String url;
	public String getPosttime() {
		return posttime;
	}
	public void setPosttime(String posttime) {
		this.posttime = posttime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "NoticeList [posttime=" + posttime + ", title=" + title
				+ ", url=" + url + "]";
	}
	public NoticeList(String posttime, String title, String url) {
		super();
		this.posttime = posttime;
		this.title = title;
		this.url = url;
	}
	public NoticeList() {
		super();
		// TODO Auto-generated constructor stub
	}

}
