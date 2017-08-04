package com.richard.java8use.dao;

import java.util.List;
import java.util.Map;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.model.SfUser;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午2:23:50
*/
public interface ReportDataDao {

	ReportData queryReportDataWithId(String id);
	
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
