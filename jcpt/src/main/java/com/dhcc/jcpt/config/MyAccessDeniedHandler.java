package com.dhcc.jcpt.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println("999999999999999999999999999999999");
        //RespBean respBean = new RespBean(403, "权限不足", e.getMessage());
        //out.write(new ObjectMapper().writeValueAsString(respBean));
        out.write("oooooooooooooooooo");
        out.flush();
        out.close();
    }
}

