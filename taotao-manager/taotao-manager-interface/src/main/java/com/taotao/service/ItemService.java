package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * 
 * @ClassName: ItemService
 * @Description: 商品相关的Service接口
 * @author: JYT
 * @date: 2019年9月8日 上午11:13:04
 */
public interface ItemService {

	/**
	 * 
	 * @Title: getItemList
	 * @Description: 查询商品列表
	 * @param page
	 * @param rows
	 * @return EasyUIDataGridResult//在taotao0common中写的响应的json数据格式EasyUIDataGridResult
	 * 
	 * @author JYT
	 * @date 2019年9月8日上午11:10:27
	 */
	public EasyUIDataGridResult getItemList(Integer page, Integer rows);

	/**
	 * 
	 * @Title: saveItem
	 * @Description: 添加商品基本数据和描述数据
	 * @param item
	 * @param desc
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月8日下午8:41:19
	 */
	public TaotaoResult saveItem(TbItem item, String desc);

	/**
	 * 
	 * @Title: getItemDesc
	 * @Description: 根据商品id查询商品描述，将查询结果封装到TaotaoResult中
	 * @param itemId
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午8:15:47
	 */
	public TaotaoResult getItemDesc(Long itemId);

	/**
	 * 
	 * @Title: updateItem
	 * @Description: 更新
	 * @param item
	 * @param desc
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午8:15:51
	 */
	public TaotaoResult updateItem(TbItem item, String desc);

	/**
	 * 
	 * @Title: updateItemStatus
	 * @Description: 根据商品id，更新商品状态：1-正常，2-下架，3-删除
	 * @param ids
	 * @param method
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午8:21:49
	 */
	public TaotaoResult updateItemStatus(List<Long> ids, String method);

	/**
	 * 
	 * @Title: getItemById
	 * @Description: 根据商品id获得商品基本信息
	 * @param itemId
	 * @return TbItem
	 * @author JYT
	 * @date 2019年9月20日下午9:25:02
	 */
	public TbItem getItemById(Long itemId);

	/**
	 * 
	 * @Title: getItemDescById
	 * @Description: 根据商品id获得商品描述信息
	 * @param itemId
	 * @return TbItemDesc
	 * @author JYT
	 * @date 2019年9月20日下午9:25:05
	 */
	public TbItemDesc getItemDescById(Long itemId);
}
