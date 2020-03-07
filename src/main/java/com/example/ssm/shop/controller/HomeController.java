package com.example.ssm.shop.controller;

import com.example.ssm.shop.dto.JsonResult;
import com.example.ssm.shop.entity.*;
import com.example.ssm.shop.enums.BiddingStatusEnum;
import com.example.ssm.shop.enums.ProductStatusEnum;
import com.example.ssm.shop.service.BiddingService;
import com.example.ssm.shop.service.CartService;
import com.example.ssm.shop.service.CategoryService;
import com.example.ssm.shop.service.ProductService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author 言曌
 * @date 2020/2/23 1:53 下午
 */
@Controller
public class HomeController extends BaseController{

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private CartService cartService;
    /**
     * 首页, 商品列表
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(@RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                        @RequestParam(required = false, defaultValue = "12") Integer pageSize,
                        @RequestParam(required = false, defaultValue = "") String keywords,
                        @RequestParam(required = false) Long cateId,
                        Model model) {
        // 商品列表
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("keywords", keywords);
        criteria.put("cateId", cateId);
        criteria.put("status", ProductStatusEnum.NORMAL.getValue());
        PageInfo<Product> productPageInfo = productService.findAll(pageIndex, pageSize, criteria);
        model.addAttribute("pageInfo", productPageInfo);

        // 分类
        if (cateId != null) {
            Category category = categoryService.findById(cateId);
            if (category != null) {
                model.addAttribute("category", category);
            }
        }
        return "index";
    }

    /**
     * 竞拍
     *
     * @param productId
     * @param price
     * @return
     */
    @RequestMapping(value = "/bidding", method = RequestMethod.POST)
    @ResponseBody
    @Transactional
    public JsonResult addBidding(@RequestParam("productId") Long productId,
                                 @RequestParam("price") Long price) {

        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断商品是否存在
        Product product = productService.findById(productId);
        if (product == null) {
            return JsonResult.error("商品不存在");
        }

        // 判断商品是否已经到了截止时间
        if (!Objects.equals(product.getStatus(), ProductStatusEnum.NORMAL.getValue()) || product.getEndTime().getTime() <= new Date().getTime()) {
            product.setStatus(ProductStatusEnum.FAIL.getValue());;
            productService.update(product);
            return JsonResult.error("商品拍卖已经结束");
        }

        // 判断价格是否大于当前最大价格
        if (price <= product.getCurrentPrice()) {
            return JsonResult.error("价格必须大于当前竞价");
        }

        // 添加/更新竞价

        Bidding bidding = biddingService.findByUserIdAndProductId(user.getId(), productId);
        if (bidding != null) {
            // 更新竞价
            bidding.setPrice(price);
            bidding.setCreateTime(new Date());
            biddingService.update(bidding);
        } else {
            // 新增竞价
            bidding = new Bidding();
            bidding.setUserId(user.getId());
            bidding.setProductId(productId);
            bidding.setPrice(price);
            bidding.setCreateTime(new Date());
            bidding.setStatus(BiddingStatusEnum.NORMAL.getValue());
            biddingService.insert(bidding);
        }
        // 修改商品当前竞价
        product.setCurrentPrice(price);
        productService.update(product);
        return JsonResult.success("竞拍操作成功", null);
    }

    /**
     * 购物车总数
     *
     * @return
     */
    @RequestMapping(value = "/quantity", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult getCartQuantity() {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.success(0);
        }

        int cartTotal = cartService.countByUserId(user.getId());
        int biddingTotal = biddingService.countByUserId(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("cartTotal", cartTotal);
        map.put("biddingTotal", biddingTotal);
        return JsonResult.success(map);
    }

    /**
     * 加入购物车
     *
     * @return
     */
    @RequestMapping(value = "/cart", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addCart(@RequestParam("productId") Long productId) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断商品是否存在
        Product product = productService.findById(productId);
        if (product == null) {
            return JsonResult.error("商品不存在");
        }

        // 判断是否已经添加过购物车
        Cart cart = cartService.findByUserIdAndProductId(user.getId(), productId);
        if (cart != null) {
            return JsonResult.error("该商品已经在购物车");
        }

        // 添加购物车记录
        Cart newCart = new Cart();
        newCart.setUserId(user.getId());
        newCart.setProductId(productId);
        newCart.setCreateTime(new Date());
        cartService.insert(newCart);
        return JsonResult.success("已加入购物车", null);
    }

    /**
     * 404
     *
     * @return
     */
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String error404() {
        return "common/404";
    }


    /**
     * 404
     *
     * @return
     */
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error403() {
        return "common/403";
    }
}
