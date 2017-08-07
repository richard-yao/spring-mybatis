package com.richard.java8use.test;

import com.richard.java8use.dao.BaseDao;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年6月13日 上午11:30:41
*/
public class TestSomething {

	public static void main(String[] args) {
		System.out.println(returnStr().toString());
		useInterface();
	}
	
	public static StringBuffer returnStr() {
		return new StringBuffer("What can I do for you, ").append("madam?");
	}
	
	public static void useInterface() {
		new BaseDao() {

			@Override
			public void baseDaoMethod() {
				// TODO Auto-generated method stub
				
			}
		}.initInterface();
		
		new BaseDao() {
			
			@Override
			public void baseDaoMethod() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void initInterface() {
				System.out.println("What's your name");
			}
		}.initInterface();
	}
}
