package com.richard.java8use.test;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date 2017年8月29日 下午5:40:51
 */
public class PrintABC {
	private static final int PRINT_A = 0;
	private static final int PRINT_B = 1;
	private static final int PRINT_C = 2;

	private static class MyThread extends Thread {
		int which; // 0：打印A；1：打印B；2：打印C
		static volatile int state; // 线程共有，判断所有的打印状态
		static final Object t = new Object();

		public MyThread(int which) {
			this.which = which;
		}

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				synchronized (t) {
					while (state % 3 != which) {
						try {
							t.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.print(toABC(which)); // 执行到这里，表明满足条件，打印
					state++;
					t.notifyAll(); // 调用notifyAll方法
				}
			}
		}
	}

	public static void main(String[] args) {
		new MyThread(PRINT_A).start();
		new MyThread(PRINT_B).start();
		new MyThread(PRINT_C).start();
	}

	private static char toABC(int which) {
		return (char) ('A' + which);
	}
}
