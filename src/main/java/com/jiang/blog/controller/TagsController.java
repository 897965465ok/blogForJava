package com.jiang.blog.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.pojo.Tags;
import com.jiang.blog.service.ArticleService;
import com.jiang.blog.service.TagsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/v1")
@RestController
public class TagsController {
    @Autowired
    TagsService tagsService;
    @Autowired
    ArticleService articleService;

    @ApiOperation("查询所有标签")
    @GetMapping("/tags")
    public ApiRestResponse queryByTags() {
        List<Tags> tagsList = tagsService.queryByTags();
        return ApiRestResponse.success(tagsList);
    }

    @ApiOperation("创建标签")
    @PostMapping("/tags")
    public ApiRestResponse creatByTags(@RequestParam(value = "article_tag") String tags) {
        Integer result = tagsService.creatByTags(tags);
        if (result == 0) {
            return ApiRestResponse.error(BlogExceptionEnum.TAGS_EXISTS);
        }
        return ApiRestResponse.success();

    }

    @ApiOperation("根据标签查询全部文章")
    @GetMapping("/query")
    public ApiRestResponse QueryArticlesByTags(
            @RequestParam(name = "tags") String tags,
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "0") Integer limit) {
        PageInfo pageInfo = articleService.queryArticlesByTags(tags, offset, limit);

        return ApiRestResponse.success(pageInfo);
    }


    @ApiOperation("删除标签")
    @PostMapping("/deleteTags")
    public ApiRestResponse deleteTags(@RequestParam ArrayList<Long> id) {

        Boolean result = tagsService.deleteTags(id);

        if (result) {
            return ApiRestResponse.success();
        }
        return ApiRestResponse.error(BlogExceptionEnum.DELETE_TAGS_FALL);

    }

    @ApiOperation("修改标签")
    @PostMapping("/updateTags")
    public ApiRestResponse updateTags(@RequestParam("id") Long id, @RequestParam("content") String content) {

        Long result = tagsService.updateTags(id, content);

        return ApiRestResponse.success(result);
    }


    @ApiOperation("查询标签表格头")
    @GetMapping("/queryTagsTableHeader")
    public ApiRestResponse queryTagsTableHeader() {
        return ApiRestResponse.success(tagsService.queryTagsTableHeader());
    }


}
