package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@MapperScan("com.fh.shop.api.goods.mapper")
public class ShopGoods {
    public static void main(String[] args) {
        SpringApplication.run(ShopGoods.class,args);
    }
}
