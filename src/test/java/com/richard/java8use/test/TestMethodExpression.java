package com.richard.java8use.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
		cars = Arrays.asList(new Car("Jeep", "Wrangler", 2011), new Car("Jeep", "Comanche", 1990),
				new Car("Dodge", "Avenger", 2010), new Car("Buick", "Cascada", 2016), new Car("Ford", "Focus", 2012),
				new Car("Chevrolet", "Geo Metro", 1992));
	}

	@Test
	public void test01MethodExpression() {
		if (cars != null) {
			List<String> result = cars.stream()
									  .filter(car -> car.getYear() > 2000) // 过滤
									  .sorted(Comparator.comparing(Car::getYear)) // 排序
									  .map(Car::getModel) // 以给定表达式作为map的value值
									  .collect(Collectors.toList()); // 取map的value形成list
			result.forEach(temp -> System.out.println(temp));
		}
	}

	@Test
	public void test02Range() {
		// the range is 0<=i<4, and the variable i is an new value every time cycle
		IntStream.range(0, 4).forEach(i -> {
			System.out.println(i + "..."); 
		});
		// rangeClosed method can reach the max limit value
		IntStream.rangeClosed(0,  4).forEach(i -> {
			System.out.println(i + "---"); 
		});
		// reverse cycle control with iterate and limit method
		IntStream.iterate(7, e -> e - 1).limit(7).forEach(i -> {
			System.out.println("--" + i + "--"); 
		});
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		IntStream.range(0,  5).forEach(i -> executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				// because this variable i is new very time cycle, so it is an effectively final variable argument
				System.out.println("----Running task " + i);
			}
		}));
		// IntStream.range(0,  5).forEach(i -> executorService.submit(() -> System.out.println("------Running task " + i)));
		executorService.shutdown();
	}
	
	@Test
	public void test03PartFinal() {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		for (int i = 0; i < 5; i++) {
			int temp = i;
			executorService.submit(new Runnable() {
				public void run() {
					// If uncommented the next line will result in an error
					// System.out.println("Running task " + i);
					// local variables referenced from an inner class must be
					// final or effectively final
					System.out.println("====Running task " + temp);
				}
			});
			// temp = 5; // if we do not change temp's value, it will be an effectively final variable, if not, we must declare temp with final
		}
		executorService.shutdown();
	}
	
	@Test
	public void test04MethodExpression() {
		// for lambda expression, every cycle will be executed for every method
		IntStream.range(0, 7)
				 .map(Car::increment)
				 .forEach(System.out::println);
		System.out.println("----------");
		List<Car> result = cars.stream()
							   .collect(Collectors.toCollection(ArrayList::new));
		result.forEach(System.out::println);
	}
}

class Car {
	private String make;
	private String model;
	private int year;
	private static int totalNumber = 0;

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
	
	public static int increment(int number) {
		totalNumber += number;
		return totalNumber;
	}
	
	@Override
	public String toString() {
		return getMake() + "---" + getModel() + "---" + getYear();
	}
}