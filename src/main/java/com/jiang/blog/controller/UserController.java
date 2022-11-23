package com.jiang.blog.controller;

import com.jiang.blog.common.ApiRestResponse;
import com.jiang.blog.exception.BlogExceptionEnum;
import com.jiang.blog.service.ArticleService;
import com.jiang.blog.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
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

}
