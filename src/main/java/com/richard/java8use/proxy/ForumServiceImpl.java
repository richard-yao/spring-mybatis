package com.richard.java8use.proxy;
/**
* @author RichardYao richardyao@tvunetworks.com
* @date 2017年8月28日 下午1:51:11
*/
public class ForumServiceImpl implements ForumService {

	@Override
	public void removeTopic(int topicId) {
		System.out.println("Simulate delete topic record: " + topicId);
		try {
			Thread.sleep(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeForum(int forumId) {
		System.out.println("Simulate delete Forum record: " + forumId);
		try {
			Thread.sleep(40);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
