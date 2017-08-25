package com.richard.java8use.context;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月25日 下午3:02:39
*/

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;

import com.richard.java8use.annotation.ItcastResource;
import com.richard.java8use.service.ArticleService;
import com.richard.java8use.service.ItcastService;

public class ItcastClassPathXmlApplicationContext {

	// store all bean definitions
	private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
	// cache all singleton beans
	private Map<String, Object> sigletons = new HashMap<String, Object>();

	public ItcastClassPathXmlApplicationContext() {
	}

	public ItcastClassPathXmlApplicationContext(String configFile) {
		this.readXml(configFile);
		this.instanceBeans();
		this.annotationInject();
	}

	private void readXml(String configFile) {
		// 这里用来读取bean定义文件并转化为BeanDefinition对象同时把这些对象缓存到beanDefines中, 为了简单直接手动输入了
		BeanDefinition bean = new RootBeanDefinition(ItcastService.class);
		bean.setBeanClassName(ItcastService.class.getName());
		String simpleName = toLowerCaseFirstOne(ItcastService.class.getSimpleName());
		bean.setAttribute("id", simpleName);
		beanDefines.add(bean);

		BeanDefinition bean2 = new RootBeanDefinition(ArticleService.class);
		bean2.setBeanClassName(ArticleService.class.getName());
		simpleName = toLowerCaseFirstOne(ArticleService.class.getSimpleName());
		bean2.setAttribute("id", simpleName);
		beanDefines.add(bean2);
	}

	private String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 进行bean的实例化
	 */
	private void instanceBeans() {
		for (BeanDefinition beanDefinition : beanDefines) {
			if (beanDefinition.getBeanClassName() != null && !"".equals(beanDefinition.getBeanClassName())) {
				try {
					sigletons.put((String) beanDefinition.getAttribute("id"),
							Class.forName(beanDefinition.getBeanClassName()).newInstance());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 处理注解给bean中属性和字段注入依赖
	 */
	private void annotationInject() {
		for (String beanName : sigletons.keySet()) {
			Object bean = sigletons.get(beanName); // 获取全局bean对象，循环处理
			if (bean != null) {
				try {
					// 对bean的属性进行处理
					PropertyDescriptor[] ps = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
					for (PropertyDescriptor properdesc : ps) {
						Method setter = properdesc.getWriteMethod();
						if(setter != null) {
							System.out.println(setter.getName());
						}
						if (setter != null && setter.isAnnotationPresent(ItcastResource.class)) {
							ItcastResource resource = setter.getAnnotation(ItcastResource.class);
							Object value = null;
							if (resource.name() != null && !"".equals(resource.name())) {
								value = sigletons.get(resource.name()); // 根据注解的name获取对应bean
							} else {
								value = sigletons.get(properdesc.getName()); // 根据字段名称来找bean
								if (value == null) { // 如果字段名不匹配，则根据字段类型来找bean
									for (String key : sigletons.keySet()) {
										if (properdesc.getPropertyType()
												.isAssignableFrom(sigletons.get(key).getClass())) {
											value = sigletons.get(key);
											break;
										}
									}
								}
							}
							setter.setAccessible(true);
							setter.invoke(bean, value);
						}
					}

					// 对bean的字段进行处理
					Field[] fields = bean.getClass().getDeclaredFields();
					for (Field field : fields) {
						if (field.isAnnotationPresent(ItcastResource.class)) {
							ItcastResource resource = field.getAnnotation(ItcastResource.class);
							Object value = null;
							if (resource.name() != null && !"".equals(resource.name())) {
								value = sigletons.get(resource.name()); // 根据注解的name获取对应bean
							} else {
								value = sigletons.get(field.getName()); // 根据字段名称来找bean
								if (value == null) { // 如果字段名不匹配，则根据字段类型来找bean
									for (String key : sigletons.keySet()) {
										if (field.getType().isAssignableFrom(sigletons.get(key).getClass())) {
											value = sigletons.get(key);
											break;
										}
									}
								}
							}
							field.setAccessible(true);
							field.set(bean, value);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Object getBean(String beanName) {
		return sigletons.get(beanName);
	}
}
