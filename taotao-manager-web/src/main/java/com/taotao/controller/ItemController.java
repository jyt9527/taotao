package com.taotao.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import com.taotao.web.util.FastDFSClient;

/**
 * 
 * @ClassName: ItemController
 * @Description: 商品的Controller
 * @author: JYT
 * @date: 2019年9月8日 上午11:29:10
 */
@Controller
public class ItemController {
	@Value("${TAOTAO_IMAGE_SERVER_URL}")
	private String TAOTAO_IMAGE_SERVER_URL;
	@Autowired
	private ItemService itemService;

	// url:/item/list
	// method:get
	// 参数：page,rows
	// 返回值:json
	/**
	 * 
	 * @Title: getItemList
	 * @Description: TODO
	 * @param page
	 * @param rows
	 * @return EasyUIDataGridResult//返回值:EasyUI的json
	 * @author JYT
	 * @date 2019年9月8日上午11:30:48
	 */
	@RequestMapping(value = "/item/list", method = RequestMethod.GET)
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 1.引入服务，在springmvc.xml
		// 2.注入服务
		// 3.调用服务的方法
		return itemService.getItemList(page, rows);
	}

//	@RequestMapping("/item/list")
//	@ResponseBody
//	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
//		EasyUIDataGridResult result = itemService.getItemList(page, rows);
//		return result;
//	}

	/**
	 * @ResponseBody 默认的content-type:application/json;charset=utf-8 google浏览器是支持
	 *               //使用火狐浏览器 使用kindeditor的时候不支持
	 *               content-type:application/json;charset=utf-8
	 *               //解决：设置content-type:text/plain;charset=utf-8 都支持
	 * @param uploadFile
	 * @return
	 */

	// 上传图片的方法
//	@RequestMapping("/pic/upload")
//	@ResponseBody
//	public Map<String, Object> uploadImage(MultipartFile uploadFile){
//		try {
//			// 1.获取元文件的扩展名
//			String originalFilename = uploadFile.getOriginalFilename();
//			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
//			// 2.获取文件的字节数组
//			byte[] bytes = uploadFile.getBytes();
//			// 3.通过fastdfsclient的方法上传图片（参数要求有 字节数组 和扩展名 不包含"."）
//			FastDFSClient client = new FastDFSClient("classpath:resource/fastdfs.conf");
//			// 返回值：group1/M00/00/00/wKgZhVk4vDqAaJ9jAA1rIuRd3Es177.jpg
//			String string = client.uploadFile(bytes, extName);
//			//拼接成完整的URL
//			String path = "http://192.168.25.133/"+string;
//			// 4.成功时，设置map
//			Map<String, Object> map = new HashMap<>();
//			map.put("error", 0);
//			map.put("url", path);
//			// 6.返回map
//			return map;
//		} catch (Exception e) {
//			// 5.失败时，设置map
//			Map<String, Object> map = new HashMap<>();
//			map.put("error", 1);
//			map.put("message", "上传失败");
//			return map;
//		}
//	}

	// produces:就可以设置content-type
	/**
	 * 
	 * @Title: uploadImage
	 * @Description: 上传图片的方法
	 * @param uploadFile
	 * @return String
	 * @author JYT
	 * @date 2019年9月8日下午9:31:11
	 */
	@RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
	@ResponseBody
	// 就需要将字符串转成jONS 格式的字符串就可以了
	public String uploadImage(MultipartFile uploadFile) {
		try {
			// 1.获取元文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
			// 2.获取文件的字节数组
			byte[] bytes = uploadFile.getBytes();
			// 3.通过fastdfsclient的方法上传图片（参数要求有 字节数组 和扩展名 不包含"."）
			FastDFSClient client = new FastDFSClient("classpath:resource/fastdfs.conf");
			// 返回值：group1/M00/00/00/wKgZhVk4vDqAaJ9jAA1rIuRd3Es177.jpg
			String string = client.uploadFile(bytes, extName);
			// 拼接成完整的URL
			// "http://192.168.25.133/"
			String path = TAOTAO_IMAGE_SERVER_URL + string;
			// 4.成功时，设置map
			Map<String, Object> map = new HashMap<>();
			map.put("error", 0);
			map.put("url", path);
			// 6.返回map
			return JsonUtils.objectToJson(map);
		} catch (Exception e) {
			// 5.失败时，设置map
			Map<String, Object> map = new HashMap<>();
			map.put("error", 1);
			map.put("message", "上传失败");
			return JsonUtils.objectToJson(map);
		}
	}

	// 添加商品的方法
	// url:：/item/save
	// 参数：tbitem ,desc
	// 返回值 json
	// method:post
	/**
	 * 
	 * @Title: saveItem
	 * @Description: 添加商品的方法
	 * @param item
	 * @param desc
	 * @return TaotaoResult :json
	 * @author JYT
	 * @date 2019年9月8日下午9:31:26
	 */
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveItem(TbItem item, String desc) {
//		//1.引入服务
		// 2.注入服务
		// 3.调用
		return itemService.saveItem(item, desc);
	}

	/**
	 * 
	 * @Title: getItemDesc
	 * @Description: 根据商品id，获取商品描述
	 * @param itemId
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午8:13:27
	 */
	@RequestMapping(value = "/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDesc(itemId);
		return result;
	}

	/**
	 * 
	 * @Title: updateItem
	 * @Description: 更新
	 * @param item
	 * @param desc
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午8:15:59
	 */
	@RequestMapping(value = "/rest/item/update", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult updateItem(TbItem item, String desc) {
		TaotaoResult result = itemService.updateItem(item, desc);
		return result;
	}

	/**
	 * 
	 * @Title: updateItemStatus
	 * @Description: 根据商品id，更新商品状态：1-正常(上架)，2-下架，3-删除
	 * @param ids
	 * @param method
	 * @return TaotaoResult
	 * @author JYT
	 * @date 2019年9月10日下午9:17:57
	 */
	@RequestMapping("/rest/item/{method}")
	@ResponseBody
	public TaotaoResult updateItemStatus(@RequestParam(value = "ids") List<Long> ids, @PathVariable String method) {
		TaotaoResult result = itemService.updateItemStatus(ids, method);
		return result;
	}
}
