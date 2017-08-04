package com.delevin.boluolcs.bean;

public class BeanShareYaoqingFanliFragment {
	private String money;
	private String time;
	private String phone;
	private String invite_profit;
	private String interest_time;
	private String time_limit;
	private String name;

	public BeanShareYaoqingFanliFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanShareYaoqingFanliFragment(String interest_time,
			String invite_profit, String money, String name, String phone,
			String time, String time_limit) {
		super();
		this.interest_time = interest_time;
		this.invite_profit = invite_profit;
		this.money = money;
		this.name = name;
		this.phone = phone;
		this.time = time;
		this.time_limit = time_limit;
	}

	@Override
	public String toString() {
		return "YanQingBean [interest_time=" + interest_time
				+ ", invite_profit=" + invite_profit + ", money=" + money
				+ ", name=" + name + ", phone=" + phone + ", time=" + time
				+ ", time_limit=" + time_limit + "]";
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getInterest_time() {
		return interest_time;
	}

	public void setInterest_time(String interest_time) {
		this.interest_time = interest_time;
	}

	public String getInvite_profit() {
		return invite_profit;
	}

	public void setInvite_profit(String invite_profit) {
		this.invite_profit = invite_profit;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
