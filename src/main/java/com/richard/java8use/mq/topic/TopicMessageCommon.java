package com.richard.java8use.mq.topic;

import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;

import com.richard.java8use.mq.MessageBroker;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月22日 下午4:27:36
*/
public abstract class TopicMessageCommon extends MessageBroker {

	TopicConnection connection = null;
	TopicSession session = null;

	public TopicMessageCommon() {}
	
	public TopicMessageCommon(String broker, String destination) {
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
