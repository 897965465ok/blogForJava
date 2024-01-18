package com.jiang.blog.controller;


import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.model.VO.MenuTableHeader;
import com.jiang.blog.model.pojo.Menu;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1")
public class MenuController {
    @Autowired
    MenuService menuService;


    @ApiOperation("添加一个菜单")
    @PostMapping("/createMenu")
    public ApiRestResponse createMenu(@RequestBody Menu menu) {
        boolean result = menuService.createMenu(menu);
        if (result) {
            return ApiRestResponse.success(result);
        } else {
            return ApiRestResponse.error(400, "创建菜单失败");
        }

    }

    @ApiOperation("删除一个菜单")
    @GetMapping("/deleteMenu")
    public ApiRestResponse deleteMenuById(String id) {
        if (menuService.deleteMenuByOne(id)) {
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(400, "删除失败");
        }

    }

    @ApiOperation("删除多个菜单")
    @PostMapping("/deleteManyMenu")
    public ApiRestResponse deleteManyMenu(@RequestBody List<Menu> Menus) {
        if (menuService.deleteManyMenu(Menus)) {
            return ApiRestResponse.success("批量删除成功");
        } else {
            return ApiRestResponse.error(400, "删除失败");
        }
    }

    @ApiOperation("查询菜单列表")
    @GetMapping("/menus")
    public ApiRestResponse queryManyMenu(Integer offset, Integer limit) {
        PageInfo pageInfo = menuService.queryManyMenu(offset, limit);
        return ApiRestResponse.success(pageInfo);
    }


    @ApiOperation("查询菜单表格头")
    @GetMapping("/queryMenuTableHeader")
    public ApiRestResponse queryMenuTableHeader() {
        MenuTableHeader menuTableHeader = menuService.queryMenuTableHeader();
        return ApiRestResponse.success(menuTableHeader);
    }


    @ApiOperation("查询一个菜单列表")
    @GetMapping("/menu")

    public ApiRestResponse queryOneMenu(Integer id) {
        menuService.queryOneMenu(id);
        return ApiRestResponse.success();
    }


    @ApiOperation("修改一个菜单")
    @GetMapping("/updateMenuById")
    public ApiRestResponse updateMenuById(Integer id) {
        menuService.queryOneMenu(id);
        return ApiRestResponse.success();
    }


    @ApiOperation("获取前端路由")
    @GetMapping("/getRouter")
    public ApiRestResponse getRouter() {
        List routers = menuService.getRouter();
        return ApiRestResponse.success(routers);
    }


}
