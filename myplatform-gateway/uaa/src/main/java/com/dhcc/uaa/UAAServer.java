package com.dhcc.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.dhcc.uaa")
public class UAAServer {

    public static void main(String[] args){

        SpringApplication.run(UAAServer.class,args);

    }






}
