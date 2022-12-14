package com.jiang.blog.controller;

import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.github.pagehelper.PageInfo;
import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
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
import java.util.Map;

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
    public ApiRestResponse userRegister(
            @NotEmpty(message = "账号不能为空") @RequestParam String account,
            @NotEmpty(message = "密码不能为空") @RequestParam String password) {
        if (userService.register(account, password) == 1) {
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

        Map userinfo = userService.userLogin(account, password);

        return ApiRestResponse.success(userinfo);
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

    @ApiOperation("获取所有用户")
    @GetMapping("/queryManyUser")
    public ApiRestResponse queryManyUser(Integer offset, Integer limit) {
        PageInfo pageInfo = userService.queryManyUser(offset, limit);
        return ApiRestResponse.success(pageInfo);
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


}
