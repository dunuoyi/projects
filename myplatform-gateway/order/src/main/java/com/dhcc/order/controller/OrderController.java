package com.dhcc.order.controller;

import com.dhcc.order.model.UserDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class OrderController {

    @GetMapping(value = "/r1")
    @PreAuthorize("hasAuthority('p1')")//拥有p1权限方可访问此url
    public String r1(){
        //获取用户身份信息
        UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userDTO.getId()+"==="+userDTO.getFullname()+"==="+userDTO.getMobile());
        return userDTO.getUsername()+"访问资源1";
    }


    @GetMapping(value = "/r2")
    @PreAuthorize("hasAuthority('p2')")//拥有p1权限方可访问此url
    public String r2(){
        //获取用户身份信息
        //UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "访问资源2";
    }


    @GetMapping(value = "/r3")
    public String r3(){
        //获取用户身份信息
        //UserDTO userDTO = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "访问资源3";
    }

}