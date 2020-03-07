package com.example.ssm.shop.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Order)实体类
 *
 * @since 2020-03-06 09:23:17
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = -25485605794925400L;

    /**
     * ID
     */
    private Long id;

    /**
     * 订单ID
     */
    private Long productId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单状态：0待支付,1待发货, 2已发货, 3已收货
     */
    private Integer status;

    /**
     * 价格
     */
    private Long price;

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