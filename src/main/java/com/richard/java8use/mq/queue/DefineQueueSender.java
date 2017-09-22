package com.richard.java8use.mq.queue;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午3:22:54
*/

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;


public class DefineQueueSender extends QueueMessageCommon {

	private Logger logger = Logger.getLogger(DefineQueueSender.class);
	
	private QueueSender sender = null;
	
	public DefineQueueSender() {}
	
	public DefineQueueSender(String broker, String destination) {
		super(broker, destination);
	}
	
	@Override
	public void init() {
		try {
			QueueConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, getBroker());
			connection = factory.createQueueConnection();
			connection.start();
			session = connection.createQueueSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(getDestination());
			sender = session.createSender(queue);
			sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			logger.error(e, e);
		}
	}
	
	public void sendMessage(String message) throws Exception {
		if(checkConnection() && sender != null) {
			TextMessage msg = session.createTextMessage(message);
			sender.send(msg);
			System.out.println("Send message: " + message);
		} else {
			throw new Exception("Connection is not establish!");
		}
	}

}
