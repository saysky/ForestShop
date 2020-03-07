package com.example.ssm.shop.entity;

import lombok.Data;

import java.util.Date;

/**
 * 竞价记录
 * @author 言曌
 * @date 2020/2/23 9:36 下午
 */
@Data
public class Bidding {

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
     * 价格
     */
    private Long price;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 竞价状态：1成功，0失败
     */
    private Integer status;

    /**
     * 用户
     */
    private User user;

    /**
     * 商品
     */
    private Product product;


}

