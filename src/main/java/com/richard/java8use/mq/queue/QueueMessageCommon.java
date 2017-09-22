package com.richard.java8use.mq.queue;

import javax.jms.Connection;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.Session;

import com.richard.java8use.mq.MessageBroker;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午3:39:44
*/
public abstract class QueueMessageCommon extends MessageBroker {

	QueueConnection connection = null;
	QueueSession session = null;
	
	public QueueMessageCommon() {}
	
	public QueueMessageCommon(String broker, String destination) {
		super(broker, destination);
	}

	@Override
	public Connection getConnection() {
		if(connection == null) {
			init();
		}
		return connection;
	}

	@Override
	public Session getSession() {
		if(session == null) {
			init();
		}
		return session;
	}
	
	
}
