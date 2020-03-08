package com.example.ssm.shop.controller;

import com.example.ssm.shop.dto.CommonConstant;
import com.example.ssm.shop.dto.JsonResult;
import com.example.ssm.shop.entity.*;
import com.example.ssm.shop.enums.OrderStatusEnum;
import com.example.ssm.shop.enums.ProductStatusEnum;
import com.example.ssm.shop.enums.UserStatusEnum;
import com.example.ssm.shop.enums.UserTypeEnum;
import com.example.ssm.shop.exception.MyBusinessException;
import com.example.ssm.shop.service.*;
import com.example.ssm.shop.util.PasswordUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 言曌
 * @date 2020/3/6 10:34 上午
 */
@RequestMapping("/account")
@Controller
public class AccountController extends BaseController {

    @Autowired
    private CartService cartService;

    @Autowired
    private BiddingService biddingService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    public static final Long TWO_DAY = 1000 * 3600 * 24 * 2L;

    /**
     * 账号中心
     *
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String account(Model model) {
        User loginUser = getLoginUser();
        Long id = loginUser.getId();
        Boolean isAdmin = Objects.equals(loginUser.getType(), UserTypeEnum.ADMIN.getValue());

        // 竞拍列表
        List<Bidding> biddingList = isAdmin ? biddingService.findAll(null) : biddingService.findByUserId(id);
        model.addAttribute("biddingList", biddingList);

        // 商品列表
        List<Product> productList = isAdmin ? productService.findAll(null) : productService.findByUserId(id);
        model.addAttribute("productList", productList);

        // 订单列表
        List<Order> orderList = isAdmin ? orderService.findAll() : orderService.findByUserId(id);
        model.addAttribute("orderList", orderList);

        model.addAttribute("user", loginUser);
        if (Objects.equals(loginUser.getType(), UserTypeEnum.ADMIN.getValue())) {
            // 用户管理
            Map<String, Object> map = new HashMap<>();
            map.put("type", UserTypeEnum.USER.getValue());
            List<User> userList = userService.findAll(map);
            model.addAttribute("userList", userList);
            return "account-admin";
        } else {
            // 购物车列表
            List<Cart> cartList = cartService.findByUserId(id);
            model.addAttribute("cartList", cartList);
            return "account-user";
        }
    }


    /**
     * 保存账号
     *
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult saveAccount(@RequestBody User user,
                                  HttpSession session) {
        User loginUser = getLoginUser();
        user.setId(loginUser.getId());

        // 判断用户名是否存在
        User checkUsername = userService.findByUsername(user.getUsername());
        if(checkUsername != null && !Objects.equals(checkUsername.getId(), loginUser.getId())) {
            return JsonResult.error("用户名已存在");
        }

        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.error("保存失败");
        }

        // 修改Session信息
        user.setType(loginUser.getType());
        session.setAttribute(CommonConstant.USER_SESSION_KEY, user);
        return JsonResult.success("保存成功");
    }

    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/password", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePassword(@RequestParam("oldPassword") String oldPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmPassword") String confirmPassword) {
        User loginUser = getLoginUser();
        Long userId = loginUser.getId();

        User user = userService.findById(userId);
        if (!PasswordUtil.match(user.getPassword(), oldPassword)) {
            return JsonResult.error("旧密码错误");
        }

        if (!Objects.equals(newPassword, confirmPassword)) {
            return JsonResult.error("两次密码不一致");
        }

        user.setPassword(PasswordUtil.encode(newPassword));
        userService.updateUser(user);

        return JsonResult.success("保存成功");
    }

    /**
     * 商品发布
     *
     * @return
     */
    @RequestMapping(value = "/product/publish", method = RequestMethod.GET)
    public String productPublishPage(Model model) {
        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);
        return "product-publish";
    }

    /**
     * 删除商品
     *
     * @return
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public JsonResult removeProduct(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Product product = productService.findById(id);
        if (product == null) {
            return JsonResult.error("商品不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(product.getUserId(), user.getId()) && !Objects.equals(user.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 删除商品记录
        productService.deleteById(id);
        // 删除购物车
        cartService.deleteByProductId(id);
        // 删除竞价
        biddingService.deleteByProductId(id);
        // 删除订单
        orderService.deleteByProductId(id);
        return JsonResult.success("删除成功", null);
    }


    /**
     * 商品修改
     *
     * @return
     */
    @RequestMapping(value = "/product/edit/{id}", method = RequestMethod.GET)
    public String productEditPage(@PathVariable("id") Long id, Model model) {

        User user = getLoginUser();

        // 分类列表
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("categoryList", categoryList);

        Product product = productService.findById(id);
        if (product == null) {
            throw new MyBusinessException("商品不存在");
        }

        // 管理员和所属人可以删除
        if (!Objects.equals(product.getUserId(), user.getId()) && !user.getType().equals(UserTypeEnum.ADMIN.getValue())) {
            throw new MyBusinessException("无权操作");

        }

        model.addAttribute("product", product);
        return "product-edit";
    }

    /**
     * 商品发布/修改提交
     *
     * @return
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult productPublishSubmit(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("name") String name,
            @RequestParam("summary") String summary,
            @RequestParam("imgUrl") String imgUrl,
            @RequestParam("cateId") Long cateId,
            @RequestParam("startPrice") Long startPrice,
            @RequestParam("endTime") String endTime) {

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(summary) ||
                StringUtils.isEmpty(imgUrl) || StringUtils.isEmpty(endTime) ||
                startPrice == null) {
            return JsonResult.error("请填写完整信息");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = getLoginUser();
        Date now = new Date();

        Product product = null;
        if (id != null) {
            product = productService.findById(id);
            if (product == null) {
                return JsonResult.error("商品不存在");
            }
            // 管理员和所属人可以删除
            if (!Objects.equals(product.getUserId(), user.getId()) && !user.getType().equals(UserTypeEnum.ADMIN.getValue())) {
                return JsonResult.error("无权操作");
            }
        } else {
            product = new Product();
        }
        product.setId(id);
        product.setName(name);
        product.setSummary(summary);
        product.setImgUrl(imgUrl);
        product.setStartPrice(startPrice);
        product.setCurrentPrice(startPrice);
        product.setStatus(ProductStatusEnum.NORMAL.getValue());
        product.setCateId(cateId);
        product.setUserId(user.getId());

        try {
            Date endTimeDate = sdf.parse(endTime);
            if (endTimeDate.before(now)) {
                return JsonResult.error("截止时间必须大于当前时间");
            }
            product.setEndTime(endTimeDate);

        } catch (ParseException e) {
            e.printStackTrace();
            product.setEndTime(new Date(System.currentTimeMillis() + TWO_DAY));
        }

        if (id == null) {
            product.setCreateTime(now);
            productService.insert(product);
        } else {
            productService.update(product);
        }
        return JsonResult.success(product.getId());
    }


    /**
     * 移除竞价
     *
     * @return
     */
    @RequestMapping(value = "/bidding/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public JsonResult removeBidding(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Bidding bidding = biddingService.findById(id);
        if (bidding == null) {
            return JsonResult.error("记录不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(bidding.getUserId(), user.getId()) && !Objects.equals(user.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 删除竞价记录
        biddingService.deleteById(id);
        return JsonResult.success("移除成功", null);
    }

    /**
     * 购物车页面
     *
     * @return
     */
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String index(Model model) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return "redirect:/login";
        }

        List<Cart> cartList = cartService.findAll();
        model.addAttribute("cartList", cartList);
        return "cart";
    }


    /**
     * 移除购物车
     *
     * @return
     */
    @RequestMapping(value = "/cart/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonResult removeCart(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Cart cart = cartService.findById(id);
        if (cart == null) {
            return JsonResult.error("记录不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(cart.getUserId(), user.getId())) {
            return JsonResult.error("无权操作");
        }

        // 删除购物车记录
        cartService.deleteById(id);
        return JsonResult.success("移除成功", null);
    }

    /**
     * 支付
     *
     * @return
     */
    @RequestMapping(value = "/order/pay/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult payOrder(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Order order = orderService.findById(id);
        if (order == null) {
            return JsonResult.error("记录不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(order.getUserId(), user.getId()) && !Objects.equals(user.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 更新状态为待发货
        order.setStatus(OrderStatusEnum.NOT_SEND.getValue());
        orderService.update(order);
        return JsonResult.success("支付成功", null);
    }

    /**
     * 发货
     *
     * @return
     */
    @RequestMapping(value = "/order/send/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult sendOrder(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Order order = orderService.findById(id);
        if (order == null) {
            return JsonResult.error("订单记录不存在");
        }

        Product product = productService.findById(order.getProductId());
        if (product == null) {
            return JsonResult.error("订单所在商品不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(user.getId(), product.getUserId()) && !Objects.equals(user.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 更新状态为待发货
        order.setStatus(OrderStatusEnum.NOT_CONFIRM.getValue());
        orderService.update(order);
        return JsonResult.success("发货成功", null);
    }

    /**
     * 确认
     *
     * @return
     */
    @RequestMapping(value = "/order/confirm/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult confirmOrder(@PathVariable("id") Long id) {
        // 获得登录用户
        User user = getLoginUser();
        if (user == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        Order order = orderService.findById(id);
        if (order == null) {
            return JsonResult.error("记录不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(order.getUserId(), user.getId()) && !Objects.equals(user.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 更新状态为待发货
        order.setStatus(OrderStatusEnum.CONFIRM.getValue());
        orderService.update(order);
        return JsonResult.success("确认成功", null);
    }

    /**
     * 禁用用户
     *
     * @return
     */
    @RequestMapping(value = "/user/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult disableCart(@PathVariable("id") Long id) {
        // 获得登录用户
        User loginUser = getLoginUser();
        if (loginUser == null) {
            return JsonResult.error("请先登录");
        }

        // 判断记录是否存在
        User user = userService.findById(id);
        if (user == null) {
            return JsonResult.error("记录不存在");
        }

        // 判断该ID是否属于该用户
        if (!Objects.equals(loginUser.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 删除购物车记录
        if (Objects.equals(user.getStatus(), UserStatusEnum.NORMAL.getValue())) {
            user.setStatus(UserStatusEnum.BAN.getValue());
            userService.updateUser(user);
            return JsonResult.success("禁用成功", null);

        } else {
            user.setStatus(UserStatusEnum.NORMAL.getValue());
            userService.updateUser(user);
            return JsonResult.success("启用成功", null);
        }
    }

    /**
     * 删除用户
     *
     * @return
     */
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @Transactional
    public JsonResult removeUser(@PathVariable("id") Long id) {
        // 获得登录用户
        User loginUser = getLoginUser();
        if (loginUser == null) {
            return JsonResult.error("请先登录");
        }


        // 判断是否为管理员
        if (!Objects.equals(loginUser.getType(), UserTypeEnum.ADMIN.getValue())) {
            return JsonResult.error("无权操作");
        }

        // 删除用户
        userService.deleteUser(id);
        // 删除订单
        orderService.deleteByUserId(id);
        // 删除购物车
        cartService.deleteByUserId(id);
        // 删除竞价
        biddingService.deleteByUserId(id);
        return JsonResult.success("删除成功");
    }

}
