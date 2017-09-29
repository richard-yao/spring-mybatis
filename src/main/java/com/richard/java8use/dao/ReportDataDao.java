package com.richard.java8use.dao;

import java.util.List;
import java.util.Map;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.model.SfUser;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午2:23:50
* 接口可以多继承接口，不能多继承抽象类
*/
public interface ReportDataDao extends BaseDao, DataDao {
	
	/**
	 * 在jdk1.8中，接口是可以有static/default方法块的
	 */
	default void init() {
		System.out.println(123);
	}

	ReportData queryReportDataWithId(String id); // 接口中的方法默认都是abstract修饰的
	
	List<Map<String, String>> queryReportDataWithName(String name); // 即便mybatis的动态sql很强大，但是对于返回多条map记录的结果也必须指定结果是list，否则会抛出异常
	
	List<ReportData> queryReportDataWithLike(ReportData data);
	
	List<ReportData> queryReportDataWithIds(String[] ids);
	
	int createReportDataRecord(ReportData data);
	
	int updateReportDataRecord(ReportData data);
	
	List<ReportData> queryAccountOwnDevices(String accountId);
	
	List<ReportData> queryReportDataWithPage(Map<String, Object> params);
	
	SfUser queryAccountAndDevices(String accountId);
	
	int createNewSfRecords(SfUser sfUser);
	
	int createMultipleNewSfRecords(List<SfUser> list);
	
	int deleteReportDataRecord(ReportData data);
}
