package com.richard.java8use.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.richard.java8use.model.Account;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月28日 下午3:30:57
*/
public class TestTransaction {
	
	private Logger logger = Logger.getLogger(TestTransaction.class);
	
	private Configuration config = null;
	private SessionFactory factory = null;
	private Session session = null;
	private Transaction tx = null;
	
	public static void main(String[] args) {
		TestTransaction test = new TestTransaction();
		test.addAccount(20000);
	}

	public void addAccount(int value) {
		Account account = new Account();
		account.setValue(value);
		openSession();
		if(session != null) {
			try {
				session.saveOrUpdate(account);
				tx.commit();
			} catch (Exception e) {
				logger.error(e, e);
				tx.rollback();
			}
		}
		closeSession();
	}
	
	public void openSession() {
		openSession("hibernate\\hibernate.cfg.xml");
	}
	
	public void openSession(String resource) {
		if(config == null) {
			config = new Configuration().configure(resource);
		}
		if(factory == null) {
			factory = config.buildSessionFactory();
		}
		if(session == null) {
			session = factory.openSession();
			tx = session.beginTransaction();
		}
	}
	
	public void closeSession() {
		if(session != null) {
			session.close();
		}
		if(factory != null) {
			factory.close();
		}
	}
}
