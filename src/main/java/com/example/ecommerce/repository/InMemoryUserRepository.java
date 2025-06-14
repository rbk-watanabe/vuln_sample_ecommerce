// src/main/java/com/example/ecommerce/repository/InMemoryUserRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.User;
import org.springframework.stereotype.Repository; // @Repository アノテーションをインポート
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong; // ID生成用

@Repository // このクラスがリポジトリ層のコンポーネントであることをSpringに伝える
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new HashMap<>(); // ユーザー情報をメモリに保持
    private final AtomicLong nextId = new AtomicLong(1); // IDの自動生成用

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            // 新規ユーザーの場合、IDを生成して追加
            user.setId(nextId.getAndIncrement());
        }
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        // ユーザー名を基にユーザーを検索
        return users.values().stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst();
    }

    @Override
    public Optional<User> findById(Long id) {
        // IDを基にユーザーを検索
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public long count() {
        return users.size();
    }
}