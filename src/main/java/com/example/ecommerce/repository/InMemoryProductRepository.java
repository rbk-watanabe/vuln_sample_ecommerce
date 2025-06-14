// src/main/java/com/example/ecommerce/repository/InMemoryProductRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Product;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<Long, Product> products = new HashMap<>(); // 商品情報をメモリに保持
    private final AtomicLong nextId = new AtomicLong(1); // IDの自動生成用

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            // 新規商品の場合、IDを生成して追加
            product.setId(nextId.getAndIncrement());
        }
        products.put(product.getId(), product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        // 現在の商品のリストを返却（変更されないように不変リストとして返す）
        return Collections.unmodifiableList(new ArrayList<>(products.values()));
    }

    @Override
    public long count() {
        return products.size();
    }
}