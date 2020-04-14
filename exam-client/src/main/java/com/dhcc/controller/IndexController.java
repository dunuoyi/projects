package com.dhcc.controller;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.common.AccessTokenUtil;
import com.dhcc.common.WeiXinApi;
import com.dhcc.entity.Permission;
import com.dhcc.entity.User;
import com.dhcc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private WeiXinApi weiXinApi;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login-success")
    public String success(){//登录成功跳转到主页
        return "index";
    }


    @RequestMapping(value="/index")
    public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {//通过微信跳转过来，完成认证登录功能
        System.out.println("==========index===========");
        //获取用户code
        String mycode = request.getParameter("code");
        if(mycode==null||"".equals(mycode)){
            System.out.println("进行了重定向！！！！");
            System.out.println(request.getContextPath()+"/login");
            return "redirect:/login";
            //response.sendRedirect(request.getContextPath()+"/login");
        }
        System.out.println("mycode=="+mycode);
        //https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
        //code=====TiZVocKNNKNvRDC0mxE_Ln6D_MF2wpYoZI347awjROM
        //传入授权吗，根据授权吗，获得用户信息，
        String accessToken = AccessTokenUtil.getInstance().getAccessToken();
        System.out.println("accessToken=="+accessToken);
        JSONObject userJson = weiXinApi.getUserByCode(accessToken,mycode);
        System.out.println("userJson=="+userJson.toJSONString());
        if(!"0".equals(userJson.getString("errcode"))){
            System.out.println("进行了重定向！！！！");
            return "redirect:/login";
        }
        String userid = userJson.getString("UserId");//"DuJianLing";//
        User user = userService.getUserByUserName(userid);
        /*JSONObject userAllJson = weiXinApi.getUserByName(accessToken,userid);
        System.out.println("userAllJson=="+userAllJson.toJSONString());
        User user = JSONObject.toJavaObject(userAllJson, User.class);
        System.out.println("==============="+user.getName());
        System.out.println("userid==="+userid);*/
        //把用户存放到security环境中
        List<Permission> permissions = user.getPermList();
        String[] authorities = new String[permissions.size()];
        for(int i=0;i<permissions.size();i++){
            authorities[i] = permissions.get(i).getCode();
        }
        //将用户信息和权限填充 到用户身份token对象中(可以把user按照正常登陆是后方如一个json串)
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user,null, AuthorityUtils.createAuthorityList(authorities));
        authenticationToken.setDetails(user);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);//将authenticationToken填充到安全上下文
        System.out.println(user.getUsername()+"===="+user.getFullname());
        return "redirect:/login-success";
    }


    @RequestMapping(value = "/r/getUserAll")
    public String getUserAll(HttpServletRequest request){
        User user = WeiXinApi.getLoginUser();
        System.out.println("username=="+user.getFullname());
        String accessToken = AccessTokenUtil.getInstance().getAccessToken();
        JSONObject userAllJson = weiXinApi.getUserByName(accessToken,user.getUsername());//((User) principal).getUsername()
        request.setAttribute("userAllJson",userAllJson.toJSONString());
        return "userall";//包含大写可能会处理
    }



    public static void main(String[] args) {
        String str = "";
    }



    /**
     * 测试资源1
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/r/r1",produces = {"text/plain;charset=UTF-8"})
    public String r1(){
        return " 访问资源1";
    }

    /**
     * 测试资源2
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=UTF-8"})
    public String r2(){
        return " 访问资源2";
    }


    @ResponseBody
    @GetMapping(value = "/r3")
    public String r3(){
        return " 访问资源3";
    }


}
