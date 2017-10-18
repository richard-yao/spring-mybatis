package com.richard.java8use.generic;

import com.richard.java8use.util.GenerateToken.Person;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年10月18日 上午9:46:48
*/
public class PersonContainer extends ObjectContainer<Person> {

	public PersonContainer(Person contained) {
		super(contained);
	}
	
	@Override
	public void setContained(Person contained) {
		super.setContained(contained);
	}
}