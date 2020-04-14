package com.dhcc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("***.*.mapper") //对应你的mapper存放的地址
public class ExamClientApplication {


    public static void main(String[] args) {
        SpringApplication.run(ExamClientApplication.class, args);
    }


}
