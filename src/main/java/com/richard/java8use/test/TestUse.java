package com.richard.java8use.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年6月9日 下午2:21:54
*/
public class TestUse {
	
	static int number = 10;

	public static void main(String[] args) {
		useParameter();
		System.out.println("Instance number is " + number);
		String[] array = {"123","wha's your name","richard","well","sort function","richard"};
		Arrays.sort(array, (val1, val2) -> (val1.compareTo(val2) >= 0 ? -1 : 1));
		
		List<String> test = Arrays.asList(array);
		
		test.forEach(elm -> System.out.println(elm));
		//test.forEach(System.out::println);
		
		Thread thread = new Thread(() -> System.out.println("what"));
		thread.start();
		
		CountDownLatch val = new CountDownLatch(10);
		Runnable runnable = () -> {
			while(val.getCount() >0) {
				System.out.println("My name is " + val.getCount());
				val.countDown();
			}
		};
		runnable.run();
		
		Integer[] numArray = {123, 32, 43, 24, 3, 5, 26, 17, 58};
		Integer result = Arrays.asList(numArray).stream().min((val1, val2) -> val1 - val2).get();
		System.out.println("The min value is " + result);
		List<Integer> list = Arrays.asList(numArray).stream().sorted((v1, v2) -> v1 - v2).collect(Collectors.toList());
		list.forEach(elm -> System.out.print(elm+"\t"));
		
		Map<String, String> testMap = new HashMap<String, String>();
		testMap.put("123", "Something");
		testMap.forEach((key, value) -> testMapValue(key, value));
	}
	
	public static void testMapValue(String key, String value) {
		System.out.println(key + value);
	}
	
	public static void useParameter() {
		String para = "oneTwoThree";
		Runnable runnable = () -> {
			number = 20; // 对于实例的字段已经静态变量是可读可写的
			System.out.println(para); // lambda表达式中的para实际上是等效final参数，虽然无需final修饰但是也必须要不可更改(局部变量)
		};
		runnable.run();
		//para = "name";
	}

}
