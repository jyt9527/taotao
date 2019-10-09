package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;

/**
 * 
 * @ClassName: ContentCategoryServiceImpl
 * @Description: 内容分类
 * @author: JYT
 * @date: 2019年9月9日 下午3:44:20
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		// 1.注入mapper
		// 2.创建example
		TbContentCategoryExample example = new TbContentCategoryExample();
		// 3.设置条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);// select * from tbcontentcategory where parent_id=1
		// 4.执行查询
		List<TbContentCategory> list = mapper.selectByExample(example);
		// 5.转成EasyUITreeNode 列表
		//
		List<EasyUITreeNode> nodes = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			node.setText(tbContentCategory.getName());// 分类名称
			nodes.add(node);
		}
		// 6.返回
		return nodes;
	}

	@Override
	public TaotaoResult createContentCategory(Long parentId, String name) {
		// 1.构建对象 补全其他的属性
		TbContentCategory category = new TbContentCategory();
		category.setCreated(new Date());
		category.setIsParent(false);// 新增的节点都是叶子节点
		category.setName(name);
		category.setParentId(parentId);
		category.setSortOrder(1);
		category.setStatus(1);
		category.setUpdated(category.getCreated());
		// 2.插入contentcategory表数据
		mapper.insertSelective(category);
		// 3.返回taotaoresult 包含内容分类的id 需要主键返回

		// 判断如果要添加的节点的父节点本身叶子节点 需要更新其为父节点
		TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
		// 判断父节点存在
		if (parent != null) {
			if (parent.getIsParent() == false) {// 原本就是叶子节点
				parent.setIsParent(true);
				mapper.updateByPrimaryKeySelective(parent);// 更新节点的is_parent属性为true
			}
		}

		return TaotaoResult.ok(category);
	}

	@Override
	public TaotaoResult updateContentCategory(Long id, String name) {
		// 1.构建对象,添加需要修改的信息
		TbContentCategory category = new TbContentCategory();
		category.setName(name);
		category.setId(id);
		// 调用mapper接口的方法
		mapper.updateByPrimaryKeySelective(category);
		return TaotaoResult.ok(category);
	}

	/**
	 * 删除节点方式一：页面显示删除成功，实际上是更新节点的status，没有真正删除，数据库还有信息
	 */
	@Override
	public TaotaoResult deleteContentCategory1_reallyUpdate(Long id) {
		// 1.获取删除节点的is_parent
		TbContentCategory tbContentCategory = mapper.selectByPrimaryKey(id);
		// 2.如果is_parent=false，说明要删除的节点不是父节点，可以删除。否则不允许删除
		// 因为大多数删除节点是父节点的请求，都被js过滤掉了，所以将is_parent=false的情况放在最先执行
		if (!tbContentCategory.getIsParent()) {
			// 3.封装更新的字段status。1(正常),2(删除)
			tbContentCategory.setStatus(2);
			// 4.更新内容分类表
			// updateByPrimaryKey：按主键更新
			// updateByPrimaryKeySelective：按主键更新值不为null的字段
			mapper.updateByPrimaryKey(tbContentCategory);
			// 5.判断该节点的父节点是否还有子节点，如果没有需要把父节点的isparent改为false
			// 查询根据parentId查询所有子节点节点
			Long parentId = tbContentCategory.getParentId();
			List<TbContentCategory> list = getContentCategoryListByParentId(parentId);
			if (list.size() == 0) {
				TbContentCategory parentNode = mapper.selectByPrimaryKey(parentId);
				parentNode.setIsParent(false);
			}
			return TaotaoResult.ok();
		} else {
			String msg = "请先删  " + tbContentCategory.getName() + " 分类下的所有子分类，再删除 " + tbContentCategory.getName() + "分类";
			TaotaoResult result = TaotaoResult.build(500, msg, null);
			return result;

		}
	}

	/**
	 * 删除节点方式二：子节点直接删除，删除父节点不允许删除，必须删除子节点之后才能删除，并且是真正的删除
	 */
	@Override
	public TaotaoResult deleteContentCategory2_reallyDelete(Long id) {
		// 1.获取删除节点的is_parent
		TbContentCategory tbContentCategory = mapper.selectByPrimaryKey(id);
		// 2.如果is_parent=false，说明要删除的节点不是父节点，可以删除。否则不允许删除
		// 因为大多数删除节点是父节点的请求，都被js过滤掉了，所以将is_parent=false的情况放在最先执行
		if (!tbContentCategory.getIsParent()) {
			// 3.封装更新的字段status。1(正常),2(删除)
			tbContentCategory.setStatus(2);
			// 4.更新内容分类表
			// updateByPrimaryKey：按主键更新
			// updateByPrimaryKeySelective：按主键更新值不为null的字段
			mapper.updateByPrimaryKey(tbContentCategory);
			// 然后，直接删除
			mapper.deleteByPrimaryKey(id);

			// 更改父节点的isparent状态的实现
			// 获取该节点的父节点
			TbContentCategory parentNode = mapper.selectByPrimaryKey(tbContentCategory.getParentId());
			// 通过父节点的id查询该父节点下的全部次级节点，即该节点的同级节点
			TbContentCategoryExample example = new TbContentCategoryExample();
			Criteria criteria = example.createCriteria();
			criteria.andParentIdEqualTo(parentNode.getId());
			List<TbContentCategory> list2 = mapper.selectByExample(example);

			// 如果同级节点不存在则改该节点的父级节点为子节点
			if (list2.size() == 0) {
				parentNode.setIsParent(false);
				// 更新
				mapper.updateByPrimaryKey(parentNode);
			}

			return TaotaoResult.ok();
		} else {
			String msg = "请先删  " + tbContentCategory.getName() + " 分类下的所有子分类，再删除 " + tbContentCategory.getName() + "分类";
			TaotaoResult result = TaotaoResult.build(500, msg, null);
			return result;

		}
	}

	/**
	 * 删除节点方式三：递归删除，而且是真正的删除
	 */
	@Override
	public TaotaoResult deleteContentCategory3_RecursiveDelete_ReallyDelete(Long id) {
		// 1.先更新自己的状态
		TbContentCategory contentCategory = mapper.selectByPrimaryKey(id);

		// 2.如果当前节点是父节点，则递归删除下面的所有子节点
		if (contentCategory.getIsParent()) {
			deleteChildrenNode(contentCategory.getId());
		}
		// 3.是子节点，直接删除
		mapper.deleteByPrimaryKey(id);
		// 4.判断该节点的父节点是否还有子节点，如果没有需要把父节点的isparent改为false
		TbContentCategory parentNode = mapper.selectByPrimaryKey(contentCategory.getParentId());
		List<TbContentCategory> list = getContentCategoryListByParentId(parentNode.getId());
		// list.size() == 0 ，说明没有字节点，将isParent=false
		if (list.size() == 0) {
			parentNode.setIsParent(false);
			mapper.updateByPrimaryKey(parentNode);
		}
		// 返回结果
		return TaotaoResult.ok();
	}

	/**
	 * 
	 * @Title: deleteChildrenNode
	 * @Description: 根据父节点id 递归删除下面所有子节点
	 * @param parentId void
	 * @author JYT
	 * @date 2019年9月9日下午10:05:44
	 */
	private void deleteChildrenNode(Long parentId) {
		// 查询根据parentId查询所有子节点节点
		List<TbContentCategory> list = getContentCategoryListByParentId(parentId);

		for (TbContentCategory tbContentCategory : list) {
			tbContentCategory.setStatus(2);
			// 更新状态：没啥用
			mapper.updateByPrimaryKey(tbContentCategory);
			// 删除
			mapper.deleteByPrimaryKey(tbContentCategory.getId());
			// 如果是父节点，则将自己的id作为parentId查询，所有字节节点
			if (tbContentCategory.getIsParent()) {
				deleteChildrenNode(tbContentCategory.getId());
			}
		}
	}

	/**
	 * 
	 * @Title: getContentCategoryListByParentId
	 * @Description: 根据parentId查询子节点列表
	 * @param parentId
	 * @return List<TbContentCategory>
	 * @author JYT
	 * @date 2019年9月9日下午10:09:41
	 */
	private List<TbContentCategory> getContentCategoryListByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = mapper.selectByExample(example);
		return list;
	}

//	/**
//	 * 删除节点方式三：递归删除，而且是真正的删除
//	 */
//	// 创建子节点对象
//	private List<TbContentCategory> allNode = new ArrayList<>();
//
//	/**
//	 * 获取当前id对应的节点下面的所有子节点(递归方法)
//	 */
//	private List<TbContentCategory> findAllNode(Long id) {
//		// 获取当前id所有次节点
//		TbContentCategoryExample example = new TbContentCategoryExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andParentIdEqualTo(id);
//		List<TbContentCategory> list = mapper.selectByExample(example);
//		// 遍历次节点
//		for (TbContentCategory tbContentCategory : list) {
//			// 添加进子节点对象中
//			allNode.add(tbContentCategory);
//			// 为父节点则递归
//			if (tbContentCategory.getIsParent()) {
//				Long childId = tbContentCategory.getId();
//				findAllNode(childId);
//			}
//		}
//		return allNode;
//	}
//
//	@Override
//	public TaotaoResult delectContentCategory_reallyDelete(Long id) {
//
//		// 通过id获取contencategory对象
//		TbContentCategory contentCategory = mapper.selectByPrimaryKey(id);
//		// 判断是否是父节点
//		if (contentCategory.getIsParent()) {
//			// 如果是父节点则调用findAllNode()方法获取全部子节点
//			List<TbContentCategory> allNode = findAllNode(id);
//			// 遍历删除
//			for (TbContentCategory tbContentCategory : allNode) {
//				mapper.deleteByPrimaryKey(tbContentCategory.getId());
//			}
//		}
//		// 为子节点则直接删除
//		mapper.deleteByPrimaryKey(id);
//
//		// 更改父节点的isparent状态的实现
//		// 获取该节点的父节点
//		TbContentCategory parentNode = mapper.selectByPrimaryKey(contentCategory.getParentId());
//		// 通过父节点的id查询该父节点下的全部次级节点，即该节点的同级节点
//		TbContentCategoryExample example = new TbContentCategoryExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andParentIdEqualTo(parentNode.getId());
//		List<TbContentCategory> list = mapper.selectByExample(example);
//
//		// 如果同级节点不存在则改该节点的父级节点为子节点
//		if (list.size() == 0) {
//			parentNode.setIsParent(false);
//			// 更新
//			mapper.updateByPrimaryKey(parentNode);
//		}
//		return TaotaoResult.ok();
//	}

}
