package com.example.ssm.shop.service;

import com.example.ssm.shop.entity.Cart;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/2/25 10:08 下午
 */
public interface CartService {

    /**
     * 根据ID删除
     *
     * @param id 购物车ID
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
     * @param cart 购物车
     * @return 影响行数
     */
    int insert(Cart cart);

    /**
     * 根据ID查询
     *
     * @param id 购物车ID
     * @return 购物车
     */
    Cart findById(Long id);

    /**
     * 更新
     *
     * @param cart 购物车
     * @return 影响行数
     */
    int update(Cart cart);

    /**
     * 获得购物车列表
     *
     * @return 购物车列表
     */
    List<Cart> findAll();

    /**
     * 根据用户ID和商品ID 查询购物车是否有记录
     *
     * @param userId
     * @param productId
     * @return
     */
    Cart findByUserIdAndProductId(Long userId, Long productId);


    /**
     * 根据用户ID获得购物车列表
     *
     * @return
     */
    List<Cart> findByUserId(Long userId);

    /**
     * 根据用户ID统计
     *
     * @return
     */
    int countByUserId(Long userId);

    /**
     * 根据用户ID删除
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);
}
