package com.richard.java8use.test;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月9日 上午11:47:21
*/
public class TestThread extends Thread {

	static String name = "richardyao";
	
	public TestThread() {}
	
	/**
	 * 在jdk中Thread类调用Runnable的run方法是通过Thread自身的run方法来调用的，
	 * 因此对于重写了Thread.run()的子类来说，如果不显式使用super.run()来调用父类的run方法，Runnable中run方法并不会执行
	 * @param run
	 */
	public TestThread(Runnable run) {
		super(run);
	}
	
	public static void main(String[] args) {
		TestThread thread = new TestThread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("How to use this method!");
			}
		});
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
		super.run();
	}
}

class Test {
	
	static {
		i = 0; // compile successfully
		i = 3;
		// System.out.println(i); // will throw error when jvm compile this code because of i not declared
	}
	// 通过javap -c 查看编译后的字节码可以知道，static int i; static int j其实是代码最初声明的，因此在上述静态代码块中，直接给i/j赋值并不会产生错误
	static int i = 1; 
	static int j = 1;
	static final int z;  //must be initialized here.
	static {
		j = 2;
		z = 3;
	}

	public static void main(String[]  args) {
		//至于在经过static块赋值却未生效，通过字节码可以看到其实i = 0, i = 3的赋值是有操作的，但是最后执行的是i = 1操作，因此debug过程看起来static块的代码并未生效
		System.out.println(Test.i);
		System.out.println(Test.j);
		System.out.println(Test.z);
	}
}
