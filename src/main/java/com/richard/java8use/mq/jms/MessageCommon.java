package com.richard.java8use.mq.jms;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Logger;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午2:44:18
*/
public abstract class MessageCommon {
	
	private Logger logger = Logger.getLogger(MessageCommon.class);

	// Connection related parameters
	Connection connection = null;
	Session session = null;
	
	// MQ configuration parameters
	String broker;
	String destination;
	
	public MessageCommon() {}
	
	public MessageCommon(String broker, String destination) {
		this.broker = broker;
		this.destination = destination;
	}
	
	/**
	 * Common method to init connection
	 */
	public abstract void init();
	
	/**
	 * To check the connection exist or not
	 * @return
	 */
	public boolean checkConnection() {
		if(connection != null && session != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Destory connection when user do not want to use
	 */
	public void destoryConnection() {
		try {
			if(session != null) {
				session.close();
				session = null;
			}
			if(connection != null) {
				connection.close();
				connection = null;
			}
		} catch (JMSException e) {
			logger.error(e, e);
		}
	}
	
	public String getBroker() {
		return broker;
	}
	public String getDestination() {
		return destination;
	}
	public void setBroker(String broker) {
		this.broker = broker;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
}
