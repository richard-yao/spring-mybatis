package com.richard.java8use.newfeature;

import com.richard.java8use.model.ReportData;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月8日 下午5:28:19
*/
@FunctionalInterface
public interface ReportDataFactory<T extends ReportData> {

	T create(String type, String version, String name);
}
