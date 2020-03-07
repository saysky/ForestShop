package com.example.ssm.shop.mapper;

import com.example.ssm.shop.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper
 *
 * @author 言曌
 * @date 2020-02-22 20:53
 */
@Mapper
public interface ProductMapper {

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
     * @param product 商品
     * @return 影响行数
     */
    int insert(Product product);

    /**
     * 根据ID查询
     *
     * @param id 商品ID
     * @return 商品
     */
    Product findById(Long id);

    /**
     * 更新
     *
     * @param product 商品
     * @return 影响行数
     */
    int update(Product product);

    /**
     * 获得商品列表
     *
     * @return 商品列表
     */
    List<Product> findAll(Map<String, Object> criteria);

    /**
     * 根据商品名查商品
     *
     * @param name 商品名
     * @return 商品
     */
    Product findByName(String name);

    /**
     * 更新所有超时的为流拍状态
     * @return
     */
    int updateAllStatus();

    /**
     * 根据用户ID获得商品列表
     * @param userId
     * @return
     */
    List<Product> findByUserId(Long userId);

    /**
     * 获得超时且正在拍卖的
     * @return
     */
    List<Product> findNormalAndOvertimeProductList();

    /**
     * 根据用户ID删除
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);
}
