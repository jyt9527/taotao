package com.taotao.search.test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * 
 * @ClassName: SolrCloudTest
 * @Description: 使用solrj管理solrcloud集群
 * @author: JYT
 * @date: 2019年9月19日 上午9:14:43
 */
public class SolrCloudTest {
	/**
	 * 
	 * @Title: testAdd
	 * @Description: 测试往solrcloud集群中添加数据到索引库
	 * @throws Exception void
	 * @author JYT
	 * @date 2019年9月19日上午9:16:37
	 */
	@Test
	public void testAdd() throws Exception {
		// 1.创建solrserver 集群的实现类
		// 指定zookeeper集群的节点列表字符串
		CloudSolrServer cloudSolrServer = new CloudSolrServer(
				"192.168.25.131:2182,192.168.25.131:2183,192.168.25.131:2184");
		// 2.设置默认的搜索的collection 默认的索引库（不是core所对应的，是指整个collection索引集合）
		cloudSolrServer.setDefaultCollection("collection2");// collection2指的是http://192.168.25.131:8180/solr中Cloud中的collection2
		// 3.创建solrinputdocumenet对象
		SolrInputDocument document = new SolrInputDocument();
		// 4.添加域到文档
		document.addField("id", "testcloudid");
		document.addField("item_title", "今天鸟语花香，容易睡觉");
		// 5.将文档提交到索引库中
		cloudSolrServer.add(document);
		// 6.提交
		cloudSolrServer.commit();
	}
}
