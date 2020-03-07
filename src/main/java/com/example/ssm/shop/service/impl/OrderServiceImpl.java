package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.mapper.OrderMapper;
import com.example.ssm.shop.entity.Order;
import com.example.ssm.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/3/6 9:33 上午
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int deleteById(Long id) {
        return orderMapper.deleteById(id);
    }

    @Override
    public int deleteByProductId(Long productId) {
        return orderMapper.deleteByProductId(productId);
    }

    @Override
    public int insert(Order order) {
        int result = 0;
        try {
            result = orderMapper.insert(order);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public Order findById(Long id) {
        return orderMapper.findById(id);
    }

    @Override
    public int update(Order order) {
        return orderMapper.update(order);
    }

    @Override
    public List<Order> findAll() {
        return orderMapper.findAll();
    }

    @Override
    public Order findByUserIdAndProductId(Long userId, Long productId) {
        return orderMapper.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderMapper.findByUserId(userId);
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return orderMapper.deleteByUserId(userId);
    }
}
