package com.lifevision.HelloSewa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {      
    
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
        .addResourceLocations(
        		"classpath:/static",
        		"classpath:/images/product",
        		"classpath:/images/thumbnail",
        		"classpath:/images/profile-picture",
        		"classpath:/images/icon");
    }
}
