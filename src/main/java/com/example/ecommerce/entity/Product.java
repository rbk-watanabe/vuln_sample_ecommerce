// src/main/java/com/example/ecommerce/entity/Product.java
package com.example.ecommerce.entity;

import lombok.Data; // Lombokのアノテーションでgetter/setterなどを自動生成

@Data // @Getter, @Setter, @EqualsAndHashCode, @ToString, @RequiredArgsConstructor をまとめて生成
public class Product {
    private Long id;
    private String name;
    private String description;
    private int price; // 価格（円など、整数で扱う）
    private String imageUrl; // 商品画像のURL

    // コンストラクタ
    public Product(Long id, String name, String description, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, String description, int price, String imageUrl) {
        this(null, name, description, price, imageUrl); // IDは自動生成を想定し、nullで初期化
    }
}