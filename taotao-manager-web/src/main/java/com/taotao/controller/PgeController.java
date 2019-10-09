package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: PgeController
 * @Description: 显示页面
 * @author: JYT
 * @date: 2019年9月8日 上午10:04:48
 */
@Controller
public class PgeController {
	// 测试是否显示后台管理界面
	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	// 显示商品的查询的页面
	// url:/item-list或item-add或item-param-list等等
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
