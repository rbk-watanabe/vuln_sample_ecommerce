// src/main/java/com/example/ecommerce/entity/Cart.java
package com.example.ecommerce.entity;

import lombok.Data;
import java.io.Serializable; // セッションに保存するために必要
import java.util.ArrayList;
import java.util.List;

@Data
public class Cart implements Serializable { // セッションに保存するためSerializableを実装
    private List<CartItem> items = new ArrayList<>();

    // カートの合計金額を計算するヘルパーメソッド
    public int getTotalPrice() {
        return items.stream()
                    .mapToInt(item -> item.getPrice() * item.getQuantity())
                    .sum();
    }
}