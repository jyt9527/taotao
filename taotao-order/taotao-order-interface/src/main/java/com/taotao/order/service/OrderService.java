package com.taotao.order.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * 
 * @ClassName: OrderService
 * @Description: 订单service
 * @author: JYT
 * @date: 2019年9月25日 下午7:25:36
 */
public interface OrderService {

	/**
	 * 
	 * @Title: createOrder
	 * @Description: 创建订单
	 * @param info
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月25日下午7:25:48
	 */
	public TaotaoResult createOrder(OrderInfo info);
}
