package com.richard.java8use.test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date 2017年10月10日 下午5:46:51
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestMethodExpression {

	List<Car> cars = null;

	@Before
	public void initTest() {
		cars = Arrays.asList(
				new Car("Jeep", "Wrangler", 2011), new Car("Jeep", "Comanche", 1990),
				new Car("Dodge", "Avenger", 2010), new Car("Buick", "Cascada", 2016), 
				new Car("Ford", "Focus", 2012), new Car("Chevrolet", "Geo Metro", 1992));
	}

	@Test
	public void test01MethodExpression() {
		if(cars != null) {
			List<String> result = cars.stream()
				.filter(car -> car.getYear() > 2000) // 过滤
				.sorted(Comparator.comparing(Car::getYear)) // 排序
				.map(Car::getModel) // 以给定表达式作为map的value值
				.collect(Collectors.toList()); // 取map的value形成list
			result.forEach(temp -> System.out.println(temp));
		}
	}
}

class Car {
	private String make;
	private String model;
	private int year;

	public Car(String theMake, String theModel, int yearOfMake) {
		make = theMake;
		model = theModel;
		year = yearOfMake;
	}

	public String getMake() {
		return make;
	}

	public String getModel() {
		return model;
	}

	public int getYear() {
		return year;
	}
}