package com.richard.java8use.test;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date 2017年8月30日 上午9:42:19
 */
public class YieldThread {

	public static void main(String[] args) {
		MyThread mt = new MyThread();
		mt.start();
		while (true) {
			System.out.println("主线程");
		}
	}
}

class MyThread extends Thread {
	public void run() {
		while (true) {
			System.out.println("被放弃线程");
			Thread.yield(); // yield是放弃当前时间片，而不是永久放弃cpu时间，因此还是会执行，只不过没有主线程占用的cpu时间多
		}
	}
}
