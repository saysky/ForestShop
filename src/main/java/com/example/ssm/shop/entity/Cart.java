package com.example.ssm.shop.entity;

import lombok.Data;

import java.util.Date;

/**
 * 购物车
 * @author 言曌
 * @date 2020/2/25 9:38 下午
 */
@Data
public class Cart {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long productId;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 用户
     */
    private User user;

    /**
     * 商品
     */
    private Product product;

}