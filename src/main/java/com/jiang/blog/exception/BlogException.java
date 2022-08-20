package com.jiang.blog.exception;

// 只捕获运行时错误
public class BlogException extends RuntimeException {
    private final Integer code;
    private final String message;

    // 构造函数
    public BlogException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    //   构造函数 调用枚举类型
    public BlogException(BlogExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMes());
    }


    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
