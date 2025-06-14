// src/main/java/com/example/ecommerce/repository/UserRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import java.util.Optional; // Optionalをインポート

public interface UserRepository {
    // ユーザーを保存または更新する
    User save(User user);

    // ユーザー名を指定してユーザーを検索する
    Optional<User> findByUsername(String username);

    // IDを指定してユーザーを検索する
    Optional<User> findById(Long id);

    // ユーザー数をカウントする (初期データ挿入の判断などに使用)
    long count();
}