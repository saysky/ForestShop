package com.example.ssm.shop.enums;

/**
 * @author 言曌
 * @date 2020/2/27 11:42 下午
 */

public enum ProductStatusEnum {

    /**
     * 流拍
     */
    FAIL(0),

    /**
     * 正在拍卖
     */
    NORMAL(1),

    /**
     * 成交
     */
    SUCCESS(2);



    private Integer value;

    ProductStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
