package com.delevin.boluolcs.bean;

public class BeanPdfList {
	private String name;
	private String path;

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

	@Override
	public String toString() {
		return "PdfList [name=" + name + ", path=" + path + "]";
	}

	public BeanPdfList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BeanPdfList(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

}
