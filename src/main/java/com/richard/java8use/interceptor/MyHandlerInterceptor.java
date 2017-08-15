package com.richard.java8use.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.service.ReportDataService;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月15日 下午1:12:24
*/
public class MyHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	ReportDataService reportDataService;
	
	/**
	 * 该方法也是需要当前对应的Interceptor的preHandle方法的返回值为true时才会执行。该方法将在整个请求完成之后，也就是DispatcherServlet渲染了视图执行,
	 * 这个方法的主要作用是用于清理资源的，当然这个方法也只能在当前这个Interceptor的preHandle方法的返回值为true时才会执行
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception exception)
			throws Exception {
		
	}

	/**
	 * 这个方法只会在当前这个Interceptor的preHandle方法返回值为true的时候才会执行。postHandle是进行处理器拦截用的，它的执行时间是在处理器进行处理之后，也就是在Controller的方法调用之后执行，
	 * 但是它会在DispatcherServlet进行视图的渲染之前执行，也就是说在这个方法中你可以对ModelAndView进行操;
	 * 这个方法的链式结构跟正常访问的方向是相反的，也就是说先声明的Interceptor拦截器该方法反而会后调用
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handle, ModelAndView modelAndView)
			throws Exception {
		if(request.getAttribute("requestTime") != null) {
			Long time = (Long) request.getAttribute("requestTime");
			Long now = System.currentTimeMillis();
			Long executeTime = now - time;
			System.out.println("The request uri: " + request.getRequestURI() + ", cost time: " + executeTime + "ms");
		}
		if(modelAndView != null) { // 对于返回结果是ModelAndView的接口，这里可以对ModelAndView进行操作
			Map<String, Object> model = modelAndView.getModel();
			String viewName = modelAndView.getViewName();
			if(reportDataService != null && model != null && model.containsKey(viewName)) {
				try {
					ReportData result = (ReportData) model.get(viewName);
					String id = result.getId();
					ReportData data = reportDataService.queryRecord(id); // 在interceptor中注入bean是可以的
					System.out.println(data.getId());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * preHandle方法在Controller处理前调用，由于interceptor是链式的，spring会按照顺序执行，并且所有的interceptor的preHandle方法都是在Controller之前调用;
	 * 当返回结果是false时，interceptor处理中断
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		Long time = System.currentTimeMillis();
		request.setAttribute("requestTime", time);
		return true;
	}

}
