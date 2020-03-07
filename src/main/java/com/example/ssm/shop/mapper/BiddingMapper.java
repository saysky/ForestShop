package com.example.ssm.shop.mapper;

import com.example.ssm.shop.entity.Bidding;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 竞价Mapper
 *
 * @author 言曌
 * @date 2020-02-22 20:53
 */
@Mapper
public interface BiddingMapper {

    /**
     * 根据ID删除
     *
     * @param id 竞价ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 添加
     *
     * @param bidding 竞价
     * @return 影响行数
     */
    int insert(Bidding bidding);

    /**
     * 根据ID查询
     *
     * @param id 竞价ID
     * @return 竞价
     */
    Bidding findById(Long id);

    /**
     * 更新
     *
     * @param bidding 竞价
     * @return 影响行数
     */
    int update(Bidding bidding);

    /**
     * 获得竞价列表
     *
     * @return 竞价列表
     */
    List<Bidding> findAll(Map<String, Object> criteria);

    /**
     * 根据商品ID获得最大价格竞价
     * @param productId
     * @return
     */
    Bidding findBiddingOfMaxPriceByProductId(Long productId);

    /**
     * 根据用户ID和商品ID 查询购物车是否有记录
     * @param userId
     * @param productId
     * @return
     */
    Bidding findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    /**
     * 根据用户ID获得购物车列表
     *
     * @return
     */
    List<Bidding> findByUserId(Long userId);

    /**
     * 根据用户ID统计
     *
     * @return
     */
    int countByUserId(Long userId);

    /**
     * 获得最大的竞价的商品列表
     * @return
     */
    List<Bidding> findFinalBiddingByMaxPrice();

    /**
     * 根据商品ID删除竞价
     * @param productId
     * @return
     */
    int deleteByProductId(Long productId);

    /**
     * 根据用户ID删除
     * @param userId
     * @return
     */
    Integer deleteByUserId(Long userId);
}
