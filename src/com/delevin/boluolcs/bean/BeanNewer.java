package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2016-12-20 下午12:46:47    类说明 
 */
public class BeanNewer {
	private String base_url;
	private String feature_id;
	private String feature_name;
	private String id;
	private String is_recommend;
	private String limit_mount;
	private String percentage;
	private String product_name;
	private String product_status;
	private String product_type;
	private String raise_limit;
	private String rate;
	private String rate_increase;
	private String sell_time;
	private String time_limit;
	private String total_mount;

	public BeanNewer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanNewer(String base_url, String feature_id, String feature_name,
			String id, String is_recommend, String limit_mount,
			String percentage, String product_name, String product_status,
			String product_type, String raise_limit, String rate,
			String rate_increase, String sell_time, String time_limit,
			String total_mount) {
		super();
		this.base_url = base_url;
		this.feature_id = feature_id;
		this.feature_name = feature_name;
		this.id = id;
		this.is_recommend = is_recommend;
		this.limit_mount = limit_mount;
		this.percentage = percentage;
		this.product_name = product_name;
		this.product_status = product_status;
		this.product_type = product_type;
		this.raise_limit = raise_limit;
		this.rate = rate;
		this.rate_increase = rate_increase;
		this.sell_time = sell_time;
		this.time_limit = time_limit;
		this.total_mount = total_mount;
	}

	public String getBase_url() {
		return base_url;
	}

	public void setBase_url(String base_url) {
		this.base_url = base_url;
	}

	public String getFeature_id() {
		return feature_id;
	}

	public void setFeature_id(String feature_id) {
		this.feature_id = feature_id;
	}

	public String getFeature_name() {
		return feature_name;
	}

	public void setFeature_name(String feature_name) {
		this.feature_name = feature_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_recommend() {
		return is_recommend;
	}

	public void setIs_recommend(String is_recommend) {
		this.is_recommend = is_recommend;
	}

	public String getLimit_mount() {
		return limit_mount;
	}

	public void setLimit_mount(String limit_mount) {
		this.limit_mount = limit_mount;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_status() {
		return product_status;
	}

	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}

	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String product_type) {
		this.product_type = product_type;
	}

	public String getRaise_limit() {
		return raise_limit;
	}

	public void setRaise_limit(String raise_limit) {
		this.raise_limit = raise_limit;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRate_increase() {
		return rate_increase;
	}

	public void setRate_increase(String rate_increase) {
		this.rate_increase = rate_increase;
	}

	public String getSell_time() {
		return sell_time;
	}

	public void setSell_time(String sell_time) {
		this.sell_time = sell_time;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getTotal_mount() {
		return total_mount;
	}

	public void setTotal_mount(String total_mount) {
		this.total_mount = total_mount;
	}

	@Override
	public String toString() {
		return "BeanNewer [base_url=" + base_url + ", feature_id=" + feature_id
				+ ", feature_name=" + feature_name + ", id=" + id
				+ ", is_recommend=" + is_recommend + ", limit_mount="
				+ limit_mount + ", percentage=" + percentage
				+ ", product_name=" + product_name + ", product_status="
				+ product_status + ", product_type=" + product_type
				+ ", raise_limit=" + raise_limit + ", rate=" + rate
				+ ", rate_increase=" + rate_increase + ", sell_time="
				+ sell_time + ", time_limit=" + time_limit + ", total_mount="
				+ total_mount + "]";
	}

}
