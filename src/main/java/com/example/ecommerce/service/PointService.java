// src/main/java/com/example/ecommerce/service/PointService.java
package com.example.ecommerce.service;

import com.example.ecommerce.entity.PointHistory;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // トランザクション管理用

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserService userService; // ユーザーのポイント残高を更新するため
    private final PointHistoryRepository pointHistoryRepository;

    /**
     * ユーザーにポイントを付与します。
     * @param userId ポイントを付与するユーザーのID
     * @param points 付与するポイント数
     * @param reason 付与理由
     * @return 更新後のユーザーオブジェクト
     */
    @Transactional
    public User addPoints(Long userId, int points, String reason) {
        if (points <= 0) {
            throw new IllegalArgumentException("付与するポイント数は正の数である必要があります。");
        }
        PointHistory history = new PointHistory(userId, points, reason);
        pointHistoryRepository.save(history);
        return userService.updatePoints(userId, points, reason);
    }

    /**
     * ユーザーがポイントを使用します。
     * @param userId ポイントを使用するユーザーのID
     * @param points 使用するポイント数
     * @param reason 使用理由
     * @return 更新後のユーザーオブジェクト
     * @throws IllegalArgumentException ポイントが不足している場合
     */
    @Transactional
    public User usePoints(Long userId, int points, String reason) {
        if (points <= 0) {
            throw new IllegalArgumentException("使用するポイント数は正の数である必要があります。");
        }
        // UserServiceでポイント残高のチェックを行う
        PointHistory history = new PointHistory(userId, -points, reason); // 利用はマイナスで記録
        pointHistoryRepository.save(history);
        return userService.updatePoints(userId, -points, reason);
    }

    /**
     * 特定のユーザーのポイント履歴を取得します。
     * @param userId ユーザーID
     * @return ポイント履歴のリスト
     */
    public List<PointHistory> getPointHistory(Long userId) {
        return pointHistoryRepository.findByUserId(userId);
    }
}