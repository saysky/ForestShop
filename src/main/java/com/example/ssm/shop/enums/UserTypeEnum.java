package com.example.ssm.shop.enums;

/**
 * @author 言曌
 * @date 2020/3/1 9:46 下午
 */

public enum UserTypeEnum {

    /**
     * 管理员
     */
    ADMIN(1),

    /**
     * 普通用户
     */
    USER(0)

    ;

    private Integer value;

    UserTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
