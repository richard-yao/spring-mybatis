package com.richard.java8use.test;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月9日 上午11:47:21
*/
public class TestThread extends Thread {

	static String name = "richardyao";
	
	public static void main(String[] args) {
		TestThread thread = new TestThread();
		System.out.println("Start main thread time: " + System.currentTimeMillis());
		thread.piggy(name);
		for(int i=0;i<1000000;i++) {}
		System.out.println("Start print name time: " + System.currentTimeMillis());
		System.out.println(name);
	}
	
	public void piggy(String name) {
		name = name + "111111";
		start();
	}
	
	@Override
	public void run() {
		
		System.out.println("Start thread run time: " + System.currentTimeMillis());
		for(int i=0;i<10;i++) {
			name = name + i;
		}
	}
}
