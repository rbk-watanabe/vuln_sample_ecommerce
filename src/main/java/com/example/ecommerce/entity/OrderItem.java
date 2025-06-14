// src/main/java/com/example/ecommerce/entity/OrderItem.java
package com.example.ecommerce.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor // デフォルトコンストラクタを生成
@AllArgsConstructor // 全てのフィールドを引数に持つコンストラクタを生成
public class OrderItem {
    private Long id;
    private Long orderId; // 関連する注文のID
    private Long productId; // 商品ID
    private String productName;
    private int price; // 購入時の単価
    private int quantity; // 数量

    // 簡易コンストラクタ
    public OrderItem(Long orderId, Long productId, String productName, int price, int quantity) {
        this(null, orderId, productId, productName, price, quantity);
    }
}