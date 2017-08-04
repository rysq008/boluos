package com.delevin.boluolcs.bean;

/**
 * @author 李红涛 E-mail:
 * @version 创建时间：2017-3-7 上午9:55:51 类说明
 */
public class BeanMeiTi {
	private String image;
	private String posttime;
	private String title;
	private String url;

	public BeanMeiTi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanMeiTi(String image, String posttime, String title, String url) {
		super();
		this.image = image;
		this.posttime = posttime;
		this.title = title;
		this.url = url;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

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
		return "BeanMeiTi [image=" + image + ", posttime=" + posttime
				+ ", title=" + title + ", url=" + url + "]";
	}
}
