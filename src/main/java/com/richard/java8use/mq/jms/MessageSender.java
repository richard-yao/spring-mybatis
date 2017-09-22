package com.richard.java8use.mq.jms;

import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午1:14:32
* 使用jms方式发送消息
*/
public class MessageSender extends MessageCommon {

	private Logger logger = Logger.getLogger(MessageSender.class);
	
	private MessageProducer producer = null;
	
	public MessageSender() {}
	
	public MessageSender(String broker, String destination) {
		super(broker, destination);
	}
	
	public void sendMessage(String message) throws Exception {
		if(checkConnection()) {
			TextMessage msg = session.createTextMessage(message);
			producer.send(msg);
			logger.info("Send message: " + message);
		} else {
			throw new Exception("Connection is not establish!");
		}
	}
	
	@Override
	public void init() {
		try {
			// Create connection factory
			ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, getBroker());
			// Use connection factory to create one connect
			connection = factory.createConnection();
			// Start this connection
			connection.start();
			// Create one session for using, session do not use transcation
			session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			// Use session to generate the specify Destination
			Destination destination = session.createQueue(getDestination());
			// Create producer for send message
			producer = session.createProducer(destination);
			// Set DeliveryMode as NON_PERSISTENT, do not store message to disk
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			//session.commit();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
