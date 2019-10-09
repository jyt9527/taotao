package com.taotao.item.pojo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.taotao.pojo.TbItem;

/**
 * 
 * @ClassName: Item
 * @Description: 根据商品id查询商品信息(tb_item)得到一个TbItem对象，缺少images属性，可以创建一个pojo继承
 *               ，添加一个getImages方法，放在taotao-item-web工程中。
 * 
 * @author: JYT
 * @date: 2019年9月20日 下午9:27:58
 */
public class Item extends TbItem {

	public Item(TbItem item) {
		BeanUtils.copyProperties(item, this);// 讲原来数据有的属性的值拷贝到item有的属性中
	}

	public String[] getImages() {
		if (StringUtils.isNotBlank(super.getImage())) {
			return super.getImage().split(",");
		}
		return null;
	}
}
