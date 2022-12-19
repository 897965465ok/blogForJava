package com.jiang.blog.controller;


import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class MenuController {
    @Autowired
    ArticleService articleService;

    @ApiOperation("查询菜单列表")
    @GetMapping("/menus")
    public ApiRestResponse queryManyArticle(Integer offset, Integer limit) {
        PageInfo pageInfo = articleService.queryManyArticle(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }


}
