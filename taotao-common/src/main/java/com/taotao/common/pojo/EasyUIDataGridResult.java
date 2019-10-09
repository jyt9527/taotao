package com.taotao.common.pojo;

import java.io.Serializable;
/**
 * datagrid 展示数据的POJO 包括商品的POJO
 * @title EasyUIDataGridResult.java
 * <p>description</p>
 * <p>company: www.itheima.com</p>
 * @author ljh 
 * @version 1.0
 */
import java.util.List;

/**
 * 
 * @ClassName: EasyUIDataGridResult
 * @Description: 响应的json数据格式EasyUIDataGridResult
 * 
 *               商品列表查询的返回的数据类
 * @author: JYT
 * @date: 2019年9月8日 上午10:31:42
 */
public class EasyUIDataGridResult implements Serializable {

	private Integer total;

	private List rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
