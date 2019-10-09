package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;

/**
 * 
 * @ClassName: ContentCategoryController
 * @Description: 内容分类的处理controller
 * @author: JYT
 * @date: 2019年9月9日 下午4:13:45
 */
@Controller
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService service;

	/**
	 * 
	 * @Title: getContentCategoryList
	 * @Description: url : '/content/category/list', animate: true, 参数: id
	 * @param parentId
	 * @return List<EasyUITreeNode>
	 * @author JYT
	 * @date 2019年9月9日下午4:14:46
	 */
	@RequestMapping(value = "/content/category/list", method = RequestMethod.GET) // method : "GET",
	@ResponseBody
	public List<EasyUITreeNode> getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		// 1.引入服务
		// 2.注入服务
		// 3调用
		return service.getContentCategoryList(parentId);
	}

	/**
	 * 
	 * @Title: createContentCategory
	 * @Description: 添加节点
	 * @param parentId就是新增节点的父节点的Id
	 * @param name新增节点的文本
	 * @return TaotaoResult包含分类的id
	 * @author JYT
	 * @date 2019年9月9日下午4:14:02
	 */
	@RequestMapping(value = "/content/category/create", method = RequestMethod.POST) // method=post
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		return service.createContentCategory(parentId, name);
	}

	/**
	 * 
	 * @Title: updateContentCategory
	 * @Description: 重命名节点
	 * @param id
	 * @param name
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午11:30:46
	 */
	@RequestMapping(value = "/content/category/update", method = RequestMethod.POST) // method=post
	@ResponseBody
	public TaotaoResult updateContentCategory(Long id, String name) {
		return service.updateContentCategory(id, name);
	}

	/**
	 * 
	 * @Title: deleteContentCategory
	 * @Description: 删除节点的三种方式
	 * @param id
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午11:31:01
	 */
	@RequestMapping(value = "/content/category/delete", method = RequestMethod.POST) // method=post
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id) {
		// 调用删除节点方式一：页面显示删除成功，实际上是更新节点的status，没有真正删除，数据库还有信息
		// 使用content-category-副本1.jsp
		// TaotaoResult result = service.deleteContentCategory1_reallyUpdate(id);

		// 调用删除节点方式二：子节点直接，删除父节点不允许删除，必须删除子节点之后才能删除，并且是真正的删除
		// 使用content-category-副本1.jsp
		TaotaoResult result = service.deleteContentCategory2_reallyDelete(id);

		// 调用删除节点方式三：递归删除，而且是真正的删除
		// 使用content-category-副本2.jsp
		// TaotaoResult result =
		// service.deleteContentCategory3_RecursiveDelete_ReallyDelete(id);
		return result;

	}
}
