// src/main/java/com/example/ecommerce/repository/PointHistoryRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.PointHistory;
import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository {
    PointHistory save(PointHistory pointHistory);
    List<PointHistory> findByUserId(Long userId);
    long count();
}