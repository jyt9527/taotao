package com.taotao.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest_Linux {
	// 测试单机版
	@Test
	public void testJedis() {
		// 1.创建Jedis 对象 需要指定 连接的地址和端口
		Jedis jedis = new Jedis("192.168.25.131", 6379);
		// 2.直接操作redis set
		jedis.set("key1234", "vlaue");
		System.out.println(jedis.get("key1234"));
		// 3.关闭Jedis
		jedis.close();
	}

	/**
	 * 
	 * @Title: teatJeisPool
	 * @Description: 单机-连接池版
	 * @author JYT
	 * @date 2019年9月16日下午9:10:29
	 */
	@Test
	public void teatJeisPool() {
		// 1.创建jedispool 对象需要指定端口和地址
		JedisPool pool = new JedisPool("192.168.25.131", 6379);
		// 2.获取jedis的对象
		Jedis jedis = pool.getResource();
		// 3.直接操作redis
		jedis.set("keypool", "keypool");
		System.out.println(jedis.get("keypool"));
		// 4.关闭redis (释放资源到连接池)
		jedis.close();
		// 5.关闭连接池（应用系统关闭的时候才关闭）
		pool.close();
	}

	// 测试集群版
	@Test
	public void testjediscluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.131", 7001));
		nodes.add(new HostAndPort("192.168.25.131", 7002));
		nodes.add(new HostAndPort("192.168.25.131", 7003));
		nodes.add(new HostAndPort("192.168.25.131", 7004));
		nodes.add(new HostAndPort("192.168.25.131", 7005));
		nodes.add(new HostAndPort("192.168.25.131", 7006));
		// 1.创建jediscluster对象
		JedisCluster cluster = new JedisCluster(nodes);
		// 2.直接根据jediscluster对象操作redis集群
		cluster.set("keycluster", "cluster的value");
		System.out.println(cluster.get("keycluster"));
		// 3.关闭jediscluster对象(是在应用系统关闭的时候关闭) 封装了连接池
		cluster.close();
	}
}
