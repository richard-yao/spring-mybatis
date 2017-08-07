package com.richard.java8use.test;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月7日 下午4:19:51
* 抽象类可以没有抽象方法，有抽象方法的一定是抽象类
*/
public abstract class BaseAbstractClass {

	/**
	 * 抽象类必须有构造函数，因为子类在继承抽象类并实例化时，需要调用到父类的构造函数
	 */
	public BaseAbstractClass() {
		System.out.println(111);
	}
}
