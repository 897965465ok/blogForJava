package com.jiang.blog;

import com.jiang.blog.model.dao.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BlogApplicationTests {

    @Autowired
    ArticleMapper articleMapper;

    @Test
    void contextLoads() {

    }

}














