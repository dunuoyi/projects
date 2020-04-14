package com.dhcc.init;

import com.dhcc.config.WebSecurityConfig;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * @author Administrator
 * ----------------暂时好像没发现啥作用      启用拦截配置规则（WebSecurityConfig中配置的）
 **/
public class SpringSecurityApplicationInitializer
        extends AbstractSecurityWebApplicationInitializer {
    public SpringSecurityApplicationInitializer() {
        //super(WebSecurityConfig.class);//如果没有用springmvc要添加这个
    }
}
