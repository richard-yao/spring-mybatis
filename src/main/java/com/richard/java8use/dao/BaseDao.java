package com.richard.java8use.dao;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月7日 下午4:15:33
*/
public interface BaseDao {
	
	default void initInterface() {
		System.out.println(1234);
	}
	
	void baseDaoMethod();
}
