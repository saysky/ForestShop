package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.entity.Cart;
import com.example.ssm.shop.mapper.CartMapper;
import com.example.ssm.shop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/2/25 10:09 下午
 */

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    @Override
    public int deleteById(Long id) {
        return cartMapper.deleteById(id);
    }

    @Override
    public int deleteByProductId(Long productId) {
        return cartMapper.deleteByProductId(productId);
    }

    @Override
    public int insert(Cart cart) {
        return cartMapper.insert(cart);
    }

    @Override
    public Cart findById(Long id) {
        return cartMapper.findById(id);
    }

    @Override
    public int update(Cart cart) {
        return cartMapper.update(cart);
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> cartList =  cartMapper.findAll();
        return cartList;
    }

    @Override
    public Cart findByUserIdAndProductId(Long userId, Long productId) {
        return cartMapper.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Cart> findByUserId(Long userId) {
        List<Cart> cartList =  cartMapper.findByUserId(userId);
        return cartList;
    }

    @Override
    public int countByUserId(Long userId) {
        return cartMapper.countByUserId(userId);
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return cartMapper.deleteByUserId(userId);
    }
}
