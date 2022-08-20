package com.jiang.blog.common;

public class UserContext {
    public static ThreadLocal<Long> context = new ThreadLocal<>();

    public static Long getUserId() {
        return context.get();
    }

    public static void setUserId(Long userId) {
        context.set(userId);
    }

    public static void shutdown() {
        context.remove();
    }

}
