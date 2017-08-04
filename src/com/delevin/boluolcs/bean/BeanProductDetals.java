package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2016-12-29 上午9:55:07    类说明 
 */

public class BeanProductDetals {

	private String rate_increase;
	private double total_mount;
	private String percentage;
	private String raise_limit;
	private String product_name;
	private String product_type;
	private String product_status;
	private String product_invest;
	private String limit_mount;
	private String rate;
	private String remain_balance;
	private String time_limit;
	private String product_image;
	private String product_detail;
	private String sell_time;
	private String repay_type;
	private double remain_money;

	public BeanProductDetals() {
		super();
	}

	public BeanProductDetals(String rate_increase, double total_mount,
			String percentage, String raise_limit, String product_name,
			String product_type, String product_status, String product_invest,
			String limit_mount, String rate, String remain_balance,
			String time_limit, String product_image, String product_detail,
			String sell_time, String repay_type, double remain_money) {
		super();
		this.rate_increase = rate_increase;
		this.total_mount = total_mount;
		this.percentage = percentage;
		this.raise_limit = raise_limit;
		this.product_name = product_name;
		this.product_type = product_type;
		this.product_status = product_status;
		this.product_invest = product_invest;
		this.limit_mount = limit_mount;
		this.rate = rate;
		this.remain_balance = remain_balance;
		this.time_limit = time_limit;
		this.product_image = product_image;
		this.product_detail = product_detail;
		this.sell_time = sell_time;
		this.repay_type = repay_type;
		this.remain_money = remain_money;
	}

	public String getRate_increase() {
		return rate_increase;
	}

	public void setRate_increase(String rate_increase) {
		this.rate_increase = rate_increase;
	}

	public double getTotal_mount() {
		return total_mount;
	}

	public void setTotal_mount(double total_mount) {
		this.total_mount = total_mount;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getRaise_limit() {
		return raise_limit;
	}

	public void setRaise_limit(String raise_limit) {
		this.raise_limit = raise_limit;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getProduct_status() {
		return product_status;
	}

	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public String getProduct_invest() {
		return product_invest;
	}

	public void setProduct_invest(String product_invest) {
		this.product_invest = product_invest;
	}

	public String getLimit_mount() {
		return limit_mount;
	}

	public void setLimit_mount(String limit_mount) {
		this.limit_mount = limit_mount;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRemain_balance() {
		return remain_balance;
	}

	public void setRemain_balance(String remain_balance) {
		this.remain_balance = remain_balance;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getProduct_image() {
		return product_image;
	}

	public void setProduct_image(String product_image) {
		this.product_image = product_image;
	}

	public String getProduct_detail() {
		return product_detail;
	}

	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}

	public String getSell_time() {
		return sell_time;
	}

	public void setSell_time(String sell_time) {
		this.sell_time = sell_time;
	}

	public String getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}

	public double getRemain_money() {
		return remain_money;
	}

	public void setRemain_money(double remain_money) {
		this.remain_money = remain_money;
	}

	@Override
	public String toString() {
		return "BeanProductDetals [rate_increase=" + rate_increase
				+ ", total_mount=" + total_mount + ", percentage=" + percentage
				+ ", raise_limit=" + raise_limit + ", product_name="
				+ product_name + ", product_type=" + product_type
				+ ", product_status=" + product_status + ", product_invest="
				+ product_invest + ", limit_mount=" + limit_mount + ", rate="
				+ rate + ", remain_balance=" + remain_balance + ", time_limit="
				+ time_limit + ", product_image=" + product_image
				+ ", product_detail=" + product_detail + ", sell_time="
				+ sell_time + ", repay_type=" + repay_type + ", remain_money="
				+ remain_money + "]";
	}
}
