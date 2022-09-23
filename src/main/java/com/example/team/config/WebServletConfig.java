package com.example.team.config;

import com.example.team.interceptor.AuthInteceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan({"com.example.team"})
public class WebServletConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInteceptor())
                .addPathPatterns("/admin");
    }

    /*@Bean
    public CommonsMultipartResolver multipartResolver() throws Exception {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxInMemorySize(2097152);
        return resolver;
    }*/
}
