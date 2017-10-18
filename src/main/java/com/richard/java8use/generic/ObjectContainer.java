package com.richard.java8use.generic;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年10月18日 上午9:37:01
*/
public class ObjectContainer<T> {

	private T contained;

	public ObjectContainer(T contained) {
		super();
		this.contained = contained;
	}

	public T getContained() {
		return contained;
	}

	public void setContained(T contained) {
		this.contained = contained;
	}
	
	@Override
	public String toString() {
		return contained.toString();
	}
}
