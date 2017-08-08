package com.richard.java8use.newfeature;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月8日 下午3:22:58
*/
public interface Formula {
	
	double calculate(int number);
	
	default double sqrt(int number) {
		return Math.sqrt(number);
	}
}
