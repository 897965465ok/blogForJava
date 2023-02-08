package com.jiang.blog.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.VO.MenuTableHeader;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @ApiOperation("查询文章列表")
    @GetMapping("/articles")
    public ApiRestResponse queryManyArticle(Integer offset, Integer limit) {
        PageInfo pageInfo = articleService.queryManyArticle(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/article")
    public ApiRestResponse deleteArticle(@RequestParam(name = "id") Integer id) {
        Integer result = articleService.deleteOneArticle(id);
        if (result == 0) {
            return ApiRestResponse.error(BlogExceptionEnum.DELETE_Article_FALL);
        }
        return ApiRestResponse.success(result);
    }

    @ApiOperation("查询一篇文章")
    @GetMapping("/article")
    public ApiRestResponse queryOneArticle(@RequestParam(name = "id") Integer id) {
        Article article = articleService.queryOneArticle(id);
        if (article == null) {
            return ApiRestResponse.error(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        }
        return ApiRestResponse.success(article);
    }

    @ApiOperation("查询文章表格头")
    @GetMapping("/queryArticleTableHeader")
    public ApiRestResponse queryArticleTableHeader() {
        ArticleTableHeader articleTableHeader =  articleService.queryMenuTableHeader();
        return ApiRestResponse.success(articleTableHeader);
    }

    //TODO 修改  增加
}
