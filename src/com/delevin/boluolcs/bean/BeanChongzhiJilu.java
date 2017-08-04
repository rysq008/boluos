package com.delevin.boluolcs.bean;

public class BeanChongzhiJilu {
	private String id;
	private String is_effect;
	private String member_id;
	private String member_name;
	private String money;
	private String query;
	private String query_class;
	private String recharge_id;
	private String time;
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIs_effect() {
		return is_effect;
	}

	public void setIs_effect(String is_effect) {
		this.is_effect = is_effect;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
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

	public String getRecharge_id() {
		return recharge_id;
	}

	public void setRecharge_id(String recharge_id) {
		this.recharge_id = recharge_id;
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
		return "ChongzhiJilu [id=" + id + ", is_effect=" + is_effect
				+ ", member_id=" + member_id + ", member_name=" + member_name
				+ ", money=" + money + ", query=" + query + ", query_class="
				+ query_class + ", recharge_id=" + recharge_id + ", time="
				+ time + ", type=" + type + "]";
	}

	public BeanChongzhiJilu(String id, String is_effect, String member_id,
			String member_name, String money, String query, String query_class,
			String recharge_id, String time, String type) {
		super();
		this.id = id;
		this.is_effect = is_effect;
		this.member_id = member_id;
		this.member_name = member_name;
		this.money = money;
		this.query = query;
		this.query_class = query_class;
		this.recharge_id = recharge_id;
		this.time = time;
		this.type = type;
	}

	public BeanChongzhiJilu() {
		super();
		// TODO Auto-generated constructor stub
	}
}
