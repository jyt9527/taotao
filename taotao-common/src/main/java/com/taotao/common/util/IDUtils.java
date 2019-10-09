package com.taotao.common.util;

import java.util.Random;

/**
 * 
 * @ClassName: IDUtils
 * @Description: ID相关生成的方法的类
 * @author: JYT
 * @date: 2019年9月8日 下午8:33:13
 */
public class IDUtils {

	/**
	 * 
	 * @Title: genImageName
	 * @Description: 图片名生成
	 * @return String
	 * @author JYT
	 * @date 2019年9月18日下午4:32:59
	 */
	public static String genImageName() {
		// 取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		// long millis = System.nanoTime();
		// 加上三位随机数
		Random random = new Random();
		int end3 = random.nextInt(999);
		// 如果不足三位前面补0
		String str = millis + String.format("%03d", end3);

		return str;
	}

	/**
	 * 
	 * @Title: genItemId
	 * @Description: 商品id生成
	 * @return long
	 * @author JYT
	 * @date 2019年9月18日下午4:33:10
	 */
	public static long genItemId() {
		// 取当前时间的长整形值包含毫秒
		long millis = System.currentTimeMillis();
		// long millis = System.nanoTime();
		// 加上两位随机数
		Random random = new Random();
		int end2 = random.nextInt(99);
		// 如果不足两位前面补0
		String str = millis + String.format("%02d", end2);
		long id = new Long(str);
		return id;
	}

	/**
	 * 
	 * @Title: main
	 * @Description: 测试
	 * @param args void
	 * @author JYT
	 * @date 2019年9月18日下午4:33:21
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++)
			System.out.println(genItemId());
	}
}
