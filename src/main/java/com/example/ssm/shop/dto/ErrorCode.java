package com.example.ssm.shop.dto;

import lombok.Data;

/**
 * 状态码
 *
 * @author 言曌
 * @date 2020-02-22 21:36
 */
@Data
public class ErrorCode {

    /**
     * 成功状态
     */
    public final static Integer SUCCESS = 0;

    /**
     * 错误状态
     */
    public final static Integer ERROR = 1;

    /**
     * 成功状态码
     */
    public final static Integer SUCCESS_STATUS_CODE = 200;

    /**
     * 没有权限
     */
    public final static Integer BAD_REQUEST_ERROR_CODE = 400;

    /**
     * 参数绑定错误
     */
    public final static Integer UNAUTHORIZED_ERROR_CODE = 403;


    /**
     * 页面不存在
     */
    public final static Integer PAGE_NOT_FOUND_ERROR_CODE = 404;

    /**
     * 方法不存在
     */
    public final static Integer METHOD_NOT_ALLOWED_ERROR_CODE = 405;

    /**
     * 媒体类型不支持
     */
    public final static Integer MEDIA_TYPE_NOT_SUPPORTED_ERROR_CODE = 415;

    /**
     * 服务器内部错误
     */
    public final static Integer SERVER_ERROR_CODE = 500;


}
