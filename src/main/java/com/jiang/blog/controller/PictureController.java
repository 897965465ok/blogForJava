package com.jiang.blog.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.service.PictureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @ApiOperation("查询图片")
    @GetMapping("/wallhaven")
    public ApiRestResponse queryPicture(Integer offset, Integer limit) {
        PageInfo pageInfo = pictureService.queryManyPicture(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }
}
