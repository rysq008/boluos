package com.delevin.boluolcs.bean;

/**
 *     @author 李红涛  @version 创建时间：2017-1-5 上午9:38:55    类说明 
 */

public class BeanZijin {
	private String balance;
	private String id;
	private String info;
	private String member_id;
	private String money;
	private String order_id;
	private String product_info;
	private String query;
	private String query_class;
	private String time;
	private String type;

	public BeanZijin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanZijin(String balance, String id, String info, String member_id,
			String money, String order_id, String product_info, String query,
			String query_class, String time, String type) {
		super();
		this.balance = balance;
		this.id = id;
		this.info = info;
		this.member_id = member_id;
		this.money = money;
		this.order_id = order_id;
		this.product_info = product_info;
		this.query = query;
		this.query_class = query_class;
		this.time = time;
		this.type = type;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getProduct_info() {
		return product_info;
	}

	public void setProduct_info(String product_info) {
		this.product_info = product_info;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQuery_class() {
		return query_class;
	}

	public void setQuery_class(String query_class) {
		this.query_class = query_class;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "BeanZijin [balance=" + balance + ", id=" + id + ", info="
				+ info + ", member_id=" + member_id + ", money=" + money
				+ ", order_id=" + order_id + ", product_info=" + product_info
				+ ", query=" + query + ", query_class=" + query_class
				+ ", time=" + time + ", type=" + type + "]";
	}

}
