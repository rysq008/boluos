package com.delevin.boluolcs.bean;

public class BeanConvert {
	private int iString;

	public BeanConvert() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanConvert(int iString) {
		super();
		this.iString = iString;
	}

	public int getiString() {
		return iString;
	}

	public void setiString(int iString) {
		this.iString = iString;
	}

	@Override
	public String toString() {
		return "BeanConvert [iString=" + iString + "]";
	}

}
