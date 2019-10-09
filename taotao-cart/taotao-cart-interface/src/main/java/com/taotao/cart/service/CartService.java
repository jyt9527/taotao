package com.taotao.cart.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

/**
 * 
 * @ClassName: CartService
 * @Description: 购物车的service接口
 * @author: JYT
 * @date: 2019年9月25日 下午2:58:47
 */
public interface CartService {

	/**
	 * 
	 * @Title: addItemCart
	 * @Description: 添加商品进购物车
	 * @param item
	 * @param num
	 * @param userId
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月25日下午2:59:04
	 */
	public TaotaoResult addItemCart(TbItem item, Integer num, Long userId);

	/**
	 * 
	 * @Title: getCartList
	 * @Description: 根据用户的ID查询用户的购物车的列表
	 * @param userId
	 * @return List<TbItem>
	 * @author JYT
	 * @date 2019年9月25日下午2:59:01
	 */
	public List<TbItem> getCartList(Long userId);

	/**
	 * 
	 * 
	 * @param itemId
	 * @param num
	 * @param userId
	 * @return
	 */
	/**
	 * 
	 * @Title: updateItemCartByItemId
	 * @Description: 更新购物车中的商品
	 * @param userId 用户的id 购物车的id
	 * @param itemId 商品的ID
	 * @param num    更新后的数量
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月25日下午2:58:58
	 */
	public TaotaoResult updateItemCartByItemId(Long userId, Long itemId, Integer num);

	/**
	 * 
	 * @Title: deleteItemCartByItemId
	 * @Description: 根据商品的ID 删除数量，删除购物车中的商品
	 * @param userId
	 * @param itemId
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月25日下午2:58:54
	 */
	public TaotaoResult deleteItemCartByItemId(Long userId, Long itemId);
}
