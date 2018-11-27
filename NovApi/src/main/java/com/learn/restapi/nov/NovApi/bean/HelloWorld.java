package com.learn.restapi.nov.NovApi.bean;

import java.util.Date;

public class HelloWorld {
	
	private String name;
	private Date date;
	
	public HelloWorld() {
		
	}
	
	public HelloWorld(String name, Date date) {
		super();
		this.name = name;
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "HelloWorld [name=" + name + ", date=" + date + "]";
	}
	
	

}
