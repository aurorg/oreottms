package com.panda.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
实现了Spring框架的WebMvcConfigurer接口，
用于配置跨域资源共享（CORS）

重写了WebMvcConfigurer接口中的addCorsMappings方法，
该方法用于添加CORS配置


GET：用于从服务器获取资源，一般用于读取操作。
HEAD：与GET类似，但是只返回请求头，不返回实际内容，一般用于获取资源的元信息。
POST：用于向服务器提交数据，一般用于写操作。
PUT：用于向服务器上传资源，一般用于更新操作。
DELETE：用于从服务器删除资源，一般用于删除操作。
OPTIONS：用于获取服务器支持的请求方法、头信息、跨域配置等信息，一般用于预检请求。
 */
@Configuration
public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
