package com.hit.lpm.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: lmp-web
 * @description: 设置上传文件大小上限
 * @author: zhaoyang
 * @create: 2019-11-6 10:51
 **/
@Configuration
public class TomcatConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件大小
        factory.setMaxFileSize("50MB");
        // 总上传数据大小
        factory.setMaxRequestSize("50MB");
        return factory.createMultipartConfig();
    }
}
