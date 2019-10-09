package com.taotao.sso.jedis;

/**
 * 
 * @ClassName: JedisClient
 * @Description: 开发一个接口，有单机版的实现类和集群版的实现类。
 * 
 *               使用时可以面向接口开发，不影响业务逻辑，使用spring管理实现类，部署时切换实现类即可。
 * @author: JYT
 * @date: 2019年9月16日 下午9:29:33
 */
public interface JedisClient {
	/**
	 * 
	 * @Title: set
	 * @Description: 设置值
	 * @param key
	 * @param value
	 * @return String
	 * @author JYT
	 * @date 2019年9月16日下午9:55:03
	 */
	String set(String key, String value);

	/**
	 * 
	 * @Title: get
	 * @Description: 获取值
	 * @param key
	 * @return String
	 * @author JYT
	 * @date 2019年9月16日下午9:55:08
	 */
	String get(String key);

	/**
	 * 
	 * @Title: exists
	 * @Description:判断存在
	 * @param key
	 * @return Boolean
	 * @author JYT
	 * @date 2019年9月16日下午9:55:12
	 */
	Boolean exists(String key);

	/**
	 * 
	 * @Title: expire
	 * @Description: 终止
	 * @param key
	 * @param seconds
	 * @return Long
	 * @author JYT
	 * @date 2019年9月16日下午9:55:15
	 */
	Long expire(String key, int seconds);

	/**
	 * 
	 * @Title: ttl
	 * @Description: TODO
	 * @param key
	 * @return Long
	 * @author JYT
	 * @date 2019年9月16日下午9:55:19
	 */
	Long ttl(String key);

	/**
	 * 
	 * @Title: incr
	 * @Description: incr key 加一
	 * @param key
	 * @return Long
	 * @author JYT
	 * @date 2019年9月16日下午9:55:22
	 */
	Long incr(String key);

	/**
	 * 
	 * @Title: hset
	 * @Description: 设置值：注入hash类型的Redis的数据
	 * @param key
	 * @param field
	 * @param value
	 * @return Long
	 * @author JYT
	 * @date 2019年9月16日下午9:55:25
	 */
	Long hset(String key, String field, String value);

	/**
	 * 
	 * @Title: hget
	 * @Description: 获取值：得到hash类型的Redis的数据
	 * @param key
	 * @param field
	 * @return String
	 * @author JYT
	 * @date 2019年9月16日下午9:55:28
	 */
	String hget(String key, String field);

	/**
	 * 
	 * @Title: hdel
	 * @Description: // 根据hash值类型的redis的key删除Redis数据
	 * @param key
	 * @param field
	 * @return Long
	 * @author JYT
	 * @date 2019年9月16日下午9:55:32
	 */
	Long hdel(String key, String... field);

}
