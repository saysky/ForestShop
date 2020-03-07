package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.entity.Bidding;
import com.example.ssm.shop.entity.Order;
import com.example.ssm.shop.entity.Product;
import com.example.ssm.shop.enums.BiddingStatusEnum;
import com.example.ssm.shop.enums.OrderStatusEnum;
import com.example.ssm.shop.enums.ProductStatusEnum;
import com.example.ssm.shop.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.example.ssm.shop.mapper.*;
import com.example.ssm.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 言曌
 * @date 2020/2/23 3:20 下午
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BiddingMapper biddingMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int deleteById(Long id) {
        return productMapper.deleteById(id);
    }

    @Override
    public int insert(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public Product findById(Long id) {
        return productMapper.findById(id);
    }

    @Override
    public int update(Product product) {
        return productMapper.update(product);
    }

    @Override
    public List<Product> findAll(Map<String, Object> criteria) {
        return productMapper.findAll(criteria);
    }

    @Override
    public PageInfo<Product> findAll(Integer pageIndex, Integer pageSize, Map<String, Object> criteria) {
        PageHelper.startPage(pageIndex, pageSize);
        List<Product> productList = productMapper.findAll(criteria);
        return new PageInfo<>(productList);
    }

    @Override
    @Transactional
    public synchronized void updateProductStatus() {
        // 查询所有正在拍卖的商品，但是实际已经到了截止时间的
        List<Product> productList = productMapper.findNormalAndOvertimeProductList();

        for (Product product : productList) {
            // 更新竞价最高的状态为竞价成功
            Bidding bidding = biddingMapper.findBiddingOfMaxPriceByProductId(product.getId());
            if (bidding != null) {
                bidding.setStatus(BiddingStatusEnum.SUCCESS.getValue());
                biddingMapper.update(bidding);

                // 更新该商品为竞拍成功
                product.setStatus(ProductStatusEnum.SUCCESS.getValue());
                productMapper.update(product);

                // 创建订单
                Order order = new Order();
                order.setUserId(bidding.getUserId());
                order.setPrice(bidding.getPrice());
                order.setStatus(OrderStatusEnum.NOT_PAY.getValue());
                order.setCreateTime(new Date());
                order.setProductId(bidding.getProductId());
                try {
                    orderMapper.insert(order);
                } catch (Exception e) {
                }
            } else {
                product.setStatus(ProductStatusEnum.FAIL.getValue());
                productMapper.update(product);
            }

        }
    }

    @Override
    public List<Product> findByUserId(Long userId) {
        return productMapper.findByUserId(userId);
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return productMapper.deleteByUserId(userId);
    }
}
