package com.richard.java8use.hibernate;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.richard.java8use.model.Account;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月28日 下午3:30:57
*/
public class TestTransaction {
	
	private Logger logger = LoggerFactory.getLogger(TestTransaction.class);
	
	private Configuration config = null;
	private SessionFactory factory = null;
	private Session session = null;
	private Transaction tx = null;
	
	public static void main(String[] args) {
		TestTransaction test = new TestTransaction();
		test.openSession();
		test.addAccount(2000);
		List<Integer> params = Arrays.asList(new Integer[]{1,2,3,4,5,6,7,8,9,10});
		test.queryListAccount(params);
		test.queryListAccount(params); // The query.list() do not use first-level cache
		test.queryAccount(9); // use first-level cache
		test.transactionCommit();
		test.closeSession();
	}
	
	public void queryListAccount(List<Integer> ids) {
		String hql = "select a from Account a where a.id in (:ids) ";
		if(session != null && ids != null) {
			Query<Account> query = session.createQuery(hql, Account.class);
			query.setParameterList("ids", ids);
			List<Account> resultList = query.list();
			if(resultList != null && resultList.size() > 0) {
				resultList.forEach(temp -> System.out.println(temp.toString()));
			}
		}
	}

	public void addAccount(int value) {
		Account account = new Account();
		account.setId(10);
		account.setValue(value);
		if(session != null) {
			try {
				session.saveOrUpdate(account); // exist id=10 record, update
				Account record = session.get(Account.class, 10); // used hibernate first-level cache
				logger.info("Query record: " + record.toString());
			} catch (Exception e) {
				logger.error("Exception", e);
				tx.rollback();
			}
		}
	}
	
	public void queryAccount(int id) {
		Account record = session.get(Account.class, id); // used hibernate first-level cache
		logger.info("Query record: " + record.toString());
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
	
	public void transactionCommit() {
		if(tx != null) {
			tx.commit();
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
