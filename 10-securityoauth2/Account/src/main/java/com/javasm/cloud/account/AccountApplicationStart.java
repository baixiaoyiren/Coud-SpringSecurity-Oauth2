package com.javasm.cloud.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Author：MoDebing
 * Version：1.0
 * Date：2022-11-20-02:01
 * Description:
 */
@SpringBootApplication(scanBasePackages = {"com.*"})
@EnableDiscoveryClient
public class AccountApplicationStart {
    public static void main(String[] args) {
        SpringApplication.run(AccountApplicationStart.class,args);
    }
}
