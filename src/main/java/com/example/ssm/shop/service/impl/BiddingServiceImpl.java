package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.entity.Bidding;
import com.example.ssm.shop.mapper.BiddingMapper;
import com.example.ssm.shop.service.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author 言曌
 * @date 2020/2/23 10:25 下午
 */
@Service
public class BiddingServiceImpl implements BiddingService {

    @Autowired
    private BiddingMapper biddingMapper;


    @Override
    public int deleteById(Long id) {
        return biddingMapper.deleteById(id);
    }

    @Override
    public int insert(Bidding bidding) {
        return biddingMapper.insert(bidding);
    }

    @Override
    public Bidding findById(Long id) {
        return biddingMapper.findById(id);
    }

    @Override
    public int update(Bidding bidding) {
        return biddingMapper.update(bidding);
    }

    @Override
    public List<Bidding> findAll(Map<String, Object> criteria) {
        List<Bidding> biddingList = biddingMapper.findAll(criteria);
        return biddingList;
    }

    @Override
    public Bidding findBiddingOfMaxPriceByProductId(Long productId) {
        return biddingMapper.findBiddingOfMaxPriceByProductId(productId);
    }

    @Override
    public Bidding findByUserIdAndProductId(Long userId, Long productId) {
        return biddingMapper.findByUserIdAndProductId(userId, productId);
    }

    @Override
    public List<Bidding> findByUserId(Long userId) {
        List<Bidding> biddingList = biddingMapper.findByUserId(userId);
        return biddingList;
    }

    @Override
    public int countByUserId(Long userId) {
        return biddingMapper.countByUserId(userId);
    }

    @Override
    public int deleteByProductId(Long productId) {
        return biddingMapper.deleteByProductId(productId);
    }

    @Override
    public Integer deleteByUserId(Long userId) {
        return biddingMapper.deleteByUserId(userId);
    }
}
