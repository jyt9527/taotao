package com.taotao.test.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.content.jedis.JedisClient;

/**
 * 
 * @ClassName: TestJedisClient
 * @Description: Spring中使用jedis操作redis
 * @author: JYT
 * @date: 2019年9月16日 下午9:25:07
 */
public class TestJedisClient {
	//@Test
	public void testdanji() {
		// 1.初始化spring容器
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-redis.xml");
		// 2.获取实现类实例
		JedisClient bean = context.getBean(JedisClient.class);
		// 3.调用方法操作
		bean.set("jedisclientkey11", "jedisclientkey");
		System.out.println(bean.get("jedisclientkey11"));
	}
}
