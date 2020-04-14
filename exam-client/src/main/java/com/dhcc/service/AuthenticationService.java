package com.dhcc.service;

import com.dhcc.entity.AuthenticationRequest;
import com.dhcc.entity.User;

/**
 * Created by Administrator.
 */
public interface AuthenticationService {
    /**
     * 用户认证
     * @param authenticationRequest 用户认证请求，账号和密码
     * @return 认证成功的用户信息
     */
    User authentication(AuthenticationRequest authenticationRequest);


}
