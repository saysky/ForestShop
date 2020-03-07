package com.example.ssm.shop.dto;

import lombok.Data;

/**
 * 响应封装类
 *
 * @author 言曌
 * @date 2020-02-22 21:27
 */
@Data
public class JsonResult {


    /**
     * 错误码：0正常，1错误
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的具体内容
     */
    private Object data;


    public JsonResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResult() {
    }


    public static JsonResult error() {
        return new JsonResult(1, "操作失败", null);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(1, msg, null);
    }

    public static JsonResult success() {
        return new JsonResult(0, "操作成功", null);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(0, "操作成功", data);
    }

    public static JsonResult success( String msg, Object data) {
        return new JsonResult(0, msg, data);
    }
}
