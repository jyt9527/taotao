package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/**
 * 处理内容表相关的
 * @title ContentController.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentService;
import com.taotao.pojo.TbContent;

/**
 * 
 * @ClassName: ContentController
 * @Description: 内容处理的controller
 * @author: JYT
 * @date: 2019年9月9日 下午4:41:21
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentservcie;

	// $.post("/content/save",$("#contentAddForm").serialize(), function(data){
	// 返回值是JSON
	/**
	 * 
	 * @Title: saveContent
	 * @Description: TODO
	 * @param tContent
	 * @return TaotaoResult JSON
	 * @author JYT
	 * @date 2019年9月9日下午4:41:47
	 */
	@RequestMapping(value = "/content/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveContent(TbContent tContent) {
		// 1.引入服务
		// 2.注入服务
		// 3.调用
		return contentservcie.saveContent(tContent);
	}

	/**
	 * 
	 * @Title: getItemList
	 * @Description: 根据内容分类id，获取内容列表查询(分页)
	 * @param categoryId
	 * @param page
	 * @param rows
	 * @return EasyUIDataGridResult
	 * @author JYT
	 * @date 2019年9月10日上午11:23:06
	 */
	@RequestMapping(value = "/content/query/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Long categoryId, Integer page, Integer rows) {
		// 1.引入服务，在springmvc.xml
		// 2.注入服务
		// 3.调用服务的方法
		return contentservcie.getContentList(categoryId, page, rows);
	}

	/**
	 * 
	 * @Title: updateContent
	 * @Description: 更新
	 * @param tContent
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午12:01:01
	 */
	@RequestMapping(value = "/rest/content/edit", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateContent(TbContent tContent) {
		// 1.引入服务
		// 2.注入服务
		// 3.调用
		return contentservcie.updateContent(tContent);
	}

	/**
	 * 
	 * @Title: deleteContent
	 * @Description: 删除内容
	 * @param ids
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午7:55:17
	 */
	@RequestMapping(value = "/content/delete", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContent(String ids) {
		// 1.引入服务
		// 2.注入服务
		// 3.调用
		return contentservcie.deleteContent(ids);
	}
}
