// src/main/java/com/example/ecommerce/controller/UserController.java
package com.example.ecommerce.controller;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public String showUserProfile(@AuthenticationPrincipal UserDetails currentUser, Model model) {
        // 現在ログインしているユーザーの情報を取得
        // AuthenticationPrincipalからユーザー名を取得し、UserServiceでUserエンティティを検索
        User user = userService.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found after authentication."));

        model.addAttribute("user", user); // テンプレートにUserオブジェクトを渡す
        return "user/profile"; // src/main/resources/templates/user/profile.html を表示
    }
}