package com.delevin.boluolcs.bean;

public class BeanNotice {
	private String url;
	private String title;

	public BeanNotice() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanNotice(String url, String title) {
		super();
		this.url = url;
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "BeanNotice [url=" + url + ", title=" + title + "]";
	}

}
