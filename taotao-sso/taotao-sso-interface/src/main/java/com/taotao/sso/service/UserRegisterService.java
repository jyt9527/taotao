package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * 
 * @ClassName: UserRegisterService
 * @Description: 用户注册的接口
 * @author: JYT
 * @date: 2019年9月23日 下午11:11:31
 */
public interface UserRegisterService {

	/**
	 * 
	 * @Title: checkData
	 * @Description: 根据参数和类型来校验数据
	 * @param param 要校验的数据
	 * @param type  1 2 3 分别代表 username,phone,email
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月23日下午11:11:51
	 */
	public TaotaoResult checkData(String param, Integer type);

	/**
	 * 
	 * @Title: register
	 * @Description: 注册用户
	 * @param user
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月23日下午11:12:14
	 */
	public TaotaoResult register(TbUser user);
}
