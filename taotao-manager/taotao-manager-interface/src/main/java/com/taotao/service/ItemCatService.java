package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;

/**
 * 
 * @ClassName: ItemCatService
 * @Description: 商品分类的service的接口
 * @author: JYT
 * @date: 2019年9月8日 下午7:00:07
 */
public interface ItemCatService {
	/**
	 * 根据父节点的id查询子节点的列表
	 * 
	 * @param parentId
	 * @return
	 */
	public List<EasyUITreeNode> getItemCatListByParentId(Long parentId);
}
