package com.richard.java8use.test;

import org.junit.Assert;
import org.mockito.Mockito;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.richard.java8use.model.ReportData;
import com.richard.java8use.service.ReportDataService;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月23日 下午4:26:10
*/
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional // 开启事务回滚以保证数据库数据完整
@ContextConfiguration(locations={"classpath:spring/spring-service.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestReportDataService {
	
	@Resource(name = "reportDataService")
	private ReportDataService reportDataService;

	@Before
	public void beforeTest() {
		System.out.println("Start service test");
	}
	
	@Test
	public void test1_queryRecord() {
		String id = "0000000000000000";
		ReportData result = reportDataService.queryRecord(id);
		Assert.assertEquals(result.getId(), id);
	}
	
	@Test
	public void test2_queryReportDataWithLike() {
		ReportData mock = Mockito.mock(ReportData.class);
		Mockito.when(mock.getName()).thenReturn("ios");
		List<ReportData> result = reportDataService.queryReportDataWithLike(mock);
		Assert.assertEquals(result.size(), 4);
	}
	
	@Test
	public void test3_deleteReportDataRecord() { // 事务在该测试用例中存在，在新的测试用例中开启新的事务
		ReportData mock = Mockito.mock(ReportData.class);
		String id = "0000000000000000";
		Mockito.when(mock.getId()).thenReturn(id);
		boolean result = reportDataService.deleteReportDataRecord(mock);
		Assert.assertTrue(result);
		ReportData record = reportDataService.queryRecord(id);
		Assert.assertNull(record);
	}
	
	@Test
	public void test4_queryRecord() {
		String id = "0000000000000000";
		ReportData result = reportDataService.queryRecord(id);
		Assert.assertNotNull(result);
	}
}
