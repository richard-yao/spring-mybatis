package com.richard.java8use.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.richard.java8use.mq.jms.MessageReceiver;
import com.richard.java8use.mq.jms.MessageSender;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午2:17:10
*/
public class TestMessage {

	// Failover Transport是一种重新连接的机制，nio表示使用java的NIO包，可以提供比tcp更好的性能
	public static final String BROKER_URL = "failover:(nio://127.0.0.1:61616)";
	public static final String DESTINATION = "richard.test.queue";
	MessageSender jmsSender = new MessageSender(BROKER_URL, DESTINATION);
	MessageReceiver jmsReceiver = new MessageReceiver(BROKER_URL, DESTINATION);
	
	@Before
	public void init() {
		jmsSender.init();
		jmsReceiver.init();
	}
	
	@Test
	public void test01SendLongMessage() {
		try {
			jmsSender.sendMessage("Hello ActiveMQ!");
			jmsReceiver.receiveMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@After
	public void destory() {
		jmsSender.destoryConnection();
	}
}
