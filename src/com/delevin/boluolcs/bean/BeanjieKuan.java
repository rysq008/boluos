package com.delevin.boluolcs.bean;

import java.io.Serializable;

/**
 * @author 李hongtaoE-mail:
 * @version 创建时间：2017-4-20 上午10:31:03 类说明
 */
public class BeanjieKuan implements Serializable {
	/**
	 * 
	 */
	private String name;
	private String path;

	public BeanjieKuan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanjieKuan(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
