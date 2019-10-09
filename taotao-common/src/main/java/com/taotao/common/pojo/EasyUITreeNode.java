package com.taotao.common.pojo;

import java.io.Serializable;

/**
 * 
 * @ClassName: EasyUITreeNode
 * @Description: 一、实现商品类目选择
 * 
 *               1、tree 控件的节点的pojo
 * @author: JYT
 * @date: 2019年9月8日 下午6:54:07
 */
public class EasyUITreeNode implements Serializable {
	private Long id;
	private String text;
	private String state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
