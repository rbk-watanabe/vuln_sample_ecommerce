// src/main/java/com/example/ecommerce/repository/InMemoryPointHistoryRepository.java
package com.example.ecommerce.repository;

import com.example.ecommerce.entity.PointHistory;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryPointHistoryRepository implements PointHistoryRepository {

    private final Map<Long, PointHistory> histories = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public PointHistory save(PointHistory pointHistory) {
        if (pointHistory.getId() == null) {
            pointHistory.setId(nextId.getAndIncrement());
        }
        histories.put(pointHistory.getId(), pointHistory);
        return pointHistory;
    }

    @Override
    public List<PointHistory> findByUserId(Long userId) {
        return histories.values().stream()
                .filter(h -> h.getUserId().equals(userId))
                .sorted(Comparator.comparing(PointHistory::getCreatedAt).reversed()) // 新しい順
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return histories.size();
    }
}