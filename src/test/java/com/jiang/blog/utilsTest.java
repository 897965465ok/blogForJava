package com.jiang.blog;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.pojo.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class utilsTest {


    @Autowired
    MenuMapper menuMapper;

    @Test
    void contextLoads() {
        Duration Du = Duration.ofMinutes(60);
        System.out.println(Du.toString());
        List<String> menus = filterMenu();
        System.out.println(menus);

    }

    @Test
    void getPassword() {

        System.out.println(SaSecureUtil.aesEncrypt("897965465", "897965465"));
    }

    @Test
    void editText() throws UnsupportedEncodingException {

        String text = "a我a";
        byte[] Contenter = text.getBytes("GBK");

        String text2 = new String(Contenter, "UTF-8");

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

        try (
                FileInputStream fs = new FileInputStream("./123.txt");
        ) {

        }

    }


    public List<String> filterMenu() {
        // todo 拿到用户权限列表
        // todo 根据用户权限列表拿到包含list的权限字符
        // todo 根据权限字符拿到菜单

        List<String> permissions = Arrays.asList(
                "blog:list",
                "article:list",
                "menu:list",
                "user:list",
                "role:list",
                "article:query",
                "article:edit",
                "article:plus",
                "article:delete",
                "menu:query",
                "menu:plus",
                "menu:delete",
                "menu:edit",
                "user:query",
                "user:plus",
                "user:edit",
                "user:delete",
                "role:plus",
                "role:delete",
                "role:query",
                "role:edit"
        );


      List<String>   stringList =  permissions.stream()
                .filter(item -> item.contains("list"))
                .map(permission -> permission.split(":")[0])
                .collect(Collectors.toList());

        QueryWrapper query = new QueryWrapper();
        query.like("perms", stringList);

        return menuMapper.selectList(query);

    }


}
