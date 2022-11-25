package com.jiang.blog.config;

import com.jiang.blog.exception.AccessDeniedHandlerImpl;
import com.jiang.blog.exception.AuthenticationEntryPointImpl;
import com.jiang.blog.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandlerImpl;

    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    //  必须定义这个方法 定义自己的加密方法 过滤器会用到这个方法
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/static/**");
    }


    // 配置过滤规则
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /**
         * anyRequest          |   匹配所有请求路径
         * access              |   SpringEl表达式结果为true时可以访问
         * anonymous           |   匿名可以访问
         * denyAll             |   用户不能访问
         * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
         * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
         * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
         * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
         * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
         * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
         * permitAll           |   用户可以任意访问
         * rememberMe          |   允许通过remember-me登录的用户访问s
         * authenticated       |   用户登录后可访问
         */


        //把token校验过滤器添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 失败的时候
                .exceptionHandling()
                // 添加认证失败处理器
                .authenticationEntryPoint(authenticationEntryPointImpl)
                // 添加权限不足处理器
                .accessDeniedHandler(accessDeniedHandlerImpl);
        // 允许跨域
        http.cors()
                .and()
                //关闭csrf跨站
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // 对于登录接口 允许匿名访问
                .antMatchers("/v1/login")
                // 允许匿名访问 ,登录后不能访问 anonymous()     不拦截 permitAll()
                .anonymous()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest()
                .authenticated();
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