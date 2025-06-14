package com.example.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // 追加
import org.springframework.security.crypto.password.PasswordEncoder; // 追加
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/home", "/register", "/css/**", "/js/**").permitAll() // /register も認証不要に
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/products", true) // ログイン成功後に / にリダイレクト
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // ログアウトのURLを設定
                .logoutSuccessUrl("/login?logout") // ログアウト成功後に /login にリダイレクト
                .invalidateHttpSession(true) // セッションを無効化
                .deleteCookies("JSESSIONID") // クッキーを削除
                .permitAll()
            )
            .csrf(csrf -> csrf.disable()) // CSRF保護を一時的に無効化
            .headers(headers -> headers
                .frameOptions(FrameOptionsConfig::disable)
            );
        return http.build();
    }

    // パスワードエンコーダーをBeanとして登録
    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptアルゴリズムを使用するパスワードエンコーダー
        return new BCryptPasswordEncoder();
    }
}