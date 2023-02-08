package com.jiang.blog.controller;


import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.model.VO.ArticleTableHeader;
import com.jiang.blog.model.VO.RoleAndMenuVO;
import com.jiang.blog.model.VO.RoleTableHeader;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.service.RoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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


    @ApiOperation("查询用户表格头")
    @GetMapping("/queryRoleTableHeader")
    public ApiRestResponse queryRoleTableHeader() {
        RoleTableHeader roleTableHeader = roleService.queryRoleTableHeader();
        return ApiRestResponse.success(roleTableHeader);
    }


    @PostMapping("/createRole")
    @ApiOperation("创建角色")
    public ApiRestResponse createRole(@RequestBody RoleAndMenuVO roleAndMenuVO) {

        int result = roleService.createRole(roleAndMenuVO.getRole(), roleAndMenuVO.getResource());
        if (result == 1) {
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(404, "创建失败");
        }
    }
}
