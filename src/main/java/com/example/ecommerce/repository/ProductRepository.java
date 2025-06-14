// src/main/java/com/example/ecommerce/repository/ProductRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    // 商品を保存または更新する
    Product save(Product product);

    // IDを指定して商品を検索する
    Optional<Product> findById(Long id);

    // 全ての商品を取得する
    List<Product> findAll();

    // 商品数をカウントする
    long count();
}