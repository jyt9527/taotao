package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.manager.jedis.JedisClient;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.service.ItemService;

/**
 * 
 * @ClassName: ItemServiceImpl
 * @Description: ItemService的实现类
 * @author: JYT
 * @date: 2019年9月8日 上午11:18:46
 */
@Service // 别忘了注解service，spring的
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper mapper;

	@Autowired
	private TbItemDescMapper descmapper;

	@Autowired
	private JedisClient client;

	// jmstemplate对象
	@Autowired
	private JmsTemplate jmstemplate;
	// 目的地
	@Resource(name = "topicDestination")
	private Destination destination;

	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;

	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;

	/**
	 * 使用pagehelper分页，返回EasyUI的特定json数据
	 */
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 1.设置分页的信息 使用pagehelper
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 30;
		PageHelper.startPage(page, rows);
		// 2.注入mapper
		// 3.创建example 对象 不需要设置查询条件
		TbItemExample example = new TbItemExample();
		// 4.根据mapper调用查询所有数据的方法
		List<TbItem> list = mapper.selectByExample(example);
		// 5.获取分页的信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		// 6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		// 7.返回
		return result;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		// 生成商品的id
		long itemId = IDUtils.genItemId();
		// 1.补全item 的其他属性
		item.setId(itemId);
		item.setCreated(new Date());
		// 1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setUpdated(item.getCreated());
		// 2.插入到item表 商品的基本信息表
		mapper.insertSelective(item);
		// 3.补全商品描述中的属性
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(itemId);
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		// 4.插入商品描述数据
		// 注入tbitemdesc的mapper
		descmapper.insertSelective(desc2);
		// 添加发送消息的业务逻辑
		jmstemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// 发送的消息（发送id）
				return session.createTextMessage(itemId + "");// +""的作用：转成字符串
			}
		});
		// 5.返回taotaoresult
		return TaotaoResult.ok();

	}

	// 解决事务还没有提交，就发送了消息导致报错的问题。的第一种方法
	// 在service中另外定义一个方法，先执行 插入商品的service的方法，然后在执行成功后，再发送消息。

	@Override
	public TaotaoResult getItemDesc(Long itemId) {
		TbItemDesc itemDesc = descmapper.selectByPrimaryKey(itemId);
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult updateItem(TbItem item, String desc) {
		// 1、根据商品id，更新商品表，条件更新
		TbItemExample itemExample = new TbItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andIdEqualTo(item.getId());
		mapper.updateByExampleSelective(item, itemExample);

		// 2、根据商品id，更新商品描述表，条件更新
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		TbItemDescExample itemDescExample = new TbItemDescExample();
		com.taotao.pojo.TbItemDescExample.Criteria createCriteria = itemDescExample.createCriteria();
		createCriteria.andItemIdEqualTo(item.getId());
		descmapper.updateByExampleSelective(itemDesc, itemDescExample);

		return TaotaoResult.ok();
	}

//下架商品
	@Override
	public TaotaoResult updateItemStatus(List<Long> ids, String method) {
		TbItem item = new TbItem();
		if (method.equals("reshelf")) {
			// 商品状态为正常，更新status=1即可
			item.setStatus((byte) 1);
		} else if (method.equals("instock")) {
			// 商品状态为下架，更新status=2即可
			item.setStatus((byte) 2);
		} else if (method.equals("delete")) {
			// 商品状态为删除，更新status=3即可
			item.setStatus((byte) 3);
		}

		for (Long id : ids) {
			// 创建查询条件，根据id更新
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdEqualTo(id);
			// 第一个参数：是要修改的部分值组成的对象，其中有些属性为null则表示该项不修改。
			// 第二个参数：是一个对应的查询条件的类， 通过这个类可以实现 order by 和一部分的where 条件。
			mapper.updateByExampleSelective(item, example);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(Long itemId) {
		// return mapper.selectByPrimaryKey(itemId);

		// 添加缓存

		// 1.从缓存中获取数据，如果有直接返回
		try {
			String jsonstr = client.get(ITEM_INFO_KEY + ":" + itemId + ":BASE");

			if (StringUtils.isNotBlank(jsonstr)) {
				// 重新设置商品的有效期
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonstr, TbItem.class);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 2如果没有数据

		// 注入mapper
		// 调用方法
		TbItem tbItem = mapper.selectByPrimaryKey(itemId);

		// 3.添加缓存到redis数据库中
		// 注入jedisclient
		// ITEM_INFO:123456:BASE
		// ITEM_INFO:123456:DESC
		try {
			client.set(ITEM_INFO_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
			// 设置缓存的有效期
			client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回tbitem
		return tbItem;
	}

	@Override
	public TbItemDesc getItemDescById(Long itemId) {
		// return descmapper.selectByPrimaryKey(itemId);

		// 添加缓存

		// 1.从缓存中获取数据，如果有直接返回
		try {
			String jsonstr = client.get(ITEM_INFO_KEY + ":" + itemId + ":DESC");

			if (StringUtils.isNotBlank(jsonstr)) {
				// 重新设置商品的有效期
				System.out.println("有缓存");
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
				return JsonUtils.jsonToPojo(jsonstr, TbItemDesc.class);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// 如果没有查到数据 从数据库中查询
		TbItemDesc itemdesc = descmapper.selectByPrimaryKey(itemId);
		// 添加缓存
		// 3.添加缓存到redis数据库中
		// 注入jedisclient
		// ITEM_INFO:123456:BASE
		// ITEM_INFO:123456:DESC
		try {
			client.set(ITEM_INFO_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemdesc));
			// 设置缓存的有效期
			client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemdesc;
	}

}
