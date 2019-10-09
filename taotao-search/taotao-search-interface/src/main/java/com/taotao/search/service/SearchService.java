package com.taotao.search.service;

import com.taotao.common.pojo.SearchResult;
import com.taotao.common.pojo.TaotaoResult;

/**
 * 
 * @ClassName: SearchService
 * @Description: 搜索相关的service接口
 * @author: JYT
 * @date: 2019年9月17日 下午7:42:44
 */
public interface SearchService {

	/**
	 * 
	 * @Title: importAllSearchItems
	 * @Description: // 导入所有的商品数据到索引库中
	 * @return
	 * @throws Exception TaotaoResult
	 * @author JYT
	 * @date 2019年9月18日下午6:40:28
	 */
	public TaotaoResult importAllSearchItems() throws Exception;

	/**
	 * 
	 * @Title: search
	 * @Description: 根据查询条件查询,根据搜索的条件搜索的结果
	 * @param queryString 查询的主条件
	 * @param page        查询的当前的页码
	 * @param rows        每页显示的行数 这个在controller中写死
	 * @return
	 * @throws Exception SearchResult
	 * @author JYT
	 * @date 2019年9月17日下午8:12:10
	 */
	public SearchResult search(String queryString, Integer page, Integer rows) throws Exception;

	/**
	 * 
	 * @Title: updateSearchItemById
	 * @Description: 更新商品搜索索引库根据商品id
	 * @param itemId
	 * @return
	 * @throws Exception TaotaoResult
	 * @author JYT
	 * @date 2019年9月20日下午7:59:05
	 */
	public TaotaoResult updateSearchItemById(Long itemId) throws Exception;
}
