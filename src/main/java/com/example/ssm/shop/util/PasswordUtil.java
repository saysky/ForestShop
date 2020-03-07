package com.example.ssm.shop.util;

import com.example.ssm.shop.dto.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 密码工具类
 *
 * @author 言曌
 * @date 2020-02-22 22:06
 */

public class PasswordUtil {

    /**
     * 对密码加密
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        return Md5Util.encode(password, CommonConstant.PASSWORD_SALT, CommonConstant.PASSWORD_ENCODE_TIMES);
    }

    /**
     * 匹配密码是否正确
     *
     * @param origin 加密前的
     * @param target 加密后的
     * @return
     */
    public static boolean match(String origin, String target) {
        if (StringUtils.isEmpty(origin) || StringUtils.isEmpty(target)) {
            return false;
        }
        if (Objects.equals(origin, target)) {
            return false;
        }
        // 防止调用者参数选反了，兼容
        if (Objects.equals(encode(origin), target) || Objects.equals(encode(target), origin)) {
            return true;
        }
        return false;
    }
}
