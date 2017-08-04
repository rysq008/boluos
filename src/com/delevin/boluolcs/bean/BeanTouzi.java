package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2016-4-12 下午5:41:06    类说明 
 */

public class BeanTouzi {
	private String rate_increase;
	private String NEWHAND_NAME;
	private String total_mount;
	private String percentage;
	private String hongbao;
	private String invest_status;
	private String product_name;
	private String link;
	private String invest_time;
	private String isnew;
	private String order_id;
	private String pro_fit;
	private String invest_money;
	private String id;
	private String rate;
	private String interest_time;
	private String end_time;
	private String time_limit;
	private String start_time;
	private String NEWHAND_ID;
	private String repay_type;

	public BeanTouzi() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanTouzi(String rate_increase, String nEWHAND_NAME,
			String total_mount, String percentage, String hongbao,
			String invest_status, String product_name, String link,
			String invest_time, String isnew, String order_id, String pro_fit,
			String invest_money, String id, String rate, String interest_time,
			String end_time, String time_limit, String start_time,
			String nEWHAND_ID, String repay_type) {
		super();
		this.rate_increase = rate_increase;
		NEWHAND_NAME = nEWHAND_NAME;
		this.total_mount = total_mount;
		this.percentage = percentage;
		this.hongbao = hongbao;
		this.invest_status = invest_status;
		this.product_name = product_name;
		this.link = link;
		this.invest_time = invest_time;
		this.isnew = isnew;
		this.order_id = order_id;
		this.pro_fit = pro_fit;
		this.invest_money = invest_money;
		this.id = id;
		this.rate = rate;
		this.interest_time = interest_time;
		this.end_time = end_time;
		this.time_limit = time_limit;
		this.start_time = start_time;
		NEWHAND_ID = nEWHAND_ID;
		this.repay_type = repay_type;
	}

	public String getRate_increase() {
		return rate_increase;
	}

	public void setRate_increase(String rate_increase) {
		this.rate_increase = rate_increase;
	}

	public String getNEWHAND_NAME() {
		return NEWHAND_NAME;
	}

	public void setNEWHAND_NAME(String nEWHAND_NAME) {
		NEWHAND_NAME = nEWHAND_NAME;
	}

	public String getTotal_mount() {
		return total_mount;
	}

	public void setTotal_mount(String total_mount) {
		this.total_mount = total_mount;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getHongbao() {
		return hongbao;
	}

	public void setHongbao(String hongbao) {
		this.hongbao = hongbao;
	}

	public String getInvest_status() {
		return invest_status;
	}

	public void setInvest_status(String invest_status) {
		this.invest_status = invest_status;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getInvest_time() {
		return invest_time;
	}

	public void setInvest_time(String invest_time) {
		this.invest_time = invest_time;
	}

	public String getIsnew() {
		return isnew;
	}

	public void setIsnew(String isnew) {
		this.isnew = isnew;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getPro_fit() {
		return pro_fit;
	}

	public void setPro_fit(String pro_fit) {
		this.pro_fit = pro_fit;
	}

	public String getInvest_money() {
		return invest_money;
	}

	public void setInvest_money(String invest_money) {
		this.invest_money = invest_money;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getInterest_time() {
		return interest_time;
	}

	public void setInterest_time(String interest_time) {
		this.interest_time = interest_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getNEWHAND_ID() {
		return NEWHAND_ID;
	}

	public void setNEWHAND_ID(String nEWHAND_ID) {
		NEWHAND_ID = nEWHAND_ID;
	}

	public String getRepay_type() {
		return repay_type;
	}

	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}

	@Override
	public String toString() {
		return "BeanTouzi [rate_increase=" + rate_increase + ", NEWHAND_NAME="
				+ NEWHAND_NAME + ", total_mount=" + total_mount
				+ ", percentage=" + percentage + ", hongbao=" + hongbao
				+ ", invest_status=" + invest_status + ", product_name="
				+ product_name + ", link=" + link + ", invest_time="
				+ invest_time + ", isnew=" + isnew + ", order_id=" + order_id
				+ ", pro_fit=" + pro_fit + ", invest_money=" + invest_money
				+ ", id=" + id + ", rate=" + rate + ", interest_time="
				+ interest_time + ", end_time=" + end_time + ", time_limit="
				+ time_limit + ", start_time=" + start_time + ", NEWHAND_ID="
				+ NEWHAND_ID + ", repay_type=" + repay_type + "]";
	}

}
