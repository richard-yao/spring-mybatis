package com.richard.java8use.mq.topic;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午4:31:26
*/
public class DefineTopicSender extends TopicMessageCommon {

	private Logger logger = Logger.getLogger(DefineTopicSender.class);
	
	private TopicPublisher publisher = null;
	
	public DefineTopicSender() {}
	
	public DefineTopicSender(String broker, String destination) {
		super(broker, destination);
	}
	
	@Override
	public void init() {
		try {
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, getBroker());
			connection = factory.createTopicConnection();
			connection.start();
			session = connection.createTopicSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(getDestination());
			publisher = session.createPublisher(topic);
			publisher.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			logger.error(e, e);
		}
	}
	
	public void sendMessage(String message) throws Exception {
		if(checkConnection() && publisher != null) {
			TextMessage msg = session.createTextMessage(message);
			publisher.send(msg);
			System.out.println("Publish message: " + message);
		} else {
			throw new Exception("Connection is not establish!");
		}
	}

}
