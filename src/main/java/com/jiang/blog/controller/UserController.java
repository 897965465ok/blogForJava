package com.jiang.blog.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;

import com.jiang.blog.model.VO.UserTableHeader;
import com.jiang.blog.model.pojo.Role;
import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.ArticleService;
import com.jiang.blog.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/v1")
@RestController
@Validated
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ArticleService articleService;

    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ApiRestResponse userRegister(@RequestBody User user) {
        if (userService.register(user) == 1) {
            return ApiRestResponse.success();
        } else {
            return ApiRestResponse.error(BlogExceptionEnum.CREATED_FALL);
        }
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ApiRestResponse userLogin(
            @NotEmpty(message = "账号不能为空") @RequestParam String account,
            @NotEmpty(message = "密码不能为空") @RequestParam String password) {
        SaTokenInfo userinfo = userService.userLogin(account, password);
        if (!Objects.isNull(userinfo)) {
            return ApiRestResponse.success(userinfo);
        } else {
            return ApiRestResponse.error(400, "用户或者密码错误");
        }

    }


    @PostMapping("/userUpdate")
    @ApiOperation("修改用户")
    public ApiRestResponse userUpdate(@RequestBody User user) {
        Integer count = userService.userUpdate(user);
        if (count > 0) {
            return ApiRestResponse.success(count);
        } else {
            return ApiRestResponse.error(BlogExceptionEnum.CREATED_FALL);
        }
    }

    @ApiOperation("批量删除用户")
    @PostMapping("/deleteManyUser")
    public ApiRestResponse deleteManyUser(@RequestBody ArrayList<String> ids) {
        int result = userService.deleteManyUser(ids);
        if (result > 0) {
            return ApiRestResponse.success(result);
        } else {
            return ApiRestResponse.error(400, "删除失败");
        }

    }

    @GetMapping("/getInfo")
    @ApiOperation("获取用户信息")
    public ApiRestResponse getInfo() {

        long userId = (long) userService.getInfo();

        return ApiRestResponse.success(userId);
    }


    @GetMapping("/favor")
    @ApiOperation("文章点赞")
    public ApiRestResponse favor(@RequestParam(name = "articleId") Integer id) {
        articleService.favor(id);
        return ApiRestResponse.success();
    }

    @GetMapping("/watchnumber")
    @ApiOperation("文章观看次数")
    public ApiRestResponse visited(@RequestParam(name = "uuid") Integer id) {
        articleService.visit(id);
        return ApiRestResponse.success();
    }


    @ApiOperation("获取用户表格表头")
    @GetMapping("/queryUserTableHeader")
    public ApiRestResponse queryUserTableHeader() {
        UserTableHeader userTableHeader = userService.queryUserTableHeader();
        return ApiRestResponse.success(userTableHeader);
    }


    @ApiOperation("获取所有用户")
    @GetMapping("/queryManyUser")
    public ApiRestResponse queryManyUser(Integer offset, Integer limit) {
        List userlist = userService.queryManyUser(offset, limit);
        return ApiRestResponse.success(userlist);
    }


    @ApiOperation("单文件上传")
    @PostMapping("/updateFile")
    public ApiRestResponse updateFile(@RequestParam(name = "jerry") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "F:\\JAVA\\Mblog\\src\\main\\resources\\static\\" + fileName;
        File dest = new File(filePath);
        try {
            InputStream Input = file.getInputStream();
            Files.copy(Input, dest.toPath());
        } catch (IOException e) {
            return new ApiRestResponse(404, "文件存入失败", e);
        }
        return ApiRestResponse.success();
    }


    @ApiOperation("根据用户查询出角色")
    @PostMapping("/queryRolesByUserId")
    public ApiRestResponse queryRolesByUserId( @NotEmpty(message = "账号不能为空") @RequestParam String account,
                                               @NotEmpty(message = "密码不能为空") @RequestParam String password) {
        User user   =   userService.selectByUserName(account);
        if (!Objects.isNull(user)){
         List <Role>  roles =  userService.queryRolesByUserId(user);
            return ApiRestResponse.success(roles);
        }else {
            return ApiRestResponse.error(400, "失败");
        }

    }


}
