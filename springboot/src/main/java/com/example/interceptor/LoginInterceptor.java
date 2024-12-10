package com.example.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object user = request.getSession().getAttribute("user");
        System.out.println("Session user: " + user); // 打印 Account 的值
        System.out.println("Request URI: " + request.getRequestURI()); // 打印请求路径
        if (user == null) {
            System.out.println("拦截");
            request.setAttribute("msg", "无法访问课程管理系统，请先登录");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.sendRedirect("/login");
            response.getWriter().flush(); // 强制刷新响应流
            return false;
        }
        System.out.println("放行");
        return true;
    }
}
