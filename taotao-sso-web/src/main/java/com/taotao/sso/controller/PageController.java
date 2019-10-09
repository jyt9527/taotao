package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: PageController
 * @Description: 跳转页面使用的controller
 * @author: JYT
 * @date: 2019年9月23日 下午11:13:58
 */
@Controller
public class PageController {

//	@RequestMapping("/page/{page}")
//	public String showPage(@PathVariable String page) {
//		return page;
//	}
//为了解决登录之后跳转到首页的问题，修改为以下代码
	@RequestMapping("/page/{page}")
	public String showPage(@PathVariable String page, String redirect, Model model) {
		System.out.println(redirect);
		model.addAttribute("redirect", redirect);
		return page;
	}
}
