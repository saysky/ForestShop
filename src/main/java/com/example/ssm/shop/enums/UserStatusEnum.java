package com.example.ssm.shop.enums;

/**
 * @author 言曌
 * @date 2020/3/1 9:46 下午
 */

public enum  UserStatusEnum {

    /**
     * 正常
     */
    NORMAL(1),

    /**
     * 禁止登录
     */
    BAN(0)

    ;

    private Integer value;

    UserStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
