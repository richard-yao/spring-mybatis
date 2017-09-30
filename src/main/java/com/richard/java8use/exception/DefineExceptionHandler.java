package com.richard.java8use.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年9月30日 下午1:41:03
*/
public class DefineExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(DefineExceptionHandler.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("Exception: ", ex);
		ModelAndView result = new ModelAndView("helloworld");
		result.addObject("message", ex);
		return result;
	}
}
