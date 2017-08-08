package com.richard.java8use.newfeature;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月8日 下午3:33:19
*/
public interface Formula2 {

	default double calculate(int number) {
		return Math.abs(number);
	}
	
	double sqrt(int number);
}
