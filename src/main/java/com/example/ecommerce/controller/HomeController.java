// src/main/java/com/example/ecommerce/controller/HomeController.java
package com.example.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * ルートパス ("/") へのGETリクエストを処理し、"home"ビューを返します。
     * このビューは src/main/resources/templates/home.html に対応します。
     * @return ビューの名前
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}