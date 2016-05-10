/**
 * Copyright (c) 2007-2015 WteamFly.  All rights reserved. 网飞网络公司 版权所有.
 * 请勿修改或删除版权声明及文件头部.
 */
package com.earl.carnet.commons.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 生成手机验证码工具类.
 * 
 * @author 宋
 * @since 3.0.0
 */
public class SmsbaoHelper {

	public SmsbaoHelper() {
		super();
	}

	public static final SmsbaoHelper mobileVerifyCodeHelper = new SmsbaoHelper();

	public static SmsbaoHelper getInstance() {
		return mobileVerifyCodeHelper;
	}

	private static String urlString;


	private static String username = "q410654146";// 短信宝帐户名
	private static String password = MD5Util.md5("940507");// 短信宝账户密码

	/**
	 * 获取字符段，发送指定信息.
	 * @param phoneNumber 指定发送手机号码.
	 * @param content 发送内容.
	 * @return
	 * @throws Exception
     */
	public static int send(String phoneNumber, String content) throws Exception {
		content = java.net.URLEncoder.encode(content, "utf-8");// 发送内容
		urlString =  "http://www.smsbao.com/sms?u="
				+ username + "&p=" + password + "&m=" + phoneNumber + "&c="
				+ content;

		URL url = new URL(urlString);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String line;
		int result = -1;
		if ((line = reader.readLine()) != null) {
			result = Integer.valueOf(line);
		}

		urlConnection.getInputStream().close();

		return result;
	}

	/**
	 * 获取短信剩余量.
	 * 
	 * @throws Exception
	 */
	public int check() throws Exception {
		urlString = "http://www.smsbao.com/query?u="
				+ username + "&p=" + password;
		URL url = new URL(urlString);
		HttpURLConnection urlConnection = (HttpURLConnection) url
				.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				urlConnection.getInputStream()));
		String line;
		int result = -1;
		// 验证是否发送成功
		if ((line = reader.readLine()) != null) {
			result = Integer.valueOf(line);
		}
		// 发送成功后，获取短信剩余量
		if (result == 0) {
			if ((line = reader.readLine()) != null) {
				int pos = line.indexOf(",");
				result = Integer.valueOf(line.substring(pos + 1));
			}
		} else {
			return -1;
		}
		System.out.println(result);
		return result;
	}

}
