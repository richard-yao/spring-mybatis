package com.richard.java8use.test;

import org.junit.Test;
import com.richard.java8use.generic.ObjectContainer;
import com.richard.java8use.generic.PersonContainer;
import com.richard.java8use.util.GenerateToken.Person;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年10月18日 上午9:44:54
*/
public class TestGenericType {

	@Test
	public void testSetContainedPersion() {
		PersonContainer pc = new PersonContainer(new Person("Richard"));
		// if do not specify generic type is Person, 
		// oc.setContainer will accept Object parameter which lead to ClassCastException
		ObjectContainer<Person> oc = pc;
		System.out.println("PersonContainer (through ObjectContainer): " + oc.toString());
		// oc.setContained("String"); // This code will be warned if do not specify generic type
	}
}
