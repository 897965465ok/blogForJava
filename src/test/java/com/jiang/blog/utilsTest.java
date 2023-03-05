package com.jiang.blog;

import org.junit.jupiter.api.Test;

import java.time.Duration;


public class utilsTest {

    @Test
    void contextLoads() {
      Duration Du = Duration.ofMinutes(60);
      System.out.println(Du.toString());

    }
}
