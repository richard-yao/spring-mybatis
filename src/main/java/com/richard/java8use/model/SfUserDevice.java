package com.richard.java8use.model;

import org.apache.ibatis.type.Alias;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午4:14:32
*/
@Alias("SfUserDevice")
public class SfUserDevice {

	int id;
	String accountId;
	String deviceId;
	String type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
