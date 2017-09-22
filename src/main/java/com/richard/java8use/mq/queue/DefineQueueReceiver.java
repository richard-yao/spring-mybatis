package com.richard.java8use.mq.queue;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午3:48:50
*/
public class DefineQueueReceiver extends QueueMessageCommon {

	private Logger logger = Logger.getLogger(DefineQueueReceiver.class);
	
	private QueueReceiver receiver = null;
	private MessageListener messageListener = null;
	
	public DefineQueueReceiver() {}
	
	public DefineQueueReceiver(String broker, String destination) {
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
			receiver = session.createReceiver(queue);
			receiver.setMessageListener(getMessageListener());
		} catch (JMSException e) {
			logger.error(e, e);
		}
		
	}

	/**
	 * @return the messageListener
	 */
	public MessageListener getMessageListener() {
		return messageListener;
	}

	/**
	 * @param messageListener the messageListener to set
	 */
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
}
