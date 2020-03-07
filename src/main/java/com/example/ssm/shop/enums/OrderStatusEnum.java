package com.example.ssm.shop.enums;

/**
 * @author 言曌
 * @date 2020/3/1 9:46 下午
 */

public enum OrderStatusEnum {

    /**
     * 待支付
     */
    NOT_PAY(0),

    /**
     * 待发货
     */
    NOT_SEND(1),


    /**
     * 未确认收货
     */
    NOT_CONFIRM(2),

    /**
     * 已收货
     */
    CONFIRM(3),

    ;

    private Integer value;

    OrderStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
