package com.richard.java8use.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.richard.java8use.dao.DataDao;
import com.richard.java8use.model.Account;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月29日 下午5:01:33
*/
@Service("dataService")
public class DataService {

	@Autowired
	private DataDao dataDao;
	
	public boolean insertAccountRecord(Account account) {
		int result = dataDao.createAccountRecord(account);
		return result == 0 ? false : true;
	}
}
