package com.richard.java8use.model;

import org.apache.ibatis.type.Alias;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月28日 下午3:25:57
*/
@Alias("Account")
public class Account {
	
	private int id;
	private int value;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("id is ").append(id).append(", value is ").append(value);
		return result.toString();
	}
}
