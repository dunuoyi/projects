package com.itheima.security.springmvc.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration  //相当于application.xml
@ComponentScan(basePackages = "com.itheima.security.springmvc",
    excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)})
public class ApplicationConfig {

    //配置bean

}
