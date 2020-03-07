package com.example.ssm.shop.service.impl;

import com.example.ssm.shop.entity.User;
import com.example.ssm.shop.mapper.UserMapper;
import com.example.ssm.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 用户Service实现
 * @author 言曌
 * @date 2020-02-22 21:19
 */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List<User> findAll(Map<String, Object> map) {
        List<User> userList = userMapper.findAll(map);
        return userList;
    }

    @Override
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public User insertUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User findByUsernameOrEmail(String str) {
        return userMapper.findByUsernameOrEmail(str);
    }

    @Override
    public User findByUsername(String name) {
        return userMapper.findByUsername(name);
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
