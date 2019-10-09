package com.taotao.cart.jedis;

import java.util.Map;

/**
 * 
 * @ClassName: JedisClient
 * @Description: TODO
 * @author: JYT
 * @date: 2019年9月25日 下午3:05:29
 */
public interface JedisClient {

	String set(String key, String value);

	String get(String key);

	Boolean exists(String key);

	Long expire(String key, int seconds);

	Long ttl(String key);

	Long incr(String key);

	Long hset(String key, String field, String value);

	String hget(String key, String field);

	/**
	 * 
	 * @Title: hdel
	 * @Description: // 删除hkey
	 * @param key
	 * @param field
	 * @return Long
	 * @author JYT
	 * @date 2019年9月25日下午3:05:42
	 */
	Long hdel(String key, String... field);

	/**
	 * 
	 * @Title: hgetAll
	 * @Description: // 查询所有的hash类型的列表
	 * @param key
	 * @return Map<String,String>
	 * @author JYT
	 * @date 2019年9月25日下午3:05:45
	 */
	Map<String, String> hgetAll(String key);

}
