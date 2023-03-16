package com.jiang.blog.common;

import com.jiang.blog.exception.BlogExceptionEnum;

public class ApiRestResponse<T> {
    private static final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private Integer code;
    private String message;
    private T result;

    public ApiRestResponse(Integer code, String message, T result) {
        this.code = code;
        this.message = message;
        this.result = result;
    }

    // 这种叫做重载
    public ApiRestResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    // 为不用填参数而设计出
    public ApiRestResponse() {
        this(SUCCESS_CODE, SUCCESS_MESSAGE);
    }

    public static <T> ApiRestResponse<T> success() {
        // 调动构造函数
        return new ApiRestResponse();
    }

    // 返回内容呗
    public static <T> ApiRestResponse<T> success(T result) {
        ApiRestResponse<T> response = new ApiRestResponse<>();

        response.setResult(result);
        return response;
    }

    // 第二层
    public static <T> ApiRestResponse<T> error(Integer status, String message) {
        return new ApiRestResponse(status, message);
    }

    // 第一层
    public static <T> ApiRestResponse<T> error(BlogExceptionEnum exceptionEnum) {
        return new ApiRestResponse(exceptionEnum.getCode(), exceptionEnum.getMes());
    }

    public static int getSuccessCode() {
        return SUCCESS_CODE;
    }

    public static String getSuccessMessage() {
        return SUCCESS_MESSAGE;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
