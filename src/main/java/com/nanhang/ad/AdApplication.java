package com.nanhang.ad;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
* 
* @author yangjianghe
* @date 2018/11/2
* @version V1.0
*/
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
public class AdApplication {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("user-Task-pool-%d").build();
        ArrayBlockingQueue queue = new ArrayBlockingQueue(20);
        return new ThreadPoolExecutor(5,10,1, TimeUnit.MINUTES,queue,namedThreadFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(AdApplication.class, args);
    }
}
