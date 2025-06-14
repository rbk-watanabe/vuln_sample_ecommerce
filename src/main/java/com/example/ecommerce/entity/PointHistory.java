// src/main/java/com/example/ecommerce/entity/PointHistory.java
package com.example.ecommerce.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime; // 日時情報用

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointHistory {
    private Long id;
    private Long userId; // どのユーザーのポイント履歴か
    private int pointsChange; // ポイントの増減 (+は獲得、-は利用)
    private String reason; // ポイント増減の理由 (例: "購入", "ポイント利用", "キャンペーン")
    private LocalDateTime createdAt; // 履歴作成日時

    public PointHistory(Long userId, int pointsChange, String reason) {
        this(null, userId, pointsChange, reason, LocalDateTime.now()); // IDと作成日時は自動設定
    }
}