// src/main/java/com/example/ecommerce/controller/AuthController.java
package com.example.ecommerce.controller;

import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor; // Lombokのアノテーション
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor // UserServiceを自動でインジェクションするためのLombokアノテーション
public class AuthController {

    private final UserService userService;

    // ログインフォーム表示
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login"; // src/main/resources/templates/auth/login.html を表示
    }

    // ユーザー登録フォーム表示
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("error", null); // エラーメッセージ用
        return "auth/register"; // src/main/resources/templates/auth/register.html を表示
    }

    // ユーザー登録処理
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               Model model) {
        try {
            // 仮で全てのユーザーを"ROLE_USER"として登録
            userService.registerNewUser(username, password, "ROLE_USER");
            // 登録成功したらログインページへリダイレクト
            return "redirect:/login?registered";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", username); // 入力値を保持
            return "auth/register"; // 登録フォームに戻り、エラーメッセージを表示
        }
    }
}