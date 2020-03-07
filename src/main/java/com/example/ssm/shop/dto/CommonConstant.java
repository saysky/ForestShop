package com.example.ssm.shop.dto;

/**
 * 通用常量类
 * @author 言曌
 * @date 2020-02-22 22:04
 */
public class CommonConstant {

    /**
     * 密码加密的盐
     */
    public static final String PASSWORD_SALT = "saysky";

    /**
     * 密码加密次数
     */
    public static final int PASSWORD_ENCODE_TIMES = 10;

    /**
     * 用户登录Session的key名称
     */
    public static final String USER_SESSION_KEY = "user";
}
