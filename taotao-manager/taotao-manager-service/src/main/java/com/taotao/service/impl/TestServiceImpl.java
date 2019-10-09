package com.taotao.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TestMapper;
import com.taotao.service.TestService;

/**
 * 
 * @ClassName: TestServiceImpl
 * @Description: TestService的实现类
 * @author: JYT
 * @date: 2019年9月7日 下午7:52:04
 */
@Service // 不是dubbo的service，而是spring的
public class TestServiceImpl implements TestService {
//1、注入mapper
	@Autowired // 自动装载dao层的mapper动态代理开发
	private TestMapper mapper;

//2、调用mapper的返回方法
	@Override
	public String queryNow() {
		return mapper.queryNow();
	}
}
