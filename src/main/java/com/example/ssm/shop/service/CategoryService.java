package com.example.ssm.shop.service;

import com.example.ssm.shop.entity.Category;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/2/23 5:55 下午
 */
public interface CategoryService {

    /**
     * 根据ID删除
     *
     * @param id 商品ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 添加
     *
     * @param category 商品
     * @return 影响行数
     */
    int insert(Category category);

    /**
     * 根据ID查询
     *
     * @param id 商品ID
     * @return 商品
     */
    Category findById(Long id);

    /**
     * 更新
     *
     * @param category 商品
     * @return 影响行数
     */
    int update(Category category);

    /**
     * 获得商品列表
     *
     * @return 商品列表
     */
    List<Category> findAll();
}
