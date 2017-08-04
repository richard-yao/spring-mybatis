package com.richard.java8use.model;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月4日 上午9:42:22
*/

import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("SfUser")
public class SfUser {

	private int id;
	private String accountId;
	private String name;
	private String type;
	private String salesRegion;
	private int devicesNumber;
	private List<SfUserDevice> devices;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSalesRegion() {
		return salesRegion;
	}
	public void setSalesRegion(String salesRegion) {
		this.salesRegion = salesRegion;
	}
	public List<SfUserDevice> getDevices() {
		return devices;
	}
	public void setDevices(List<SfUserDevice> devices) {
		this.devices = devices;
	}
	public int getDevicesNumber() {
		return devicesNumber;
	}
	public void setDevicesNumber(int devicesNumber) {
		this.devicesNumber = devicesNumber;
	}
}
