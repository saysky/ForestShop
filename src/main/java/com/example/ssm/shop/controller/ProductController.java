package com.example.ssm.shop.controller;

import com.example.ssm.shop.entity.*;
import com.example.ssm.shop.service.*;
import com.example.ssm.shop.entity.*;
import com.example.ssm.shop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @author 言曌
 * @date 2020/2/23 8:50 下午
 */
@Controller
public class ProductController extends BaseController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private CartService cartService;

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable("id") Long id, Model model) {
        // 商品信息
        Product product = productService.findById(id);
        if (product == null) {
            return "forward:/404";
        }
        // 用户信息
        User user = userService.findById(product.getUserId());
        product.setUser(user);


        // 分类信息
        Category category = categoryService.findById(product.getCateId());
        product.setCategory(category);

        // 竞价信息
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("productId", id);
        List<Bidding> biddingList = biddingService.findAll(criteria);
        product.setBiddingList(biddingList);

        User loginUser = getLoginUser();

        // 是否加入购物车
        boolean isAddCart = false;
        if (loginUser != null) {
            // 判断是否已经加入到购物车
            Cart cart = cartService.findByUserIdAndProductId(user.getId(), id);
            if (cart != null) {
                isAddCart = true;
            }
        }

        // 是否竞拍
        boolean isAddBidding = false;
        if (loginUser != null) {
            // 判断是否已经加入到购物车
            Bidding bidding = biddingService.findByUserIdAndProductId(loginUser.getId(), id);
            if (bidding != null) {
                isAddBidding = true;
                model.addAttribute("biddingPrice", bidding.getPrice());
            }
        }

        model.addAttribute("isAddCart", isAddCart);
        model.addAttribute("isAddBidding", isAddBidding);

        model.addAttribute("product", product);
        return "product-details";
    }



}
