package com.richard.java8use.model;

import org.apache.ibatis.type.Alias;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午2:24:43
*/
@Alias("ReportData")
public class ReportData {

	String id;
	String type;
	String version;
	String name;
	
	public ReportData(){}
	
	public ReportData(String type, String version, String name) {
		this.type = type;
		this.version = version;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuffer temp = new StringBuffer();
		temp.append("id: ").append(id).append(",type: ").append(type).append(",version: ").append(version).append(",name: ").append(name);
		return temp.toString();
	}
}
