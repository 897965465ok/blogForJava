package com.jiang.blog.interceptor;

import com.jiang.blog.common.UserContext;
import com.jiang.blog.utils.JWTUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过拦截器");
        String token = request.getHeader("Authorization");
        Map user = JWTUtils.verifyToken(token);
        if (user == null) {
            //未登录，返回登陆页
//            request.setAttribute("msg", "您没有权限进行此操作，请先登陆！");
//            request.getRequestDispatcher("/v1/user/login").forward(request, response);
            PrintWriter out = response.getWriter();
            out.write("{\"status\":10010,\"message\":\"user not login\"}");
            out.flush();
            out.close();
            return false;
        } else {
            UserContext.setUserId(Long.valueOf(String.valueOf(user.get("id"))));
            //放行
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
