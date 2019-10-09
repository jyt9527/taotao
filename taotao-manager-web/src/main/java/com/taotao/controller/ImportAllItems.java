package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.SearchService;

/**
 * 
 * @ClassName: ImportAllItems
 * @Description: 后台管理-索引管理
 * @author: JYT
 * @date: 2019年9月17日 下午8:49:38
 */
@Controller
public class ImportAllItems {
	@Autowired
	private SearchService service;

	/**
	 * 
	 * @Title: importAll
	 * @Description: 导入所有的商品的数据到solr索引库中
	 * @return
	 * @throws Exception TaotaoResult
	 * @author JYT
	 * @date 2019年9月17日下午8:50:02
	 */
	@RequestMapping("/index/importAll")
	@ResponseBody
	public TaotaoResult importAll() throws Exception {
		// 1.引入服务
		// 2.注入服务
		// 3.调用方法
		return service.importAllSearchItems();
	}
}
