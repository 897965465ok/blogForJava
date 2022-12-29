package com.jiang.blog.controller;


import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class RoleController {

    @Autowired
    RoleService roleService;

    @ApiOperation("获取所有用户")
    @GetMapping("/queryManyRole")
    public ApiRestResponse queryManyRole(Integer offset, Integer limit) {
        PageInfo pageInfo = roleService.queryManyRole(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }
}
