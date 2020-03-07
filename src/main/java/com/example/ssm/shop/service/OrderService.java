package com.example.ssm.shop.service;

import com.example.ssm.shop.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/3/6 9:32 上午
 */

public interface OrderService {

    /**
     * 根据ID删除
     *
     * @param id 订单ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据商品ID删除
     *
     * @param productId 商品ID
     * @return 影响行数
     */
    int deleteByProductId(Long productId);

    /**
     * 添加
     *
     * @param order 订单
     * @return 影响行数
     */
    int insert(Order order);

    /**
     * 根据ID查询
     *
     * @param id 订单ID
     * @return 订单
     */
    Order findById(Long id);

    /**
     * 更新
     *
     * @param order 订单
     * @return 影响行数
     */
    int update(Order order);

    /**
     * 获得订单列表
     *
     * @return 订单列表
     */
    List<Order> findAll();

    /**
     * 根据用户ID和商品ID 查询订单是否有记录
     *
     * @param userId
     * @param productId
     * @return
     */
    Order findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据用户ID获得订单列表
     *
     * @return
     */
    List<Order> findByUserId(Long userId);

    /**
     * 根据用户ID删除
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);
}
