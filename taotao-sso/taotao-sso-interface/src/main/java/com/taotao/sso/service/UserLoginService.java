package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

/**
 * 
 * @ClassName: UserLoginService
 * @Description: 登录的接口
 * @author: JYT
 * @date: 2019年9月23日 下午11:10:42
 */
public interface UserLoginService {

	/**
	 * 
	 * @Title: login
	 * @Description: 根据用户名和密码登录
	 * @param username
	 * @param password
	 * @return TaotaoResult 登录成功 返回200 并且包含一个token数据 登录失败：返回400
	 * @author JYT
	 * @date 2019年9月23日下午11:10:57
	 */
	public TaotaoResult login(String username, String password);

	/**
	 * 
	 * @Title: getUserByToken
	 * @Description: 根据token获取用户的信息
	 * @param token
	 * @return TaotaoResult 应该包含用户的信息
	 * @author JYT
	 * @date 2019年9月23日下午11:11:15
	 */
	public TaotaoResult getUserByToken(String token);

	/**
	 * 
	 * @Title: logoutByToken
	 * @Description: 用户安全退出,就是删除redis中的session缓存，设置过期时间为零
	 * @param token
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月25日下午7:57:44
	 */
	public TaotaoResult logoutByToken(String token);
}
