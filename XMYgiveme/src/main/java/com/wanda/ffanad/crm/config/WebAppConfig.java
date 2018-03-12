package com.wanda.ffanad.crm.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.wanda.ffanad.crm.interceptors.LoginInterceptor;

/**
 * SpringWebMVC设置.
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    /**
     * 添加拦截器.
     *
     * @param registry 自动注入的注册机
     */
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        //开发阶段暂时禁用该拦截
        //        registry.addInterceptor(new JwtInterceptor())
        //                .addPathPatterns("/**")
        //                .excludePathPatterns("/assets/**", "/backcrmlogin.html", "/account/login");
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/", "/error",
                "/account/login");
        super.addInterceptors(registry);
    }
}
