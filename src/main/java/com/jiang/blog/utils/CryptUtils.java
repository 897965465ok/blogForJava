package com.jiang.blog.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;


public class CryptUtils {

    public static Boolean verifyPassword(String password, String bcryptHashString) {

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), bcryptHashString);
        return result.verified;
    }

    public static String GeneratePassword(String password, Integer number) {

        String bcryptHashString = BCrypt.withDefaults().hashToString(number, password.toCharArray());

        return bcryptHashString;
    }

}
