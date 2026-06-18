package com.jiang.blog.controller;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.ArticleAndFileVO;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.pojo.Article;
import com.jiang.blog.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @ApiOperation("查询文章列表")
    @GetMapping("/queryManyArticle")
    public ApiRestResponse queryManyArticle(Integer offset, Integer limit) {
        PageInfo pageInfo = articleService.queryManyArticle(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }

    @ApiOperation("删除文章")
    @DeleteMapping("/deleteArticle")
    public ApiRestResponse deleteArticle(@RequestParam(name = "id") Integer id) {
        Integer result = articleService.deleteOneArticle(id);
        if (result == 0) {
            return ApiRestResponse.error(BlogExceptionEnum.DELETE_Article_FALL);
        }
        return ApiRestResponse.success(result);
    }

    @ApiOperation("删除多文章")
    @PostMapping("/deleteManyArticle")
    public ApiRestResponse deleteManyArticle(@RequestParam(name = "id") ArrayList<Long> id) {
        Boolean result = articleService.deleteManyArticle(id);

        if (result) {
            return ApiRestResponse.success("删除成功");

        } else {
            return ApiRestResponse.error(BlogExceptionEnum.DELETE_Article_FALL);
        }

    }


    @ApiOperation("查询一篇文章")
    @GetMapping("/queryOneArticle")
    public ApiRestResponse queryOneArticle(@RequestParam(name = "id") Long id) {
        Article article = articleService.queryOneArticle(id);
        if (article == null) {
            return ApiRestResponse.error(BlogExceptionEnum.ARTICLE_NOT_EXISTS);
        }
        return ApiRestResponse.success(article);
    }

    @ApiOperation("查询文章表格头")
    @GetMapping("/queryArticleTableHeader")
    public ApiRestResponse queryArticleTableHeader() {
        ArticleTableHeader articleTableHeader = articleService.ArticleTableHeader();
        return ApiRestResponse.success(articleTableHeader);
    }


    @ApiOperation("新增文章")
    @PostMapping("/addOneArticle")
    public ApiRestResponse addOneArticle(@ModelAttribute ArticleAndFileVO articleAndFileVO) throws IOException {

        String fileUrl = articleService.addOneArticle(articleAndFileVO);
        return ApiRestResponse.success(fileUrl);
    }


    @ApiOperation("修改文章")
    @PostMapping("/updateOneArticle")
    public ApiRestResponse updateOneArticle(@RequestParam("article") String articleJson, @RequestPart("file") MultipartFile file) throws IOException {
        Article article = JSONUtil.toBean(articleJson, Article.class);
        String result = articleService.updateOneArticle(article, file);
        return ApiRestResponse.success();
    }

    @ApiOperation("批量导入文章")
    @PostMapping("/batchAddArticles")
    public ApiRestResponse batchAddArticles(@RequestParam("files") List<MultipartFile> files) throws IOException {
        Integer count = articleService.batchAddArticles(files);
        return ApiRestResponse.success(count);
    }
}
