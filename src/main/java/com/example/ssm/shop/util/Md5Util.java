package com.example.ssm.shop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author 言曌
 * @date 2020-02-22 21:58
 */
public class Md5Util {

    /**
     * 获得Md5加密
     *
     * @param str 原字符串
     * @return 加密后的字符串
     */
    public static String encode(String str) {
        String md5Str = null;
        if (str != null && str.length() != 0) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte b[] = md.digest();

                int i;
                StringBuffer buf = new StringBuffer("");
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
                //32位
                md5Str = buf.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return md5Str;
    }

    /**
     * 加盐加密
     *
     * @param str  原字符串
     * @param salt 盐
     * @return
     */
    public static String encode(String str, String salt) {
        return encode(str + salt);
    }

    /**
     * 加盐加密，多次加密
     *
     * @param str   原字符串
     * @param salt  盐
     * @param times 次数
     * @return
     */
    public static String encode(String str, String salt, int times) {
        times = times > 1 ? times : 1;
        str = str + salt;
        int i = 0;
        while (i < times) {
            str = encode(str);
            i++;
        }
        return str;
    }
}
