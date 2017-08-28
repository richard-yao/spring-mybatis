package com.richard.java8use.test;

import java.lang.reflect.Proxy;

import org.junit.Assert;
import org.junit.Test;

import com.richard.java8use.proxy.CglibProxy;
import com.richard.java8use.proxy.ForumService;
import com.richard.java8use.proxy.ForumServiceImpl;
import com.richard.java8use.proxy.PerformanceHandler;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月28日 下午2:07:03
*/
public class TestForumService {

	@Test
	public void testJdkProxy() {
		ForumService target = new ForumServiceImpl();
		PerformanceHandler handler = new PerformanceHandler(target);
		// 由于jdk动态代理只能基于接口，所以导致使用jdk动态代理的spring方法必须实现接口才能注入事务
		ForumService proxy = (ForumService) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), handler);
		proxy.removeForum(10);
		proxy.removeTopic(2014);
		Assert.assertTrue(proxy instanceof ForumService);
	}
	
	@Test
	public void testCglibProxy() {
		CglibProxy proxy = new CglibProxy(); // Cglib基于动态字节码技术，可以通过创建目标类子类的方式调用目标类方法
		ForumServiceImpl forumServiceImpl = (ForumServiceImpl) proxy.getProxy(ForumServiceImpl.class);
		forumServiceImpl.removeForum(10);
		forumServiceImpl.removeTopic(1023);
	}
}
