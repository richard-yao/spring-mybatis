package com.richard.java8use.mq.jms;

import javax.jms.Connection;
import javax.jms.Session;

import com.richard.java8use.mq.MessageBroker;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午2:44:18
*/
public abstract class MessageCommon extends MessageBroker {
	
	// Connection related parameters
	Connection connection = null;
	Session session = null;
	
	public MessageCommon() {}
	
	public MessageCommon(String broker, String destination) {
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
