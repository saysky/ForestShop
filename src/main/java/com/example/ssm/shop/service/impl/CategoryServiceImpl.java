package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.entity.Category;
import com.example.ssm.shop.mapper.CategoryMapper;
import com.example.ssm.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/2/23 5:56 下午
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public int deleteById(Long id) {
        return categoryMapper.deleteById(id);
    }

    @Override
    public int insert(Category category) {
        return categoryMapper.insert(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryMapper.findById(id);
    }

    @Override
    public int update(Category category) {
        return categoryMapper.update(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryMapper.findAll();
    }
}
