package com.jiang.blog.utils;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.jiang.blog.common.Constant;

public class Crypt {

    private static String key = Constant.SECRET;

    public static String encrypt(String password) {
        return SaSecureUtil.aesEncrypt(key, password);
    }

    public static String decrypt(String encryptText) {
        // 解密
        return SaSecureUtil.aesDecrypt(key, encryptText);
    }
}
