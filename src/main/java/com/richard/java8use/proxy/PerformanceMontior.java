package com.richard.java8use.proxy;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月28日 下午1:55:32
*/
public class PerformanceMontior {

	private static ThreadLocal<MethodPerformance> pr = new ThreadLocal<MethodPerformance>();
	
	public static void begin(String method) {
		System.out.println("Begin monitor...");
		MethodPerformance mp = new MethodPerformance(method);
		pr.set(mp);
	}
	
	public static void end() {
		System.out.println("End monitor...");
		MethodPerformance mp = pr.get();
		mp.printPerformance();
	}
}


class MethodPerformance {
	
	private long begin;
	private long end;
	private String serviceMethod;
	
	public MethodPerformance(String serviceMethod) {
		this.serviceMethod = serviceMethod;
		begin = System.currentTimeMillis();
	}
	
	public void printPerformance() {
		end = System.currentTimeMillis();
		long elapse = end - begin;
		System.out.println(serviceMethod + " cost " + elapse + "ms");
	}
}