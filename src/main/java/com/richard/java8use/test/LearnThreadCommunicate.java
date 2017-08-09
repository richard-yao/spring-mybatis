package com.richard.java8use.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date 2017年8月9日 上午11:02:03
 */
public class LearnThreadCommunicate {

	public static void main(String[] args) {
		final BoundedBuffer boundedBuffer = new BoundedBuffer();
		for (int i = 0; i < 50; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						boundedBuffer.put(Math.random());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			thread.setName("Thread-write" + i);
			thread.start();
		}
		for (int i = 0; i < 50; i++) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						boundedBuffer.take();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			thread.setName("Thread-read" + i);
			thread.start();
		}
	}

}

class BoundedBuffer {
	final Lock lock = new ReentrantLock(); // 锁对象
	//ReentrantReadWriteLock lock2 = new ReentrantReadWriteLock();
	final Condition notFull = lock.newCondition(); // 写线程锁
	final Condition notEmpty = lock.newCondition(); // 读线程锁

	final Object[] items = new Object[10]; // 缓存队列
	int putptr; // 写索引
	int takeptr; // 读索引
	int count; // 队列中数据数目

	// 写
	public void put(Object x) throws InterruptedException {
		//lock2.readLock().newCondition(); // just throw unsupport exception
		//lock2.writeLock().newCondition();
		lock.lock();
		try {
			// 如果队列满，则阻塞写线程
			while (count == items.length) {
				notFull.await();
			}

			// 写入队列，并更新索引
			System.out.println("write data is " + x);
			items[putptr] = x;
			if (++putptr == items.length)
				putptr = 0;
			++count;

			// 唤醒读线程
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	// 读
	public Object take() throws InterruptedException {
		lock.lock();
		try {
			// 如果队列空，则阻塞读线程
			while (count == 0) {
				notEmpty.await();
			}

			// 读取队列，并更新索引
			Object x = items[takeptr];
			System.out.println("read data is " + x);
			if (++takeptr == items.length)
				takeptr = 0;
			--count;

			// 唤醒写线程
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}
