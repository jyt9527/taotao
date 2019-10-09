package com.taotao.common.util;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @ClassName: JsonUtils
 * @Description:Json数据与...之间的转换的方法的类
 * @author: JYT
 * @date: 2019年9月8日 下午8:33:25
 */
public class JsonUtils {

	// 定义jackson对象
	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 
	 * @Title: objectToJson
	 * @Description: 将对象转换成json字符串。
	 * @param data
	 * @return String
	 * @author JYT
	 * @date 2019年9月18日下午4:34:06
	 */
	public static String objectToJson(Object data) {
		try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: jsonToPojo
	 * @Description: 将json结果集转化为对象
	 * @param jsonData
	 * @param beanType
	 * @return T
	 * @author JYT
	 * @date 2019年9月18日下午4:34:18
	 */
	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			T t = MAPPER.readValue(jsonData, beanType);
			return t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: jsonToList
	 * @Description: 将json数据转换成pojo对象list
	 * @param jsonData
	 * @param beanType
	 * @return List<T>
	 * @author JYT
	 * @date 2019年9月18日下午4:34:27
	 */
	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			List<T> list = MAPPER.readValue(jsonData, javaType);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
