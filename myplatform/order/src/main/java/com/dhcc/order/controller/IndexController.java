package com.dhcc.order.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.dhcc.order.common.RestTemplateUtil;
import com.dhcc.order.entity.User;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class IndexController {


    Logger logger = LoggerFactory.getLogger(IndexController.class);

    // 网关请求方式类
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping(value="/login/ui")
    public String loginUi(){
        System.out.println("=====================");
        return "login";
    }

    @PostMapping(value="/login")
    public String login(User user, @RequestHeader HttpHeaders headers, HttpServletResponse response) {
        System.out.println("====================="+user.getUsername());
        System.out.println("====================="+user.getPassword());
        //根据用户名密码去访问授权程序申请token
        String url = "http://localhost:53010/uaa/oauth/token";
        // 使用oauth2密码模式登录.
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("username", user.getUsername());
        postParameters.add("password", user.getPassword());
        postParameters.add("client_id", "c1");
        postParameters.add("client_secret", "secret");
        postParameters.add("grant_type", "password");
        // 添加参数区分,第三方登录
        //postParameters.add("login_type", type);
//        HttpHeaders headers = new HttpHeaders();
        // 使用客户端的请求头,发起请求
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 强制移除 原来的请求头,防止token失效
        headers.remove(HttpHeaders.AUTHORIZATION);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity(postParameters, headers);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode() != 401){
                    super.handleError(response);
                }
            }
        });
        JSONObject result = restTemplate.postForObject(url, request, JSONObject.class);
        //sessionStorage.setItem("access_token", jsonResult.access_token);
        //sessionStorage.setItem("refresh_token", jsonResult.refresh_token);
        String access_token = result.getString("access_token");
        Cookie cookie = new Cookie("token",access_token);
        response.addCookie(cookie);
        //String result = this.restTemplateUtil.post(url, jsonObject.toJSONString());
        //申请token后，保存到用户的cokie，每次用户访问cookie中的token信息
        System.out.println("====================="+result);
        if("".equals("")) {
            return "index";
        }else{
            return "index";
        }
    }


    @RequestMapping(value="/success")
    public String success(){
        System.out.println("=====================");
        return "success";
    }

}
