package com.example.ssm.shop.service;

import com.example.ssm.shop.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 用户Service
 * @author 言曌
 * @date 2020-02-22 21:17
 */
public interface UserService {

    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<User> findAll(Map<String, Object> map);

    /**
     * 根据id查询用户信息
     *
     * @param id 用户ID
     * @return 用户
     */
    User findById(Long id);

    /**
     * 修改用户信息
     *
     * @param user 用户
     */
    void updateUser(User user);

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);

    /**
     * 添加用户
     *
     * @param user 用户
     * @return 用户
     */
    User insertUser(User user);

    /**
     * 根据用户名和邮箱查询用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    User findByUsernameOrEmail(String str);

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     * @return 用户
     */
    User findByUsername(String name);

    /**
     * 根据邮箱查询用户
     *
     * @param email Email
     * @return 用户
     */
    User findByEmail(String email);


}
