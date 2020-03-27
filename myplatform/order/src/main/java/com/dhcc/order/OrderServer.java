package com.dhcc.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("***.*.mapper") //对应你的mapper存放的地址
public class OrderServer {

    public static void main(String[] args){
        SpringApplication.run(OrderServer.class,args);
    }


}
