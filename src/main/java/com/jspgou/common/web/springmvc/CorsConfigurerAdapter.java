package com.jspgou.common.web.springmvc;

import com.jspgou.cms.web.AdminApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class CorsConfigurerAdapter extends WebMvcConfigurerAdapter {

    @Autowired
    private AdminApiInterceptor adminApiInterceptor;

    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRe = registry.addMapping("/**");
        corsRe.allowedOrigins(new String[]{"*"}).allowedHeaders(new String[]{"*"})
                .allowedMethods(new String[]{
                        "GET", "POST", "DELETE", "PUT", "OPTIONS"});
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.adminApiInterceptor);
        super.addInterceptors(registry);
    }
}

