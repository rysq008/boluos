package com.delevin.boluolcs.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *     @author 李红涛  @version 创建时间：2016-12-28 上午10:59:36    类说明 
 */

public class BeanMy {
	private String z_hongbao;
	private String z_yaoqing_shouyi;
	private String z_activity_interest;
	private String sum_profit;
	private String is_vip;
	private String uncollectedamount;
	private String type;
	private String freezeamount;
	private String touxiang_image;
	private String remainamount;
	private String remain_balance;
	private String name;
	private String z_interest;
	private String rechargeamount;
	private String has_hongbao_num;

	public BeanMy() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanMy(String z_hongbao, String z_yaoqing_shouyi,
			String z_activity_interest, String sum_profit, String is_vip,
			String uncollectedamount, String type, String freezeamount,
			String touxiang_image, String remainamount, String remain_balance,
			String name, String z_interest, String rechargeamount,
			String has_hongbao_num) {
		super();
		this.z_hongbao = z_hongbao;
		this.z_yaoqing_shouyi = z_yaoqing_shouyi;
		this.z_activity_interest = z_activity_interest;
		this.sum_profit = sum_profit;
		this.is_vip = is_vip;
		this.uncollectedamount = uncollectedamount;
		this.type = type;
		this.freezeamount = freezeamount;
		this.touxiang_image = touxiang_image;
		this.remainamount = remainamount;
		this.remain_balance = remain_balance;
		this.name = name;
		this.z_interest = z_interest;
		this.rechargeamount = rechargeamount;
		this.has_hongbao_num = has_hongbao_num;
	}

	public void getMyData(JSONObject result, BeanMy beanMy) {
		try {
			JSONObject object = result.getJSONObject("content");
			beanMy.setZ_hongbao(object.getString("z_hongbao"));
			beanMy.setZ_yaoqing_shouyi(object.getString("z_yaoqing_shouyi"));
			beanMy.setZ_activity_interest(object
					.getString("z_activity_interest"));
			beanMy.setSum_profit(object.getString("sum_profit"));
			beanMy.setIs_vip(object.getString("is_vip"));
			beanMy.setUncollectedamount(object.getString("uncollectedamount"));
			beanMy.setType(object.getString("type"));
			beanMy.setFreezeamount(object.getString("freezeamount"));
			beanMy.setTouxiang_image(object.getString("touxiang_image"));
			beanMy.setRemainamount(object.getString("remainamount"));
			beanMy.setRemain_balance(object.getString("remain_balance"));
			beanMy.setName(object.getString("name"));
			beanMy.setZ_interest(object.getString("z_interest"));
			beanMy.setRechargeamount(object.getString("rechargeamount"));
			beanMy.setHas_hongbao_num(object.getString("has_hongbao_num"));
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public String getZ_hongbao() {
		return z_hongbao;
	}

	public void setZ_hongbao(String z_hongbao) {
		this.z_hongbao = z_hongbao;
	}

	public String getZ_yaoqing_shouyi() {
		return z_yaoqing_shouyi;
	}

	public void setZ_yaoqing_shouyi(String z_yaoqing_shouyi) {
		this.z_yaoqing_shouyi = z_yaoqing_shouyi;
	}

	public String getZ_activity_interest() {
		return z_activity_interest;
	}

	public void setZ_activity_interest(String z_activity_interest) {
		this.z_activity_interest = z_activity_interest;
	}

	public String getSum_profit() {
		return sum_profit;
	}

	public void setSum_profit(String sum_profit) {
		this.sum_profit = sum_profit;
	}

	public String getIs_vip() {
		return is_vip;
	}

	public void setIs_vip(String is_vip) {
		this.is_vip = is_vip;
	}

	public String getUncollectedamount() {
		return uncollectedamount;
	}

	public void setUncollectedamount(String uncollectedamount) {
		this.uncollectedamount = uncollectedamount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFreezeamount() {
		return freezeamount;
	}

	public void setFreezeamount(String freezeamount) {
		this.freezeamount = freezeamount;
	}

	public String getTouxiang_image() {
		return touxiang_image;
	}

	public void setTouxiang_image(String touxiang_image) {
		this.touxiang_image = touxiang_image;
	}

	public String getRemainamount() {
		return remainamount;
	}

	public void setRemainamount(String remainamount) {
		this.remainamount = remainamount;
	}

	public String getRemain_balance() {
		return remain_balance;
	}

	public void setRemain_balance(String remain_balance) {
		this.remain_balance = remain_balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZ_interest() {
		return z_interest;
	}

	public void setZ_interest(String z_interest) {
		this.z_interest = z_interest;
	}

	public String getRechargeamount() {
		return rechargeamount;
	}

	public void setRechargeamount(String rechargeamount) {
		this.rechargeamount = rechargeamount;
	}

	public String getHas_hongbao_num() {
		return has_hongbao_num;
	}

	public void setHas_hongbao_num(String has_hongbao_num) {
		this.has_hongbao_num = has_hongbao_num;
	}

	@Override
	public String toString() {
		return "BeanMy [z_hongbao=" + z_hongbao + ", z_yaoqing_shouyi="
				+ z_yaoqing_shouyi + ", z_activity_interest="
				+ z_activity_interest + ", sum_profit=" + sum_profit
				+ ", is_vip=" + is_vip + ", uncollectedamount="
				+ uncollectedamount + ", type=" + type + ", freezeamount="
				+ freezeamount + ", touxiang_image=" + touxiang_image
				+ ", remainamount=" + remainamount + ", remain_balance="
				+ remain_balance + ", name=" + name + ", z_interest="
				+ z_interest + ", rechargeamount=" + rechargeamount
				+ ", has_hongbao_num=" + has_hongbao_num + "]";
	}

}
