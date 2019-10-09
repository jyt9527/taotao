package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;

/**
 * 
 * @ClassName: ContentCategoryService
 * @Description: 内容分类的service接口
 * @author: JYT
 * @date: 2019年9月9日 下午3:37:53
 */
public interface ContentCategoryService {

	/**
	 * 
	 * @Title: getContentCategoryList
	 * @Description: 通过父节点的id查询该节点的子节点列表
	 * @param parentId
	 * @return List<EasyUITreeNode>
	 * @author JYT
	 * @date 2019年9月9日下午3:38:43
	 */
	public List<EasyUITreeNode> getContentCategoryList(Long parentId);

	/**
	 * 
	 * @Title: createContentCategory
	 * @Description: 添加内容分类
	 * @param parentId 父节点的id
	 * @param name     新增节点的名称
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午3:38:16
	 */
	public TaotaoResult createContentCategory(Long parentId, String name);

	/**
	 * 
	 * @Title: updateContentCategory
	 * @Description: 更新节点
	 * @param id
	 * @param name
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午7:42:39
	 */
	public TaotaoResult updateContentCategory(Long id, String name);

	/**
	 * 
	 * @Title: deleteContentCategory1_reallyUpdate
	 * @Description: 删除节点方式一：实际上是更新节点的status，没有真正删除，数据库还有信息
	 * @param id
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午8:15:14
	 */
	public TaotaoResult deleteContentCategory1_reallyUpdate(Long id);

	/**
	 * 
	 * @Title: deleteContentCategory2_reallyDelete
	 * @Description: 删除节点方式二：子节点直接，删除父节点不允许删除，必须删除子节点之后才能删除，并且是真正的删除
	 * @param id
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午10:10:37
	 */
	public TaotaoResult deleteContentCategory2_reallyDelete(Long id);

	/**
	 * 
	 * @Title: deleteContentCategory3_RecursiveDelete_ReallyDelete
	 * @Description: 删除节点方式三：递归删除，而且是真正的删除
	 * @param id
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月9日下午10:10:10
	 */
	public TaotaoResult deleteContentCategory3_RecursiveDelete_ReallyDelete(Long id);
//	/**
//	 * 
//	 * @Title: delectContentCategory_reallyDelete
//	 * @Description: 删除节点方式三：递归删除，而且是真正的删除
//	 * @param id
//	 * @return TaotaoResult
//	 * @author JYT
//	 * @date 2019年9月9日下午9:45:34
//	 */
//	public TaotaoResult delectContentCategory_reallyDelete(Long id);
}
