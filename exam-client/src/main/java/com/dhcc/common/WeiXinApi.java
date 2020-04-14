package com.dhcc.common;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 调用微信api接口
 * dujianling
 */
@Component
public class WeiXinApi {
    //应用的id
    private static final String CORPID = "xxx";
    //企业微信的secret
    private static final String CORPSECRET = "xxx";
    //根据code获取用户信息
    private static final String USER_BY_CODE = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    //根据name获取用户信息
    private static final String USER_BY_NAME = "https://qyapi.weixin.qq.com/cgi-bin/user/get";
    @Autowired
    private RestTemplateUtil restTemplateUtil;

    //获取用户信息  code只能使用一次
    public JSONObject getUserByCode(String accessToken,String code){
        System.out.println(USER_BY_CODE+"?access_token="+accessToken+"&code="+code);
        String result = restTemplateUtil.get(USER_BY_CODE+"?access_token="+accessToken+"&code="+code);
        return JSONObject.parseObject(result);
    }

    //根据用户名，获取用户信息
    public JSONObject getUserByName(String accessToken,String userid){
        String result = restTemplateUtil.get(USER_BY_NAME+"?access_token="+accessToken+"&userid="+userid);
        return JSONObject.parseObject(result);
    }


    //根据用户名，获取用户信息
    public JSONObject getDepartmentInfo(String accessToken,String userid){
        String result = restTemplateUtil.get(USER_BY_NAME+"?access_token="+accessToken+"&userid="+userid);
        return JSONObject.parseObject(result);
    }


    public static User getLoginUser(){
        SecurityContextHolder.getContext().getAuthentication().getDetails();
        //获取所在部门信息
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal);
        }else if(principal instanceof org.springframework.security.core.userdetails.User){
            org.springframework.security.core.userdetails.User sysUser = (org.springframework.security.core.userdetails.User)principal;
            JSONObject json = JSONObject.parseObject(sysUser.getUsername());
            User user = JSONObject.toJavaObject(json, User.class);
            return user;
        }else{
            return null;
        }
    }


    //获取企业的access_token ，access_token应该保存到内存中，2个小时有效
    //https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=xxx&corpsecret=xxx

    //获取用户信息  code只能使用一次
    //https://qyapi.weixin.qq.com/cgi-bin/service/getuserinfo3rd?access_token=SUITE_ACCESS_TOKEN&code=CODE
    //{
    //    "UserId": "aa",
    //        "DeviceId": "zz",
    //       "errcode": 0,
    //       "errmsg": "ok"
    // }
    //获取成功完成用户登录信息
}
