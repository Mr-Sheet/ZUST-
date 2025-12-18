package com.zjtec.travel.util;

import java.security.MessageDigest;

/**
 * MD5加密工具类
 * 功能：将明文密码转换为MD5加密后的密文
 * 特点：与MySQL的md5()函数结果保持一致
 * 示例：明文"123456"加密后为"e10adc3949ba59abbe56e057f20f883e"
 * 老师可能提问：
 * 1. 为什么要使用MD5对密码进行加密？
 * 2. MD5算法的特点是什么？
 * 3. 为什么Md5Util类被声明为final？
 */
public final class Md5Util {
	/**
	 * 私有构造方法
	 * 作用：防止外部创建实例，确保工具类的单例特性
	 */
	private Md5Util(){}
	/**
	 * 将明文密码转成MD5密码 
	 * @param password 明文密码
	 * @return String MD5加密后的密文
	 * @throws Exception 加密过程中可能出现的异常
	 * 老师可能提问：
	 * 1. 为什么使用MessageDigest类进行MD5加密？
	 * 2. 加密后的结果长度是多少？
	 * 3. 为什么要处理byte数组？
	 */
	public static String encodeByMd5(String password) throws Exception{
		//Java中MessageDigest类封装了MD5和SHA算法，获取MD5算法实例
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		//调用MD5算法，返回16个byte类型的值
		byte[] byteArray = md5.digest(password.getBytes());
		//将byte数组转换为十六进制字符串
		return byteArrayToHexString(byteArray);
	}
	/**
	 * 将byte[]转换为16进制字符串 
	 * @param byteArray MD5加密后的byte数组
	 * @return String 十六进制字符串
	 * 老师可能提问：
	 * 1. 为什么需要将byte数组转换为十六进制字符串？
	 * 2. 这个方法的时间复杂度是多少？
	 */
	private static String byteArrayToHexString(byte[] byteArray) {
		StringBuffer sb = new StringBuffer();
		//遍历byte数组（MD5加密后固定16个字节）
		for(byte b : byteArray){//16次
			//将每个byte转换为十六进制字符串
			String hex = byteToHexString(b);
			//将转换后的值放入StringBuffer中
			sb.append(hex);
		}
		return sb.toString();
	}
	/**
	 * 将byte转换为16进制字符串 
	 * @param b 单个byte值
	 * @return String 十六进制字符串
	 * 示例：-31转成e1，10转成0a
	 * 老师可能提问：
	 * 1. 为什么要处理负数情况？
	 * 2. 256 + n的作用是什么？
	 * 3. 为什么使用除以16和取余16的方式？
	 */
	private static String byteToHexString(byte b) {
		//将byte类型转换为int类型
		int n = b;
		//如果n是负数，将其转换为正数
		if(n < 0){
			//-31的16进制数，等价于求225的16进制数
			n = 256 + n;
		}
		//计算商和余数，确定十六进制的两个字符
		int d1 = n / 16; // 商，确定第一个十六进制字符的下标
		int d2 = n % 16; // 余数，确定第二个十六进制字符的下标
		//通过下标从十六进制字符数组中取值并拼接
		return hex[d1] + hex[d2];
	}
	/**
	 * 十六进制字符数组
	 * 用于将数字转换为对应的十六进制字符
	 */
	private static String[] hex = {"0","1","2","3","4","5","6","7","8","9","a","b","c","d","e","f"};
	
	/**
	 * 测试方法
	 * 功能：验证MD5加密功能是否正常工作
	 */
	public static void main(String[] args) throws Exception{
		String password = "123456";
		String passwordMD5 = Md5Util.encodeByMd5(password);
		System.out.println("明文密码：" + password);
		System.out.println("MD5密文：" + passwordMD5);
	}
}
