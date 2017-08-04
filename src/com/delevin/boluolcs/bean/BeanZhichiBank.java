package com.delevin.boluolcs.bean;

public class BeanZhichiBank {
	private String name;
	private String url;
	private String single_amt;
	private String day_amt;

	public BeanZhichiBank(String name, String url, String single_amt,
			String day_amt) {
		super();
		this.name = name;
		this.url = url;
		this.single_amt = single_amt;
		this.day_amt = day_amt;
	}

	public BeanZhichiBank() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSingle_amt() {
		return single_amt;
	}

	public void setSingle_amt(String single_amt) {
		this.single_amt = single_amt;
	}

	public String getDay_amt() {
		return day_amt;
	}

	public void setDay_amt(String day_amt) {
		this.day_amt = day_amt;
	}

	@Override
	public String toString() {
		return "ZhichiBank [name=" + name + ", url=" + url + ", single_amt="
				+ single_amt + ", day_amt=" + day_amt + "]";
	}

}
