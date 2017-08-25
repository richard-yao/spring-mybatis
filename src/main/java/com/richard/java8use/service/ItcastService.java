package com.richard.java8use.service;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月25日 下午2:59:11
*/

import java.util.List;

import com.richard.java8use.annotation.ItcastResource;
import com.richard.java8use.model.Article;

public class ItcastService {

	@ItcastResource
	private ArticleService articleService;
	
	public void setArticleService(ArticleService articleService) {
		this.articleService = articleService;
	}

	public List<Article> getArticles() {
		return articleService.getArticles();
	}
}
