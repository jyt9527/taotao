package com.taotao.order.pojo;

import java.io.Serializable;
import java.util.List;

import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

/**
 * 
 * @ClassName: OrderInfo
 * @Description: 订单信息
 * 
 *               可以扩展TbOrder，在子类中添加两个属性一个是商品明细列表，一个是配送信息。
 * @author: JYT
 * @date: 2019年9月25日 下午7:25:16
 */
public class OrderInfo extends TbOrder implements Serializable {
	private List<TbOrderItem> orderItems;// 订单项
	private TbOrderShipping orderShipping;

	public List<TbOrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<TbOrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}

	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}

}
