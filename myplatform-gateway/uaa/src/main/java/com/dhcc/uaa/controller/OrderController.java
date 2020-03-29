package com.dhcc.uaa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class OrderController {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping(value = "/r3")
    public String r3(){
        ServiceInstance serviceInstance = loadBalancerClient.choose("uaa-service");
        String url = String.format("http://%s:%s/order/",serviceInstance.getHost()
                ,serviceInstance.getPort(),appName);
        System.out.println(url);
        return restTemplate.getForObject(url,String.class);
    }

}