package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.TestService;

@Controller
public class TestController {
	// 1、引入服务：在springmvc.xml中引入
	// 2、注入服务
	@Autowired
	private TestService testservice;

	// 3、调用服务的方法
	/**
	 * 
	 * @Title: queryNow
	 * @Description: 测试dubbo配置是否正常
	 * @return String
	 * @author JYT
	 * @date 2019年9月7日下午8:05:31
	 */
	@RequestMapping("/test/queryNow")
	@ResponseBody
	public String queryNow() {
		return testservice.queryNow();
	}
}