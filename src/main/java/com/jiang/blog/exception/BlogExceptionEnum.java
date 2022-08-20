package com.jiang.blog.exception;

public enum BlogExceptionEnum {
    CREATED_TAGS_FALL(1001, "创建失败"),
    TAGS_EXISTS(1002, "标签已存在"),
    DELETE_TAGS_FALL(1003, "删除失败"),
    DELETE_Article_FALL(1004, "删除失败"),
    ARTICLE_NOT_EXISTS(1005, "文章不存在"),
    TAGS_NOT_EXISTS(1006, "标签不存在"),
    CREATED_FALL(1007, "创建失败"),
    USER_EXISTS(1008, "用户已存在"),
    USER_NOT_EXISTS(1009, "密码或者账号不存在"),
    USER_NOT_LOGIN(10010, "用户未登录"),
    REQUEST_PARAM_ERROR(10012, "参数错误"),
    SYSTEM_ERROR(20000, "系统异常");
    // 异常码
    Integer code;
    // 异常信息
    String mes;

    BlogExceptionEnum(Integer code, String mes) {
        this.code = code;
        this.mes = mes;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
