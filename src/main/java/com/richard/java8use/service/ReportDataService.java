package com.richard.java8use.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
	
	public ReportData queryRecord(String id) {
		return reportData.queryReportDataWithId(id);
	}
	
	public List<ReportData> queryReportDataWithLike(ReportData data) {
		return reportData.queryReportDataWithLike(data);
	}
	
	public List<ReportData> queryReportDataWithIds(String[] ids) {
		return reportData.queryReportDataWithIds(ids);
	}
	
	public boolean createReportDataRecord(ReportData record) {
		int number = reportData.createReportDataRecord(record);
		if(number > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean updateReportDataRecord(ReportData data) {
		int number = reportData.updateReportDataRecord(data);
		if(number > 0) {
			return true;
		} else {
			return false;
		}
	}
	
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
