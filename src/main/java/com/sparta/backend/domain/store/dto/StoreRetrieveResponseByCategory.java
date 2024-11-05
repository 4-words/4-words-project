package com.sparta.backend.domain.store.dto;

import java.time.LocalDateTime;

public record StoreRetrieveResponseByCategory(
        Long storeId,
        String name,
        String category,
        String image,
        String introduce,
        String address,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        Integer minOrderPrice
) {
}
