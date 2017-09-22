package com.richard.java8use.mq.jms;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午2:25:46
* 接收jms消息
*/
public class MessageReceiver extends MessageCommon {
	
	private Logger logger = Logger.getLogger(MessageReceiver.class);

	private MessageConsumer consumer = null;
	
	public MessageReceiver() {}
	
	public MessageReceiver(String broker, String destination) {
		super(broker, destination);
	}
	
	/**
	 * This message destination is a queue, so only one consumer will receive the message and return ack though exist multiple available consumers
	 * @throws Exception
	 */
	public void receiveMessage() throws Exception {
		if(checkConnection() && consumer != null) {
			while(true) {
				Message message = consumer.receive(1000); // timeout interval, unit is ms
				TextMessage text = (TextMessage) message;
				if(text != null) {
					logger.info("Receive message: " + text.getText());
				} else {
					break;
				}
			}
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
			consumer = session.createConsumer(destination);
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
}
