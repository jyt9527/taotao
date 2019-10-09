package com.taotao.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.content.jedis.JedisClient;
import com.taotao.content.service.ContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;

/**
 * 
 * @ClassName: ContentServiceImpl
 * @Description: 内容处理的service实现类
 * @author: JYT
 * @date: 2019年9月9日 下午3:44:36
 */
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private JedisClient client;

	@Autowired
	private TbContentMapper mapper;

	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;

//对内容信息做增删改操作后只需要把对应缓存key删除即可。
	@Override
	public TaotaoResult saveContent(TbContent content) {
		// 1.注入mapper

		// 2.补全其他的属性
		content.setCreated(new Date());
		content.setUpdated(content.getCreated());
		// 3.插入内容表中
		mapper.insertSelective(content);
		// 缓存同步，一定要加上try-cash语句
		try {
			client.hdel(CONTENT_KEY, content.getCategoryId().toString());
		} catch (Exception e) {

			e.printStackTrace();
		}

		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		// 1.设置分页的信息 使用pagehelper
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 30;
		PageHelper.startPage(page, rows);
		// 2.注入mapper
		// 3.创建example 对象 设置查询条件
		TbContentExample example = new TbContentExample();
		// 设置查询条件
		TbContentExample.Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);// 根据内容分类的的id查询， select * from tb_content where category_id=？
		// 4.根据mapper调用查询所有数据的方法
		List<TbContent> list = mapper.selectByExample(example);
		// 5.获取分页的信息
		PageInfo<TbContent> info = new PageInfo<>(list);
		// 6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		// 7.返回
		return result;
	}

	/**
	 * 业务逻辑:
	 * 
	 * 根据id 更新tbContent 可以是逆向工程。（单表）
	 * 
	 * 补全其他没有更新过来的属性。(created updated)
	 * 
	 * 服务层发布服务(前面已经发布过了)
	 * 
	 * 表现层引入服务（调用服务方法，返回）
	 */
	@Override
	public TaotaoResult updateContent(TbContent tContent) {
		// 补全
		tContent.setUpdated(new Date());
		tContent.setCreated(tContent.getCreated());
		// 调用dao层方法
		mapper.updateByPrimaryKeySelective(tContent);
		// 缓存同步，一定要加上try-cash语句
		try {
			client.hdel(CONTENT_KEY, tContent.getCategoryId().toString());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return TaotaoResult.ok();
	}

	/**
	 * 业务逻辑:
	 * 
	 * 根据ids 的值分割字符串,得到id 的数组：以逗号切割
	 * 
	 * 根据id循环删除
	 */
	@Override
	public TaotaoResult deleteContent(String ids) {

		// 分割ids字符串
		String[] idsSplit = ids.split(",");
		// 遍历删除
		for (int i = 0; i < idsSplit.length; i++) {
			// String转成Long类型
			Long id = Long.parseLong(idsSplit[i]);
			mapper.deleteByPrimaryKey(id);
			// 根据id得到TbContent对象
			TbContent tbContent = mapper.selectByPrimaryKey(id);
			// 然后同步缓存删除
			// 记得try-catch
			try {
				client.hdel(CONTENT_KEY, tbContent.getCategoryId().toString());
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return TaotaoResult.ok();
	}

	/**
	 * 根据内容分类id查询列表。
	 * 
	 * 加入缓存（使用Redis缓存）
	 */
	@Override
	public List<TbContent> getContentListByCatId(Long categoryId) {
		// 添加缓存不能影响正常的业务逻辑

		// 判断 是否redis中有数据 如果有 直接从redis中获取数据 返回

		try {
			String jsonstr = client.hget(CONTENT_KEY, categoryId + "");// 从redis数据库中获取内容分类下的所有的内容。
			// 如果(不为空)存在，说明有缓存
			if (StringUtils.isNotBlank(jsonstr)) {
				System.out.println("这里有缓存啦！！！！！");
				return JsonUtils.jsonToList(jsonstr, TbContent.class);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 1.注入mapper
		// 2.创建example
		TbContentExample example = new TbContentExample();
		// 3.设置查询的条件
		example.createCriteria().andCategoryIdEqualTo(categoryId);// select × from tbcontent where category_id=1
		// 4.执行查询
		List<TbContent> list = mapper.selectByExample(example);

		// 将数据写入到redis数据库中

		// 注入jedisclient
		// 调用方法写入redis key - value
		try {
			System.out.println("没有缓存！！！！！！");
			// 名字为CONTENT_KEY的HashSet类型的redis数据
			client.hset(CONTENT_KEY, categoryId + "", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 返回
		return list;

	}
}
