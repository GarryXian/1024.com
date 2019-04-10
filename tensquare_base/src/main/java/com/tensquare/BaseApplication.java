package com.tensquare;

import com.tensquare.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * SpringBoot引导类
 */
@SpringBootApplication
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
    }


    @Bean
    public IdWorker getIdWorker(){
        //设置雪花id的机器码为1, 数据中心编码为1
        return new IdWorker(1L,1L);
    }
}
