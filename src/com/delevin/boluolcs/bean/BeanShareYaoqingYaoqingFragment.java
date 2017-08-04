package com.delevin.boluolcs.bean;

public class BeanShareYaoqingYaoqingFragment {
	private String name;
	private String phone;
	private String reg_time;
	private int sign;

	public BeanShareYaoqingYaoqingFragment() {

		super();

	}

	public BeanShareYaoqingYaoqingFragment(String name, String phone,
			String reg_time, int sign) {
		super();
		this.name = name;
		this.phone = phone;
		this.reg_time = reg_time;
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "FanLiBean [name=" + name + ", phone=" + phone + ", reg_time="
				+ reg_time + ", sign=" + sign + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getReg_time() {
		return reg_time;
	}

	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}
}
