package com.richard.java8use.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.richard.java8use.model.Account;
import com.richard.java8use.service.DataService;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月30日 上午11:30:22
*/
@Controller
public class DataController {

	@Autowired
	private DataService dataService;
	
	@RequestMapping(value = "/update/account/{id}-{value}", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> updateRecord(@PathVariable int id, @PathVariable int value) {
		Account account = new Account();
		if(id > 0) {
			account.setId(id);
		} else {
			account.setId(0);
		}
		account.setValue(value);
		boolean result = dataService.updateAccountRecord(account);
		Map<String, String> response = new HashMap<String, String>();
		response.put("result", String.valueOf(result));
		return response;
	}
}
