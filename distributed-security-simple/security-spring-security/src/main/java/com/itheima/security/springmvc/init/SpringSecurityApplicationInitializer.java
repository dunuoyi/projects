package com.itheima.security.springmvc.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Administrator
 * @version 1.0
 **/
public class SpringSecurityApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityApplicationInitializer() {
        //super(WebSecurityConfig.class);//如果没有用springmvc要添加这个
    }
}
