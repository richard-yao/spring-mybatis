package com.richard.java8use.proxy;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月28日 下午2:12:58
*/
public class CglibProxy implements MethodInterceptor {

	private Enhancer enhancer = new Enhancer();
	
	public Object getProxy(Class<?> clazz) {
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		PerformanceMontior.begin(obj.getClass().getName() + "." + method.getName());
		Object result = proxy.invokeSuper(obj, args);
		PerformanceMontior.end();
		return result;
	}

}
