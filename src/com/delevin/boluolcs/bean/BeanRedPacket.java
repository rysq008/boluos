package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 下午1:47:15    类说明 
 */

public class BeanRedPacket {

	private String dead_time;
	private String id;
	private String is_freeze;
	private String is_use;
	private String money;
	private String name;
	private String is_new_product;
	private String product_id;
	private String rules;
	private String start_money;
	private String type;

	public BeanRedPacket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanRedPacket(String dead_time, String id, String is_freeze,
			String is_use, String money, String name, String is_new_product,
			String product_id, String rules, String start_money, String type) {
		super();
		this.dead_time = dead_time;
		this.id = id;
		this.is_freeze = is_freeze;
		this.is_use = is_use;
		this.money = money;
		this.name = name;
		this.is_new_product = is_new_product;
		this.product_id = product_id;
		this.rules = rules;
		this.start_money = start_money;
		this.type = type;
	}

	public String getDead_time() {
		return dead_time;
	}

	public void setDead_time(String dead_time) {
		this.dead_time = dead_time;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_freeze() {
		return is_freeze;
	}

	public void setIs_freeze(String is_freeze) {
		this.is_freeze = is_freeze;
	}

	public String getIs_use() {
		return is_use;
	}

	public void setIs_use(String is_use) {
		this.is_use = is_use;
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

	public String getIs_new_product() {
		return is_new_product;
	}

	public void setIs_new_product(String is_new_product) {
		this.is_new_product = is_new_product;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getStart_money() {
		return start_money;
	}

	public void setStart_money(String start_money) {
		this.start_money = start_money;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BeanRedPacket [dead_time=" + dead_time + ", id=" + id
				+ ", is_freeze=" + is_freeze + ", is_use=" + is_use
				+ ", money=" + money + ", name=" + name + ", is_new_product="
				+ is_new_product + ", product_id=" + product_id + ", rules="
				+ rules + ", start_money=" + start_money + ", type=" + type
				+ "]";
	}
}
