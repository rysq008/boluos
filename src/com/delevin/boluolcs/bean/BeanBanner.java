package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2016-12-16 下午1:43:29    类说明 
 */

public class BeanBanner {

	private String img; // 图片
	private String url; // html地址
	private String type;
	private String can_link;

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCan_link() {
		return can_link;
	}

	public void setCan_link(String can_link) {
		this.can_link = can_link;
	}

	public BeanBanner() {
		super();
	}

	@Override
	public String toString() {
		return "BannerData [img=" + img + ", url=" + url + ", can_link="
				+ can_link + "]";
	}

	public BeanBanner(String img, String url, String type, String can_link) {
		super();
		this.img = img;
		this.url = url;
		this.type = type;
		this.can_link = can_link;
	}

}
