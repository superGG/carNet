package com.earl.carnet.commons.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5加密工具类
 * Created by Administrator on 2016/4/3.
 */
public class MD5Util {

    private static Logger logger = Logger.getLogger(MD5Util.class);

    /**
     * 登陆密码加密.
     *
     * @param plainText  加密明文
     * @return 加密后密码.
     */
    public static String md5(String plainText) {
        logger.info("进入MD5方法");
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
        logger.info("退出MD5方法");
        return buf.toString();
    }
}
