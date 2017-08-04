package com.delevin.boluolcs.bean;

public class BeanTixianJilu {
	private String batchcurrnum;
	private String batchdate;
	private String fee;
	private String id;
	private String member_id;
	private String member_name;
	private String money;
	private String query;
	private String query_class;
	private String reason;
	private String reflect_id;
	private String time;
	private String type;

	public String getBatchcurrnum() {
		return batchcurrnum;
	}

	public void setBatchcurrnum(String batchcurrnum) {
		this.batchcurrnum = batchcurrnum;
	}

	public String getBatchdate() {
		return batchdate;
	}

	public void setBatchdate(String batchdate) {
		this.batchdate = batchdate;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReflect_id() {
		return reflect_id;
	}

	public void setReflect_id(String reflect_id) {
		this.reflect_id = reflect_id;
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
		return "TixianJilu [batchcurrnum=" + batchcurrnum + ", batchdate="
				+ batchdate + ", fee=" + fee + ", id=" + id + ", member_id="
				+ member_id + ", member_name=" + member_name + ", money="
				+ money + ", query=" + query + ", query_class=" + query_class
				+ ", reason=" + reason + ", reflect_id=" + reflect_id
				+ ", time=" + time + ", type=" + type + "]";
	}

	public BeanTixianJilu(String batchcurrnum, String batchdate, String fee,
			String id, String member_id, String member_name, String money,
			String query, String query_class, String reason, String reflect_id,
			String time, String type) {
		super();
		this.batchcurrnum = batchcurrnum;
		this.batchdate = batchdate;
		this.fee = fee;
		this.id = id;
		this.member_id = member_id;
		this.member_name = member_name;
		this.money = money;
		this.query = query;
		this.query_class = query_class;
		this.reason = reason;
		this.reflect_id = reflect_id;
		this.time = time;
		this.type = type;
	}

	public BeanTixianJilu() {
		super();
		// TODO Auto-generated constructor stub
	}

}
