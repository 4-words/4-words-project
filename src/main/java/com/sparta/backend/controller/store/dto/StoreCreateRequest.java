package com.sparta.backend.controller.store.dto;

import java.time.LocalDateTime;

public record StoreCreateRequest(
        String name,
        String category,
        String introduce,
        String address,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        Integer minOrderPrice
) {
}
