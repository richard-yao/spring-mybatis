package com.richard.java8use.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

/**
 * @author RichardYao richardyao@tvunetworks.com
 * @date 2017年8月10日 下午5:08:22
 */
public class GenerateToken {

	public static final String PROVISION_TOKEN = "provision";
	private static final long EPOCH_SECONDS = 62167219200l;
	private static final String DELIM = "\0";

	public static String generateProvisionToken(String key, String jid, String expires, String vcard)
			throws NumberFormatException {
		String payload = String.join(DELIM, PROVISION_TOKEN, jid, calculateExpiry(expires), vcard);
		return new String(
				Base64.encodeBase64((String.join(DELIM, payload, HmacUtils.hmacSha384Hex(key, payload))).getBytes()));
	}

	public static String calculateExpiry(String expires) throws NumberFormatException {
		long expiresLong = 0l;
		long currentUnixTimestamp = System.currentTimeMillis() / 1000;
		expiresLong = Long.parseLong(expires);
		return "" + (EPOCH_SECONDS + currentUnixTimestamp + expiresLong);
	}
	
	/**
	 * 静态代码块中的方法在类初始化时被调用一次;
	 * 未实例化而在静态方法被调用时也会执行一次（只有一次);
	 * 实例化子类时会先调用父类的静态代码块,再执行子类的静态代码块
	 */
	static {
		System.out.println("initialize GenerateToken class");
	}
	
	/**
	 * 静态内部类的使用和普通的类一样，可以实例化而不受外部类限制
	 * 并且不会像静态方法块或者静态成员那样直接初始化，需要new单独实例化
	 * @author RichardYao
	 */
	public static class Person {
		
		private String name;
		
		public Person() {
			System.out.println("Start initialize static class of Person");
		}
		
		public Person(String name) {
			this();
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return "name parameter: " + getName();
		}
	}
	
	public class Card {
		
		private int number;
		
		public Card() {
			System.out.println("Start initialize static class of Card");
		}
		
		public Card(int number) {
			this();
			this.number = number;
		}
		
		public int getNumber() {
			return this.number;
		}
	}
}
