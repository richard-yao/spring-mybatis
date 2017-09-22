package com.richard.java8use.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.richard.java8use.mq.jms.MessageReceiver;
import com.richard.java8use.mq.jms.MessageSender;
import com.richard.java8use.mq.queue.DefineQueueReceiver;
import com.richard.java8use.mq.queue.DefineQueueSender;
import com.richard.java8use.mq.topic.DefineTopicReceiver;
import com.richard.java8use.mq.topic.DefineTopicSender;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午2:17:10
*/
public class TestMessage {

	// Failover Transport是一种重新连接的机制，nio表示使用java的NIO包，可以提供比tcp更好的性能
	public static final String BROKER_URL = "failover:(nio://127.0.0.1:61616)";
	public static final String DESTINATION = "richard.test.queue";
	public static final String TOPIC = "richard.test.topic";
	// JMS message
	MessageSender jmsSender = new MessageSender(BROKER_URL, DESTINATION);
	MessageReceiver jmsReceiver = new MessageReceiver(BROKER_URL, DESTINATION);
	// Queue message
	DefineQueueSender queueSender = new DefineQueueSender(BROKER_URL, DESTINATION);
	DefineQueueReceiver queueReceiver = new DefineQueueReceiver(BROKER_URL, DESTINATION);
	// Topic message
	DefineTopicSender topicSender = new DefineTopicSender(BROKER_URL, TOPIC);
	DefineTopicReceiver topicReceiver = new DefineTopicReceiver(BROKER_URL, TOPIC);
	
	@Before
	public void init() {
		jmsSender.init();
		jmsReceiver.init();
		queueSender.init();
		topicSender.init();
	}
	
	@Test
	public void test01SendJMSMessage() {
		try {
			jmsSender.sendMessage("Hello ActiveMQ!");
			jmsReceiver.receiveMessage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02SendQueueMessage(){
		try {
			queueSender.sendMessage("Hello MessageQueue");
			queueReceiver.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage msg = (TextMessage) message;
					if(msg != null) {
						try {
							System.out.println("Receive message: " + msg.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});
			queueReceiver.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test03SendTopicMessage() {
		try {
			topicSender.sendMessage("Hello MessageQueue");
			topicReceiver.setSubscriberName("Client-Test-03");
			topicReceiver.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					TextMessage msg = (TextMessage) message;
					if(msg != null) {
						try {
							System.out.println("Subscriber message: " + msg.getText());
						} catch (JMSException e) {
							e.printStackTrace();
						}
					}
				}
			});
			topicReceiver.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void destory() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		jmsSender.destoryConnection();
		jmsReceiver.destoryConnection();
		queueSender.destoryConnection();
		queueReceiver.destoryConnection();
		topicSender.destoryConnection();
		topicReceiver.destoryConnection();
	}
}
