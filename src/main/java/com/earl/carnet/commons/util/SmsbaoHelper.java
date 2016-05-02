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
 * @author 宋文光
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

	private String urlString;

	public SmsbaoHelper(String urlString) {
		this.urlString = urlString;
	}

	/**
	 * 获取字符段，发送指定信息.
	 * 
	 * @throws Exception
	 */
	public int send() throws Exception {
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
		return result;
	}

	/**
	 * 获取短信剩余量.
	 * 
	 * @throws Exception
	 */
	public int check() throws Exception {
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

	/**
	 * 登陆密码加密.
	 * 
	 * @param plainText
	 *            登陆smsbao密码.
	 * @return 加密后密码.
	 */
	public static String md5(String plainText) {
		StringBuilder buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes(Charset.forName("UTF-8")));// 指定字符串默认编码集，否则会使用系统默认编码，不同系统间移植性不高
			byte b[] = md.digest();
			int i;
			buf = new StringBuilder("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

}
