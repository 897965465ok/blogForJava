package com.jiang.blog;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

    @Test
    void   editText() throws UnsupportedEncodingException {

        String  text = "a我a";
        byte[] Contenter =  text.getBytes("GBK");

        String  text2 =  new  String(Contenter,"UTF-8");

        /*
          String提供了如下方法
        byte[] getBytes()
        使用平台的默认字符集将该String编码为一系列字节，将结果存储到新的字节数组中

        byte[ ] getBytes(String charsetName)
        使用指定的字符集将该String编码为一系列字节，将结果存储到新的字节数组中

        string提供了如下方法
        String(byte[] bytes)
        通过使用平台的默认字符集解码指定的字节数组来构造新的String

        string(byte[ ] bytes，string charsetName)
        通过指定的字符集解码指定的字节数组来构造新的String
         */






    }

    @Test
    void upDataToMini() throws IOException {

        try(
                FileInputStream fs = new FileInputStream("./123.txt");
        ) {

        }

    }




}
