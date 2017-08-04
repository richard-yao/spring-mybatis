package com.richard.java8use.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.model.SfUser;
import com.richard.java8use.service.ReportDataService;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年7月24日 下午3:04:48
*/
@Controller
public class ReportDataController {

	@Autowired
	private ReportDataService reportDataService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView queryRecord(@RequestParam("id") String id) {
		ModelAndView result = new ModelAndView("record");
		ReportData temp = reportDataService.queryRecord(id);
		result.addObject("record", temp);
		return result;
	}
	
	@RequestMapping(value = "/report-data/query", method = RequestMethod.GET)
	public @ResponseBody List<ReportData> queryRecordWithLike(@RequestParam(required = false, value = "id") String id, 
			@RequestParam(required = false, value = "type") String type, @RequestParam(required = false, value = "version") String version,
			@RequestParam(required = false, value = "name") String name, @RequestParam(required = false, value = "ids") String[] ids) {
		ReportData temp = new ReportData();
		temp.setId(id);
		temp.setName(name);
		temp.setVersion(version);
		temp.setType(type);
		List<ReportData> result = null;
		if(ids != null) {
			result = reportDataService.queryReportDataWithIds(ids);
		} else {
			result = reportDataService.queryReportDataWithLike(temp);
		}
		return result;
	}
	
	@RequestMapping(value = "/report-data/create", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> createRecord() {
		ReportData data = new ReportData();
		data.setName("oracle");
		data.setType("T");
		data.setVersion("6.0.0.1");
		boolean result = reportDataService.createReportDataRecord(data);
		Map<String, Object> tempResult = new HashMap<String, Object>();
		tempResult.put("result", result);
		return tempResult;
	}
	
	@RequestMapping(value = "/report-data/update", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> updateRecord(@RequestParam("id") String id) {
		ReportData data = new ReportData();
		data.setName("oracle");
		data.setType("T");
		data.setVersion("6.0.0.1");
		data.setId(id);
		boolean result = reportDataService.updateReportDataRecord(data);
		Map<String, Object> tempResult = new HashMap<String, Object>();
		tempResult.put("result", result);
		return tempResult;
	}
	
	@RequestMapping(value = "/report-data/delete", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> deleteRecord() {
		ReportData data = new ReportData();
		data.setName("oracle");
		data.setType("T");
		data.setVersion("6.0.0.1");
		boolean result = reportDataService.deleteReportDataRecord(data);
		Map<String, Object> tempResult = new HashMap<String, Object>();
		tempResult.put("result", result);
		return tempResult;
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public @ResponseBody ReportData queryReportData(@RequestParam("id") String id) {
		ReportData result = reportDataService.queryRecord(id);
		return result;
	}
	
	@RequestMapping(value = "/list/query", method = RequestMethod.GET)
	public @ResponseBody List<ReportData> queryAccountOwnDevices(@RequestParam("accountId") String accountId) {
		List<ReportData> result = reportDataService.queryAccountOwnDevices(accountId);
		return result;
	}
	
	@RequestMapping(value = "/list/pageQuery", method = RequestMethod.GET)
	public @ResponseBody List<ReportData> queryReportDataWithPage(@RequestParam("id") String id, 
			@RequestParam("offset") String offset, @RequestParam("number") String number) {
		id = "".equals(id) ? null : id;
		int realOffset = offset != null && !"".equals(offset) ? Integer.parseInt(offset) : 0;
		int realNumber = number != null && !"".equals(number) ? Integer.parseInt(number) : 10;
		List<ReportData> result = reportDataService.queryReportDataWithPage(id, realOffset, realNumber);
		return result;
	}
	
	@RequestMapping(value = "/sf/account/devices", method = RequestMethod.GET)
	public @ResponseBody SfUser queryAccountAndDevices(@RequestParam("accountId") String accountId) {
		SfUser result = reportDataService.queryAccountAndDevices(accountId);
		return result;
	}
	
	@RequestMapping(value = "/sf/account/create", method = RequestMethod.GET)
	public @ResponseBody SfUser createNewSfAccount(@RequestParam("accountId") String accountId, 
			@RequestParam("name") String name, @RequestParam("type") String type, 
			@RequestParam("salesRegion") String salesRegion) {
		SfUser temp = new SfUser();
		temp.setAccountId(accountId);
		temp.setName(name);
		temp.setType(type);
		temp.setSalesRegion(salesRegion);
		boolean result = reportDataService.createNewSfRecords(temp);
		if(result) {
			System.out.println("Create new record successfully!");
		}
		return temp;
	}
	
	@RequestMapping(value = "/sf/account/createMultiple", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> createMultipleNewSfAccount(@RequestParam("accountId") String accountId, 
			@RequestParam("name") String name, @RequestParam("type") String type, 
			@RequestParam("salesRegion") String salesRegion) {
		List<SfUser> list = new ArrayList<SfUser>();
		for(int i = 0; i < 3; i++) {
			SfUser temp = new SfUser();
			temp.setAccountId(accountId);
			temp.setName(name);
			temp.setType(type);
			temp.setSalesRegion(salesRegion);
			list.add(temp);
		}
		boolean result = reportDataService.createMultipleNewSfRecords(list);
		if(result) {
			System.out.println("Create new record successfully!");
		}
		Map<String, Object> tempResult = new HashMap<String, Object>();
		tempResult.put("result", result);
		return tempResult;
	}
}
