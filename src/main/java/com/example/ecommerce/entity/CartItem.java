// src/main/java/com/example/ecommerce/entity/CartItem.java
package com.example.ecommerce.entity;

import lombok.AllArgsConstructor; // 全てのフィールドを持つコンストラクタを生成
import lombok.Data;
import lombok.NoArgsConstructor; // 引数なしのコンストラクタを生成
import java.io.Serializable; // セッションに保存するために必要

@Data
@NoArgsConstructor // デフォルトコンストラクタをLombokで生成
@AllArgsConstructor // 全てのフィールドを引数に持つコンストラクタをLombokで生成
public class CartItem implements Serializable { // セッションに保存するためSerializableを実装
    private Long productId;
    private String productName;
    private int price; // 商品単価
    private int quantity; // 数量
    private String imageUrl; // 商品画像URL
}