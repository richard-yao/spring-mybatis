package com.richard.java8use.model;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月24日 上午11:02:25
*/
public class Article {

	/*
	 * 文章编号
	 */
	private int id;
	
	/*
	 * 文章标题
	 */
	private String title;
	
	/*
	 * 文章内容
	 */
	private String content;
	
	public Article() {}
	
	public Article(int id, String title, String content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Article [id=" + id + ", title=" + title + ", content=" + content + "]";
	}
}
