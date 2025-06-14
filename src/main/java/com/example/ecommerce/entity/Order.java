// src/main/java/com/example/ecommerce/entity/Order.java
package com.example.ecommerce.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // デフォルトコンストラクタを生成
@AllArgsConstructor // 全てのフィールドを引数に持つコンストラクタを生成
public class Order {
    private Long id;
    private Long userId; // 注文したユーザーのID
    private int totalAmount; // 注文合計金額（ポイント利用後）
    private int usedPoints; // 利用したポイント数
    private LocalDateTime orderDate; // 注文日時
    private List<OrderItem> items = new ArrayList<>(); // 注文アイテムのリスト

    // 簡易コンストラクタ
    public Order(Long userId, int totalAmount, int usedPoints, List<OrderItem> items) {
        this(null, userId, totalAmount, usedPoints, LocalDateTime.now(), items);
    }
}