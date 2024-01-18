package com.jiang.blog;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.jiang.blog.utils.Crypt;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class utilsTest {

    @Test
    void contextLoads() {
      Duration Du = Duration.ofMinutes(60);
      System.out.println(Du.toString());


    }
    @Test
    void getPassword(){

        System.out.println(    SaSecureUtil.aesEncrypt("897965465", "897965465"));
    }
}
