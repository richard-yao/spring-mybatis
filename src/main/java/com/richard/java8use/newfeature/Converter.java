package com.richard.java8use.newfeature;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月8日 下午4:25:56
* 这个注解用来标注该类是一个函数式接口（并不是强制限制），
* 只有一个未实现方法的接口都是函数式接口
*/
@FunctionalInterface
public interface Converter<E, T> {

	/**
	 * 将E类型转换为T类型
	 * @param str
	 * @return
	 */
	T convert(E str);
	
	default int compare(E str, E str2) {
		return str.hashCode() - str2.hashCode();
	}
}
