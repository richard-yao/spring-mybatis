package com.richard.java8use.dao;

import org.apache.ibatis.annotations.Update;

import com.richard.java8use.model.Account;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月7日 下午4:15:47
*/
public interface DataDao {

	int createAccountRecord(Account account);
	
	@Update("update account set value=#{value} where id=#{id}")
	int updateAccountRecord(Account account);
}
