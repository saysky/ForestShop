package com.example.ssm.shop.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户
 * @author 言曌
 * @date 2020-02-22 20:48
 */
@Data
public class User {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 显示名
     */
    private String displayName;

    /**
     * Email
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 地址
     */
    private String address;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 状态：1正常，0禁止登录
     */
    private Integer status;

    /**
     * 类型，1管理员，0普通用户
     */
    private Integer type;

}
