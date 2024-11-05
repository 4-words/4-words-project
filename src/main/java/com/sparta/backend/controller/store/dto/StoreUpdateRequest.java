package com.sparta.backend.controller.store.dto;

import java.time.LocalDateTime;

public record StoreUpdateRequest(
        String name,
        String category,
        String introduce,
        String address,
        LocalDateTime openedAt,
        LocalDateTime closedAt,
        Integer minOrderPrice
) {
}
