package com.richard.java8use.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.richard.java8use.dao.ReportDataDao;
import com.richard.java8use.model.ReportData;
import com.richard.java8use.model.SfUser;


/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午3:00:02
*/
@Service("reportDataService")
public class ReportDataService {

	@Autowired
	ReportDataDao reportData;
	
	public ReportData repeatQueryRecord(String id) {
		// 由于Cacheable实现基于Spring AOP，对于方法内部调用queryRecord(xx)方法，AOP代理无法生效，spring cache自然也没法使用到了
		return queryRecord(id);
	}
	
	// Cacheable注解，如果在cache中命中则不再执行方法内容； 
	@Cacheable(value = "accountCache", key = "targetClass + '.' + #id", condition = "#id.length() == 16")
	public ReportData queryRecord(String id) {
		return reportData.queryReportDataWithId(id);
	}
	
	public List<Map<String, String>> queryRecordWithName(String name) {
		return reportData.queryReportDataWithName(name);
	}
	
	public List<ReportData> queryReportDataWithLike(ReportData data) {
		return reportData.queryReportDataWithLike(data);
	}
	
	public List<ReportData> queryReportDataWithIds(String[] ids) {
		return reportData.queryReportDataWithIds(ids);
	}
	
	@CacheEvict(value = "accountCache", key = "targetClass + '.' + #record.id")
	public boolean createReportDataRecord(ReportData record) {
		int number = reportData.createReportDataRecord(record);
		if(number > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@CacheEvict(value = "accountCache", key = "targetClass + '.' + #data.id")
	public boolean updateReportDataRecord(ReportData data) {
		int number = reportData.updateReportDataRecord(data);
		if(number > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	// 将方法执行结果作为value存入accountCache中，每次都会执行方法部分
	@CachePut(value = "accountCache", key = "targetClass + '.' + #data.id")
	public ReportData mergeReportDataRecord(ReportData data) {
		int number = reportData.updateReportDataRecord(data);
		if(number > 0) {
			return data;
		} else {
			number = reportData.createReportDataRecord(data);
			if(number > 0) {
				return data;
			} else {
				return null;
			}
		}
	}
	
	// 清理accountCache中的所有缓存对象;beforeInvocation表示在方法执行前清空缓存，因为CacheEvict会因注解方法抛出异常而终止执行
	@CacheEvict(value = "accountCache", allEntries = true, beforeInvocation = true)
	public boolean deleteReportDataRecord(ReportData data) {
		int number = reportData.deleteReportDataRecord(data);
		if(number > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public List<ReportData> queryAccountOwnDevices(String accountId) {
		return reportData.queryAccountOwnDevices(accountId);
	}
	
	public List<ReportData> queryReportDataWithPage(String id, int offset, int number) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		params.put("offset", offset);
		params.put("number", number);
		return reportData.queryReportDataWithPage(params);
	}
	
	public SfUser queryAccountAndDevices(String accountId) {
		return reportData.queryAccountAndDevices(accountId);
	}
	
	public boolean createNewSfRecords(SfUser sfUser) {
		int insertNumber = reportData.createNewSfRecords(sfUser);
		if(insertNumber > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean createMultipleNewSfRecords(List<SfUser> list) {
		int insertNumber = reportData.createMultipleNewSfRecords(list);
		if(insertNumber > 0 && insertNumber == list.size()) {
			return true;
		} else {
			return false;
		}
	}
}
