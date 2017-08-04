package com.richard.java8use.test;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年6月13日 上午11:30:41
*/
public class TestSomething {

	public static void main(String[] args) {
		System.out.println(returnStr().toString());
	}
	
	public static StringBuffer returnStr() {
		return new StringBuffer("What can I do for you, ").append("madam?");
	}

}
