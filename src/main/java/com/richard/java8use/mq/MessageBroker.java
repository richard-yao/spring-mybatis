package com.richard.java8use.mq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Session;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午4:07:20
* This class used to define the MQ broker url and customize topic/queue
*/
public abstract class MessageBroker {

	// MQ configuration parameters
	String broker;
	String destination;
	
	public MessageBroker() {}
	
	public MessageBroker(String broker, String destination) {
		this.broker = broker;
		this.destination = destination;
	}
	
	/**
	 * Common method to init connection
	 */
	public abstract void init();
	
	/**
	 * Get valid connection
	 * @return
	 */
	public abstract Connection getConnection();
	
	/**
	 * Get valid session
	 * @return
	 */
	public abstract Session getSession();
	
	/**
	 * To check the connection exist or not
	 * @return
	 */
	public boolean checkConnection() {
		if(getConnection() != null && getSession() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Destory connection when user do not want to use
	 */
	public void destoryConnection() {
		try {
			if(getSession() != null) {
				getSession().close();
			}
			if(getConnection() != null) {
				getConnection().close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
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
