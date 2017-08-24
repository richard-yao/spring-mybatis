package com.richard.java8use.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.richard.java8use.model.Article;
import com.richard.java8use.service.ArticleService;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月24日 上午11:23:06
*/
public class NewsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7074711853886572135L;

	private ArticleService articleService = new ArticleService();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html; charset=UTF-8");
		String indexPath = request.getServletContext().getRealPath("newslist.html");
		File file = new File(indexPath);
		if(!file.exists()) {
			// 创建freemarker.template.Configuration实例
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
			// 获得模板路径
			String templatePath = request.getServletContext().getRealPath("/WEB-INF/ftl");
			try {
				cfg.setDirectoryForTemplateLoading(new File(templatePath));
				cfg.setDefaultEncoding("UTF-8");
				
				Map<String, Object> articleData = new HashMap<String, Object>();
				List<Article> articles = articleService.getArticles();
				articleData.put("articles", articles);
				
				Template template = cfg.getTemplate("newsList.ftl");
				Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
				try {
					template.process(articleData, writer);
					writer.flush();
					articleData.clear();
					
					template = cfg.getTemplate("news.ftl");
					// 生成单个新闻页面
					for(Article article : articles) {
						articleData.put("article", article);
						file = new File(request.getServletContext().getRealPath("/html/" + article.getId() + ".html"));
						writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
						template.process(articleData, writer);
						writer.flush();
					}
					writer.close();
				} catch (TemplateException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			request.getRequestDispatcher("newslist.html").forward(request,  response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
