package com.richard.java8use.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	
	/**
	 * 没有指定RequestMapping则默认访问路径为根目录
	 */
	@RequestMapping
	public String index(Model model) {
		model.addAttribute("message", "Welcom to my web site!");
		return "index";
	}
	
	@RequestMapping(path = "/index/download/excel")
	@ResponseBody
	public String downloadExcel(HttpServletResponse response) { // 生成excel，pdf文件也可以通过自定义视图
		// 通过指定http请求head来生成excel文件, 不过这种生成内容格式完全不对; 但是作为例子说明excel文件其实也只是response返回的数据流
		response.setHeader("Content-type","application/octet-stream");
		response.setHeader("Content-Disposition","attachment; filename=table.xls");
		return "<table><tr><td>Hello</td><td>Excel</td></tr></table>";
	}
	
	/**
	 * 在没有指定View或者ResponseBody注解情况下，访问url内容对应的就是相应目录位置下.jsp内容
	 * @return
	 */
	@RequestMapping(path = "/helloworld")
	public Map<String, String> helloWordl() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("message", "Hello everybody!"); // 对于基本数据类型，如果想要作为返回结果，则必须添加ResponseBody注解，否则会抛出异常
		return result;
	}
	
	/**
	 * 对于RequestMapping的路径映射配置, paht/value是等价的, 从语义上来说当然path更容易理解
	 * @return
	 */
	@RequestMapping(path = {"/index", "/index/2"}, method = RequestMethod.GET)
	public ModelAndView queryRecord(@RequestParam("id") String id) {
		ModelAndView result = new ModelAndView("record");
		ReportData temp = reportDataService.queryRecord(id);
		result.addObject("record", temp);
		result.addObject("message", "<h2>Hello ModelAndView</h2>");
		return result;
	}
	
	/**
	 * 在使用RequestBody注解时，前端必须显式指定Content-Type是application/json,不然会抛出415异常，不能使用表单提交
	 * @return
	 */
	@RequestMapping(path = {"/index"}, method = RequestMethod.POST)
	public @ResponseBody Map<String, String> changeRecord(@RequestBody ReportData reportData) {
		Map<String, String> error = new HashMap<String, String>();
		if(reportData != null) {
			boolean result = reportDataService.updateReportDataRecord(reportData);
			error.put("result", String.valueOf(result));
		} else {
			error.put("result", String.valueOf(false));
		}
		return error;
	}
	
	/**
	 * 使用RequestParam注解可以将输入的数组参数自动绑定
	 * @param ids
	 * @return
	 */
	@RequestMapping(path = "/index/postArray", method = RequestMethod.GET)
	public @ResponseBody List<String> useRequestParam(@RequestParam(name = "id", required = true) List<String> ids) {
		return ids;
	}
	
	/**
	 * 前端使用ajax传递的json数据集，可以使用RequestBody注解来转换
	 * @param parameters
	 * @return
	 */
	@RequestMapping(path = "/index/postCollection", method = RequestMethod.POST)
	public @ResponseBody List<Map<String, String>> useRequestBody(@RequestBody List<Map<String, String>> parameters) {
		return parameters;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(path = "/index/redirectAddress", method = RequestMethod.GET)
	public @ResponseBody List<Map<String, String>> testRedirect(Model model) {
		List<Map<String, String>> result = (List<Map<String, String>>) model.asMap().get("parameters");
		if(model.containsAttribute("map")) {
			result.add((Map<String, String>) model.asMap().get("map"));
		}
		return result;
	}
	
	/**
	 * 使用redirect跳转到指定action的同时，使用addFlashAttribute来保证跳转过程中的参数不会丢失
	 * redirect请求类型是GET请求, 整个过程: POST-redirect-GET
	 * @param parameters
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(path = "/index/useRedirect", method = RequestMethod.POST)
	public String useRedirect(@RequestBody List<Map<String, String>> parameters, RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("parameters", parameters);
		return "redirect:redirectAddress"; // 跳转路径是同级路径下的redirectAddress，或者使用/index/redirectAddress，等效
	}
	
	/**
	 * ModelAttribute注解方法会在其他action调用之前被调用,
	 * 并且如果有返回结果，则会把结果保存在action的model对象中,
	 * 多个ModelAttribute注解方法的执行顺序是无序的
	 * @return
	 */
	@ModelAttribute
	public Map<String, String> useModelAttribute() {
		//System.out.println("Action execute of method attribute0");
		Map<String, String> version = new HashMap<String, String>();
		version.put("version", "1.0.0");
		return version;
	}
	
	@ModelAttribute
	public void useModelAttribute1() {
		//System.out.println("Action execute of method attribute1");
	}
	
	@ModelAttribute
	public void useModelAttribute2() {
		//System.out.println("Action execute of method attribute2");
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
	
	@RequestMapping("/fm/ftl")
	public String ftl(Model model) {
		model.addAttribute("users", new String[]{"tom","mark","jack"});
		model.addAttribute("message", "Hello FreeMarker View!");
		Map<String, Object> complexResult = new HashMap<String, Object>();
		ReportData data = new ReportData();
		data.setName("tvu");
		List<ReportData> records = reportDataService.queryReportDataWithLike(data);
		if(records != null) {
			complexResult.put("report", records);
		}
		// ftl中变量名只能是字母,数字,下划线,$,@和#的组合,且不能以数字开头
		model.addAttribute("reportdata", complexResult);
		return "demo";
	}
}
