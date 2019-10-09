package com.taotao.search.mapper;

import java.util.List;

import com.taotao.common.pojo.SearchItem;

/**
 * 
 * @ClassName: SearchItemMapper
 * @Description: 定义Mapper 关联查询3张表 查询出搜索时的商品数据
 * @author: JYT
 * @date: 2019年9月17日 下午7:43:12
 */
public interface SearchItemMapper {

	/**
	 * 
	 * @Title: getSearchItemList
	 * @Description: // 查询所有的商品的数据
	 * @return List<SearchItem>
	 * @author JYT
	 * @date 2019年9月20日下午8:02:56
	 */
	public List<SearchItem> getSearchItemList();

	/**
	 * 
	 * @Title: getSearchItemById
	 * @Description:// 根据商品的id查询商品的数据
	 * @param itemId
	 * @return SearchItem
	 * @author JYT
	 * @date 2019年9月20日下午8:02:19
	 */
	public SearchItem getSearchItemById(Long itemId);
}
