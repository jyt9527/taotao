package com.taotao.listener;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.servlet.jsp.jstl.core.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.taotao.item.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.service.ItemService;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * @ClassName: ItemChangeGenHtmMessageListener
 * @Description: 监听器 ,获取消息 ,执行生成静态页面的业务逻辑
 * @author: JYT
 * @date: 2019年9月21日 下午8:18:53
 */
public class ItemChangeGenHtmMessageListener implements MessageListener {

	@Autowired
	private ItemService itemservcie;
	@Autowired
	private FreeMarkerConfigurer config;

	@Override
	public void onMessage(Message message) {
		if (message instanceof TextMessage) {
			// 1.获取消息 商品的id
			TextMessage message2 = (TextMessage) message;
			try {
				Long itemId = Long.valueOf(message2.getText());
				// 2.从数据库中获取数据 可以调用manager中的服务 获取到了数据集
				// 引入服务
				// 注入服务
				// 调用
				TbItem tbItem = itemservcie.getItemById(itemId);
				Item item = new Item(tbItem);// 转成在页面中显示数据时的POJO
				TbItemDesc tbItemDesc = itemservcie.getItemDescById(itemId);
				// 3.生成静态页面 准备好模板 和 数据集
				// 方法
				genHtmlFreemarker(item, tbItemDesc);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 
	 * @Title: genHtmlFreemarker
	 * @Description: 生成静态页面
	 * @param item
	 * @param tbItemDesc
	 * @throws Exception void
	 * @author JYT
	 * @date 2019年9月21日下午8:36:47
	 */
	private void genHtmlFreemarker(Item item, TbItemDesc tbItemDesc) throws Exception {
		// 1.获取configuration对象
		Configuration configuration = config.getConfiguration();
		// 2.创建模板 获取模板文件对象
		Template template = configuration.getTemplate("item.ftl");

		// 3.创建数据集
		Map model = new HashMap<>();
		model.put("item", item);
		model.put("itemDesc", tbItemDesc);

		// 4.输出
		// F:\taotao_freemarker\freemarker\item\1234.html
		Writer writer = new FileWriter(new File("F:/taotao_freemarker/freemarker/item" + "/" + item.getId() + ".html"));

		template.process(model, writer);
		// 5.关闭流
		writer.close();
	}

}
