package com.jiang.blog.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.jwt.StpLogicJwtForSimple;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpLogic;
import cn.dev33.satoken.util.SaResult;
import com.jiang.blog.common.Constant;
import com.jiang.blog.model.dao.MenuMapper;
import com.jiang.blog.model.dao.UserMapper;
import com.jiang.blog.model.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class SaTokenJWt implements StpInterface {

    @Autowired
    UserMapper userMapper;
    @Autowired
    MenuMapper menuMapper;


    @Bean
    public StpLogic getStpLogicJwt() {
        return new StpLogicJwtForSimple();
    }

    // Sa-Token 参数配置，参考文档：https://sa-token.cc
    // 此配置会覆盖 application.yml 中的配置
    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("token");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
        config.setActivityTimeout(60 * 60 * 12);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setTokenStyle("uuid");               // token风格
        config.setIsLog(false);                     // 是否输出操作日志
        config.setJwtSecretKey(Constant.SECRET); // 密匙
        return config;
    }


    // 跨域
    @Bean
    public SaServletFilter getSaServletFilter() {
        return new SaServletFilter()
                // 拦截与排除 path
                .addInclude("/**").addExclude("/favicon.ico")

                // 全局认证函数
                .setAuth(obj -> {
                    // ...
                })
                // 异常处理函数

                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(e -> {
                    System.out.println("---------- 进入Sa-Token异常处理 -----------");
                    return SaResult.error(e.getMessage());
                })

                // 前置函数：在每次认证函数之前执行
                .setBeforeAuth(obj -> {
                    // ---------- 设置跨域响应头 ----------
                    SaHolder.getResponse()
                            // 允许指定域访问跨域资源
                            .setHeader("Access-Control-Allow-Origin", "*")
                            // 允许所有请求方式
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
                            // 有效时间
                            .setHeader("Access-Control-Max-Age", "3600")
                            // 允许的header参数
                            .setHeader("Access-Control-Allow-Headers", "*");

                    // 如果是预检请求，则立即返回到前端
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
                            .back();
                });
    }

    /**
     * 返回一个账号所拥有的权限码集合
     */

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        String id = loginId.toString();
        List<String> permissionList = new LinkedList<>();
        if (id.equals("1")) {
            permissionList.add("*");
        } else {
            // 如果不是管理员
            List<String> Permission = menuMapper.selectPermsByUserId(Long.parseLong(id));
            permissionList.addAll(Permission);
        }
        return permissionList;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String id = loginId.toString();
        List<String> roleName = new LinkedList<>();
        if (id.equals("1")) {
            roleName.add("admin");
        } else {
            List<Role> roles = userMapper.queryRolesByUserId(Long.parseLong(id));
            roleName.addAll(roles.stream().map(item -> item.getRoleKey()).collect(Collectors.toList()));
        }
        return roleName;
    }


}
