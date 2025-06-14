// src/main/java/com/example/ecommerce/EcommerceApplication.java (抜粋)
package com.example.ecommerce;

import com.example.ecommerce.service.UserService; // 追加
import org.springframework.boot.CommandLineRunner; // 追加
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean; // 追加

@SpringBootApplication
public class EcommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

    // アプリケーション起動時に初期ユーザーを作成するためのBean
    @Bean
    public CommandLineRunner initUsers(UserService userService) {
        return args -> {
            userService.createInitialUsersIfNotExist();
        };
    }
}