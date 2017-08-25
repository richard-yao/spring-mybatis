package com.richard.java8use.test;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月25日 下午4:31:45
*/

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.richard.java8use.context.ItcastClassPathXmlApplicationContext;
import com.richard.java8use.model.Article;
import com.richard.java8use.service.ItcastService;

public class TestContext {

	@Test
	public void testDefineContext() {
		ItcastClassPathXmlApplicationContext context = new ItcastClassPathXmlApplicationContext("");
		ItcastService service = (ItcastService) context.getBean("itcastService");
		if(service != null) {
			List<Article> result = service.getArticles();
			Assert.assertEquals(5, result.size());
		}
	}
}
