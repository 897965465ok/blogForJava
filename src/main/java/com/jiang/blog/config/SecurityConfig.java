package com.jiang.blog.config;

import com.jiang.blog.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity // 开启securityconfig配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

  /*  @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;*/

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //  必须定义这个方法 定义自己的加密方法 过滤器会用到这个方法
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // 配置过滤规则
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //关闭csrf
        http.csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/v1/login")
                // 允许匿名访问 ,登录后不能访问 anonymous()  不拦截 permitAll()
                .anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();
        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // 配置错误操作处理
       /* http.exceptionHandling()
                // 添加认证失败处理器
                .authenticationEntryPoint(authenticationEntryPointImpl)
                // 添加权限不足处理器
                .accessDeniedHandler(accessDeniedHandlerImpl);*/

        // 允许跨域
        http.cors();
        // antMatchers("/user/login").hasAuthority("*") 配置方式添加权限
        return http.build();

    }


    // 生成密码
/*
    private String password() {
        return PasswordEncoder.encode("897965465");
    }
*/


/*
     解析密码
   private boolean parserPassword() {
        PasswordEncoder.matches("原密码", "加密的密码");
    }*/

}