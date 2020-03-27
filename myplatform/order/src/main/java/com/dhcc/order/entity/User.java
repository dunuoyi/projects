package com.dhcc.order.entity;


import lombok.Data;

/**
 * 用户信息
 */
@Data
public class User {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String fullname;




}
