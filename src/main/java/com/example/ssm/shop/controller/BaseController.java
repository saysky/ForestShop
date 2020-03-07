package com.example.ssm.shop.controller;

import com.example.ssm.shop.dto.CommonConstant;
import com.example.ssm.shop.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 言曌
 * @date 2020/2/23 9:03 下午
 */
@Controller
public class BaseController {

    @Autowired
    private HttpServletRequest request;


    /**
     * 获得登录用户
     * @return
     */
    public User getLoginUser() {
        User user = (User) request.getSession().getAttribute(CommonConstant.USER_SESSION_KEY);
        return user;
    }
}
