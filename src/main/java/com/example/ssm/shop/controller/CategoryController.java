package com.example.ssm.shop.controller;

import com.example.ssm.shop.dto.JsonResult;
import com.example.ssm.shop.entity.Category;
import com.example.ssm.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author 言曌
 * @date 2020/2/23 5:58 下午
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获得分类列表
     * @return
     */
    @RequestMapping(value = "/category", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult findAll() {
        List<Category> categoryList = categoryService.findAll();
        return JsonResult.success(categoryList);
    }

}
