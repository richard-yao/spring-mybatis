package com.richard.java8use.test;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月8日 下午3:25:29
*/

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.newfeature.Converter;
import com.richard.java8use.newfeature.Formula;
import com.richard.java8use.newfeature.Formula2;
import com.richard.java8use.newfeature.ReportDataFactory;
import com.richard.java8use.util.GenerateToken;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestCase {

	
	@Test
	public void test1InterfaceWithDefault() {
		Formula formula = new Formula() {
			
			@Override
			public double calculate(int number) {
				return sqrt(number * 100); // interface中的default方法是可以直接使用的,无需实现
			}
		};
		System.out.println("Test default: " + formula.calculate(100));
		System.out.println("Test default: " + formula.sqrt(100));
	}
	
	@Test
	public void test2ImplTwoInterfaces() {
		FormulaTest test = new FormulaTest();
		System.out.println("Test two interface: " + test.calculate(-100));
		System.out.println("Test two interface: " + test.sqrt(100));
	}
	
	@Test
	public void test3UseLambda() {
		// this list is fixed-size list which means you cannot add or remove element, but you can reset element value
		List<String> list = Arrays.asList("peter", "anna", "make");
		Collections.sort(list, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		System.out.println("Test three: " + Collections.singletonList(list));
		list = Arrays.asList("peter", "anna", "make", "123");
		Collections.sort(list, (a, b) -> { 
			return a.compareTo(b); 
		});
		System.out.println("Test three: " + Collections.singletonList(list));
		list = Arrays.asList("peter", "anna", "make", "123", "321");
		Collections.sort(list, String::compareTo);
		System.out.println("Test three: " + Collections.singletonList(list));
	}
	
	@Test
	public void test4UseLambda() {
		/*Object rObject = (Runnable) () -> System.out.println("hello, lambda");
		Runnable test = (Runnable) rObject;*/
		// 这里的lambda语句没法直接赋值给Object，因为编译器会检查引用类型是否真有这么一个函数式接口
		Runnable test = () -> System.out.println("Test four: " + "Hello, lambda"); 
		test.run();
	}
	
	@Test
	public void test5UseFunctionInterface() {
		Converter<String, Integer> converter = (str) -> Integer.valueOf(str);
		Integer integer = converter.convert("321");
		System.out.println("Test five: " + integer);
		System.out.println("Test five: " + converter.compare("3", "6"));
	}
	
	@Test
	public void test6InnerClassUse() {
		HelloNormal helloNormal = new HelloNormal();
		helloNormal.runnable.run();
	}
	
	@Test
	public void test7LambdaInnerUse() {
		HelloLambda helloLambda = new HelloLambda();
		helloLambda.runnable.run();
	}
	
	@Test
	public void test8LambdaUseConstructor() {
		ReportDataFactory<ReportData> factory = ReportData::new; // lambda表达式构造函数引用
		ReportData record = factory.create("T", "4.2.8", "richard-test");
		System.out.println(record.getType() + record.getName() + record.getVersion());
	}
	
	@Test
	public void test9TestInterviewQuestions() {
		System.out.println(-1 >>> 2); // 无符号右移
		System.out.println(2 >> 1);
		System.out.println(-2 >> 1);
	}
	
	@Test
	public void test10TestGenerateToken() {
		String key = "42936e5715ef4ebf8588452ca09f1a6e";
		String appID = "3c8146.vidyo.io";
		String userName = "richardyao";
		String expiresInSecs = "10000";
		String token = GenerateToken.generateProvisionToken(key, userName + "@" + appID, expiresInSecs, "");
		System.out.println(token);
	}
	
	@Test
	public void test11UseShort() {
		short s1 = 1;
		s1 = (short) (s1 + 1); // short是16位二进制数据，1是int类型，32位，因此基础运算需要加上类型强制转换
		short s2 = 1;
		s2 += 1; // += 操作隐含了强制类型转换
		Assert.assertTrue(s1 == s2);
	}
	
	@Test
	public void test12UseIntern() {
		String s2 = new StringBuilder("ja").append("va").toString();
		Assert.assertFalse(s2.intern() == s2); // java这个字符串，在程序启动时jvm中就有实现了导致intern取到的是默认常量池中的字符而不是自己生成的这个
		String s1 = new StringBuilder("go").append("od").toString();
		Assert.assertTrue(s1.intern() == s1);
	}
	
	@Test
	public void test13EndLoop() {
		int number = 0;
		for(int i = 0; i < 10; i++) {
			for(int j = 1; j < 5; j++) {
				if(j % 3 == 0 && number == 0) {
					number = j;
					//return; // return直接结束方法 , 不再执行
					break; // break只能跳出一层循环
				}
			}
			number++;
		}
		System.out.println("Number is " + number);
		Assert.assertEquals(13, number);
	}
	
	@Test
	public void test14Equal() {
		String s1 = "Programming";
		String s2 = new String("Programming");
		String s3 = "Program";
		String s4 = "ming";
		String s5 = "Program" + "ming";
		String s6 = s3 + s4;
		Assert.assertFalse(s1 == s2);
		Assert.assertTrue(s1 == s5);
		Assert.assertFalse(s1 == s6);
		Assert.assertTrue(s1 == s6.intern());
		Assert.assertFalse(s2 == s2.intern());
	}
}

/**
 * 对于多接口中抽象方法和default方法相同定义的情况，需要重写该方法
 * 对于两个接口中有相同default方法，定义一个类同时实现这两个接口也需要复写defult方法
 * @author RichardYao
 *
 */
class FormulaTest implements Formula, Formula2 {

	@Override
	public double calculate(int number) {
		return Formula2.super.calculate(number);
	}

	@Override
	public double sqrt(int number) {
		return Formula.super.sqrt(number);
	}
}

class HelloNormal {
	
	public Runnable runnable = new Runnable() {
		
		@Override
		public void run() {
			System.out.println("-->1 " + this); // 这里的this是匿名对象的类
			System.out.println("-->2 " + toString());
			
			System.out.println("++1 " + HelloNormal.this); // 这里的this才是HelloNormal对象的类
			System.out.println("++2 " + HelloNormal.this.toString());
		}
	};
	
	@Override
	public String toString() {
		return "Hello's custom toString()";
	}
}

class HelloLambda {
	
	public Runnable runnable = () -> {
		System.out.println(this); // lambda表达式中访问this其实是HelloLambda对象
		System.out.println(toString());
	};
	
	@Override
	public String toString() {
		return "Hello's custom toString()";
	}
}