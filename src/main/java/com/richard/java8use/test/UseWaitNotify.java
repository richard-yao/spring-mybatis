package com.richard.java8use.test;

import java.util.Date;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月9日 下午5:54:56
*/
public class UseWaitNotify {

	public static void main(String[] args) {
		
		Thread thread1 = new Thread(() -> {
			synchronized (UseWaitNotify.class) {
				try {
					System.out.println(new Date() + " Tread1 is running");
					UseWaitNotify.class.wait(); // 等待notify激活并在获取锁的情况下才能继续执行
					System.out.println(new Date() + " Thread1 ended");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		
		Thread thread2 = new Thread(() ->{
			synchronized (UseWaitNotify.class) {
				try {
					System.out.println(new Date() + " Thread2 is running");
					UseWaitNotify.class.notify(); // 调用notify后，thread2并没有释放锁，所以thread1需要在thread2释放锁之后才会继续执行
					for(long i = 0L; i < 200000; i++) {
						for(long j = 0L; j < 100000; j++){}
					}
					System.out.println(new Date() + " Thread2 release lock");
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			for(long i = 0L; i < 200000; i++) {
				for(long j = 0L; j < 100000; j++){}
			}
			System.out.println(new Date() + " Thread2 ended");
		});
		
		for(long i = 0L; i < 200000; i++) {
			for(long j = 0L; j < 100000; j++){}
		}
		thread2.start();
	}
}
