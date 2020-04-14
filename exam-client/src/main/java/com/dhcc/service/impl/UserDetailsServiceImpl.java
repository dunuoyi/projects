package com.dhcc.service.impl;

import com.alibaba.fastjson.JSON;
import com.dhcc.entity.Permission;
import com.dhcc.entity.User;
import com.dhcc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 继承UserDetailsService实现loadUserByUsername方法完成登录校验
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //将来连接数据库根据账号查询用户信息
        User user = userMapper.getUserByUserName(username);
        if(user == null){
            //如果用户查不到，返回null，由provider来抛出异常
            return null;
        }
        //根据用户的id查询用户的权限
        List<Permission> permissions = user.getPermList();
        //将permissions转成数组
        String[] permissionArray = new String[permissions.size()];
        for(int i=0;i<permissions.size();i++){
            permissionArray[i] = permissions.get(i).getCode();
        }
        //将userDto转成json
        String principal = JSON.toJSONString(user);
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(principal)
                                                    .password("123").authorities(permissionArray).build();



        return userDetails;
    }



}
