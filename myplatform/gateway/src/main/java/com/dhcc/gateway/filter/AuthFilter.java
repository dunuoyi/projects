package com.dhcc.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.dhcc.gateway.common.EncryptUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现网关过滤器，从上下文解析权限信息，认证通过后才会执行
 * 加入http的header中,转发给微服务
 **/
public class AuthFilter extends ZuulFilter {

    @Override
    public boolean shouldFilter() {
        return true;
    }
    //run执行位置，pre代表在方法前执行
    @Override
    public String filterType() {
        return "pre";
    }
    //决定过滤器加载顺序，值越小，最先加载
    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public Object run() throws ZuulException {

        System.out.println("处理权限信息。。。。");


        RequestContext ctx = RequestContext.getCurrentContext();

        //从安全上下文中拿 到用户身份对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)){
            return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        //取出用户身份信息
        String principal = userAuthentication.getName();

        //取出用户权限
        List<String> authorities = new ArrayList<>();
        //从userAuthentication取出权限，放在authorities
        userAuthentication.getAuthorities().stream().forEach(c->authorities.add(((GrantedAuthority) c).getAuthority()));

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if(userAuthentication!=null){
            jsonToken.put("principal",principal);
            jsonToken.put("authorities",authorities);
        }
        System.out.println("token"+JSON.toJSONString(jsonToken));
        //把身份信息和权限信息放在json中，加入http的header中,转发给微服务
        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));

        return null;
    }
}
