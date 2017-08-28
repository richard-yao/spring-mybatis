package com.richard.java8use.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月28日 下午2:03:15
*/
public class PerformanceHandler implements InvocationHandler {

	private Object target;
	
	public PerformanceHandler(Object target) {
		this.target = target;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		PerformanceMontior.begin(target.getClass().getName() + "." + method.getName());
		Object object = method.invoke(target, args);
		PerformanceMontior.end();
		return object;
	}

}
