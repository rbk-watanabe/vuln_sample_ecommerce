// src/main/java/com/example/ecommerce/service/UserService.java (抜粋)
package com.example.ecommerce.service;

import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 新しいユーザーを登録します。
     * パスワードはハッシュ化されて保存されます。初期ポイントは0です。
     * @param username ユーザー名
     * @param rawPassword 平文のパスワード
     * @param role ユーザーのロール（例: "ROLE_USER"）
     * @return 登録されたユーザー
     * @throws IllegalArgumentException ユーザー名が既に存在する場合
     */
    @Transactional
    public User registerNewUser(String username, String rawPassword, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("ユーザー名が既に存在します: " + username);
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        // Userコンストラクタにpoints引数を追加 (0で初期化)
        User newUser = new User(username, encodedPassword, role, 0); // ここを修正
        return userRepository.save(newUser);
    }

    // ... (findByUsername, loadUserByUsername メソッドは省略)

    @Transactional
    public void createInitialUsersIfNotExist() {
        if (userRepository.count() == 0) {
            registerNewUser("admin", "password", "ROLE_ADMIN"); // 管理者
            registerNewUser("user", "password", "ROLE_USER");   // 一般ユーザー
            // ↓ テスト用に初期ポイントを持つユーザーを追加する場合 (任意)
            // registerNewUser("tester", "password", "ROLE_USER", 1000);
            System.out.println("初期ユーザー（admin/password, user/password）を作成しました。");
        }
    }

    /**
     * ユーザーのポイントを更新します。
     * @param userId 対象ユーザーのID
     * @param pointsChange 増減するポイント数
     * @param reason 変更理由
     * @return 更新後のユーザーオブジェクト
     * @throws IllegalArgumentException ユーザーが見つからない場合
     */
    @Transactional
    public User updatePoints(Long userId, int pointsChange, String reason) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("ユーザーが見つかりません: " + userId));

        int newPoints = user.getPoints() + pointsChange;
        if (newPoints < 0) {
            throw new IllegalArgumentException("ポイントが不足しています。"); // マイナスポイントを防ぐ
        }
        user.setPoints(newPoints);
        userRepository.save(user); // InMemoryUserRepositoryは上書き保存
        return user;
    }

    /*
     * Spring Securityがユーザーを認証するために呼び出すメソッド。
     * UserDetailsServiceインターフェースの実装。
     * @param username 認証しようとしているユーザー名
     * @return ユーザーの詳細情報（Spring SecurityのUserオブジェクト）
     * @throws UsernameNotFoundException 指定されたユーザー名が見つからない場合
     */
    @Override // このアノテーションがあるか確認
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Spring Securityが利用するUserDetailsオブジェクトに変換して返す
        // パスワードは既にハッシュ化されているものがDB（InMemoryUserRepository）から取得される
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", "")) // "ROLE_USER" -> "USER"
                .build();
    }

    /**
     * ユーザーIDでユーザーを取得します。
     * @param userId ユーザーID
     * @return ユーザー (存在しない場合はOptional.empty())
     */
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * ユーザー名でユーザーを取得します。
     * @param username ユーザー名
     * @return ユーザー (存在しない場合はOptional.empty())
     */
    // ↓↓↓ このメソッドが不足している可能性があります。追加してください ↓↓↓
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    // ↑↑↑ ここまで ↑↑↑
}