package com.richard.java8use.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;

/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月9日 下午4:37:49
*/
public class TestXmlPost {

	public static String sendSSLPostRequest(String reqURL, String param, String type, String username, String password) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		BufferedReader reader = null;
		try {
			httpClient = SSLClient.getSSLClient(new DefaultHttpClient());
			HttpParams httpParams = httpClient.getParams();
			httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);// 请求连接超时时间
			httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);// 请求响应超时时间
			httpPost = new HttpPost(reqURL);
			httpPost.setHeader("Content-Type", type);
			String encoding = Base64.getEncoder().encodeToString((username + ":" + password).getBytes(Charset.forName("UTF-8")));
			System.out.println(encoding);
			httpPost.setHeader("Authorization", "Basic " + encoding);
			httpPost.setHeader("User-Agent", "VidyoDesktop");
			httpPost.setHeader("SOAPAction", "logIn");
			StringEntity stringEntity = new StringEntity(param, "UTF-8");
			httpPost.setEntity(stringEntity);

			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				String inputLine = null;
				String resultData = "";
				while (((inputLine = reader.readLine()) != null)) {
					resultData += inputLine;
				}
				reader.close();
				return resultData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpPost != null) {
				httpPost.abort();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(httpClient != null && httpClient.getConnectionManager() != null ) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String url = "http://api.byshang.cn/services/v1_1/VidyoPortalUserService";
		String type = "text/xml;charset=UTF-8";
		String username = "tvu1";
		String password = "tvu1";
		String param = "";
		String path = "E:\\test.xml";
		param = loadXmlParam(path);
		param = param.replaceAll("\\s", "");
		String response = sendSSLPostRequest(url, param, type, username, password);
		System.out.println(response);
	}
	
	public static String loadXmlParam(String path) {
		
		File file = new File(path);
		if(file.isFile() && file.exists()) {
			StringBuilder result;
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				result = new StringBuilder();
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					result.append(lineTxt);
				}
				bufferedReader.close();
				read.close();
				return result.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
