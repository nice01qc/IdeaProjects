package com.wanda.ffanad.crm;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用于测试知晓前端调用的接口, 方便直接找到代码
 * Created by shushangjin on 2016-7-28.
 */
public class ApiPrintInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HandlerMethod method = (HandlerMethod) handler;
        String className = method.getBean().getClass().getName();
        System.err.println("访问的API:" + className + " -> " + method.getMethod().getName());

        return true;
    }
}
