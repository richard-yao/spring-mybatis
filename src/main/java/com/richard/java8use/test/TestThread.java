package com.richard.java8use.test;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月9日 上午11:47:21
*/
public class TestThread extends Thread {

	static String name = "richardyao";
	
	public static void main(String[] args) {
		TestThread thread = new TestThread();
		thread.piggy(name);
		for(int i = 0; i< 1000; i++) {
			System.out.println(name);
		}
	}
	
	public void piggy(String name) {
		name = name + "111111";
		start();
	}
	
	@Override
	public void run() {
		
		for(int i=0;i<1000;i++) {
			System.out.println(i + " ");
		}
	}
}
