package com.study.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**").excludePathPatterns("/loginHtml","/upload","/login","/reg","/register", "/**/*.svg",
                "/**/asserts/**", "/**/*.css", "/**/*.js", "/**/**/*.js","/upload/*.*","/images/*.*", "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/**/*.svg");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //获取文件的真实路径 work_project代表项目工程名 需要更改
        String path = System.getProperty("user.dir") + "/src/main/resources/static/upload/";
        //https://blog.csdn.net/qq_43925089/article/details/104928616
        registry.addResourceHandler("/**","/upload/**").addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/").addResourceLocations("file:" + path);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
