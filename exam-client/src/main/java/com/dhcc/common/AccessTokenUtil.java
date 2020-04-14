package com.dhcc.common;

import com.alibaba.fastjson.JSONObject;
import com.dhcc.config.RestTemplateConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.Date;

/**
 * 用于获取、保存、刷新access_token信息，采用单例模式
 * dujianling
 */
public class AccessTokenUtil {

    public static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);
    private static AccessTokenUtil accessTokenUtil = new AccessTokenUtil();

    private String accessToken;//企业微信token
    private Date getAtTime;//从微信获取token时间
    private Long expiresIn = 7200L;//token的有效期限
    //获取token接口
    private static final String ACCESS_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=xxx&corpsecret=xxx";

    //构造方法私有化，外部不能创建
    private AccessTokenUtil() {
    }

    //提供一个公有的静态方法，返回实例对象
    public static AccessTokenUtil getInstance() {
        return accessTokenUtil;
    }

    //获取企业微信的access_token
    public String getAccessToken(){
        //如果token为空，或者有效期小于10分钟，重新获取token
        synchronized (AccessTokenUtil.class){
            if(accessToken==null||getTimeDelta(new Date(),getAtTime)>(expiresIn-600)){
                System.out.println("调用了微信的获取token接口");
                RestTemplate restTemplate = getResTemplate();
                String result = restTemplate.getForObject(ACCESS_TOKEN_URL, String.class);
                System.out.println(result);
                JSONObject jsonObject = JSONObject.parseObject(result);
                System.out.println(jsonObject.getString("errcode"));
                System.out.println("0".equals(jsonObject.getString("errcode")));
                if("0".equals(jsonObject.getString("errcode"))){
                    accessToken = jsonObject.getString("access_token");
                    expiresIn = jsonObject.getLong("expires_in");
                    getAtTime = new Date();
                }
                return accessToken;
            }else{
                return accessToken;
            }
        }
    }
    private RestTemplate getResTemplate(){
        return new RestTemplate(clientHttpRequestFactory());
    }

    private HttpComponentsClientHttpRequestFactory clientHttpRequestFactory() {
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            //开始设置连接池
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager
                    = new PoolingHttpClientConnectionManager();
            //最大连接数
            poolingHttpClientConnectionManager.setMaxTotal(100);
            //同路由并发数
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
            httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);

            HttpClient httpClient = httpClientBuilder.build();
            // httpClient连接配置
            HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                    = new HttpComponentsClientHttpRequestFactory(httpClient);
            //连接超时
            clientHttpRequestFactory.setConnectTimeout(30 * 1000);
            //数据读取超时时间
            clientHttpRequestFactory.setReadTimeout(60 * 1000);
            //连接不够用的等待时间
            clientHttpRequestFactory.setConnectionRequestTimeout(30 * 1000);
            return clientHttpRequestFactory;
        } catch (Exception e) {
            logger.error("初始化clientHttpRequestFactory出错", e);
        }
        return null;
    }

    private long getTimeDelta(Date date1,Date date2){
        Calendar nowDate=Calendar.getInstance();
        Calendar oldDate=Calendar.getInstance();
        nowDate.setTime(date1);//设置为当前系统时间
        oldDate.setTime(date2);//设置为想要比较的日期
        Long timeNow=nowDate.getTimeInMillis();
        Long timeOld=oldDate.getTimeInMillis();
        Long time = (timeNow-timeOld)/1000;//相差毫秒数
        return time;
    }

}
