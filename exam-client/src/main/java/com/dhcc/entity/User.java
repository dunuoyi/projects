package com.dhcc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author Administrator
 * @version 1.0
 **/
@Data
@AllArgsConstructor
public class User {
    public static final String SESSION_USER_KEY = "_user";
    //用户身份信息
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;


    private List<Permission> permList;

    /**
     * 用户权限
     */
    private Set<String> authorities;

    //无参构造函数是需要的
    public User(){

    }

    public User(String id,String username,String password,String fullname,String mobile,Set<String> authorities){
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.mobile = mobile;
        this.authorities = authorities;
    }



}
