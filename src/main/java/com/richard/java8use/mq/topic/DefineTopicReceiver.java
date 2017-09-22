package com.richard.java8use.mq.topic;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午4:37:03
*/
public class DefineTopicReceiver extends TopicMessageCommon {

	private Logger logger = Logger.getLogger(DefineTopicReceiver.class);
	
	private TopicSubscriber subscriber = null;
	private MessageListener messageListener = null;
	private String subscriberName = null;
	
	public DefineTopicReceiver() {}
	
	public DefineTopicReceiver(String broker, String destination) {
		super(broker, destination);
	}
	
	@Override
	public void init() {
		try {
			TopicConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, getBroker());
			connection = factory.createTopicConnection();
			connection.setClientID(getSubscriberName());
			connection.start();
			session = connection.createTopicSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(getDestination());
			subscriber = session.createDurableSubscriber(topic, getSubscriberName());
			subscriber.setMessageListener(getMessageListener());
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

	/**
	 * @return the subscriberName
	 */
	public String getSubscriberName() {
		return subscriberName;
	}

	/**
	 * @param subscriberName the subscriberName to set
	 */
	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}
}
