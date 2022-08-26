package com.jiang.blog.filter;

import com.jiang.blog.model.pojo.User;
import com.jiang.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import java.io.IOException;

//@WebFilter(filterName = "UserFilter",urlPatterns ="/*",initParams = {
//        @WebInitParam(name = "encoding",value = "UTF-8")
//})
public class UserFilter implements Filter {
    public static User currentUser;
    @Autowired
    UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        String token = httpRequest.getHeader("Authorization");
//        Map user = JWTUtils.verifyToken(token);
//        if (user == null) {
//            PrintWriter out = new HttpServletResponseWrapper((HttpServletResponse) servletResponse).getWriter();
//            out.write("{\"status\":10007,\"msg\":\"user not login\",\"data\":null}");
//            out.flush();
//            out.close();
//            return;
//        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
