// src/main/java/com/example/ecommerce/entity/User.java
package com.example.ecommerce.entity;

import lombok.Data;
// import lombok.RequiredArgsConstructor; // 使用していないので削除しても良い

@Data
public class User {
    private Long id;
    private String username;
    private String password; // ハッシュ化されたパスワードを格納
    private String role; // 例: "ROLE_USER", "ROLE_ADMIN"
    private int points; // ポイント残高を追加

    // コンストラクタを更新
    public User(Long id, String username, String password, String role, int points) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.points = points; // ポイントも初期化
    }

    // IDなしのコンストラクタも更新（新規登録用）
    public User(String username, String password, String role, int points) {
        this(null, username, password, role, points);
    }
}