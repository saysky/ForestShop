package com.example.ssm.shop.schedule;

import com.example.ssm.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时器，定时更新超过2天的竞价为流拍
 * @author 言曌
 * @date 2020/2/27 10:48 下午
 */
@Component
public class TimerTask {

    @Autowired
    private ProductService productService;

    /**
     * 每分钟执行一次
     * 更新已经到了截止时间的(发布时间超过2天)的为状态流拍
     * 删除截止时间到了的购物车
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void updateProductStatus() {
        productService.updateProductStatus();
    }
}
