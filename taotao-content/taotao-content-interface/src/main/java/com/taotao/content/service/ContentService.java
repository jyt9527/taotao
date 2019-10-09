package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 
 * @ClassName: ContentService
 * @Description: 内容处理的接口
 * @author: JYT
 * @date: 2019年9月9日 下午3:38:03
 */
public interface ContentService {

	/**
	 * 
	 * @Title: saveContent
	 * @Description: 插入内容表
	 * @param content
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午3:38:57
	 */
	public TaotaoResult saveContent(TbContent content);

	/**
	 * 
	 * @Title: getContentList
	 * @Description:根据内容分类id，获取内容列表查询(分页)
	 * @param categoryId 内容分类id
	 * @param page
	 * @param rows
	 * @return EasyUIDataGridResult
	 * @author JYT
	 * @date 2019年9月10日上午11:18:57
	 */
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);

	/**
	 * 
	 * @Title: updateContent
	 * @Description: 更新
	 * @param tContent
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午12:01:25
	 */
	public TaotaoResult updateContent(TbContent tContent);

	/**
	 * 
	 * @Title: deleteContent
	 * @Description: 删除
	 * @param ids
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午12:08:55
	 */
	public TaotaoResult deleteContent(String ids);

	/**
	 * 
	 * @Title: getContentListByCatId
	 * @Description: 根据内容分类id得到内容列表(首页显示轮播图使用)
	 * @param categoryId
	 * @return List<TbContent>
	 * @author JYT
	 * @date 2019年9月16日上午10:01:09
	 */
	public List<TbContent> getContentListByCatId(Long categoryId);
}
