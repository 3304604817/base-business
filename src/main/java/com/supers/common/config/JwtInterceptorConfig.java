package com.supers.common.config;

import com.supers.common.util.jwt.JwtAuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author gaoyang
 * JWT拦截器配置类
 */
@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**")  // 所有接口都走拦截器
                .excludePathPatterns(
                        "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.map", "/**/api-docs",
                        "/**/error",
                        "/**/swagger-resources/**",
                        "/**/login");  // 排除不需要走拦截器的接口
    }

    @Bean
    public JwtAuthenticationInterceptor authenticationInterceptor() {
        return new JwtAuthenticationInterceptor();
    }
}
