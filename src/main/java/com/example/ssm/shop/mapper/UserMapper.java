package com.example.ssm.shop.mapper;

import com.example.ssm.shop.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户Mapper
 *
 * @author 言曌
 * @date 2020-02-22 20:53
 */
@Mapper
public interface UserMapper {

    /**
     * 根据ID删除
     *
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 添加
     *
     * @param user 用户
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 根据ID查询
     *
     * @param id 用户ID
     * @return 用户
     */
    User findById(Long id);

    /**
     * 更新
     *
     * @param user 用户
     * @return 影响行数
     */
    int update(User user);

    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    List<User> findAll(Map<String, Object> map);


    /**
     * 根据用户名或Email获得用户
     *
     * @param str 用户名或Email
     * @return 用户
     */
    User findByUsernameOrEmail(String str);

    /**
     * 根据用户名查用户
     *
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 根据Email查询用户
     *
     * @param email 邮箱
     * @return 用户
     */
    User findByEmail(String email);
}
