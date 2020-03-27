package com.dhcc.gateway.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.LogRecord;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("=========myfilter======");
        //获取所有cookie
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        Cookie[] cookies = request.getCookies();
        //遍历cookie
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                //获取cookie的名字
                String cookieName = cookie.getName();
                System.out.println(cookieName);  //新浪的cookie文件会传过来给你吗？
					/*if("userName".equalsIgnoreCase(cookieName)){
						//获取cookie 的数据
						System.out.println(cookieName+" : "+ cookie.getValue() );
					}*/
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
