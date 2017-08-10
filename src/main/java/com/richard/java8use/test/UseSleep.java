package com.richard.java8use.test;

import java.util.Date;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月10日 上午10:02:33
*/
public class UseSleep {

public static void main(String[] args) {
		
		Thread thread1 = new Thread(() -> {
			synchronized (UseSleep.class) {
				try {
					System.out.println(new Date() + " Tread1 is running");
					Thread.sleep(2000);
					System.out.println(new Date() + " Thread1 ended");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		
		Thread thread2 = new Thread(() ->{
			synchronized (UseSleep.class) {
				try {
					System.out.println(new Date() + " Thread2 is running");
					Thread.sleep(2000);
					System.out.println(new Date() + " Thread2 ended");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		for(long i = 0L; i < 200000; i++) {
			for(long j = 0L; j < 100000; j++){}
		}
		thread2.start();
	}
}
