package com.jiang.blog.common;

import com.jiang.blog.exception.BlogExceptionEnum;
import lombok.Data;


@Data
public class ApiRestResponse<T> {
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private Integer code;
    private String message;
    private T result;

     // 构造函数
    public ApiRestResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /*
     * success 直接返回成功
     */
    public ApiRestResponse() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    // 调动无参构造
    public static ApiRestResponse success() {

        return new ApiRestResponse();
    }

    // 状态加内容
    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setResult(result);
        return response;
    }

    public ApiRestResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }


    //  有参数错误处理
    public static <T> ApiRestResponse<T> error(Integer status, String message) {
        return new ApiRestResponse(status, message);
    }

    // 错误静态方法
    public static <T> ApiRestResponse<T> error(BlogExceptionEnum exceptionEnum) {
        return new ApiRestResponse(exceptionEnum.getCode(), exceptionEnum.getMes());
    }

}
